package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.io.SignerInputStream;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;

class TlsECDHEKeyExchange extends TlsECDHKeyExchange {
    TlsECDHEKeyExchange(TlsClientContext tlsClientContext, int i) {
        super(tlsClientContext, i);
    }

    protected Signer initSigner(TlsSigner tlsSigner, SecurityParameters securityParameters) {
        Signer createVerifyer = tlsSigner.createVerifyer(this.serverPublicKey);
        createVerifyer.update(securityParameters.clientRandom, 0, securityParameters.clientRandom.length);
        createVerifyer.update(securityParameters.serverRandom, 0, securityParameters.serverRandom.length);
        return createVerifyer;
    }

    public void processClientCredentials(TlsCredentials tlsCredentials) throws IOException {
        if (!(tlsCredentials instanceof TlsSignerCredentials)) {
            throw new TlsFatalAlert((short) 80);
        }
    }

    public void processServerKeyExchange(InputStream inputStream) throws IOException {
        Signer initSigner = initSigner(this.tlsSigner, this.context.getSecurityParameters());
        InputStream signerInputStream = new SignerInputStream(inputStream, initSigner);
        if (TlsUtils.readUint8(signerInputStream) == (short) 3) {
            ECDomainParameters eCParameters = NamedCurve.getECParameters(TlsUtils.readUint16(signerInputStream));
            byte[] readOpaque8 = TlsUtils.readOpaque8(signerInputStream);
            if (initSigner.verifySignature(TlsUtils.readOpaque16(inputStream))) {
                this.ecAgreeServerPublicKey = validateECPublicKey(new ECPublicKeyParameters(eCParameters.getCurve().decodePoint(readOpaque8), eCParameters));
                return;
            }
            throw new TlsFatalAlert((short) 42);
        }
        throw new TlsFatalAlert((short) 40);
    }

    public void skipServerKeyExchange() throws IOException {
        throw new TlsFatalAlert((short) 10);
    }

    public void validateCertificateRequest(CertificateRequest certificateRequest) throws IOException {
        short[] certificateTypes = certificateRequest.getCertificateTypes();
        int i = 0;
        while (i < certificateTypes.length) {
            switch (certificateTypes[i]) {
                case (short) 1:
                case (short) 2:
                case (short) 64:
                    i++;
                default:
                    throw new TlsFatalAlert((short) 47);
            }
        }
    }
}
