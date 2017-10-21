package org.spongycastle.asn1.x500.style;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Encoding;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1String;
import org.spongycastle.asn1.DERUniversalString;
import org.spongycastle.asn1.x500.AttributeTypeAndValue;
import org.spongycastle.asn1.x500.RDN;
import org.spongycastle.asn1.x500.X500NameBuilder;
import org.spongycastle.asn1.x500.X500NameStyle;
import org.spongycastle.crypto.tls.CipherSuite;
import org.spongycastle.util.Strings;
import org.spongycastle.util.encoders.Hex;

public class IETFUtils {
    public static void appendTypeAndValue(StringBuffer stringBuffer, AttributeTypeAndValue attributeTypeAndValue, Hashtable hashtable) {
        String str = (String) hashtable.get(attributeTypeAndValue.getType());
        if (str != null) {
            stringBuffer.append(str);
        } else {
            stringBuffer.append(attributeTypeAndValue.getType().getId());
        }
        stringBuffer.append('=');
        stringBuffer.append(valueToString(attributeTypeAndValue.getValue()));
    }

    private static String bytesToString(byte[] bArr) {
        char[] cArr = new char[bArr.length];
        for (int i = 0; i != cArr.length; i++) {
            cArr[i] = (char) (bArr[i] & CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        }
        return new String(cArr);
    }

    public static String canonicalize(String str) {
        String toLowerCase;
        String toLowerCase2 = Strings.toLowerCase(str.trim());
        if (toLowerCase2.length() > 0 && toLowerCase2.charAt(0) == '#') {
            ASN1Primitive decodeObject = decodeObject(toLowerCase2);
            if (decodeObject instanceof ASN1String) {
                toLowerCase = Strings.toLowerCase(((ASN1String) decodeObject).getString().trim());
                return stripInternalSpaces(toLowerCase);
            }
        }
        toLowerCase = toLowerCase2;
        return stripInternalSpaces(toLowerCase);
    }

    public static ASN1ObjectIdentifier decodeAttrName(String str, Hashtable hashtable) {
        if (Strings.toUpperCase(str).startsWith("OID.")) {
            return new ASN1ObjectIdentifier(str.substring(4));
        }
        if (str.charAt(0) >= '0' && str.charAt(0) <= '9') {
            return new ASN1ObjectIdentifier(str);
        }
        ASN1ObjectIdentifier aSN1ObjectIdentifier = (ASN1ObjectIdentifier) hashtable.get(Strings.toLowerCase(str));
        if (aSN1ObjectIdentifier != null) {
            return aSN1ObjectIdentifier;
        }
        throw new IllegalArgumentException("Unknown object id - " + str + " - passed to distinguished name");
    }

    private static ASN1Primitive decodeObject(String str) {
        try {
            return ASN1Primitive.fromByteArray(Hex.decode(str.substring(1)));
        } catch (IOException e) {
            throw new IllegalStateException("unknown encoding in name: " + e);
        }
    }

    public static RDN[] rDNsFromString(String str, X500NameStyle x500NameStyle) {
        X500NameTokenizer x500NameTokenizer = new X500NameTokenizer(str);
        X500NameBuilder x500NameBuilder = new X500NameBuilder(x500NameStyle);
        while (x500NameTokenizer.hasMoreTokens()) {
            String nextToken = x500NameTokenizer.nextToken();
            int indexOf = nextToken.indexOf(61);
            if (indexOf == -1) {
                throw new IllegalArgumentException("badly formated directory string");
            }
            String substring = nextToken.substring(0, indexOf);
            nextToken = nextToken.substring(indexOf + 1);
            ASN1ObjectIdentifier attrNameToOID = x500NameStyle.attrNameToOID(substring);
            if (nextToken.indexOf(43) > 0) {
                X500NameTokenizer x500NameTokenizer2 = new X500NameTokenizer(nextToken, '+');
                nextToken = x500NameTokenizer2.nextToken();
                Vector vector = new Vector();
                Vector vector2 = new Vector();
                vector.addElement(attrNameToOID);
                vector2.addElement(nextToken);
                while (x500NameTokenizer2.hasMoreTokens()) {
                    nextToken = x500NameTokenizer2.nextToken();
                    indexOf = nextToken.indexOf(61);
                    String substring2 = nextToken.substring(0, indexOf);
                    nextToken = nextToken.substring(indexOf + 1);
                    vector.addElement(x500NameStyle.attrNameToOID(substring2));
                    vector2.addElement(nextToken);
                }
                x500NameBuilder.addMultiValuedRDN(toOIDArray(vector), toValueArray(vector2));
            } else {
                x500NameBuilder.addRDN(attrNameToOID, nextToken);
            }
        }
        return x500NameBuilder.build().getRDNs();
    }

    public static String stripInternalSpaces(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        if (str.length() != 0) {
            char charAt = str.charAt(0);
            stringBuffer.append(charAt);
            int i = 1;
            while (i < str.length()) {
                char charAt2 = str.charAt(i);
                if (charAt != ' ' || charAt2 != ' ') {
                    stringBuffer.append(charAt2);
                }
                i++;
                charAt = charAt2;
            }
        }
        return stringBuffer.toString();
    }

    private static ASN1ObjectIdentifier[] toOIDArray(Vector vector) {
        ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = new ASN1ObjectIdentifier[vector.size()];
        for (int i = 0; i != aSN1ObjectIdentifierArr.length; i++) {
            aSN1ObjectIdentifierArr[i] = (ASN1ObjectIdentifier) vector.elementAt(i);
        }
        return aSN1ObjectIdentifierArr;
    }

    private static String[] toValueArray(Vector vector) {
        String[] strArr = new String[vector.size()];
        for (int i = 0; i != strArr.length; i++) {
            strArr[i] = (String) vector.elementAt(i);
        }
        return strArr;
    }

    public static ASN1Encodable valueFromHexString(String str, int i) throws IOException {
        String toLowerCase = Strings.toLowerCase(str);
        byte[] bArr = new byte[((toLowerCase.length() - i) / 2)];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            char charAt = toLowerCase.charAt((i2 * 2) + i);
            char charAt2 = toLowerCase.charAt(((i2 * 2) + i) + 1);
            if (charAt < 'a') {
                bArr[i2] = (byte) ((charAt - 48) << 4);
            } else {
                bArr[i2] = (byte) (((charAt - 97) + 10) << 4);
            }
            if (charAt2 < 'a') {
                bArr[i2] = (byte) (bArr[i2] | ((byte) (charAt2 - 48)));
            } else {
                bArr[i2] = (byte) (bArr[i2] | ((byte) ((charAt2 - 97) + 10)));
            }
        }
        return ASN1Primitive.fromByteArray(bArr);
    }

    public static String valueToString(ASN1Encodable aSN1Encodable) {
        int i = 2;
        StringBuffer stringBuffer = new StringBuffer();
        if (!(aSN1Encodable instanceof ASN1String) || (aSN1Encodable instanceof DERUniversalString)) {
            try {
                stringBuffer.append("#" + bytesToString(Hex.encode(aSN1Encodable.toASN1Primitive().getEncoded(ASN1Encoding.DER))));
            } catch (IOException e) {
                throw new IllegalArgumentException("Other value has no encoded form");
            }
        }
        String string = ((ASN1String) aSN1Encodable).getString();
        if (string.length() <= 0 || string.charAt(0) != '#') {
            stringBuffer.append(string);
        } else {
            stringBuffer.append("\\" + string);
        }
        int length = stringBuffer.length();
        if (!(stringBuffer.length() >= 2 && stringBuffer.charAt(0) == '\\' && stringBuffer.charAt(1) == '#')) {
            i = 0;
        }
        while (i != length) {
            if (stringBuffer.charAt(i) == ',' || stringBuffer.charAt(i) == '\"' || stringBuffer.charAt(i) == '\\' || stringBuffer.charAt(i) == '+' || stringBuffer.charAt(i) == '=' || stringBuffer.charAt(i) == '<' || stringBuffer.charAt(i) == '>' || stringBuffer.charAt(i) == ';') {
                stringBuffer.insert(i, "\\");
                i++;
                length++;
            }
            i++;
        }
        return stringBuffer.toString();
    }
}
