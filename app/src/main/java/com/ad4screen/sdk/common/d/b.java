package com.ad4screen.sdk.common.d;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.plugins.ADMPlugin;
import com.ad4screen.sdk.plugins.AdvertiserPlugin;
import com.ad4screen.sdk.plugins.BasePlugin;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.GCMPlugin;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.LocationPlugin;

public class b {
    public static GCMPlugin a() {
        BasePlugin a = a.a(Constants.PLUGIN_GCM_NAME, 2);
        return a instanceof GCMPlugin ? (GCMPlugin) a : null;
    }

    public static ADMPlugin b() {
        BasePlugin a = a.a(Constants.PLUGIN_ADM_NAME, 1);
        return a instanceof ADMPlugin ? (ADMPlugin) a : null;
    }

    public static AdvertiserPlugin c() {
        BasePlugin a = a.a(Constants.PLUGIN_ADVERTISER_NAME, 1);
        return a instanceof AdvertiserPlugin ? (AdvertiserPlugin) a : null;
    }

    public static GeofencePlugin d() {
        BasePlugin a = a.a(Constants.PLUGIN_GEOFENCING_NAME, 3);
        return a instanceof GeofencePlugin ? (GeofencePlugin) a : null;
    }

    public static BeaconPlugin e() {
        BasePlugin a = a.a(Constants.PLUGIN_BEACON_NAME, 2);
        return a instanceof BeaconPlugin ? (BeaconPlugin) a : null;
    }

    public static LocationPlugin f() {
        BasePlugin a = a.a(Constants.PLUGIN_LOCATION_NAME, 1);
        return a instanceof LocationPlugin ? (LocationPlugin) a : null;
    }
}
