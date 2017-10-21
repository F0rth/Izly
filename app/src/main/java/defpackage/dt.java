package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetConfidentialitySettingsData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dt {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetConfidentialitySettings");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(0));
        if (jSONObject.isNull("ErrorMessage")) {
            GetConfidentialitySettingsData getConfidentialitySettingsData = new GetConfidentialitySettingsData();
            if (!jSONObject.isNull("UP")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("UP");
                getConfidentialitySettingsData.g.a = Double.parseDouble(jSONObject2.getString("BAL"));
                getConfidentialitySettingsData.g.c = ae.a.parse(jSONObject2.getString("LUD")).getTime();
            }
            if (!jSONObject.isNull("Result")) {
                jSONObject = jSONObject.getJSONObject("Result");
                getConfidentialitySettingsData.a = jSONObject.getInt("Photo");
                getConfidentialitySettingsData.b = jSONObject.getInt("Name");
                getConfidentialitySettingsData.c = jSONObject.getInt("PhoneNumber");
                getConfidentialitySettingsData.d = jSONObject.getInt("Alias");
                getConfidentialitySettingsData.e = jSONObject.getInt("Address");
                getConfidentialitySettingsData.f = jSONObject.getInt("Email");
            }
            GetConfidentialitySettingsData getConfidentialitySettingsData2 = getConfidentialitySettingsData;
            parcelable = null;
            Object obj = getConfidentialitySettingsData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.getConfidentialitySettingsData", parcelable2);
        }
        return bundle;
    }
}
