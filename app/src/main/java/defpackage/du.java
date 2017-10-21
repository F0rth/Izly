package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class du {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetContactDetails");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("Identifier", str3);
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("Latitude", str4);
        jSONObject3.put("Longitude", str5);
        jSONObject.put("receiver", jSONObject2);
        if (!(str4 == null || str5 == null)) {
            jSONObject.put("position", jSONObject3);
        }
        cqVar.b(jSONObject.toString());
        return am.a(cqVar.a(1));
    }
}
