package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetBankAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dz {
    public static Bundle a(String str, String str2) throws JSONException, ClientProtocolException, IOException {
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetMyBankAccount");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        if (a == null || a.length() == 0) {
            Bundle bundle = new Bundle();
            bundle.putParcelable("fr.smoney.android.izly.extras.getBankAccountData", new GetBankAccountData());
            return bundle;
        }
        JSONObject jSONObject = new JSONObject(a);
        if (a == null || a.length() == 0) {
            bundle = new Bundle();
            bundle.putParcelable("fr.smoney.android.izly.extras.getBankAccountData", new GetBankAccountData());
            return bundle;
        }
        Parcelable parcelable2;
        if (jSONObject.isNull("ErrorMessage")) {
            GetBankAccountData getBankAccountData = new GetBankAccountData();
            getBankAccountData.b = jd.a(jSONObject, "Alias");
            getBankAccountData.e = jd.a(jSONObject, "Bic");
            getBankAccountData.f = jd.a(jSONObject, "BicHint");
            getBankAccountData.c = jd.a(jSONObject, "Iban");
            getBankAccountData.d = jd.a(jSONObject, "IbanHint");
            getBankAccountData.a = jSONObject.optLong("Id");
            getBankAccountData.g = jSONObject.optBoolean("IsActive");
            getBankAccountData.h = jSONObject.optBoolean("IsUpdateAuthorized");
            getBankAccountData.i = jd.a(jSONObject, "UpdateHelpMessage");
            GetBankAccountData getBankAccountData2 = getBankAccountData;
            parcelable2 = null;
            Object obj = getBankAccountData2;
        } else {
            parcelable2 = new ServerError();
            parcelable2.b = jSONObject.getInt("Code");
            parcelable2.c = jSONObject.getString("ErrorMessage");
            parcelable2.e = jSONObject.getInt("Priority");
            parcelable2.d = jSONObject.getString("Title");
        }
        Bundle bundle2 = new Bundle();
        if (parcelable2 != null) {
            bundle2.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable2);
        }
        if (parcelable != null) {
            bundle2.putParcelable("fr.smoney.android.izly.extras.getBankAccountData", parcelable);
        }
        return bundle2;
    }
}
