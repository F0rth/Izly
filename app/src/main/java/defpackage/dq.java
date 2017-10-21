package defpackage;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetBilendiBannerData;
import java.io.IOException;
import java.text.ParseException;
import org.json.JSONException;

public final class dq {
    public static Bundle a(Context context, String str, String str2, String str3, String str4, String str5, String str6) throws IOException, JSONException, ParseException {
        cq cqVar = new cq("http://izly.maximiles.com/m/" + str + context.getString(R.string.profilage_bancaire_slash) + str2 + context.getString(R.string.profilage_bancaire_slash) + str3 + context.getString(R.string.profilage_bancaire_zip_code) + str4 + context.getString(R.string.profilage_bancaire_crous) + str5 + context.getString(R.string.profilage_bancaire_bank) + str6);
        String a = cqVar.a(0);
        int i = cqVar.b;
        Parcelable getBilendiBannerData = new GetBilendiBannerData();
        Bundle bundle = new Bundle();
        getBilendiBannerData.b = i;
        getBilendiBannerData.a = a;
        bundle.putParcelable("fr.smoney.android.izly.extra.BilendiBannerData", getBilendiBannerData);
        return bundle;
    }
}
