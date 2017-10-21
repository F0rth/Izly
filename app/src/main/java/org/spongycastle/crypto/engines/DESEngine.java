package org.spongycastle.crypto.engines;

import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class DESEngine implements BlockCipher {
    protected static final int BLOCK_SIZE = 8;
    private static final int[] SP1 = new int[]{16843776, 0, PKIFailureInfo.notAuthorized, 16843780, 16842756, 66564, 4, PKIFailureInfo.notAuthorized, PKIFailureInfo.badRecipientNonce, 16843776, 16843780, PKIFailureInfo.badRecipientNonce, 16778244, 16842756, 16777216, 4, 1028, 16778240, 16778240, 66560, 66560, 16842752, 16842752, 16778244, 65540, 16777220, 16777220, 65540, 0, 1028, 66564, 16777216, PKIFailureInfo.notAuthorized, 16843780, 4, 16842752, 16843776, 16777216, 16777216, PKIFailureInfo.badRecipientNonce, 16842756, PKIFailureInfo.notAuthorized, 66560, 16777220, PKIFailureInfo.badRecipientNonce, 4, 16778244, 66564, 16843780, 65540, 16842752, 16778244, 16777220, 1028, 66564, 16843776, 1028, 16778240, 16778240, 0, 65540, 66560, 0, 16842756};
    private static final int[] SP2 = new int[]{-2146402272, -2147450880, 32768, 1081376, PKIFailureInfo.badCertTemplate, 32, -2146435040, -2147450848, -2147483616, -2146402272, -2146402304, PKIFailureInfo.systemUnavail, -2147450880, PKIFailureInfo.badCertTemplate, 32, -2146435040, 1081344, 1048608, -2147450848, 0, PKIFailureInfo.systemUnavail, 32768, 1081376, -2146435072, 1048608, -2147483616, 0, 1081344, 32800, -2146402304, -2146435072, 32800, 0, 1081376, -2146435040, PKIFailureInfo.badCertTemplate, -2147450848, -2146435072, -2146402304, 32768, -2146435072, -2147450880, 32, -2146402272, 1081376, 32, 32768, PKIFailureInfo.systemUnavail, 32800, -2146402304, PKIFailureInfo.badCertTemplate, -2147483616, 1048608, -2147450848, -2147483616, 1048608, 1081344, 0, -2147450880, 32800, PKIFailureInfo.systemUnavail, -2146435040, -2146402272, 1081344};
    private static final int[] SP3 = new int[]{520, 134349312, 0, 134348808, 134218240, 0, 131592, 134218240, 131080, 134217736, 134217736, PKIFailureInfo.unsupportedVersion, 134349320, 131080, 134348800, 520, 134217728, 8, 134349312, 512, 131584, 134348800, 134348808, 131592, 134218248, 131584, PKIFailureInfo.unsupportedVersion, 134218248, 8, 134349320, 512, 134217728, 134349312, 134217728, 131080, 520, PKIFailureInfo.unsupportedVersion, 134349312, 134218240, 0, 512, 131080, 134349320, 134218240, 134217736, 512, 0, 134348808, 134218248, PKIFailureInfo.unsupportedVersion, 134217728, 134349320, 8, 131592, 131584, 134217736, 134348800, 134218248, 520, 134348800, 131592, 8, 134348808, 131584};
    private static final int[] SP4 = new int[]{8396801, 8321, 8321, 128, 8396928, 8388737, 8388609, 8193, 0, 8396800, 8396800, 8396929, Wbxml.EXT_T_1, 0, 8388736, 8388609, 1, PKIFailureInfo.certRevoked, 8388608, 8396801, 128, 8388608, 8193, 8320, 8388737, 1, 8320, 8388736, PKIFailureInfo.certRevoked, 8396928, 8396929, Wbxml.EXT_T_1, 8388736, 8388609, 8396800, 8396929, Wbxml.EXT_T_1, 0, 0, 8396800, 8320, 8388736, 8388737, 1, 8396801, 8321, 8321, 128, 8396929, Wbxml.EXT_T_1, 1, PKIFailureInfo.certRevoked, 8388609, 8193, 8396928, 8388737, 8193, 8320, 8388608, 8396801, 128, 8388608, PKIFailureInfo.certRevoked, 8396928};
    private static final int[] SP5 = new int[]{256, 34078976, 34078720, 1107296512, PKIFailureInfo.signerNotTrusted, 256, 1073741824, 34078720, 1074266368, PKIFailureInfo.signerNotTrusted, 33554688, 1074266368, 1107296512, 1107820544, 524544, 1073741824, 33554432, 1074266112, 1074266112, 0, 1073742080, 1107820800, 1107820800, 33554688, 1107820544, 1073742080, 0, 1107296256, 34078976, 33554432, 1107296256, 524544, PKIFailureInfo.signerNotTrusted, 1107296512, 256, 33554432, 1073741824, 34078720, 1107296512, 1074266368, 33554688, 1073741824, 1107820544, 34078976, 1074266368, 256, 33554432, 1107820544, 1107820800, 524544, 1107296256, 1107820800, 34078720, 0, 1074266112, 1107296256, 524544, 33554688, 1073742080, PKIFailureInfo.signerNotTrusted, 0, 1074266112, 34078976, 1073742080};
    private static final int[] SP6 = new int[]{536870928, 541065216, 16384, 541081616, 541065216, 16, 541081616, 4194304, 536887296, 4210704, 4194304, 536870928, 4194320, 536887296, PKIFailureInfo.duplicateCertReq, 16400, 0, 4194320, 536887312, 16384, 4210688, 536887312, 16, 541065232, 541065232, 0, 4210704, 541081600, 16400, 4210688, 541081600, PKIFailureInfo.duplicateCertReq, 536887296, 16, 541065232, 4210688, 541081616, 4194304, 16400, 536870928, 4194304, 536887296, PKIFailureInfo.duplicateCertReq, 16400, 536870928, 541081616, 4210688, 541065216, 4210704, 541081600, 0, 541065232, 16, 16384, 541065216, 4210704, 16384, 4194320, 536887312, 0, 541081600, PKIFailureInfo.duplicateCertReq, 4194320, 536887312};
    private static final int[] SP7 = new int[]{PKIFailureInfo.badSenderNonce, 69206018, 67110914, 0, PKIFailureInfo.wrongIntegrity, 67110914, 2099202, 69208064, 69208066, PKIFailureInfo.badSenderNonce, 0, 67108866, 2, 67108864, 69206018, 2050, 67110912, 2099202, 2097154, 67110912, 67108866, 69206016, 69208064, 2097154, 69206016, PKIFailureInfo.wrongIntegrity, 2050, 69208066, 2099200, 2, 67108864, 2099200, 67108864, 2099200, PKIFailureInfo.badSenderNonce, 67110914, 67110914, 69206018, 69206018, 2, 2097154, 67108864, 67110912, PKIFailureInfo.badSenderNonce, 69208064, 2050, 2099202, 69208064, 2050, 67108866, 69208066, 69206016, 2099200, 0, 2, 69208066, 0, 2099202, 69206016, PKIFailureInfo.wrongIntegrity, 67108866, 67110912, PKIFailureInfo.wrongIntegrity, 2097154};
    private static final int[] SP8 = new int[]{268439616, PKIFailureInfo.certConfirmed, 262144, 268701760, 268435456, 268439616, 64, 268435456, 262208, 268697600, 268701760, 266240, 268701696, 266304, PKIFailureInfo.certConfirmed, 64, 268697600, 268435520, 268439552, 4160, 266240, 262208, 268697664, 268701696, 4160, 0, 0, 268697664, 268435520, 268439552, 266304, 262144, 266304, 262144, 268701696, PKIFailureInfo.certConfirmed, 64, 268697664, PKIFailureInfo.certConfirmed, 266304, 268439552, 64, 268435520, 268697600, 268697664, 268435456, 262144, 268439616, 0, 268701760, 262208, 268435520, 268697600, 268439552, 268439616, 0, 268701760, 266240, 266240, 4160, 4160, 262208, 268435456, 268701696};
    private static final int[] bigbyte = new int[]{8388608, 4194304, PKIFailureInfo.badSenderNonce, PKIFailureInfo.badCertTemplate, PKIFailureInfo.signerNotTrusted, 262144, PKIFailureInfo.unsupportedVersion, PKIFailureInfo.notAuthorized, 32768, 16384, PKIFailureInfo.certRevoked, PKIFailureInfo.certConfirmed, PKIFailureInfo.wrongIntegrity, PKIFailureInfo.badRecipientNonce, 512, 256, 128, 64, 32, 16, 8, 4, 2, 1};
    private static final short[] bytebit = new short[]{(short) 128, (short) 64, (short) 32, (short) 16, (short) 8, (short) 4, (short) 2, (short) 1};
    private static final byte[] pc1 = new byte[]{(byte) 56, (byte) 48, (byte) 40, (byte) 32, (byte) 24, Tnaf.POW_2_WIDTH, (byte) 8, (byte) 0, (byte) 57, (byte) 49, (byte) 41, (byte) 33, (byte) 25, (byte) 17, (byte) 9, (byte) 1, (byte) 58, (byte) 50, (byte) 42, (byte) 34, (byte) 26, (byte) 18, (byte) 10, (byte) 2, (byte) 59, (byte) 51, (byte) 43, (byte) 35, (byte) 62, (byte) 54, (byte) 46, (byte) 38, (byte) 30, (byte) 22, (byte) 14, (byte) 6, (byte) 61, (byte) 53, (byte) 45, (byte) 37, (byte) 29, (byte) 21, (byte) 13, (byte) 5, (byte) 60, (byte) 52, (byte) 44, (byte) 36, (byte) 28, (byte) 20, (byte) 12, (byte) 4, (byte) 27, (byte) 19, (byte) 11, (byte) 3};
    private static final byte[] pc2 = new byte[]{(byte) 13, Tnaf.POW_2_WIDTH, (byte) 10, (byte) 23, (byte) 0, (byte) 4, (byte) 2, (byte) 27, (byte) 14, (byte) 5, (byte) 20, (byte) 9, (byte) 22, (byte) 18, (byte) 11, (byte) 3, (byte) 25, (byte) 7, (byte) 15, (byte) 6, (byte) 26, (byte) 19, (byte) 12, (byte) 1, (byte) 40, (byte) 51, (byte) 30, (byte) 36, (byte) 46, (byte) 54, (byte) 29, (byte) 39, (byte) 50, (byte) 44, (byte) 32, (byte) 47, (byte) 43, (byte) 48, (byte) 38, (byte) 55, (byte) 33, (byte) 52, (byte) 45, (byte) 41, (byte) 49, (byte) 35, (byte) 28, (byte) 31};
    private static final byte[] totrot = new byte[]{(byte) 1, (byte) 2, (byte) 4, (byte) 6, (byte) 8, (byte) 10, (byte) 12, (byte) 14, (byte) 15, (byte) 17, (byte) 19, (byte) 21, (byte) 23, (byte) 25, (byte) 27, (byte) 28};
    private int[] workingKey = null;

    protected void desFunc(int[] iArr, byte[] bArr, int i, byte[] bArr2, int i2) {
        int i3 = ((((bArr[i + 0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i4 = ((((bArr[i + 4] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | ((bArr[i + 5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i + 6] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i + 7] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i5 = ((i3 >>> 4) ^ i4) & 252645135;
        i4 ^= i5;
        i3 ^= i5 << 4;
        i5 = ((i3 >>> 16) ^ i4) & 65535;
        i4 ^= i5;
        i3 ^= i5 << 16;
        i5 = ((i4 >>> 2) ^ i3) & 858993459;
        i3 ^= i5;
        i4 ^= i5 << 2;
        i5 = ((i4 >>> 8) ^ i3) & 16711935;
        i3 ^= i5;
        i4 ^= i5 << 8;
        i4 = ((i4 << 1) | ((i4 >>> 31) & 1)) & -1;
        i5 = (i3 ^ i4) & -1431655766;
        i3 ^= i5;
        i4 ^= i5;
        i3 = ((i3 << 1) | ((i3 >>> 31) & 1)) & -1;
        for (i5 = 0; i5 < 8; i5++) {
            int i6 = ((i4 << 28) | (i4 >>> 4)) ^ iArr[(i5 * 4) + 0];
            int i7 = SP7[i6 & 63];
            int i8 = SP5[(i6 >>> 8) & 63];
            int i9 = SP3[(i6 >>> 16) & 63];
            int i10 = iArr[(i5 * 4) + 1] ^ i4;
            i3 ^= ((((SP1[(i6 >>> 24) & 63] | ((i7 | i8) | i9)) | SP8[i10 & 63]) | SP6[(i10 >>> 8) & 63]) | SP4[(i10 >>> 16) & 63]) | SP2[(i10 >>> 24) & 63];
            i6 = ((i3 << 28) | (i3 >>> 4)) ^ iArr[(i5 * 4) + 2];
            i7 = SP7[i6 & 63];
            i8 = SP5[(i6 >>> 8) & 63];
            i9 = SP3[(i6 >>> 16) & 63];
            i10 = iArr[(i5 * 4) + 3] ^ i3;
            i4 ^= ((((SP1[(i6 >>> 24) & 63] | ((i7 | i8) | i9)) | SP8[i10 & 63]) | SP6[(i10 >>> 8) & 63]) | SP4[(i10 >>> 16) & 63]) | SP2[(i10 >>> 24) & 63];
        }
        i4 = (i4 << 31) | (i4 >>> 1);
        i5 = (i3 ^ i4) & -1431655766;
        i3 ^= i5;
        i4 ^= i5;
        i3 = (i3 << 31) | (i3 >>> 1);
        i5 = ((i3 >>> 8) ^ i4) & 16711935;
        i4 ^= i5;
        i3 ^= i5 << 8;
        i5 = ((i3 >>> 2) ^ i4) & 858993459;
        i4 ^= i5;
        i3 ^= i5 << 2;
        i5 = ((i4 >>> 16) ^ i3) & 65535;
        i3 ^= i5;
        i4 ^= i5 << 16;
        i5 = ((i4 >>> 4) ^ i3) & 252645135;
        i3 ^= i5;
        i4 ^= i5 << 4;
        bArr2[i2 + 0] = (byte) ((i4 >>> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 1] = (byte) ((i4 >>> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 2] = (byte) ((i4 >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 3] = (byte) (i4 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 4] = (byte) ((i3 >>> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 5] = (byte) ((i3 >>> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 6] = (byte) ((i3 >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr2[i2 + 7] = (byte) (i3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    protected int[] generateWorkingKey(boolean z, byte[] bArr) {
        int i;
        int[] iArr = new int[32];
        boolean[] zArr = new boolean[56];
        boolean[] zArr2 = new boolean[56];
        for (i = 0; i < 56; i++) {
            byte b = pc1[i];
            zArr[i] = (bytebit[b & 7] & bArr[b >>> 3]) != 0;
        }
        int i2 = 0;
        while (i2 < 16) {
            int i3 = z ? i2 << 1 : (15 - i2) << 1;
            int i4 = i3 + 1;
            iArr[i4] = 0;
            iArr[i3] = 0;
            for (i = 0; i < 28; i++) {
                int i5 = totrot[i2] + i;
                if (i5 < 28) {
                    zArr2[i] = zArr[i5];
                } else {
                    zArr2[i] = zArr[i5 - 28];
                }
            }
            for (i = 28; i < 56; i++) {
                i5 = totrot[i2] + i;
                if (i5 < 56) {
                    zArr2[i] = zArr[i5];
                } else {
                    zArr2[i] = zArr[i5 - 28];
                }
            }
            for (i = 0; i < 24; i++) {
                if (zArr2[pc2[i]]) {
                    iArr[i3] = iArr[i3] | bigbyte[i];
                }
                if (zArr2[pc2[i + 24]]) {
                    iArr[i4] = iArr[i4] | bigbyte[i];
                }
            }
            i2++;
        }
        for (i3 = 0; i3 != 32; i3 += 2) {
            i = iArr[i3];
            i2 = iArr[i3 + 1];
            iArr[i3] = ((((16515072 & i) << 6) | ((i & 4032) << 10)) | ((16515072 & i2) >>> 10)) | ((i2 & 4032) >>> 6);
            iArr[i3 + 1] = ((((i & 258048) << 12) | ((i & 63) << 16)) | ((258048 & i2) >>> 4)) | (i2 & 63);
        }
        return iArr;
    }

    public String getAlgorithmName() {
        return "DES";
    }

    public int getBlockSize() {
        return 8;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException("invalid parameter passed to DES init - " + cipherParameters.getClass().getName());
        } else if (((KeyParameter) cipherParameters).getKey().length > 8) {
            throw new IllegalArgumentException("DES key too long - should be 8 bytes");
        } else {
            this.workingKey = generateWorkingKey(z, ((KeyParameter) cipherParameters).getKey());
        }
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.workingKey == null) {
            throw new IllegalStateException("DES engine not initialised");
        } else if (i + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 8 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            desFunc(this.workingKey, bArr, i, bArr2, i2);
            return 8;
        }
    }

    public void reset() {
    }
}
