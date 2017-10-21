package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.ProMoneyRecipient;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class df {
    public static Bundle a(String str, String str2, ArrayList<Parcelable> arrayList, String str3, String str4, String str5) throws ClientProtocolException, IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/CheckMoneyDemandForPro");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONArray jSONArray = new JSONArray();
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ProMoneyRecipient proMoneyRecipient = (ProMoneyRecipient) arrayList.get(i);
            JSONObject jSONObject2 = new JSONObject();
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put("Identifier", proMoneyRecipient.a);
            jSONObject3.put("FirstName", proMoneyRecipient.b);
            jSONObject3.put("LastName", proMoneyRecipient.c);
            jSONObject2.put("Key", jSONObject3);
            jSONObject2.put("Value", (double) proMoneyRecipient.d);
            jSONArray.put(jSONObject2);
        }
        jSONObject.put("moneyDemands", jSONArray);
        jSONObject.put("tax", str3);
        jSONObject.put("message", str4);
        jSONObject.put("cashingModelId", str5);
        cqVar.b(jSONObject.toString());
        return ai.a(cqVar.a(1));
    }
}
