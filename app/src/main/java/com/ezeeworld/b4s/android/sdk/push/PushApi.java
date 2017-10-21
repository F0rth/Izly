package com.ezeeworld.b4s.android.sdk.push;

import android.content.SharedPreferences;

import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.Device;
import com.ezeeworld.b4s.android.sdk.playservices.gcm.InstanceIDServices;
import com.ezeeworld.b4s.android.sdk.server.Api2;

import java.io.IOException;

import retrofit2.Response;

public class PushApi {
    private final SharedPreferences a;
    private final Api2 b;

    static class a {
        static final PushApi a = new PushApi();
    }

    private PushApi() {
        this.a = B4SSettings.get().getPreferences();
        this.b = Api2.get();
    }

    private boolean a() {
        return B4SSettings.get().getPushMessagingSenderId() != null ? (this.a.contains("b4s_push_senderid") && this.a.getString("b4s_push_senderid", "").equals(B4SSettings.get().getPushMessagingSenderId()) && this.a.contains("b4s_push_token") && this.a.getInt("b4s_push_appcode", 0) == B4SSettings.get().getAppCode()) ? false : true : false;
    }

    public static PushApi get() {
        return a.a;
    }

    public void perhapsRegisterToken() {
        if (a()) {
            registerToken();
        }
    }

    public boolean registerToken() {
        B4SSettings b4SSettings = B4SSettings.get();
        String token = InstanceIDServices.get().getToken(b4SSettings.getPushMessagingSenderId());
        if (token == null) {
            return false;
        }
        Response execute;
        PushRegistration pushRegistration = new PushRegistration();
        pushRegistration.sAppId = b4SSettings.getAppId();
        pushRegistration.sBundleID = b4SSettings.getAppPackage();
        pushRegistration.sDeviceOs = "ANDROID";
        pushRegistration.sDeviceUdid = Device.getDeviceId();
        pushRegistration.sRemoteNotificationToken = token;
        try {
            execute = ((PushRoutes) this.b.getRoutes(PushRoutes.class)).registerToken(pushRegistration).execute();
        } catch (IOException e) {
            execute = null;
        }
        if (execute == null || execute.code() != 200) {
            return false;
        }
        this.a.edit().putString("b4s_push_senderid", b4SSettings.getPushMessagingSenderId()).putString("b4s_push_token", token).putInt("b4s_push_appcode", b4SSettings.getAppCode()).apply();
        return true;
    }

    public void removeTokenRegistration() {
        this.a.edit().remove("b4s_push_senderid").remove("b4s_push_token").remove("b4s_push_appversion").apply();
    }

    public void renewTokenRegistration() {
        this.a.edit().remove("b4s_push_senderid").remove("b4s_push_token").remove("b4s_push_appversion").apply();
        registerToken();
    }
}
