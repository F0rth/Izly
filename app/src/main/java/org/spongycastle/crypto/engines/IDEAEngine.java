package org.spongycastle.crypto.engines;

import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class IDEAEngine implements BlockCipher {
    private static final int BASE = 65537;
    protected static final int BLOCK_SIZE = 8;
    private static final int MASK = 65535;
    private int[] workingKey = null;

    private int bytesToWord(byte[] bArr, int i) {
        return ((bArr[i] << 8) & 65280) + (bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    private int[] expandKey(byte[] bArr) {
        int i;
        int[] iArr = new int[52];
        if (bArr.length < 16) {
            Object obj = new byte[16];
            System.arraycopy(bArr, 0, obj, 16 - bArr.length, bArr.length);
            bArr = obj;
        }
        for (i = 0; i < 8; i++) {
            iArr[i] = bytesToWord(bArr, i * 2);
        }
        for (i = 8; i < 52; i++) {
            if ((i & 7) < 6) {
                iArr[i] = (((iArr[i - 7] & CertificateBody.profileType) << 9) | (iArr[i - 6] >> 7)) & MASK;
            } else if ((i & 7) == 6) {
                iArr[i] = (((iArr[i - 7] & CertificateBody.profileType) << 9) | (iArr[i - 14] >> 7)) & MASK;
            } else {
                iArr[i] = (((iArr[i - 15] & CertificateBody.profileType) << 9) | (iArr[i - 14] >> 7)) & MASK;
            }
        }
        return iArr;
    }

    private int[] generateWorkingKey(boolean z, byte[] bArr) {
        return z ? expandKey(bArr) : invertKey(expandKey(bArr));
    }

    private void ideaFunc(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3;
        int i4 = 0;
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 2);
        int bytesToWord3 = bytesToWord(bArr, i + 4);
        int bytesToWord4 = bytesToWord(bArr, i + 6);
        for (i3 = 0; i3 < 8; i3++) {
            int i5 = i4 + 1;
            bytesToWord = mul(bytesToWord, iArr[i4]);
            i4 = i5 + 1;
            i5 = (bytesToWord2 + iArr[i5]) & MASK;
            bytesToWord2 = i4 + 1;
            bytesToWord3 = (iArr[i4] + bytesToWord3) & MASK;
            i4 = bytesToWord2 + 1;
            bytesToWord2 = mul(bytesToWord4, iArr[bytesToWord2]);
            bytesToWord4 = i4 + 1;
            int mul = mul(bytesToWord3 ^ bytesToWord, iArr[i4]);
            i4 = bytesToWord4 + 1;
            int mul2 = mul(((i5 ^ bytesToWord2) + mul) & MASK, iArr[bytesToWord4]);
            mul = (mul + mul2) & MASK;
            bytesToWord ^= mul2;
            bytesToWord4 = bytesToWord2 ^ mul;
            bytesToWord2 = mul2 ^ bytesToWord3;
            bytesToWord3 = mul ^ i5;
        }
        i3 = i4 + 1;
        wordToBytes(mul(bytesToWord, iArr[i4]), bArr2, i2);
        bytesToWord = i3 + 1;
        wordToBytes(iArr[i3] + bytesToWord3, bArr2, i2 + 2);
        wordToBytes(iArr[bytesToWord] + bytesToWord2, bArr2, i2 + 4);
        wordToBytes(mul(bytesToWord4, iArr[bytesToWord + 1]), bArr2, i2 + 6);
    }

    private int[] invertKey(int[] iArr) {
        int i = 48;
        int i2 = 1;
        int[] iArr2 = new int[52];
        int mulInv = mulInv(iArr[0]);
        int addInv = addInv(iArr[1]);
        int addInv2 = addInv(iArr[2]);
        int i3 = 4;
        iArr2[51] = mulInv(iArr[3]);
        iArr2[50] = addInv2;
        iArr2[49] = addInv;
        iArr2[48] = mulInv;
        while (i2 < 8) {
            mulInv = i3 + 1;
            i3 = iArr[i3];
            addInv = mulInv + 1;
            i--;
            iArr2[i] = iArr[mulInv];
            i--;
            iArr2[i] = i3;
            i3 = addInv + 1;
            mulInv = mulInv(iArr[addInv]);
            addInv = i3 + 1;
            addInv2 = addInv(iArr[i3]);
            int i4 = addInv + 1;
            addInv = addInv(iArr[addInv]);
            i3 = i4 + 1;
            i--;
            iArr2[i] = mulInv(iArr[i4]);
            i--;
            iArr2[i] = addInv2;
            i--;
            iArr2[i] = addInv;
            i--;
            iArr2[i] = mulInv;
            i2++;
        }
        i2 = i3 + 1;
        i3 = iArr[i3];
        mulInv = i2 + 1;
        i--;
        iArr2[i] = iArr[i2];
        i--;
        iArr2[i] = i3;
        i3 = mulInv + 1;
        i2 = mulInv(iArr[mulInv]);
        mulInv = i3 + 1;
        i3 = addInv(iArr[i3]);
        addInv = addInv(iArr[mulInv]);
        i--;
        iArr2[i] = mulInv(iArr[mulInv + 1]);
        i--;
        iArr2[i] = addInv;
        i--;
        iArr2[i] = i3;
        iArr2[i - 1] = i2;
        return iArr2;
    }

    private int mul(int i, int i2) {
        int i3;
        if (i == 0) {
            i3 = BASE - i2;
        } else if (i2 == 0) {
            i3 = BASE - i;
        } else {
            i3 = i * i2;
            int i4 = i3 & MASK;
            int i5 = i3 >>> 16;
            i3 = (i4 < i5 ? 1 : 0) + (i4 - i5);
        }
        return i3 & MASK;
    }

    private int mulInv(int i) {
        if (i < 2) {
            return i;
        }
        int i2 = BASE % i;
        int i3 = BASE / i;
        int i4 = 1;
        while (i2 != 1) {
            int i5 = i % i2;
            i = (i4 + ((i / i2) * i3)) & MASK;
            if (i5 == 1) {
                return i;
            }
            i3 = (((i2 / i5) * i) + i3) & MASK;
            i2 %= i5;
            i4 = i;
            i = i5;
        }
        return (1 - i3) & MASK;
    }

    private void wordToBytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) i;
    }

    int addInv(int i) {
        return (0 - i) & MASK;
    }

    public String getAlgorithmName() {
        return "IDEA";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.workingKey = generateWorkingKey(z, ((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to IDEA init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("IDEA engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            ideaFunc(this.workingKey, bArr, i, bArr2, i2);
            return 8;
        }
    }

    public void reset() {
    }
}
