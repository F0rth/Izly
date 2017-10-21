package defpackage;

import android.view.View;

final class h implements Runnable {
    private /* synthetic */ String a;
    private /* synthetic */ View b;
    private /* synthetic */ e c;

    h(e eVar, String str, View view) {
        this.c = eVar;
        this.a = str;
        this.b = view;
    }

    public final void run() {
        int i = 4;
        if (this.a.equals("fade_in") && this.c.b.d == ox.Top && this.b.getId() != 13 && this.b.getId() != 15 && this.b.getId() != 14 && this.b.getId() != 16) {
            this.b.setVisibility(4);
        } else if (!this.a.equals("fade_in") || (!(this.c.b.d == ox.Left || this.c.b.d == ox.Right) || this.b.getId() == 33 || this.b.getId() == 35 || this.b.getId() == 34 || this.b.getId() == 36)) {
            View view = this.b;
            if (this.a.equals("fade_in")) {
                i = 0;
            }
            view.setVisibility(i);
            this.b.bringToFront();
        } else {
            this.b.setVisibility(4);
        }
    }
}
