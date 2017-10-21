package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class ElGamalPrivateKeyParameters extends ElGamalKeyParameters {
    private BigInteger x;

    public ElGamalPrivateKeyParameters(BigInteger bigInteger, ElGamalParameters elGamalParameters) {
        super(true, elGamalParameters);
        this.x = bigInteger;
    }

    public boolean equals(Object obj) {
        return !(obj instanceof ElGamalPrivateKeyParameters) ? false : !((ElGamalPrivateKeyParameters) obj).getX().equals(this.x) ? false : super.equals(obj);
    }

    public BigInteger getX() {
        return this.x;
    }

    public int hashCode() {
        return getX().hashCode();
    }
}
