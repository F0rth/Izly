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

public final class cv {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("bookmarkId", str3);
        hashMap.put("bookmarkFName", str4);
        hashMap.put("bookmarkLName", str5);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "AddBookmark", "Service/AddBookmark", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        ah ahVar = new ah();
        xMLReader.setContentHandler(ahVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (ahVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", ahVar.a);
        }
        if (ahVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.addBookmarkData", ahVar.b);
        }
        return bundle;
    }
}
