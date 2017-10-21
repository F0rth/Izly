package com.ezeeworld.b4s.android.sdk.server;

public class NearbyShop {
    public double nDistance;
    public double nLatitude;
    public double nLongitude;
    public String sName;
    public String sShopId;

    static class Request {
        public Double nLatitude;
        public Double nLongitude;
        public int nRadius;
        public String sClientId;

        Request() {
        }
    }

    public String toString() {
        return this.sName;
    }
}
