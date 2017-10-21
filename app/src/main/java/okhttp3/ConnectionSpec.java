package okhttp3;

import java.util.Arrays;
import java.util.List;
import javax.net.ssl.SSLSocket;
import okhttp3.internal.Util;

public final class ConnectionSpec {
    private static final CipherSuite[] APPROVED_CIPHER_SUITES = new CipherSuite[]{CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256, CipherSuite.TLS_RSA_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_WITH_3DES_EDE_CBC_SHA};
    public static final ConnectionSpec CLEARTEXT = new Builder(false).build();
    public static final ConnectionSpec COMPATIBLE_TLS = new Builder(MODERN_TLS).tlsVersions(TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    public static final ConnectionSpec MODERN_TLS = new Builder(true).cipherSuites(APPROVED_CIPHER_SUITES).tlsVersions(TlsVersion.TLS_1_2, TlsVersion.TLS_1_1, TlsVersion.TLS_1_0).supportsTlsExtensions(true).build();
    private final String[] cipherSuites;
    private final boolean supportsTlsExtensions;
    private final boolean tls;
    private final String[] tlsVersions;

    public static final class Builder {
        private String[] cipherSuites;
        private boolean supportsTlsExtensions;
        private boolean tls;
        private String[] tlsVersions;

        public Builder(ConnectionSpec connectionSpec) {
            this.tls = connectionSpec.tls;
            this.cipherSuites = connectionSpec.cipherSuites;
            this.tlsVersions = connectionSpec.tlsVersions;
            this.supportsTlsExtensions = connectionSpec.supportsTlsExtensions;
        }

        Builder(boolean z) {
            this.tls = z;
        }

        public final Builder allEnabledCipherSuites() {
            if (this.tls) {
                this.cipherSuites = null;
                return this;
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public final Builder allEnabledTlsVersions() {
            if (this.tls) {
                this.tlsVersions = null;
                return this;
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }

        public final ConnectionSpec build() {
            return new ConnectionSpec();
        }

        public final Builder cipherSuites(String... strArr) {
            if (!this.tls) {
                throw new IllegalStateException("no cipher suites for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one cipher suite is required");
            } else {
                this.cipherSuites = (String[]) strArr.clone();
                return this;
            }
        }

        public final Builder cipherSuites(CipherSuite... cipherSuiteArr) {
            if (this.tls) {
                String[] strArr = new String[cipherSuiteArr.length];
                for (int i = 0; i < cipherSuiteArr.length; i++) {
                    strArr[i] = cipherSuiteArr[i].javaName;
                }
                return cipherSuites(strArr);
            }
            throw new IllegalStateException("no cipher suites for cleartext connections");
        }

        public final Builder supportsTlsExtensions(boolean z) {
            if (this.tls) {
                this.supportsTlsExtensions = z;
                return this;
            }
            throw new IllegalStateException("no TLS extensions for cleartext connections");
        }

        public final Builder tlsVersions(String... strArr) {
            if (!this.tls) {
                throw new IllegalStateException("no TLS versions for cleartext connections");
            } else if (strArr.length == 0) {
                throw new IllegalArgumentException("At least one TLS version is required");
            } else {
                this.tlsVersions = (String[]) strArr.clone();
                return this;
            }
        }

        public final Builder tlsVersions(TlsVersion... tlsVersionArr) {
            if (this.tls) {
                String[] strArr = new String[tlsVersionArr.length];
                for (int i = 0; i < tlsVersionArr.length; i++) {
                    strArr[i] = tlsVersionArr[i].javaName;
                }
                return tlsVersions(strArr);
            }
            throw new IllegalStateException("no TLS versions for cleartext connections");
        }
    }

    private ConnectionSpec(Builder builder) {
        this.tls = builder.tls;
        this.cipherSuites = builder.cipherSuites;
        this.tlsVersions = builder.tlsVersions;
        this.supportsTlsExtensions = builder.supportsTlsExtensions;
    }

    private static boolean nonEmptyIntersection(String[] strArr, String[] strArr2) {
        if (strArr == null || strArr2 == null || strArr.length == 0 || strArr2.length == 0) {
            return false;
        }
        for (String contains : strArr) {
            if (Util.contains(strArr2, contains)) {
                return true;
            }
        }
        return false;
    }

    private ConnectionSpec supportedSpec(SSLSocket sSLSocket, boolean z) {
        String[] enabledCipherSuites = this.cipherSuites != null ? (String[]) Util.intersect(String.class, this.cipherSuites, sSLSocket.getEnabledCipherSuites()) : sSLSocket.getEnabledCipherSuites();
        String[] enabledProtocols = this.tlsVersions != null ? (String[]) Util.intersect(String.class, this.tlsVersions, sSLSocket.getEnabledProtocols()) : sSLSocket.getEnabledProtocols();
        if (z && Util.contains(sSLSocket.getSupportedCipherSuites(), "TLS_FALLBACK_SCSV")) {
            enabledCipherSuites = Util.concat(enabledCipherSuites, "TLS_FALLBACK_SCSV");
        }
        return new Builder(this).cipherSuites(enabledCipherSuites).tlsVersions(enabledProtocols).build();
    }

    final void apply(SSLSocket sSLSocket, boolean z) {
        ConnectionSpec supportedSpec = supportedSpec(sSLSocket, z);
        if (supportedSpec.tlsVersions != null) {
            sSLSocket.setEnabledProtocols(supportedSpec.tlsVersions);
        }
        if (supportedSpec.cipherSuites != null) {
            sSLSocket.setEnabledCipherSuites(supportedSpec.cipherSuites);
        }
    }

    public final List<CipherSuite> cipherSuites() {
        if (this.cipherSuites == null) {
            return null;
        }
        Object[] objArr = new CipherSuite[this.cipherSuites.length];
        for (int i = 0; i < this.cipherSuites.length; i++) {
            objArr[i] = CipherSuite.forJavaName(this.cipherSuites[i]);
        }
        return Util.immutableList(objArr);
    }

    public final boolean equals(Object obj) {
        if (obj instanceof ConnectionSpec) {
            if (obj == this) {
                return true;
            }
            ConnectionSpec connectionSpec = (ConnectionSpec) obj;
            if (this.tls == connectionSpec.tls) {
                if (!this.tls) {
                    return true;
                }
                if (Arrays.equals(this.cipherSuites, connectionSpec.cipherSuites) && Arrays.equals(this.tlsVersions, connectionSpec.tlsVersions) && this.supportsTlsExtensions == connectionSpec.supportsTlsExtensions) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int hashCode() {
        if (!this.tls) {
            return 17;
        }
        int hashCode = Arrays.hashCode(this.cipherSuites);
        return (this.supportsTlsExtensions ? 0 : 1) + ((((hashCode + 527) * 31) + Arrays.hashCode(this.tlsVersions)) * 31);
    }

    public final boolean isCompatible(SSLSocket sSLSocket) {
        return this.tls && ((this.tlsVersions == null || nonEmptyIntersection(this.tlsVersions, sSLSocket.getEnabledProtocols())) && (this.cipherSuites == null || nonEmptyIntersection(this.cipherSuites, sSLSocket.getEnabledCipherSuites())));
    }

    public final boolean isTls() {
        return this.tls;
    }

    public final boolean supportsTlsExtensions() {
        return this.supportsTlsExtensions;
    }

    public final List<TlsVersion> tlsVersions() {
        if (this.tlsVersions == null) {
            return null;
        }
        Object[] objArr = new TlsVersion[this.tlsVersions.length];
        for (int i = 0; i < this.tlsVersions.length; i++) {
            objArr[i] = TlsVersion.forJavaName(this.tlsVersions[i]);
        }
        return Util.immutableList(objArr);
    }

    public final String toString() {
        if (!this.tls) {
            return "ConnectionSpec()";
        }
        return "ConnectionSpec(cipherSuites=" + (this.cipherSuites != null ? cipherSuites().toString() : "[all enabled]") + ", tlsVersions=" + (this.tlsVersions != null ? tlsVersions().toString() : "[all enabled]") + ", supportsTlsExtensions=" + this.supportsTlsExtensions + ")";
    }
}
