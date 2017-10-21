package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ed {
    public static Bundle a(String str, String str2, double d, double d2, int i, int i2) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetNearPros");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("Latitude", d);
        jSONObject2.put("Longitude", d2);
        jSONObject.put("position", jSONObject2);
        jSONObject.put("filter", i);
        jSONObject.put("radiusType", i2);
        cqVar.b(jSONObject.toString());
        return ap.a(cqVar.a(1));
    }
}
