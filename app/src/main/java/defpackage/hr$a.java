package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class hr$a extends BroadcastReceiver {
    final /* synthetic */ hr a;

    private hr$a(hr hrVar) {
        this.a = hrVar;
    }

    public final void onReceive(Context context, Intent intent) {
        if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
            this.a.o();
        }
    }
}
