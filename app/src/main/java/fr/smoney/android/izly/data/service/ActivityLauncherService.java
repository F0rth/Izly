package fr.smoney.android.izly.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import defpackage.is;
import fr.smoney.android.izly.ui.LoginActivity;

public class ActivityLauncherService extends Service {
    public IBinder onBind(Intent intent) {
        return null;
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        String stringExtra = intent.getStringExtra("fr.smoney.android.izly.extras.serviceActivityLauncher");
        if (stringExtra != null && stringExtra.equals("fr.smoney.android.izly.ui.PhoneValidationActivity")) {
            Intent a = is.a(this, LoginActivity.class);
            a.setData(intent.getData());
            a.putExtra("fr.smoney.android.izly.extras.startByBrowser", false);
            a.setFlags(335544320);
            a.putExtra("fr.smoney.android.izly.extras.launchActivity", 5);
            startActivity(a);
        }
        stopSelf();
        return 2;
    }
}
