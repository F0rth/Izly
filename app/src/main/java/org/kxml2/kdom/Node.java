package org.kxml2.kdom;

import java.io.IOException;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Node {
    public static final int CDSECT = 5;
    public static final int COMMENT = 9;
    public static final int DOCDECL = 10;
    public static final int DOCUMENT = 0;
    public static final int ELEMENT = 2;
    public static final int ENTITY_REF = 6;
    public static final int IGNORABLE_WHITESPACE = 7;
    public static final int PROCESSING_INSTRUCTION = 8;
    public static final int TEXT = 4;
    protected Vector children;
    protected StringBuffer types;

    public void addChild(int i, int i2, Object obj) {
        if (obj == null) {
            throw new NullPointerException();
        }
        if (this.children == null) {
            this.children = new Vector();
            this.types = new StringBuffer();
        }
        if (i2 == 2) {
            if (obj instanceof Element) {
                ((Element) obj).setParent(this);
            } else {
                throw new RuntimeException("Element obj expected)");
            }
        } else if (!(obj instanceof String)) {
            throw new RuntimeException("String expected");
        }
        this.children.insertElementAt(obj, i);
        this.types.insert(i, (char) i2);
    }

    public void addChild(int i, Object obj) {
        addChild(getChildCount(), i, obj);
    }

    public Element createElement(String str, String str2) {
        Element element = new Element();
        if (str == null) {
            str = "";
        }
        element.namespace = str;
        element.name = str2;
        return element;
    }

    public Object getChild(int i) {
        return this.children.elementAt(i);
    }

    public int getChildCount() {
        return this.children == null ? 0 : this.children.size();
    }

    public Element getElement(int i) {
        Object child = getChild(i);
        return child instanceof Element ? (Element) child : null;
    }

    public Element getElement(String str, String str2) {
        int indexOf = indexOf(str, str2, 0);
        int indexOf2 = indexOf(str, str2, indexOf + 1);
        if (indexOf != -1 && indexOf2 == -1) {
            return getElement(indexOf);
        }
        throw new RuntimeException("Element {" + str + "}" + str2 + (indexOf == -1 ? " not found in " : " more than once in ") + this);
    }

    public String getText(int i) {
        return isText(i) ? (String) getChild(i) : null;
    }

    public int getType(int i) {
        return this.types.charAt(i);
    }

    public int indexOf(String str, String str2, int i) {
        int childCount = getChildCount();
        int i2 = i;
        while (i2 < childCount) {
            Element element = getElement(i2);
            if (element != null && str2.equals(element.getName()) && (str == null || str.equals(element.getNamespace()))) {
                return i2;
            }
            i2++;
        }
        return -1;
    }

    public boolean isText(int i) {
        int type = getType(i);
        return type == 4 || type == 7 || type == 5;
    }

    public void parse(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        Object obj = null;
        do {
            int eventType = xmlPullParser.getEventType();
            switch (eventType) {
                case 1:
                case 3:
                    obj = 1;
                    continue;
                case 2:
                    Element createElement = createElement(xmlPullParser.getNamespace(), xmlPullParser.getName());
                    addChild(2, createElement);
                    createElement.parse(xmlPullParser);
                    continue;
                default:
                    if (xmlPullParser.getText() != null) {
                        if (eventType == 6) {
                            eventType = 4;
                        }
                        addChild(eventType, xmlPullParser.getText());
                    } else if (eventType == 6 && xmlPullParser.getName() != null) {
                        addChild(6, xmlPullParser.getName());
                    }
                    xmlPullParser.nextToken();
                    continue;
            }
        } while (obj == null);
    }

    public void removeChild(int i) {
        this.children.removeElementAt(i);
        int length = this.types.length() - 1;
        while (i < length) {
            this.types.setCharAt(i, this.types.charAt(i + 1));
            i++;
        }
        this.types.setLength(length);
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        writeChildren(xmlSerializer);
        xmlSerializer.flush();
    }

    public void writeChildren(XmlSerializer xmlSerializer) throws IOException {
        if (this.children != null) {
            int size = this.children.size();
            for (int i = 0; i < size; i++) {
                int type = getType(i);
                Object elementAt = this.children.elementAt(i);
                switch (type) {
                    case 2:
                        ((Element) elementAt).write(xmlSerializer);
                        break;
                    case 4:
                        xmlSerializer.text((String) elementAt);
                        break;
                    case 5:
                        xmlSerializer.cdsect((String) elementAt);
                        break;
                    case 6:
                        xmlSerializer.entityRef((String) elementAt);
                        break;
                    case 7:
                        xmlSerializer.ignorableWhitespace((String) elementAt);
                        break;
                    case 8:
                        xmlSerializer.processingInstruction((String) elementAt);
                        break;
                    case 9:
                        xmlSerializer.comment((String) elementAt);
                        break;
                    case 10:
                        xmlSerializer.docdecl((String) elementAt);
                        break;
                    default:
                        throw new RuntimeException("Illegal type: " + type);
                }
            }
        }
    }
}
