package com.nineoldandroids.animation;

import org.spongycastle.crypto.tls.CipherSuite;

public class ArgbEvaluator implements TypeEvaluator {
    public Object evaluate(float f, Object obj, Object obj2) {
        int intValue = ((Integer) obj).intValue();
        int i = intValue >> 24;
        int i2 = (intValue >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        int i3 = (intValue >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        intValue &= CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        int intValue2 = ((Integer) obj2).intValue();
        return Integer.valueOf((intValue + ((int) (((float) ((intValue2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) - intValue)) * f))) | ((((i + ((int) (((float) ((intValue2 >> 24) - i)) * f))) << 24) | ((i2 + ((int) (((float) (((intValue2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) - i2)) * f))) << 16)) | ((((int) (((float) (((intValue2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) - i3)) * f)) + i3) << 8)));
    }
}
