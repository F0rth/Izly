package defpackage;

import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

final class hk$2 implements OnGlobalLayoutListener {
    final /* synthetic */ View a;
    final /* synthetic */ hk b;

    hk$2(hk hkVar, View view) {
        this.b = hkVar;
        this.a = view;
    }

    public final void onGlobalLayout() {
        if (VERSION.SDK_INT < 16) {
            this.a.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            this.a.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        this.b.p();
    }
}
