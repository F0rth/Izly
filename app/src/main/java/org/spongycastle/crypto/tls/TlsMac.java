package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class TlsMac {
    protected TlsClientContext context;
    protected Mac mac;
    protected byte[] secret;
    protected long seqNo = 0;

    public TlsMac(TlsClientContext tlsClientContext, Digest digest, byte[] bArr, int i, int i2) {
        this.context = tlsClientContext;
        CipherParameters keyParameter = new KeyParameter(bArr, i, i2);
        this.secret = Arrays.clone(keyParameter.getKey());
        if ((tlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : null) != null) {
            this.mac = new HMac(digest);
        } else {
            this.mac = new SSL3Mac(digest);
        }
        this.mac.init(keyParameter);
    }

    public byte[] calculateMac(short s, byte[] bArr, int i, int i2) {
        ProtocolVersion serverVersion = this.context.getServerVersion();
        int i3 = serverVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(i3 != 0 ? 13 : 11);
        try {
            long j = this.seqNo;
            this.seqNo = 1 + j;
            TlsUtils.writeUint64(j, byteArrayOutputStream);
            TlsUtils.writeUint8(s, byteArrayOutputStream);
            if (i3 != 0) {
                TlsUtils.writeVersion(serverVersion, byteArrayOutputStream);
            }
            TlsUtils.writeUint16(i2, byteArrayOutputStream);
            byte[] toByteArray = byteArrayOutputStream.toByteArray();
            this.mac.update(toByteArray, 0, toByteArray.length);
            this.mac.update(bArr, i, i2);
            toByteArray = new byte[this.mac.getMacSize()];
            this.mac.doFinal(toByteArray, 0);
            return toByteArray;
        } catch (IOException e) {
            throw new IllegalStateException("Internal error during mac calculation");
        }
    }

    public byte[] getMACSecret() {
        return this.secret;
    }

    public long getSequenceNumber() {
        return this.seqNo;
    }

    public int getSize() {
        return this.mac.getMacSize();
    }

    public void incSequenceNumber() {
        this.seqNo++;
    }
}
