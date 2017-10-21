package com.ezeeworld.b4s.android.sdk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ezeeworld.b4s.android.sdk.monitor.InteractionEvent;
import com.ezeeworld.b4s.android.sdk.server.SessionRegistration;
import com.ezeeworld.b4s.android.sdk.server.TrackingBeacons;
import com.ezeeworld.b4s.android.sdk.server.TrackingBeacons.BeaconRssi;
import com.ezeeworld.b4s.android.sdk.server.TrackingLocation;

import nl.qbusict.cupboard.Cupboard;
import nl.qbusict.cupboard.CupboardBuilder;
import nl.qbusict.cupboard.DatabaseCompartment;

public class CupboardDbHelper extends SQLiteOpenHelper {
    private static Cupboard a;
    private static SQLiteDatabase b;
    private static DatabaseCompartment c;

    static {
        a = new Cupboard();
        Cupboard build = new CupboardBuilder().useAnnotations().build();
        a = build;
        build.register(InteractionEvent.class);
        a.register(TrackingBeacons.class);
        a.register(BeaconRssi.class);
        a.register(SessionRegistration.class);
        a.register(TrackingLocation.class);
    }

    public CupboardDbHelper(Context context) {
        super(context, "B4S.db", null, 10);
    }

    public static SQLiteDatabase connection(Context context) {
        synchronized (CupboardDbHelper.class) {
            Class applicationContext;
            try {
                if (b == null) {
                    applicationContext = context.getApplicationContext();
                    b = new CupboardDbHelper(applicationContext).getWritableDatabase();
                }
                SQLiteDatabase sQLiteDatabase = b;
                return sQLiteDatabase;
            } finally {
                applicationContext = CupboardDbHelper.class;
            }
        }
    }

    public static DatabaseCompartment database(Context context) {
        synchronized (CupboardDbHelper.class) {
            Class connection;
            try {
                if (c == null) {
                    Cupboard cupboard = a;
                    connection = connection(context);
                    c = cupboard.withDatabase(connection);
                }
                DatabaseCompartment databaseCompartment = c;
                return databaseCompartment;
            } finally {
                connection = CupboardDbHelper.class;
            }
        }
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        a.withDatabase(sQLiteDatabase).createTables();
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        a.withDatabase(sQLiteDatabase).upgradeTables();
    }
}
