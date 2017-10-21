package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

class TlsPSKKeyExchange implements TlsKeyExchange {
    protected TlsClientContext context;
    protected DHPrivateKeyParameters dhAgreeClientPrivateKey = null;
    protected DHPublicKeyParameters dhAgreeServerPublicKey = null;
    protected int keyExchange;
    protected byte[] premasterSecret;
    protected TlsPSKIdentity pskIdentity;
    protected byte[] psk_identity_hint = null;
    protected RSAKeyParameters rsaServerPublicKey = null;

    TlsPSKKeyExchange(TlsClientContext tlsClientContext, int i, TlsPSKIdentity tlsPSKIdentity) {
        switch (i) {
            case 13:
            case 14:
            case 15:
                this.context = tlsClientContext;
                this.keyExchange = i;
                this.pskIdentity = tlsPSKIdentity;
                return;
            default:
                throw new IllegalArgumentException("unsupported key exchange algorithm");
        }
    }

    public void generateClientKeyExchange(OutputStream outputStream) throws IOException {
        if (this.psk_identity_hint == null || this.psk_identity_hint.length == 0) {
            this.pskIdentity.skipIdentityHint();
        } else {
            this.pskIdentity.notifyIdentityHint(this.psk_identity_hint);
        }
        TlsUtils.writeOpaque16(this.pskIdentity.getPSKIdentity(), outputStream);
        if (this.keyExchange == 15) {
            this.premasterSecret = TlsRSAUtils.generateEncryptedPreMasterSecret(this.context, this.rsaServerPublicKey, outputStream);
        } else if (this.keyExchange == 14) {
            this.dhAgreeClientPrivateKey = TlsDHUtils.generateEphemeralClientKeyExchange(this.context.getSecureRandom(), this.dhAgreeServerPublicKey.getParameters(), outputStream);
        }
    }

    protected byte[] generateOtherSecret(int i) {
        return this.keyExchange == 14 ? TlsDHUtils.calculateDHBasicAgreement(this.dhAgreeServerPublicKey, this.dhAgreeClientPrivateKey) : this.keyExchange == 15 ? this.premasterSecret : new byte[i];
    }

    public byte[] generatePremasterSecret() throws IOException {
        byte[] psk = this.pskIdentity.getPSK();
        byte[] generateOtherSecret = generateOtherSecret(psk.length);
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream((generateOtherSecret.length + 4) + psk.length);
        TlsUtils.writeOpaque16(generateOtherSecret, byteArrayOutputStream);
        TlsUtils.writeOpaque16(psk, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        throw new TlsFatalAlert((short) 80);
    }

    public void processServerCertificate(Certificate certificate) throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        this.psk_identity_hint = TlsUtils.readOpaque16(inputStream);
        if (this.keyExchange == 14) {
            byte[] readOpaque16 = TlsUtils.readOpaque16(inputStream);
            byte[] readOpaque162 = TlsUtils.readOpaque16(inputStream);
            this.dhAgreeServerPublicKey = TlsDHUtils.validateDHPublicKey(new DHPublicKeyParameters(new BigInteger(1, TlsUtils.readOpaque16(inputStream)), new DHParameters(new BigInteger(1, readOpaque16), new BigInteger(1, readOpaque162))));
        }
    }

    public void skipClientCredentials() throws IOException {
    }

    public void skipServerCertificate() throws IOException {
    }

    public void skipServerKeyExchange() throws IOException {
        this.psk_identity_hint = new byte[0];
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        throw new TlsFatalAlert((short) 10);
    }
}
