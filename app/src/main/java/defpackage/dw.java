package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.CounterFamily;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class dw {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        int i = 0;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetCounterList");
        cqVar.a("2.0");
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        Parcelable parcelable = null;
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject(a);
        if (!jSONObject.isNull("ErrorMessage")) {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        } else if (!jSONObject.isNull("GetCounterListResult")) {
            JSONArray jSONArray = jSONObject.getJSONArray("GetCounterListResult");
            int length = jSONArray.length();
            while (i < length) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                arrayList.add(new CounterFamily(jSONObject2.getLong("CounterTypeId"), jSONObject2.getString("Label"), jSONObject2.getInt("Value")));
                i++;
            }
        }
        Parcelable counterListData = new CounterListData(arrayList);
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        bundle.putParcelable("fr.smoney.android.izly.extras.GetMyCounterList", counterListData);
        return bundle;
    }
}
