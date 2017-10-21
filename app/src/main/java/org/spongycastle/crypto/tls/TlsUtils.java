package org.spongycastle.crypto.tls;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.x509.KeyUsage;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.io.Streams;

public class TlsUtils {
    static final byte[][] SSL3_CONST = genConst();
    static final byte[] SSL_CLIENT = new byte[]{(byte) 67, (byte) 76, (byte) 78, (byte) 84};
    static final byte[] SSL_SERVER = new byte[]{(byte) 83, (byte) 82, (byte) 86, (byte) 82};

    protected static byte[] PRF(byte[] bArr, String str, byte[] bArr2, int i) {
        int i2 = 0;
        byte[] toByteArray = Strings.toByteArray(str);
        int length = (bArr.length + 1) / 2;
        Object obj = new byte[length];
        Object obj2 = new byte[length];
        System.arraycopy(bArr, 0, obj, 0, length);
        System.arraycopy(bArr, bArr.length - length, obj2, 0, length);
        toByteArray = concat(toByteArray, bArr2);
        byte[] bArr3 = new byte[i];
        byte[] bArr4 = new byte[i];
        hmac_hash(new MD5Digest(), obj, toByteArray, bArr4);
        hmac_hash(new SHA1Digest(), obj2, toByteArray, bArr3);
        while (i2 < i) {
            bArr3[i2] = (byte) (bArr3[i2] ^ bArr4[i2]);
            i2++;
        }
        return bArr3;
    }

    static byte[] PRF_1_2(Digest digest, byte[] bArr, String str, byte[] bArr2, int i) {
        byte[] bArr3 = new byte[i];
        hmac_hash(digest, bArr, concat(Strings.toByteArray(str), bArr2), bArr3);
        return bArr3;
    }

    static byte[] calculateKeyBlock(TlsClientContext tlsClientContext, int i) {
        ProtocolVersion serverVersion = tlsClientContext.getServerVersion();
        SecurityParameters securityParameters = tlsClientContext.getSecurityParameters();
        byte[] concat = concat(securityParameters.serverRandom, securityParameters.clientRandom);
        if ((serverVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0) != 0) {
            return PRF(securityParameters.masterSecret, "key expansion", concat, i);
        }
        Digest mD5Digest = new MD5Digest();
        Digest sHA1Digest = new SHA1Digest();
        int digestSize = mD5Digest.getDigestSize();
        byte[] bArr = new byte[sHA1Digest.getDigestSize()];
        Object obj = new byte[(i + digestSize)];
        int i2 = 0;
        int i3 = 0;
        while (i2 < i) {
            byte[] bArr2 = SSL3_CONST[i3];
            sHA1Digest.update(bArr2, 0, bArr2.length);
            sHA1Digest.update(securityParameters.masterSecret, 0, securityParameters.masterSecret.length);
            sHA1Digest.update(concat, 0, concat.length);
            sHA1Digest.doFinal(bArr, 0);
            mD5Digest.update(securityParameters.masterSecret, 0, securityParameters.masterSecret.length);
            mD5Digest.update(bArr, 0, bArr.length);
            mD5Digest.doFinal(obj, i2);
            i2 += digestSize;
            i3++;
        }
        Object obj2 = new byte[i];
        System.arraycopy(obj, 0, obj2, 0, i);
        return obj2;
    }

    static byte[] calculateMasterSecret(TlsClientContext tlsClientContext, byte[] bArr) {
        ProtocolVersion serverVersion = tlsClientContext.getServerVersion();
        SecurityParameters securityParameters = tlsClientContext.getSecurityParameters();
        byte[] concat = concat(securityParameters.clientRandom, securityParameters.serverRandom);
        if ((serverVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : 0) != 0) {
            return PRF(bArr, "master secret", concat, 48);
        }
        Digest mD5Digest = new MD5Digest();
        Digest sHA1Digest = new SHA1Digest();
        int digestSize = mD5Digest.getDigestSize();
        byte[] bArr2 = new byte[sHA1Digest.getDigestSize()];
        byte[] bArr3 = new byte[(digestSize * 3)];
        int i = 0;
        for (int i2 = 0; i2 < 3; i2++) {
            byte[] bArr4 = SSL3_CONST[i2];
            sHA1Digest.update(bArr4, 0, bArr4.length);
            sHA1Digest.update(bArr, 0, bArr.length);
            sHA1Digest.update(concat, 0, concat.length);
            sHA1Digest.doFinal(bArr2, 0);
            mD5Digest.update(bArr, 0, bArr.length);
            mD5Digest.update(bArr2, 0, bArr2.length);
            mD5Digest.doFinal(bArr3, i);
            i += digestSize;
        }
        return bArr3;
    }

    static byte[] calculateVerifyData(TlsClientContext tlsClientContext, String str, byte[] bArr) {
        return (tlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion() ? 1 : null) != null ? PRF(tlsClientContext.getSecurityParameters().masterSecret, str, bArr, 12) : bArr;
    }

    static byte[] concat(byte[] bArr, byte[] bArr2) {
        Object obj = new byte[(bArr.length + bArr2.length)];
        System.arraycopy(bArr, 0, obj, 0, bArr.length);
        System.arraycopy(bArr2, 0, obj, bArr.length, bArr2.length);
        return obj;
    }

    private static byte[][] genConst() {
        byte[][] bArr = new byte[10][];
        for (int i = 0; i < 10; i++) {
            byte[] bArr2 = new byte[(i + 1)];
            Arrays.fill(bArr2, (byte) (i + 65));
            bArr[i] = bArr2;
        }
        return bArr;
    }

    private static void hmac_hash(Digest digest, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        HMac hMac = new HMac(digest);
        CipherParameters keyParameter = new KeyParameter(bArr);
        int digestSize = digest.getDigestSize();
        int length = ((bArr3.length + digestSize) - 1) / digestSize;
        byte[] bArr4 = new byte[hMac.getMacSize()];
        Object obj = new byte[hMac.getMacSize()];
        int i = 0;
        byte[] bArr5 = bArr2;
        while (i < length) {
            hMac.init(keyParameter);
            hMac.update(bArr5, 0, bArr5.length);
            hMac.doFinal(bArr4, 0);
            hMac.init(keyParameter);
            hMac.update(bArr4, 0, bArr4.length);
            hMac.update(bArr2, 0, bArr2.length);
            hMac.doFinal(obj, 0);
            System.arraycopy(obj, 0, bArr3, digestSize * i, Math.min(digestSize, bArr3.length - (digestSize * i)));
            i++;
            bArr5 = bArr4;
        }
    }

    protected static void readFully(byte[] bArr, InputStream inputStream) throws IOException {
        if (Streams.readFully(inputStream, bArr) != bArr.length) {
            throw new EOFException();
        }
    }

    protected static byte[] readOpaque16(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[readUint16(inputStream)];
        readFully(bArr, inputStream);
        return bArr;
    }

    protected static byte[] readOpaque8(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[readUint8(inputStream)];
        readFully(bArr, inputStream);
        return bArr;
    }

    protected static int readUint16(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        int read2 = inputStream.read();
        if ((read | read2) >= 0) {
            return (read << 8) | read2;
        }
        throw new EOFException();
    }

    protected static int readUint24(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        int read2 = inputStream.read();
        int read3 = inputStream.read();
        if (((read | read2) | read3) >= 0) {
            return ((read << 16) | (read2 << 8)) | read3;
        }
        throw new EOFException();
    }

    protected static long readUint32(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        int read2 = inputStream.read();
        int read3 = inputStream.read();
        int read4 = inputStream.read();
        if ((((read | read2) | read3) | read4) < 0) {
            throw new EOFException();
        }
        return (((((long) read2) << 16) | (((long) read) << 24)) | (((long) read3) << 8)) | ((long) read4);
    }

    protected static short readUint8(InputStream inputStream) throws IOException {
        int read = inputStream.read();
        if (read != -1) {
            return (short) read;
        }
        throw new EOFException();
    }

    static ProtocolVersion readVersion(InputStream inputStream) throws IOException {
        return ProtocolVersion.get(inputStream.read(), inputStream.read());
    }

    static ProtocolVersion readVersion(byte[] bArr) throws IOException {
        return ProtocolVersion.get(bArr[0], bArr[1]);
    }

    static void validateKeyUsage(X509CertificateStructure x509CertificateStructure, int i) throws IOException {
        X509Extensions extensions = x509CertificateStructure.getTBSCertificate().getExtensions();
        if (extensions != null) {
            X509Extension extension = extensions.getExtension(X509Extension.keyUsage);
            if (extension != null && ((KeyUsage.getInstance(extension).getBytes()[0] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV) & i) != i) {
                throw new TlsFatalAlert((short) 46);
            }
        }
    }

    protected static void writeGMTUnixTime(byte[] bArr, int i) {
        int currentTimeMillis = (int) (System.currentTimeMillis() / 1000);
        bArr[i] = (byte) (currentTimeMillis >> 24);
        bArr[i + 1] = (byte) (currentTimeMillis >> 16);
        bArr[i + 2] = (byte) (currentTimeMillis >> 8);
        bArr[i + 3] = (byte) currentTimeMillis;
    }

    protected static void writeOpaque16(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint16(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    protected static void writeOpaque24(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint24(bArr.length, outputStream);
        outputStream.write(bArr);
    }

    protected static void writeOpaque8(byte[] bArr, OutputStream outputStream) throws IOException {
        writeUint8((short) bArr.length, outputStream);
        outputStream.write(bArr);
    }

    protected static void writeUint16(int i, OutputStream outputStream) throws IOException {
        outputStream.write(i >> 8);
        outputStream.write(i);
    }

    protected static void writeUint16(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >> 8);
        bArr[i2 + 1] = (byte) i;
    }

    protected static void writeUint16Array(int[] iArr, OutputStream outputStream) throws IOException {
        for (int writeUint16 : iArr) {
            writeUint16(writeUint16, outputStream);
        }
    }

    protected static void writeUint24(int i, OutputStream outputStream) throws IOException {
        outputStream.write(i >> 16);
        outputStream.write(i >> 8);
        outputStream.write(i);
    }

    protected static void writeUint24(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) (i >> 16);
        bArr[i2 + 1] = (byte) (i >> 8);
        bArr[i2 + 2] = (byte) i;
    }

    protected static void writeUint32(long j, OutputStream outputStream) throws IOException {
        outputStream.write((int) (j >> 24));
        outputStream.write((int) (j >> 16));
        outputStream.write((int) (j >> 8));
        outputStream.write((int) j);
    }

    protected static void writeUint32(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) (j >> 24));
        bArr[i + 1] = (byte) ((int) (j >> 16));
        bArr[i + 2] = (byte) ((int) (j >> 8));
        bArr[i + 3] = (byte) ((int) j);
    }

    protected static void writeUint64(long j, OutputStream outputStream) throws IOException {
        outputStream.write((int) (j >> 56));
        outputStream.write((int) (j >> 48));
        outputStream.write((int) (j >> 40));
        outputStream.write((int) (j >> 32));
        outputStream.write((int) (j >> 24));
        outputStream.write((int) (j >> 16));
        outputStream.write((int) (j >> 8));
        outputStream.write((int) j);
    }

    protected static void writeUint64(long j, byte[] bArr, int i) {
        bArr[i] = (byte) ((int) (j >> 56));
        bArr[i + 1] = (byte) ((int) (j >> 48));
        bArr[i + 2] = (byte) ((int) (j >> 40));
        bArr[i + 3] = (byte) ((int) (j >> 32));
        bArr[i + 4] = (byte) ((int) (j >> 24));
        bArr[i + 5] = (byte) ((int) (j >> 16));
        bArr[i + 6] = (byte) ((int) (j >> 8));
        bArr[i + 7] = (byte) ((int) j);
    }

    protected static void writeUint8(short s, OutputStream outputStream) throws IOException {
        outputStream.write(s);
    }

    protected static void writeUint8(short s, byte[] bArr, int i) {
        bArr[i] = (byte) s;
    }

    protected static void writeUint8Array(short[] sArr, OutputStream outputStream) throws IOException {
        for (short writeUint8 : sArr) {
            writeUint8(writeUint8, outputStream);
        }
    }

    static void writeVersion(ProtocolVersion protocolVersion, OutputStream outputStream) throws IOException {
        outputStream.write(protocolVersion.getMajorVersion());
        outputStream.write(protocolVersion.getMinorVersion());
    }

    static void writeVersion(ProtocolVersion protocolVersion, byte[] bArr, int i) throws IOException {
        bArr[i] = (byte) protocolVersion.getMajorVersion();
        bArr[i + 1] = (byte) protocolVersion.getMinorVersion();
    }
}
