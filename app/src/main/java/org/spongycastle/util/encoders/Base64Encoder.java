package org.spongycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.tls.CipherSuite;

public class Base64Encoder implements Encoder {
    protected final byte[] decodingTable = new byte[128];
    protected final byte[] encodingTable = new byte[]{(byte) 65, (byte) 66, (byte) 67, (byte) 68, (byte) 69, (byte) 70, (byte) 71, (byte) 72, (byte) 73, (byte) 74, (byte) 75, (byte) 76, (byte) 77, (byte) 78, (byte) 79, (byte) 80, (byte) 81, (byte) 82, (byte) 83, (byte) 84, (byte) 85, (byte) 86, (byte) 87, (byte) 88, (byte) 89, (byte) 90, (byte) 97, (byte) 98, (byte) 99, (byte) 100, (byte) 101, (byte) 102, (byte) 103, (byte) 104, (byte) 105, (byte) 106, (byte) 107, (byte) 108, (byte) 109, (byte) 110, (byte) 111, (byte) 112, (byte) 113, (byte) 114, (byte) 115, (byte) 116, (byte) 117, (byte) 118, (byte) 119, (byte) 120, (byte) 121, (byte) 122, (byte) 48, (byte) 49, (byte) 50, (byte) 51, (byte) 52, (byte) 53, (byte) 54, (byte) 55, (byte) 56, (byte) 57, (byte) 43, (byte) 47};
    protected byte padding = (byte) 61;

    public Base64Encoder() {
        initialiseDecodingTable();
    }

    private int decodeLastBlock(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        if (c3 == this.padding) {
            outputStream.write((this.decodingTable[c] << 2) | (this.decodingTable[c2] >> 4));
            return 1;
        } else if (c4 == this.padding) {
            r0 = this.decodingTable[c];
            r1 = this.decodingTable[c2];
            r2 = this.decodingTable[c3];
            outputStream.write((r0 << 2) | (r1 >> 4));
            outputStream.write((r1 << 4) | (r2 >> 2));
            return 2;
        } else {
            r0 = this.decodingTable[c];
            r1 = this.decodingTable[c2];
            r2 = this.decodingTable[c3];
            byte b = this.decodingTable[c4];
            outputStream.write((r0 << 2) | (r1 >> 4));
            outputStream.write((r1 << 4) | (r2 >> 2));
            outputStream.write((r2 << 6) | b);
            return 3;
        }
    }

    private boolean ignore(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    private int nextI(String str, int i, int i2) {
        while (i < i2 && ignore(str.charAt(i))) {
            i++;
        }
        return i;
    }

    private int nextI(byte[] bArr, int i, int i2) {
        while (i < i2 && ignore((char) bArr[i])) {
            i++;
        }
        return i;
    }

    public int decode(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && ignore(str.charAt(length - 1))) {
            length--;
        }
        int i = length - 4;
        int nextI = nextI(str, 0, i);
        int i2 = 0;
        while (nextI < i) {
            byte b = this.decodingTable[str.charAt(nextI)];
            nextI = nextI(str, nextI + 1, i);
            byte b2 = this.decodingTable[str.charAt(nextI)];
            nextI = nextI(str, nextI + 1, i);
            byte b3 = this.decodingTable[str.charAt(nextI)];
            nextI = nextI(str, nextI + 1, i);
            byte b4 = this.decodingTable[str.charAt(nextI)];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            nextI = nextI(str, nextI + 1, i);
            i2 += 3;
        }
        return decodeLastBlock(outputStream, str.charAt(length - 4), str.charAt(length - 3), str.charAt(length - 2), str.charAt(length - 1)) + i2;
    }

    public int decode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        int i3 = i + i2;
        while (i3 > i && ignore((char) bArr[i3 - 1])) {
            i3--;
        }
        int i4 = i3 - 4;
        int nextI = nextI(bArr, i, i4);
        int i5 = 0;
        while (nextI < i4) {
            byte b = this.decodingTable[bArr[nextI]];
            nextI = nextI(bArr, nextI + 1, i4);
            byte b2 = this.decodingTable[bArr[nextI]];
            nextI = nextI(bArr, nextI + 1, i4);
            byte b3 = this.decodingTable[bArr[nextI]];
            nextI = nextI(bArr, nextI + 1, i4);
            byte b4 = this.decodingTable[bArr[nextI]];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            nextI = nextI(bArr, nextI + 1, i4);
            i5 += 3;
        }
        return decodeLastBlock(outputStream, (char) bArr[i3 - 4], (char) bArr[i3 - 3], (char) bArr[i3 - 2], (char) bArr[i3 - 1]) + i5;
    }

    public int encode(byte[] bArr, int i, int i2, OutputStream outputStream) throws IOException {
        int i3;
        int i4 = i2 % 3;
        int i5 = i2 - i4;
        for (i3 = i; i3 < i + i5; i3 += 3) {
            int i6 = bArr[i3] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            int i7 = bArr[i3 + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            int i8 = bArr[i3 + 2] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
            outputStream.write(this.encodingTable[(i6 >>> 2) & 63]);
            outputStream.write(this.encodingTable[((i6 << 4) | (i7 >>> 4)) & 63]);
            outputStream.write(this.encodingTable[((i7 << 2) | (i8 >>> 6)) & 63]);
            outputStream.write(this.encodingTable[i8 & 63]);
        }
        switch (i4) {
            case 1:
                i3 = bArr[i + i5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                outputStream.write(this.encodingTable[(i3 >>> 2) & 63]);
                outputStream.write(this.encodingTable[(i3 << 4) & 63]);
                outputStream.write(this.padding);
                outputStream.write(this.padding);
                break;
            case 2:
                i3 = bArr[i + i5] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                i6 = bArr[(i + i5) + 1] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV;
                outputStream.write(this.encodingTable[(i3 >>> 2) & 63]);
                outputStream.write(this.encodingTable[((i3 << 4) | (i6 >>> 4)) & 63]);
                outputStream.write(this.encodingTable[(i6 << 2) & 63]);
                outputStream.write(this.padding);
                break;
        }
        return (i4 == 0 ? 0 : 4) + ((i5 / 3) * 4);
    }

    protected void initialiseDecodingTable() {
        for (int i = 0; i < this.encodingTable.length; i++) {
            this.decodingTable[this.encodingTable[i]] = (byte) i;
        }
    }
}
