package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.content.pm.PackageInfo;

@TargetApi(9)
public final class l {
    public static long a(PackageInfo packageInfo) {
        return packageInfo.firstInstallTime;
    }
}
