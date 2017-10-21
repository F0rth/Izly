package org.spongycastle.jce.provider;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;
import org.spongycastle.crypto.tls.CipherSuite;

class OldPKCS12ParametersGenerator extends PBEParametersGenerator {
    public static final int IV_MATERIAL = 2;
    public static final int KEY_MATERIAL = 1;
    public static final int MAC_MATERIAL = 3;
    private Digest digest;
    private int u;
    private int v;

    public OldPKCS12ParametersGenerator(Digest digest) {
        this.digest = digest;
        if (digest instanceof MD5Digest) {
            this.u = 16;
            this.v = 64;
        } else if (digest instanceof SHA1Digest) {
            this.u = 20;
            this.v = 64;
        } else if (digest instanceof RIPEMD160Digest) {
            this.u = 20;
            this.v = 64;
        } else {
            throw new IllegalArgumentException("Digest " + digest.getAlgorithmName() + " unsupported");
        }
    }

    private void adjust(byte[] bArr, int i, byte[] bArr2) {
        int i2 = ((bArr2[bArr2.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + (bArr[(bArr2.length + i) - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV)) + 1;
        bArr[(bArr2.length + i) - 1] = (byte) i2;
        i2 >>>= 8;
        for (int length = bArr2.length - 2; length >= 0; length--) {
            i2 += (bArr2[length] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) + (bArr[i + length] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            bArr[i + length] = (byte) i2;
            i2 >>>= 8;
        }
    }

    private byte[] generateDerivedKey(int i, int i2) {
        int i3;
        Object obj;
        Object obj2;
        Object obj3;
        int i4;
        byte[] bArr = new byte[this.v];
        Object obj4 = new byte[i2];
        for (i3 = 0; i3 != bArr.length; i3++) {
            bArr[i3] = (byte) i;
        }
        if (this.salt == null || this.salt.length == 0) {
            obj = new byte[0];
        } else {
            obj2 = new byte[(this.v * (((this.salt.length + this.v) - 1) / this.v))];
            for (i3 = 0; i3 != obj2.length; i3++) {
                obj2[i3] = this.salt[i3 % this.salt.length];
            }
            obj = obj2;
        }
        if (this.password == null || this.password.length == 0) {
            obj2 = new byte[0];
        } else {
            obj3 = new byte[(this.v * (((this.password.length + this.v) - 1) / this.v))];
            for (i4 = 0; i4 != obj3.length; i4++) {
                obj3[i4] = this.password[i4 % this.password.length];
            }
            obj2 = obj3;
        }
        obj3 = new byte[(obj.length + obj2.length)];
        System.arraycopy(obj, 0, obj3, 0, obj.length);
        System.arraycopy(obj2, 0, obj3, obj.length, obj2.length);
        byte[] bArr2 = new byte[this.v];
        int i5 = ((this.u + i2) - 1) / this.u;
        for (i4 = 1; i4 <= i5; i4++) {
            Object obj5 = new byte[this.u];
            this.digest.update(bArr, 0, bArr.length);
            this.digest.update(obj3, 0, obj3.length);
            this.digest.doFinal(obj5, 0);
            for (i3 = 1; i3 != this.iterationCount; i3++) {
                this.digest.update(obj5, 0, obj5.length);
                this.digest.doFinal(obj5, 0);
            }
            for (i3 = 0; i3 != bArr2.length; i3++) {
                bArr2[i4] = obj5[i3 % obj5.length];
            }
            for (i3 = 0; i3 != obj3.length / this.v; i3++) {
                adjust(obj3, this.v * i3, bArr2);
            }
            if (i4 == i5) {
                System.arraycopy(obj5, 0, obj4, (i4 - 1) * this.u, i2 - ((i4 - 1) * this.u));
            } else {
                System.arraycopy(obj5, 0, obj4, (i4 - 1) * this.u, obj5.length);
            }
        }
        return obj4;
    }

    public CipherParameters generateDerivedMacParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(generateDerivedKey(3, i2), 0, i2);
    }

    public CipherParameters generateDerivedParameters(int i) {
        int i2 = i / 8;
        return new KeyParameter(generateDerivedKey(1, i2), 0, i2);
    }

    public CipherParameters generateDerivedParameters(int i, int i2) {
        int i3 = i / 8;
        int i4 = i2 / 8;
        byte[] generateDerivedKey = generateDerivedKey(1, i3);
        return new ParametersWithIV(new KeyParameter(generateDerivedKey, 0, i3), generateDerivedKey(2, i4), 0, i4);
    }
}
