package defpackage;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
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

public class fy {
    private static final String a = fy.class.getSimpleName();

    public static Bundle a(Context context, String str, String str2, fy$a fy_a) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        String register = GoogleCloudMessaging.getInstance(context.getApplicationContext()).register(new String[]{"1074937585998"});
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        hashMap.put("token", register);
        hashMap.put("tokentype", fy_a.c);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "SetDeviceToken", "Service/SetDeviceToken", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bx bxVar = new bx();
        xMLReader.setContentHandler(bxVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bxVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bxVar.a);
        }
        if (bxVar.b != null) {
            bxVar.b.a = register;
            bundle.putParcelable("fr.smoney.android.izly.extras.setDeviceTokenData", bxVar.b);
        }
        if (((ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError")) == null) {
            jg.a(context, register);
        }
        return bundle;
    }
}
