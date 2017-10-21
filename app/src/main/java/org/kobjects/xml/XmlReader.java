package org.kobjects.xml;

import java.io.IOException;
import java.io.Reader;
import java.util.Hashtable;
import org.spongycastle.asn1.cmp.PKIFailureInfo;
import org.spongycastle.asn1.eac.EACTags;

public class XmlReader {
    static final int CDSECT = 5;
    public static final int END_DOCUMENT = 1;
    public static final int END_TAG = 3;
    static final int ENTITY_REF = 6;
    private static final int LEGACY = 999;
    public static final int START_DOCUMENT = 0;
    public static final int START_TAG = 2;
    public static final int TEXT = 4;
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    private String[] TYPES;
    private int attributeCount;
    private String[] attributes;
    private int column;
    private boolean degenerated;
    private int depth;
    private String[] elementStack = new String[4];
    private Hashtable entityMap;
    private boolean eof;
    private boolean isWhitespace;
    private int line;
    private String name;
    private int peek0;
    private int peek1;
    private Reader reader;
    public boolean relaxed;
    private char[] srcBuf;
    private int srcCount;
    private int srcPos;
    private String text;
    private char[] txtBuf;
    private int txtPos;
    private int type;

    public XmlReader(Reader reader) throws IOException {
        this.srcBuf = new char[(Runtime.getRuntime().freeMemory() >= 1048576 ? PKIFailureInfo.certRevoked : 128)];
        this.txtBuf = new char[128];
        this.attributes = new String[16];
        this.TYPES = new String[]{"Start Document", "End Document", "Start Tag", "End Tag", "Text"};
        this.reader = reader;
        this.peek0 = reader.read();
        this.peek1 = reader.read();
        this.eof = this.peek0 == -1;
        this.entityMap = new Hashtable();
        this.entityMap.put("amp", "&");
        this.entityMap.put("apos", "'");
        this.entityMap.put("gt", ">");
        this.entityMap.put("lt", "<");
        this.entityMap.put("quot", "\"");
        this.line = 1;
        this.column = 1;
    }

    private static final String[] ensureCapacity(String[] strArr, int i) {
        if (strArr.length >= i) {
            return strArr;
        }
        Object obj = new String[(i + 16)];
        System.arraycopy(strArr, 0, obj, 0, strArr.length);
        return obj;
    }

    private final void exception(String str) throws IOException {
        throw new IOException(str + " pos: " + getPositionDescription());
    }

    private final void parseDoctype() throws IOException {
        int i = 1;
        while (true) {
            switch (read()) {
                case -1:
                    exception(UNEXPECTED_EOF);
                    break;
                case 60:
                    break;
                case 62:
                    i--;
                    if (i != 0) {
                        continue;
                    } else {
                        return;
                    }
                default:
                    continue;
            }
            i++;
        }
    }

    private final void parseEndTag() throws IOException {
        read();
        read();
        this.name = readName();
        if (this.depth == 0 && !this.relaxed) {
            exception("element stack empty");
        }
        if (this.name.equals(this.elementStack[this.depth - 1])) {
            this.depth--;
        } else if (!this.relaxed) {
            exception("expected: " + this.elementStack[this.depth]);
        }
        skip();
        read('>');
    }

    private final void parseLegacy(boolean z) throws IOException {
        int i = 45;
        String str = "";
        read();
        int read = read();
        if (read == 63) {
            i = 63;
        } else if (read != 33) {
            if (read != 91) {
                exception("cantreachme: " + read);
            }
            str = "CDATA[";
            i = 93;
        } else if (this.peek0 == 45) {
            str = "--";
        } else {
            str = "DOCTYPE";
            i = -1;
        }
        for (read = 0; read < str.length(); read++) {
            read(str.charAt(read));
        }
        if (i == -1) {
            parseDoctype();
            return;
        }
        while (true) {
            if (this.eof) {
                exception(UNEXPECTED_EOF);
            }
            int read2 = read();
            if (z) {
                push(read2);
            }
            if ((i == 63 || read2 == i) && this.peek0 == i && this.peek1 == 62) {
                break;
            }
        }
        read();
        read();
        if (z && i != 63) {
            pop(this.txtPos - 1);
        }
    }

    private final void parseStartTag() throws IOException {
        read();
        this.name = readName();
        this.elementStack = ensureCapacity(this.elementStack, this.depth + 1);
        String[] strArr = this.elementStack;
        int i = this.depth;
        this.depth = i + 1;
        strArr[i] = this.name;
        while (true) {
            skip();
            int i2 = this.peek0;
            if (i2 == 47) {
                this.degenerated = true;
                read();
                skip();
                read('>');
                return;
            } else if (i2 == 62) {
                read();
                return;
            } else {
                if (i2 == -1) {
                    exception(UNEXPECTED_EOF);
                }
                String readName = readName();
                if (readName.length() == 0) {
                    exception("attr name expected");
                }
                skip();
                read('=');
                skip();
                i2 = read();
                if (!(i2 == 39 || i2 == 34)) {
                    if (!this.relaxed) {
                        exception("<" + this.name + ">: invalid delimiter: " + ((char) i2));
                    }
                    i2 = 32;
                }
                int i3 = this.attributeCount;
                this.attributeCount = i3 + 1;
                i3 <<= 1;
                this.attributes = ensureCapacity(this.attributes, i3 + 4);
                this.attributes[i3] = readName;
                i = this.txtPos;
                pushText(i2);
                this.attributes[i3 + 1] = pop(i);
                if (i2 != 32) {
                    read();
                }
            }
        }
    }

    private final int peekType() {
        switch (this.peek0) {
            case -1:
                return 1;
            case 38:
                return 6;
            case 60:
                switch (this.peek1) {
                    case 33:
                    case 63:
                        return LEGACY;
                    case 47:
                        return 3;
                    case 91:
                        return 5;
                    default:
                        return 2;
                }
            default:
                return 4;
        }
    }

    private final String pop(int i) {
        String str = new String(this.txtBuf, i, this.txtPos - i);
        this.txtPos = i;
        return str;
    }

    private final void push(int i) {
        if (i != 0) {
            if (this.txtPos == this.txtBuf.length) {
                Object obj = new char[(((this.txtPos * 4) / 3) + 4)];
                System.arraycopy(this.txtBuf, 0, obj, 0, this.txtPos);
                this.txtBuf = obj;
            }
            char[] cArr = this.txtBuf;
            int i2 = this.txtPos;
            this.txtPos = i2 + 1;
            cArr[i2] = (char) i;
        }
    }

    private final boolean pushText(int i) throws IOException {
        boolean z = true;
        int i2 = this.peek0;
        while (!this.eof && i2 != i && (i != 32 || (i2 > 32 && i2 != 62))) {
            if (i2 != 38) {
                if (i2 > 32) {
                    z = false;
                }
                push(read());
            } else if (!pushEntity()) {
                z = false;
            }
            i2 = this.peek0;
        }
        return z;
    }

    private final int read() throws IOException {
        int i = this.peek0;
        this.peek0 = this.peek1;
        if (this.peek0 == -1) {
            this.eof = true;
        } else {
            if (i == 10 || i == 13) {
                this.line++;
                this.column = 0;
                if (i == 13 && this.peek0 == 10) {
                    this.peek0 = 0;
                }
            }
            this.column++;
            if (this.srcPos >= this.srcCount) {
                this.srcCount = this.reader.read(this.srcBuf, 0, this.srcBuf.length);
                if (this.srcCount <= 0) {
                    this.peek1 = -1;
                } else {
                    this.srcPos = 0;
                }
            }
            char[] cArr = this.srcBuf;
            int i2 = this.srcPos;
            this.srcPos = i2 + 1;
            this.peek1 = cArr[i2];
        }
        return i;
    }

    private final void read(char c) throws IOException {
        if (read() == c) {
            return;
        }
        if (!this.relaxed) {
            exception("expected: '" + c + "'");
        } else if (c <= ' ') {
            skip();
            read();
        }
    }

    private final String readName() throws IOException {
        int i = this.txtPos;
        int i2 = this.peek0;
        if ((i2 < 97 || i2 > EACTags.SECURITY_SUPPORT_TEMPLATE) && !((i2 >= 65 && i2 <= 90) || i2 == 95 || i2 == 58 || this.relaxed)) {
            exception("name expected");
        }
        while (true) {
            push(read());
            i2 = this.peek0;
            if ((i2 < 97 || i2 > EACTags.SECURITY_SUPPORT_TEMPLATE) && ((i2 < 65 || i2 > 90) && !((i2 >= 48 && i2 <= 57) || i2 == 95 || i2 == 45 || i2 == 58 || i2 == 46))) {
                return pop(i);
            }
        }
    }

    private final void skip() throws IOException {
        while (!this.eof && this.peek0 <= 32) {
            read();
        }
    }

    public void defineCharacterEntity(String str, String str2) {
        this.entityMap.put(str, str2);
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public String getAttributeName(int i) {
        if (i < this.attributeCount) {
            return this.attributes[i << 1];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(int i) {
        if (i < this.attributeCount) {
            return this.attributes[(i << 1) + 1];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(String str) {
        for (int i = (this.attributeCount << 1) - 2; i >= 0; i -= 2) {
            if (this.attributes[i].equals(str)) {
                return this.attributes[i + 1];
            }
        }
        return null;
    }

    public int getColumnNumber() {
        return this.column;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getLineNumber() {
        return this.line;
    }

    public String getName() {
        return this.name;
    }

    public String getPositionDescription() {
        StringBuffer stringBuffer = new StringBuffer(this.type < this.TYPES.length ? this.TYPES[this.type] : "Other");
        stringBuffer.append(" @" + this.line + ":" + this.column + ": ");
        if (this.type == 2 || this.type == 3) {
            stringBuffer.append('<');
            if (this.type == 3) {
                stringBuffer.append('/');
            }
            stringBuffer.append(this.name);
            stringBuffer.append('>');
        } else if (this.isWhitespace) {
            stringBuffer.append("[whitespace]");
        } else {
            stringBuffer.append(getText());
        }
        return stringBuffer.toString();
    }

    public String getText() {
        if (this.text == null) {
            this.text = pop(0);
        }
        return this.text;
    }

    public int getType() {
        return this.type;
    }

    public boolean isEmptyElementTag() {
        return this.degenerated;
    }

    public boolean isWhitespace() {
        return this.isWhitespace;
    }

    public int next() throws IOException {
        int i = 0;
        if (this.degenerated) {
            this.type = 3;
            this.degenerated = false;
            this.depth--;
            return this.type;
        }
        this.txtPos = 0;
        this.isWhitespace = true;
        while (true) {
            this.attributeCount = 0;
            this.name = null;
            this.text = null;
            this.type = peekType();
            switch (this.type) {
                case 1:
                    break;
                case 2:
                    parseStartTag();
                    break;
                case 3:
                    parseEndTag();
                    break;
                case 4:
                    this.isWhitespace &= pushText(60);
                    break;
                case 5:
                    parseLegacy(true);
                    this.isWhitespace = false;
                    this.type = 4;
                    break;
                case 6:
                    this.isWhitespace &= pushEntity();
                    this.type = 4;
                    break;
                default:
                    parseLegacy(false);
                    break;
            }
            if (this.type <= 4 && (this.type != 4 || peekType() < 4)) {
                boolean z = this.isWhitespace;
                if (this.type == 4) {
                    i = 1;
                }
                this.isWhitespace = i & z;
                return this.type;
            }
        }
    }

    public final boolean pushEntity() throws IOException {
        boolean z;
        read();
        int i = this.txtPos;
        while (!this.eof && this.peek0 != 59) {
            push(read());
        }
        String pop = pop(i);
        read();
        if (pop.length() <= 0 || pop.charAt(0) != '#') {
            String str = (String) this.entityMap.get(pop);
            pop = str == null ? "&" + pop + ";" : str;
            z = true;
            for (int i2 = 0; i2 < pop.length(); i2++) {
                char charAt = pop.charAt(i2);
                if (charAt > ' ') {
                    z = false;
                }
                push(charAt);
            }
        } else {
            i = pop.charAt(1) == 'x' ? Integer.parseInt(pop.substring(2), 16) : Integer.parseInt(pop.substring(1));
            push(i);
            if (i > 32) {
                return false;
            }
            z = true;
        }
        return z;
    }

    public String readText() throws IOException {
        if (this.type != 4) {
            return "";
        }
        String text = getText();
        next();
        return text;
    }

    public void require(int i, String str) throws IOException {
        if (this.type == 4 && i != 4 && isWhitespace()) {
            next();
        }
        if (i != this.type || (str != null && !str.equals(getName()))) {
            exception("expected: " + this.TYPES[i] + "/" + str);
        }
    }
}
