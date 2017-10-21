package org.spongycastle.asn1.eac;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERApplicationSpecific;
import org.spongycastle.asn1.DEROctetString;

public class CertificateBody extends ASN1Object {
    private static final int CAR = 2;
    private static final int CEfD = 32;
    private static final int CExD = 64;
    private static final int CHA = 16;
    private static final int CHR = 8;
    private static final int CPI = 1;
    private static final int PK = 4;
    public static final int profileType = 127;
    public static final int requestType = 13;
    private DERApplicationSpecific certificateEffectiveDate;
    private DERApplicationSpecific certificateExpirationDate;
    private CertificateHolderAuthorization certificateHolderAuthorization;
    private DERApplicationSpecific certificateHolderReference;
    private DERApplicationSpecific certificateProfileIdentifier;
    private int certificateType = 0;
    private DERApplicationSpecific certificationAuthorityReference;
    private PublicKeyDataObject publicKey;
    ASN1InputStream seq;

    private CertificateBody(DERApplicationSpecific dERApplicationSpecific) throws IOException {
        setIso7816CertificateBody(dERApplicationSpecific);
    }

    public CertificateBody(DERApplicationSpecific dERApplicationSpecific, CertificationAuthorityReference certificationAuthorityReference, PublicKeyDataObject publicKeyDataObject, CertificateHolderReference certificateHolderReference, CertificateHolderAuthorization certificateHolderAuthorization, PackedDate packedDate, PackedDate packedDate2) {
        setCertificateProfileIdentifier(dERApplicationSpecific);
        setCertificationAuthorityReference(new DERApplicationSpecific(2, certificationAuthorityReference.getEncoded()));
        setPublicKey(publicKeyDataObject);
        setCertificateHolderReference(new DERApplicationSpecific(32, certificateHolderReference.getEncoded()));
        setCertificateHolderAuthorization(certificateHolderAuthorization);
        try {
            setCertificateEffectiveDate(new DERApplicationSpecific(false, 37, new DEROctetString(packedDate.getEncoding())));
            setCertificateExpirationDate(new DERApplicationSpecific(false, 36, new DEROctetString(packedDate2.getEncoding())));
        } catch (IOException e) {
            throw new IllegalArgumentException("unable to encode dates: " + e.getMessage());
        }
    }

    public static CertificateBody getInstance(Object obj) throws IOException {
        return obj instanceof CertificateBody ? (CertificateBody) obj : obj != null ? new CertificateBody(DERApplicationSpecific.getInstance(obj)) : null;
    }

    private ASN1Primitive profileToASN1Object() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certificateProfileIdentifier);
        aSN1EncodableVector.add(this.certificationAuthorityReference);
        aSN1EncodableVector.add(new DERApplicationSpecific(false, 73, this.publicKey));
        aSN1EncodableVector.add(this.certificateHolderReference);
        aSN1EncodableVector.add(this.certificateHolderAuthorization);
        aSN1EncodableVector.add(this.certificateEffectiveDate);
        aSN1EncodableVector.add(this.certificateExpirationDate);
        return new DERApplicationSpecific(78, aSN1EncodableVector);
    }

    private ASN1Primitive requestToASN1Object() throws IOException {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        aSN1EncodableVector.add(this.certificateProfileIdentifier);
        aSN1EncodableVector.add(new DERApplicationSpecific(false, 73, this.publicKey));
        aSN1EncodableVector.add(this.certificateHolderReference);
        return new DERApplicationSpecific(78, aSN1EncodableVector);
    }

    private void setCertificateEffectiveDate(DERApplicationSpecific dERApplicationSpecific) throws IllegalArgumentException {
        if (dERApplicationSpecific.getApplicationTag() == 37) {
            this.certificateEffectiveDate = dERApplicationSpecific;
            this.certificateType |= 32;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EFFECTIVE_DATE tag :" + EACTags.encodeTag(dERApplicationSpecific));
    }

    private void setCertificateExpirationDate(DERApplicationSpecific dERApplicationSpecific) throws IllegalArgumentException {
        if (dERApplicationSpecific.getApplicationTag() == 36) {
            this.certificateExpirationDate = dERApplicationSpecific;
            this.certificateType |= 64;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.APPLICATION_EXPIRATION_DATE tag");
    }

    private void setCertificateHolderAuthorization(CertificateHolderAuthorization certificateHolderAuthorization) {
        this.certificateHolderAuthorization = certificateHolderAuthorization;
        this.certificateType |= 16;
    }

    private void setCertificateHolderReference(DERApplicationSpecific dERApplicationSpecific) throws IllegalArgumentException {
        if (dERApplicationSpecific.getApplicationTag() == 32) {
            this.certificateHolderReference = dERApplicationSpecific;
            this.certificateType |= 8;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.CARDHOLDER_NAME tag");
    }

    private void setCertificateProfileIdentifier(DERApplicationSpecific dERApplicationSpecific) throws IllegalArgumentException {
        if (dERApplicationSpecific.getApplicationTag() == 41) {
            this.certificateProfileIdentifier = dERApplicationSpecific;
            this.certificateType |= 1;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.INTERCHANGE_PROFILE tag :" + EACTags.encodeTag(dERApplicationSpecific));
    }

    private void setCertificationAuthorityReference(DERApplicationSpecific dERApplicationSpecific) throws IllegalArgumentException {
        if (dERApplicationSpecific.getApplicationTag() == 2) {
            this.certificationAuthorityReference = dERApplicationSpecific;
            this.certificateType |= 2;
            return;
        }
        throw new IllegalArgumentException("Not an Iso7816Tags.ISSUER_IDENTIFICATION_NUMBER tag");
    }

    private void setIso7816CertificateBody(DERApplicationSpecific dERApplicationSpecific) throws IOException {
        if (dERApplicationSpecific.getApplicationTag() == 78) {
            ASN1InputStream aSN1InputStream = new ASN1InputStream(dERApplicationSpecific.getContents());
            while (true) {
                ASN1Primitive readObject = aSN1InputStream.readObject();
                if (readObject == null) {
                    return;
                }
                if (readObject instanceof DERApplicationSpecific) {
                    DERApplicationSpecific dERApplicationSpecific2 = (DERApplicationSpecific) readObject;
                    switch (dERApplicationSpecific2.getApplicationTag()) {
                        case 2:
                            setCertificationAuthorityReference(dERApplicationSpecific2);
                            break;
                        case 32:
                            setCertificateHolderReference(dERApplicationSpecific2);
                            break;
                        case 36:
                            setCertificateExpirationDate(dERApplicationSpecific2);
                            break;
                        case 37:
                            setCertificateEffectiveDate(dERApplicationSpecific2);
                            break;
                        case 41:
                            setCertificateProfileIdentifier(dERApplicationSpecific2);
                            break;
                        case 73:
                            setPublicKey(PublicKeyDataObject.getInstance(dERApplicationSpecific2.getObject(16)));
                            break;
                        case 76:
                            setCertificateHolderAuthorization(new CertificateHolderAuthorization(dERApplicationSpecific2));
                            break;
                        default:
                            this.certificateType = 0;
                            throw new IOException("Not a valid iso7816 DERApplicationSpecific tag " + dERApplicationSpecific2.getApplicationTag());
                    }
                }
                throw new IOException("Not a valid iso7816 content : not a DERApplicationSpecific Object :" + EACTags.encodeTag(dERApplicationSpecific) + readObject.getClass());
            }
        }
        throw new IOException("Bad tag : not an iso7816 CERTIFICATE_CONTENT_TEMPLATE");
    }

    private void setPublicKey(PublicKeyDataObject publicKeyDataObject) {
        this.publicKey = PublicKeyDataObject.getInstance(publicKeyDataObject);
        this.certificateType |= 4;
    }

    public PackedDate getCertificateEffectiveDate() {
        return (this.certificateType & 32) == 32 ? new PackedDate(this.certificateEffectiveDate.getContents()) : null;
    }

    public PackedDate getCertificateExpirationDate() throws IOException {
        if ((this.certificateType & 64) == 64) {
            return new PackedDate(this.certificateExpirationDate.getContents());
        }
        throw new IOException("certificate Expiration Date not set");
    }

    public CertificateHolderAuthorization getCertificateHolderAuthorization() throws IOException {
        if ((this.certificateType & 16) == 16) {
            return this.certificateHolderAuthorization;
        }
        throw new IOException("Certificate Holder Authorisation not set");
    }

    public CertificateHolderReference getCertificateHolderReference() {
        return new CertificateHolderReference(this.certificateHolderReference.getContents());
    }

    public DERApplicationSpecific getCertificateProfileIdentifier() {
        return this.certificateProfileIdentifier;
    }

    public int getCertificateType() {
        return this.certificateType;
    }

    public CertificationAuthorityReference getCertificationAuthorityReference() throws IOException {
        if ((this.certificateType & 2) == 2) {
            return new CertificationAuthorityReference(this.certificationAuthorityReference.getContents());
        }
        throw new IOException("Certification authority reference not set");
    }

    public PublicKeyDataObject getPublicKey() {
        return this.publicKey;
    }

    public ASN1Primitive toASN1Primitive() {
        try {
            if (this.certificateType == profileType) {
                return profileToASN1Object();
            }
            if (this.certificateType == 13) {
                return requestToASN1Object();
            }
            System.err.println("returning null");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
