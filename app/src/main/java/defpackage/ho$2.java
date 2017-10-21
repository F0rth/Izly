package defpackage;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

final class ho$2 implements OnTouchListener {
    final /* synthetic */ ho a;

    ho$2(ho hoVar) {
        this.a = hoVar;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        return motionEvent.getAction() == 2;
    }
}
