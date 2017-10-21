package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class hq$b extends BroadcastReceiver {
    final /* synthetic */ hq a;

    private hq$b(hq hqVar) {
        this.a = hqVar;
    }

    public final void onReceive(Context context, Intent intent) {
        int intExtra = intent.getIntExtra("fr.smoney.android.izly.sessionState", -1);
        if (intExtra == 0) {
            this.a.l.clear();
            this.a.l.notifyDataSetChanged();
            this.a.d();
        } else if (intExtra == 1 && !this.a.h) {
            this.a.a(true, true, false);
        } else if (intExtra == 2) {
            this.a.a(hw.a(this.a.d, this.a, ie.ConnexionErrorDuringIsSessionValid));
        }
    }
}
