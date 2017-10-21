package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.ServiceData;
import java.text.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class ao {
    public static Bundle a(String str) throws JSONException, ParseException {
        Parcelable parcelable;
        int i = 0;
        Parcelable parcelable2 = null;
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            if (!jSONObject.isNull("GetLogonInfosResult")) {
                JSONObject jSONObject2 = jSONObject.getJSONObject("GetLogonInfosResult");
                if (!jSONObject2.isNull("Result")) {
                    JSONObject jSONObject3;
                    LoginData loginData = new LoginData();
                    JSONObject jSONObject4 = jSONObject2.getJSONObject("Result");
                    loginData.b = jSONObject4.optString("UserId");
                    loginData.w = jSONObject4.optString("Alias");
                    loginData.j = jSONObject4.optString("Currency");
                    loginData.r = jSONObject4.optString("Email");
                    loginData.p = jSONObject4.optString("FirstName");
                    loginData.q = jSONObject4.optString("LastName");
                    loginData.s = jSONObject4.optInt("Age");
                    loginData.t = jSONObject4.optString("ZipCode");
                    loginData.u = jSONObject4.optString("Crous");
                    loginData.v = jSONObject4.optInt("CategoryUserId");
                    loginData.x = jSONObject4.optString("CrousName");
                    loginData.y = jSONObject4.optString("TermsConditionsAgreementDate");
                    loginData.z = jSONObject4.optString("SubscriptionDate");
                    loginData.A = jSONObject4.optString("Banks");
                    if (!jSONObject4.isNull("LimitMoneyIn")) {
                        jSONObject3 = jSONObject4.getJSONObject("LimitMoneyIn");
                        loginData.g = Double.parseDouble(iu.c(jSONObject3.optString("Max")));
                        loginData.f = Double.parseDouble(iu.c(jSONObject3.optString("Min")));
                    }
                    if (!jSONObject4.isNull("LimitMoneyOut")) {
                        jSONObject3 = jSONObject4.getJSONObject("LimitMoneyOut");
                        try {
                            loginData.i = Double.parseDouble(iu.c(jSONObject3.optString("Max")));
                        } catch (NumberFormatException e) {
                        }
                        try {
                            loginData.h = Double.parseDouble(iu.c(jSONObject3.optString("Min")));
                        } catch (NumberFormatException e2) {
                        }
                    }
                    if (!jSONObject4.isNull("LimitPayment")) {
                        jSONObject3 = jSONObject4.getJSONObject("LimitPayment");
                        loginData.e = Double.parseDouble(iu.c(jSONObject3.optString("Max")));
                        loginData.d = Double.parseDouble(iu.c(jSONObject3.optString("Min")));
                    }
                    loginData.C = jSONObject4.optBoolean("OptIn");
                    loginData.D = jSONObject4.optBoolean("OptInPartners");
                    loginData.F = jSONObject4.optInt("Role") == 2;
                    if (!jSONObject4.isNull("ServicesInfos")) {
                        JSONArray jSONArray = jSONObject4.getJSONArray("ServicesInfos");
                        int length = jSONArray.length();
                        while (i < length) {
                            JSONObject jSONObject5 = jSONArray.getJSONObject(i);
                            ServiceData serviceData = new ServiceData();
                            serviceData.a = jSONObject5.getInt("Id");
                            serviceData.b = jSONObject5.getBoolean("CguExpired");
                            loginData.J.add(serviceData);
                            i++;
                        }
                    }
                    loginData.I = jSONObject4.optString("Token");
                    loginData.a = jSONObject4.optString("UserIdentifier");
                    loginData.E = jSONObject4.optInt("UserStatus");
                    if (!jSONObject2.isNull("UP")) {
                        jSONObject3 = jSONObject2.getJSONObject("UP");
                        loginData.B.a = Double.parseDouble(jSONObject3.getString("BAL"));
                        loginData.B.b = Double.parseDouble(jSONObject3.getString("CASHBAL"));
                        loginData.B.c = ae.a.parse(jSONObject3.getString("LUD")).getTime();
                    }
                    parcelable = null;
                    parcelable2 = loginData;
                }
            }
            parcelable = null;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetLogonInfos", parcelable2);
        }
        return bundle;
    }
}
