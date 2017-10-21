package com.google.tagmanager;

import android.text.TextUtils;

class Hit {
    private final long mHitFirstDispatchTime;
    private final long mHitId;
    private final long mHitTime;
    private String mHitUrl;

    Hit(long j, long j2) {
        this.mHitId = j;
        this.mHitTime = j2;
        this.mHitFirstDispatchTime = 0;
    }

    Hit(long j, long j2, long j3) {
        this.mHitId = j;
        this.mHitTime = j2;
        this.mHitFirstDispatchTime = j3;
    }

    long getHitFirstDispatchTime() {
        return this.mHitFirstDispatchTime;
    }

    long getHitId() {
        return this.mHitId;
    }

    long getHitTime() {
        return this.mHitTime;
    }

    String getHitUrl() {
        return this.mHitUrl;
    }

    void setHitUrl(String str) {
        if (str != null && !TextUtils.isEmpty(str.trim())) {
            this.mHitUrl = str;
        }
    }
}
