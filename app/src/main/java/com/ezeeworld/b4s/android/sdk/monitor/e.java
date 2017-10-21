package com.ezeeworld.b4s.android.sdk.monitor;

import android.content.Context;
import android.location.Location;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.SessionRegistration;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

final class e extends c {
    final Shop a;
    final HashMap<String, a> b = new HashMap();
    final HashMap<String, b> c = new HashMap();
    Interaction d;
    Beacon e;
    Double f;
    Location g;
    SessionRegistration m;

    private e(Context context, String str, long j, Shop shop, SessionRegistration sessionRegistration) {
        super(context, str, j);
        this.a = shop;
        if (sessionRegistration == null) {
            this.m = new SessionRegistration();
            this.m.sSessionId = str;
            this.m.dDate = new Date(this.k);
            this.m.nEndCause = 2;
            this.m.nSessionType = 0;
            this.m.fExitLatitude = 0.0d;
            this.m.fExitLongitude = 0.0d;
            this.m.fEntryLatitude = 0.0d;
            this.m.fEntryLongitude = 0.0d;
            this.f = new Double(0.0d);
            this.m.bSessionClosed = Boolean.valueOf(false);
            if (shop != null) {
                this.m.sShopId = shop.sShopId;
                this.m.sCountry = shop.sCountry;
            } else {
                this.m.sShopId = "";
                this.m.sCountry = "";
                B4SLog.d((Object) this, "No shopId for shop session:" + str);
            }
            if (this.e != null) {
                this.m.sGroupId = this.e.sGroupId;
                this.m.sBeaconId = this.e.sInnerName;
                this.m.sCategoryId = this.e.sCategoryId;
            }
            B4SLog.d((Object) this, "Shop session:" + str + " date:" + this.m.dDate);
            BufferedSessionRegistration.get().persistSession(this.m);
            return;
        }
        this.m = sessionRegistration;
        this.f = new Double((double) (this.m.nDistance.intValue() / 100));
        this.g = new Location("shopEntry");
        this.g.setLatitude(sessionRegistration.fEntryLatitude);
        this.g.setLongitude(sessionRegistration.fEntryLongitude);
        this.k = this.m.dDate.getTime();
        this.l = this.k + ((long) (this.m.nDuration * 1000));
        B4SLog.d((Object) this, "Shop session reload (id " + str + ") dist=" + this.m.nDistance + " entry=" + sessionRegistration.fEntryLatitude + "," + sessionRegistration.fEntryLongitude + " exit=" + sessionRegistration.fExitLatitude + "," + sessionRegistration.fExitLongitude);
    }

    static e a(Context context, Shop shop, long j, SessionRegistration sessionRegistration) {
        return new e(context, sessionRegistration != null ? sessionRegistration.sSessionId : "0-" + UUID.randomUUID().toString(), j, shop, sessionRegistration);
    }

    public final void a() {
        SessionRegistration sessionRegistration = this.m;
        sessionRegistration.nInteractions++;
    }

    final void a(double d) {
        if (this.f == null) {
            this.f = Double.valueOf(d);
        } else {
            this.f = Double.valueOf(Math.min(this.f.doubleValue(), d));
        }
        this.l = System.currentTimeMillis();
        this.m.nDuration = d();
        this.m.nDistance = new Integer(this.f.intValue() * 100);
    }

    final void a(Interaction interaction) {
        a(interaction, null);
    }

    final void a(Interaction interaction, Beacon beacon) {
        this.d = interaction;
        this.e = beacon;
        a();
    }

    public final boolean a(Location location, Shop shop) {
        this.g = location;
        this.m.fEntryLongitude = location.getLongitude();
        this.m.fEntryLatitude = location.getLatitude();
        this.m.fExitLatitude = location.getLatitude();
        this.m.fExitLongitude = location.getLongitude();
        float distanceTo = location.distanceTo(shop.location());
        this.f = new Double((double) distanceTo);
        this.m.nDistance = Integer.valueOf(((int) distanceTo) * 100);
        B4SLog.d((Object) this, "Shop session started (id " + this.i + ") @:" + shop.sShopId + " (" + shop.sName + ") dist=" + this.m.nDistance + " coords=" + location.getLatitude() + "," + location.getLongitude());
        BufferedSessionRegistration.get().persistSession(this.m);
        InteractionHistory.get().registerEvent(EventType.ShopEntry, this.i, System.currentTimeMillis(), shop.nLatitude, shop.nLongitude, shop.sCity, shop.sName, shop.sZipCode);
        return false;
    }

    public final boolean a(Location location, Shop shop, Boolean bool) {
        InteractionHistory.get().registerEvent(EventType.ShopExit, this.i, System.currentTimeMillis());
        if (bool.booleanValue()) {
            this.l = System.currentTimeMillis();
            this.m.nEndCause = 3;
        }
        int d = d();
        B4SLog.d((Object) this, "Shop session ended (id " + this.i + ") with " + this.m.nInteractions + " hits and which lasted " + d + "s @:" + shop.sShopId + " (" + shop.sName + ")");
        if (location != null) {
            this.m.fExitLatitude = location.getLatitude();
            this.m.fExitLongitude = location.getLongitude();
        } else {
            this.m.fExitLatitude = 0.0d;
            this.m.fExitLongitude = 0.0d;
        }
        if (this.g != null) {
            this.m.fEntryLatitude = this.g.getLatitude();
            this.m.fEntryLongitude = this.g.getLongitude();
        } else {
            this.m.fEntryLatitude = 0.0d;
            this.m.fEntryLongitude = 0.0d;
        }
        if (this.f != null) {
            this.m.nDistance = Integer.valueOf((int) (this.f.doubleValue() * 100.0d));
        }
        this.m.nDuration = d;
        this.m.dDate = new Date(this.k);
        BufferedSessionRegistration.get().registerSession(this.m);
        return false;
    }
}
