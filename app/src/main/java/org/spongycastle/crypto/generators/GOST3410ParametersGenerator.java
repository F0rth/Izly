package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410ValidationParameters;

public class GOST3410ParametersGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);
    private SecureRandom init_random;
    private int size;
    private int typeproc;

    private int procedure_A(int i, int i2, BigInteger[] bigIntegerArr, int i3) {
        while (true) {
            if (i >= 0 && i <= 65536) {
                break;
            }
            i = this.init_random.nextInt() / 32768;
        }
        while (true) {
            if (i2 >= 0 && i2 <= 65536 && i2 / 2 != 0) {
                break;
            }
            i2 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger bigInteger = new BigInteger(Integer.toString(i2));
        BigInteger bigInteger2 = new BigInteger("19381");
        BigInteger bigInteger3 = new BigInteger(Integer.toString(i));
        Object obj = new int[]{i3};
        int i4 = 0;
        for (int i5 = 0; obj[i5] >= 17; i5++) {
            Object obj2 = new int[(obj.length + 1)];
            System.arraycopy(obj, 0, obj2, 0, obj.length);
            obj = new int[obj2.length];
            System.arraycopy(obj2, 0, obj, 0, obj2.length);
            obj[i5 + 1] = obj[i5] / 2;
            i4 = i5 + 1;
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[(i4 + 1)];
        bigIntegerArr2[i4] = new BigInteger("8003", 16);
        Object obj3 = new BigInteger[]{bigInteger3};
        int i6 = i4 - 1;
        for (int i7 = 0; i7 < i4; i7++) {
            int i8;
            int i9 = obj[i6] / 16;
            while (true) {
                int i10;
                Object obj4 = new BigInteger[obj3.length];
                System.arraycopy(obj3, 0, obj4, 0, obj3.length);
                obj3 = new BigInteger[(i9 + 1)];
                System.arraycopy(obj4, 0, obj3, 0, obj4.length);
                for (i8 = 0; i8 < i9; i8++) {
                    obj3[i8 + 1] = obj3[i8].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(16));
                }
                BigInteger bigInteger4 = new BigInteger("0");
                for (i10 = 0; i10 < i9; i10++) {
                    bigInteger4 = bigInteger4.add(obj3[i10].multiply(TWO.pow(i10 * 16)));
                }
                obj3[0] = obj3[i9];
                bigInteger4 = TWO.pow(obj[i6] - 1).divide(bigIntegerArr2[i6 + 1]).add(TWO.pow(obj[i6] - 1).multiply(bigInteger4).divide(bigIntegerArr2[i6 + 1].multiply(TWO.pow(i9 * 16))));
                if (bigInteger4.mod(TWO).compareTo(ONE) == 0) {
                    bigInteger4 = bigInteger4.add(ONE);
                }
                i10 = 0;
                while (true) {
                    bigIntegerArr2[i6] = bigIntegerArr2[i6 + 1].multiply(bigInteger4.add(BigInteger.valueOf((long) i10))).add(ONE);
                    if (bigIntegerArr2[i6].compareTo(TWO.pow(obj[i6])) != 1) {
                        if (TWO.modPow(bigIntegerArr2[i6 + 1].multiply(bigInteger4.add(BigInteger.valueOf((long) i10))), bigIntegerArr2[i6]).compareTo(ONE) == 0 && TWO.modPow(bigInteger4.add(BigInteger.valueOf((long) i10)), bigIntegerArr2[i6]).compareTo(ONE) != 0) {
                            break;
                        }
                        i10 += 2;
                    }
                }
            }
            i8 = i6 - 1;
            if (i8 < 0) {
                bigIntegerArr[0] = bigIntegerArr2[0];
                bigIntegerArr[1] = bigIntegerArr2[1];
                return obj3[0].intValue();
            }
            i6 = i8;
        }
        return obj3[0].intValue();
    }

    private long procedure_Aa(long j, long j2, BigInteger[] bigIntegerArr, int i) {
        while (true) {
            if (j >= 0 && j <= 4294967296L) {
                break;
            }
            j = (long) (this.init_random.nextInt() * 2);
        }
        while (true) {
            if (j2 >= 0 && j2 <= 4294967296L && j2 / 2 != 0) {
                break;
            }
            j2 = (long) ((this.init_random.nextInt() * 2) + 1);
        }
        BigInteger bigInteger = new BigInteger(Long.toString(j2));
        BigInteger bigInteger2 = new BigInteger("97781173");
        BigInteger bigInteger3 = new BigInteger(Long.toString(j));
        Object obj = new int[]{i};
        int i2 = 0;
        for (int i3 = 0; obj[i3] >= 33; i3++) {
            Object obj2 = new int[(obj.length + 1)];
            System.arraycopy(obj, 0, obj2, 0, obj.length);
            obj = new int[obj2.length];
            System.arraycopy(obj2, 0, obj, 0, obj2.length);
            obj[i3 + 1] = obj[i3] / 2;
            i2 = i3 + 1;
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[(i2 + 1)];
        bigIntegerArr2[i2] = new BigInteger("8000000B", 16);
        Object obj3 = new BigInteger[]{bigInteger3};
        int i4 = i2 - 1;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6;
            int i7 = obj[i4] / 32;
            while (true) {
                int i8;
                Object obj4 = new BigInteger[obj3.length];
                System.arraycopy(obj3, 0, obj4, 0, obj3.length);
                obj3 = new BigInteger[(i7 + 1)];
                System.arraycopy(obj4, 0, obj3, 0, obj4.length);
                for (i6 = 0; i6 < i7; i6++) {
                    obj3[i6 + 1] = obj3[i6].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(32));
                }
                BigInteger bigInteger4 = new BigInteger("0");
                for (i8 = 0; i8 < i7; i8++) {
                    bigInteger4 = bigInteger4.add(obj3[i8].multiply(TWO.pow(i8 * 32)));
                }
                obj3[0] = obj3[i7];
                bigInteger4 = TWO.pow(obj[i4] - 1).divide(bigIntegerArr2[i4 + 1]).add(TWO.pow(obj[i4] - 1).multiply(bigInteger4).divide(bigIntegerArr2[i4 + 1].multiply(TWO.pow(i7 * 32))));
                if (bigInteger4.mod(TWO).compareTo(ONE) == 0) {
                    bigInteger4 = bigInteger4.add(ONE);
                }
                i8 = 0;
                while (true) {
                    bigIntegerArr2[i4] = bigIntegerArr2[i4 + 1].multiply(bigInteger4.add(BigInteger.valueOf((long) i8))).add(ONE);
                    if (bigIntegerArr2[i4].compareTo(TWO.pow(obj[i4])) != 1) {
                        if (TWO.modPow(bigIntegerArr2[i4 + 1].multiply(bigInteger4.add(BigInteger.valueOf((long) i8))), bigIntegerArr2[i4]).compareTo(ONE) == 0 && TWO.modPow(bigInteger4.add(BigInteger.valueOf((long) i8)), bigIntegerArr2[i4]).compareTo(ONE) != 0) {
                            break;
                        }
                        i8 += 2;
                    }
                }
            }
            i6 = i4 - 1;
            if (i6 < 0) {
                bigIntegerArr[0] = bigIntegerArr2[0];
                bigIntegerArr[1] = bigIntegerArr2[1];
                return obj3[0].longValue();
            }
            i4 = i6;
        }
        return obj3[0].longValue();
    }

    private void procedure_B(int i, int i2, BigInteger[] bigIntegerArr) {
        while (true) {
            if (i >= 0 && i <= PKIFailureInfo.notAuthorized) {
                break;
            }
            i = this.init_random.nextInt() / 32768;
        }
        while (true) {
            if (i2 >= 0 && i2 <= PKIFailureInfo.notAuthorized && i2 / 2 != 0) {
                break;
            }
            i2 = (this.init_random.nextInt() / 32768) + 1;
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Integer.toString(i2));
        BigInteger bigInteger2 = new BigInteger("19381");
        int procedure_A = procedure_A(i, i2, bigIntegerArr2, 256);
        BigInteger bigInteger3 = bigIntegerArr2[0];
        procedure_A = procedure_A(procedure_A, i2, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[65];
        bigIntegerArr3[0] = new BigInteger(Integer.toString(procedure_A));
        while (true) {
            for (int i3 = 0; i3 < 64; i3++) {
                bigIntegerArr3[i3 + 1] = bigIntegerArr3[i3].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(16));
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (procedure_A = 0; procedure_A < 64; procedure_A++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[procedure_A].multiply(TWO.pow(procedure_A * 16)));
            }
            bigIntegerArr3[0] = bigIntegerArr3[64];
            bigInteger5 = TWO.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(TWO.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(TWO.pow(PKIFailureInfo.badRecipientNonce))));
            if (bigInteger5.mod(TWO).compareTo(ONE) == 0) {
                bigInteger5 = bigInteger5.add(ONE);
            }
            procedure_A = 0;
            while (true) {
                BigInteger add = bigInteger3.multiply(bigInteger4).multiply(bigInteger5.add(BigInteger.valueOf((long) procedure_A))).add(ONE);
                if (add.compareTo(TWO.pow(PKIFailureInfo.badRecipientNonce)) != 1) {
                    if (TWO.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger5.add(BigInteger.valueOf((long) procedure_A))), add).compareTo(ONE) != 0 || TWO.modPow(bigInteger3.multiply(bigInteger5.add(BigInteger.valueOf((long) procedure_A))), add).compareTo(ONE) == 0) {
                        procedure_A += 2;
                    } else {
                        bigIntegerArr[0] = add;
                        bigIntegerArr[1] = bigInteger3;
                        return;
                    }
                }
            }
        }
    }

    private void procedure_Bb(long j, long j2, BigInteger[] bigIntegerArr) {
        long j3 = j;
        while (true) {
            if (j3 >= 0 && j3 <= 4294967296L) {
                break;
            }
            j3 = (long) (this.init_random.nextInt() * 2);
        }
        long j4 = j2;
        while (true) {
            if (j4 >= 0 && j4 <= 4294967296L && j4 / 2 != 0) {
                break;
            }
            j4 = (long) ((this.init_random.nextInt() * 2) + 1);
        }
        BigInteger[] bigIntegerArr2 = new BigInteger[2];
        BigInteger bigInteger = new BigInteger(Long.toString(j4));
        BigInteger bigInteger2 = new BigInteger("97781173");
        j3 = procedure_Aa(j3, j4, bigIntegerArr2, 256);
        BigInteger bigInteger3 = bigIntegerArr2[0];
        long procedure_Aa = procedure_Aa(j3, j4, bigIntegerArr2, 512);
        BigInteger bigInteger4 = bigIntegerArr2[0];
        BigInteger[] bigIntegerArr3 = new BigInteger[33];
        bigIntegerArr3[0] = new BigInteger(Long.toString(procedure_Aa));
        while (true) {
            int i;
            for (int i2 = 0; i2 < 32; i2++) {
                bigIntegerArr3[i2 + 1] = bigIntegerArr3[i2].multiply(bigInteger2).add(bigInteger).mod(TWO.pow(32));
            }
            BigInteger bigInteger5 = new BigInteger("0");
            for (i = 0; i < 32; i++) {
                bigInteger5 = bigInteger5.add(bigIntegerArr3[i].multiply(TWO.pow(i * 32)));
            }
            bigIntegerArr3[0] = bigIntegerArr3[32];
            bigInteger5 = TWO.pow(1023).divide(bigInteger3.multiply(bigInteger4)).add(TWO.pow(1023).multiply(bigInteger5).divide(bigInteger3.multiply(bigInteger4).multiply(TWO.pow(PKIFailureInfo.badRecipientNonce))));
            if (bigInteger5.mod(TWO).compareTo(ONE) == 0) {
                bigInteger5 = bigInteger5.add(ONE);
            }
            i = 0;
            while (true) {
                BigInteger add = bigInteger3.multiply(bigInteger4).multiply(bigInteger5.add(BigInteger.valueOf((long) i))).add(ONE);
                if (add.compareTo(TWO.pow(PKIFailureInfo.badRecipientNonce)) != 1) {
                    if (TWO.modPow(bigInteger3.multiply(bigInteger4).multiply(bigInteger5.add(BigInteger.valueOf((long) i))), add).compareTo(ONE) != 0 || TWO.modPow(bigInteger3.multiply(bigInteger5.add(BigInteger.valueOf((long) i))), add).compareTo(ONE) == 0) {
                        i += 2;
                    } else {
                        bigIntegerArr[0] = add;
                        bigIntegerArr[1] = bigInteger3;
                        return;
                    }
                }
            }
        }
    }

    private BigInteger procedure_C(BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger subtract = bigInteger.subtract(ONE);
        BigInteger divide = subtract.divide(bigInteger2);
        int bitLength = bigInteger.bitLength();
        while (true) {
            BigInteger bigInteger3 = new BigInteger(bitLength, this.init_random);
            if (bigInteger3.compareTo(ONE) > 0 && bigInteger3.compareTo(subtract) < 0) {
                bigInteger3 = bigInteger3.modPow(divide, bigInteger);
                if (bigInteger3.compareTo(ONE) != 0) {
                    return bigInteger3;
                }
            }
        }
    }

    public GOST3410Parameters generateParameters() {
        BigInteger[] bigIntegerArr = new BigInteger[2];
        if (this.typeproc == 1) {
            int nextInt = this.init_random.nextInt();
            int nextInt2 = this.init_random.nextInt();
            switch (this.size) {
                case 512:
                    procedure_A(nextInt, nextInt2, bigIntegerArr, 512);
                    break;
                case PKIFailureInfo.badRecipientNonce /*1024*/:
                    procedure_B(nextInt, nextInt2, bigIntegerArr);
                    break;
                default:
                    throw new IllegalArgumentException("Ooops! key size 512 or 1024 bit.");
            }
            BigInteger bigInteger = bigIntegerArr[0];
            BigInteger bigInteger2 = bigIntegerArr[1];
            return new GOST3410Parameters(bigInteger, bigInteger2, procedure_C(bigInteger, bigInteger2), new GOST3410ValidationParameters(nextInt, nextInt2));
        }
        long nextLong = this.init_random.nextLong();
        long nextLong2 = this.init_random.nextLong();
        switch (this.size) {
            case 512:
                procedure_Aa(nextLong, nextLong2, bigIntegerArr, 512);
                break;
            case PKIFailureInfo.badRecipientNonce /*1024*/:
                procedure_Bb(nextLong, nextLong2, bigIntegerArr);
                break;
            default:
                throw new IllegalStateException("Ooops! key size 512 or 1024 bit.");
        }
        BigInteger bigInteger3 = bigIntegerArr[0];
        BigInteger bigInteger4 = bigIntegerArr[1];
        return new GOST3410Parameters(bigInteger3, bigInteger4, procedure_C(bigInteger3, bigInteger4), new GOST3410ValidationParameters(nextLong, nextLong2));
    }

    public void init(int i, int i2, SecureRandom secureRandom) {
        this.size = i;
        this.typeproc = i2;
        this.init_random = secureRandom;
    }
}
