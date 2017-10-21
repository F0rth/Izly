package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.SetConfidentialitySettingsData;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class fx {
    public static Bundle a(String str, String str2, int i, int i2, int i3, int i4, int i5, int i6) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/SetConfidentialitySettings");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("Photo", i);
        jSONObject2.put("Name", i2);
        jSONObject2.put("PhoneNumber", i3);
        jSONObject2.put("Alias", i4);
        jSONObject2.put("Address", i5);
        jSONObject2.put("Email", i6);
        jSONObject.put("settings", jSONObject2);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            SetConfidentialitySettingsData setConfidentialitySettingsData = new SetConfidentialitySettingsData();
            if (!jSONObject.isNull("UP")) {
                jSONObject = jSONObject.getJSONObject("UP");
                setConfidentialitySettingsData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                setConfidentialitySettingsData.a.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
            }
            SetConfidentialitySettingsData setConfidentialitySettingsData2 = setConfidentialitySettingsData;
            parcelable = null;
            Object obj = setConfidentialitySettingsData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.setConfidentialitySettingsData", parcelable2);
        }
        return bundle;
    }
}
