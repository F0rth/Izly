package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;
import java.util.Iterator;

public class zzh {
    final String mName;
    final String zzaUa;
    final String zzaVM;
    final long zzaVN;
    final EventParams zzaVO;
    final long zzaez;

    zzh(zzw com_google_android_gms_measurement_internal_zzw, String str, String str2, String str3, long j, long j2, Bundle bundle) {
        zzx.zzcM(str2);
        zzx.zzcM(str3);
        this.zzaUa = str2;
        this.mName = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzaVM = str;
        this.zzaez = j;
        this.zzaVN = j2;
        if (this.zzaVN != 0 && this.zzaVN > this.zzaez) {
            com_google_android_gms_measurement_internal_zzw.zzAo().zzCF().zzfg("Event created with reverse previous/current timestamps");
        }
        this.zzaVO = zza(com_google_android_gms_measurement_internal_zzw, bundle);
    }

    private zzh(zzw com_google_android_gms_measurement_internal_zzw, String str, String str2, String str3, long j, long j2, EventParams eventParams) {
        zzx.zzcM(str2);
        zzx.zzcM(str3);
        zzx.zzz(eventParams);
        this.zzaUa = str2;
        this.mName = str3;
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzaVM = str;
        this.zzaez = j;
        this.zzaVN = j2;
        if (this.zzaVN != 0 && this.zzaVN > this.zzaez) {
            com_google_android_gms_measurement_internal_zzw.zzAo().zzCF().zzfg("Event created with reverse previous/current timestamps");
        }
        this.zzaVO = eventParams;
    }

    private EventParams zza(zzw com_google_android_gms_measurement_internal_zzw, Bundle bundle) {
        if (bundle == null || bundle.isEmpty()) {
            return new EventParams(new Bundle());
        }
        Bundle bundle2 = new Bundle(bundle);
        Iterator it = bundle2.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str == null) {
                it.remove();
            } else {
                Object zzk = com_google_android_gms_measurement_internal_zzw.zzCk().zzk(str, bundle2.get(str));
                if (zzk == null) {
                    it.remove();
                } else {
                    com_google_android_gms_measurement_internal_zzw.zzCk().zza(bundle2, str, zzk);
                }
            }
        }
        return new EventParams(bundle2);
    }

    public String toString() {
        return "Event{appId='" + this.zzaUa + '\'' + ", name='" + this.mName + '\'' + ", params=" + this.zzaVO + '}';
    }

    zzh zza(zzw com_google_android_gms_measurement_internal_zzw, long j) {
        return new zzh(com_google_android_gms_measurement_internal_zzw, this.zzaVM, this.zzaUa, this.mName, this.zzaez, j, this.zzaVO);
    }
}
