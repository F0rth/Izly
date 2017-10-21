package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class hl$d extends BroadcastReceiver {
    final /* synthetic */ hl a;

    private hl$d(hl hlVar) {
        this.a = hlVar;
    }

    public final void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("fr.smoney.android.izly.sessionState", -1);
        if (intExtra == 0) {
            this.a.i.clear();
            this.a.i.notifyDataSetChanged();
            this.a.d();
        } else if (intExtra == 1) {
            this.a.a(true, true, false);
        } else if (intExtra == 2) {
            this.a.a(hw.a(this.a.d, this.a, ie.ConnexionErrorDuringIsSessionValid));
        }
    }
}
