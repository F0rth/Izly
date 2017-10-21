package org.spongycastle.jce.provider;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyStore.LoadStoreParameter;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.ProtectionParameter;
import java.security.KeyStoreException;
import java.security.KeyStoreSpi;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.KeySpec;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERConstructedOctetString;
import org.spongycastle.asn1.BEROutputStream;
import org.spongycastle.asn1.DERBMPString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.pkcs.AuthenticatedSafe;
import org.spongycastle.asn1.pkcs.CertBag;
import org.spongycastle.asn1.pkcs.ContentInfo;
import org.spongycastle.asn1.pkcs.EncryptedData;
import org.spongycastle.asn1.pkcs.EncryptedPrivateKeyInfo;
import org.spongycastle.asn1.pkcs.MacData;
import org.spongycastle.asn1.pkcs.PKCS12PBEParams;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.Pfx;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.SafeBag;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.jcajce.provider.symmetric.util.BCPBEKey;
import org.spongycastle.jce.interfaces.BCKeyStore;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class JDKPKCS12KeyStore extends KeyStoreSpi implements PKCSObjectIdentifiers, X509ObjectIdentifiers, BCKeyStore {
    static final int CERTIFICATE = 1;
    static final int KEY = 2;
    static final int KEY_PRIVATE = 0;
    static final int KEY_PUBLIC = 1;
    static final int KEY_SECRET = 2;
    private static final int MIN_ITERATIONS = 1024;
    static final int NULL = 0;
    private static final int SALT_SIZE = 20;
    static final int SEALED = 4;
    static final int SECRET = 3;
    private static final Provider bcProvider = new BouncyCastleProvider();
    private ASN1ObjectIdentifier certAlgorithm;
    private CertificateFactory certFact;
    private IgnoresCaseHashtable certs = new IgnoresCaseHashtable();
    private Hashtable chainCerts = new Hashtable();
    private ASN1ObjectIdentifier keyAlgorithm;
    private Hashtable keyCerts = new Hashtable();
    private IgnoresCaseHashtable keys = new IgnoresCaseHashtable();
    private Hashtable localIds = new Hashtable();
    protected SecureRandom random = new SecureRandom();

    public static class BCPKCS12KeyStore3DES extends JDKPKCS12KeyStore {
        public BCPKCS12KeyStore3DES() {
            super(JDKPKCS12KeyStore.bcProvider, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    public static class BCPKCS12KeyStore extends JDKPKCS12KeyStore {
        public BCPKCS12KeyStore() {
            super(JDKPKCS12KeyStore.bcProvider, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    class CertId {
        byte[] id;

        CertId(PublicKey publicKey) {
            this.id = JDKPKCS12KeyStore.this.createSubjectKeyId(publicKey).getKeyIdentifier();
        }

        CertId(byte[] bArr) {
            this.id = bArr;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof CertId)) {
                return false;
            }
            return Arrays.areEqual(this.id, ((CertId) obj).id);
        }

        public int hashCode() {
            return Arrays.hashCode(this.id);
        }
    }

    public static class DefPKCS12KeyStore3DES extends JDKPKCS12KeyStore {
        public DefPKCS12KeyStore3DES() {
            super(null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd3_KeyTripleDES_CBC);
        }
    }

    public static class DefPKCS12KeyStore extends JDKPKCS12KeyStore {
        public DefPKCS12KeyStore() {
            super(null, pbeWithSHAAnd3_KeyTripleDES_CBC, pbeWithSHAAnd40BitRC2_CBC);
        }
    }

    static class IgnoresCaseHashtable {
        private Hashtable keys;
        private Hashtable orig;

        private IgnoresCaseHashtable() {
            this.orig = new Hashtable();
            this.keys = new Hashtable();
        }

        public Enumeration elements() {
            return this.orig.elements();
        }

        public Object get(String str) {
            String str2 = (String) this.keys.get(Strings.toLowerCase(str));
            return str2 == null ? null : this.orig.get(str2);
        }

        public Enumeration keys() {
            return this.orig.keys();
        }

        public void put(String str, Object obj) {
            String toLowerCase = Strings.toLowerCase(str);
            String str2 = (String) this.keys.get(toLowerCase);
            if (str2 != null) {
                this.orig.remove(str2);
            }
            this.keys.put(toLowerCase, str);
            this.orig.put(str, obj);
        }

        public Object remove(String str) {
            String str2 = (String) this.keys.remove(Strings.toLowerCase(str));
            return str2 == null ? null : this.orig.remove(str2);
        }
    }

    public JDKPKCS12KeyStore(Provider provider, ASN1ObjectIdentifier aSN1ObjectIdentifier, ASN1ObjectIdentifier aSN1ObjectIdentifier2) {
        this.keyAlgorithm = aSN1ObjectIdentifier;
        this.certAlgorithm = aSN1ObjectIdentifier2;
        if (provider != null) {
            try {
                this.certFact = CertificateFactory.getInstance("X.509", provider);
                return;
            } catch (Exception e) {
                throw new IllegalArgumentException("can't create cert factory - " + e.toString());
            }
        }
        this.certFact = CertificateFactory.getInstance("X.509");
    }

    private static byte[] calculatePbeMac(ASN1ObjectIdentifier aSN1ObjectIdentifier, byte[] bArr, int i, char[] cArr, boolean z, byte[] bArr2) throws Exception {
        SecretKeyFactory instance = SecretKeyFactory.getInstance(aSN1ObjectIdentifier.getId(), bcProvider);
        AlgorithmParameterSpec pBEParameterSpec = new PBEParameterSpec(bArr, i);
        BCPBEKey bCPBEKey = (BCPBEKey) instance.generateSecret(new PBEKeySpec(cArr));
        bCPBEKey.setTryWrongPKCS12Zero(z);
        Mac instance2 = Mac.getInstance(aSN1ObjectIdentifier.getId(), bcProvider);
        instance2.init(bCPBEKey, pBEParameterSpec);
        instance2.update(bArr2);
        return instance2.doFinal();
    }

    private SubjectKeyIdentifier createSubjectKeyId(PublicKey publicKey) {
        try {
            return new SubjectKeyIdentifier(new SubjectPublicKeyInfo((ASN1Sequence) ASN1Primitive.fromByteArray(publicKey.getEncoded())));
        } catch (Exception e) {
            throw new RuntimeException("error creating key");
        }
    }

    private void doStore(OutputStream outputStream, char[] cArr, boolean z) throws IOException {
        if (cArr == null) {
            throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
        }
        ASN1EncodableVector aSN1EncodableVector;
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        Enumeration keys = this.keys.keys();
        while (keys.hasMoreElements()) {
            PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier;
            Object obj;
            byte[] bArr = new byte[20];
            this.random.nextBytes(bArr);
            String str = (String) keys.nextElement();
            PrivateKey privateKey = (PrivateKey) this.keys.get(str);
            PKCS12PBEParams pKCS12PBEParams = new PKCS12PBEParams(bArr, 1024);
            EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(new AlgorithmIdentifier(this.keyAlgorithm, pKCS12PBEParams.toASN1Primitive()), wrapKey(this.keyAlgorithm.getId(), privateKey, pKCS12PBEParams, cArr));
            ASN1EncodableVector aSN1EncodableVector3 = new ASN1EncodableVector();
            if (privateKey instanceof PKCS12BagAttributeCarrier) {
                pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) privateKey;
                DERBMPString dERBMPString = (DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
                if (dERBMPString == null || !dERBMPString.getString().equals(str)) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str));
                }
                if (pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                    pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate(str).getPublicKey()));
                }
                Enumeration bagAttributeKeys = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                obj = null;
                while (bagAttributeKeys.hasMoreElements()) {
                    ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) bagAttributeKeys.nextElement();
                    ASN1EncodableVector aSN1EncodableVector4 = new ASN1EncodableVector();
                    aSN1EncodableVector4.add(aSN1ObjectIdentifier);
                    aSN1EncodableVector4.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier)));
                    obj = 1;
                    aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector4));
                }
            } else {
                obj = null;
            }
            if (obj == null) {
                ASN1EncodableVector aSN1EncodableVector5 = new ASN1EncodableVector();
                Certificate engineGetCertificate = engineGetCertificate(str);
                aSN1EncodableVector5.add(pkcs_9_at_localKeyId);
                aSN1EncodableVector5.add(new DERSet(createSubjectKeyId(engineGetCertificate.getPublicKey())));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector5));
                aSN1EncodableVector5 = new ASN1EncodableVector();
                aSN1EncodableVector5.add(pkcs_9_at_friendlyName);
                aSN1EncodableVector5.add(new DERSet(new DERBMPString(str)));
                aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector5));
            }
            aSN1EncodableVector2.add(new SafeBag(pkcs8ShroudedKeyBag, encryptedPrivateKeyInfo.toASN1Primitive(), new DERSet(aSN1EncodableVector3)));
        }
        ASN1Encodable bERConstructedOctetString = new BERConstructedOctetString(new DERSequence(aSN1EncodableVector2).getEncoded(ASN1Encoding.DER));
        byte[] bArr2 = new byte[20];
        this.random.nextBytes(bArr2);
        ASN1EncodableVector aSN1EncodableVector6 = new ASN1EncodableVector();
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(this.certAlgorithm, new PKCS12PBEParams(bArr2, 1024).toASN1Primitive());
        Hashtable hashtable = new Hashtable();
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            DERBMPString dERBMPString2;
            try {
                Object obj2;
                str = (String) keys2.nextElement();
                Certificate engineGetCertificate2 = engineGetCertificate(str);
                CertBag certBag = new CertBag(x509Certificate, new DEROctetString(engineGetCertificate2.getEncoded()));
                aSN1EncodableVector = new ASN1EncodableVector();
                if (engineGetCertificate2 instanceof PKCS12BagAttributeCarrier) {
                    pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) engineGetCertificate2;
                    dERBMPString2 = (DERBMPString) pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_friendlyName);
                    if (dERBMPString2 == null || !dERBMPString2.getString().equals(str)) {
                        pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str));
                    }
                    if (pKCS12BagAttributeCarrier.getBagAttribute(pkcs_9_at_localKeyId) == null) {
                        pKCS12BagAttributeCarrier.setBagAttribute(pkcs_9_at_localKeyId, createSubjectKeyId(engineGetCertificate2.getPublicKey()));
                    }
                    Enumeration bagAttributeKeys2 = pKCS12BagAttributeCarrier.getBagAttributeKeys();
                    Object obj3 = null;
                    while (bagAttributeKeys2.hasMoreElements()) {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) bagAttributeKeys2.nextElement();
                        ASN1EncodableVector aSN1EncodableVector7 = new ASN1EncodableVector();
                        aSN1EncodableVector7.add(aSN1ObjectIdentifier2);
                        aSN1EncodableVector7.add(new DERSet(pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier2)));
                        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector7));
                        obj3 = 1;
                    }
                    obj2 = obj3;
                } else {
                    obj2 = null;
                }
                if (obj2 == null) {
                    aSN1EncodableVector5 = new ASN1EncodableVector();
                    aSN1EncodableVector5.add(pkcs_9_at_localKeyId);
                    aSN1EncodableVector5.add(new DERSet(createSubjectKeyId(engineGetCertificate2.getPublicKey())));
                    aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector5));
                    aSN1EncodableVector5 = new ASN1EncodableVector();
                    aSN1EncodableVector5.add(pkcs_9_at_friendlyName);
                    aSN1EncodableVector5.add(new DERSet(new DERBMPString(str)));
                    aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector5));
                }
                aSN1EncodableVector6.add(new SafeBag(certBag, certBag.toASN1Primitive(), new DERSet(aSN1EncodableVector)));
                hashtable.put(engineGetCertificate2, engineGetCertificate2);
            } catch (CertificateEncodingException e) {
                throw new IOException("Error encoding certificate: " + e.toString());
            }
        }
        Enumeration keys3 = this.certs.keys();
        while (keys3.hasMoreElements()) {
            try {
                str = (String) keys3.nextElement();
                Certificate certificate = (Certificate) this.certs.get(str);
                Object obj4 = null;
                if (this.keys.get(str) == null) {
                    CertBag certBag2 = new CertBag(x509Certificate, new DEROctetString(certificate.getEncoded()));
                    ASN1EncodableVector aSN1EncodableVector8 = new ASN1EncodableVector();
                    if (certificate instanceof PKCS12BagAttributeCarrier) {
                        PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) certificate;
                        dERBMPString2 = (DERBMPString) pKCS12BagAttributeCarrier2.getBagAttribute(pkcs_9_at_friendlyName);
                        if (dERBMPString2 == null || !dERBMPString2.getString().equals(str)) {
                            pKCS12BagAttributeCarrier2.setBagAttribute(pkcs_9_at_friendlyName, new DERBMPString(str));
                        }
                        Enumeration bagAttributeKeys3 = pKCS12BagAttributeCarrier2.getBagAttributeKeys();
                        while (bagAttributeKeys3.hasMoreElements()) {
                            aSN1ObjectIdentifier2 = (ASN1ObjectIdentifier) bagAttributeKeys3.nextElement();
                            if (!aSN1ObjectIdentifier2.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                                aSN1EncodableVector3 = new ASN1EncodableVector();
                                aSN1EncodableVector3.add(aSN1ObjectIdentifier2);
                                aSN1EncodableVector3.add(new DERSet(pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier2)));
                                aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector3));
                                obj4 = 1;
                            }
                        }
                    } else {
                        obj4 = null;
                    }
                    if (obj4 == null) {
                        aSN1EncodableVector2 = new ASN1EncodableVector();
                        aSN1EncodableVector2.add(pkcs_9_at_friendlyName);
                        aSN1EncodableVector2.add(new DERSet(new DERBMPString(str)));
                        aSN1EncodableVector8.add(new DERSequence(aSN1EncodableVector2));
                    }
                    aSN1EncodableVector6.add(new SafeBag(certBag, certBag2.toASN1Primitive(), new DERSet(aSN1EncodableVector8)));
                    hashtable.put(certificate, certificate);
                }
            } catch (CertificateEncodingException e2) {
                throw new IOException("Error encoding certificate: " + e2.toString());
            }
        }
        Enumeration keys4 = this.chainCerts.keys();
        while (keys4.hasMoreElements()) {
            Certificate certificate2 = (Certificate) this.chainCerts.get((CertId) keys4.nextElement());
            if (hashtable.get(certificate2) == null) {
                CertBag certBag3 = new CertBag(x509Certificate, new DEROctetString(certificate2.getEncoded()));
                aSN1EncodableVector3 = new ASN1EncodableVector();
                if (certificate2 instanceof PKCS12BagAttributeCarrier) {
                    PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier3 = (PKCS12BagAttributeCarrier) certificate2;
                    keys3 = pKCS12BagAttributeCarrier3.getBagAttributeKeys();
                    while (keys3.hasMoreElements()) {
                        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = (ASN1ObjectIdentifier) keys3.nextElement();
                        if (!aSN1ObjectIdentifier3.equals(PKCSObjectIdentifiers.pkcs_9_at_localKeyId)) {
                            aSN1EncodableVector = new ASN1EncodableVector();
                            aSN1EncodableVector.add(aSN1ObjectIdentifier3);
                            aSN1EncodableVector.add(new DERSet(pKCS12BagAttributeCarrier3.getBagAttribute(aSN1ObjectIdentifier3)));
                            aSN1EncodableVector3.add(new DERSequence(aSN1EncodableVector));
                        }
                    }
                }
                try {
                    aSN1EncodableVector6.add(new SafeBag(certBag, certBag3.toASN1Primitive(), new DERSet(aSN1EncodableVector3)));
                } catch (CertificateEncodingException e22) {
                    throw new IOException("Error encoding certificate: " + e22.toString());
                }
            }
        }
        EncryptedData encryptedData = new EncryptedData(data, algorithmIdentifier, new BERConstructedOctetString(cryptData(true, algorithmIdentifier, cArr, false, new DERSequence(aSN1EncodableVector6).getEncoded(ASN1Encoding.DER))));
        ASN1Encodable authenticatedSafe = new AuthenticatedSafe(new ContentInfo[]{new ContentInfo(data, bERConstructedOctetString), new ContentInfo(encryptedData, encryptedData.toASN1Primitive())});
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        (z ? new DEROutputStream(byteArrayOutputStream) : new BEROutputStream(byteArrayOutputStream)).writeObject(authenticatedSafe);
        ContentInfo contentInfo = new ContentInfo(data, new BERConstructedOctetString(byteArrayOutputStream.toByteArray()));
        byte[] bArr3 = new byte[20];
        this.random.nextBytes(bArr3);
        try {
            (z ? new DEROutputStream(outputStream) : new BEROutputStream(outputStream)).writeObject(new Pfx(contentInfo, new MacData(new DigestInfo(new AlgorithmIdentifier(id_SHA1, new DERNull()), calculatePbeMac(id_SHA1, bArr3, 1024, cArr, false, ((ASN1OctetString) contentInfo.getContent()).getOctets())), bArr3, 1024)));
        } catch (Exception e3) {
            throw new IOException("error constructing MAC: " + e3.toString());
        }
    }

    protected byte[] cryptData(boolean z, AlgorithmIdentifier algorithmIdentifier, char[] cArr, boolean z2, byte[] bArr) throws IOException {
        String id = algorithmIdentifier.getAlgorithm().getId();
        PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
        KeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory instance2 = SecretKeyFactory.getInstance(id, bcProvider);
            AlgorithmParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), instance.getIterations().intValue());
            BCPBEKey bCPBEKey = (BCPBEKey) instance2.generateSecret(pBEKeySpec);
            bCPBEKey.setTryWrongPKCS12Zero(z2);
            Cipher instance3 = Cipher.getInstance(id, bcProvider);
            instance3.init(z ? 1 : 2, bCPBEKey, pBEParameterSpec);
            return instance3.doFinal(bArr);
        } catch (Exception e) {
            throw new IOException("exception decrypting data - " + e.toString());
        }
    }

    public Enumeration engineAliases() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            String str = (String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.keys();
    }

    public boolean engineContainsAlias(String str) {
        return (this.certs.get(str) == null && this.keys.get(str) == null) ? false : true;
    }

    public void engineDeleteEntry(String str) throws KeyStoreException {
        Key key = (Key) this.keys.remove(str);
        Certificate certificate = (Certificate) this.certs.remove(str);
        if (certificate != null) {
            this.chainCerts.remove(new CertId(certificate.getPublicKey()));
        }
        if (key != null) {
            String str2 = (String) this.localIds.remove(str);
            if (str2 != null) {
                certificate = (Certificate) this.keyCerts.remove(str2);
            }
            if (certificate != null) {
                this.chainCerts.remove(new CertId(certificate.getPublicKey()));
            }
        }
        if (certificate == null && key == null) {
            throw new KeyStoreException("no such entry as " + str);
        }
    }

    public Certificate engineGetCertificate(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificate.");
        }
        Certificate certificate = (Certificate) this.certs.get(str);
        if (certificate != null) {
            return certificate;
        }
        String str2 = (String) this.localIds.get(str);
        return str2 != null ? (Certificate) this.keyCerts.get(str2) : (Certificate) this.keyCerts.get(str);
    }

    public String engineGetCertificateAlias(Certificate certificate) {
        Enumeration elements = this.certs.elements();
        Enumeration keys = this.certs.keys();
        while (elements.hasMoreElements()) {
            String str = (String) keys.nextElement();
            if (((Certificate) elements.nextElement()).equals(certificate)) {
                return str;
            }
        }
        elements = this.keyCerts.elements();
        keys = this.keyCerts.keys();
        while (elements.hasMoreElements()) {
            str = (String) keys.nextElement();
            if (((Certificate) elements.nextElement()).equals(certificate)) {
                return str;
            }
        }
        return null;
    }

    public Certificate[] engineGetCertificateChain(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null alias passed to getCertificateChain.");
        } else if (!engineIsKeyEntry(str)) {
            return null;
        } else {
            X509Certificate engineGetCertificate = engineGetCertificate(str);
            if (engineGetCertificate == null) {
                return null;
            }
            Vector vector = new Vector();
            while (engineGetCertificate != null) {
                Certificate certificate;
                X509Certificate x509Certificate;
                X509Certificate x509Certificate2 = engineGetCertificate;
                byte[] extensionValue = x509Certificate2.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                if (extensionValue != null) {
                    try {
                        AuthorityKeyIdentifier instance = AuthorityKeyIdentifier.getInstance((ASN1Sequence) new ASN1InputStream(((ASN1OctetString) new ASN1InputStream(extensionValue).readObject()).getOctets()).readObject());
                        certificate = instance.getKeyIdentifier() != null ? (Certificate) this.chainCerts.get(new CertId(instance.getKeyIdentifier())) : null;
                    } catch (IOException e) {
                        throw new RuntimeException(e.toString());
                    }
                }
                certificate = null;
                if (certificate == null) {
                    Principal issuerDN = x509Certificate2.getIssuerDN();
                    if (!issuerDN.equals(x509Certificate2.getSubjectDN())) {
                        Enumeration keys = this.chainCerts.keys();
                        while (keys.hasMoreElements()) {
                            x509Certificate = (X509Certificate) this.chainCerts.get(keys.nextElement());
                            if (x509Certificate.getSubjectDN().equals(issuerDN)) {
                                try {
                                    x509Certificate2.verify(x509Certificate.getPublicKey());
                                    break;
                                } catch (Exception e2) {
                                }
                            }
                        }
                    }
                }
                Certificate certificate2 = certificate;
                vector.addElement(engineGetCertificate);
                engineGetCertificate = x509Certificate != engineGetCertificate ? x509Certificate : null;
            }
            Certificate[] certificateArr = new Certificate[vector.size()];
            for (int i = 0; i != certificateArr.length; i++) {
                certificateArr[i] = (Certificate) vector.elementAt(i);
            }
            return certificateArr;
        }
    }

    public Date engineGetCreationDate(String str) {
        return new Date();
    }

    public Key engineGetKey(String str, char[] cArr) throws NoSuchAlgorithmException, UnrecoverableKeyException {
        if (str != null) {
            return (Key) this.keys.get(str);
        }
        throw new IllegalArgumentException("null alias passed to getKey.");
    }

    public boolean engineIsCertificateEntry(String str) {
        return this.certs.get(str) != null && this.keys.get(str) == null;
    }

    public boolean engineIsKeyEntry(String str) {
        return this.keys.get(str) != null;
    }

    public void engineLoad(InputStream inputStream, char[] cArr) throws IOException {
        if (inputStream != null) {
            if (cArr == null) {
                throw new NullPointerException("No password supplied for PKCS#12 KeyStore.");
            }
            InputStream bufferedInputStream = new BufferedInputStream(inputStream);
            bufferedInputStream.mark(10);
            if (bufferedInputStream.read() != 48) {
                throw new IOException("stream does not represent a PKCS12 key store");
            }
            boolean z;
            ASN1Sequence aSN1Sequence;
            ASN1ObjectIdentifier aSN1ObjectIdentifier;
            String string;
            bufferedInputStream.reset();
            Pfx instance = Pfx.getInstance((ASN1Sequence) new ASN1InputStream(bufferedInputStream).readObject());
            ContentInfo authSafe = instance.getAuthSafe();
            Vector vector = new Vector();
            Object obj = null;
            if (instance.getMacData() != null) {
                MacData macData = instance.getMacData();
                DigestInfo mac = macData.getMac();
                AlgorithmIdentifier algorithmId = mac.getAlgorithmId();
                byte[] salt = macData.getSalt();
                int intValue = macData.getIterationCount().intValue();
                byte[] octets = ((ASN1OctetString) authSafe.getContent()).getOctets();
                try {
                    byte[] calculatePbeMac = calculatePbeMac(algorithmId.getObjectId(), salt, intValue, cArr, false, octets);
                    byte[] digest = mac.getDigest();
                    if (Arrays.constantTimeAreEqual(calculatePbeMac, digest)) {
                        z = false;
                    } else if (cArr.length > 0) {
                        throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                    } else if (Arrays.constantTimeAreEqual(calculatePbeMac(algorithmId.getObjectId(), salt, intValue, cArr, true, octets), digest)) {
                        z = true;
                    } else {
                        throw new IOException("PKCS12 key store mac invalid - wrong password or corrupted file.");
                    }
                } catch (IOException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new IOException("error constructing MAC: " + e2.toString());
                }
            }
            z = false;
            this.keys = new IgnoresCaseHashtable();
            this.localIds = new Hashtable();
            if (authSafe.getContentType().equals(data)) {
                ContentInfo[] contentInfo = AuthenticatedSafe.getInstance(new ASN1InputStream(((ASN1OctetString) authSafe.getContent()).getOctets()).readObject()).getContentInfo();
                int i = 0;
                while (i != contentInfo.length) {
                    Object obj2;
                    int i2;
                    Object obj3;
                    Enumeration objects;
                    ASN1Encodable bagAttribute;
                    if (contentInfo[i].getContentType().equals(data)) {
                        ASN1Sequence aSN1Sequence2 = (ASN1Sequence) new ASN1InputStream(((ASN1OctetString) contentInfo[i].getContent()).getOctets()).readObject();
                        i2 = 0;
                        obj3 = obj;
                        while (i2 != aSN1Sequence2.size()) {
                            SafeBag instance2 = SafeBag.getInstance(aSN1Sequence2.getObjectAt(i2));
                            if (instance2.getBagId().equals(pkcs8ShroudedKeyBag)) {
                                Object obj4;
                                EncryptedPrivateKeyInfo instance3 = EncryptedPrivateKeyInfo.getInstance(instance2.getBagValue());
                                PrivateKey unwrapKey = unwrapKey(instance3.getEncryptionAlgorithm(), instance3.getEncryptedData(), cArr, z);
                                PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier = (PKCS12BagAttributeCarrier) unwrapKey;
                                ASN1OctetString aSN1OctetString = null;
                                if (instance2.getBagAttributes() != null) {
                                    objects = instance2.getBagAttributes().getObjects();
                                    obj4 = null;
                                    while (objects.hasMoreElements()) {
                                        ASN1Encodable aSN1Encodable;
                                        aSN1Sequence = (ASN1Sequence) objects.nextElement();
                                        aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
                                        ASN1Set aSN1Set = (ASN1Set) aSN1Sequence.getObjectAt(1);
                                        if (aSN1Set.size() > 0) {
                                            aSN1Encodable = (ASN1Primitive) aSN1Set.getObjectAt(0);
                                            bagAttribute = pKCS12BagAttributeCarrier.getBagAttribute(aSN1ObjectIdentifier);
                                            if (bagAttribute == null) {
                                                pKCS12BagAttributeCarrier.setBagAttribute(aSN1ObjectIdentifier, aSN1Encodable);
                                            } else if (!bagAttribute.toASN1Primitive().equals(aSN1Encodable)) {
                                                throw new IOException("attempt to add existing attribute with different value");
                                            }
                                        }
                                        aSN1Encodable = null;
                                        if (aSN1ObjectIdentifier.equals(pkcs_9_at_friendlyName)) {
                                            string = ((DERBMPString) aSN1Encodable).getString();
                                            this.keys.put(string, unwrapKey);
                                            obj4 = string;
                                        } else {
                                            aSN1OctetString = aSN1ObjectIdentifier.equals(pkcs_9_at_localKeyId) ? (ASN1OctetString) aSN1Encodable : aSN1OctetString;
                                        }
                                    }
                                } else {
                                    obj4 = null;
                                    aSN1OctetString = null;
                                }
                                if (aSN1OctetString != null) {
                                    string = new String(Hex.encode(aSN1OctetString.getOctets()));
                                    if (obj4 == null) {
                                        this.keys.put(string, unwrapKey);
                                        obj = obj3;
                                    } else {
                                        this.localIds.put(obj4, string);
                                        obj = obj3;
                                    }
                                } else {
                                    this.keys.put("unmarked", unwrapKey);
                                    int i3 = 1;
                                }
                            } else if (instance2.getBagId().equals(certBag)) {
                                vector.addElement(instance2);
                                obj = obj3;
                            } else {
                                System.out.println("extra in data " + instance2.getBagId());
                                System.out.println(ASN1Dump.dumpAsString(instance2));
                                obj = obj3;
                            }
                            i2++;
                            obj3 = obj;
                        }
                        obj2 = obj3;
                    } else if (contentInfo[i].getContentType().equals(encryptedData)) {
                        EncryptedData instance4 = EncryptedData.getInstance(contentInfo[i].getContent());
                        aSN1Sequence = (ASN1Sequence) ASN1Primitive.fromByteArray(cryptData(false, instance4.getEncryptionAlgorithm(), cArr, z, instance4.getContent().getOctets()));
                        for (i2 = 0; i2 != aSN1Sequence.size(); i2++) {
                            SafeBag instance5 = SafeBag.getInstance(aSN1Sequence.getObjectAt(i2));
                            if (instance5.getBagId().equals(certBag)) {
                                vector.addElement(instance5);
                            } else if (instance5.getBagId().equals(pkcs8ShroudedKeyBag)) {
                                EncryptedPrivateKeyInfo instance6 = EncryptedPrivateKeyInfo.getInstance(instance5.getBagValue());
                                r4 = unwrapKey(instance6.getEncryptionAlgorithm(), instance6.getEncryptedData(), cArr, z);
                                r3 = (PKCS12BagAttributeCarrier) r4;
                                objects = instance5.getBagAttributes().getObjects();
                                obj3 = null;
                                r12 = null;
                                while (objects.hasMoreElements()) {
                                    r5 = (ASN1Sequence) objects.nextElement();
                                    r7 = (ASN1ObjectIdentifier) r5.getObjectAt(0);
                                    r5 = (ASN1Set) r5.getObjectAt(1);
                                    if (r5.size() > 0) {
                                        r5 = (ASN1Primitive) r5.getObjectAt(0);
                                        bagAttribute = r3.getBagAttribute(r7);
                                        if (bagAttribute == null) {
                                            r3.setBagAttribute(r7, r5);
                                        } else if (!bagAttribute.toASN1Primitive().equals(r5)) {
                                            throw new IOException("attempt to add existing attribute with different value");
                                        }
                                    }
                                    r5 = null;
                                    if (r7.equals(pkcs_9_at_friendlyName)) {
                                        r5 = ((DERBMPString) r5).getString();
                                        this.keys.put(r5, r4);
                                        obj3 = r5;
                                    } else {
                                        r12 = r7.equals(pkcs_9_at_localKeyId) ? (ASN1OctetString) r5 : r12;
                                    }
                                }
                                r3 = new String(Hex.encode(r12.getOctets()));
                                if (obj3 == null) {
                                    this.keys.put(r3, r4);
                                } else {
                                    this.localIds.put(obj3, r3);
                                }
                            } else if (instance5.getBagId().equals(keyBag)) {
                                r4 = BouncyCastleProvider.getPrivateKey(new PrivateKeyInfo((ASN1Sequence) instance5.getBagValue()));
                                r3 = (PKCS12BagAttributeCarrier) r4;
                                objects = instance5.getBagAttributes().getObjects();
                                obj3 = null;
                                r12 = null;
                                while (objects.hasMoreElements()) {
                                    r5 = (ASN1Sequence) objects.nextElement();
                                    r7 = (ASN1ObjectIdentifier) r5.getObjectAt(0);
                                    r5 = (ASN1Set) r5.getObjectAt(1);
                                    if (r5.size() > 0) {
                                        r5 = (ASN1Primitive) r5.getObjectAt(0);
                                        bagAttribute = r3.getBagAttribute(r7);
                                        if (bagAttribute == null) {
                                            r3.setBagAttribute(r7, r5);
                                        } else if (!bagAttribute.toASN1Primitive().equals(r5)) {
                                            throw new IOException("attempt to add existing attribute with different value");
                                        }
                                    }
                                    r5 = null;
                                    if (r7.equals(pkcs_9_at_friendlyName)) {
                                        r5 = ((DERBMPString) r5).getString();
                                        this.keys.put(r5, r4);
                                        obj3 = r5;
                                    } else {
                                        r12 = r7.equals(pkcs_9_at_localKeyId) ? (ASN1OctetString) r5 : r12;
                                    }
                                }
                                r3 = new String(Hex.encode(r12.getOctets()));
                                if (obj3 == null) {
                                    this.keys.put(r3, r4);
                                } else {
                                    this.localIds.put(obj3, r3);
                                }
                            } else {
                                System.out.println("extra in encryptedData " + instance5.getBagId());
                                System.out.println(ASN1Dump.dumpAsString(instance5));
                            }
                        }
                        obj2 = obj;
                    } else {
                        System.out.println("extra " + contentInfo[i].getContentType().getId());
                        System.out.println("extra " + ASN1Dump.dumpAsString(contentInfo[i].getContent()));
                        obj2 = obj;
                    }
                    i++;
                    obj = obj2;
                }
            } else {
                obj = null;
            }
            this.certs = new IgnoresCaseHashtable();
            this.chainCerts = new Hashtable();
            this.keyCerts = new Hashtable();
            int i4 = 0;
            while (i4 != vector.size()) {
                SafeBag safeBag = (SafeBag) vector.elementAt(i4);
                CertBag instance7 = CertBag.getInstance(safeBag.getBagValue());
                if (instance7.getCertId().equals(x509Certificate)) {
                    try {
                        String str;
                        Certificate generateCertificate = this.certFact.generateCertificate(new ByteArrayInputStream(((ASN1OctetString) instance7.getCertValue()).getOctets()));
                        ASN1OctetString aSN1OctetString2 = null;
                        if (safeBag.getBagAttributes() != null) {
                            Enumeration objects2 = safeBag.getBagAttributes().getObjects();
                            str = null;
                            aSN1OctetString2 = null;
                            while (objects2.hasMoreElements()) {
                                aSN1Sequence = (ASN1Sequence) objects2.nextElement();
                                aSN1ObjectIdentifier = (ASN1ObjectIdentifier) aSN1Sequence.getObjectAt(0);
                                ASN1Primitive aSN1Primitive = (ASN1Primitive) ((ASN1Set) aSN1Sequence.getObjectAt(1)).getObjectAt(0);
                                if (generateCertificate instanceof PKCS12BagAttributeCarrier) {
                                    PKCS12BagAttributeCarrier pKCS12BagAttributeCarrier2 = (PKCS12BagAttributeCarrier) generateCertificate;
                                    ASN1Encodable bagAttribute2 = pKCS12BagAttributeCarrier2.getBagAttribute(aSN1ObjectIdentifier);
                                    if (bagAttribute2 == null) {
                                        pKCS12BagAttributeCarrier2.setBagAttribute(aSN1ObjectIdentifier, aSN1Primitive);
                                    } else if (!bagAttribute2.toASN1Primitive().equals(aSN1Primitive)) {
                                        throw new IOException("attempt to add existing attribute with different value");
                                    }
                                }
                                if (aSN1ObjectIdentifier.equals(pkcs_9_at_friendlyName)) {
                                    str = ((DERBMPString) aSN1Primitive).getString();
                                } else {
                                    aSN1OctetString2 = aSN1ObjectIdentifier.equals(pkcs_9_at_localKeyId) ? (ASN1OctetString) aSN1Primitive : aSN1OctetString2;
                                }
                            }
                        } else {
                            str = null;
                        }
                        this.chainCerts.put(new CertId(generateCertificate.getPublicKey()), generateCertificate);
                        if (obj == null) {
                            if (aSN1OctetString2 != null) {
                                this.keyCerts.put(new String(Hex.encode(aSN1OctetString2.getOctets())), generateCertificate);
                            }
                            if (str != null) {
                                this.certs.put(str, generateCertificate);
                            }
                        } else if (this.keyCerts.isEmpty()) {
                            string = new String(Hex.encode(createSubjectKeyId(generateCertificate.getPublicKey()).getKeyIdentifier()));
                            this.keyCerts.put(string, generateCertificate);
                            this.keys.put(string, this.keys.remove("unmarked"));
                        }
                        i4++;
                    } catch (Exception e22) {
                        throw new RuntimeException(e22.toString());
                    }
                }
                throw new RuntimeException("Unsupported certificate type: " + instance7.getCertId());
            }
        }
    }

    public void engineSetCertificateEntry(String str, Certificate certificate) throws KeyStoreException {
        if (this.keys.get(str) != null) {
            throw new KeyStoreException("There is a key entry with the name " + str + ".");
        }
        this.certs.put(str, certificate);
        this.chainCerts.put(new CertId(certificate.getPublicKey()), certificate);
    }

    public void engineSetKeyEntry(String str, Key key, char[] cArr, Certificate[] certificateArr) throws KeyStoreException {
        int i = 0;
        if ((key instanceof PrivateKey) && certificateArr == null) {
            throw new KeyStoreException("no certificate chain for private key");
        }
        if (this.keys.get(str) != null) {
            engineDeleteEntry(str);
        }
        this.keys.put(str, key);
        this.certs.put(str, certificateArr[0]);
        while (i != certificateArr.length) {
            this.chainCerts.put(new CertId(certificateArr[i].getPublicKey()), certificateArr[i]);
            i++;
        }
    }

    public void engineSetKeyEntry(String str, byte[] bArr, Certificate[] certificateArr) throws KeyStoreException {
        throw new RuntimeException("operation not supported");
    }

    public int engineSize() {
        Hashtable hashtable = new Hashtable();
        Enumeration keys = this.certs.keys();
        while (keys.hasMoreElements()) {
            hashtable.put(keys.nextElement(), "cert");
        }
        Enumeration keys2 = this.keys.keys();
        while (keys2.hasMoreElements()) {
            String str = (String) keys2.nextElement();
            if (hashtable.get(str) == null) {
                hashtable.put(str, "key");
            }
        }
        return hashtable.size();
    }

    public void engineStore(OutputStream outputStream, char[] cArr) throws IOException {
        doStore(outputStream, cArr, false);
    }

    public void engineStore(LoadStoreParameter loadStoreParameter) throws IOException, NoSuchAlgorithmException, CertificateException {
        if (loadStoreParameter == null) {
            throw new IllegalArgumentException("'param' arg cannot be null");
        } else if (loadStoreParameter instanceof JDKPKCS12StoreParameter) {
            char[] cArr;
            JDKPKCS12StoreParameter jDKPKCS12StoreParameter = (JDKPKCS12StoreParameter) loadStoreParameter;
            ProtectionParameter protectionParameter = loadStoreParameter.getProtectionParameter();
            if (protectionParameter == null) {
                cArr = null;
            } else if (protectionParameter instanceof PasswordProtection) {
                cArr = ((PasswordProtection) protectionParameter).getPassword();
            } else {
                throw new IllegalArgumentException("No support for protection parameter of type " + protectionParameter.getClass().getName());
            }
            doStore(jDKPKCS12StoreParameter.getOutputStream(), cArr, jDKPKCS12StoreParameter.isUseDEREncoding());
        } else {
            throw new IllegalArgumentException("No support for 'param' of type " + loadStoreParameter.getClass().getName());
        }
    }

    public void setRandom(SecureRandom secureRandom) {
        this.random = secureRandom;
    }

    protected PrivateKey unwrapKey(AlgorithmIdentifier algorithmIdentifier, byte[] bArr, char[] cArr, boolean z) throws IOException {
        String id = algorithmIdentifier.getAlgorithm().getId();
        PKCS12PBEParams instance = PKCS12PBEParams.getInstance(algorithmIdentifier.getParameters());
        KeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory instance2 = SecretKeyFactory.getInstance(id, bcProvider);
            AlgorithmParameterSpec pBEParameterSpec = new PBEParameterSpec(instance.getIV(), instance.getIterations().intValue());
            Key generateSecret = instance2.generateSecret(pBEKeySpec);
            ((BCPBEKey) generateSecret).setTryWrongPKCS12Zero(z);
            Cipher instance3 = Cipher.getInstance(id, bcProvider);
            instance3.init(4, generateSecret, pBEParameterSpec);
            return (PrivateKey) instance3.unwrap(bArr, "", 2);
        } catch (Exception e) {
            throw new IOException("exception unwrapping private key - " + e.toString());
        }
    }

    protected byte[] wrapKey(String str, Key key, PKCS12PBEParams pKCS12PBEParams, char[] cArr) throws IOException {
        KeySpec pBEKeySpec = new PBEKeySpec(cArr);
        try {
            SecretKeyFactory instance = SecretKeyFactory.getInstance(str, bcProvider);
            AlgorithmParameterSpec pBEParameterSpec = new PBEParameterSpec(pKCS12PBEParams.getIV(), pKCS12PBEParams.getIterations().intValue());
            Cipher instance2 = Cipher.getInstance(str, bcProvider);
            instance2.init(3, instance.generateSecret(pBEKeySpec), pBEParameterSpec);
            return instance2.wrap(key);
        } catch (Exception e) {
            throw new IOException("exception encrypting data - " + e.toString());
        }
    }
}
