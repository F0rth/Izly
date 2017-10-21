package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.DeleteBankAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dm {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/DeleteMyBankAccount");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            jSONObject = jSONObject.optJSONObject("DeleteMyBankAccountResult");
            if (jSONObject != null) {
                DeleteBankAccountData deleteBankAccountData = new DeleteBankAccountData();
                if (!jSONObject.isNull("UP")) {
                    jSONObject = jSONObject.getJSONObject("UP");
                    deleteBankAccountData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                    deleteBankAccountData.a.c = ag.a.parse(jSONObject.getString("LUD")).getTime();
                }
                DeleteBankAccountData deleteBankAccountData2 = deleteBankAccountData;
                parcelable = null;
                Object obj = deleteBankAccountData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.deleteBankAccountData", parcelable2);
        }
        return bundle;
    }
}
