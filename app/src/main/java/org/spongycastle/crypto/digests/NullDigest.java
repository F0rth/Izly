package org.spongycastle.crypto.digests;

import java.io.ByteArrayOutputStream;
import org.spongycastle.crypto.Digest;

public class NullDigest implements Digest {
    private ByteArrayOutputStream bOut = new ByteArrayOutputStream();

    public int doFinal(byte[] bArr, int i) {
        Object toByteArray = this.bOut.toByteArray();
        System.arraycopy(toByteArray, 0, bArr, i, toByteArray.length);
        reset();
        return toByteArray.length;
    }

    public String getAlgorithmName() {
        return "NULL";
    }

    public int getDigestSize() {
        return this.bOut.size();
    }

    public void reset() {
        this.bOut.reset();
    }

    public void update(byte b) {
        this.bOut.write(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.bOut.write(bArr, i, i2);
    }
}
