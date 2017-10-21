package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.ISO18033KDFParameters;
import org.spongycastle.crypto.params.KDFParameters;

public class BaseKDFBytesGenerator implements DerivationFunction {
    private int counterStart;
    private Digest digest;
    private byte[] iv;
    private byte[] shared;

    protected BaseKDFBytesGenerator(int i, Digest digest) {
        this.counterStart = i;
        this.digest = digest;
    }

    public int generateBytes(byte[] bArr, int i, int i2) throws DataLengthException, IllegalArgumentException {
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        long j = (long) i2;
        int digestSize = this.digest.getDigestSize();
        if (j > 8589934591L) {
            throw new IllegalArgumentException("Output length too large");
        }
        int i3 = (int) (((j + ((long) digestSize)) - 1) / ((long) digestSize));
        Object obj = new byte[this.digest.getDigestSize()];
        int i4 = i2;
        int i5 = this.counterStart;
        int i6 = i;
        for (int i7 = 0; i7 < i3; i7++) {
            this.digest.update(this.shared, 0, this.shared.length);
            this.digest.update((byte) (i5 >> 24));
            this.digest.update((byte) (i5 >> 16));
            this.digest.update((byte) (i5 >> 8));
            this.digest.update((byte) i5);
            if (this.iv != null) {
                this.digest.update(this.iv, 0, this.iv.length);
            }
            this.digest.doFinal(obj, 0);
            if (i4 > digestSize) {
                System.arraycopy(obj, 0, bArr, i6, digestSize);
                i6 += digestSize;
                i4 -= digestSize;
            } else {
                System.arraycopy(obj, 0, bArr, i6, i4);
            }
            i5++;
        }
        this.digest.reset();
        return i4;
    }

    public Digest getDigest() {
        return this.digest;
    }

    public void init(DerivationParameters derivationParameters) {
        if (derivationParameters instanceof KDFParameters) {
            KDFParameters kDFParameters = (KDFParameters) derivationParameters;
            this.shared = kDFParameters.getSharedSecret();
            this.iv = kDFParameters.getIV();
        } else if (derivationParameters instanceof ISO18033KDFParameters) {
            this.shared = ((ISO18033KDFParameters) derivationParameters).getSeed();
            this.iv = null;
        } else {
            throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
        }
    }
}
