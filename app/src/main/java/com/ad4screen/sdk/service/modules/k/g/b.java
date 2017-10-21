package com.ad4screen.sdk.service.modules.k.g;

import android.os.Bundle;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.Log;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class b {
    protected final A4S a;

    public b(A4S a4s) {
        this.a = a4s;
    }

    private void d(List<String> list) {
        Bundle c = c(list);
        if (c.size() > 0) {
            this.a.updateDeviceInfo(c);
        }
    }

    public abstract void a(List<String> list);

    public void b(List<String> list) {
        if (list != null && !list.isEmpty()) {
            d(list);
        }
    }

    protected Bundle c(List<String> list) {
        Bundle bundle = new Bundle();
        if (list == null) {
            return bundle;
        }
        for (String str : list) {
            if (str != null) {
                try {
                    JSONObject jSONObject = new JSONObject(str);
                    Iterator keys = jSONObject.keys();
                    while (keys.hasNext()) {
                        String str2 = (String) keys.next();
                        try {
                            bundle.putString(str2, jSONObject.getString(str2));
                        } catch (JSONException e) {
                            Log.internal("DefaultUrlActionsExecutor| No value for key " + str2);
                        }
                    }
                } catch (JSONException e2) {
                    Log.internal("DefaultUrlActionsExecutor| Impossible to parse value in JSONObject : " + str);
                }
            }
        }
        return bundle;
    }
}
