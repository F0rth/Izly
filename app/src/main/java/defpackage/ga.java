package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.UpdatePasswordData;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class ga {
    public static Bundle a(String str, String str2, String str3, String str4) throws IOException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/UpdatePassword");
        cqVar.a("2.0");
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("oldPassword", str4);
        jSONObject.put("newPassword", str3);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            UpdatePasswordData updatePasswordData = new UpdatePasswordData();
            updatePasswordData.a = jSONObject.getJSONObject("UpdatePasswordResult").getString("SALT");
            UpdatePasswordData updatePasswordData2 = updatePasswordData;
            parcelable = null;
            Object obj = updatePasswordData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.updatePasswordData", parcelable2);
        }
        return bundle;
    }
}
