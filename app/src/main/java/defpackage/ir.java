package defpackage;

import android.content.Context;
import android.os.Bundle;
import com.ad4screen.sdk.A4S;
import fr.smoney.android.izly.data.model.LoginData;

public final class ir {
    public static void a(Context context, LoginData loginData) {
        String str = "";
        String str2 = "";
        if (loginData.C) {
            str = "Y";
        }
        if (loginData.D) {
            str2 = "Y";
        }
        Bundle bundle = new Bundle();
        bundle.putString("optin_smoney", str);
        bundle.putString("optin_partenaires", str2);
        bundle.putString("date_validation_formulaire", loginData.z);
        bundle.putString("user_id", loginData.b);
        bundle.putString("date_signature_cgu", loginData.y);
        bundle.putString("code_postal", loginData.t);
        bundle.putString("crous_de_rattachement", loginData.x);
        A4S.get(context).updateDeviceInfo(bundle);
    }
}
