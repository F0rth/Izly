package org.spongycastle.crypto.engines;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.NTRUEncryptionParameters;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Arrays;

public class IndexGenerator {
    private int N;
    private BitString buf;
    private int c;
    private int counter = 0;
    private int hLen;
    private Digest hashAlg;
    private boolean initialized;
    private int minCallsR;
    private int remLen = 0;
    private byte[] seed;
    private int totLen = 0;

    public static class BitString {
        byte[] bytes = new byte[4];
        int lastByteBits;
        int numBytes;

        public void appendBits(byte b) {
            if (this.numBytes == this.bytes.length) {
                this.bytes = IndexGenerator.copyOf(this.bytes, this.bytes.length * 2);
            }
            if (this.numBytes == 0) {
                this.numBytes = 1;
                this.bytes[0] = b;
                this.lastByteBits = 8;
            } else if (this.lastByteBits == 8) {
                byte[] bArr = this.bytes;
                int i = this.numBytes;
                this.numBytes = i + 1;
                bArr[i] = b;
            } else {
                int i2 = this.lastByteBits;
                byte[] bArr2 = this.bytes;
                int i3 = this.numBytes - 1;
                bArr2[i3] = (byte) (bArr2[i3] | ((b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << this.lastByteBits));
                bArr2 = this.bytes;
                i3 = this.numBytes;
                this.numBytes = i3 + 1;
                bArr2[i3] = (byte) ((b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >> (8 - i2));
            }
        }

        void appendBits(byte[] bArr) {
            for (int i = 0; i != bArr.length; i++) {
                appendBits(bArr[i]);
            }
        }

        public byte[] getBytes() {
            return Arrays.clone(this.bytes);
        }

        public int getLeadingAsInt(int i) {
            int i2 = (((this.numBytes - 1) * 8) + this.lastByteBits) - i;
            int i3 = i2 / 8;
            int i4 = i2 % 8;
            i2 = (this.bytes[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >>> i4;
            i4 = 8 - i4;
            for (i3++; i3 < this.numBytes; i3++) {
                i2 |= (this.bytes[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << i4;
                i4 += 8;
            }
            return i2;
        }

        public BitString getTrailing(int i) {
            int i2;
            BitString bitString = new BitString();
            bitString.numBytes = (i + 7) / 8;
            bitString.bytes = new byte[bitString.numBytes];
            for (i2 = 0; i2 < bitString.numBytes; i2++) {
                bitString.bytes[i2] = this.bytes[i2];
            }
            bitString.lastByteBits = i % 8;
            if (bitString.lastByteBits == 0) {
                bitString.lastByteBits = 8;
                return bitString;
            }
            i2 = 32 - bitString.lastByteBits;
            bitString.bytes[bitString.numBytes - 1] = (byte) ((bitString.bytes[bitString.numBytes - 1] << i2) >>> i2);
            return bitString;
        }
    }

    IndexGenerator(byte[] bArr, NTRUEncryptionParameters nTRUEncryptionParameters) {
        this.seed = bArr;
        this.N = nTRUEncryptionParameters.N;
        this.c = nTRUEncryptionParameters.c;
        this.minCallsR = nTRUEncryptionParameters.minCallsR;
        this.hashAlg = nTRUEncryptionParameters.hashAlg;
        this.hLen = this.hashAlg.getDigestSize();
        this.initialized = false;
    }

    private void appendHash(BitString bitString, byte[] bArr) {
        this.hashAlg.update(this.seed, 0, this.seed.length);
        putInt(this.hashAlg, this.counter);
        this.hashAlg.doFinal(bArr, 0);
        bitString.appendBits(bArr);
    }

    private static byte[] copyOf(byte[] bArr, int i) {
        Object obj = new byte[i];
        if (i >= bArr.length) {
            i = bArr.length;
        }
        System.arraycopy(bArr, 0, obj, 0, i);
        return obj;
    }

    private void putInt(Digest digest, int i) {
        digest.update((byte) (i >> 24));
        digest.update((byte) (i >> 16));
        digest.update((byte) (i >> 8));
        digest.update((byte) i);
    }

    int nextIndex() {
        int i;
        if (!this.initialized) {
            this.buf = new BitString();
            byte[] bArr = new byte[this.hashAlg.getDigestSize()];
            while (this.counter < this.minCallsR) {
                appendHash(this.buf, bArr);
                this.counter++;
            }
            this.totLen = (this.minCallsR * 8) * this.hLen;
            this.remLen = this.totLen;
            this.initialized = true;
        }
        do {
            this.totLen += this.c;
            BitString trailing = this.buf.getTrailing(this.remLen);
            if (this.remLen < this.c) {
                i = this.c - this.remLen;
                int i2 = this.counter;
                int i3 = ((this.hLen + i) - 1) / this.hLen;
                byte[] bArr2 = new byte[this.hashAlg.getDigestSize()];
                while (this.counter < i2 + i3) {
                    appendHash(trailing, bArr2);
                    this.counter++;
                    if (i > this.hLen * 8) {
                        i -= this.hLen * 8;
                    }
                }
                this.remLen = (this.hLen * 8) - i;
                this.buf = new BitString();
                this.buf.appendBits(bArr2);
            } else {
                this.remLen -= this.c;
            }
            i = trailing.getLeadingAsInt(this.c);
        } while (i >= (1 << this.c) - ((1 << this.c) % this.N));
        return i % this.N;
    }
}
