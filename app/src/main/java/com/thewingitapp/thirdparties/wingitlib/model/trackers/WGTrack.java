package com.thewingitapp.thirdparties.wingitlib.model.trackers;

public class WGTrack extends WGTrackDTO {
    public String getData() {
        return this.data;
    }

    public String getURI() {
        return this.uri;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setData(String str) {
        this.data = str;
    }

    public void setURI(String str) {
        this.uri = str;
    }

    public void setUserId(String str) {
        this.userId = str;
    }
}
