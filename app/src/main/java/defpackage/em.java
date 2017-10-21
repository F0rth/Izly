package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.InitiatePasswordRecoveryData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class em {
    public static Bundle a(String str, boolean z) throws IOException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/InitiatePasswordRecovery");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.b("unlockAccount", String.valueOf(z));
        String a = cqVar.a(0);
        if (a == null || a.length() <= 0) {
            InitiatePasswordRecoveryData initiatePasswordRecoveryData = new InitiatePasswordRecoveryData();
            parcelable = null;
            Object obj = initiatePasswordRecoveryData;
        } else {
            JSONObject jSONObject = new JSONObject(a);
            if (jSONObject.isNull("ErrorMessage")) {
                parcelable = null;
            } else {
                parcelable = new ServerError();
                parcelable.b = jSONObject.getInt("Code");
                parcelable.c = jSONObject.getString("ErrorMessage");
                parcelable.e = jSONObject.getInt("Priority");
                parcelable.d = jSONObject.getString("Title");
            }
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.initiatePasswordRecoveryData", parcelable2);
        }
        return bundle;
    }
}
