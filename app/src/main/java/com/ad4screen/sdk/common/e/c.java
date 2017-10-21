package com.ad4screen.sdk.common.e;

import android.content.Context;
import android.net.Uri;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.common.c.d;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.service.modules.b.a.a;
import com.ad4screen.sdk.service.modules.b.b;
import com.ezeeworld.b4s.android.sdk.server.Api2;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class c implements com.ad4screen.sdk.common.c.c<c>, d, Runnable {
    private static b l;
    HttpURLConnection a;
    public long b = g.e().a();
    private String c = "text/xml;charset=utf-8";
    private int d = 1;
    private boolean e = false;
    private boolean f = false;
    private int g;
    private long h = g.e().a();
    private int i = 0;
    private int j = 0;
    private com.ad4screen.sdk.d.b k;
    private Context m;
    private com.ad4screen.sdk.service.modules.b.a.c n = new com.ad4screen.sdk.service.modules.b.a.c(this) {
        final /* synthetic */ c a;

        {
            this.a = r1;
        }

        public void a() {
            f.a().b(a.class, this.a.n);
            f.a().b(com.ad4screen.sdk.service.modules.b.a.b.class, this.a.n);
            Log.error("Could not retrieve a valid token");
            this.a.a(new ConnectException("Could not retrieve a valid token"));
            f.a().a(new d.a(this.a, this.a.c()));
        }

        public void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            f.a().b(a.class, this.a.n);
            f.a().b(com.ad4screen.sdk.service.modules.b.a.b.class, this.a.n);
            this.a.a(this.a.e(), this.a.d());
        }
    };

    public c(Context context) {
        this.m = context;
        this.k = com.ad4screen.sdk.d.b.a(this.m);
    }

    private String a(InputStream inputStream) throws IOException {
        try {
            String readLine;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder(8192);
            for (readLine = bufferedReader.readLine(); readLine != null; readLine = bufferedReader.readLine()) {
                stringBuilder.append(readLine);
            }
            readLine = stringBuilder.toString();
            if (inputStream != null) {
                inputStream.close();
            }
            return readLine;
        } catch (IOException e) {
            throw e;
        } catch (Throwable th) {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    protected static String a(String str, String str2, long j) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str2 != null) {
            stringBuilder.append(str2);
        }
        if (str != null) {
            stringBuilder.append(str);
        }
        stringBuilder.append(j);
        return h.b(stringBuilder.toString());
    }

    private void a(HttpURLConnection httpURLConnection, String str) {
        c("application/json;charset=utf-8");
        long timeInMillis = Calendar.getInstance(Locale.US).getTimeInMillis() / 1000;
        String str2 = null;
        StringBuilder stringBuilder = new StringBuilder();
        if (this.k != null) {
            com.ad4screen.sdk.service.modules.b.a.a h = i.a(this.m).h();
            if (h != null) {
                if (!(h.b == null || c().equals(com.ad4screen.sdk.d.d.b.AuthenticationWebservice.toString()))) {
                    stringBuilder.append(h.b);
                }
                if (!(h.a == null || c().equals(com.ad4screen.sdk.d.d.b.AuthenticationWebservice.toString()))) {
                    stringBuilder.append(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR).append(h.a);
                }
            }
            if (stringBuilder.length() > 0) {
                httpURLConnection.setRequestProperty("Authorization", stringBuilder.toString());
            }
            str2 = this.k.m();
        }
        httpURLConnection.setRequestProperty("Accengage-Signature", a(str2, str, timeInMillis));
        httpURLConnection.setRequestProperty("Accengage-Time", String.valueOf(timeInMillis));
    }

    private void b() {
        f.a().a(a.class, this.n);
        f.a().a(com.ad4screen.sdk.service.modules.b.a.b.class, this.n);
        if (l != null) {
            l.run();
        }
    }

    protected void a(int i) {
        this.d = i;
    }

    public abstract void a(String str);

    public void a(String str, String str2) {
        String str3 = null;
        this.i++;
        if (str2 != null && str2.contains("null")) {
            str2 = null;
        }
        m.i.a(4273235);
        try {
            this.a = (HttpURLConnection) new URL(h.a(this.m, str, new e("partnerId", Uri.encode(this.k.l())), new e("sharedId", Uri.encode(this.k.c())))).openConnection();
            if (p()) {
                a(this.a, str2);
                com.ad4screen.sdk.service.modules.b.a.a h = i.a(this.m).h();
                if (!(c() == null || c().equals(com.ad4screen.sdk.d.d.b.AuthenticationWebservice.toString()) || (h != null && h.a != null && h.b != null))) {
                    b();
                    return;
                }
            }
            this.a.setDoInput(true);
            this.a.setUseCaches(false);
            this.a.setConnectTimeout(10000);
            this.a.setReadTimeout(10000);
            this.a.setRequestProperty("Content-Type", this.c);
            this.a.setRequestProperty("User-Agent", m.a(this.m));
            this.a.setInstanceFollowRedirects(true);
            this.a.setRequestMethod(d(str2));
            if (str2 == null) {
                this.a.setDoOutput(false);
            } else {
                this.a.setDoOutput(true);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(this.a.getOutputStream());
                bufferedOutputStream.write(str2.getBytes());
                bufferedOutputStream.close();
            }
            f();
            int responseCode = this.a.getResponseCode();
            if (responseCode == 200) {
                str3 = a(this.a.getInputStream());
            }
            b(responseCode, str3);
            if (responseCode / 100 == 3) {
                String headerField = this.a.getHeaderField("Location");
                this.a.disconnect();
                a(headerField, str2);
                if (this.a != null) {
                    this.a.disconnect();
                }
                m.i.a();
            } else if (responseCode == 401) {
                Log.internal("Token expired, retrieving new one");
                this.a.disconnect();
                b();
                if (this.a != null) {
                    this.a.disconnect();
                }
                m.i.a();
            } else {
                if (responseCode == 200) {
                    a(str3);
                    f.a().a(new d.b(this, c()));
                } else {
                    str3 = a(this.a.getErrorStream());
                    Log.error("Could not reach Accengage servers");
                    if (a(responseCode, str3)) {
                        f.a().a(new d.b(this, c()));
                    } else {
                        a(new ConnectException("Could not reach Accengage servers"));
                        f.a().a(new d.a(this, c()));
                    }
                }
                if (this.a != null) {
                    this.a.disconnect();
                }
                m.i.a();
            }
        } catch (Throwable e) {
            Log.internal("URLConnectionTask URL error @ " + str, e);
            a(e);
            f.a().a(new d.a(this, c()));
        } catch (Throwable e2) {
            if (p() && e2.getMessage() != null && e2.getMessage().contains("authentication challenge")) {
                Log.internal("Token invalid, retrying with new one");
                b();
            } else {
                if (a(-1, e2.getMessage())) {
                    a("");
                    f.a().a(new d.b(this, c()));
                } else {
                    Log.internal("URLConnectionTask IO error! @ " + str, e2);
                    a(e2);
                    f.a().a(new d.a(this, c()));
                }
                if (this.a != null) {
                    this.a.disconnect();
                }
                m.i.a();
            }
        } catch (Throwable e22) {
            Log.internal("Tracking needs more permission to work. Please refer to the documentation.", e22);
            a(e22);
            f.a().a(new d.a(this, c()));
        } finally {
            if (this.a != null) {
                this.a.disconnect();
            }
            m.i.a();
        }
    }

    public abstract void a(Throwable th);

    public boolean a() {
        return true;
    }

    public boolean a(int i, String str) {
        return false;
    }

    public abstract c b(c cVar);

    protected void b(int i) {
        this.d |= i;
    }

    protected void b(int i, String str) {
        Log.internal(k() + " HttpResp[" + String.valueOf(i) + "] " + e() + (str == null ? "" : " Content=" + str));
    }

    public void b(String str) {
        com.ad4screen.sdk.common.a.a a = com.ad4screen.sdk.common.a.a.a(this.m);
        if (this.e || (str != null && str.equals(com.ad4screen.sdk.d.d.b.DownloadWebservices.toString()))) {
            this.j++;
            a(e(), d());
            return;
        }
        if (n()) {
            this.e = true;
            a.a(this, str);
        }
        if (this.j >= 3) {
            Log.internal("This task has been retried too many times, will be retried at next flush");
            this.j = 2;
            f.a().a(new d.a(this, c()));
            if (c().equals(com.ad4screen.sdk.d.d.b.AuthenticationWebservice.toString())) {
                f.a().a(new a());
            }
        } else if (!n()) {
            this.j++;
            a.a(new Runnable(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void run() {
                    this.a.a(this.a.e(), this.a.d());
                }
            });
        } else if ((this.d & 4) != 0) {
            this.j++;
            a.f();
        }
    }

    public abstract String c();

    public void c(int i) {
        this.h = g.e().a();
        this.g = i;
    }

    protected void c(String str) {
        this.c = str;
    }

    public abstract String d();

    public String d(String str) {
        return str == null ? "GET" : "POST";
    }

    public c e(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("com.ad4screen.sdk.common.tasks.URLConnectionTask");
        if (!jSONObject.isNull("flags")) {
            this.d = jSONObject.getInt("flags");
        }
        if (!jSONObject.isNull(Api2.HEADER_CACHED)) {
            this.e = jSONObject.getBoolean(Api2.HEADER_CACHED);
        }
        if (!jSONObject.isNull("alreadyPrepared")) {
            this.f = jSONObject.getBoolean("alreadyPrepared");
        }
        if (!jSONObject.isNull("contentType")) {
            this.c = jSONObject.getString("contentType");
        }
        if (!jSONObject.isNull("creationTimestamp")) {
            this.b = jSONObject.getLong("creationTimestamp");
        }
        if (!jSONObject.isNull("isSecure") && jSONObject.getBoolean("isSecure")) {
            i();
        }
        return this;
    }

    public abstract String e();

    protected void f() {
        String k = k();
        String e = e();
        String d = d();
        Log.internal(k + " HttpReq[" + this.a.getRequestMethod() + "] " + e + (d == null ? "" : " Content=" + d));
    }

    public /* synthetic */ Object fromJSON(String str) throws JSONException {
        return e(str);
    }

    public void g() {
        if ((this.d & 2) != 0) {
            a();
        }
        a(e(), d());
    }

    public abstract String getClassKey();

    public int h() {
        return this.d;
    }

    protected void i() {
        if (l == null) {
            l = new b(this.m);
        }
        if (!p()) {
            b(8);
        }
    }

    protected void j() {
        b(16);
    }

    public String k() {
        String classKey = getClassKey();
        if (classKey != null) {
            String[] split = classKey.split("\\.");
            if (split != null && split.length > 0) {
                classKey = split[split.length - 1];
            }
        }
        return new String(classKey);
    }

    public int l() {
        return this.g;
    }

    public boolean m() {
        return g.e().a() - this.h > ((long) this.g) && this.i < 6;
    }

    public boolean n() {
        return (h() & 1) != 0;
    }

    public boolean o() {
        return (h() & 16) != 0;
    }

    public boolean p() {
        return (h() & 8) != 0;
    }

    public boolean q() {
        return e() != null;
    }

    public int r() {
        return this.i;
    }

    public void run() {
        String e = e();
        Log.internal("Starting URL request @ " + e);
        if (com.ad4screen.sdk.d.b.a(this.m).c() == null) {
            Log.debug("No Shared id available yet, waiting..");
            f.a().a(com.ad4screen.sdk.service.modules.k.f.f.class, new com.ad4screen.sdk.service.modules.k.f.d(this) {
                final /* synthetic */ c a;

                {
                    this.a = r1;
                }

                public void a() {
                    this.a.run();
                }
            });
        } else if (this.f || a()) {
            this.f = true;
            b(c());
        } else {
            Log.internal("Cancelled URL request @ " + e);
        }
    }

    public int s() {
        return 6;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("flags", this.d);
        jSONObject2.put(Api2.HEADER_CACHED, this.e);
        jSONObject2.put("alreadyPrepared", this.f);
        jSONObject2.put("contentType", this.c);
        jSONObject2.put("creationTimestamp", this.b);
        jSONObject2.put("isSecure", p());
        jSONObject.put("type", getClassKey());
        jSONObject.put("com.ad4screen.sdk.common.tasks.URLConnectionTask", jSONObject2);
        return jSONObject;
    }
}
