package org.spongycastle.crypto.params;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;

public class NTRUEncryptionPrivateKeyParameters extends NTRUEncryptionKeyParameters {
    public IntegerPolynomial fp;
    public IntegerPolynomial h;
    public Polynomial t;

    public NTRUEncryptionPrivateKeyParameters(InputStream inputStream, NTRUEncryptionParameters nTRUEncryptionParameters) throws IOException {
        Polynomial fromBinary;
        super(true, nTRUEncryptionParameters);
        if (nTRUEncryptionParameters.polyType == 1) {
            int i = nTRUEncryptionParameters.N;
            int i2 = nTRUEncryptionParameters.df1;
            int i3 = nTRUEncryptionParameters.df2;
            int i4 = nTRUEncryptionParameters.df3;
            int i5 = nTRUEncryptionParameters.fastFp ? nTRUEncryptionParameters.df3 : nTRUEncryptionParameters.df3 - 1;
            this.h = IntegerPolynomial.fromBinary(inputStream, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
            fromBinary = ProductFormPolynomial.fromBinary(inputStream, i, i2, i3, i4, i5);
        } else {
            this.h = IntegerPolynomial.fromBinary(inputStream, nTRUEncryptionParameters.N, nTRUEncryptionParameters.q);
            IntegerPolynomial fromBinary3Tight = IntegerPolynomial.fromBinary3Tight(inputStream, nTRUEncryptionParameters.N);
            fromBinary = nTRUEncryptionParameters.sparse ? new SparseTernaryPolynomial(fromBinary3Tight) : new DenseTernaryPolynomial(fromBinary3Tight);
        }
        this.t = fromBinary;
        init();
    }

    public NTRUEncryptionPrivateKeyParameters(IntegerPolynomial integerPolynomial, Polynomial polynomial, IntegerPolynomial integerPolynomial2, NTRUEncryptionParameters nTRUEncryptionParameters) {
        super(true, nTRUEncryptionParameters);
        this.h = integerPolynomial;
        this.t = polynomial;
        this.fp = integerPolynomial2;
    }

    public NTRUEncryptionPrivateKeyParameters(byte[] bArr, NTRUEncryptionParameters nTRUEncryptionParameters) throws IOException {
        this(new ByteArrayInputStream(bArr), nTRUEncryptionParameters);
    }

    private void init() {
        if (this.params.fastFp) {
            this.fp = new IntegerPolynomial(this.params.N);
            this.fp.coeffs[0] = 1;
            return;
        }
        this.fp = this.t.toIntegerPolynomial().invertF3();
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || !(obj instanceof NTRUEncryptionPrivateKeyParameters)) {
                return false;
            }
            NTRUEncryptionPrivateKeyParameters nTRUEncryptionPrivateKeyParameters = (NTRUEncryptionPrivateKeyParameters) obj;
            if (this.params == null) {
                if (nTRUEncryptionPrivateKeyParameters.params != null) {
                    return false;
                }
            } else if (!this.params.equals(nTRUEncryptionPrivateKeyParameters.params)) {
                return false;
            }
            if (this.t == null) {
                if (nTRUEncryptionPrivateKeyParameters.t != null) {
                    return false;
                }
            } else if (!this.t.equals(nTRUEncryptionPrivateKeyParameters.t)) {
                return false;
            }
            if (!this.h.equals(nTRUEncryptionPrivateKeyParameters.h)) {
                return false;
            }
        }
        return true;
    }

    public byte[] getEncoded() {
        Object toBinary = this.h.toBinary(this.params.q);
        Object toBinary2 = this.t instanceof ProductFormPolynomial ? ((ProductFormPolynomial) this.t).toBinary() : this.t.toIntegerPolynomial().toBinary3Tight();
        Object obj = new byte[(toBinary.length + toBinary2.length)];
        System.arraycopy(toBinary, 0, obj, 0, toBinary.length);
        System.arraycopy(toBinary2, 0, obj, toBinary.length, toBinary2.length);
        return obj;
    }

    public int hashCode() {
        int i = 0;
        int hashCode = this.params == null ? 0 : this.params.hashCode();
        int hashCode2 = this.t == null ? 0 : this.t.hashCode();
        if (this.h != null) {
            i = this.h.hashCode();
        }
        return ((((hashCode + 31) * 31) + hashCode2) * 31) + i;
    }

    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(getEncoded());
    }
}
