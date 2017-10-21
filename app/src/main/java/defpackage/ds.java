package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ClientUserStatus;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class ds {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetClientUserStatus");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(0));
        if (jSONObject.isNull("ErrorMessage")) {
            JSONObject jSONObject2;
            ClientUserStatus clientUserStatus = new ClientUserStatus();
            if (!jSONObject.isNull("Status")) {
                clientUserStatus.a = jSONObject.getInt("Status");
            }
            if (!jSONObject.isNull("LimitMoneyIn")) {
                jSONObject2 = jSONObject.getJSONObject("LimitMoneyIn");
                if (!jSONObject2.isNull("Max")) {
                    clientUserStatus.e = jSONObject2.getDouble("Max");
                }
                if (!jSONObject2.isNull("Min")) {
                    clientUserStatus.d = jSONObject2.getDouble("Min");
                }
            }
            if (!jSONObject.isNull("LimitMoneyOut")) {
                jSONObject2 = jSONObject.getJSONObject("LimitMoneyOut");
                if (!jSONObject2.isNull("Max")) {
                    clientUserStatus.g = jSONObject2.getDouble("Max");
                }
                if (!jSONObject2.isNull("Min")) {
                    clientUserStatus.f = jSONObject2.getDouble("Min");
                }
            }
            if (!jSONObject.isNull("LimitPayment")) {
                jSONObject = jSONObject.getJSONObject("LimitPayment");
                if (!jSONObject.isNull("Max")) {
                    clientUserStatus.c = jSONObject.getDouble("Max");
                }
                if (!jSONObject.isNull("Min")) {
                    clientUserStatus.b = jSONObject.getDouble("Min");
                }
            }
            Object obj = clientUserStatus;
            parcelable = null;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.ClientUserStatus", parcelable2);
        }
        return bundle;
    }
}
