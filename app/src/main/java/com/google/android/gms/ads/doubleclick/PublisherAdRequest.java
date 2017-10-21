package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.internal.client.zzaa;
import com.google.android.gms.ads.internal.client.zzaa.zza;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.common.internal.zzv;
import com.google.android.gms.common.internal.zzx;
import java.util.Date;
import java.util.List;
import java.util.Set;

public final class PublisherAdRequest {
    public static final String DEVICE_ID_EMULATOR = zzaa.DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    public static final int GENDER_FEMALE = 2;
    public static final int GENDER_MALE = 1;
    public static final int GENDER_UNKNOWN = 0;
    private final zzaa zzoE;

    public static final class Builder {
        private final zza zzoF = new zza();

        public final Builder addCategoryExclusion(String str) {
            this.zzoF.zzG(str);
            return this;
        }

        public final Builder addCustomEventExtrasBundle(Class<? extends CustomEvent> cls, Bundle bundle) {
            this.zzoF.zzb(cls, bundle);
            return this;
        }

        public final Builder addCustomTargeting(String str, String str2) {
            this.zzoF.zza(str, str2);
            return this;
        }

        public final Builder addCustomTargeting(String str, List<String> list) {
            if (list != null) {
                this.zzoF.zza(str, zzv.zzcL(",").zza(list));
            }
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
            return this;
        }

        public final Builder addTestDevice(String str) {
            this.zzoF.zzB(str);
            return this;
        }

        public final PublisherAdRequest build() {
            return new PublisherAdRequest();
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

        @Deprecated
        public final Builder setManualImpressionsEnabled(boolean z) {
            this.zzoF.setManualImpressionsEnabled(z);
            return this;
        }

        public final Builder setPublisherProvidedId(String str) {
            this.zzoF.zzE(str);
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

    private PublisherAdRequest(Builder builder) {
        this.zzoE = new zzaa(builder.zzoF);
    }

    public static void updateCorrelator() {
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

    public final Bundle getCustomTargeting() {
        return this.zzoE.getCustomTargeting();
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

    public final boolean getManualImpressionsEnabled() {
        return this.zzoE.getManualImpressionsEnabled();
    }

    @Deprecated
    public final <T extends NetworkExtras> T getNetworkExtras(Class<T> cls) {
        return this.zzoE.getNetworkExtras(cls);
    }

    public final <T extends MediationAdapter> Bundle getNetworkExtrasBundle(Class<T> cls) {
        return this.zzoE.getNetworkExtrasBundle(cls);
    }

    public final String getPublisherProvidedId() {
        return this.zzoE.getPublisherProvidedId();
    }

    public final boolean isTestDevice(Context context) {
        return this.zzoE.isTestDevice(context);
    }

    public final zzaa zzaE() {
        return this.zzoE;
    }
}
