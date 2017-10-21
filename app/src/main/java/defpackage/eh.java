package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class eh {
    public static Bundle a(String str, String str2, int i) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetProCashingModels");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("version", i);
        cqVar.b(jSONObject.toString());
        return as.a(cqVar.a(1));
    }
}
