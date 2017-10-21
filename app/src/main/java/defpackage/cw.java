package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.AssociatePhoneNumberData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class cw {
    public static Bundle a(String str) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        Bundle bundle;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/AssociatePhoneNumber");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("phoneNumber", str);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        if (a != null && a.length() > 0) {
            jSONObject = new JSONObject(a);
            if (!jSONObject.isNull("ErrorMessage")) {
                serverError = new ServerError();
                serverError.b = jSONObject.getInt("Code");
                serverError.c = jSONObject.getString("ErrorMessage");
                serverError.e = jSONObject.getInt("Priority");
                serverError.d = jSONObject.getString("Title");
            } else if (!jSONObject.isNull("AssociatePhoneNumberResult")) {
                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("AssociatePhoneNumberResult"));
                AssociatePhoneNumberData associatePhoneNumberData = new AssociatePhoneNumberData();
                associatePhoneNumberData.a = jSONObject2.optString("SALT");
                associatePhoneNumberData.b = jSONObject2.optString("PhoneNumber");
                AssociatePhoneNumberData associatePhoneNumberData2 = associatePhoneNumberData;
                serverError = null;
                Object obj = associatePhoneNumberData2;
            }
            bundle = new Bundle();
            if (serverError != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
            }
            if (parcelable != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.AssociatePhoneNumberData", parcelable);
            }
            return bundle;
        }
        serverError = null;
        bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.AssociatePhoneNumberData", parcelable);
        }
        return bundle;
    }
}
