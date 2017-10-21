package org.spongycastle.crypto.params;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUSigningPrivateKeyParameters extends AsymmetricKeyParameter {
    private List<Basis> bases;
    private NTRUSigningPublicKeyParameters publicKey;

    public static class Basis {
        public Polynomial f;
        public Polynomial fPrime;
        public IntegerPolynomial h;
        NTRUSigningKeyGenerationParameters params;

        Basis(InputStream inputStream, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters, boolean z) throws IOException {
            Polynomial fromBinary;
            int i = nTRUSigningKeyGenerationParameters.N;
            int i2 = nTRUSigningKeyGenerationParameters.q;
            int i3 = nTRUSigningKeyGenerationParameters.d1;
            int i4 = nTRUSigningKeyGenerationParameters.d2;
            int i5 = nTRUSigningKeyGenerationParameters.d3;
            boolean z2 = nTRUSigningKeyGenerationParameters.sparse;
            this.params = nTRUSigningKeyGenerationParameters;
            if (nTRUSigningKeyGenerationParameters.polyType == 1) {
                fromBinary = ProductFormPolynomial.fromBinary(inputStream, i, i3, i4, i5 + 1, i5);
            } else {
                IntegerPolynomial fromBinary3Tight = IntegerPolynomial.fromBinary3Tight(inputStream, i);
                fromBinary = z2 ? new SparseTernaryPolynomial(fromBinary3Tight) : new DenseTernaryPolynomial(fromBinary3Tight);
            }
            this.f = fromBinary;
            if (nTRUSigningKeyGenerationParameters.basisType == 0) {
                Polynomial fromBinary2 = IntegerPolynomial.fromBinary(inputStream, i, i2);
                for (int i6 = 0; i6 < fromBinary2.coeffs.length; i6++) {
                    int[] iArr = fromBinary2.coeffs;
                    iArr[i6] = iArr[i6] - (i2 / 2);
                }
                this.fPrime = fromBinary2;
            } else if (nTRUSigningKeyGenerationParameters.polyType == 1) {
                this.fPrime = ProductFormPolynomial.fromBinary(inputStream, i, i3, i4, i5 + 1, i5);
            } else {
                this.fPrime = IntegerPolynomial.fromBinary3Tight(inputStream, i);
            }
            if (z) {
                this.h = IntegerPolynomial.fromBinary(inputStream, i, i2);
            }
        }

        protected Basis(Polynomial polynomial, Polynomial polynomial2, IntegerPolynomial integerPolynomial, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) {
            this.f = polynomial;
            this.fPrime = polynomial2;
            this.h = integerPolynomial;
            this.params = nTRUSigningKeyGenerationParameters;
        }

        private byte[] getEncoded(Polynomial polynomial) {
            return polynomial instanceof ProductFormPolynomial ? ((ProductFormPolynomial) polynomial).toBinary() : polynomial.toIntegerPolynomial().toBinary3Tight();
        }

        void encode(OutputStream outputStream, boolean z) throws IOException {
            int i = this.params.q;
            outputStream.write(getEncoded(this.f));
            if (this.params.basisType == 0) {
                IntegerPolynomial toIntegerPolynomial = this.fPrime.toIntegerPolynomial();
                for (int i2 = 0; i2 < toIntegerPolynomial.coeffs.length; i2++) {
                    int[] iArr = toIntegerPolynomial.coeffs;
                    iArr[i2] = iArr[i2] + (i / 2);
                }
                outputStream.write(toIntegerPolynomial.toBinary(i));
            } else {
                outputStream.write(getEncoded(this.fPrime));
            }
            if (z) {
                outputStream.write(this.h.toBinary(i));
            }
        }

        public boolean equals(Object obj) {
            if (this != obj) {
                if (obj == null || !(obj instanceof Basis)) {
                    return false;
                }
                Basis basis = (Basis) obj;
                if (this.f == null) {
                    if (basis.f != null) {
                        return false;
                    }
                } else if (!this.f.equals(basis.f)) {
                    return false;
                }
                if (this.fPrime == null) {
                    if (basis.fPrime != null) {
                        return false;
                    }
                } else if (!this.fPrime.equals(basis.fPrime)) {
                    return false;
                }
                if (this.h == null) {
                    if (basis.h != null) {
                        return false;
                    }
                } else if (!this.h.equals(basis.h)) {
                    return false;
                }
                if (this.params == null) {
                    if (basis.params != null) {
                        return false;
                    }
                } else if (!this.params.equals(basis.params)) {
                    return false;
                }
            }
            return true;
        }

        public int hashCode() {
            int i = 0;
            int hashCode = this.f == null ? 0 : this.f.hashCode();
            int hashCode2 = this.fPrime == null ? 0 : this.fPrime.hashCode();
            int hashCode3 = this.h == null ? 0 : this.h.hashCode();
            if (this.params != null) {
                i = this.params.hashCode();
            }
            return ((((((hashCode + 31) * 31) + hashCode2) * 31) + hashCode3) * 31) + i;
        }
    }

    public NTRUSigningPrivateKeyParameters(InputStream inputStream, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) throws IOException {
        super(true);
        this.bases = new ArrayList();
        int i = 0;
        while (i <= nTRUSigningKeyGenerationParameters.B) {
            add(new Basis(inputStream, nTRUSigningKeyGenerationParameters, i != 0));
            i++;
        }
        this.publicKey = new NTRUSigningPublicKeyParameters(inputStream, nTRUSigningKeyGenerationParameters.getSigningParameters());
    }

    public NTRUSigningPrivateKeyParameters(List<Basis> list, NTRUSigningPublicKeyParameters nTRUSigningPublicKeyParameters) {
        super(true);
        this.bases = new ArrayList(list);
        this.publicKey = nTRUSigningPublicKeyParameters;
    }

    public NTRUSigningPrivateKeyParameters(byte[] bArr, NTRUSigningKeyGenerationParameters nTRUSigningKeyGenerationParameters) throws IOException {
        this(new ByteArrayInputStream(bArr), nTRUSigningKeyGenerationParameters);
    }

    private void add(Basis basis) {
        this.bases.add(basis);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            NTRUSigningPrivateKeyParameters nTRUSigningPrivateKeyParameters = (NTRUSigningPrivateKeyParameters) obj;
            if ((this.bases != null || nTRUSigningPrivateKeyParameters.bases == null) && this.bases.size() == nTRUSigningPrivateKeyParameters.bases.size()) {
                int i = 0;
                while (i < this.bases.size()) {
                    Basis basis = (Basis) this.bases.get(i);
                    Basis basis2 = (Basis) nTRUSigningPrivateKeyParameters.bases.get(i);
                    if (basis.f.equals(basis2.f) && basis.fPrime.equals(basis2.fPrime) && ((i == 0 || basis.h.equals(basis2.h)) && basis.params.equals(basis2.params))) {
                        i++;
                    }
                }
                return true;
            }
        }
        return false;
    }

    public Basis getBasis(int i) {
        return (Basis) this.bases.get(i);
    }

    public byte[] getEncoded() throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i = 0;
        while (i < this.bases.size()) {
            ((Basis) this.bases.get(i)).encode(byteArrayOutputStream, i != 0);
            i++;
        }
        byteArrayOutputStream.write(this.publicKey.getEncoded());
        return byteArrayOutputStream.toByteArray();
    }

    public NTRUSigningPublicKeyParameters getPublicKey() {
        return this.publicKey;
    }

    public int hashCode() {
        int hashCode = this.bases == null ? 0 : this.bases.hashCode();
        int i = hashCode + 31;
        for (Basis hashCode2 : this.bases) {
            i = hashCode2.hashCode() + i;
        }
        return i;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(getEncoded());
    }
}
