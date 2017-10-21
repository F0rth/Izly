package com.google.android.gms.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.MainThread;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzrp;
import com.google.android.gms.measurement.internal.zzaj;
import com.google.android.gms.measurement.internal.zzp;
import com.google.android.gms.measurement.internal.zzw;

public final class AppMeasurementReceiver extends BroadcastReceiver {
    static zzrp zzOM;
    static Boolean zzON;
    static final Object zzqy = new Object();

    public static boolean zzY(Context context) {
        zzx.zzz(context);
        if (zzON != null) {
            return zzON.booleanValue();
        }
        boolean zza = zzaj.zza(context, AppMeasurementReceiver.class, false);
        zzON = Boolean.valueOf(zza);
        return zza;
    }

    @MainThread
    public final void onReceive(Context context, Intent intent) {
        zzw zzaT = zzw.zzaT(context);
        zzp zzAo = zzaT.zzAo();
        String action = intent.getAction();
        if (zzaT.zzCp().zzkr()) {
            zzAo.zzCK().zzj("Device AppMeasurementReceiver got", action);
        } else {
            zzAo.zzCK().zzj("Local AppMeasurementReceiver got", action);
        }
        if ("com.google.android.gms.measurement.UPLOAD".equals(action)) {
            boolean zzZ = AppMeasurementService.zzZ(context);
            Intent intent2 = new Intent(context, AppMeasurementService.class);
            intent2.setAction("com.google.android.gms.measurement.UPLOAD");
            synchronized (zzqy) {
                context.startService(intent2);
                if (zzZ) {
                    try {
                        if (zzOM == null) {
                            zzrp com_google_android_gms_internal_zzrp = new zzrp(context, 1, "AppMeasurement WakeLock");
                            zzOM = com_google_android_gms_internal_zzrp;
                            com_google_android_gms_internal_zzrp.setReferenceCounted(false);
                        }
                        zzOM.acquire(1000);
                    } catch (SecurityException e) {
                        zzAo.zzCF().zzfg("AppMeasurementService at risk of not starting. For more reliable app measurements, add the WAKE_LOCK permission to your manifest.");
                    }
                    return;
                }
            }
        }
    }
}
