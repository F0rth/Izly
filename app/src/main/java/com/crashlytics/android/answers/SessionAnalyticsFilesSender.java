package com.crashlytics.android.answers;

import defpackage.js;
import defpackage.jy;
import java.io.File;
import java.util.List;

class SessionAnalyticsFilesSender extends kh implements lz {
    static final String FILE_CONTENT_TYPE = "application/vnd.crashlytics.android.events";
    static final String FILE_PARAM_NAME = "session_analytics_file_";
    private final String apiKey;

    public SessionAnalyticsFilesSender(jy jyVar, String str, String str2, mh mhVar, String str3) {
        super(jyVar, str, str2, mhVar, mf.b);
        this.apiKey = str3;
    }

    public boolean send(List<File> list) {
        mg a = getHttpRequest().a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).a("X-CRASHLYTICS-API-KEY", this.apiKey);
        int i = 0;
        for (File file : list) {
            a.a(new StringBuilder(FILE_PARAM_NAME).append(i).toString(), file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        js.a().a(Answers.TAG, "Sending " + list.size() + " analytics files to " + getUrl());
        int b = a.b();
        js.a().a(Answers.TAG, "Response code for analytics file send is " + b);
        return kz.a(b) == 0;
    }
}
