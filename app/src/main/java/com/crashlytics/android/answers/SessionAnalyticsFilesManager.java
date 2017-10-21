package com.crashlytics.android.answers;

import android.content.Context;
import java.io.IOException;
import java.util.UUID;

class SessionAnalyticsFilesManager extends lv<SessionEvent> {
    private static final String SESSION_ANALYTICS_TO_SEND_FILE_EXTENSION = ".tap";
    private static final String SESSION_ANALYTICS_TO_SEND_FILE_PREFIX = "sa";
    private mq analyticsSettingsData;

    SessionAnalyticsFilesManager(Context context, SessionEventTransform sessionEventTransform, kr krVar, lw lwVar) throws IOException {
        super(context, sessionEventTransform, krVar, lwVar, 100);
    }

    protected String generateUniqueRollOverFileName() {
        return new StringBuilder(SESSION_ANALYTICS_TO_SEND_FILE_PREFIX).append("_").append(UUID.randomUUID().toString()).append("_").append(this.currentTimeProvider.a()).append(SESSION_ANALYTICS_TO_SEND_FILE_EXTENSION).toString();
    }

    protected int getMaxByteSizePerFile() {
        return this.analyticsSettingsData == null ? super.getMaxByteSizePerFile() : this.analyticsSettingsData.c;
    }

    protected int getMaxFilesToKeep() {
        return this.analyticsSettingsData == null ? super.getMaxFilesToKeep() : this.analyticsSettingsData.e;
    }

    void setAnalyticsSettingsData(mq mqVar) {
        this.analyticsSettingsData = mqVar;
    }
}
