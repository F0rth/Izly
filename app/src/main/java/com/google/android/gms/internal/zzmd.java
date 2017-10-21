package com.google.android.gms.internal;

import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import com.google.android.gms.common.internal.zzw;

public final class zzmd extends LruCache<zza, Drawable> {

    public static final class zza {
        public final int zzakx;
        public final int zzaky;

        public zza(int i, int i2) {
            this.zzakx = i;
            this.zzaky = i2;
        }

        public final boolean equals(Object obj) {
            if (obj instanceof zza) {
                if (this == obj) {
                    return true;
                }
                zza com_google_android_gms_internal_zzmd_zza = (zza) obj;
                if (com_google_android_gms_internal_zzmd_zza.zzakx == this.zzakx && com_google_android_gms_internal_zzmd_zza.zzaky == this.zzaky) {
                    return true;
                }
            }
            return false;
        }

        public final int hashCode() {
            return zzw.hashCode(Integer.valueOf(this.zzakx), Integer.valueOf(this.zzaky));
        }
    }

    public zzmd() {
        super(10);
    }
}
