package fr.smoney.android.izly.data.gcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GCMNotificationInsideApplicationReceiver extends BroadcastReceiver {
    private static final String b = GCMNotificationInsideApplicationReceiver.class.getSimpleName();
    public a a;

    public interface a {
        void a(String str);
    }

    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("fr.smoney.android.izly.intentExtra.notifMessage");
        if (this.a != null) {
            this.a.a(stringExtra);
        }
        abortBroadcast();
    }
}
