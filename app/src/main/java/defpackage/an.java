package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.Counter;
import fr.smoney.android.izly.data.model.CounterData;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class an {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable = null;
        Bundle bundle = new Bundle();
        ArrayList arrayList = new ArrayList();
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("ErrorMessage")) {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        } else if (!jSONObject.isNull("GetCounterDetailsResult")) {
            JSONArray jSONArray = jSONObject.getJSONArray("GetCounterDetailsResult");
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                arrayList.add(new Counter(jSONObject2.getLong("CounterTypeId"), jk.a(jd.a(jSONObject2, "OperationDate")), jSONObject2.getInt("OperationValue"), jd.a(jSONObject2, "CrousOperationLabel")));
            }
        }
        CounterData counterData = new CounterData(arrayList);
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        bundle.putParcelable("fr.smoney.android.izly.extras.GetMyCounterDetail", counterData);
        return bundle;
    }
}
