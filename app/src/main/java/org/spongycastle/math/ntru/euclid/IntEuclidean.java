package org.spongycastle.math.ntru.euclid;

public class IntEuclidean {
    public int gcd;
    public int x;
    public int y;

    private IntEuclidean() {
    }

    public static IntEuclidean calculate(int i, int i2) {
        int i3 = 1;
        int i4 = 0;
        int i5 = 0;
        int i6 = i2;
        int i7 = 1;
        while (i6 != 0) {
            int i8 = i / i6;
            i2 = i % i6;
            i = i6;
            i6 = i2;
            int i9 = i3 - (i8 * i4);
            i3 = i4;
            i4 = i9;
            int i10 = i7;
            i7 = i5 - (i8 * i7);
            i5 = i10;
        }
        IntEuclidean intEuclidean = new IntEuclidean();
        intEuclidean.x = i3;
        intEuclidean.y = i5;
        intEuclidean.gcd = i;
        return intEuclidean;
    }
}
