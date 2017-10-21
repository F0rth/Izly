package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.util.Random;
import org.spongycastle.crypto.AsymmetricCipherKeyPair;
import org.spongycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410KeyGenerationParameters;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;

public class GOST3410KeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private GOST3410KeyGenerationParameters param;

    public AsymmetricCipherKeyPair generateKeyPair() {
        GOST3410Parameters parameters = this.param.getParameters();
        Random random = this.param.getRandom();
        BigInteger q = parameters.getQ();
        BigInteger p = parameters.getP();
        BigInteger a = parameters.getA();
        while (true) {
            BigInteger bigInteger = new BigInteger(256, random);
            if (!bigInteger.equals(ZERO) && bigInteger.compareTo(q) < 0) {
                return new AsymmetricCipherKeyPair(new GOST3410PublicKeyParameters(a.modPow(bigInteger, p), parameters), new GOST3410PrivateKeyParameters(bigInteger, parameters));
            }
        }
    }

    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (GOST3410KeyGenerationParameters) keyGenerationParameters;
    }
}
