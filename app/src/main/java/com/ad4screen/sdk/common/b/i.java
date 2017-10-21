package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.os.HandlerThread;

@TargetApi(5)
public final class i {
    public static boolean a(HandlerThread handlerThread) {
        return handlerThread.quit();
    }
}
