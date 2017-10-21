package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class et {
    public static Bundle a(String str, String str2, long j, long[] jArr) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakeMoneyDemandRelaunch");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        for (long put : jArr) {
            jSONArray.put(put);
        }
        jSONObject.put("moneyDemandId", j);
        jSONObject.put("moneyDemandRequestIds", jSONArray);
        cqVar.b(jSONObject.toString());
        return ax.a(cqVar.a(1));
    }
}
