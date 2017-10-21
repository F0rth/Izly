package org.spongycastle.asn1.x509;

public class X509NameTokenizer {
    private StringBuffer buf;
    private int index;
    private char seperator;
    private String value;

    public X509NameTokenizer(String str) {
        this(str, ',');
    }

    public X509NameTokenizer(String str, char c) {
        this.buf = new StringBuffer();
        this.value = str;
        this.index = -1;
        this.seperator = c;
    }

    public boolean hasMoreTokens() {
        return this.index != this.value.length();
    }

    public String nextToken() {
        if (this.index == this.value.length()) {
            return null;
        }
        int i = this.index;
        this.buf.setLength(0);
        i++;
        int i2 = 0;
        int i3 = 0;
        while (i != this.value.length()) {
            char charAt = this.value.charAt(i);
            if (charAt != '\"') {
                if (i3 == 0 && i2 == 0) {
                    if (charAt != '\\') {
                        if (charAt == this.seperator) {
                            break;
                        }
                        this.buf.append(charAt);
                    } else {
                        i3 = 1;
                    }
                } else {
                    if (charAt == '#' && this.buf.charAt(this.buf.length() - 1) == '=') {
                        this.buf.append('\\');
                    } else if (charAt == '+' && this.seperator != '+') {
                        this.buf.append('\\');
                    }
                    this.buf.append(charAt);
                    i3 = 0;
                }
            } else {
                if (i3 == 0) {
                    i2 = i2 == 0 ? 1 : 0;
                } else {
                    this.buf.append(charAt);
                }
                i3 = 0;
            }
            i++;
        }
        this.index = i;
        return this.buf.toString().trim();
    }
}
