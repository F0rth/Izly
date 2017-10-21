package org.spongycastle.x509;

import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.CertificatePair;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.jce.provider.X509CertificateObject;

public class X509CertificatePair {
    private X509Certificate forward;
    private X509Certificate reverse;

    public X509CertificatePair(X509Certificate x509Certificate, X509Certificate x509Certificate2) {
        this.forward = x509Certificate;
        this.reverse = x509Certificate2;
    }

    public X509CertificatePair(CertificatePair certificatePair) throws CertificateParsingException {
        if (certificatePair.getForward() != null) {
            this.forward = new X509CertificateObject(certificatePair.getForward());
        }
        if (certificatePair.getReverse() != null) {
            this.reverse = new X509CertificateObject(certificatePair.getReverse());
        }
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof X509CertificatePair)) {
            return false;
        }
        X509CertificatePair x509CertificatePair = (X509CertificatePair) obj;
        boolean equals = this.forward != null ? this.forward.equals(x509CertificatePair.forward) : x509CertificatePair.forward == null;
        boolean equals2 = this.reverse != null ? this.reverse.equals(x509CertificatePair.reverse) : x509CertificatePair.reverse == null;
        return equals && equals2;
    }

    public byte[] getEncoded() throws CertificateEncodingException {
        X509CertificateStructure x509CertificateStructure = null;
        try {
            X509CertificateStructure instance;
            if (this.forward != null) {
                instance = X509CertificateStructure.getInstance(new ASN1InputStream(this.forward.getEncoded()).readObject());
                if (instance == null) {
                    throw new CertificateEncodingException("unable to get encoding for forward");
                }
            }
            instance = null;
            if (this.reverse != null) {
                x509CertificateStructure = X509CertificateStructure.getInstance(new ASN1InputStream(this.reverse.getEncoded()).readObject());
                if (x509CertificateStructure == null) {
                    throw new CertificateEncodingException("unable to get encoding for reverse");
                }
            }
            return new CertificatePair(instance, x509CertificateStructure).getEncoded(ASN1Encoding.DER);
        } catch (Throwable e) {
            throw new ExtCertificateEncodingException(e.toString(), e);
        } catch (Throwable e2) {
            throw new ExtCertificateEncodingException(e2.toString(), e2);
        }
    }

    public X509Certificate getForward() {
        return this.forward;
    }

    public X509Certificate getReverse() {
        return this.reverse;
    }

    public int hashCode() {
        int i = -1;
        if (this.forward != null) {
            i = this.forward.hashCode() ^ -1;
        }
        return this.reverse != null ? (i * 17) ^ this.reverse.hashCode() : i;
    }
}
