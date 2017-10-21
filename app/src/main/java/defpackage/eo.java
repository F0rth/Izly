package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.BalanceData;
import fr.smoney.android.izly.data.model.IsSessionValidData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class eo {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/IsSessionValid");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("sessionId", str2);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            if (!jSONObject.isNull("IsSessionValidResult")) {
                jSONObject = jSONObject.getJSONObject("IsSessionValidResult");
                if (!jSONObject.isNull("Result")) {
                    IsSessionValidData isSessionValidData = new IsSessionValidData();
                    isSessionValidData.a = jSONObject.getBoolean("Result");
                    if (!jSONObject.isNull("UP")) {
                        jSONObject = jSONObject.getJSONObject("UP");
                        isSessionValidData.b = new BalanceData();
                        isSessionValidData.b.b = Double.parseDouble(jSONObject.getString("CASHBAL"));
                        isSessionValidData.b.a = Double.parseDouble(jSONObject.getString("BAL"));
                        isSessionValidData.b.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
                    }
                    IsSessionValidData isSessionValidData2 = isSessionValidData;
                    parcelable = null;
                    Object obj = isSessionValidData2;
                }
            }
            parcelable = null;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.IsSessionValid", parcelable2);
        }
        return bundle;
    }
}
