package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import nl.qbusict.cupboard.annotation.Ignore;

public class TrackingBeacons {
    @JsonIgnore
    public Long _id;
    @JsonProperty("A")
    public String advertisingIdentifier;
    @JsonProperty("T")
    public int advertisingTrackingEnabled;
    @JsonProperty("a")
    public String applicationId;
    @JsonProperty("n")
    public String beaconSessionId;
    @JsonProperty("B")
    @Ignore
    public List<BeaconRssi> beaconsAround;
    @JsonProperty("y")
    public String categoryId;
    @JsonProperty("c")
    public String clientId;
    @JsonProperty("N")
    public String connectionType;
    @JsonProperty("u")
    public String deviceId;
    @JsonProperty("d")
    public Double distance;
    @JsonProperty("i")
    public String interactionId;
    @JsonProperty("L")
    public double latitude;
    @JsonProperty("l")
    public double longitude;
    @JsonProperty("m")
    public String shopId;
    @JsonProperty("s")
    public String shopSessionId;
    @JsonProperty("t")
    public long timestamp;
    @JsonProperty("V")
    public int version;

    public static class BeaconRssi {
        @JsonIgnore
        public Long _id;
        @JsonProperty("b")
        public String beaconInnerName;
        @JsonProperty("r")
        public int rssi;
        @JsonIgnore
        public Long trackingId;
    }
}
