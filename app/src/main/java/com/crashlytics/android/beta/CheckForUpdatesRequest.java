package com.crashlytics.android.beta;

import defpackage.js;
import defpackage.jy;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

class CheckForUpdatesRequest extends kh {
    static final String BETA_SOURCE = "3";
    static final String BUILD_VERSION = "build_version";
    static final String DISPLAY_VERSION = "display_version";
    static final String HEADER_BETA_TOKEN = "X-CRASHLYTICS-BETA-TOKEN";
    static final String INSTANCE = "instance";
    static final String SDK_ANDROID_DIR_TOKEN_TYPE = "3";
    static final String SOURCE = "source";
    private final CheckForUpdatesResponseTransform responseTransform;

    public CheckForUpdatesRequest(jy jyVar, String str, String str2, mh mhVar, CheckForUpdatesResponseTransform checkForUpdatesResponseTransform) {
        super(jyVar, str, str2, mhVar, mf.a);
        this.responseTransform = checkForUpdatesResponseTransform;
    }

    private mg applyHeadersTo(mg mgVar, String str, String str2) {
        return mgVar.a("Accept", "application/json").a("User-Agent", "Crashlytics Android SDK/" + this.kit.getVersion()).a("X-CRASHLYTICS-DEVELOPER-TOKEN", "470fa2b4ae81cd56ecbcda9735803434cec591fa").a("X-CRASHLYTICS-API-CLIENT-TYPE", "android").a("X-CRASHLYTICS-API-CLIENT-VERSION", this.kit.getVersion()).a("X-CRASHLYTICS-API-KEY", str).a(HEADER_BETA_TOKEN, createBetaTokenHeaderValueFor(str2));
    }

    static String createBetaTokenHeaderValueFor(String str) {
        return "3:" + str;
    }

    private Map<String, String> getQueryParamsFor(BuildProperties buildProperties) {
        Map<String, String> hashMap = new HashMap();
        hashMap.put(BUILD_VERSION, buildProperties.versionCode);
        hashMap.put(DISPLAY_VERSION, buildProperties.versionName);
        hashMap.put(INSTANCE, buildProperties.buildId);
        hashMap.put(SOURCE, "3");
        return hashMap;
    }

    public CheckForUpdatesResponse invoke(String str, String str2, BuildProperties buildProperties) {
        mg applyHeadersTo;
        CheckForUpdatesResponse fromJson;
        Throwable e;
        Throwable th;
        mg mgVar = null;
        try {
            Map queryParamsFor = getQueryParamsFor(buildProperties);
            try {
                applyHeadersTo = applyHeadersTo(getHttpRequest(queryParamsFor), str, str2);
                js.a().a(Beta.TAG, "Checking for updates from " + getUrl());
                js.a().a(Beta.TAG, "Checking for updates query params are: " + queryParamsFor);
                if ((200 == applyHeadersTo.b() ? 1 : null) != null) {
                    js.a().a(Beta.TAG, "Checking for updates was successful");
                    fromJson = this.responseTransform.fromJson(new JSONObject(applyHeadersTo.c()));
                    if (applyHeadersTo != null) {
                        js.a().a("Fabric", "Checking for updates request ID: " + applyHeadersTo.a("X-REQUEST-ID"));
                    }
                } else {
                    js.a().e(Beta.TAG, "Checking for updates failed. Response code: " + applyHeadersTo.b());
                    if (applyHeadersTo != null) {
                        js.a().a("Fabric", "Checking for updates request ID: " + applyHeadersTo.a("X-REQUEST-ID"));
                    }
                }
            } catch (Exception e2) {
                e = e2;
                try {
                    js.a().c(Beta.TAG, "Error while checking for updates from " + getUrl(), e);
                    if (applyHeadersTo != null) {
                        js.a().a("Fabric", "Checking for updates request ID: " + applyHeadersTo.a("X-REQUEST-ID"));
                    }
                    return fromJson;
                } catch (Throwable th2) {
                    mg mgVar2 = applyHeadersTo;
                    th = th2;
                    mgVar = mgVar2;
                    if (mgVar != null) {
                        js.a().a("Fabric", "Checking for updates request ID: " + mgVar.a("X-REQUEST-ID"));
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            e = th3;
            applyHeadersTo = null;
            js.a().c(Beta.TAG, "Error while checking for updates from " + getUrl(), e);
            if (applyHeadersTo != null) {
                js.a().a("Fabric", "Checking for updates request ID: " + applyHeadersTo.a("X-REQUEST-ID"));
            }
            return fromJson;
        } catch (Throwable th4) {
            th3 = th4;
            if (mgVar != null) {
                js.a().a("Fabric", "Checking for updates request ID: " + mgVar.a("X-REQUEST-ID"));
            }
            throw th3;
        }
        return fromJson;
    }
}
