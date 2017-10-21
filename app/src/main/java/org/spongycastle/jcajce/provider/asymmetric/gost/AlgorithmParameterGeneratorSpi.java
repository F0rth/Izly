package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.crypto.generators.GOST3410ParametersGenerator;
import org.spongycastle.crypto.params.GOST3410Parameters;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public abstract class AlgorithmParameterGeneratorSpi extends java.security.AlgorithmParameterGeneratorSpi {
    protected SecureRandom random;
    protected int strength = PKIFailureInfo.badRecipientNonce;

    protected AlgorithmParameters engineGenerateParameters() {
        GOST3410ParametersGenerator gOST3410ParametersGenerator = new GOST3410ParametersGenerator();
        if (this.random != null) {
            gOST3410ParametersGenerator.init(this.strength, 2, this.random);
        } else {
            gOST3410ParametersGenerator.init(this.strength, 2, new SecureRandom());
        }
        GOST3410Parameters generateParameters = gOST3410ParametersGenerator.generateParameters();
        try {
            AlgorithmParameters instance = AlgorithmParameters.getInstance("GOST3410", BouncyCastleProvider.PROVIDER_NAME);
            instance.init(new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(generateParameters.getP(), generateParameters.getQ(), generateParameters.getA())));
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected void engineInit(int i, SecureRandom secureRandom) {
        this.strength = i;
        this.random = secureRandom;
    }

    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        throw new InvalidAlgorithmParameterException("No supported AlgorithmParameterSpec for GOST3410 parameter generation.");
    }
}
