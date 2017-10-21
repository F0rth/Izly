package com.ad4screen.sdk.provider;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.contract.A4SContract.BeaconGeofenceGroups;
import com.ad4screen.sdk.contract.A4SContract.BeaconGroups;
import com.ad4screen.sdk.contract.A4SContract.BeaconParams;
import com.ad4screen.sdk.contract.A4SContract.Beacons;
import com.ad4screen.sdk.contract.A4SContract.BeaconsColumns;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.provider.c.a;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public abstract class b extends c {
    public b(a aVar, Context context) {
        super(aVar, context);
    }

    private long getBeaconId(Beacon beacon) {
        String str = beacon.id;
        String str2 = beacon.uuid;
        int i = beacon.major;
        int i2 = beacon.minor;
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id"}, "server_id=? AND uuid=? AND major=? AND minor=?", new String[]{str, str2, String.valueOf(i), String.valueOf(i2)}, null);
        if (a == null) {
            return -1;
        }
        long j;
        int count = a.getCount();
        if (count > 0) {
            a.moveToFirst();
            j = a.getLong(0);
            if (count > 1) {
                Log.warn("BeaconHelper|getBeaconId|there are " + count + " instances of " + beacon);
            }
        } else {
            j = -1;
        }
        a.close();
        return j;
    }

    public int deleteAllBeacons() {
        int a = this.mAcc.a(Beacons.getContentUri(this.mContext), null, null);
        this.mAcc.a(BeaconParams.getContentUri(this.mContext), null, null);
        this.mAcc.a(BeaconGroups.getContentUri(this.mContext), null, null);
        return a;
    }

    public int deleteBeacon(Beacon beacon) {
        long beaconId = beacon.rowId > 0 ? beacon.rowId : getBeaconId(beacon);
        int a = this.mAcc.a(Uri.withAppendedPath(Beacons.getContentUri(this.mContext), String.valueOf(beaconId)), null, null);
        this.mAcc.a(BeaconParams.getContentUri(this.mContext), "beacon_id=" + beaconId, null);
        return a;
    }

    public void deleteBeacons(List<Beacon> list) {
        ArrayList arrayList = new ArrayList(list.size());
        if (VERSION.SDK_INT >= 5) {
            for (Beacon beacon : list) {
                arrayList.add(ContentProviderOperation.newDelete(Beacons.getContentUri(this.mContext)).withSelection("server_id=" + beacon.id, null).build());
            }
            try {
                this.mAcc.a(arrayList);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void fillBeaconGroups(Beacon beacon) {
        String l = Long.toString(beacon.rowId);
        Cursor a = this.mAcc.a(BeaconGeofenceGroups.getBeaconsContentFilterUri(this.mContext), new String[]{"server_id"}, "beacon_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                Set hashSet = new HashSet(a.getCount());
                do {
                    hashSet.add(a.getString(0));
                } while (a.moveToNext());
                beacon.setGroups(hashSet);
            }
            a.close();
        }
    }

    public void fillBeaconParams(Beacon beacon) {
        String l = Long.toString(beacon.rowId);
        Cursor a = this.mAcc.a(BeaconParams.getContentUri(this.mContext), new String[]{"param_key", "param_value"}, "beacon_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                Map hashMap = new HashMap(a.getCount());
                do {
                    hashMap.put(a.getString(0), a.getString(1));
                } while (a.moveToNext());
                beacon.customs = hashMap;
            }
            a.close();
        }
    }

    public List<Beacon> getAllBeacons() {
        return getAllBeacons(null, null);
    }

    public List<Beacon> getAllBeacons(String str, String[] strArr) {
        List<Beacon> list = null;
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id", "server_id", "external_id", "name", BeaconsColumns.UUID, BeaconsColumns.MAJOR, BeaconsColumns.MINOR, "detected_time", "notified_time"}, str, strArr, null);
        if (a != null) {
            if (a.moveToFirst()) {
                list = new ArrayList(a.getCount());
                do {
                    Beacon beacon = new Beacon();
                    beacon.rowId = a.getLong(0);
                    beacon.id = a.getString(1);
                    beacon.externalId = a.getString(2);
                    beacon.name = a.getString(3);
                    beacon.uuid = a.getString(4);
                    beacon.major = a.getInt(5);
                    beacon.minor = a.getInt(6);
                    beacon.setDetectedTime(c.convertStringToDate(a.getString(7)));
                    beacon.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                    fillBeaconParams(beacon);
                    fillBeaconGroups(beacon);
                    list.add(beacon);
                } while (a.moveToNext());
            }
            a.close();
        }
        return list;
    }

    public Beacon getBeacon(String str, String[] strArr) {
        Beacon beacon = null;
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id", "server_id", "external_id", "name", BeaconsColumns.UUID, BeaconsColumns.MAJOR, BeaconsColumns.MINOR, "detected_time", "notified_time"}, str, strArr, null);
        if (a != null) {
            if (a.getCount() > 0) {
                a.moveToFirst();
                beacon = new Beacon();
                beacon.rowId = a.getLong(0);
                beacon.id = a.getString(1);
                beacon.externalId = a.getString(2);
                beacon.name = a.getString(3);
                beacon.uuid = a.getString(4);
                beacon.major = a.getInt(5);
                beacon.minor = a.getInt(6);
                beacon.setDetectedTime(c.convertStringToDate(a.getString(7)));
                beacon.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                fillBeaconParams(beacon);
            }
            a.close();
        }
        return beacon;
    }

    public Beacon getBeaconByClientId(String str) {
        Beacon beacon = null;
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id", "server_id", "external_id", "name", BeaconsColumns.UUID, BeaconsColumns.MAJOR, BeaconsColumns.MINOR, "detected_time", "notified_time"}, "server_id=?", new String[]{str}, null);
        if (a != null) {
            if (a.getCount() > 0) {
                a.moveToFirst();
                beacon = new Beacon();
                beacon.rowId = a.getLong(0);
                beacon.id = a.getString(1);
                beacon.externalId = a.getString(2);
                beacon.name = a.getString(3);
                beacon.uuid = a.getString(4);
                beacon.major = a.getInt(5);
                beacon.minor = a.getInt(6);
                beacon.setDetectedTime(c.convertStringToDate(a.getString(7)));
                beacon.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                fillBeaconParams(beacon);
                fillBeaconGroups(beacon);
            }
            a.close();
        }
        return beacon;
    }

    public int getBeaconsCount() {
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id"}, null, null, null);
        if (a == null) {
            return -1;
        }
        int count = a.getCount();
        a.close();
        return count;
    }

    public List<Beacon> getBeaconsFilteredByGroups(String str, String[] strArr) {
        Map hashMap = new HashMap();
        Cursor a = this.mAcc.a(Beacons.getGroupsContentFilterUri(this.mContext), new String[]{"beacons._id", "beacons.server_id", "beacons.external_id", "beacons.name", "beacons.uuid", "beacons.major", "beacons.minor", "beacons.detected_time", "beacons.notified_time"}, str, strArr, null);
        if (a != null && a.moveToFirst()) {
            do {
                long j = a.getLong(0);
                String string = a.getString(1);
                if (!hashMap.containsKey(string)) {
                    Beacon beacon = new Beacon();
                    beacon.id = string;
                    beacon.rowId = j;
                    beacon.externalId = a.getString(2);
                    beacon.name = a.getString(3);
                    beacon.uuid = a.getString(4);
                    beacon.major = a.getInt(5);
                    beacon.minor = a.getInt(6);
                    beacon.setDetectedTime(c.convertStringToDate(a.getString(7)));
                    beacon.setNotifiedTime(c.convertStringToDate(a.getString(8)));
                    fillBeaconParams(beacon);
                    fillBeaconGroups(beacon);
                    hashMap.put(string, beacon);
                }
            } while (a.moveToNext());
            a.close();
        }
        return hashMap.size() == 0 ? null : new ArrayList(hashMap.values());
    }

    public long hasBeacon(String str) {
        Cursor a = this.mAcc.a(Beacons.getContentUri(this.mContext), new String[]{"_id"}, "server_id=?", new String[]{str}, null);
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

    public long hasBeaconGroup(String str) {
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

    public long insertBeacon(Beacon beacon) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("server_id", beacon.id);
        contentValues.put("external_id", beacon.externalId);
        contentValues.put("name", beacon.name);
        contentValues.put(BeaconsColumns.UUID, beacon.uuid);
        contentValues.put(BeaconsColumns.MAJOR, Integer.valueOf(beacon.major));
        contentValues.put(BeaconsColumns.MINOR, Integer.valueOf(beacon.minor));
        contentValues.put("detected_time", c.convertDateToString(beacon.getDetectedTime()));
        contentValues.put("notified_time", c.convertDateToString(beacon.getNotifiedTime()));
        Uri a = this.mAcc.a(Beacons.getContentUri(this.mContext), contentValues);
        if (a == null) {
            return -1;
        }
        long longValue = Long.valueOf((String) a.getPathSegments().get(1)).longValue();
        beacon.rowId = longValue;
        if (beacon.customs.size() > 0) {
            insertBeaconParams(longValue, beacon.customs);
        }
        if (beacon.getGroups().size() <= 0) {
            return longValue;
        }
        insertBeaconGroups(longValue, beacon.getGroups());
        return longValue;
    }

    public void insertBeaconGroups(long j, Set<String> set) {
        for (String str : set) {
            long longValue;
            long hasBeaconGroup = hasBeaconGroup(str);
            if (hasBeaconGroup < 0) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("server_id", str);
                longValue = Long.valueOf((String) this.mAcc.a(BeaconGeofenceGroups.getContentUri(this.mContext), contentValues).getPathSegments().get(1)).longValue();
            } else {
                longValue = hasBeaconGroup;
            }
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("beacon_id", Long.valueOf(j));
            contentValues2.put("group_id", Long.valueOf(longValue));
            this.mAcc.a(BeaconGroups.getContentUri(this.mContext), contentValues2);
        }
    }

    public void insertBeaconParams(long j, Map<String, String> map) {
        for (Entry entry : map.entrySet()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("beacon_id", Long.valueOf(j));
            contentValues.put("param_key", (String) entry.getKey());
            contentValues.put("param_value", (String) entry.getValue());
            this.mAcc.a(BeaconParams.getContentUri(this.mContext), contentValues);
        }
    }

    public int insertBeacons(List<Beacon> list) {
        if (list == null || list.size() == 0) {
            Log.warn("A4SBeaconsResolver|bulkInsert no beacons");
            return 0;
        }
        ContentValues[] contentValuesArr = new ContentValues[list.size()];
        int i = 0;
        for (Beacon beacon : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("server_id", beacon.id);
            contentValues.put("external_id", beacon.externalId);
            contentValues.put("name", beacon.name);
            contentValues.put(BeaconsColumns.UUID, beacon.uuid);
            contentValues.put(BeaconsColumns.MAJOR, Integer.valueOf(beacon.major));
            contentValues.put(BeaconsColumns.MINOR, Integer.valueOf(beacon.minor));
            contentValues.put("detected_time", c.convertDateToString(beacon.getDetectedTime()));
            contentValues.put("notified_time", c.convertDateToString(beacon.getNotifiedTime()));
            contentValuesArr[i] = contentValues;
            i++;
        }
        int a = this.mAcc.a(Beacons.getContentUri(this.mContext), contentValuesArr);
        if (a != i) {
            Log.error("A4SBeaconResolver|bulkInsert hasn't inserted all beacons, needed=" + i + ", inserted=" + a);
            return a;
        }
        long hasBeacon = hasBeacon(((Beacon) list.get(0)).id);
        if (hasBeacon < 0) {
            Log.error("A4SBeaconResolver|bulkInsert an ID of the first inserted beacon is not found");
            return a;
        }
        List arrayList = new ArrayList();
        List arrayList2 = new ArrayList();
        long j = hasBeacon;
        for (Beacon beacon2 : list) {
            if (beacon2.customs.size() > 0) {
                for (Entry entry : beacon2.customs.entrySet()) {
                    ContentValues contentValues2 = new ContentValues();
                    contentValues2.put("beacon_id", Long.valueOf(j));
                    contentValues2.put("param_key", (String) entry.getKey());
                    contentValues2.put("param_value", (String) entry.getValue());
                    arrayList.add(contentValues2);
                }
            }
            if (beacon2.getGroups().size() > 0) {
                for (String str : beacon2.getGroups()) {
                    ContentValues contentValues3 = new ContentValues();
                    contentValues3.put("beacon_id", Long.valueOf(j));
                    contentValues3.put("server_id", str);
                    arrayList2.add(contentValues3);
                }
            }
            j = 1 + j;
        }
        if (arrayList.size() > 0) {
            this.mAcc.a(BeaconParams.getContentUri(this.mContext), (ContentValues[]) arrayList.toArray(new ContentValues[arrayList.size()]));
        }
        if (arrayList2.size() > 0) {
            this.mAcc.a(BeaconGroups.getCustomContentUri(this.mContext), (ContentValues[]) arrayList2.toArray(new ContentValues[arrayList2.size()]));
        }
        return a;
    }

    public int updateBeacon(Beacon beacon) {
        String str;
        String[] strArr;
        if (beacon.rowId > 0) {
            str = "_id=?";
            strArr = new String[]{String.valueOf(beacon.rowId)};
        } else {
            str = "server_id=?";
            strArr = new String[]{beacon.id};
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put("external_id", beacon.externalId);
        contentValues.put("name", beacon.name);
        contentValues.put(BeaconsColumns.UUID, beacon.uuid);
        contentValues.put(BeaconsColumns.MAJOR, Integer.valueOf(beacon.major));
        contentValues.put(BeaconsColumns.MINOR, Integer.valueOf(beacon.minor));
        contentValues.put("detected_time", c.convertDateToString(beacon.getDetectedTime()));
        contentValues.put("notified_time", c.convertDateToString(beacon.getNotifiedTime()));
        int a = this.mAcc.a(Beacons.getContentUri(this.mContext), contentValues, str, strArr);
        long beaconId = getBeaconId(beacon);
        beacon.rowId = beaconId;
        updateBeaconParams(beaconId, beacon.customs);
        updateBeaconGroups(beaconId, beacon.getGroups());
        return a;
    }

    public void updateBeaconGroups(long j, Set<String> set) {
        Set hashSet = new HashSet(set);
        String l = Long.toString(j);
        Cursor a = this.mAcc.a(BeaconGeofenceGroups.getBeaconsContentFilterUri(this.mContext), new String[]{"beacon_geofence_groups._id", "server_id", "beacon_groups._id"}, "beacon_id=?", new String[]{l}, null);
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
                        this.mAcc.a(Uri.withAppendedPath(BeaconGroups.getContentUri(this.mContext), "/" + i2), null, null);
                    }
                } while (a.moveToNext());
            }
            a.close();
        }
        if (hashSet.size() > 0) {
            insertBeaconGroups(j, hashSet);
        }
    }

    public void updateBeaconParams(long j, Map<String, String> map) {
        Map hashMap = new HashMap(map);
        String l = Long.toString(j);
        Cursor a = this.mAcc.a(BeaconParams.getContentUri(this.mContext), new String[]{"_id", "param_key"}, "beacon_id=?", new String[]{l}, null);
        if (a != null) {
            if (a.moveToFirst()) {
                do {
                    Uri withAppendedPath = Uri.withAppendedPath(BeaconParams.getContentUri(this.mContext), "/" + a.getInt(0));
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
            insertBeaconParams(j, hashMap);
        }
    }

    public void updateBeacons(List<Beacon> list) {
        for (Beacon beacon : list) {
            if (hasBeacon(beacon.id) < 0) {
                if (!beacon.isNeedToBeRemoved) {
                    insertBeacon(beacon);
                }
            } else if (beacon.isNeedToBeRemoved) {
                deleteBeacon(beacon);
            } else {
                updateBeacon(beacon);
            }
        }
    }
}
