package org.ksoap2.serialization;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.SoapFault12;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SoapSerializationEnvelope extends SoapEnvelope {
    private static final String ANY_TYPE_LABEL = "anyType";
    private static final String ARRAY_MAPPING_NAME = "Array";
    private static final String ARRAY_TYPE_LABEL = "arrayType";
    static final Marshal DEFAULT_MARSHAL = new DM();
    private static final String HREF_LABEL = "href";
    private static final String ID_LABEL = "id";
    private static final String ITEM_LABEL = "item";
    private static final String NIL_LABEL = "nil";
    private static final String NULL_LABEL = "null";
    protected static final int QNAME_MARSHAL = 3;
    protected static final int QNAME_NAMESPACE = 0;
    protected static final int QNAME_TYPE = 1;
    private static final String ROOT_LABEL = "root";
    private static final String TYPE_LABEL = "type";
    static Class class$org$ksoap2$serialization$SoapObject;
    protected boolean addAdornments = true;
    public boolean avoidExceptionForUnknownProperty;
    protected Hashtable classToQName = new Hashtable();
    public boolean dotNet;
    Hashtable idMap = new Hashtable();
    public boolean implicitTypes;
    Vector multiRef;
    public Hashtable properties = new Hashtable();
    protected Hashtable qNameToClass = new Hashtable();

    public SoapSerializationEnvelope(int i) {
        super(i);
        addMapping(this.enc, ARRAY_MAPPING_NAME, PropertyInfo.VECTOR_CLASS);
        DEFAULT_MARSHAL.register(this);
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    private int getIndex(String str, int i, int i2) {
        return (str != null && str.length() - i >= 3) ? Integer.parseInt(str.substring(i + 1, str.length() - 1)) : i2;
    }

    private void writeElement(XmlSerializer xmlSerializer, Object obj, PropertyInfo propertyInfo, Object obj2) throws IOException {
        if (obj2 != null) {
            ((Marshal) obj2).writeInstance(xmlSerializer, obj);
        } else if (obj instanceof SoapObject) {
            writeObjectBody(xmlSerializer, (SoapObject) obj);
        } else if (obj instanceof KvmSerializable) {
            writeObjectBody(xmlSerializer, (KvmSerializable) obj);
        } else if (obj instanceof Vector) {
            writeVectorBody(xmlSerializer, (Vector) obj, propertyInfo.elementType);
        } else {
            throw new RuntimeException(new StringBuffer("Cannot serialize: ").append(obj).toString());
        }
    }

    public void addMapping(String str, String str2, Class cls) {
        addMapping(str, str2, cls, null);
    }

    public void addMapping(String str, String str2, Class cls, Marshal marshal) {
        Object obj;
        Hashtable hashtable = this.qNameToClass;
        SoapPrimitive soapPrimitive = new SoapPrimitive(str, str2, null);
        if (marshal == null) {
            obj = cls;
        } else {
            Marshal marshal2 = marshal;
        }
        hashtable.put(soapPrimitive, obj);
        this.classToQName.put(cls.getName(), new Object[]{str, str2, null, marshal});
    }

    public void addTemplate(SoapObject soapObject) {
        this.qNameToClass.put(new SoapPrimitive(soapObject.namespace, soapObject.name, null), soapObject);
    }

    public Object[] getInfo(Object obj, Object obj2) {
        Class cls = obj == null ? ((obj2 instanceof SoapObject) || (obj2 instanceof SoapPrimitive)) ? obj2 : obj2.getClass() : obj;
        if (cls instanceof SoapObject) {
            SoapObject soapObject = (SoapObject) cls;
            return new Object[]{soapObject.getNamespace(), soapObject.getName(), null, null};
        } else if (cls instanceof SoapPrimitive) {
            SoapPrimitive soapPrimitive = (SoapPrimitive) cls;
            return new Object[]{soapPrimitive.getNamespace(), soapPrimitive.getName(), null, DEFAULT_MARSHAL};
        } else {
            if ((cls instanceof Class) && cls != PropertyInfo.OBJECT_CLASS) {
                Object[] objArr = (Object[]) this.classToQName.get(cls.getName());
                if (objArr != null) {
                    return objArr;
                }
            }
            return new Object[]{this.xsd, ANY_TYPE_LABEL, null, null};
        }
    }

    public Object getResponse() throws SoapFault {
        int i = 0;
        if (this.bodyIn instanceof SoapFault) {
            throw ((SoapFault) this.bodyIn);
        }
        KvmSerializable kvmSerializable = (KvmSerializable) this.bodyIn;
        if (kvmSerializable.getPropertyCount() == 0) {
            return null;
        }
        if (kvmSerializable.getPropertyCount() == 1) {
            return kvmSerializable.getProperty(0);
        }
        Vector vector = new Vector();
        while (i < kvmSerializable.getPropertyCount()) {
            vector.add(kvmSerializable.getProperty(i));
            i++;
        }
        return vector;
    }

    public boolean isAddAdornments() {
        return this.addAdornments;
    }

    public void parseBody(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        this.bodyIn = null;
        xmlPullParser.nextTag();
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getNamespace().equals(this.env) && xmlPullParser.getName().equals("Fault")) {
            SoapFault soapFault = this.version < 120 ? new SoapFault(this.version) : new SoapFault12(this.version);
            soapFault.parse(xmlPullParser);
            this.bodyIn = soapFault;
            return;
        }
        while (xmlPullParser.getEventType() == 2) {
            String attributeValue = xmlPullParser.getAttributeValue(this.enc, ROOT_LABEL);
            Object read = read(xmlPullParser, null, -1, xmlPullParser.getNamespace(), xmlPullParser.getName(), PropertyInfo.OBJECT_TYPE);
            if ("1".equals(attributeValue) || this.bodyIn == null) {
                this.bodyIn = read;
            }
            xmlPullParser.nextTag();
        }
    }

    public Object read(XmlPullParser xmlPullParser, Object obj, int i, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        Object obj2;
        String name = xmlPullParser.getName();
        String attributeValue = xmlPullParser.getAttributeValue(null, HREF_LABEL);
        FwdRef fwdRef;
        if (attributeValue == null) {
            Object readInstance;
            attributeValue = xmlPullParser.getAttributeValue(this.xsi, NIL_LABEL);
            String attributeValue2 = xmlPullParser.getAttributeValue(null, ID_LABEL);
            if (attributeValue == null) {
                attributeValue = xmlPullParser.getAttributeValue(this.xsi, NULL_LABEL);
            }
            if (attributeValue == null || !SoapEnvelope.stringToBoolean(attributeValue)) {
                String attributeValue3 = xmlPullParser.getAttributeValue(this.xsi, TYPE_LABEL);
                if (attributeValue3 != null) {
                    int indexOf = attributeValue3.indexOf(58);
                    str2 = attributeValue3.substring(indexOf + 1);
                    str = xmlPullParser.getNamespace(indexOf == -1 ? "" : attributeValue3.substring(0, indexOf));
                } else if (str2 == null && str == null) {
                    if (xmlPullParser.getAttributeValue(this.enc, ARRAY_TYPE_LABEL) != null) {
                        str = this.enc;
                        str2 = ARRAY_MAPPING_NAME;
                    } else {
                        Object[] info = getInfo(propertyInfo.type, null);
                        str2 = (String) info[1];
                        str = (String) info[0];
                    }
                }
                if (attributeValue3 == null) {
                    this.implicitTypes = true;
                }
                readInstance = readInstance(xmlPullParser, str, str2, propertyInfo);
                if (readInstance == null) {
                    readInstance = readUnknown(xmlPullParser, str, str2);
                }
            } else {
                xmlPullParser.nextTag();
                xmlPullParser.require(3, null, name);
                readInstance = null;
            }
            if (attributeValue2 != null) {
                obj2 = this.idMap.get(attributeValue2);
                if (obj2 instanceof FwdRef) {
                    fwdRef = (FwdRef) obj2;
                    while (true) {
                        if (fwdRef.obj instanceof KvmSerializable) {
                            ((KvmSerializable) fwdRef.obj).setProperty(fwdRef.index, readInstance);
                        } else {
                            ((Vector) fwdRef.obj).setElementAt(readInstance, fwdRef.index);
                        }
                        FwdRef fwdRef2 = fwdRef.next;
                        if (fwdRef2 == null) {
                            break;
                        }
                        fwdRef = fwdRef2;
                    }
                } else if (obj2 != null) {
                    throw new RuntimeException("double ID");
                }
                this.idMap.put(attributeValue2, readInstance);
            }
            obj2 = readInstance;
        } else if (obj == null) {
            throw new RuntimeException("href at root level?!?");
        } else {
            String substring = attributeValue.substring(1);
            obj2 = this.idMap.get(substring);
            if (obj2 == null || (obj2 instanceof FwdRef)) {
                fwdRef = new FwdRef();
                fwdRef.next = (FwdRef) obj2;
                fwdRef.obj = obj;
                fwdRef.index = i;
                this.idMap.put(substring, fwdRef);
                obj2 = null;
            }
            xmlPullParser.nextTag();
            xmlPullParser.require(3, null, name);
        }
        xmlPullParser.require(3, null, name);
        return obj2;
    }

    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        Class cls = this.qNameToClass.get(new SoapPrimitive(str, str2, null));
        if (cls == null) {
            return null;
        }
        if (cls instanceof Marshal) {
            return ((Marshal) cls).readInstance(xmlPullParser, str, str2, propertyInfo);
        }
        Object newInstance;
        if (cls instanceof SoapObject) {
            newInstance = ((SoapObject) cls).newInstance();
        } else {
            Class class$;
            if (class$org$ksoap2$serialization$SoapObject == null) {
                class$ = class$("org.ksoap2.serialization.SoapObject");
                class$org$ksoap2$serialization$SoapObject = class$;
            } else {
                class$ = class$org$ksoap2$serialization$SoapObject;
            }
            if (cls == class$) {
                newInstance = new SoapObject(str, str2);
            } else {
                try {
                    newInstance = cls.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e.toString());
                }
            }
        }
        if (newInstance instanceof SoapObject) {
            readSerializable(xmlPullParser, (SoapObject) newInstance);
            return newInstance;
        } else if (newInstance instanceof KvmSerializable) {
            readSerializable(xmlPullParser, (KvmSerializable) newInstance);
            return newInstance;
        } else if (newInstance instanceof Vector) {
            readVector(xmlPullParser, (Vector) newInstance, propertyInfo.elementType);
            return newInstance;
        } else {
            throw new RuntimeException(new StringBuffer("no deserializer for ").append(newInstance.getClass()).toString());
        }
    }

    protected void readSerializable(XmlPullParser xmlPullParser, KvmSerializable kvmSerializable) throws IOException, XmlPullParserException {
        while (xmlPullParser.nextTag() != 3) {
            String name = xmlPullParser.getName();
            if (this.implicitTypes && (kvmSerializable instanceof SoapObject)) {
                ((SoapObject) kvmSerializable).addProperty(xmlPullParser.getName(), read(xmlPullParser, kvmSerializable, kvmSerializable.getPropertyCount(), ((SoapObject) kvmSerializable).getNamespace(), name, PropertyInfo.OBJECT_TYPE));
            } else {
                PropertyInfo propertyInfo = new PropertyInfo();
                int propertyCount = kvmSerializable.getPropertyCount();
                Object obj = null;
                for (int i = 0; i < propertyCount && obj == null; i++) {
                    propertyInfo.clear();
                    kvmSerializable.getPropertyInfo(i, this.properties, propertyInfo);
                    if ((name.equals(propertyInfo.name) && propertyInfo.namespace == null) || (name.equals(propertyInfo.name) && xmlPullParser.getNamespace().equals(propertyInfo.namespace))) {
                        kvmSerializable.setProperty(i, read(xmlPullParser, kvmSerializable, i, null, null, propertyInfo));
                        obj = 1;
                    }
                }
                if (obj != null) {
                    continue;
                } else if (this.avoidExceptionForUnknownProperty) {
                    while (true) {
                        if (xmlPullParser.next() == 3) {
                            if (name.equals(xmlPullParser.getName())) {
                                break;
                            }
                        }
                    }
                } else {
                    throw new RuntimeException(new StringBuffer("Unknown Property: ").append(name).toString());
                }
            }
        }
        xmlPullParser.require(3, null, null);
    }

    protected void readSerializable(XmlPullParser xmlPullParser, SoapObject soapObject) throws IOException, XmlPullParserException {
        for (int i = 0; i < xmlPullParser.getAttributeCount(); i++) {
            soapObject.addAttribute(xmlPullParser.getAttributeName(i), xmlPullParser.getAttributeValue(i));
        }
        readSerializable(xmlPullParser, (KvmSerializable) soapObject);
    }

    protected Object readUnknown(XmlPullParser xmlPullParser, String str, String str2) throws IOException, XmlPullParserException {
        String str3;
        Object obj;
        int i = 0;
        String name = xmlPullParser.getName();
        String namespace = xmlPullParser.getNamespace();
        Vector vector = new Vector();
        for (int i2 = 0; i2 < xmlPullParser.getAttributeCount(); i2++) {
            AttributeInfo attributeInfo = new AttributeInfo();
            attributeInfo.setName(xmlPullParser.getAttributeName(i2));
            attributeInfo.setValue(xmlPullParser.getAttributeValue(i2));
            attributeInfo.setNamespace(xmlPullParser.getAttributeNamespace(i2));
            attributeInfo.setType(xmlPullParser.getAttributeType(i2));
            vector.addElement(attributeInfo);
        }
        xmlPullParser.next();
        int i3;
        if (xmlPullParser.getEventType() == 4) {
            String text = xmlPullParser.getText();
            SoapPrimitive soapPrimitive = new SoapPrimitive(str, str2, text);
            for (i3 = 0; i3 < vector.size(); i3++) {
                soapPrimitive.addAttribute((AttributeInfo) vector.elementAt(i3));
            }
            xmlPullParser.next();
            str3 = text;
            obj = soapPrimitive;
        } else if (xmlPullParser.getEventType() == 3) {
            SoapObject soapObject = new SoapObject(str, str2);
            for (i3 = 0; i3 < vector.size(); i3++) {
                soapObject.addAttribute((AttributeInfo) vector.elementAt(i3));
            }
            str3 = null;
            SoapObject soapObject2 = soapObject;
        } else {
            str3 = null;
            obj = null;
        }
        if (xmlPullParser.getEventType() == 2) {
            if (str3 == null || str3.trim().length() == 0) {
                obj = new SoapObject(str, str2);
                while (i < vector.size()) {
                    obj.addAttribute((AttributeInfo) vector.elementAt(i));
                    i++;
                }
                while (xmlPullParser.getEventType() != 3) {
                    obj.addProperty(xmlPullParser.getName(), read(xmlPullParser, obj, obj.getPropertyCount(), null, null, PropertyInfo.OBJECT_TYPE));
                    xmlPullParser.nextTag();
                }
            } else {
                throw new RuntimeException("Malformed input: Mixed content");
            }
        }
        xmlPullParser.require(3, namespace, name);
        return obj;
    }

    protected void readVector(XmlPullParser xmlPullParser, Vector vector, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        String substring;
        String namespace;
        int i;
        int i2;
        int size = vector.size();
        String attributeValue = xmlPullParser.getAttributeValue(this.enc, ARRAY_TYPE_LABEL);
        if (attributeValue != null) {
            size = attributeValue.indexOf(58);
            int indexOf = attributeValue.indexOf("[", size);
            substring = attributeValue.substring(size + 1, indexOf);
            namespace = xmlPullParser.getNamespace(size == -1 ? "" : attributeValue.substring(0, size));
            size = getIndex(attributeValue, indexOf, -1);
            if (size != -1) {
                vector.setSize(size);
                i = 0;
                i2 = size;
            } else {
                i = 1;
                i2 = size;
            }
        } else {
            substring = null;
            namespace = null;
            i = 1;
            i2 = size;
        }
        PropertyInfo propertyInfo2 = propertyInfo == null ? PropertyInfo.OBJECT_TYPE : propertyInfo;
        xmlPullParser.nextTag();
        size = getIndex(xmlPullParser.getAttributeValue(this.enc, "offset"), 0, 0);
        while (xmlPullParser.getEventType() != 3) {
            indexOf = getIndex(xmlPullParser.getAttributeValue(this.enc, "position"), 0, size);
            if (i != 0 && indexOf >= r1) {
                i2 = indexOf + 1;
                vector.setSize(i2);
            }
            int i3 = i2;
            vector.setElementAt(read(xmlPullParser, vector, indexOf, namespace, substring, propertyInfo2), indexOf);
            size = indexOf + 1;
            xmlPullParser.nextTag();
            i2 = i3;
        }
        xmlPullParser.require(3, null, null);
    }

    public void setAddAdornments(boolean z) {
        this.addAdornments = z;
    }

    public void setBodyOutEmpty(boolean z) {
        if (z) {
            this.bodyOut = null;
        }
    }

    public void writeBody(XmlSerializer xmlSerializer) throws IOException {
        if (this.bodyOut != null) {
            this.multiRef = new Vector();
            this.multiRef.addElement(this.bodyOut);
            Object[] info = getInfo(null, this.bodyOut);
            xmlSerializer.startTag(this.dotNet ? "" : (String) info[0], (String) info[1]);
            if (this.dotNet) {
                xmlSerializer.attribute(null, "xmlns", (String) info[0]);
            }
            if (this.addAdornments) {
                xmlSerializer.attribute(null, ID_LABEL, info[2] == null ? "o0" : (String) info[2]);
                xmlSerializer.attribute(this.enc, ROOT_LABEL, "1");
            }
            writeElement(xmlSerializer, this.bodyOut, null, info[3]);
            xmlSerializer.endTag(this.dotNet ? "" : (String) info[0], (String) info[1]);
        }
    }

    public void writeObjectBody(XmlSerializer xmlSerializer, KvmSerializable kvmSerializable) throws IOException {
        int propertyCount = kvmSerializable.getPropertyCount();
        PropertyInfo propertyInfo = new PropertyInfo();
        for (int i = 0; i < propertyCount; i++) {
            Object property = kvmSerializable.getProperty(i);
            kvmSerializable.getPropertyInfo(i, this.properties, propertyInfo);
            if (property instanceof SoapObject) {
                SoapObject soapObject = (SoapObject) property;
                Object[] info = getInfo(null, soapObject);
                String str = (String) info[0];
                String str2 = (String) info[1];
                String str3 = (propertyInfo.name == null || propertyInfo.name.length() <= 0) ? (String) info[1] : propertyInfo.name;
                xmlSerializer.startTag(this.dotNet ? "" : str, str3);
                if (!this.implicitTypes) {
                    xmlSerializer.attribute(this.xsi, TYPE_LABEL, new StringBuffer().append(xmlSerializer.getPrefix(str, true)).append(":").append(str2).toString());
                }
                writeObjectBody(xmlSerializer, soapObject);
                if (this.dotNet) {
                    str = "";
                }
                xmlSerializer.endTag(str, str3);
            } else if ((propertyInfo.flags & 1) == 0) {
                xmlSerializer.startTag(propertyInfo.namespace, propertyInfo.name);
                writeProperty(xmlSerializer, kvmSerializable.getProperty(i), propertyInfo);
                xmlSerializer.endTag(propertyInfo.namespace, propertyInfo.name);
            }
        }
    }

    public void writeObjectBody(XmlSerializer xmlSerializer, SoapObject soapObject) throws IOException {
        int attributeCount = soapObject.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            AttributeInfo attributeInfo = new AttributeInfo();
            soapObject.getAttributeInfo(i, attributeInfo);
            xmlSerializer.attribute(attributeInfo.getNamespace(), attributeInfo.getName(), attributeInfo.getValue().toString());
        }
        writeObjectBody(xmlSerializer, (KvmSerializable) soapObject);
    }

    protected void writeProperty(XmlSerializer xmlSerializer, Object obj, PropertyInfo propertyInfo) throws IOException {
        if (obj == null) {
            xmlSerializer.attribute(this.xsi, this.version >= 120 ? NIL_LABEL : NULL_LABEL, "true");
            return;
        }
        Object[] info = getInfo(null, obj);
        if (propertyInfo.multiRef || info[2] != null) {
            int indexOf = this.multiRef.indexOf(obj);
            if (indexOf == -1) {
                indexOf = this.multiRef.size();
                this.multiRef.addElement(obj);
            }
            xmlSerializer.attribute(null, HREF_LABEL, info[2] == null ? new StringBuffer("#o").append(indexOf).toString() : new StringBuffer("#").append(info[2]).toString());
            return;
        }
        if (!(this.implicitTypes && obj.getClass() == propertyInfo.type)) {
            xmlSerializer.attribute(this.xsi, TYPE_LABEL, new StringBuffer().append(xmlSerializer.getPrefix((String) info[0], true)).append(":").append(info[1]).toString());
        }
        writeElement(xmlSerializer, obj, propertyInfo, info[3]);
    }

    protected void writeVectorBody(XmlSerializer xmlSerializer, Vector vector, PropertyInfo propertyInfo) throws IOException {
        String str;
        String str2;
        if (propertyInfo == null) {
            propertyInfo = PropertyInfo.OBJECT_TYPE;
            str = ITEM_LABEL;
            str2 = null;
        } else if (!(propertyInfo instanceof PropertyInfo) || propertyInfo.name == null) {
            str = ITEM_LABEL;
            str2 = null;
        } else {
            String str3 = propertyInfo.name;
            str2 = propertyInfo.namespace;
            str = str3;
        }
        int size = vector.size();
        Object[] info = getInfo(propertyInfo.type, null);
        if (!this.implicitTypes) {
            xmlSerializer.attribute(this.enc, ARRAY_TYPE_LABEL, new StringBuffer().append(xmlSerializer.getPrefix((String) info[0], false)).append(":").append(info[1]).append("[").append(size).append("]").toString());
        }
        boolean z = false;
        for (int i = 0; i < size; i++) {
            if (vector.elementAt(i) == null) {
                z = true;
            } else {
                xmlSerializer.startTag(str2, str);
                if (z) {
                    xmlSerializer.attribute(this.enc, "position", new StringBuffer("[").append(i).append("]").toString());
                    z = false;
                }
                writeProperty(xmlSerializer, vector.elementAt(i), propertyInfo);
                xmlSerializer.endTag(str2, str);
            }
        }
    }
}
