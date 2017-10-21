package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.MakeMoneyInBankAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class eu {
    public static Bundle a(String str, String str2, double d, String str3) throws IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakeMoneyInBankAccount");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("passOTP", SmoneyApplication.c.a(str, str3));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("amount", d);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        Bundle bundle = new Bundle();
        JSONObject jSONObject2 = new JSONObject(a);
        if (jSONObject2.isNull("ErrorMessage")) {
            if (!jSONObject2.isNull("MakeMoneyInBankAccountResult")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("MakeMoneyInBankAccountResult");
                if (!jSONObject3.isNull("Result")) {
                    jSONObject2 = jSONObject3.getJSONObject("Result");
                    if (!(jSONObject2.isNull("Amount") || jSONObject2.isNull("Date"))) {
                        MakeMoneyInBankAccountData makeMoneyInBankAccountData = new MakeMoneyInBankAccountData(jSONObject2.optString("Date"), jSONObject2.optDouble("Amount"));
                        parcelable = null;
                        Object obj = makeMoneyInBankAccountData;
                    }
                }
            }
            parcelable = null;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject2.getInt("Code");
            parcelable.c = jSONObject2.getString("ErrorMessage");
            parcelable.e = jSONObject2.getInt("Priority");
            parcelable.d = jSONObject2.getString("Title");
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.MakeMoneyInBankAccountData", parcelable2);
        }
        return bundle;
    }
}
