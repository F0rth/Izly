package com.ad4screen.sdk.service.modules.d;

import android.content.Context;
import android.net.Uri;

import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.a.a;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.d;
import com.ad4screen.sdk.d.d.b;

import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

public class c extends com.ad4screen.sdk.common.e.c {
    private final String c = "com.ad4screen.sdk.service.modules.common.DownloadWebservicesURLTask";
    private final int d = 10;
    private final Context e;
    private int f = 1;

    public c(Context context) {
        super(context);
        this.e = context;
    }

    protected void a(String str) {
        d.a(this.e).e(b.DownloadWebservices);
        try {
            JSONArray jSONArray = new JSONObject(str).getJSONArray("data");
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                b a = b.a(jSONObject.getString("name"));
                if (a != null) {
                    d.a(this.e).a(a, jSONObject.getString("url"));
                    Log.internal("DownloadWebservicesURLTask|" + a.toString() + " url updated");
                }
            }
            com.ad4screen.sdk.d.b.a(this.e).b(Calendar.getInstance().getTime());
            Log.internal("DownloadWebservicesURLTask|Routes updated");
            a.a(this.e).f();
        } catch (Throwable e) {
            Log.internal("DownloadWebservicesURLTask|Webservices URLs Parsing error!", e);
        }
    }

    protected void a(Throwable th) {
    }

    protected boolean a() {
        if (d.a(this.e).c(b.DownloadWebservices)) {
            a(4);
            return true;
        }
        Log.debug("Service interruption on DownloadWebservicesURLTask");
        return false;
    }

    protected boolean a(int i, String str) {
        if (this.f < 10) {
            Log.internal("DownloadWebservicesURLTask|Call to server " + this.f + " failed");
            this.f++;
            Log.internal("DownloadWebservicesURLTask|Now calling server " + this.f);
            a.a(this.e).a((Runnable) this);
            return false;
        }
        Log.error("DownloadWebservicesURLTask|Can't download webservices url");
        return super.a(i, str);
    }

    public com.ad4screen.sdk.common.e.c b(com.ad4screen.sdk.common.e.c cVar) {
        return cVar;
    }

    protected String c() {
        return b.DownloadWebservices.toString();
    }

    protected String d() {
        return null;
    }

    protected String e() {
        return h.a(this.e, d.a(this.e).a(b.DownloadWebservices), false, new e("SERVER", String.valueOf(this.f)), new e("version", Uri.encode(Constants.SDK_VERSION)));
    }

    public String getClassKey() {
        return "com.ad4screen.sdk.service.modules.common.DownloadWebservicesURLTask";
    }
}
