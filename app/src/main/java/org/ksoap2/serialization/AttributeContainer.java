package org.ksoap2.serialization;

import java.util.Vector;

public class AttributeContainer {
    private Vector attributes = new Vector();

    private Integer attributeIndex(String str) {
        for (int i = 0; i < this.attributes.size(); i++) {
            if (str.equals(((AttributeInfo) this.attributes.elementAt(i)).getName())) {
                return new Integer(i);
            }
        }
        return null;
    }

    public void addAttribute(String str, Object obj) {
        AttributeInfo attributeInfo = new AttributeInfo();
        attributeInfo.name = str;
        attributeInfo.type = obj == null ? PropertyInfo.OBJECT_CLASS : obj.getClass();
        attributeInfo.value = obj;
        addAttribute(attributeInfo);
    }

    public void addAttribute(AttributeInfo attributeInfo) {
        this.attributes.addElement(attributeInfo);
    }

    public void addAttributeIfValue(String str, Object obj) {
        if (obj != null) {
            addAttribute(str, obj);
        }
    }

    public void addAttributeIfValue(AttributeInfo attributeInfo) {
        if (attributeInfo.value != null) {
            this.attributes.addElement(attributeInfo);
        }
    }

    protected boolean attributesAreEqual(AttributeContainer attributeContainer) {
        int attributeCount = getAttributeCount();
        if (attributeCount != attributeContainer.getAttributeCount()) {
            return false;
        }
        for (int i = 0; i < attributeCount; i++) {
            AttributeInfo attributeInfo = (AttributeInfo) this.attributes.elementAt(i);
            Object value = attributeInfo.getValue();
            if (!attributeContainer.hasAttribute(attributeInfo.getName())) {
                return false;
            }
            if (!value.equals(attributeContainer.getAttributeSafely(attributeInfo.getName()))) {
                return false;
            }
        }
        return true;
    }

    public Object getAttribute(int i) {
        return ((AttributeInfo) this.attributes.elementAt(i)).getValue();
    }

    public Object getAttribute(String str) {
        Integer attributeIndex = attributeIndex(str);
        if (attributeIndex != null) {
            return getAttribute(attributeIndex.intValue());
        }
        throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
    }

    public String getAttributeAsString(int i) {
        return ((AttributeInfo) this.attributes.elementAt(i)).getValue().toString();
    }

    public String getAttributeAsString(String str) {
        Integer attributeIndex = attributeIndex(str);
        if (attributeIndex != null) {
            return getAttribute(attributeIndex.intValue()).toString();
        }
        throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
    }

    public int getAttributeCount() {
        return this.attributes.size();
    }

    public void getAttributeInfo(int i, AttributeInfo attributeInfo) {
        AttributeInfo attributeInfo2 = (AttributeInfo) this.attributes.elementAt(i);
        attributeInfo.name = attributeInfo2.name;
        attributeInfo.namespace = attributeInfo2.namespace;
        attributeInfo.flags = attributeInfo2.flags;
        attributeInfo.type = attributeInfo2.type;
        attributeInfo.elementType = attributeInfo2.elementType;
        attributeInfo.value = attributeInfo2.getValue();
    }

    public Object getAttributeSafely(String str) {
        Integer attributeIndex = attributeIndex(str);
        return attributeIndex != null ? getAttribute(attributeIndex.intValue()) : null;
    }

    public Object getAttributeSafelyAsString(String str) {
        Integer attributeIndex = attributeIndex(str);
        return attributeIndex != null ? getAttribute(attributeIndex.intValue()).toString() : "";
    }

    public boolean hasAttribute(String str) {
        return attributeIndex(str) != null;
    }
}
