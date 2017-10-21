package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;

public class X9IntegerConverter {
    public int getByteLength(ECCurve eCCurve) {
        return (eCCurve.getFieldSize() + 7) / 8;
    }

    public int getByteLength(ECFieldElement eCFieldElement) {
        return (eCFieldElement.getFieldSize() + 7) / 8;
    }

    public byte[] integerToBytes(BigInteger bigInteger, int i) {
        Object toByteArray = bigInteger.toByteArray();
        Object obj;
        if (i < toByteArray.length) {
            obj = new byte[i];
            System.arraycopy(toByteArray, toByteArray.length - i, obj, 0, i);
            return obj;
        } else if (i <= toByteArray.length) {
            return toByteArray;
        } else {
            obj = new byte[i];
            System.arraycopy(toByteArray, 0, obj, i - toByteArray.length, toByteArray.length);
            return obj;
        }
    }
}
