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

public final class fi {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(str);
        if (str2 != null) {
            hashMap.put("sessionId", str2);
            stringBuilder.append(",").append(str2);
        }
        stringBuilder.append(",").append(str3).append(",").append(str4);
        if (str5 != null) {
            String a = SmoneyApplication.c.a(str, str5);
            hashMap.put("passOTP", a);
            stringBuilder.append(",").append(a);
            ac acVar = SmoneyApplication.c;
            hashMap.put("print", ac.b(a, stringBuilder.toString()));
        }
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("getmult", str3);
        hashMap.put("message", str4);
        String a2 = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PGetMultConfirm", "Service/P2PGetMultConfirm", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bn bnVar = new bn();
        xMLReader.setContentHandler(bnVar);
        xMLReader.parse(new InputSource(new StringReader(a2)));
        Bundle bundle = new Bundle();
        if (bnVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bnVar.a);
        }
        if (bnVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.p2pGetMultConfirmData", bnVar.b);
        }
        return bundle;
    }
}
