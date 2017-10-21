package org.spongycastle.math.ntru.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.spongycastle.math.ntru.euclid.IntEuclidean;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.TernaryPolynomial;

public class Util {
    private static volatile boolean IS_64_BITNESS_KNOWN;
    private static volatile boolean IS_64_BIT_JVM;

    public static TernaryPolynomial generateRandomTernary(int i, int i2, int i3, boolean z, SecureRandom secureRandom) {
        return z ? SparseTernaryPolynomial.generateRandom(i, i2, i3, secureRandom) : DenseTernaryPolynomial.generateRandom(i, i2, i3, secureRandom);
    }

    public static int[] generateRandomTernary(int i, int i2, int i3, SecureRandom secureRandom) {
        int i4;
        Integer num = new Integer(1);
        Integer num2 = new Integer(-1);
        Integer num3 = new Integer(0);
        List arrayList = new ArrayList();
        for (i4 = 0; i4 < i2; i4++) {
            arrayList.add(num);
        }
        for (i4 = 0; i4 < i3; i4++) {
            arrayList.add(num2);
        }
        while (arrayList.size() < i) {
            arrayList.add(num3);
        }
        Collections.shuffle(arrayList, secureRandom);
        int[] iArr = new int[i];
        for (i4 = 0; i4 < i; i4++) {
            iArr[i4] = ((Integer) arrayList.get(i4)).intValue();
        }
        return iArr;
    }

    public static int invert(int i, int i2) {
        int i3 = i % i2;
        if (i3 < 0) {
            i3 += i2;
        }
        return IntEuclidean.calculate(i3, i2).x;
    }

    public static boolean is64BitJVM() {
        if (!IS_64_BITNESS_KNOWN) {
            String property = System.getProperty("os.arch");
            boolean z = "amd64".equals(property) || "x86_64".equals(property) || "ppc64".equals(property) || "64".equals(System.getProperty("sun.arch.data.model"));
            IS_64_BIT_JVM = z;
            IS_64_BITNESS_KNOWN = true;
        }
        return IS_64_BIT_JVM;
    }

    public static int pow(int i, int i2, int i3) {
        int i4 = 1;
        for (int i5 = 0; i5 < i2; i5++) {
            i4 = (i4 * i) % i3;
        }
        return i4;
    }

    public static long pow(long j, int i, long j2) {
        long j3 = 1;
        for (int i2 = 0; i2 < i; i2++) {
            j3 = (j3 * j) % j2;
        }
        return j3;
    }

    public static byte[] readFullLength(InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        if (inputStream.read(bArr) == i) {
            return bArr;
        }
        throw new IOException("Not enough bytes to read.");
    }
}
