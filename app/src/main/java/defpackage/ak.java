package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.ServerError;
import org.json.JSONException;
import org.json.JSONObject;

public final class ak {
    public static Bundle a(String str) throws JSONException {
        Parcelable parcelable = null;
        JSONObject jSONObject = new JSONObject(str);
        if (!jSONObject.isNull("ErrorMessage")) {
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
        return bundle;
    }
}
