package org.spongycastle.crypto.params;

public class DHKeyParameters extends AsymmetricKeyParameter {
    private DHParameters params;

    protected DHKeyParameters(boolean z, DHParameters dHParameters) {
        super(z);
        this.params = dHParameters;
    }

    public boolean equals(Object obj) {
        if (obj instanceof DHKeyParameters) {
            DHKeyParameters dHKeyParameters = (DHKeyParameters) obj;
            if (this.params != null) {
                return this.params.equals(dHKeyParameters.getParameters());
            }
            if (dHKeyParameters.getParameters() == null) {
                return true;
            }
        }
        return false;
    }

    public DHParameters getParameters() {
        return this.params;
    }

    public int hashCode() {
        int i = isPrivate() ? 0 : 1;
        return this.params != null ? i ^ this.params.hashCode() : i;
    }
}
