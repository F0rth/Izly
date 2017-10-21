package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import android.util.Base64;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.OAuthData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

public final class fq {
    public static Bundle a(String str, String str2) throws IOException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        String c = SmoneyApplication.c.c(str);
        if (c != null && c.length() > 0) {
            c = Base64.encodeToString(("S-money:" + c).getBytes(), 8);
            c = c.substring(0, c.length() - 1);
        }
        cq cqVar = new cq("https://rest.izly.fr/oauth/token");
        cqVar.a("Authorization", "Basic " + c);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("grant_type", "refresh_token");
        jSONObject.put("refresh_token", str2);
        cqVar.b(jSONObject.toString());
        JSONObject jSONObject2 = new JSONObject(cqVar.a(1));
        if (jSONObject2.isNull("error")) {
            c = !jSONObject2.isNull("access_token") ? jSONObject2.getString("access_token") : null;
            String string = !jSONObject2.isNull("refresh_token") ? jSONObject2.getString("refresh_token") : null;
            long j = !jSONObject2.isNull("expires_in") ? jSONObject2.getLong("expires_in") : -1;
            if (c == null || string == null || j == -1) {
                parcelable = null;
            } else {
                OAuthData oAuthData = new OAuthData(c, string, j);
                parcelable = null;
                parcelable2 = oAuthData;
            }
        } else {
            parcelable = new ServerError();
            parcelable.e = 1;
            parcelable.b = ServerError.a(jSONObject2.getString("error"));
            parcelable.c = jSONObject2.getString("error_description");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.RefreshToken", parcelable2);
        }
        return bundle;
    }
}
