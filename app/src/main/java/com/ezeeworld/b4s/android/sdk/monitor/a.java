package com.ezeeworld.b4s.android.sdk.monitor;

import android.content.Context;
import android.location.Location;

import com.ezeeworld.b4s.android.sdk.ActivityTracker;
import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.notifications.NotificationService;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.Group;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.SessionRegistration;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

final class a extends c {
    final Beacon a;
    final Group b;
    final e c;
    final IBeaconData d;
    final List<d> e;
    Double f;
    SessionRegistration g;

    private a(Context context, String str, long j, Beacon beacon, Group group, e eVar, IBeaconData iBeaconData, List<d> list, SessionRegistration sessionRegistration) {
        super(context, str, j);
        this.a = beacon;
        this.b = group;
        this.c = eVar;
        this.d = iBeaconData;
        this.e = list;
        if (sessionRegistration == null) {
            this.g = new SessionRegistration();
            this.g.sSessionId = str;
            this.g.nEndCause = 0;
            this.g.nSessionType = 2;
            this.g.sGlobalSessionId = eVar.i;
            if (group != null) {
                this.g.sGroupId = group.sGroupId;
            } else {
                this.g.sGroupId = "";
            }
            this.g.sShopId = eVar.a.sShopId;
            this.g.sCountry = eVar.a.sCountry;
            this.g.sBeaconId = beacon.sInnerName;
            this.g.sCategoryId = beacon.sCategoryId;
            this.g.dDate = new Date(this.k);
            this.g.bSessionClosed = Boolean.valueOf(false);
            BufferedSessionRegistration.get().persistSession(this.g);
        } else {
            this.g = sessionRegistration;
            this.f = new Double((double) (this.g.nDistance.intValue() / 100));
            this.k = this.g.dDate.getTime();
            this.l = this.k + ((long) (this.g.nDuration * 1000));
        }
        B4SLog.d((Object) this, "BeaconSession.interactions=" + list.size());
    }

    static a a(Context context, e eVar, Beacon beacon, Group group, IBeaconData iBeaconData, List<Interaction> list, SessionRegistration sessionRegistration) {
        if (iBeaconData != null) {
            B4SLog.d("BSN", "BeaconSession beacon=" + iBeaconData.getID() + " distance=" + iBeaconData.getDistance());
        }
        Collection arrayList = new ArrayList(list.size());
        for (Interaction a : list) {
            d a2 = d.a(a);
            if (a2.a(0) && a2.a(InteractionsApi.B4SCAMPAIGN_TYPE_PROXIMITY) && a2.e() && a2.a(eVar.a) && a2.a(beacon)) {
                arrayList.add(a2);
            }
        }
        return new a(context, sessionRegistration != null ? sessionRegistration.sSessionId : "2-" + UUID.randomUUID().toString(), 40000, beacon, group, eVar, iBeaconData, new ArrayList(arrayList), sessionRegistration);
    }

    private void a(d dVar, IBeaconData iBeaconData, Shop shop, Location location) {
        try {
            String str = "5-" + UUID.randomUUID().toString();
            B4SLog.d((Object) this, "[notifyMatch] Matched interaction " + dVar.a.sName + " of " + (iBeaconData == null ? this.a.toString() : iBeaconData.toString()) + " (session id " + str + ")");
            B4SLog.d((Object) this, "[notifyMatch] Matched NotificationType " + dVar.a.sNotificationType + " CampaignType " + dVar.a.sCampaignType + ")");
            SessionRegistration sessionRegistration = this.g;
            sessionRegistration.nInteractions++;
            this.c.a(dVar.a, this.a);
            try {
                InteractionHistory.get().registerEvent(EventType.InteractionMatched, dVar.a.sInteractionId, System.currentTimeMillis(), str, dVar.a.sCampaignName, dVar.a.sName, dVar.a.sCampaignType, dVar.a.sNotificationType, dVar.a.sPushText, shop.nLatitude, shop.nLongitude, dVar.a.nRangeMax / 100, 0, dVar.a.bIncomingInteraction, dVar.a.bOutgoingInteraction, 0.0f, shop.nLatitude, shop.nLongitude, shop.sCity, shop.sName, shop.sZipCode);
            } catch (Exception e) {
                B4SLog.e((Object) this, "[notifyMatch] exception:" + e.toString());
            }
            int currentTimeMillis = (int) ((System.currentTimeMillis() - this.k) / 1000);
            SessionRegistration sessionRegistration2 = new SessionRegistration();
            sessionRegistration2.sSessionId = str;
            sessionRegistration2.nEndCause = 1;
            sessionRegistration2.nSessionType = 5;
            sessionRegistration2.sGlobalSessionId = this.c.i;
            if (this.b != null) {
                sessionRegistration2.sGroupId = this.b.sGroupId;
            } else {
                sessionRegistration2.sGroupId = "";
            }
            if (location != null) {
                sessionRegistration2.fEntryLatitude = location.getLatitude();
                sessionRegistration2.fEntryLongitude = location.getLongitude();
                sessionRegistration2.fExitLatitude = location.getLatitude();
                sessionRegistration2.fExitLongitude = location.getLongitude();
            }
            sessionRegistration2.sShopId = this.c.a.sShopId;
            sessionRegistration2.sCountry = this.c.a.sCountry;
            sessionRegistration2.sBeaconId = this.a.sInnerName;
            sessionRegistration2.sCategoryId = this.a.sCategoryId;
            sessionRegistration2.sInteractionId = dVar.a.sInteractionId;
            sessionRegistration2.sCampaignId = dVar.a.sCampaignId;
            sessionRegistration2.nDuration = currentTimeMillis;
            String str2 = (!dVar.a.sNotificationType.equals("passive") && ActivityTracker.isAppInForeground()) ? InteractionsApi.B4SSATUS_OPENED : InteractionsApi.B4SSATUS_SENT;
            sessionRegistration2.sAcknowledgeData = str2;
            sessionRegistration2.nInteractions = 1;
            sessionRegistration2.nDistance = Integer.valueOf((int) (this.f.doubleValue() * 100.0d));
            sessionRegistration2.dDate = new Date();
            BufferedSessionRegistration.get().registerSession(sessionRegistration2);
            NotificationService.notifyFromMatch(this.h, dVar.a, this.a, iBeaconData, this.c.a, this.b, str, iBeaconData == null ? null : Double.valueOf(iBeaconData.getDistance()));
            sessionRegistration = this.g;
            sessionRegistration.nInteractions++;
            this.g.nDistance = sessionRegistration2.nDistance;
            this.g.nDuration = d();
            BufferedSessionRegistration.get().persistSession(this.g);
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    final void a(double d) {
        if (this.f == null) {
            this.f = Double.valueOf(d);
        } else {
            this.f = Double.valueOf(Math.min(this.f.doubleValue(), d));
        }
        this.l = System.currentTimeMillis();
        this.g.nDuration = d();
        this.g.nDistance = new Integer(this.f.intValue() * 100);
        B4SLog.d((Object) this, "Beacon Dist=" + d + " min=" + this.f + " duration=" + this.g.nDuration);
    }

    public final void a(Interaction interaction) {
        Object obj = null;
        try {
            d a = d.a(interaction);
            if (a.a(0) && a.a(InteractionsApi.B4SCAMPAIGN_TYPE_PROXIMITY) && a.e() && a.a(this.c.a) && a.a(this.a)) {
                obj = 1;
            }
            for (d dVar : this.e) {
                if (dVar.a.sInteractionId.equals(interaction.sInteractionId)) {
                    break;
                }
            }
            d dVar2 = null;
            if (obj != null && dVar2 == null) {
                this.e.add(a);
            } else if (obj == null && dVar2 != null) {
                this.e.remove(dVar2);
            } else if (obj != null) {
                this.e.remove(dVar2);
                InteractionHistory.get().clearEvents(EventType.InteractionMatched, dVar2.a.sInteractionId);
                InteractionHistory.get().clearEvents(EventType.InteractionNotificationOpened, dVar2.a.sInteractionId);
                this.e.add(d.a(dVar2, interaction));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean a(Location location, Shop shop) {
        return false;
    }

    public final boolean a(Location location, Shop shop, Boolean bool) {
        if (bool.booleanValue()) {
            this.l = System.currentTimeMillis();
            this.g.nEndCause = 3;
        }
        int d = d();
        B4SLog.d((Object) this, "Beacon session ended (id " + this.i + ") with " + this.g.nInteractions + " hits and which lasted " + d + "s");
        this.g.nDuration = d;
        this.g.nDistance = Integer.valueOf(this.f.intValue() * 100);
        BufferedSessionRegistration.get().registerSession(this.g);
        return false;
    }

    public final boolean a(IBeaconData iBeaconData, Shop shop, Location location) {
        boolean z;
        if (iBeaconData != null) {
            try {
                if (this.f == null) {
                    this.f = Double.valueOf(iBeaconData.getDistance());
                } else {
                    this.f = Double.valueOf(Math.min(this.f.doubleValue(), iBeaconData.getDistance()));
                }
                this.c.a(this.f.doubleValue());
                this.l = System.currentTimeMillis();
            } catch (Exception e) {
                Exception exception = e;
                z = false;
                Exception exception2 = exception;
                exception2.printStackTrace();
                return z;
            }
        }
        z = false;
        for (d dVar : this.e) {
            try {
                if (dVar.a != null) {
                    dVar.a(iBeaconData);
                    if (dVar.a() && dVar.b() && dVar.c() && dVar.d() && dVar.e() && dVar.f() && dVar.a(this.c.k) && dVar.g() && dVar.b(this.k) && dVar.h() && dVar.i() && dVar.j()) {
                        a(dVar, iBeaconData, shop, location);
                        z = true;
                    }
                } else {
                    B4SLog.e((Object) this, "Not interaction on interactionSession");
                }
            } catch (Exception e2) {
                exception2 = e2;
            }
        }
        return z;
    }

    public final void b(Interaction interaction) {
        for (Object obj : this.e) {
            if (obj.a.sInteractionId.equals(interaction.sInteractionId)) {
                break;
            }
        }
        Object obj2 = null;
        if (obj2 != null) {
            this.e.remove(obj2);
        }
    }
}
