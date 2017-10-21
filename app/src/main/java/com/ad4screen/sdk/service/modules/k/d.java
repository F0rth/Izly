package com.ad4screen.sdk.service.modules.k;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.d.h;
import com.ad4screen.sdk.d.i.g;
import com.ad4screen.sdk.d.i.i;
import com.ad4screen.sdk.d.i.j;
import com.ad4screen.sdk.plugins.BeaconPlugin;
import com.ad4screen.sdk.plugins.GeofencePlugin;
import com.ad4screen.sdk.plugins.model.Beacon;
import com.ad4screen.sdk.provider.A4SBeaconResolver;
import com.ad4screen.sdk.provider.A4SGeofenceResolver;
import com.ad4screen.sdk.service.modules.c.a.c;
import com.ad4screen.sdk.service.modules.c.e;
import com.ad4screen.sdk.service.modules.e.b;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public final class d {
    private final e a;
    private final a b;
    private a c;
    private final c d = new c(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a() {
        }

        public void a(e eVar) {
            List arrayList = new ArrayList(eVar.a().values());
            A4SBeaconResolver a4SBeaconResolver = new A4SBeaconResolver(this.a.b.a());
            if (eVar.c()) {
                Log.info("Tracker|onBeaconConfigurationLoaded|to update " + arrayList.size() + " beacons");
                a4SBeaconResolver.updateBeacons(arrayList);
            } else {
                Log.info("Tracker|onBeaconConfigurationLoaded|deleted: " + a4SBeaconResolver.deleteAllBeacons() + " old beacons");
                a4SBeaconResolver.insertBeacons(arrayList);
                Log.info("Tracker|onBeaconConfigurationLoaded|added: " + arrayList.size() + " new beacons");
            }
            List allBeacons = a4SBeaconResolver.getAllBeacons();
            if (allBeacons != null && allBeacons.size() > 0) {
                String[] strArr = new String[allBeacons.size()];
                for (int i = 0; i < allBeacons.size(); i++) {
                    strArr[i] = ((Beacon) allBeacons.get(i)).uuid;
                }
                this.a.b.h().a(strArr);
                Beacon[] beaconArr = new Beacon[allBeacons.size()];
                allBeacons.toArray(beaconArr);
                this.a.b.h().a(beaconArr);
            }
            this.a.a.b(eVar.b().getTime());
        }
    };
    private final b.c e = new b.c(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a() {
            com.ad4screen.sdk.service.modules.g.a.a(this.a.b.a(), true, false);
        }

        public void a(com.ad4screen.sdk.service.modules.e.a aVar) {
            List arrayList = new ArrayList(aVar.a().values());
            A4SGeofenceResolver a4SGeofenceResolver = new A4SGeofenceResolver(this.a.b.a());
            if (aVar.d()) {
                Log.info("Tracker|onGeofencingConfigurationLoaded|to update " + arrayList.size() + " geofences");
                a4SGeofenceResolver.updateGeofences(arrayList);
            } else {
                Log.info("Tracker|onGeofencingConfigurationLoaded|deleted: " + a4SGeofenceResolver.deleteAllGeofences() + " old geofences");
                a4SGeofenceResolver.insertGeofences(arrayList);
                Log.info("Tracker|onGeofencingConfigurationLoaded|added: " + arrayList.size() + " new geofences");
            }
            this.a.a.a(aVar.b().getTime());
            com.ad4screen.sdk.service.modules.g.a.a(this.a.b.a(), aVar.d(), aVar.c(), a4SGeofenceResolver);
        }
    };
    private final com.ad4screen.sdk.service.modules.j.b.a f = new com.ad4screen.sdk.service.modules.j.b.a(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a(Bundle bundle) {
            this.a.a(bundle);
            this.a.b.d().e();
        }
    };
    private final com.ad4screen.sdk.service.modules.i.b.a g = new com.ad4screen.sdk.service.modules.i.b.a(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a(String[] strArr) {
            HashMap hashMap = new HashMap();
            for (int i = 0; i < strArr.length; i++) {
                boolean z = true;
                if (com.ad4screen.sdk.common.b.m.d.a(this.a.b.a(), h.a(h.a.a(strArr[i]))) != 0) {
                    z = false;
                }
                hashMap.put(strArr[i], Boolean.valueOf(z));
            }
            new com.ad4screen.sdk.service.modules.k.e.a(this.a.b.a(), hashMap).run();
        }
    };
    private final i h = new i(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.d();
            com.ad4screen.sdk.d.b.a(this.a.b.a()).a();
            this.a.a();
            if (VERSION.SDK_INT >= 23) {
                new com.ad4screen.sdk.service.modules.i.a(this.a.b.a()).run();
            }
        }
    };
    private final com.ad4screen.sdk.d.i.h i = new com.ad4screen.sdk.d.i.h(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.b();
        }
    };
    private final g j = new g(this) {
        final /* synthetic */ d a;

        {
            this.a = r1;
        }

        public void a() {
            this.a.c();
        }
    };

    public d(a aVar) {
        this.b = aVar;
        this.a = new e(aVar.a());
        this.c = new a(aVar);
        f.a().a(com.ad4screen.sdk.d.i.d.class, this.i);
        f.a().a(com.ad4screen.sdk.d.i.c.class, this.j);
        f.a().a(j.class, this.h);
        f.a().a(b.b.class, this.e);
        f.a().a(b.a.class, this.e);
        f.a().a(com.ad4screen.sdk.service.modules.c.a.b.class, this.d);
        f.a().a(com.ad4screen.sdk.service.modules.c.a.a.class, this.d);
        f.a().a(com.ad4screen.sdk.service.modules.i.b.b.class, this.g);
        f.a().a(com.ad4screen.sdk.service.modules.j.b.b.class, this.f);
    }

    public static void a(final Callback<String> callback, Context context) {
        final com.ad4screen.sdk.d.b a = com.ad4screen.sdk.d.b.a(context);
        if (a.k() != null) {
            Log.debug("Tracker|IDFV was cached in application : " + a.k());
            callback.onResult(a.k());
            return;
        }
        Log.debug("Tracker|No IDFV stored, querying other apps...");
        Intent intent = new Intent(Constants.ACTION_QUERY);
        intent.addCategory(Constants.CATEGORY_IDFV);
        context.sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
            public final void onReceive(Context context, Intent intent) {
                String str;
                ArrayList stringArrayList = getResultExtras(true).getStringArrayList("anonymId");
                HashMap hashMap = new HashMap();
                if (stringArrayList != null) {
                    Log.debug("Tracker|" + stringArrayList.size() + " Response(s) from other apps...");
                    Iterator it = stringArrayList.iterator();
                    while (it.hasNext()) {
                        String[] split = ((String) it.next()).split("\\|");
                        if (split.length != 2) {
                            Log.debug("Tracker|1 response with wrong format found...");
                        } else {
                            String str2 = split[0];
                            if (com.ad4screen.sdk.common.i.a(str2 + "y^X*4]6k],:!e%$&n{@[#!|S2[yH#/I1]Qd;^{+'J1rAkp8!%&&)OV0)\"H$#V2{\"O<+v^6k=q}74;1}=6X3-:G~&F!$]f_L6C>@M").equals(split[1])) {
                                int[] iArr = (int[]) hashMap.get(str2);
                                if (iArr == null) {
                                    hashMap.put(str2, new int[]{1});
                                } else {
                                    iArr[0] = iArr[0] + 1;
                                }
                            } else {
                                Log.debug("Tracker|1 bad response found...");
                            }
                        }
                    }
                }
                Entry entry = null;
                for (Entry entry2 : hashMap.entrySet()) {
                    Entry entry22;
                    Log.debug("Tracker|Id '" + ((String) entry22.getKey()) + "' has " + ((int[]) entry22.getValue())[0] + " occurence(s)...");
                    if (entry == null) {
                        entry = entry22;
                    } else {
                        if (((int[]) entry.getValue())[0] >= ((int[]) entry22.getValue())[0]) {
                            entry22 = entry;
                        }
                        entry = entry22;
                    }
                }
                if (entry != null) {
                    Log.debug("Tracker|Id '" + ((String) entry.getKey()) + " is probably good, using it.");
                    str = (String) entry.getKey();
                    if (str.contains("#")) {
                        str = str.substring(1);
                    }
                } else {
                    str = com.ad4screen.sdk.common.h.b();
                    Log.debug("Tracker|No good entry found, generating one : '" + str + "'.");
                }
                a.c(str);
                f.a().a(new f.f());
                callback.onResult(str);
            }
        }, new Handler(), -1, null, null);
    }

    protected static boolean a(Object obj) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (str.startsWith("+") || str.startsWith("-")) {
                return true;
            }
        }
        return false;
    }

    private boolean g() {
        return this.a.g();
    }

    public final void a() {
        Log.debug("Tracker|Tracking started");
        a(new Callback<String>(this) {
            final /* synthetic */ d a;

            {
                this.a = r1;
            }

            public void a(String str) {
                if (this.a.g()) {
                    Log.info("Tracker|Tracking refused");
                    return;
                }
                new com.ad4screen.sdk.service.modules.inapp.i(this.a.b.a(), null, false).run();
                this.a.c.a();
            }

            public void onError(int i, String str) {
            }

            public /* synthetic */ void onResult(Object obj) {
                a((String) obj);
            }
        }, this.b.a());
    }

    public final void a(long j, Bundle bundle, String... strArr) {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.a(j, bundle, strArr);
        }
    }

    public final void a(Bundle bundle) {
        Bundle f = this.a.f();
        f.putAll(bundle);
        this.a.a(f);
    }

    public final void a(Bundle bundle, boolean z) {
        if (!z && (!com.ad4screen.sdk.d.d.a(this.b.a()).d(com.ad4screen.sdk.d.d.b.UpdateDeviceInfoCanSendSameKeyValues) || com.ad4screen.sdk.d.d.a(this.b.a()).c(com.ad4screen.sdk.d.d.b.UpdateDeviceInfoCanSendSameKeyValues))) {
            Bundle f = this.a.f();
            Bundle bundle2 = new Bundle(bundle);
            for (String str : bundle.keySet()) {
                if (!a(bundle.get(str)) && f.containsKey(str) && f.get(str).equals(bundle.get(str))) {
                    Log.debug("Tracker|Key : " + str + " is already up to date and will not be sent again");
                    bundle2.remove(str);
                }
            }
            if (bundle2.size() > 0) {
                com.ad4screen.sdk.service.modules.d.h.a(this.b, bundle2, z);
            }
        } else if (bundle.size() > 0) {
            com.ad4screen.sdk.service.modules.d.h.a(this.b, bundle, z);
        }
    }

    public final void a(Cart cart, Bundle bundle) {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.a(cart, bundle);
        }
    }

    public final void a(Lead lead, Bundle bundle) {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.a(lead, bundle);
        }
    }

    public final void a(Purchase purchase, Bundle bundle) {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.a(purchase, bundle);
        }
    }

    public final void a(com.ad4screen.sdk.j jVar) {
        com.ad4screen.sdk.service.modules.d.h.a(this.b.a(), jVar);
    }

    public final void a(com.ad4screen.sdk.service.modules.k.d.c cVar) {
        this.c.a(cVar);
    }

    public final void a(String str) {
        this.c.a(str);
    }

    public final void a(String str, String str2, String[] strArr) {
        Log.debug("Tracker|Uploading Facebook Profile");
        new b(this.b.a(), str, str2, strArr).run();
    }

    public final void a(List<com.ad4screen.sdk.service.modules.k.c.e> list) {
        com.ad4screen.sdk.service.modules.d.h.a(this.b.a(), (List) list);
    }

    public final void a(List<com.ad4screen.sdk.service.modules.k.c.e> list, com.ad4screen.sdk.j jVar) {
        com.ad4screen.sdk.service.modules.d.h.a(this.b.a(), (List) list, jVar);
    }

    public final void a(boolean z) {
        GeofencePlugin d = com.ad4screen.sdk.common.d.b.d();
        if (d != null) {
            if (d.isGeofencingServiceDeclared(this.b.a())) {
                new com.ad4screen.sdk.service.modules.e.e(this.b.a(), f().longValue()).run();
            } else {
                Log.warn("Tracker|Can't use Geofencing plugin because A4SGeofencingService service is not declared in your AndroidManifest.xml");
                Log.warn("Tracker|If you want to use Geofencing feature, please add : <service android:name=\"com.ad4screen.sdk.A4SGeofencingService\" android:exported=\"false\"></service> to your AndroidManifest.xml");
            }
        }
        BeaconPlugin e = com.ad4screen.sdk.common.d.b.e();
        if (e == null) {
            return;
        }
        if (e.isBeaconServiceDeclared(this.b.a())) {
            new com.ad4screen.sdk.service.modules.c.f(this.b.a(), e().longValue()).run();
            return;
        }
        Log.warn("Tracker|Can't use Beacon plugin because A4SBeaconService service is not declared in your AndroidManifest.xml");
        Log.warn("Tracker|If you want to use Beacon feature, please add : <service android:name=\"com.ad4screen.sdk.A4SBeaconService\" android:exported=\"true\"></service> to your AndroidManifest.xml");
    }

    public final void b() {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.b();
        }
    }

    public final void b(List<com.ad4screen.sdk.service.modules.k.c.e> list) {
        com.ad4screen.sdk.service.modules.d.h.b(this.b.a(), list);
    }

    public final void c() {
        if (g()) {
            Log.debug("Tracker|Tracking refused");
        } else {
            this.c.c();
        }
    }

    public final void d() {
        this.a.a(new Bundle());
    }

    public final Long e() {
        return Long.valueOf(this.a.a());
    }

    public final Long f() {
        return Long.valueOf(this.a.e());
    }
}
