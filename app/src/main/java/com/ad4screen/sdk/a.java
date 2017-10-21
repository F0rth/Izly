package com.ad4screen.sdk;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.widget.FrameLayout.LayoutParams;
import android.widget.Toast;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.IA4SService.Stub;
import com.ad4screen.sdk.a.c;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.b.b;
import com.ad4screen.sdk.common.b.m.l;
import com.ad4screen.sdk.common.f;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.external.shortcutbadger.ShortcutBadger;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.provider.A4SBeaconResolver;
import com.ad4screen.sdk.service.modules.inapp.e;
import com.ad4screen.sdk.service.modules.inapp.e.d;
import com.ad4screen.sdk.service.modules.inapp.e.g;
import com.ad4screen.sdk.service.modules.inapp.e.j;
import com.ad4screen.sdk.service.modules.inapp.e.k;
import com.ad4screen.sdk.service.modules.inapp.e.m;
import com.ad4screen.sdk.service.modules.inapp.e.n;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

final class a extends A4S {
    private static b q;
    private static boolean r = false;
    private static ArrayList<Callback<Inbox>> s;
    private static ArrayList<String> t;
    private static ArrayList<Callback<Inbox>> u;
    private static ArrayList<String> v;
    private static boolean w = false;
    private j A = new j(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(String str, int i, String str2, String str3, HashMap<String, String> hashMap) {
            this.a.a(str, str3, i, str2, (HashMap) hashMap);
        }
    };
    private k B = new k(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(String str, int i, String str2, boolean z, boolean z2) {
            this.a.a(str, z, i, str2, z2);
        }
    };
    Callback<InApp> a;
    Callback<InApp> b;
    Callback<InApp> c;
    Callback<InApp> d;
    int[] e;
    int[] f;
    int[] g;
    int[] h;
    c.b i = new c.b(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(final Bundle bundle) {
            this.a.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "openedPush") {
                final /* synthetic */ AnonymousClass56 b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.openedPush(bundle);
                }
            });
        }
    };
    com.ad4screen.sdk.a.c.a j = new com.ad4screen.sdk.a.c.a(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(final Bundle bundle) {
            this.a.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "closedPush") {
                final /* synthetic */ AnonymousClass2 b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.closedPush(bundle);
                }
            });
        }
    };
    private final Context k;
    private final com.ad4screen.sdk.a.b l;
    private final com.ad4screen.sdk.a.a<IA4SService> m;
    private final Handler n;
    private String o;
    private String p;
    private String x;
    private n y = new n(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(String str, int i, String str2, boolean z) {
            this.a.a(str, i, str2, z);
        }
    };
    private m z = new m(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void a(String str, int i, String str2, HashMap<String, String> hashMap, boolean z) {
            this.a.a(str, i, str2, (HashMap) hashMap, z);
        }
    };

    protected a(Context context, boolean z) {
        if (z) {
            this.k = context.getApplicationContext();
            this.n = new Handler(Looper.getMainLooper());
            com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.k);
            Log.setEnabled(a.E());
            if (a.H()) {
                Log.error("**************************************/!\\**************************************");
                Log.error("/!\\ Unsecure push is Enabled and must be DISABLED in production environment /!\\");
                Log.error("**************************************/!\\**************************************");
                Toast.makeText(this.k, "Unsecure Push is ENABLED and must be DISABLED in production", 1).show();
            }
            if (a.E()) {
                Log.info("A4S SDK VERSION : A3.4.1 (Build : 5df599ae361a93e2689c21e734ed1daf83a444f9)");
                Log.error("***********************************/!\\***********************************");
                Log.error("/!\\ Logging is Enabled and must be DISABLED in production environment /!\\");
                Log.error("***********************************/!\\***********************************");
                if (!a.h()) {
                    Toast.makeText(this.k, "A4S Logging is ENABLED and must be DISABLED in production for app " + this.k.getPackageName(), 1).show();
                }
            }
            f.a(this.k);
            com.ad4screen.sdk.d.f.a().a(e.f.class, this.z);
            com.ad4screen.sdk.d.f.a().a(e.c.class, this.A);
            com.ad4screen.sdk.d.f.a().a(d.class, this.B);
            com.ad4screen.sdk.d.f.a().a(g.class, this.y);
            com.ad4screen.sdk.d.f.a().a(c.d.class, this.i);
            com.ad4screen.sdk.d.f.a().a(c.c.class, this.j);
            this.l = com.ad4screen.sdk.a.b.a(this.k);
            this.m = new com.ad4screen.sdk.a.a<IA4SService>(this, this.k) {
                final /* synthetic */ a a;

                protected IA4SService a(IBinder iBinder) {
                    return Stub.asInterface(iBinder);
                }

                protected void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.setClientCallback(this.a.l.a());
                }

                protected /* synthetic */ Object b(IBinder iBinder) {
                    return a(iBinder);
                }
            };
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "clientStarted") {
                final /* synthetic */ a a;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.clientStarted();
                }
            });
            return;
        }
        this.k = context.getApplicationContext();
        Log.setEnabled(com.ad4screen.sdk.d.b.a(this.k).E());
        Log.info("Initializing A4S light mode");
        this.n = new Handler(Looper.getMainLooper());
        this.l = com.ad4screen.sdk.a.b.a(this.k);
        this.m = new com.ad4screen.sdk.a.a<IA4SService>(this, this.k) {
            final /* synthetic */ a a;

            protected IA4SService a(IBinder iBinder) {
                return Stub.asInterface(iBinder);
            }

            protected void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setClientCallback(this.a.l.a());
            }

            protected /* synthetic */ Object b(IBinder iBinder) {
                return a(iBinder);
            }
        };
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setDoNotTrackEnabled") {
            final /* synthetic */ a a;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setDoNotTrack(true, true, false, new com.ad4screen.sdk.d.a(this) {
                    final /* synthetic */ AnonymousClass34 a;

                    {
                        this.a = r1;
                    }

                    public void a(boolean z) throws RemoteException {
                        this.a.a.c();
                    }
                });
            }
        });
    }

    private void a(Intent intent) {
        Uri data = intent.getData();
        if (data != null) {
            try {
                this.x = data.getQueryParameter("bma4ssrc");
            } catch (Throwable e) {
                Log.internal("Can't get uri parameters", e);
            }
        }
        if (!(intent.getExtras() == null || intent.getExtras().getBundle(Constants.EXTRA_GCM_PAYLOAD) == null)) {
            com.ad4screen.sdk.service.modules.push.a.a a = com.ad4screen.sdk.service.modules.push.a.a.a(intent.getExtras().getBundle(Constants.EXTRA_GCM_PAYLOAD));
            if (a != null) {
                this.x = a.z;
            }
        }
        if (this.x != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setSource") {
                final /* synthetic */ a a;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.setSource(this.a.x);
                }
            });
        }
    }

    private void a(final String str, int i, String str2, HashMap<String, String> hashMap, boolean z) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "displayedInApp") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.onInAppDisplayed(str);
            }
        });
        if (this.b != null && !z) {
            if (this.f == null || this.f.length == 0) {
                this.b.onResult(new InApp(str, i, str2, hashMap));
                return;
            }
            for (int i2 : this.f) {
                if (i == i2) {
                    this.b.onResult(new InApp(str, i, str2, hashMap));
                    return;
                }
            }
        }
    }

    private void a(final String str, final int i, String str2, boolean z) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "InAppReady") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.onInAppReady(str, i);
            }
        });
        if (this.a != null && !z) {
            if (this.e == null || this.e.length == 0) {
                this.a.onResult(new InApp(str, i, str2, null));
                return;
            }
            for (int i2 : this.e) {
                if (i == i2) {
                    this.a.onResult(new InApp(str, i, str2, null));
                    return;
                }
            }
        }
    }

    private void a(final String str, final String str2, int i, String str3, HashMap<String, String> hashMap) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "clickedInApp") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.onInAppClicked(str, str2);
            }
        });
        if (this.d == null) {
            return;
        }
        if (this.h == null || this.h.length == 0) {
            this.d.onResult(new InApp(str, i, str3, hashMap));
            return;
        }
        for (int i2 : this.h) {
            if (i == i2) {
                this.b.onResult(new InApp(str, i, str3, hashMap));
                return;
            }
        }
    }

    private void a(final String str, final boolean z, int i, String str2, boolean z2) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "closedInApp") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.onInAppClosed(str, z);
            }
        });
        if (this.c != null && !z2) {
            if (this.g == null || this.g.length == 0) {
                this.c.onResult(new InApp(str, i, str2, null));
                return;
            }
            for (int i2 : this.g) {
                if (i == i2) {
                    this.c.onResult(new InApp(str, i, str2, null));
                    return;
                }
            }
        }
    }

    protected final void a() {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "interstitialDisplayed") {
            final /* synthetic */ a a;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.interstitialDisplayed();
            }
        });
    }

    protected final void a(int i, Callback<Inbox> callback) {
        if (v == null) {
            v = new ArrayList();
        }
        if (u == null) {
            u = new ArrayList();
        }
        if (!v.contains(q.a[i].a)) {
            v.add(q.a[i].a);
        }
        u.add(callback);
        w = true;
    }

    protected final void a(int i, final Callback<Inbox> callback, boolean z) {
        int i2 = 0;
        if (q == null) {
            Log.debug("Please use method getInbox first in order to retrieve some messages.");
        } else if (callback == null && !z) {
        } else {
            if (r || w) {
                a(i, (Callback) callback);
                return;
            }
            int i3;
            if (s == null) {
                s = new ArrayList();
            } else {
                s.clear();
            }
            if (z) {
                s = new ArrayList(u);
                u.clear();
                u = null;
            } else {
                s.add(callback);
            }
            if (t == null) {
                t = new ArrayList();
            } else {
                t.clear();
            }
            if (z) {
                t = new ArrayList(v);
                v.clear();
                v = null;
            } else {
                t.add(q.a[i].a);
            }
            int i4 = 0;
            for (i3 = i + 1; i3 < q.a.length; i3++) {
                if (!q.a[i3].n && i4 < 15) {
                    t.add(q.a[i3].a);
                    i4++;
                }
            }
            i4 = 0;
            for (i3 = i - 1; i3 > 0; i3--) {
                if (!q.a[i3].n && i4 < 15) {
                    t.add(q.a[i3].a);
                    i4++;
                }
            }
            r = true;
            i3 = t.size();
            final String[] strArr = new String[i3];
            while (i2 < i3) {
                strArr[i2] = (String) t.get(i2);
                i2++;
            }
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getMessages") {
                final /* synthetic */ a c;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getMessagesDetails(strArr, new com.ad4screen.sdk.h.a(this) {
                        final /* synthetic */ AnonymousClass41 a;

                        {
                            this.a = r1;
                        }

                        public void a() throws RemoteException {
                            a.r = false;
                            this.a.c.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    if (callback != null) {
                                        callback.onError(0, "An error occured while downloading messages");
                                    }
                                }
                            });
                        }

                        public void a(com.ad4screen.sdk.b.c[] cVarArr) throws RemoteException {
                            a.r = false;
                            for (com.ad4screen.sdk.b.c cVar : cVarArr) {
                                for (int i = 0; i < a.q.a.length; i++) {
                                    if (a.q.a[i].a.equals(cVar.a)) {
                                        a.q.a[i] = cVar;
                                    }
                                }
                            }
                            this.a.c.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void run() {
                                    int i;
                                    int i2 = 0;
                                    Inbox a = i.a(this.a.a.c.k, a.q);
                                    for (i = 0; i < a.s.size(); i++) {
                                        ((Callback) a.s.get(i)).onResult(a);
                                    }
                                    if (a.w) {
                                        a.w = false;
                                        int i3 = 0;
                                        for (i = 0; i < a.v.size(); i++) {
                                            if (!a.t.contains(a.v.get(i))) {
                                                i3 = true;
                                            }
                                        }
                                        if (i3 != 0) {
                                            this.a.a.c.a(0, null, true);
                                            return;
                                        }
                                        while (i2 < a.u.size()) {
                                            ((Callback) a.u.get(i2)).onResult(a);
                                            i2++;
                                        }
                                        a.u.clear();
                                        a.v.clear();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    protected final void a(Context context, final boolean z, final boolean z2) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setDoNotTrackEnabled") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setDoNotTrack(z, z2, true, new com.ad4screen.sdk.d.a(this) {
                    final /* synthetic */ AnonymousClass48 a;

                    {
                        this.a = r1;
                    }

                    public void a(boolean z) throws RemoteException {
                        if (z) {
                            this.a.c.c();
                        }
                    }
                });
            }
        });
    }

    protected final void a(final String str) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "clickMessageButton") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                for (int i = 0; i < b.b.q.length; i++) {
                    if (b.b.q[i].a.equals(str)) {
                        com.ad4screen.sdk.b.a aVar = b.b.q[i];
                        i.a(this.b.k, Constants.ACTION_CLICKED, b.b.q[i].f);
                        iA4SService.clickButtonMessage(aVar, b.b.a);
                        if (aVar.c != com.ad4screen.sdk.b.c.a.Url) {
                            i.a(this.b.n, this.b.k, this.b.l, aVar.c, aVar.d, aVar.b);
                        }
                    }
                }
            }
        });
    }

    protected final void a(final String str, final Callback<Message> callback) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "displayMessage") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                for (int i = 0; i < a.q.a.length; i++) {
                    if (a.q.a[i].a.equals(str)) {
                        b.b = a.q.a[i];
                        iA4SService.displayMessage(b.b);
                        this.c.n.post(new Runnable(this) {
                            final /* synthetic */ AnonymousClass42 a;

                            {
                                this.a = r1;
                            }

                            public void run() {
                                if (b.b.j == com.ad4screen.sdk.b.c.a.Text || b.b.j == com.ad4screen.sdk.b.c.a.Web) {
                                    i.a(this.a.c.k, Constants.ACTION_DISPLAYED, b.b.r);
                                    callback.onResult(i.a(b.b));
                                    return;
                                }
                                i.a(this.a.c.k, Constants.ACTION_CLICKED, b.b.r);
                                if (b.b.j != com.ad4screen.sdk.b.c.a.Url) {
                                    i.a(this.a.c.n, this.a.c.k, this.a.c.l, b.b.j, b.b.e, b.b.b);
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    protected final void a(String str, String str2, String[] strArr) {
        final String str3 = str;
        final String str4 = str2;
        final String[] strArr2 = strArr;
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "uploadFacebookProfile") {
            final /* synthetic */ a d;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.uploadFacebookProfile(str3, str4, strArr2);
            }
        });
    }

    protected final void b() {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "interstitialClosed") {
            final /* synthetic */ a a;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.interstitialClosed();
            }
        });
    }

    protected final void b(String str) {
        if (this.o == null || this.p == null) {
            Log.error("A4S|No action to perform");
        } else if (this.p.equals(str)) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "doAction") {
                final /* synthetic */ a a;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.doAction(this.a.o);
                }
            });
            com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(this.k);
            if ("activateInternalLogging".equals(this.o)) {
                Log.debug("A4S|Internal Logging is now enabled on " + a.p() + " (" + a.u() + ")");
                Log.setInternalLoggingEnabled(true);
            } else if ("disableInternalLogging".equals(this.o)) {
                Log.debug("A4S|Internal Logging is now disabled on " + a.p() + " (" + a.u() + ")");
                Log.setInternalLoggingEnabled(false);
            } else if ("activateLogging".equals(this.o)) {
                l.a(this.k);
                Log.setEnabled(true);
                Log.debug("A4S|Logging is now enabled on " + a.p() + " (" + a.u() + ")");
                Log.debug("A4S|App Version : " + a.t());
                Log.debug("A4S|Partner Id : " + a.l());
                Log.debug("A4S|SDK Version : " + a.i());
                a.a(true);
            } else if ("disableLogging".equals(this.o)) {
                Log.debug("A4S|Logging is now disabled on " + a.p() + " (" + a.u() + ")");
                Log.setEnabled(false);
                a.a(false);
            }
        } else {
            Log.error("A4S|Wrong confirmation");
        }
    }

    protected final void c() {
        this.m.a();
    }

    @SuppressLint({"TrulyRandom"})
    protected final void c(String str) {
        Throwable e;
        if (str != null) {
            try {
                Key generatePublic = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(com.ad4screen.sdk.common.b.m.c.a("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdvmLrVeu/wHpscTzjVh6Z61lUmvAGGHRKF+KRF9ZhfUvDrS/T4vxetFx4gRU2ofYVOoLFsFWPIzsZKL3G9bLQnsmGFsiqjAiOWUmm5TbozwGtISsB4OKMtM+lMoC44SIUWx1dpwh5N0F92gMRS4HJPmvhEAXEkvsAvH3cOUqsrwIDAQAB", 0)));
                Cipher instance = Cipher.getInstance("RSA");
                instance.init(1, generatePublic);
                this.o = str;
                this.p = h.b();
                Intent intent = new Intent("com.ad4screen.sdk.action.CONFIRM");
                intent.addCategory("com.ad4screen.sdk.intent.category.ACTION");
                intent.putExtra("confirmation", com.ad4screen.sdk.common.b.m.c.a(instance.doFinal(this.p.getBytes()), 0));
                this.k.sendBroadcast(intent);
            } catch (NoSuchAlgorithmException e2) {
                e = e2;
                Log.error("A4S|Can't send confirmation for this action", e);
            } catch (InvalidKeySpecException e3) {
                e = e3;
                Log.error("A4S|Can't send confirmation for this action", e);
            } catch (IllegalBlockSizeException e4) {
                e = e4;
                Log.error("A4S|Can't send confirmation for this action", e);
            } catch (BadPaddingException e5) {
                e = e5;
                Log.error("A4S|Can't send confirmation for this action", e);
            } catch (NoSuchPaddingException e6) {
                e = e6;
                Log.error("A4S|Can't send confirmation for this action", e);
            } catch (InvalidKeyException e7) {
                e = e7;
                Log.error("A4S|Can't send confirmation for this action", e);
            }
        }
    }

    protected final void d(final String str) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "trackReferrer") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.trackReferrer(str);
            }
        });
    }

    public final void displayInApp(final String str) {
        if (this.a == null) {
            Log.error("A4S|You should call setInAppReadyCallback before displayInApp");
        } else {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "displayInApp") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.displayInApp(str);
                }
            });
        }
    }

    public final void getA4SId(final Callback<String> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getA4SId") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getA4SId(new com.ad4screen.sdk.e.a(this) {
                        final /* synthetic */ AnonymousClass24 a;

                        {
                            this.a = r1;
                        }

                        public void a(final String str) throws RemoteException {
                            this.a.b.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    callback.onResult(str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public final String getAndroidId() {
        return i.b(this.k);
    }

    protected final ArrayList<Beacon> getBeacons() {
        return (ArrayList) new A4SBeaconResolver(this.k).getAllBeacons();
    }

    public final void getIDFV(final Callback<String> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getIDFV") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getIDFV(new com.ad4screen.sdk.e.a(this) {
                        final /* synthetic */ AnonymousClass25 a;

                        {
                            this.a = r1;
                        }

                        public void a(final String str) throws RemoteException {
                            this.a.b.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    callback.onResult(str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public final void getInbox(final Callback<Inbox> callback) {
        if (callback != null) {
            if (r) {
                Log.debug("A call to Inbox Webservice is in progress. Please wait for it to complete before calling again");
            }
            r = true;
            getA4SId(new Callback<String>(this) {
                final /* synthetic */ a b;

                public void a(String str) {
                    this.b.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getInbox") {
                        final /* synthetic */ AnonymousClass40 a;

                        public void a(IA4SService iA4SService) throws RemoteException {
                            iA4SService.getMessagesList(new com.ad4screen.sdk.h.a(this) {
                                final /* synthetic */ AnonymousClass1 a;

                                {
                                    this.a = r1;
                                }

                                public void a() throws RemoteException {
                                    a.r = false;
                                    this.a.a.b.n.post(new Runnable(this) {
                                        final /* synthetic */ AnonymousClass1 a;

                                        {
                                            this.a = r1;
                                        }

                                        public void run() {
                                            callback.onError(0, "An error occured while downloading messages");
                                        }
                                    });
                                }

                                public void a(com.ad4screen.sdk.b.c[] cVarArr) throws RemoteException {
                                    a.r = false;
                                    a.q = new b(cVarArr);
                                    final Inbox a = i.a(this.a.a.b.k, a.q);
                                    this.a.a.b.n.post(new Runnable(this) {
                                        final /* synthetic */ AnonymousClass1 b;

                                        public void run() {
                                            callback.onResult(a);
                                        }
                                    });
                                }
                            });
                        }
                    });
                }

                public void onError(int i, String str) {
                }

                public /* synthetic */ void onResult(Object obj) {
                    a((String) obj);
                }
            });
        }
    }

    public final void getListOfSubscriptions(final Callback<List<StaticList>> callback) {
        if (callback == null) {
            Log.error("callback parameter can't be null for getListOfSubscriptions() method");
        } else {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getListOfSubscriptions") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getListOfSubscriptions(new com.ad4screen.sdk.j.a(this) {
                        final /* synthetic */ AnonymousClass52 a;

                        {
                            this.a = r1;
                        }

                        public void a(final int i, final String str) throws RemoteException {
                            this.a.b.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 c;

                                public void run() {
                                    callback.onError(i, str);
                                }
                            });
                        }

                        public void a(final List<com.ad4screen.sdk.service.modules.k.c.e> list) throws RemoteException {
                            this.a.b.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    callback.onResult(k.a(list));
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public final void getPushToken(final Callback<String> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getPushToken") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getPushToken(new com.ad4screen.sdk.g.a(this) {
                        final /* synthetic */ AnonymousClass30 a;

                        {
                            this.a = r1;
                        }

                        public void a(final String str) throws RemoteException {
                            this.a.b.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    callback.onResult(str);
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    public final void getSubscriptionStatusForLists(final List<StaticList> list, final Callback<List<StaticList>> callback) {
        if (callback == null) {
            Log.error("callback parameter can't be null for getSubscriptionStatusForLists() method");
        } else if (list == null) {
            Log.error("staticLists parameter can't be null for getSubscriptionStatusForLists() method");
        } else {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "getSubscriptionStatusForLists") {
                final /* synthetic */ a c;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.getSubscriptionStatusForLists(k.b(list), new com.ad4screen.sdk.j.a(this) {
                        final /* synthetic */ AnonymousClass51 a;

                        {
                            this.a = r1;
                        }

                        public void a(final int i, final String str) throws RemoteException {
                            this.a.c.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 c;

                                public void run() {
                                    callback.onError(i, str);
                                }
                            });
                        }

                        public void a(final List<com.ad4screen.sdk.service.modules.k.c.e> list) throws RemoteException {
                            this.a.c.n.post(new Runnable(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void run() {
                                    callback.onResult(k.a(list));
                                }
                            });
                        }
                    });
                }
            });
        }
    }

    protected final void handleGeofencingMessage(final Bundle bundle) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "handleGeofencingMessage") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.handleGeofencingMessage(bundle);
            }
        });
    }

    protected final void handlePushMessage(final Bundle bundle) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "handlePushMessage") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.handlePushMessage(bundle);
            }
        });
    }

    public final void isGCMEnabled(Callback<Boolean> callback) {
        isPushEnabled(callback);
    }

    public final void isInAppDisplayLocked(final Callback<Boolean> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "isInAppDisplayLocked") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    callback.onResult(Boolean.valueOf(iA4SService.isInAppDisplayLocked()));
                }
            });
        }
    }

    public final void isPushEnabled(final Callback<Boolean> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "isPushEnabled") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    callback.onResult(Boolean.valueOf(iA4SService.isPushEnabled()));
                }
            });
        }
    }

    public final boolean isPushNotificationLocked() {
        return this.l.e();
    }

    public final void isRestrictedConnection(final Callback<Boolean> callback) {
        if (callback != null) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "isRestrictedConnection") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    callback.onResult(Boolean.valueOf(iA4SService.isRestrictedConnection()));
                }
            });
        }
    }

    public final void putState(final String str, final String str2) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "putState") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.putState(str, str2);
            }
        });
    }

    public final void refreshPushToken() {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "refreshPushToken") {
            final /* synthetic */ a a;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.refreshPushToken();
            }
        });
    }

    public final void resetOverlayPosition() {
        com.ad4screen.sdk.d.l.a(this.k).a(new LayoutParams(-1, -2, 80));
    }

    public final void sendGCMToken(String str) {
        sendPushToken(str);
    }

    public final void sendPushToken(String str) {
        if (str != null) {
            Bundle bundle = new Bundle();
            bundle.putString("registration_id", str);
            updatePushRegistration(bundle);
        }
    }

    public final void setGCMEnabled(boolean z) {
        setPushEnabled(z);
    }

    public final void setInAppClickedCallback(Callback<InApp> callback, int... iArr) {
        this.d = callback;
        this.h = iArr;
    }

    public final void setInAppClosedCallback(Callback<InApp> callback, int... iArr) {
        this.c = callback;
        this.g = iArr;
    }

    public final void setInAppDisplayLocked(final boolean z) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setInAppDisplayLocked") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setInAppDisplayLocked(z);
            }
        });
    }

    public final void setInAppDisplayedCallback(Callback<InApp> callback, int... iArr) {
        this.b = callback;
        this.f = iArr;
    }

    public final void setInAppReadyCallback(final boolean z, Callback<InApp> callback, final int... iArr) {
        this.a = callback;
        this.e = iArr;
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setInAppReadyCallback") {
            final /* synthetic */ a c;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setInAppReadyCallback(z, iArr);
            }
        });
    }

    public final void setIntent(Intent intent) {
        this.l.a(intent);
    }

    public final void setOverlayPosition(LayoutParams layoutParams) {
        com.ad4screen.sdk.d.l.a(this.k).a(layoutParams);
    }

    public final void setPushEnabled(final boolean z) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setPushEnabled") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setPushEnabled(z);
            }
        });
    }

    public final void setPushNotificationLocked(boolean z) {
        this.l.a(z);
        Log.debug("Push|Push display is now " + (z ? "" : "un") + "locked");
        if (!z) {
            this.l.b();
        }
    }

    public final void setRestrictedConnection(final boolean z) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setRestrictedConnection") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setRestrictedConnection(z);
            }
        });
    }

    public final void setView(final String str) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "setView") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.setView(str);
            }
        });
    }

    public final void startActivity(final Activity activity) {
        if (activity == null) {
            Log.error("A4S|Activity for startActivity can't be null");
            return;
        }
        com.ad4screen.sdk.common.c.a(activity);
        ShortcutBadger.removeCount(this.k);
        this.l.a(activity);
        Intent intent = activity.getIntent();
        if (intent != null) {
            a(intent);
        }
        this.l.c();
        this.l.b();
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "startActivity") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.startActivity(com.ad4screen.sdk.d.l.b(activity), com.ad4screen.sdk.d.l.a(activity), com.ad4screen.sdk.d.l.c(activity));
            }
        });
    }

    public final void stopActivity(final Activity activity) {
        if (activity == null) {
            Log.error("A4S|Activity for stopActivity can't be null");
            return;
        }
        this.l.b(activity);
        if (this.a != null) {
            setInAppReadyCallback(false, null, new int[0]);
        }
        if (this.b != null) {
            setInAppDisplayedCallback(null, new int[0]);
        }
        if (this.d != null) {
            setInAppClickedCallback(null, new int[0]);
        }
        if (this.c != null) {
            setInAppClosedCallback(null, new int[0]);
        }
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "stopActivity") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.stopActivity(com.ad4screen.sdk.d.l.a(activity));
            }
        });
    }

    public final void subscribeToLists(final List<StaticList> list) {
        if (list == null || list.isEmpty()) {
            Log.error("You can't send null or empty parameter to removeFromStaticList()");
        } else {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "subscribeToLists") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.subscribeToLists(k.b(list));
                }
            });
        }
    }

    public final void subscribeToLists(StaticList... staticListArr) {
        subscribeToLists(new ArrayList(Arrays.asList(staticListArr)));
    }

    public final void trackAddToCart(Cart cart) {
        if (cart == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        try {
            final Cart cart2 = (Cart) cart.clone();
            final Bundle bundle = new Bundle();
            bundle.putString("FBToken", com.ad4screen.sdk.common.c.c(this.k));
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "trackAddToCart") {
                final /* synthetic */ a c;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.trackAddToCart(cart2, bundle);
                }
            });
        } catch (Throwable e) {
            Log.error("Cannot properly clone Cart", e);
        }
    }

    public final void trackEvent(long j, String str, String... strArr) {
        int i = 1;
        if (str == null) {
            Log.warn("You need to provide a valid value for the event " + j);
            return;
        }
        String[] strArr2;
        final Bundle bundle = new Bundle();
        bundle.putString("FBToken", com.ad4screen.sdk.common.c.c(this.k));
        if (strArr == null || strArr.length == 0) {
            strArr2 = new String[1];
        } else {
            strArr2 = new String[(strArr.length + 1)];
            while (i <= strArr.length) {
                strArr2[i] = strArr[i - 1];
                i++;
            }
        }
        strArr2[0] = str;
        final long j2 = j;
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "trackEvent") {
            final /* synthetic */ a d;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.trackEvent(j2, strArr2, bundle);
            }
        });
    }

    public final void trackLead(Lead lead) {
        if (lead == null) {
            throw new IllegalArgumentException("Lead cannot be null");
        }
        try {
            final Lead lead2 = (Lead) lead.clone();
            final Bundle bundle = new Bundle();
            bundle.putString("FBToken", com.ad4screen.sdk.common.c.c(this.k));
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "trackLead") {
                final /* synthetic */ a c;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.trackLead(lead2, bundle);
                }
            });
        } catch (Throwable e) {
            Log.error("Cannot properly clone Lead", e);
        }
    }

    public final void trackPurchase(Purchase purchase) {
        if (purchase == null) {
            throw new IllegalArgumentException("Purchase cannot be null");
        }
        try {
            final Purchase purchase2 = (Purchase) purchase.clone();
            final Bundle bundle = new Bundle();
            bundle.putString("FBToken", com.ad4screen.sdk.common.c.c(this.k));
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "trackPurchase") {
                final /* synthetic */ a c;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.trackPurchase(purchase2, bundle);
                }
            });
        } catch (Throwable e) {
            Log.error("Cannot properly clone Purchase", e);
        }
    }

    protected final void triggerBeacons(final Bundle bundle) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "triggerBeacons") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.triggerBeacons(bundle);
            }
        });
    }

    public final void unsubscribeFromLists(final List<StaticList> list) {
        if (list == null || list.isEmpty()) {
            Log.error("You can't send null or empty parameter to removeFromStaticList()");
        } else {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "removeFromList") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.unsubscribeFromLists(k.b(list));
                }
            });
        }
    }

    public final void unsubscribeFromLists(StaticList... staticListArr) {
        unsubscribeFromLists(new ArrayList(Arrays.asList(staticListArr)));
    }

    public final void updateDeviceInfo(Bundle bundle) {
        if (bundle != null) {
            final Bundle bundle2 = new Bundle(bundle);
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "updateDeviceInfo") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    iA4SService.updateUserPreferences(bundle2, false);
                }
            });
        }
    }

    public final void updateGeolocation(final Location location) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "updateGeolocation") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.updateGeolocation(location);
            }
        });
    }

    public final void updateMessages(final Inbox inbox) {
        if (inbox != null && inbox.countMessages() != 0) {
            this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "updateMessages") {
                final /* synthetic */ a b;

                public void a(IA4SService iA4SService) throws RemoteException {
                    b e = a.q;
                    int i = 0;
                    while (i < e.a.length) {
                        if (inbox.a[i].isRead() != e.a[i].l || inbox.a[i].isArchived() != e.a[i].m) {
                            e.a[i].l = inbox.a[i].isRead();
                            e.a[i].m = inbox.a[i].isArchived();
                            e.a[i].o = true;
                        }
                        i++;
                    }
                    iA4SService.updateMessages(e.a, new com.ad4screen.sdk.h.a(this) {
                        final /* synthetic */ AnonymousClass39 a;

                        {
                            this.a = r1;
                        }

                        public void a() throws RemoteException {
                            Log.debug("Inbox|Update failed, we will try again as soon as updateMessages method will be called again");
                        }

                        public void a(com.ad4screen.sdk.b.c[] cVarArr) throws RemoteException {
                            a.q = new b(cVarArr);
                        }
                    });
                }
            });
        }
    }

    protected final void updatePushRegistration(final Bundle bundle) {
        this.m.a(new com.ad4screen.sdk.a.a.a<IA4SService>(this, "updatePushRegistration") {
            final /* synthetic */ a b;

            public void a(IA4SService iA4SService) throws RemoteException {
                iA4SService.updatePushRegistration(bundle);
            }
        });
    }
}
