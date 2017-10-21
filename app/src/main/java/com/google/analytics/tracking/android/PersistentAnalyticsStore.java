package com.google.analytics.tracking.android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.Command;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.impl.client.DefaultHttpClient;

class PersistentAnalyticsStore implements AnalyticsStore {
    private static final String CREATE_HITS_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' INTEGER NOT NULL, '%s' TEXT NOT NULL, '%s' TEXT NOT NULL, '%s' INTEGER);", new Object[]{HITS_TABLE, HIT_ID, HIT_TIME, HIT_URL, HIT_STRING, HIT_APP_ID});
    private static final String DATABASE_FILENAME = "google_analytics_v2.db";
    @VisibleForTesting
    static final String HITS_TABLE = "hits2";
    @VisibleForTesting
    static final String HIT_APP_ID = "hit_app_id";
    @VisibleForTesting
    static final String HIT_ID = "hit_id";
    @VisibleForTesting
    static final String HIT_STRING = "hit_string";
    @VisibleForTesting
    static final String HIT_TIME = "hit_time";
    @VisibleForTesting
    static final String HIT_URL = "hit_url";
    private Clock mClock;
    private final Context mContext;
    private final String mDatabaseName;
    private final AnalyticsDatabaseHelper mDbHelper;
    private volatile Dispatcher mDispatcher;
    private long mLastDeleteStaleHitsTime;
    private final AnalyticsStoreStateListener mListener;

    @VisibleForTesting
    class AnalyticsDatabaseHelper extends SQLiteOpenHelper {
        private boolean mBadDatabase;
        private long mLastDatabaseCheckTime = 0;

        AnalyticsDatabaseHelper(Context context, String str) {
            super(context, str, null, 1);
        }

        private boolean tablePresent(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            Throwable th;
            Cursor cursor2;
            Cursor query;
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
            Object obj = null;
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM hits2 WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (hashSet.remove(PersistentAnalyticsStore.HIT_ID) && hashSet.remove(PersistentAnalyticsStore.HIT_URL) && hashSet.remove(PersistentAnalyticsStore.HIT_STRING) && hashSet.remove(PersistentAnalyticsStore.HIT_TIME)) {
                    if (!hashSet.remove(PersistentAnalyticsStore.HIT_APP_ID)) {
                        obj = 1;
                    }
                    if (!hashSet.isEmpty()) {
                        throw new SQLiteException("Database has extra columns");
                    } else if (obj != null) {
                        sQLiteDatabase.execSQL("ALTER TABLE hits2 ADD COLUMN hit_app_id");
                        return;
                    } else {
                        return;
                    }
                }
                throw new SQLiteException("Database column missing");
            } finally {
                rawQuery.close();
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            if (!this.mBadDatabase || this.mLastDatabaseCheckTime + 3600000 <= PersistentAnalyticsStore.this.mClock.currentTimeMillis()) {
                SQLiteDatabase sQLiteDatabase = null;
                this.mBadDatabase = true;
                this.mLastDatabaseCheckTime = PersistentAnalyticsStore.this.mClock.currentTimeMillis();
                try {
                    sQLiteDatabase = super.getWritableDatabase();
                } catch (SQLiteException e) {
                    PersistentAnalyticsStore.this.mContext.getDatabasePath(PersistentAnalyticsStore.this.mDatabaseName).delete();
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
            if (tablePresent(PersistentAnalyticsStore.HITS_TABLE, sQLiteDatabase)) {
                validateColumnsPresent(sQLiteDatabase);
            } else {
                sQLiteDatabase.execSQL(PersistentAnalyticsStore.CREATE_HITS_TABLE);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }

        void setBadDatabase(boolean z) {
            this.mBadDatabase = z;
        }
    }

    PersistentAnalyticsStore(AnalyticsStoreStateListener analyticsStoreStateListener, Context context) {
        this(analyticsStoreStateListener, context, DATABASE_FILENAME);
    }

    @VisibleForTesting
    PersistentAnalyticsStore(AnalyticsStoreStateListener analyticsStoreStateListener, Context context, String str) {
        this.mContext = context.getApplicationContext();
        this.mDatabaseName = str;
        this.mListener = analyticsStoreStateListener;
        this.mClock = new Clock() {
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        };
        this.mDbHelper = new AnalyticsDatabaseHelper(this.mContext, this.mDatabaseName);
        this.mDispatcher = new SimpleNetworkDispatcher(new DefaultHttpClient(), this.mContext);
        this.mLastDeleteStaleHitsTime = 0;
    }

    private void fillVersionParameter(Map<String, String> map, Collection<Command> collection) {
        String substring = "&_v".substring(1);
        if (collection != null) {
            for (Command command : collection) {
                if (Command.APPEND_VERSION.equals(command.getId())) {
                    map.put(substring, command.getValue());
                    return;
                }
            }
        }
    }

    static String generateHitString(Map<String, String> map) {
        Iterable arrayList = new ArrayList(map.size());
        for (Entry entry : map.entrySet()) {
            arrayList.add(HitBuilder.encode((String) entry.getKey()) + "=" + HitBuilder.encode((String) entry.getValue()));
        }
        return TextUtils.join("&", arrayList);
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

    private void writeHitToDatabase(Map<String, String> map, long j, String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for putHit");
        if (writableDatabase != null) {
            long parseLong;
            ContentValues contentValues = new ContentValues();
            contentValues.put(HIT_STRING, generateHitString(map));
            contentValues.put(HIT_TIME, Long.valueOf(j));
            if (map.containsKey(Fields.ANDROID_APP_UID)) {
                try {
                    parseLong = Long.parseLong((String) map.get(Fields.ANDROID_APP_UID));
                } catch (NumberFormatException e) {
                }
                contentValues.put(HIT_APP_ID, Long.valueOf(parseLong));
                if (str == null) {
                    str = "http://www.google-analytics.com/collect";
                }
                if (str.length() != 0) {
                    Log.w("Empty path: not sending hit");
                }
                contentValues.put(HIT_URL, str);
                try {
                    writableDatabase.insert(HITS_TABLE, null, contentValues);
                    this.mListener.reportStoreIsEmpty(false);
                    return;
                } catch (SQLiteException e2) {
                    Log.w("Error storing hit");
                    return;
                }
            }
            parseLong = 0;
            contentValues.put(HIT_APP_ID, Long.valueOf(parseLong));
            if (str == null) {
                str = "http://www.google-analytics.com/collect";
            }
            if (str.length() != 0) {
                contentValues.put(HIT_URL, str);
                writableDatabase.insert(HITS_TABLE, null, contentValues);
                this.mListener.reportStoreIsEmpty(false);
                return;
            }
            Log.w("Empty path: not sending hit");
        }
    }

    public void clearHits(long j) {
        boolean z = false;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for clearHits");
        if (writableDatabase != null) {
            if (j == 0) {
                writableDatabase.delete(HITS_TABLE, null, null);
            } else {
                writableDatabase.delete(HITS_TABLE, "hit_app_id = ?", new String[]{Long.valueOf(j).toString()});
            }
            AnalyticsStoreStateListener analyticsStoreStateListener = this.mListener;
            if (getNumStoredHits() == 0) {
                z = true;
            }
            analyticsStoreStateListener.reportStoreIsEmpty(z);
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

    @Deprecated
    void deleteHits(Collection<Hit> collection) {
        if (collection == null || collection.isEmpty()) {
            Log.w("Empty/Null collection passed to deleteHits.");
            return;
        }
        String[] strArr = new String[collection.size()];
        int i = 0;
        for (Hit hitId : collection) {
            strArr[i] = String.valueOf(hitId.getHitId());
            i++;
        }
        deleteHits(strArr);
    }

    void deleteHits(String[] strArr) {
        boolean z = false;
        if (strArr == null || strArr.length == 0) {
            Log.w("Empty hitIds passed to deleteHits.");
            return;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for deleteHits.");
        if (writableDatabase != null) {
            try {
                writableDatabase.delete(HITS_TABLE, String.format("HIT_ID in (%s)", new Object[]{TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                AnalyticsStoreStateListener analyticsStoreStateListener = this.mListener;
                if (getNumStoredHits() == 0) {
                    z = true;
                }
                analyticsStoreStateListener.reportStoreIsEmpty(z);
            } catch (SQLiteException e) {
                Log.w("Error deleting hits " + strArr);
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
        AnalyticsStoreStateListener analyticsStoreStateListener = this.mListener;
        if (getNumStoredHits() == 0) {
            z = true;
        }
        analyticsStoreStateListener.reportStoreIsEmpty(z);
        return delete;
    }

    public void dispatch() {
        Log.v("Dispatch running...");
        if (this.mDispatcher.okToDispatch()) {
            List peekHits = peekHits(40);
            if (peekHits.isEmpty()) {
                Log.v("...nothing to dispatch");
                this.mListener.reportStoreIsEmpty(true);
                return;
            }
            int dispatchHits = this.mDispatcher.dispatchHits(peekHits);
            Log.v("sent " + dispatchHits + " of " + peekHits.size() + " hits");
            deleteHits(peekHits.subList(0, Math.min(dispatchHits, peekHits.size())));
            if (dispatchHits == peekHits.size() && getNumStoredHits() > 0) {
                GAServiceManager.getInstance().dispatchLocalHits();
            }
        }
    }

    @VisibleForTesting
    public AnalyticsDatabaseHelper getDbHelper() {
        return this.mDbHelper;
    }

    public Dispatcher getDispatcher() {
        return this.mDispatcher;
    }

    @VisibleForTesting
    AnalyticsDatabaseHelper getHelper() {
        return this.mDbHelper;
    }

    int getNumStoredHits() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for getNumStoredHits.");
        if (writableDatabase != null) {
            try {
                cursor = writableDatabase.rawQuery("SELECT COUNT(*) from hits2", null);
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

    List<String> peekHitIds(int i) {
        Cursor query;
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
    public java.util.List<com.google.analytics.tracking.android.Hit> peekHits(int r17) {
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
        r4 = new java.lang.Object[r4];	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r9 = java.lang.String.format(r3, r4);	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r3 = "hits2";
        r4 = 2;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r5 = 1;
        r6 = "hit_time";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r11 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x00d3, all -> 0x017f }
        r15 = new java.util.ArrayList;	 Catch:{ SQLiteException -> 0x0168, all -> 0x0177 }
        r15.<init>();	 Catch:{ SQLiteException -> 0x0168, all -> 0x0177 }
        r3 = r11.moveToFirst();	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        if (r3 == 0) goto L_0x0061;
    L_0x0048:
        r4 = new com.google.analytics.tracking.android.Hit;	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        r5 = 0;
        r3 = 0;
        r6 = r11.getLong(r3);	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        r3 = 1;
        r8 = r11.getLong(r3);	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        r4.<init>(r5, r6, r8);	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        r15.add(r4);	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        r3 = r11.moveToNext();	 Catch:{ SQLiteException -> 0x016d, all -> 0x0177 }
        if (r3 != 0) goto L_0x0048;
    L_0x0061:
        if (r11 == 0) goto L_0x0066;
    L_0x0063:
        r11.close();
    L_0x0066:
        r3 = "%s ASC";
        r4 = 1;
        r4 = new java.lang.Object[r4];	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r9 = java.lang.String.format(r3, r4);	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r10 = java.lang.Integer.toString(r17);	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r3 = "hits2";
        r4 = 3;
        r4 = new java.lang.String[r4];	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r5 = 0;
        r6 = "hit_id";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r5 = 1;
        r6 = "hit_string";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r5 = 2;
        r6 = "hit_url";
        r4[r5] = r6;	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r3 = r2.query(r3, r4, r5, r6, r7, r8, r9, r10);	 Catch:{ SQLiteException -> 0x0165, all -> 0x017c }
        r2 = r3.moveToFirst();	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        if (r2 == 0) goto L_0x00cb;
    L_0x009a:
        r4 = r12;
    L_0x009b:
        r0 = r3;
        r0 = (android.database.sqlite.SQLiteCursor) r0;	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = r0;
        r2 = r2.getWindow();	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = r2.getNumRows();	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        if (r2 <= 0) goto L_0x00fa;
    L_0x00a9:
        r2 = r15.get(r4);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = (com.google.analytics.tracking.android.Hit) r2;	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r5 = 1;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2.setHitString(r5);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = r15.get(r4);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = (com.google.analytics.tracking.android.Hit) r2;	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r5 = 2;
        r5 = r3.getString(r5);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2.setHitUrl(r5);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
    L_0x00c5:
        r2 = r3.moveToNext();	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        if (r2 != 0) goto L_0x0172;
    L_0x00cb:
        if (r3 == 0) goto L_0x00d0;
    L_0x00cd:
        r3.close();
    L_0x00d0:
        r2 = r15;
        goto L_0x0013;
    L_0x00d3:
        r2 = move-exception;
        r3 = r2;
        r2 = r14;
    L_0x00d6:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f3 }
        r5 = "Error in peekHits fetching hitIds: ";
        r4.<init>(r5);	 Catch:{ all -> 0x00f3 }
        r3 = r3.getMessage();	 Catch:{ all -> 0x00f3 }
        r3 = r4.append(r3);	 Catch:{ all -> 0x00f3 }
        r3 = r3.toString();	 Catch:{ all -> 0x00f3 }
        com.google.analytics.tracking.android.Log.w(r3);	 Catch:{ all -> 0x00f3 }
        if (r11 == 0) goto L_0x0013;
    L_0x00ee:
        r11.close();
        goto L_0x0013;
    L_0x00f3:
        r2 = move-exception;
    L_0x00f4:
        if (r11 == 0) goto L_0x00f9;
    L_0x00f6:
        r11.close();
    L_0x00f9:
        throw r2;
    L_0x00fa:
        r5 = "HitString for hitId %d too large.  Hit will be deleted.";
        r2 = 1;
        r6 = new java.lang.Object[r2];	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r7 = 0;
        r2 = r15.get(r4);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = (com.google.analytics.tracking.android.Hit) r2;	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r8 = r2.getHitId();	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = java.lang.Long.valueOf(r8);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r6[r7] = r2;	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        r2 = java.lang.String.format(r5, r6);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        com.google.analytics.tracking.android.Log.w(r2);	 Catch:{ SQLiteException -> 0x0118, all -> 0x017a }
        goto L_0x00c5;
    L_0x0118:
        r2 = move-exception;
    L_0x0119:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0156 }
        r5 = "Error in peekHits fetching hitString: ";
        r4.<init>(r5);	 Catch:{ all -> 0x0156 }
        r2 = r2.getMessage();	 Catch:{ all -> 0x0156 }
        r2 = r4.append(r2);	 Catch:{ all -> 0x0156 }
        r2 = r2.toString();	 Catch:{ all -> 0x0156 }
        com.google.analytics.tracking.android.Log.w(r2);	 Catch:{ all -> 0x0156 }
        r5 = new java.util.ArrayList;	 Catch:{ all -> 0x0156 }
        r5.<init>();	 Catch:{ all -> 0x0156 }
        r6 = r15.iterator();	 Catch:{ all -> 0x0156 }
        r4 = r12;
    L_0x0139:
        r2 = r6.hasNext();	 Catch:{ all -> 0x0156 }
        if (r2 == 0) goto L_0x015d;
    L_0x013f:
        r2 = r6.next();	 Catch:{ all -> 0x0156 }
        r2 = (com.google.analytics.tracking.android.Hit) r2;	 Catch:{ all -> 0x0156 }
        r7 = r2.getHitParams();	 Catch:{ all -> 0x0156 }
        r7 = android.text.TextUtils.isEmpty(r7);	 Catch:{ all -> 0x0156 }
        if (r7 == 0) goto L_0x0152;
    L_0x014f:
        if (r4 != 0) goto L_0x015d;
    L_0x0151:
        r4 = r13;
    L_0x0152:
        r5.add(r2);	 Catch:{ all -> 0x0156 }
        goto L_0x0139;
    L_0x0156:
        r2 = move-exception;
    L_0x0157:
        if (r3 == 0) goto L_0x015c;
    L_0x0159:
        r3.close();
    L_0x015c:
        throw r2;
    L_0x015d:
        if (r3 == 0) goto L_0x0162;
    L_0x015f:
        r3.close();
    L_0x0162:
        r2 = r5;
        goto L_0x0013;
    L_0x0165:
        r2 = move-exception;
        r3 = r11;
        goto L_0x0119;
    L_0x0168:
        r2 = move-exception;
        r3 = r2;
        r2 = r14;
        goto L_0x00d6;
    L_0x016d:
        r2 = move-exception;
        r3 = r2;
        r2 = r15;
        goto L_0x00d6;
    L_0x0172:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x009b;
    L_0x0177:
        r2 = move-exception;
        goto L_0x00f4;
    L_0x017a:
        r2 = move-exception;
        goto L_0x0157;
    L_0x017c:
        r2 = move-exception;
        r3 = r11;
        goto L_0x0157;
    L_0x017f:
        r2 = move-exception;
        goto L_0x00f4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.analytics.tracking.android.PersistentAnalyticsStore.peekHits(int):java.util.List<com.google.analytics.tracking.android.Hit>");
    }

    public void putHit(Map<String, String> map, long j, String str, Collection<Command> collection) {
        deleteStaleHits();
        removeOldHitIfFull();
        fillVersionParameter(map, collection);
        writeHitToDatabase(map, j, str);
    }

    @VisibleForTesting
    public void setClock(Clock clock) {
        this.mClock = clock;
    }

    public void setDispatch(boolean z) {
        this.mDispatcher = z ? new SimpleNetworkDispatcher(new DefaultHttpClient(), this.mContext) : new NoopDispatcher();
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
