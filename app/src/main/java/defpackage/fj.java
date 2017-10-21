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

public final class fj {
    public static Bundle a(String str, String str2, String str3, String str4) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("getmult", str3);
        hashMap.put("message", str4);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PGetMult", "Service/P2PGetMult", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bo boVar = new bo();
        xMLReader.setContentHandler(boVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (boVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", boVar.a);
        }
        if (boVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.p2pGetMultData", boVar.b);
        }
        return bundle;
    }
}
