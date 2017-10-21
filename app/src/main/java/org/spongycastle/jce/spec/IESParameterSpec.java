package org.spongycastle.jce.spec;

import java.security.spec.AlgorithmParameterSpec;

public class IESParameterSpec implements AlgorithmParameterSpec {
    private byte[] derivation;
    private byte[] encoding;
    private int macKeySize;

    public IESParameterSpec(byte[] bArr, byte[] bArr2, int i) {
        this.derivation = new byte[bArr.length];
        System.arraycopy(bArr, 0, this.derivation, 0, bArr.length);
        this.encoding = new byte[bArr2.length];
        System.arraycopy(bArr2, 0, this.encoding, 0, bArr2.length);
        this.macKeySize = i;
    }

    public byte[] getDerivationV() {
        return this.derivation;
    }

    public byte[] getEncodingV() {
        return this.encoding;
    }

    public int getMacKeySize() {
        return this.macKeySize;
    }
}
