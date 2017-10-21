package com.google.zxing.common.detector;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

public final class MonochromeRectangleDetector {
    private static final int MAX_MODULES = 32;
    private final BitMatrix image;

    public MonochromeRectangleDetector(BitMatrix bitMatrix) {
        this.image = bitMatrix;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private int[] blackWhiteRange(int r5, int r6, int r7, int r8, boolean r9) {
        /*
        r4 = this;
        r0 = r7 + r8;
        r1 = r0 >> 1;
        r2 = r1;
    L_0x0005:
        if (r2 < r7) goto L_0x0031;
    L_0x0007:
        if (r9 == 0) goto L_0x0014;
    L_0x0009:
        r0 = r4.image;
        r0 = r0.get(r2, r5);
        if (r0 == 0) goto L_0x001c;
    L_0x0011:
        r2 = r2 + -1;
        goto L_0x0005;
    L_0x0014:
        r0 = r4.image;
        r0 = r0.get(r5, r2);
        if (r0 != 0) goto L_0x0011;
    L_0x001c:
        r0 = r2;
    L_0x001d:
        r0 = r0 + -1;
        if (r0 < r7) goto L_0x002b;
    L_0x0021:
        if (r9 == 0) goto L_0x0042;
    L_0x0023:
        r3 = r4.image;
        r3 = r3.get(r0, r5);
        if (r3 == 0) goto L_0x001d;
    L_0x002b:
        if (r0 < r7) goto L_0x0031;
    L_0x002d:
        r3 = r2 - r0;
        if (r3 <= r6) goto L_0x004b;
    L_0x0031:
        r2 = r2 + 1;
    L_0x0033:
        if (r1 >= r8) goto L_0x006a;
    L_0x0035:
        if (r9 == 0) goto L_0x004d;
    L_0x0037:
        r0 = r4.image;
        r0 = r0.get(r1, r5);
        if (r0 == 0) goto L_0x0055;
    L_0x003f:
        r1 = r1 + 1;
        goto L_0x0033;
    L_0x0042:
        r3 = r4.image;
        r3 = r3.get(r5, r0);
        if (r3 == 0) goto L_0x001d;
    L_0x004a:
        goto L_0x002b;
    L_0x004b:
        r2 = r0;
        goto L_0x0005;
    L_0x004d:
        r0 = r4.image;
        r0 = r0.get(r5, r1);
        if (r0 != 0) goto L_0x003f;
    L_0x0055:
        r0 = r1;
    L_0x0056:
        r0 = r0 + 1;
        if (r0 >= r8) goto L_0x0064;
    L_0x005a:
        if (r9 == 0) goto L_0x0078;
    L_0x005c:
        r3 = r4.image;
        r3 = r3.get(r0, r5);
        if (r3 == 0) goto L_0x0056;
    L_0x0064:
        if (r0 >= r8) goto L_0x006a;
    L_0x0066:
        r3 = r0 - r1;
        if (r3 <= r6) goto L_0x0081;
    L_0x006a:
        r1 = r1 + -1;
        if (r1 <= r2) goto L_0x0083;
    L_0x006e:
        r0 = 2;
        r0 = new int[r0];
        r3 = 0;
        r0[r3] = r2;
        r2 = 1;
        r0[r2] = r1;
    L_0x0077:
        return r0;
    L_0x0078:
        r3 = r4.image;
        r3 = r3.get(r5, r0);
        if (r3 == 0) goto L_0x0056;
    L_0x0080:
        goto L_0x0064;
    L_0x0081:
        r1 = r0;
        goto L_0x0033;
    L_0x0083:
        r0 = 0;
        goto L_0x0077;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.common.detector.MonochromeRectangleDetector.blackWhiteRange(int, int, int, int, boolean):int[]");
    }

    private ResultPoint findCornerFromCenter(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) throws NotFoundException {
        int[] iArr = null;
        int i10 = i;
        int i11 = i5;
        while (i11 < i8 && i11 >= i7 && i10 < i4 && i10 >= i3) {
            int[] blackWhiteRange = i2 == 0 ? blackWhiteRange(i11, i9, i3, i4, true) : blackWhiteRange(i10, i9, i7, i8, false);
            if (blackWhiteRange != null) {
                i11 += i6;
                iArr = blackWhiteRange;
                i10 += i2;
            } else if (iArr == null) {
                throw NotFoundException.getNotFoundInstance();
            } else if (i2 == 0) {
                int i12 = i11 - i6;
                if (iArr[0] >= i) {
                    return new ResultPoint((float) iArr[1], (float) i12);
                }
                if (iArr[1] <= i) {
                    return new ResultPoint((float) iArr[0], (float) i12);
                }
                return new ResultPoint(i6 > 0 ? (float) iArr[0] : (float) iArr[1], (float) i12);
            } else {
                i11 = i10 - i2;
                if (iArr[0] >= i5) {
                    return new ResultPoint((float) i11, (float) iArr[1]);
                }
                if (iArr[1] <= i5) {
                    return new ResultPoint((float) i11, (float) iArr[0]);
                }
                return new ResultPoint((float) i11, i2 < 0 ? (float) iArr[0] : (float) iArr[1]);
            }
        }
        throw NotFoundException.getNotFoundInstance();
    }

    public final ResultPoint[] detect() throws NotFoundException {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int i = height >> 1;
        int i2 = width >> 1;
        int max = Math.max(1, height / 256);
        int max2 = Math.max(1, width / 256);
        int y = ((int) findCornerFromCenter(i2, 0, 0, width, i, -max, 0, height, i2 >> 1).getY()) - 1;
        int x = ((int) findCornerFromCenter(i2, -max2, 0, width, i, 0, y, height, i >> 1).getX()) - 1;
        ResultPoint findCornerFromCenter = findCornerFromCenter(i2, 0, x, ((int) findCornerFromCenter(i2, max2, x, width, i, 0, y, height, i >> 1).getX()) + 1, i, max, y, height, i2 >> 1);
        return new ResultPoint[]{findCornerFromCenter(i2, 0, x, width, i, -max, y, ((int) findCornerFromCenter.getY()) + 1, i2 >> 2), r13, r12, findCornerFromCenter};
    }
}
