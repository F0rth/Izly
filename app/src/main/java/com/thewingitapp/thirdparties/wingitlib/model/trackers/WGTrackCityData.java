package com.thewingitapp.thirdparties.wingitlib.model.trackers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class WGTrackCityData implements IWGTrackData {
    @SerializedName("cityname")
    String cityname;

    public String getCityName() {
        return this.cityname;
    }

    public String getJsonString() {
        return new Gson().toJson((Object) this);
    }

    public void setCityname(String str) {
        this.cityname = str;
    }
}
