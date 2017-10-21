package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.DeleteUserPictureData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class dn {
    public static Bundle a(String str, String str2) throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/DeleteUserPicture");
        cqVar.a(BuildConfig.VERSION_NAME);
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        cqVar.a("sessionId", str2);
        JSONObject jSONObject = new JSONObject(cqVar.a(1));
        if (jSONObject.isNull("ErrorMessage")) {
            DeleteUserPictureData deleteUserPictureData = new DeleteUserPictureData();
            if (!jSONObject.isNull("DeleteUserPictureResult")) {
                jSONObject = jSONObject.getJSONObject("DeleteUserPictureResult");
                if (!jSONObject.isNull("UP")) {
                    jSONObject = jSONObject.getJSONObject("UP");
                    deleteUserPictureData.a.a = Double.parseDouble(jSONObject.getString("BAL"));
                    deleteUserPictureData.a.c = ae.a.parse(jSONObject.getString("LUD")).getTime();
                }
            }
            Object obj = deleteUserPictureData;
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
            bundle.putParcelable("fr.smoney.android.izly.extras.deleteUserPictureData", parcelable2);
        }
        return bundle;
    }
}
