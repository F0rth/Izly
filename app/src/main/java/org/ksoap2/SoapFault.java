package org.ksoap2;

import java.io.IOException;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SoapFault extends IOException {
    private static final long serialVersionUID = 1011001;
    public Node detail;
    public String faultactor;
    public String faultcode;
    public String faultstring;
    public int version;

    public SoapFault() {
        this.version = 110;
    }

    public SoapFault(int i) {
        this.version = i;
    }

    public String getMessage() {
        return this.faultstring;
    }

    public void parse(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, SoapEnvelope.ENV, "Fault");
        while (xmlPullParser.nextTag() == 2) {
            String name = xmlPullParser.getName();
            if (name.equals("detail")) {
                this.detail = new Node();
                this.detail.parse(xmlPullParser);
                if (xmlPullParser.getNamespace().equals(SoapEnvelope.ENV) && xmlPullParser.getName().equals("Fault")) {
                    break;
                }
            }
            if (name.equals("faultcode")) {
                this.faultcode = xmlPullParser.nextText();
            } else if (name.equals("faultstring")) {
                this.faultstring = xmlPullParser.nextText();
            } else if (name.equals("faultactor")) {
                this.faultactor = xmlPullParser.nextText();
            } else {
                throw new RuntimeException(new StringBuffer("unexpected tag:").append(name).toString());
            }
            xmlPullParser.require(3, null, name);
        }
        xmlPullParser.require(3, SoapEnvelope.ENV, "Fault");
        xmlPullParser.nextTag();
    }

    public String toString() {
        return new StringBuffer("SoapFault - faultcode: '").append(this.faultcode).append("' faultstring: '").append(this.faultstring).append("' faultactor: '").append(this.faultactor).append("' detail: ").append(this.detail).toString();
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(SoapEnvelope.ENV, "Fault");
        xmlSerializer.startTag(null, "faultcode");
        xmlSerializer.text(new StringBuffer().append(this.faultcode).toString());
        xmlSerializer.endTag(null, "faultcode");
        xmlSerializer.startTag(null, "faultstring");
        xmlSerializer.text(new StringBuffer().append(this.faultstring).toString());
        xmlSerializer.endTag(null, "faultstring");
        xmlSerializer.startTag(null, "detail");
        if (this.detail != null) {
            this.detail.write(xmlSerializer);
        }
        xmlSerializer.endTag(null, "detail");
        xmlSerializer.endTag(SoapEnvelope.ENV, "Fault");
    }
}
