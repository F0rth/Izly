package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GetMyCbListData;
import fr.smoney.android.izly.data.model.MoneyInCbCb;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ea {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        int i = 0;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetMyCardList");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(a);
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull("GetMyCardListResult")) {
            serverError = null;
        } else {
            JSONArray jSONArray = jSONObject.getJSONArray("GetMyCardListResult");
            int length = jSONArray.length();
            GetMyCbListData getMyCbListData = new GetMyCbListData();
            while (i < length) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                MoneyInCbCb moneyInCbCb = new MoneyInCbCb();
                moneyInCbCb.b = jd.a(jSONObject2, "Alias");
                moneyInCbCb.d = jd.a(jSONObject2, "Hint");
                moneyInCbCb.f = jSONObject2.optBoolean("IsActive");
                moneyInCbCb.e = jSONObject2.optBoolean("IsDefault");
                moneyInCbCb.c = jSONObject2.optInt("Network", -1);
                moneyInCbCb.a = jd.a(jSONObject2, "SystemPayCardId");
                getMyCbListData.a.add(moneyInCbCb);
                i++;
            }
            GetMyCbListData getMyCbListData2 = getMyCbListData;
            serverError = null;
            Object obj = getMyCbListData2;
        }
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetMyCbList", parcelable);
        }
        return bundle;
    }
}
