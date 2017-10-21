package com.ad4screen.sdk.common.a.a;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.b;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.e.c;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

public class a extends b {
    public a(Context context) {
        super(context, "com.ad4screen.sdk.common.cache.RequestManager");
    }

    private boolean a(JSONObject jSONObject) {
        try {
            return com.ad4screen.sdk.common.c.b.a.a(jSONObject, "savedQueue");
        } catch (Throwable e) {
            Log.internal("RequestManagerArchive|Error during upgrade file from v4 to v5", e);
            return false;
        }
    }

    private boolean b(JSONObject jSONObject) {
        List arrayList = new ArrayList();
        arrayList.add("com.ad4screen.sdk.service.modules.tracking.ReferrerTrackingTask");
        arrayList.add("com.ad4screen.sdk.service.modules.location.LocationUpdateTask");
        arrayList.add("com.ad4screen.sdk.service.modules.geofencing.LoadGeofencingConfigurationTask");
        arrayList.add("com.ad4screen.sdk.service.modules.authentication.GeofencingUpdateTask");
        arrayList.add("com.ad4screen.sdk.service.modules.tracking.EventTrackingTask");
        arrayList.add("com.ad4screen.sdk.service.modules.tracking.EventLeadTrackingTask");
        arrayList.add("com.ad4screen.sdk.service.modules.tracking.EventCartTrackingTask");
        arrayList.add("com.ad4screen.sdk.service.modules.tracking.EventPurchaseTrackingTask");
        arrayList.add("com.ad4screen.sdk.service.modules.profile.UpdateMemberInfoTask");
        arrayList.add("com.ad4screen.sdk.service.modules.profile.UpdateDeviceInfoTask");
        arrayList.add("com.ad4screen.sdk.service.modules.common.TrackPushTask");
        arrayList.add("com.ad4screen.sdk.service.modules.common.TrackInboxTask");
        arrayList.add("com.ad4screen.sdk.service.modules.common.TrackInAppTask");
        try {
            ConcurrentHashMap concurrentHashMap = (ConcurrentHashMap) new e().a(jSONObject.getString("savedQueue"), new ConcurrentHashMap());
            jSONObject.remove("savedQueue");
            if (!(concurrentHashMap == null || concurrentHashMap.isEmpty())) {
                for (JSONObject jSONObject2 : concurrentHashMap.values()) {
                    try {
                        if (arrayList.contains(jSONObject2.getString("type")) && jSONObject2.has("com.ad4screen.sdk.common.tasks.URLConnectionTask")) {
                            JSONObject jSONObject3 = jSONObject2.getJSONObject("com.ad4screen.sdk.common.tasks.URLConnectionTask");
                            jSONObject3.put("isSecure", true);
                            jSONObject2.put("com.ad4screen.sdk.common.tasks.URLConnectionTask", jSONObject3);
                        }
                    } catch (Throwable e) {
                        Log.internal("RequestManagerArchive|Error during JSONFile upgrade", e);
                    }
                }
                jSONObject.put("savedQueue", new e().a(concurrentHashMap));
            }
            return true;
        } catch (Throwable e2) {
            Log.internal("RequestManagerArchive|Error during upgrade file from v5 to v6", e2);
            return false;
        }
    }

    public ConcurrentHashMap<String, JSONObject> a() {
        return (ConcurrentHashMap) b("savedQueue", new ConcurrentHashMap());
    }

    public void a(ConcurrentHashMap<String, c> concurrentHashMap) {
        a("savedQueue", (Object) concurrentHashMap);
    }

    public boolean a(int i, JSONObject jSONObject) {
        int i2 = 0;
        switch (i) {
            case 4:
                i2 = a(jSONObject) & 0;
                break;
            case 5:
                break;
            default:
                return false;
        }
        return i2 & b(jSONObject);
    }

    public int b() {
        return 6;
    }
}
