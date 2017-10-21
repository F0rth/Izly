package org.spongycastle.crypto.tls;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.crypto.prng.ThreadedSeedGenerator;
import org.spongycastle.util.Arrays;

public class TlsProtocolHandler {
    private static final short CS_CERTIFICATE_REQUEST_RECEIVED = (short) 5;
    private static final short CS_CERTIFICATE_VERIFY_SEND = (short) 8;
    private static final short CS_CLIENT_CHANGE_CIPHER_SPEC_SEND = (short) 9;
    private static final short CS_CLIENT_FINISHED_SEND = (short) 10;
    private static final short CS_CLIENT_HELLO_SEND = (short) 1;
    private static final short CS_CLIENT_KEY_EXCHANGE_SEND = (short) 7;
    private static final short CS_DONE = (short) 12;
    private static final short CS_SERVER_CERTIFICATE_RECEIVED = (short) 3;
    private static final short CS_SERVER_CHANGE_CIPHER_SPEC_RECEIVED = (short) 11;
    private static final short CS_SERVER_HELLO_DONE_RECEIVED = (short) 6;
    private static final short CS_SERVER_HELLO_RECEIVED = (short) 2;
    private static final short CS_SERVER_KEY_EXCHANGE_RECEIVED = (short) 4;
    private static final Integer EXT_RenegotiationInfo = new Integer(65281);
    private static final String TLS_ERROR_MESSAGE = "Internal TLS error, this could be an attack";
    private static final byte[] emptybuf = new byte[0];
    private ByteQueue alertQueue;
    private boolean appDataReady;
    private ByteQueue applicationDataQueue;
    private TlsAuthentication authentication;
    private CertificateRequest certificateRequest;
    private ByteQueue changeCipherSpecQueue;
    private Hashtable clientExtensions;
    private boolean closed;
    private short connection_state;
    private boolean failedWithError;
    private ByteQueue handshakeQueue;
    private TlsKeyExchange keyExchange;
    private int[] offeredCipherSuites;
    private short[] offeredCompressionMethods;
    private SecureRandom random;
    private RecordStream rs;
    private SecurityParameters securityParameters;
    private TlsClient tlsClient;
    private TlsClientContextImpl tlsClientContext;
    private TlsInputStream tlsInputStream;
    private TlsOutputStream tlsOutputStream;

    public TlsProtocolHandler(InputStream inputStream, OutputStream outputStream) {
        this(inputStream, outputStream, createSecureRandom());
    }

    public TlsProtocolHandler(InputStream inputStream, OutputStream outputStream, SecureRandom secureRandom) {
        this.applicationDataQueue = new ByteQueue();
        this.changeCipherSpecQueue = new ByteQueue();
        this.alertQueue = new ByteQueue();
        this.handshakeQueue = new ByteQueue();
        this.tlsInputStream = null;
        this.tlsOutputStream = null;
        this.closed = false;
        this.failedWithError = false;
        this.appDataReady = false;
        this.securityParameters = null;
        this.tlsClientContext = null;
        this.tlsClient = null;
        this.offeredCipherSuites = null;
        this.offeredCompressionMethods = null;
        this.keyExchange = null;
        this.authentication = null;
        this.certificateRequest = null;
        this.connection_state = (short) 0;
        this.rs = new RecordStream(this, inputStream, outputStream);
        this.random = secureRandom;
    }

    private static boolean arrayContains(int[] iArr, int i) {
        for (int i2 : iArr) {
            if (i2 == i) {
                return true;
            }
        }
        return false;
    }

    private static boolean arrayContains(short[] sArr, short s) {
        for (short s2 : sArr) {
            if (s2 == s) {
                return true;
            }
        }
        return false;
    }

    private static byte[] createRenegotiationInfo(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeOpaque8(bArr, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private static SecureRandom createSecureRandom() {
        ThreadedSeedGenerator threadedSeedGenerator = new ThreadedSeedGenerator();
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(threadedSeedGenerator.generateSeed(20, true));
        return secureRandom;
    }

    private void failWithError(short s, short s2) throws IOException {
        if (this.closed) {
            throw new IOException(TLS_ERROR_MESSAGE);
        }
        this.closed = true;
        if (s == (short) 2) {
            this.failedWithError = true;
        }
        sendAlert(s, s2);
        this.rs.close();
        if (s == (short) 2) {
            throw new IOException(TLS_ERROR_MESSAGE);
        }
    }

    private void processAlert() throws IOException {
        while (this.alertQueue.size() >= 2) {
            byte[] bArr = new byte[2];
            this.alertQueue.read(bArr, 0, 2, 0);
            this.alertQueue.removeData(2);
            short s = (short) bArr[0];
            short s2 = (short) bArr[1];
            if (s == (short) 2) {
                this.failedWithError = true;
                this.closed = true;
                try {
                    this.rs.close();
                } catch (Exception e) {
                }
                throw new IOException(TLS_ERROR_MESSAGE);
            } else if (s2 == (short) 0) {
                failWithError((short) 1, (short) 0);
            }
        }
    }

    private void processApplicationData() {
    }

    private void processChangeCipherSpec() throws IOException {
        while (this.changeCipherSpecQueue.size() > 0) {
            byte[] bArr = new byte[1];
            this.changeCipherSpecQueue.read(bArr, 0, 1, 0);
            this.changeCipherSpecQueue.removeData(1);
            if (bArr[0] != (byte) 1) {
                failWithError((short) 2, (short) 10);
            }
            if (this.connection_state != (short) 10) {
                failWithError((short) 2, (short) 40);
            }
            this.rs.serverClientSpecReceived();
            this.connection_state = (short) 11;
        }
    }

    private void processHandshake() throws IOException {
        int i;
        do {
            if (this.handshakeQueue.size() >= 4) {
                byte[] bArr = new byte[4];
                this.handshakeQueue.read(bArr, 0, 4, 0);
                InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
                short readUint8 = TlsUtils.readUint8(byteArrayInputStream);
                int readUint24 = TlsUtils.readUint24(byteArrayInputStream);
                if (this.handshakeQueue.size() >= readUint24 + 4) {
                    byte[] bArr2 = new byte[readUint24];
                    this.handshakeQueue.read(bArr2, 0, readUint24, 4);
                    this.handshakeQueue.removeData(readUint24 + 4);
                    switch (readUint8) {
                        case (short) 0:
                        case (short) 20:
                            break;
                        default:
                            this.rs.updateHandshakeData(bArr, 0, 4);
                            this.rs.updateHandshakeData(bArr2, 0, readUint24);
                            break;
                    }
                    processHandshakeMessage(readUint8, bArr2);
                    i = 1;
                    continue;
                }
            }
            i = 0;
            continue;
        } while (i != 0);
    }

    private void processHandshakeMessage(short s, byte[] bArr) throws IOException {
        byte b = (byte) 0;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        byte[] readOpaque8;
        short readUint8;
        switch (s) {
            case (short) 0:
                if (this.connection_state == (short) 12) {
                    sendAlert((short) 1, (short) 100);
                    return;
                }
                return;
            case (short) 2:
                switch (this.connection_state) {
                    case (short) 1:
                        ProtocolVersion readVersion = TlsUtils.readVersion(byteArrayInputStream);
                        if (readVersion.getFullVersion() > this.tlsClientContext.getClientVersion().getFullVersion()) {
                            failWithError((short) 2, (short) 47);
                        }
                        this.tlsClientContext.setServerVersion(readVersion);
                        this.tlsClient.notifyServerVersion(readVersion);
                        this.securityParameters.serverRandom = new byte[32];
                        TlsUtils.readFully(this.securityParameters.serverRandom, byteArrayInputStream);
                        readOpaque8 = TlsUtils.readOpaque8(byteArrayInputStream);
                        if (readOpaque8.length > 32) {
                            failWithError((short) 2, (short) 47);
                        }
                        this.tlsClient.notifySessionID(readOpaque8);
                        int readUint16 = TlsUtils.readUint16(byteArrayInputStream);
                        if (!arrayContains(this.offeredCipherSuites, readUint16) || readUint16 == CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) {
                            failWithError((short) 2, (short) 47);
                        }
                        this.tlsClient.notifySelectedCipherSuite(readUint16);
                        readUint8 = TlsUtils.readUint8(byteArrayInputStream);
                        if (!arrayContains(this.offeredCompressionMethods, readUint8)) {
                            failWithError((short) 2, (short) 47);
                        }
                        this.tlsClient.notifySelectedCompressionMethod(readUint8);
                        Hashtable hashtable = new Hashtable();
                        if (byteArrayInputStream.available() > 0) {
                            InputStream byteArrayInputStream2 = new ByteArrayInputStream(TlsUtils.readOpaque16(byteArrayInputStream));
                            while (byteArrayInputStream2.available() > 0) {
                                Integer num = new Integer(TlsUtils.readUint16(byteArrayInputStream2));
                                Object readOpaque16 = TlsUtils.readOpaque16(byteArrayInputStream2);
                                if (!num.equals(EXT_RenegotiationInfo) && this.clientExtensions.get(num) == null) {
                                    failWithError((short) 2, AlertDescription.unsupported_extension);
                                }
                                if (hashtable.containsKey(num)) {
                                    failWithError((short) 2, (short) 47);
                                }
                                hashtable.put(num, readOpaque16);
                            }
                        }
                        assertEmpty(byteArrayInputStream);
                        boolean containsKey = hashtable.containsKey(EXT_RenegotiationInfo);
                        if (containsKey && !Arrays.constantTimeAreEqual((byte[]) hashtable.get(EXT_RenegotiationInfo), createRenegotiationInfo(emptybuf))) {
                            failWithError((short) 2, (short) 40);
                        }
                        this.tlsClient.notifySecureRenegotiation(containsKey);
                        if (this.clientExtensions != null) {
                            this.tlsClient.processServerExtensions(hashtable);
                        }
                        this.keyExchange = this.tlsClient.getKeyExchange();
                        this.connection_state = (short) 2;
                        return;
                    default:
                        failWithError((short) 2, (short) 10);
                        return;
                }
            case (short) 11:
                switch (this.connection_state) {
                    case (short) 2:
                        Certificate parse = Certificate.parse(byteArrayInputStream);
                        assertEmpty(byteArrayInputStream);
                        this.keyExchange.processServerCertificate(parse);
                        this.authentication = this.tlsClient.getAuthentication();
                        this.authentication.notifyServerCertificate(parse);
                        break;
                    default:
                        failWithError((short) 2, (short) 10);
                        break;
                }
                this.connection_state = (short) 3;
                return;
            case (short) 12:
                switch (this.connection_state) {
                    case (short) 2:
                        this.keyExchange.skipServerCertificate();
                        this.authentication = null;
                        break;
                    case (short) 3:
                        break;
                    default:
                        failWithError((short) 2, (short) 10);
                        break;
                }
                this.keyExchange.processServerKeyExchange(byteArrayInputStream);
                assertEmpty(byteArrayInputStream);
                this.connection_state = (short) 4;
                return;
            case (short) 13:
                switch (this.connection_state) {
                    case (short) 3:
                        this.keyExchange.skipServerKeyExchange();
                        break;
                    case (short) 4:
                        break;
                    default:
                        failWithError((short) 2, (short) 10);
                        break;
                }
                if (this.authentication == null) {
                    failWithError((short) 2, (short) 40);
                }
                short readUint82 = TlsUtils.readUint8(byteArrayInputStream);
                short[] sArr = new short[readUint82];
                for (readUint8 = (short) 0; readUint8 < readUint82; readUint8++) {
                    sArr[readUint8] = TlsUtils.readUint8(byteArrayInputStream);
                }
                readOpaque8 = TlsUtils.readOpaque16(byteArrayInputStream);
                assertEmpty(byteArrayInputStream);
                Vector vector = new Vector();
                byteArrayInputStream = new ByteArrayInputStream(readOpaque8);
                while (byteArrayInputStream.available() > 0) {
                    vector.addElement(X500Name.getInstance(ASN1Primitive.fromByteArray(TlsUtils.readOpaque16(byteArrayInputStream))));
                }
                this.certificateRequest = new CertificateRequest(sArr, vector);
                this.keyExchange.validateCertificateRequest(this.certificateRequest);
                this.connection_state = CS_CERTIFICATE_REQUEST_RECEIVED;
                return;
            case (short) 14:
                switch (this.connection_state) {
                    case (short) 2:
                        this.keyExchange.skipServerCertificate();
                        this.authentication = null;
                        break;
                    case (short) 3:
                        break;
                    case (short) 4:
                    case (short) 5:
                        break;
                    default:
                        failWithError((short) 2, (short) 40);
                        return;
                }
                this.keyExchange.skipServerKeyExchange();
                assertEmpty(byteArrayInputStream);
                this.connection_state = CS_SERVER_HELLO_DONE_RECEIVED;
                TlsCredentials tlsCredentials = null;
                if (this.certificateRequest == null) {
                    this.keyExchange.skipClientCredentials();
                } else {
                    tlsCredentials = this.authentication.getClientCredentials(this.certificateRequest);
                    if (tlsCredentials == null) {
                        this.keyExchange.skipClientCredentials();
                        if (this.tlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {
                            sendClientCertificate(Certificate.EMPTY_CHAIN);
                        } else {
                            sendAlert((short) 1, (short) 41);
                        }
                    } else {
                        this.keyExchange.processClientCredentials(tlsCredentials);
                        sendClientCertificate(tlsCredentials.getCertificate());
                    }
                }
                sendClientKeyExchange();
                this.connection_state = CS_CLIENT_KEY_EXCHANGE_SEND;
                byte[] generatePremasterSecret = this.keyExchange.generatePremasterSecret();
                this.securityParameters.masterSecret = TlsUtils.calculateMasterSecret(this.tlsClientContext, generatePremasterSecret);
                Arrays.fill(generatePremasterSecret, (byte) 0);
                if (tlsCredentials != null && (tlsCredentials instanceof TlsSignerCredentials)) {
                    sendCertificateVerify(((TlsSignerCredentials) tlsCredentials).generateCertificateSignature(this.rs.getCurrentHash(null)));
                    this.connection_state = CS_CERTIFICATE_VERIFY_SEND;
                }
                this.rs.writeMessage((short) 20, new byte[]{(byte) 1}, 0, 1);
                this.connection_state = CS_CLIENT_CHANGE_CIPHER_SPEC_SEND;
                this.rs.clientCipherSpecDecided(this.tlsClient.getCompression(), this.tlsClient.getCipher());
                readOpaque8 = TlsUtils.calculateVerifyData(this.tlsClientContext, "client finished", this.rs.getCurrentHash(TlsUtils.SSL_CLIENT));
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                TlsUtils.writeUint8((short) 20, byteArrayOutputStream);
                TlsUtils.writeOpaque24(readOpaque8, byteArrayOutputStream);
                readOpaque8 = byteArrayOutputStream.toByteArray();
                this.rs.writeMessage((short) 22, readOpaque8, 0, readOpaque8.length);
                this.connection_state = (short) 10;
                return;
            case (short) 20:
                switch (this.connection_state) {
                    case (short) 11:
                        if (this.tlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {
                            b = (byte) 1;
                        }
                        readOpaque8 = new byte[(b != (byte) 0 ? 12 : 36)];
                        TlsUtils.readFully(readOpaque8, byteArrayInputStream);
                        assertEmpty(byteArrayInputStream);
                        if (!Arrays.constantTimeAreEqual(TlsUtils.calculateVerifyData(this.tlsClientContext, "server finished", this.rs.getCurrentHash(TlsUtils.SSL_SERVER)), readOpaque8)) {
                            failWithError((short) 2, (short) 40);
                        }
                        this.connection_state = (short) 12;
                        this.appDataReady = true;
                        return;
                    default:
                        failWithError((short) 2, (short) 10);
                        return;
                }
            default:
                failWithError((short) 2, (short) 10);
                return;
        }
    }

    private void safeReadData() throws IOException {
        try {
            this.rs.readData();
        } catch (TlsFatalAlert e) {
            if (!this.closed) {
                failWithError((short) 2, e.getAlertDescription());
            }
            throw e;
        } catch (IOException e2) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e2;
        } catch (RuntimeException e3) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e3;
        }
    }

    private void safeWriteMessage(short s, byte[] bArr, int i, int i2) throws IOException {
        try {
            this.rs.writeMessage(s, bArr, i, i2);
        } catch (TlsFatalAlert e) {
            if (!this.closed) {
                failWithError((short) 2, e.getAlertDescription());
            }
            throw e;
        } catch (IOException e2) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e2;
        } catch (RuntimeException e3) {
            if (!this.closed) {
                failWithError((short) 2, (short) 80);
            }
            throw e3;
        }
    }

    private void sendAlert(short s, short s2) throws IOException {
        byte b = (byte) s;
        byte b2 = (byte) s2;
        this.rs.writeMessage((short) 21, new byte[]{b, b2}, 0, 2);
    }

    private void sendCertificateVerify(byte[] bArr) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 15, byteArrayOutputStream);
        TlsUtils.writeUint24(bArr.length + 2, byteArrayOutputStream);
        TlsUtils.writeOpaque16(bArr, byteArrayOutputStream);
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        this.rs.writeMessage((short) 22, toByteArray, 0, toByteArray.length);
    }

    private void sendClientCertificate(Certificate certificate) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 11, byteArrayOutputStream);
        TlsUtils.writeUint24(0, byteArrayOutputStream);
        certificate.encode(byteArrayOutputStream);
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        TlsUtils.writeUint24(toByteArray.length - 4, toByteArray, 1);
        this.rs.writeMessage((short) 22, toByteArray, 0, toByteArray.length);
    }

    private void sendClientKeyExchange() throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        TlsUtils.writeUint8((short) 16, byteArrayOutputStream);
        TlsUtils.writeUint24(0, byteArrayOutputStream);
        this.keyExchange.generateClientKeyExchange(byteArrayOutputStream);
        byte[] toByteArray = byteArrayOutputStream.toByteArray();
        TlsUtils.writeUint24(toByteArray.length - 4, toByteArray, 1);
        this.rs.writeMessage((short) 22, toByteArray, 0, toByteArray.length);
    }

    private static void writeExtension(OutputStream outputStream, Integer num, byte[] bArr) throws IOException {
        TlsUtils.writeUint16(num.intValue(), outputStream);
        TlsUtils.writeOpaque16(bArr, outputStream);
    }

    protected void assertEmpty(ByteArrayInputStream byteArrayInputStream) throws IOException {
        if (byteArrayInputStream.available() > 0) {
            throw new TlsFatalAlert((short) 50);
        }
    }

    public void close() throws IOException {
        if (!this.closed) {
            failWithError((short) 1, (short) 0);
        }
    }

    public void connect(CertificateVerifyer certificateVerifyer) throws IOException {
        connect(new LegacyTlsClient(certificateVerifyer));
    }

    public void connect(TlsClient tlsClient) throws IOException {
        if (tlsClient == null) {
            throw new IllegalArgumentException("'tlsClient' cannot be null");
        } else if (this.tlsClient != null) {
            throw new IllegalStateException("connect can only be called once");
        } else {
            this.securityParameters = new SecurityParameters();
            this.securityParameters.clientRandom = new byte[32];
            this.random.nextBytes(this.securityParameters.clientRandom);
            TlsUtils.writeGMTUnixTime(this.securityParameters.clientRandom, 0);
            this.tlsClientContext = new TlsClientContextImpl(this.random, this.securityParameters);
            this.rs.init(this.tlsClientContext);
            this.tlsClient = tlsClient;
            this.tlsClient.init(this.tlsClientContext);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ProtocolVersion clientVersion = this.tlsClient.getClientVersion();
            this.tlsClientContext.setClientVersion(clientVersion);
            this.tlsClientContext.setServerVersion(clientVersion);
            TlsUtils.writeVersion(clientVersion, byteArrayOutputStream);
            byteArrayOutputStream.write(this.securityParameters.clientRandom);
            TlsUtils.writeUint8((short) 0, byteArrayOutputStream);
            this.offeredCipherSuites = this.tlsClient.getCipherSuites();
            this.clientExtensions = this.tlsClient.getClientExtensions();
            short s = (this.clientExtensions == null || this.clientExtensions.get(EXT_RenegotiationInfo) == null) ? (short) 1 : (short) 0;
            int length = this.offeredCipherSuites.length;
            if (s != (short) 0) {
                length++;
            }
            TlsUtils.writeUint16(length * 2, byteArrayOutputStream);
            TlsUtils.writeUint16Array(this.offeredCipherSuites, byteArrayOutputStream);
            if (s != (short) 0) {
                TlsUtils.writeUint16(CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV, byteArrayOutputStream);
            }
            this.offeredCompressionMethods = this.tlsClient.getCompressionMethods();
            TlsUtils.writeUint8((short) this.offeredCompressionMethods.length, byteArrayOutputStream);
            TlsUtils.writeUint8Array(this.offeredCompressionMethods, byteArrayOutputStream);
            if (this.clientExtensions != null) {
                OutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                Enumeration keys = this.clientExtensions.keys();
                while (keys.hasMoreElements()) {
                    Integer num = (Integer) keys.nextElement();
                    writeExtension(byteArrayOutputStream2, num, (byte[]) this.clientExtensions.get(num));
                }
                TlsUtils.writeOpaque16(byteArrayOutputStream2.toByteArray(), byteArrayOutputStream);
            }
            OutputStream byteArrayOutputStream3 = new ByteArrayOutputStream();
            TlsUtils.writeUint8((short) 1, byteArrayOutputStream3);
            TlsUtils.writeUint24(byteArrayOutputStream.size(), byteArrayOutputStream3);
            byteArrayOutputStream3.write(byteArrayOutputStream.toByteArray());
            byte[] toByteArray = byteArrayOutputStream3.toByteArray();
            safeWriteMessage((short) 22, toByteArray, 0, toByteArray.length);
            this.connection_state = (short) 1;
            while (this.connection_state != (short) 12) {
                safeReadData();
            }
            this.tlsInputStream = new TlsInputStream(this);
            this.tlsOutputStream = new TlsOutputStream(this);
        }
    }

    protected void flush() throws IOException {
        this.rs.flush();
    }

    public InputStream getInputStream() {
        return this.tlsInputStream;
    }

    public OutputStream getOutputStream() {
        return this.tlsOutputStream;
    }

    protected void processData(short s, byte[] bArr, int i, int i2) throws IOException {
        switch (s) {
            case (short) 20:
                this.changeCipherSpecQueue.addData(bArr, i, i2);
                processChangeCipherSpec();
                return;
            case (short) 21:
                this.alertQueue.addData(bArr, i, i2);
                processAlert();
                return;
            case (short) 22:
                this.handshakeQueue.addData(bArr, i, i2);
                processHandshake();
                return;
            case (short) 23:
                if (!this.appDataReady) {
                    failWithError((short) 2, (short) 10);
                }
                this.applicationDataQueue.addData(bArr, i, i2);
                processApplicationData();
                return;
            default:
                return;
        }
    }

    protected int readApplicationData(byte[] bArr, int i, int i2) throws IOException {
        while (this.applicationDataQueue.size() == 0) {
            if (!this.closed) {
                safeReadData();
            } else if (!this.failedWithError) {
                return -1;
            } else {
                throw new IOException(TLS_ERROR_MESSAGE);
            }
        }
        int min = Math.min(i2, this.applicationDataQueue.size());
        this.applicationDataQueue.read(bArr, i, min, 0);
        this.applicationDataQueue.removeData(min);
        return min;
    }

    protected void writeData(byte[] bArr, int i, int i2) throws IOException {
        if (!this.closed) {
            safeWriteMessage((short) 23, emptybuf, 0, 0);
            do {
                int min = Math.min(i2, 16384);
                safeWriteMessage((short) 23, bArr, i, min);
                i += min;
                i2 -= min;
            } while (i2 > 0);
        } else if (this.failedWithError) {
            throw new IOException(TLS_ERROR_MESSAGE);
        } else {
            throw new IOException("Sorry, connection has been closed, you cannot write more data");
        }
    }
}
