package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class SSL3Mac implements Mac {
    private static final byte IPAD = (byte) 54;
    static final byte[] MD5_IPAD = genPad(IPAD, 48);
    static final byte[] MD5_OPAD = genPad(OPAD, 48);
    private static final byte OPAD = (byte) 92;
    static final byte[] SHA1_IPAD = genPad(IPAD, 40);
    static final byte[] SHA1_OPAD = genPad(OPAD, 40);
    private Digest digest;
    private byte[] ipad;
    private byte[] opad;
    private byte[] secret;

    public SSL3Mac(Digest digest) {
        this.digest = digest;
        if (digest.getDigestSize() == 20) {
            this.ipad = SHA1_IPAD;
            this.opad = SHA1_OPAD;
            return;
        }
        this.ipad = MD5_IPAD;
        this.opad = MD5_OPAD;
    }

    private static byte[] genPad(byte b, int i) {
        byte[] bArr = new byte[i];
        Arrays.fill(bArr, b);
        return bArr;
    }

    public int doFinal(byte[] bArr, int i) {
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        this.digest.update(this.secret, 0, this.secret.length);
        this.digest.update(this.opad, 0, this.opad.length);
        this.digest.update(bArr2, 0, bArr2.length);
        int doFinal = this.digest.doFinal(bArr, i);
        reset();
        return doFinal;
    }

    public String getAlgorithmName() {
        return this.digest.getAlgorithmName() + "/SSL3MAC";
    }

    public int getMacSize() {
        return this.digest.getDigestSize();
    }

    public Digest getUnderlyingDigest() {
        return this.digest;
    }

    public void init(CipherParameters cipherParameters) {
        this.secret = Arrays.clone(((KeyParameter) cipherParameters).getKey());
        reset();
    }

    public void reset() {
        this.digest.reset();
        this.digest.update(this.secret, 0, this.secret.length);
        this.digest.update(this.ipad, 0, this.ipad.length);
    }

    public void update(byte b) {
        this.digest.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.digest.update(bArr, i, i2);
    }
}
