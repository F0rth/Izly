package org.spongycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.jce.X509LDAPCertStoreParameters;
import org.spongycastle.util.Selector;
import org.spongycastle.util.StoreException;
import org.spongycastle.x509.X509CertPairStoreSelector;
import org.spongycastle.x509.X509CertStoreSelector;
import org.spongycastle.x509.X509CertificatePair;
import org.spongycastle.x509.X509StoreParameters;
import org.spongycastle.x509.X509StoreSpi;
import org.spongycastle.x509.util.LDAPStoreHelper;

public class X509StoreLDAPCerts extends X509StoreSpi {
    private LDAPStoreHelper helper;

    private Collection getCertificatesFromCrossCertificatePairs(X509CertStoreSelector x509CertStoreSelector) throws StoreException {
        Collection hashSet = new HashSet();
        X509CertPairStoreSelector x509CertPairStoreSelector = new X509CertPairStoreSelector();
        x509CertPairStoreSelector.setForwardSelector(x509CertStoreSelector);
        x509CertPairStoreSelector.setReverseSelector(new X509CertStoreSelector());
        Set<X509CertificatePair> hashSet2 = new HashSet(this.helper.getCrossCertificatePairs(x509CertPairStoreSelector));
        Collection hashSet3 = new HashSet();
        Collection hashSet4 = new HashSet();
        for (X509CertificatePair x509CertificatePair : hashSet2) {
            if (x509CertificatePair.getForward() != null) {
                hashSet3.add(x509CertificatePair.getForward());
            }
            if (x509CertificatePair.getReverse() != null) {
                hashSet4.add(x509CertificatePair.getReverse());
            }
        }
        hashSet.addAll(hashSet3);
        hashSet.addAll(hashSet4);
        return hashSet;
    }

    public Collection engineGetMatches(Selector selector) throws StoreException {
        if (!(selector instanceof X509CertStoreSelector)) {
            return Collections.EMPTY_SET;
        }
        X509CertStoreSelector x509CertStoreSelector = (X509CertStoreSelector) selector;
        Collection hashSet = new HashSet();
        if (x509CertStoreSelector.getBasicConstraints() > 0) {
            hashSet.addAll(this.helper.getCACertificates(x509CertStoreSelector));
            hashSet.addAll(getCertificatesFromCrossCertificatePairs(x509CertStoreSelector));
            return hashSet;
        } else if (x509CertStoreSelector.getBasicConstraints() == -2) {
            hashSet.addAll(this.helper.getUserCertificates(x509CertStoreSelector));
            return hashSet;
        } else {
            hashSet.addAll(this.helper.getUserCertificates(x509CertStoreSelector));
            hashSet.addAll(this.helper.getCACertificates(x509CertStoreSelector));
            hashSet.addAll(getCertificatesFromCrossCertificatePairs(x509CertStoreSelector));
            return hashSet;
        }
    }

    public void engineInit(X509StoreParameters x509StoreParameters) {
        if (x509StoreParameters instanceof X509LDAPCertStoreParameters) {
            this.helper = new LDAPStoreHelper((X509LDAPCertStoreParameters) x509StoreParameters);
            return;
        }
        throw new IllegalArgumentException("Initialization parameters must be an instance of " + X509LDAPCertStoreParameters.class.getName() + ".");
    }
}
