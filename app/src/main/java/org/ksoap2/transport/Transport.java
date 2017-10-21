package org.ksoap2.transport;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.util.List;
import org.ksoap2.SoapEnvelope;
import org.kxml2.io.KXmlParser;
import org.kxml2.io.KXmlSerializer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

public abstract class Transport {
    protected static final String CONTENT_TYPE_SOAP_XML_CHARSET_UTF_8 = "application/soap+xml;charset=utf-8";
    protected static final String CONTENT_TYPE_XML_CHARSET_UTF_8 = "text/xml;charset=utf-8";
    protected static final String USER_AGENT = "ksoap2-android/2.6.0+";
    private int bufferLength;
    public boolean debug;
    protected Proxy proxy;
    public String requestDump;
    public String responseDump;
    protected int timeout;
    protected String url;
    private String xmlVersionTag;

    public Transport() {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
    }

    public Transport(String str) {
        this(null, str);
    }

    public Transport(String str, int i) {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
        this.url = str;
        this.timeout = i;
    }

    public Transport(String str, int i, int i2) {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
        this.url = str;
        this.timeout = i;
        this.bufferLength = i2;
    }

    public Transport(Proxy proxy, String str) {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
        this.proxy = proxy;
        this.url = str;
    }

    public Transport(Proxy proxy, String str, int i) {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
        this.proxy = proxy;
        this.url = str;
        this.timeout = i;
    }

    public Transport(Proxy proxy, String str, int i, int i2) {
        this.timeout = 20000;
        this.xmlVersionTag = "";
        this.bufferLength = 262144;
        this.proxy = proxy;
        this.url = str;
        this.timeout = i;
        this.bufferLength = i2;
    }

    public abstract List call(String str, SoapEnvelope soapEnvelope, List list) throws IOException, XmlPullParserException;

    public void call(String str, SoapEnvelope soapEnvelope) throws IOException, XmlPullParserException {
        call(str, soapEnvelope, null);
    }

    protected byte[] createRequestData(SoapEnvelope soapEnvelope) throws IOException {
        return createRequestData(soapEnvelope, null);
    }

    protected byte[] createRequestData(SoapEnvelope soapEnvelope, String str) throws IOException {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream(this.bufferLength);
        byteArrayOutputStream.write(this.xmlVersionTag.getBytes());
        XmlSerializer kXmlSerializer = new KXmlSerializer();
        kXmlSerializer.setOutput(byteArrayOutputStream, str);
        soapEnvelope.write(kXmlSerializer);
        kXmlSerializer.flush();
        byteArrayOutputStream.write(13);
        byteArrayOutputStream.write(10);
        byteArrayOutputStream.flush();
        return byteArrayOutputStream.toByteArray();
    }

    public abstract String getHost();

    public abstract String getPath();

    public abstract int getPort();

    public abstract ServiceConnection getServiceConnection() throws IOException;

    protected void parseResponse(SoapEnvelope soapEnvelope, InputStream inputStream) throws XmlPullParserException, IOException {
        XmlPullParser kXmlParser = new KXmlParser();
        kXmlParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, true);
        kXmlParser.setInput(inputStream, null);
        soapEnvelope.parse(kXmlParser);
        inputStream.close();
    }

    public void reset() {
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public void setXmlVersionTag(String str) {
        this.xmlVersionTag = str;
    }
}
