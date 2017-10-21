package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrackingCoordinates {
    @JsonIgnore
    public Long _id;
    @JsonProperty("idfa")
    public String advertisingIdentifier;
    @JsonProperty("application_uid")
    public String applicationId;
    @JsonProperty("client_uid")
    public String clientId;
    @JsonProperty("device_uid")
    public String deviceId;
    @JsonProperty("locations")
    public List<TrackingLocation> locations;

    public TrackingCoordinates(AppInfo appInfo) {
        this.applicationId = appInfo.appId;
        this.clientId = appInfo.clientId;
    }
}
