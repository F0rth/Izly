package okhttp3.internal.tls;

import javax.security.auth.x500.X500Principal;

final class DistinguishedNameParser {
    private int beg;
    private char[] chars;
    private int cur;
    private final String dn;
    private int end;
    private final int length = this.dn.length();
    private int pos;

    public DistinguishedNameParser(X500Principal x500Principal) {
        this.dn = x500Principal.getName("RFC2253");
    }

    private String escapedAV() {
        this.beg = this.pos;
        this.end = this.pos;
        while (this.pos < this.length) {
            char[] cArr;
            int i;
            switch (this.chars[this.pos]) {
                case ' ':
                    this.cur = this.end;
                    this.pos++;
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = ' ';
                    while (this.pos < this.length && this.chars[this.pos] == ' ') {
                        cArr = this.chars;
                        i = this.end;
                        this.end = i + 1;
                        cArr[i] = ' ';
                        this.pos++;
                    }
                    if (this.pos != this.length && this.chars[this.pos] != ',' && this.chars[this.pos] != '+' && this.chars[this.pos] != ';') {
                        break;
                    }
                    return new String(this.chars, this.beg, this.cur - this.beg);
                    break;
                case '+':
                case ',':
                case ';':
                    return new String(this.chars, this.beg, this.end - this.beg);
                case '\\':
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = getEscaped();
                    this.pos++;
                    break;
                default:
                    cArr = this.chars;
                    i = this.end;
                    this.end = i + 1;
                    cArr[i] = this.chars[this.pos];
                    this.pos++;
                    break;
            }
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private int getByte(int i) {
        if (i + 1 >= this.length) {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        }
        int i2;
        int i3;
        char c = this.chars[i];
        if (c >= '0' && c <= '9') {
            i2 = c - 48;
        } else if (c >= 'a' && c <= 'f') {
            i2 = c - 87;
        } else if (c < 'A' || c > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        } else {
            i2 = c - 55;
        }
        char c2 = this.chars[i + 1];
        if (c2 >= '0' && c2 <= '9') {
            i3 = c2 - 48;
        } else if (c2 >= 'a' && c2 <= 'f') {
            i3 = c2 - 87;
        } else if (c2 < 'A' || c2 > 'F') {
            throw new IllegalStateException("Malformed DN: " + this.dn);
        } else {
            i3 = c2 - 55;
        }
        return (i2 << 4) + i3;
    }

    private char getEscaped() {
        this.pos++;
        if (this.pos == this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        switch (this.chars[this.pos]) {
            case ' ':
            case '\"':
            case '#':
            case '%':
            case '*':
            case '+':
            case ',':
            case ';':
            case '<':
            case '=':
            case '>':
            case '\\':
            case '_':
                return this.chars[this.pos];
            default:
                return getUTF8();
        }
    }

    private char getUTF8() {
        int i = getByte(this.pos);
        this.pos++;
        if (i < 128) {
            return (char) i;
        }
        if (i < 192 || i > 247) {
            return '?';
        }
        int i2;
        if (i <= 223) {
            i2 = 1;
            i &= 31;
        } else if (i <= 239) {
            i2 = 2;
            i &= 15;
        } else {
            i2 = 3;
            i &= 7;
        }
        for (int i3 = 0; i3 < i2; i3++) {
            this.pos++;
            if (this.pos == this.length || this.chars[this.pos] != '\\') {
                return '?';
            }
            this.pos++;
            int i4 = getByte(this.pos);
            this.pos++;
            if ((i4 & 192) != 128) {
                return '?';
            }
            i = (i << 6) + (i4 & 63);
        }
        return (char) i;
    }

    private String hexAV() {
        if (this.pos + 4 >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        int i;
        this.beg = this.pos;
        this.pos++;
        while (this.pos != this.length && this.chars[this.pos] != '+' && this.chars[this.pos] != ',' && this.chars[this.pos] != ';') {
            int i2;
            if (this.chars[this.pos] == ' ') {
                this.end = this.pos;
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                i = this.end - this.beg;
                if (i >= 5 || (i & 1) == 0) {
                    throw new IllegalStateException("Unexpected end of DN: " + this.dn);
                }
                byte[] bArr = new byte[(i / 2)];
                int i3 = this.beg + 1;
                for (i2 = 0; i2 < bArr.length; i2++) {
                    bArr[i2] = (byte) getByte(i3);
                    i3 += 2;
                }
                return new String(this.chars, this.beg, i);
            }
            if (this.chars[this.pos] >= 'A' && this.chars[this.pos] <= 'F') {
                char[] cArr = this.chars;
                i2 = this.pos;
                cArr[i2] = (char) (cArr[i2] + 32);
            }
            this.pos++;
        }
        this.end = this.pos;
        i = this.end - this.beg;
        if (i >= 5) {
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    private String nextAT() {
        while (this.pos < this.length && this.chars[this.pos] == ' ') {
            this.pos++;
        }
        if (this.pos == this.length) {
            return null;
        }
        this.beg = this.pos;
        this.pos++;
        while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] != ' ') {
            this.pos++;
        }
        if (this.pos >= this.length) {
            throw new IllegalStateException("Unexpected end of DN: " + this.dn);
        }
        this.end = this.pos;
        if (this.chars[this.pos] == ' ') {
            while (this.pos < this.length && this.chars[this.pos] != '=' && this.chars[this.pos] == ' ') {
                this.pos++;
            }
            if (this.chars[this.pos] != '=' || this.pos == this.length) {
                throw new IllegalStateException("Unexpected end of DN: " + this.dn);
            }
        }
        do {
            this.pos++;
            if (this.pos >= this.length) {
                break;
            }
        } while (this.chars[this.pos] == ' ');
        if (this.end - this.beg > 4 && this.chars[this.beg + 3] == '.' && ((this.chars[this.beg] == 'O' || this.chars[this.beg] == 'o') && ((this.chars[this.beg + 1] == 'I' || this.chars[this.beg + 1] == 'i') && (this.chars[this.beg + 2] == 'D' || this.chars[this.beg + 2] == 'd')))) {
            this.beg += 4;
        }
        return new String(this.chars, this.beg, this.end - this.beg);
    }

    private String quotedAV() {
        this.pos++;
        this.beg = this.pos;
        this.end = this.beg;
        while (this.pos != this.length) {
            if (this.chars[this.pos] == '\"') {
                this.pos++;
                while (this.pos < this.length && this.chars[this.pos] == ' ') {
                    this.pos++;
                }
                return new String(this.chars, this.beg, this.end - this.beg);
            }
            if (this.chars[this.pos] == '\\') {
                this.chars[this.end] = getEscaped();
            } else {
                this.chars[this.end] = this.chars[this.pos];
            }
            this.pos++;
            this.end++;
        }
        throw new IllegalStateException("Unexpected end of DN: " + this.dn);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.String findMostSpecific(java.lang.String r6) {
        /*
        r5 = this;
        r2 = 0;
        r0 = 0;
        r5.pos = r0;
        r5.beg = r0;
        r5.end = r0;
        r5.cur = r0;
        r0 = r5.dn;
        r0 = r0.toCharArray();
        r5.chars = r0;
        r0 = r5.nextAT();
        if (r0 != 0) goto L_0x001b;
    L_0x0018:
        r1 = r2;
    L_0x0019:
        r2 = r1;
    L_0x001a:
        return r2;
    L_0x001b:
        r1 = "";
        r3 = r5.pos;
        r4 = r5.length;
        if (r3 == r4) goto L_0x001a;
    L_0x0023:
        r3 = r5.chars;
        r4 = r5.pos;
        r3 = r3[r4];
        switch(r3) {
            case 34: goto L_0x0071;
            case 35: goto L_0x0076;
            case 43: goto L_0x0030;
            case 44: goto L_0x0030;
            case 59: goto L_0x0030;
            default: goto L_0x002c;
        };
    L_0x002c:
        r1 = r5.escapedAV();
    L_0x0030:
        r0 = r6.equalsIgnoreCase(r0);
        if (r0 != 0) goto L_0x0019;
    L_0x0036:
        r0 = r5.pos;
        r1 = r5.length;
        if (r0 >= r1) goto L_0x001a;
    L_0x003c:
        r0 = r5.chars;
        r1 = r5.pos;
        r0 = r0[r1];
        r1 = 44;
        if (r0 == r1) goto L_0x007b;
    L_0x0046:
        r0 = r5.chars;
        r1 = r5.pos;
        r0 = r0[r1];
        r1 = 59;
        if (r0 == r1) goto L_0x007b;
    L_0x0050:
        r0 = r5.chars;
        r1 = r5.pos;
        r0 = r0[r1];
        r1 = 43;
        if (r0 == r1) goto L_0x007b;
    L_0x005a:
        r0 = new java.lang.IllegalStateException;
        r1 = new java.lang.StringBuilder;
        r2 = "Malformed DN: ";
        r1.<init>(r2);
        r2 = r5.dn;
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
    L_0x0071:
        r1 = r5.quotedAV();
        goto L_0x0030;
    L_0x0076:
        r1 = r5.hexAV();
        goto L_0x0030;
    L_0x007b:
        r0 = r5.pos;
        r0 = r0 + 1;
        r5.pos = r0;
        r0 = r5.nextAT();
        if (r0 != 0) goto L_0x001b;
    L_0x0087:
        r0 = new java.lang.IllegalStateException;
        r1 = new java.lang.StringBuilder;
        r2 = "Malformed DN: ";
        r1.<init>(r2);
        r2 = r5.dn;
        r1 = r1.append(r2);
        r1 = r1.toString();
        r0.<init>(r1);
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.internal.tls.DistinguishedNameParser.findMostSpecific(java.lang.String):java.lang.String");
    }
}
