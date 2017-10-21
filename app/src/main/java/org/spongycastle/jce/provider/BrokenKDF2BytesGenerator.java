package org.spongycastle.jce.provider;

import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.DerivationFunction;
import org.spongycastle.crypto.DerivationParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.KDFParameters;
import org.spongycastle.crypto.tls.CipherSuite;

public class BrokenKDF2BytesGenerator implements DerivationFunction {
    private Digest digest;
    private byte[] iv;
    private byte[] shared;

    public BrokenKDF2BytesGenerator(Digest digest) {
        this.digest = digest;
    }

    public int generateBytes(byte[] bArr, int i, int i2) throws DataLengthException, IllegalArgumentException {
        if (bArr.length - i2 < i) {
            throw new DataLengthException("output buffer too small");
        }
        long j = (long) (i2 * 8);
        if (j > ((long) (this.digest.getDigestSize() * 8)) * 29) {
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Output length to large");
        }
        int digestSize = (int) (j / ((long) this.digest.getDigestSize()));
        Object obj = new byte[this.digest.getDigestSize()];
        int i3 = i;
        for (int i4 = 1; i4 <= digestSize; i4++) {
            this.digest.update(this.shared, 0, this.shared.length);
            this.digest.update((byte) (i4 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
            this.digest.update((byte) ((i4 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
            this.digest.update((byte) ((i4 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
            this.digest.update((byte) ((i4 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
            this.digest.update(this.iv, 0, this.iv.length);
            this.digest.doFinal(obj, 0);
            if (i2 - i3 > obj.length) {
                System.arraycopy(obj, 0, bArr, i3, obj.length);
                i3 += obj.length;
            } else {
                System.arraycopy(obj, 0, bArr, i3, i2 - i3);
            }
        }
        this.digest.reset();
        return i2;
    }

    public Digest getDigest() {
        return this.digest;
    }

    public void init(DerivationParameters derivationParameters) {
        if (derivationParameters instanceof KDFParameters) {
            KDFParameters kDFParameters = (KDFParameters) derivationParameters;
            this.shared = kDFParameters.getSharedSecret();
            this.iv = kDFParameters.getIV();
            return;
        }
        throw new IllegalArgumentException("KDF parameters required for KDF2Generator");
    }
}
