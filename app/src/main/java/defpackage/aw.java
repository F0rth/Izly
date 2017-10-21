package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.MakeMoneyDemandForProData;
import fr.smoney.android.izly.data.model.ProMoneyDemand;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class aw {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            JSONObject jSONObject2 = jSONObject.getJSONObject("MakeMoneyDemandForProResult");
            MakeMoneyDemandForProData makeMoneyDemandForProData = new MakeMoneyDemandForProData();
            if (!jSONObject2.isNull("UP")) {
                jSONObject = jSONObject2.getJSONObject("UP");
                makeMoneyDemandForProData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                makeMoneyDemandForProData.a.b = Double.parseDouble(jSONObject.getString("CASHBAL"));
                makeMoneyDemandForProData.a.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
            }
            if (!jSONObject2.isNull("Demands")) {
                JSONArray jSONArray = jSONObject2.getJSONArray("Demands");
                int length = jSONArray.length();
                ArrayList arrayList = new ArrayList();
                for (int i = 0; i < length; i++) {
                    JSONObject jSONObject3 = jSONArray.getJSONObject(i);
                    if (!(jSONObject3.isNull("Amount") || jSONObject3.isNull("User"))) {
                        ProMoneyDemand proMoneyDemand = new ProMoneyDemand();
                        JSONObject jSONObject4 = jSONObject3.getJSONObject("Amount");
                        if (!jSONObject4.isNull("AmountHT")) {
                            proMoneyDemand.g = Float.parseFloat(jSONObject4.getString("AmountHT"));
                        }
                        if (!jSONObject4.isNull("AmountTTC")) {
                            proMoneyDemand.h = Float.parseFloat(jSONObject4.getString("AmountTTC"));
                        }
                        if (!jSONObject4.isNull("Tax")) {
                            proMoneyDemand.i = Float.parseFloat(jSONObject4.getString("Tax"));
                        }
                        jSONObject4 = jSONObject3.getJSONObject("User");
                        if (!jSONObject4.isNull("DisplayName")) {
                            proMoneyDemand.a = jSONObject4.getString("DisplayName");
                        }
                        if (!jSONObject4.isNull("Identifier")) {
                            proMoneyDemand.b = jSONObject4.getString("Identifier");
                        }
                        if (!jSONObject4.isNull("IsBlocked")) {
                            proMoneyDemand.c = jSONObject4.getBoolean("IsBlocked");
                        }
                        if (!jSONObject4.isNull("IsBookmarked")) {
                            proMoneyDemand.d = jSONObject4.getBoolean("IsBookmarked");
                        }
                        if (!jSONObject4.isNull("IsSmoneyPro")) {
                            proMoneyDemand.e = jSONObject4.getBoolean("IsSmoneyPro");
                        }
                        if (!jSONObject4.isNull("IsSmoneyUser")) {
                            proMoneyDemand.f = jSONObject4.getBoolean("IsSmoneyUser");
                        }
                        if (!jSONObject3.isNull("Error")) {
                            jSONObject3 = jSONObject3.getJSONObject("Error");
                            if (!jSONObject3.isNull("Code")) {
                                proMoneyDemand.j = jSONObject3.getInt("Code");
                            }
                            if (!jSONObject3.isNull("Msg")) {
                                proMoneyDemand.k = jSONObject3.getString("Msg");
                            }
                            if (!jSONObject3.isNull("Prio")) {
                                proMoneyDemand.l = jSONObject3.getInt("Prio");
                            }
                        }
                        arrayList.add(proMoneyDemand);
                    }
                }
                makeMoneyDemandForProData.b = arrayList;
            }
            if (!jSONObject2.isNull("Total")) {
                jSONObject = jSONObject2.getJSONObject("Total");
                if (!jSONObject.isNull("AmountHT")) {
                    makeMoneyDemandForProData.f = Float.parseFloat(jSONObject.getString("AmountHT"));
                }
                if (!jSONObject.isNull("AmountTTC")) {
                    makeMoneyDemandForProData.g = Float.parseFloat(jSONObject.getString("AmountTTC"));
                }
                if (!jSONObject.isNull("Tax")) {
                    makeMoneyDemandForProData.h = Float.parseFloat(jSONObject.getString("Tax"));
                }
            }
            if (!jSONObject2.isNull("Message")) {
                makeMoneyDemandForProData.d = jSONObject2.getString("Message");
            }
            makeMoneyDemandForProData.e = jk.a(jSONObject2.getString("OperationDate"));
            if (!jSONObject2.isNull("Id")) {
                makeMoneyDemandForProData.c = jSONObject2.getLong("Id");
            }
            parcelable2 = makeMoneyDemandForProData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.MakeMoneyDemandForPro", parcelable2);
        }
        return bundle;
    }
}
