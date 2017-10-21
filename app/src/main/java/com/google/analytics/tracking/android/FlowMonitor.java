package com.google.analytics.tracking.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.android.gms.common.util.VisibleForTesting;
import java.util.HashMap;
import java.util.Map;

class FlowMonitor {
    static final String HITS_DISPATCHED = "hitsDispatched";
    static final String HITS_DISPATCHED_PARAM = "_s";
    static final String HITS_GENERATED = "hitsGenerated";
    static final String HITS_GENERATED_PARAM = "_g";
    static final String HITS_PAST_PROXY = "hitsPastProxy";
    static final String HITS_PAST_PROXY_PARAM = "_p";
    static final String HITS_STORED_IN_DB = "hitsStoredInDb";
    static final String HITS_STORED_IN_DB_PARAM = "_d";
    private static final int HIT_INTERVAL = 50;
    static final String LAST_QUERY_TIMESTAMP = "lastQueryTimestamp";
    static final String LAST_QUERY_TIMESTAMP_PARAM = "_ts";
    static final String MONITOR_TYPE = "flow";
    static final String MONITOR_TYPE_PARAM = "_t";
    static final String PREFERENCES_LABEL = "GoogleAnalytics";
    private static final long TIME_INTERVAL = 14400000;
    private static FlowMonitor sInstance;
    Editor mEditor = this.mPersistentStore.edit();
    private int mHitsDispatched = this.mPersistentStore.getInt(HITS_DISPATCHED, 0);
    private int mHitsGenerated = this.mPersistentStore.getInt(HITS_GENERATED, 0);
    private int mHitsPastProxy = this.mPersistentStore.getInt(HITS_PAST_PROXY, 0);
    private int mHitsStoredInDb = this.mPersistentStore.getInt(HITS_STORED_IN_DB, 0);
    private long mLastQueryTimeStamp = this.mPersistentStore.getLong(LAST_QUERY_TIMESTAMP, 0);
    SharedPreferences mPersistentStore;

    @VisibleForTesting
    FlowMonitor(Context context) {
        this.mPersistentStore = context.getSharedPreferences(PREFERENCES_LABEL, 0);
        if (this.mLastQueryTimeStamp == 0) {
            this.mLastQueryTimeStamp = System.currentTimeMillis();
            this.mEditor.putLong(LAST_QUERY_TIMESTAMP, this.mLastQueryTimeStamp);
            this.mEditor.commit();
        }
    }

    public static FlowMonitor getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new FlowMonitor(context);
        }
        return sInstance;
    }

    public String buildAndResetHitsDispatched() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("_s=").append(this.mHitsDispatched);
        this.mHitsDispatched = 0;
        this.mEditor.putInt(HITS_DISPATCHED, this.mHitsDispatched);
        this.mEditor.commit();
        return stringBuilder.toString();
    }

    public Map<String, String> generateHit() {
        Map<String, String> hashMap = new HashMap();
        hashMap.put(MONITOR_TYPE_PARAM, MONITOR_TYPE);
        hashMap.put(LAST_QUERY_TIMESTAMP_PARAM, String.valueOf(this.mLastQueryTimeStamp));
        this.mLastQueryTimeStamp = System.currentTimeMillis();
        this.mEditor.putLong(LAST_QUERY_TIMESTAMP, this.mLastQueryTimeStamp);
        hashMap.put(HITS_GENERATED_PARAM, getAndResetHitsGeneratedParameter());
        return hashMap;
    }

    String getAndResetHitsGeneratedParameter() {
        int i = this.mHitsGenerated;
        this.mHitsGenerated = 0;
        this.mEditor.putInt(HITS_GENERATED, 0);
        this.mEditor.commit();
        return String.valueOf(i);
    }

    public String getAndResetHitsPastProxy() {
        int i = this.mHitsPastProxy;
        this.mHitsPastProxy = 0;
        this.mEditor.putInt(HITS_PAST_PROXY, this.mHitsPastProxy);
        this.mEditor.commit();
        return String.valueOf(i);
    }

    public String getAndResetHitsStoredInDb() {
        int i = this.mHitsStoredInDb;
        this.mHitsStoredInDb = 0;
        this.mEditor.putInt(HITS_STORED_IN_DB, this.mHitsStoredInDb);
        this.mEditor.commit();
        return String.valueOf(i);
    }

    public void incrementHitsDispatched(int i) {
        this.mHitsDispatched += i;
        this.mEditor.putInt(HITS_DISPATCHED, i);
        this.mEditor.commit();
    }

    public void incrementHitsGenerated(int i) {
        this.mHitsGenerated += i;
        this.mEditor.putInt(HITS_GENERATED, i);
        this.mEditor.commit();
    }

    public void incrementHitsPastProxy(int i) {
        this.mHitsPastProxy += i;
        this.mEditor.putInt(HITS_PAST_PROXY, i);
        this.mEditor.commit();
    }

    public void incrementHitsStoredInDb(int i) {
        this.mHitsStoredInDb += i;
        this.mEditor.putInt(HITS_STORED_IN_DB, i);
        this.mEditor.commit();
    }

    public boolean shouldReport() {
        return this.mHitsGenerated >= 50 && System.currentTimeMillis() - this.mLastQueryTimeStamp >= TIME_INTERVAL;
    }
}
