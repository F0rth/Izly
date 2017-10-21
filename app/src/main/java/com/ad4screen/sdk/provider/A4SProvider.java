package com.ad4screen.sdk.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.text.TextUtils;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract;
import com.ad4screen.sdk.contract.A4SContract.BeaconGeofenceGroups;
import com.ad4screen.sdk.contract.A4SContract.BeaconGroups;
import com.ad4screen.sdk.contract.A4SContract.BeaconParams;
import com.ad4screen.sdk.contract.A4SContract.Beacons;
import com.ad4screen.sdk.contract.A4SContract.GeofenceGroups;
import com.ad4screen.sdk.contract.A4SContract.GeofenceParams;
import com.ad4screen.sdk.contract.A4SContract.Geofences;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.plugins.model.Geofence;

import java.util.ArrayList;
import java.util.List;

public class A4SProvider extends ContentProvider {
    private static UriMatcher a = new UriMatcher(-1);
    private SQLiteDatabase b;
    private b c;
    private a d;
    private c e;

    class a extends b {
        final /* synthetic */ A4SProvider a;

        public a(A4SProvider a4SProvider, com.ad4screen.sdk.provider.c.a aVar) {
            this.a = a4SProvider;
            super(aVar, a4SProvider.getContext());
        }

        public void updateBeacons(List<Beacon> list) {
            List arrayList = new ArrayList();
            this.a.b.beginTransaction();
            try {
                int i;
                int i2 = 0;
                int i3 = 0;
                for (Beacon beacon : list) {
                    beacon.rowId = hasBeacon(beacon.id);
                    if (beacon.rowId < 0) {
                        if (beacon.isNeedToBeRemoved) {
                            i = i3;
                        } else {
                            arrayList.add(beacon);
                        }
                    } else if (beacon.isNeedToBeRemoved) {
                        i2 = deleteBeacon(beacon) + i2;
                    } else {
                        i = updateBeacon(beacon) + i3;
                    }
                    i3 = i;
                }
                i = arrayList.size() > 0 ? insertBeacons(arrayList) : 0;
                this.a.b.setTransactionSuccessful();
                Log.debug("A4SProvider|updateBeacons inserted: " + i + ", updated: " + i3 + ", deleted: " + i2);
            } finally {
                this.a.b.endTransaction();
            }
        }
    }

    class b implements com.ad4screen.sdk.provider.c.a {
        final /* synthetic */ A4SProvider a;

        private b(A4SProvider a4SProvider) {
            this.a = a4SProvider;
        }

        public int a(Uri uri, ContentValues contentValues, String str, String[] strArr) {
            return this.a.update(uri, contentValues, str, strArr);
        }

        public int a(Uri uri, String str, String[] strArr) {
            return this.a.delete(uri, str, strArr);
        }

        public int a(Uri uri, ContentValues[] contentValuesArr) {
            return this.a.bulkInsert(uri, contentValuesArr);
        }

        public Cursor a(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
            return this.a.query(uri, strArr, str, strArr2, str2);
        }

        public Uri a(Uri uri, ContentValues contentValues) {
            return this.a.insert(uri, contentValues);
        }

        public ContentProviderResult[] a(ArrayList<ContentProviderOperation> arrayList) throws OperationApplicationException {
            return VERSION.SDK_INT >= 5 ? this.a.applyBatch(arrayList) : null;
        }
    }

    class c extends e {
        final /* synthetic */ A4SProvider a;

        public c(A4SProvider a4SProvider, com.ad4screen.sdk.provider.c.a aVar) {
            this.a = a4SProvider;
            super(aVar, a4SProvider.getContext());
        }

        public void updateGeofences(List<Geofence> list) {
            List arrayList = new ArrayList();
            this.a.b.beginTransaction();
            try {
                int i;
                int i2 = 0;
                int i3 = 0;
                for (Geofence geofence : list) {
                    long hasGeofence = hasGeofence(geofence.getId());
                    geofence.setRowId(hasGeofence);
                    if (hasGeofence < 0) {
                        if (geofence.isNeedToBeRemoved()) {
                            i = i3;
                        } else {
                            arrayList.add(geofence);
                        }
                    } else if (geofence.isNeedToBeRemoved()) {
                        i2 = deleteGeofence(geofence) + i2;
                    } else {
                        i = updateGeofence(geofence) + i3;
                    }
                    i3 = i;
                }
                i = arrayList.size() > 0 ? insertGeofences(arrayList) : 0;
                this.a.b.setTransactionSuccessful();
                Log.debug("A4SProvider|updateGeofences inserted: " + i + ", updated: " + i3 + ", deleted: " + i2);
            } finally {
                this.a.b.endTransaction();
            }
        }
    }

    private void a(Context context) {
        UriMatcher uriMatcher = new UriMatcher(-1);
        a = uriMatcher;
        uriMatcher.addURI(A4SContract.getAuthority(context), "beacons", 100);
        a.addURI(A4SContract.getAuthority(context), "beacons/#", 101);
        a.addURI(A4SContract.getAuthority(context), "beacons/beacon_groups/filter", 102);
        a.addURI(A4SContract.getAuthority(context), "beacon_params", 200);
        a.addURI(A4SContract.getAuthority(context), "beacon_params/#", 201);
        a.addURI(A4SContract.getAuthority(context), "geofences", 300);
        a.addURI(A4SContract.getAuthority(context), "geofences/#", 301);
        a.addURI(A4SContract.getAuthority(context), "geofences/geofence_groups/filter", 302);
        a.addURI(A4SContract.getAuthority(context), "geofence_params", 400);
        a.addURI(A4SContract.getAuthority(context), "geofence_params/#", 401);
        a.addURI(A4SContract.getAuthority(context), "beacon_geofence_groups", 500);
        a.addURI(A4SContract.getAuthority(context), "beacon_geofence_groups/#", 501);
        a.addURI(A4SContract.getAuthority(context), "beacon_geofence_groups/beacon_groups/filter", 502);
        a.addURI(A4SContract.getAuthority(context), "beacon_geofence_groups/geofence_groups/filter", 503);
        a.addURI(A4SContract.getAuthority(context), "beacon_groups", 600);
        a.addURI(A4SContract.getAuthority(context), "beacon_groups/#", 601);
        a.addURI(A4SContract.getAuthority(context), "custom_beacon_groups", 602);
        a.addURI(A4SContract.getAuthority(context), "geofence_groups", 700);
        a.addURI(A4SContract.getAuthority(context), "geofence_groups/#", 701);
        a.addURI(A4SContract.getAuthority(context), "custom_geofence_groups", 702);
    }

    private boolean a() {
        Context context = getContext();
        a(context);
        this.b = a.a(context).getWritableDatabase();
        this.c = new b();
        this.d = new a(this, this.c);
        this.e = new c(this, this.c);
        return this.b != null;
    }

    private String[] a(String[] strArr, String str) {
        if (strArr == null) {
            return new String[]{str};
        }
        Object obj = new String[(strArr.length + 1)];
        obj[0] = str;
        System.arraycopy(strArr, 0, obj, 1, strArr.length);
        return obj;
    }

    public int bulkInsert(Uri uri, ContentValues[] contentValuesArr) {
        String str;
        ContentValues[] contentValuesArr2;
        int length;
        int i;
        int i2;
        ContentValues contentValues;
        String asString;
        Cursor query;
        long j;
        ContentValues contentValues2;
        switch (a.match(uri)) {
            case 100:
                str = "beacons";
                break;
            case 200:
                str = "beacon_params";
                break;
            case 300:
                str = "geofences";
                break;
            case 400:
                str = "geofence_params";
                break;
            case 500:
                str = "beacon_geofence_groups";
                break;
            case 600:
                str = "beacon_groups";
                break;
            case 602:
                contentValuesArr2 = new ContentValues[contentValuesArr.length];
                length = contentValuesArr.length;
                i = 0;
                i2 = 0;
                while (i < length) {
                    contentValues = contentValuesArr[i];
                    asString = contentValues.getAsString("beacon_id");
                    query = this.b.query("beacon_geofence_groups", new String[]{"_id"}, "server_id=?", new String[]{contentValues.getAsString("server_id")}, null, null, null);
                    if (query != null) {
                        if (query.getCount() > 0) {
                            query.moveToFirst();
                            j = (long) query.getInt(0);
                        } else {
                            j = -1;
                        }
                        query.close();
                    } else {
                        j = -1;
                    }
                    if (j == -1) {
                        contentValues = new ContentValues();
                        contentValues.put("server_id", r15);
                        j = this.b.insert("beacon_geofence_groups", null, contentValues);
                    }
                    contentValues2 = new ContentValues();
                    contentValues2.put("beacon_id", asString);
                    contentValues2.put("group_id", Long.valueOf(j));
                    contentValuesArr2[i2] = contentValues2;
                    i++;
                    i2++;
                }
                str = "beacon_groups";
                contentValuesArr = contentValuesArr2;
                break;
            case 700:
                str = "geofence_groups";
                break;
            case 702:
                contentValuesArr2 = new ContentValues[contentValuesArr.length];
                length = contentValuesArr.length;
                i = 0;
                i2 = 0;
                while (i < length) {
                    contentValues = contentValuesArr[i];
                    asString = contentValues.getAsString("geofence_id");
                    query = this.b.query("beacon_geofence_groups", new String[]{"_id"}, "server_id=?", new String[]{contentValues.getAsString("server_id")}, null, null, null);
                    if (query != null) {
                        if (query.getCount() > 0) {
                            query.moveToFirst();
                            j = (long) query.getInt(0);
                        } else {
                            j = -1;
                        }
                        query.close();
                    } else {
                        j = -1;
                    }
                    if (j == -1) {
                        contentValues = new ContentValues();
                        contentValues.put("server_id", r15);
                        j = this.b.insert("beacon_geofence_groups", null, contentValues);
                    }
                    contentValues2 = new ContentValues();
                    contentValues2.put("geofence_id", asString);
                    contentValues2.put("group_id", Long.valueOf(j));
                    contentValuesArr2[i2] = contentValues2;
                    i++;
                    i2++;
                }
                str = "geofence_groups";
                contentValuesArr = contentValuesArr2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        this.b.beginTransaction();
        int i3 = 0;
        for (ContentValues insert : r18) {
            if (this.b.insert(str, null, insert) >= 0) {
                i3++;
            }
        }
        this.b.setTransactionSuccessful();
        this.b.endTransaction();
        if (i3 > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return i3;
    }

    public Bundle call(String str, String str2, Bundle bundle) {
        Object obj = -1;
        switch (str.hashCode()) {
            case -1897996758:
                if (str.equals("updateGeofences")) {
                    obj = null;
                    break;
                }
                break;
            case -1730717786:
                if (str.equals("updateBeacons")) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case null:
                bundle.setClassLoader(Geofence.class.getClassLoader());
                this.e.updateGeofences(bundle.getParcelableArrayList("geofences"));
                break;
            case 1:
                bundle.setClassLoader(Beacon.class.getClassLoader());
                this.d.updateBeacons(bundle.getParcelableArrayList("beacons"));
                break;
            default:
                Log.warn("A4SProvider|call method is not found");
                break;
        }
        return null;
    }

    public int delete(Uri uri, String str, String[] strArr) {
        String str2;
        Uri uri2;
        switch (a.match(uri)) {
            case 100:
                str2 = "beacons";
                uri2 = null;
                break;
            case 101:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = Beacons.getContentUri(getContext());
                str2 = "beacons";
                break;
            case 200:
                str2 = "beacon_params";
                uri2 = null;
                break;
            case 201:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = BeaconParams.getContentUri(getContext());
                str2 = "beacon_params";
                break;
            case 300:
                str2 = "geofences";
                uri2 = null;
                break;
            case 301:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = Geofences.getContentUri(getContext());
                str2 = "geofences";
                break;
            case 400:
                str2 = "geofence_params";
                uri2 = null;
                break;
            case 401:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = GeofenceParams.getContentUri(getContext());
                str2 = "geofence_params";
                break;
            case 500:
                str2 = "beacon_geofence_groups";
                uri2 = null;
                break;
            case 501:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = BeaconGeofenceGroups.getContentUri(getContext());
                str2 = "beacon_geofence_groups";
                break;
            case 600:
                str2 = "beacon_groups";
                uri2 = null;
                break;
            case 601:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = BeaconGroups.getContentUri(getContext());
                str2 = "beacon_groups";
                break;
            case 700:
                str2 = "geofence_groups";
                uri2 = null;
                break;
            case 701:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                uri2 = GeofenceGroups.getContentUri(getContext());
                str2 = "geofence_groups";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int delete = this.b.delete(str2, str, strArr);
        if (delete > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
            if (uri2 != null) {
                getContext().getContentResolver().notifyChange(uri2, null);
            }
        }
        return delete;
    }

    public String getType(Uri uri) {
        switch (a.match(uri)) {
            case 100:
                return Beacons.CONTENT_TYPE;
            case 101:
                return Beacons.CONTENT_ITEM_TYPE;
            case 200:
                return BeaconParams.CONTENT_TYPE;
            case 201:
                return BeaconParams.CONTENT_ITEM_TYPE;
            case 300:
                return Geofences.CONTENT_TYPE;
            case 301:
                return Geofences.CONTENT_ITEM_TYPE;
            case 400:
                return GeofenceParams.CONTENT_TYPE;
            case 401:
                return GeofenceParams.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        String str;
        switch (a.match(uri)) {
            case 100:
                str = "beacons";
                break;
            case 200:
                str = "beacon_params";
                break;
            case 300:
                str = "geofences";
                break;
            case 400:
                str = "geofence_params";
                break;
            case 500:
                str = "beacon_geofence_groups";
                break;
            case 600:
                str = "beacon_groups";
                break;
            case 700:
                str = "geofence_groups";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long insert = this.b.insert(str, null, contentValues);
        if (insert > 0) {
            Uri withAppendedId = ContentUris.withAppendedId(uri, insert);
            getContext().getContentResolver().notifyChange(uri, null);
            return withAppendedId;
        }
        throw new SQLException("Failed to insert row into " + uri);
    }

    public boolean onCreate() {
        try {
            return a();
        } catch (Throwable e) {
            Log.error("A4SProvider|Cannot start A4SProvider", e);
            return false;
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        String[] strArr3;
        int i = 0;
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        long parseId;
        StringBuffer stringBuffer;
        int length;
        switch (a.match(uri)) {
            case 100:
                sQLiteQueryBuilder.setTables("beacons");
                strArr3 = strArr2;
                break;
            case 101:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("beacons");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 102:
                stringBuffer = new StringBuffer("SELECT ");
                if (strArr == null) {
                    stringBuffer.append("*");
                } else {
                    length = strArr.length;
                    while (i < length) {
                        stringBuffer.append(strArr[i]);
                        stringBuffer.append(",");
                        i++;
                    }
                    if (stringBuffer.length() > 0) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    }
                }
                stringBuffer.append(" FROM beacons");
                stringBuffer.append(" INNER JOIN beacon_groups");
                stringBuffer.append(" ON beacons._id=beacon_groups.beacon_id");
                stringBuffer.append(" INNER JOIN beacon_geofence_groups");
                stringBuffer.append(" ON beacon_groups.group_id=beacon_geofence_groups._id");
                if (str != null) {
                    stringBuffer.append(" WHERE " + str);
                }
                return this.b.rawQuery(stringBuffer.toString(), strArr2);
            case 200:
                sQLiteQueryBuilder.setTables("beacon_params");
                strArr3 = strArr2;
                break;
            case 201:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("beacon_params");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 300:
                sQLiteQueryBuilder.setTables("geofences");
                strArr3 = strArr2;
                break;
            case 301:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("geofences");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 302:
                stringBuffer = new StringBuffer("SELECT ");
                if (strArr == null) {
                    stringBuffer.append("*");
                } else {
                    length = strArr.length;
                    while (i < length) {
                        stringBuffer.append(strArr[i]);
                        stringBuffer.append(",");
                        i++;
                    }
                    if (stringBuffer.length() > 0) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    }
                }
                stringBuffer.append(" FROM geofences");
                stringBuffer.append(" INNER JOIN geofence_groups");
                stringBuffer.append(" ON geofences._id=geofence_groups.geofence_id");
                stringBuffer.append(" INNER JOIN beacon_geofence_groups");
                stringBuffer.append(" ON geofence_groups.group_id=beacon_geofence_groups._id");
                if (str != null) {
                    stringBuffer.append(" WHERE " + str);
                }
                return this.b.rawQuery(stringBuffer.toString(), strArr2);
            case 400:
                sQLiteQueryBuilder.setTables("geofence_params");
                strArr3 = strArr2;
                break;
            case 401:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("geofence_params");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 500:
                sQLiteQueryBuilder.setTables("beacon_geofence_groups");
                strArr3 = strArr2;
                break;
            case 501:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("beacon_geofence_groups");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 502:
                stringBuffer = new StringBuffer("SELECT ");
                if (strArr == null) {
                    stringBuffer.append("*");
                } else {
                    length = strArr.length;
                    while (i < length) {
                        stringBuffer.append(strArr[i]);
                        stringBuffer.append(",");
                        i++;
                    }
                    if (stringBuffer.length() > 0) {
                        stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                    }
                }
                stringBuffer.append(" FROM beacon_geofence_groups");
                stringBuffer.append(" INNER JOIN beacon_groups");
                stringBuffer.append(" ON beacon_geofence_groups._id=beacon_groups.group_id");
                if (str != null) {
                    stringBuffer.append(" WHERE " + str);
                }
                return this.b.rawQuery(stringBuffer.toString(), strArr2);
            case 503:
                StringBuffer stringBuffer2 = new StringBuffer("SELECT ");
                if (strArr == null) {
                    stringBuffer2.append("*");
                } else {
                    for (String append : strArr) {
                        stringBuffer2.append(append);
                        stringBuffer2.append(",");
                    }
                    if (stringBuffer2.length() > 0) {
                        stringBuffer2.deleteCharAt(stringBuffer2.length() - 1);
                    }
                }
                stringBuffer2.append(" FROM beacon_geofence_groups");
                stringBuffer2.append(" INNER JOIN geofence_groups");
                stringBuffer2.append(" ON beacon_geofence_groups._id=geofence_groups.group_id");
                if (str != null) {
                    stringBuffer2.append(" WHERE " + str);
                }
                return this.b.rawQuery(stringBuffer2.toString(), strArr2);
            case 600:
                sQLiteQueryBuilder.setTables("beacon_groups");
                strArr3 = strArr2;
                break;
            case 601:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("beacon_groups");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            case 700:
                sQLiteQueryBuilder.setTables("geofence_groups");
                strArr3 = strArr2;
                break;
            case 701:
                parseId = ContentUris.parseId(uri);
                sQLiteQueryBuilder.setTables("geofence_groups");
                strArr2 = a(strArr2, String.valueOf(parseId));
                sQLiteQueryBuilder.appendWhere("_id=?");
                strArr3 = strArr2;
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        Cursor query = sQLiteQueryBuilder.query(this.b, strArr, str, strArr3, null, null, str2);
        if (query == null) {
            return query;
        }
        query.setNotificationUri(getContext().getContentResolver(), uri);
        return query;
    }

    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        String str2;
        switch (a.match(uri)) {
            case 100:
                str2 = "beacons";
                break;
            case 101:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "beacons";
                break;
            case 200:
                str2 = "beacon_params";
                break;
            case 201:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "beacon_params";
                break;
            case 300:
                str2 = "geofences";
                break;
            case 301:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "geofences";
                break;
            case 400:
                str2 = "geofence_params";
                break;
            case 401:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "geofence_params";
                break;
            case 500:
                str2 = "beacon_geofence_groups";
                break;
            case 501:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "beacon_geofence_groups";
                break;
            case 600:
                str2 = "beacon_groups";
                break;
            case 601:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "beacon_groups";
                break;
            case 700:
                str2 = "geofence_groups";
                break;
            case 701:
                strArr = a(strArr, String.valueOf(ContentUris.parseId(uri)));
                str = "_id=?" + (!TextUtils.isEmpty(str) ? " AND (" + str + ")" : "");
                str2 = "geofence_groups";
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int update = this.b.update(str2, contentValues, str, strArr);
        if (update > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return update;
    }
}
