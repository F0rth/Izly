package com.ad4screen.sdk.service.modules.k.f.a;

import android.content.Context;

public class b extends com.ad4screen.sdk.common.e.b {
    public b(Context context) {
        super(context);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean a() {
        /*
        r7 = this;
        r6 = 1;
        r0 = r7.b;
        r0 = com.ad4screen.sdk.d.b.a(r0);
        r1 = r0.K();
        if (r1 != 0) goto L_0x0013;
    L_0x000d:
        r0 = "Facebook|No Facebook AppId found, not sending facebook deferred applink request.";
        com.ad4screen.sdk.Log.info(r0);
    L_0x0012:
        return r6;
    L_0x0013:
        r2 = r7.b;
        r2 = com.ad4screen.sdk.common.c.a(r2);
        r3 = r7.b;
        r4 = r7.b;
        r4 = com.ad4screen.sdk.common.c.b(r4);
        r3 = com.ad4screen.sdk.common.c.a(r3, r4);
        r4 = r7.b;
        r4 = com.ad4screen.sdk.d.d.a(r4);
        r5 = com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice;
        r4 = r4.c(r5);
        if (r4 != 0) goto L_0x0039;
    L_0x0033:
        r0 = "Facebook|Facebook Tracking interrupted. Not sending facebook applink request";
        com.ad4screen.sdk.Log.internal(r0);
        goto L_0x0012;
    L_0x0039:
        r4 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r5 = "https://graph.facebook.com/";
        r4.<init>(r5);	 Catch:{ Exception -> 0x018b }
        r1 = r4.append(r1);	 Catch:{ Exception -> 0x018b }
        r4 = "/activities?format";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r4 = "=json";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r4 = "&sdk";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r4 = "=android";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r4 = "&application_package_name";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r4 = "=";
        r1 = r1.append(r4);	 Catch:{ Exception -> 0x018b }
        r0 = r0.p();	 Catch:{ Exception -> 0x018b }
        r0 = android.net.Uri.encode(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r1.append(r0);	 Catch:{ Exception -> 0x018b }
        r1 = "&application_tracking_enabled";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r1 = "=1&advertiser_tracking_enabled";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r1 = "=1&event=DEFERRED_APP_LINK";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r0 = r0.toString();	 Catch:{ Exception -> 0x018b }
        if (r2 == 0) goto L_0x011d;
    L_0x008c:
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r1.<init>();	 Catch:{ Exception -> 0x018b }
        r0 = r1.append(r0);	 Catch:{ Exception -> 0x018b }
        r1 = "&attribution=";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r0 = r0.append(r2);	 Catch:{ Exception -> 0x018b }
        r0 = r0.toString();	 Catch:{ Exception -> 0x018b }
        r1 = r0;
    L_0x00a4:
        r0 = 17986; // 0x4642 float:2.5204E-41 double:8.8863E-320;
        com.ad4screen.sdk.common.b.m.i.a(r0);	 Catch:{ Exception -> 0x018b }
        r0 = new java.net.URL;	 Catch:{ Exception -> 0x018b }
        r0.<init>(r1);	 Catch:{ Exception -> 0x018b }
        r0 = r0.openConnection();	 Catch:{ Exception -> 0x018b }
        r0 = (java.net.HttpURLConnection) r0;	 Catch:{ Exception -> 0x018b }
        r2 = 1;
        r0.setDoInput(r2);	 Catch:{ Exception -> 0x018b }
        r2 = 0;
        r0.setUseCaches(r2);	 Catch:{ Exception -> 0x018b }
        r2 = "Content-Type";
        r3 = "application/json";
        r0.setRequestProperty(r2, r3);	 Catch:{ Exception -> 0x018b }
        r2 = "User-Agent";
        r3 = "FBAndroidSDK.4.7.0";
        r0.setRequestProperty(r2, r3);	 Catch:{ Exception -> 0x018b }
        r2 = 1;
        r0.setDoOutput(r2);	 Catch:{ Exception -> 0x018b }
        r2 = "POST";
        r0.setRequestMethod(r2);	 Catch:{ Exception -> 0x018b }
        r2 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r3 = "Facebook|Sending query to : ";
        r2.<init>(r3);	 Catch:{ Exception -> 0x018b }
        r1 = r2.append(r1);	 Catch:{ Exception -> 0x018b }
        r1 = r1.toString();	 Catch:{ Exception -> 0x018b }
        com.ad4screen.sdk.Log.internal(r1);	 Catch:{ Exception -> 0x018b }
        r1 = new java.io.BufferedOutputStream;	 Catch:{ Exception -> 0x018b }
        r2 = r0.getOutputStream();	 Catch:{ Exception -> 0x018b }
        r1.<init>(r2);	 Catch:{ Exception -> 0x018b }
        r1.close();	 Catch:{ Exception -> 0x018b }
        r1 = r0.getResponseCode();	 Catch:{ Exception -> 0x018b }
        r2 = "Facebook|Query sent";
        com.ad4screen.sdk.Log.internal(r2);	 Catch:{ Exception -> 0x018b }
        r2 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x018b }
        r3 = new java.io.InputStreamReader;	 Catch:{ Exception -> 0x018b }
        r0 = r0.getInputStream();	 Catch:{ Exception -> 0x018b }
        r3.<init>(r0);	 Catch:{ Exception -> 0x018b }
        r2.<init>(r3);	 Catch:{ Exception -> 0x018b }
        r3 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r0 = 2048; // 0x800 float:2.87E-42 double:1.0118E-320;
        r3.<init>(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r2.readLine();	 Catch:{ Exception -> 0x018b }
    L_0x0113:
        if (r0 == 0) goto L_0x0139;
    L_0x0115:
        r3.append(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r2.readLine();	 Catch:{ Exception -> 0x018b }
        goto L_0x0113;
    L_0x011d:
        if (r3 == 0) goto L_0x01c0;
    L_0x011f:
        r1 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r1.<init>();	 Catch:{ Exception -> 0x018b }
        r0 = r1.append(r0);	 Catch:{ Exception -> 0x018b }
        r1 = "&advertiser_id=";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r0 = r0.append(r3);	 Catch:{ Exception -> 0x018b }
        r0 = r0.toString();	 Catch:{ Exception -> 0x018b }
        r1 = r0;
        goto L_0x00a4;
    L_0x0139:
        r0 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r1 != r0) goto L_0x0196;
    L_0x013d:
        r0 = "Facebook|Send Facebook applink request success";
        com.ad4screen.sdk.Log.debug(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r7.b;	 Catch:{ Exception -> 0x018b }
        r0 = com.ad4screen.sdk.d.d.a(r0);	 Catch:{ Exception -> 0x018b }
        r1 = com.ad4screen.sdk.d.d.b.FacebookTrackingWebservice;	 Catch:{ Exception -> 0x018b }
        r0.e(r1);	 Catch:{ Exception -> 0x018b }
        r0 = "Facebook|Received DEFERRED_APP_LINK response";
        com.ad4screen.sdk.Log.internal(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r3.toString();	 Catch:{ Exception -> 0x018b }
        r0 = com.ad4screen.sdk.service.modules.k.f.a.a.a.a(r0);	 Catch:{ Exception -> 0x018b }
        if (r0 == 0) goto L_0x0185;
    L_0x015c:
        r1 = new android.content.Intent;	 Catch:{ Exception -> 0x018b }
        r2 = "android.intent.action.VIEW";
        r1.<init>(r2);	 Catch:{ Exception -> 0x018b }
        r2 = r0.b();	 Catch:{ Exception -> 0x018b }
        r1.putExtras(r2);	 Catch:{ Exception -> 0x018b }
        r0 = r0.a();	 Catch:{ Exception -> 0x018b }
        r1.setData(r0);	 Catch:{ Exception -> 0x018b }
        r0 = 268435456; // 0x10000000 float:2.5243549E-29 double:1.32624737E-315;
        r1.setFlags(r0);	 Catch:{ Exception -> 0x018b }
        r0 = "Facebook|Found a Deferred APPLINK, redirection is now triggered";
        com.ad4screen.sdk.Log.internal(r0);	 Catch:{ Exception -> 0x018b }
        r0 = r7.b;	 Catch:{ Exception -> 0x018b }
        r0.startActivity(r1);	 Catch:{ Exception -> 0x018b }
    L_0x0180:
        com.ad4screen.sdk.common.b.m.i.a();
        goto L_0x0012;
    L_0x0185:
        r0 = "Facebook|No APPLINK found";
        com.ad4screen.sdk.Log.internal(r0);	 Catch:{ Exception -> 0x018b }
        goto L_0x0180;
    L_0x018b:
        r0 = move-exception;
        r1 = "Facebook|Error while sending Facebook applink request";
        com.ad4screen.sdk.Log.error(r1, r0);	 Catch:{ all -> 0x01bb }
        com.ad4screen.sdk.common.b.m.i.a();
        goto L_0x0012;
    L_0x0196:
        r0 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x018b }
        r2 = "Facebook|Send Facebook applink request failed : ";
        r0.<init>(r2);	 Catch:{ Exception -> 0x018b }
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r1 = "  ";
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r1 = r3.toString();	 Catch:{ Exception -> 0x018b }
        r0 = r0.append(r1);	 Catch:{ Exception -> 0x018b }
        r0 = r0.toString();	 Catch:{ Exception -> 0x018b }
        com.ad4screen.sdk.Log.error(r0);	 Catch:{ Exception -> 0x018b }
        com.ad4screen.sdk.common.b.m.i.a();
        goto L_0x0012;
    L_0x01bb:
        r0 = move-exception;
        com.ad4screen.sdk.common.b.m.i.a();
        throw r0;
    L_0x01c0:
        r1 = r0;
        goto L_0x00a4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ad4screen.sdk.service.modules.k.f.a.b.a():boolean");
    }

    public String getClassKey() {
        return "com.facebook.sdk.applink";
    }
}
