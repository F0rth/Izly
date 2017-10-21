package com.google.android.gms.internal;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.zzj.zza;
import com.google.android.gms.ads.internal.request.zzk;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzr;
import com.google.android.gms.internal.zzeg.zzb;
import com.google.android.gms.internal.zzeg.zzc;
import com.google.android.gms.internal.zzeg.zzd;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONException;
import org.json.JSONObject;

@zzhb
public final class zzhd extends zza {
    private static zzhd zzIQ;
    private static final Object zzqy = new Object();
    private final Context mContext;
    private final zzhc zzIR;
    private final zzbm zzIS;
    private final zzeg zzIT;

    zzhd(Context context, zzbm com_google_android_gms_internal_zzbm, zzhc com_google_android_gms_internal_zzhc) {
        this.mContext = context;
        this.zzIR = com_google_android_gms_internal_zzhc;
        this.zzIS = com_google_android_gms_internal_zzbm;
        this.zzIT = new zzeg(context.getApplicationContext() != null ? context.getApplicationContext() : context, new VersionInfoParcel(8487000, 8487000, true), com_google_android_gms_internal_zzbm.zzdp(), new zzb<zzed>(this) {
            final /* synthetic */ zzhd zzJe;

            {
                this.zzJe = r1;
            }

            public void zza(zzed com_google_android_gms_internal_zzed) {
                com_google_android_gms_internal_zzed.zza("/log", zzde.zzzf);
            }

            public /* synthetic */ void zze(Object obj) {
                zza((zzed) obj);
            }
        }, new zzc());
    }

    private static AdResponseParcel zza(Context context, zzeg com_google_android_gms_internal_zzeg, zzbm com_google_android_gms_internal_zzbm, zzhc com_google_android_gms_internal_zzhc, AdRequestInfoParcel adRequestInfoParcel) {
        Bundle bundle;
        Future future;
        Throwable e;
        com.google.android.gms.ads.internal.util.client.zzb.zzaI("Starting ad request from service.");
        zzbt.initialize(context);
        final zzcb com_google_android_gms_internal_zzcb = new zzcb(((Boolean) zzbt.zzwg.get()).booleanValue(), "load_ad", adRequestInfoParcel.zzrp.zzuh);
        if (adRequestInfoParcel.versionCode > 10 && adRequestInfoParcel.zzHL != -1) {
            com_google_android_gms_internal_zzcb.zza(com_google_android_gms_internal_zzcb.zzb(adRequestInfoParcel.zzHL), "cts");
        }
        zzbz zzdB = com_google_android_gms_internal_zzcb.zzdB();
        Bundle bundle2 = (adRequestInfoParcel.versionCode < 4 || adRequestInfoParcel.zzHA == null) ? null : adRequestInfoParcel.zzHA;
        if (!((Boolean) zzbt.zzwp.get()).booleanValue() || com_google_android_gms_internal_zzhc.zzIP == null) {
            bundle = bundle2;
            future = null;
        } else {
            if (bundle2 == null && ((Boolean) zzbt.zzwq.get()).booleanValue()) {
                zzin.v("contentInfo is not present, but we'll still launch the app index task");
                bundle = new Bundle();
            } else {
                bundle = bundle2;
            }
            if (bundle != null) {
                final zzhc com_google_android_gms_internal_zzhc2 = com_google_android_gms_internal_zzhc;
                final Context context2 = context;
                final AdRequestInfoParcel adRequestInfoParcel2 = adRequestInfoParcel;
                future = zziq.zza(new Callable<Void>() {
                    public final /* synthetic */ Object call() throws Exception {
                        return zzdt();
                    }

                    public final Void zzdt() throws Exception {
                        com_google_android_gms_internal_zzhc2.zzIP.zza(context2, adRequestInfoParcel2.zzHu.packageName, bundle);
                        return null;
                    }
                });
            } else {
                future = null;
            }
        }
        com_google_android_gms_internal_zzhc.zzIK.zzex();
        zzhj zzE = zzr.zzbI().zzE(context);
        if (zzE.zzKc == -1) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaI("Device is offline.");
            return new AdResponseParcel(2);
        }
        String uuid = adRequestInfoParcel.versionCode >= 7 ? adRequestInfoParcel.zzHI : UUID.randomUUID().toString();
        final zzhf com_google_android_gms_internal_zzhf = new zzhf(uuid, adRequestInfoParcel.applicationInfo.packageName);
        if (adRequestInfoParcel.zzHt.extras != null) {
            String string = adRequestInfoParcel.zzHt.extras.getString("_ad");
            if (string != null) {
                return zzhe.zza(context, adRequestInfoParcel, string);
            }
        }
        Location zzd = com_google_android_gms_internal_zzhc.zzIK.zzd(250);
        String token = com_google_android_gms_internal_zzhc.zzIL.getToken(context, adRequestInfoParcel.zzrj, adRequestInfoParcel.zzHu.packageName);
        List zza = com_google_android_gms_internal_zzhc.zzII.zza(adRequestInfoParcel);
        String zzf = com_google_android_gms_internal_zzhc.zzIM.zzf(adRequestInfoParcel);
        zzhn.zza zzF = com_google_android_gms_internal_zzhc.zzIN.zzF(context);
        if (future != null) {
            try {
                zzin.v("Waiting for app index fetching task.");
                future.get(((Long) zzbt.zzwr.get()).longValue(), TimeUnit.MILLISECONDS);
                zzin.v("App index fetching task completed.");
            } catch (ExecutionException e2) {
                e = e2;
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to fetch app index signal", e);
            } catch (InterruptedException e3) {
                e = e3;
                com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to fetch app index signal", e);
            } catch (TimeoutException e4) {
                com.google.android.gms.ads.internal.util.client.zzb.zzaI("Timed out waiting for app index fetching task");
            }
        }
        JSONObject zza2 = zzhe.zza(context, adRequestInfoParcel, zzE, zzF, zzd, com_google_android_gms_internal_zzbm, token, zzf, zza, bundle);
        if (adRequestInfoParcel.versionCode < 7) {
            try {
                zza2.put("request_id", uuid);
            } catch (JSONException e5) {
            }
        }
        if (zza2 == null) {
            return new AdResponseParcel(0);
        }
        final String jSONObject = zza2.toString();
        com_google_android_gms_internal_zzcb.zza(zzdB, "arc");
        final zzbz zzdB2 = com_google_android_gms_internal_zzcb.zzdB();
        if (((Boolean) zzbt.zzvC.get()).booleanValue()) {
            final zzeg com_google_android_gms_internal_zzeg2 = com_google_android_gms_internal_zzeg;
            final zzhf com_google_android_gms_internal_zzhf2 = com_google_android_gms_internal_zzhf;
            final zzcb com_google_android_gms_internal_zzcb2 = com_google_android_gms_internal_zzcb;
            zzir.zzMc.post(new Runnable() {
                public final void run() {
                    zzd zzer = com_google_android_gms_internal_zzeg2.zzer();
                    com_google_android_gms_internal_zzhf2.zzb(zzer);
                    com_google_android_gms_internal_zzcb2.zza(zzdB2, "rwc");
                    final zzbz zzdB = com_google_android_gms_internal_zzcb2.zzdB();
                    zzer.zza(new zzji.zzc<zzeh>(this) {
                        final /* synthetic */ AnonymousClass2 zzJc;

                        public void zzd(zzeh com_google_android_gms_internal_zzeh) {
                            com_google_android_gms_internal_zzcb2.zza(zzdB, "jsf");
                            com_google_android_gms_internal_zzcb2.zzdC();
                            com_google_android_gms_internal_zzeh.zza("/invalidRequest", com_google_android_gms_internal_zzhf2.zzJk);
                            com_google_android_gms_internal_zzeh.zza("/loadAdURL", com_google_android_gms_internal_zzhf2.zzJl);
                            try {
                                com_google_android_gms_internal_zzeh.zze("AFMA_buildAdURL", jSONObject);
                            } catch (Throwable e) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error requesting an ad url", e);
                            }
                        }

                        public /* synthetic */ void zze(Object obj) {
                            zzd((zzeh) obj);
                        }
                    }, new zzji.zza(this) {
                        final /* synthetic */ AnonymousClass2 zzJc;

                        {
                            this.zzJc = r1;
                        }

                        public void run() {
                        }
                    });
                }
            });
        } else {
            final Context context3 = context;
            final AdRequestInfoParcel adRequestInfoParcel3 = adRequestInfoParcel;
            final zzbz com_google_android_gms_internal_zzbz = zzdB2;
            final String str = jSONObject;
            final zzbm com_google_android_gms_internal_zzbm2 = com_google_android_gms_internal_zzbm;
            zzir.zzMc.post(new Runnable() {
                public final void run() {
                    zzjp zza = zzr.zzbD().zza(context3, new AdSizeParcel(), false, false, null, adRequestInfoParcel3.zzrl);
                    if (zzr.zzbF().zzhi()) {
                        zza.clearCache(true);
                    }
                    zza.getWebView().setWillNotDraw(true);
                    com_google_android_gms_internal_zzhf.zzh(zza);
                    com_google_android_gms_internal_zzcb.zza(com_google_android_gms_internal_zzbz, "rwc");
                    zzjq.zza zzb = zzhd.zza(str, com_google_android_gms_internal_zzcb, com_google_android_gms_internal_zzcb.zzdB());
                    zzjq zzhU = zza.zzhU();
                    zzhU.zza("/invalidRequest", com_google_android_gms_internal_zzhf.zzJk);
                    zzhU.zza("/loadAdURL", com_google_android_gms_internal_zzhf.zzJl);
                    zzhU.zza("/log", zzde.zzzf);
                    zzhU.zza(zzb);
                    com.google.android.gms.ads.internal.util.client.zzb.zzaI("Loading the JS library.");
                    zza.loadUrl(com_google_android_gms_internal_zzbm2.zzdp());
                }
            });
        }
        AdResponseParcel adResponseParcel;
        try {
            zzhi com_google_android_gms_internal_zzhi = (zzhi) com_google_android_gms_internal_zzhf.zzgC().get(10, TimeUnit.SECONDS);
            if (com_google_android_gms_internal_zzhi == null) {
                adResponseParcel = new AdResponseParcel(0);
                return adResponseParcel;
            } else if (com_google_android_gms_internal_zzhi.getErrorCode() != -2) {
                adResponseParcel = new AdResponseParcel(com_google_android_gms_internal_zzhi.getErrorCode());
                com_google_android_gms_internal_zzhc2 = com_google_android_gms_internal_zzhc;
                context2 = context;
                adRequestInfoParcel2 = adRequestInfoParcel;
                zzir.zzMc.post(new Runnable() {
                    public final void run() {
                        com_google_android_gms_internal_zzhc2.zzIJ.zza(context2, com_google_android_gms_internal_zzhf, adRequestInfoParcel2.zzrl);
                    }
                });
                return adResponseParcel;
            } else {
                if (com_google_android_gms_internal_zzcb.zzdE() != null) {
                    com_google_android_gms_internal_zzcb.zza(com_google_android_gms_internal_zzcb.zzdE(), "rur");
                }
                String str2 = null;
                if (com_google_android_gms_internal_zzhi.zzgG()) {
                    str2 = com_google_android_gms_internal_zzhc.zzIH.zzaz(adRequestInfoParcel.zzHu.packageName);
                }
                adResponseParcel = zza(adRequestInfoParcel, context, adRequestInfoParcel.zzrl.afmaVersion, com_google_android_gms_internal_zzhi.getUrl(), str2, com_google_android_gms_internal_zzhi.zzgH() ? token : null, com_google_android_gms_internal_zzhi, com_google_android_gms_internal_zzcb, com_google_android_gms_internal_zzhc);
                if (adResponseParcel.zzIf == 1) {
                    com_google_android_gms_internal_zzhc.zzIL.clearToken(context, adRequestInfoParcel.zzHu.packageName);
                }
                com_google_android_gms_internal_zzcb.zza(zzdB, "tts");
                adResponseParcel.zzIh = com_google_android_gms_internal_zzcb.zzdD();
                com_google_android_gms_internal_zzhc2 = com_google_android_gms_internal_zzhc;
                context2 = context;
                adRequestInfoParcel2 = adRequestInfoParcel;
                zzir.zzMc.post(/* anonymous class already generated */);
                return adResponseParcel;
            }
        } catch (Exception e6) {
            adResponseParcel = new AdResponseParcel(0);
            return adResponseParcel;
        } finally {
            com_google_android_gms_internal_zzhc2 = com_google_android_gms_internal_zzhc;
            context2 = context;
            adRequestInfoParcel2 = adRequestInfoParcel;
            zzir.zzMc.post(/* anonymous class already generated */);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.ads.internal.request.AdResponseParcel zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel r13, android.content.Context r14, java.lang.String r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, com.google.android.gms.internal.zzhi r19, com.google.android.gms.internal.zzcb r20, com.google.android.gms.internal.zzhc r21) {
        /*
        if (r20 == 0) goto L_0x00ec;
    L_0x0002:
        r2 = r20.zzdB();
        r6 = r2;
    L_0x0007:
        r7 = new com.google.android.gms.internal.zzhg;	 Catch:{ IOException -> 0x0107 }
        r7.<init>(r13);	 Catch:{ IOException -> 0x0107 }
        r2 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x0107 }
        r3 = "AdRequestServiceImpl: Sending request: ";
        r2.<init>(r3);	 Catch:{ IOException -> 0x0107 }
        r0 = r16;
        r2 = r2.append(r0);	 Catch:{ IOException -> 0x0107 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x0107 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaI(r2);	 Catch:{ IOException -> 0x0107 }
        r3 = new java.net.URL;	 Catch:{ IOException -> 0x0107 }
        r0 = r16;
        r3.<init>(r0);	 Catch:{ IOException -> 0x0107 }
        r2 = com.google.android.gms.ads.internal.zzr.zzbG();	 Catch:{ IOException -> 0x0107 }
        r8 = r2.elapsedRealtime();	 Catch:{ IOException -> 0x0107 }
        r2 = 0;
        r4 = r2;
        r5 = r3;
    L_0x0032:
        if (r21 == 0) goto L_0x003b;
    L_0x0034:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgJ();	 Catch:{ IOException -> 0x0107 }
    L_0x003b:
        r2 = r5.openConnection();	 Catch:{ IOException -> 0x0107 }
        r2 = (java.net.HttpURLConnection) r2;	 Catch:{ IOException -> 0x0107 }
        r3 = com.google.android.gms.ads.internal.zzr.zzbC();	 Catch:{ all -> 0x00f9 }
        r10 = 0;
        r3.zza(r14, r15, r10, r2);	 Catch:{ all -> 0x00f9 }
        r3 = android.text.TextUtils.isEmpty(r17);	 Catch:{ all -> 0x00f9 }
        if (r3 != 0) goto L_0x0056;
    L_0x004f:
        r3 = "x-afma-drt-cookie";
        r0 = r17;
        r2.addRequestProperty(r3, r0);	 Catch:{ all -> 0x00f9 }
    L_0x0056:
        r3 = android.text.TextUtils.isEmpty(r18);	 Catch:{ all -> 0x00f9 }
        if (r3 != 0) goto L_0x0072;
    L_0x005c:
        r3 = "Authorization";
        r10 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f9 }
        r11 = "Bearer ";
        r10.<init>(r11);	 Catch:{ all -> 0x00f9 }
        r0 = r18;
        r10 = r10.append(r0);	 Catch:{ all -> 0x00f9 }
        r10 = r10.toString();	 Catch:{ all -> 0x00f9 }
        r2.addRequestProperty(r3, r10);	 Catch:{ all -> 0x00f9 }
    L_0x0072:
        if (r19 == 0) goto L_0x009d;
    L_0x0074:
        r3 = r19.zzgF();	 Catch:{ all -> 0x00f9 }
        r3 = android.text.TextUtils.isEmpty(r3);	 Catch:{ all -> 0x00f9 }
        if (r3 != 0) goto L_0x009d;
    L_0x007e:
        r3 = 1;
        r2.setDoOutput(r3);	 Catch:{ all -> 0x00f9 }
        r3 = r19.zzgF();	 Catch:{ all -> 0x00f9 }
        r10 = r3.getBytes();	 Catch:{ all -> 0x00f9 }
        r3 = r10.length;	 Catch:{ all -> 0x00f9 }
        r2.setFixedLengthStreamingMode(r3);	 Catch:{ all -> 0x00f9 }
        r3 = new java.io.BufferedOutputStream;	 Catch:{ all -> 0x00f0 }
        r11 = r2.getOutputStream();	 Catch:{ all -> 0x00f0 }
        r3.<init>(r11);	 Catch:{ all -> 0x00f0 }
        r3.write(r10);	 Catch:{ all -> 0x01c9 }
        com.google.android.gms.internal.zzna.zzb(r3);	 Catch:{ all -> 0x00f9 }
    L_0x009d:
        r3 = r2.getResponseCode();	 Catch:{ all -> 0x00f9 }
        r10 = r2.getHeaderFields();	 Catch:{ all -> 0x00f9 }
        r11 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        if (r3 < r11) goto L_0x012e;
    L_0x00a9:
        r11 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r3 >= r11) goto L_0x012e;
    L_0x00ad:
        r5 = r5.toString();	 Catch:{ all -> 0x00f9 }
        r4 = new java.io.InputStreamReader;	 Catch:{ all -> 0x0125 }
        r11 = r2.getInputStream();	 Catch:{ all -> 0x0125 }
        r4.<init>(r11);	 Catch:{ all -> 0x0125 }
        r11 = com.google.android.gms.ads.internal.zzr.zzbC();	 Catch:{ all -> 0x01c3 }
        r11 = r11.zza(r4);	 Catch:{ all -> 0x01c3 }
        com.google.android.gms.internal.zzna.zzb(r4);	 Catch:{ all -> 0x00f9 }
        zza(r5, r10, r11, r3);	 Catch:{ all -> 0x00f9 }
        r7.zzb(r5, r10, r11);	 Catch:{ all -> 0x00f9 }
        if (r20 == 0) goto L_0x00da;
    L_0x00cd:
        r3 = 1;
        r3 = new java.lang.String[r3];	 Catch:{ all -> 0x00f9 }
        r4 = 0;
        r5 = "ufe";
        r3[r4] = r5;	 Catch:{ all -> 0x00f9 }
        r0 = r20;
        r0.zza(r6, r3);	 Catch:{ all -> 0x00f9 }
    L_0x00da:
        r3 = r7.zzj(r8);	 Catch:{ all -> 0x00f9 }
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x00ea;
    L_0x00e3:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
    L_0x00ea:
        r2 = r3;
    L_0x00eb:
        return r2;
    L_0x00ec:
        r2 = 0;
        r6 = r2;
        goto L_0x0007;
    L_0x00f0:
        r3 = move-exception;
        r4 = 0;
        r12 = r4;
        r4 = r3;
        r3 = r12;
    L_0x00f5:
        com.google.android.gms.internal.zzna.zzb(r3);	 Catch:{ all -> 0x00f9 }
        throw r4;	 Catch:{ all -> 0x00f9 }
    L_0x00f9:
        r3 = move-exception;
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x0106;
    L_0x00ff:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
    L_0x0106:
        throw r3;	 Catch:{ IOException -> 0x0107 }
    L_0x0107:
        r2 = move-exception;
        r3 = new java.lang.StringBuilder;
        r4 = "Error while connecting to ad server: ";
        r3.<init>(r4);
        r2 = r2.getMessage();
        r2 = r3.append(r2);
        r2 = r2.toString();
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r2);
        r2 = new com.google.android.gms.ads.internal.request.AdResponseParcel;
        r3 = 2;
        r2.<init>(r3);
        goto L_0x00eb;
    L_0x0125:
        r3 = move-exception;
        r4 = 0;
        r12 = r4;
        r4 = r3;
        r3 = r12;
    L_0x012a:
        com.google.android.gms.internal.zzna.zzb(r3);	 Catch:{ all -> 0x00f9 }
        throw r4;	 Catch:{ all -> 0x00f9 }
    L_0x012e:
        r5 = r5.toString();	 Catch:{ all -> 0x00f9 }
        r11 = 0;
        zza(r5, r10, r11, r3);	 Catch:{ all -> 0x00f9 }
        r5 = 300; // 0x12c float:4.2E-43 double:1.48E-321;
        if (r3 < r5) goto L_0x0187;
    L_0x013a:
        r5 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        if (r3 >= r5) goto L_0x0187;
    L_0x013e:
        r3 = "Location";
        r3 = r2.getHeaderField(r3);	 Catch:{ all -> 0x00f9 }
        r5 = android.text.TextUtils.isEmpty(r3);	 Catch:{ all -> 0x00f9 }
        if (r5 == 0) goto L_0x0163;
    L_0x014a:
        r3 = "No location header to follow redirect.";
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r3);	 Catch:{ all -> 0x00f9 }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x00f9 }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x00f9 }
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x0161;
    L_0x015a:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
    L_0x0161:
        r2 = r3;
        goto L_0x00eb;
    L_0x0163:
        r5 = new java.net.URL;	 Catch:{ all -> 0x00f9 }
        r5.<init>(r3);	 Catch:{ all -> 0x00f9 }
        r3 = r4 + 1;
        r4 = 5;
        if (r3 <= r4) goto L_0x01ae;
    L_0x016d:
        r3 = "Too many redirects.";
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r3);	 Catch:{ all -> 0x00f9 }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x00f9 }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x00f9 }
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x0184;
    L_0x017d:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
    L_0x0184:
        r2 = r3;
        goto L_0x00eb;
    L_0x0187:
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x00f9 }
        r5 = "Received error HTTP response code: ";
        r4.<init>(r5);	 Catch:{ all -> 0x00f9 }
        r3 = r4.append(r3);	 Catch:{ all -> 0x00f9 }
        r3 = r3.toString();	 Catch:{ all -> 0x00f9 }
        com.google.android.gms.ads.internal.util.client.zzb.zzaK(r3);	 Catch:{ all -> 0x00f9 }
        r3 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x00f9 }
        r4 = 0;
        r3.<init>(r4);	 Catch:{ all -> 0x00f9 }
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x01ab;
    L_0x01a4:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
    L_0x01ab:
        r2 = r3;
        goto L_0x00eb;
    L_0x01ae:
        r7.zzj(r10);	 Catch:{ all -> 0x00f9 }
        r2.disconnect();	 Catch:{ IOException -> 0x0107 }
        if (r21 == 0) goto L_0x01c0;
    L_0x01b6:
        r0 = r21;
        r2 = r0.zzIO;	 Catch:{ IOException -> 0x0107 }
        r2.zzgK();	 Catch:{ IOException -> 0x0107 }
        r4 = r3;
        goto L_0x0032;
    L_0x01c0:
        r4 = r3;
        goto L_0x0032;
    L_0x01c3:
        r3 = move-exception;
        r12 = r4;
        r4 = r3;
        r3 = r12;
        goto L_0x012a;
    L_0x01c9:
        r4 = move-exception;
        goto L_0x00f5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhd.zza(com.google.android.gms.ads.internal.request.AdRequestInfoParcel, android.content.Context, java.lang.String, java.lang.String, java.lang.String, java.lang.String, com.google.android.gms.internal.zzhi, com.google.android.gms.internal.zzcb, com.google.android.gms.internal.zzhc):com.google.android.gms.ads.internal.request.AdResponseParcel");
    }

    public static zzhd zza(Context context, zzbm com_google_android_gms_internal_zzbm, zzhc com_google_android_gms_internal_zzhc) {
        zzhd com_google_android_gms_internal_zzhd;
        synchronized (zzqy) {
            if (zzIQ == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                zzIQ = new zzhd(context, com_google_android_gms_internal_zzbm, com_google_android_gms_internal_zzhc);
            }
            com_google_android_gms_internal_zzhd = zzIQ;
        }
        return com_google_android_gms_internal_zzhd;
    }

    private static zzjq.zza zza(final String str, final zzcb com_google_android_gms_internal_zzcb, final zzbz com_google_android_gms_internal_zzbz) {
        return new zzjq.zza() {
            public final void zza(zzjp com_google_android_gms_internal_zzjp, boolean z) {
                com_google_android_gms_internal_zzcb.zza(com_google_android_gms_internal_zzbz, "jsf");
                com_google_android_gms_internal_zzcb.zzdC();
                com_google_android_gms_internal_zzjp.zze("AFMA_buildAdURL", str);
            }
        };
    }

    private static void zza(String str, Map<String, List<String>> map, String str2, int i) {
        if (com.google.android.gms.ads.internal.util.client.zzb.zzQ(2)) {
            zzin.v("Http Response: {\n  URL:\n    " + str + "\n  Headers:");
            if (map != null) {
                for (String str3 : map.keySet()) {
                    zzin.v("    " + str3 + ":");
                    for (String str32 : (List) map.get(str32)) {
                        zzin.v("      " + str32);
                    }
                }
            }
            zzin.v("  Body:");
            if (str2 != null) {
                for (int i2 = 0; i2 < Math.min(str2.length(), 100000); i2 += 1000) {
                    zzin.v(str2.substring(i2, Math.min(str2.length(), i2 + 1000)));
                }
            } else {
                zzin.v("    null");
            }
            zzin.v("  Response Code:\n    " + i + "\n}");
        }
    }

    public final void zza(final AdRequestInfoParcel adRequestInfoParcel, final zzk com_google_android_gms_ads_internal_request_zzk) {
        zzr.zzbF().zzb(this.mContext, adRequestInfoParcel.zzrl);
        zziq.zza(new Runnable(this) {
            final /* synthetic */ zzhd zzJe;

            public void run() {
                AdResponseParcel zzd;
                try {
                    zzd = this.zzJe.zzd(adRequestInfoParcel);
                } catch (Throwable e) {
                    zzr.zzbF().zzb(e, true);
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Could not fetch ad response due to an Exception.", e);
                    zzd = null;
                }
                if (zzd == null) {
                    zzd = new AdResponseParcel(0);
                }
                try {
                    com_google_android_gms_ads_internal_request_zzk.zzb(zzd);
                } catch (Throwable e2) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzd("Fail to forward ad response.", e2);
                }
            }
        });
    }

    public final AdResponseParcel zzd(AdRequestInfoParcel adRequestInfoParcel) {
        return zza(this.mContext, this.zzIT, this.zzIS, this.zzIR, adRequestInfoParcel);
    }
}
