package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.crypto.util.Pack;

public class SHA1Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 20;
    private static final int Y1 = 1518500249;
    private static final int Y2 = 1859775393;
    private static final int Y3 = -1894007588;
    private static final int Y4 = -899497514;
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int[] X;
    private int xOff;

    public SHA1Digest() {
        this.X = new int[80];
        reset();
    }

    public SHA1Digest(SHA1Digest sHA1Digest) {
        super(sHA1Digest);
        this.X = new int[80];
        this.H1 = sHA1Digest.H1;
        this.H2 = sHA1Digest.H2;
        this.H3 = sHA1Digest.H3;
        this.H4 = sHA1Digest.H4;
        this.H5 = sHA1Digest.H5;
        System.arraycopy(sHA1Digest.X, 0, this.X, 0, sHA1Digest.X.length);
        this.xOff = sHA1Digest.xOff;
    }

    private int f(int i, int i2, int i3) {
        return (i & i2) | ((i ^ -1) & i3);
    }

    private int g(int i, int i2, int i3) {
        return ((i & i2) | (i & i3)) | (i2 & i3);
    }

    private int h(int i, int i2, int i3) {
        return (i ^ i2) ^ i3;
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i);
        Pack.intToBigEndian(this.H2, bArr, i + 4);
        Pack.intToBigEndian(this.H3, bArr, i + 8);
        Pack.intToBigEndian(this.H4, bArr, i + 12);
        Pack.intToBigEndian(this.H5, bArr, i + 16);
        reset();
        return 20;
    }

    public String getAlgorithmName() {
        return "SHA-1";
    }

    public int getDigestSize() {
        return 20;
    }

    protected void processBlock() {
        int i;
        int i2;
        for (i = 16; i < 80; i++) {
            i2 = ((this.X[i - 3] ^ this.X[i - 8]) ^ this.X[i - 14]) ^ this.X[i - 16];
            this.X[i] = (i2 << 1) | (i2 >>> 31);
        }
        int i3 = this.H1;
        i2 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int i6 = this.H5;
        int i7 = 0;
        for (i = 0; i < 4; i++) {
            int i8 = i7 + 1;
            i6 += (this.X[i7] + (f(i2, i4, i5) + ((i3 << 5) | (i3 >>> 27)))) + Y1;
            i2 = (i2 << 30) | (i2 >>> 2);
            int i9 = i8 + 1;
            i7 = (((f(i3, i2, i4) + ((i6 << 5) | (i6 >>> 27))) + this.X[i8]) + Y1) + i5;
            i3 = (i3 << 30) | (i3 >>> 2);
            i8 = i9 + 1;
            i4 += ((f(i6, i3, i2) + ((i7 << 5) | (i7 >>> 27))) + this.X[i9]) + Y1;
            i6 = (i6 >>> 2) | (i6 << 30);
            i9 = i8 + 1;
            i2 += ((f(i7, i6, i3) + ((i4 << 5) | (i4 >>> 27))) + this.X[i8]) + Y1;
            i5 = (i7 >>> 2) | (i7 << 30);
            i7 = i9 + 1;
            i3 += ((f(i4, i5, i6) + ((i2 << 5) | (i2 >>> 27))) + this.X[i9]) + Y1;
            i4 = (i4 << 30) | (i4 >>> 2);
        }
        for (i = 0; i < 4; i++) {
            i8 = i7 + 1;
            i6 += (this.X[i7] + (h(i2, i4, i5) + ((i3 << 5) | (i3 >>> 27)))) + Y2;
            i2 = (i2 << 30) | (i2 >>> 2);
            i9 = i8 + 1;
            i7 = (((h(i3, i2, i4) + ((i6 << 5) | (i6 >>> 27))) + this.X[i8]) + Y2) + i5;
            i3 = (i3 << 30) | (i3 >>> 2);
            i8 = i9 + 1;
            i4 += ((h(i6, i3, i2) + ((i7 << 5) | (i7 >>> 27))) + this.X[i9]) + Y2;
            i6 = (i6 >>> 2) | (i6 << 30);
            i9 = i8 + 1;
            i2 += ((h(i7, i6, i3) + ((i4 << 5) | (i4 >>> 27))) + this.X[i8]) + Y2;
            i5 = (i7 >>> 2) | (i7 << 30);
            i7 = i9 + 1;
            i3 += ((h(i4, i5, i6) + ((i2 << 5) | (i2 >>> 27))) + this.X[i9]) + Y2;
            i4 = (i4 << 30) | (i4 >>> 2);
        }
        i = i3;
        i3 = i4;
        i4 = 0;
        while (i4 < 4) {
            i8 = i7 + 1;
            i6 += (this.X[i7] + (g(i2, i3, i5) + ((i << 5) | (i >>> 27)))) - 1894007588;
            i2 = (i2 << 30) | (i2 >>> 2);
            i9 = i8 + 1;
            i7 = (((g(i, i2, i3) + ((i6 << 5) | (i6 >>> 27))) + this.X[i8]) - 1894007588) + i5;
            i = (i << 30) | (i >>> 2);
            i8 = i9 + 1;
            int g = i3 + (((g(i6, i, i2) + ((i7 << 5) | (i7 >>> 27))) + this.X[i9]) - 1894007588);
            i6 = (i6 >>> 2) | (i6 << 30);
            i9 = i8 + 1;
            i2 += ((g(i7, i6, i) + ((g << 5) | (g >>> 27))) + this.X[i8]) - 1894007588;
            i5 = (i7 >>> 2) | (i7 << 30);
            i7 = i9 + 1;
            i3 = (((g(g, i5, i6) + ((i2 << 5) | (i2 >>> 27))) + this.X[i9]) - 1894007588) + i;
            g = (g << 30) | (g >>> 2);
            i4++;
            i = i3;
            i3 = g;
        }
        for (i4 = 0; i4 <= 3; i4++) {
            i8 = i7 + 1;
            i6 += (this.X[i7] + (h(i2, i3, i5) + ((i << 5) | (i >>> 27)))) - 899497514;
            i2 = (i2 << 30) | (i2 >>> 2);
            i9 = i8 + 1;
            i7 = (((h(i, i2, i3) + ((i6 << 5) | (i6 >>> 27))) + this.X[i8]) - 899497514) + i5;
            i = (i << 30) | (i >>> 2);
            i8 = i9 + 1;
            i3 += ((h(i6, i, i2) + ((i7 << 5) | (i7 >>> 27))) + this.X[i9]) - 899497514;
            i6 = (i6 >>> 2) | (i6 << 30);
            i9 = i8 + 1;
            i2 += ((h(i7, i6, i) + ((i3 << 5) | (i3 >>> 27))) + this.X[i8]) - 899497514;
            i5 = (i7 >>> 2) | (i7 << 30);
            i7 = i9 + 1;
            i += ((h(i3, i5, i6) + ((i2 << 5) | (i2 >>> 27))) + this.X[i9]) - 899497514;
            i3 = (i3 << 30) | (i3 >>> 2);
        }
        this.H1 = i + this.H1;
        this.H2 += i2;
        this.H3 += i3;
        this.H4 += i5;
        this.H5 += i6;
        this.xOff = 0;
        for (i = 0; i < 16; i++) {
            this.X[i] = 0;
        }
    }

    protected void processLength(long j) {
        if (this.xOff > 14) {
            processBlock();
        }
        this.X[14] = (int) (j >>> 32);
        this.X[15] = (int) (-1 & j);
    }

    protected void processWord(byte[] bArr, int i) {
        byte b = bArr[i];
        int i2 = i + 1;
        byte b2 = bArr[i2];
        i2++;
        this.X[this.xOff] = (((b << 24) | ((b2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i2 + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i3 = this.xOff + 1;
        this.xOff = i3;
        if (i3 == 16) {
            processBlock();
        }
    }

    public void reset() {
        super.reset();
        this.H1 = 1732584193;
        this.H2 = -271733879;
        this.H3 = -1732584194;
        this.H4 = 271733878;
        this.H5 = -1009589776;
        this.xOff = 0;
        for (int i = 0; i != this.X.length; i++) {
            this.X[i] = 0;
        }
    }
}
