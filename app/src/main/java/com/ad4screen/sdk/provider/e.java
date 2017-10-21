package com.ad4screen.sdk.provider;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract.BeaconGeofenceGroups;
import com.ad4screen.sdk.contract.A4SContract.Beacons;
import com.ad4screen.sdk.contract.A4SContract.GeofenceGroups;
import com.ad4screen.sdk.contract.A4SContract.GeofenceParams;
import com.ad4screen.sdk.contract.A4SContract.Geofences;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;
import com.ad4screen.sdk.plugins.model.Geofence;
import com.ad4screen.sdk.provider.c.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class e extends c {
    public e(a aVar, Context context) {
        super(aVar, context);
    }

    private long getGeofenceId(Geofence geofence) {
        String id = geofence.getId();
        Cursor a = this.mAcc.a(Geofences.getContentUri(this.mContext), new String[]{"_id"}, "server_id=?", new String[]{id}, null);
        if (a == null) {
            return -1;
        }
        long j;
        int count = a.getCount();
        if (count > 0) {
            a.moveToFirst();
            j = a.getLong(0);
            if (count > 1) {
                Log.warn("A4SGeofenceResolver|getGeofenceId|there are " + count + " instances of " + geofence);
            }
        } else {
            j = -1;
        }
        a.close();
        return j;
    }

    public int deleteAllGeofences() {
        int a = this.mAcc.a(Geofences.getContentUri(this.mContext), null, null);
        this.mAcc.a(GeofenceParams.getContentUri(this.mContext), null, null);
        this.mAcc.a(GeofenceGroups.getContentUri(this.mContext), null, null);
        return a;
    }

    public void deleteBeacons(List<Geofence> list) {
        ArrayList arrayList = new ArrayList(list.size());
        if (VERSION.SDK_INT >= 5) {
            for (Geofence id : list) {
                arrayList.add(ContentProviderOperation.newDelete(Beacons.getContentUri(this.mContext)).withSelection("server_id=" + id.getId(), null).build());
            }
            try {
                this.mAcc.a(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public int deleteGeofence(Geofence geofence) {
        long rowId = geofence.getRowId() > 0 ? geofence.getRowId() : getGeofenceId(geofence);
        int a = this.mAcc.a(Uri.withAppendedPath(Geofences.getContentUri(this.mContext), String.valueOf(rowId)), null, null);
        Log.internal("BeaconHelper|DELETE geofence id=" + geofence.getId() + ", name: " + geofence.getName() + " at " + rowId);
        this.mAcc.a(GeofenceParams.getContentUri(this.mContext), "geofence_id=" + rowId, null);
        return a;
    }

    public void fillGeofenceGroups(Geofence geofence) {
        String l = Long.toString(geofence.getRowId());
        Cursor a = this.mAcc.a(BeaconGeofenceGroups.getGeofencesContentFilterUri(this.mContext), new String[]{"server_id"}, "geofence_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                Set hashSet = new HashSet(a.getCount());
                do {
                    hashSet.add(a.getString(0));
                } while (a.moveToNext());
                geofence.setGroups(hashSet);
            }
            a.close();
        }
    }

    public void fillGeofenceParams(Geofence geofence) {
        String l = Long.toString(geofence.getRowId());
        Cursor a = this.mAcc.a(GeofenceParams.getContentUri(this.mContext), new String[]{"param_key", "param_value"}, "geofence_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                Map hashMap = new HashMap(a.getCount());
                do {
                    hashMap.put(a.getString(0), a.getString(1));
                } while (a.moveToNext());
                geofence.setCustomParams(hashMap);
            }
            a.close();
        }
    }

    public List<Geofence> getAllGeofences() {
        return getAllGeofences(null, null);
    }

    public List<Geofence> getAllGeofences(String str, String[] strArr) {
        List<Geofence> list = null;
        Cursor a = this.mAcc.a(Geofences.getContentUri(this.mContext), new String[]{"_id", "server_id", "external_id", "name", GeofencesColumns.LATITUDE, GeofencesColumns.LONGITUDE, GeofencesColumns.RADIUS, "detected_time", "notified_time"}, str, strArr, null);
        if (a != null) {
            if (a.moveToFirst()) {
                list = new ArrayList(a.getCount());
                do {
                    long j = a.getLong(0);
                    Geofence geofence = new Geofence(a.getString(1));
                    geofence.setRowId(j);
                    geofence.setExternalId(a.getString(2));
                    geofence.setName(a.getString(3));
                    geofence.setLatitude(a.getDouble(4));
                    geofence.setLongitude(a.getDouble(5));
                    geofence.setRadius((float) a.getInt(6));
                    geofence.setDetectedTime(c.convertStringToDate(a.getString(7)));
                    geofence.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                    fillGeofenceParams(geofence);
                    fillGeofenceGroups(geofence);
                    list.add(geofence);
                } while (a.moveToNext());
            }
            a.close();
        }
        return list;
    }

    public Geofence getGeofenceByClientId(String str) {
        Geofence geofence = null;
        Cursor a = this.mAcc.a(Geofences.getContentUri(this.mContext), new String[]{"_id", "server_id", "external_id", "name", GeofencesColumns.LATITUDE, GeofencesColumns.LONGITUDE, GeofencesColumns.RADIUS, "detected_time", "notified_time"}, "server_id=?", new String[]{str}, null);
        if (a != null) {
            if (a.getCount() > 0) {
                a.moveToFirst();
                long j = a.getLong(0);
                geofence = new Geofence(Integer.toString(a.getInt(1)));
                geofence.setRowId(j);
                geofence.setExternalId(a.getString(2));
                geofence.setName(a.getString(3));
                geofence.setLatitude(a.getDouble(4));
                geofence.setLongitude(a.getDouble(5));
                geofence.setRadius((float) a.getInt(6));
                geofence.setDetectedTime(c.convertStringToDate(a.getString(7)));
                geofence.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                fillGeofenceParams(geofence);
                fillGeofenceGroups(geofence);
            }
            a.close();
        }
        return geofence;
    }

    public int getGeofencesCount() {
        Cursor a = this.mAcc.a(Geofences.getContentUri(this.mContext), new String[]{"_id"}, null, null, null);
        if (a == null) {
            return -1;
        }
        int count = a.getCount();
        a.close();
        return count;
    }

    public List<Geofence> getGeofencesFilteredByGroups(String str, String[] strArr) {
        Map hashMap = new HashMap();
        Cursor a = this.mAcc.a(Geofences.getGroupsContentFilterUri(this.mContext), new String[]{"geofences._id", "geofences.server_id", "geofences.external_id", "geofences.name", "geofences.latitude", "geofences.longitude", "geofences.radius", "geofences.detected_time", "geofences.notified_time"}, str, strArr, null);
        if (a != null) {
            if (a.moveToFirst()) {
                do {
                    long j = a.getLong(0);
                    String string = a.getString(1);
                    if (!hashMap.containsKey(string)) {
                        Geofence geofence = new Geofence(string);
                        geofence.setRowId(j);
                        geofence.setExternalId(a.getString(2));
                        geofence.setName(a.getString(3));
                        geofence.setLatitude(a.getDouble(4));
                        geofence.setLongitude(a.getDouble(5));
                        geofence.setRadius((float) a.getInt(6));
                        geofence.setDetectedTime(c.convertStringToDate(a.getString(7)));
                        geofence.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                        fillGeofenceParams(geofence);
                        fillGeofenceGroups(geofence);
                        hashMap.put(string, geofence);
                    }
                } while (a.moveToNext());
            }
            a.close();
        }
        return hashMap.size() == 0 ? null : new ArrayList(hashMap.values());
    }

    public long hasGeofence(String str) {
        Cursor a = this.mAcc.a(Geofences.getContentUri(this.mContext), new String[]{"_id"}, "server_id=?", new String[]{str}, null);
        if (a == null) {
            return -1;
        }
        long j;
        if (a.getCount() > 0) {
            a.moveToFirst();
            j = a.getLong(0);
        } else {
            j = -1;
        }
        a.close();
        return j;
    }

    public long hasGeofenceGroup(String str) {
        Cursor a = this.mAcc.a(BeaconGeofenceGroups.getContentUri(this.mContext), new String[]{"_id"}, "server_id=?", new String[]{str}, null);
        if (a == null) {
            return -1;
        }
        long j;
        if (a.getCount() > 0) {
            a.moveToFirst();
            j = a.getLong(0);
        } else {
            j = -1;
        }
        a.close();
        return j;
    }

    public long insertGeofence(Geofence geofence) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("server_id", geofence.getId());
        contentValues.put("external_id", geofence.getExternalId());
        contentValues.put("name", geofence.getName());
        contentValues.put(GeofencesColumns.LATITUDE, Double.valueOf(geofence.getLatitude()));
        contentValues.put(GeofencesColumns.LONGITUDE, Double.valueOf(geofence.getLongitude()));
        contentValues.put(GeofencesColumns.RADIUS, Float.valueOf(geofence.getRadius()));
        contentValues.put("detected_time", c.convertDateToString(geofence.getDetectedTime()));
        contentValues.put("notified_time", c.convertDateToString(geofence.getNotifiedTime()));
        Uri a = this.mAcc.a(Geofences.getContentUri(this.mContext), contentValues);
        if (a == null) {
            return -1;
        }
        long longValue = Long.valueOf((String) a.getPathSegments().get(1)).longValue();
        Log.internal("A4SGeofenceResolver|INSERT geofence id=" + geofence.getId() + ", name: " + geofence.getName() + " at " + longValue);
        if (geofence.getCustomParams().size() > 0) {
            insertGeofenceParams(longValue, geofence.getCustomParams());
        }
        if (geofence.getGroups().size() <= 0) {
            return longValue;
        }
        insertGeofenceGroups(longValue, geofence.getGroups());
        return longValue;
    }

    public void insertGeofenceGroups(long j, Set<String> set) {
        for (String str : set) {
            long longValue;
            long hasGeofenceGroup = hasGeofenceGroup(str);
            if (hasGeofenceGroup < 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("server_id", str);
                longValue = Long.valueOf((String) this.mAcc.a(BeaconGeofenceGroups.getContentUri(this.mContext), contentValues).getPathSegments().get(1)).longValue();
            } else {
                longValue = hasGeofenceGroup;
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("geofence_id", Long.valueOf(j));
            contentValues2.put("group_id", Long.valueOf(longValue));
            this.mAcc.a(GeofenceGroups.getContentUri(this.mContext), contentValues2);
        }
    }

    public void insertGeofenceParams(long j, Map<String, String> map) {
        for (Entry entry : map.entrySet()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("geofence_id", Long.valueOf(j));
            contentValues.put("param_key", (String) entry.getKey());
            contentValues.put("param_value", (String) entry.getValue());
            this.mAcc.a(GeofenceParams.getContentUri(this.mContext), contentValues);
        }
    }

    public int insertGeofences(List<Geofence> list) {
        if (list == null || list.size() == 0) {
            Log.warn("A4SGeofenceResolver|bulkInsert no geofences");
            return 0;
        }
        ContentValues[] contentValuesArr = new ContentValues[list.size()];
        int i = 0;
        for (Geofence geofence : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("server_id", geofence.getId());
            contentValues.put("external_id", geofence.getExternalId());
            contentValues.put("name", geofence.getName());
            contentValues.put(GeofencesColumns.LATITUDE, Double.valueOf(geofence.getLatitude()));
            contentValues.put(GeofencesColumns.LONGITUDE, Double.valueOf(geofence.getLongitude()));
            contentValues.put(GeofencesColumns.RADIUS, Float.valueOf(geofence.getRadius()));
            contentValues.put("detected_time", c.convertDateToString(geofence.getDetectedTime()));
            contentValues.put("notified_time", c.convertDateToString(geofence.getNotifiedTime()));
            contentValuesArr[i] = contentValues;
            i++;
        }
        int a = this.mAcc.a(Geofences.getContentUri(this.mContext), contentValuesArr);
        if (a != i) {
            Log.error("A4SGeofenceResolver|bulkInsert hasn't inserted all geofences, needed=" + i + ", inserted=" + a);
            return a;
        }
        long hasGeofence = hasGeofence(((Geofence) list.get(0)).getId());
        if (hasGeofence < 0) {
            Log.error("A4SGeofenceResolver|bulkInsert an ID of the first inserted geofence is not found");
            return a;
        }
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        long j = hasGeofence;
        for (Geofence geofence2 : list) {
            if (geofence2.getCustomParams().size() > 0) {
                for (Entry entry : geofence2.getCustomParams().entrySet()) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("geofence_id", Long.valueOf(j));
                    contentValues2.put("param_key", (String) entry.getKey());
                    contentValues2.put("param_value", (String) entry.getValue());
                    arrayList.add(contentValues2);
                }
            }
            if (geofence2.getGroups().size() > 0) {
                for (String str : geofence2.getGroups()) {
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("geofence_id", Long.valueOf(j));
                    contentValues3.put("server_id", str);
                    arrayList2.add(contentValues3);
                }
            }
            j = 1 + j;
        }
        if (arrayList.size() > 0) {
            this.mAcc.a(GeofenceParams.getContentUri(this.mContext), (ContentValues[]) arrayList.toArray(new ContentValues[arrayList.size()]));
        }
        if (arrayList2.size() > 0) {
            this.mAcc.a(GeofenceGroups.getCustomContentUri(this.mContext), (ContentValues[]) arrayList2.toArray(new ContentValues[arrayList2.size()]));
        }
        return a;
    }

    public int updateGeofence(Geofence geofence) {
        String str;
        String[] strArr;
        if (geofence.getRowId() > 0) {
            str = "_id=?";
            strArr = new String[]{String.valueOf(geofence.getRowId())};
        } else {
            str = "server_id=?";
            strArr = new String[]{geofence.getId()};
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("external_id", geofence.getExternalId());
        contentValues.put("name", geofence.getName());
        contentValues.put(GeofencesColumns.LATITUDE, Double.valueOf(geofence.getLatitude()));
        contentValues.put(GeofencesColumns.LONGITUDE, Double.valueOf(geofence.getLongitude()));
        contentValues.put(GeofencesColumns.RADIUS, Float.valueOf(geofence.getRadius()));
        contentValues.put("detected_time", c.convertDateToString(geofence.getDetectedTime()));
        contentValues.put("notified_time", c.convertDateToString(geofence.getNotifiedTime()));
        int a = this.mAcc.a(Geofences.getContentUri(this.mContext), contentValues, str, strArr);
        Log.internal("A4SGeofenceResolver|UPDATE geofence id=" + geofence.getId() + ", name: " + geofence.getName());
        long geofenceId = getGeofenceId(geofence);
        updateGeofenceParams(geofenceId, geofence.getCustomParams());
        updateGeofenceGroups(geofenceId, geofence.getGroups());
        return a;
    }

    public void updateGeofenceGroups(long j, Set<String> set) {
        Set hashSet = new HashSet(set);
        String l = Long.toString(j);
        Cursor a = this.mAcc.a(BeaconGeofenceGroups.getGeofencesContentFilterUri(this.mContext), new String[]{"beacon_geofence_groups._id", "server_id", "geofence_groups._id"}, "geofence_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                do {
                    int i = a.getInt(0);
                    String string = a.getString(1);
                    int i2 = a.getInt(2);
                    if (set.contains(string)) {
                        hashSet.remove(string);
                    } else {
                        this.mAcc.a(Uri.withAppendedPath(BeaconGeofenceGroups.getContentUri(this.mContext), "/" + i), null, null);
                        this.mAcc.a(Uri.withAppendedPath(GeofenceGroups.getContentUri(this.mContext), "/" + i2), null, null);
                    }
                } while (a.moveToNext());
            }
            a.close();
        }
        if (hashSet.size() > 0) {
            insertGeofenceGroups(j, hashSet);
        }
    }

    public void updateGeofenceParams(long j, Map<String, String> map) {
        Map hashMap = new HashMap(map);
        String l = Long.toString(j);
        Cursor a = this.mAcc.a(GeofenceParams.getContentUri(this.mContext), new String[]{"_id", "param_key"}, "geofence_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                do {
                    Uri withAppendedPath = Uri.withAppendedPath(GeofenceParams.getContentUri(this.mContext), "/" + a.getInt(0));
                    String string = a.getString(1);
                    if (map.containsKey(string)) {
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("param_value", (String) map.get(string));
                        this.mAcc.a(withAppendedPath, contentValues, null, null);
                        hashMap.remove(string);
                    } else {
                        this.mAcc.a(withAppendedPath, null, null);
                    }
                } while (a.moveToNext());
            }
            a.close();
        }
        if (hashMap.size() > 0) {
            insertGeofenceParams(j, hashMap);
        }
    }

    public void updateGeofences(List<Geofence> list) {
        for (Geofence geofence : list) {
            if (hasGeofence(geofence.getId()) < 0) {
                if (!geofence.isNeedToBeRemoved()) {
                    insertGeofence(geofence);
                }
            } else if (geofence.isNeedToBeRemoved()) {
                deleteGeofence(geofence);
            } else {
                updateGeofence(geofence);
            }
        }
    }
}
