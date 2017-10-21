package defpackage;

import android.widget.TextView;

final class g implements Runnable {
    private /* synthetic */ TextView a;
    private /* synthetic */ String b;

    g(TextView textView, String str) {
        this.a = textView;
        this.b = str;
    }

    public final void run() {
        this.a.setText(this.b);
    }
}
