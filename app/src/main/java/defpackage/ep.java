package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.BalanceData;
import fr.smoney.android.izly.data.model.LoginLightData;
import fr.smoney.android.izly.data.model.OAuthData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ep {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable serverError;
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/LogonLight");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("password", str2);
        cqVar.a("passOTP", SmoneyApplication.c.a(str, str2));
        JSONObject jSONObject = new JSONObject(cqVar.a(1));
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
            parcelable = null;
        } else if (jSONObject.isNull("LogonLightResult")) {
            serverError = null;
            parcelable = null;
        } else {
            OAuthData oAuthData;
            OAuthData oAuthData2;
            JSONObject jSONObject2 = jSONObject.getJSONObject("LogonLightResult");
            parcelable = new LoginLightData();
            if (!jSONObject2.isNull("Result")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("Result");
                parcelable.a = jSONObject3.getString("SessionId");
                parcelable.b = jSONObject3.getInt("UserStatus");
                if (!jSONObject3.isNull("Tokens")) {
                    JSONObject jSONObject4 = jSONObject3.getJSONObject("Tokens");
                    oAuthData = new OAuthData();
                    oAuthData = new OAuthData();
                    oAuthData.a = jSONObject4.getString("AccessToken");
                    oAuthData.c = jSONObject4.getLong("ExpiresIn");
                    oAuthData.b = jSONObject4.getString("RefreshToken");
                    if (!jSONObject2.isNull("UP")) {
                        jSONObject2 = jSONObject2.getJSONObject("UP");
                        parcelable.c = new BalanceData();
                        parcelable.c.a = Double.parseDouble(jSONObject2.getString("BAL"));
                        parcelable.c.b = Double.parseDouble(jSONObject2.getString("CASHBAL"));
                        parcelable.c.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
                    }
                    oAuthData2 = oAuthData;
                    serverError = null;
                    parcelable2 = oAuthData2;
                }
            }
            oAuthData = null;
            if (jSONObject2.isNull("UP")) {
                jSONObject2 = jSONObject2.getJSONObject("UP");
                parcelable.c = new BalanceData();
                parcelable.c.a = Double.parseDouble(jSONObject2.getString("BAL"));
                parcelable.c.b = Double.parseDouble(jSONObject2.getString("CASHBAL"));
                parcelable.c.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
            }
            oAuthData2 = oAuthData;
            serverError = null;
            parcelable2 = oAuthData2;
        }
        Bundle bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.LoginLight", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.oAuthData", parcelable2);
        }
        return bundle;
    }
}
