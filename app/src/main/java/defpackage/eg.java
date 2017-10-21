package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetNewsFeedData;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class eg {
    public static Bundle a(String str, String str2, int i, int i2, long j, long j2, boolean z, GetNewsFeedData getNewsFeedData) throws IOException, JSONException, ParseException {
        Object obj = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetNewsFeed");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        Object b = j != -1 ? jk.b(j) : null;
        if (j2 != -1) {
            obj = jk.b(j2);
        }
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("currentPage", i);
        jSONObject.put("nbItemPerPage", i2);
        if (b != null) {
            jSONObject.put("from", b);
        }
        if (obj != null) {
            jSONObject.put("to", obj);
        }
        cqVar.b(jSONObject.toString());
        return ar.a(cqVar.a(1), z);
    }
}
