package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class WGPlaceDTO implements Serializable {
    @SerializedName("address")
    String address;
    @SerializedName("cityId")
    Integer cityId;
    @SerializedName("lat")
    Double lat;
    @SerializedName("lon")
    Double lon;
    @SerializedName("name")
    String name;
    @SerializedName("placeId")
    String placeId;
}
