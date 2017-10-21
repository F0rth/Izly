package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UpdateUserProfileData;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class gc {
    public static Bundle a(String str, String str2, int i, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, int i2, String str13, int i3, int i4, String str14) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/UpdateUserProfile");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        JSONObject jSONObject3 = new JSONObject();
        jSONObject3.put("Civility", i);
        jSONObject3.put("FirstName", str3);
        jSONObject3.put("LastName", str4);
        jSONObject3.put("Email", str8);
        jSONObject3.put("BirthDate", "/Date(" + str5 + ")/");
        jSONObject3.put("TwitterEmail", str14);
        JSONObject jSONObject4 = new JSONObject();
        if (str10.length() == 0 && str11.length() == 0 && str12.length() == 0) {
            jSONObject3.put("Address", null);
        } else {
            jSONObject4.put("Name", str10);
            jSONObject4.put("ZipCode", str11);
            jSONObject4.put("City", str12);
            jSONObject4.put("Country", i2);
            jSONObject3.put("Address", jSONObject4);
        }
        jSONObject2.put("OptIn", i3);
        jSONObject2.put("OptInPartners", i4);
        jSONObject2.put("ActiveAlias", str7);
        jSONObject2.put("Profile", jSONObject3);
        if (!TextUtils.isEmpty(str6)) {
            jSONObject3 = new JSONObject();
            jSONObject3.put("Activity", str6);
            jSONObject3.put("WebSite", str9);
            jSONObject3.put("CommercialMessage", str13);
            jSONObject.put("pro", jSONObject3);
        }
        jSONObject.put("info", jSONObject2);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        Parcelable parcelable = null;
        Parcelable parcelable2 = null;
        jSONObject3 = new JSONObject(a);
        if (jSONObject3.isNull("ErrorMessage")) {
            parcelable = new UpdateUserProfileData();
            jSONObject2 = jSONObject3.getJSONObject("UpdateUserProfileResult");
            if (!jSONObject2.isNull("UP")) {
                jSONObject3 = jSONObject2.getJSONObject("UP");
                parcelable.a.a = Double.parseDouble(jSONObject3.getString("BAL"));
                parcelable.a.c = ae.a.parse(jSONObject3.getString("LUD")).getTime();
            }
            if (!jSONObject2.isNull("Result")) {
                parcelable.b = jSONObject2.getInt("Result");
            }
        } else {
            parcelable2 = new ServerError();
            parcelable2.b = jSONObject3.getInt("Code");
            parcelable2.c = jSONObject3.getString("ErrorMessage");
            parcelable2.e = jSONObject3.getInt("Priority");
            parcelable2.d = jSONObject3.getString("Title");
        }
        Bundle bundle = new Bundle();
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable2);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.updateUserProfileData", parcelable);
        }
        return bundle;
    }
}
