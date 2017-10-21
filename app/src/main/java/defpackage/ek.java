package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetUserActivationData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class ek {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        Bundle bundle;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetUserActivationData?email=" + str + "&activationcode=" + str2);
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        String a = cqVar.a(0);
        if (a != null && a.length() > 0) {
            JSONObject jSONObject = new JSONObject(a);
            if (!jSONObject.isNull("ErrorMessage")) {
                serverError = new ServerError();
                serverError.b = jSONObject.getInt("Code");
                serverError.c = jSONObject.getString("ErrorMessage");
                serverError.e = jSONObject.getInt("Priority");
                serverError.d = jSONObject.getString("Title");
            } else if (!jSONObject.isNull("GetUserActivationDataResult")) {
                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("GetUserActivationDataResult"));
                GetUserActivationData getUserActivationData = new GetUserActivationData();
                getUserActivationData.a = jSONObject2.optString("FirstName");
                getUserActivationData.b = jSONObject2.optString("LastName");
                getUserActivationData.c = jSONObject2.optString("BirthDate");
                GetUserActivationData getUserActivationData2 = getUserActivationData;
                serverError = null;
                Object obj = getUserActivationData2;
            }
            bundle = new Bundle();
            if (serverError != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
            }
            if (parcelable != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.GetUserActivationData", parcelable);
            }
            return bundle;
        }
        serverError = null;
        bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetUserActivationData", parcelable);
        }
        return bundle;
    }
}
