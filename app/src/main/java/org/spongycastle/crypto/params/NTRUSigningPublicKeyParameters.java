package org.spongycastle.crypto.params;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;

public class NTRUSigningPublicKeyParameters extends AsymmetricKeyParameter {
    public IntegerPolynomial h;
    private NTRUSigningParameters params;

    public NTRUSigningPublicKeyParameters(InputStream inputStream, NTRUSigningParameters nTRUSigningParameters) throws IOException {
        super(false);
        this.h = IntegerPolynomial.fromBinary(inputStream, nTRUSigningParameters.N, nTRUSigningParameters.q);
        this.params = nTRUSigningParameters;
    }

    public NTRUSigningPublicKeyParameters(IntegerPolynomial integerPolynomial, NTRUSigningParameters nTRUSigningParameters) {
        super(false);
        this.h = integerPolynomial;
        this.params = nTRUSigningParameters;
    }

    public NTRUSigningPublicKeyParameters(byte[] bArr, NTRUSigningParameters nTRUSigningParameters) {
        super(false);
        this.h = IntegerPolynomial.fromBinary(bArr, nTRUSigningParameters.N, nTRUSigningParameters.q);
        this.params = nTRUSigningParameters;
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters = (NTRUSigningPublicKeyParameters) obj;
            if (this.h == null) {
                if (nTRUSigningPublicKeyParameters.h != null) {
                    return false;
                }
            } else if (!this.h.equals(nTRUSigningPublicKeyParameters.h)) {
                return false;
            }
            if (this.params == null) {
                if (nTRUSigningPublicKeyParameters.params != null) {
                    return false;
                }
            } else if (!this.params.equals(nTRUSigningPublicKeyParameters.params)) {
                return false;
            }
        }
        return true;
    }

    public byte[] getEncoded() {
        return this.h.toBinary(this.params.q);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.h == null ? 0 : this.h.hashCode();
        if (this.params != null) {
            i = this.params.hashCode();
        }
        return ((hashCode + 31) * 31) + i;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(getEncoded());
    }
}
