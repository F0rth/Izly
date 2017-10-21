package com.thewingitapp.thirdparties.wingitlib.model.trackers;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

class WGTrackDTO implements Serializable {
    @SerializedName("data")
    String data;
    @SerializedName("uri")
    String uri;
    @SerializedName("userId")
    String userId;

    WGTrackDTO() {
    }
}
