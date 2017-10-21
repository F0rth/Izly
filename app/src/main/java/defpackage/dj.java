package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ChooseDefaultCbData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dj {
    public static Bundle a(String str, String str2, String str3) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/ChooseDefaultCB");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("cardId", str3);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            ChooseDefaultCbData chooseDefaultCbData = new ChooseDefaultCbData();
            parcelable = null;
            Object obj = chooseDefaultCbData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.chooseDefaultIdData", parcelable2);
        }
        return bundle;
    }
}
