package org.kxml2.kdom;

import java.io.IOException;
import java.util.Vector;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class Element extends Node {
    protected Vector attributes;
    protected String name;
    protected String namespace;
    protected Node parent;
    protected Vector prefixes;

    public void clear() {
        this.attributes = null;
        this.children = null;
    }

    public Element createElement(String str, String str2) {
        return this.parent == null ? super.createElement(str, str2) : this.parent.createElement(str, str2);
    }

    public int getAttributeCount() {
        return this.attributes == null ? 0 : this.attributes.size();
    }

    public String getAttributeName(int i) {
        return ((String[]) this.attributes.elementAt(i))[1];
    }

    public String getAttributeNamespace(int i) {
        return ((String[]) this.attributes.elementAt(i))[0];
    }

    public String getAttributeValue(int i) {
        return ((String[]) this.attributes.elementAt(i))[2];
    }

    public String getAttributeValue(String str, String str2) {
        int i = 0;
        while (i < getAttributeCount()) {
            if (str2.equals(getAttributeName(i)) && (str == null || str.equals(getAttributeNamespace(i)))) {
                return getAttributeValue(i);
            }
            i++;
        }
        return null;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public int getNamespaceCount() {
        return this.prefixes == null ? 0 : this.prefixes.size();
    }

    public String getNamespacePrefix(int i) {
        return ((String[]) this.prefixes.elementAt(i))[0];
    }

    public String getNamespaceUri(int i) {
        return ((String[]) this.prefixes.elementAt(i))[1];
    }

    public String getNamespaceUri(String str) {
        int namespaceCount = getNamespaceCount();
        int i = 0;
        while (i < namespaceCount) {
            if (str == getNamespacePrefix(i) || (str != null && str.equals(getNamespacePrefix(i)))) {
                return getNamespaceUri(i);
            }
            i++;
        }
        return this.parent instanceof Element ? ((Element) this.parent).getNamespaceUri(str) : null;
    }

    public Node getParent() {
        return this.parent;
    }

    public Node getRoot() {
        Node node;
        while (node.parent != null) {
            if (!(node.parent instanceof Element)) {
                return node.parent;
            }
            node = (Element) node.parent;
        }
        return node;
    }

    public void init() {
    }

    public void parse(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int namespaceCount;
        for (namespaceCount = xmlPullParser.getNamespaceCount(xmlPullParser.getDepth() - 1); namespaceCount < xmlPullParser.getNamespaceCount(xmlPullParser.getDepth()); namespaceCount++) {
            setPrefix(xmlPullParser.getNamespacePrefix(namespaceCount), xmlPullParser.getNamespaceUri(namespaceCount));
        }
        for (namespaceCount = 0; namespaceCount < xmlPullParser.getAttributeCount(); namespaceCount++) {
            setAttribute(xmlPullParser.getAttributeNamespace(namespaceCount), xmlPullParser.getAttributeName(namespaceCount), xmlPullParser.getAttributeValue(namespaceCount));
        }
        init();
        if (xmlPullParser.isEmptyElementTag()) {
            xmlPullParser.nextToken();
        } else {
            xmlPullParser.nextToken();
            super.parse(xmlPullParser);
            if (getChildCount() == 0) {
                addChild(7, "");
            }
        }
        xmlPullParser.require(3, getNamespace(), getName());
        xmlPullParser.nextToken();
    }

    public void setAttribute(String str, String str2, String str3) {
        Object obj;
        if (this.attributes == null) {
            this.attributes = new Vector();
        }
        if (str == null) {
            obj = "";
        }
        int size = this.attributes.size() - 1;
        while (size >= 0) {
            String[] strArr = (String[]) this.attributes.elementAt(size);
            if (!strArr[0].equals(obj) || !strArr[1].equals(str2)) {
                size--;
            } else if (str3 == null) {
                this.attributes.removeElementAt(size);
                return;
            } else {
                strArr[2] = str3;
                return;
            }
        }
        this.attributes.addElement(new String[]{obj, str2, str3});
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNamespace(String str) {
        if (str == null) {
            throw new NullPointerException("Use \"\" for empty namespace");
        }
        this.namespace = str;
    }

    protected void setParent(Node node) {
        this.parent = node;
    }

    public void setPrefix(String str, String str2) {
        if (this.prefixes == null) {
            this.prefixes = new Vector();
        }
        this.prefixes.addElement(new String[]{str, str2});
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        int i;
        int i2 = 0;
        if (this.prefixes != null) {
            for (i = 0; i < this.prefixes.size(); i++) {
                xmlSerializer.setPrefix(getNamespacePrefix(i), getNamespaceUri(i));
            }
        }
        xmlSerializer.startTag(getNamespace(), getName());
        i = getAttributeCount();
        while (i2 < i) {
            xmlSerializer.attribute(getAttributeNamespace(i2), getAttributeName(i2), getAttributeValue(i2));
            i2++;
        }
        writeChildren(xmlSerializer);
        xmlSerializer.endTag(getNamespace(), getName());
    }
}
