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

public final class ff {
    public static Bundle a(String str, String str2, long j, int i) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("getId", String.valueOf(j));
        hashMap.put("getType", String.valueOf(i));
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PGetCancel", "Service/P2PGetCancel", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bk bkVar = new bk();
        xMLReader.setContentHandler(bkVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        bundle.putLong("fr.smoney.android.izly.extras.p2pGetCancelIdToCancel", bkVar.a);
        if (bkVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bkVar.b);
        }
        return bundle;
    }
}
