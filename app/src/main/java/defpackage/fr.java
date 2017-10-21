package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class fr {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/SendPayInRequest");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("amount", str5);
        jSONObject.put("message", str4);
        jSONObject.put("recipient", str3);
        cqVar.b(jSONObject.toString());
        return ak.a(cqVar.a(1));
    }
}
