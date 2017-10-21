package com.google.android.gms.gcm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.measurement.AppMeasurement;

class zza {
    static AppMeasurement zzaLx;

    private static void zza(Context context, String str, Intent intent) {
        Bundle bundle = new Bundle();
        String stringExtra = intent.getStringExtra("google.c.a.c_id");
        String stringExtra2 = intent.getStringExtra("google.c.a.c_l");
        if (!(stringExtra == null || stringExtra2 == null)) {
            bundle.putString("_nmid", stringExtra);
            bundle.putString("_nmn", stringExtra2);
        }
        stringExtra = intent.getStringExtra("from");
        if (stringExtra == null || !stringExtra.startsWith("/topics/")) {
            stringExtra = null;
        }
        if (stringExtra != null) {
            bundle.putString("_nt", stringExtra);
        }
        try {
            bundle.putInt("_nmt", Integer.valueOf(intent.getStringExtra("google.c.a.ts")).intValue());
        } catch (NumberFormatException e) {
            Log.w("GcmAnalytics", "Error while parsing timestamp in GCM event.");
        }
        if (intent.hasExtra("google.c.a.udt")) {
            try {
                bundle.putInt("_ndt", Integer.valueOf(intent.getStringExtra("google.c.a.udt")).intValue());
            } catch (NumberFormatException e2) {
                Log.w("GcmAnalytics", "Error while parsing use_device_time in GCM event.");
            }
        }
        if (Log.isLoggable("GcmAnalytics", 3)) {
            Log.d("GcmAnalytics", "Sending event=" + str + " params=" + bundle);
        }
        try {
            (zzaLx == null ? AppMeasurement.getInstance(context) : zzaLx).zzd(GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE, str, bundle);
        } catch (NoClassDefFoundError e3) {
            Log.w("GcmAnalytics", "Unable to log event, missing measurement library");
        }
    }

    public static void zze(Context context, Intent intent) {
        zza(context, "_nr", intent);
    }

    public static void zzf(Context context, Intent intent) {
        zza(context, "_no", intent);
    }

    public static void zzg(Context context, Intent intent) {
        zza(context, "_nd", intent);
    }

    public static void zzh(Context context, Intent intent) {
        zza(context, "_nf", intent);
    }
}
