package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class fz {
    public static Bundle a(String str, String str2, String str3, String str4, String str5, String str6) throws IOException, JSONException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/UpdateMyAddress");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("Name", str3);
        jSONObject2.put("ZipCode", str4);
        jSONObject2.put("City", str5);
        jSONObject2.put("Country", str6);
        jSONObject.put("address", jSONObject2);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        Parcelable parcelable = null;
        jSONObject2 = new JSONObject(a);
        if (!jSONObject2.isNull("ErrorMessage")) {
            parcelable = new ServerError();
            parcelable.b = jSONObject2.getInt("Code");
            parcelable.c = jSONObject2.getString("ErrorMessage");
            parcelable.e = jSONObject2.getInt("Priority");
            parcelable.d = jSONObject2.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        return bundle;
    }
}
