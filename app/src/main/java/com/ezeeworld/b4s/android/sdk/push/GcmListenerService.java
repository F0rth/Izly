package com.ezeeworld.b4s.android.sdk.push;

import android.os.Bundle;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.notifications.NotificationService;

public class GcmListenerService extends com.ezeeworld.b4s.android.sdk.playservices.gcm.GcmListenerService {
    public void onMessageReceived(String str, Bundle bundle) {
        String string = bundle.getString("sPushText");
        String string2 = bundle.getString("sInteractionData");
        String string3 = bundle.getString("sInteractionId");
        if (string == null) {
            B4SLog.e((Object) this, "Received push message, but it does not include any B4S push text: IGNORE MESSAGE");
            return;
        }
        int hashCode;
        if (string3 != null) {
            hashCode = string3.hashCode();
        } else {
            B4SLog.w((Object) this, "Received push message without bound interaction; continue but this should be for testing only!");
            hashCode = string.hashCode();
        }
        NotificationService.notifyFromPush(this, hashCode, string, string2);
    }
}
