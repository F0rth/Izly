package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzpz.zzf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

class zzae {
    final boolean zzaXg;
    final int zzaZh;
    final boolean zzaZi;
    final String zzaZj;
    final List<String> zzaZk;
    final String zzaZl;

    public zzae(zzf com_google_android_gms_internal_zzpz_zzf) {
        boolean z;
        boolean z2 = false;
        zzx.zzz(com_google_android_gms_internal_zzpz_zzf);
        if (com_google_android_gms_internal_zzpz_zzf.zzaZN == null || com_google_android_gms_internal_zzpz_zzf.zzaZN.intValue() == 0) {
            z = false;
        } else {
            if (com_google_android_gms_internal_zzpz_zzf.zzaZN.intValue() == 6) {
                if (com_google_android_gms_internal_zzpz_zzf.zzaZQ == null || com_google_android_gms_internal_zzpz_zzf.zzaZQ.length == 0) {
                    z = false;
                }
            } else if (com_google_android_gms_internal_zzpz_zzf.zzaZO == null) {
                z = false;
            }
            z = true;
        }
        if (z) {
            this.zzaZh = com_google_android_gms_internal_zzpz_zzf.zzaZN.intValue();
            if (com_google_android_gms_internal_zzpz_zzf.zzaZP != null && com_google_android_gms_internal_zzpz_zzf.zzaZP.booleanValue()) {
                z2 = true;
            }
            this.zzaZi = z2;
            if (this.zzaZi || this.zzaZh == 1 || this.zzaZh == 6) {
                this.zzaZj = com_google_android_gms_internal_zzpz_zzf.zzaZO;
            } else {
                this.zzaZj = com_google_android_gms_internal_zzpz_zzf.zzaZO.toUpperCase(Locale.ENGLISH);
            }
            this.zzaZk = com_google_android_gms_internal_zzpz_zzf.zzaZQ == null ? null : zza(com_google_android_gms_internal_zzpz_zzf.zzaZQ, this.zzaZi);
            if (this.zzaZh == 1) {
                this.zzaZl = this.zzaZj;
            } else {
                this.zzaZl = null;
            }
        } else {
            this.zzaZh = 0;
            this.zzaZi = false;
            this.zzaZj = null;
            this.zzaZk = null;
            this.zzaZl = null;
        }
        this.zzaXg = z;
    }

    private List<String> zza(String[] strArr, boolean z) {
        if (z) {
            return Arrays.asList(strArr);
        }
        List<String> arrayList = new ArrayList();
        for (String toUpperCase : strArr) {
            arrayList.add(toUpperCase.toUpperCase(Locale.ENGLISH));
        }
        return arrayList;
    }

    public Boolean zzfp(String str) {
        if (!this.zzaXg) {
            return null;
        }
        if (!(this.zzaZi || this.zzaZh == 1)) {
            str = str.toUpperCase(Locale.ENGLISH);
        }
        switch (this.zzaZh) {
            case 1:
                return Boolean.valueOf(Pattern.compile(this.zzaZl, this.zzaZi ? 0 : 66).matcher(str).matches());
            case 2:
                return Boolean.valueOf(str.startsWith(this.zzaZj));
            case 3:
                return Boolean.valueOf(str.endsWith(this.zzaZj));
            case 4:
                return Boolean.valueOf(str.contains(this.zzaZj));
            case 5:
                return Boolean.valueOf(str.equals(this.zzaZj));
            case 6:
                return Boolean.valueOf(this.zzaZk.contains(str));
            default:
                return null;
        }
    }
}
