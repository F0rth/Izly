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

public final class fu {
    public static Bundle a(String str, String str2, boolean z) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("secretAnswer", str2);
        hashMap.put("unlockAccount", z ? "1" : "0");
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "SecretQuestionAnswer", "Service/SecretQuestionAnswer", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bw bwVar = new bw();
        xMLReader.setContentHandler(bwVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bwVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bwVar.a);
        }
        if (bwVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.secretQuestionAnswerData", bwVar.b);
        }
        return bundle;
    }
}
