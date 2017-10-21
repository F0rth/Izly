package defpackage;

import android.view.View;
import android.view.View.OnFocusChangeListener;

final class hz$2 implements OnFocusChangeListener {
    final /* synthetic */ hz a;

    hz$2(hz hzVar) {
        this.a = hzVar;
    }

    public final void onFocusChange(View view, boolean z) {
        if (z) {
            this.a.b.post(new hz$2$1(this));
            this.a.b.setOnFocusChangeListener(null);
        }
    }
}
