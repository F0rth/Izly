package fr.smoney.android.izly.data.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import defpackage.ig;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {
    private ig a;
    private boolean b = false;
    private boolean c = false;
    private boolean d;

    public ConnectivityBroadcastReceiver(ig igVar) {
        this.a = igVar;
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.net.conn.CONNECTIVITY_CHANGE") && this.a != null) {
            boolean booleanExtra = intent.getBooleanExtra("noConnectivity", false);
            this.b = booleanExtra;
            if (booleanExtra) {
                if (this.c) {
                    this.a.m();
                    this.c = false;
                }
                if (!this.d) {
                    this.a.m();
                    this.d = true;
                    return;
                }
                return;
            }
            if (!this.c) {
                this.a.l();
                this.c = true;
            }
            if (!this.d) {
                this.a.l();
                this.d = true;
            }
        }
    }
}
