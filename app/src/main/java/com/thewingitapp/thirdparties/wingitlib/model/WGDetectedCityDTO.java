package com.thewingitapp.thirdparties.wingitlib.model;

import com.google.gson.annotations.SerializedName;

class WGDetectedCityDTO extends WGCity {
    @SerializedName("distance")
    Double distance;
    @SerializedName("insideCity")
    Boolean insideCity;
}
