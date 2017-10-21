package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class RC6Engine implements BlockCipher {
    private static final int LGW = 5;
    private static final int P32 = -1209970333;
    private static final int Q32 = -1640531527;
    private static final int _noRounds = 20;
    private static final int bytesPerWord = 4;
    private static final int wordSize = 32;
    private int[] _S = null;
    private boolean forEncryption;

    private int bytesToWord(byte[] bArr, int i) {
        int i2 = 0;
        for (int i3 = 3; i3 >= 0; i3--) {
            i2 = (i2 << 8) + (bArr[i3 + i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        return i2;
    }

    private int decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int rotateLeft;
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 4);
        int bytesToWord3 = bytesToWord(bArr, i + 8);
        int i3 = bytesToWord3 - this._S[43];
        bytesToWord3 = bytesToWord - this._S[42];
        bytesToWord = 20;
        int bytesToWord4 = bytesToWord(bArr, i + 12);
        int i4 = bytesToWord3;
        bytesToWord3 = bytesToWord4;
        while (bytesToWord > 0) {
            rotateLeft = rotateLeft(((i4 * 2) + 1) * i4, 5);
            int rotateLeft2 = rotateLeft(((i3 * 2) + 1) * i3, 5);
            int rotateRight = rotateRight(bytesToWord2 - this._S[(bytesToWord * 2) + 1], rotateLeft);
            bytesToWord--;
            bytesToWord4 = i4;
            i4 = rotateRight(bytesToWord3 - this._S[bytesToWord * 2], rotateLeft2) ^ rotateLeft;
            bytesToWord2 = bytesToWord4;
            int i5 = i3;
            i3 = rotateRight ^ rotateLeft2;
            bytesToWord3 = i5;
        }
        bytesToWord = this._S[1];
        rotateLeft = this._S[0];
        wordToBytes(i4, bArr2, i2);
        wordToBytes(bytesToWord2 - rotateLeft, bArr2, i2 + 4);
        wordToBytes(i3, bArr2, i2 + 8);
        wordToBytes(bytesToWord3 - bytesToWord, bArr2, i2 + 12);
        return 16;
    }

    private int encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int rotateLeft;
        int bytesToWord = bytesToWord(bArr, i);
        int bytesToWord2 = bytesToWord(bArr, i + 4);
        int bytesToWord3 = bytesToWord(bArr, i + 8);
        int bytesToWord4 = bytesToWord(bArr, i + 12);
        int i3 = this._S[1] + bytesToWord4;
        bytesToWord4 = bytesToWord2 + this._S[0];
        bytesToWord2 = bytesToWord3;
        bytesToWord3 = 1;
        while (bytesToWord3 <= 20) {
            rotateLeft = rotateLeft(((bytesToWord4 * 2) + 1) * bytesToWord4, 5);
            int rotateLeft2 = rotateLeft(((i3 * 2) + 1) * i3, 5);
            int rotateLeft3 = rotateLeft(bytesToWord ^ rotateLeft, rotateLeft2);
            int i4 = this._S[bytesToWord3 * 2];
            bytesToWord = bytesToWord3 + 1;
            int i5 = bytesToWord4;
            bytesToWord4 = rotateLeft(bytesToWord2 ^ rotateLeft2, rotateLeft) + this._S[(bytesToWord3 * 2) + 1];
            bytesToWord3 = bytesToWord;
            bytesToWord = i5;
            int i6 = i3;
            i3 = i4 + rotateLeft3;
            bytesToWord2 = i6;
        }
        bytesToWord3 = this._S[42];
        rotateLeft = this._S[43];
        wordToBytes(bytesToWord + bytesToWord3, bArr2, i2);
        wordToBytes(bytesToWord4, bArr2, i2 + 4);
        wordToBytes(bytesToWord2 + rotateLeft, bArr2, i2 + 8);
        wordToBytes(i3, bArr2, i2 + 12);
        return 16;
    }

    private int rotateLeft(int i, int i2) {
        return (i << i2) | (i >>> (-i2));
    }

    private int rotateRight(int i, int i2) {
        return (i >>> i2) | (i << (-i2));
    }

    private void setKey(byte[] bArr) {
        int length;
        int i = 0;
        int[] iArr = new int[(((bArr.length + 4) - 1) / 4)];
        for (length = bArr.length - 1; length >= 0; length--) {
            iArr[length / 4] = (iArr[length / 4] << 8) + (bArr[length] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        this._S = new int[44];
        this._S[0] = P32;
        for (length = 1; length < this._S.length; length++) {
            this._S[length] = this._S[length - 1] - 1640531527;
        }
        length = iArr.length > this._S.length ? iArr.length * 3 : this._S.length * 3;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < length; i5++) {
            int[] iArr2 = this._S;
            i = rotateLeft((i + this._S[i3]) + i2, 3);
            iArr2[i3] = i;
            i2 = rotateLeft((iArr[i4] + i) + i2, i2 + i);
            iArr[i4] = i2;
            i3 = (i3 + 1) % this._S.length;
            i4 = (i4 + 1) % iArr.length;
        }
    }

    private void wordToBytes(int i, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            bArr[i3 + i2] = (byte) i;
            i >>>= 8;
        }
    }

    public String getAlgorithmName() {
        return "RC6";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            KeyParameter keyParameter = (KeyParameter) cipherParameters;
            this.forEncryption = z;
            setKey(keyParameter.getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to RC6 init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        int blockSize = getBlockSize();
        if (this._S == null) {
            throw new IllegalStateException("RC6 engine not initialised");
        } else if (i + blockSize > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (blockSize + i2 <= bArr2.length) {
            return this.forEncryption ? encryptBlock(bArr, i, bArr2, i2) : decryptBlock(bArr, i, bArr2, i2);
        } else {
            throw new DataLengthException("output buffer too short");
        }
    }

    public void reset() {
    }
}
