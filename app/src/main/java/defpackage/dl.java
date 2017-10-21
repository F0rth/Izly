package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.DealsData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.i18n.MessageBundle;

public final class dl {
    public static Bundle a(String str) throws IOException, JSONException, ParseException {
        Parcelable parcelable;
        Parcelable parcelable2 = null;
        cq cqVar = new cq(str);
        cqVar.a(BuildConfig.VERSION_NAME);
        String a = cqVar.a(0);
        Bundle bundle = new Bundle();
        JSONObject jSONObject = new JSONObject(a);
        if (jSONObject.isNull("ErrorMessage")) {
            Object dealsData = new DealsData(jSONObject.getString("long_description"), jSONObject.getString("image_big_path"), jSONObject.getString(MessageBundle.TITLE_ENTRY), jSONObject.getString("id"), jSONObject.getString("type_of_mechanical"), jSONObject.getString("link_redirect"));
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
            bundle.putParcelable("fr.smoney.android.izly.extra.DealsData", parcelable2);
        }
        return bundle;
    }
}
