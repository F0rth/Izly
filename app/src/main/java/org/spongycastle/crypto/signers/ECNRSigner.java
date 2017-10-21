package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.generators.ECKeyPairGenerator;
import org.spongycastle.crypto.params.ECKeyGenerationParameters;
import org.spongycastle.crypto.params.ECKeyParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ec.ECAlgorithms;
import org.spongycastle.math.ec.ECConstants;

public class ECNRSigner implements DSA {
    private boolean forSigning;
    private ECKeyParameters key;
    private SecureRandom random;

    public BigInteger[] generateSignature(byte[] bArr) {
        if (this.forSigning) {
            BigInteger n = ((ECPrivateKeyParameters) this.key).getParameters().getN();
            int bitLength = n.bitLength();
            BigInteger bigInteger = new BigInteger(1, bArr);
            ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) this.key;
            if (bigInteger.bitLength() > bitLength) {
                throw new DataLengthException("input too large for ECNR key.");
            }
            ECKeyPairGenerator eCKeyPairGenerator;
            do {
                eCKeyPairGenerator = new ECKeyPairGenerator();
                eCKeyPairGenerator.init(new ECKeyGenerationParameters(eCPrivateKeyParameters.getParameters(), this.random));
            } while (((ECPublicKeyParameters) eCKeyPairGenerator.generateKeyPair().getPublic()).getQ().getX().toBigInteger().add(bigInteger).mod(n).equals(ECConstants.ZERO));
            bigInteger = eCPrivateKeyParameters.getD();
            return new BigInteger[]{r1, ((ECPrivateKeyParameters) r4.getPrivate()).getD().subtract(r1.multiply(bigInteger)).mod(n)};
        }
        throw new IllegalStateException("not initialised for signing");
    }

    public void init(boolean z, CipherParameters cipherParameters) {
        this.forSigning = z;
        if (!z) {
            this.key = (ECPublicKeyParameters) cipherParameters;
        } else if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.random = parametersWithRandom.getRandom();
            this.key = (ECPrivateKeyParameters) parametersWithRandom.getParameters();
        } else {
            this.random = new SecureRandom();
            this.key = (ECPrivateKeyParameters) cipherParameters;
        }
    }

    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        if (this.forSigning) {
            throw new IllegalStateException("not initialised for verifying");
        }
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) this.key;
        BigInteger n = eCPublicKeyParameters.getParameters().getN();
        int bitLength = n.bitLength();
        BigInteger bigInteger3 = new BigInteger(1, bArr);
        if (bigInteger3.bitLength() <= bitLength) {
            return (bigInteger.compareTo(ECConstants.ONE) < 0 || bigInteger.compareTo(n) >= 0) ? false : (bigInteger2.compareTo(ECConstants.ZERO) < 0 || bigInteger2.compareTo(n) >= 0) ? false : bigInteger.subtract(ECAlgorithms.sumOfTwoMultiplies(eCPublicKeyParameters.getParameters().getG(), bigInteger2, eCPublicKeyParameters.getQ(), bigInteger).getX().toBigInteger()).mod(n).equals(bigInteger3);
        } else {
            throw new DataLengthException("input too large for ECNR key.");
        }
    }
}
