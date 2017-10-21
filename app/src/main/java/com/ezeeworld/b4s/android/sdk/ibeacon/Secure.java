package com.ezeeworld.b4s.android.sdk.ibeacon;

public final class Secure {
    static {
        System.loadLibrary("sec");
    }

    public final native byte[] computeCommand(String str, byte[] bArr, char c, char c2, byte[] bArr2);
}
