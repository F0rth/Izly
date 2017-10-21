package defpackage;

import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

final class hz$3 implements OnEditorActionListener {
    final /* synthetic */ hz a;

    hz$3(hz hzVar) {
        this.a = hzVar;
    }

    public final boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        hz.a(this.a);
        return false;
    }
}
