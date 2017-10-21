package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.MakeMoneyDemandRelaunchData;
import fr.smoney.android.izly.data.model.MoneyDemandRelaunch;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ax {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("MakeMoneyDemandRelaunchResult");
            MakeMoneyDemandRelaunchData makeMoneyDemandRelaunchData = new MakeMoneyDemandRelaunchData();
            if (!jSONObject2.isNull("Result")) {
                jSONObject2 = jSONObject2.getJSONObject("Result");
                makeMoneyDemandRelaunchData.a = jSONObject2.getLong("Id");
                makeMoneyDemandRelaunchData.b = true;
                JSONArray jSONArray = jSONObject2.getJSONArray("MoneyDemands");
                int length = jSONArray.length();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    MoneyDemandRelaunch moneyDemandRelaunch = new MoneyDemandRelaunch();
                    moneyDemandRelaunch.a = jSONObject3.getLong("Id");
                    moneyDemandRelaunch.b = jSONObject3.getJSONObject("Sender").getString("Identifier");
                    if (!jSONObject3.isNull("RevivalWaitTimeDisplay")) {
                        String string = jSONObject3.getString("RevivalWaitTimeDisplay");
                        string = string.substring(0, string.indexOf(":"));
                        if (string.equals("0")) {
                            string = "1";
                        }
                        moneyDemandRelaunch.d = string;
                    }
                    moneyDemandRelaunch.e = jSONObject3.getBoolean("IsRelaunched");
                    if (!jSONObject3.isNull("Error")) {
                        jSONObject2 = jSONObject3.getJSONObject("Error");
                        moneyDemandRelaunch.f = jSONObject2.getInt("Code");
                        moneyDemandRelaunch.g = jSONObject2.getString("ErrorMessage");
                    }
                    if (!(moneyDemandRelaunch.e || moneyDemandRelaunch.f == -1)) {
                        makeMoneyDemandRelaunchData.b = false;
                    }
                    arrayList.add(moneyDemandRelaunch);
                }
                makeMoneyDemandRelaunchData.c = arrayList;
            }
            parcelable = null;
            parcelable2 = makeMoneyDemandRelaunchData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.MakeMoneyDemandRelaunch", parcelable2);
        }
        return bundle;
    }
}
