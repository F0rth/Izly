package defpackage;

import android.os.Bundle;
import android.os.Parcelable;
import com.slidingmenu.lib.BuildConfig;
import fr.smoney.android.izly.data.model.GoodDealsArray;
import fr.smoney.android.izly.data.model.GoodDealsData;
import fr.smoney.android.izly.data.model.ServerError;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.spongycastle.i18n.MessageBundle;

public final class el {
    public static Bundle a() throws IOException, JSONException, ParseException {
        Parcelable parcelable;
        int i = 0;
        Parcelable parcelable2 = null;
        cq cqVar = new cq("http://izly.maximiles.com/api/deals?limit=20");
        cqVar.a(BuildConfig.VERSION_NAME);
        String a = cqVar.a(0);
        Bundle bundle = new Bundle();
        if (a == null || a.length() <= 0) {
            parcelable = null;
        } else {
            ServerError serverError;
            ArrayList arrayList = new ArrayList();
            JSONObject jSONObject = new JSONObject(a);
            if (jSONObject.isNull("ErrorMessage")) {
                JSONArray jSONArray = jSONObject.getJSONObject("_embedded").getJSONArray("deals");
                int length = jSONArray.length();
                while (i < length) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    arrayList.add(new GoodDealsData(jSONObject2.getString("id"), jSONObject2.getString(MessageBundle.TITLE_ENTRY), jSONObject2.getString("image_small_path"), jSONObject2.getString("short_description")));
                    i++;
                }
            } else {
                serverError = new ServerError();
                serverError.b = jSONObject.getInt("Code");
                serverError.c = jSONObject.getString("ErrorMessage");
                serverError.e = jSONObject.getInt("Priority");
                serverError.d = jSONObject.getString("Title");
            }
            GoodDealsArray goodDealsArray = new GoodDealsArray(arrayList);
            parcelable = serverError;
            parcelable2 = goodDealsArray;
        }
        if (parcelable != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.serverError", parcelable);
        }
        if (parcelable2 != null) {
            bundle.putParcelable("fr.smoney.android.izly.extras.GoodDealsData", parcelable2);
        }
        return bundle;
    }
}
