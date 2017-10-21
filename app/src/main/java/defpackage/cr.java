package defpackage;

import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.OAuthData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class cr {
    private static final String a = cr.class.getSimpleName();

    public static String a(String str, String str2, String str3, String str4, String str5, HashMap<String, String> hashMap) throws IOException, XmlPullParserException {
        cp cpVar = new cp(str2, str3, str4, str5);
        List arrayList = new ArrayList();
        arrayList.add(new HeaderProperty("clientVersion", "0.22"));
        arrayList.add(new HeaderProperty("smoneyClientType", ad.a ? "PRO" : "PART"));
        OAuthData a = SmoneyApplication.c.a();
        if (!(a == null || a.a == null)) {
            arrayList.add(new HeaderProperty("Authorization", "Bearer " + a.a));
        }
        if (!hashMap.isEmpty()) {
            ArrayList arrayList2 = new ArrayList(hashMap.keySet());
            int size = arrayList2.size();
            for (int i = 0; i < size; i++) {
                String str6 = (String) arrayList2.get(i);
                cpVar.addProperty(str6, hashMap.get(str6));
            }
        }
        SoapEnvelope soapSerializationEnvelope = new SoapSerializationEnvelope(110);
        soapSerializationEnvelope.dotNet = true;
        soapSerializationEnvelope.encodingStyle = SoapEnvelope.XSD;
        soapSerializationEnvelope.bodyOut = cpVar;
        soapSerializationEnvelope.setOutputSoapObject(cpVar);
        HttpTransportSE httpTransportSE = new HttpTransportSE(str, 40000);
        System.setProperty("http.keepAlive", "false");
        httpTransportSE.setXmlVersionTag("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        httpTransportSE.call(cpVar.a, soapSerializationEnvelope, arrayList);
        return ((SoapPrimitive) soapSerializationEnvelope.getResponse()).toString();
    }
}
