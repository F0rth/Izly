package org.spongycastle.crypto.encodings;

import java.math.BigInteger;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.tls.CipherSuite;

public class ISO9796d1Encoding implements AsymmetricBlockCipher {
    private static final BigInteger SIX = BigInteger.valueOf(6);
    private static final BigInteger SIXTEEN = BigInteger.valueOf(16);
    private static byte[] inverse = new byte[]{(byte) 8, (byte) 15, (byte) 6, (byte) 1, (byte) 5, (byte) 2, (byte) 11, (byte) 12, (byte) 3, (byte) 4, (byte) 13, (byte) 10, (byte) 14, (byte) 9, (byte) 0, (byte) 7};
    private static byte[] shadows = new byte[]{(byte) 14, (byte) 3, (byte) 5, (byte) 8, (byte) 9, (byte) 4, (byte) 2, (byte) 15, (byte) 0, (byte) 13, (byte) 11, (byte) 6, (byte) 7, (byte) 10, (byte) 12, (byte) 1};
    private int bitSize;
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private BigInteger modulus;
    private int padBits = 0;

    public ISO9796d1Encoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this.engine = asymmetricBlockCipher;
    }

    private static byte[] convertOutputDecryptOnly(BigInteger bigInteger) {
        Object toByteArray = bigInteger.toByteArray();
        if (toByteArray[0] != (byte) 0) {
            return toByteArray;
        }
        Object obj = new byte[(toByteArray.length - 1)];
        System.arraycopy(toByteArray, 1, obj, 0, obj.length);
        return obj;
    }

    private byte[] decodeBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        int i3 = 0;
        int i4 = (this.bitSize + 13) / 16;
        BigInteger bigInteger = new BigInteger(1, this.engine.processBlock(bArr, i, i2));
        if (!bigInteger.mod(SIXTEEN).equals(SIX)) {
            if (this.modulus.subtract(bigInteger).mod(SIXTEEN).equals(SIX)) {
                bigInteger = this.modulus.subtract(bigInteger);
            } else {
                throw new InvalidCipherTextException("resulting integer iS or (modulus - iS) is not congruent to 6 mod 16");
            }
        }
        byte[] convertOutputDecryptOnly = convertOutputDecryptOnly(bigInteger);
        if ((convertOutputDecryptOnly[convertOutputDecryptOnly.length - 1] & 15) != 6) {
            throw new InvalidCipherTextException("invalid forcing byte in block");
        }
        convertOutputDecryptOnly[convertOutputDecryptOnly.length - 1] = (byte) (((convertOutputDecryptOnly[convertOutputDecryptOnly.length - 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >>> 4) | (inverse[(convertOutputDecryptOnly[convertOutputDecryptOnly.length - 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >> 4] << 4));
        convertOutputDecryptOnly[0] = (byte) ((shadows[(convertOutputDecryptOnly[1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >>> 4] << 4) | shadows[convertOutputDecryptOnly[1] & 15]);
        int i5 = 0;
        int i6 = 1;
        int i7 = 0;
        for (int length = convertOutputDecryptOnly.length - 1; length >= convertOutputDecryptOnly.length - (i4 * 2); length -= 2) {
            int i8 = (shadows[(convertOutputDecryptOnly[length] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >>> 4] << 4) | shadows[convertOutputDecryptOnly[length] & 15];
            if (((convertOutputDecryptOnly[length - 1] ^ i8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) != 0) {
                if (i7 == 0) {
                    i6 = (convertOutputDecryptOnly[length - 1] ^ i8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                    i5 = length - 1;
                    i7 = 1;
                } else {
                    throw new InvalidCipherTextException("invalid tsums in block");
                }
            }
        }
        convertOutputDecryptOnly[i5] = (byte) 0;
        byte[] bArr2 = new byte[((convertOutputDecryptOnly.length - i5) / 2)];
        while (i3 < bArr2.length) {
            bArr2[i3] = convertOutputDecryptOnly[((i3 * 2) + i5) + 1];
            i3++;
        }
        this.padBits = i6 - 1;
        return bArr2;
    }

    private byte[] encodeBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        Object obj = new byte[((this.bitSize + 7) / 8)];
        int i3 = this.padBits;
        int i4 = (this.bitSize + 13) / 16;
        int i5 = 0;
        while (i5 < i4) {
            if (i5 > i4 - i2) {
                System.arraycopy(bArr, (i + i2) - (i4 - i5), obj, obj.length - i4, i4 - i5);
            } else {
                System.arraycopy(bArr, i, obj, obj.length - (i5 + i2), i2);
            }
            i5 += i2;
        }
        for (i5 = obj.length - (i4 * 2); i5 != obj.length; i5 += 2) {
            byte b = obj[(obj.length - i4) + (i5 / 2)];
            obj[i5] = (byte) ((shadows[(b & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) >>> 4] << 4) | shadows[b & 15]);
            obj[i5 + 1] = b;
        }
        i5 = obj.length - (i2 * 2);
        obj[i5] = (byte) ((i3 + 1) ^ obj[i5]);
        obj[obj.length - 1] = (byte) ((obj[obj.length - 1] << 4) | 6);
        i5 = 8 - ((this.bitSize - 1) % 8);
        if (i5 != 8) {
            obj[0] = (byte) (obj[0] & (CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV >>> i5));
            obj[0] = (byte) ((128 >>> i5) | obj[0]);
            i5 = 0;
        } else {
            obj[0] = null;
            obj[1] = (byte) (obj[1] | 128);
            i5 = 1;
        }
        return this.engine.processBlock(obj, i5, obj.length - i5);
    }

    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? (inputBlockSize + 1) / 2 : inputBlockSize;
    }

    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : (outputBlockSize + 1) / 2;
    }

    public int getPadBits() {
        return this.padBits;
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = cipherParameters instanceof ParametersWithRandom ? (RSAKeyParameters) ((ParametersWithRandom) cipherParameters).getParameters() : (RSAKeyParameters) cipherParameters;
        this.engine.init(z, cipherParameters);
        this.modulus = rSAKeyParameters.getModulus();
        this.bitSize = this.modulus.bitLength();
        this.forEncryption = z;
    }

    public byte[] processBlock(byte[] bArr, int i, int i2) throws InvalidCipherTextException {
        return this.forEncryption ? encodeBlock(bArr, i, i2) : decodeBlock(bArr, i, i2);
    }

    public void setPadBits(int i) {
        if (i > 7) {
            throw new IllegalArgumentException("padBits > 7");
        }
        this.padBits = i;
    }
}
