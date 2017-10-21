package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.IsEnrollmentOpenData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

public final class en {
    public static Bundle a() throws ClientProtocolException, IOException, JSONException, ParseException {
        Parcelable serverError;
        Parcelable parcelable = null;
        JSONObject jSONObject = new JSONObject(new cq("https://rest.izly.fr/Service/PublicService.svc/rest/isEnrollmentOpen").a(0));
        if (!jSONObject.isNull("ErrorMessage")) {
            serverError = new ServerError();
            serverError.b = jSONObject.getInt("Code");
            serverError.c = jSONObject.getString("ErrorMessage");
            serverError.e = jSONObject.getInt("Priority");
            serverError.d = jSONObject.getString("Title");
        } else if (jSONObject.isNull("IsEnrollmentOpenResult")) {
            serverError = null;
        } else {
            IsEnrollmentOpenData isEnrollmentOpenData = new IsEnrollmentOpenData();
            isEnrollmentOpenData.a = jSONObject.getBoolean("IsEnrollmentOpenResult");
            IsEnrollmentOpenData isEnrollmentOpenData2 = isEnrollmentOpenData;
            serverError = null;
            Object obj = isEnrollmentOpenData2;
        }
        Bundle bundle = new Bundle();
        if (serverError != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", serverError);
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.IsEnrollementOpen", parcelable);
        }
        return bundle;
    }
}
