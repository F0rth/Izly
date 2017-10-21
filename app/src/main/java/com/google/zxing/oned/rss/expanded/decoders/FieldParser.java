package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
    private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    private static final Object[][] TWO_DIGIT_DATA_LENGTH;
    private static final Object VARIABLE_LENGTH = new Object();

    static {
        r1 = new Object[24][];
        r1[0] = new Object[]{"00", Integer.valueOf(18)};
        r1[1] = new Object[]{"01", Integer.valueOf(14)};
        r1[2] = new Object[]{"02", Integer.valueOf(14)};
        r1[3] = new Object[]{"10", VARIABLE_LENGTH, Integer.valueOf(20)};
        r1[4] = new Object[]{"11", Integer.valueOf(6)};
        r1[5] = new Object[]{"12", Integer.valueOf(6)};
        r1[6] = new Object[]{"13", Integer.valueOf(6)};
        r1[7] = new Object[]{"15", Integer.valueOf(6)};
        r1[8] = new Object[]{"17", Integer.valueOf(6)};
        r1[9] = new Object[]{"20", Integer.valueOf(2)};
        r1[10] = new Object[]{"21", VARIABLE_LENGTH, Integer.valueOf(20)};
        r1[11] = new Object[]{"22", VARIABLE_LENGTH, Integer.valueOf(29)};
        r1[12] = new Object[]{"30", VARIABLE_LENGTH, Integer.valueOf(8)};
        r1[13] = new Object[]{"37", VARIABLE_LENGTH, Integer.valueOf(8)};
        r1[14] = new Object[]{"90", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[15] = new Object[]{"91", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[16] = new Object[]{"92", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[17] = new Object[]{"93", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[18] = new Object[]{"94", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[19] = new Object[]{"95", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[20] = new Object[]{"96", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[21] = new Object[]{"97", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[22] = new Object[]{"98", VARIABLE_LENGTH, Integer.valueOf(30)};
        r1[23] = new Object[]{"99", VARIABLE_LENGTH, Integer.valueOf(30)};
        TWO_DIGIT_DATA_LENGTH = r1;
        Object obj = VARIABLE_LENGTH;
        Object[] objArr = new Object[]{"241", VARIABLE_LENGTH, Integer.valueOf(30)};
        Object obj2 = VARIABLE_LENGTH;
        Object obj3 = VARIABLE_LENGTH;
        Object obj4 = VARIABLE_LENGTH;
        Object obj5 = VARIABLE_LENGTH;
        Object obj6 = VARIABLE_LENGTH;
        Object obj7 = VARIABLE_LENGTH;
        Object obj8 = VARIABLE_LENGTH;
        Object obj9 = VARIABLE_LENGTH;
        Object[] objArr2 = new Object[]{"410", Integer.valueOf(13)};
        Object obj10 = VARIABLE_LENGTH;
        Object obj11 = VARIABLE_LENGTH;
        Object obj12 = VARIABLE_LENGTH;
        r15 = new Object[23][];
        r15[0] = new Object[]{"240", obj, Integer.valueOf(30)};
        r15[1] = objArr;
        r15[2] = new Object[]{"242", obj2, Integer.valueOf(6)};
        r15[3] = new Object[]{"250", obj3, Integer.valueOf(30)};
        r15[4] = new Object[]{"251", obj4, Integer.valueOf(30)};
        r15[5] = new Object[]{"253", obj5, Integer.valueOf(17)};
        r15[6] = new Object[]{"254", obj6, Integer.valueOf(20)};
        r15[7] = new Object[]{"400", obj7, Integer.valueOf(30)};
        r15[8] = new Object[]{"401", obj8, Integer.valueOf(30)};
        r15[9] = new Object[]{"402", Integer.valueOf(17)};
        r15[10] = new Object[]{"403", obj9, Integer.valueOf(30)};
        r15[11] = objArr2;
        r15[12] = new Object[]{"411", Integer.valueOf(13)};
        r15[13] = new Object[]{"412", Integer.valueOf(13)};
        r15[14] = new Object[]{"413", Integer.valueOf(13)};
        r15[15] = new Object[]{"414", Integer.valueOf(13)};
        r15[16] = new Object[]{"420", obj10, Integer.valueOf(20)};
        r15[17] = new Object[]{"421", obj11, Integer.valueOf(15)};
        r15[18] = new Object[]{"422", Integer.valueOf(3)};
        r15[19] = new Object[]{"423", obj12, Integer.valueOf(15)};
        r15[20] = new Object[]{"424", Integer.valueOf(3)};
        r15[21] = new Object[]{"425", Integer.valueOf(3)};
        r15[22] = new Object[]{"426", Integer.valueOf(3)};
        THREE_DIGIT_DATA_LENGTH = r15;
        Object[] objArr3 = new Object[]{"310", Integer.valueOf(6)};
        objArr = new Object[]{"311", Integer.valueOf(6)};
        Object[] objArr4 = new Object[]{"315", Integer.valueOf(6)};
        Object[] objArr5 = new Object[]{"316", Integer.valueOf(6)};
        Object[] objArr6 = new Object[]{"320", Integer.valueOf(6)};
        Object[] objArr7 = new Object[]{"321", Integer.valueOf(6)};
        Object[] objArr8 = new Object[]{"323", Integer.valueOf(6)};
        Object[] objArr9 = new Object[]{"325", Integer.valueOf(6)};
        Object[] objArr10 = new Object[]{"327", Integer.valueOf(6)};
        Object[] objArr11 = new Object[]{"330", Integer.valueOf(6)};
        objArr2 = new Object[]{"331", Integer.valueOf(6)};
        Object[] objArr12 = new Object[]{"334", Integer.valueOf(6)};
        Object[] objArr13 = new Object[]{"335", Integer.valueOf(6)};
        Object[] objArr14 = new Object[]{"336", Integer.valueOf(6)};
        Object[] objArr15 = new Object[]{"341", Integer.valueOf(6)};
        String[] strArr = new Object[]{"342", Integer.valueOf(6)};
        String[] strArr2 = new Object[]{"344", Integer.valueOf(6)};
        String[] strArr3 = new Object[]{"345", Integer.valueOf(6)};
        String[] strArr4 = new Object[]{"347", Integer.valueOf(6)};
        String[] strArr5 = new Object[]{"350", Integer.valueOf(6)};
        String[] strArr6 = new Object[]{"353", Integer.valueOf(6)};
        String[] strArr7 = new Object[]{"354", Integer.valueOf(6)};
        String[] strArr8 = new Object[]{"355", Integer.valueOf(6)};
        String[] strArr9 = new Object[]{"356", Integer.valueOf(6)};
        String[] strArr10 = new Object[]{"360", Integer.valueOf(6)};
        String[] strArr11 = new Object[]{"361", Integer.valueOf(6)};
        String[] strArr12 = new Object[]{"362", Integer.valueOf(6)};
        String[] strArr13 = new Object[]{"363", Integer.valueOf(6)};
        String[] strArr14 = new Object[]{"364", Integer.valueOf(6)};
        String[] strArr15 = new Object[]{"367", Integer.valueOf(6)};
        String[] strArr16 = new Object[]{"368", Integer.valueOf(6)};
        String[] strArr17 = new Object[]{"369", Integer.valueOf(6)};
        Object obj13 = VARIABLE_LENGTH;
        String[] strArr18 = new Object[]{"391", VARIABLE_LENGTH, Integer.valueOf(18)};
        Object obj14 = VARIABLE_LENGTH;
        String[] strArr19 = new Object[]{"393", VARIABLE_LENGTH, Integer.valueOf(18)};
        String[] strArr20 = new Object[]{"703", VARIABLE_LENGTH, Integer.valueOf(30)};
        r38 = new Object[57][];
        r38[2] = new Object[]{"312", Integer.valueOf(6)};
        r38[3] = new Object[]{"313", Integer.valueOf(6)};
        r38[4] = new Object[]{"314", Integer.valueOf(6)};
        r38[5] = objArr4;
        r38[6] = objArr5;
        r38[7] = objArr6;
        r38[8] = objArr7;
        r38[9] = new Object[]{"322", Integer.valueOf(6)};
        r38[10] = objArr8;
        r38[11] = new Object[]{"324", Integer.valueOf(6)};
        r38[12] = objArr9;
        r38[13] = new Object[]{"326", Integer.valueOf(6)};
        r38[14] = objArr10;
        r38[15] = new Object[]{"328", Integer.valueOf(6)};
        r38[16] = new Object[]{"329", Integer.valueOf(6)};
        r38[17] = objArr11;
        r38[18] = objArr2;
        r38[19] = new Object[]{"332", Integer.valueOf(6)};
        r38[20] = new Object[]{"333", Integer.valueOf(6)};
        r38[21] = objArr12;
        r38[22] = objArr13;
        r38[23] = objArr14;
        r38[24] = new Object[]{"340", Integer.valueOf(6)};
        r38[25] = objArr15;
        r38[26] = strArr;
        r38[27] = new Object[]{"343", Integer.valueOf(6)};
        r38[28] = strArr2;
        r38[29] = strArr3;
        r38[30] = new Object[]{"346", Integer.valueOf(6)};
        r38[31] = strArr4;
        r38[32] = new Object[]{"348", Integer.valueOf(6)};
        r38[33] = new Object[]{"349", Integer.valueOf(6)};
        r38[34] = strArr5;
        r38[35] = new Object[]{"351", Integer.valueOf(6)};
        r38[36] = new Object[]{"352", Integer.valueOf(6)};
        r38[37] = strArr6;
        r38[38] = strArr7;
        r38[39] = strArr8;
        r38[40] = strArr9;
        r38[41] = new Object[]{"357", Integer.valueOf(6)};
        r38[42] = strArr10;
        r38[43] = strArr11;
        r38[44] = strArr12;
        r38[45] = strArr13;
        r38[46] = strArr14;
        r38[47] = new Object[]{"365", Integer.valueOf(6)};
        r38[48] = new Object[]{"366", Integer.valueOf(6)};
        r38[49] = strArr15;
        r38[50] = strArr16;
        r38[51] = strArr17;
        r38[52] = new Object[]{"390", obj13, Integer.valueOf(15)};
        r38[53] = strArr18;
        r38[54] = new Object[]{"392", obj14, Integer.valueOf(15)};
        r38[55] = strArr19;
        r38[56] = strArr20;
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = r38;
        objArr3 = new Object[]{"7001", Integer.valueOf(13)};
        objArr = new Object[]{"7002", VARIABLE_LENGTH, Integer.valueOf(30)};
        objArr4 = new Object[]{"8002", VARIABLE_LENGTH, Integer.valueOf(20)};
        objArr5 = new Object[]{"8003", VARIABLE_LENGTH, Integer.valueOf(30)};
        objArr6 = new Object[]{"8004", VARIABLE_LENGTH, Integer.valueOf(30)};
        objArr7 = new Object[]{"8005", Integer.valueOf(6)};
        objArr8 = new Object[]{"8006", Integer.valueOf(18)};
        obj7 = VARIABLE_LENGTH;
        objArr10 = new Object[]{"8008", VARIABLE_LENGTH, Integer.valueOf(12)};
        objArr11 = new Object[]{"8018", Integer.valueOf(18)};
        objArr2 = new Object[]{"8020", VARIABLE_LENGTH, Integer.valueOf(25)};
        objArr12 = new Object[]{"8100", Integer.valueOf(6)};
        obj11 = VARIABLE_LENGTH;
        objArr14 = new Object[]{"8200", VARIABLE_LENGTH, Integer.valueOf(70)};
        r15 = new Object[18][];
        r15[2] = new Object[]{"7003", Integer.valueOf(10)};
        r15[3] = new Object[]{"8001", Integer.valueOf(14)};
        r15[4] = objArr4;
        r15[5] = objArr5;
        r15[6] = objArr6;
        r15[7] = objArr7;
        r15[8] = objArr8;
        r15[9] = new Object[]{"8007", obj7, Integer.valueOf(30)};
        r15[10] = objArr10;
        r15[11] = objArr11;
        r15[12] = objArr2;
        r15[13] = objArr12;
        r15[14] = new Object[]{"8101", Integer.valueOf(10)};
        r15[15] = new Object[]{"8102", Integer.valueOf(2)};
        r15[16] = new Object[]{"8110", obj11, Integer.valueOf(70)};
        r15[17] = objArr14;
        FOUR_DIGIT_DATA_LENGTH = r15;
    }

    private FieldParser() {
    }

    static String parseFieldsInGeneralPurpose(String str) throws NotFoundException {
        if (str.length() == 0) {
            return null;
        }
        if (str.length() < 2) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring = str.substring(0, 2);
        Object[][] objArr = TWO_DIGIT_DATA_LENGTH;
        int length = objArr.length;
        int i = 0;
        while (i < length) {
            Object[] objArr2 = objArr[i];
            if (objArr2[0].equals(substring)) {
                return objArr2[1] == VARIABLE_LENGTH ? processVariableAI(2, ((Integer) objArr2[2]).intValue(), str) : processFixedAI(2, ((Integer) objArr2[1]).intValue(), str);
            } else {
                i++;
            }
        }
        if (str.length() < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        substring = str.substring(0, 3);
        objArr = THREE_DIGIT_DATA_LENGTH;
        length = objArr.length;
        i = 0;
        while (i < length) {
            objArr2 = objArr[i];
            if (objArr2[0].equals(substring)) {
                return objArr2[1] == VARIABLE_LENGTH ? processVariableAI(3, ((Integer) objArr2[2]).intValue(), str) : processFixedAI(3, ((Integer) objArr2[1]).intValue(), str);
            } else {
                i++;
            }
        }
        objArr = THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
        length = objArr.length;
        i = 0;
        while (i < length) {
            objArr2 = objArr[i];
            if (objArr2[0].equals(substring)) {
                return objArr2[1] == VARIABLE_LENGTH ? processVariableAI(4, ((Integer) objArr2[2]).intValue(), str) : processFixedAI(4, ((Integer) objArr2[1]).intValue(), str);
            } else {
                i++;
            }
        }
        if (str.length() < 4) {
            throw NotFoundException.getNotFoundInstance();
        }
        substring = str.substring(0, 4);
        objArr = FOUR_DIGIT_DATA_LENGTH;
        length = objArr.length;
        i = 0;
        while (i < length) {
            objArr2 = objArr[i];
            if (objArr2[0].equals(substring)) {
                return objArr2[1] == VARIABLE_LENGTH ? processVariableAI(4, ((Integer) objArr2[2]).intValue(), str) : processFixedAI(4, ((Integer) objArr2[1]).intValue(), str);
            } else {
                i++;
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processFixedAI(int i, int i2, String str) throws NotFoundException {
        if (str.length() < i) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring = str.substring(0, i);
        if (str.length() < i + i2) {
            throw NotFoundException.getNotFoundInstance();
        }
        String substring2 = str.substring(i, i + i2);
        String substring3 = str.substring(i + i2);
        substring = "(" + substring + ')' + substring2;
        substring2 = parseFieldsInGeneralPurpose(substring3);
        return substring2 == null ? substring : substring + substring2;
    }

    private static String processVariableAI(int i, int i2, String str) throws NotFoundException {
        String substring = str.substring(0, i);
        int length = str.length() < i + i2 ? str.length() : i + i2;
        String substring2 = str.substring(i, length);
        String substring3 = str.substring(length);
        String str2 = "(" + substring + ')' + substring2;
        substring = parseFieldsInGeneralPurpose(substring3);
        return substring == null ? str2 : str2 + substring;
    }
}
