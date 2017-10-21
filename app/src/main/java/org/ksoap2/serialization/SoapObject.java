package org.ksoap2.serialization;

import java.util.Hashtable;
import java.util.Vector;

public class SoapObject extends AttributeContainer implements KvmSerializable {
    private static final String EMPTY_STRING = "";
    static Class class$java$lang$String;
    static Class class$org$ksoap2$serialization$SoapObject;
    protected String name;
    protected String namespace;
    protected Vector properties;

    public SoapObject() {
        this("", "");
    }

    public SoapObject(String str, String str2) {
        this.properties = new Vector();
        this.namespace = str;
        this.name = str2;
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private Integer propertyIndex(String str) {
        if (str != null) {
            for (int i = 0; i < this.properties.size(); i++) {
                if (str.equals(((PropertyInfo) this.properties.elementAt(i)).getName())) {
                    return new Integer(i);
                }
            }
        }
        return null;
    }

    public SoapObject addProperty(String str, Object obj) {
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.name = str;
        propertyInfo.type = obj == null ? PropertyInfo.OBJECT_CLASS : obj.getClass();
        propertyInfo.value = obj;
        return addProperty(propertyInfo);
    }

    public SoapObject addProperty(PropertyInfo propertyInfo) {
        this.properties.addElement(propertyInfo);
        return this;
    }

    public SoapObject addPropertyIfValue(String str, Object obj) {
        return obj != null ? addProperty(str, obj) : this;
    }

    public SoapObject addPropertyIfValue(PropertyInfo propertyInfo) {
        if (propertyInfo.value != null) {
            this.properties.addElement(propertyInfo);
        }
        return this;
    }

    public SoapObject addPropertyIfValue(PropertyInfo propertyInfo, Object obj) {
        if (obj == null) {
            return this;
        }
        propertyInfo.setValue(obj);
        return addProperty(propertyInfo);
    }

    public SoapObject addSoapObject(SoapObject soapObject) {
        this.properties.addElement(soapObject);
        return this;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof SoapObject)) {
            return false;
        }
        SoapObject soapObject = (SoapObject) obj;
        if (!this.name.equals(soapObject.name) || !this.namespace.equals(soapObject.namespace)) {
            return false;
        }
        int size = this.properties.size();
        if (size != soapObject.properties.size()) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!soapObject.isPropertyEqual(this.properties.elementAt(i), i)) {
                return false;
            }
        }
        return attributesAreEqual(soapObject);
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Object getPrimitiveProperty(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex != null) {
            Class class$;
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(propertyIndex.intValue());
            Class type = propertyInfo.getType();
            if (class$org$ksoap2$serialization$SoapObject == null) {
                class$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = class$;
            } else {
                class$ = class$org$ksoap2$serialization$SoapObject;
            }
            if (type != class$) {
                return propertyInfo.getValue();
            }
            Object class$2;
            PropertyInfo propertyInfo2 = new PropertyInfo();
            if (class$java$lang$String == null) {
                class$2 = class$("java.lang.String");
                class$java$lang$String = class$2;
            } else {
                class$2 = class$java$lang$String;
            }
            propertyInfo2.setType(class$2);
            propertyInfo2.setValue("");
            propertyInfo2.setName(str);
            return propertyInfo2.getValue();
        }
        throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
    }

    public String getPrimitivePropertyAsString(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex != null) {
            Class class$;
            PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(propertyIndex.intValue());
            Class type = propertyInfo.getType();
            if (class$org$ksoap2$serialization$SoapObject == null) {
                class$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = class$;
            } else {
                class$ = class$org$ksoap2$serialization$SoapObject;
            }
            return type != class$ ? propertyInfo.getValue().toString() : "";
        } else {
            throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
        }
    }

    public Object getPrimitivePropertySafely(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex == null) {
            return new NullSoapObject();
        }
        Class class$;
        PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(propertyIndex.intValue());
        Class type = propertyInfo.getType();
        if (class$org$ksoap2$serialization$SoapObject == null) {
            class$ = class$("org.ksoap2.serialization.SoapObject");
            class$org$ksoap2$serialization$SoapObject = class$;
        } else {
            class$ = class$org$ksoap2$serialization$SoapObject;
        }
        if (type != class$) {
            return propertyInfo.getValue().toString();
        }
        Object class$2;
        PropertyInfo propertyInfo2 = new PropertyInfo();
        if (class$java$lang$String == null) {
            class$2 = class$("java.lang.String");
            class$java$lang$String = class$2;
        } else {
            class$2 = class$java$lang$String;
        }
        propertyInfo2.setType(class$2);
        propertyInfo2.setValue("");
        propertyInfo2.setName(str);
        return propertyInfo2.getValue();
    }

    public String getPrimitivePropertySafelyAsString(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex == null) {
            return "";
        }
        Class class$;
        PropertyInfo propertyInfo = (PropertyInfo) this.properties.elementAt(propertyIndex.intValue());
        Class type = propertyInfo.getType();
        if (class$org$ksoap2$serialization$SoapObject == null) {
            class$ = class$("org.ksoap2.serialization.SoapObject");
            class$org$ksoap2$serialization$SoapObject = class$;
        } else {
            class$ = class$org$ksoap2$serialization$SoapObject;
        }
        return type != class$ ? propertyInfo.getValue().toString() : "";
    }

    public Object getProperty(int i) {
        Object elementAt = this.properties.elementAt(i);
        return elementAt instanceof PropertyInfo ? ((PropertyInfo) elementAt).getValue() : (SoapObject) elementAt;
    }

    public Object getProperty(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex != null) {
            return getProperty(propertyIndex.intValue());
        }
        throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
    }

    public String getPropertyAsString(int i) {
        return ((PropertyInfo) this.properties.elementAt(i)).getValue().toString();
    }

    public String getPropertyAsString(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex != null) {
            return getProperty(propertyIndex.intValue()).toString();
        }
        throw new RuntimeException(new StringBuffer("illegal property: ").append(str).toString());
    }

    public int getPropertyCount() {
        return this.properties.size();
    }

    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        getPropertyInfo(i, propertyInfo);
    }

    public void getPropertyInfo(int i, PropertyInfo propertyInfo) {
        Object elementAt = this.properties.elementAt(i);
        if (elementAt instanceof PropertyInfo) {
            PropertyInfo propertyInfo2 = (PropertyInfo) elementAt;
            propertyInfo.name = propertyInfo2.name;
            propertyInfo.namespace = propertyInfo2.namespace;
            propertyInfo.flags = propertyInfo2.flags;
            propertyInfo.type = propertyInfo2.type;
            propertyInfo.elementType = propertyInfo2.elementType;
            propertyInfo.value = propertyInfo2.value;
            propertyInfo.multiRef = propertyInfo2.multiRef;
            return;
        }
        propertyInfo.name = null;
        propertyInfo.namespace = null;
        propertyInfo.flags = 0;
        propertyInfo.type = null;
        propertyInfo.elementType = null;
        propertyInfo.value = elementAt;
        propertyInfo.multiRef = false;
    }

    public Object getPropertySafely(String str) {
        Integer propertyIndex = propertyIndex(str);
        return propertyIndex != null ? getProperty(propertyIndex.intValue()) : new NullSoapObject();
    }

    public Object getPropertySafely(String str, Object obj) {
        Integer propertyIndex = propertyIndex(str);
        return propertyIndex != null ? getProperty(propertyIndex.intValue()) : obj;
    }

    public String getPropertySafelyAsString(String str) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex == null) {
            return "";
        }
        Object property = getProperty(propertyIndex.intValue());
        return property == null ? "" : property.toString();
    }

    public String getPropertySafelyAsString(String str, Object obj) {
        Integer propertyIndex = propertyIndex(str);
        if (propertyIndex == null) {
            return obj != null ? obj.toString() : "";
        } else {
            Object property = getProperty(propertyIndex.intValue());
            return property != null ? property.toString() : "";
        }
    }

    public boolean hasProperty(String str) {
        return propertyIndex(str) != null;
    }

    public boolean isPropertyEqual(Object obj, int i) {
        if (i >= getPropertyCount()) {
            return false;
        }
        Object elementAt = this.properties.elementAt(i);
        if (!(obj instanceof PropertyInfo) || !(elementAt instanceof PropertyInfo)) {
            return ((obj instanceof SoapObject) && (elementAt instanceof SoapObject)) ? ((SoapObject) obj).equals((SoapObject) elementAt) : false;
        } else {
            PropertyInfo propertyInfo = (PropertyInfo) obj;
            PropertyInfo propertyInfo2 = (PropertyInfo) elementAt;
            return propertyInfo.getName().equals(propertyInfo2.getName()) && propertyInfo.getValue().equals(propertyInfo2.getValue());
        }
    }

    public SoapObject newInstance() {
        int i = 0;
        SoapObject soapObject = new SoapObject(this.namespace, this.name);
        for (int i2 = 0; i2 < this.properties.size(); i2++) {
            Object elementAt = this.properties.elementAt(i2);
            if (elementAt instanceof PropertyInfo) {
                soapObject.addProperty((PropertyInfo) ((PropertyInfo) this.properties.elementAt(i2)).clone());
            } else if (elementAt instanceof SoapObject) {
                soapObject.addSoapObject(((SoapObject) elementAt).newInstance());
            }
        }
        while (i < getAttributeCount()) {
            AttributeInfo attributeInfo = new AttributeInfo();
            getAttributeInfo(i, attributeInfo);
            soapObject.addAttribute(attributeInfo);
            i++;
        }
        return soapObject;
    }

    public void setProperty(int i, Object obj) {
        Object elementAt = this.properties.elementAt(i);
        if (elementAt instanceof PropertyInfo) {
            ((PropertyInfo) elementAt).setValue(obj);
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(new StringBuffer().append(this.name).append("{").toString());
        for (int i = 0; i < getPropertyCount(); i++) {
            Object elementAt = this.properties.elementAt(i);
            if (elementAt instanceof PropertyInfo) {
                stringBuffer.append(((PropertyInfo) elementAt).getName()).append("=").append(getProperty(i)).append("; ");
            } else {
                stringBuffer.append(((SoapObject) elementAt).toString());
            }
        }
        stringBuffer.append("}");
        return stringBuffer.toString();
    }
}
