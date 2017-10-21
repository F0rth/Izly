package org.ksoap2;

import java.io.IOException;
import org.kxml2.kdom.Node;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public class SoapFault12 extends SoapFault {
    private static final long serialVersionUID = 1012001;
    public Node Code;
    public Node Detail;
    public Node Node;
    public Node Reason;
    public Node Role;

    public SoapFault12() {
        this.version = 120;
    }

    public SoapFault12(int i) {
        this.version = i;
    }

    private void parseSelf(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        xmlPullParser.require(2, SoapEnvelope.ENV2003, "Fault");
        while (xmlPullParser.nextTag() == 2) {
            String name = xmlPullParser.getName();
            xmlPullParser.nextTag();
            if (name.equals("Code")) {
                this.Code = new Node();
                this.Code.parse(xmlPullParser);
            } else if (name.equals("Reason")) {
                this.Reason = new Node();
                this.Reason.parse(xmlPullParser);
            } else if (name.equals("Node")) {
                this.Node = new Node();
                this.Node.parse(xmlPullParser);
            } else if (name.equals("Role")) {
                this.Role = new Node();
                this.Role.parse(xmlPullParser);
            } else if (name.equals("Detail")) {
                this.Detail = new Node();
                this.Detail.parse(xmlPullParser);
            } else {
                throw new RuntimeException(new StringBuffer("unexpected tag:").append(name).toString());
            }
            xmlPullParser.require(3, SoapEnvelope.ENV2003, name);
        }
        xmlPullParser.require(3, SoapEnvelope.ENV2003, "Fault");
        xmlPullParser.nextTag();
    }

    public String getMessage() {
        return this.Reason.getElement(SoapEnvelope.ENV2003, "Text").getText(0);
    }

    public void parse(XmlPullParser xmlPullParser) throws IOException, XmlPullParserException {
        parseSelf(xmlPullParser);
        this.faultcode = this.Code.getElement(SoapEnvelope.ENV2003, "Value").getText(0);
        this.faultstring = this.Reason.getElement(SoapEnvelope.ENV2003, "Text").getText(0);
        this.detail = this.Detail;
        this.faultactor = null;
    }

    public String toString() {
        return new StringBuffer("Code: ").append(this.Code.getElement(SoapEnvelope.ENV2003, "Value").getText(0)).append(", Reason: ").append(this.Reason.getElement(SoapEnvelope.ENV2003, "Text").getText(0)).toString();
    }

    public void write(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(SoapEnvelope.ENV2003, "Fault");
        xmlSerializer.startTag(SoapEnvelope.ENV2003, "Code");
        this.Code.write(xmlSerializer);
        xmlSerializer.endTag(SoapEnvelope.ENV2003, "Code");
        xmlSerializer.startTag(SoapEnvelope.ENV2003, "Reason");
        this.Reason.write(xmlSerializer);
        xmlSerializer.endTag(SoapEnvelope.ENV2003, "Reason");
        if (this.Node != null) {
            xmlSerializer.startTag(SoapEnvelope.ENV2003, "Node");
            this.Node.write(xmlSerializer);
            xmlSerializer.endTag(SoapEnvelope.ENV2003, "Node");
        }
        if (this.Role != null) {
            xmlSerializer.startTag(SoapEnvelope.ENV2003, "Role");
            this.Role.write(xmlSerializer);
            xmlSerializer.endTag(SoapEnvelope.ENV2003, "Role");
        }
        if (this.Detail != null) {
            xmlSerializer.startTag(SoapEnvelope.ENV2003, "Detail");
            this.Detail.write(xmlSerializer);
            xmlSerializer.endTag(SoapEnvelope.ENV2003, "Detail");
        }
        xmlSerializer.endTag(SoapEnvelope.ENV2003, "Fault");
    }
}
