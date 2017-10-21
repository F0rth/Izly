package org.spongycastle.crypto.params;

public class ElGamalKeyParameters extends AsymmetricKeyParameter {
    private ElGamalParameters params;

    protected ElGamalKeyParameters(boolean z, ElGamalParameters elGamalParameters) {
        super(z);
        this.params = elGamalParameters;
    }

    public boolean equals(Object obj) {
        if (obj instanceof ElGamalKeyParameters) {
            ElGamalKeyParameters elGamalKeyParameters = (ElGamalKeyParameters) obj;
            if (this.params != null) {
                return this.params.equals(elGamalKeyParameters.getParameters());
            }
            if (elGamalKeyParameters.getParameters() == null) {
                return true;
            }
        }
        return false;
    }

    public ElGamalParameters getParameters() {
        return this.params;
    }

    public int hashCode() {
        return this.params != null ? this.params.hashCode() : 0;
    }
}
