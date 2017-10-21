package com.ezeeworld.b4s.android.sdk;

import android.location.Location;

import com.ezeeworld.b4s.android.sdk.AsyncExecutor.RunnableEx;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionService;
import com.ezeeworld.b4s.android.sdk.playservices.location.LocationServices;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.ManualTag;

import java.util.Hashtable;

public class B4STag {
    public static void event(final String str, final String str2) {
        AsyncExecutor.get().execute(new RunnableEx() {
            public final void run() throws Exception {
                try {
                    ManualTag manualTag = new ManualTag();
                    manualTag.event = str;
                    manualTag.userData = str2;
                    manualTag.userDataDictionnary = null;
                    Location lastLocation = LocationServices.get().getLastLocation();
                    if (lastLocation != null) {
                        manualTag.longitude = Float.valueOf((float) lastLocation.getLongitude());
                        manualTag.latitude = Float.valueOf((float) lastLocation.getLatitude());
                    }
                    InteractionService.fillSessionForTag(manualTag);
                    if (B4SSettings.isInitialized()) {
                        InteractionsApi.get().sendManualTag(manualTag);
                    }
                } catch (Exception e) {
                    B4SLog.e("B4STag", "Tagging failed:" + e.toString());
                }
            }
        });
    }

    public static void event(final String str, final Hashtable hashtable) {
        AsyncExecutor.get().execute(new RunnableEx() {
            public final void run() throws Exception {
                try {
                    ManualTag manualTag = new ManualTag();
                    manualTag.event = str;
                    manualTag.userData = null;
                    manualTag.userDataDictionnary = hashtable;
                    Location lastLocation = LocationServices.get().getLastLocation();
                    if (lastLocation != null) {
                        manualTag.longitude = Float.valueOf((float) lastLocation.getLongitude());
                        manualTag.latitude = Float.valueOf((float) lastLocation.getLatitude());
                    }
                    InteractionService.fillSessionForTag(manualTag);
                    if (B4SSettings.isInitialized()) {
                        InteractionsApi.get().sendManualTag(manualTag);
                    }
                } catch (Exception e) {
                    B4SLog.e("B4STag", "Tagging failed:" + e.toString());
                }
            }
        });
    }
}
