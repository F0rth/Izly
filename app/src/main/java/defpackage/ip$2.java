package defpackage;

import android.view.View;
import android.view.View.OnClickListener;

final class ip$2 implements OnClickListener {
    final /* synthetic */ ip a;

    ip$2(ip ipVar) {
        this.a = ipVar;
    }

    public final void onClick(View view) {
        iq$a iq_a = this.a.j;
        if (iq_a != null) {
            int size = this.a.p.size();
            for (int i = 0; i < size; i++) {
                if (view == ((io) this.a.p.get(i)).c.get()) {
                    iq_a.a(this.a, i);
                    break;
                }
            }
        }
        if (this.a.c) {
            this.a.dismiss();
        }
    }
}
