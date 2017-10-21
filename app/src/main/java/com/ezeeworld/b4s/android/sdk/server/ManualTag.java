package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Hashtable;

public class ManualTag {
    @JsonProperty("A")
    public String advertisingIdentifier;
    @JsonProperty("T")
    public int advertisingTrackingEnabled;
    @JsonProperty("a")
    public String applicationId;
    @JsonProperty("c")
    public String clientId;
    @JsonProperty("u")
    public String deviceId;
    @JsonProperty("e")
    public String event;
    @JsonProperty("L")
    public Float latitude;
    @JsonProperty("l")
    public Float longitude;
    @JsonProperty("s")
    public String sessionId;
    @JsonProperty("h")
    public String shopId;
    @JsonProperty("t")
    public Long timestamp;
    @JsonProperty("d")
    public String userData;
    @JsonProperty("D")
    public Hashtable userDataDictionnary;
}
