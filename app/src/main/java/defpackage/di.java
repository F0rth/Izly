package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class di {
    public static Bundle a(String str, String str2, int i, String str3, int i2) throws IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/CheckUserActivationData");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("email", str);
        jSONObject.put("activationCode", str2);
        jSONObject.put("civility", i);
        jSONObject.put("birthdate", str3);
        jSONObject.put("country", i2);
        cqVar.b(jSONObject.toString());
        return aj.a(cqVar.a(1));
    }
}
