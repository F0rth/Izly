package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UserData;
import fr.smoney.android.izly.data.model.UserSubscribingValues;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class er {
    public static Bundle a(UserSubscribingValues userSubscribingValues) throws IOException, JSONException {
        Parcelable serverError;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/restfile/MakeEnrollment");
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
        jSONObject = new JSONObject(cqVar.a(1));
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull("MakeEnrollmentResult")) {
            serverError = null;
        } else {
            UserData userData = new UserData();
            JSONObject jSONObject3 = jSONObject.getJSONObject("MakeEnrollmentResult");
            userData.b = jSONObject3.getString("Salt");
            userData.a = jSONObject3.getString("UserPhone");
            Object obj2 = userData;
            serverError = null;
        }
        Bundle bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.userData", parcelable);
        }
        return bundle;
    }
}
