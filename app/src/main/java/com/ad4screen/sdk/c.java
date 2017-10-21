package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class c extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        String b = com.ad4screen.sdk.common.c.b(context);
        String c = com.ad4screen.sdk.common.c.c(context);
        if (intent == null) {
            Log.internal("FacebookReceiver|Trying to retrieve a Facebook Token of a previous opened session");
        }
        if (b == null) {
            Log.internal("FacebookReceiver|Can't retrieve Facebook App Id");
        } else if (c == null) {
            Log.internal("FacebookReceiver|Can't retrieve Facebook Token");
        } else {
            Log.internal("FacebookReceiver|New Facebook session opened, uploading facebook profile");
            A4S.get(context).a(b, c, com.ad4screen.sdk.common.c.d(context));
        }
    }
}
