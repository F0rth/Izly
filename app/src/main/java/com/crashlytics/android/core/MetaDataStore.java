package com.crashlytics.android.core;

import defpackage.js;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class MetaDataStore {
    private static final String KEYDATA_SUFFIX = "keys";
    private static final String KEY_USER_EMAIL = "userEmail";
    private static final String KEY_USER_ID = "userId";
    private static final String KEY_USER_NAME = "userName";
    private static final String METADATA_EXT = ".meta";
    private static final String USERDATA_SUFFIX = "user";
    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final File filesDir;

    public MetaDataStore(File file) {
        this.filesDir = file;
    }

    private File getKeysFileForSession(String str) {
        return new File(this.filesDir, str + "keys.meta");
    }

    private File getUserDataFileForSession(String str) {
        return new File(this.filesDir, str + "user.meta");
    }

    private static Map<String, String> jsonToKeysData(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        Map<String, String> hashMap = new HashMap();
        Iterator keys = jSONObject.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            hashMap.put(str2, valueOrNull(jSONObject, str2));
        }
        return hashMap;
    }

    private static UserMetaData jsonToUserData(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        return new UserMetaData(valueOrNull(jSONObject, KEY_USER_ID), valueOrNull(jSONObject, KEY_USER_NAME), valueOrNull(jSONObject, KEY_USER_EMAIL));
    }

    private static String keysDataToJson(Map<String, String> map) throws JSONException {
        return new JSONObject(map).toString();
    }

    private static String userDataToJson(final UserMetaData userMetaData) throws JSONException {
        return new JSONObject() {
        }.toString();
    }

    private static String valueOrNull(JSONObject jSONObject, String str) {
        return !jSONObject.isNull(str) ? jSONObject.optString(str, null) : null;
    }

    public Map<String, String> readKeyData(String str) {
        Throwable e;
        Closeable closeable = null;
        File keysFileForSession = getKeysFileForSession(str);
        if (!keysFileForSession.exists()) {
            return Collections.emptyMap();
        }
        try {
            Closeable fileInputStream = new FileInputStream(keysFileForSession);
            try {
                Map<String, String> jsonToKeysData = jsonToKeysData(kp.a(fileInputStream));
                kp.a(fileInputStream, "Failed to close user metadata file.");
                return jsonToKeysData;
            } catch (Exception e2) {
                e = e2;
                closeable = fileInputStream;
                try {
                    js.a().c(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    kp.a(closeable, "Failed to close user metadata file.");
                    return Collections.emptyMap();
                } catch (Throwable th) {
                    e = th;
                    kp.a(closeable, "Failed to close user metadata file.");
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                closeable = fileInputStream;
                kp.a(closeable, "Failed to close user metadata file.");
                throw e;
            }
        } catch (Exception e3) {
            e = e3;
            js.a().c(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            kp.a(closeable, "Failed to close user metadata file.");
            return Collections.emptyMap();
        } catch (Throwable th3) {
            e = th3;
            kp.a(closeable, "Failed to close user metadata file.");
            throw e;
        }
    }

    public UserMetaData readUserData(String str) {
        Throwable e;
        Closeable closeable = null;
        File userDataFileForSession = getUserDataFileForSession(str);
        if (!userDataFileForSession.exists()) {
            return UserMetaData.EMPTY;
        }
        try {
            Closeable fileInputStream = new FileInputStream(userDataFileForSession);
            try {
                UserMetaData jsonToUserData = jsonToUserData(kp.a(fileInputStream));
                kp.a(fileInputStream, "Failed to close user metadata file.");
                return jsonToUserData;
            } catch (Exception e2) {
                e = e2;
                closeable = fileInputStream;
                try {
                    js.a().c(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
                    kp.a(closeable, "Failed to close user metadata file.");
                    return UserMetaData.EMPTY;
                } catch (Throwable th) {
                    e = th;
                    kp.a(closeable, "Failed to close user metadata file.");
                    throw e;
                }
            } catch (Throwable th2) {
                e = th2;
                closeable = fileInputStream;
                kp.a(closeable, "Failed to close user metadata file.");
                throw e;
            }
        } catch (Exception e3) {
            e = e3;
            js.a().c(CrashlyticsCore.TAG, "Error deserializing user metadata.", e);
            kp.a(closeable, "Failed to close user metadata file.");
            return UserMetaData.EMPTY;
        } catch (Throwable th3) {
            e = th3;
            kp.a(closeable, "Failed to close user metadata file.");
            throw e;
        }
    }

    public void writeKeyData(String str, Map<String, String> map) {
        Closeable bufferedWriter;
        Throwable e;
        Throwable th;
        Throwable th2;
        Closeable closeable = null;
        File keysFileForSession = getKeysFileForSession(str);
        try {
            String keysDataToJson = keysDataToJson(map);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(keysFileForSession), UTF_8));
            try {
                bufferedWriter.write(keysDataToJson);
                bufferedWriter.flush();
                kp.a(bufferedWriter, "Failed to close key/value metadata file.");
            } catch (Exception e2) {
                e = e2;
                try {
                    js.a().c(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e);
                    kp.a(bufferedWriter, "Failed to close key/value metadata file.");
                } catch (Throwable e3) {
                    th = e3;
                    closeable = bufferedWriter;
                    th2 = th;
                    kp.a(closeable, "Failed to close key/value metadata file.");
                    throw th2;
                }
            }
        } catch (Throwable th22) {
            th = th22;
            bufferedWriter = null;
            e3 = th;
            js.a().c(CrashlyticsCore.TAG, "Error serializing key/value metadata.", e3);
            kp.a(bufferedWriter, "Failed to close key/value metadata file.");
        } catch (Throwable th3) {
            th22 = th3;
            kp.a(closeable, "Failed to close key/value metadata file.");
            throw th22;
        }
    }

    public void writeUserData(String str, UserMetaData userMetaData) {
        Closeable bufferedWriter;
        Throwable e;
        Throwable th;
        Throwable th2;
        Closeable closeable = null;
        File userDataFileForSession = getUserDataFileForSession(str);
        try {
            String userDataToJson = userDataToJson(userMetaData);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(userDataFileForSession), UTF_8));
            try {
                bufferedWriter.write(userDataToJson);
                bufferedWriter.flush();
                kp.a(bufferedWriter, "Failed to close user metadata file.");
            } catch (Exception e2) {
                e = e2;
                try {
                    js.a().c(CrashlyticsCore.TAG, "Error serializing user metadata.", e);
                    kp.a(bufferedWriter, "Failed to close user metadata file.");
                } catch (Throwable e3) {
                    th = e3;
                    closeable = bufferedWriter;
                    th2 = th;
                    kp.a(closeable, "Failed to close user metadata file.");
                    throw th2;
                }
            }
        } catch (Throwable th22) {
            th = th22;
            bufferedWriter = null;
            e3 = th;
            js.a().c(CrashlyticsCore.TAG, "Error serializing user metadata.", e3);
            kp.a(bufferedWriter, "Failed to close user metadata file.");
        } catch (Throwable th3) {
            th22 = th3;
            kp.a(closeable, "Failed to close user metadata file.");
            throw th22;
        }
    }
}
