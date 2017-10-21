package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class dh {
    public static Bundle a(String str, String str2, PreAuthorizationContainerData preAuthorizationContainerData) throws IOException, JSONException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/CheckPreAuthorizePayment");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("proIdentifier", preAuthorizationContainerData.a.a);
        if (preAuthorizationContainerData.d.a != null && preAuthorizationContainerData.d.a.length() > 0 && preAuthorizationContainerData.d.b > 0) {
            jSONObject.put("identifier", preAuthorizationContainerData.d.a);
        }
        jSONObject.put("identifierType", preAuthorizationContainerData.d.b);
        jSONObject.put("amount", preAuthorizationContainerData.b);
        cqVar.b(jSONObject.toString());
        return bj.a(cqVar.a(1), "CheckPreAuthorizePaymentResult");
    }
}
