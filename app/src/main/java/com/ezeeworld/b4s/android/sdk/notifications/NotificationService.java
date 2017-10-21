package com.ezeeworld.b4s.android.sdk.notifications;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.provider.Settings.System;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.app.NotificationCompat.InboxStyle;
import android.support.v4.app.NotificationCompat.Style;
import android.text.TextUtils;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringStatus;
import com.ezeeworld.b4s.android.sdk.server.Beacon;
import com.ezeeworld.b4s.android.sdk.server.Group;
import com.ezeeworld.b4s.android.sdk.server.Interaction;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;
import com.ezeeworld.b4s.android.sdk.server.Shop;

import de.greenrobot.event.NoSubscriberEvent;

public class NotificationService extends IntentService {
    public static final String BROADCAST_DEEP_LINK = "com.ezeeworld.b4s.android.sdk.notifications.DEEP_LINK";
    public static final String INTENT_ACTIONID = "b4s_intent_actionid";
    public static final String INTENT_BEACONID = "b4s_intent_beaconid";
    public static final String INTENT_BEACONNAME = "b4s_intent_beaconname";
    public static final String INTENT_BROADCAST = "b4s_intent_broadcast";
    public static final String INTENT_CAMPAIGN = "b4s_intent_campaign";
    public static final String INTENT_CAMPAIGNNAME = "b4s_intent_campaign_name";
    public static final String INTENT_CAMPAIGNTYPE = "b4s_intent_campaign_type";
    public static final String INTENT_DATA = "b4s_intent_data";
    public static final String INTENT_DISTANCE = "b4s_intent_distance";
    public static final String INTENT_GROUPID = "b4s_intent_groupid";
    public static final String INTENT_GROUPNAME = "b4s_intent_groupname";
    public static final String INTENT_INTERACTION = "b4s_intent_interaction";
    public static final String INTENT_INTERACTIONNAME = "b4s_intent_interaction_name";
    public static final String INTENT_MESSAGE = "b4s_intent_message";
    public static final String INTENT_NOTIFICATION = "b4s_intent_notification";
    public static final String INTENT_SESSION = "b4s_intent_session";
    public static final String INTENT_SHOPCLIENTREF = "b4s_intent_shopclientref";
    public static final String INTENT_SHOPID = "b4s_intent_shopid";
    public static final String INTENT_SHOPNAME = "b4s_intent_shopname";
    public static final String INTENT_SHOW = "b4s_notification_show";
    public static final String INTENT_TITLE = "b4s_intent_title";
    public static final String INTENT_WRAPPED = "b4s_intent_wrapped";
    private static NotificationModifier a;
    private static DeepLinkStyle b = DeepLinkStyle.LaunchActivityClearTask;

    public enum DeepLinkStyle {
        LaunchActivityClearTask,
        LaunchActivityDirect,
        BroadcastReceiver
    }

    public interface NotificationModifier {
        String modifyNotificationMessage(Bundle bundle);

        String modifyNotificationTitle(Bundle bundle);
    }

    public NotificationService() {
        super("B4S NotificationService");
    }

    private static String a(String str, Bundle bundle) {
        CharSequence string = bundle.getString(INTENT_BEACONNAME);
        if (!TextUtils.isEmpty(string)) {
            str = str.replace("<BEACON>", string);
        }
        string = bundle.getString(INTENT_SHOPNAME);
        if (!TextUtils.isEmpty(string)) {
            str = str.replace("<SHOP>", string);
        }
        string = bundle.getString(INTENT_GROUPNAME);
        if (!TextUtils.isEmpty(string)) {
            str = str.replace("<GROUP>", string);
        }
        B4SSettings b4SSettings = B4SSettings.get();
        String replace = (b4SSettings == null || b4SSettings.getCustomerLastName() == null) ? str : str.replace("<NAME>", b4SSettings.getCustomerLastName());
        return (b4SSettings == null || b4SSettings.getCustomerFirstName() == null) ? replace : replace.replace("<FIRSTNAME>", b4SSettings.getCustomerFirstName());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void a(android.content.Context r14, int r15, java.lang.String r16, java.lang.String r17, java.lang.String r18, java.lang.String r19, java.lang.String r20, java.lang.String r21, android.os.Bundle r22) {
        /*
        r2 = 0;
        r3 = com.ezeeworld.b4s.android.sdk.server.Api2.get();	 Catch:{ Exception -> 0x0209 }
        r3 = r3.getJackson();	 Catch:{ Exception -> 0x0209 }
        r0 = r17;
        r3 = r3.readTree(r0);	 Catch:{ Exception -> 0x0209 }
        r2 = "title";
        r2 = r3.has(r2);	 Catch:{ Exception -> 0x015f }
        if (r2 == 0) goto L_0x0029;
    L_0x0017:
        r2 = "title";
        r2 = r3.get(r2);	 Catch:{ Exception -> 0x015f }
        r2 = r2.asText();	 Catch:{ Exception -> 0x015f }
        r4 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x015f }
        if (r4 != 0) goto L_0x0029;
    L_0x0027:
        r18 = r2;
    L_0x0029:
        r2 = "action";
        r2 = r3.has(r2);	 Catch:{ Exception -> 0x015f }
        if (r2 == 0) goto L_0x0206;
    L_0x0031:
        r2 = "action";
        r2 = r3.get(r2);	 Catch:{ Exception -> 0x015f }
        r2 = r2.asText();	 Catch:{ Exception -> 0x015f }
        r4 = android.text.TextUtils.isEmpty(r2);	 Catch:{ Exception -> 0x015f }
        if (r4 != 0) goto L_0x0206;
    L_0x0041:
        if (r3 == 0) goto L_0x0167;
    L_0x0043:
        r4 = "webaction";
        r4 = r3.has(r4);
        if (r4 == 0) goto L_0x0167;
    L_0x004b:
        r2 = "webaction";
        r2 = r3.get(r2);
        r2 = r2.asText();
        r5 = com.ezeeworld.b4s.android.sdk.monitor.WebViewInteractionActivity.newStartIntent(r14, r2);
        r2 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r5.setFlags(r2);
        r4 = 0;
        r2 = 0;
        r6 = r4;
        r7 = r5;
        r5 = r2;
    L_0x0064:
        if (r3 == 0) goto L_0x01ee;
    L_0x0066:
        r2 = "alert";
        r2 = r3.has(r2);
        if (r2 == 0) goto L_0x01ee;
    L_0x006e:
        r2 = "alert";
        r2 = r3.get(r2);
        r2 = r2.asInt();
        r4 = 1;
        if (r2 != r4) goto L_0x01ee;
    L_0x007b:
        r2 = 1;
        r4 = r2;
    L_0x007d:
        if (r3 == 0) goto L_0x01f2;
    L_0x007f:
        r2 = "actionbutton";
        r2 = r3.has(r2);
        if (r2 == 0) goto L_0x01f2;
    L_0x0087:
        r2 = "actionbutton";
        r2 = r3.get(r2);
        r2 = r2.asText();
        r11 = r2;
    L_0x0092:
        if (r3 == 0) goto L_0x01f6;
    L_0x0094:
        r2 = "cancelbutton";
        r2 = r3.has(r2);
        if (r2 == 0) goto L_0x01f6;
    L_0x009c:
        r2 = "cancelbutton";
        r2 = r3.get(r2);
        r2 = r2.asText();
        r10 = r2;
    L_0x00a7:
        if (r3 == 0) goto L_0x01fa;
    L_0x00a9:
        r2 = "secondTitle";
        r2 = r3.has(r2);
        if (r2 == 0) goto L_0x01fa;
    L_0x00b1:
        r2 = "secondTitle";
        r2 = r3.get(r2);
        r2 = r2.asText();
        r9 = r2;
    L_0x00bc:
        if (r3 == 0) goto L_0x01fe;
    L_0x00be:
        r2 = "secondText";
        r2 = r3.has(r2);
        if (r2 == 0) goto L_0x01fe;
    L_0x00c6:
        r2 = "secondText";
        r2 = r3.get(r2);
        r2 = r2.asText();
    L_0x00d0:
        r0 = r18;
        r1 = r22;
        r8 = a(r0, r1);
        r0 = r16;
        r1 = r22;
        r3 = a(r0, r1);
        r0 = r22;
        r9 = a(r9, r0);
        r0 = r22;
        r12 = a(r2, r0);
        r2 = "b4s_intent_title";
        r0 = r22;
        r0.putString(r2, r8);
        r2 = "b4s_intent_message";
        r0 = r22;
        r0.putString(r2, r3);
        r2 = a;
        if (r2 == 0) goto L_0x0202;
    L_0x00fe:
        r2 = a;
        r0 = r22;
        r3 = r2.modifyNotificationTitle(r0);
        r2 = a;
        r0 = r22;
        r2 = r2.modifyNotificationMessage(r0);
        r8 = "b4s_intent_title";
        r0 = r22;
        r0.putString(r8, r3);
        r8 = "b4s_intent_message";
        r0 = r22;
        r0.putString(r8, r2);
    L_0x011c:
        if (r6 != 0) goto L_0x0123;
    L_0x011e:
        r0 = r22;
        r7.putExtras(r0);
    L_0x0123:
        r6 = new com.ezeeworld.b4s.android.sdk.notifications.PreparedNotification;
        r6.<init>();
        r6.a = r15;
        r0 = r20;
        r6.b = r0;
        r0 = r21;
        r6.c = r0;
        r0 = r19;
        r6.d = r0;
        r6.e = r4;
        r6.f = r3;
        r6.g = r2;
        r6.h = r9;
        r6.i = r12;
        r6.j = r11;
        r6.k = r10;
        r6.l = r7;
        r6.m = r5;
        r2 = new android.content.Intent;
        r3 = com.ezeeworld.b4s.android.sdk.notifications.NotificationService.class;
        r2.<init>(r14, r3);
        r3 = "com.ezeeworld.b4s.android.sdk.notifications.B4S_NOTIFY";
        r2 = r2.setAction(r3);
        r3 = "b4s_intent_notification";
        r2 = r2.putExtra(r3, r6);
        r14.startService(r2);
        return;
    L_0x015f:
        r2 = move-exception;
        r2 = r3;
    L_0x0161:
        r3 = 0;
        r13 = r3;
        r3 = r2;
        r2 = r13;
        goto L_0x0041;
    L_0x0167:
        if (r3 == 0) goto L_0x0193;
    L_0x0169:
        r4 = "extwebaction";
        r4 = r3.has(r4);
        if (r4 == 0) goto L_0x0193;
    L_0x0171:
        r5 = new android.content.Intent;
        r2 = "android.intent.action.VIEW";
        r4 = "extwebaction";
        r4 = r3.get(r4);
        r4 = r4.asText();
        r4 = android.net.Uri.parse(r4);
        r5.<init>(r2, r4);
        r2 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r5.setFlags(r2);
        r4 = 1;
        r2 = 0;
        r6 = r4;
        r7 = r5;
        r5 = r2;
        goto L_0x0064;
    L_0x0193:
        if (r2 == 0) goto L_0x01cf;
    L_0x0195:
        r4 = "b4s_intent_actionid";
        r0 = r22;
        r0.putString(r4, r2);
        r2 = b;
        r4 = com.ezeeworld.b4s.android.sdk.notifications.NotificationService.DeepLinkStyle.BroadcastReceiver;
        if (r2 != r4) goto L_0x01b0;
    L_0x01a2:
        r5 = new android.content.Intent;
        r2 = "com.ezeeworld.b4s.android.sdk.notifications.DEEP_LINK";
        r5.<init>(r2);
        r4 = 0;
        r2 = 1;
        r6 = r4;
        r7 = r5;
        r5 = r2;
        goto L_0x0064;
    L_0x01b0:
        r2 = r14.getPackageManager();
        r4 = r14.getPackageName();
        r2 = r2.getLaunchIntentForPackage(r4);
        r4 = b;
        r5 = com.ezeeworld.b4s.android.sdk.notifications.NotificationService.DeepLinkStyle.LaunchActivityClearTask;
        if (r4 != r5) goto L_0x01e7;
    L_0x01c2:
        r4 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r2.setFlags(r4);
        r5 = 0;
        r4 = 0;
        r6 = r5;
        r7 = r2;
        r5 = r4;
        goto L_0x0064;
    L_0x01cf:
        r2 = r14.getPackageManager();
        r4 = r14.getPackageName();
        r2 = r2.getLaunchIntentForPackage(r4);
        r4 = b;
        r5 = com.ezeeworld.b4s.android.sdk.notifications.NotificationService.DeepLinkStyle.LaunchActivityDirect;
        if (r4 == r5) goto L_0x01e7;
    L_0x01e1:
        r4 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;
        r2.setFlags(r4);
    L_0x01e7:
        r5 = 0;
        r4 = 0;
        r6 = r5;
        r7 = r2;
        r5 = r4;
        goto L_0x0064;
    L_0x01ee:
        r2 = 0;
        r4 = r2;
        goto L_0x007d;
    L_0x01f2:
        r2 = 0;
        r11 = r2;
        goto L_0x0092;
    L_0x01f6:
        r2 = 0;
        r10 = r2;
        goto L_0x00a7;
    L_0x01fa:
        r9 = r18;
        goto L_0x00bc;
    L_0x01fe:
        r2 = r16;
        goto L_0x00d0;
    L_0x0202:
        r2 = r3;
        r3 = r8;
        goto L_0x011c;
    L_0x0206:
        r2 = 0;
        goto L_0x0041;
    L_0x0209:
        r3 = move-exception;
        goto L_0x0161;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.ezeeworld.b4s.android.sdk.notifications.NotificationService.a(android.content.Context, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, android.os.Bundle):void");
    }

    private void a(PreparedNotification preparedNotification) {
        Intent intent = new Intent(this, NotificationService.class);
        intent.setAction("com.ezeeworld.b4s.android.sdk.notifications.OPEN_NOTIFICATION");
        intent.putExtra(INTENT_WRAPPED, preparedNotification.l);
        intent.putExtra(INTENT_BROADCAST, preparedNotification.m);
        intent.putExtra(INTENT_SESSION, preparedNotification.c);
        intent.putExtra(INTENT_BEACONNAME, preparedNotification.d);
        intent.putExtra(INTENT_INTERACTION, preparedNotification.b);
        Builder autoCancel = new Builder(this).setContentTitle(preparedNotification.f).setContentIntent(PendingIntent.getService(this, preparedNotification.a, intent, 268435456)).setContentText(preparedNotification.g).setAutoCancel(true);
        B4SSettings b4SSettings = B4SSettings.get();
        Style inboxStyle = new InboxStyle();
        String[] strArr = new String[2];
        strArr[0] = preparedNotification.g;
        inboxStyle.setBigContentTitle(preparedNotification.f);
        inboxStyle.addLine(strArr[0]);
        autoCancel.setStyle(inboxStyle);
        if (b4SSettings.shouldVibrateOnNotification()) {
            autoCancel.setVibrate(new long[]{0, 500});
        }
        if (b4SSettings.shouldRingOnNotification()) {
            autoCancel.setSound(System.DEFAULT_NOTIFICATION_URI);
        }
        autoCancel.setColor(b4SSettings.getNotificationBackgroundColor());
        autoCancel.setPriority(2);
        if (b4SSettings.notificationSoundStatus()) {
            autoCancel.setSound(RingtoneManager.getDefaultUri(2));
        }
        if (b4SSettings.getCustomNotificationIcon() != null) {
            autoCancel.setSmallIcon(b4SSettings.getCustomNotificationIcon().intValue());
        } else {
            autoCancel.setSmallIcon(getApplicationInfo().icon);
        }
        ((NotificationManager) getSystemService("notification")).notify(preparedNotification.a, autoCancel.build());
        if (preparedNotification.c != null) {
            new a(preparedNotification.c, preparedNotification.d, preparedNotification.b, InteractionsApi.B4SSATUS_SENT).execute(new Context[]{this});
        }
    }

    public static void notifyFromMatch(Context context, Interaction interaction, Beacon beacon, IBeaconData iBeaconData, Shop shop, Group group, String str, Double d) {
        MonitoringStatus.matchedInteraction(new MatchedInteration(System.currentTimeMillis(), interaction.sName, interaction.sCampaignName));
        if (interaction.sNotificationType == null || !interaction.sNotificationType.equals("passive")) {
            String str2 = interaction.sPushText;
            String str3 = interaction.sPushData;
            if (interaction.bCustomPushEnabled) {
                if (beacon != null && !TextUtils.isEmpty(beacon.sPushText)) {
                    str2 = beacon.sPushText;
                } else if (shop != null && !TextUtils.isEmpty(shop.sPushText)) {
                    str2 = shop.sPushText;
                } else if (!(group == null || TextUtils.isEmpty(group.sPushText))) {
                    str2 = group.sPushText;
                }
                if (!(beacon == null || TextUtils.isEmpty(beacon.sPushData))) {
                    str3 = beacon.sPushData;
                }
            } else if (!(group == null || TextUtils.isEmpty(group.sPushText))) {
                str2 = group.sPushText;
            }
            int hashCode = interaction.sInteractionId.hashCode();
            String str4 = beacon == null ? null : beacon.sInnerName;
            Bundle bundle = new Bundle();
            bundle.putInt(INTENT_SHOW, hashCode);
            bundle.putString(INTENT_INTERACTION, interaction.sInteractionId);
            bundle.putString(INTENT_INTERACTIONNAME, interaction.sName);
            bundle.putString(INTENT_CAMPAIGN, interaction.sCampaignId);
            bundle.putString(INTENT_CAMPAIGNNAME, interaction.sCampaignName);
            bundle.putString(INTENT_CAMPAIGNTYPE, interaction.sCampaignType);
            bundle.putString(INTENT_DATA, str3);
            bundle.putString(INTENT_SESSION, str);
            if (beacon != null) {
                bundle.putString(INTENT_BEACONNAME, beacon.sName);
            }
            if (d != null) {
                bundle.putDouble(INTENT_DISTANCE, d.doubleValue());
            }
            if (shop != null) {
                bundle.putString(INTENT_SHOPID, shop.sShopId);
                bundle.putString(INTENT_SHOPNAME, shop.sName);
                bundle.putString(INTENT_SHOPCLIENTREF, shop.sClientRef);
            }
            if (group != null) {
                bundle.putString(INTENT_GROUPID, group.sGroupId);
                bundle.putString(INTENT_GROUPNAME, group.sName);
            }
            B4SLog.i(NotificationService.class.getSimpleName(), "Send interaction notification for " + interaction.sName + "(" + interaction.sInteractionId + " / " + str + ") with text '" + str2 + "' and data '" + str3 + "'");
            a(context, hashCode, str2, str3, interaction.sName, str4, interaction.sInteractionId, str, bundle);
            return;
        }
        B4SLog.i(NotificationService.class.getSimpleName(), "No notification shown for " + interaction.sName + " as it is a PASSIVE interaction");
    }

    public static void notifyFromPush(Context context, int i, String str, String str2) {
        Bundle bundle = new Bundle();
        bundle.putInt(INTENT_SHOW, i);
        bundle.putString(INTENT_DATA, str2);
        B4SLog.i(NotificationService.class.getSimpleName(), "Send push notification for with text '" + str + "' and data '" + str2 + "'");
        a(context, i, str, str2, "", null, null, null, bundle);
    }

    public static void registerDeepLinkStyle(DeepLinkStyle deepLinkStyle) {
        b = deepLinkStyle;
    }

    public static void registerNotificationModifier(NotificationModifier notificationModifier) {
        a = notificationModifier;
    }

    public void onEvent(NoSubscriberEvent noSubscriberEvent) {
        Object obj = noSubscriberEvent.originalEvent;
        if (obj instanceof PreparedNotification) {
            a((PreparedNotification) obj);
        }
    }

    protected void onHandleIntent(Intent intent) {
        if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.notifications.B4S_NOTIFY")) {
            PreparedNotification preparedNotification = (PreparedNotification) intent.getParcelableExtra(INTENT_NOTIFICATION);
            EventBus.get().register(this);
            EventBus.get().post(preparedNotification);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
            }
            EventBus.get().unregister(this);
        } else if (intent.getAction() != null && intent.getAction().equals("com.ezeeworld.b4s.android.sdk.notifications.OPEN_NOTIFICATION")) {
            Intent intent2 = (Intent) intent.getParcelableExtra(INTENT_WRAPPED);
            boolean booleanExtra = intent.getBooleanExtra(INTENT_BROADCAST, false);
            String stringExtra = intent.getStringExtra(INTENT_SESSION);
            String stringExtra2 = intent.getStringExtra(INTENT_BEACONNAME);
            String stringExtra3 = intent.getStringExtra(INTENT_INTERACTION);
            if (stringExtra != null) {
                new a(stringExtra, stringExtra2, stringExtra3, InteractionsApi.B4SSATUS_OPENED).a();
            }
            if (booleanExtra) {
                sendBroadcast(intent2);
                return;
            }
            intent2.setFlags(268435456);
            startActivity(intent2);
        }
    }
}
