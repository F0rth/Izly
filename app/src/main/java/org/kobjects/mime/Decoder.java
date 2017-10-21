package org.kobjects.mime;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import org.kobjects.base64.Base64;

public class Decoder {
    String boundary;
    char[] buf;
    String characterEncoding;
    boolean consumed;
    boolean eof;
    Hashtable header;
    InputStream is;

    public Decoder(InputStream inputStream, String str) throws IOException {
        this(inputStream, str, null);
    }

    public Decoder(InputStream inputStream, String str, String str2) throws IOException {
        this.buf = new char[256];
        this.characterEncoding = str2;
        this.is = inputStream;
        this.boundary = "--" + str;
        String readLine;
        do {
            readLine = readLine();
            if (readLine == null) {
                throw new IOException("Unexpected EOF");
            }
        } while (!readLine.startsWith(this.boundary));
        if (readLine.endsWith("--")) {
            this.eof = true;
            inputStream.close();
        }
        this.consumed = true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.util.Hashtable getHeaderElements(java.lang.String r9) {
        /*
        r8 = 59;
        r7 = 34;
        r6 = -1;
        r0 = "";
        r1 = 0;
        r2 = new java.util.Hashtable;
        r2.<init>();
        r3 = r9.length();
    L_0x0011:
        if (r1 >= r3) goto L_0x001e;
    L_0x0013:
        r4 = r9.charAt(r1);
        r5 = 32;
        if (r4 > r5) goto L_0x001e;
    L_0x001b:
        r1 = r1 + 1;
        goto L_0x0011;
    L_0x001e:
        if (r1 >= r3) goto L_0x0078;
    L_0x0020:
        r4 = r9.charAt(r1);
        if (r4 != r7) goto L_0x006b;
    L_0x0026:
        r1 = r1 + 1;
        r4 = r9.indexOf(r7, r1);
        if (r4 != r6) goto L_0x0043;
    L_0x002e:
        r0 = new java.lang.RuntimeException;
        r1 = new java.lang.StringBuilder;
        r2 = "End quote expected in ";
        r1.<init>(r2);
        r1 = r1.append(r9);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0043:
        r1 = r9.substring(r1, r4);
        r2.put(r0, r1);
        r0 = r4 + 2;
        if (r0 >= r3) goto L_0x0078;
    L_0x004e:
        r1 = r0 + -1;
        r1 = r9.charAt(r1);
        if (r1 == r8) goto L_0x0082;
    L_0x0056:
        r0 = new java.lang.RuntimeException;
        r1 = new java.lang.StringBuilder;
        r2 = "; expected in ";
        r1.<init>(r2);
        r1 = r1.append(r9);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x006b:
        r4 = r9.indexOf(r8, r1);
        if (r4 != r6) goto L_0x0079;
    L_0x0071:
        r1 = r9.substring(r1);
        r2.put(r0, r1);
    L_0x0078:
        return r2;
    L_0x0079:
        r1 = r9.substring(r1, r4);
        r2.put(r0, r1);
        r0 = r4 + 1;
    L_0x0082:
        r1 = 61;
        r1 = r9.indexOf(r1, r0);
        if (r1 == r6) goto L_0x0078;
    L_0x008a:
        r0 = r9.substring(r0, r1);
        r0 = r0.toLowerCase();
        r0 = r0.trim();
        r1 = r1 + 1;
        goto L_0x0011;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.kobjects.mime.Decoder.getHeaderElements(java.lang.String):java.util.Hashtable");
    }

    private final String readLine() throws IOException {
        int i = 0;
        while (true) {
            int read = this.is.read();
            if (read == -1 && i == 0) {
                return null;
            }
            if (read != -1 && read != 10) {
                if (read != 13) {
                    if (i >= this.buf.length) {
                        Object obj = new char[((this.buf.length * 3) / 2)];
                        System.arraycopy(this.buf, 0, obj, 0, this.buf.length);
                        this.buf = obj;
                    }
                    this.buf[i] = (char) read;
                    i++;
                }
            }
        }
        return new String(this.buf, 0, i);
    }

    public String getHeader(String str) {
        return (String) this.header.get(str.toLowerCase());
    }

    public Enumeration getHeaderNames() {
        return this.header.keys();
    }

    public boolean next() throws IOException {
        if (!this.consumed) {
            readContent(null);
        }
        if (this.eof) {
            return false;
        }
        this.header = new Hashtable();
        while (true) {
            String readLine = readLine();
            if (readLine == null || readLine.equals("")) {
                this.consumed = false;
            } else {
                int indexOf = readLine.indexOf(58);
                if (indexOf == -1) {
                    throw new IOException("colon missing in multipart header line: " + readLine);
                }
                this.header.put(readLine.substring(0, indexOf).trim().toLowerCase(), readLine.substring(indexOf + 1).trim());
            }
        }
        this.consumed = false;
        return true;
    }

    public String readContent() throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        readContent(byteArrayOutputStream);
        String str = this.characterEncoding == null ? new String(byteArrayOutputStream.toByteArray()) : new String(byteArrayOutputStream.toByteArray(), this.characterEncoding);
        System.out.println("Field content: '" + str + "'");
        return str;
    }

    public void readContent(OutputStream outputStream) throws IOException {
        if (this.consumed) {
            throw new RuntimeException("Content already consumed!");
        }
        getHeader("Content-Type");
        String readLine;
        if ("base64".equals(getHeader("Content-Transfer-Encoding"))) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                readLine = readLine();
                if (readLine != null) {
                    if (readLine.startsWith(this.boundary)) {
                        break;
                    }
                    Base64.decode(readLine, outputStream);
                } else {
                    throw new IOException("Unexpected EOF");
                }
            }
        }
        String str = "\r\n" + this.boundary;
        int i = 0;
        while (true) {
            int read = this.is.read();
            if (read == -1) {
                throw new RuntimeException("Unexpected EOF");
            } else if (((char) read) == str.charAt(i)) {
                i++;
                if (i == str.length()) {
                    break;
                }
            } else {
                if (i > 0) {
                    for (int i2 = 0; i2 < i; i2++) {
                        outputStream.write((byte) str.charAt(i2));
                    }
                    i = ((char) read) == str.charAt(0) ? 1 : 0;
                }
                if (i == 0) {
                    outputStream.write((byte) read);
                }
            }
        }
        readLine = readLine();
        if (readLine.endsWith("--")) {
            this.eof = true;
        }
        this.consumed = true;
    }
}
