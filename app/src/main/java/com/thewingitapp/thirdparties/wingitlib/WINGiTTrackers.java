package com.thewingitapp.thirdparties.wingitlib;

import java.util.ArrayList;
import java.util.List;

public class WINGiTTrackers {
    private static List<String> mDisplayTrackers = new ArrayList();

    public static void addDisplayTracker(String str) {
        if (str != null && !str.isEmpty() && !mDisplayTrackers.contains(str)) {
            mDisplayTrackers.add(str);
        }
    }

    public static void clearAllDisplayTrackers() {
        mDisplayTrackers.clear();
    }

    public static boolean isDisplayTrackerShown(String str) {
        return mDisplayTrackers.contains(str);
    }
}
