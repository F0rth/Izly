package defpackage;

import android.os.Bundle;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class ev {
    public static Bundle a(String str, String str2, String str3, PreAuthorizationContainerData preAuthorizationContainerData) throws IOException, JSONException {
        String a = SmoneyApplication.c.a(str, str3);
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakePreAuthorizePayment");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("passOTP", a);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("proIdentifier", preAuthorizationContainerData.a.a);
        jSONObject.put("identifier", preAuthorizationContainerData.d.a);
        jSONObject.put("identifierType", preAuthorizationContainerData.d.b);
        jSONObject.put("amount", preAuthorizationContainerData.b);
        cqVar.b(jSONObject.toString());
        return bj.a(cqVar.a(1), "MakePreAuthorizePaymentResult");
    }
}
