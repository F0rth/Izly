package org.spongycastle.math.ntru.euclid;

import java.math.BigInteger;

public class BigIntEuclidean {
    public BigInteger gcd;
    public BigInteger x;
    public BigInteger y;

    private BigIntEuclidean() {
    }

    public static BigIntEuclidean calculate(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigInteger3 = BigInteger.ZERO;
        BigInteger bigInteger4 = BigInteger.ONE;
        BigInteger bigInteger5 = BigInteger.ONE;
        BigInteger bigInteger6 = BigInteger.ZERO;
        BigInteger bigInteger7 = bigInteger4;
        bigInteger4 = bigInteger2;
        while (!bigInteger4.equals(BigInteger.ZERO)) {
            BigInteger[] divideAndRemainder = bigInteger.divideAndRemainder(bigInteger4);
            BigInteger bigInteger8 = divideAndRemainder[0];
            bigInteger2 = divideAndRemainder[1];
            bigInteger7 = bigInteger7.subtract(bigInteger8.multiply(bigInteger3));
            bigInteger = bigInteger4;
            bigInteger4 = bigInteger2;
            BigInteger bigInteger9 = bigInteger5;
            bigInteger5 = bigInteger6.subtract(bigInteger8.multiply(bigInteger5));
            bigInteger6 = bigInteger9;
            BigInteger bigInteger10 = bigInteger3;
            bigInteger3 = bigInteger7;
            bigInteger7 = bigInteger10;
        }
        BigIntEuclidean bigIntEuclidean = new BigIntEuclidean();
        bigIntEuclidean.x = bigInteger7;
        bigIntEuclidean.y = bigInteger6;
        bigIntEuclidean.gcd = bigInteger;
        return bigIntEuclidean;
    }
}
