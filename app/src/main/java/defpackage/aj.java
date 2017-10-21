package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.CheckUserActivationData;
import fr.smoney.android.izly.data.model.ServerError;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class aj {
    public static Bundle a(String str) throws JSONException {
        Parcelable serverError;
        Bundle bundle;
        int i = 0;
        Parcelable parcelable = null;
        if (str != null && str.length() > 0) {
            JSONObject jSONObject = new JSONObject(str);
            if (!jSONObject.isNull("ErrorMessage")) {
                serverError = new ServerError();
                serverError.b = jSONObject.getInt("Code");
                serverError.c = jSONObject.getString("ErrorMessage");
                serverError.e = jSONObject.getInt("Priority");
                serverError.d = jSONObject.getString("Title");
            } else if (!jSONObject.isNull("CheckUserActivationDataResult")) {
                JSONObject jSONObject2 = new JSONObject(jSONObject.optString("CheckUserActivationDataResult"));
                CheckUserActivationData checkUserActivationData = new CheckUserActivationData();
                if (!jSONObject2.isNull("BanPasswords")) {
                    checkUserActivationData.a = new ArrayList();
                    JSONArray jSONArray = jSONObject2.getJSONArray("BanPasswords");
                    int length = jSONArray.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        checkUserActivationData.a.add(jSONArray.getString(i2));
                    }
                }
                checkUserActivationData.b = jSONObject2.optString("CguUrl");
                if (!jSONObject2.isNull("SecretQuestions")) {
                    checkUserActivationData.c = new ArrayList();
                    JSONArray jSONArray2 = jSONObject2.getJSONArray("SecretQuestions");
                    int length2 = jSONArray2.length();
                    while (i < length2) {
                        checkUserActivationData.c.add(jSONArray2.getString(i));
                        i++;
                    }
                }
                CheckUserActivationData checkUserActivationData2 = checkUserActivationData;
                serverError = null;
                parcelable = checkUserActivationData2;
            }
            bundle = new Bundle();
            if (serverError != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
            }
            if (parcelable != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.CheckUserActivationData", parcelable);
            }
            return bundle;
        }
        serverError = null;
        bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.CheckUserActivationData", parcelable);
        }
        return bundle;
    }
}
