package org.kobjects.crypt;

import java.io.IOException;
import java.util.Random;
import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.tls.CipherSuite;

public class Crypt {
    private static final int ITERATIONS = 16;
    private static final int[][] SPtrans;
    private static final int[] con_salt = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 62, 63, 0, 0, 0, 0, 0};
    private static final int[] cov_2char = new int[]{46, 47, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, EACTags.QUALIFIED_NAME, EACTags.CARDHOLDER_IMAGE_TEMPLATE, EACTags.APPLICATION_IMAGE_TEMPLATE, 110, EACTags.FCI_TEMPLATE, 112, 113, 114, EACTags.DISCRETIONARY_DATA_OBJECTS, 116, 117, 118, 119, 120, EACTags.COEXISTANT_TAG_ALLOCATION_AUTHORITY, EACTags.SECURITY_SUPPORT_TEMPLATE};
    private static final boolean[] shifts2 = new boolean[]{false, false, true, true, true, true, true, true, false, true, true, true, true, true, true, false};
    private static final int[][] skb;

    static {
        int[] iArr = new int[]{0, 33554432, PKIFailureInfo.certRevoked, 33562624, PKIFailureInfo.badSenderNonce, 35651584, 2105344, 35659776, 4, 33554436, 8196, 33562628, 2097156, 35651588, 2105348, 35659780, PKIFailureInfo.badRecipientNonce, 33555456, 9216, 33563648, 2098176, 35652608, 2106368, 35660800, 1028, 33555460, 9220, 33563652, 2098180, 35652612, 2106372, 35660804, 268435456, 301989888, 268443648, 301998080, 270532608, 304087040, 270540800, 304095232, 268435460, 301989892, 268443652, 301998084, 270532612, 304087044, 270540804, 304095236, 268436480, 301990912, 268444672, 301999104, 270533632, 304088064, 270541824, 304096256, 268436484, 301990916, 268444676, 301999108, 270533636, 304088068, 270541828, 304096260};
        skb = new int[][]{new int[]{0, 16, PKIFailureInfo.duplicateCertReq, 536870928, PKIFailureInfo.notAuthorized, 65552, 536936448, 536936464, PKIFailureInfo.wrongIntegrity, 2064, 536872960, 536872976, 67584, 67600, 536938496, 536938512, 32, 48, 536870944, 536870960, 65568, 65584, 536936480, 536936496, 2080, 2096, 536872992, 536873008, 67616, 67632, 536938528, 536938544, PKIFailureInfo.signerNotTrusted, 524304, 537395200, 537395216, 589824, 589840, 537460736, 537460752, 526336, 526352, 537397248, 537397264, 591872, 591888, 537462784, 537462800, 524320, 524336, 537395232, 537395248, 589856, 589872, 537460768, 537460784, 526368, 526384, 537397280, 537397296, 591904, 591920, 537462816, 537462832}, iArr, new int[]{0, 1, 262144, 262145, 16777216, 16777217, 17039360, 17039361, 2, 3, 262146, 262147, 16777218, 16777219, 17039362, 17039363, 512, 513, 262656, 262657, 16777728, 16777729, 17039872, 17039873, 514, 515, 262658, 262659, 16777730, 16777731, 17039874, 17039875, 134217728, 134217729, 134479872, 134479873, 150994944, 150994945, 151257088, 151257089, 134217730, 134217731, 134479874, 134479875, 150994946, 150994947, 151257090, 151257091, 134218240, 134218241, 134480384, 134480385, 150995456, 150995457, 151257600, 151257601, 134218242, 134218243, 134480386, 134480387, 150995458, 150995459, 151257602, 151257603}, new int[]{0, PKIFailureInfo.badCertTemplate, 256, 1048832, 8, 1048584, 264, 1048840, PKIFailureInfo.certConfirmed, 1052672, 4352, 1052928, 4104, 1052680, 4360, 1052936, 67108864, 68157440, 67109120, 68157696, 67108872, 68157448, 67109128, 68157704, 67112960, 68161536, 67113216, 68161792, 67112968, 68161544, 67113224, 68161800, PKIFailureInfo.unsupportedVersion, 1179648, 131328, 1179904, 131080, 1179656, 131336, 1179912, 135168, 1183744, 135424, 1184000, 135176, 1183752, 135432, 1184008, 67239936, 68288512, 67240192, 68288768, 67239944, 68288520, 67240200, 68288776, 67244032, 68292608, 67244288, 68292864, 67244040, 68292616, 67244296, 68292872}, new int[]{0, 268435456, PKIFailureInfo.notAuthorized, 268500992, 4, 268435460, 65540, 268500996, PKIFailureInfo.duplicateCertReq, 805306368, 536936448, 805371904, 536870916, 805306372, 536936452, 805371908, PKIFailureInfo.badCertTemplate, 269484032, 1114112, 269549568, 1048580, 269484036, 1114116, 269549572, 537919488, 806354944, 537985024, 806420480, 537919492, 806354948, 537985028, 806420484, PKIFailureInfo.certConfirmed, 268439552, 69632, 268505088, 4100, 268439556, 69636, 268505092, 536875008, 805310464, 536940544, 805376000, 536875012, 805310468, 536940548, 805376004, 1052672, 269488128, 1118208, 269553664, 1052676, 269488132, 1118212, 269553668, 537923584, 806359040, 537989120, 806424576, 537923588, 806359044, 537989124, 806424580}, new int[]{0, 134217728, 8, 134217736, PKIFailureInfo.badRecipientNonce, 134218752, 1032, 134218760, PKIFailureInfo.unsupportedVersion, 134348800, 131080, 134348808, 132096, 134349824, 132104, 134349832, 1, 134217729, 9, 134217737, 1025, 134218753, 1033, 134218761, 131073, 134348801, 131081, 134348809, 132097, 134349825, 132105, 134349833, 33554432, 167772160, 33554440, 167772168, 33555456, 167773184, 33555464, 167773192, 33685504, 167903232, 33685512, 167903240, 33686528, 167904256, 33686536, 167904264, 33554433, 167772161, 33554441, 167772169, 33555457, 167773185, 33555465, 167773193, 33685505, 167903233, 33685513, 167903241, 33686529, 167904257, 33686537, 167904265}, new int[]{0, 256, PKIFailureInfo.signerNotTrusted, 524544, 16777216, 16777472, 17301504, 17301760, 16, 272, 524304, 524560, 16777232, 16777488, 17301520, 17301776, PKIFailureInfo.badSenderNonce, 2097408, 2621440, 2621696, 18874368, 18874624, 19398656, 19398912, 2097168, 2097424, 2621456, 2621712, 18874384, 18874640, 19398672, 19398928, 512, 768, 524800, 525056, 16777728, 16777984, 17302016, 17302272, 528, 784, 524816, 525072, 16777744, 16778000, 17302032, 17302288, 2097664, 2097920, 2621952, 2622208, 18874880, 18875136, 19399168, 19399424, 2097680, 2097936, 2621968, 2622224, 18874896, 18875152, 19399184, 19399440}, new int[]{0, 67108864, 262144, 67371008, 2, 67108866, 262146, 67371010, PKIFailureInfo.certRevoked, 67117056, 270336, 67379200, 8194, 67117058, 270338, 67379202, 32, 67108896, 262176, 67371040, 34, 67108898, 262178, 67371042, 8224, 67117088, 270368, 67379232, 8226, 67117090, 270370, 67379234, PKIFailureInfo.wrongIntegrity, 67110912, 264192, 67373056, 2050, 67110914, 264194, 67373058, 10240, 67119104, 272384, 67381248, 10242, 67119106, 272386, 67381250, 2080, 67110944, 264224, 67373088, 2082, 67110946, 264226, 67373090, 10272, 67119136, 272416, 67381280, 10274, 67119138, 272418, 67381282}};
        iArr = new int[]{536870928, 524304, 0, 537397248, 524304, PKIFailureInfo.wrongIntegrity, 536872976, PKIFailureInfo.signerNotTrusted, 2064, 537397264, 526336, PKIFailureInfo.duplicateCertReq, 536872960, 536870928, 537395200, 526352, PKIFailureInfo.signerNotTrusted, 536872976, 537395216, 0, PKIFailureInfo.wrongIntegrity, 16, 537397248, 537395216, 537397264, 537395200, PKIFailureInfo.duplicateCertReq, 2064, 16, 526336, 526352, 536872960, 2064, PKIFailureInfo.duplicateCertReq, 536872960, 526352, 537397248, 524304, 0, 536872960, PKIFailureInfo.duplicateCertReq, PKIFailureInfo.wrongIntegrity, 537395216, PKIFailureInfo.signerNotTrusted, 524304, 537397264, 526336, 16, 537397264, 526336, PKIFailureInfo.signerNotTrusted, 536872976, 536870928, 537395200, 526352, 0, PKIFailureInfo.wrongIntegrity, 536870928, 536872976, 537397248, 537395200, 2064, 16, 537395216};
        int[] iArr2 = new int[]{PKIFailureInfo.certConfirmed, 128, 4194432, 4194305, 4198529, 4097, 4224, 0, 4194304, 4194433, Wbxml.EXT_T_1, 4198400, 1, 4198528, 4198400, Wbxml.EXT_T_1, 4194433, PKIFailureInfo.certConfirmed, 4097, 4198529, 0, 4194432, 4194305, 4224, 4198401, 4225, 4198528, 1, 4225, 4198401, 128, 4194304, 4225, 4198400, 4198401, Wbxml.EXT_T_1, PKIFailureInfo.certConfirmed, 128, 4194304, 4198401, 4194433, 4225, 4224, 0, 128, 4194305, 1, 4194432, 0, 4194433, 4194432, 4224, Wbxml.EXT_T_1, PKIFailureInfo.certConfirmed, 4198529, 4194304, 4198528, 1, 4097, 4198529, 4194305, 4198528, 4198400, 4097};
        SPtrans = new int[][]{new int[]{8520192, PKIFailureInfo.unsupportedVersion, -2139095040, -2138963456, 8388608, -2147352064, -2147352576, -2139095040, -2147352064, 8520192, 8519680, -2147483136, -2139094528, 8388608, 0, -2147352576, PKIFailureInfo.unsupportedVersion, PKIFailureInfo.systemUnavail, 8389120, 131584, -2138963456, 8519680, -2147483136, 8389120, PKIFailureInfo.systemUnavail, 512, 131584, -2138963968, 512, -2139094528, -2138963968, 0, 0, -2138963456, 8389120, -2147352576, 8520192, PKIFailureInfo.unsupportedVersion, -2147483136, 8389120, -2138963968, 512, 131584, -2139095040, -2147352064, PKIFailureInfo.systemUnavail, -2139095040, 8519680, -2138963456, 131584, 8519680, -2139094528, 8388608, -2147483136, -2147352576, 0, PKIFailureInfo.unsupportedVersion, 8388608, -2139094528, 8520192, PKIFailureInfo.systemUnavail, -2138963968, 512, -2147352064}, new int[]{268705796, 0, 270336, 268697600, 268435460, 8196, 268443648, 270336, PKIFailureInfo.certRevoked, 268697604, 4, 268443648, 262148, 268705792, 268697600, 4, 262144, 268443652, 268697604, PKIFailureInfo.certRevoked, 270340, 268435456, 0, 262148, 268443652, 270340, 268705792, 268435460, 268435456, 262144, 8196, 268705796, 262148, 268705792, 268443648, 270340, 268705796, 262148, 268435460, 0, 268435456, 8196, 262144, 268697604, PKIFailureInfo.certRevoked, 268435456, 270340, 268443652, 268705792, PKIFailureInfo.certRevoked, 0, 268435460, 4, 268705796, 270336, 268697600, 268697604, 262144, 8196, 268443648, 268443652, 4, 268697600, 270336}, new int[]{1090519040, 16842816, 64, 1090519104, 1073807360, 16777216, 1090519104, 65600, 16777280, PKIFailureInfo.notAuthorized, 16842752, 1073741824, 1090584640, 1073741888, 1073741824, 1090584576, 0, 1073807360, 16842816, 64, 1073741888, 1090584640, PKIFailureInfo.notAuthorized, 1090519040, 1090584576, 16777280, 1073807424, 16842752, 65600, 0, 16777216, 1073807424, 16842816, 64, 1073741824, PKIFailureInfo.notAuthorized, 1073741888, 1073807360, 16842752, 1090519104, 0, 16842816, 65600, 1090584576, 1073807360, 16777216, 1090584640, 1073741824, 1073807424, 1090519040, 16777216, 1090584640, PKIFailureInfo.notAuthorized, 16777280, 1090519104, 65600, 16777280, 0, 1090584576, 1073741888, 1090519040, 1073807424, 64, 16842752}, new int[]{1049602, 67109888, 2, 68158466, 0, 68157440, 67109890, 1048578, 68158464, 67108866, 67108864, 1026, 67108866, 1049602, PKIFailureInfo.badCertTemplate, 67108864, 68157442, 1049600, PKIFailureInfo.badRecipientNonce, 2, 1049600, 67109890, 68157440, PKIFailureInfo.badRecipientNonce, 1026, 0, 1048578, 68158464, 67109888, 68157442, 68158466, PKIFailureInfo.badCertTemplate, 68157442, 1026, PKIFailureInfo.badCertTemplate, 67108866, 1049600, 67109888, 2, 68157440, 67109890, 0, PKIFailureInfo.badRecipientNonce, 1048578, 0, 68157442, 68158464, PKIFailureInfo.badRecipientNonce, 67108864, 68158466, 1049602, PKIFailureInfo.badCertTemplate, 68158466, 2, 67109888, 1049602, 1048578, 1049600, 68157440, 67109890, 1026, 67108864, 67108866, 68158464}, new int[]{33554432, 16384, 256, 33571080, 33570824, 33554688, 16648, 33570816, 16384, 8, 33554440, 16640, 33554696, 33570824, 33571072, 0, 16640, 33554432, 16392, 264, 33554688, 16648, 0, 33554440, 8, 33554696, 33571080, 16392, 33570816, 256, 264, 33571072, 33571072, 33554696, 16392, 33570816, 16384, 8, 33554440, 33554688, 33554432, 16640, 33571080, 0, 16648, 33554432, 256, 16392, 33554696, 256, 0, 33571080, 33570824, 33571072, 264, 16384, 16640, 33570824, 33554688, 264, 8, 16648, 33570816, 33554440}, iArr, iArr2, new int[]{136314912, 136347648, 32800, 0, 134250496, 2097184, 136314880, 136347680, 32, 134217728, 2129920, 32800, 2129952, 134250528, 134217760, 136314880, 32768, 2129952, 2097184, 134250496, 136347680, 134217760, 0, 2129920, 134217728, PKIFailureInfo.badSenderNonce, 134250528, 136314912, PKIFailureInfo.badSenderNonce, 32768, 136347648, 32, PKIFailureInfo.badSenderNonce, 32768, 134217760, 136347680, 32800, 134217728, 0, 2129920, 136314912, 134250528, 134250496, 2097184, 136347648, 32, 2097184, 134250496, 136347680, PKIFailureInfo.badSenderNonce, 136314880, 134217760, 2129920, 32800, 134250528, 136314880, 32, 136347648, 2129952, 0, 134217728, 136314912, 32768, 2129952}};
    }

    private Crypt() {
    }

    private static final int D_ENCRYPT(int i, int i2, int i3, int i4, int i5, int[] iArr) {
        int i6 = (i2 >>> 16) ^ i2;
        int i7 = i6 & i4;
        i6 &= i5;
        i7 = ((i7 ^ (i7 << 16)) ^ i2) ^ iArr[i3];
        i6 = ((i6 ^ (i6 << 16)) ^ i2) ^ iArr[i3 + 1];
        i6 = (i6 >>> 4) | (i6 << 28);
        int i8 = SPtrans[1][i6 & 63];
        int i9 = SPtrans[3][(i6 >>> 8) & 63];
        return (((((SPtrans[7][(i6 >>> 24) & 63] | ((i8 | i9) | SPtrans[5][(i6 >>> 16) & 63])) | SPtrans[0][i7 & 63]) | SPtrans[2][(i7 >>> 8) & 63]) | SPtrans[4][(i7 >>> 16) & 63]) | SPtrans[6][(i7 >>> 24) & 63]) ^ i;
    }

    private static final int HPERM_OP(int i, int i2, int i3) {
        int i4 = ((i << (16 - i2)) ^ i) & i3;
        return (i4 ^ i) ^ (i4 >>> (16 - i2));
    }

    private static final void PERM_OP(int i, int i2, int i3, int i4, int[] iArr) {
        int i5 = ((i >>> i3) ^ i2) & i4;
        iArr[0] = (i5 << i3) ^ i;
        iArr[1] = i5 ^ i2;
    }

    private static final int[] body(int[] iArr, int i, int i2) {
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        while (i4 < 25) {
            int i6 = 0;
            while (i6 < 32) {
                int D_ENCRYPT = D_ENCRYPT(i5, i3, i6, i, i2, iArr);
                i3 = D_ENCRYPT(i3, D_ENCRYPT, i6 + 2, i, i2, iArr);
                i6 += 4;
                i5 = D_ENCRYPT;
            }
            i4++;
            int i7 = i5;
            i5 = i3;
            i3 = i7;
        }
        int[] iArr2 = new int[2];
        PERM_OP(((i5 >>> 1) | (i5 << 31)) & -1, ((i3 >>> 1) | (i3 << 31)) & -1, 1, 1431655765, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 8, 16711935, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 2, 858993459, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 16, 65535, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 4, 252645135, iArr2);
        i5 = iArr2[0];
        return new int[]{iArr2[1], i5};
    }

    private static final int byteToUnsigned(byte b) {
        return b >= (byte) 0 ? b : b + 256;
    }

    public static final String crypt(String str, String str2) {
        int i;
        while (str.length() < 2) {
            str = str + "A";
        }
        StringBuffer stringBuffer = new StringBuffer("             ");
        char charAt = str.charAt(0);
        char charAt2 = str.charAt(1);
        stringBuffer.setCharAt(0, charAt);
        stringBuffer.setCharAt(1, charAt2);
        int i2 = con_salt[charAt];
        int i3 = con_salt[charAt2];
        byte[] bArr = new byte[8];
        for (i = 0; i < 8; i++) {
            bArr[i] = (byte) 0;
        }
        i = 0;
        while (i < 8 && i < str2.length()) {
            bArr[i] = (byte) (str2.charAt(i) << 1);
            i++;
        }
        int[] body = body(des_set_key(bArr), i2, i3 << 4);
        byte[] bArr2 = new byte[9];
        intToFourBytes(body[0], bArr2, 0);
        intToFourBytes(body[1], bArr2, 4);
        bArr2[8] = (byte) 0;
        int i4 = 2;
        i = 128;
        int i5 = 0;
        while (i4 < 13) {
            i2 = 0;
            int i6 = i;
            i3 = 0;
            while (i2 < 6) {
                i = i3 << 1;
                i3 = (bArr2[i5] & i6) != 0 ? i | 1 : i;
                i = i6 >>> 1;
                if (i == 0) {
                    i5++;
                    i = 128;
                }
                stringBuffer.setCharAt(i4, (char) cov_2char[i3]);
                i2++;
                i6 = i;
            }
            i4++;
            i = i6;
        }
        return stringBuffer.toString();
    }

    private static int[] des_set_key(byte[] bArr) {
        int[] iArr = new int[32];
        int[] iArr2 = new int[2];
        PERM_OP(fourBytesToInt(bArr, 4), fourBytesToInt(bArr, 0), 4, 252645135, iArr2);
        int i = iArr2[0];
        PERM_OP(HPERM_OP(i, -2, -859045888), HPERM_OP(iArr2[1], -2, -859045888), 1, 1431655765, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 8, 16711935, iArr2);
        PERM_OP(iArr2[1], iArr2[0], 1, 1431655765, iArr2);
        i = iArr2[0];
        int i2 = iArr2[1];
        int i3 = ((-268435456 & i2) >>> 4) | (((i & 65280) | ((i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) << 16)) | ((16711680 & i) >>> 16));
        i = i2 & 268435455;
        i2 = 0;
        int i4 = 0;
        while (i4 < 16) {
            if (shifts2[i4]) {
                i = (i >>> 2) | (i << 26);
                i3 = (i3 >>> 2) | (i3 << 26);
            } else {
                i = (i >>> 1) | (i << 27);
                i3 = (i3 >>> 1) | (i3 << 27);
            }
            i &= 268435455;
            i3 &= 268435455;
            int i5 = ((skb[0][i & 63] | skb[1][((i >>> 6) & 3) | ((i >>> 7) & 60)]) | skb[2][((i >>> 13) & 15) | ((i >>> 14) & 48)]) | skb[3][(((i >>> 20) & 1) | ((i >>> 21) & 6)) | ((i >>> 22) & 56)];
            int i6 = ((skb[4][i3 & 63] | skb[5][((i3 >>> 7) & 3) | ((i3 >>> 8) & 60)]) | skb[6][(i3 >>> 15) & 63]) | skb[7][((i3 >>> 21) & 15) | ((i3 >>> 22) & 48)];
            int i7 = i2 + 1;
            iArr[i2] = ((i6 << 16) | (65535 & i5)) & -1;
            i2 = (i5 >>> 16) | (-65536 & i6);
            iArr[i7] = ((i2 << 4) | (i2 >>> 28)) & -1;
            i4++;
            i2 = i7 + 1;
        }
        return iArr;
    }

    private static int fourBytesToInt(byte[] bArr, int i) {
        int i2 = i + 1;
        int i3 = i2 + 1;
        return (((byteToUnsigned(bArr[i2]) << 8) | byteToUnsigned(bArr[i])) | (byteToUnsigned(bArr[i3]) << 16)) | (byteToUnsigned(bArr[i3 + 1]) << 24);
    }

    public static final String generate(String str) {
        return crypt(Integer.toHexString(new Random().nextInt() & 65535), str);
    }

    private static final void intToFourBytes(int i, byte[] bArr, int i2) {
        int i3 = i2 + 1;
        bArr[i2] = (byte) (i & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        int i4 = i3 + 1;
        bArr[i3] = (byte) ((i >>> 8) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr[i4] = (byte) ((i >>> 16) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        bArr[i4 + 1] = (byte) ((i >>> 24) & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
    }

    public static void main(String[] strArr) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (true) {
            int read = System.in.read();
            if (read >= 32) {
                stringBuffer.append((char) read);
            } else {
                System.out.println(generate(stringBuffer.toString()));
                return;
            }
        }
    }

    public static final boolean match(String str, String str2) {
        return (str2 == null || str2.length() < 3) ? false : str2.equals(crypt(str2.substring(0, 2), str));
    }
}
