package fr.smoney.android.izly.data.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat.BigTextStyle;
import android.support.v4.app.NotificationCompat.Builder;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.ui.HomeActivity;
import fr.smoney.android.izly.ui.LoginActivity;

public class GCMMessageReceiver extends BroadcastReceiver {
    private static final String a = GCMMessageReceiver.class.getSimpleName();

    private static P2PPayCommerceInfos a(Intent intent) {
        String stringExtra = intent.getStringExtra("amount");
        String stringExtra2 = intent.getStringExtra("appId");
        String stringExtra3 = intent.getStringExtra("receiver");
        String stringExtra4 = intent.getStringExtra("transactionId");
        String stringExtra5 = intent.getStringExtra("signature");
        String stringExtra6 = intent.getStringExtra("message");
        String stringExtra7 = intent.getStringExtra("callbackParam");
        String stringExtra8 = intent.getStringExtra("editableFieldParam");
        P2PPayCommerceInfos p2PPayCommerceInfos = new P2PPayCommerceInfos();
        try {
            double parseDouble = Double.parseDouble(stringExtra) / 100.0d;
            if (parseDouble >= 0.0d) {
                p2PPayCommerceInfos.b = parseDouble;
            }
        } catch (NumberFormatException e) {
        }
        p2PPayCommerceInfos.d = stringExtra2;
        p2PPayCommerceInfos.a = stringExtra3;
        p2PPayCommerceInfos.e = stringExtra4;
        p2PPayCommerceInfos.g = stringExtra5;
        p2PPayCommerceInfos.j = stringExtra6;
        p2PPayCommerceInfos.r = Boolean.parseBoolean(stringExtra7);
        if (stringExtra8 != null && stringExtra8.length() > 0) {
            int parseInt = Integer.parseInt(stringExtra8);
            p2PPayCommerceInfos.q = parseInt;
            p2PPayCommerceInfos.o = LoginActivity.a(parseInt, 1);
            p2PPayCommerceInfos.n = LoginActivity.a(parseInt, 2);
            p2PPayCommerceInfos.p = LoginActivity.a(parseInt, 4);
        }
        return p2PPayCommerceInfos;
    }

    public void onReceive(Context context, Intent intent) {
        Notification notification = null;
        if ("com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            CharSequence stringExtra;
            CharSequence stringExtra2;
            String stringExtra3 = intent.getStringExtra("message_notif");
            String stringExtra4 = intent.getStringExtra("type");
            String stringExtra5 = intent.getStringExtra("a4surl");
            if (stringExtra5 != null) {
                stringExtra = intent.getStringExtra("a4stitle");
                stringExtra2 = intent.getStringExtra("a4scontent");
                stringExtra3 = "91";
            } else {
                Object obj = stringExtra3;
                stringExtra3 = stringExtra4;
                stringExtra = null;
            }
            if (stringExtra3 != null) {
                PendingIntent activity;
                Intent intent2;
                switch (Integer.parseInt(stringExtra3)) {
                    case 90:
                        intent2 = new Intent(context, LoginActivity.class);
                        intent2.putExtra("startFromWebPayement", true);
                        intent2.putExtra("startFromWebPayementObject", a(intent));
                        activity = PendingIntent.getActivity(context, 0, intent2, 0);
                        break;
                    case 91:
                        Intent intent3 = new Intent(context, SmoneyApplication.a.b != null ? HomeActivity.class : LoginActivity.class);
                        intent3.setData(Uri.parse(stringExtra5));
                        activity = PendingIntent.getActivity(context, 0, intent3, 0);
                        break;
                    default:
                        intent2 = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_DISPATCHER");
                        intent2.putExtra("fr.smoney.android.izly.intentExtra.notifMessage", stringExtra2);
                        activity = PendingIntent.getBroadcast(context, 0, intent2, 134217728);
                        break;
                }
                if (stringExtra == null) {
                    stringExtra = context.getString(2131230841);
                }
                Builder builder = new Builder(context);
                builder.setContentText(stringExtra2);
                builder.setContentTitle(stringExtra);
                builder.setContentIntent(activity);
                builder.setSmallIcon(R.drawable.logo_application);
                BigTextStyle bigTextStyle = new BigTextStyle();
                bigTextStyle.setBigContentTitle(stringExtra).bigText(stringExtra2);
                builder.setStyle(bigTextStyle);
                notification = builder.build();
                notification.flags |= 16;
                notification.defaults |= 1;
                notification.defaults |= 4;
            }
            if (notification != null) {
                ((NotificationManager) context.getSystemService("notification")).notify(1, notification);
            }
            ca.a(context);
        }
    }
}
