package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.g;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class d {
    private static d b;
    private final e a;
    private Context c;

    static class a {

        public static class a {
            public static final List<b> a;

            static {
                List arrayList = new ArrayList();
                arrayList.add(b.InboxMessageListWebservice);
                arrayList.add(b.InboxMessageDetailsWebservice);
                arrayList.add(b.InboxMessageUpdateWebservice);
                a = Collections.unmodifiableList(arrayList);
            }
        }

        public static class b {
            public static final List<b> a;

            static {
                List arrayList = new ArrayList();
                arrayList.add(b.MemberLinkWebservice);
                arrayList.add(b.MemberListWebservice);
                arrayList.add(b.MemberUnlinkWebservice);
                arrayList.add(b.UpdateMemberInfoWebservice);
                a = Collections.unmodifiableList(arrayList);
            }
        }

        public static class c {
            public static final List<b> a;

            static {
                List arrayList = new ArrayList();
                arrayList.add(b.BulkWebservice);
                arrayList.add(b.AuthenticationWebservice);
                arrayList.add(b.VersionTrackingWebservice);
                arrayList.add(b.ListsSubscriptionWebservice);
                arrayList.add(b.ListsWebservice);
                arrayList.add(b.TrackingWebservice);
                arrayList.add(b.ConfigurationWebservice);
                arrayList.add(b.EventWebservice);
                arrayList.add(b.EventLeadWebservice);
                arrayList.add(b.EventCartWebservice);
                arrayList.add(b.EventPurchaseWebservice);
                arrayList.add(b.FacebookProfileWebservice);
                arrayList.add(b.PushTokenWebservice);
                arrayList.add(b.UpdateLocationWebservice);
                arrayList.add(b.GeofencingConfigurationWebservice);
                arrayList.add(b.GeofencingUpdateWebservice);
                arrayList.add(b.BeaconConfigurationWebservice);
                arrayList.add(b.BeaconUpdateWebservice);
                arrayList.add(b.UpdateDeviceInfoWebservice);
                arrayList.add(b.TrackPushWebservice);
                arrayList.add(b.TrackInAppWebservice);
                arrayList.add(b.TrackInboxWebservice);
                arrayList.add(b.InAppConfigurationWebservice);
                arrayList.add(b.ReferrerWebservice);
                arrayList.add(b.WebviewScriptWebservice);
                a = Collections.unmodifiableList(arrayList);
            }
        }
    }

    public enum b {
        DownloadWebservices,
        AuthenticationWebservice,
        UpdateDeviceInfoWebservice,
        UpdateMemberInfoWebservice,
        InAppConfigurationWebservice,
        BulkWebservice,
        VersionTrackingWebservice,
        TrackingWebservice,
        ConfigurationWebservice,
        ReferrerWebservice,
        MemberLinkWebservice,
        MemberUnlinkWebservice,
        MemberListWebservice,
        InboxMessageListWebservice,
        InboxMessageDetailsWebservice,
        InboxMessageUpdateWebservice,
        TrackPushWebservice,
        TrackInAppWebservice,
        TrackInboxWebservice,
        EventWebservice,
        EventLeadWebservice,
        EventCartWebservice,
        EventPurchaseWebservice,
        ListsWebservice,
        ListsDeleteWebservice,
        ListsStatusWebservice,
        ListsSubscriptionWebservice,
        PermissionsWebservice,
        PermissionsWebserviceUpdate,
        PushTokenWebservice,
        UpdateLocationWebservice,
        GeofencingUpdateWebservice,
        GeofencingConfigurationWebservice,
        BeaconConfigurationWebservice,
        BeaconUpdateWebservice,
        FacebookProfileWebservice,
        FacebookTrackingWebservice,
        WebviewScriptWebservice,
        UploadLocalDate,
        UploadConnectionType,
        UploadCarrierName,
        UpdateDeviceInfoCanSendSameKeyValues,
        OfflineInAppDisplay;

        public static b a(String str) {
            return "BMA4SBulk".equals(str) ? BulkWebservice : "BMA4SVersion".equals(str) ? VersionTrackingWebservice : "BMA4SList".equals(str) ? ListsWebservice : "BMA4SListListing".equals(str) ? ListsSubscriptionWebservice : "BMA4SPermissions".equals(str) ? PermissionsWebservice : "BMA4SPermissionsUpdate".equals(str) ? PermissionsWebserviceUpdate : "BMA4SAuthentication".equals(str) ? AuthenticationWebservice : "BMA4SReferrer".equals(str) ? ReferrerWebservice : "BMA4SInAppNotification".equals(str) ? InAppConfigurationWebservice : "BMA4SNotificationToken".equals(str) ? PushTokenWebservice : "BMA4SPrivateTracker".equals(str) ? TrackingWebservice : "BMA4SConfig".equals(str) ? ConfigurationWebservice : "BMA4SGeoLoc".equals(str) ? UpdateLocationWebservice : "BMA4SBeacons".equals(str) ? BeaconConfigurationWebservice : "BMA4SBeaconsUpdate".equals(str) ? BeaconUpdateWebservice : "BMA4SGeofencing".equals(str) ? GeofencingConfigurationWebservice : "BMA4SGeofencingUpdate".equals(str) ? GeofencingUpdateWebservice : "BMA4SEvent".equals(str) ? EventWebservice : "BMA4SLead".equals(str) ? EventLeadWebservice : "BMA4SCart".equals(str) ? EventCartWebservice : "BMA4SPurchase".equals(str) ? EventPurchaseWebservice : "BMA4SUpdateDeviceFields".equals(str) ? UpdateDeviceInfoWebservice : "BMA4SUpdateMemberFields".equals(str) ? UpdateMemberInfoWebservice : "BMA4SUploadFacebookProfile".equals(str) ? FacebookProfileWebservice : "BMA4SEventFacebook".equals(str) ? FacebookTrackingWebservice : "BMA4SMemberLink".equals(str) ? MemberLinkWebservice : "BMA4SMemberUnlink".equals(str) ? MemberUnlinkWebservice : "BMA4SMemberList".equals(str) ? MemberListWebservice : "BMA4SLocalDate".equals(str) ? UploadLocalDate : "BMA4SConnectionType".equals(str) ? UploadConnectionType : "BMA4SCarrierName".equals(str) ? UploadCarrierName : "BMA4SNotificationTracking".equals(str) ? TrackPushWebservice : "BMA4SInAppTracking".equals(str) ? TrackInAppWebservice : "BMA4SInboxTracking".equals(str) ? TrackInboxWebservice : "BMA4SInboxList".equals(str) ? InboxMessageListWebservice : "BMA4SInboxDetails".equals(str) ? InboxMessageDetailsWebservice : "BMA4SInboxUpdate".equals(str) ? InboxMessageUpdateWebservice : "BMA4SWebviewScriptUrl".equals(str) ? WebviewScriptWebservice : "BMA4SAlwaysUpdateDeviceFields".equals(str) ? UpdateDeviceInfoCanSendSameKeyValues : "BMA4SOfflineInAppDisplay".equals(str) ? OfflineInAppDisplay : null;
        }
    }

    private d(Context context) {
        this.a = new e(context);
        this.c = context;
    }

    public static d a(Context context) {
        d dVar;
        synchronized (d.class) {
            try {
                if (b == null) {
                    b = new d(context.getApplicationContext());
                }
                dVar = b;
            } catch (Throwable th) {
                Class cls = d.class;
            }
        }
        return dVar;
    }

    public final String a(b bVar) {
        b a;
        if (bVar == b.DownloadWebservices) {
            a = b.a(this.c);
            return a.I().equalsIgnoreCase("development") ? "http://api.ad4s.local:8000/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|" : a.I().equalsIgnoreCase("preproduction") ? "http://preprodapi.a4.tl/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|" : "https://api|SERVER|.accengage.com/routes?partnerId=|partnerId|&sharedId=|sharedId|&version=|version|";
        } else if (bVar != b.WebviewScriptWebservice || this.a.a(bVar) != null) {
            return this.a.a(bVar);
        } else {
            a = b.a(this.c);
            return a.I().equalsIgnoreCase("development") ? "http://apptrk.ad4s.local/api/event/?partnerId=|partnerId|" : a.I().equalsIgnoreCase("preproduction") ? "http://preprodapptrk.a4.tl/api/event/?partnerId=|partnerId|" : "https://apptrk.a4.tl/api/event/?partnerId=|partnerId|";
        }
    }

    public final List<b> a() {
        List arrayList = new ArrayList();
        arrayList.addAll(c.a);
        arrayList.addAll(b.a);
        arrayList.addAll(a.a);
        return Collections.unmodifiableList(arrayList);
    }

    public final void a(b bVar, int i) {
        this.a.a(bVar, i);
    }

    public final void a(b bVar, long j) {
        this.a.b(bVar, j);
    }

    public final void a(b bVar, String str) {
        this.a.a(bVar, str);
    }

    public final boolean b() {
        for (b bVar : a()) {
            if (a(bVar) == null) {
                Log.debug(bVar.toString() + " url is missing");
                return true;
            }
        }
        return false;
    }

    public final boolean b(b bVar) {
        long a = g.e().a() / 1000;
        long b = this.a.b(bVar);
        return (b <= 0 || a - b <= 2592000) ? c(bVar) : true;
    }

    public final void c() {
        for (b a : b.values()) {
            this.a.a(a, 0);
        }
    }

    public final boolean c(b bVar) {
        int c = this.a.c(bVar);
        int d = this.a.d(bVar) + 1;
        if (g.e().a() < this.a.e(bVar)) {
            return false;
        }
        if (c <= 1 || d >= c) {
            this.a.c(bVar, 0);
            return true;
        }
        this.a.c(bVar, d);
        return false;
    }

    public final void d() {
        for (b b : b.values()) {
            this.a.b(b, 0);
        }
    }

    public final boolean d(b bVar) {
        return (this.a.b(bVar, -1) == -1 && this.a.c(bVar, -1) == -1) ? false : true;
    }

    public final void e(b bVar) {
        this.a.a(bVar, g.e().a() / 1000);
    }
}
