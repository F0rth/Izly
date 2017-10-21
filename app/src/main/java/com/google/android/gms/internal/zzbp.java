package com.google.android.gms.internal;

import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzr;

@zzhb
public abstract class zzbp<T> {
    private final int zzvr;
    private final String zzvs;
    private final T zzvt;

    private zzbp(int i, String str, T t) {
        this.zzvr = i;
        this.zzvs = str;
        this.zzvt = t;
        zzr.zzbK().zza(this);
    }

    public static zzbp<String> zza(int i, String str) {
        zzbp<String> zza = zza(i, str, null);
        zzr.zzbK().zzb(zza);
        return zza;
    }

    public static zzbp<Integer> zza(int i, String str, int i2) {
        return new zzbp<Integer>(i, str, Integer.valueOf(i2)) {
            public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
                return zzc(sharedPreferences);
            }

            public final Integer zzc(SharedPreferences sharedPreferences) {
                return Integer.valueOf(sharedPreferences.getInt(getKey(), ((Integer) zzdq()).intValue()));
            }
        };
    }

    public static zzbp<Long> zza(int i, String str, long j) {
        return new zzbp<Long>(i, str, Long.valueOf(j)) {
            public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
                return zzd(sharedPreferences);
            }

            public final Long zzd(SharedPreferences sharedPreferences) {
                return Long.valueOf(sharedPreferences.getLong(getKey(), ((Long) zzdq()).longValue()));
            }
        };
    }

    public static zzbp<Boolean> zza(int i, String str, Boolean bool) {
        return new zzbp<Boolean>(i, str, bool) {
            public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
                return zzb(sharedPreferences);
            }

            public final Boolean zzb(SharedPreferences sharedPreferences) {
                return Boolean.valueOf(sharedPreferences.getBoolean(getKey(), ((Boolean) zzdq()).booleanValue()));
            }
        };
    }

    public static zzbp<String> zza(int i, String str, String str2) {
        return new zzbp<String>(i, str, str2) {
            public final /* synthetic */ Object zza(SharedPreferences sharedPreferences) {
                return zze(sharedPreferences);
            }

            public final String zze(SharedPreferences sharedPreferences) {
                return sharedPreferences.getString(getKey(), (String) zzdq());
            }
        };
    }

    public static zzbp<String> zzb(int i, String str) {
        zzbp<String> zza = zza(i, str, null);
        zzr.zzbK().zzc(zza);
        return zza;
    }

    public T get() {
        return zzr.zzbL().zzd(this);
    }

    public String getKey() {
        return this.zzvs;
    }

    protected abstract T zza(SharedPreferences sharedPreferences);

    public T zzdq() {
        return this.zzvt;
    }
}
