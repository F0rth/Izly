package defpackage;

import android.os.Bundle;
import fr.smoney.android.izly.SmoneyApplication;
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

public final class fk {
    public static Bundle a(String str, String str2, long j, int i, String str3, double d, String str4) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        String a = SmoneyApplication.c.a(str, str4);
        hashMap.put("userId", str);
        hashMap.put("passOTP", a);
        hashMap.put("payRequestId", String.valueOf(j));
        hashMap.put("responseStatus", String.valueOf(i));
        hashMap.put("responseMessage", str3);
        hashMap.put("language", Locale.getDefault().getLanguage());
        stringBuilder.append(str);
        if (str2 != null) {
            hashMap.put("sessionId", str2);
            stringBuilder.append(",").append(str2);
        }
        stringBuilder.append(",").append(j).append(",").append(i).append(",").append(str3);
        if (d != -1.0d) {
            hashMap.put("amount", String.valueOf(d));
            stringBuilder.append(",").append(d);
        }
        stringBuilder.append(",").append(a);
        ac acVar = SmoneyApplication.c;
        hashMap.put("print", ac.b(a, stringBuilder.toString()));
        String a2 = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PPayRequestConfirm", "Service/P2PPayRequestConfirm", "2.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        br brVar = new br();
        xMLReader.setContentHandler(brVar);
        xMLReader.parse(new InputSource(new StringReader(a2)));
        Bundle bundle = new Bundle();
        if (brVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", brVar.b);
        }
        if (brVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.p2pPayRequestConfirmData", brVar.a);
        }
        return bundle;
    }
}
