package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.MakeBankAccountUpdateData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class eq {
    public static Bundle a(String str, String str2, String str3, String str4, String str5, String str6) throws IOException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        String a = SmoneyApplication.c.a(str, str6);
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakeBankAccountUpdate");
        cqVar.a("2.0");
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("passOTP", a);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("iban", str4);
        jSONObject.put("bic", str5);
        jSONObject.put("alias", str3);
        cqVar.b(jSONObject.toString());
        JSONObject jSONObject2 = new JSONObject(cqVar.a(1));
        if (jSONObject2.isNull("ErrorMessage")) {
            JSONObject optJSONObject = jSONObject2.optJSONObject("MakeBankAccountUpdateResult");
            if (optJSONObject != null) {
                MakeBankAccountUpdateData makeBankAccountUpdateData = new MakeBankAccountUpdateData();
                makeBankAccountUpdateData.a = jd.a(optJSONObject, "Alias");
                makeBankAccountUpdateData.b = jd.a(optJSONObject, "BankName");
                makeBankAccountUpdateData.d = jd.a(optJSONObject, "Bic");
                makeBankAccountUpdateData.f = jd.a(optJSONObject, "BicHint");
                makeBankAccountUpdateData.c = jd.a(optJSONObject, "Iban");
                makeBankAccountUpdateData.e = jd.a(optJSONObject, "IbanHint");
                makeBankAccountUpdateData.j = jd.a(optJSONObject, "UpdateHelpMessage");
                makeBankAccountUpdateData.h = optJSONObject.optBoolean("IsActive");
                makeBankAccountUpdateData.i = optJSONObject.optBoolean("IsUpdateAuthorized");
                makeBankAccountUpdateData.g = optJSONObject.optLong("Id");
                if (!optJSONObject.isNull("SessionId")) {
                    makeBankAccountUpdateData.k = optJSONObject.getString("SessionId");
                }
                MakeBankAccountUpdateData makeBankAccountUpdateData2 = makeBankAccountUpdateData;
                parcelable = null;
                Object obj = makeBankAccountUpdateData2;
            } else {
                parcelable = null;
            }
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject2.getInt("Code");
            parcelable.c = jSONObject2.getString("ErrorMessage");
            parcelable.e = jSONObject2.getInt("Priority");
            parcelable.d = jSONObject2.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.changeTransferAccountData", parcelable2);
        }
        return bundle;
    }
}
