package fr.smoney.android.izly.data.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import fr.smoney.android.izly.SmoneyApplication;

public class GCMNotificationsDispatcherReceiver extends BroadcastReceiver {
    private static final String a = GCMNotificationsDispatcherReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        if (SmoneyApplication.a != null) {
            SmoneyApplication.a.cs = true;
            SmoneyApplication.a.ct = true;
            SmoneyApplication.a.cu = true;
            SmoneyApplication.a.cv = true;
        }
        Intent intent2 = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_DISPATCHED");
        intent2.putExtra("fr.smoney.android.izly.intentExtra.notifMessage", intent.getStringExtra("fr.smoney.android.izly.intentExtra.notifMessage"));
        context.sendOrderedBroadcast(intent2, null);
    }
}
