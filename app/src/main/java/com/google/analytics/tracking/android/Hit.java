package com.google.analytics.tracking.android;

import android.text.TextUtils;

class Hit {
    private final long mHitId;
    private String mHitString;
    private final long mHitTime;
    private String mHitUrlScheme = "https:";

    Hit(String str, long j, long j2) {
        this.mHitString = str;
        this.mHitId = j;
        this.mHitTime = j2;
    }

    long getHitId() {
        return this.mHitId;
    }

    String getHitParams() {
        return this.mHitString;
    }

    long getHitTime() {
        return this.mHitTime;
    }

    String getHitUrlScheme() {
        return this.mHitUrlScheme;
    }

    void setHitString(String str) {
        this.mHitString = str;
    }

    void setHitUrl(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim()) && str.toLowerCase().startsWith("http:")) {
            this.mHitUrlScheme = "http:";
        }
    }
}
