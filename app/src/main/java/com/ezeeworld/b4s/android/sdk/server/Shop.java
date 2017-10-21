package com.ezeeworld.b4s.android.sdk.server;

import android.location.Location;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.ezeeworld.b4s.android.sdk.B4SLog;

import java.util.ArrayList;
import java.util.List;

public class Shop implements Parcelable {
    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        public final Shop a(Parcel parcel) {
            return new Shop(parcel);
        }

        public final Shop[] a(int i) {
            return new Shop[i];
        }

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            return a(parcel);
        }

        public final /* synthetic */ Object[] newArray(int i) {
            return a(i);
        }
    };
    public static final double GEOHASH_LAT_RATIO = 0.0010000000474974513d;
    public static final double GEOHASH_LON_RATIO = 0.0015200000489130616d;
    public static final double LATITUDE_TO_METERS_RATIO = 8.99499991646735E-6d;
    public static final double LONGITUDE_TO_METERS_RATIO = 1.3677000424650032E-5d;
    public static final int MAX_VIRTUAL_BEACONS = 70;
    private Location a = null;
    public List<String> aMACList;
    public String aMACs;
    public String compositeCoordinateIndex = "";
    public List<Interaction> geofenceInteractionsList;
    public float interactionRadius;
    public Double lastComputedNotificationDistance;
    public double latGeoIndex;
    public double lonGeoIndex;
    public double nLatitude;
    public double nLongitude;
    public double roundedLat;
    public double roundedLon;
    public String sCity;
    public String sClientRef;
    public String sCountry;
    public String sGroupId;
    public String sKey = "";
    public String sName;
    public String sPushText;
    public String sSSID = "";
    public String sShopId;
    public String sZipCode;

    protected Shop(Parcel parcel) {
        B4SLog.i((Object) this, "Shop instanciate:" + Shop.class);
        B4SLog.i((Object) this, "Shop instanciate:" + Shop.class + "<" + parcel.toString() + ">");
        B4SLog.i((Object) this, "Shop instanciate:" + Shop.class + "<" + parcel.readByte() + ">");
        this.sShopId = parcel.readString();
        this.sName = parcel.readString();
        this.sGroupId = parcel.readString();
        this.sPushText = parcel.readString();
        this.sCity = parcel.readString();
        this.sZipCode = parcel.readString();
        this.sCountry = parcel.readString();
        this.sClientRef = parcel.readString();
        this.nLongitude = parcel.readDouble();
        this.nLatitude = parcel.readDouble();
        this.sSSID = parcel.readString();
        this.sKey = parcel.readString();
    }

    private String a() {
        this.roundedLat = (double) ((((float) ((int) ((this.nLatitude * 10000.0d) / 2.0d))) * 2.0f) / 10000.0f);
        this.roundedLon = (double) ((((float) ((int) ((this.nLongitude * 10000.0d) / 3.0d))) * 3.0f) / 10000.0f);
        return "VB4S-" + String.format("%1.4f", new Object[]{Double.valueOf(this.roundedLat)}) + "," + String.format("%1.4f", new Object[]{Double.valueOf(this.roundedLon)});
    }

    public void addInteraction(Interaction interaction) {
        this.geofenceInteractionsList.add(interaction);
    }

    public int describeContents() {
        return 0;
    }

    public double fastComputeNotificationDistanceFrom(Location location) {
        double longitude = (this.nLongitude - location.getLongitude()) / LONGITUDE_TO_METERS_RATIO;
        longitude = Math.sqrt(Math.pow(longitude, 2.0d) + Math.pow((this.nLatitude - location.getLatitude()) / LATITUDE_TO_METERS_RATIO, 2.0d));
        if (longitude < ((double) (this.interactionRadius + 20.0f))) {
            this.lastComputedNotificationDistance = Double.valueOf(-longitude);
        } else {
            this.lastComputedNotificationDistance = Double.valueOf(longitude - ((double) this.interactionRadius));
        }
        return this.lastComputedNotificationDistance.doubleValue();
    }

    protected void finalize() throws Throwable {
        if (this.geofenceInteractionsList != null) {
            this.geofenceInteractionsList.clear();
        }
    }

    public Location location() {
        if (this.a == null) {
            Location location = new Location("Shop");
            location.setLatitude(this.nLatitude);
            location.setLongitude(this.nLongitude);
            this.a = location;
        }
        return this.a;
    }

    public void setupAsVirtualBeacon() {
        this.latGeoIndex = this.nLatitude / GEOHASH_LAT_RATIO;
        this.lonGeoIndex = this.nLongitude / GEOHASH_LON_RATIO;
        this.compositeCoordinateIndex = a();
        this.geofenceInteractionsList = new ArrayList();
    }

    public String toString() {
        return this.sName;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.sShopId);
        parcel.writeString(this.sName);
        parcel.writeString(this.sGroupId);
        parcel.writeString(this.sPushText);
        parcel.writeString(this.sCity);
        parcel.writeString(this.sZipCode);
        parcel.writeString(this.sCountry);
        parcel.writeString(this.sClientRef);
        parcel.writeDouble(this.nLongitude);
        parcel.writeDouble(this.nLatitude);
        parcel.writeString(this.sSSID);
        parcel.writeString(this.sKey);
    }
}
