package defpackage;

import fr.smoney.android.izly.SmoneyApplication;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.NameValuePair;

public class jl {
    private static final String a = jl.class.getSimpleName();

    public static String a(int i) {
        return String.format("https://mon-espace.izly.fr/tools/Photos/GetPromotionalOfferPhoto?id=%s&display=0", new Object[]{Integer.valueOf(i)});
    }

    public static String a(String str) {
        cl clVar = SmoneyApplication.a;
        if (ad.a) {
            return String.format("https://mon-espace.izly.fr/tools/photos/getphoto?user=%1$s&contact=%2$s&sid=%3$s", new Object[]{clVar.b.a, str, clVar.b.c});
        } else if (clVar.b == null) {
            return "";
        } else {
            return String.format("https://mon-espace.izly.fr/tools/photos/GetPhotoWithToken?user=%1$s&contact=%2$s&token=%3$s", new Object[]{clVar.b.a, str, SmoneyApplication.c.a().a});
        }
    }

    public static String a(String str, ArrayList<NameValuePair> arrayList) {
        StringBuffer stringBuffer = new StringBuffer(str);
        if (!arrayList.isEmpty()) {
            stringBuffer.append("?");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                NameValuePair nameValuePair = (NameValuePair) it.next();
                try {
                    stringBuffer.append(URLEncoder.encode(nameValuePair.getName(), "UTF-8"));
                    stringBuffer.append("=");
                    String value = nameValuePair.getValue();
                    if (value == null) {
                        value = "";
                    }
                    stringBuffer.append(URLEncoder.encode(value, "UTF-8"));
                    stringBuffer.append("&");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String b(int i) {
        return String.format("https://mon-espace.izly.fr/tools/Photos/GetPromotionalOfferPhoto?id=%s&display=1", new Object[]{Integer.valueOf(i)});
    }
}
