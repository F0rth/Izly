package com.ezeeworld.b4s.android.sdk.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class TrackingLocation {
    @JsonIgnore
    public Long _id;
    @JsonProperty("speed")
    public double averageSpeed;
    @JsonProperty("date")
    public Date date;
    @JsonProperty("lat")
    public double latitude;
    @JsonProperty("lon")
    public double longitude;
    @JsonProperty("precision")
    public int precision;
    @JsonProperty("sIdx")
    public int speedIndex;
    @JsonIgnore
    public Boolean toBesend;
    @JsonProperty("wifi")
    public int wifiStatus;
}
