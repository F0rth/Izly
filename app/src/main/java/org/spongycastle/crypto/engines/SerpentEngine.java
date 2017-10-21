package org.spongycastle.crypto.engines;

import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.tls.CipherSuite;

public class SerpentEngine implements BlockCipher {
    private static final int BLOCK_SIZE = 16;
    static final int PHI = -1640531527;
    static final int ROUNDS = 32;
    private int X0;
    private int X1;
    private int X2;
    private int X3;
    private boolean encrypting;
    private int[] wKey;

    private void LT() {
        int rotateLeft = rotateLeft(this.X0, 13);
        int rotateLeft2 = rotateLeft(this.X2, 3);
        int i = this.X1;
        int i2 = this.X3;
        this.X1 = rotateLeft((i ^ rotateLeft) ^ rotateLeft2, 1);
        this.X3 = rotateLeft((i2 ^ rotateLeft2) ^ (rotateLeft << 3), 7);
        this.X0 = rotateLeft((rotateLeft ^ this.X1) ^ this.X3, 5);
        this.X2 = rotateLeft((this.X3 ^ rotateLeft2) ^ (this.X1 << 7), 22);
    }

    private int bytesToWord(byte[] bArr, int i) {
        return ((((bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 24) | ((bArr[i + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((bArr[i + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 8)) | (bArr[i + 3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    private void decryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.X3 = this.wKey[Wbxml.STR_T] ^ bytesToWord(bArr, i);
        this.X2 = this.wKey[Wbxml.EXT_T_2] ^ bytesToWord(bArr, i + 4);
        this.X1 = this.wKey[Wbxml.EXT_T_1] ^ bytesToWord(bArr, i + 8);
        this.X0 = this.wKey[128] ^ bytesToWord(bArr, i + 12);
        ib7(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE];
        this.X1 ^= this.wKey[EACTags.SECURE_MESSAGING_TEMPLATE];
        this.X2 ^= this.wKey[EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE];
        this.X3 ^= this.wKey[CertificateBody.profileType];
        inverseLT();
        ib6(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[120];
        this.X1 ^= this.wKey[EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY];
        this.X2 ^= this.wKey[EACTags.SECURITY_SUPPORT_TEMPLATE];
        this.X3 ^= this.wKey[EACTags.SECURITY_ENVIRONMENT_TEMPLATE];
        inverseLT();
        ib5(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[116];
        this.X1 ^= this.wKey[117];
        this.X2 ^= this.wKey[118];
        this.X3 ^= this.wKey[119];
        inverseLT();
        ib4(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[112];
        this.X1 ^= this.wKey[113];
        this.X2 ^= this.wKey[114];
        this.X3 ^= this.wKey[EACTags.DISCRETIONARY_DATA_OBJECTS];
        inverseLT();
        ib3(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[EACTags.CARDHOLDER_IMAGE_TEMPLATE];
        this.X1 ^= this.wKey[EACTags.APPLICATION_IMAGE_TEMPLATE];
        this.X2 ^= this.wKey[110];
        this.X3 ^= this.wKey[EACTags.FCI_TEMPLATE];
        inverseLT();
        ib2(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[104];
        this.X1 ^= this.wKey[105];
        this.X2 ^= this.wKey[106];
        this.X3 ^= this.wKey[EACTags.QUALIFIED_NAME];
        inverseLT();
        ib1(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[100];
        this.X1 ^= this.wKey[101];
        this.X2 ^= this.wKey[102];
        this.X3 ^= this.wKey[103];
        inverseLT();
        ib0(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[96];
        this.X1 ^= this.wKey[97];
        this.X2 ^= this.wKey[98];
        this.X3 ^= this.wKey[99];
        inverseLT();
        ib7(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[92];
        this.X1 ^= this.wKey[93];
        this.X2 ^= this.wKey[94];
        this.X3 ^= this.wKey[95];
        inverseLT();
        ib6(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[88];
        this.X1 ^= this.wKey[89];
        this.X2 ^= this.wKey[90];
        this.X3 ^= this.wKey[91];
        inverseLT();
        ib5(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[84];
        this.X1 ^= this.wKey[85];
        this.X2 ^= this.wKey[86];
        this.X3 ^= this.wKey[87];
        inverseLT();
        ib4(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[80];
        this.X1 ^= this.wKey[81];
        this.X2 ^= this.wKey[82];
        this.X3 ^= this.wKey[83];
        inverseLT();
        ib3(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[76];
        this.X1 ^= this.wKey[77];
        this.X2 ^= this.wKey[78];
        this.X3 ^= this.wKey[79];
        inverseLT();
        ib2(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[72];
        this.X1 ^= this.wKey[73];
        this.X2 ^= this.wKey[74];
        this.X3 ^= this.wKey[75];
        inverseLT();
        ib1(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[68];
        this.X1 ^= this.wKey[69];
        this.X2 ^= this.wKey[70];
        this.X3 ^= this.wKey[71];
        inverseLT();
        ib0(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[64];
        this.X1 ^= this.wKey[65];
        this.X2 ^= this.wKey[66];
        this.X3 ^= this.wKey[67];
        inverseLT();
        ib7(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[60];
        this.X1 ^= this.wKey[61];
        this.X2 ^= this.wKey[62];
        this.X3 ^= this.wKey[63];
        inverseLT();
        ib6(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[56];
        this.X1 ^= this.wKey[57];
        this.X2 ^= this.wKey[58];
        this.X3 ^= this.wKey[59];
        inverseLT();
        ib5(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[52];
        this.X1 ^= this.wKey[53];
        this.X2 ^= this.wKey[54];
        this.X3 ^= this.wKey[55];
        inverseLT();
        ib4(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[48];
        this.X1 ^= this.wKey[49];
        this.X2 ^= this.wKey[50];
        this.X3 ^= this.wKey[51];
        inverseLT();
        ib3(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[44];
        this.X1 ^= this.wKey[45];
        this.X2 ^= this.wKey[46];
        this.X3 ^= this.wKey[47];
        inverseLT();
        ib2(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[40];
        this.X1 ^= this.wKey[41];
        this.X2 ^= this.wKey[42];
        this.X3 ^= this.wKey[43];
        inverseLT();
        ib1(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[36];
        this.X1 ^= this.wKey[37];
        this.X2 ^= this.wKey[38];
        this.X3 ^= this.wKey[39];
        inverseLT();
        ib0(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[32];
        this.X1 ^= this.wKey[33];
        this.X2 ^= this.wKey[34];
        this.X3 ^= this.wKey[35];
        inverseLT();
        ib7(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[28];
        this.X1 ^= this.wKey[29];
        this.X2 ^= this.wKey[30];
        this.X3 ^= this.wKey[31];
        inverseLT();
        ib6(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[24];
        this.X1 ^= this.wKey[25];
        this.X2 ^= this.wKey[26];
        this.X3 ^= this.wKey[27];
        inverseLT();
        ib5(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[20];
        this.X1 ^= this.wKey[21];
        this.X2 ^= this.wKey[22];
        this.X3 ^= this.wKey[23];
        inverseLT();
        ib4(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[16];
        this.X1 ^= this.wKey[17];
        this.X2 ^= this.wKey[18];
        this.X3 ^= this.wKey[19];
        inverseLT();
        ib3(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[12];
        this.X1 ^= this.wKey[13];
        this.X2 ^= this.wKey[14];
        this.X3 ^= this.wKey[15];
        inverseLT();
        ib2(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[8];
        this.X1 ^= this.wKey[9];
        this.X2 ^= this.wKey[10];
        this.X3 ^= this.wKey[11];
        inverseLT();
        ib1(this.X0, this.X1, this.X2, this.X3);
        this.X0 ^= this.wKey[4];
        this.X1 ^= this.wKey[5];
        this.X2 ^= this.wKey[6];
        this.X3 ^= this.wKey[7];
        inverseLT();
        ib0(this.X0, this.X1, this.X2, this.X3);
        wordToBytes(this.X3 ^ this.wKey[3], bArr2, i2);
        wordToBytes(this.X2 ^ this.wKey[2], bArr2, i2 + 4);
        wordToBytes(this.X1 ^ this.wKey[1], bArr2, i2 + 8);
        wordToBytes(this.X0 ^ this.wKey[0], bArr2, i2 + 12);
    }

    private void encryptBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        this.X3 = bytesToWord(bArr, i);
        this.X2 = bytesToWord(bArr, i + 4);
        this.X1 = bytesToWord(bArr, i + 8);
        this.X0 = bytesToWord(bArr, i + 12);
        sb0(this.wKey[0] ^ this.X0, this.wKey[1] ^ this.X1, this.wKey[2] ^ this.X2, this.wKey[3] ^ this.X3);
        LT();
        sb1(this.wKey[4] ^ this.X0, this.wKey[5] ^ this.X1, this.wKey[6] ^ this.X2, this.wKey[7] ^ this.X3);
        LT();
        sb2(this.wKey[8] ^ this.X0, this.wKey[9] ^ this.X1, this.wKey[10] ^ this.X2, this.wKey[11] ^ this.X3);
        LT();
        sb3(this.wKey[12] ^ this.X0, this.wKey[13] ^ this.X1, this.wKey[14] ^ this.X2, this.wKey[15] ^ this.X3);
        LT();
        sb4(this.wKey[16] ^ this.X0, this.wKey[17] ^ this.X1, this.wKey[18] ^ this.X2, this.wKey[19] ^ this.X3);
        LT();
        sb5(this.wKey[20] ^ this.X0, this.wKey[21] ^ this.X1, this.wKey[22] ^ this.X2, this.wKey[23] ^ this.X3);
        LT();
        sb6(this.wKey[24] ^ this.X0, this.wKey[25] ^ this.X1, this.wKey[26] ^ this.X2, this.wKey[27] ^ this.X3);
        LT();
        sb7(this.wKey[28] ^ this.X0, this.wKey[29] ^ this.X1, this.wKey[30] ^ this.X2, this.wKey[31] ^ this.X3);
        LT();
        sb0(this.wKey[32] ^ this.X0, this.wKey[33] ^ this.X1, this.wKey[34] ^ this.X2, this.wKey[35] ^ this.X3);
        LT();
        sb1(this.wKey[36] ^ this.X0, this.wKey[37] ^ this.X1, this.wKey[38] ^ this.X2, this.wKey[39] ^ this.X3);
        LT();
        sb2(this.wKey[40] ^ this.X0, this.wKey[41] ^ this.X1, this.wKey[42] ^ this.X2, this.wKey[43] ^ this.X3);
        LT();
        sb3(this.wKey[44] ^ this.X0, this.wKey[45] ^ this.X1, this.wKey[46] ^ this.X2, this.wKey[47] ^ this.X3);
        LT();
        sb4(this.wKey[48] ^ this.X0, this.wKey[49] ^ this.X1, this.wKey[50] ^ this.X2, this.wKey[51] ^ this.X3);
        LT();
        sb5(this.wKey[52] ^ this.X0, this.wKey[53] ^ this.X1, this.wKey[54] ^ this.X2, this.wKey[55] ^ this.X3);
        LT();
        sb6(this.wKey[56] ^ this.X0, this.wKey[57] ^ this.X1, this.wKey[58] ^ this.X2, this.wKey[59] ^ this.X3);
        LT();
        sb7(this.wKey[60] ^ this.X0, this.wKey[61] ^ this.X1, this.wKey[62] ^ this.X2, this.wKey[63] ^ this.X3);
        LT();
        sb0(this.wKey[64] ^ this.X0, this.wKey[65] ^ this.X1, this.wKey[66] ^ this.X2, this.wKey[67] ^ this.X3);
        LT();
        sb1(this.wKey[68] ^ this.X0, this.wKey[69] ^ this.X1, this.wKey[70] ^ this.X2, this.wKey[71] ^ this.X3);
        LT();
        sb2(this.wKey[72] ^ this.X0, this.wKey[73] ^ this.X1, this.wKey[74] ^ this.X2, this.wKey[75] ^ this.X3);
        LT();
        sb3(this.wKey[76] ^ this.X0, this.wKey[77] ^ this.X1, this.wKey[78] ^ this.X2, this.wKey[79] ^ this.X3);
        LT();
        sb4(this.wKey[80] ^ this.X0, this.wKey[81] ^ this.X1, this.wKey[82] ^ this.X2, this.wKey[83] ^ this.X3);
        LT();
        sb5(this.wKey[84] ^ this.X0, this.wKey[85] ^ this.X1, this.wKey[86] ^ this.X2, this.wKey[87] ^ this.X3);
        LT();
        sb6(this.wKey[88] ^ this.X0, this.wKey[89] ^ this.X1, this.wKey[90] ^ this.X2, this.wKey[91] ^ this.X3);
        LT();
        sb7(this.wKey[92] ^ this.X0, this.wKey[93] ^ this.X1, this.wKey[94] ^ this.X2, this.wKey[95] ^ this.X3);
        LT();
        sb0(this.wKey[96] ^ this.X0, this.wKey[97] ^ this.X1, this.wKey[98] ^ this.X2, this.wKey[99] ^ this.X3);
        LT();
        sb1(this.wKey[100] ^ this.X0, this.wKey[101] ^ this.X1, this.wKey[102] ^ this.X2, this.wKey[103] ^ this.X3);
        LT();
        sb2(this.wKey[104] ^ this.X0, this.wKey[105] ^ this.X1, this.wKey[106] ^ this.X2, this.wKey[EACTags.QUALIFIED_NAME] ^ this.X3);
        LT();
        sb3(this.wKey[EACTags.CARDHOLDER_IMAGE_TEMPLATE] ^ this.X0, this.wKey[EACTags.APPLICATION_IMAGE_TEMPLATE] ^ this.X1, this.wKey[110] ^ this.X2, this.wKey[EACTags.FCI_TEMPLATE] ^ this.X3);
        LT();
        sb4(this.wKey[112] ^ this.X0, this.wKey[113] ^ this.X1, this.wKey[114] ^ this.X2, this.wKey[EACTags.DISCRETIONARY_DATA_OBJECTS] ^ this.X3);
        LT();
        sb5(this.wKey[116] ^ this.X0, this.wKey[117] ^ this.X1, this.wKey[118] ^ this.X2, this.wKey[119] ^ this.X3);
        LT();
        sb6(this.wKey[120] ^ this.X0, this.wKey[EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY] ^ this.X1, this.wKey[EACTags.SECURITY_SUPPORT_TEMPLATE] ^ this.X2, this.wKey[EACTags.SECURITY_ENVIRONMENT_TEMPLATE] ^ this.X3);
        LT();
        sb7(this.wKey[EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE] ^ this.X0, this.wKey[EACTags.SECURE_MESSAGING_TEMPLATE] ^ this.X1, this.wKey[EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE] ^ this.X2, this.wKey[CertificateBody.profileType] ^ this.X3);
        wordToBytes(this.wKey[Wbxml.STR_T] ^ this.X3, bArr2, i2);
        wordToBytes(this.wKey[Wbxml.EXT_T_2] ^ this.X2, bArr2, i2 + 4);
        wordToBytes(this.wKey[Wbxml.EXT_T_1] ^ this.X1, bArr2, i2 + 8);
        wordToBytes(this.wKey[128] ^ this.X0, bArr2, i2 + 12);
    }

    private void ib0(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = (i5 | i6) ^ i4;
        int i8 = i3 ^ i7;
        this.X2 = i6 ^ i8;
        i5 ^= i6 & i4;
        this.X1 = (this.X2 & i5) ^ i7;
        this.X3 = (i & i7) ^ (this.X1 | i8);
        this.X0 = (i5 ^ i8) ^ this.X3;
    }

    private void ib1(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i4;
        int i6 = (i2 & i5) ^ i;
        int i7 = i5 ^ i6;
        this.X3 = i3 ^ i7;
        i5 = (i5 & i6) ^ i2;
        this.X1 = i6 ^ (this.X3 | i5);
        i6 = this.X1 ^ -1;
        i5 ^= this.X3;
        this.X0 = i6 ^ i5;
        this.X2 = (i5 | i6) ^ i7;
    }

    private void ib2(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i4;
        int i6 = i ^ i3;
        int i7 = i3 ^ i5;
        this.X0 = (i2 & i7) ^ i6;
        this.X3 = i5 ^ ((((i5 ^ -1) | i) ^ i4) | i6);
        i5 = i7 ^ -1;
        i7 = this.X0 | this.X3;
        this.X1 = i5 ^ i7;
        this.X2 = (i5 & i4) ^ (i6 ^ i7);
    }

    private void ib3(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i3;
        int i6 = (i2 & i5) ^ i;
        int i7 = i4 | i6;
        this.X0 = i5 ^ i7;
        i5 = (i5 | i7) ^ i4;
        this.X2 = (i3 ^ i6) ^ i5;
        i5 ^= i | i2;
        this.X3 = i6 ^ (this.X0 & i5);
        this.X1 = (i5 ^ this.X0) ^ this.X3;
    }

    private void ib4(int i, int i2, int i3, int i4) {
        int i5 = ((i3 | i4) & i) ^ i2;
        int i6 = (i & i5) ^ i3;
        this.X1 = i4 ^ i6;
        int i7 = i ^ -1;
        this.X3 = (i6 & this.X1) ^ i5;
        i6 = (this.X1 | i7) ^ i4;
        this.X0 = this.X3 ^ i6;
        this.X2 = (i5 & i6) ^ (this.X1 ^ i7);
    }

    private void ib5(int i, int i2, int i3, int i4) {
        int i5 = i3 ^ -1;
        int i6 = (i2 & i5) ^ i4;
        int i7 = i & i6;
        this.X3 = (i2 ^ i5) ^ i7;
        int i8 = this.X3 | i2;
        this.X1 = i6 ^ (i & i8);
        i6 = i | i4;
        this.X0 = (i5 ^ i8) ^ i6;
        this.X2 = (i2 & i6) ^ ((i ^ i3) | i7);
    }

    private void ib6(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i3 ^ i6;
        int i8 = (i3 | i5) ^ i4;
        this.X1 = i7 ^ i8;
        i6 ^= i7 & i8;
        this.X3 = i8 ^ (i2 | i6);
        i8 = this.X3 | i2;
        this.X0 = i6 ^ i8;
        this.X2 = (i5 & i4) ^ (i7 ^ i8);
    }

    private void ib7(int i, int i2, int i3, int i4) {
        int i5 = (i & i2) | i3;
        int i6 = (i | i2) & i4;
        this.X3 = i5 ^ i6;
        i6 ^= i2;
        this.X1 = (((i4 ^ -1) ^ this.X3) | i6) ^ i;
        this.X0 = (i6 ^ i3) ^ (this.X1 | i4);
        this.X2 = (i5 ^ this.X1) ^ (this.X0 ^ (this.X3 & i));
    }

    private void inverseLT() {
        int rotateRight = (rotateRight(this.X2, 22) ^ this.X3) ^ (this.X1 << 7);
        int rotateRight2 = (rotateRight(this.X0, 5) ^ this.X1) ^ this.X3;
        int rotateRight3 = rotateRight(this.X3, 7);
        int rotateRight4 = rotateRight(this.X1, 1);
        this.X3 = (rotateRight3 ^ rotateRight) ^ (rotateRight2 << 3);
        this.X1 = (rotateRight4 ^ rotateRight2) ^ rotateRight;
        this.X2 = rotateRight(rotateRight, 3);
        this.X0 = rotateRight(rotateRight2, 13);
    }

    private int[] makeWorkingKey(byte[] bArr) throws IllegalArgumentException {
        Object obj = new int[16];
        int length = bArr.length - 4;
        int i = 0;
        while (length > 0) {
            obj[i] = bytesToWord(bArr, length);
            length -= 4;
            i++;
        }
        if (length == 0) {
            length = i + 1;
            obj[i] = bytesToWord(bArr, 0);
            if (length < 8) {
                obj[length] = 1;
            }
            Object obj2 = new int[Wbxml.LITERAL_A];
            for (length = 8; length < 16; length++) {
                obj[length] = rotateLeft(((((obj[length - 8] ^ obj[length - 5]) ^ obj[length - 3]) ^ obj[length - 1]) ^ PHI) ^ (length - 8), 11);
            }
            System.arraycopy(obj, 8, obj2, 0, 8);
            for (length = 8; length < Wbxml.LITERAL_A; length++) {
                obj2[length] = rotateLeft(((((obj2[length - 8] ^ obj2[length - 5]) ^ obj2[length - 3]) ^ obj2[length - 1]) ^ PHI) ^ length, 11);
            }
            sb3(obj2[0], obj2[1], obj2[2], obj2[3]);
            obj2[0] = this.X0;
            obj2[1] = this.X1;
            obj2[2] = this.X2;
            obj2[3] = this.X3;
            sb2(obj2[4], obj2[5], obj2[6], obj2[7]);
            obj2[4] = this.X0;
            obj2[5] = this.X1;
            obj2[6] = this.X2;
            obj2[7] = this.X3;
            sb1(obj2[8], obj2[9], obj2[10], obj2[11]);
            obj2[8] = this.X0;
            obj2[9] = this.X1;
            obj2[10] = this.X2;
            obj2[11] = this.X3;
            sb0(obj2[12], obj2[13], obj2[14], obj2[15]);
            obj2[12] = this.X0;
            obj2[13] = this.X1;
            obj2[14] = this.X2;
            obj2[15] = this.X3;
            sb7(obj2[16], obj2[17], obj2[18], obj2[19]);
            obj2[16] = this.X0;
            obj2[17] = this.X1;
            obj2[18] = this.X2;
            obj2[19] = this.X3;
            sb6(obj2[20], obj2[21], obj2[22], obj2[23]);
            obj2[20] = this.X0;
            obj2[21] = this.X1;
            obj2[22] = this.X2;
            obj2[23] = this.X3;
            sb5(obj2[24], obj2[25], obj2[26], obj2[27]);
            obj2[24] = this.X0;
            obj2[25] = this.X1;
            obj2[26] = this.X2;
            obj2[27] = this.X3;
            sb4(obj2[28], obj2[29], obj2[30], obj2[31]);
            obj2[28] = this.X0;
            obj2[29] = this.X1;
            obj2[30] = this.X2;
            obj2[31] = this.X3;
            sb3(obj2[32], obj2[33], obj2[34], obj2[35]);
            obj2[32] = this.X0;
            obj2[33] = this.X1;
            obj2[34] = this.X2;
            obj2[35] = this.X3;
            sb2(obj2[36], obj2[37], obj2[38], obj2[39]);
            obj2[36] = this.X0;
            obj2[37] = this.X1;
            obj2[38] = this.X2;
            obj2[39] = this.X3;
            sb1(obj2[40], obj2[41], obj2[42], obj2[43]);
            obj2[40] = this.X0;
            obj2[41] = this.X1;
            obj2[42] = this.X2;
            obj2[43] = this.X3;
            sb0(obj2[44], obj2[45], obj2[46], obj2[47]);
            obj2[44] = this.X0;
            obj2[45] = this.X1;
            obj2[46] = this.X2;
            obj2[47] = this.X3;
            sb7(obj2[48], obj2[49], obj2[50], obj2[51]);
            obj2[48] = this.X0;
            obj2[49] = this.X1;
            obj2[50] = this.X2;
            obj2[51] = this.X3;
            sb6(obj2[52], obj2[53], obj2[54], obj2[55]);
            obj2[52] = this.X0;
            obj2[53] = this.X1;
            obj2[54] = this.X2;
            obj2[55] = this.X3;
            sb5(obj2[56], obj2[57], obj2[58], obj2[59]);
            obj2[56] = this.X0;
            obj2[57] = this.X1;
            obj2[58] = this.X2;
            obj2[59] = this.X3;
            sb4(obj2[60], obj2[61], obj2[62], obj2[63]);
            obj2[60] = this.X0;
            obj2[61] = this.X1;
            obj2[62] = this.X2;
            obj2[63] = this.X3;
            sb3(obj2[64], obj2[65], obj2[66], obj2[67]);
            obj2[64] = this.X0;
            obj2[65] = this.X1;
            obj2[66] = this.X2;
            obj2[67] = this.X3;
            sb2(obj2[68], obj2[69], obj2[70], obj2[71]);
            obj2[68] = this.X0;
            obj2[69] = this.X1;
            obj2[70] = this.X2;
            obj2[71] = this.X3;
            sb1(obj2[72], obj2[73], obj2[74], obj2[75]);
            obj2[72] = this.X0;
            obj2[73] = this.X1;
            obj2[74] = this.X2;
            obj2[75] = this.X3;
            sb0(obj2[76], obj2[77], obj2[78], obj2[79]);
            obj2[76] = this.X0;
            obj2[77] = this.X1;
            obj2[78] = this.X2;
            obj2[79] = this.X3;
            sb7(obj2[80], obj2[81], obj2[82], obj2[83]);
            obj2[80] = this.X0;
            obj2[81] = this.X1;
            obj2[82] = this.X2;
            obj2[83] = this.X3;
            sb6(obj2[84], obj2[85], obj2[86], obj2[87]);
            obj2[84] = this.X0;
            obj2[85] = this.X1;
            obj2[86] = this.X2;
            obj2[87] = this.X3;
            sb5(obj2[88], obj2[89], obj2[90], obj2[91]);
            obj2[88] = this.X0;
            obj2[89] = this.X1;
            obj2[90] = this.X2;
            obj2[91] = this.X3;
            sb4(obj2[92], obj2[93], obj2[94], obj2[95]);
            obj2[92] = this.X0;
            obj2[93] = this.X1;
            obj2[94] = this.X2;
            obj2[95] = this.X3;
            sb3(obj2[96], obj2[97], obj2[98], obj2[99]);
            obj2[96] = this.X0;
            obj2[97] = this.X1;
            obj2[98] = this.X2;
            obj2[99] = this.X3;
            sb2(obj2[100], obj2[101], obj2[102], obj2[103]);
            obj2[100] = this.X0;
            obj2[101] = this.X1;
            obj2[102] = this.X2;
            obj2[103] = this.X3;
            sb1(obj2[104], obj2[105], obj2[106], obj2[EACTags.QUALIFIED_NAME]);
            obj2[104] = this.X0;
            obj2[105] = this.X1;
            obj2[106] = this.X2;
            obj2[EACTags.QUALIFIED_NAME] = this.X3;
            sb0(obj2[EACTags.CARDHOLDER_IMAGE_TEMPLATE], obj2[EACTags.APPLICATION_IMAGE_TEMPLATE], obj2[110], obj2[EACTags.FCI_TEMPLATE]);
            obj2[EACTags.CARDHOLDER_IMAGE_TEMPLATE] = this.X0;
            obj2[EACTags.APPLICATION_IMAGE_TEMPLATE] = this.X1;
            obj2[110] = this.X2;
            obj2[EACTags.FCI_TEMPLATE] = this.X3;
            sb7(obj2[112], obj2[113], obj2[114], obj2[EACTags.DISCRETIONARY_DATA_OBJECTS]);
            obj2[112] = this.X0;
            obj2[113] = this.X1;
            obj2[114] = this.X2;
            obj2[EACTags.DISCRETIONARY_DATA_OBJECTS] = this.X3;
            sb6(obj2[116], obj2[117], obj2[118], obj2[119]);
            obj2[116] = this.X0;
            obj2[117] = this.X1;
            obj2[118] = this.X2;
            obj2[119] = this.X3;
            sb5(obj2[120], obj2[EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY], obj2[EACTags.SECURITY_SUPPORT_TEMPLATE], obj2[EACTags.SECURITY_ENVIRONMENT_TEMPLATE]);
            obj2[120] = this.X0;
            obj2[EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY] = this.X1;
            obj2[EACTags.SECURITY_SUPPORT_TEMPLATE] = this.X2;
            obj2[EACTags.SECURITY_ENVIRONMENT_TEMPLATE] = this.X3;
            sb4(obj2[EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE], obj2[EACTags.SECURE_MESSAGING_TEMPLATE], obj2[EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE], obj2[CertificateBody.profileType]);
            obj2[EACTags.DYNAMIC_AUTHENTIFICATION_TEMPLATE] = this.X0;
            obj2[EACTags.SECURE_MESSAGING_TEMPLATE] = this.X1;
            obj2[EACTags.NON_INTERINDUSTRY_DATA_OBJECT_NESTING_TEMPLATE] = this.X2;
            obj2[CertificateBody.profileType] = this.X3;
            sb3(obj2[128], obj2[Wbxml.EXT_T_1], obj2[Wbxml.EXT_T_2], obj2[Wbxml.STR_T]);
            obj2[128] = this.X0;
            obj2[Wbxml.EXT_T_1] = this.X1;
            obj2[Wbxml.EXT_T_2] = this.X2;
            obj2[Wbxml.STR_T] = this.X3;
            return obj2;
        }
        throw new IllegalArgumentException("key must be a multiple of 4 bytes");
    }

    private int rotateLeft(int i, int i2) {
        return (i << i2) | (i >>> (-i2));
    }

    private int rotateRight(int i, int i2) {
        return (i >>> i2) | (i << (-i2));
    }

    private void sb0(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i3 ^ i5;
        int i7 = i2 ^ i6;
        this.X3 = (i & i4) ^ i7;
        i5 = (i5 & i2) ^ i;
        this.X2 = i7 ^ (i3 | i5);
        i7 = this.X3 & (i6 ^ i5);
        this.X1 = (i6 ^ -1) ^ i7;
        this.X0 = (i5 ^ -1) ^ i7;
    }

    private void sb1(int i, int i2, int i3, int i4) {
        int i5 = (i ^ -1) ^ i2;
        int i6 = (i | i5) ^ i3;
        this.X2 = i4 ^ i6;
        int i7 = (i4 | i5) ^ i2;
        i5 ^= this.X2;
        this.X3 = (i6 & i7) ^ i5;
        i7 ^= i6;
        this.X1 = this.X3 ^ i7;
        this.X0 = (i5 & i7) ^ i6;
    }

    private void sb2(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i2 ^ i4;
        this.X0 = (i3 & i5) ^ i6;
        int i7 = i3 ^ i5;
        int i8 = (this.X0 ^ i3) & i2;
        this.X3 = i7 ^ i8;
        this.X2 = ((i7 | this.X0) & (i8 | i4)) ^ i;
        this.X1 = ((i5 | i4) ^ this.X2) ^ (i6 ^ this.X3);
    }

    private void sb3(int i, int i2, int i3, int i4) {
        int i5 = i ^ i2;
        int i6 = i | i4;
        int i7 = i3 ^ i4;
        int i8 = (i & i3) | (i5 & i6);
        this.X2 = i7 ^ i8;
        i6 = (i6 ^ i2) ^ i8;
        this.X0 = i5 ^ (i7 & i6);
        i5 = this.X2 & this.X0;
        this.X1 = i6 ^ i5;
        this.X3 = (i5 ^ i7) ^ (i2 | i4);
    }

    private void sb4(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = (i4 & i5) ^ i3;
        int i7 = i2 | i6;
        this.X3 = i5 ^ i7;
        int i8 = i2 ^ -1;
        this.X0 = (i5 | i8) ^ i6;
        i5 ^= i8;
        this.X2 = (i7 & i5) ^ (this.X0 & i);
        this.X1 = (i5 & this.X2) ^ (i6 ^ i);
    }

    private void sb5(int i, int i2, int i3, int i4) {
        int i5 = i ^ -1;
        int i6 = i ^ i2;
        int i7 = i ^ i4;
        this.X0 = (i3 ^ i5) ^ (i6 | i7);
        int i8 = this.X0 & i4;
        this.X1 = (this.X0 ^ i6) ^ i8;
        i5 = (i5 | this.X0) ^ i7;
        this.X2 = (i6 | i8) ^ i5;
        this.X3 = (i5 & this.X1) ^ (i2 ^ i8);
    }

    private void sb6(int i, int i2, int i3, int i4) {
        int i5 = i ^ i4;
        int i6 = i2 ^ i5;
        int i7 = ((i ^ -1) | i5) ^ i3;
        this.X1 = i2 ^ i7;
        i5 = (i5 | this.X1) ^ i4;
        this.X2 = (i7 & i5) ^ i6;
        i5 ^= i7;
        this.X0 = this.X2 ^ i5;
        this.X3 = (i5 & i6) ^ (i7 ^ -1);
    }

    private void sb7(int i, int i2, int i3, int i4) {
        int i5 = i2 ^ i3;
        int i6 = (i3 & i5) ^ i4;
        int i7 = i ^ i6;
        this.X1 = ((i4 | i5) & i7) ^ i2;
        int i8 = this.X1;
        this.X3 = i5 ^ (i & i7);
        i5 = (i8 | i6) ^ i7;
        this.X2 = i6 ^ (this.X3 & i5);
        this.X0 = (i5 ^ -1) ^ (this.X3 & this.X2);
    }

    private void wordToBytes(int i, byte[] bArr, int i2) {
        bArr[i2 + 3] = (byte) i;
        bArr[i2 + 2] = (byte) (i >>> 8);
        bArr[i2 + 1] = (byte) (i >>> 16);
        bArr[i2] = (byte) (i >>> 24);
    }

    public String getAlgorithmName() {
        return "Serpent";
    }

    public int getBlockSize() {
        return 16;
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        if (cipherParameters instanceof KeyParameter) {
            this.encrypting = z;
            this.wKey = makeWorkingKey(((KeyParameter) cipherParameters).getKey());
            return;
        }
        throw new IllegalArgumentException("invalid parameter passed to Serpent init - " + cipherParameters.getClass().getName());
    }

    public final int processBlock(byte[] bArr, int i, byte[] bArr2, int i2) {
        if (this.wKey == null) {
            throw new IllegalStateException("Serpent not initialised");
        } else if (i + 16 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        } else if (i2 + 16 > bArr2.length) {
            throw new DataLengthException("output buffer too short");
        } else {
            if (this.encrypting) {
                encryptBlock(bArr, i, bArr2, i2);
            } else {
                decryptBlock(bArr, i, bArr2, i2);
            }
            return 16;
        }
    }

    public void reset() {
    }
}
