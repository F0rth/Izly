package defpackage;

import android.os.Bundle;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Locale;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParserException;

public final class fn {
    public static Bundle a(String str, String str2, long j, int i, String str3, double d) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("payRequestId", String.valueOf(j));
        hashMap.put("responseStatus", String.valueOf(i));
        hashMap.put("responseMessage", str3);
        if (d != -1.0d) {
            hashMap.put("amount", String.valueOf(d));
        }
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PPayRequest", "Service/P2PPayRequest", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bs bsVar = new bs();
        xMLReader.setContentHandler(bsVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bsVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bsVar.b);
        }
        if (bsVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.p2pPayRequestData", bsVar.a);
        }
        return bundle;
    }
}
