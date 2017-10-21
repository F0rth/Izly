package defpackage;

import android.util.Log;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.ActivateUserData;
import fr.smoney.android.izly.data.model.OAuthData;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class cq {
    private static final String c = cq.class.getSimpleName();
    public String a;
    public int b;
    private String d;
    private ArrayList<NameValuePair> e;
    private ArrayList<NameValuePair> f;
    private String g;

    public cq() {
        this("");
    }

    public cq(String str) {
        this.d = str;
        this.e = new ArrayList();
        this.f = new ArrayList();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String a(java.io.InputStream r4) {
        /*
        r0 = new java.io.BufferedReader;
        r1 = new java.io.InputStreamReader;
        r1.<init>(r4);
        r0.<init>(r1);
        r1 = new java.lang.StringBuilder;
        r1.<init>();
    L_0x000f:
        r2 = r0.readLine();	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        if (r2 == 0) goto L_0x0035;
    L_0x0015:
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        r3.<init>();	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        r2 = r3.append(r2);	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        r3 = "\n";
        r2 = r2.append(r3);	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        r2 = r2.toString();	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        r1.append(r2);	 Catch:{ IOException -> 0x002c, all -> 0x003b }
        goto L_0x000f;
    L_0x002c:
        r0 = move-exception;
        r4.close();	 Catch:{ IOException -> 0x0040 }
    L_0x0030:
        r0 = r1.toString();
        return r0;
    L_0x0035:
        r4.close();	 Catch:{ IOException -> 0x0039 }
        goto L_0x0030;
    L_0x0039:
        r0 = move-exception;
        goto L_0x0030;
    L_0x003b:
        r0 = move-exception;
        r4.close();	 Catch:{ IOException -> 0x0042 }
    L_0x003f:
        throw r0;
    L_0x0040:
        r0 = move-exception;
        goto L_0x0030;
    L_0x0042:
        r1 = move-exception;
        goto L_0x003f;
        */
        throw new UnsupportedOperationException("Method not decompiled: cq.a(java.io.InputStream):java.lang.String");
    }

    public final String a(int i) throws ClientProtocolException, IOException {
        HttpResponse execute;
        int i2 = 0;
        HttpClient defaultHttpClient = new DefaultHttpClient();
        int size;
        NameValuePair nameValuePair;
        switch (i) {
            case 0:
                if (!this.f.isEmpty()) {
                    size = this.f.size();
                    while (i2 < size) {
                        if (i2 == 0) {
                            this.d += "?" + this.f.get(i2);
                        } else {
                            this.d += "&" + this.f.get(i2);
                        }
                        i2++;
                    }
                }
                HttpUriRequest httpGet = new HttpGet(this.d);
                Iterator it = this.e.iterator();
                while (it.hasNext()) {
                    nameValuePair = (NameValuePair) it.next();
                    httpGet.addHeader(nameValuePair.getName(), nameValuePair.getValue());
                }
                execute = defaultHttpClient.execute(httpGet);
                break;
            case 1:
                HttpUriRequest httpPost = new HttpPost(this.d);
                Iterator it2 = this.e.iterator();
                while (it2.hasNext()) {
                    nameValuePair = (NameValuePair) it2.next();
                    httpPost.addHeader(nameValuePair.getName(), nameValuePair.getValue());
                }
                if (this.a != null) {
                    httpPost.setEntity(new StringEntity(this.a, "UTF-8"));
                }
                Log.i(c, "REQUEST HEADERS:");
                Header[] allHeaders = httpPost.getAllHeaders();
                for (size = 0; size < allHeaders.length; size++) {
                    Log.i(c, "* " + allHeaders[size].getName() + ":" + allHeaders[size].getValue());
                }
                execute = defaultHttpClient.execute(httpPost);
                break;
            default:
                execute = null;
                break;
        }
        this.b = execute.getStatusLine().getStatusCode();
        this.g = execute.getStatusLine().getReasonPhrase();
        HttpEntity entity = execute.getEntity();
        return entity != null ? cq.a(entity.getContent()) : null;
    }

    public final void a(String str) {
        a("version", str);
        a("channel", "AIZ");
        a("format", "T");
        a("model", "A");
        a("clientVersion", "0.22");
        a("smoneyClientType", ad.a ? "PRO" : "PART");
        OAuthData a = SmoneyApplication.c.a();
        if (a != null) {
            a("Authorization", "Bearer " + a.a);
            return;
        }
        ActivateUserData c = SmoneyApplication.c.c();
        if (c != null) {
            StringBuilder append = new StringBuilder("Bearer ").append(c.b);
            a("Username", c.c);
            a("Authorization", append.toString());
        }
    }

    public final void a(String str, String str2) {
        this.e.add(new BasicNameValuePair(str, str2));
    }

    public final void b(String str) {
        a("Content-Type", kh.ACCEPT_JSON_VALUE);
        this.a = str;
    }

    public final void b(String str, String str2) {
        this.f.add(new BasicNameValuePair(str, str2));
    }
}
