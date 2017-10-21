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

public final class fl {
    public static Bundle a(String str, String str2, long j) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("payRequestId", String.valueOf(j));
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "P2PPayRequestHide", "Service/P2PPayRequestHide", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bt btVar = new bt();
        xMLReader.setContentHandler(btVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        bundle.putLong("fr.smoney.android.izly.extras.p2pPayRequestHideIdToHide", btVar.a);
        if (btVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", btVar.b);
        }
        return bundle;
    }
}
