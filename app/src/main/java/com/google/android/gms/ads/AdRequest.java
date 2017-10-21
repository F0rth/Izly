package com.google.android.gms.ads;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.client.zzaa;
import com.google.android.gms.ads.internal.client.zzaa.zza;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.common.internal.zzx;
import java.util.Date;
import java.util.Set;

public final class AdRequest {
    public static final String DEVICE_ID_EMULATOR = zzaa.DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    public static final int MAX_CONTENT_URL_LENGTH = 512;
    private final zzaa zzoE;

    public static final class Builder {
        private final zza zzoF = new zza();

        public Builder() {
            this.zzoF.zzB(AdRequest.DEVICE_ID_EMULATOR);
        }

        public final Builder addCustomEventExtrasBundle(Class<? extends CustomEvent> cls, Bundle bundle) {
            this.zzoF.zzb(cls, bundle);
            return this;
        }

        public final Builder addKeyword(String str) {
            this.zzoF.zzA(str);
            return this;
        }

        public final Builder addNetworkExtras(NetworkExtras networkExtras) {
            this.zzoF.zza(networkExtras);
            return this;
        }

        public final Builder addNetworkExtrasBundle(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.zzoF.zza((Class) cls, bundle);
            if (cls.equals(AdMobAdapter.class) && bundle.getBoolean("_emulatorLiveAds")) {
                this.zzoF.zzC(AdRequest.DEVICE_ID_EMULATOR);
            }
            return this;
        }

        public final Builder addTestDevice(String str) {
            this.zzoF.zzB(str);
            return this;
        }

        public final AdRequest build() {
            return new AdRequest();
        }

        public final Builder setBirthday(Date date) {
            this.zzoF.zza(date);
            return this;
        }

        public final Builder setContentUrl(String str) {
            zzx.zzb((Object) str, (Object) "Content URL must be non-null.");
            zzx.zzh(str, "Content URL must be non-empty.");
            zzx.zzb(str.length() <= 512, "Content URL must not exceed %d in length.  Provided length was %d.", Integer.valueOf(512), Integer.valueOf(str.length()));
            this.zzoF.zzD(str);
            return this;
        }

        public final Builder setGender(int i) {
            this.zzoF.zzn(i);
            return this;
        }

        public final Builder setIsDesignedForFamilies(boolean z) {
            this.zzoF.zzl(z);
            return this;
        }

        public final Builder setLocation(Location location) {
            this.zzoF.zzb(location);
            return this;
        }

        public final Builder setRequestAgent(String str) {
            this.zzoF.zzF(str);
            return this;
        }

        public final Builder tagForChildDirectedTreatment(boolean z) {
            this.zzoF.zzk(z);
            return this;
        }
    }

    private AdRequest(Builder builder) {
        this.zzoE = new zzaa(builder.zzoF);
    }

    public final Date getBirthday() {
        return this.zzoE.getBirthday();
    }

    public final String getContentUrl() {
        return this.zzoE.getContentUrl();
    }

    public final <T extends CustomEvent> Bundle getCustomEventExtrasBundle(Class<T> cls) {
        return this.zzoE.getCustomEventExtrasBundle(cls);
    }

    public final int getGender() {
        return this.zzoE.getGender();
    }

    public final Set<String> getKeywords() {
        return this.zzoE.getKeywords();
    }

    public final Location getLocation() {
        return this.zzoE.getLocation();
    }

    @Deprecated
    public final <T extends NetworkExtras> T getNetworkExtras(Class<T> cls) {
        return this.zzoE.getNetworkExtras(cls);
    }

    public final <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> cls) {
        return this.zzoE.getNetworkExtrasBundle(cls);
    }

    public final boolean isTestDevice(Context context) {
        return this.zzoE.isTestDevice(context);
    }

    public final zzaa zzaE() {
        return this.zzoE;
    }
}
