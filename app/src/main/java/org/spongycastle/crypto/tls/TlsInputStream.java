package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;

class TlsInputStream extends InputStream {
    private byte[] buf = new byte[1];
    private TlsProtocolHandler handler = null;

    TlsInputStream(TlsProtocolHandler tlsProtocolHandler) {
        this.handler = tlsProtocolHandler;
    }

    public void close() throws IOException {
        this.handler.close();
    }

    public int read() throws IOException {
        return read(this.buf) < 0 ? -1 : this.buf[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        return this.handler.readApplicationData(bArr, i, i2);
    }
}
