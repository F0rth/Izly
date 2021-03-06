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

public final class fa {
    public static Bundle a(String str, String str2, String str3, double d, String str4, long j) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        String a = SmoneyApplication.c.a(str, str4);
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("cardId", str3);
        hashMap.put("amount", String.valueOf(d));
        hashMap.put("passOTP", a);
        stringBuilder.append(str);
        if (str2 != null) {
            hashMap.put("sessionId", str2);
            stringBuilder.append(",").append(str2);
        }
        stringBuilder.append(",").append(str3).append(",").append(d).append(",").append(a);
        if (j != -1) {
            hashMap.put("engagementId", String.valueOf(j));
            stringBuilder.append(",").append(j);
        }
        ac acVar = SmoneyApplication.c;
        hashMap.put("print", ac.b(a, stringBuilder.toString()));
        String a2 = cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyInCbConfirm", "Service/MoneyInCbConfirm", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        be beVar = new be();
        xMLReader.setContentHandler(beVar);
        xMLReader.parse(new InputSource(new StringReader(a2)));
        Bundle bundle = new Bundle();
        if (beVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", beVar.a);
        }
        if (beVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyInCbConfirmData", beVar.b);
        }
        return bundle;
    }
}
