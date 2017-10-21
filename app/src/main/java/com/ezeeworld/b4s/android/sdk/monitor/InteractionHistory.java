package com.ezeeworld.b4s.android.sdk.monitor;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.CupboardDbHelper;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;

import java.util.List;

import nl.qbusict.cupboard.DatabaseCompartment;

public class InteractionHistory {
    private final DatabaseCompartment a;

    public enum EventType {
        InteractionMatched,
        ShopEntry,
        ShopExit,
        BeaconObserved,
        InteractionNotificationOpened
    }

    static class a {
        static final InteractionHistory a = new InteractionHistory();
    }

    private InteractionHistory() {
        this.a = CupboardDbHelper.database(B4SSettings.get().getApplicationContext());
    }

    public static InteractionHistory get() {
        return a.a;
    }

    public void clearAllEvents() {
        this.a.delete(InteractionEvent.class, "", new String[0]);
    }

    public void clearEvents(EventType eventType, String str) {
        this.a.delete(InteractionEvent.class, "eventType = ? AND objectId = ?", new String[]{Integer.toString(eventType.ordinal()), str});
    }

    public InteractionEvent findEventRecent(EventType eventType, String str, long j) {
        InteractionEvent interactionEvent;
        synchronized (this) {
            if (j == 0) {
                interactionEvent = (InteractionEvent) this.a.query(InteractionEvent.class).withSelection("eventType = ? AND objectId = ?", new String[]{Integer.toString(eventType.ordinal()), str}).limit(1).get();
            } else {
                interactionEvent = (InteractionEvent) this.a.query(InteractionEvent.class).withSelection("eventType = ? AND objectId = ? AND timestamp >= ?", new String[]{Integer.toString(eventType.ordinal()), str, Long.toString(j)}).limit(1).get();
            }
        }
        return interactionEvent;
    }

    public List<InteractionEvent> findEventsRecent(EventType eventType, String str, long j) {
        return this.a.query(InteractionEvent.class).withSelection("eventType = ? AND objectId = ? AND timestamp >= ?", new String[]{Integer.toString(eventType.ordinal()), str, Long.toString(j)}).list();
    }

    public List<InteractionEvent> findInteractionEvents(int i) {
        return this.a.query(InteractionEvent.class).withSelection("notificationType IN (?,?,?,?)", new String[]{"passive", InteractionsApi.B4SNOTIFICATION_TYPE_DEEPLINK, InteractionsApi.B4SNOTIFICATION_TYPE_SIMPLE, InteractionsApi.B4SNOTIFICATION_TYPE_RICH}).orderBy("-timestamp").limit(i).list();
    }

    public InteractionEvent getInteractionEventWithSessionId(String str) {
        B4SLog.d((Object) this, "getInteractionEventWithSessionId interactionSessionId=" + str);
        return (InteractionEvent) this.a.query(InteractionEvent.class).withSelection("interactionSessionId = ?", new String[]{str}).limit(1).get();
    }

    public void registerEvent(EventType eventType, String str, long j) {
        synchronized (this) {
            InteractionEvent interactionEvent = new InteractionEvent();
            interactionEvent.eventType = eventType.ordinal();
            interactionEvent.objectId = str;
            interactionEvent.timestamp = j;
            interactionEvent.interactionSessionId = "";
            interactionEvent.campaignName = "";
            interactionEvent.interactionName = "";
            interactionEvent.notificationType = "";
            interactionEvent.message = "";
            interactionEvent.longitude = 0.0d;
            interactionEvent.latitude = 0.0d;
            interactionEvent.radius = 0;
            interactionEvent.exitRadius = 0;
            interactionEvent.incomingInteraction = false;
            interactionEvent.outgoingInteraction = false;
            interactionEvent.locationAccuracy = 0.0f;
            interactionEvent.shopLatitude = 0.0d;
            interactionEvent.shopLongitude = 0.0d;
            interactionEvent.shopCity = "";
            interactionEvent.shopName = "";
            interactionEvent.shopZipCode = "";
            this.a.put(interactionEvent);
        }
    }

    public void registerEvent(EventType eventType, String str, long j, double d, double d2, String str2, String str3, String str4) {
        synchronized (this) {
            InteractionEvent interactionEvent = new InteractionEvent();
            interactionEvent.eventType = eventType.ordinal();
            interactionEvent.objectId = str;
            interactionEvent.timestamp = j;
            interactionEvent.interactionSessionId = "";
            interactionEvent.campaignName = "";
            interactionEvent.interactionName = "";
            interactionEvent.notificationType = "";
            interactionEvent.message = "";
            interactionEvent.longitude = 0.0d;
            interactionEvent.latitude = 0.0d;
            interactionEvent.radius = 0;
            interactionEvent.exitRadius = 0;
            interactionEvent.incomingInteraction = false;
            interactionEvent.outgoingInteraction = false;
            interactionEvent.locationAccuracy = 0.0f;
            interactionEvent.shopLatitude = d;
            interactionEvent.shopLongitude = d2;
            interactionEvent.shopCity = str2;
            interactionEvent.shopName = str3;
            interactionEvent.shopZipCode = str4;
            this.a.put(interactionEvent);
        }
    }

    public void registerEvent(EventType eventType, String str, long j, String str2, String str3, String str4, String str5, String str6, String str7, double d, double d2, int i, int i2, boolean z, boolean z2, float f, double d3, double d4, String str8, String str9, String str10) {
        synchronized (this) {
            InteractionEvent interactionEvent = new InteractionEvent();
            interactionEvent.eventType = eventType.ordinal();
            interactionEvent.objectId = str;
            interactionEvent.timestamp = j;
            interactionEvent.interactionSessionId = str2;
            interactionEvent.campaignName = str3;
            interactionEvent.interactionName = str4;
            interactionEvent.notificationType = str6;
            interactionEvent.interactionType = str5;
            interactionEvent.message = str7;
            interactionEvent.longitude = d2;
            interactionEvent.latitude = d;
            interactionEvent.radius = i;
            interactionEvent.exitRadius = i2;
            interactionEvent.incomingInteraction = z;
            interactionEvent.outgoingInteraction = z2;
            interactionEvent.locationAccuracy = f;
            interactionEvent.shopLatitude = d3;
            interactionEvent.shopLongitude = d4;
            interactionEvent.shopCity = str8;
            interactionEvent.shopName = str9;
            interactionEvent.shopZipCode = str10;
            this.a.put(interactionEvent);
        }
    }

    public void registerLatestEvent(EventType eventType, String str, long j) {
        synchronized (this) {
            InteractionEvent interactionEvent = (InteractionEvent) this.a.query(InteractionEvent.class).withSelection("eventType = ? AND objectId = ?", new String[]{Integer.toString(eventType.ordinal()), str}).get();
            if (interactionEvent != null) {
                interactionEvent.timestamp = j;
                this.a.put(interactionEvent);
            } else {
                interactionEvent = new InteractionEvent();
                interactionEvent.eventType = eventType.ordinal();
                interactionEvent.objectId = str;
                interactionEvent.timestamp = j;
                this.a.put(interactionEvent);
            }
        }
    }
}
