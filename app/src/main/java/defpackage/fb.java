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

public final class fb {
    public static Bundle a(String str, String str2, String str3, double d, long j) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("cardId", str3);
        hashMap.put("amount", String.valueOf(d));
        if (j != -1) {
            hashMap.put("engagementId", String.valueOf(j));
        }
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyInCb", "Service/MoneyInCb", "2.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bf bfVar = new bf();
        xMLReader.setContentHandler(bfVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bfVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bfVar.a);
        }
        if (bfVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyInCbData", bfVar.b);
        }
        return bundle;
    }
}
