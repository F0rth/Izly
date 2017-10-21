package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.SendChatMessageData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.i18n.TextBundle;

public final class fw {
    public static Bundle a(String str, String str2, long j, String str3) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/SendChatMessage");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("operationId", j);
        jSONObject2.put(TextBundle.TEXT_ENTRY, str3);
        jSONObject.put("message", jSONObject2);
        cqVar.b(jSONObject.toString());
        jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            SendChatMessageData sendChatMessageData = new SendChatMessageData();
            jSONObject = jSONObject.optJSONObject("SendChatMessageResult");
            if (!(jSONObject == null || jSONObject.isNull("UP"))) {
                jSONObject = jSONObject.getJSONObject("UP");
                sendChatMessageData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                sendChatMessageData.a.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
            }
            SendChatMessageData sendChatMessageData2 = sendChatMessageData;
            parcelable = null;
            Object obj = sendChatMessageData2;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.sendChatMessageData", parcelable2);
        }
        return bundle;
    }
}
