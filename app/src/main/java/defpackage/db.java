package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.CardPaymentsData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParserException;

public final class db {
    public static Bundle a(String str, String str2, String str3, String str4) throws IOException, XmlPullParserException, ParserConfigurationException, SAXException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/api/payins/cardpayments");
        cqVar.a("1");
        cqVar.a(kh.HEADER_ACCEPT, "application/vnd.s-money.v1+json");
        cqVar.a("Content-Type", "application/vnd.s-money.v1+json");
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("amount", Double.valueOf(str3));
        jSONObject.put("cards", str4);
        jSONObject.put("ismine", true);
        jSONObject.put("urlReturn", "http://80.11.255.231");
        cqVar.a = jSONObject.toString();
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            CardPaymentsData cardPaymentsData = new CardPaymentsData();
            cardPaymentsData.a = jSONObject.getString("Href");
            Object obj = cardPaymentsData;
            parcelable = null;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.cardPayments", parcelable2);
        }
        return bundle;
    }
}
