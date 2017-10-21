package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.BalanceData;
import fr.smoney.android.izly.data.model.GetActiveMandateData;
import fr.smoney.android.izly.data.model.Mandate;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class do {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        int i = 0;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetActiveMandate");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(a);
        if (jSONObject.isNull("ErrorMessage")) {
            GetActiveMandateData getActiveMandateData = new GetActiveMandateData();
            if (!jSONObject.isNull("UP")) {
                getActiveMandateData.a = new BalanceData();
                JSONObject jSONObject2 = jSONObject.getJSONObject("UP");
                getActiveMandateData.a.a = Double.parseDouble(jSONObject2.getString("BAL"));
                getActiveMandateData.a.b = Double.parseDouble(jSONObject2.getString("CASHBAL"));
                getActiveMandateData.a.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
            }
            jSONObject = jSONObject.optJSONObject("Result");
            if (jSONObject != null) {
                if (!jSONObject.isNull("Max")) {
                    getActiveMandateData.b = jSONObject.getDouble("Max");
                }
                if (!jSONObject.isNull("Min")) {
                    getActiveMandateData.c = jSONObject.getDouble("Min");
                }
                if (!jSONObject.isNull("Steps")) {
                    JSONArray jSONArray = jSONObject.getJSONArray("Steps");
                    getActiveMandateData.d = new int[jSONArray.length()];
                    while (i != jSONArray.length()) {
                        getActiveMandateData.d[i] = jSONArray.getInt(i);
                        i++;
                    }
                }
                if (!jSONObject.isNull("Mandate")) {
                    JSONObject optJSONObject = jSONObject.optJSONObject("Mandate");
                    if (!optJSONObject.isNull("BankAccount")) {
                        optJSONObject = optJSONObject.optJSONObject("BankAccount");
                        getActiveMandateData.e = new Mandate(optJSONObject.optString("Bic"), optJSONObject.optString("BicHint"), optJSONObject.optString("Iban"), optJSONObject.optString("IbanHint"));
                    }
                }
            }
            parcelable = null;
            Object obj = getActiveMandateData;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetActiveMandate", parcelable2);
        }
        return bundle;
    }
}
