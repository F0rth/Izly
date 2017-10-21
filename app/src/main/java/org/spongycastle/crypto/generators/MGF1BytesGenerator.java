package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.MGFParameters;

public class MGF1BytesGenerator implements DerivationFunction {
    private Digest digest;
    private int hLen;
    private byte[] seed;

    public MGF1BytesGenerator(Digest digest) {
        this.digest = digest;
        this.hLen = digest.getDigestSize();
    }

    private void ItoOSP(int i, byte[] bArr) {
        bArr[0] = (byte) (i >>> 24);
        bArr[1] = (byte) (i >>> 16);
        bArr[2] = (byte) (i >>> 8);
        bArr[3] = (byte) (i >>> 0);
    }

    public int generateBytes(byte[] bArr, int i, int i2) throws DataLengthException, IllegalArgumentException {
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        int i3;
        Object obj = new byte[this.hLen];
        byte[] bArr2 = new byte[4];
        this.digest.reset();
        if (i2 > this.hLen) {
            i3 = 0;
            do {
                ItoOSP(i3, bArr2);
                this.digest.update(this.seed, 0, this.seed.length);
                this.digest.update(bArr2, 0, 4);
                this.digest.doFinal(obj, 0);
                System.arraycopy(obj, 0, bArr, (this.hLen * i3) + i, this.hLen);
                i3++;
            } while (i3 < i2 / this.hLen);
        } else {
            i3 = 0;
        }
        if (this.hLen * i3 < i2) {
            ItoOSP(i3, bArr2);
            this.digest.update(this.seed, 0, this.seed.length);
            this.digest.update(bArr2, 0, 4);
            this.digest.doFinal(obj, 0);
            System.arraycopy(obj, 0, bArr, (this.hLen * i3) + i, i2 - (i3 * this.hLen));
        }
        return i2;
    }

    public Digest getDigest() {
        return this.digest;
    }

    public void init(DerivationParameters derivationParameters) {
        if (derivationParameters instanceof MGFParameters) {
            this.seed = ((MGFParameters) derivationParameters).getSeed();
            return;
        }
        throw new IllegalArgumentException("MGF parameters required for MGF1Generator");
    }
}
