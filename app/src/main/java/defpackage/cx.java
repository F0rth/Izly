package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.BlockAccountData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class cx {
    public static Bundle a(String str, String str2, String str3, String str4) throws IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        String a = SmoneyApplication.c.a(str, str4);
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/MakeOpposition");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        cqVar.a("passOTP", a);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("comment", str3);
        cqVar.b(jSONObject.toString());
        JSONObject jSONObject2 = new JSONObject(cqVar.a(1));
        if (jSONObject2.isNull("ErrorMessage")) {
            BlockAccountData blockAccountData = new BlockAccountData();
            if (!jSONObject2.isNull("UP")) {
                JSONObject jSONObject3 = jSONObject2.getJSONObject("UP");
                blockAccountData.a.a = Double.parseDouble(jSONObject3.getString("BAL"));
                blockAccountData.a.c = ag.a.parse(jSONObject3.getString("LUD")).getTime();
            }
            BlockAccountData blockAccountData2 = blockAccountData;
            parcelable = null;
            Object obj = blockAccountData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.blockAccountData", parcelable2);
        }
        return bundle;
    }
}
