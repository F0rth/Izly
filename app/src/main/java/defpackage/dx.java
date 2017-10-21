package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.CrousData;
import fr.smoney.android.izly.data.model.GetCrousContactListData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class dx {
    public static Bundle a(String str, String str2) throws IOException, JSONException, ParseException {
        Parcelable serverError;
        Bundle bundle;
        int i = 0;
        Parcelable parcelable = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetCrousContactList");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        String a = cqVar.a(0);
        if (a != null && a.length() > 0) {
            JSONObject jSONObject = new JSONObject(a);
            if (!jSONObject.isNull("ErrorMessage")) {
                serverError = new ServerError();
                serverError.b = jSONObject.getInt("Code");
                serverError.c = jSONObject.getString("ErrorMessage");
                serverError.e = jSONObject.getInt("Priority");
                serverError.d = jSONObject.getString("Title");
            } else if (!jSONObject.isNull("GetCrousContactListResult")) {
                jSONObject = jSONObject.getJSONObject("GetCrousContactListResult");
                if (!jSONObject.isNull("Result")) {
                    GetCrousContactListData getCrousContactListData = new GetCrousContactListData();
                    jSONObject = jSONObject.getJSONObject("Result");
                    if (!jSONObject.isNull("UserContact")) {
                        JSONObject jSONObject2 = jSONObject.getJSONObject("UserContact");
                        getCrousContactListData.a = jSONObject2.getString("Name");
                        getCrousContactListData.b = jSONObject2.getString("Email");
                        getCrousContactListData.c = jSONObject2.getString("Phone");
                    }
                    JSONArray optJSONArray = jSONObject.optJSONArray("CrousList");
                    if (optJSONArray != null) {
                        int length = optJSONArray.length();
                        while (i < length) {
                            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
                            if (optJSONObject != null) {
                                CrousData crousData = new CrousData();
                                crousData.a = optJSONObject.optString("Number");
                                crousData.b = optJSONObject.optString("Name");
                                crousData.c = optJSONObject.optBoolean("IsDefault");
                                getCrousContactListData.d.add(crousData);
                            }
                            i++;
                        }
                    }
                    GetCrousContactListData getCrousContactListData2 = getCrousContactListData;
                    serverError = null;
                    Object obj = getCrousContactListData2;
                }
            }
            bundle = new Bundle();
            if (serverError != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
            }
            if (parcelable != null) {
                bundle.putParcelable("fr.smoney.android.izly.extras.GetCrousContactListData", parcelable);
            }
            return bundle;
        }
        serverError = null;
        bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GetCrousContactListData", parcelable);
        }
        return bundle;
    }
}
