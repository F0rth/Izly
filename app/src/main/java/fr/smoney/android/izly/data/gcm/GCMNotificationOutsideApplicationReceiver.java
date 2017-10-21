package fr.smoney.android.izly.data.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import defpackage.is;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.ui.HomeActivity;
import fr.smoney.android.izly.ui.LoginActivity;

public class GCMNotificationOutsideApplicationReceiver extends BroadcastReceiver {
    private static final String a = GCMNotificationOutsideApplicationReceiver.class.getSimpleName();

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("fr.smoney.android.izly.intentExtra.notifMessage");
        if (SmoneyApplication.a.b != null) {
            Intent a = is.a(context, HomeActivity.class);
            a.setFlags(335544320);
            a.putExtra("fr.smoney.android.izly.intentExtra.notifMessage", stringExtra);
            a.putExtra("fr.smoney.android.izly.extras.launchActivity", 1);
            context.startActivity(a);
            return;
        }
        a = is.a(context, LoginActivity.class);
        a.setFlags(335544320);
        a.putExtra("fr.smoney.android.izly.intentExtra.notifMessage", stringExtra);
        a.putExtra("fr.smoney.android.izly.extras.launchActivity", 1);
        context.startActivity(a);
    }
}
