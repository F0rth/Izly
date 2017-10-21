package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.x509.X509CertificateStructure;

public class Certificate {
    public static final Certificate EMPTY_CHAIN = new Certificate(new X509CertificateStructure[0]);
    protected X509CertificateStructure[] certs;

    public Certificate(X509CertificateStructure[] x509CertificateStructureArr) {
        if (x509CertificateStructureArr == null) {
            throw new IllegalArgumentException("'certs' cannot be null");
        }
        this.certs = x509CertificateStructureArr;
    }

    protected static Certificate parse(InputStream inputStream) throws IOException {
        int readUint24 = TlsUtils.readUint24(inputStream);
        if (readUint24 == 0) {
            return EMPTY_CHAIN;
        }
        Vector vector = new Vector();
        while (readUint24 > 0) {
            int readUint242 = TlsUtils.readUint24(inputStream);
            readUint24 -= readUint242 + 3;
            byte[] bArr = new byte[readUint242];
            TlsUtils.readFully(bArr, inputStream);
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            vector.addElement(X509CertificateStructure.getInstance(new ASN1InputStream(byteArrayInputStream).readObject()));
            if (byteArrayInputStream.available() > 0) {
                throw new IllegalArgumentException("Sorry, there is garbage data left after the certificate");
            }
        }
        X509CertificateStructure[] x509CertificateStructureArr = new X509CertificateStructure[vector.size()];
        for (readUint242 = 0; readUint242 < vector.size(); readUint242++) {
            x509CertificateStructureArr[readUint242] = (X509CertificateStructure) vector.elementAt(readUint242);
        }
        return new Certificate(x509CertificateStructureArr);
    }

    protected void encode(OutputStream outputStream) throws IOException {
        int i = 0;
        Vector vector = new Vector();
        int i2 = 0;
        for (X509CertificateStructure encoded : this.certs) {
            Object encoded2 = encoded.getEncoded(ASN1Encoding.DER);
            vector.addElement(encoded2);
            i2 += encoded2.length + 3;
        }
        TlsUtils.writeUint24(i2, outputStream);
        while (i < vector.size()) {
            TlsUtils.writeOpaque24((byte[]) vector.elementAt(i), outputStream);
            i++;
        }
    }

    public X509CertificateStructure[] getCerts() {
        Object obj = new X509CertificateStructure[this.certs.length];
        System.arraycopy(this.certs, 0, obj, 0, this.certs.length);
        return obj;
    }

    public boolean isEmpty() {
        return this.certs.length == 0;
    }
}
