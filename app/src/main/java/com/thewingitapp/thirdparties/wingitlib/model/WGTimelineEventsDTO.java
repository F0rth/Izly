package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.List;

class WGTimelineEventsDTO implements Serializable {
    @SerializedName("detectedCity")
    WGDetectedCity detectedCity;
    @SerializedName("events")
    List<WGEvent> events;
    @SerializedName("limit")
    Integer limitValue;
    @SerializedName("offset")
    Integer offset;
    @SerializedName("offsetDay")
    Integer offsetDay;
    @SerializedName("totalNumber")
    Integer totalNumber;
}
