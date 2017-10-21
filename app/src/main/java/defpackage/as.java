package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.CashingModel;
import fr.smoney.android.izly.data.model.GetProCashingModelsData;
import fr.smoney.android.izly.data.model.ServerError;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class as {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            GetProCashingModelsData getProCashingModelsData = new GetProCashingModelsData();
            if (!jSONObject.isNull("GetProCashingModelsResult")) {
                jSONObject = jSONObject.getJSONObject("GetProCashingModelsResult");
                if (!jSONObject.isNull("Result")) {
                    JSONObject jSONObject2 = jSONObject.getJSONObject("Result");
                    if (!jSONObject2.isNull("CashingModels")) {
                        JSONArray jSONArray = jSONObject2.getJSONArray("CashingModels");
                        int length = jSONArray.length();
                        for (int i = 0; i < length; i++) {
                            JSONObject jSONObject3;
                            CashingModel cashingModel = new CashingModel();
                            JSONObject jSONObject4 = jSONArray.getJSONObject(i);
                            if (!jSONObject4.isNull("Amount")) {
                                jSONObject3 = jSONObject4.getJSONObject("Amount");
                                if (!jSONObject3.isNull("AmountHT")) {
                                    cashingModel.b = Double.parseDouble(iu.c(jSONObject3.getString("AmountHT")));
                                }
                                if (!jSONObject3.isNull("AmountTTC")) {
                                    cashingModel.c = Double.parseDouble(iu.c(jSONObject3.getString("AmountTTC")));
                                }
                                if (!jSONObject3.isNull("Tax")) {
                                    cashingModel.d = Double.parseDouble(iu.c(jSONObject3.getString("Tax")));
                                }
                            }
                            if (!jSONObject4.isNull("Id")) {
                                cashingModel.a = jSONObject4.getInt("Id");
                            }
                            if (!jSONObject4.isNull("Message")) {
                                cashingModel.e = jSONObject4.getString("Message");
                            }
                            if (!jSONObject4.isNull("Name")) {
                                cashingModel.f = jSONObject4.getString("Name");
                            }
                            if (!jSONObject4.isNull("Recipient")) {
                                jSONObject3 = jSONObject4.getJSONObject("Recipient");
                                if (!jSONObject3.isNull("Identifier")) {
                                    cashingModel.g = jSONObject3.getString("Identifier");
                                }
                                if (!jSONObject3.isNull("DisplayName")) {
                                    cashingModel.h = jSONObject3.getString("DisplayName");
                                }
                            }
                            if (!jSONObject4.isNull("Attachment")) {
                                jSONObject4 = jSONObject4.getJSONObject("Attachment");
                                if (!jSONObject4.isNull("Id")) {
                                    cashingModel.i = jSONObject4.getLong("Id");
                                }
                                if (!jSONObject4.isNull("Name")) {
                                    cashingModel.j = jSONObject4.getString("Name");
                                }
                                if (!jSONObject4.isNull("ContentType")) {
                                    cashingModel.k = jSONObject4.getString("ContentType");
                                }
                                if (!jSONObject4.isNull("Size")) {
                                    cashingModel.l = jSONObject4.getInt("Size");
                                }
                            }
                            getProCashingModelsData.a.add(cashingModel);
                        }
                    }
                    if (!jSONObject2.isNull("Version")) {
                        getProCashingModelsData.b = jSONObject2.getInt("Version");
                    }
                }
            }
            parcelable2 = getProCashingModelsData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.GetProCashingModels", parcelable2);
        }
        return bundle;
    }
}
