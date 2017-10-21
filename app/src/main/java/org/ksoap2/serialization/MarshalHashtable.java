package org.ksoap2.serialization;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class MarshalHashtable implements Marshal {
    public static final Class HASHTABLE_CLASS = new Hashtable().getClass();
    public static final String NAME = "Map";
    public static final String NAMESPACE = "http://xml.apache.org/xml-soap";
    SoapSerializationEnvelope envelope;

    class ItemSoapObject extends SoapObject {
        Hashtable h;
        int resolvedIndex = -1;
        private final MarshalHashtable this$0;

        ItemSoapObject(MarshalHashtable marshalHashtable, Hashtable hashtable) {
            super(null, null);
            this.this$0 = marshalHashtable;
            this.h = hashtable;
            addProperty("key", null);
            addProperty("value", null);
        }

        public void setProperty(int i, Object obj) {
            if (this.resolvedIndex == -1) {
                super.setProperty(i, obj);
                this.resolvedIndex = i;
                return;
            }
            Object property = this.resolvedIndex == 0 ? getProperty(0) : getProperty(1);
            if (i == 0) {
                this.h.put(obj, property);
            } else {
                this.h.put(property, obj);
            }
        }
    }

    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        Hashtable hashtable = new Hashtable();
        String name = xmlPullParser.getName();
        while (xmlPullParser.nextTag() != 3) {
            SoapObject itemSoapObject = new ItemSoapObject(this, hashtable);
            xmlPullParser.require(2, null, "item");
            xmlPullParser.nextTag();
            Object read = this.envelope.read(xmlPullParser, itemSoapObject, 0, null, null, PropertyInfo.OBJECT_TYPE);
            xmlPullParser.nextTag();
            if (read != null) {
                itemSoapObject.setProperty(0, read);
            }
            read = this.envelope.read(xmlPullParser, itemSoapObject, 1, null, null, PropertyInfo.OBJECT_TYPE);
            xmlPullParser.nextTag();
            if (read != null) {
                itemSoapObject.setProperty(1, read);
            }
            xmlPullParser.require(3, null, "item");
        }
        xmlPullParser.require(3, null, name);
        return hashtable;
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        this.envelope = soapSerializationEnvelope;
        soapSerializationEnvelope.addMapping(NAMESPACE, NAME, HASHTABLE_CLASS, this);
    }

    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        Hashtable hashtable = (Hashtable) obj;
        SoapObject soapObject = new SoapObject(null, null);
        soapObject.addProperty("key", null);
        soapObject.addProperty("value", null);
        Enumeration keys = hashtable.keys();
        while (keys.hasMoreElements()) {
            xmlSerializer.startTag("", "item");
            Object nextElement = keys.nextElement();
            soapObject.setProperty(0, nextElement);
            soapObject.setProperty(1, hashtable.get(nextElement));
            this.envelope.writeObjectBody(xmlSerializer, soapObject);
            xmlSerializer.endTag("", "item");
        }
    }
}
