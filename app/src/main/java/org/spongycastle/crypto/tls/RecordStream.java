package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.crypto.Digest;

class RecordStream {
    private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    private TlsClientContext context = null;
    private TlsProtocolHandler handler;
    private CombinedHash hash = null;
    private InputStream is;
    private OutputStream os;
    private TlsCipher readCipher = null;
    private TlsCompression readCompression = null;
    private TlsCipher writeCipher = null;
    private TlsCompression writeCompression = null;

    RecordStream(TlsProtocolHandler tlsProtocolHandler, InputStream inputStream, OutputStream outputStream) {
        this.handler = tlsProtocolHandler;
        this.is = inputStream;
        this.os = outputStream;
        this.readCompression = new TlsNullCompression();
        this.writeCompression = this.readCompression;
        this.readCipher = new TlsNullCipher();
        this.writeCipher = this.readCipher;
    }

    private static byte[] doFinal(Digest digest) {
        byte[] bArr = new byte[digest.getDigestSize()];
        digest.doFinal(bArr, 0);
        return bArr;
    }

    private byte[] getBufferContents() {
        byte[] toByteArray = this.buffer.toByteArray();
        this.buffer.reset();
        return toByteArray;
    }

    void clientCipherSpecDecided(TlsCompression tlsCompression, TlsCipher tlsCipher) {
        this.writeCompression = tlsCompression;
        this.writeCipher = tlsCipher;
    }

    protected void close() throws IOException {
        IOException iOException = null;
        try {
            this.is.close();
        } catch (IOException e) {
            iOException = e;
        }
        try {
            this.os.close();
        } catch (IOException e2) {
            iOException = e2;
        }
        if (iOException != null) {
            throw iOException;
        }
    }

    protected byte[] decodeAndVerify(short s, InputStream inputStream, int i) throws IOException {
        byte[] bArr = new byte[i];
        TlsUtils.readFully(bArr, inputStream);
        bArr = this.readCipher.decodeCiphertext(s, bArr, 0, i);
        OutputStream decompress = this.readCompression.decompress(this.buffer);
        if (decompress == this.buffer) {
            return bArr;
        }
        decompress.write(bArr, 0, bArr.length);
        decompress.flush();
        return getBufferContents();
    }

    protected void flush() throws IOException {
        this.os.flush();
    }

    byte[] getCurrentHash(byte[] bArr) {
        Digest combinedHash = new CombinedHash(this.hash);
        if ((this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0) == 0 && bArr != null) {
            combinedHash.update(bArr, 0, bArr.length);
        }
        return doFinal(combinedHash);
    }

    void init(TlsClientContext tlsClientContext) {
        this.context = tlsClientContext;
        this.hash = new CombinedHash(tlsClientContext);
    }

    public void readData() throws IOException {
        short readUint8 = TlsUtils.readUint8(this.is);
        if (ProtocolVersion.TLSv10.equals(TlsUtils.readVersion(this.is))) {
            byte[] decodeAndVerify = decodeAndVerify(readUint8, this.is, TlsUtils.readUint16(this.is));
            this.handler.processData(readUint8, decodeAndVerify, 0, decodeAndVerify.length);
            return;
        }
        throw new TlsFatalAlert((short) 47);
    }

    void serverClientSpecReceived() {
        this.readCompression = this.writeCompression;
        this.readCipher = this.writeCipher;
    }

    void updateHandshakeData(byte[] bArr, int i, int i2) {
        this.hash.update(bArr, i, i2);
    }

    protected void writeMessage(short s, byte[] bArr, int i, int i2) throws IOException {
        Object encodePlaintext;
        if (s == (short) 22) {
            updateHandshakeData(bArr, i, i2);
        }
        OutputStream compress = this.writeCompression.compress(this.buffer);
        if (compress == this.buffer) {
            encodePlaintext = this.writeCipher.encodePlaintext(s, bArr, i, i2);
        } else {
            compress.write(bArr, i, i2);
            compress.flush();
            byte[] bufferContents = getBufferContents();
            encodePlaintext = this.writeCipher.encodePlaintext(s, bufferContents, 0, bufferContents.length);
        }
        Object obj = new byte[(encodePlaintext.length + 5)];
        TlsUtils.writeUint8(s, obj, 0);
        TlsUtils.writeVersion(ProtocolVersion.TLSv10, obj, 1);
        TlsUtils.writeUint16(encodePlaintext.length, obj, 3);
        System.arraycopy(encodePlaintext, 0, obj, 5, encodePlaintext.length);
        this.os.write(obj);
        this.os.flush();
    }
}
