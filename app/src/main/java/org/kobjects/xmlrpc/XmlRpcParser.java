package org.kobjects.xmlrpc;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.kobjects.base64.Base64;
import org.kobjects.isodate.IsoDate;
import org.kobjects.xml.XmlReader;

public class XmlRpcParser {
    private XmlReader parser = null;

    public XmlRpcParser(XmlReader xmlReader) {
        this.parser = xmlReader;
    }

    private final int nextTag() throws IOException {
        this.parser.getType();
        int next = this.parser.next();
        if (next == 4 && this.parser.isWhitespace()) {
            next = this.parser.next();
        }
        if (next == 3 || next == 2) {
            return next;
        }
        throw new IOException("unexpected type: " + next);
    }

    private final String nextText() throws IOException {
        if (this.parser.getType() != 2) {
            throw new IOException("precondition: START_TAG");
        }
        String text;
        int next = this.parser.next();
        if (next == 4) {
            text = this.parser.getText();
            next = this.parser.next();
        } else {
            text = "";
        }
        if (next == 3) {
            return text;
        }
        throw new IOException("END_TAG expected");
    }

    private final Vector parseArray() throws IOException {
        nextTag();
        int nextTag = nextTag();
        Vector vector = new Vector();
        while (nextTag != 3) {
            vector.addElement(parseValue());
            nextTag = this.parser.getType();
        }
        nextTag();
        nextTag();
        return vector;
    }

    private final Object parseFault() throws IOException {
        nextTag();
        Object parseValue = parseValue();
        nextTag();
        return parseValue;
    }

    private final Object parseParams() throws IOException {
        Vector vector = new Vector();
        int nextTag = nextTag();
        while (nextTag != 3) {
            nextTag();
            vector.addElement(parseValue());
            nextTag = nextTag();
        }
        nextTag();
        return vector;
    }

    private final Hashtable parseStruct() throws IOException {
        Hashtable hashtable = new Hashtable();
        int nextTag = nextTag();
        while (nextTag != 3) {
            nextTag();
            String nextText = nextText();
            nextTag();
            hashtable.put(nextText, parseValue());
            nextTag = nextTag();
        }
        nextTag();
        return hashtable;
    }

    private final Object parseValue() throws IOException {
        Object text;
        int next = this.parser.next();
        if (next == 4) {
            text = this.parser.getText();
            next = this.parser.next();
        } else {
            text = null;
        }
        if (next == 2) {
            String name = this.parser.getName();
            if (name.equals("array")) {
                text = parseArray();
            } else if (name.equals("struct")) {
                text = parseStruct();
            } else {
                if (name.equals("string")) {
                    text = nextText();
                } else if (name.equals("i4") || name.equals("int")) {
                    text = new Integer(Integer.parseInt(nextText().trim()));
                } else if (name.equals("boolean")) {
                    text = new Boolean(nextText().trim().equals("1"));
                } else if (name.equals("dateTime.iso8601")) {
                    text = IsoDate.stringToDate(nextText(), 3);
                } else if (name.equals("base64")) {
                    text = Base64.decode(nextText());
                } else if (name.equals("double")) {
                    text = nextText();
                }
                nextTag();
            }
        }
        nextTag();
        return text;
    }

    public final Object parseResponse() throws IOException {
        nextTag();
        return nextTag() == 2 ? "fault".equals(this.parser.getName()) ? parseFault() : "params".equals(this.parser.getName()) ? parseParams() : null : null;
    }
}
