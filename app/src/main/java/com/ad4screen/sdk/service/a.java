package com.ad4screen.sdk.service;

import android.location.Location;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ResultReceiver;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.IA4SService.Stub;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.b.c;
import com.ad4screen.sdk.common.e;
import com.ad4screen.sdk.d.b;
import com.ad4screen.sdk.d.i;
import com.ad4screen.sdk.f;
import com.ad4screen.sdk.g;
import com.ad4screen.sdk.j;
import com.ad4screen.sdk.service.modules.d.h;
import com.ad4screen.sdk.service.modules.k.d;

import java.util.List;

public final class a extends Stub {
    private final com.ad4screen.sdk.A4SService.a a;
    private final Object b = new Object();

    public a(com.ad4screen.sdk.A4SService.a aVar) {
        this.a = aVar;
    }

    public final void clickButtonMessage(com.ad4screen.sdk.b.a aVar, String str) throws RemoteException {
        if (aVar.c == com.ad4screen.sdk.b.c.a.Close) {
            h.a(this.a, str, aVar.a, com.ad4screen.sdk.service.modules.d.e.a.CLOSE);
        } else {
            h.a(this.a, str, aVar.a, com.ad4screen.sdk.service.modules.d.e.a.CLICK);
        }
        if (aVar.c == com.ad4screen.sdk.b.c.a.Url) {
            h.a(this.a.a(), aVar.d, new e("nid", str), new e("bid", aVar.a));
        }
    }

    public final void clientStarted() throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                i.a(this.a.a.a()).c();
            }
        });
    }

    public final void closeCurrentInApp() {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a.d().d();
            }
        });
    }

    public final void closedPush(final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.c().closedPush(bundle);
            }
        });
    }

    public final void displayInApp(final String str) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.d().g(str);
            }
        });
    }

    public final void displayMessage(c cVar) throws RemoteException {
        h.a(this.a, cVar.a, com.ad4screen.sdk.service.modules.d.e.a.DISP);
        if (cVar.j == com.ad4screen.sdk.b.c.a.Url) {
            h.a(this.a.a(), cVar.e, new e("nid", cVar.a));
        }
    }

    public final void doAction(String str) {
        if ("activateInternalLogging".equals(str)) {
            Log.setInternalLoggingEnabled(true);
        } else if ("disableInternalLogging".equals(str)) {
            Log.setInternalLoggingEnabled(false);
        } else if ("activateLogging".equals(str)) {
            Log.setEnabled(true);
            b.a(this.a.a()).a(true);
        } else if ("disableLogging".equals(str)) {
            Log.setEnabled(false);
            b.a(this.a.a()).a(false);
        }
    }

    public final void getA4SId(final com.ad4screen.sdk.e eVar) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                String c = b.a(this.b.a.a()).c();
                if (c != null) {
                    try {
                        eVar.a(c);
                        return;
                    } catch (Throwable e) {
                        Log.error("A4SService|Error while sending A4SId back to client", e);
                        return;
                    }
                }
                this.b.a.a(this, 1000);
            }
        });
    }

    public final void getActiveMember(com.ad4screen.sdk.e eVar) {
        try {
            eVar.a(com.ad4screen.sdk.service.modules.h.c.a(this.a.a()).b());
        } catch (Throwable e) {
            Log.error("MemberManager|Error while sending active member to client. Please check connection between service and client", e);
        }
    }

    public final void getIDFV(final com.ad4screen.sdk.e eVar) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                d.a(new Callback<String>(this) {
                    final /* synthetic */ AnonymousClass7 a;

                    {
                        this.a = r1;
                    }

                    public void a(String str) {
                        try {
                            eVar.a(str);
                        } catch (Throwable e) {
                            Log.error("A4SService|Error while sending IDFV back to client", e);
                        }
                    }

                    public void onError(int i, String str) {
                    }

                    public /* synthetic */ void onResult(Object obj) {
                        a((String) obj);
                    }
                }, this.b.a.a());
            }
        });
    }

    public final void getListOfSubscriptions(final j jVar) throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.b().a(jVar);
            }
        });
    }

    public final void getMembers(final f fVar) {
        com.ad4screen.sdk.service.modules.h.c.a(this.a.a()).a(new Callback<com.ad4screen.sdk.service.modules.h.a.a>(this) {
            final /* synthetic */ a b;

            public void a(com.ad4screen.sdk.service.modules.h.a.a aVar) {
                String[] strArr = new String[aVar.a.size()];
                for (int i = 0; i < aVar.a.size(); i++) {
                    strArr[i] = ((com.ad4screen.sdk.service.modules.h.a.b) aVar.a.get(i)).a;
                }
                try {
                    fVar.a(strArr);
                } catch (Throwable e) {
                    Log.error("MemberManager|Error while sending retrieved members to client. Please check connection between service and client", e);
                }
            }

            public void onError(int i, String str) {
            }

            public /* synthetic */ void onResult(Object obj) {
                a((com.ad4screen.sdk.service.modules.h.a.a) obj);
            }
        });
    }

    public final void getMessagesDetails(String[] strArr, com.ad4screen.sdk.h hVar) throws RemoteException {
        this.a.e().a(strArr, hVar);
    }

    public final void getMessagesList(com.ad4screen.sdk.h hVar) throws RemoteException {
        this.a.e().a(hVar);
    }

    public final void getPushToken(final g gVar) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                try {
                    gVar.a(this.b.a.c().getPushToken());
                } catch (Throwable e) {
                    Log.error("A4SService|Error while sending push token back to client", e);
                }
            }
        });
    }

    public final void getSubscriptionStatusForLists(final List<com.ad4screen.sdk.service.modules.k.c.e> list, final j jVar) throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.b().a(list, jVar);
            }
        });
    }

    public final void handleGeofencingMessage(final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.g().a(bundle);
            }
        });
    }

    public final void handlePushMessage(final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.c().handleMessage(bundle);
            }
        });
    }

    public final void interstitialClosed() {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                i.a(this.a.a.a()).e();
            }
        });
    }

    public final void interstitialDisplayed() {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                i.a(this.a.a.a()).d();
            }
        });
    }

    public final boolean isInAppDisplayLocked() {
        return this.a.d().c();
    }

    public final boolean isPushEnabled() {
        boolean isEnabled;
        synchronized (this.b) {
            isEnabled = this.a.c().isEnabled();
        }
        return isEnabled;
    }

    public final boolean isRestrictedConnection() {
        return com.ad4screen.sdk.common.a.a.a(this.a.a()).d();
    }

    public final void logIn(String str) throws RemoteException {
        com.ad4screen.sdk.service.modules.h.c.a(this.a.a()).a(str);
    }

    public final void logOut() throws RemoteException {
        com.ad4screen.sdk.service.modules.h.c.a(this.a.a()).a();
    }

    public final void onInAppClicked(final String str, final String str2) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.d().a(str, str2);
            }
        });
    }

    public final void onInAppClosed(final String str, final boolean z) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                if (z) {
                    this.c.a.d().c(str);
                } else {
                    this.c.a.d().b(str);
                }
            }
        });
    }

    public final void onInAppDisplayed(final String str) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.d().d(str);
            }
        });
    }

    public final void onInAppReady(final String str, final int i) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.d().a(str, i);
            }
        });
    }

    public final void openedPush(final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.c().openedPush(bundle);
            }
        });
    }

    public final void putState(final String str, final String str2) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.d().b(str, str2);
            }
        });
    }

    public final void refreshPushToken() {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                this.a.a.c().b();
            }
        });
    }

    public final void removeMembers(String[] strArr) throws RemoteException {
        com.ad4screen.sdk.service.modules.h.c.a(this.a.a()).a(strArr);
    }

    public final void setClientCallback(ResultReceiver resultReceiver) {
        this.a.f().a(resultReceiver);
    }

    public final void setDoNotTrack(boolean z, boolean z2, boolean z3, final com.ad4screen.sdk.d dVar) throws RemoteException {
        com.ad4screen.sdk.common.a.a a = com.ad4screen.sdk.common.a.a.a(this.a.a());
        if (!z2) {
            a.g();
        }
        if (z3) {
            new com.ad4screen.sdk.service.modules.j.a(this.a.a(), z).run();
        }
        a.a(new Callback<Boolean>(this) {
            final /* synthetic */ a b;

            public void a(Boolean bool) {
                if (dVar != null) {
                    try {
                        dVar.a(bool.booleanValue());
                    } catch (Throwable e) {
                        Log.error("A4SService|Error while sending flush result back to client", e);
                    }
                }
            }

            public void onError(int i, String str) {
                if (dVar != null) {
                    try {
                        dVar.a(true);
                    } catch (Throwable e) {
                        Log.error("A4SService|Error while sending flush result back to client", e);
                    }
                }
            }

            public /* synthetic */ void onResult(Object obj) {
                a((Boolean) obj);
            }
        });
    }

    public final void setInAppDisplayLocked(final boolean z) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.d().a(z);
            }
        });
    }

    public final void setInAppReadyCallback(final boolean z, final int[] iArr) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.d().a(z, iArr);
            }
        });
    }

    public final void setPushEnabled(boolean z) {
        synchronized (this.b) {
            this.a.c().setEnabled(z);
        }
    }

    public final void setRestrictedConnection(boolean z) {
        Log.debug("SDK Internet Connection is now : " + (z ? "restricted" : "unrestricted"));
        if (z) {
            com.ad4screen.sdk.common.a.a.a(this.a.a()).c();
        } else {
            com.ad4screen.sdk.common.a.a.a(this.a.a()).b();
        }
    }

    public final void setSource(String str) {
        b.a(this.a.a()).e(str);
        Log.info("A4S|New source : " + str);
    }

    public final void setView(final String str) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.d().f(str);
            }
        });
    }

    public final void startActivity(final String str, final String str2, final String str3) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a d;

            public void run() {
                i.a(this.d.a.a()).a(str, str2, str3);
            }
        });
    }

    public final void stopActivity(String str) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a a;

            {
                this.a = r1;
            }

            public void run() {
                if (i.a(this.a.a.a()).g()) {
                    i.a(this.a.a.a()).b();
                } else {
                    Log.info("A4S|Received StopActivity but no Activity started yet");
                }
            }
        });
    }

    public final void subscribeToLists(final List<com.ad4screen.sdk.service.modules.k.c.e> list) throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.b().a(list);
            }
        });
    }

    public final void trackAddToCart(final Cart cart, final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.b().a(cart, bundle);
            }
        });
    }

    public final void trackEvent(long j, String[] strArr, Bundle bundle) {
        final long j2 = j;
        final Bundle bundle2 = bundle;
        final String[] strArr2 = strArr;
        this.a.a(new Runnable(this) {
            final /* synthetic */ a d;

            public void run() {
                this.d.a.b().a(j2, bundle2, strArr2);
            }
        });
    }

    public final void trackLead(final Lead lead, final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.b().a(lead, bundle);
            }
        });
    }

    public final void trackPurchase(final Purchase purchase, final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.b().a(purchase, bundle);
            }
        });
    }

    public final void trackReferrer(final String str) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.b().a(str);
            }
        });
    }

    public final void triggerBeacons(final Bundle bundle) throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.h().a(bundle);
            }
        });
    }

    public final void unsubscribeFromLists(final List<com.ad4screen.sdk.service.modules.k.c.e> list) throws RemoteException {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.b().b(list);
            }
        });
    }

    public final void updateGeolocation(final Location location) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.g().a(location);
            }
        });
    }

    public final void updateMessages(c[] cVarArr, com.ad4screen.sdk.h hVar) throws RemoteException {
        this.a.e().a(cVarArr, hVar);
    }

    public final void updatePushRegistration(final Bundle bundle) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a b;

            public void run() {
                this.b.a.c().updateRegistration(bundle);
            }
        });
    }

    public final void updateUserPreferences(final Bundle bundle, final boolean z) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a c;

            public void run() {
                this.c.a.b().a(bundle, z);
            }
        });
    }

    public final void uploadFacebookProfile(final String str, final String str2, final String[] strArr) {
        this.a.a(new Runnable(this) {
            final /* synthetic */ a d;

            public void run() {
                this.d.a.b().a(str, str2, strArr);
            }
        });
    }
}
