package org.spongycastle.x509;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateFactory;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.PKIXCertPathChecker;
import java.security.cert.PKIXParameters;
import java.security.cert.PolicyNode;
import java.security.cert.TrustAnchor;
import java.security.cert.X509CRL;
import java.security.cert.X509CRLEntry;
import java.security.cert.X509CertSelector;
import java.security.cert.X509Certificate;
import java.security.cert.X509Extension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AccessDescription;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AuthorityInformationAccess;
import org.spongycastle.asn1.x509.AuthorityKeyIdentifier;
import org.spongycastle.asn1.x509.BasicConstraints;
import org.spongycastle.asn1.x509.CRLDistPoint;
import org.spongycastle.asn1.x509.DisplayText;
import org.spongycastle.asn1.x509.DistributionPoint;
import org.spongycastle.asn1.x509.DistributionPointName;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.GeneralSubtree;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.NameConstraints;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.asn1.x509.qualified.MonetaryValue;
import org.spongycastle.asn1.x509.qualified.QCStatement;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.i18n.ErrorBundle;
import org.spongycastle.i18n.LocaleString;
import org.spongycastle.i18n.filter.TrustedInput;
import org.spongycastle.i18n.filter.UntrustedInput;
import org.spongycastle.i18n.filter.UntrustedUrlInput;
import org.spongycastle.jce.provider.AnnotatedException;
import org.spongycastle.jce.provider.CertPathValidatorUtilities;
import org.spongycastle.jce.provider.PKIXNameConstraintValidator;
import org.spongycastle.x509.extension.X509ExtensionUtil;

public class PKIXCertPathReviewer extends CertPathValidatorUtilities {
    private static final String AUTH_INFO_ACCESS = X509Extensions.AuthorityInfoAccess.getId();
    private static final String CRL_DIST_POINTS = X509Extensions.CRLDistributionPoints.getId();
    private static final String QC_STATEMENT = X509Extensions.QCStatements.getId();
    private static final String RESOURCE_NAME = "org.spongycastle.x509.CertPathReviewerMessages";
    protected CertPath certPath;
    protected List certs;
    protected List[] errors;
    private boolean initialized;
    protected int n;
    protected List[] notifications;
    protected PKIXParameters pkixParams;
    protected PolicyNode policyTree;
    protected PublicKey subjectPublicKey;
    protected TrustAnchor trustAnchor;
    protected Date validDate;

    public PKIXCertPathReviewer(CertPath certPath, PKIXParameters pKIXParameters) throws CertPathReviewerException {
        init(certPath, pKIXParameters);
    }

    private String IPtoString(byte[] bArr) {
        try {
            return InetAddress.getByAddress(bArr).getHostAddress();
        } catch (Exception e) {
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i != bArr.length; i++) {
                stringBuffer.append(Integer.toHexString(bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV));
                stringBuffer.append(' ');
            }
            return stringBuffer.toString();
        }
    }

    private void checkCriticalExtensions() {
        Iterator it;
        List<PKIXCertPathChecker> certPathCheckers = this.pkixParams.getCertPathCheckers();
        for (PKIXCertPathChecker init : certPathCheckers) {
            try {
                init.init(false);
            } catch (CertPathValidatorException e) {
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.criticalExtensionError", new Object[]{e.getMessage(), e, e.getClass().getName()}), e.getCause(), this.certPath, r3);
            } catch (Throwable e2) {
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certPathCheckerError", new Object[]{e2.getMessage(), e2, e2.getClass().getName()}), e2);
            } catch (CertPathReviewerException e3) {
                addError(e3.getErrorMessage(), e3.getIndex());
                return;
            }
        }
        int size = this.certs.size() - 1;
        while (size >= 0) {
            Certificate certificate = (X509Certificate) this.certs.get(size);
            Set criticalExtensionOIDs = certificate.getCriticalExtensionOIDs();
            if (!(criticalExtensionOIDs == null || criticalExtensionOIDs.isEmpty())) {
                criticalExtensionOIDs.remove(KEY_USAGE);
                criticalExtensionOIDs.remove(CERTIFICATE_POLICIES);
                criticalExtensionOIDs.remove(POLICY_MAPPINGS);
                criticalExtensionOIDs.remove(INHIBIT_ANY_POLICY);
                criticalExtensionOIDs.remove(ISSUING_DISTRIBUTION_POINT);
                criticalExtensionOIDs.remove(DELTA_CRL_INDICATOR);
                criticalExtensionOIDs.remove(POLICY_CONSTRAINTS);
                criticalExtensionOIDs.remove(BASIC_CONSTRAINTS);
                criticalExtensionOIDs.remove(SUBJECT_ALTERNATIVE_NAME);
                criticalExtensionOIDs.remove(NAME_CONSTRAINTS);
                if (criticalExtensionOIDs.contains(QC_STATEMENT) && processQcStatements(certificate, size)) {
                    criticalExtensionOIDs.remove(QC_STATEMENT);
                }
                for (PKIXCertPathChecker init2 : certPathCheckers) {
                    init2.check(certificate, criticalExtensionOIDs);
                }
                if (!criticalExtensionOIDs.isEmpty()) {
                    it = criticalExtensionOIDs.iterator();
                    while (it.hasNext()) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.unknownCriticalExt", new Object[]{new DERObjectIdentifier((String) it.next())}), size);
                    }
                }
            }
            size--;
        }
    }

    private void checkNameConstraints() {
        PKIXNameConstraintValidator pKIXNameConstraintValidator = new PKIXNameConstraintValidator();
        for (int size = this.certs.size() - 1; size > 0; size--) {
            ASN1Sequence aSN1Sequence;
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate)) {
                try {
                    aSN1Sequence = (ASN1Sequence) new ASN1InputStream(new ByteArrayInputStream(CertPathValidatorUtilities.getSubjectPrincipal(x509Certificate).getEncoded())).readObject();
                    pKIXNameConstraintValidator.checkPermittedDN(aSN1Sequence);
                    pKIXNameConstraintValidator.checkExcludedDN(aSN1Sequence);
                    aSN1Sequence = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, SUBJECT_ALTERNATIVE_NAME);
                    if (aSN1Sequence != null) {
                        for (int i = 0; i < aSN1Sequence.size(); i++) {
                            GeneralName instance = GeneralName.getInstance(aSN1Sequence.getObjectAt(i));
                            pKIXNameConstraintValidator.checkPermitted(instance);
                            pKIXNameConstraintValidator.checkExcluded(instance);
                        }
                    }
                } catch (Throwable e) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ncExtError"), e, this.certPath, size);
                } catch (Throwable e2) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notPermittedEmail", new Object[]{new UntrustedInput(instance)}), e2, this.certPath, size);
                } catch (Throwable e22) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.subjAltNameExtError"), e22, this.certPath, size);
                } catch (Throwable e222) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.excludedDN", new Object[]{new UntrustedInput(r2.getName())}), e222, this.certPath, size);
                } catch (Throwable e2222) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notPermittedDN", new Object[]{new UntrustedInput(r2.getName())}), e2222, this.certPath, size);
                } catch (Throwable e22222) {
                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ncSubjectNameError", new Object[]{new UntrustedInput(r2)}), e22222, this.certPath, size);
                } catch (CertPathReviewerException e3) {
                    addError(e3.getErrorMessage(), e3.getIndex());
                    return;
                }
            }
            ASN1Sequence aSN1Sequence2 = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, NAME_CONSTRAINTS);
            if (aSN1Sequence2 != null) {
                NameConstraints instance2 = NameConstraints.getInstance(aSN1Sequence2);
                aSN1Sequence = instance2.getPermittedSubtrees();
                if (aSN1Sequence != null) {
                    pKIXNameConstraintValidator.intersectPermittedSubtree(aSN1Sequence);
                }
                aSN1Sequence2 = instance2.getExcludedSubtrees();
                if (aSN1Sequence2 != null) {
                    Enumeration objects = aSN1Sequence2.getObjects();
                    while (objects.hasMoreElements()) {
                        pKIXNameConstraintValidator.addExcludedSubtree(GeneralSubtree.getInstance(objects.nextElement()));
                    }
                }
            }
        }
    }

    private void checkPathLength() {
        int i = this.n;
        int size = this.certs.size() - 1;
        int i2 = 0;
        while (size > 0) {
            BasicConstraints instance;
            int intValue;
            X509Certificate x509Certificate = (X509Certificate) this.certs.get(size);
            if (!CertPathValidatorUtilities.isSelfIssued(x509Certificate)) {
                if (i <= 0) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.pathLenghtExtended"));
                }
                i--;
                i2++;
            }
            try {
                instance = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, BASIC_CONSTRAINTS));
            } catch (AnnotatedException e) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.processLengthConstError"), size);
                instance = null;
            }
            if (instance != null) {
                BigInteger pathLenConstraint = instance.getPathLenConstraint();
                if (pathLenConstraint != null) {
                    intValue = pathLenConstraint.intValue();
                    if (intValue < i) {
                        size--;
                        i = intValue;
                    }
                }
            }
            intValue = i;
            size--;
            i = intValue;
        }
        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.totalPathLength", new Object[]{new Integer(i2)}));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void checkPolicy() {
        /*
        r24 = this;
        r0 = r24;
        r2 = r0.pkixParams;
        r19 = r2.getInitialPolicies();
        r0 = r24;
        r2 = r0.n;
        r2 = r2 + 1;
        r0 = new java.util.ArrayList[r2];
        r20 = r0;
        r2 = 0;
    L_0x0013:
        r0 = r20;
        r3 = r0.length;
        if (r2 >= r3) goto L_0x0022;
    L_0x0018:
        r3 = new java.util.ArrayList;
        r3.<init>();
        r20[r2] = r3;
        r2 = r2 + 1;
        goto L_0x0013;
    L_0x0022:
        r5 = new java.util.HashSet;
        r5.<init>();
        r2 = "2.5.29.32.0";
        r5.add(r2);
        r2 = new org.spongycastle.jce.provider.PKIXPolicyNode;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = 0;
        r6 = 0;
        r7 = new java.util.HashSet;
        r7.<init>();
        r8 = "2.5.29.32.0";
        r9 = 0;
        r2.<init>(r3, r4, r5, r6, r7, r8, r9);
        r3 = 0;
        r3 = r20[r3];
        r3.add(r2);
        r0 = r24;
        r3 = r0.pkixParams;
        r3 = r3.isExplicitPolicyRequired();
        if (r3 == 0) goto L_0x00f1;
    L_0x0050:
        r3 = 0;
    L_0x0051:
        r0 = r24;
        r4 = r0.pkixParams;
        r4 = r4.isAnyPolicyInhibited();
        if (r4 == 0) goto L_0x00f9;
    L_0x005b:
        r4 = 0;
    L_0x005c:
        r0 = r24;
        r5 = r0.pkixParams;
        r5 = r5.isPolicyMappingInhibited();
        if (r5 == 0) goto L_0x0101;
    L_0x0066:
        r5 = 0;
    L_0x0067:
        r10 = 0;
        r0 = r24;
        r6 = r0.certs;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = r6.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r7 = r6 + -1;
        r6 = 0;
        r15 = r2;
        r16 = r7;
        r13 = r5;
        r14 = r4;
        r12 = r3;
        r5 = r6;
    L_0x007a:
        if (r16 < 0) goto L_0x0461;
    L_0x007c:
        r0 = r24;
        r2 = r0.n;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = r2 - r16;
        r0 = r24;
        r2 = r0.certs;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2 = r2.get(r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r2;
        r0 = (java.security.cert.X509Certificate) r0;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r10 = r0;
        r2 = CERTIFICATE_POLICIES;	 Catch:{ AnnotatedException -> 0x0109 }
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r2);	 Catch:{ AnnotatedException -> 0x0109 }
        r0 = r2;
        r0 = (org.spongycastle.asn1.ASN1Sequence) r0;	 Catch:{ AnnotatedException -> 0x0109 }
        r11 = r0;
        if (r11 == 0) goto L_0x0694;
    L_0x009c:
        if (r15 == 0) goto L_0x0694;
    L_0x009e:
        r3 = r11.getObjects();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x00a7:
        r6 = r3.hasMoreElements();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r6 == 0) goto L_0x0135;
    L_0x00ad:
        r6 = r3.nextElement();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = org.spongycastle.asn1.x509.PolicyInformation.getInstance(r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r7 = r6.getPolicyIdentifier();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r7.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.add(r8);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = "2.5.29.32.0";
        r9 = r7.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r8.equals(r9);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 != 0) goto L_0x00a7;
    L_0x00cc:
        r6 = r6.getPolicyQualifiers();	 Catch:{ CertPathValidatorException -> 0x011f }
        r6 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(r6);	 Catch:{ CertPathValidatorException -> 0x011f }
        r0 = r20;
        r8 = org.spongycastle.jce.provider.CertPathValidatorUtilities.processCertD1i(r4, r0, r7, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 != 0) goto L_0x00a7;
    L_0x00dc:
        r0 = r20;
        org.spongycastle.jce.provider.CertPathValidatorUtilities.processCertD1ii(r4, r0, r7, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x00a7;
    L_0x00e2:
        r2 = move-exception;
        r3 = r2.getErrorMessage();
        r2 = r2.getIndex();
        r0 = r24;
        r0.addError(r3, r2);
    L_0x00f0:
        return;
    L_0x00f1:
        r0 = r24;
        r3 = r0.n;
        r3 = r3 + 1;
        goto L_0x0051;
    L_0x00f9:
        r0 = r24;
        r4 = r0.n;
        r4 = r4 + 1;
        goto L_0x005c;
    L_0x0101:
        r0 = r24;
        r5 = r0.n;
        r5 = r5 + 1;
        goto L_0x0067;
    L_0x0109:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyExtError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x011f:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyQualifierError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0135:
        if (r5 == 0) goto L_0x013f;
    L_0x0137:
        r3 = "2.5.29.32.0";
        r3 = r5.contains(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 == 0) goto L_0x01c9;
    L_0x013f:
        r17 = r2;
    L_0x0141:
        if (r14 > 0) goto L_0x014f;
    L_0x0143:
        r0 = r24;
        r2 = r0.n;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r4 >= r2) goto L_0x0228;
    L_0x0149:
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(r10);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0228;
    L_0x014f:
        r2 = r11.getObjects();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0153:
        r3 = r2.hasMoreElements();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 == 0) goto L_0x0228;
    L_0x0159:
        r3 = r2.nextElement();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = org.spongycastle.asn1.x509.PolicyInformation.getInstance(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "2.5.29.32.0";
        r6 = r3.getPolicyIdentifier();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = r6.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = r5.equals(r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 == 0) goto L_0x0153;
    L_0x0171:
        r2 = r3.getPolicyQualifiers();	 Catch:{ CertPathValidatorException -> 0x01e8 }
        r7 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getQualifierSet(r2);	 Catch:{ CertPathValidatorException -> 0x01e8 }
        r2 = r4 + -1;
        r21 = r20[r2];
        r2 = 0;
        r18 = r2;
    L_0x0180:
        r2 = r21.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r18;
        if (r0 >= r2) goto L_0x0228;
    L_0x0188:
        r0 = r21;
        r1 = r18;
        r6 = r0.get(r1);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = (org.spongycastle.jce.provider.PKIXPolicyNode) r6;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r6.getExpectedPolicies();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r22 = r2.iterator();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x019a:
        r2 = r22.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0663;
    L_0x01a0:
        r2 = r22.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = r2 instanceof java.lang.String;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 == 0) goto L_0x01fe;
    L_0x01a8:
        r2 = (java.lang.String) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r2;
    L_0x01ab:
        r3 = 0;
        r5 = r6.getChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x01b0:
        r2 = r5.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0209;
    L_0x01b6:
        r2 = r5.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r8.equals(r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0660;
    L_0x01c6:
        r2 = 1;
    L_0x01c7:
        r3 = r2;
        goto L_0x01b0;
    L_0x01c9:
        r3 = r5.iterator();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r17 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r17.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x01d2:
        r5 = r3.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 == 0) goto L_0x0141;
    L_0x01d8:
        r5 = r3.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = r2.contains(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r6 == 0) goto L_0x01d2;
    L_0x01e2:
        r0 = r17;
        r0.add(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x01d2;
    L_0x01e8:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyQualifierError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x01fe:
        r3 = r2 instanceof org.spongycastle.asn1.DERObjectIdentifier;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 == 0) goto L_0x019a;
    L_0x0202:
        r2 = (org.spongycastle.asn1.DERObjectIdentifier) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r2.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x01ab;
    L_0x0209:
        if (r3 != 0) goto L_0x019a;
    L_0x020b:
        r5 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5.add(r8);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = new org.spongycastle.jce.provider.PKIXPolicyNode;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new java.util.ArrayList;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r9 = 0;
        r2.<init>(r3, r4, r5, r6, r7, r8, r9);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6.addChild(r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = r20[r4];	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3.add(r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x019a;
    L_0x0228:
        r2 = r4 + -1;
        r6 = r2;
    L_0x022b:
        if (r6 < 0) goto L_0x0251;
    L_0x022d:
        r7 = r20[r6];
        r2 = 0;
        r3 = r15;
        r5 = r2;
    L_0x0232:
        r2 = r7.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 >= r2) goto L_0x0669;
    L_0x0238:
        r2 = r7.get(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r2.hasChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 != 0) goto L_0x065d;
    L_0x0244:
        r0 = r20;
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(r3, r0, r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x066a;
    L_0x024c:
        r3 = r5 + 1;
        r5 = r3;
        r3 = r2;
        goto L_0x0232;
    L_0x0251:
        r2 = r10.getCriticalExtensionOIDs();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0274;
    L_0x0257:
        r3 = CERTIFICATE_POLICIES;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = r2.contains(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = r20[r4];
        r2 = 0;
        r3 = r2;
    L_0x0261:
        r2 = r6.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 >= r2) goto L_0x0274;
    L_0x0267:
        r2 = r6.get(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.setCritical(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0261;
    L_0x0274:
        r9 = r17;
        r7 = r15;
    L_0x0277:
        if (r11 != 0) goto L_0x027a;
    L_0x0279:
        r7 = 0;
    L_0x027a:
        if (r12 > 0) goto L_0x028d;
    L_0x027c:
        if (r7 != 0) goto L_0x028d;
    L_0x027e:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.noValidPolicyTree";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.<init>(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x028d:
        r0 = r24;
        r2 = r0.n;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r4 == r2) goto L_0x0657;
    L_0x0293:
        r2 = POLICY_MAPPINGS;	 Catch:{ AnnotatedException -> 0x02dc }
        r3 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r2);	 Catch:{ AnnotatedException -> 0x02dc }
        if (r3 == 0) goto L_0x0313;
    L_0x029b:
        r0 = r3;
        r0 = (org.spongycastle.asn1.ASN1Sequence) r0;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r0;
        r5 = 0;
        r8 = r5;
    L_0x02a1:
        r5 = r2.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 >= r5) goto L_0x0313;
    L_0x02a7:
        r5 = r2.getObjectAt(r8);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = (org.spongycastle.asn1.ASN1Sequence) r5;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = 0;
        r6 = r5.getObjectAt(r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = (org.spongycastle.asn1.DERObjectIdentifier) r6;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r11 = 1;
        r5 = r5.getObjectAt(r11);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = (org.spongycastle.asn1.DERObjectIdentifier) r5;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r11 = "2.5.29.32.0";
        r6 = r6.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r6 = r11.equals(r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r6 == 0) goto L_0x02f2;
    L_0x02c7:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.invalidPolicyMapping";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x02dc:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyMapExtError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x02f2:
        r6 = "2.5.29.32.0";
        r5 = r5.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = r6.equals(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 == 0) goto L_0x0670;
    L_0x02fe:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.invalidPolicyMapping";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0313:
        if (r3 == 0) goto L_0x03b8;
    L_0x0315:
        r0 = r3;
        r0 = (org.spongycastle.asn1.ASN1Sequence) r0;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = r0;
        r8 = new java.util.HashMap;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r11 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r11.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = 0;
        r6 = r2;
    L_0x0325:
        r2 = r5.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r6 >= r2) goto L_0x0369;
    L_0x032b:
        r2 = r5.getObjectAt(r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.asn1.ASN1Sequence) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = 0;
        r3 = r2.getObjectAt(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = (org.spongycastle.asn1.DERObjectIdentifier) r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = r3.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r15 = 1;
        r2 = r2.getObjectAt(r15);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.asn1.DERObjectIdentifier) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r15 = r2.getId();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r8.containsKey(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 != 0) goto L_0x035f;
    L_0x034d:
        r2 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.add(r15);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8.put(r3, r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r11.add(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x035b:
        r2 = r6 + 1;
        r6 = r2;
        goto L_0x0325;
    L_0x035f:
        r2 = r8.get(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (java.util.Set) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.add(r15);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x035b;
    L_0x0369:
        r5 = r11.iterator();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = r7;
    L_0x036e:
        r2 = r5.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0675;
    L_0x0374:
        r2 = r5.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (java.lang.String) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r13 <= 0) goto L_0x03ae;
    L_0x037c:
        r0 = r20;
        org.spongycastle.jce.provider.CertPathValidatorUtilities.prepareNextCertB1(r4, r0, r2, r8, r10);	 Catch:{ AnnotatedException -> 0x0382, CertPathValidatorException -> 0x0398 }
        goto L_0x036e;
    L_0x0382:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyExtError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0398:
        r2 = move-exception;
        r3 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = "org.spongycastle.x509.CertPathReviewerMessages";
        r6 = "CertPathReviewer.policyQualifierError";
        r4.<init>(r5, r6);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r5 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r3.<init>(r4, r2, r5, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r3;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x03ae:
        if (r13 > 0) goto L_0x0654;
    L_0x03b0:
        r0 = r20;
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.prepareNextCertB2(r4, r0, r2, r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x03b6:
        r3 = r2;
        goto L_0x036e;
    L_0x03b8:
        r5 = r7;
    L_0x03b9:
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(r10);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 != 0) goto L_0x064f;
    L_0x03bf:
        if (r12 == 0) goto L_0x064c;
    L_0x03c1:
        r3 = r12 + -1;
    L_0x03c3:
        if (r13 == 0) goto L_0x0649;
    L_0x03c5:
        r4 = r13 + -1;
    L_0x03c7:
        if (r14 == 0) goto L_0x0646;
    L_0x03c9:
        r14 = r14 + -1;
        r6 = r14;
    L_0x03cc:
        r2 = POLICY_CONSTRAINTS;	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r2);	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = (org.spongycastle.asn1.ASN1Sequence) r2;	 Catch:{ AnnotatedException -> 0x0411 }
        if (r2 == 0) goto L_0x0427;
    L_0x03d6:
        r7 = r2.getObjects();	 Catch:{ AnnotatedException -> 0x0411 }
    L_0x03da:
        r2 = r7.hasMoreElements();	 Catch:{ AnnotatedException -> 0x0411 }
        if (r2 == 0) goto L_0x0427;
    L_0x03e0:
        r2 = r7.nextElement();	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = (org.spongycastle.asn1.ASN1TaggedObject) r2;	 Catch:{ AnnotatedException -> 0x0411 }
        r8 = r2.getTagNo();	 Catch:{ AnnotatedException -> 0x0411 }
        switch(r8) {
            case 0: goto L_0x03f0;
            case 1: goto L_0x0401;
            default: goto L_0x03ed;
        };	 Catch:{ AnnotatedException -> 0x0411 }
    L_0x03ed:
        r2 = r4;
    L_0x03ee:
        r4 = r2;
        goto L_0x03da;
    L_0x03f0:
        r8 = 0;
        r2 = org.spongycastle.asn1.DERInteger.getInstance(r2, r8);	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = r2.getValue();	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = r2.intValue();	 Catch:{ AnnotatedException -> 0x0411 }
        if (r2 >= r3) goto L_0x03ed;
    L_0x03ff:
        r3 = r2;
        goto L_0x03da;
    L_0x0401:
        r8 = 0;
        r2 = org.spongycastle.asn1.DERInteger.getInstance(r2, r8);	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = r2.getValue();	 Catch:{ AnnotatedException -> 0x0411 }
        r2 = r2.intValue();	 Catch:{ AnnotatedException -> 0x0411 }
        if (r2 >= r4) goto L_0x03ed;
    L_0x0410:
        goto L_0x03ee;
    L_0x0411:
        r2 = move-exception;
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.policyConstExtError";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0427:
        r2 = INHIBIT_ANY_POLICY;	 Catch:{ AnnotatedException -> 0x044b }
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r2);	 Catch:{ AnnotatedException -> 0x044b }
        r2 = (org.spongycastle.asn1.DERInteger) r2;	 Catch:{ AnnotatedException -> 0x044b }
        if (r2 == 0) goto L_0x0642;
    L_0x0431:
        r2 = r2.getValue();	 Catch:{ AnnotatedException -> 0x044b }
        r2 = r2.intValue();	 Catch:{ AnnotatedException -> 0x044b }
        if (r2 >= r6) goto L_0x0642;
    L_0x043b:
        r23 = r5;
        r5 = r2;
        r2 = r23;
    L_0x0440:
        r6 = r16 + -1;
        r15 = r2;
        r16 = r6;
        r13 = r4;
        r14 = r5;
        r12 = r3;
        r5 = r9;
        goto L_0x007a;
    L_0x044b:
        r2 = move-exception;
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.policyInhibitExtError";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0461:
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.isSelfIssued(r10);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 != 0) goto L_0x063f;
    L_0x0467:
        if (r12 <= 0) goto L_0x063f;
    L_0x0469:
        r3 = r12 + -1;
    L_0x046b:
        r2 = POLICY_CONSTRAINTS;	 Catch:{ AnnotatedException -> 0x04a1 }
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.getExtensionValue(r10, r2);	 Catch:{ AnnotatedException -> 0x04a1 }
        r2 = (org.spongycastle.asn1.ASN1Sequence) r2;	 Catch:{ AnnotatedException -> 0x04a1 }
        if (r2 == 0) goto L_0x04b7;
    L_0x0475:
        r4 = r2.getObjects();	 Catch:{ AnnotatedException -> 0x04a1 }
    L_0x0479:
        r2 = r4.hasMoreElements();	 Catch:{ AnnotatedException -> 0x04a1 }
        if (r2 == 0) goto L_0x04b7;
    L_0x047f:
        r2 = r4.nextElement();	 Catch:{ AnnotatedException -> 0x04a1 }
        r2 = (org.spongycastle.asn1.ASN1TaggedObject) r2;	 Catch:{ AnnotatedException -> 0x04a1 }
        r6 = r2.getTagNo();	 Catch:{ AnnotatedException -> 0x04a1 }
        switch(r6) {
            case 0: goto L_0x048f;
            default: goto L_0x048c;
        };	 Catch:{ AnnotatedException -> 0x04a1 }
    L_0x048c:
        r2 = r3;
    L_0x048d:
        r3 = r2;
        goto L_0x0479;
    L_0x048f:
        r6 = 0;
        r2 = org.spongycastle.asn1.DERInteger.getInstance(r2, r6);	 Catch:{ AnnotatedException -> 0x04a1 }
        r2 = r2.getValue();	 Catch:{ AnnotatedException -> 0x04a1 }
        r2 = r2.intValue();	 Catch:{ AnnotatedException -> 0x04a1 }
        if (r2 != 0) goto L_0x048c;
    L_0x049e:
        r3 = 0;
        r2 = r3;
        goto L_0x048d;
    L_0x04a1:
        r2 = move-exception;
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.policyConstExtError";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x04b7:
        r6 = r3;
        if (r15 != 0) goto L_0x04ee;
    L_0x04ba:
        r0 = r24;
        r2 = r0.pkixParams;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2.isExplicitPolicyRequired();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0678;
    L_0x04c4:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.explicitPolicy";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x04d9:
        r2 = r3;
    L_0x04da:
        r3 = r2;
    L_0x04db:
        if (r6 > 0) goto L_0x00f0;
    L_0x04dd:
        if (r3 != 0) goto L_0x00f0;
    L_0x04df:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.invalidPolicy";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2.<init>(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x04ee:
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.isAnyPolicy(r19);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0598;
    L_0x04f4:
        r0 = r24;
        r2 = r0.pkixParams;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2.isExplicitPolicyRequired();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x063c;
    L_0x04fe:
        r2 = r5.isEmpty();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0519;
    L_0x0504:
        r2 = new org.spongycastle.x509.CertPathReviewerException;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = new org.spongycastle.i18n.ErrorBundle;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r4 = "org.spongycastle.x509.CertPathReviewerMessages";
        r5 = "CertPathReviewer.explicitPolicy";
        r3.<init>(r4, r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r24;
        r4 = r0.certPath;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r16;
        r2.<init>(r3, r4, r0);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        throw r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0519:
        r7 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r7.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = 0;
        r4 = r2;
    L_0x0520:
        r0 = r20;
        r2 = r0.length;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r4 >= r2) goto L_0x0553;
    L_0x0525:
        r8 = r20[r4];
        r2 = 0;
        r3 = r2;
    L_0x0529:
        r2 = r8.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 >= r2) goto L_0x0680;
    L_0x052f:
        r2 = r8.get(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r9 = "2.5.29.32.0";
        r10 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r9 = r9.equals(r10);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r9 == 0) goto L_0x067b;
    L_0x0541:
        r2 = r2.getChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0545:
        r9 = r2.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r9 == 0) goto L_0x067b;
    L_0x054b:
        r9 = r2.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r7.add(r9);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x0545;
    L_0x0553:
        r3 = r7.iterator();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0557:
        r2 = r3.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x056b;
    L_0x055d:
        r2 = r3.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5.contains(r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x0557;
    L_0x056b:
        if (r15 == 0) goto L_0x063c;
    L_0x056d:
        r0 = r24;
        r2 = r0.n;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2 + -1;
        r3 = r15;
        r4 = r2;
    L_0x0575:
        if (r4 < 0) goto L_0x04db;
    L_0x0577:
        r7 = r20[r4];
        r2 = 0;
        r5 = r2;
    L_0x057b:
        r2 = r7.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 >= r2) goto L_0x0685;
    L_0x0581:
        r2 = r7.get(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r2.hasChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 != 0) goto L_0x0639;
    L_0x058d:
        r0 = r20;
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(r3, r0, r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x0593:
        r3 = r5 + 1;
        r5 = r3;
        r3 = r2;
        goto L_0x057b;
    L_0x0598:
        r5 = new java.util.HashSet;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5.<init>();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = 0;
        r4 = r2;
    L_0x059f:
        r0 = r20;
        r2 = r0.length;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r4 >= r2) goto L_0x05e0;
    L_0x05a4:
        r7 = r20[r4];
        r2 = 0;
        r3 = r2;
    L_0x05a8:
        r2 = r7.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r3 >= r2) goto L_0x068f;
    L_0x05ae:
        r2 = r7.get(r3);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = "2.5.29.32.0";
        r9 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r8.equals(r9);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 == 0) goto L_0x068a;
    L_0x05c0:
        r8 = r2.getChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x05c4:
        r2 = r8.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x068a;
    L_0x05ca:
        r2 = r8.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r9 = "2.5.29.32.0";
        r10 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r9 = r9.equals(r10);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r9 != 0) goto L_0x05c4;
    L_0x05dc:
        r5.add(r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x05c4;
    L_0x05e0:
        r4 = r5.iterator();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r3 = r15;
    L_0x05e5:
        r2 = r4.hasNext();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r2 == 0) goto L_0x0604;
    L_0x05eb:
        r2 = r4.next();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r5 = r2.getValidPolicy();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r0 = r19;
        r5 = r0.contains(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 != 0) goto L_0x05e5;
    L_0x05fd:
        r0 = r20;
        r3 = org.spongycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(r3, r0, r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        goto L_0x05e5;
    L_0x0604:
        if (r3 == 0) goto L_0x04d9;
    L_0x0606:
        r0 = r24;
        r2 = r0.n;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = r2 + -1;
        r4 = r2;
        r2 = r3;
    L_0x060e:
        if (r4 < 0) goto L_0x04da;
    L_0x0610:
        r7 = r20[r4];
        r3 = 0;
        r5 = r3;
        r3 = r2;
    L_0x0615:
        r2 = r7.size();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r5 >= r2) goto L_0x0632;
    L_0x061b:
        r2 = r7.get(r5);	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r2 = (org.spongycastle.jce.provider.PKIXPolicyNode) r2;	 Catch:{ CertPathReviewerException -> 0x00e2 }
        r8 = r2.hasChildren();	 Catch:{ CertPathReviewerException -> 0x00e2 }
        if (r8 != 0) goto L_0x0637;
    L_0x0627:
        r0 = r20;
        r2 = org.spongycastle.jce.provider.CertPathValidatorUtilities.removePolicyNode(r3, r0, r2);	 Catch:{ CertPathReviewerException -> 0x00e2 }
    L_0x062d:
        r3 = r5 + 1;
        r5 = r3;
        r3 = r2;
        goto L_0x0615;
    L_0x0632:
        r2 = r4 + -1;
        r4 = r2;
        r2 = r3;
        goto L_0x060e;
    L_0x0637:
        r2 = r3;
        goto L_0x062d;
    L_0x0639:
        r2 = r3;
        goto L_0x0593;
    L_0x063c:
        r3 = r15;
        goto L_0x04db;
    L_0x063f:
        r3 = r12;
        goto L_0x046b;
    L_0x0642:
        r2 = r5;
        r5 = r6;
        goto L_0x0440;
    L_0x0646:
        r6 = r14;
        goto L_0x03cc;
    L_0x0649:
        r4 = r13;
        goto L_0x03c7;
    L_0x064c:
        r3 = r12;
        goto L_0x03c3;
    L_0x064f:
        r3 = r12;
        r4 = r13;
        r6 = r14;
        goto L_0x03cc;
    L_0x0654:
        r2 = r3;
        goto L_0x03b6;
    L_0x0657:
        r2 = r7;
        r3 = r12;
        r4 = r13;
        r5 = r14;
        goto L_0x0440;
    L_0x065d:
        r2 = r3;
        goto L_0x024c;
    L_0x0660:
        r2 = r3;
        goto L_0x01c7;
    L_0x0663:
        r2 = r18 + 1;
        r18 = r2;
        goto L_0x0180;
    L_0x0669:
        r2 = r3;
    L_0x066a:
        r3 = r6 + -1;
        r15 = r2;
        r6 = r3;
        goto L_0x022b;
    L_0x0670:
        r5 = r8 + 1;
        r8 = r5;
        goto L_0x02a1;
    L_0x0675:
        r5 = r3;
        goto L_0x03b9;
    L_0x0678:
        r3 = 0;
        goto L_0x04db;
    L_0x067b:
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x0529;
    L_0x0680:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x0520;
    L_0x0685:
        r2 = r4 + -1;
        r4 = r2;
        goto L_0x0575;
    L_0x068a:
        r2 = r3 + 1;
        r3 = r2;
        goto L_0x05a8;
    L_0x068f:
        r2 = r4 + 1;
        r4 = r2;
        goto L_0x059f;
    L_0x0694:
        r9 = r5;
        r7 = r15;
        goto L_0x0277;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.spongycastle.x509.PKIXCertPathReviewer.checkPolicy():void");
    }

    private void checkSignatures() {
        X509Certificate x509Certificate;
        TrustAnchor trustAnchor;
        TrustAnchor trustAnchor2;
        CertPathReviewerException e;
        X500Principal x500Principal;
        X509Certificate trustedCert;
        X500Principal subjectPrincipal;
        boolean[] keyUsage;
        PublicKey publicKey;
        AlgorithmIdentifier algorithmIdentifier;
        int size;
        X509Certificate x509Certificate2;
        X500Principal x500Principal2;
        PublicKey publicKey2;
        int i;
        X509Certificate x509Certificate3;
        ErrorBundle errorBundle;
        byte[] extensionValue;
        AuthorityKeyIdentifier instance;
        GeneralNames authorityCertIssuer;
        GeneralName generalName;
        CRLDistPoint cRLDistPoint;
        ASN1Primitive extensionValue2;
        AuthorityInformationAccess authorityInformationAccess;
        ASN1Primitive extensionValue3;
        Vector cRLDistUrls;
        Vector oCSPUrls;
        Iterator it;
        BasicConstraints instance2;
        boolean[] keyUsage2;
        AlgorithmIdentifier algorithmIdentifier2;
        Throwable th;
        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certPathValidDate", new Object[]{new TrustedInput(this.validDate), new TrustedInput(new Date())}));
        try {
            x509Certificate = (X509Certificate) this.certs.get(this.certs.size() - 1);
            Collection trustAnchors = getTrustAnchors(x509Certificate, this.pkixParams.getTrustAnchors());
            if (trustAnchors.size() > 1) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.conflictingTrustAnchors", new Object[]{new Integer(trustAnchors.size()), new UntrustedInput(x509Certificate.getIssuerX500Principal())}));
                trustAnchor = null;
            } else if (trustAnchors.isEmpty()) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noTrustAnchorFound", new Object[]{new UntrustedInput(x509Certificate.getIssuerX500Principal()), new Integer(this.pkixParams.getTrustAnchors().size())}));
                trustAnchor = null;
            } else {
                trustAnchor2 = (TrustAnchor) trustAnchors.iterator().next();
                try {
                    try {
                        CertPathValidatorUtilities.verifyX509Certificate(x509Certificate, trustAnchor2.getTrustedCert() != null ? trustAnchor2.getTrustedCert().getPublicKey() : trustAnchor2.getCAPublicKey(), this.pkixParams.getSigProvider());
                        trustAnchor = trustAnchor2;
                    } catch (SignatureException e2) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustButInvalidCert"));
                        trustAnchor = trustAnchor2;
                    } catch (Exception e3) {
                        trustAnchor = trustAnchor2;
                    }
                } catch (CertPathReviewerException e4) {
                    e = e4;
                    addError(e.getErrorMessage());
                    trustAnchor = trustAnchor2;
                    if (trustAnchor != null) {
                        x500Principal = null;
                    } else {
                        trustedCert = trustAnchor.getTrustedCert();
                        if (trustedCert == null) {
                            try {
                            } catch (IllegalArgumentException e5) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustDNInvalid", new Object[]{new UntrustedInput(trustAnchor.getCAName())}));
                                subjectPrincipal = null;
                            }
                        }
                        if (trustedCert != null) {
                            keyUsage = trustedCert.getKeyUsage();
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                            x500Principal = subjectPrincipal;
                        }
                        x500Principal = subjectPrincipal;
                    }
                    publicKey = null;
                    x509Certificate = null;
                    if (trustAnchor != null) {
                        x509Certificate = trustAnchor.getTrustedCert();
                        publicKey = x509Certificate != null ? trustAnchor.getCAPublicKey() : x509Certificate.getPublicKey();
                        try {
                            algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                            algorithmIdentifier.getObjectId();
                            algorithmIdentifier.getParameters();
                        } catch (CertPathValidatorException e6) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustPubKeyError"));
                        }
                    }
                    size = this.certs.size() - 1;
                    x509Certificate2 = x509Certificate;
                    x500Principal2 = x500Principal;
                    publicKey2 = publicKey;
                    while (size >= 0) {
                        i = this.n;
                        x509Certificate3 = (X509Certificate) this.certs.get(size);
                        if (publicKey2 != null) {
                            try {
                                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, publicKey2, this.pkixParams.getSigProvider());
                            } catch (GeneralSecurityException e7) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.signatureNotVerified", new Object[]{e7.getMessage(), e7, e7.getClass().getName()}), size);
                            }
                        } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate3)) {
                            errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                            extensionValue = x509Certificate3.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                            if (extensionValue != null) {
                                try {
                                    instance = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
                                    authorityCertIssuer = instance.getAuthorityCertIssuer();
                                    if (authorityCertIssuer != null) {
                                        generalName = authorityCertIssuer.getNames()[0];
                                        if (instance.getAuthorityCertSerialNumber() != null) {
                                            errorBundle.setExtraArguments(new Object[]{new LocaleString(RESOURCE_NAME, "missingIssuer"), " \"", generalName, "\" ", new LocaleString(RESOURCE_NAME, "missingSerial"), " ", instance.getAuthorityCertSerialNumber()});
                                        }
                                    }
                                } catch (IOException e8) {
                                }
                            }
                            addError(errorBundle, size);
                        } else {
                            try {
                                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, x509Certificate3.getPublicKey(), this.pkixParams.getSigProvider());
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
                            } catch (GeneralSecurityException e72) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.signatureNotVerified", new Object[]{e72.getMessage(), e72, e72.getClass().getName()}), size);
                            }
                        }
                        try {
                            x509Certificate3.checkValidity(this.validDate);
                        } catch (CertificateNotYetValidException e9) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certificateNotYetValid", new Object[]{new TrustedInput(x509Certificate3.getNotBefore())}), size);
                        } catch (CertificateExpiredException e10) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certificateExpired", new Object[]{new TrustedInput(x509Certificate3.getNotAfter())}), size);
                        }
                        if (this.pkixParams.isRevocationEnabled()) {
                            cRLDistPoint = null;
                            try {
                                extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, CRL_DIST_POINTS);
                                if (extensionValue2 != null) {
                                    cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                                }
                            } catch (AnnotatedException e11) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPtExtError"), size);
                            }
                            authorityInformationAccess = null;
                            try {
                                extensionValue3 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, AUTH_INFO_ACCESS);
                                if (extensionValue3 != null) {
                                    authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue3);
                                }
                            } catch (AnnotatedException e12) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlAuthInfoAccError"), size);
                            }
                            cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                            oCSPUrls = getOCSPUrls(authorityInformationAccess);
                            it = cRLDistUrls.iterator();
                            while (it.hasNext()) {
                                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(it.next())}), size);
                            }
                            it = oCSPUrls.iterator();
                            while (it.hasNext()) {
                                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(it.next())}), size);
                            }
                            try {
                                checkRevocation(this.pkixParams, x509Certificate3, this.validDate, x509Certificate2, publicKey2, cRLDistUrls, oCSPUrls, size);
                            } catch (CertPathReviewerException e13) {
                                addError(e13.getErrorMessage(), size);
                            }
                        }
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate3.getIssuerX500Principal().getName()}), size);
                        if (i - size != this.n) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                            try {
                                instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate3, BASIC_CONSTRAINTS));
                                if (instance2 == null) {
                                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), size);
                                } else if (!instance2.isCA()) {
                                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                                }
                            } catch (AnnotatedException e14) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.errorProcesingBC"), size);
                            }
                            keyUsage2 = x509Certificate3.getKeyUsage();
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), size);
                        }
                        subjectPrincipal = x509Certificate3.getSubjectX500Principal();
                        try {
                            publicKey2 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, size);
                            try {
                                algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
                                algorithmIdentifier2.getObjectId();
                                algorithmIdentifier2.getParameters();
                            } catch (CertPathValidatorException e15) {
                            }
                        } catch (CertPathValidatorException e16) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.pubKeyError"), size);
                            size--;
                            x509Certificate2 = x509Certificate3;
                            x500Principal2 = subjectPrincipal;
                        }
                        size--;
                        x509Certificate2 = x509Certificate3;
                        x500Principal2 = subjectPrincipal;
                    }
                    this.trustAnchor = trustAnchor;
                    this.subjectPublicKey = publicKey2;
                } catch (Throwable th2) {
                    th = th2;
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.unknown", new Object[]{new UntrustedInput(th.getMessage()), new UntrustedInput(th)}));
                    trustAnchor = trustAnchor2;
                    if (trustAnchor != null) {
                        trustedCert = trustAnchor.getTrustedCert();
                        subjectPrincipal = trustedCert == null ? CertPathValidatorUtilities.getSubjectPrincipal(trustedCert) : new X500Principal(trustAnchor.getCAName());
                        if (trustedCert != null) {
                            keyUsage = trustedCert.getKeyUsage();
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                            x500Principal = subjectPrincipal;
                        }
                        x500Principal = subjectPrincipal;
                    } else {
                        x500Principal = null;
                    }
                    publicKey = null;
                    x509Certificate = null;
                    if (trustAnchor != null) {
                        x509Certificate = trustAnchor.getTrustedCert();
                        if (x509Certificate != null) {
                        }
                        algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                        algorithmIdentifier.getObjectId();
                        algorithmIdentifier.getParameters();
                    }
                    size = this.certs.size() - 1;
                    x509Certificate2 = x509Certificate;
                    x500Principal2 = x500Principal;
                    publicKey2 = publicKey;
                    while (size >= 0) {
                        i = this.n;
                        x509Certificate3 = (X509Certificate) this.certs.get(size);
                        if (publicKey2 != null) {
                            CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, publicKey2, this.pkixParams.getSigProvider());
                        } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate3)) {
                            CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, x509Certificate3.getPublicKey(), this.pkixParams.getSigProvider());
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
                        } else {
                            errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                            extensionValue = x509Certificate3.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                            if (extensionValue != null) {
                                instance = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
                                authorityCertIssuer = instance.getAuthorityCertIssuer();
                                if (authorityCertIssuer != null) {
                                    generalName = authorityCertIssuer.getNames()[0];
                                    if (instance.getAuthorityCertSerialNumber() != null) {
                                        errorBundle.setExtraArguments(new Object[]{new LocaleString(RESOURCE_NAME, "missingIssuer"), " \"", generalName, "\" ", new LocaleString(RESOURCE_NAME, "missingSerial"), " ", instance.getAuthorityCertSerialNumber()});
                                    }
                                }
                            }
                            addError(errorBundle, size);
                        }
                        x509Certificate3.checkValidity(this.validDate);
                        if (this.pkixParams.isRevocationEnabled()) {
                            cRLDistPoint = null;
                            extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, CRL_DIST_POINTS);
                            if (extensionValue2 != null) {
                                cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                            }
                            authorityInformationAccess = null;
                            extensionValue3 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, AUTH_INFO_ACCESS);
                            if (extensionValue3 != null) {
                                authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue3);
                            }
                            cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                            oCSPUrls = getOCSPUrls(authorityInformationAccess);
                            it = cRLDistUrls.iterator();
                            while (it.hasNext()) {
                                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(it.next())}), size);
                            }
                            it = oCSPUrls.iterator();
                            while (it.hasNext()) {
                                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(it.next())}), size);
                            }
                            checkRevocation(this.pkixParams, x509Certificate3, this.validDate, x509Certificate2, publicKey2, cRLDistUrls, oCSPUrls, size);
                        }
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate3.getIssuerX500Principal().getName()}), size);
                        if (i - size != this.n) {
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                            instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate3, BASIC_CONSTRAINTS));
                            if (instance2 == null) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), size);
                            } else if (instance2.isCA()) {
                                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                            }
                            keyUsage2 = x509Certificate3.getKeyUsage();
                            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), size);
                        }
                        subjectPrincipal = x509Certificate3.getSubjectX500Principal();
                        publicKey2 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, size);
                        algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
                        algorithmIdentifier2.getObjectId();
                        algorithmIdentifier2.getParameters();
                        size--;
                        x509Certificate2 = x509Certificate3;
                        x500Principal2 = subjectPrincipal;
                    }
                    this.trustAnchor = trustAnchor;
                    this.subjectPublicKey = publicKey2;
                }
            }
        } catch (CertPathReviewerException e17) {
            e13 = e17;
            trustAnchor2 = null;
            addError(e13.getErrorMessage());
            trustAnchor = trustAnchor2;
            if (trustAnchor != null) {
                x500Principal = null;
            } else {
                trustedCert = trustAnchor.getTrustedCert();
                if (trustedCert == null) {
                }
                if (trustedCert != null) {
                    keyUsage = trustedCert.getKeyUsage();
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                    x500Principal = subjectPrincipal;
                }
                x500Principal = subjectPrincipal;
            }
            publicKey = null;
            x509Certificate = null;
            if (trustAnchor != null) {
                x509Certificate = trustAnchor.getTrustedCert();
                if (x509Certificate != null) {
                }
                algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                algorithmIdentifier.getObjectId();
                algorithmIdentifier.getParameters();
            }
            size = this.certs.size() - 1;
            x509Certificate2 = x509Certificate;
            x500Principal2 = x500Principal;
            publicKey2 = publicKey;
            while (size >= 0) {
                i = this.n;
                x509Certificate3 = (X509Certificate) this.certs.get(size);
                if (publicKey2 != null) {
                    CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, publicKey2, this.pkixParams.getSigProvider());
                } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate3)) {
                    errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                    extensionValue = x509Certificate3.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                    if (extensionValue != null) {
                        instance = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
                        authorityCertIssuer = instance.getAuthorityCertIssuer();
                        if (authorityCertIssuer != null) {
                            generalName = authorityCertIssuer.getNames()[0];
                            if (instance.getAuthorityCertSerialNumber() != null) {
                                errorBundle.setExtraArguments(new Object[]{new LocaleString(RESOURCE_NAME, "missingIssuer"), " \"", generalName, "\" ", new LocaleString(RESOURCE_NAME, "missingSerial"), " ", instance.getAuthorityCertSerialNumber()});
                            }
                        }
                    }
                    addError(errorBundle, size);
                } else {
                    CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, x509Certificate3.getPublicKey(), this.pkixParams.getSigProvider());
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
                }
                x509Certificate3.checkValidity(this.validDate);
                if (this.pkixParams.isRevocationEnabled()) {
                    cRLDistPoint = null;
                    extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, CRL_DIST_POINTS);
                    if (extensionValue2 != null) {
                        cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                    }
                    authorityInformationAccess = null;
                    extensionValue3 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, AUTH_INFO_ACCESS);
                    if (extensionValue3 != null) {
                        authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue3);
                    }
                    cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                    oCSPUrls = getOCSPUrls(authorityInformationAccess);
                    it = cRLDistUrls.iterator();
                    while (it.hasNext()) {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(it.next())}), size);
                    }
                    it = oCSPUrls.iterator();
                    while (it.hasNext()) {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(it.next())}), size);
                    }
                    checkRevocation(this.pkixParams, x509Certificate3, this.validDate, x509Certificate2, publicKey2, cRLDistUrls, oCSPUrls, size);
                }
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate3.getIssuerX500Principal().getName()}), size);
                if (i - size != this.n) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                    instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate3, BASIC_CONSTRAINTS));
                    if (instance2 == null) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), size);
                    } else if (instance2.isCA()) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                    }
                    keyUsage2 = x509Certificate3.getKeyUsage();
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), size);
                }
                subjectPrincipal = x509Certificate3.getSubjectX500Principal();
                publicKey2 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, size);
                algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
                algorithmIdentifier2.getObjectId();
                algorithmIdentifier2.getParameters();
                size--;
                x509Certificate2 = x509Certificate3;
                x500Principal2 = subjectPrincipal;
            }
            this.trustAnchor = trustAnchor;
            this.subjectPublicKey = publicKey2;
        } catch (Throwable th3) {
            th = th3;
            trustAnchor2 = null;
            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.unknown", new Object[]{new UntrustedInput(th.getMessage()), new UntrustedInput(th)}));
            trustAnchor = trustAnchor2;
            if (trustAnchor != null) {
                trustedCert = trustAnchor.getTrustedCert();
                if (trustedCert == null) {
                }
                if (trustedCert != null) {
                    keyUsage = trustedCert.getKeyUsage();
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                    x500Principal = subjectPrincipal;
                }
                x500Principal = subjectPrincipal;
            } else {
                x500Principal = null;
            }
            publicKey = null;
            x509Certificate = null;
            if (trustAnchor != null) {
                x509Certificate = trustAnchor.getTrustedCert();
                if (x509Certificate != null) {
                }
                algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
                algorithmIdentifier.getObjectId();
                algorithmIdentifier.getParameters();
            }
            size = this.certs.size() - 1;
            x509Certificate2 = x509Certificate;
            x500Principal2 = x500Principal;
            publicKey2 = publicKey;
            while (size >= 0) {
                i = this.n;
                x509Certificate3 = (X509Certificate) this.certs.get(size);
                if (publicKey2 != null) {
                    CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, publicKey2, this.pkixParams.getSigProvider());
                } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate3)) {
                    CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, x509Certificate3.getPublicKey(), this.pkixParams.getSigProvider());
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
                } else {
                    errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                    extensionValue = x509Certificate3.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                    if (extensionValue != null) {
                        instance = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
                        authorityCertIssuer = instance.getAuthorityCertIssuer();
                        if (authorityCertIssuer != null) {
                            generalName = authorityCertIssuer.getNames()[0];
                            if (instance.getAuthorityCertSerialNumber() != null) {
                                errorBundle.setExtraArguments(new Object[]{new LocaleString(RESOURCE_NAME, "missingIssuer"), " \"", generalName, "\" ", new LocaleString(RESOURCE_NAME, "missingSerial"), " ", instance.getAuthorityCertSerialNumber()});
                            }
                        }
                    }
                    addError(errorBundle, size);
                }
                x509Certificate3.checkValidity(this.validDate);
                if (this.pkixParams.isRevocationEnabled()) {
                    cRLDistPoint = null;
                    extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, CRL_DIST_POINTS);
                    if (extensionValue2 != null) {
                        cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                    }
                    authorityInformationAccess = null;
                    extensionValue3 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, AUTH_INFO_ACCESS);
                    if (extensionValue3 != null) {
                        authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue3);
                    }
                    cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                    oCSPUrls = getOCSPUrls(authorityInformationAccess);
                    it = cRLDistUrls.iterator();
                    while (it.hasNext()) {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(it.next())}), size);
                    }
                    it = oCSPUrls.iterator();
                    while (it.hasNext()) {
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(it.next())}), size);
                    }
                    checkRevocation(this.pkixParams, x509Certificate3, this.validDate, x509Certificate2, publicKey2, cRLDistUrls, oCSPUrls, size);
                }
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate3.getIssuerX500Principal().getName()}), size);
                if (i - size != this.n) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                    instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate3, BASIC_CONSTRAINTS));
                    if (instance2 == null) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), size);
                    } else if (instance2.isCA()) {
                        addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                    }
                    keyUsage2 = x509Certificate3.getKeyUsage();
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), size);
                }
                subjectPrincipal = x509Certificate3.getSubjectX500Principal();
                publicKey2 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, size);
                algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
                algorithmIdentifier2.getObjectId();
                algorithmIdentifier2.getParameters();
                size--;
                x509Certificate2 = x509Certificate3;
                x500Principal2 = subjectPrincipal;
            }
            this.trustAnchor = trustAnchor;
            this.subjectPublicKey = publicKey2;
        }
        if (trustAnchor != null) {
            trustedCert = trustAnchor.getTrustedCert();
            if (trustedCert == null) {
            }
            if (trustedCert != null) {
                keyUsage = trustedCert.getKeyUsage();
                if (!(keyUsage == null || keyUsage[5])) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustKeyUsage"));
                    x500Principal = subjectPrincipal;
                }
            }
            x500Principal = subjectPrincipal;
        } else {
            x500Principal = null;
        }
        publicKey = null;
        x509Certificate = null;
        if (trustAnchor != null) {
            x509Certificate = trustAnchor.getTrustedCert();
            if (x509Certificate != null) {
            }
            algorithmIdentifier = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey);
            algorithmIdentifier.getObjectId();
            algorithmIdentifier.getParameters();
        }
        size = this.certs.size() - 1;
        x509Certificate2 = x509Certificate;
        x500Principal2 = x500Principal;
        publicKey2 = publicKey;
        while (size >= 0) {
            i = this.n;
            x509Certificate3 = (X509Certificate) this.certs.get(size);
            if (publicKey2 != null) {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, publicKey2, this.pkixParams.getSigProvider());
            } else if (CertPathValidatorUtilities.isSelfIssued(x509Certificate3)) {
                CertPathValidatorUtilities.verifyX509Certificate(x509Certificate3, x509Certificate3.getPublicKey(), this.pkixParams.getSigProvider());
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.rootKeyIsValidButNotATrustAnchor"), size);
            } else {
                errorBundle = new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.NoIssuerPublicKey");
                extensionValue = x509Certificate3.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
                if (extensionValue != null) {
                    instance = AuthorityKeyIdentifier.getInstance(X509ExtensionUtil.fromExtensionValue(extensionValue));
                    authorityCertIssuer = instance.getAuthorityCertIssuer();
                    if (authorityCertIssuer != null) {
                        generalName = authorityCertIssuer.getNames()[0];
                        if (instance.getAuthorityCertSerialNumber() != null) {
                            errorBundle.setExtraArguments(new Object[]{new LocaleString(RESOURCE_NAME, "missingIssuer"), " \"", generalName, "\" ", new LocaleString(RESOURCE_NAME, "missingSerial"), " ", instance.getAuthorityCertSerialNumber()});
                        }
                    }
                }
                addError(errorBundle, size);
            }
            x509Certificate3.checkValidity(this.validDate);
            if (this.pkixParams.isRevocationEnabled()) {
                cRLDistPoint = null;
                extensionValue2 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, CRL_DIST_POINTS);
                if (extensionValue2 != null) {
                    cRLDistPoint = CRLDistPoint.getInstance(extensionValue2);
                }
                authorityInformationAccess = null;
                extensionValue3 = CertPathValidatorUtilities.getExtensionValue(x509Certificate3, AUTH_INFO_ACCESS);
                if (extensionValue3 != null) {
                    authorityInformationAccess = AuthorityInformationAccess.getInstance(extensionValue3);
                }
                cRLDistUrls = getCRLDistUrls(cRLDistPoint);
                oCSPUrls = getOCSPUrls(authorityInformationAccess);
                it = cRLDistUrls.iterator();
                while (it.hasNext()) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlDistPoint", new Object[]{new UntrustedUrlInput(it.next())}), size);
                }
                it = oCSPUrls.iterator();
                while (it.hasNext()) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.ocspLocation", new Object[]{new UntrustedUrlInput(it.next())}), size);
                }
                checkRevocation(this.pkixParams, x509Certificate3, this.validDate, x509Certificate2, publicKey2, cRLDistUrls, oCSPUrls, size);
            }
            if (!(x500Principal2 == null || x509Certificate3.getIssuerX500Principal().equals(x500Principal2))) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certWrongIssuer", new Object[]{x500Principal2.getName(), x509Certificate3.getIssuerX500Principal().getName()}), size);
            }
            if (i - size != this.n) {
                if (x509Certificate3 != null && x509Certificate3.getVersion() == 1) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                }
                instance2 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate3, BASIC_CONSTRAINTS));
                if (instance2 == null) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBasicConstraints"), size);
                } else if (instance2.isCA()) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCACert"), size);
                }
                keyUsage2 = x509Certificate3.getKeyUsage();
                if (!(keyUsage2 == null || keyUsage2[5])) {
                    addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCertSign"), size);
                }
            }
            subjectPrincipal = x509Certificate3.getSubjectX500Principal();
            publicKey2 = CertPathValidatorUtilities.getNextWorkingKey(this.certs, size);
            algorithmIdentifier2 = CertPathValidatorUtilities.getAlgorithmIdentifier(publicKey2);
            algorithmIdentifier2.getObjectId();
            algorithmIdentifier2.getParameters();
            size--;
            x509Certificate2 = x509Certificate3;
            x500Principal2 = subjectPrincipal;
        }
        this.trustAnchor = trustAnchor;
        this.subjectPublicKey = publicKey2;
    }

    private X509CRL getCRL(String str) throws CertPathReviewerException {
        try {
            URL url = new URL(str);
            if (!url.getProtocol().equals("http") && !url.getProtocol().equals("https")) {
                return null;
            }
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE) {
                return (X509CRL) CertificateFactory.getInstance("X.509", "SC").generateCRL(httpURLConnection.getInputStream());
            }
            throw new Exception(httpURLConnection.getResponseMessage());
        } catch (Exception e) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.loadCrlDistPointError", new Object[]{new UntrustedInput(str), e.getMessage(), e, e.getClass().getName()}));
        }
    }

    private boolean processQcStatements(X509Certificate x509Certificate, int i) {
        try {
            ASN1Sequence aSN1Sequence = (ASN1Sequence) CertPathValidatorUtilities.getExtensionValue(x509Certificate, QC_STATEMENT);
            Object obj = null;
            for (int i2 = 0; i2 < aSN1Sequence.size(); i2++) {
                QCStatement instance = QCStatement.getInstance(aSN1Sequence.getObjectAt(i2));
                if (QCStatement.id_etsi_qcs_QcCompliance.equals(instance.getStatementId())) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcEuCompliance"), i);
                } else if (QCStatement.id_qcs_pkixQCSyntax_v1.equals(instance.getStatementId())) {
                    continue;
                } else if (QCStatement.id_etsi_qcs_QcSSCD.equals(instance.getStatementId())) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcSSCD"), i);
                } else if (QCStatement.id_etsi_qcs_LimiteValue.equals(instance.getStatementId())) {
                    MonetaryValue instance2 = MonetaryValue.getInstance(instance.getStatementInfo());
                    instance2.getCurrency();
                    double doubleValue = instance2.getAmount().doubleValue() * Math.pow(10.0d, instance2.getExponent().doubleValue());
                    addNotification(instance2.getCurrency().isAlphabetic() ? new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcLimitValueAlpha", new Object[]{instance2.getCurrency().getAlphabetic(), new TrustedInput(new Double(doubleValue)), instance2}) : new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcLimitValueNum", new Object[]{new Integer(instance2.getCurrency().getNumeric()), new TrustedInput(new Double(doubleValue)), instance2}), i);
                } else {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcUnknownStatement", new Object[]{instance.getStatementId(), new UntrustedInput(instance)}), i);
                    obj = 1;
                }
            }
            return obj == null;
        } catch (AnnotatedException e) {
            addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.QcStatementExtError"), i);
            return false;
        }
    }

    protected void addError(ErrorBundle errorBundle) {
        this.errors[0].add(errorBundle);
    }

    protected void addError(ErrorBundle errorBundle, int i) {
        if (i < -1 || i >= this.n) {
            throw new IndexOutOfBoundsException();
        }
        this.errors[i + 1].add(errorBundle);
    }

    protected void addNotification(ErrorBundle errorBundle) {
        this.notifications[0].add(errorBundle);
    }

    protected void addNotification(ErrorBundle errorBundle, int i) {
        if (i < -1 || i >= this.n) {
            throw new IndexOutOfBoundsException();
        }
        this.notifications[i + 1].add(errorBundle);
    }

    protected void checkCRLs(PKIXParameters pKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, Vector vector, int i) throws CertPathReviewerException {
        Object obj;
        X509CRLStoreSelector x509CRLStoreSelector = new X509CRLStoreSelector();
        try {
            Iterator it;
            X509CRL x509crl;
            Object obj2;
            X509Extension crl;
            String str;
            x509CRLStoreSelector.addIssuerName(CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).getEncoded());
            x509CRLStoreSelector.setCertificateChecking(x509Certificate);
            try {
                Collection findCRLs = CRL_UTIL.findCRLs(x509CRLStoreSelector, pKIXParameters);
                it = findCRLs.iterator();
                if (findCRLs.isEmpty()) {
                    List arrayList = new ArrayList();
                    for (X509CRL issuerX500Principal : CRL_UTIL.findCRLs(new X509CRLStoreSelector(), pKIXParameters)) {
                        arrayList.add(issuerX500Principal.getIssuerX500Principal());
                    }
                    int size = arrayList.size();
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCrlInCertstore", new Object[]{new UntrustedInput(x509CRLStoreSelector.getIssuerNames()), new UntrustedInput(arrayList), new Integer(size)}), i);
                }
            } catch (AnnotatedException e) {
                addError(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlExtractionError", new Object[]{e.getCause().getMessage(), e.getCause(), e.getCause().getClass().getName()}), i);
                it = new ArrayList().iterator();
            }
            X509CRL issuerX500Principal2 = null;
            while (it.hasNext()) {
                issuerX500Principal2 = (X509CRL) it.next();
                if (issuerX500Principal2.getNextUpdate() == null || pKIXParameters.getDate().before(issuerX500Principal2.getNextUpdate())) {
                    addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.localValidCRL", new Object[]{new TrustedInput(issuerX500Principal2.getThisUpdate()), new TrustedInput(issuerX500Principal2.getNextUpdate())}), i);
                    x509crl = issuerX500Principal2;
                    obj2 = 1;
                    break;
                }
                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.localInvalidCRL", new Object[]{new TrustedInput(issuerX500Principal2.getThisUpdate()), new TrustedInput(issuerX500Principal2.getNextUpdate())}), i);
            }
            x509crl = issuerX500Principal2;
            obj2 = null;
            Object obj3;
            if (obj2 == null) {
                Iterator it2 = vector.iterator();
                obj = obj2;
                while (it2.hasNext()) {
                    try {
                        crl = getCRL((String) it2.next());
                        if (crl == null) {
                            continue;
                        } else if (!x509Certificate.getIssuerX500Principal().equals(crl.getIssuerX500Principal())) {
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineCRLWrongCA", new Object[]{new UntrustedInput(crl.getIssuerX500Principal().getName()), new UntrustedInput(x509Certificate.getIssuerX500Principal().getName()), new UntrustedUrlInput(str)}), i);
                        } else if (crl.getNextUpdate() == null || this.pkixParams.getDate().before(crl.getNextUpdate())) {
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineValidCRL", new Object[]{new TrustedInput(crl.getThisUpdate()), new TrustedInput(crl.getNextUpdate()), new UntrustedUrlInput(str)}), i);
                            obj = 1;
                            break;
                        } else {
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.onlineInvalidCRL", new Object[]{new TrustedInput(crl.getThisUpdate()), new TrustedInput(crl.getNextUpdate()), new UntrustedUrlInput(str)}), i);
                        }
                    } catch (CertPathReviewerException e2) {
                        CertPathReviewerException certPathReviewerException = e2;
                        obj2 = obj;
                        addNotification(certPathReviewerException.getErrorMessage(), i);
                        obj = obj2;
                    }
                }
                obj3 = x509crl;
            } else {
                obj = obj2;
                obj3 = x509crl;
            }
            if (crl != null) {
                if (x509Certificate2 != null) {
                    boolean[] keyUsage = x509Certificate2.getKeyUsage();
                    if (keyUsage != null && (keyUsage.length < 7 || !keyUsage[6])) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noCrlSigningPermited"));
                    }
                }
                if (publicKey != null) {
                    try {
                        crl.verify(publicKey, "SC");
                        X509CRLEntry revokedCertificate = crl.getRevokedCertificate(x509Certificate.getSerialNumber());
                        if (revokedCertificate != null) {
                            str = null;
                            if (revokedCertificate.hasExtensions()) {
                                try {
                                    DEREnumerated instance = DEREnumerated.getInstance(CertPathValidatorUtilities.getExtensionValue(revokedCertificate, X509Extensions.ReasonCode.getId()));
                                    if (instance != null) {
                                        str = crlReasons[instance.getValue().intValue()];
                                    }
                                } catch (Throwable e3) {
                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlReasonExtError"), e3);
                                }
                            }
                            if (str == null) {
                                str = crlReasons[7];
                            }
                            LocaleString localeString = new LocaleString(RESOURCE_NAME, str);
                            if (date.before(revokedCertificate.getRevocationDate())) {
                                addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.revokedAfterValidation", new Object[]{new TrustedInput(revokedCertificate.getRevocationDate()), localeString}), i);
                            } else {
                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.certRevoked", new Object[]{new TrustedInput(revokedCertificate.getRevocationDate()), localeString}));
                            }
                        }
                        addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.notRevoked"), i);
                        if (crl.getNextUpdate() != null && crl.getNextUpdate().before(this.pkixParams.getDate())) {
                            addNotification(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlUpdateAvailable", new Object[]{new TrustedInput(crl.getNextUpdate())}), i);
                        }
                        try {
                            ASN1Primitive extensionValue = CertPathValidatorUtilities.getExtensionValue(crl, ISSUING_DISTRIBUTION_POINT);
                            try {
                                ASN1Primitive extensionValue2 = CertPathValidatorUtilities.getExtensionValue(crl, DELTA_CRL_INDICATOR);
                                if (extensionValue2 != null) {
                                    X509CRLStoreSelector x509CRLStoreSelector2 = new X509CRLStoreSelector();
                                    try {
                                        x509CRLStoreSelector2.addIssuerName(CertPathValidatorUtilities.getIssuerPrincipal(crl).getEncoded());
                                        x509CRLStoreSelector2.setMinCRLNumber(((DERInteger) extensionValue2).getPositiveValue());
                                        try {
                                            x509CRLStoreSelector2.setMaxCRLNumber(((DERInteger) CertPathValidatorUtilities.getExtensionValue(crl, CRL_NUMBER)).getPositiveValue().subtract(BigInteger.valueOf(1)));
                                            try {
                                                for (X509CRL issuerX500Principal22 : CRL_UTIL.findCRLs(x509CRLStoreSelector2, pKIXParameters)) {
                                                    try {
                                                        extensionValue2 = CertPathValidatorUtilities.getExtensionValue(issuerX500Principal22, ISSUING_DISTRIBUTION_POINT);
                                                        if (extensionValue == null) {
                                                            if (extensionValue2 == null) {
                                                                obj2 = 1;
                                                                break;
                                                            }
                                                        } else if (extensionValue.equals(extensionValue2)) {
                                                            obj2 = 1;
                                                            break;
                                                        }
                                                    } catch (Throwable e32) {
                                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.distrPtExtError"), e32);
                                                    }
                                                }
                                                obj2 = null;
                                                if (obj2 == null) {
                                                    throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noBaseCRL"));
                                                }
                                            } catch (Throwable e322) {
                                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlExtractionError"), e322);
                                            }
                                        } catch (Throwable e3222) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlNbrExtError"), e3222);
                                        }
                                    } catch (Throwable e32222) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlIssuerException"), e32222);
                                    }
                                }
                                if (extensionValue != null) {
                                    IssuingDistributionPoint instance2 = IssuingDistributionPoint.getInstance(extensionValue);
                                    try {
                                        BasicConstraints instance3 = BasicConstraints.getInstance(CertPathValidatorUtilities.getExtensionValue(x509Certificate, BASIC_CONSTRAINTS));
                                        if (instance2.onlyContainsUserCerts() && instance3 != null && instance3.isCA()) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyUserCert"));
                                        } else if (instance2.onlyContainsCACerts() && (instance3 == null || !instance3.isCA())) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyCaCert"));
                                        } else if (instance2.onlyContainsAttributeCerts()) {
                                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlOnlyAttrCert"));
                                        }
                                    } catch (Throwable e322222) {
                                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlBCExtError"), e322222);
                                    }
                                }
                            } catch (AnnotatedException e4) {
                                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.deltaCrlExtError"));
                            }
                        } catch (AnnotatedException e5) {
                            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.distrPtExtError"));
                        }
                    } catch (Throwable e3222222) {
                        throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlVerifyFailed"), e3222222);
                    }
                }
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlNoIssuerPublicKey"));
            }
            if (obj == null) {
                throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.noValidCrlFound"));
            }
        } catch (Throwable e32222222) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.crlIssuerException"), e32222222);
        }
    }

    protected void checkRevocation(PKIXParameters pKIXParameters, X509Certificate x509Certificate, Date date, X509Certificate x509Certificate2, PublicKey publicKey, Vector vector, Vector vector2, int i) throws CertPathReviewerException {
        checkCRLs(pKIXParameters, x509Certificate, date, x509Certificate2, publicKey, vector, i);
    }

    protected void doChecks() {
        if (!this.initialized) {
            throw new IllegalStateException("Object not initialized. Call init() first.");
        } else if (this.notifications == null) {
            this.notifications = new List[(this.n + 1)];
            this.errors = new List[(this.n + 1)];
            for (int i = 0; i < this.notifications.length; i++) {
                this.notifications[i] = new ArrayList();
                this.errors[i] = new ArrayList();
            }
            checkSignatures();
            checkNameConstraints();
            checkPathLength();
            checkPolicy();
            checkCriticalExtensions();
        }
    }

    protected Vector getCRLDistUrls(CRLDistPoint cRLDistPoint) {
        Vector vector = new Vector();
        if (cRLDistPoint != null) {
            DistributionPoint[] distributionPoints = cRLDistPoint.getDistributionPoints();
            for (DistributionPoint distributionPoint : distributionPoints) {
                DistributionPointName distributionPoint2 = distributionPoint.getDistributionPoint();
                if (distributionPoint2.getType() == 0) {
                    GeneralName[] names = GeneralNames.getInstance(distributionPoint2.getName()).getNames();
                    for (int i = 0; i < names.length; i++) {
                        if (names[i].getTagNo() == 6) {
                            vector.add(((DERIA5String) names[i].getName()).getString());
                        }
                    }
                }
            }
        }
        return vector;
    }

    public CertPath getCertPath() {
        return this.certPath;
    }

    public int getCertPathSize() {
        return this.n;
    }

    public List getErrors(int i) {
        doChecks();
        return this.errors[i + 1];
    }

    public List[] getErrors() {
        doChecks();
        return this.errors;
    }

    public List getNotifications(int i) {
        doChecks();
        return this.notifications[i + 1];
    }

    public List[] getNotifications() {
        doChecks();
        return this.notifications;
    }

    protected Vector getOCSPUrls(AuthorityInformationAccess authorityInformationAccess) {
        Vector vector = new Vector();
        if (authorityInformationAccess != null) {
            AccessDescription[] accessDescriptions = authorityInformationAccess.getAccessDescriptions();
            for (int i = 0; i < accessDescriptions.length; i++) {
                if (accessDescriptions[i].getAccessMethod().equals(AccessDescription.id_ad_ocsp)) {
                    GeneralName accessLocation = accessDescriptions[i].getAccessLocation();
                    if (accessLocation.getTagNo() == 6) {
                        vector.add(((DERIA5String) accessLocation.getName()).getString());
                    }
                }
            }
        }
        return vector;
    }

    public PolicyNode getPolicyTree() {
        doChecks();
        return this.policyTree;
    }

    public PublicKey getSubjectPublicKey() {
        doChecks();
        return this.subjectPublicKey;
    }

    public TrustAnchor getTrustAnchor() {
        doChecks();
        return this.trustAnchor;
    }

    protected Collection getTrustAnchors(X509Certificate x509Certificate, Set set) throws CertPathReviewerException {
        Collection arrayList = new ArrayList();
        X509CertSelector x509CertSelector = new X509CertSelector();
        try {
            x509CertSelector.setSubject(CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).getEncoded());
            byte[] extensionValue = x509Certificate.getExtensionValue(X509Extensions.AuthorityKeyIdentifier.getId());
            if (extensionValue != null) {
                AuthorityKeyIdentifier instance = AuthorityKeyIdentifier.getInstance(ASN1Primitive.fromByteArray(((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets()));
                x509CertSelector.setSerialNumber(instance.getAuthorityCertSerialNumber());
                extensionValue = instance.getKeyIdentifier();
                if (extensionValue != null) {
                    x509CertSelector.setSubjectKeyIdentifier(new DEROctetString(extensionValue).getEncoded());
                }
            }
            for (TrustAnchor trustAnchor : set) {
                if (trustAnchor.getTrustedCert() != null) {
                    if (x509CertSelector.match(trustAnchor.getTrustedCert())) {
                        arrayList.add(trustAnchor);
                    }
                } else if (!(trustAnchor.getCAName() == null || trustAnchor.getCAPublicKey() == null || !CertPathValidatorUtilities.getEncodedIssuerPrincipal(x509Certificate).equals(new X500Principal(trustAnchor.getCAName())))) {
                    arrayList.add(trustAnchor);
                }
            }
            return arrayList;
        } catch (IOException e) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.trustAnchorIssuerError"));
        }
    }

    public void init(CertPath certPath, PKIXParameters pKIXParameters) throws CertPathReviewerException {
        if (this.initialized) {
            throw new IllegalStateException("object is already initialized!");
        }
        this.initialized = true;
        if (certPath == null) {
            throw new NullPointerException("certPath was null");
        }
        this.certPath = certPath;
        this.certs = certPath.getCertificates();
        this.n = this.certs.size();
        if (this.certs.isEmpty()) {
            throw new CertPathReviewerException(new ErrorBundle(RESOURCE_NAME, "CertPathReviewer.emptyCertPath"));
        }
        this.pkixParams = (PKIXParameters) pKIXParameters.clone();
        this.validDate = CertPathValidatorUtilities.getValidDate(this.pkixParams);
        this.notifications = null;
        this.errors = null;
        this.trustAnchor = null;
        this.subjectPublicKey = null;
        this.policyTree = null;
    }

    public boolean isValidCertPath() {
        doChecks();
        for (List isEmpty : this.errors) {
            if (!isEmpty.isEmpty()) {
                return false;
            }
        }
        return true;
    }
}
