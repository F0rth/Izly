package defpackage;

import android.os.Bundle;
import java.io.IOException;
import java.text.ParseException;
import java.util.Locale;
import org.json.JSONException;

public final class dy {
    public static Bundle a(String str) throws IOException, JSONException, ParseException {
        cq cqVar = new cq("https://rest.izly.fr/Service/PublicService.svc/rest/GetLogonInfos");
        cqVar.a("2.0");
        cqVar.a("language", Locale.getDefault().getLanguage());
        cqVar.a("userId", str);
        return ao.a(cqVar.a(0));
    }
}
