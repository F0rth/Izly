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

public final class ex {
    public static Bundle a(String str, String str2, String str3, long j, double d, double d2, long j2, int i, String str4, String str5) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        StringBuilder stringBuilder = new StringBuilder();
        String a = SmoneyApplication.c.a(str, str5);
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("cardId", str3);
        hashMap.put("payRequestId", String.valueOf(j2));
        hashMap.put("responseStatus", String.valueOf(i));
        hashMap.put("responseMessage", str4);
        hashMap.put("passOTP", a);
        hashMap.put("cbAmount", String.valueOf(d));
        stringBuilder.append(str);
        if (str2 != null) {
            hashMap.put("sessionId", str2);
            stringBuilder.append(",").append(str2);
        }
        stringBuilder.append(",").append(str3).append(",").append(d).append(",").append(j2).append(",").append(i).append(",").append(str4);
        if (d2 != -1.0d) {
            hashMap.put("amount", String.valueOf(d2));
            stringBuilder.append(",").append(d2);
        }
        stringBuilder.append(",").append(a);
        if (j != -1) {
            hashMap.put("engagementId", String.valueOf(j));
            stringBuilder.append(",").append(j);
        }
        ac acVar = SmoneyApplication.c;
        hashMap.put("print", ac.b(a, stringBuilder.toString()));
        String a2 = cr.a("https://soap.izly.fr/Service.asmx", "Service", "MoneyInCbAndP2PPayRequestConfirm", "Service/MoneyInCbAndP2PPayRequestConfirm", "3.0", hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bb bbVar = new bb();
        xMLReader.setContentHandler(bbVar);
        xMLReader.parse(new InputSource(new StringReader(a2)));
        Bundle bundle = new Bundle();
        if (bbVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bbVar.a);
        }
        if (bbVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.moneyInCbAndPayRequestConfirmData", bbVar.b);
        }
        return bundle;
    }
}
