package okhttp3;

import defpackage.nz;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import okhttp3.internal.Util;
import okhttp3.internal.tls.CertificateChainCleaner;

public final class CertificatePinner {
    public static final CertificatePinner DEFAULT = new Builder().build();
    private final CertificateChainCleaner certificateChainCleaner;
    private final List<Pin> pins;

    public static final class Builder {
        private final List<Pin> pins = new ArrayList();

        public final Builder add(String str, String... strArr) {
            if (str == null) {
                throw new NullPointerException("pattern == null");
            }
            for (String pin : strArr) {
                this.pins.add(new Pin(str, pin));
            }
            return this;
        }

        public final CertificatePinner build() {
            return new CertificatePinner(Util.immutableList(this.pins), null);
        }
    }

    static final class Pin {
        final nz hash;
        final String hashAlgorithm;
        final String pattern;

        Pin(String str, String str2) {
            this.pattern = str;
            if (str2.startsWith("sha1/")) {
                this.hashAlgorithm = "sha1/";
                this.hash = nz.b(str2.substring(5));
            } else if (str2.startsWith("sha256/")) {
                this.hashAlgorithm = "sha256/";
                this.hash = nz.b(str2.substring(7));
            } else {
                throw new IllegalArgumentException("pins must start with 'sha256/' or 'sha1/': " + str2);
            }
            if (this.hash == null) {
                throw new IllegalArgumentException("pins must be base64: " + str2);
            }
        }

        public final boolean equals(Object obj) {
            return (obj instanceof Pin) && this.pattern.equals(((Pin) obj).pattern) && this.hashAlgorithm.equals(((Pin) obj).hashAlgorithm) && this.hash.equals(((Pin) obj).hash);
        }

        public final int hashCode() {
            return ((((this.pattern.hashCode() + 527) * 31) + this.hashAlgorithm.hashCode()) * 31) + this.hash.hashCode();
        }

        final boolean matches(String str) {
            if (this.pattern.equals(str)) {
                return true;
            }
            int indexOf = str.indexOf(46);
            if (!this.pattern.startsWith("*.")) {
                return false;
            }
            return str.regionMatches(false, indexOf + 1, this.pattern, 2, this.pattern.length() + -2);
        }

        public final String toString() {
            return this.hashAlgorithm + this.hash.b();
        }
    }

    private CertificatePinner(List<Pin> list, CertificateChainCleaner certificateChainCleaner) {
        this.pins = list;
        this.certificateChainCleaner = certificateChainCleaner;
    }

    public static String pin(Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            return "sha256/" + sha256((X509Certificate) certificate).b();
        }
        throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
    }

    static nz sha1(X509Certificate x509Certificate) {
        return Util.sha1(nz.a(x509Certificate.getPublicKey().getEncoded()));
    }

    static nz sha256(X509Certificate x509Certificate) {
        return Util.sha256(nz.a(x509Certificate.getPublicKey().getEncoded()));
    }

    public final void check(String str, List<Certificate> list) throws SSLPeerUnverifiedException {
        List findMatchingPins = findMatchingPins(str);
        if (!findMatchingPins.isEmpty()) {
            List clean;
            int i;
            if (this.certificateChainCleaner != null) {
                clean = this.certificateChainCleaner.clean(list, str);
            }
            int size = clean.size();
            for (int i2 = 0; i2 < size; i2++) {
                X509Certificate x509Certificate = (X509Certificate) clean.get(i2);
                int size2 = findMatchingPins.size();
                Object obj = null;
                int i3 = 0;
                Object obj2 = null;
                while (i3 < size2) {
                    Object obj3;
                    Pin pin = (Pin) findMatchingPins.get(i3);
                    if (pin.hashAlgorithm.equals("sha256/")) {
                        if (obj2 == null) {
                            obj2 = sha256(x509Certificate);
                        }
                        if (!pin.hash.equals(obj2)) {
                            obj3 = obj;
                            obj = obj2;
                        } else {
                            return;
                        }
                    } else if (pin.hashAlgorithm.equals("sha1/")) {
                        if (obj == null) {
                            obj = sha1(x509Certificate);
                        }
                        if (!pin.hash.equals(obj)) {
                            obj3 = obj;
                            obj = obj2;
                        } else {
                            return;
                        }
                    } else {
                        throw new AssertionError();
                    }
                    i3++;
                    obj2 = obj;
                    obj = obj3;
                }
            }
            StringBuilder append = new StringBuilder("Certificate pinning failure!").append("\n  Peer certificate chain:");
            int size3 = clean.size();
            for (i = 0; i < size3; i++) {
                X509Certificate x509Certificate2 = (X509Certificate) clean.get(i);
                append.append("\n    ").append(pin(x509Certificate2)).append(": ").append(x509Certificate2.getSubjectDN().getName());
            }
            append.append("\n  Pinned certificates for ").append(str).append(":");
            size3 = findMatchingPins.size();
            for (i = 0; i < size3; i++) {
                append.append("\n    ").append((Pin) findMatchingPins.get(i));
            }
            throw new SSLPeerUnverifiedException(append.toString());
        }
    }

    public final void check(String str, Certificate... certificateArr) throws SSLPeerUnverifiedException {
        check(str, Arrays.asList(certificateArr));
    }

    final List<Pin> findMatchingPins(String str) {
        List<Pin> emptyList = Collections.emptyList();
        List<Pin> list = emptyList;
        for (Pin pin : this.pins) {
            if (pin.matches(str)) {
                if (list.isEmpty()) {
                    list = new ArrayList();
                }
                list.add(pin);
            }
        }
        return list;
    }

    final CertificatePinner withCertificateChainCleaner(CertificateChainCleaner certificateChainCleaner) {
        return this.certificateChainCleaner != certificateChainCleaner ? new CertificatePinner(this.pins, certificateChainCleaner) : this;
    }
}
