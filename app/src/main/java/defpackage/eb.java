package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetMySupportListData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.Support;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class eb {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetSupports");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        Parcelable parcelable = null;
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(a);
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull("GetSupportsResult")) {
            serverError = null;
        } else {
            JSONArray jSONArray = jSONObject.getJSONArray("GetSupportsResult");
            int length = jSONArray.length();
            GetMySupportListData getMySupportListData = new GetMySupportListData();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                long j = jSONObject2.getLong("Id");
                co coVar = co.values()[jSONObject2.getInt("SupportType") - 1];
                String a2 = jd.a(jSONObject2, "OppositionType");
                if (a2.length() == 0) {
                    a2 = "0";
                }
                getMySupportListData.a.add(new Support(j, coVar, jd.a(jSONObject2, "CrousName"), jd.a(jSONObject2, "Label"), jd.a(jSONObject2, "ExpiryDate"), jd.a(jSONObject2, "OppositionDate"), Boolean.valueOf(jSONObject2.getString("IsOppositionPermanent")).booleanValue(), Integer.valueOf(a2).intValue()));
            }
            serverError = null;
            Object obj = getMySupportListData;
        }
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetMySupportList", parcelable);
        }
        return bundle;
    }
}
