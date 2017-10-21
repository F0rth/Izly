package defpackage;

import android.view.View;
import android.view.View.OnFocusChangeListener;

final class hy$2 implements OnFocusChangeListener {
    final /* synthetic */ hy a;

    hy$2(hy hyVar) {
        this.a = hyVar;
    }

    public final void onFocusChange(View view, boolean z) {
        if (z) {
            this.a.h.post(new hy$2$1(this));
            this.a.h.setOnFocusChangeListener(null);
        }
    }
}
