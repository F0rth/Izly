package org.ksoap2.serialization;

import java.io.IOException;
import java.math.BigDecimal;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class MarshalFloat implements Marshal {
    static Class class$java$lang$Double;
    static Class class$java$lang$Float;
    static Class class$java$math$BigDecimal;

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    public Object readInstance(XmlPullParser xmlPullParser, String str, String str2, PropertyInfo propertyInfo) throws IOException, XmlPullParserException {
        String nextText = xmlPullParser.nextText();
        if (str2.equals("float")) {
            return new Float(nextText);
        }
        if (str2.equals("double")) {
            return new Double(nextText);
        }
        if (str2.equals("decimal")) {
            return new BigDecimal(nextText);
        }
        throw new RuntimeException("float, double, or decimal expected");
    }

    public void register(SoapSerializationEnvelope soapSerializationEnvelope) {
        Class class$;
        String str = soapSerializationEnvelope.xsd;
        if (class$java$lang$Float == null) {
            class$ = class$("java.lang.Float");
            class$java$lang$Float = class$;
        } else {
            class$ = class$java$lang$Float;
        }
        soapSerializationEnvelope.addMapping(str, "float", class$, this);
        str = soapSerializationEnvelope.xsd;
        if (class$java$lang$Double == null) {
            class$ = class$("java.lang.Double");
            class$java$lang$Double = class$;
        } else {
            class$ = class$java$lang$Double;
        }
        soapSerializationEnvelope.addMapping(str, "double", class$, this);
        str = soapSerializationEnvelope.xsd;
        if (class$java$math$BigDecimal == null) {
            class$ = class$("java.math.BigDecimal");
            class$java$math$BigDecimal = class$;
        } else {
            class$ = class$java$math$BigDecimal;
        }
        soapSerializationEnvelope.addMapping(str, "decimal", class$, this);
    }

    public void writeInstance(XmlSerializer xmlSerializer, Object obj) throws IOException {
        xmlSerializer.text(obj.toString());
    }
}
