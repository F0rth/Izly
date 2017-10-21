package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.CbChangeAliasData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dc {
    public static Bundle a(String str, String str2, String str3, String str4) throws JSONException, ClientProtocolException, IOException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/CBChangeAlias");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cardId", str3);
        jSONObject.put("alias", str4);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            jSONObject = jSONObject.optJSONObject("CBChangeAliasResult");
            if (jSONObject != null) {
                CbChangeAliasData cbChangeAliasData = new CbChangeAliasData();
                cbChangeAliasData.a.b = jd.a(jSONObject, "Alias");
                cbChangeAliasData.a.d = jd.a(jSONObject, "Hint");
                cbChangeAliasData.a.a = jd.a(jSONObject, "SystemPayCardId");
                cbChangeAliasData.a.e = jSONObject.optBoolean("IsDefault");
                cbChangeAliasData.a.f = jSONObject.optBoolean("IsActive");
                cbChangeAliasData.a.c = jSONObject.optInt("Network");
                CbChangeAliasData cbChangeAliasData2 = cbChangeAliasData;
                parcelable = null;
                Object obj = cbChangeAliasData2;
            } else {
                parcelable = null;
            }
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.cbChangeAliasData", parcelable2);
        }
        return bundle;
    }
}
