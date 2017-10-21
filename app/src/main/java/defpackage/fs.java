package defpackage;

import android.content.Context;
import android.os.Bundle;
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

public class fs {
    public static final String a = fs.class.getSimpleName();

    public static Bundle a(Context context, String str, String str2) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException {
        HashMap hashMap = new HashMap();
        hashMap.put("language", Locale.getDefault().getLanguage());
        hashMap.put("userId", str);
        hashMap.put("sessionId", str2);
        String a = cr.a("https://soap.izly.fr/Service.asmx", "Service", "RemoveDeviceToken", "Service/RemoveDeviceToken", BuildConfig.VERSION_NAME, hashMap);
        XMLReader xMLReader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        bv bvVar = new bv();
        xMLReader.setContentHandler(bvVar);
        xMLReader.parse(new InputSource(new StringReader(a)));
        Bundle bundle = new Bundle();
        if (bvVar.a != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", bvVar.a);
        }
        if (bvVar.b != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.removeDeviceTokenData", bvVar.b);
        }
        if (((ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError")) == null) {
            jg.a(context, "");
        }
        return bundle;
    }
}
