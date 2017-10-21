package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetNewEventNumberData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public final class ee {
    public static Bundle a(String str, String str2) throws IOException, JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetNewEventNumber");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(0));
        if (jSONObject.isNull("ErrorMessage")) {
            GetNewEventNumberData getNewEventNumberData = new GetNewEventNumberData();
            getNewEventNumberData.d = jSONObject.optBoolean("IsPart");
            getNewEventNumberData.c = jSONObject.optInt("HistoryNewItems");
            getNewEventNumberData.a = jSONObject.optInt("MoneyDemandNewItems");
            getNewEventNumberData.b = jSONObject.optInt("ToPayNewItems");
            Object obj = getNewEventNumberData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.getBadgesData", parcelable2);
        }
        return bundle;
    }
}
