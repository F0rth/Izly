package org.ksoap2;

import java.io.IOException;
import org.kxml2.kdom.Element;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SoapEnvelope {
    public static final String ENC = "http://schemas.xmlsoap.org/soap/encoding/";
    public static final String ENC2003 = "http://www.w3.org/2003/05/soap-encoding";
    public static final String ENV = "http://schemas.xmlsoap.org/soap/envelope/";
    public static final String ENV2003 = "http://www.w3.org/2003/05/soap-envelope";
    public static final int VER10 = 100;
    public static final int VER11 = 110;
    public static final int VER12 = 120;
    public static final String XSD = "http://www.w3.org/2001/XMLSchema";
    public static final String XSD1999 = "http://www.w3.org/1999/XMLSchema";
    public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XSI1999 = "http://www.w3.org/1999/XMLSchema-instance";
    public Object bodyIn;
    public Object bodyOut;
    public String enc;
    public String encodingStyle;
    public String env;
    public Element[] headerIn;
    public Element[] headerOut;
    public int version;
    public String xsd;
    public String xsi;

    public SoapEnvelope(int i) {
        this.version = i;
        if (i == 100) {
            this.xsi = XSI1999;
            this.xsd = XSD1999;
        } else {
            this.xsi = XSI;
            this.xsd = XSD;
        }
        if (i < 120) {
            this.enc = ENC;
            this.env = ENV;
            return;
        }
        this.enc = ENC2003;
        this.env = ENV2003;
    }

    public static boolean stringToBoolean(String str) {
        if (str != null) {
            String toLowerCase = str.trim().toLowerCase();
            if (toLowerCase.equals("1") || toLowerCase.equals("true")) {
                return true;
            }
        }
        return false;
    }

    public void parse(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.nextTag();
        xmlPullParser.require(2, this.env, "Envelope");
        this.encodingStyle = xmlPullParser.getAttributeValue(this.env, "encodingStyle");
        xmlPullParser.nextTag();
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getNamespace().equals(this.env) && xmlPullParser.getName().equals("Header")) {
            parseHeader(xmlPullParser);
            xmlPullParser.require(3, this.env, "Header");
            xmlPullParser.nextTag();
        }
        xmlPullParser.require(2, this.env, "Body");
        this.encodingStyle = xmlPullParser.getAttributeValue(this.env, "encodingStyle");
        parseBody(xmlPullParser);
        xmlPullParser.require(3, this.env, "Body");
        xmlPullParser.nextTag();
        xmlPullParser.require(3, this.env, "Envelope");
    }

    public void parseBody(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.nextTag();
        if (xmlPullParser.getEventType() == 2 && xmlPullParser.getNamespace().equals(this.env) && xmlPullParser.getName().equals("Fault")) {
            SoapFault soapFault = this.version < 120 ? new SoapFault(this.version) : new SoapFault12(this.version);
            soapFault.parse(xmlPullParser);
            this.bodyIn = soapFault;
            return;
        }
        Node node = this.bodyIn instanceof Node ? (Node) this.bodyIn : new Node();
        node.parse(xmlPullParser);
        this.bodyIn = node;
    }

    public void parseHeader(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        int i = 0;
        xmlPullParser.nextTag();
        Node node = new Node();
        node.parse(xmlPullParser);
        int i2 = 0;
        for (int i3 = 0; i3 < node.getChildCount(); i3++) {
            if (node.getElement(i3) != null) {
                i2++;
            }
        }
        this.headerIn = new Element[i2];
        i2 = 0;
        while (i < node.getChildCount()) {
            Element element = node.getElement(i);
            if (element != null) {
                this.headerIn[i2] = element;
                i2++;
            }
            i++;
        }
    }

    public void setOutputSoapObject(Object obj) {
        this.bodyOut = obj;
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.setPrefix("i", this.xsi);
        xmlSerializer.setPrefix("d", this.xsd);
        xmlSerializer.setPrefix("c", this.enc);
        xmlSerializer.setPrefix("v", this.env);
        xmlSerializer.startTag(this.env, "Envelope");
        xmlSerializer.startTag(this.env, "Header");
        writeHeader(xmlSerializer);
        xmlSerializer.endTag(this.env, "Header");
        xmlSerializer.startTag(this.env, "Body");
        writeBody(xmlSerializer);
        xmlSerializer.endTag(this.env, "Body");
        xmlSerializer.endTag(this.env, "Envelope");
    }

    public void writeBody(XmlSerializer xmlSerializer) throws IOException {
        if (this.encodingStyle != null) {
            xmlSerializer.attribute(this.env, "encodingStyle", this.encodingStyle);
        }
        ((Node) this.bodyOut).write(xmlSerializer);
    }

    public void writeHeader(XmlSerializer xmlSerializer) throws IOException {
        if (this.headerOut != null) {
            for (Element write : this.headerOut) {
                write.write(xmlSerializer);
            }
        }
    }
}
