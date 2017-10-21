package org.spongycastle.crypto.engines;

import java.lang.reflect.Array;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.signers.PSSSigner;
import org.spongycastle.crypto.tls.CipherSuite;

public class AESLightEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    private static final byte[] S = new byte[]{(byte) 99, (byte) 124, (byte) 119, (byte) 123, (byte) -14, (byte) 107, (byte) 111, (byte) -59, (byte) 48, (byte) 1, (byte) 103, (byte) 43, (byte) -2, (byte) -41, (byte) -85, (byte) 118, (byte) -54, (byte) -126, (byte) -55, (byte) 125, (byte) -6, (byte) 89, (byte) 71, (byte) -16, (byte) -83, (byte) -44, (byte) -94, (byte) -81, (byte) -100, (byte) -92, (byte) 114, (byte) -64, (byte) -73, (byte) -3, (byte) -109, (byte) 38, (byte) 54, (byte) 63, (byte) -9, (byte) -52, (byte) 52, (byte) -91, (byte) -27, (byte) -15, (byte) 113, (byte) -40, (byte) 49, (byte) 21, (byte) 4, (byte) -57, (byte) 35, (byte) -61, (byte) 24, (byte) -106, (byte) 5, (byte) -102, (byte) 7, (byte) 18, Byte.MIN_VALUE, (byte) -30, (byte) -21, (byte) 39, (byte) -78, (byte) 117, (byte) 9, (byte) -125, (byte) 44, (byte) 26, (byte) 27, (byte) 110, (byte) 90, (byte) -96, (byte) 82, (byte) 59, (byte) -42, (byte) -77, (byte) 41, (byte) -29, (byte) 47, (byte) -124, (byte) 83, (byte) -47, (byte) 0, (byte) -19, (byte) 32, (byte) -4, (byte) -79, (byte) 91, (byte) 106, (byte) -53, (byte) -66, (byte) 57, (byte) 74, (byte) 76, (byte) 88, (byte) -49, (byte) -48, (byte) -17, (byte) -86, (byte) -5, (byte) 67, (byte) 77, (byte) 51, (byte) -123, (byte) 69, (byte) -7, (byte) 2, Byte.MAX_VALUE, (byte) 80, (byte) 60, (byte) -97, (byte) -88, (byte) 81, (byte) -93, (byte) 64, (byte) -113, (byte) -110, (byte) -99, (byte) 56, (byte) -11, PSSSigner.TRAILER_IMPLICIT, (byte) -74, (byte) -38, (byte) 33, Tnaf.POW_2_WIDTH, (byte) -1, (byte) -13, (byte) -46, (byte) -51, (byte) 12, (byte) 19, (byte) -20, (byte) 95, (byte) -105, (byte) 68, (byte) 23, (byte) -60, (byte) -89, (byte) 126, (byte) 61, (byte) 100, (byte) 93, (byte) 25, (byte) 115, (byte) 96, (byte) -127, (byte) 79, (byte) -36, (byte) 34, (byte) 42, (byte) -112, (byte) -120, (byte) 70, (byte) -18, (byte) -72, (byte) 20, (byte) -34, (byte) 94, (byte) 11, (byte) -37, (byte) -32, (byte) 50, (byte) 58, (byte) 10, (byte) 73, (byte) 6, (byte) 36, (byte) 92, (byte) -62, (byte) -45, (byte) -84, (byte) 98, (byte) -111, (byte) -107, (byte) -28, (byte) 121, (byte) -25, (byte) -56, (byte) 55, (byte) 109, (byte) -115, (byte) -43, (byte) 78, (byte) -87, (byte) 108, (byte) 86, (byte) -12, (byte) -22, (byte) 101, (byte) 122, (byte) -82, (byte) 8, (byte) -70, (byte) 120, (byte) 37, (byte) 46, (byte) 28, (byte) -90, (byte) -76, (byte) -58, (byte) -24, (byte) -35, (byte) 116, (byte) 31, (byte) 75, (byte) -67, (byte) -117, (byte) -118, (byte) 112, (byte) 62, (byte) -75, (byte) 102, (byte) 72, (byte) 3, (byte) -10, (byte) 14, (byte) 97, (byte) 53, (byte) 87, (byte) -71, (byte) -122, (byte) -63, (byte) 29, (byte) -98, (byte) -31, (byte) -8, (byte) -104, (byte) 17, (byte) 105, (byte) -39, (byte) -114, (byte) -108, (byte) -101, (byte) 30, (byte) -121, (byte) -23, (byte) -50, (byte) 85, (byte) 40, (byte) -33, (byte) -116, (byte) -95, (byte) -119, (byte) 13, (byte) -65, (byte) -26, (byte) 66, (byte) 104, (byte) 65, (byte) -103, (byte) 45, (byte) 15, (byte) -80, (byte) 84, (byte) -69, (byte) 22};
    private static final byte[] Si = new byte[]{(byte) 82, (byte) 9, (byte) 106, (byte) -43, (byte) 48, (byte) 54, (byte) -91, (byte) 56, (byte) -65, (byte) 64, (byte) -93, (byte) -98, (byte) -127, (byte) -13, (byte) -41, (byte) -5, (byte) 124, (byte) -29, (byte) 57, (byte) -126, (byte) -101, (byte) 47, (byte) -1, (byte) -121, (byte) 52, (byte) -114, (byte) 67, (byte) 68, (byte) -60, (byte) -34, (byte) -23, (byte) -53, (byte) 84, (byte) 123, (byte) -108, (byte) 50, (byte) -90, (byte) -62, (byte) 35, (byte) 61, (byte) -18, (byte) 76, (byte) -107, (byte) 11, (byte) 66, (byte) -6, (byte) -61, (byte) 78, (byte) 8, (byte) 46, (byte) -95, (byte) 102, (byte) 40, (byte) -39, (byte) 36, (byte) -78, (byte) 118, (byte) 91, (byte) -94, (byte) 73, (byte) 109, (byte) -117, (byte) -47, (byte) 37, (byte) 114, (byte) -8, (byte) -10, (byte) 100, (byte) -122, (byte) 104, (byte) -104, (byte) 22, (byte) -44, (byte) -92, (byte) 92, (byte) -52, (byte) 93, (byte) 101, (byte) -74, (byte) -110, (byte) 108, (byte) 112, (byte) 72, (byte) 80, (byte) -3, (byte) -19, (byte) -71, (byte) -38, (byte) 94, (byte) 21, (byte) 70, (byte) 87, (byte) -89, (byte) -115, (byte) -99, (byte) -124, (byte) -112, (byte) -40, (byte) -85, (byte) 0, (byte) -116, PSSSigner.TRAILER_IMPLICIT, (byte) -45, (byte) 10, (byte) -9, (byte) -28, (byte) 88, (byte) 5, (byte) -72, (byte) -77, (byte) 69, (byte) 6, (byte) -48, (byte) 44, (byte) 30, (byte) -113, (byte) -54, (byte) 63, (byte) 15, (byte) 2, (byte) -63, (byte) -81, (byte) -67, (byte) 3, (byte) 1, (byte) 19, (byte) -118, (byte) 107, (byte) 58, (byte) -111, (byte) 17, (byte) 65, (byte) 79, (byte) 103, (byte) -36, (byte) -22, (byte) -105, (byte) -14, (byte) -49, (byte) -50, (byte) -16, (byte) -76, (byte) -26, (byte) 115, (byte) -106, (byte) -84, (byte) 116, (byte) 34, (byte) -25, (byte) -83, (byte) 53, (byte) -123, (byte) -30, (byte) -7, (byte) 55, (byte) -24, (byte) 28, (byte) 117, (byte) -33, (byte) 110, (byte) 71, (byte) -15, (byte) 26, (byte) 113, (byte) 29, (byte) 41, (byte) -59, (byte) -119, (byte) 111, (byte) -73, (byte) 98, (byte) 14, (byte) -86, (byte) 24, (byte) -66, (byte) 27, (byte) -4, (byte) 86, (byte) 62, (byte) 75, (byte) -58, (byte) -46, (byte) 121, (byte) 32, (byte) -102, (byte) -37, (byte) -64, (byte) -2, (byte) 120, (byte) -51, (byte) 90, (byte) -12, (byte) 31, (byte) -35, (byte) -88, (byte) 51, (byte) -120, (byte) 7, (byte) -57, (byte) 49, (byte) -79, (byte) 18, Tnaf.POW_2_WIDTH, (byte) 89, (byte) 39, Byte.MIN_VALUE, (byte) -20, (byte) 95, (byte) 96, (byte) 81, Byte.MAX_VALUE, (byte) -87, (byte) 25, (byte) -75, (byte) 74, (byte) 13, (byte) 45, (byte) -27, (byte) 122, (byte) -97, (byte) -109, (byte) -55, (byte) -100, (byte) -17, (byte) -96, (byte) -32, (byte) 59, (byte) 77, (byte) -82, (byte) 42, (byte) -11, (byte) -80, (byte) -56, (byte) -21, (byte) -69, (byte) 60, (byte) -125, (byte) 83, (byte) -103, (byte) 97, (byte) 23, (byte) 43, (byte) 4, (byte) 126, (byte) -70, (byte) 119, (byte) -42, (byte) 38, (byte) -31, (byte) 105, (byte) 20, (byte) 99, (byte) 85, (byte) 33, (byte) 12, (byte) 125};
    private static final int m1 = -2139062144;
    private static final int m2 = 2139062143;
    private static final int m3 = 27;
    private static final int[] rcon = new int[]{1, 2, 4, 8, 16, 32, 64, 128, 27, 54, EACTags.CARDHOLDER_IMAGE_TEMPLATE, 216, 171, 77, 154, 47, 94, 188, 99, 198, 151, 53, 106, 212, 179, EACTags.SECURE_MESSAGING_TEMPLATE, 250, 239, 197, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA};
    private int C0;
    private int C1;
    private int C2;
    private int C3;
    private int ROUNDS;
    private int[][] WorkingKey = null;
    private boolean forEncryption;

    private int FFmulX(int i) {
        return ((m2 & i) << 1) ^ (((m1 & i) >>> 7) * 27);
    }

    private void decryptBlock(int[][] iArr) {
        int inv_mcol;
        int inv_mcol2;
        int inv_mcol3;
        this.C0 ^= iArr[this.ROUNDS][0];
        this.C1 ^= iArr[this.ROUNDS][1];
        this.C2 ^= iArr[this.ROUNDS][2];
        this.C3 ^= iArr[this.ROUNDS][3];
        int i = this.ROUNDS - 1;
        while (i > 1) {
            inv_mcol = inv_mcol((((Si[this.C0 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C1 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][0];
            inv_mcol2 = inv_mcol((((Si[this.C1 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C0 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][1];
            inv_mcol3 = inv_mcol((((Si[this.C2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C1 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C0 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][2];
            int i2 = i - 1;
            i = iArr[i][3] ^ inv_mcol((((Si[this.C3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C1 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C0 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
            this.C0 = inv_mcol((((Si[inv_mcol & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(i >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][0];
            this.C1 = inv_mcol((((Si[inv_mcol2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(i >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][1];
            this.C2 = inv_mcol((((Si[inv_mcol3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(i >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][2];
            inv_mcol = inv_mcol((((Si[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
            i = i2 - 1;
            this.C3 = inv_mcol ^ iArr[i2][3];
        }
        inv_mcol = inv_mcol((((Si[this.C0 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C1 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][0];
        inv_mcol2 = inv_mcol((((Si[this.C1 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C0 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][1];
        inv_mcol3 = inv_mcol((((Si[this.C2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C1 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C0 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][2];
        i = iArr[i][3] ^ inv_mcol((((Si[this.C3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(this.C2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(this.C1 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(this.C0 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
        this.C0 = ((((Si[inv_mcol & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(i >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[0][0];
        this.C1 = ((((Si[inv_mcol2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(i >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[0][1];
        this.C2 = ((((Si[inv_mcol3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(i >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[0][2];
        this.C3 = ((((Si[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((Si[(inv_mcol3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((Si[(inv_mcol2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (Si[(inv_mcol >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[0][3];
    }

    private void encryptBlock(int[][] iArr) {
        int mcol;
        int mcol2;
        int mcol3;
        int i;
        this.C0 ^= iArr[0][0];
        this.C1 ^= iArr[0][1];
        this.C2 ^= iArr[0][2];
        this.C3 ^= iArr[0][3];
        int i2 = 1;
        while (i2 < this.ROUNDS - 1) {
            mcol = mcol((((S[this.C0 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C1 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][0];
            mcol2 = mcol((((S[this.C1 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C0 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][1];
            mcol3 = mcol((((S[this.C2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C0 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C1 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][2];
            i = i2 + 1;
            i2 = iArr[i2][3] ^ mcol((((S[this.C3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C0 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C1 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
            this.C0 = mcol((((S[mcol & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(i2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][0];
            this.C1 = mcol((((S[mcol2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(i2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][1];
            this.C2 = mcol((((S[mcol3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(i2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][2];
            mcol = mcol((((S[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
            i2 = i + 1;
            this.C3 = mcol ^ iArr[i][3];
        }
        mcol = mcol((((S[this.C0 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C1 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][0];
        mcol2 = mcol((((S[this.C1 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C0 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][1];
        mcol3 = mcol((((S[this.C2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C0 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C1 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i2][2];
        i = i2 + 1;
        i2 = iArr[i2][3] ^ mcol((((S[this.C3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(this.C0 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(this.C1 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(this.C2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
        this.C0 = ((((S[mcol & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol3 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(i2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][0];
        this.C1 = iArr[i][1] ^ ((((S[mcol2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol3 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(i2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24));
        this.C2 = ((((S[mcol3 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(i2 >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol2 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][2];
        this.C3 = ((((S[i2 & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) ^ ((S[(mcol >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) ^ ((S[(mcol2 >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) ^ (S[(mcol3 >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24)) ^ iArr[i][3];
    }

    private int[][] generateWorkingKey(byte[] bArr, boolean z) {
        int length = bArr.length / 4;
        if ((length == 4 || length == 6 || length == 8) && length * 4 == bArr.length) {
            this.ROUNDS = length + 6;
            int i = this.ROUNDS;
            int[][] iArr = (int[][]) Array.newInstance(Integer.TYPE, new int[]{i + 1, 4});
            int i2 = 0;
            int i3 = 0;
            while (i2 < bArr.length) {
                iArr[i3 >> 2][i3 & 3] = (((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((bArr[i2 + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | ((bArr[i2 + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | (bArr[i2 + 3] << 24);
                i2 += 4;
                i3++;
            }
            int i4 = this.ROUNDS;
            i3 = length;
            while (i3 < ((i4 + 1) << 2)) {
                i2 = iArr[(i3 - 1) >> 2][(i3 - 1) & 3];
                if (i3 % length == 0) {
                    i2 = subWord(shift(i2, 8)) ^ rcon[(i3 / length) - 1];
                } else if (length > 6 && i3 % length == 4) {
                    i2 = subWord(i2);
                }
                iArr[i3 >> 2][i3 & 3] = i2 ^ iArr[(i3 - length) >> 2][(i3 - length) & 3];
                i3++;
            }
            if (!z) {
                for (i2 = 1; i2 < this.ROUNDS; i2++) {
                    for (i3 = 0; i3 < 4; i3++) {
                        iArr[i2][i3] = inv_mcol(iArr[i2][i3]);
                    }
                }
            }
            return iArr;
        }
        throw new IllegalArgumentException("Key length not 128/192/256 bits.");
    }

    private int inv_mcol(int i) {
        int FFmulX = FFmulX(i);
        int FFmulX2 = FFmulX(FFmulX);
        int FFmulX3 = FFmulX(FFmulX2);
        int i2 = i ^ FFmulX3;
        return ((((FFmulX ^ FFmulX2) ^ FFmulX3) ^ shift(FFmulX ^ i2, 8)) ^ shift(FFmulX2 ^ i2, 16)) ^ shift(i2, 24);
    }

    private int mcol(int i) {
        int FFmulX = FFmulX(i);
        return ((FFmulX ^ shift(i ^ FFmulX, 8)) ^ shift(i, 16)) ^ shift(i, 24);
    }

    private void packBlock(byte[] bArr, int i) {
        int i2 = i + 1;
        bArr[i] = (byte) this.C0;
        int i3 = i2 + 1;
        bArr[i2] = (byte) (this.C0 >> 8);
        i2 = i3 + 1;
        bArr[i3] = (byte) (this.C0 >> 16);
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C0 >> 24);
        i2 = i3 + 1;
        bArr[i3] = (byte) this.C1;
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C1 >> 8);
        i2 = i3 + 1;
        bArr[i3] = (byte) (this.C1 >> 16);
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C1 >> 24);
        i2 = i3 + 1;
        bArr[i3] = (byte) this.C2;
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C2 >> 8);
        i2 = i3 + 1;
        bArr[i3] = (byte) (this.C2 >> 16);
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C2 >> 24);
        i2 = i3 + 1;
        bArr[i3] = (byte) this.C3;
        i3 = i2 + 1;
        bArr[i2] = (byte) (this.C3 >> 8);
        bArr[i3] = (byte) (this.C3 >> 16);
        bArr[i3 + 1] = (byte) (this.C3 >> 24);
    }

    private int shift(int i, int i2) {
        return (i >>> i2) | (i << (-i2));
    }

    private int subWord(int i) {
        return (((S[i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) | ((S[(i >> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | ((S[(i >> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | (S[(i >> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV] << 24);
    }

    private void unpackBlock(byte[] bArr, int i) {
        int i2 = i + 1;
        this.C0 = bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        int i3 = i2 + 1;
        this.C0 = ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | this.C0;
        int i4 = i3 + 1;
        this.C0 |= (bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16;
        i3 = i4 + 1;
        this.C0 |= bArr[i4] << 24;
        i2 = i3 + 1;
        this.C1 = bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        i3 = i2 + 1;
        this.C1 = ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | this.C1;
        i4 = i3 + 1;
        this.C1 |= (bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16;
        i3 = i4 + 1;
        this.C1 |= bArr[i4] << 24;
        i2 = i3 + 1;
        this.C2 = bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        i3 = i2 + 1;
        this.C2 = ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | this.C2;
        i4 = i3 + 1;
        this.C2 |= (bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16;
        i3 = i4 + 1;
        this.C2 |= bArr[i4] << 24;
        i2 = i3 + 1;
        this.C3 = bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
        i3 = i2 + 1;
        this.C3 = ((bArr[i2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8) | this.C3;
        this.C3 |= (bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16;
        this.C3 |= bArr[i3 + 1] << 24;
    }

    public String getAlgorithmName() {
        return "AES";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.WorkingKey = generateWorkingKey(((KeyParameter) cipherParameters).getKey(), z);
            this.forEncryption = z;
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to AES init - " + cipherParameters.getClass().getName());
    }

    public int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.WorkingKey == null) {
            throw new IllegalStateException("AES engine not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            if (this.forEncryption) {
                unpackBlock(bArr, i);
                encryptBlock(this.WorkingKey);
                packBlock(bArr2, i2);
            } else {
                unpackBlock(bArr, i);
                decryptBlock(this.WorkingKey);
                packBlock(bArr2, i2);
            }
            return 16;
        }
    }

    public void reset() {
    }
}
