package com.ezeeworld.b4s.android.sdk.monitor;

import android.content.Context;
import android.location.Location;
import android.support.v4.widget.AutoScrollHelper;

import com.ezeeworld.b4s.android.sdk.ActivityTracker;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.notifications.NotificationService;
import com.ezeeworld.b4s.android.sdk.server.Group;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.SessionRegistration;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

final class b extends c {
    final e a;
    final Location b;
    final Group c;
    d d;
    float e = AutoScrollHelper.NO_MAX;
    SessionRegistration f;

    private b(Context context, String str, long j, e eVar, Location location, Group group, d dVar, SessionRegistration sessionRegistration) {
        super(context, str, j);
        this.a = eVar;
        this.d = dVar;
        this.c = group;
        if (sessionRegistration == null) {
            this.b = location;
            this.f = new SessionRegistration();
            this.f.sSessionId = str;
            this.f.nEndCause = 0;
            this.f.nSessionType = 3;
            this.f.sGlobalSessionId = eVar.i;
            if (location != null) {
                this.f.fEntryLongitude = location.getLongitude();
                this.f.fEntryLatitude = location.getLatitude();
            } else {
                this.f.fEntryLongitude = 0.0d;
                this.f.fEntryLatitude = 0.0d;
            }
            this.f.fExitLatitude = 0.0d;
            this.f.fExitLongitude = 0.0d;
            this.f.bSessionClosed = Boolean.valueOf(false);
            if (group != null) {
                this.f.sGroupId = group.sGroupId;
            } else {
                this.f.sGroupId = "";
            }
            this.f.sShopId = eVar.a.sShopId;
            this.f.sCountry = eVar.a.sCountry;
            this.f.sInteractionId = dVar.a.sInteractionId;
            this.f.dDate = new Date(this.k);
            BufferedSessionRegistration.get().persistSession(this.f);
            B4SLog.d((Object) this, "New GeofenceSession:" + this.f.sSessionId + " nRangeEnteredTime=" + this.f.nRangeEnteredTime);
            return;
        }
        this.f = sessionRegistration;
        this.d = dVar;
        this.k = this.f.dDate.getTime();
        this.b = new Location("");
        this.b.setLatitude(this.f.fEntryLatitude);
        this.b.setLongitude(this.f.fEntryLongitude);
        this.e = (float) (this.f.nDistance.intValue() / 100);
        this.l = this.k + ((long) (this.f.nDuration * 1000));
        B4SLog.d((Object) this, "ReNew GeofenceSession:" + this.f.sSessionId + " nRangeEnteredTime=" + this.f.nRangeEnteredTime);
    }

    static b a(Context context, e eVar, Group group, Interaction interaction, SessionRegistration sessionRegistration) {
        Location location = new Location(eVar.a.sName);
        location.setLatitude(eVar.a.nLatitude);
        location.setLongitude(eVar.a.nLongitude);
        return new b(context, sessionRegistration != null ? sessionRegistration.sSessionId : "3-" + UUID.randomUUID().toString(), 0, eVar, location, group, d.a(interaction), sessionRegistration);
    }

    private void c(Location location, Shop shop) {
        String str;
        SessionRegistration sessionRegistration;
        this.a.a(this.d.a);
        String str2 = "5-" + UUID.randomUUID().toString();
        SessionRegistration sessionRegistration2 = new SessionRegistration();
        sessionRegistration2.sSessionId = str2;
        sessionRegistration2.nEndCause = 0;
        sessionRegistration2.nSessionType = 5;
        sessionRegistration2.sGlobalSessionId = this.a.i;
        if (this.c != null) {
            sessionRegistration2.sGroupId = this.c.sGroupId;
        } else {
            sessionRegistration2.sGroupId = "";
        }
        sessionRegistration2.fEntryLatitude = location.getLatitude();
        sessionRegistration2.fEntryLongitude = location.getLongitude();
        sessionRegistration2.fExitLatitude = location.getLatitude();
        sessionRegistration2.fExitLongitude = location.getLongitude();
        sessionRegistration2.bSessionClosed = Boolean.valueOf(false);
        sessionRegistration2.sShopId = this.a.a.sShopId;
        sessionRegistration2.sCountry = this.a.a.sCountry;
        sessionRegistration2.sBeaconId = "VB4S_" + sessionRegistration2.sShopId;
        sessionRegistration2.sCategoryId = "";
        sessionRegistration2.sInteractionId = this.d.a.sInteractionId;
        sessionRegistration2.sCampaignId = this.d.a.sCampaignId;
        sessionRegistration2.nDuration = 0;
        if (!this.d.a.sNotificationType.equals("passive") && ActivityTracker.isAppInForeground()) {
            str = InteractionsApi.B4SSATUS_OPENED;
            sessionRegistration = sessionRegistration2;
        } else {
            str = InteractionsApi.B4SSATUS_SENT;
            sessionRegistration = sessionRegistration2;
        }
        sessionRegistration.sAcknowledgeData = str;
        sessionRegistration2.nInteractions = 1;
        sessionRegistration2.nDistance = Integer.valueOf((int) (this.e * 100.0f));
        sessionRegistration2.dDate = new Date();
        BufferedSessionRegistration.get().registerSession(sessionRegistration2);
        NotificationService.notifyFromMatch(this.h, this.d.a, null, null, this.a.a, this.c, str2, Double.valueOf(sessionRegistration2.nDistance.doubleValue()));
        B4SLog.d((Object) this, String.format(Locale.US, "Matched geofence interaction %1$s at %2$.3f,%3$.3f interactionSessionId=%4$s", new Object[]{this.d.a.sName, Double.valueOf(location.getLatitude()), Double.valueOf(location.getLongitude()), str2}));
        int i = 0;
        if (this.d.a.bOutgoingInteraction) {
            i = (this.d.a.nRangeMax / 100) + 200;
        }
        a();
        try {
            InteractionHistory.get().registerEvent(EventType.InteractionMatched, this.d.a.sInteractionId, System.currentTimeMillis(), str2, this.d.a.sCampaignName, this.d.a.sName, this.d.a.sCampaignType, this.d.a.sNotificationType, this.d.a.sPushText, location.getLatitude(), location.getLongitude(), this.d.a.nRangeMax / 100, i, this.d.a.bIncomingInteraction, this.d.a.bOutgoingInteraction, location.getAccuracy(), this.b.getLatitude(), this.b.getLongitude(), shop.sCity, shop.sName, shop.sZipCode);
        } catch (Exception e) {
            B4SLog.e((Object) this, "notifyMatch exception:" + e.toString());
        }
    }

    public final void a() {
        SessionRegistration sessionRegistration = this.f;
        sessionRegistration.nInteractions++;
    }

    public final void a(Location location) {
        this.e = Math.min(this.e, location.distanceTo(this.b));
        this.a.a((double) this.e);
        if (this.b.distanceTo(location) < ((float) (this.d.a.nRangeMax / 100))) {
            this.a.c();
            c();
        }
        this.f.nDistance = Integer.valueOf(((int) this.e) * 100);
        this.f.nDuration = d();
    }

    public final void a(Interaction interaction) {
        if (this.d.a.sInteractionId.equals(interaction.sInteractionId)) {
            InteractionHistory.get().clearEvents(EventType.InteractionMatched, this.d.a.sInteractionId);
            InteractionHistory.get().clearEvents(EventType.InteractionNotificationOpened, this.d.a.sInteractionId);
            this.d = d.a(this.d, interaction);
        }
    }

    public final boolean a(Location location, Shop shop) {
        B4SLog.d((Object) this, "Entered geofence area for '" + this.d.a.sName + "' sr=" + this.f.sSessionId);
        MonitoringStatus.update(MonitoringStatus.EnteredGeoFence, "Entered geofence area for interaction " + this.d.a.sName);
        this.e = location.distanceTo(this.b);
        this.f.nDistance = Integer.valueOf(((int) this.e) * 100);
        c();
        this.d.a(a.Entered);
        this.f.nRangeEnteredTime = this.d.c;
        this.f.nRangeState = this.d.b.ordinal();
        B4SLog.d((Object) this, "Entered geofence rangeEnteredTime=" + this.d.c);
        if ((this.d.a(2) || this.d.a()) && this.d.b() && this.d.c() && this.d.d() && this.d.e() && this.d.g() && this.d.i() && this.d.j()) {
            c(location, shop);
            return true;
        }
        B4SLog.d((Object) this, "Geofence");
        BufferedSessionRegistration.get().persistSession(this.f);
        return false;
    }

    public final boolean a(Location location, Shop shop, Boolean bool) {
        if (bool.booleanValue()) {
            this.l = System.currentTimeMillis();
        }
        c();
        this.e = Math.min(this.e, location.distanceTo(this.b));
        this.d.a(a.Exited);
        boolean z = (this.d.a(3) || this.d.a()) && this.d.b(this.a.e) && this.d.b() && this.d.c() && this.d.d() && this.d.e() && this.d.g() && this.d.j();
        if (!z) {
            if ((this.d.a(3) || this.d.a()) && this.d.a.bGeoNotifyIfBeacon && !this.d.b(this.a.e)) {
                B4SLog.d((Object) this, "Exited geofence for interaction '" + this.d.a.sName + "' but we did not match any beacon, so no notification");
            }
        }
        b(location);
        if (z) {
            c(location, shop);
        }
        return z;
    }

    public final void b(Location location) {
        this.l = System.currentTimeMillis();
        int i = (int) ((this.l - this.k) / 1000);
        if (location != null) {
            this.f.fExitLongitude = location.getLongitude();
            this.f.fExitLatitude = location.getLatitude();
        }
        this.f.nDuration = i;
        this.f.nDistance = Integer.valueOf((int) (this.e * 100.0f));
        this.f.dDate = new Date(this.k);
        this.f.nEndCause = 3;
        BufferedSessionRegistration.get().registerSession(this.f);
    }

    public final boolean b(Location location, Shop shop) {
        B4SLog.d((Object) this, "presenceDurationTest for 1:" + this.d);
        B4SLog.d((Object) this, "presenceDurationTest for 2:" + this.d.c);
        B4SLog.d((Object) this, "presenceDurationTest for 2:" + this.d.b);
        B4SLog.d((Object) this, "presenceDurationTest for 3:" + this.d.a);
        B4SLog.d((Object) this, "presenceDurationTest for '" + this.d.a.sName + "' duration=" + this.d.a.nNotificationPresenceDuration + " presence=" + ((System.currentTimeMillis() - this.d.c.longValue()) / 1000));
        this.e = location.distanceTo(this.b);
        c();
        if ((!this.d.a(2) && !this.d.a()) || !this.d.b() || !this.d.c() || !this.d.d() || !this.d.e() || !this.d.g() || !this.d.i() || !this.d.j()) {
            return false;
        }
        c(location, shop);
        return true;
    }
}
