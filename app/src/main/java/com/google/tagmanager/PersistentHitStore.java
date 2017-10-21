package com.google.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.common.util.VisibleForTesting;
import com.google.tagmanager.SimpleNetworkDispatcher.DispatchListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.http.impl.client.DefaultHttpClient;

class PersistentHitStore implements HitStore {
    private static final String CREATE_HITS_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL,'%s' INTEGER NOT NULL);", new Object[]{HITS_TABLE, HIT_ID, HIT_TIME, HIT_URL, HIT_FIRST_DISPATCH_TIME});
    private static final String DATABASE_FILENAME = "gtm_urls.db";
    @VisibleForTesting
    static final String HITS_TABLE = "gtm_hits";
    static final long HIT_DISPATCH_RETRY_WINDOW = 14400000;
    @VisibleForTesting
    static final String HIT_FIRST_DISPATCH_TIME = "hit_first_send_time";
    @VisibleForTesting
    static final String HIT_ID = "hit_id";
    private static final String HIT_ID_WHERE_CLAUSE = "hit_id=?";
    @VisibleForTesting
    static final String HIT_TIME = "hit_time";
    @VisibleForTesting
    static final String HIT_URL = "hit_url";
    private Clock mClock;
    private final Context mContext;
    private final String mDatabaseName;
    private final UrlDatabaseHelper mDbHelper;
    private volatile Dispatcher mDispatcher;
    private long mLastDeleteStaleHitsTime;
    private final HitStoreStateListener mListener;

    @VisibleForTesting
    class StoreDispatchListener implements DispatchListener {
        StoreDispatchListener() {
        }

        public void onHitDispatched(Hit hit) {
            PersistentHitStore.this.deleteHit(hit.getHitId());
        }

        public void onHitPermanentDispatchFailure(Hit hit) {
            PersistentHitStore.this.deleteHit(hit.getHitId());
            Log.v("Permanent failure dispatching hitId: " + hit.getHitId());
        }

        public void onHitTransientDispatchFailure(Hit hit) {
            long hitFirstDispatchTime = hit.getHitFirstDispatchTime();
            if (hitFirstDispatchTime == 0) {
                PersistentHitStore.this.setHitFirstDispatchTime(hit.getHitId(), PersistentHitStore.this.mClock.currentTimeMillis());
            } else if (hitFirstDispatchTime + PersistentHitStore.HIT_DISPATCH_RETRY_WINDOW < PersistentHitStore.this.mClock.currentTimeMillis()) {
                PersistentHitStore.this.deleteHit(hit.getHitId());
                Log.v("Giving up on failed hitId: " + hit.getHitId());
            }
        }
    }

    @VisibleForTesting
    class UrlDatabaseHelper extends SQLiteOpenHelper {
        private boolean mBadDatabase;
        private long mLastDatabaseCheckTime = 0;

        UrlDatabaseHelper(Context context, String str) {
            super(context, str, null, 1);
        }

        private boolean tablePresent(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor query;
            Cursor cursor;
            Throwable th;
            Cursor cursor2;
            try {
                SQLiteDatabase sQLiteDatabase2 = sQLiteDatabase;
                query = sQLiteDatabase2.query("SQLITE_MASTER", new String[]{"name"}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e) {
                    try {
                        Log.w("Error querying for table " + str);
                        if (query != null) {
                            query.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        cursor = query;
                        th = th2;
                        cursor2 = cursor;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th22) {
                    cursor = query;
                    th = th22;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                query = null;
                Log.w("Error querying for table " + str);
                if (query != null) {
                    query.close();
                }
                return false;
            } catch (Throwable th222) {
                th = th222;
                cursor2 = null;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }

        private void validateColumnsPresent(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM gtm_hits WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove(PersistentHitStore.HIT_ID) || !hashSet.remove(PersistentHitStore.HIT_URL) || !hashSet.remove(PersistentHitStore.HIT_TIME) || !hashSet.remove(PersistentHitStore.HIT_FIRST_DISPATCH_TIME)) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!this.mBadDatabase || this.mLastDatabaseCheckTime + 3600000 <= PersistentHitStore.this.mClock.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.mBadDatabase = true;
                this.mLastDatabaseCheckTime = PersistentHitStore.this.mClock.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    PersistentHitStore.this.mContext.getDatabasePath(PersistentHitStore.this.mDatabaseName).delete();
                }
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = super.getWritableDatabase();
                }
                this.mBadDatabase = false;
                return sQLiteDatabase;
            }
            throw new SQLiteException("Database creation failed");
        }

        boolean isBadDatabase() {
            return this.mBadDatabase;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            FutureApis.setOwnerOnlyReadWrite(sQLiteDatabase.getPath());
        }

        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (tablePresent(PersistentHitStore.HITS_TABLE, sQLiteDatabase)) {
                validateColumnsPresent(sQLiteDatabase);
            } else {
                sQLiteDatabase.execSQL(PersistentHitStore.CREATE_HITS_TABLE);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        void setBadDatabase(boolean z) {
            this.mBadDatabase = z;
        }
    }

    PersistentHitStore(HitStoreStateListener hitStoreStateListener, Context context) {
        this(hitStoreStateListener, context, DATABASE_FILENAME);
    }

    @VisibleForTesting
    PersistentHitStore(HitStoreStateListener hitStoreStateListener, Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.mDatabaseName = str;
        this.mListener = hitStoreStateListener;
        this.mClock = new Clock() {
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.mDbHelper = new UrlDatabaseHelper(this.mContext, this.mDatabaseName);
        this.mDispatcher = new SimpleNetworkDispatcher(new DefaultHttpClient(), this.mContext, new StoreDispatchListener());
        this.mLastDeleteStaleHitsTime = 0;
    }

    private void deleteHit(long j) {
        deleteHits(new String[]{String.valueOf(j)});
    }

    private SQLiteDatabase getWritableDatabase(String str) {
        try {
            return this.mDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.w(str);
            return null;
        }
    }

    private void removeOldHitIfFull() {
        int numStoredHits = (getNumStoredHits() - 2000) + 1;
        if (numStoredHits > 0) {
            List peekHitIds = peekHitIds(numStoredHits);
            Log.v("Store full, deleting " + peekHitIds.size() + " hits to make room.");
            deleteHits((String[]) peekHitIds.toArray(new String[0]));
        }
    }

    private void setHitFirstDispatchTime(long j, long j2) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for getNumStoredHits.");
        if (writableDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(HIT_FIRST_DISPATCH_TIME, Long.valueOf(j2));
            try {
                writableDatabase.update(HITS_TABLE, contentValues, HIT_ID_WHERE_CLAUSE, new String[]{String.valueOf(j)});
            } catch (SQLiteException e) {
                Log.w("Error setting HIT_FIRST_DISPATCH_TIME for hitId: " + j);
                deleteHit(j);
            }
        }
    }

    private void writeHitToDatabase(long j, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for putHit");
        if (writableDatabase != null) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(HIT_TIME, Long.valueOf(j));
            contentValues.put(HIT_URL, str);
            contentValues.put(HIT_FIRST_DISPATCH_TIME, Integer.valueOf(0));
            try {
                writableDatabase.insert(HITS_TABLE, null, contentValues);
                this.mListener.reportStoreIsEmpty(false);
            } catch (SQLiteException e) {
                Log.w("Error storing hit");
            }
        }
    }

    public void close() {
        try {
            this.mDbHelper.getWritableDatabase().close();
            this.mDispatcher.close();
        } catch (SQLiteException e) {
            Log.w("Error opening database for close");
        }
    }

    void deleteHits(String[] strArr) {
        boolean z = false;
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for deleteHits.");
            if (writableDatabase != null) {
                try {
                    writableDatabase.delete(HITS_TABLE, String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                    HitStoreStateListener hitStoreStateListener = this.mListener;
                    if (getNumStoredHits() == 0) {
                        z = true;
                    }
                    hitStoreStateListener.reportStoreIsEmpty(z);
                } catch (SQLiteException e) {
                    Log.w("Error deleting hits");
                }
            }
        }
    }

    int deleteStaleHits() {
        boolean z = false;
        long currentTimeMillis = this.mClock.currentTimeMillis();
        if (currentTimeMillis <= this.mLastDeleteStaleHitsTime + 86400000) {
            return 0;
        }
        this.mLastDeleteStaleHitsTime = currentTimeMillis;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for deleteStaleHits.");
        if (writableDatabase == null) {
            return 0;
        }
        int delete = writableDatabase.delete(HITS_TABLE, "HIT_TIME < ?", new String[]{Long.toString(this.mClock.currentTimeMillis() - 2592000000L)});
        HitStoreStateListener hitStoreStateListener = this.mListener;
        if (getNumStoredHits() == 0) {
            z = true;
        }
        hitStoreStateListener.reportStoreIsEmpty(z);
        return delete;
    }

    public void dispatch() {
        Log.v("GTM Dispatch running...");
        if (this.mDispatcher.okToDispatch()) {
            List peekHits = peekHits(40);
            if (peekHits.isEmpty()) {
                Log.v("...nothing to dispatch");
                this.mListener.reportStoreIsEmpty(true);
                return;
            }
            this.mDispatcher.dispatchHits(peekHits);
            if (getNumStoredUntriedHits() > 0) {
                ServiceManagerImpl.getInstance().dispatch();
            }
        }
    }

    @VisibleForTesting
    public UrlDatabaseHelper getDbHelper() {
        return this.mDbHelper;
    }

    public Dispatcher getDispatcher() {
        return this.mDispatcher;
    }

    @VisibleForTesting
    UrlDatabaseHelper getHelper() {
        return this.mDbHelper;
    }

    int getNumStoredHits() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for getNumStoredHits.");
        if (writableDatabase != null) {
            try {
                cursor = writableDatabase.rawQuery("SELECT COUNT(*) from gtm_hits", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                Log.w("Error getting numStoredHits");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    int getNumStoredUntriedHits() {
        Cursor query;
        int count;
        Throwable th;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for getNumStoredHits.");
        if (writableDatabase == null) {
            return 0;
        }
        try {
            query = writableDatabase.query(HITS_TABLE, new String[]{HIT_ID, HIT_FIRST_DISPATCH_TIME}, "hit_first_send_time=0", null, null, null, null);
            try {
                count = query.getCount();
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e) {
                try {
                    Log.w("Error getting num untried hits");
                    if (query == null) {
                        count = 0;
                    } else {
                        query.close();
                        count = 0;
                    }
                    return count;
                } catch (Throwable th2) {
                    th = th2;
                    if (query != null) {
                        query.close();
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        } catch (SQLiteException e2) {
            query = null;
            Log.w("Error getting num untried hits");
            if (query == null) {
                query.close();
                count = 0;
            } else {
                count = 0;
            }
            return count;
        } catch (Throwable th4) {
            th = th4;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return count;
    }

    List<String> peekHitIds(int i) {
        SQLiteException e;
        Throwable th;
        Cursor cursor = null;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            Log.w("Invalid maxHits specified. Skipping");
            return arrayList;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for peekHitIds.");
        if (writableDatabase == null) {
            return arrayList;
        }
        Cursor query;
        try {
            String format = String.format("%s ASC", new Object[]{HIT_ID});
            String num = Integer.toString(i);
            query = writableDatabase.query(HITS_TABLE, new String[]{HIT_ID}, null, null, null, null, format, num);
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    Log.w("Error in peekHits fetching hitIds: " + e.getMessage());
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
                } catch (Throwable th2) {
                    th = th2;
                    cursor = query;
                    if (cursor != null) {
                        cursor.close();
                    }
                    throw th;
                }
            }
        } catch (SQLiteException e3) {
            e = e3;
            query = null;
            Log.w("Error in peekHits fetching hitIds: " + e.getMessage());
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
        return arrayList;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<com.google.tagmanager.Hit> peekHits(int r17) {
        /*
        r16 = this;
        r13 = 1;
        r12 = 0;
        r11 = 0;
        r14 = new java.util.ArrayList;
        r14.<init>();
        r2 = "Error opening database for peekHits";
        r0 = r16;
        r2 = r0.getWritableDatabase(r2);
        if (r2 != 0) goto L_0x0014;
    L_0x0012:
        r2 = r14;
    L_0x0013:
        return r2;
    L_0x0014:
        r3 = "%s ASC";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r9 = java.lang.String.format(r3, r4);	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r3 = "gtm_hits";
        r4 = 3;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r5 = 1;
        r6 = "hit_time";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r5 = 2;
        r6 = "hit_first_send_time";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x00c9, all -> 0x0175 }
        r15 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x015e, all -> 0x016d }
        r15.<init>();	 Catch:{ SQLiteException -> 0x015e, all -> 0x016d }
        r3 = r11.moveToFirst();	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        if (r3 == 0) goto L_0x006a;
    L_0x004d:
        r3 = new com.google.tagmanager.Hit;	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r4 = 0;
        r4 = r11.getLong(r4);	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r6 = 1;
        r6 = r11.getLong(r6);	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r8 = 2;
        r8 = r11.getLong(r8);	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r3.<init>(r4, r6, r8);	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r15.add(r3);	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        r3 = r11.moveToNext();	 Catch:{ SQLiteException -> 0x0163, all -> 0x016d }
        if (r3 != 0) goto L_0x004d;
    L_0x006a:
        if (r11 == 0) goto L_0x006f;
    L_0x006c:
        r11.close();
    L_0x006f:
        r3 = "%s ASC";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r9 = java.lang.String.format(r3, r4);	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r3 = "gtm_hits";
        r4 = 2;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r5 = 1;
        r6 = "hit_url";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x015b, all -> 0x0172 }
        r2 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        if (r2 == 0) goto L_0x00c1;
    L_0x009e:
        r4 = r12;
    L_0x009f:
        r0 = r3;
        r0 = (android.database.sqlite.SQLiteCursor) r0;	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = r0;
        r2 = r2.getWindow();	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = r2.getNumRows();	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        if (r2 <= 0) goto L_0x00f0;
    L_0x00ad:
        r2 = r15.get(r4);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = (com.google.tagmanager.Hit) r2;	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r5 = 1;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2.setHitUrl(r5);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
    L_0x00bb:
        r2 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        if (r2 != 0) goto L_0x0168;
    L_0x00c1:
        if (r3 == 0) goto L_0x00c6;
    L_0x00c3:
        r3.close();
    L_0x00c6:
        r2 = r15;
        goto L_0x0013;
    L_0x00c9:
        r2 = move-exception;
        r3 = r2;
        r2 = r14;
    L_0x00cc:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00e9 }
        r5 = "Error in peekHits fetching hitIds: ";
        r4.<init>(r5);	 Catch:{ all -> 0x00e9 }
        r3 = r3.getMessage();	 Catch:{ all -> 0x00e9 }
        r3 = r4.append(r3);	 Catch:{ all -> 0x00e9 }
        r3 = r3.toString();	 Catch:{ all -> 0x00e9 }
        com.google.tagmanager.Log.w(r3);	 Catch:{ all -> 0x00e9 }
        if (r11 == 0) goto L_0x0013;
    L_0x00e4:
        r11.close();
        goto L_0x0013;
    L_0x00e9:
        r2 = move-exception;
    L_0x00ea:
        if (r11 == 0) goto L_0x00ef;
    L_0x00ec:
        r11.close();
    L_0x00ef:
        throw r2;
    L_0x00f0:
        r5 = "HitString for hitId %d too large.  Hit will be deleted.";
        r2 = 1;
        r6 = new java.lang.Object[r2];	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r7 = 0;
        r2 = r15.get(r4);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = (com.google.tagmanager.Hit) r2;	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r8 = r2.getHitId();	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = java.lang.Long.valueOf(r8);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r6[r7] = r2;	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        r2 = java.lang.String.format(r5, r6);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        com.google.tagmanager.Log.w(r2);	 Catch:{ SQLiteException -> 0x010e, all -> 0x0170 }
        goto L_0x00bb;
    L_0x010e:
        r2 = move-exception;
    L_0x010f:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x014c }
        r5 = "Error in peekHits fetching hit url: ";
        r4.<init>(r5);	 Catch:{ all -> 0x014c }
        r2 = r2.getMessage();	 Catch:{ all -> 0x014c }
        r2 = r4.append(r2);	 Catch:{ all -> 0x014c }
        r2 = r2.toString();	 Catch:{ all -> 0x014c }
        com.google.tagmanager.Log.w(r2);	 Catch:{ all -> 0x014c }
        r5 = new java.util.ArrayList;	 Catch:{ all -> 0x014c }
        r5.<init>();	 Catch:{ all -> 0x014c }
        r6 = r15.iterator();	 Catch:{ all -> 0x014c }
        r4 = r12;
    L_0x012f:
        r2 = r6.hasNext();	 Catch:{ all -> 0x014c }
        if (r2 == 0) goto L_0x0153;
    L_0x0135:
        r2 = r6.next();	 Catch:{ all -> 0x014c }
        r2 = (com.google.tagmanager.Hit) r2;	 Catch:{ all -> 0x014c }
        r7 = r2.getHitUrl();	 Catch:{ all -> 0x014c }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x014c }
        if (r7 == 0) goto L_0x0148;
    L_0x0145:
        if (r4 != 0) goto L_0x0153;
    L_0x0147:
        r4 = r13;
    L_0x0148:
        r5.add(r2);	 Catch:{ all -> 0x014c }
        goto L_0x012f;
    L_0x014c:
        r2 = move-exception;
    L_0x014d:
        if (r3 == 0) goto L_0x0152;
    L_0x014f:
        r3.close();
    L_0x0152:
        throw r2;
    L_0x0153:
        if (r3 == 0) goto L_0x0158;
    L_0x0155:
        r3.close();
    L_0x0158:
        r2 = r5;
        goto L_0x0013;
    L_0x015b:
        r2 = move-exception;
        r3 = r11;
        goto L_0x010f;
    L_0x015e:
        r2 = move-exception;
        r3 = r2;
        r2 = r14;
        goto L_0x00cc;
    L_0x0163:
        r2 = move-exception;
        r3 = r2;
        r2 = r15;
        goto L_0x00cc;
    L_0x0168:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x009f;
    L_0x016d:
        r2 = move-exception;
        goto L_0x00ea;
    L_0x0170:
        r2 = move-exception;
        goto L_0x014d;
    L_0x0172:
        r2 = move-exception;
        r3 = r11;
        goto L_0x014d;
    L_0x0175:
        r2 = move-exception;
        goto L_0x00ea;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.tagmanager.PersistentHitStore.peekHits(int):java.util.List<com.google.tagmanager.Hit>");
    }

    public void putHit(long j, String str) {
        deleteStaleHits();
        removeOldHitIfFull();
        writeHitToDatabase(j, str);
    }

    @VisibleForTesting
    public void setClock(Clock clock) {
        this.mClock = clock;
    }

    @VisibleForTesting
    void setDispatcher(Dispatcher dispatcher) {
        this.mDispatcher = dispatcher;
    }

    @VisibleForTesting
    void setLastDeleteStaleHitsTime(long j) {
        this.mLastDeleteStaleHitsTime = j;
    }
}
