package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.crypto.util.Pack;

public class SHA224Digest extends GeneralDigest {
    private static final int DIGEST_LENGTH = 28;
    static final int[] K = new int[]{1116352408, 1899447441, -1245643825, -373957723, 961987163, 1508970993, -1841331548, -1424204075, -670586216, 310598401, 607225278, 1426881987, 1925078388, -2132889090, -1680079193, -1046744716, -459576895, -272742522, 264347078, 604807628, 770255983, 1249150122, 1555081692, 1996064986, -1740746414, -1473132947, -1341970488, -1084653625, -958395405, -710438585, 113926993, 338241895, 666307205, 773529912, 1294757372, 1396182291, 1695183700, 1986661051, -2117940946, -1838011259, -1564481375, -1474664885, -1035236496, -949202525, -778901479, -694614492, -200395387, 275423344, 430227734, 506948616, 659060556, 883997877, 958139571, 1322822218, 1537002063, 1747873779, 1955562222, 2024104815, -2067236844, -1933114872, -1866530822, -1538233109, -1090935817, -965641998};
    private int H1;
    private int H2;
    private int H3;
    private int H4;
    private int H5;
    private int H6;
    private int H7;
    private int H8;
    private int[] X;
    private int xOff;

    public SHA224Digest() {
        this.X = new int[64];
        reset();
    }

    public SHA224Digest(SHA224Digest sHA224Digest) {
        super(sHA224Digest);
        this.X = new int[64];
        this.H1 = sHA224Digest.H1;
        this.H2 = sHA224Digest.H2;
        this.H3 = sHA224Digest.H3;
        this.H4 = sHA224Digest.H4;
        this.H5 = sHA224Digest.H5;
        this.H6 = sHA224Digest.H6;
        this.H7 = sHA224Digest.H7;
        this.H8 = sHA224Digest.H8;
        System.arraycopy(sHA224Digest.X, 0, this.X, 0, sHA224Digest.X.length);
        this.xOff = sHA224Digest.xOff;
    }

    private int Ch(int i, int i2, int i3) {
        return (i & i2) ^ ((i ^ -1) & i3);
    }

    private int Maj(int i, int i2, int i3) {
        return ((i & i2) ^ (i & i3)) ^ (i2 & i3);
    }

    private int Sum0(int i) {
        return (((i >>> 2) | (i << 30)) ^ ((i >>> 13) | (i << 19))) ^ ((i >>> 22) | (i << 10));
    }

    private int Sum1(int i) {
        return (((i >>> 6) | (i << 26)) ^ ((i >>> 11) | (i << 21))) ^ ((i >>> 25) | (i << 7));
    }

    private int Theta0(int i) {
        return (((i >>> 7) | (i << 25)) ^ ((i >>> 18) | (i << 14))) ^ (i >>> 3);
    }

    private int Theta1(int i) {
        return (((i >>> 17) | (i << 15)) ^ ((i >>> 19) | (i << 13))) ^ (i >>> 10);
    }

    public int doFinal(byte[] bArr, int i) {
        finish();
        Pack.intToBigEndian(this.H1, bArr, i);
        Pack.intToBigEndian(this.H2, bArr, i + 4);
        Pack.intToBigEndian(this.H3, bArr, i + 8);
        Pack.intToBigEndian(this.H4, bArr, i + 12);
        Pack.intToBigEndian(this.H5, bArr, i + 16);
        Pack.intToBigEndian(this.H6, bArr, i + 20);
        Pack.intToBigEndian(this.H7, bArr, i + 24);
        reset();
        return 28;
    }

    public String getAlgorithmName() {
        return "SHA-224";
    }

    public int getDigestSize() {
        return 28;
    }

    protected void processBlock() {
        int i;
        for (i = 16; i <= 63; i++) {
            this.X[i] = ((Theta1(this.X[i - 2]) + this.X[i - 7]) + Theta0(this.X[i - 15])) + this.X[i - 16];
        }
        int i2 = this.H1;
        int i3 = this.H2;
        int i4 = this.H3;
        int i5 = this.H4;
        int i6 = this.H5;
        int i7 = this.H6;
        int i8 = this.H7;
        i = this.H8;
        int i9 = i2;
        i2 = i6;
        i6 = i3;
        i3 = i7;
        i7 = i4;
        i4 = i8;
        i8 = 0;
        for (int i10 = 0; i10 < 8; i10++) {
            i += ((Sum1(i2) + Ch(i2, i3, i4)) + K[i8]) + this.X[i8];
            i5 += i;
            i += Sum0(i9) + Maj(i9, i6, i7);
            i8++;
            i4 += ((Sum1(i5) + Ch(i5, i2, i3)) + K[i8]) + this.X[i8];
            i7 += i4;
            i4 += Sum0(i) + Maj(i, i9, i6);
            i8++;
            i3 += ((Sum1(i7) + Ch(i7, i5, i2)) + K[i8]) + this.X[i8];
            i6 += i3;
            i3 += Sum0(i4) + Maj(i4, i, i9);
            i8++;
            i2 += ((Sum1(i6) + Ch(i6, i7, i5)) + K[i8]) + this.X[i8];
            i9 += i2;
            i2 += Sum0(i3) + Maj(i3, i4, i);
            i8++;
            i5 += ((Sum1(i9) + Ch(i9, i6, i7)) + K[i8]) + this.X[i8];
            i += i5;
            i5 += Sum0(i2) + Maj(i2, i3, i4);
            i8++;
            i7 += ((Sum1(i) + Ch(i, i9, i6)) + K[i8]) + this.X[i8];
            i4 += i7;
            i7 += Sum0(i5) + Maj(i5, i2, i3);
            i8++;
            i6 += ((Sum1(i4) + Ch(i4, i, i9)) + K[i8]) + this.X[i8];
            i3 += i6;
            i6 += Sum0(i7) + Maj(i7, i5, i2);
            i8++;
            i9 += ((Sum1(i3) + Ch(i3, i4, i)) + K[i8]) + this.X[i8];
            i2 += i9;
            i9 += Sum0(i6) + Maj(i6, i7, i5);
            i8++;
        }
        this.H1 += i9;
        this.H2 += i6;
        this.H3 += i7;
        this.H4 = i5 + this.H4;
        this.H5 += i2;
        this.H6 += i3;
        this.H7 += i4;
        this.H8 = i + this.H8;
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
        this.H1 = -1056596264;
        this.H2 = 914150663;
        this.H3 = 812702999;
        this.H4 = -150054599;
        this.H5 = -4191439;
        this.H6 = 1750603025;
        this.H7 = 1694076839;
        this.H8 = -1090891868;
        this.xOff = 0;
        for (int i = 0; i != this.X.length; i++) {
            this.X[i] = 0;
        }
    }
}
