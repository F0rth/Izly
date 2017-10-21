package com.google.android.gms.ads;

import android.content.Context;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzn;

public final class AdSize {
    public static final int AUTO_HEIGHT = -2;
    public static final AdSize BANNER = new AdSize(320, 50, "320x50_mb");
    public static final AdSize FLUID = new AdSize(-3, -4, "fluid");
    public static final AdSize FULL_BANNER = new AdSize(468, 60, "468x60_as");
    public static final int FULL_WIDTH = -1;
    public static final AdSize LARGE_BANNER = new AdSize(320, 100, "320x100_as");
    public static final AdSize LEADERBOARD = new AdSize(728, 90, "728x90_as");
    public static final AdSize MEDIUM_RECTANGLE = new AdSize(300, Callback.DEFAULT_SWIPE_ANIMATION_DURATION, "300x250_as");
    public static final AdSize SMART_BANNER = new AdSize(-1, -2, "smart_banner");
    public static final AdSize WIDE_SKYSCRAPER = new AdSize(160, 600, "160x600_as");
    private final int zzoG;
    private final int zzoH;
    private final String zzoI;

    public AdSize(int i, int i2) {
        this(i, i2, (i == -1 ? "FULL" : String.valueOf(i)) + "x" + (i2 == -2 ? "AUTO" : String.valueOf(i2)) + "_as");
    }

    AdSize(int i, int i2, String str) {
        if (i < 0 && i != -1 && i != -3) {
            throw new IllegalArgumentException("Invalid width for AdSize: " + i);
        } else if (i2 >= 0 || i2 == -2 || i2 == -4) {
            this.zzoG = i;
            this.zzoH = i2;
            this.zzoI = str;
        } else {
            throw new IllegalArgumentException("Invalid height for AdSize: " + i2);
        }
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof AdSize)) {
                return false;
            }
            AdSize adSize = (AdSize) obj;
            if (this.zzoG != adSize.zzoG || this.zzoH != adSize.zzoH) {
                return false;
            }
            if (!this.zzoI.equals(adSize.zzoI)) {
                return false;
            }
        }
        return true;
    }

    public final int getHeight() {
        return this.zzoH;
    }

    public final int getHeightInPixels(Context context) {
        switch (this.zzoH) {
            case -4:
            case -3:
                return -1;
            case -2:
                return AdSizeParcel.zzb(context.getResources().getDisplayMetrics());
            default:
                return zzn.zzcS().zzb(context, this.zzoH);
        }
    }

    public final int getWidth() {
        return this.zzoG;
    }

    public final int getWidthInPixels(Context context) {
        switch (this.zzoG) {
            case -4:
            case -3:
                return -1;
            case -1:
                return AdSizeParcel.zza(context.getResources().getDisplayMetrics());
            default:
                return zzn.zzcS().zzb(context, this.zzoG);
        }
    }

    public final int hashCode() {
        return this.zzoI.hashCode();
    }

    public final boolean isAutoHeight() {
        return this.zzoH == -2;
    }

    public final boolean isFluid() {
        return this.zzoG == -3 && this.zzoH == -4;
    }

    public final boolean isFullWidth() {
        return this.zzoG == -1;
    }

    public final String toString() {
        return this.zzoI;
    }
}
