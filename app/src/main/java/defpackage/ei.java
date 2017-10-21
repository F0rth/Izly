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

public final class ei {
    public static Bundle a(String str) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "GetSecretQuestion", "Service/GetSecretQuestion", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        at atVar = new at();
        xMLReader.setContentHandler(atVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (atVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", atVar.a);
        }
        if (atVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.getSecretQuestionData", atVar.b);
        }
        return bundle;
    }
}
