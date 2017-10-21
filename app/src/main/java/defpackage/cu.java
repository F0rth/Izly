package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class cu {
    public static Bundle a(String str, String str2, int i, String str3, int i2, String str4, String str5, String str6, int i3) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        Bundle bundle;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/ActivateUser");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("email", str);
        jSONObject.put("activationCode", str2);
        jSONObject.put("civility", i);
        jSONObject.put("birthdate", str3);
        jSONObject.put("country", i2);
        jSONObject.put("password", str4);
        jSONObject.put("secretQuestion", str5);
        jSONObject.put("secretAnswer", str6);
        jSONObject.put("cgu", i3);
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
            } else if (!jSONObject.isNull("ActivateUserResult")) {
                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("ActivateUserResult"));
                ActivateUserData activateUserData = new ActivateUserData();
                activateUserData.a = jSONObject2.optInt("Status");
                activateUserData.b = jSONObject2.optString("Token");
                activateUserData.c = jSONObject2.optString("Username");
                ActivateUserData activateUserData2 = activateUserData;
                serverError = null;
                Object obj = activateUserData2;
            }
            bundle = new Bundle();
            if (serverError != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
            }
            if (parcelable != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.ActivateUserData", parcelable);
            }
            return bundle;
        }
        serverError = null;
        bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.ActivateUserData", parcelable);
        }
        return bundle;
    }
}
