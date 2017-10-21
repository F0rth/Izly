package org.kobjects.io;

import java.io.IOException;
import java.io.Reader;
import org.spongycastle.asn1.eac.CertificateBody;

public class LookAheadReader extends Reader {
    char[] buf;
    int bufPos;
    int bufValid;
    Reader reader;

    public LookAheadReader(Reader reader) {
        this.buf = new char[(Runtime.getRuntime().freeMemory() > 1000000 ? 16384 : 128)];
        this.bufPos = 0;
        this.bufValid = 0;
        this.reader = reader;
    }

    public void close() throws IOException {
        this.reader.close();
    }

    public int peek(int i) throws IOException {
        if (i > CertificateBody.profileType) {
            throw new RuntimeException("peek > 127 not supported!");
        }
        while (i >= this.bufValid) {
            int length = (this.bufPos + this.bufValid) % this.buf.length;
            length = this.reader.read(this.buf, length, Math.min(this.buf.length - length, this.buf.length - this.bufValid));
            if (length == -1) {
                return -1;
            }
            this.bufValid = length + this.bufValid;
        }
        return this.buf[this.bufPos + (i % this.buf.length)];
    }

    public int read() throws IOException {
        int peek = peek(0);
        if (peek != -1) {
            int i = this.bufPos + 1;
            this.bufPos = i;
            if (i == this.buf.length) {
                this.bufPos = 0;
            }
            this.bufValid--;
        }
        return peek;
    }

    public int read(char[] cArr, int i, int i2) throws IOException {
        int i3 = -1;
        if (!(this.bufValid == 0 && peek(0) == -1)) {
            if (i2 > this.bufValid) {
                i2 = this.bufValid;
            }
            i3 = i2 > this.buf.length - this.bufPos ? this.buf.length - this.bufPos : i2;
            System.arraycopy(this.buf, this.bufPos, cArr, i, i3);
            this.bufValid -= i3;
            this.bufPos += i3;
            if (this.bufPos > this.buf.length) {
                this.bufPos -= this.buf.length;
            }
        }
        return i3;
    }

    public String readLine() throws IOException {
        if (peek(0) == -1) {
            return null;
        }
        String readTo = readTo("\r\n");
        if (read() != 13 || peek(0) != 10) {
            return readTo;
        }
        read();
        return readTo;
    }

    public String readTo(char c) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (peek(0) != -1 && peek(0) != c) {
            stringBuffer.append((char) read());
        }
        return stringBuffer.toString();
    }

    public String readTo(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (peek(0) != -1 && str.indexOf((char) peek(0)) == -1) {
            stringBuffer.append((char) read());
        }
        return stringBuffer.toString();
    }

    public String readWhile(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (peek(0) != -1 && str.indexOf((char) peek(0)) != -1) {
            stringBuffer.append((char) read());
        }
        return stringBuffer.toString();
    }

    public void skip(String str) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        while (peek(0) != -1 && str.indexOf((char) peek(0)) != -1) {
            read();
        }
    }
}
