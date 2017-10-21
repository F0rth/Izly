package com.ezeeworld.b4s.android.sdk.notifications;

public class MatchedInteration {
    public String campaignName;
    public String interactionName;
    public long timestamp;

    MatchedInteration(long j, String str, String str2) {
        this.timestamp = j;
        this.interactionName = str;
        this.campaignName = str2;
    }
}
