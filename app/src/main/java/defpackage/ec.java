package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ec {
    public static Bundle a(String str, String str2, double d, double d2) throws JSONException, ClientProtocolException, IOException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetNbNewPromoOffers");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("latitude", d);
        jSONObject2.put("longitude", d2);
        jSONObject.put("position", jSONObject2);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        Parcelable parcelable = null;
        int i = -1;
        JSONObject jSONObject3 = new JSONObject(a);
        if (!jSONObject3.isNull("ErrorMessage")) {
            parcelable = new ServerError();
            parcelable.b = jSONObject3.getInt("Code");
            parcelable.c = jSONObject3.getString("ErrorMessage");
            parcelable.e = jSONObject3.getInt("Priority");
            parcelable.d = jSONObject3.getString("Title");
        } else if (!jSONObject3.isNull("GetNbNewPromoOffersResult")) {
            jSONObject2 = jSONObject3.getJSONObject("GetNbNewPromoOffersResult");
            if (!jSONObject2.isNull("Result")) {
                i = jSONObject2.getInt("Result");
            }
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        bundle.putInt("fr.smoney.android.izly.extras.NbNewPromoOffers", i);
        return bundle;
    }
}
