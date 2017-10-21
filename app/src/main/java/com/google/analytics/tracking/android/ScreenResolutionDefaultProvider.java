package com.google.analytics.tracking.android;

import android.content.Context;
import android.util.DisplayMetrics;
import com.google.android.gms.common.util.VisibleForTesting;

class ScreenResolutionDefaultProvider implements DefaultProvider {
    private static ScreenResolutionDefaultProvider sInstance;
    private static Object sInstanceLock = new Object();
    private final Context mContext;

    @VisibleForTesting
    protected ScreenResolutionDefaultProvider(Context context) {
        this.mContext = context;
    }

    @VisibleForTesting
    static void dropInstance() {
        synchronized (sInstanceLock) {
            sInstance = null;
        }
    }

    public static ScreenResolutionDefaultProvider getProvider() {
        ScreenResolutionDefaultProvider screenResolutionDefaultProvider;
        synchronized (sInstanceLock) {
            screenResolutionDefaultProvider = sInstance;
        }
        return screenResolutionDefaultProvider;
    }

    public static void initializeProvider(Context context) {
        synchronized (sInstanceLock) {
            if (sInstance == null) {
                sInstance = new ScreenResolutionDefaultProvider(context);
            }
        }
    }

    protected String getScreenResolutionString() {
        DisplayMetrics displayMetrics = this.mContext.getResources().getDisplayMetrics();
        return displayMetrics.widthPixels + "x" + displayMetrics.heightPixels;
    }

    public String getValue(String str) {
        return (str != null && str.equals(Fields.SCREEN_RESOLUTION)) ? getScreenResolutionString() : null;
    }

    public boolean providesField(String str) {
        return Fields.SCREEN_RESOLUTION.equals(str);
    }
}
