package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;
import okhttp3.internal.http.StatusLine;
import org.kxml2.wap.Wbxml;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.spongycastle.crypto.params.NaccacheSternKeyParameters;
import org.spongycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.spongycastle.crypto.tls.CipherSuite;

public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static int[] smallPrimes = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, EACTags.QUALIFIED_NAME, EACTags.APPLICATION_IMAGE_TEMPLATE, 113, CertificateBody.profileType, Wbxml.STR_T, 137, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, 151, 157, 163, 167, 173, 179, 181, 191, Wbxml.EXT_1, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, StatusLine.HTTP_TEMP_REDIRECT, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557};
    private NaccacheSternKeyGenerationParameters param;

    private static Vector findFirstPrimes(int i) {
        Vector vector = new Vector(i);
        for (int i2 = 0; i2 != i; i2++) {
            vector.addElement(BigInteger.valueOf((long) smallPrimes[i2]));
        }
        return vector;
    }

    private static BigInteger generatePrime(int i, int i2, SecureRandom secureRandom) {
        BigInteger bigInteger = new BigInteger(i, i2, secureRandom);
        while (bigInteger.bitLength() != i) {
            bigInteger = new BigInteger(i, i2, secureRandom);
        }
        return bigInteger;
    }

    private static int getInt(SecureRandom secureRandom, int i) {
        if (((-i) & i) == i) {
            return (int) ((((long) i) * ((long) (secureRandom.nextInt() & Integer.MAX_VALUE))) >> 31);
        }
        int i2;
        int nextInt;
        do {
            nextInt = secureRandom.nextInt() & Integer.MAX_VALUE;
            i2 = nextInt % i;
        } while ((nextInt - i2) + (i - 1) < 0);
        return i2;
    }

    private static Vector permuteList(Vector vector, SecureRandom secureRandom) {
        Vector vector2 = new Vector();
        Vector vector3 = new Vector();
        for (int i = 0; i < vector.size(); i++) {
            vector3.addElement(vector.elementAt(i));
        }
        vector2.addElement(vector3.elementAt(0));
        vector3.removeElementAt(0);
        while (vector3.size() != 0) {
            vector2.insertElementAt(vector3.elementAt(0), getInt(secureRandom, vector2.size() + 1));
            vector3.removeElementAt(0);
        }
        return vector2;
    }

    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger add;
        BigInteger add2;
        int strength = this.param.getStrength();
        Random random = this.param.getRandom();
        int certainty = this.param.getCertainty();
        boolean isDebug = this.param.isDebug();
        if (isDebug) {
            System.out.println("Fetching first " + this.param.getCntSmallPrimes() + " primes.");
        }
        Vector permuteList = permuteList(findFirstPrimes(this.param.getCntSmallPrimes()), random);
        BigInteger bigInteger = ONE;
        BigInteger bigInteger2 = ONE;
        int i = 0;
        BigInteger bigInteger3 = bigInteger;
        while (i < permuteList.size() / 2) {
            bigInteger = bigInteger3.multiply((BigInteger) permuteList.elementAt(i));
            i++;
            bigInteger3 = bigInteger;
        }
        BigInteger bigInteger4 = bigInteger2;
        int size = permuteList.size() / 2;
        while (size < permuteList.size()) {
            bigInteger = bigInteger4.multiply((BigInteger) permuteList.elementAt(size));
            size++;
            bigInteger4 = bigInteger;
        }
        BigInteger multiply = bigInteger3.multiply(bigInteger4);
        int bitLength = (strength - multiply.bitLength()) - 48;
        BigInteger generatePrime = generatePrime((bitLength / 2) + 1, certainty, random);
        BigInteger generatePrime2 = generatePrime((bitLength / 2) + 1, certainty, random);
        if (isDebug) {
            System.out.println("generating p and q");
        }
        bigInteger2 = generatePrime.multiply(bigInteger3).shiftLeft(1);
        bigInteger4 = generatePrime2.multiply(bigInteger4).shiftLeft(1);
        long j = 0;
        while (true) {
            BigInteger generatePrime3;
            j++;
            BigInteger generatePrime4 = generatePrime(24, certainty, random);
            add = generatePrime4.multiply(bigInteger2).add(ONE);
            if (add.isProbablePrime(certainty)) {
                while (true) {
                    generatePrime3 = generatePrime(24, certainty, random);
                    if (!generatePrime4.equals(generatePrime3)) {
                        add2 = generatePrime3.multiply(bigInteger4).add(ONE);
                        if (add2.isProbablePrime(certainty)) {
                            break;
                        }
                    }
                }
                if (multiply.gcd(generatePrime4.multiply(generatePrime3)).equals(ONE)) {
                    if (add.multiply(add2).bitLength() >= strength) {
                        break;
                    } else if (isDebug) {
                        System.out.println("key size too small. Should be " + strength + " but is actually " + add.multiply(add2).bitLength());
                    }
                } else {
                    continue;
                }
            }
        }
        if (isDebug) {
            System.out.println("needed " + j + " tries to generate p and q.");
        }
        bigInteger4 = add.multiply(add2);
        BigInteger multiply2 = add.subtract(ONE).multiply(add2.subtract(ONE));
        j = 0;
        if (isDebug) {
            System.out.println("generating g");
        }
        while (true) {
            Object obj;
            Vector vector = new Vector();
            long j2 = j;
            for (int i2 = 0; i2 != permuteList.size(); i2++) {
                bigInteger = multiply2.divide((BigInteger) permuteList.elementAt(i2));
                do {
                    j2++;
                    bigInteger2 = new BigInteger(strength, certainty, random);
                } while (bigInteger2.modPow(bigInteger, bigInteger4).equals(ONE));
                vector.addElement(bigInteger2);
            }
            bigInteger3 = ONE;
            for (int i3 = 0; i3 < permuteList.size(); i3++) {
                bigInteger3 = bigInteger3.multiply(((BigInteger) vector.elementAt(i3)).modPow(multiply.divide((BigInteger) permuteList.elementAt(i3)), bigInteger4)).mod(bigInteger4);
            }
            size = 0;
            while (size < permuteList.size()) {
                if (bigInteger3.modPow(multiply2.divide((BigInteger) permuteList.elementAt(size)), bigInteger4).equals(ONE)) {
                    if (isDebug) {
                        System.out.println("g has order phi(n)/" + permuteList.elementAt(size) + "\n g: " + bigInteger3);
                    }
                    obj = 1;
                    if (obj == null) {
                        if (bigInteger3.modPow(multiply2.divide(BigInteger.valueOf(4)), bigInteger4).equals(ONE)) {
                            if (bigInteger3.modPow(multiply2.divide(generatePrime4), bigInteger4).equals(ONE)) {
                                if (bigInteger3.modPow(multiply2.divide(generatePrime3), bigInteger4).equals(ONE)) {
                                    if (bigInteger3.modPow(multiply2.divide(generatePrime), bigInteger4).equals(ONE)) {
                                        if (bigInteger3.modPow(multiply2.divide(generatePrime2), bigInteger4).equals(ONE)) {
                                            break;
                                        } else if (isDebug) {
                                            System.out.println("g has order phi(n)/b\n g: " + bigInteger3);
                                            j = j2;
                                        }
                                    } else if (isDebug) {
                                        System.out.println("g has order phi(n)/a\n g: " + bigInteger3);
                                        j = j2;
                                    }
                                } else if (isDebug) {
                                    System.out.println("g has order phi(n)/q'\n g: " + bigInteger3);
                                    j = j2;
                                }
                            } else if (isDebug) {
                                System.out.println("g has order phi(n)/p'\n g: " + bigInteger3);
                                j = j2;
                            }
                        } else if (isDebug) {
                            System.out.println("g has order phi(n)/4\n g:" + bigInteger3);
                            j = j2;
                        }
                    }
                    j = j2;
                } else {
                    size++;
                }
            }
            obj = null;
            if (obj == null) {
                if (bigInteger3.modPow(multiply2.divide(BigInteger.valueOf(4)), bigInteger4).equals(ONE)) {
                    if (bigInteger3.modPow(multiply2.divide(generatePrime4), bigInteger4).equals(ONE)) {
                        if (bigInteger3.modPow(multiply2.divide(generatePrime3), bigInteger4).equals(ONE)) {
                            if (bigInteger3.modPow(multiply2.divide(generatePrime), bigInteger4).equals(ONE)) {
                                if (bigInteger3.modPow(multiply2.divide(generatePrime2), bigInteger4).equals(ONE)) {
                                    break;
                                } else if (isDebug) {
                                    System.out.println("g has order phi(n)/b\n g: " + bigInteger3);
                                    j = j2;
                                }
                            } else if (isDebug) {
                                System.out.println("g has order phi(n)/a\n g: " + bigInteger3);
                                j = j2;
                            }
                        } else if (isDebug) {
                            System.out.println("g has order phi(n)/q'\n g: " + bigInteger3);
                            j = j2;
                        }
                    } else if (isDebug) {
                        System.out.println("g has order phi(n)/p'\n g: " + bigInteger3);
                        j = j2;
                    }
                } else if (isDebug) {
                    System.out.println("g has order phi(n)/4\n g:" + bigInteger3);
                    j = j2;
                }
            }
            j = j2;
        }
        if (isDebug) {
            System.out.println("needed " + j2 + " tries to generate g");
            System.out.println();
            System.out.println("found new NaccacheStern cipher variables:");
            System.out.println("smallPrimes: " + permuteList);
            System.out.println("sigma:...... " + multiply + " (" + multiply.bitLength() + " bits)");
            System.out.println("a:.......... " + generatePrime);
            System.out.println("b:.......... " + generatePrime2);
            System.out.println("p':......... " + generatePrime4);
            System.out.println("q':......... " + generatePrime3);
            System.out.println("p:.......... " + add);
            System.out.println("q:.......... " + add2);
            System.out.println("n:.......... " + bigInteger4);
            System.out.println("phi(n):..... " + multiply2);
            System.out.println("g:.......... " + bigInteger3);
            System.out.println();
        }
        return new AsymmetricCipherKeyPair(new NaccacheSternKeyParameters(false, bigInteger3, bigInteger4, multiply.bitLength()), new NaccacheSternPrivateKeyParameters(bigInteger3, bigInteger4, multiply.bitLength(), permuteList, multiply2));
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (NaccacheSternKeyGenerationParameters) keyGenerationParameters;
    }
}
