package org.kxml2.wap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.eac.CertificateBody;
import org.spongycastle.i18n.LocalizedMessage;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class WbxmlParser implements XmlPullParser {
    static final String HEX_DIGITS = "0123456789abcdef";
    private static final String ILLEGAL_TYPE = "Wrong event type";
    private static final String UNEXPECTED_EOF = "Unexpected EOF";
    public static final int WAP_EXTENSION = 64;
    private int ATTR_START_TABLE = 1;
    private int ATTR_VALUE_TABLE = 2;
    private int TAG_TABLE = 0;
    private String[] attrStartTable;
    private String[] attrValueTable;
    private int attributeCount;
    private String[] attributes = new String[16];
    private Hashtable cacheStringTable = null;
    private boolean degenerated;
    private int depth;
    private String[] elementStack = new String[16];
    private String encoding;
    private InputStream in;
    private boolean isWhitespace;
    private String name;
    private String namespace;
    private int nextId = -2;
    private int[] nspCounts = new int[4];
    private String[] nspStack = new String[8];
    private String prefix;
    private boolean processNsp;
    private int publicIdentifierId;
    private byte[] stringTable;
    private Vector tables = new Vector();
    private String[] tagTable;
    private String text;
    private int type;
    private int version;
    private int wapCode;
    private Object wapExtensionData;

    private final boolean adjustNsp() throws XmlPullParserException {
        boolean z = false;
        int i = 0;
        while (i < (this.attributeCount << 2)) {
            String substring;
            String str = this.attributes[i + 2];
            int indexOf = str.indexOf(58);
            if (indexOf != -1) {
                substring = str.substring(0, indexOf);
                str = str.substring(indexOf + 1);
            } else if (str.equals("xmlns")) {
                String str2 = str;
                str = null;
                substring = str2;
            } else {
                i += 4;
            }
            if (substring.equals("xmlns")) {
                int[] iArr = this.nspCounts;
                indexOf = this.depth;
                int i2 = iArr[indexOf];
                iArr[indexOf] = i2 + 1;
                int i3 = i2 << 1;
                this.nspStack = ensureCapacity(this.nspStack, i3 + 2);
                this.nspStack[i3] = str;
                this.nspStack[i3 + 1] = this.attributes[i + 3];
                if (str != null && this.attributes[i + 3].equals("")) {
                    exception("illegal empty namespace");
                }
                Object obj = this.attributes;
                Object obj2 = this.attributes;
                indexOf = this.attributeCount - 1;
                this.attributeCount = indexOf;
                System.arraycopy(obj, i + 4, obj2, i, (indexOf << 2) - i);
                i -= 4;
            } else {
                z = true;
            }
            i += 4;
        }
        if (z) {
            for (i3 = (this.attributeCount << 2) - 4; i3 >= 0; i3 -= 4) {
                String str3 = this.attributes[i3 + 2];
                int indexOf2 = str3.indexOf(58);
                if (indexOf2 == 0) {
                    throw new RuntimeException("illegal attribute name: " + str3 + " at " + this);
                }
                if (indexOf2 != -1) {
                    String substring2 = str3.substring(0, indexOf2);
                    str = str3.substring(indexOf2 + 1);
                    String namespace = getNamespace(substring2);
                    if (namespace == null) {
                        throw new RuntimeException("Undefined Prefix: " + substring2 + " in " + this);
                    }
                    this.attributes[i3] = namespace;
                    this.attributes[i3 + 1] = substring2;
                    this.attributes[i3 + 2] = str;
                    i = (this.attributeCount << 2) - 4;
                    while (i > i3) {
                        if (str.equals(this.attributes[i + 2]) && namespace.equals(this.attributes[i])) {
                            exception("Duplicate Attribute: {" + namespace + "}" + str);
                        }
                        i -= 4;
                    }
                }
            }
        }
        i = this.name.indexOf(58);
        if (i == 0) {
            exception("illegal tag name: " + this.name);
        } else if (i != -1) {
            this.prefix = this.name.substring(0, i);
            this.name = this.name.substring(i + 1);
        }
        this.namespace = getNamespace(this.prefix);
        if (this.namespace == null) {
            if (this.prefix != null) {
                exception("undefined prefix: " + this.prefix);
            }
            this.namespace = "";
        }
        return z;
    }

    private final String[] ensureCapacity(String[] strArr, int i) {
        if (strArr.length >= i) {
            return strArr;
        }
        Object obj = new String[(i + 16)];
        System.arraycopy(strArr, 0, obj, 0, strArr.length);
        return obj;
    }

    private final void exception(String str) throws XmlPullParserException {
        throw new XmlPullParserException(str, this, null);
    }

    private final void nextImpl() throws IOException, XmlPullParserException {
        if (this.type == 3) {
            this.depth--;
        }
        if (this.degenerated) {
            this.type = 3;
            this.degenerated = false;
            return;
        }
        this.text = null;
        this.prefix = null;
        this.name = null;
        int peekId = peekId();
        while (peekId == 0) {
            this.nextId = -2;
            selectPage(readByte(), true);
            peekId = peekId();
        }
        this.nextId = -2;
        switch (peekId) {
            case -1:
                this.type = 1;
                return;
            case 1:
                peekId = (this.depth - 1) << 2;
                this.type = 3;
                this.namespace = this.elementStack[peekId];
                this.prefix = this.elementStack[peekId + 1];
                this.name = this.elementStack[peekId + 2];
                return;
            case 2:
                this.type = 6;
                char readInt = (char) readInt();
                this.text = readInt;
                this.name = "#" + readInt;
                return;
            case 3:
                this.type = 4;
                this.text = readStrI();
                return;
            case 64:
            case 65:
            case 66:
            case 128:
            case Wbxml.EXT_T_1 /*129*/:
            case Wbxml.EXT_T_2 /*130*/:
            case 192:
            case Wbxml.EXT_1 /*193*/:
            case Wbxml.EXT_2 /*194*/:
            case Wbxml.OPAQUE /*195*/:
                this.type = 64;
                this.wapCode = peekId;
                this.wapExtensionData = parseWapExtension(peekId);
                return;
            case 67:
                throw new RuntimeException("PI curr. not supp.");
            case Wbxml.STR_T /*131*/:
                this.type = 4;
                this.text = readStrT();
                return;
            default:
                parseElement(peekId);
                return;
        }
    }

    private int peekId() throws IOException {
        if (this.nextId == -2) {
            this.nextId = this.in.read();
        }
        return this.nextId;
    }

    private void selectPage(int i, boolean z) throws XmlPullParserException {
        if (this.tables.size() != 0 || i != 0) {
            if (i * 3 > this.tables.size()) {
                exception("Code Page " + i + " undefined!");
            }
            if (z) {
                this.tagTable = (String[]) this.tables.elementAt((i * 3) + this.TAG_TABLE);
                return;
            }
            this.attrStartTable = (String[]) this.tables.elementAt((i * 3) + this.ATTR_START_TABLE);
            this.attrValueTable = (String[]) this.tables.elementAt((i * 3) + this.ATTR_VALUE_TABLE);
        }
    }

    private final void setTable(int i, int i2, String[] strArr) {
        if (this.stringTable != null) {
            throw new RuntimeException("setXxxTable must be called before setInput!");
        }
        while (this.tables.size() < (i * 3) + 3) {
            this.tables.addElement(null);
        }
        this.tables.setElementAt(strArr, (i * 3) + i2);
    }

    public void defineEntityReplacementText(String str, String str2) throws XmlPullParserException {
    }

    public int getAttributeCount() {
        return this.attributeCount;
    }

    public String getAttributeName(int i) {
        if (i < this.attributeCount) {
            return this.attributes[(i << 2) + 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeNamespace(int i) {
        if (i < this.attributeCount) {
            return this.attributes[i << 2];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributePrefix(int i) {
        if (i < this.attributeCount) {
            return this.attributes[(i << 2) + 1];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeType(int i) {
        return "CDATA";
    }

    public String getAttributeValue(int i) {
        if (i < this.attributeCount) {
            return this.attributes[(i << 2) + 3];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getAttributeValue(String str, String str2) {
        int i = (this.attributeCount << 2) - 4;
        while (i >= 0) {
            if (this.attributes[i + 2].equals(str2) && (str == null || this.attributes[i].equals(str))) {
                return this.attributes[i + 3];
            }
            i -= 4;
        }
        return null;
    }

    public int getColumnNumber() {
        return -1;
    }

    public int getDepth() {
        return this.depth;
    }

    public int getEventType() throws XmlPullParserException {
        return this.type;
    }

    public boolean getFeature(String str) {
        return XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str) ? this.processNsp : false;
    }

    public String getInputEncoding() {
        return this.encoding;
    }

    public int getLineNumber() {
        return -1;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public String getNamespace(String str) {
        if ("xml".equals(str)) {
            return "http://www.w3.org/XML/1998/namespace";
        }
        if ("xmlns".equals(str)) {
            return "http://www.w3.org/2000/xmlns/";
        }
        for (int namespaceCount = (getNamespaceCount(this.depth) << 1) - 2; namespaceCount >= 0; namespaceCount -= 2) {
            if (str == null) {
                if (this.nspStack[namespaceCount] == null) {
                    return this.nspStack[namespaceCount + 1];
                }
            } else if (str.equals(this.nspStack[namespaceCount])) {
                return this.nspStack[namespaceCount + 1];
            }
        }
        return null;
    }

    public int getNamespaceCount(int i) {
        if (i <= this.depth) {
            return this.nspCounts[i];
        }
        throw new IndexOutOfBoundsException();
    }

    public String getNamespacePrefix(int i) {
        return this.nspStack[i << 1];
    }

    public String getNamespaceUri(int i) {
        return this.nspStack[(i << 1) + 1];
    }

    public String getPositionDescription() {
        StringBuffer stringBuffer = new StringBuffer(this.type < TYPES.length ? TYPES[this.type] : "unknown");
        stringBuffer.append(' ');
        if (this.type == 2 || this.type == 3) {
            if (this.degenerated) {
                stringBuffer.append("(empty) ");
            }
            stringBuffer.append('<');
            if (this.type == 3) {
                stringBuffer.append('/');
            }
            if (this.prefix != null) {
                stringBuffer.append("{" + this.namespace + "}" + this.prefix + ":");
            }
            stringBuffer.append(this.name);
            int i = this.attributeCount;
            for (int i2 = 0; i2 < (i << 2); i2 += 4) {
                stringBuffer.append(' ');
                if (this.attributes[i2 + 1] != null) {
                    stringBuffer.append("{" + this.attributes[i2] + "}" + this.attributes[i2 + 1] + ":");
                }
                stringBuffer.append(this.attributes[i2 + 2] + "='" + this.attributes[i2 + 3] + "'");
            }
            stringBuffer.append('>');
        } else if (this.type != 7) {
            if (this.type != 4) {
                stringBuffer.append(getText());
            } else if (this.isWhitespace) {
                stringBuffer.append("(whitespace)");
            } else {
                String text = getText();
                if (text.length() > 16) {
                    text = text.substring(0, 16) + "...";
                }
                stringBuffer.append(text);
            }
        }
        return stringBuffer.toString();
    }

    public String getPrefix() {
        return this.prefix;
    }

    public Object getProperty(String str) {
        return null;
    }

    public String getText() {
        return this.text;
    }

    public char[] getTextCharacters(int[] iArr) {
        if (this.type >= 4) {
            iArr[0] = 0;
            iArr[1] = this.text.length();
            char[] cArr = new char[this.text.length()];
            this.text.getChars(0, this.text.length(), cArr, 0);
            return cArr;
        }
        iArr[0] = -1;
        iArr[1] = -1;
        return null;
    }

    public int getWapCode() {
        return this.wapCode;
    }

    public Object getWapExtensionData() {
        return this.wapExtensionData;
    }

    public boolean isAttributeDefault(int i) {
        return false;
    }

    public boolean isEmptyElementTag() throws XmlPullParserException {
        if (this.type != 2) {
            exception(ILLEGAL_TYPE);
        }
        return this.degenerated;
    }

    public boolean isWhitespace() throws XmlPullParserException {
        if (!(this.type == 4 || this.type == 7 || this.type == 5)) {
            exception(ILLEGAL_TYPE);
        }
        return this.isWhitespace;
    }

    public int next() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        int i = 9999;
        while (true) {
            String str = this.text;
            nextImpl();
            if (this.type < i) {
                i = this.type;
            }
            if (i <= 5) {
                if (i >= 4) {
                    if (str != null) {
                        if (this.text != null) {
                            str = str + this.text;
                        }
                        this.text = str;
                    }
                    switch (peekId()) {
                        case 2:
                        case 3:
                        case 4:
                        case 68:
                        case Wbxml.STR_T /*131*/:
                        case Wbxml.LITERAL_A /*132*/:
                        case Wbxml.LITERAL_AC /*196*/:
                            break;
                        default:
                            break;
                    }
                }
                this.type = i;
                if (this.type > 4) {
                    this.type = 4;
                }
                return this.type;
            }
        }
    }

    public int nextTag() throws XmlPullParserException, IOException {
        next();
        if (this.type == 4 && this.isWhitespace) {
            next();
        }
        if (!(this.type == 3 || this.type == 2)) {
            exception("unexpected type");
        }
        return this.type;
    }

    public String nextText() throws XmlPullParserException, IOException {
        String text;
        if (this.type != 2) {
            exception("precondition: START_TAG");
        }
        next();
        if (this.type == 4) {
            text = getText();
            next();
        } else {
            text = "";
        }
        if (this.type != 3) {
            exception("END_TAG expected");
        }
        return text;
    }

    public int nextToken() throws XmlPullParserException, IOException {
        this.isWhitespace = true;
        nextImpl();
        return this.type;
    }

    void parseElement(int i) throws IOException, XmlPullParserException {
        this.type = 2;
        this.name = resolveId(this.tagTable, i & 63);
        this.attributeCount = 0;
        if ((i & 128) != 0) {
            readAttr();
        }
        this.degenerated = (i & 64) == 0;
        int i2 = this.depth;
        this.depth = i2 + 1;
        int i3 = i2 << 2;
        this.elementStack = ensureCapacity(this.elementStack, i3 + 4);
        this.elementStack[i3 + 3] = this.name;
        if (this.depth >= this.nspCounts.length) {
            Object obj = new int[(this.depth + 4)];
            System.arraycopy(this.nspCounts, 0, obj, 0, this.nspCounts.length);
            this.nspCounts = obj;
        }
        this.nspCounts[this.depth] = this.nspCounts[this.depth - 1];
        for (int i4 = this.attributeCount - 1; i4 > 0; i4--) {
            for (i2 = 0; i2 < i4; i2++) {
                if (getAttributeName(i4).equals(getAttributeName(i2))) {
                    exception("Duplicate Attribute: " + getAttributeName(i4));
                }
            }
        }
        if (this.processNsp) {
            adjustNsp();
        } else {
            this.namespace = "";
        }
        this.elementStack[i3] = this.namespace;
        this.elementStack[i3 + 1] = this.prefix;
        this.elementStack[i3 + 2] = this.name;
    }

    public Object parseWapExtension(int i) throws IOException, XmlPullParserException {
        switch (i) {
            case 64:
            case 65:
            case 66:
                return readStrI();
            case 128:
            case Wbxml.EXT_T_1 /*129*/:
            case Wbxml.EXT_T_2 /*130*/:
                return new Integer(readInt());
            case 192:
            case Wbxml.EXT_1 /*193*/:
            case Wbxml.EXT_2 /*194*/:
                return null;
            case Wbxml.OPAQUE /*195*/:
                int readInt = readInt();
                Object obj = new byte[readInt];
                while (readInt > 0) {
                    readInt -= this.in.read(obj, obj.length - readInt, readInt);
                }
                return obj;
            default:
                exception("illegal id: " + i);
                return null;
        }
    }

    public void readAttr() throws IOException, XmlPullParserException {
        int readByte = readByte();
        int i = 0;
        while (readByte != 1) {
            StringBuffer stringBuffer;
            while (readByte == 0) {
                selectPage(readByte(), false);
                readByte = readByte();
            }
            String resolveId = resolveId(this.attrStartTable, readByte);
            int indexOf = resolveId.indexOf(61);
            if (indexOf == -1) {
                stringBuffer = new StringBuffer();
            } else {
                stringBuffer = new StringBuffer(resolveId.substring(indexOf + 1));
                resolveId = resolveId.substring(0, indexOf);
            }
            indexOf = readByte();
            while (true) {
                if (indexOf > 128 || indexOf == 0 || indexOf == 2 || indexOf == 3 || indexOf == Wbxml.STR_T || ((indexOf >= 64 && indexOf <= 66) || (indexOf >= 128 && indexOf <= Wbxml.EXT_T_2))) {
                    switch (indexOf) {
                        case 0:
                            selectPage(readByte(), false);
                            break;
                        case 2:
                            stringBuffer.append((char) readInt());
                            break;
                        case 3:
                            stringBuffer.append(readStrI());
                            break;
                        case 64:
                        case 65:
                        case 66:
                        case 128:
                        case Wbxml.EXT_T_1 /*129*/:
                        case Wbxml.EXT_T_2 /*130*/:
                        case 192:
                        case Wbxml.EXT_1 /*193*/:
                        case Wbxml.EXT_2 /*194*/:
                        case Wbxml.OPAQUE /*195*/:
                            stringBuffer.append(resolveWapExtension(indexOf, parseWapExtension(indexOf)));
                            break;
                        case Wbxml.STR_T /*131*/:
                            stringBuffer.append(readStrT());
                            break;
                        default:
                            stringBuffer.append(resolveId(this.attrValueTable, indexOf));
                            break;
                    }
                    indexOf = readByte();
                } else {
                    this.attributes = ensureCapacity(this.attributes, i + 4);
                    int i2 = i + 1;
                    this.attributes[i] = "";
                    int i3 = i2 + 1;
                    this.attributes[i2] = null;
                    i2 = i3 + 1;
                    this.attributes[i3] = resolveId;
                    this.attributes[i2] = stringBuffer.toString();
                    this.attributeCount++;
                    i = i2 + 1;
                    readByte = indexOf;
                }
            }
        }
    }

    int readByte() throws IOException {
        int read = this.in.read();
        if (read != -1) {
            return read;
        }
        throw new IOException(UNEXPECTED_EOF);
    }

    int readInt() throws IOException {
        int i = 0;
        int readByte;
        do {
            readByte = readByte();
            i = (i << 7) | (readByte & CertificateBody.profileType);
        } while ((readByte & 128) != 0);
        return i;
    }

    String readStrI() throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        boolean z = true;
        while (true) {
            int read = this.in.read();
            if (read == 0) {
                this.isWhitespace = z;
                String str = new String(byteArrayOutputStream.toByteArray(), this.encoding);
                byteArrayOutputStream.close();
                return str;
            } else if (read == -1) {
                throw new IOException(UNEXPECTED_EOF);
            } else {
                if (read > 32) {
                    z = false;
                }
                byteArrayOutputStream.write(read);
            }
        }
    }

    String readStrT() throws IOException {
        int readInt = readInt();
        if (this.cacheStringTable == null) {
            this.cacheStringTable = new Hashtable();
        }
        String str = (String) this.cacheStringTable.get(new Integer(readInt));
        if (str != null) {
            return str;
        }
        int i = readInt;
        while (i < this.stringTable.length && this.stringTable[i] != (byte) 0) {
            i++;
        }
        String str2 = new String(this.stringTable, readInt, i - readInt, this.encoding);
        this.cacheStringTable.put(new Integer(readInt), str2);
        return str2;
    }

    public void require(int i, String str, String str2) throws XmlPullParserException, IOException {
        if (i != this.type || ((str != null && !str.equals(getNamespace())) || (str2 != null && !str2.equals(getName())))) {
            exception("expected: " + (i == 64 ? "WAP Ext." : TYPES[i] + " {" + str + "}" + str2));
        }
    }

    String resolveId(String[] strArr, int i) throws IOException {
        int i2 = (i & CertificateBody.profileType) - 5;
        if (i2 == -1) {
            this.wapCode = -1;
            return readStrT();
        } else if (i2 < 0 || strArr == null || i2 >= strArr.length || strArr[i2] == null) {
            throw new IOException("id " + i + " undef.");
        } else {
            this.wapCode = i2 + 5;
            return strArr[i2];
        }
    }

    protected String resolveWapExtension(int i, Object obj) {
        if (!(obj instanceof byte[])) {
            return "$(" + obj + ")";
        }
        StringBuffer stringBuffer = new StringBuffer();
        byte[] bArr = (byte[]) obj;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            stringBuffer.append(HEX_DIGITS.charAt((bArr[i2] >> 4) & 15));
            stringBuffer.append(HEX_DIGITS.charAt(bArr[i2] & 15));
        }
        return stringBuffer.toString();
    }

    public void setAttrStartTable(int i, String[] strArr) {
        setTable(i, this.ATTR_START_TABLE, strArr);
    }

    public void setAttrValueTable(int i, String[] strArr) {
        setTable(i, this.ATTR_VALUE_TABLE, strArr);
    }

    public void setFeature(String str, boolean z) throws XmlPullParserException {
        if (XmlPullParser.FEATURE_PROCESS_NAMESPACES.equals(str)) {
            this.processNsp = z;
        } else {
            exception("unsupported feature: " + str);
        }
    }

    public void setInput(InputStream inputStream, String str) throws XmlPullParserException {
        int i = 0;
        this.in = inputStream;
        try {
            this.version = readByte();
            this.publicIdentifierId = readInt();
            if (this.publicIdentifierId == 0) {
                readInt();
            }
            int readInt = readInt();
            if (str == null) {
                switch (readInt) {
                    case 4:
                        this.encoding = LocalizedMessage.DEFAULT_ENCODING;
                        break;
                    case 106:
                        this.encoding = "UTF-8";
                        break;
                    default:
                        throw new UnsupportedEncodingException(readInt);
                }
            }
            this.encoding = str;
            readInt = readInt();
            this.stringTable = new byte[readInt];
            while (i < readInt) {
                int read = inputStream.read(this.stringTable, i, readInt - i);
                if (read > 0) {
                    i += read;
                } else {
                    selectPage(0, true);
                    selectPage(0, false);
                }
            }
            selectPage(0, true);
            selectPage(0, false);
        } catch (IOException e) {
            exception("Illegal input format");
        }
    }

    public void setInput(Reader reader) throws XmlPullParserException {
        exception("InputStream required");
    }

    public void setProperty(String str, Object obj) throws XmlPullParserException {
        throw new XmlPullParserException("unsupported property: " + str);
    }

    public void setTagTable(int i, String[] strArr) {
        setTable(i, this.TAG_TABLE, strArr);
    }
}
