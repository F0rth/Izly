package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.RC5Parameters;
import org.spongycastle.crypto.tls.CipherSuite;

public class RC532Engine implements BlockCipher {
    private static final int P32 = -1209970333;
    private static final int Q32 = -1640531527;
    private int[] _S = null;
    private int _noRounds = 12;
    private boolean forEncryption;

    private int bytesToWord(byte[] bArr, int i) {
        return (((bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | ((bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24);
    }

    private int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 4);
        for (int i3 = this._noRounds; i3 > 0; i3--) {
            bytesToWord2 = rotateRight(bytesToWord2 - this._S[(i3 * 2) + 1], bytesToWord) ^ bytesToWord;
            bytesToWord = rotateRight(bytesToWord - this._S[i3 * 2], bytesToWord2) ^ bytesToWord2;
        }
        wordToBytes(bytesToWord - this._S[0], bArr2, i2);
        wordToBytes(bytesToWord2 - this._S[1], bArr2, i2 + 4);
        return 8;
    }

    private int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = 1;
        int bytesToWord = bytesToWord(bArr, i) + this._S[0];
        int bytesToWord2 = bytesToWord(bArr, i + 4) + this._S[1];
        while (i3 <= this._noRounds) {
            bytesToWord = rotateLeft(bytesToWord ^ bytesToWord2, bytesToWord2) + this._S[i3 * 2];
            bytesToWord2 = rotateLeft(bytesToWord2 ^ bytesToWord, bytesToWord) + this._S[(i3 * 2) + 1];
            i3++;
        }
        wordToBytes(bytesToWord, bArr2, i2);
        wordToBytes(bytesToWord2, bArr2, i2 + 4);
        return 8;
    }

    private int rotateLeft(int i, int i2) {
        return (i << (i2 & 31)) | (i >>> (32 - (i2 & 31)));
    }

    private int rotateRight(int i, int i2) {
        return (i >>> (i2 & 31)) | (i << (32 - (i2 & 31)));
    }

    private void setKey(byte[] bArr) {
        int i;
        int i2;
        int i3 = 0;
        int[] iArr = new int[((bArr.length + 3) / 4)];
        for (i = 0; i != bArr.length; i++) {
            i2 = i / 4;
            iArr[i2] = iArr[i2] + ((bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << ((i % 4) * 8));
        }
        this._S = new int[((this._noRounds + 1) * 2)];
        this._S[0] = P32;
        for (i = 1; i < this._S.length; i++) {
            this._S[i] = this._S[i - 1] - 1640531527;
        }
        i = iArr.length > this._S.length ? iArr.length * 3 : this._S.length * 3;
        i2 = 0;
        int i4 = 0;
        int i5 = 0;
        for (int i6 = 0; i6 < i; i6++) {
            int[] iArr2 = this._S;
            i3 = rotateLeft((i3 + this._S[i4]) + i2, 3);
            iArr2[i4] = i3;
            i2 = rotateLeft((iArr[i5] + i3) + i2, i2 + i3);
            iArr[i5] = i2;
            i4 = (i4 + 1) % this._S.length;
            i5 = (i5 + 1) % iArr.length;
        }
    }

    private void wordToBytes(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) i;
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) (i >> 16);
        bArr[i2 + 3] = (byte) (i >> 24);
    }

    public String getAlgorithmName() {
        return "RC5-32";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof RC5Parameters) {
            RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
            this._noRounds = rC5Parameters.getRounds();
            setKey(rC5Parameters.getKey());
        } else if (cipherParameters instanceof KeyParameter) {
            setKey(((KeyParameter) cipherParameters).getKey());
        } else {
            throw new IllegalArgumentException("invalid parameter passed to RC532 init - " + cipherParameters.getClass().getName());
        }
        this.forEncryption = z;
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        return this.forEncryption ? encryptBlock(bArr, i, bArr2, i2) : decryptBlock(bArr, i, bArr2, i2);
    }

    public void reset() {
    }
}
