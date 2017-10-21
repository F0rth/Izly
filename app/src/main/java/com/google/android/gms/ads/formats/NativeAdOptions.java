package com.google.android.gms.ads.formats;

public final class NativeAdOptions {
    public static final int ORIENTATION_ANY = 0;
    public static final int ORIENTATION_LANDSCAPE = 2;
    public static final int ORIENTATION_PORTRAIT = 1;
    private final boolean zzoN;
    private final int zzoO;
    private final boolean zzoP;

    public static final class Builder {
        private boolean zzoN = false;
        private int zzoO = 0;
        private boolean zzoP = false;

        public final NativeAdOptions build() {
            return new NativeAdOptions();
        }

        public final Builder setImageOrientation(int i) {
            this.zzoO = i;
            return this;
        }

        public final Builder setRequestMultipleImages(boolean z) {
            this.zzoP = z;
            return this;
        }

        public final Builder setReturnUrlsForImageAssets(boolean z) {
            this.zzoN = z;
            return this;
        }
    }

    private NativeAdOptions(Builder builder) {
        this.zzoN = builder.zzoN;
        this.zzoO = builder.zzoO;
        this.zzoP = builder.zzoP;
    }

    public final int getImageOrientation() {
        return this.zzoO;
    }

    public final boolean shouldRequestMultipleImages() {
        return this.zzoP;
    }

    public final boolean shouldReturnUrlsForImageAssets() {
        return this.zzoN;
    }
}
