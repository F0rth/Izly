package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

class CombinedHash implements Digest {
    protected TlsClientContext context;
    protected MD5Digest md5;
    protected SHA1Digest sha1;

    CombinedHash() {
        this.md5 = new MD5Digest();
        this.sha1 = new SHA1Digest();
    }

    CombinedHash(CombinedHash combinedHash) {
        this.context = combinedHash.context;
        this.md5 = new MD5Digest(combinedHash.md5);
        this.sha1 = new SHA1Digest(combinedHash.sha1);
    }

    CombinedHash(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
        this.md5 = new MD5Digest();
        this.sha1 = new SHA1Digest();
    }

    public int doFinal(byte[] bArr, int i) {
        if (this.context != null) {
            if ((this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : null) == null) {
                ssl3Complete(this.md5, SSL3Mac.MD5_IPAD, SSL3Mac.MD5_OPAD);
                ssl3Complete(this.sha1, SSL3Mac.SHA1_IPAD, SSL3Mac.SHA1_OPAD);
            }
        }
        return this.md5.doFinal(bArr, i) + this.sha1.doFinal(bArr, i + 16);
    }

    public String getAlgorithmName() {
        return this.md5.getAlgorithmName() + " and " + this.sha1.getAlgorithmName();
    }

    public int getDigestSize() {
        return 36;
    }

    public void reset() {
        this.md5.reset();
        this.sha1.reset();
    }

    protected void ssl3Complete(Digest digest, byte[] bArr, byte[] bArr2) {
        byte[] bArr3 = this.context.getSecurityParameters().masterSecret;
        digest.update(bArr3, 0, bArr3.length);
        digest.update(bArr, 0, bArr.length);
        byte[] bArr4 = new byte[digest.getDigestSize()];
        digest.doFinal(bArr4, 0);
        digest.update(bArr3, 0, bArr3.length);
        digest.update(bArr2, 0, bArr2.length);
        digest.update(bArr4, 0, bArr4.length);
    }

    public void update(byte b) {
        this.md5.update(b);
        this.sha1.update(b);
    }

    public void update(byte[] bArr, int i, int i2) {
        this.md5.update(bArr, i, i2);
        this.sha1.update(bArr, i, i2);
    }
}
