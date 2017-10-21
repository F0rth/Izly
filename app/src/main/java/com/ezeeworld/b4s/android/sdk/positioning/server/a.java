package com.ezeeworld.b4s.android.sdk.positioning.server;

import android.os.Build.VERSION;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.Device;
import com.ezeeworld.b4s.android.sdk.playservices.AdvertisingServices;
import com.ezeeworld.b4s.android.sdk.positioning.server.IndoorMap.Request;
import com.ezeeworld.b4s.android.sdk.server.Api2;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class a {
    private final Api2 a;
    private Map<String, IndoorMap> b;
    private final String c;
    private String d;

    private a() {
        this.a = Api2.get();
        this.b = new HashMap();
        this.c = Device.getDeviceModel();
        String identifier = AdvertisingServices.get().getIdentifier();
        if (identifier == null) {
            identifier = "0";
        }
        this.d = identifier;
    }

    public static a a() {
        return c.a;
    }

    public final IndoorMap a(String str) {
        Request request = new Request();
        request.sMapId = str;
        request.sOS = "ANDROID";
        request.sOSVersion = VERSION.RELEASE;
        request.sDeviceModel = Device.getDeviceModel();
        try {
            IndoorMap indoorMap = (IndoorMap) ((PositioningRoutes) this.a.getRoutes(PositioningRoutes.class)).getIndoorMap(request).execute().body();
            this.b.put(str, indoorMap);
            return indoorMap;
        } catch (IOException e) {
            B4SLog.w((Object) this, "Impossible to load map data for '" + str + "' (use cache if possible): " + e.toString());
            return (IndoorMap) this.b.get(str);
        }
    }
}
