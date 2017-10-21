package defpackage;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;

public final class jj {
    public static void a(Activity activity, P2PPayCommerceInfos p2PPayCommerceInfos, boolean z) {
        String string = activity.getString(R.string.pay_url);
        if (p2PPayCommerceInfos != null && p2PPayCommerceInfos.r) {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new BasicNameValuePair("appid", p2PPayCommerceInfos.d));
            arrayList.add(new BasicNameValuePair("transactionid", p2PPayCommerceInfos.e));
            if (z) {
                arrayList.add(new BasicNameValuePair("smoneyid", String.valueOf(p2PPayCommerceInfos.f)));
            }
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(jl.a(string, arrayList))));
        }
        activity.finish();
    }
}
