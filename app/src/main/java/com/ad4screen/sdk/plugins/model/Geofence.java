package com.ad4screen.sdk.plugins.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ad4screen.sdk.c.a.d.a;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.c.c;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.contract.A4SContract.GeofencesColumns;
import com.ad4screen.sdk.provider.A4SGeofenceResolver;

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

@API
public class Geofence implements Parcelable, c<Geofence>, d, Comparable<Geofence> {
    private static final String CLASS_KEY = "com.ad4screen.sdk.plugins.model.Geofence";
    public static final Creator<Geofence> CREATOR = new Creator<Geofence>() {
        public final Geofence createFromParcel(Parcel parcel) {
            return new Geofence(parcel);
        }

        public final Geofence[] newArray(int i) {
            return new Geofence[i];
        }
    };
    public static final String KEY_CUSTOM_PARAMS = "data";
    public static final String KEY_EXTERNAL_ID = "externalId";
    public static final String KEY_GROUPS = "groups";
    public static final String KEY_NAME = "name";
    public static final String KEY_TARGET_LATITUDE = "targetLat";
    public static final String KEY_TARGET_LONGITUDE = "targetLong";
    private Map<String, String> mCustomParams = new HashMap();
    private Date mDetectedTime = new Date(0);
    private int mDistance;
    private String mExternalId;
    private Set<String> mGroups = new HashSet();
    private String mId;
    private boolean mIsNeedToBeRemoved = false;
    private double mLatitude;
    private double mLongitude;
    private String mName;
    private Date mNotifiedTime = new Date(0);
    private float mRadius;
    public long mRowId = -1;

    public static class PersonalParamsReplacer implements a {
        private Geofence mGeofence;

        public PersonalParamsReplacer(Context context, String str) {
            this.mGeofence = new A4SGeofenceResolver(context).getGeofenceByClientId(str);
        }

        public void onPersonalParamFound(String str, StringBuffer stringBuffer) {
            if ("name".equals(str)) {
                stringBuffer.append(this.mGeofence.getName());
            } else if (Geofence.KEY_TARGET_LATITUDE.equals(str)) {
                stringBuffer.append(String.valueOf(this.mGeofence.getLatitude()));
            } else if (Geofence.KEY_TARGET_LONGITUDE.equals(str)) {
                stringBuffer.append(String.valueOf(this.mGeofence.getLongitude()));
            } else if (this.mGeofence.getCustomParams().containsKey(str)) {
                stringBuffer.append((String) this.mGeofence.getCustomParams().get(str));
            }
        }
    }

    protected Geofence(Parcel parcel) {
        int i;
        int i2 = 0;
        this.mId = parcel.readString();
        this.mRowId = parcel.readLong();
        this.mExternalId = parcel.readString();
        this.mLatitude = parcel.readDouble();
        this.mLongitude = parcel.readDouble();
        this.mRadius = parcel.readFloat();
        this.mIsNeedToBeRemoved = parcel.readInt() != 0;
        this.mDistance = parcel.readInt();
        this.mName = parcel.readString();
        int readInt = parcel.readInt();
        this.mCustomParams = new HashMap(readInt);
        for (i = 0; i < readInt; i++) {
            this.mCustomParams.put(parcel.readString(), parcel.readString());
        }
        i = parcel.readInt();
        this.mGroups = new HashSet(i);
        while (i2 < i) {
            this.mGroups.add(parcel.readString());
            i2++;
        }
    }

    public Geofence(String str) {
        this.mId = str;
        this.mExternalId = "";
    }

    public int compareTo(Geofence geofence) {
        return this.mDistance - geofence.getDistance();
    }

    public int describeContents() {
        return 0;
    }

    public Geofence fromJSON(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        Geofence geofence = new Geofence(jSONObject.getString("id"));
        geofence.setRowId(jSONObject.getLong("rowId"));
        geofence.setExternalId(jSONObject.getString("externalId"));
        geofence.setLatitude(jSONObject.getDouble(GeofencesColumns.LATITUDE));
        geofence.setLongitude(jSONObject.getDouble(GeofencesColumns.LONGITUDE));
        geofence.setRadius((float) jSONObject.getDouble(GeofencesColumns.RADIUS));
        geofence.setNeedToBeRemoved(jSONObject.getBoolean("toRemove"));
        geofence.setDistance(jSONObject.getInt("distance"));
        geofence.setName(jSONObject.getString("name"));
        if (!jSONObject.isNull("data")) {
            geofence.parseCustomParams(jSONObject);
        }
        if (!jSONObject.isNull("groups")) {
            geofence.parseGroups(jSONObject);
        }
        return geofence;
    }

    public String getClassKey() {
        return CLASS_KEY;
    }

    public Map<String, String> getCustomParams() {
        return this.mCustomParams;
    }

    public Date getDetectedTime() {
        return new Date(this.mDetectedTime.getTime());
    }

    public int getDistance() {
        return this.mDistance;
    }

    public String getExternalId() {
        return this.mExternalId;
    }

    public Set<String> getGroups() {
        return this.mGroups;
    }

    public String getId() {
        return this.mId;
    }

    public double getLatitude() {
        return this.mLatitude;
    }

    public double getLongitude() {
        return this.mLongitude;
    }

    public String getName() {
        return this.mName;
    }

    public Date getNotifiedTime() {
        return new Date(this.mNotifiedTime.getTime());
    }

    public float getRadius() {
        return this.mRadius;
    }

    public long getRowId() {
        return this.mRowId;
    }

    public boolean isNeedToBeRemoved() {
        return this.mIsNeedToBeRemoved;
    }

    public void parseCustomParams(JSONObject jSONObject) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject("data");
        Iterator keys = jSONObject2.keys();
        while (keys.hasNext()) {
            String str = (String) keys.next();
            String string = jSONObject2.getString(str);
            if (this.mCustomParams.containsKey(str)) {
                throw new JSONException(str + " already exists beacon: " + this.mId);
            }
            this.mCustomParams.put(str, string);
        }
    }

    public void parseGroups(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = jSONObject.getJSONArray("groups");
        for (int i = 0; i < jSONArray.length(); i++) {
            this.mGroups.add(jSONArray.getString(i));
        }
    }

    public void setCustomParams(Map<String, String> map) {
        this.mCustomParams = map;
    }

    public void setDetectedTime(Date date) {
        this.mDetectedTime = new Date(date.getTime());
    }

    public void setDistance(int i) {
        this.mDistance = i;
    }

    public void setExternalId(String str) {
        this.mExternalId = str;
    }

    public void setGroups(Set<String> set) {
        this.mGroups = set;
    }

    public void setLatitude(double d) {
        this.mLatitude = d;
    }

    public void setLongitude(double d) {
        this.mLongitude = d;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public void setNeedToBeRemoved(boolean z) {
        this.mIsNeedToBeRemoved = z;
    }

    public void setNotifiedTime(Date date) {
        this.mNotifiedTime = new Date(date.getTime());
    }

    public void setRadius(float f) {
        this.mRadius = f;
    }

    public void setRowId(long j) {
        this.mRowId = j;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("id", this.mId);
        jSONObject.put("rowId", this.mRowId);
        jSONObject.put("externalId", this.mExternalId);
        jSONObject.put(GeofencesColumns.LATITUDE, this.mLatitude);
        jSONObject.put(GeofencesColumns.LONGITUDE, this.mLongitude);
        jSONObject.put(GeofencesColumns.RADIUS, (double) this.mRadius);
        jSONObject.put("toRemove", this.mIsNeedToBeRemoved);
        jSONObject.put("distance", this.mDistance);
        jSONObject.put("name", this.mName);
        JSONObject jSONObject2 = new JSONObject();
        for (Entry entry : this.mCustomParams.entrySet()) {
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
        return "Geofence: " + this.mId + ", " + this.mName + ", distance: " + this.mDistance + ", groups: " + this.mGroups.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mId);
        parcel.writeLong(this.mRowId);
        parcel.writeString(this.mExternalId);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitude);
        parcel.writeFloat(this.mRadius);
        parcel.writeInt(this.mIsNeedToBeRemoved ? 1 : 0);
        parcel.writeInt(this.mDistance);
        parcel.writeString(this.mName);
        parcel.writeInt(this.mCustomParams.size());
        for (Entry entry : this.mCustomParams.entrySet()) {
            parcel.writeString((String) entry.getKey());
            parcel.writeString((String) entry.getValue());
        }
        parcel.writeInt(this.mGroups.size());
        for (String writeString : this.mGroups) {
            parcel.writeString(writeString);
        }
    }
}
