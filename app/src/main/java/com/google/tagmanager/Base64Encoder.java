package com.google.tagmanager;

import android.os.Build.VERSION;
import android.util.Base64;
import com.google.android.gms.common.util.VisibleForTesting;

class Base64Encoder {
    public static final int DEFAULT = 0;
    public static final int NO_PADDING = 1;
    public static final int URL_SAFE = 2;

    Base64Encoder() {
    }

    public static byte[] decode(String str, int i) {
        if (getSdkVersion() >= 8) {
            int i2 = 2;
            if ((i & 1) != 0) {
                i2 = 3;
            }
            if ((i & 2) != 0) {
                i2 |= 8;
            }
            return Base64.decode(str, i2);
        }
        return ((i & 2) != 0 ? 1 : null) != null ? Base64.decodeWebSafe(str) : Base64.decode(str);
    }

    public static String encodeToString(byte[] bArr, int i) {
        Object obj = 1;
        if (getSdkVersion() >= 8) {
            int i2 = 2;
            if ((i & 1) != 0) {
                i2 = 3;
            }
            if ((i & 2) != 0) {
                i2 |= 8;
            }
            return Base64.encodeToString(bArr, i2);
        }
        boolean z = (i & 1) == 0;
        if ((i & 2) == 0) {
            obj = null;
        }
        return obj != null ? Base64.encodeWebSafe(bArr, z) : Base64.encode(bArr, z);
    }

    @VisibleForTesting
    static int getSdkVersion() {
        return VERSION.SDK_INT;
    }
}
