package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetTaxListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.TVAData;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ej {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable serverError;
        int i = 0;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetTaxList");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(0));
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull(a.a.name())) {
            serverError = null;
        } else {
            GetTaxListData getTaxListData = new GetTaxListData();
            JSONArray jSONArray = jSONObject.getJSONObject(a.a.name()).getJSONArray(a.b.name());
            while (i < jSONArray.length()) {
                getTaxListData.a.add(new TVAData(jSONArray.getString(i)));
                i++;
            }
            serverError = null;
            Object obj = getTaxListData;
        }
        Bundle bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetTaxList", parcelable);
        }
        return bundle;
    }
}
