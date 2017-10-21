package org.ksoap2.serialization;

import java.io.IOException;
import org.kobjects.base64.Base64;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class MarshalBase64 implements Marshal {
    public static Class BYTE_ARRAY_CLASS = new byte[0].getClass();

    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        return Base64.decode(xmlPullParser.nextText());
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "base64Binary", BYTE_ARRAY_CLASS, this);
        soapSerializationEnvelope.addMapping(SoapEnvelope.ENC, "base64", BYTE_ARRAY_CLASS, this);
    }

    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        xmlSerializer.text(Base64.encode((byte[]) obj));
    }
}
