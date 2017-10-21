package com.ezeeworld.b4s.android.sdk.server;

import android.location.Location;

import com.ezeeworld.b4s.android.sdk.B4SLog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Spot {
    public List<Beacon> beacons;
    public HashMap<String, Shop> geoHashedShops;
    public List<Interaction> interactions;
    public String sDataMD5;
    public List<Shop> shops;
    public HashMap<String, Shop> shopsTableByShopId;
    public boolean wasRefreshed;

    static class Request {
        public SupportedFeature[] aSupported;
        public boolean bBluetooth;
        public int nAdvertisingTrackingEnabled;
        public int nCause;
        public Double nDistance;
        public Double nLatitude;
        public Double nLongitude;
        public int nRadius;
        public String sAdvertisingIdentifier;
        public String sAppId;
        public String sClientId;
        public String sDataMD5;
        public boolean sDemoMode;
        public String sDeviceUdid;
        public String sInnerName;

        Request() {
        }
    }

    static class SpotWrapper {
        public Spot res;

        SpotWrapper() {
        }
    }

    static class SupportedFeature {
        public String type;

        SupportedFeature() {
        }
    }

    protected void finalize() throws Throwable {
        if (this.geoHashedShops != null) {
            this.geoHashedShops.clear();
        }
        if (this.shopsTableByShopId != null) {
            this.shopsTableByShopId.clear();
        }
    }

    public Shop findShop(String str) {
        init();
        Shop shop = (Shop) this.shopsTableByShopId.get(str);
        if (shop != null) {
            return shop;
        }
        for (Shop shop2 : this.shops) {
            if (shop2.sShopId.equals(str)) {
                shop2.setupAsVirtualBeacon();
                this.shopsTableByShopId.put(shop2.sShopId, shop2);
                this.geoHashedShops.put(shop2.compositeCoordinateIndex, shop2);
                return shop2;
            }
        }
        return shop;
    }

    public Shop getOneBeaconAtCompositeIndex(String str) {
        for (Shop shop : this.geoHashedShops.values()) {
            if (shop.compositeCoordinateIndex.equals(str)) {
                return shop;
            }
        }
        return null;
    }

    public void init() {
        if (this.shopsTableByShopId == null) {
            this.shopsTableByShopId = new HashMap();
            this.geoHashedShops = new HashMap();
        }
    }

    public List<Shop> virtualBeaconsAtCompositeIndex(String str) {
        List<Shop> arrayList = new ArrayList();
        for (Shop shop : this.shops) {
            if (shop.compositeCoordinateIndex.equals(str)) {
                arrayList.add(shop);
            }
        }
        return arrayList;
    }

    public List<Shop> virtualBeaconsNear(Location location) {
        List<Shop> arrayList = new ArrayList();
        if (this.geoHashedShops == null) {
            return null;
        }
        Collection<Shop> values = this.geoHashedShops.values();
        double latitude = location.getLatitude() / Shop.GEOHASH_LAT_RATIO;
        double longitude = location.getLongitude() / Shop.GEOHASH_LON_RATIO;
        B4SLog.d("Spot", "[virtualBeaconsNear] localIndex=" + latitude + "/" + longitude + " virtualBeacons=" + values.size());
        int i = 6;
        while (i <= 12) {
            arrayList.clear();
            for (Shop shop : values) {
                if (Math.abs(shop.latGeoIndex - latitude) <= ((double) i) && Math.abs(shop.lonGeoIndex - longitude) <= ((double) i)) {
                    shop.fastComputeNotificationDistanceFrom(location);
                    arrayList.add(shop);
                }
            }
            if (arrayList.size() >= 69) {
                break;
            }
            i += 2;
        }
        return arrayList;
    }

    public List<Shop> virtualBeaconsNearestFromLocation(Location location) {
        List<Shop> arrayList = new ArrayList();
        if (this.geoHashedShops == null) {
            return null;
        }
        Collection<Shop> values = this.geoHashedShops.values();
        double latitude = location.getLatitude() / Shop.GEOHASH_LAT_RATIO;
        double longitude = location.getLongitude() / Shop.GEOHASH_LON_RATIO;
        for (Shop shop : values) {
            if (Math.abs(shop.latGeoIndex - latitude) <= 1.0d && Math.abs(shop.lonGeoIndex - longitude) <= 2.0d) {
                shop.fastComputeNotificationDistanceFrom(location);
                arrayList.add(shop);
            }
        }
        B4SLog.d("Spot", "[virtualBeaconsNearestFromLocation] localIndex=" + latitude + "/" + longitude + " virtualBeacons=" + arrayList.size() + "/" + values.size());
        return arrayList;
    }
}
