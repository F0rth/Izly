package defpackage;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

final class hi$6 implements OnTouchListener {
    final /* synthetic */ hi a;

    hi$6(hi hiVar) {
        this.a = hiVar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return motionEvent.getAction() == 2;
    }
}
