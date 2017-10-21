package com.ad4screen.sdk.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ad4screen.sdk.Log;

public class a extends SQLiteOpenHelper {
    private static a a = null;

    private a(Context context) {
        super(context, "accengage.db", null, 3400);
    }

    public static a a(Context context) {
        synchronized (a.class) {
            try {
                if (a == null) {
                    a = new a(context);
                }
                a aVar = a;
                return aVar;
            } finally {
                Object obj = a.class;
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Log.info("A4SDatabaseHelper|onCreate create DB: accengage.db version: 3400");
        sQLiteDatabase.execSQL("CREATE TABLE beacons (_id INTEGER PRIMARY KEY AUTOINCREMENT, server_id TEXT NOT NULL, external_id TEXT NOT NULL, name TEXT, uuid TEXT NOT NULL, major INTEGER NOT NULL, minor INTEGER NOT NULL, detected_time TEXT NOT NULL, notified_time TEXT NOT NULL );");
        sQLiteDatabase.execSQL("CREATE TABLE beacon_params (_id INTEGER PRIMARY KEY AUTOINCREMENT, beacon_id INTEGER REFERENCES beacons(_id), param_key TEXT NOT NULL, param_value TEXT );");
        sQLiteDatabase.execSQL("CREATE TABLE geofences (_id INTEGER PRIMARY KEY AUTOINCREMENT, server_id TEXT NOT NULL, external_id TEXT NOT NULL, name TEXT, latitude REAL NOT NULL, longitude REAL NOT NULL, radius INTEGER NOT NULL, detected_time TEXT NOT NULL, notified_time TEXT NOT NULL );");
        sQLiteDatabase.execSQL("CREATE TABLE geofence_params (_id INTEGER PRIMARY KEY AUTOINCREMENT, geofence_id INTEGER REFERENCES geofences(_id), param_key TEXT NOT NULL, param_value TEXT );");
        sQLiteDatabase.execSQL("CREATE TABLE beacon_geofence_groups (_id INTEGER PRIMARY KEY AUTOINCREMENT, server_id TEXT NOT NULL );");
        sQLiteDatabase.execSQL("CREATE TABLE beacon_groups (_id INTEGER PRIMARY KEY AUTOINCREMENT, beacon_id INTEGER REFERENCES beacons(_id), group_id INTEGER REFERENCES beacon_geofence_groups(_id) );");
        sQLiteDatabase.execSQL("CREATE TABLE geofence_groups (_id INTEGER PRIMARY KEY AUTOINCREMENT, geofence_id INTEGER REFERENCES geofences(_id), group_id INTEGER REFERENCES beacon_geofence_groups(_id) );");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }
}
