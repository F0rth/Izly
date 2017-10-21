package com.ad4screen.sdk;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.d.b;

import java.util.ArrayList;

@API
public class A4SIDFVHandler extends BroadcastReceiver {
    public void onReceive(Context context, Intent intent) {
        if (intent.getExtras() != null) {
            Bundle extras = intent.getExtras();
            if (extras.containsKey("action")) {
                A4S.get(context).c(extras.getString("action"));
            }
            if (extras.containsKey("confirmation")) {
                A4S.get(context).b(extras.getString("confirmation"));
                return;
            }
            return;
        }
        b a = b.a(context, true);
        if (a.k() != null) {
            Bundle resultExtras = getResultExtras(true);
            ArrayList stringArrayList = resultExtras.getStringArrayList("anonymId");
            if (stringArrayList == null) {
                stringArrayList = new ArrayList();
            }
            stringArrayList.add(a.k() + "|" + i.a(a.k() + "y^X*4]6k],:!e%$&n{@[#!|S2[yH#/I1]Qd;^{+'J1rAkp8!%&&)OV0)\"H$#V2{\"O<+v^6k=q}74;1}=6X3-:G~&F!$]f_L6C>@M"));
            resultExtras.putStringArrayList("anonymId", stringArrayList);
            setResultExtras(resultExtras);
            if (stringArrayList.size() > 5) {
                abortBroadcast();
            }
        }
    }
}
