package com.crashlytics.android.beta;

import java.io.IOException;
import org.json.JSONObject;

class CheckForUpdatesResponseTransform {
    static final String BUILD_VERSION = "build_version";
    static final String DISPLAY_VERSION = "display_version";
    static final String IDENTIFIER = "identifier";
    static final String INSTANCE_IDENTIFIER = "instance_identifier";
    static final String URL = "url";
    static final String VERSION_STRING = "version_string";

    CheckForUpdatesResponseTransform() {
    }

    public CheckForUpdatesResponse fromJson(JSONObject jSONObject) throws IOException {
        if (jSONObject == null) {
            return null;
        }
        return new CheckForUpdatesResponse(jSONObject.optString(URL, null), jSONObject.optString(VERSION_STRING, null), jSONObject.optString(DISPLAY_VERSION, null), jSONObject.optString(BUILD_VERSION, null), jSONObject.optString(IDENTIFIER, null), jSONObject.optString(INSTANCE_IDENTIFIER, null));
    }
}
