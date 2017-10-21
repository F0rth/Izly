package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.content.Context;

@TargetApi(23)
public final class g {
    public static int a(Context context, String str) {
        return context.checkSelfPermission(str);
    }

    public static int a(Context context, String... strArr) {
        for (String str : strArr) {
            if (a(context, "android.permission." + str) != 0) {
                return -1;
            }
        }
        return 0;
    }
}
