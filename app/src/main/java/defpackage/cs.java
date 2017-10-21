package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class cs {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/AcceptCGU");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(1);
        Parcelable parcelable = null;
        if (a != null && a.length() > 0) {
            JSONObject jSONObject = new JSONObject(a);
            if (!jSONObject.isNull("ErrorMessage")) {
                parcelable = new ServerError();
                parcelable.b = jSONObject.getInt("Code");
                parcelable.c = jSONObject.getString("ErrorMessage");
                parcelable.e = jSONObject.getInt("Priority");
                parcelable.d = jSONObject.getString("Title");
            }
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        return bundle;
    }
}
