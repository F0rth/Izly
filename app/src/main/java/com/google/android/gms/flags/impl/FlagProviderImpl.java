package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.flags.impl.zza.zzb;
import com.google.android.gms.flags.impl.zza.zzc;
import com.google.android.gms.flags.impl.zza.zzd;
import com.google.android.gms.internal.zzpk.zza;

public class FlagProviderImpl extends zza {
    private boolean zzqA = false;
    private SharedPreferences zzvx;

    public boolean getBooleanFlagValue(String str, boolean z, int i) {
        return zza.zza.zza(this.zzvx, str, Boolean.valueOf(z)).booleanValue();
    }

    public int getIntFlagValue(String str, int i, int i2) {
        return zzb.zza(this.zzvx, str, Integer.valueOf(i)).intValue();
    }

    public long getLongFlagValue(String str, long j, int i) {
        return zzc.zza(this.zzvx, str, Long.valueOf(j)).longValue();
    }

    public String getStringFlagValue(String str, String str2, int i) {
        return zzd.zza(this.zzvx, str, str2);
    }

    public void init(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        Context context = (Context) zze.zzp(com_google_android_gms_dynamic_zzd);
        if (!this.zzqA) {
            try {
                this.zzvx = zzb.zzw(context.createPackageContext("com.google.android.gms", 0));
                this.zzqA = true;
            } catch (NameNotFoundException e) {
            }
        }
    }
}
