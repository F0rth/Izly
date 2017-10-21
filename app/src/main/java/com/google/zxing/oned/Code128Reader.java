package com.google.zxing.oned;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

public final class Code128Reader extends OneDReader {
    private static final int CODE_CODE_A = 101;
    private static final int CODE_CODE_B = 100;
    private static final int CODE_CODE_C = 99;
    private static final int CODE_FNC_1 = 102;
    private static final int CODE_FNC_2 = 97;
    private static final int CODE_FNC_3 = 96;
    private static final int CODE_FNC_4_A = 101;
    private static final int CODE_FNC_4_B = 100;
    static final int[][] CODE_PATTERNS;
    private static final int CODE_SHIFT = 98;
    private static final int CODE_START_A = 103;
    private static final int CODE_START_B = 104;
    private static final int CODE_START_C = 105;
    private static final int CODE_STOP = 106;
    private static final int MAX_AVG_VARIANCE = 64;
    private static final int MAX_INDIVIDUAL_VARIANCE = 179;

    static {
        int[] iArr = new int[]{2, 2, 2, 1, 2, 2};
        int[] iArr2 = new int[]{2, 2, 2, 2, 2, 1};
        int[] iArr3 = new int[]{1, 2, 1, 2, 2, 3};
        int[] iArr4 = new int[]{1, 2, 1, 3, 2, 2};
        int[] iArr5 = new int[]{1, 2, 2, 2, 1, 3};
        int[] iArr6 = new int[]{1, 2, 2, 3, 1, 2};
        int[] iArr7 = new int[]{1, 3, 2, 2, 1, 2};
        int[] iArr8 = new int[]{2, 2, 1, 2, 1, 3};
        int[] iArr9 = new int[]{2, 2, 1, 3, 1, 2};
        int[] iArr10 = new int[]{1, 2, 2, 1, 3, 2};
        int[] iArr11 = new int[]{1, 2, 2, 2, 3, 1};
        int[] iArr12 = new int[]{1, 2, 3, 1, 2, 2};
        int[] iArr13 = new int[]{2, 2, 3, 2, 1, 1};
        int[] iArr14 = new int[]{2, 2, 1, 1, 3, 2};
        int[] iArr15 = new int[6];
        iArr15 = new int[]{2, 2, 1, 2, 3, 1};
        int[] iArr16 = new int[6];
        iArr16 = new int[]{2, 1, 3, 2, 1, 2};
        int[] iArr17 = new int[6];
        iArr17 = new int[]{2, 2, 3, 1, 1, 2};
        int[] iArr18 = new int[6];
        iArr18 = new int[]{3, 1, 2, 1, 3, 1};
        int[] iArr19 = new int[6];
        iArr19 = new int[]{3, 2, 1, 1, 2, 2};
        int[] iArr20 = new int[6];
        iArr20 = new int[]{3, 1, 2, 2, 1, 2};
        int[] iArr21 = new int[6];
        iArr21 = new int[]{3, 2, 2, 1, 1, 2};
        int[] iArr22 = new int[6];
        iArr22 = new int[]{2, 1, 2, 3, 2, 1};
        int[] iArr23 = new int[6];
        iArr23 = new int[]{2, 3, 2, 1, 2, 1};
        int[] iArr24 = new int[6];
        iArr24 = new int[]{1, 1, 2, 3, 1, 3};
        int[] iArr25 = new int[6];
        iArr25 = new int[]{1, 3, 2, 1, 1, 3};
        int[] iArr26 = new int[6];
        iArr26 = new int[]{2, 1, 1, 3, 1, 3};
        int[] iArr27 = new int[6];
        iArr27 = new int[]{2, 3, 1, 1, 1, 3};
        int[] iArr28 = new int[6];
        iArr28 = new int[]{2, 3, 1, 3, 1, 1};
        int[] iArr29 = new int[6];
        iArr29 = new int[]{1, 1, 2, 3, 3, 1};
        int[] iArr30 = new int[6];
        iArr30 = new int[]{1, 3, 2, 1, 3, 1};
        int[] iArr31 = new int[6];
        iArr31 = new int[]{1, 1, 3, 1, 2, 3};
        int[] iArr32 = new int[6];
        iArr32 = new int[]{1, 3, 3, 1, 2, 1};
        int[] iArr33 = new int[6];
        iArr33 = new int[]{3, 1, 3, 1, 2, 1};
        int[] iArr34 = new int[6];
        iArr34 = new int[]{2, 3, 1, 1, 3, 1};
        int[] iArr35 = new int[6];
        iArr35 = new int[]{2, 1, 3, 1, 1, 3};
        int[] iArr36 = new int[6];
        iArr36 = new int[]{2, 1, 3, 1, 3, 1};
        int[] iArr37 = new int[6];
        iArr37 = new int[]{3, 1, 1, 1, 2, 3};
        int[] iArr38 = new int[6];
        iArr38 = new int[]{3, 1, 1, 3, 2, 1};
        int[] iArr39 = new int[6];
        iArr39 = new int[]{3, 3, 1, 1, 2, 1};
        int[] iArr40 = new int[6];
        iArr40 = new int[]{3, 1, 2, 1, 1, 3};
        int[] iArr41 = new int[6];
        iArr41 = new int[]{3, 1, 2, 3, 1, 1};
        int[] iArr42 = new int[6];
        iArr42 = new int[]{3, 3, 2, 1, 1, 1};
        int[] iArr43 = new int[6];
        iArr43 = new int[]{3, 1, 4, 1, 1, 1};
        int[] iArr44 = new int[6];
        iArr44 = new int[]{2, 2, 1, 4, 1, 1};
        int[] iArr45 = new int[6];
        iArr45 = new int[]{4, 3, 1, 1, 1, 1};
        int[] iArr46 = new int[6];
        iArr46 = new int[]{1, 1, 1, 4, 2, 2};
        int[] iArr47 = new int[6];
        iArr47 = new int[]{1, 2, 1, 1, 2, 4};
        int[] iArr48 = new int[6];
        iArr48 = new int[]{1, 2, 1, 4, 2, 1};
        int[] iArr49 = new int[6];
        iArr49 = new int[]{1, 4, 1, 1, 2, 2};
        int[] iArr50 = new int[6];
        iArr50 = new int[]{1, 4, 1, 2, 2, 1};
        int[] iArr51 = new int[6];
        iArr51 = new int[]{1, 1, 2, 2, 1, 4};
        int[] iArr52 = new int[6];
        iArr52 = new int[]{1, 2, 2, 1, 1, 4};
        int[] iArr53 = new int[6];
        iArr53 = new int[]{1, 4, 2, 1, 1, 2};
        int[] iArr54 = new int[6];
        iArr54 = new int[]{1, 4, 2, 2, 1, 1};
        int[] iArr55 = new int[6];
        iArr55 = new int[]{2, 4, 1, 2, 1, 1};
        int[] iArr56 = new int[6];
        iArr56 = new int[]{2, 2, 1, 1, 1, 4};
        int[] iArr57 = new int[6];
        iArr57 = new int[]{4, 1, 3, 1, 1, 1};
        int[] iArr58 = new int[6];
        iArr58 = new int[]{2, 4, 1, 1, 1, 2};
        int[] iArr59 = new int[6];
        iArr59 = new int[]{1, 1, 1, 2, 4, 2};
        int[] iArr60 = new int[6];
        iArr60 = new int[]{1, 2, 1, 1, 4, 2};
        int[] iArr61 = new int[6];
        iArr61 = new int[]{1, 2, 1, 2, 4, 1};
        int[] iArr62 = new int[6];
        iArr62 = new int[]{1, 1, 4, 2, 1, 2};
        int[] iArr63 = new int[6];
        iArr63 = new int[]{1, 2, 4, 2, 1, 1};
        int[] iArr64 = new int[6];
        iArr64 = new int[]{4, 1, 1, 2, 1, 2};
        int[] iArr65 = new int[6];
        iArr65 = new int[]{4, 2, 1, 2, 1, 1};
        int[] iArr66 = new int[6];
        iArr66 = new int[]{2, 1, 4, 1, 2, 1};
        int[] iArr67 = new int[6];
        iArr67 = new int[]{1, 1, 1, 3, 4, 1};
        int[] iArr68 = new int[6];
        iArr68 = new int[]{1, 3, 1, 1, 4, 1};
        int[] iArr69 = new int[6];
        iArr69 = new int[]{1, 1, 4, 3, 1, 1};
        int[] iArr70 = new int[6];
        iArr70 = new int[]{4, 1, 1, 1, 1, 3};
        int[] iArr71 = new int[6];
        iArr71 = new int[]{4, 1, 1, 3, 1, 1};
        int[] iArr72 = new int[6];
        iArr72 = new int[]{1, 1, 3, 1, 4, 1};
        int[] iArr73 = new int[6];
        iArr73 = new int[]{1, 1, 4, 1, 3, 1};
        int[] iArr74 = new int[6];
        iArr74 = new int[]{3, 1, 1, 1, 4, 1};
        int[] iArr75 = new int[6];
        iArr75 = new int[]{4, 1, 1, 1, 3, 1};
        int[] iArr76 = new int[6];
        iArr76 = new int[]{2, 1, 1, 2, 1, 4};
        r78 = new int[6];
        r78 = new int[]{2, 1, 1, 2, 3, 2};
        r79 = new int[7];
        r79 = new int[]{2, 3, 3, 1, 1, 1, 2};
        CODE_PATTERNS = new int[][]{new int[]{2, 1, 2, 2, 2, 2}, iArr, iArr2, iArr3, iArr4, new int[]{1, 3, 1, 2, 2, 2}, iArr5, iArr6, iArr7, iArr8, iArr9, new int[]{2, 3, 1, 2, 1, 2}, new int[]{1, 1, 2, 2, 3, 2}, iArr10, iArr11, new int[]{1, 1, 3, 2, 2, 2}, iArr12, new int[]{1, 2, 3, 2, 2, 1}, iArr13, iArr14, iArr15, iArr16, iArr17, iArr18, new int[]{3, 1, 1, 2, 2, 2}, iArr19, new int[]{3, 2, 1, 2, 2, 1}, iArr20, iArr21, new int[]{3, 2, 2, 2, 1, 1}, new int[]{2, 1, 2, 1, 2, 3}, iArr22, iArr23, new int[]{1, 1, 1, 3, 2, 3}, new int[]{1, 3, 1, 1, 2, 3}, new int[]{1, 3, 1, 3, 2, 1}, iArr24, iArr25, new int[]{1, 3, 2, 3, 1, 1}, iArr26, iArr27, iArr28, new int[]{1, 1, 2, 1, 3, 3}, iArr29, iArr30, iArr31, new int[]{1, 1, 3, 3, 2, 1}, iArr32, iArr33, new int[]{2, 1, 1, 3, 3, 1}, iArr34, iArr35, new int[]{2, 1, 3, 3, 1, 1}, iArr36, iArr37, iArr38, iArr39, iArr40, iArr41, iArr42, iArr43, iArr44, iArr45, new int[]{1, 1, 1, 2, 2, 4}, iArr46, iArr47, iArr48, iArr49, iArr50, iArr51, new int[]{1, 1, 2, 4, 1, 2}, iArr52, new int[]{1, 2, 2, 4, 1, 1}, iArr53, iArr54, iArr55, iArr56, iArr57, iArr58, new int[]{1, 3, 4, 1, 1, 1}, iArr59, iArr60, iArr61, iArr62, new int[]{1, 2, 4, 1, 1, 2}, iArr63, iArr64, new int[]{4, 2, 1, 1, 1, 2}, iArr65, new int[]{2, 1, 2, 1, 4, 1}, iArr66, new int[]{4, 1, 2, 1, 2, 1}, new int[]{1, 1, 1, 1, 4, 3}, iArr67, iArr68, new int[]{1, 1, 4, 1, 1, 3}, iArr69, iArr70, iArr71, iArr72, iArr73, iArr74, iArr75, new int[]{2, 1, 1, 4, 1, 2}, iArr76, r78, r79};
    }

    private static int decodeCode(BitArray bitArray, int[] iArr, int i) throws NotFoundException {
        OneDReader.recordPattern(bitArray, i, iArr);
        int i2 = 64;
        int i3 = -1;
        for (int i4 = 0; i4 < CODE_PATTERNS.length; i4++) {
            int patternMatchVariance = OneDReader.patternMatchVariance(iArr, CODE_PATTERNS[i4], MAX_INDIVIDUAL_VARIANCE);
            if (patternMatchVariance < i2) {
                i3 = i4;
                i2 = patternMatchVariance;
            }
        }
        if (i3 >= 0) {
            return i3;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static int[] findStartPattern(BitArray bitArray) throws NotFoundException {
        int size = bitArray.getSize();
        int nextSet = bitArray.getNextSet(0);
        int i = 0;
        Object obj = new int[6];
        int i2 = 0;
        int i3 = nextSet;
        while (i3 < size) {
            if ((bitArray.get(i3) ^ i2) != 0) {
                obj[i] = obj[i] + 1;
            } else {
                if (i == 5) {
                    int i4 = 64;
                    int i5 = -1;
                    int i6 = 103;
                    while (i6 <= 105) {
                        int patternMatchVariance = OneDReader.patternMatchVariance(obj, CODE_PATTERNS[i6], MAX_INDIVIDUAL_VARIANCE);
                        if (patternMatchVariance < i4) {
                            i5 = i6;
                        } else {
                            patternMatchVariance = i4;
                        }
                        i6++;
                        i4 = patternMatchVariance;
                    }
                    if (i5 < 0 || !bitArray.isRange(Math.max(0, nextSet - ((i3 - nextSet) / 2)), nextSet, false)) {
                        nextSet += obj[0] + obj[1];
                        System.arraycopy(obj, 2, obj, 0, 4);
                        obj[4] = null;
                        obj[5] = null;
                        i--;
                    } else {
                        return new int[]{nextSet, i3, i5};
                    }
                }
                i++;
                obj[i] = 1;
                i2 = i2 == 0 ? 1 : 0;
            }
            i3++;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.zxing.Result decodeRow(int r23, com.google.zxing.common.BitArray r24, java.util.Map<com.google.zxing.DecodeHintType, ?> r25) throws com.google.zxing.NotFoundException, com.google.zxing.FormatException, com.google.zxing.ChecksumException {
        /*
        r22 = this;
        if (r25 == 0) goto L_0x001c;
    L_0x0002:
        r2 = com.google.zxing.DecodeHintType.ASSUME_GS1;
        r0 = r25;
        r2 = r0.containsKey(r2);
        if (r2 == 0) goto L_0x001c;
    L_0x000c:
        r2 = 1;
    L_0x000d:
        r16 = findStartPattern(r24);
        r3 = 2;
        r7 = r16[r3];
        switch(r7) {
            case 103: goto L_0x001e;
            case 104: goto L_0x0080;
            case 105: goto L_0x0083;
            default: goto L_0x0017;
        };
    L_0x0017:
        r2 = com.google.zxing.FormatException.getFormatInstance();
        throw r2;
    L_0x001c:
        r2 = 0;
        goto L_0x000d;
    L_0x001e:
        r3 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
    L_0x0020:
        r17 = new java.lang.StringBuilder;
        r4 = 20;
        r0 = r17;
        r0.<init>(r4);
        r18 = new java.util.ArrayList;
        r4 = 20;
        r0 = r18;
        r0.<init>(r4);
        r4 = 0;
        r11 = r16[r4];
        r4 = 1;
        r10 = r16[r4];
        r4 = 6;
        r0 = new int[r4];
        r19 = r0;
        r8 = 1;
        r6 = 0;
        r5 = 0;
        r13 = 0;
        r9 = 0;
        r4 = 0;
        r14 = r6;
        r6 = r9;
        r9 = r3;
        r3 = r5;
        r5 = r8;
        r8 = r11;
        r11 = r10;
        r20 = r7;
        r7 = r4;
        r4 = r20;
    L_0x004f:
        if (r6 != 0) goto L_0x0222;
    L_0x0051:
        r8 = 0;
        r0 = r24;
        r1 = r19;
        r15 = decodeCode(r0, r1, r11);
        r7 = (byte) r15;
        r7 = java.lang.Byte.valueOf(r7);
        r0 = r18;
        r0.add(r7);
        r7 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r15 == r7) goto L_0x0069;
    L_0x0068:
        r5 = 1;
    L_0x0069:
        r7 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r15 == r7) goto L_0x0072;
    L_0x006d:
        r3 = r3 + 1;
        r7 = r3 * r15;
        r4 = r4 + r7;
    L_0x0072:
        r7 = 0;
        r10 = r11;
        r12 = r7;
    L_0x0075:
        r7 = 6;
        if (r12 >= r7) goto L_0x0086;
    L_0x0078:
        r7 = r19[r12];
        r7 = r7 + r10;
        r10 = r12 + 1;
        r12 = r10;
        r10 = r7;
        goto L_0x0075;
    L_0x0080:
        r3 = 100;
        goto L_0x0020;
    L_0x0083:
        r3 = 99;
        goto L_0x0020;
    L_0x0086:
        switch(r15) {
            case 103: goto L_0x00a4;
            case 104: goto L_0x00a4;
            case 105: goto L_0x00a4;
            default: goto L_0x0089;
        };
    L_0x0089:
        switch(r9) {
            case 99: goto L_0x01a9;
            case 100: goto L_0x0128;
            case 101: goto L_0x00a9;
            default: goto L_0x008c;
        };
    L_0x008c:
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
    L_0x0093:
        if (r14 == 0) goto L_0x009b;
    L_0x0095:
        r9 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        if (r5 != r9) goto L_0x021e;
    L_0x0099:
        r5 = 100;
    L_0x009b:
        r14 = r6;
        r9 = r5;
        r5 = r7;
        r6 = r8;
        r8 = r11;
        r7 = r13;
        r11 = r10;
        r13 = r15;
        goto L_0x004f;
    L_0x00a4:
        r2 = com.google.zxing.FormatException.getFormatInstance();
        throw r2;
    L_0x00a9:
        r7 = 64;
        if (r15 >= r7) goto L_0x00bd;
    L_0x00ad:
        r7 = r15 + 32;
        r7 = (char) r7;
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x00bd:
        r7 = 96;
        if (r15 >= r7) goto L_0x00d1;
    L_0x00c1:
        r7 = r15 + -64;
        r7 = (char) r7;
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x00d1:
        r7 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r15 == r7) goto L_0x02c7;
    L_0x00d5:
        r5 = 0;
        r7 = r5;
    L_0x00d7:
        switch(r15) {
            case 96: goto L_0x0103;
            case 97: goto L_0x0103;
            case 98: goto L_0x010a;
            case 99: goto L_0x011d;
            case 100: goto L_0x0114;
            case 101: goto L_0x0103;
            case 102: goto L_0x00df;
            case 103: goto L_0x02c4;
            case 104: goto L_0x02c4;
            case 105: goto L_0x02c4;
            case 106: goto L_0x0126;
            default: goto L_0x00da;
        };
    L_0x00da:
        r5 = r6;
    L_0x00db:
        r6 = r8;
        r8 = r5;
        r5 = r9;
        goto L_0x0093;
    L_0x00df:
        if (r2 == 0) goto L_0x02c4;
    L_0x00e1:
        r5 = r17.length();
        if (r5 != 0) goto L_0x00f5;
    L_0x00e7:
        r5 = "]C1";
        r0 = r17;
        r0.append(r5);
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x00f5:
        r5 = 29;
        r0 = r17;
        r0.append(r5);
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x0103:
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x010a:
        r5 = 1;
        r8 = 100;
        r20 = r5;
        r5 = r8;
        r8 = r6;
        r6 = r20;
        goto L_0x0093;
    L_0x0114:
        r5 = 100;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        goto L_0x0093;
    L_0x011d:
        r5 = 99;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        goto L_0x0093;
    L_0x0126:
        r5 = 1;
        goto L_0x00db;
    L_0x0128:
        r7 = 96;
        if (r15 >= r7) goto L_0x013d;
    L_0x012c:
        r7 = r15 + 32;
        r7 = (char) r7;
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x013d:
        r7 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r15 == r7) goto L_0x0142;
    L_0x0141:
        r5 = 0;
    L_0x0142:
        switch(r15) {
            case 96: goto L_0x0176;
            case 97: goto L_0x0176;
            case 98: goto L_0x017f;
            case 99: goto L_0x0199;
            case 100: goto L_0x0176;
            case 101: goto L_0x018b;
            case 102: goto L_0x014e;
            case 103: goto L_0x0145;
            case 104: goto L_0x0145;
            case 105: goto L_0x0145;
            case 106: goto L_0x01a7;
            default: goto L_0x0145;
        };
    L_0x0145:
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x014e:
        if (r2 == 0) goto L_0x0145;
    L_0x0150:
        r7 = r17.length();
        if (r7 != 0) goto L_0x0166;
    L_0x0156:
        r7 = "]C1";
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x0166:
        r7 = 29;
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x0176:
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x017f:
        r7 = 1;
        r8 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r20 = r7;
        r7 = r5;
        r5 = r8;
        r8 = r6;
        r6 = r20;
        goto L_0x0093;
    L_0x018b:
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        r21 = r5;
        r5 = r7;
        r7 = r21;
        goto L_0x0093;
    L_0x0199:
        r7 = 99;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        r21 = r5;
        r5 = r7;
        r7 = r21;
        goto L_0x0093;
    L_0x01a7:
        r6 = 1;
        goto L_0x0145;
    L_0x01a9:
        r7 = 100;
        if (r15 >= r7) goto L_0x01c6;
    L_0x01ad:
        r7 = 10;
        if (r15 >= r7) goto L_0x01b8;
    L_0x01b1:
        r7 = 48;
        r0 = r17;
        r0.append(r7);
    L_0x01b8:
        r0 = r17;
        r0.append(r15);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x01c6:
        r7 = 106; // 0x6a float:1.49E-43 double:5.24E-322;
        if (r15 == r7) goto L_0x01cb;
    L_0x01ca:
        r5 = 0;
    L_0x01cb:
        switch(r15) {
            case 100: goto L_0x01d0;
            case 101: goto L_0x0206;
            case 102: goto L_0x01de;
            case 103: goto L_0x01ce;
            case 104: goto L_0x01ce;
            case 105: goto L_0x01ce;
            case 106: goto L_0x0214;
            default: goto L_0x01ce;
        };
    L_0x01ce:
        goto L_0x008c;
    L_0x01d0:
        r7 = 100;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        r21 = r5;
        r5 = r7;
        r7 = r21;
        goto L_0x0093;
    L_0x01de:
        if (r2 == 0) goto L_0x008c;
    L_0x01e0:
        r7 = r17.length();
        if (r7 != 0) goto L_0x01f6;
    L_0x01e6:
        r7 = "]C1";
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x01f6:
        r7 = 29;
        r0 = r17;
        r0.append(r7);
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x0206:
        r7 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        r20 = r8;
        r8 = r6;
        r6 = r20;
        r21 = r5;
        r5 = r7;
        r7 = r21;
        goto L_0x0093;
    L_0x0214:
        r6 = 1;
        r7 = r5;
        r5 = r9;
        r20 = r6;
        r6 = r8;
        r8 = r20;
        goto L_0x0093;
    L_0x021e:
        r5 = 101; // 0x65 float:1.42E-43 double:5.0E-322;
        goto L_0x009b;
    L_0x0222:
        r0 = r24;
        r2 = r0.getNextUnset(r11);
        r6 = r24.getSize();
        r10 = r2 - r8;
        r10 = r10 / 2;
        r10 = r10 + r2;
        r6 = java.lang.Math.min(r6, r10);
        r10 = 0;
        r0 = r24;
        r6 = r0.isRange(r2, r6, r10);
        if (r6 != 0) goto L_0x0243;
    L_0x023e:
        r2 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r2;
    L_0x0243:
        r3 = r3 * r7;
        r3 = r4 - r3;
        r3 = r3 % 103;
        if (r3 == r7) goto L_0x024f;
    L_0x024a:
        r2 = com.google.zxing.ChecksumException.getChecksumInstance();
        throw r2;
    L_0x024f:
        r3 = r17.length();
        if (r3 != 0) goto L_0x025a;
    L_0x0255:
        r2 = com.google.zxing.NotFoundException.getNotFoundInstance();
        throw r2;
    L_0x025a:
        if (r3 <= 0) goto L_0x0269;
    L_0x025c:
        if (r5 == 0) goto L_0x0269;
    L_0x025e:
        r4 = 99;
        if (r9 != r4) goto L_0x0297;
    L_0x0262:
        r4 = r3 + -2;
        r0 = r17;
        r0.delete(r4, r3);
    L_0x0269:
        r3 = 1;
        r3 = r16[r3];
        r4 = 0;
        r4 = r16[r4];
        r3 = r3 + r4;
        r3 = (float) r3;
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r4 = r3 / r4;
        r2 = r2 + r8;
        r2 = (float) r2;
        r3 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r5 = r2 / r3;
        r6 = r18.size();
        r7 = new byte[r6];
        r2 = 0;
        r3 = r2;
    L_0x0283:
        if (r3 >= r6) goto L_0x029f;
    L_0x0285:
        r0 = r18;
        r2 = r0.get(r3);
        r2 = (java.lang.Byte) r2;
        r2 = r2.byteValue();
        r7[r3] = r2;
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0283;
    L_0x0297:
        r4 = r3 + -1;
        r0 = r17;
        r0.delete(r4, r3);
        goto L_0x0269;
    L_0x029f:
        r2 = r17.toString();
        r3 = new com.google.zxing.ResultPoint;
        r0 = r23;
        r6 = (float) r0;
        r3.<init>(r4, r6);
        r4 = new com.google.zxing.ResultPoint;
        r0 = r23;
        r6 = (float) r0;
        r4.<init>(r5, r6);
        r5 = com.google.zxing.BarcodeFormat.CODE_128;
        r6 = new com.google.zxing.Result;
        r8 = 2;
        r8 = new com.google.zxing.ResultPoint[r8];
        r9 = 0;
        r8[r9] = r3;
        r3 = 1;
        r8[r3] = r4;
        r6.<init>(r2, r7, r8, r5);
        return r6;
    L_0x02c4:
        r5 = r6;
        goto L_0x00db;
    L_0x02c7:
        r7 = r5;
        goto L_0x00d7;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code128Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }
}
