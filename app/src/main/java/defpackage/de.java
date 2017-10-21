package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class de {
    public static Bundle a(UserSubscribingValues userSubscribingValues) throws IOException, JSONException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/restfile/CheckEnrollment");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        Object obj = "";
        if (userSubscribingValues.m != null) {
            obj = Base64.encodeToString(userSubscribingValues.m, 8).replace("\n", "");
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("language", Locale.getDefault().getLanguage());
        jSONObject.put("Civility", String.valueOf(userSubscribingValues.a));
        jSONObject.put("FirstName", userSubscribingValues.b);
        jSONObject.put("LastName", userSubscribingValues.c);
        jSONObject.put("Alias", userSubscribingValues.d);
        jSONObject.put("BirthDate", userSubscribingValues.e);
        jSONObject.put("PhoneNumber", userSubscribingValues.g);
        jSONObject.put("Email", userSubscribingValues.h);
        jSONObject.put("Password", userSubscribingValues.f);
        jSONObject.put("SecretQuestion", userSubscribingValues.i);
        jSONObject.put("Answer", userSubscribingValues.j);
        jSONObject.put("OptIn", String.valueOf(userSubscribingValues.k));
        jSONObject.put("OptInPartners", String.valueOf(userSubscribingValues.l));
        jSONObject.put("Picture", obj);
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("user", jSONObject);
        cqVar.b(jSONObject2.toString());
        Parcelable parcelable = null;
        jSONObject = new JSONObject(cqVar.a(1));
        if (!jSONObject.isNull("ErrorMessage")) {
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
        return bundle;
    }
}
