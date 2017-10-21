package org.ksoap2.serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Vector;

public class PropertyInfo implements Serializable {
    public static final Class BOOLEAN_CLASS = new Boolean(true).getClass();
    public static final Class INTEGER_CLASS = new Integer(0).getClass();
    public static final Class LONG_CLASS = new Long(0).getClass();
    public static final int MULTI_REF = 2;
    public static final Class OBJECT_CLASS = new Object().getClass();
    public static final PropertyInfo OBJECT_TYPE = new PropertyInfo();
    public static final int REF_ONLY = 4;
    public static final Class STRING_CLASS = "".getClass();
    public static final int TRANSIENT = 1;
    public static final Class VECTOR_CLASS = new Vector().getClass();
    public PropertyInfo elementType;
    public int flags;
    public boolean multiRef;
    public String name;
    public String namespace;
    public Object type = OBJECT_CLASS;
    protected Object value;

    public void clear() {
        this.type = OBJECT_CLASS;
        this.flags = 0;
        this.name = null;
        this.namespace = null;
    }

    public Object clone() {
        Object obj = null;
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(this);
            objectOutputStream.flush();
            objectOutputStream.close();
            obj = new ObjectInputStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray())).readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NotSerializableException e2) {
            e2.printStackTrace();
        } catch (IOException e3) {
            e3.printStackTrace();
        }
        return obj;
    }

    public PropertyInfo getElementType() {
        return this.elementType;
    }

    public int getFlags() {
        return this.flags;
    }

    public String getName() {
        return this.name;
    }

    public String getNamespace() {
        return this.namespace;
    }

    public Object getType() {
        return this.type;
    }

    public Object getValue() {
        return this.value;
    }

    public boolean isMultiRef() {
        return this.multiRef;
    }

    public void setElementType(PropertyInfo propertyInfo) {
        this.elementType = propertyInfo;
    }

    public void setFlags(int i) {
        this.flags = i;
    }

    public void setMultiRef(boolean z) {
        this.multiRef = z;
    }

    public void setName(String str) {
        this.name = str;
    }

    public void setNamespace(String str) {
        this.namespace = str;
    }

    public void setType(Object obj) {
        this.type = obj;
    }

    public void setValue(Object obj) {
        this.value = obj;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.name);
        stringBuffer.append(" : ");
        if (this.value != null) {
            stringBuffer.append(this.value);
        } else {
            stringBuffer.append("(not set)");
        }
        return stringBuffer.toString();
    }
}
