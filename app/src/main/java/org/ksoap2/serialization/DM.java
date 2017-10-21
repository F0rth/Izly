package org.ksoap2.serialization;

import java.io.IOException;
import org.ksoap2.SoapEnvelope;
import org.spongycastle.asn1.eac.EACTags;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

class DM implements Marshal {
    DM() {
    }

    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        String nextText = xmlPullParser.nextText();
        switch (str2.charAt(0)) {
            case 'b':
                return new Boolean(SoapEnvelope.stringToBoolean(nextText));
            case 'i':
                return new Integer(Integer.parseInt(nextText));
            case EACTags.CARDHOLDER_IMAGE_TEMPLATE /*108*/:
                return new Long(Long.parseLong(nextText));
            case EACTags.DISCRETIONARY_DATA_OBJECTS /*115*/:
                return nextText;
            default:
                throw new RuntimeException();
        }
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "int", PropertyInfo.INTEGER_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "long", PropertyInfo.LONG_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "string", PropertyInfo.STRING_CLASS, this);
        soapSerializationEnvelope.addMapping(soapSerializationEnvelope.xsd, "boolean", PropertyInfo.BOOLEAN_CLASS, this);
    }

    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        if (obj instanceof AttributeContainer) {
            AttributeContainer attributeContainer = (AttributeContainer) obj;
            int attributeCount = attributeContainer.getAttributeCount();
            for (int i = 0; i < attributeCount; i++) {
                AttributeInfo attributeInfo = new AttributeInfo();
                attributeContainer.getAttributeInfo(i, attributeInfo);
                xmlSerializer.attribute(attributeInfo.getNamespace(), attributeInfo.getName(), attributeInfo.getValue().toString());
            }
        }
        xmlSerializer.text(obj.toString());
    }
}
