package com.crashlytics.android.answers;

interface SessionAnalyticsManagerStrategy extends ly {
    void deleteAllEvents();

    void processEvent(Builder builder);

    void sendEvents();

    void setAnalyticsSettingsData(mq mqVar, String str);
}
