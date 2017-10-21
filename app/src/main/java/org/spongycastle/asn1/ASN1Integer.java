package org.spongycastle.asn1;

import java.math.BigInteger;

public class ASN1Integer extends DERInteger {
    public ASN1Integer(int i) {
        super(i);
    }

    public ASN1Integer(BigInteger bigInteger) {
        super(bigInteger);
    }

    ASN1Integer(byte[] bArr) {
        super(bArr);
    }
}
