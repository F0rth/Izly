package com.ad4screen.sdk.service.modules.d;

import android.os.Bundle;
import android.os.ResultReceiver;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.common.c.e;

public class a {
    private ResultReceiver a;

    public void a(ResultReceiver resultReceiver) {
        this.a = resultReceiver;
    }

    public void a(d dVar) {
        if (this.a != null) {
            try {
                String jSONObject = new e().a(dVar).toString();
                if (jSONObject != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("com.ad4screen.sdk.A4SClient.inAppFormat", jSONObject);
                    this.a.send(2, bundle);
                    return;
                }
                Log.error("InApp|InApp #" + dVar.h + " format could not be serialized to JSON.");
            } catch (Throwable e) {
                Log.internal("InApp|Error while serializing InApp Format.", e);
            }
        }
    }

    public void a(String str, String str2) {
        if (this.a != null) {
            Bundle bundle = new Bundle();
            bundle.putString("com.ad4screen.sdk.A4SClient.inAppId", str);
            if (str2 != null) {
                bundle.putString("com.ad4screen.sdk.A4SClient.activityInstance", str2);
            }
            this.a.send(1, bundle);
        }
    }

    public boolean a(d dVar, int i) {
        if (this.a != null) {
            try {
                String jSONObject = new e().a(dVar).toString();
                if (jSONObject != null) {
                    Bundle bundle = new Bundle();
                    bundle.putString("com.ad4screen.sdk.A4SClient.inAppFormat", jSONObject);
                    bundle.putInt("com.ad4screen.sdk.A4SClient.inAppTemplate", i);
                    this.a.send(3, bundle);
                    return true;
                }
                Log.error("InApp|InApp #" + dVar.h + " format could not be serialized to JSON.");
            } catch (Throwable e) {
                Log.internal("InApp|Error while serializing InApp Format.", e);
            }
        }
        return false;
    }

    public boolean a(d dVar, String str) {
        if (this.a == null) {
            return false;
        }
        try {
            String jSONObject = new e().a(dVar).toString();
            if (jSONObject != null) {
                Bundle bundle = new Bundle();
                bundle.putString("com.ad4screen.sdk.A4SClient.inAppFormat", jSONObject);
                bundle.putString("com.ad4screen.sdk.A4SClient.activityInstance", str);
                this.a.send(0, bundle);
                return true;
            }
            Log.error("InApp|InApp #" + dVar.h + " format could not be serialized to JSON.");
            return false;
        } catch (Throwable e) {
            Log.internal("InApp|Error while serializing InApp Format.", e);
            return false;
        }
    }
}
