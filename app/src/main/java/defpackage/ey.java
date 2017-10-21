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

public final class ey {
    public static Bundle a(String str, String str2, String str3, long j, double d, double d2, long j2, int i, String str4) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("cardId", str3);
        if (j != -1) {
            hashMap.put("engagementId", String.valueOf(j));
        }
        hashMap.put("cbAmount", String.valueOf(d));
        hashMap.put("amount", String.valueOf(d2));
        hashMap.put("payRequestId", String.valueOf(j2));
        hashMap.put("responseStatus", String.valueOf(i));
        hashMap.put("responseMessage", str4);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyInCbAndP2PPayRequest", "Service/MoneyInCbAndP2PPayRequest", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bc bcVar = new bc();
        xMLReader.setContentHandler(bcVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bcVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bcVar.a);
        }
        if (bcVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestData", bcVar.b);
        }
        return bundle;
    }
}
