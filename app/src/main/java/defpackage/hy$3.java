package defpackage;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

final class hy$3 implements OnEditorActionListener {
    final /* synthetic */ hy a;

    hy$3(hy hyVar) {
        this.a = hyVar;
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        hy.a(this.a);
        return false;
    }
}
