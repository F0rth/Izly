package com.crashlytics.android.core;

import defpackage.js;
import defpackage.jy;
import java.io.File;
import java.util.Map.Entry;

class DefaultCreateReportSpiCall extends kh implements CreateReportSpiCall {
    static final String FILE_CONTENT_TYPE = "application/octet-stream";
    static final String FILE_PARAM = "report[file]";
    static final String IDENTIFIER_PARAM = "report[identifier]";
    static final String MULTI_FILE_PARAM = "report[file";

    public DefaultCreateReportSpiCall(jy jyVar, String str, String str2, mh mhVar) {
        super(jyVar, str, str2, mhVar, mf.b);
    }

    DefaultCreateReportSpiCall(jy jyVar, String str, String str2, mh mhVar, int i) {
        super(jyVar, str, str2, mhVar, i);
    }

    private mg applyHeadersTo(mg mgVar, CreateReportRequest createReportRequest) {
        mg a = mgVar.a("X-CRASHLYTICS-API-KEY", createReportRequest.apiKey).a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion());
        mg mgVar2 = a;
        for (Entry entry : createReportRequest.report.getCustomHeaders().entrySet()) {
            mgVar2 = mgVar2.a((String) entry.getKey(), (String) entry.getValue());
        }
        return mgVar2;
    }

    private mg applyMultipartDataTo(mg mgVar, Report report) {
        int i = 0;
        mgVar.a(IDENTIFIER_PARAM, null, report.getIdentifier());
        if (report.getFiles().length == 1) {
            js.a().a(CrashlyticsCore.TAG, "Adding single file " + report.getFileName() + " to report " + report.getIdentifier());
            return mgVar.a(FILE_PARAM, report.getFileName(), FILE_CONTENT_TYPE, report.getFile());
        }
        for (File file : report.getFiles()) {
            js.a().a(CrashlyticsCore.TAG, "Adding file " + file.getName() + " to report " + report.getIdentifier());
            mgVar.a(new StringBuilder(MULTI_FILE_PARAM).append(i).append("]").toString(), file.getName(), FILE_CONTENT_TYPE, file);
            i++;
        }
        return mgVar;
    }

    public boolean invoke(CreateReportRequest createReportRequest) {
        mg applyMultipartDataTo = applyMultipartDataTo(applyHeadersTo(getHttpRequest(), createReportRequest), createReportRequest.report);
        js.a().a(CrashlyticsCore.TAG, "Sending report to: " + getUrl());
        int b = applyMultipartDataTo.b();
        js.a().a(CrashlyticsCore.TAG, "Create report request ID: " + applyMultipartDataTo.a("X-REQUEST-ID"));
        js.a().a(CrashlyticsCore.TAG, "Result was: " + b);
        return kz.a(b) == 0;
    }
}
