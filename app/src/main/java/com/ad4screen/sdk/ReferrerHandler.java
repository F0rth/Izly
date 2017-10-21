package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.ad4screen.sdk.common.annotations.API;

@API
public final class ReferrerHandler extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null && intent.hasExtra("referrer")) {
            String stringExtra = intent.getStringExtra("referrer");
            Log.debug("Referrer was found : " + stringExtra);
            A4S.get(context).d(stringExtra);
        }
    }
}
