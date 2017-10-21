package com.google.android.gms.ads.search;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.internal.client.zzaa;
import com.google.android.gms.ads.internal.client.zzaa.zza;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;

public final class SearchAdRequest {
    public static final int BORDER_TYPE_DASHED = 1;
    public static final int BORDER_TYPE_DOTTED = 2;
    public static final int BORDER_TYPE_NONE = 0;
    public static final int BORDER_TYPE_SOLID = 3;
    public static final int CALL_BUTTON_COLOR_DARK = 2;
    public static final int CALL_BUTTON_COLOR_LIGHT = 0;
    public static final int CALL_BUTTON_COLOR_MEDIUM = 1;
    public static final String DEVICE_ID_EMULATOR = zzaa.DEVICE_ID_EMULATOR;
    public static final int ERROR_CODE_INTERNAL_ERROR = 0;
    public static final int ERROR_CODE_INVALID_REQUEST = 1;
    public static final int ERROR_CODE_NETWORK_ERROR = 2;
    public static final int ERROR_CODE_NO_FILL = 3;
    private final int zzOA;
    private final int zzOB;
    private final int zzOC;
    private final int zzOD;
    private final String zzOE;
    private final int zzOF;
    private final String zzOG;
    private final int zzOH;
    private final int zzOI;
    private final String zzOJ;
    private final int zzOx;
    private final int zzOy;
    private final int zzOz;
    private final zzaa zzoE;
    private final int zzxO;

    public static final class Builder {
        private int zzOA;
        private int zzOB;
        private int zzOC = 0;
        private int zzOD;
        private String zzOE;
        private int zzOF;
        private String zzOG;
        private int zzOH;
        private int zzOI;
        private String zzOJ;
        private int zzOx;
        private int zzOy;
        private int zzOz;
        private final zza zzoF = new zza();
        private int zzxO;

        public final Builder addCustomEventExtrasBundle(Class<? extends CustomEvent> cls, Bundle bundle) {
            this.zzoF.zzb(cls, bundle);
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

        public final SearchAdRequest build() {
            return new SearchAdRequest();
        }

        public final Builder setAnchorTextColor(int i) {
            this.zzOx = i;
            return this;
        }

        public final Builder setBackgroundColor(int i) {
            this.zzxO = i;
            this.zzOy = Color.argb(0, 0, 0, 0);
            this.zzOz = Color.argb(0, 0, 0, 0);
            return this;
        }

        public final Builder setBackgroundGradient(int i, int i2) {
            this.zzxO = Color.argb(0, 0, 0, 0);
            this.zzOy = i2;
            this.zzOz = i;
            return this;
        }

        public final Builder setBorderColor(int i) {
            this.zzOA = i;
            return this;
        }

        public final Builder setBorderThickness(int i) {
            this.zzOB = i;
            return this;
        }

        public final Builder setBorderType(int i) {
            this.zzOC = i;
            return this;
        }

        public final Builder setCallButtonColor(int i) {
            this.zzOD = i;
            return this;
        }

        public final Builder setCustomChannels(String str) {
            this.zzOE = str;
            return this;
        }

        public final Builder setDescriptionTextColor(int i) {
            this.zzOF = i;
            return this;
        }

        public final Builder setFontFace(String str) {
            this.zzOG = str;
            return this;
        }

        public final Builder setHeaderTextColor(int i) {
            this.zzOH = i;
            return this;
        }

        public final Builder setHeaderTextSize(int i) {
            this.zzOI = i;
            return this;
        }

        public final Builder setLocation(Location location) {
            this.zzoF.zzb(location);
            return this;
        }

        public final Builder setQuery(String str) {
            this.zzOJ = str;
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

    private SearchAdRequest(Builder builder) {
        this.zzOx = builder.zzOx;
        this.zzxO = builder.zzxO;
        this.zzOy = builder.zzOy;
        this.zzOz = builder.zzOz;
        this.zzOA = builder.zzOA;
        this.zzOB = builder.zzOB;
        this.zzOC = builder.zzOC;
        this.zzOD = builder.zzOD;
        this.zzOE = builder.zzOE;
        this.zzOF = builder.zzOF;
        this.zzOG = builder.zzOG;
        this.zzOH = builder.zzOH;
        this.zzOI = builder.zzOI;
        this.zzOJ = builder.zzOJ;
        this.zzoE = new zzaa(builder.zzoF, this);
    }

    public final int getAnchorTextColor() {
        return this.zzOx;
    }

    public final int getBackgroundColor() {
        return this.zzxO;
    }

    public final int getBackgroundGradientBottom() {
        return this.zzOy;
    }

    public final int getBackgroundGradientTop() {
        return this.zzOz;
    }

    public final int getBorderColor() {
        return this.zzOA;
    }

    public final int getBorderThickness() {
        return this.zzOB;
    }

    public final int getBorderType() {
        return this.zzOC;
    }

    public final int getCallButtonColor() {
        return this.zzOD;
    }

    public final String getCustomChannels() {
        return this.zzOE;
    }

    public final <T extends CustomEvent> Bundle getCustomEventExtrasBundle(Class<T> cls) {
        return this.zzoE.getCustomEventExtrasBundle(cls);
    }

    public final int getDescriptionTextColor() {
        return this.zzOF;
    }

    public final String getFontFace() {
        return this.zzOG;
    }

    public final int getHeaderTextColor() {
        return this.zzOH;
    }

    public final int getHeaderTextSize() {
        return this.zzOI;
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

    public final String getQuery() {
        return this.zzOJ;
    }

    public final boolean isTestDevice(Context context) {
        return this.zzoE.isTestDevice(context);
    }

    final zzaa zzaE() {
        return this.zzoE;
    }
}
