package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.PostDealsCodeResponse;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;

public final class fo {
    public static Bundle a(String str, String str2, String str3, String str4, String str5) throws IOException, JSONException, ParseException {
        cq cqVar = new cq(str);
        cqVar.a(BuildConfig.VERSION_NAME);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("email", str2);
        jSONObject.put("lastname", str3);
        jSONObject.put("firstname", str4);
        jSONObject.put("idizly", str5);
        cqVar.b(jSONObject.toString());
        String a = cqVar.a(1);
        int i = cqVar.b;
        Parcelable parcelable = null;
        Parcelable postDealsCodeResponse = new PostDealsCodeResponse(i);
        Bundle bundle = new Bundle();
        if (a != null && a.length() > 0) {
            JSONObject jSONObject2 = new JSONObject(a);
            if (!jSONObject2.isNull("ErrorMessage")) {
                parcelable = new ServerError();
                parcelable.b = jSONObject2.getInt("Code");
                parcelable.c = jSONObject2.getString("ErrorMessage");
                parcelable.e = jSONObject2.getInt("Priority");
                parcelable.d = jSONObject2.getString("Title");
            }
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        bundle.putParcelable("fr.smoney.android.izly.extra.PostDealsData", postDealsCodeResponse);
        return bundle;
    }
}
