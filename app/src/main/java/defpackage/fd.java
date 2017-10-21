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

public final class fd {
    public static Bundle a(String str, String str2, double d, String str3, String str4) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        String a = SmoneyApplication.c.a(str, str4);
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("amount", String.valueOf(d));
        hashMap.put("label", str3);
        hashMap.put("passOTP", a);
        stringBuilder.append(str);
        if (str2 != null) {
            hashMap.put("sessionId", str2);
            stringBuilder.append(",").append(str2);
        }
        stringBuilder.append(",").append(d).append(",").append(str3).append(",").append(a);
        ac acVar = SmoneyApplication.c;
        hashMap.put("print", ac.b(a, stringBuilder.toString()));
        String a2 = cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyOutTransferConfirm", "Service/MoneyOutTransferConfirm", "2.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bh bhVar = new bh();
        xMLReader.setContentHandler(bhVar);
        xMLReader.parse(new InputSource(new StringReader(a2)));
        Bundle bundle = new Bundle();
        if (bhVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bhVar.a);
        }
        if (bhVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyOutTransferConfirmData", bhVar.b);
        }
        return bundle;
    }
}
