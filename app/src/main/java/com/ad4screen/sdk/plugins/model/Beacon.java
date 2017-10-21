package com.ad4screen.sdk.plugins.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.c.a.d.a;
import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.contract.A4SContract.BeaconsColumns;
import com.ad4screen.sdk.provider.A4SBeaconResolver;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Beacon implements Parcelable, c<Beacon>, d {
    private static final String CLASS_KEY = "com.ad4screen.sdk.plugins.model.Beacon";
    public static final Creator<Beacon> CREATOR = new Creator<Beacon>() {
        public final Beacon createFromParcel(Parcel parcel) {
            return new Beacon(parcel);
        }

        public final Beacon[] newArray(int i) {
            return new Beacon[i];
        }
    };
    public static final String KEY_CUSTOM_PARAMS = "data";
    public static final String KEY_EXTERNAL_ID = "externalId";
    public static final String KEY_GROUPS = "groups";
    public static final String KEY_NAME = "name";
    public static final int UNKNOWN_BEACON = 0;
    public Map<String, String> customs = new HashMap();
    public String externalId = "";
    public String id;
    public boolean isNeedToBeRemoved = false;
    private Date mDetectedTime = new Date(0);
    private Set<String> mGroups = new HashSet();
    private Date mNotifiedTime = new Date(0);
    public int major = -1;
    public int minor = -1;
    public String name = "";
    public long rowId = -1;
    public String uuid = "";

    public static class PersonalParamsReplacer implements a {
        private Beacon mBeacon;
        private A4SBeaconResolver mResolver;

        public PersonalParamsReplacer(Context context, String str) {
            this.mResolver = new A4SBeaconResolver(context);
            this.mBeacon = this.mResolver.getBeaconByClientId(str);
        }

        public void onPersonalParamFound(String str, StringBuffer stringBuffer) {
            if ("name".equals(str)) {
                stringBuffer.append(this.mBeacon.name);
            } else if (this.mBeacon.customs.containsKey(str)) {
                stringBuffer.append((String) this.mBeacon.customs.get(str));
            }
        }
    }

    public Beacon(int i, String str, int i2, int i3) {
        this.id = Integer.toString(i);
        this.uuid = str;
        this.major = i2;
        this.minor = i3;
    }

    public Beacon(Parcel parcel) {
        int i;
        boolean z = true;
        int i2 = 0;
        this.id = parcel.readString();
        this.rowId = parcel.readLong();
        this.externalId = parcel.readString();
        this.uuid = parcel.readString();
        this.major = parcel.readInt();
        this.minor = parcel.readInt();
        if (parcel.readInt() != 1) {
            z = false;
        }
        this.isNeedToBeRemoved = z;
        int readInt = parcel.readInt();
        this.customs = new HashMap(readInt);
        for (i = 0; i < readInt; i++) {
            this.customs.put(parcel.readString(), parcel.readString());
        }
        i = parcel.readInt();
        this.mGroups = new HashSet(i);
        while (i2 < i) {
            this.mGroups.add(parcel.readString());
            i2++;
        }
    }

    public Beacon(String str) {
        this.id = str;
    }

    public Beacon(String str, String str2, int i, int i2) {
        this.id = str;
        this.uuid = str2;
        this.major = i;
        this.minor = i2;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Beacon beacon = (Beacon) obj;
            if (this.id == null) {
                if (beacon.id != null) {
                    return false;
                }
            } else if (!this.id.equals(beacon.id)) {
                return false;
            }
        }
        return true;
    }

    public Beacon fromJSON(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        Beacon beacon = new Beacon(jSONObject.getString("id"));
        beacon.rowId = jSONObject.getLong("rowId");
        beacon.externalId = jSONObject.getString("externalId");
        beacon.uuid = jSONObject.getString(BeaconsColumns.UUID);
        beacon.isNeedToBeRemoved = jSONObject.getBoolean("enabled");
        beacon.major = jSONObject.getInt(BeaconsColumns.MAJOR);
        beacon.minor = jSONObject.getInt(BeaconsColumns.MINOR);
        if (!jSONObject.isNull("data")) {
            beacon.parseCustomParams(jSONObject);
        }
        if (!jSONObject.isNull("groups")) {
            beacon.parseGroups(jSONObject);
        }
        return beacon;
    }

    public String getClassKey() {
        return CLASS_KEY;
    }

    public Date getDetectedTime() {
        return new Date(this.mDetectedTime.getTime());
    }

    public Set<String> getGroups() {
        return this.mGroups;
    }

    public Date getNotifiedTime() {
        return new Date(this.mNotifiedTime.getTime());
    }

    public int hashCode() {
        return (this.id == null ? 0 : this.id.hashCode()) + 31;
    }

    public void parseCustomParams(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        Iterator keys = jSONObject2.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            String string = jSONObject2.getString(str);
            if (this.customs.containsKey(str)) {
                throw new JSONException(str + " already exists beacon: " + this.id);
            }
            this.customs.put(str, string);
        }
    }

    public void parseGroups(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("groups");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.mGroups.add(jSONArray.getString(i));
        }
    }

    public void setDetectedTime(Date date) {
        this.mDetectedTime = new Date(date.getTime());
    }

    public void setGroups(Set<String> set) {
        this.mGroups = set;
    }

    public void setNotifiedTime(Date date) {
        this.mNotifiedTime = new Date(date.getTime());
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.id);
        jSONObject.put("rowId", this.rowId);
        jSONObject.put("externalId", this.externalId);
        jSONObject.put(BeaconsColumns.UUID, this.uuid);
        jSONObject.put("enabled", this.isNeedToBeRemoved);
        jSONObject.put(BeaconsColumns.MAJOR, this.major);
        jSONObject.put(BeaconsColumns.MINOR, this.minor);
        JSONObject jSONObject2 = new JSONObject();
        for (Entry entry : this.customs.entrySet()) {
            jSONObject2.put((String) entry.getKey(), (String) entry.getValue());
        }
        jSONObject.put("data", jSONObject2);
        JSONArray jSONArray = new JSONArray();
        for (String put : this.mGroups) {
            jSONArray.put(put);
        }
        jSONObject.put("groups", jSONArray);
        return jSONObject;
    }

    public String toString() {
        return "Beacon: " + this.id + ", name: " + this.name + ", externalId: " + this.externalId + ", uuid: " + this.uuid + ", major: " + String.valueOf(this.major) + ", minor: " + String.valueOf(this.minor);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeLong(this.rowId);
        parcel.writeString(this.externalId);
        parcel.writeString(this.uuid);
        parcel.writeInt(this.major);
        parcel.writeInt(this.minor);
        parcel.writeInt(this.isNeedToBeRemoved ? 1 : 0);
        parcel.writeInt(this.customs.size());
        for (Entry entry : this.customs.entrySet()) {
            parcel.writeString((String) entry.getKey());
            parcel.writeString((String) entry.getValue());
        }
        parcel.writeInt(this.mGroups.size());
        for (String writeString : this.mGroups) {
            parcel.writeString(writeString);
        }
    }
}
