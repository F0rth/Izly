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
import com.google.tagmanager.DataLayer.PersistentStore.Callback;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class DataLayerPersistentStoreImpl implements PersistentStore {
    private static final String CREATE_MAPS_TABLE = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{MAPS_TABLE, ID_FIELD, KEY_FIELD, "value", EXPIRE_FIELD});
    private static final String DATABASE_NAME = "google_tagmanager.db";
    private static final String EXPIRE_FIELD = "expires";
    private static final String ID_FIELD = "ID";
    private static final String KEY_FIELD = "key";
    private static final String MAPS_TABLE = "datalayer";
    private static final int MAX_NUM_STORED_ITEMS = 2000;
    private static final String VALUE_FIELD = "value";
    private Clock mClock;
    private final Context mContext;
    private DatabaseHelper mDbHelper;
    private final Executor mExecutor;
    private int mMaxNumStoredItems;

    @VisibleForTesting
    class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context, String str) {
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
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove(DataLayerPersistentStoreImpl.KEY_FIELD) || !hashSet.remove("value") || !hashSet.remove(DataLayerPersistentStoreImpl.ID_FIELD) || !hashSet.remove(DataLayerPersistentStoreImpl.EXPIRE_FIELD)) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                DataLayerPersistentStoreImpl.this.mContext.getDatabasePath(DataLayerPersistentStoreImpl.DATABASE_NAME).delete();
            }
            return sQLiteDatabase == null ? super.getWritableDatabase() : sQLiteDatabase;
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
            if (tablePresent(DataLayerPersistentStoreImpl.MAPS_TABLE, sQLiteDatabase)) {
                validateColumnsPresent(sQLiteDatabase);
            } else {
                sQLiteDatabase.execSQL(DataLayerPersistentStoreImpl.CREATE_MAPS_TABLE);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    static class KeyAndSerialized {
        final String mKey;
        final byte[] mSerialized;

        KeyAndSerialized(String str, byte[] bArr) {
            this.mKey = str;
            this.mSerialized = bArr;
        }

        public String toString() {
            return "KeyAndSerialized: key = " + this.mKey + " serialized hash = " + Arrays.hashCode(this.mSerialized);
        }
    }

    public DataLayerPersistentStoreImpl(Context context) {
        this(context, new Clock() {
            public long currentTimeMillis() {
                return System.currentTimeMillis();
            }
        }, DATABASE_NAME, MAX_NUM_STORED_ITEMS, Executors.newSingleThreadExecutor());
    }

    @VisibleForTesting
    DataLayerPersistentStoreImpl(Context context, Clock clock, String str, int i, Executor executor) {
        this.mContext = context;
        this.mClock = clock;
        this.mMaxNumStoredItems = i;
        this.mExecutor = executor;
        this.mDbHelper = new DatabaseHelper(this.mContext, str);
    }

    private void clearKeysWithPrefixSingleThreaded(String str) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for clearKeysWithPrefix.");
        if (writableDatabase != null) {
            try {
                Log.v("Cleared " + writableDatabase.delete(MAPS_TABLE, "key = ? OR key LIKE ?", new String[]{str, str + ".%"}) + " items");
            } catch (SQLiteException e) {
                Log.w("Error deleting entries with key prefix: " + str + " (" + e + ").");
            } finally {
                closeDatabaseConnection();
            }
        }
    }

    private void closeDatabaseConnection() {
        try {
            this.mDbHelper.close();
        } catch (SQLiteException e) {
        }
    }

    private void deleteEntries(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for deleteEntries.");
            if (writableDatabase != null) {
                try {
                    writableDatabase.delete(MAPS_TABLE, String.format("%s in (%s)", new Object[]{ID_FIELD, TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                } catch (SQLiteException e) {
                    Log.w("Error deleting entries " + Arrays.toString(strArr));
                }
            }
        }
    }

    private void deleteEntriesOlderThan(long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for deleteOlderThan.");
        if (writableDatabase != null) {
            try {
                Log.v("Deleted " + writableDatabase.delete(MAPS_TABLE, "expires <= ?", new String[]{Long.toString(j)}) + " expired items");
            } catch (SQLiteException e) {
                Log.w("Error deleting old entries.");
            }
        }
    }

    private int getNumStoredEntries() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for getNumStoredEntries.");
        if (writableDatabase != null) {
            try {
                cursor = writableDatabase.rawQuery("SELECT COUNT(*) from datalayer", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                Log.w("Error getting numStoredEntries");
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

    private SQLiteDatabase getWritableDatabase(String str) {
        try {
            return this.mDbHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            Log.w(str);
            return null;
        }
    }

    private List<KeyAndSerialized> loadSerialized() {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for loadSerialized.");
        List<KeyAndSerialized> arrayList = new ArrayList();
        if (writableDatabase == null) {
            return arrayList;
        }
        Cursor query = writableDatabase.query(MAPS_TABLE, new String[]{KEY_FIELD, "value"}, null, null, null, null, ID_FIELD, null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new KeyAndSerialized(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private List<KeyValue> loadSingleThreaded() {
        try {
            deleteEntriesOlderThan(this.mClock.currentTimeMillis());
            List<KeyValue> unserializeValues = unserializeValues(loadSerialized());
            return unserializeValues;
        } finally {
            closeDatabaseConnection();
        }
    }

    private void makeRoomForEntries(int i) {
        int numStoredEntries = (getNumStoredEntries() - this.mMaxNumStoredItems) + i;
        if (numStoredEntries > 0) {
            List peekEntryIds = peekEntryIds(numStoredEntries);
            Log.i("DataLayer store full, deleting " + peekEntryIds.size() + " entries to make room.");
            deleteEntries((String[]) peekEntryIds.toArray(new String[0]));
        }
    }

    private List<String> peekEntryIds(int i) {
        Cursor query;
        SQLiteException e;
        Throwable th;
        Cursor cursor = null;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            Log.w("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for peekEntryIds.");
        if (writableDatabase == null) {
            return arrayList;
        }
        try {
            String format = String.format("%s ASC", new Object[]{ID_FIELD});
            String num = Integer.toString(i);
            query = writableDatabase.query(MAPS_TABLE, new String[]{ID_FIELD}, null, null, null, null, format, num);
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
                    Log.w("Error in peekEntries fetching entryIds: " + e.getMessage());
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
            Log.w("Error in peekEntries fetching entryIds: " + e.getMessage());
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

    private void saveSingleThreaded(List<KeyAndSerialized> list, long j) {
        synchronized (this) {
            try {
                long currentTimeMillis = this.mClock.currentTimeMillis();
                deleteEntriesOlderThan(currentTimeMillis);
                makeRoomForEntries(list.size());
                writeEntriesToDatabase(list, currentTimeMillis + j);
                closeDatabaseConnection();
            } catch (Throwable th) {
                closeDatabaseConnection();
            }
        }
    }

    private byte[] serialize(Object obj) {
        Throwable th;
        byte[] bArr = null;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream;
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                bArr = byteArrayOutputStream.toByteArray();
                try {
                    objectOutputStream.close();
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayOutputStream.close();
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream = bArr;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            return bArr;
        } catch (Throwable th3) {
            byte[] bArr2 = bArr;
            th = th3;
            objectOutputStream = bArr2;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
        return bArr;
    }

    private List<KeyAndSerialized> serializeValues(List<KeyValue> list) {
        List<KeyAndSerialized> arrayList = new ArrayList();
        for (KeyValue keyValue : list) {
            arrayList.add(new KeyAndSerialized(keyValue.mKey, serialize(keyValue.mValue)));
        }
        return arrayList;
    }

    private Object unserialize(byte[] bArr) {
        ObjectInputStream objectInputStream;
        Object readObject;
        Throwable th;
        ObjectInputStream objectInputStream2 = null;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
            try {
                readObject = objectInputStream.readObject();
                try {
                    objectInputStream.close();
                    byteArrayInputStream.close();
                } catch (IOException e) {
                }
            } catch (IOException e2) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (ClassNotFoundException e4) {
                if (objectInputStream != null) {
                    try {
                        objectInputStream.close();
                    } catch (IOException e5) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (Throwable th2) {
                Throwable th3 = th2;
                objectInputStream2 = objectInputStream;
                th = th3;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream = objectInputStream2;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (ClassNotFoundException e8) {
            objectInputStream = objectInputStream2;
            if (objectInputStream != null) {
                objectInputStream.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (Throwable th4) {
            th = th4;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            throw th;
        }
        return readObject;
    }

    private List<KeyValue> unserializeValues(List<KeyAndSerialized> list) {
        List<KeyValue> arrayList = new ArrayList();
        for (KeyAndSerialized keyAndSerialized : list) {
            arrayList.add(new KeyValue(keyAndSerialized.mKey, unserialize(keyAndSerialized.mSerialized)));
        }
        return arrayList;
    }

    private void writeEntriesToDatabase(List<KeyAndSerialized> list, long j) {
        SQLiteDatabase writableDatabase = getWritableDatabase("Error opening database for writeEntryToDatabase.");
        if (writableDatabase != null) {
            for (KeyAndSerialized keyAndSerialized : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EXPIRE_FIELD, Long.valueOf(j));
                contentValues.put(KEY_FIELD, keyAndSerialized.mKey);
                contentValues.put("value", keyAndSerialized.mSerialized);
                writableDatabase.insert(MAPS_TABLE, null, contentValues);
            }
        }
    }

    public void clearKeysWithPrefix(final String str) {
        this.mExecutor.execute(new Runnable() {
            public void run() {
                DataLayerPersistentStoreImpl.this.clearKeysWithPrefixSingleThreaded(str);
            }
        });
    }

    public void loadSaved(final Callback callback) {
        this.mExecutor.execute(new Runnable() {
            public void run() {
                callback.onKeyValuesLoaded(DataLayerPersistentStoreImpl.this.loadSingleThreaded());
            }
        });
    }

    public void saveKeyValues(List<KeyValue> list, final long j) {
        final List serializeValues = serializeValues(list);
        this.mExecutor.execute(new Runnable() {
            public void run() {
                DataLayerPersistentStoreImpl.this.saveSingleThreaded(serializeValues, j);
            }
        });
    }
}
