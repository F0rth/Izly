package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
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

public final class fm {
    public static Bundle a(String str, String str2, int i, long j, int i2) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("firstId", String.valueOf(j));
        hashMap.put("nbItems", String.valueOf(i));
        hashMap.put("filter", String.valueOf(i2));
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PPayRequestList", "Service/P2PPayRequestList", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bu buVar = new bu();
        xMLReader.setContentHandler(buVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (buVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", buVar.a);
        }
        if (buVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.p2pPayRequestListData", buVar.b);
        }
        return bundle;
    }
}
