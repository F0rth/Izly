package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.d.b;

@API
public class GCMHandler extends BroadcastReceiver {
    @API
    protected final boolean isA4SPush(Context context, Intent intent) {
        return intent != null ? intent.hasExtra("a4sid") : false;
    }

    @API
    protected final void onMessage(Context context, Intent intent) {
        Log.debug("Push|Received GCM message");
        processPush(context, intent);
    }

    @API
    protected void onPushReceive(Context context, Intent intent) {
    }

    public final void onReceive(Context context, Intent intent) {
        Log.debug("Push|GCM Registration Receiver received a broadcast");
        String action = intent.getAction();
        if ("com.google.android.c2dm.intent.REGISTRATION".equals(action)) {
            onRegistered(context, intent);
        } else if ("com.google.android.c2dm.intent.RECEIVE".equals(action)) {
            onMessage(context, intent);
        }
    }

    @API
    protected final void onRegistered(Context context, Intent intent) {
        if (b.a() != null) {
            Log.debug("Push|Using InstanceID registration, ignored the registration status");
        } else {
            A4S.get(context).updatePushRegistration(intent.getExtras());
            Log.debug("Push|Received GCM registration status");
        }
        onPushReceive(context, intent);
    }

    @API
    protected final void processA4SPush(Context context, Intent intent) {
        A4S.get(context).handlePushMessage(intent.getExtras());
    }

    @API
    protected void processPush(Context context, Intent intent) {
        if (isA4SPush(context, intent)) {
            processA4SPush(context, intent);
        } else {
            onPushReceive(context, intent);
        }
    }
}
