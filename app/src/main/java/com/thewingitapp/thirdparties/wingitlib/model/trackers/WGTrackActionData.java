package com.thewingitapp.thirdparties.wingitlib.model.trackers;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class WGTrackActionData implements IWGTrackData {
    @SerializedName("action")
    String action;
    @SerializedName("providerId")
    String providerId;

    public String getAction() {
        return this.action;
    }

    public String getJsonString() {
        return new Gson().toJson((Object) this);
    }

    public String getProviderId() {
        return this.providerId;
    }

    public void setAction(String str) {
        this.action = str;
    }

    public void setProviderId(String str) {
        this.providerId = str;
    }
}
