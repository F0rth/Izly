package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.util.Hashtable;

public class PSKTlsClient implements TlsClient {
    protected TlsCipherFactory cipherFactory;
    protected TlsClientContext context;
    protected TlsPSKIdentity pskIdentity;
    protected int selectedCipherSuite;
    protected int selectedCompressionMethod;

    public PSKTlsClient(TlsCipherFactory tlsCipherFactory, TlsPSKIdentity tlsPSKIdentity) {
        this.cipherFactory = tlsCipherFactory;
        this.pskIdentity = tlsPSKIdentity;
    }

    public PSKTlsClient(TlsPSKIdentity tlsPSKIdentity) {
        this(new DefaultTlsCipherFactory(), tlsPSKIdentity);
    }

    protected TlsKeyExchange createPSKKeyExchange(int i) {
        return new TlsPSKKeyExchange(this.context, i, this.pskIdentity);
    }

    public TlsAuthentication getAuthentication() throws IOException {
        return null;
    }

    public TlsCipher getCipher() throws IOException {
        switch (this.selectedCipherSuite) {
            case CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA /*139*/:
            case CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA /*143*/:
            case CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA /*147*/:
                return this.cipherFactory.createCipher(this.context, 7, 2);
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /*140*/:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /*144*/:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA /*148*/:
                return this.cipherFactory.createCipher(this.context, 8, 2);
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /*141*/:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA /*145*/:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA /*149*/:
                return this.cipherFactory.createCipher(this.context, 9, 2);
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    public int[] getCipherSuites() {
        return new int[]{CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA};
    }

    public Hashtable getClientExtensions() throws IOException {
        return null;
    }

    public ProtocolVersion getClientVersion() {
        return ProtocolVersion.TLSv10;
    }

    public TlsCompression getCompression() throws IOException {
        switch (this.selectedCompressionMethod) {
            case 0:
                return new TlsNullCompression();
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    public short[] getCompressionMethods() {
        return new short[]{(short) 0};
    }

    public TlsKeyExchange getKeyExchange() throws IOException {
        switch (this.selectedCipherSuite) {
            case CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA /*139*/:
            case CipherSuite.TLS_PSK_WITH_AES_128_CBC_SHA /*140*/:
            case CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA /*141*/:
                return createPSKKeyExchange(13);
            case CipherSuite.TLS_DHE_PSK_WITH_3DES_EDE_CBC_SHA /*143*/:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_128_CBC_SHA /*144*/:
            case CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA /*145*/:
                return createPSKKeyExchange(14);
            case CipherSuite.TLS_RSA_PSK_WITH_3DES_EDE_CBC_SHA /*147*/:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_128_CBC_SHA /*148*/:
            case CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA /*149*/:
                return createPSKKeyExchange(15);
            default:
                throw new TlsFatalAlert((short) 80);
        }
    }

    public void init(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
    }

    public void notifySecureRenegotiation(boolean z) throws IOException {
    }

    public void notifySelectedCipherSuite(int i) {
        this.selectedCipherSuite = i;
    }

    public void notifySelectedCompressionMethod(short s) {
        this.selectedCompressionMethod = s;
    }

    public void notifyServerVersion(ProtocolVersion protocolVersion) throws IOException {
        if (!ProtocolVersion.TLSv10.equals(protocolVersion)) {
            throw new TlsFatalAlert((short) 47);
        }
    }

    public void notifySessionID(byte[] bArr) {
    }

    public void processServerExtensions(Hashtable hashtable) {
    }
}
