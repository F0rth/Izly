package org.ksoap2.transport;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.xmlpull.v1.XmlPullParserException;

public class KeepAliveHttpTransportSE extends HttpTransportSE {
    public KeepAliveHttpTransportSE(String str) {
        super(null, str);
    }

    public KeepAliveHttpTransportSE(String str, int i) {
        super(str, i);
    }

    public KeepAliveHttpTransportSE(String str, int i, int i2) {
        super(str, i);
    }

    public KeepAliveHttpTransportSE(Proxy proxy, String str) {
        super(proxy, str);
    }

    public KeepAliveHttpTransportSE(Proxy proxy, String str, int i) {
        super(proxy, str, i);
    }

    public KeepAliveHttpTransportSE(Proxy proxy, String str, int i, int i2) {
        super(proxy, str, i);
    }

    public List call(String str, SoapEnvelope soapEnvelope, List list) throws IOException, XmlPullParserException {
        if (list == null) {
            list = new ArrayList();
        }
        HeaderProperty header = getHeader(list, "Connection");
        if (header == null) {
            list.add(new HeaderProperty("Connection", "keep-alive"));
        } else {
            header.setValue("keep-alive");
        }
        return super.call(str, soapEnvelope, list);
    }

    protected HeaderProperty getHeader(List list, String str) {
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                HeaderProperty headerProperty = (HeaderProperty) list.get(i);
                if (str.equals(headerProperty.getKey())) {
                    return headerProperty;
                }
            }
        }
        return null;
    }
}
