package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class TlsRSAUtils {
    public static byte[] generateEncryptedPreMasterSecret(TlsClientContext tlsClientContext, RSAKeyParameters rSAKeyParameters, OutputStream outputStream) throws IOException {
        int i = 0;
        byte[] bArr = new byte[48];
        tlsClientContext.getSecureRandom().nextBytes(bArr);
        TlsUtils.writeVersion(tlsClientContext.getClientVersion(), bArr, 0);
        PKCS1Encoding pKCS1Encoding = new PKCS1Encoding(new RSABlindedEngine());
        pKCS1Encoding.init(true, new ParametersWithRandom(rSAKeyParameters, tlsClientContext.getSecureRandom()));
        try {
            if (tlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {
                i = 1;
            }
            byte[] processBlock = pKCS1Encoding.processBlock(bArr, 0, 48);
            if (i != 0) {
                TlsUtils.writeOpaque16(processBlock, outputStream);
            } else {
                outputStream.write(processBlock);
            }
            return bArr;
        } catch (InvalidCipherTextException e) {
            throw new TlsFatalAlert((short) 80);
        }
    }
}
