package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.data.model.Contact;
import fr.smoney.android.izly.data.model.Contact.b;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData;
import fr.smoney.android.izly.data.model.PreAuthorizationContainerData.PreAuthorization;
import fr.smoney.android.izly.data.model.ServerError;
import org.json.JSONException;
import org.json.JSONObject;

public final class bj {
    public static Bundle a(String str, String str2) throws JSONException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        JSONObject jSONObject = new JSONObject(str);
        if (jSONObject.isNull("ErrorMessage")) {
            parcelable = null;
        } else {
            parcelable = new ServerError();
            parcelable.b = jSONObject.getInt("Code");
            parcelable.c = jSONObject.getString("ErrorMessage");
            parcelable.e = jSONObject.getInt("Priority");
            parcelable.d = jSONObject.getString("Title");
        }
        if (!jSONObject.isNull(str2)) {
            parcelable2 = new PreAuthorizationContainerData();
            jSONObject = jSONObject.optJSONObject(str2);
            if (jSONObject != null) {
                jSONObject = jSONObject.optJSONObject("Result");
                if (jSONObject != null) {
                    parcelable2.b = jSONObject.getDouble("Amount");
                    parcelable2.c = jk.a(jSONObject.getString("ExpiryDate"));
                    JSONObject optJSONObject = jSONObject.optJSONObject("PreAuthorization");
                    if (optJSONObject != null) {
                        PreAuthorization preAuthorization = new PreAuthorization();
                        preAuthorization.a = jd.a(optJSONObject, "Identifier");
                        preAuthorization.b = optJSONObject.optInt("IdentifierType");
                        parcelable2.d = preAuthorization;
                    }
                    jSONObject = jSONObject.optJSONObject("Pro");
                    if (jSONObject != null) {
                        Contact contact = new Contact();
                        contact.b = jd.a(jSONObject, "DisplayName");
                        contact.a = jd.a(jSONObject, "Identifier");
                        boolean optBoolean = jSONObject.optBoolean("IsSmoneyUser");
                        boolean optBoolean2 = jSONObject.optBoolean("IsSmoneyPro");
                        if (optBoolean) {
                            contact.f = b.b;
                        }
                        if (optBoolean2) {
                            contact.f = b.c;
                        }
                        parcelable2.a = contact;
                    }
                }
            }
        }
        Bundle bundle = new Bundle();
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.RECEIVER_EXTRA_PRE_AUTHORIZATION_DATA", parcelable2);
        }
        return bundle;
    }
}
