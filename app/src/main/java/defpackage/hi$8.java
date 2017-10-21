package defpackage;

import android.view.View;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

final class hi$8 extends AnimatorListenerAdapter {
    final /* synthetic */ View a;
    final /* synthetic */ hi b;

    hi$8(hi hiVar, View view) {
        this.b = hiVar;
        this.a = view;
    }

    public final void onAnimationEnd(Animator animator) {
        this.b.t.clearAnimation();
        this.a.clearAnimation();
        this.b.H = false;
    }

    public final void onAnimationStart(Animator animator) {
        this.b.H = true;
        this.b.w.setVisibility(8);
    }
}
