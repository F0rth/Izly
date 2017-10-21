package defpackage;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

final class hi$7 extends AnimatorListenerAdapter {
    final /* synthetic */ hi a;

    hi$7(hi hiVar) {
        this.a = hiVar;
    }

    public final void onAnimationEnd(Animator animator) {
        this.a.s.clearAnimation();
        this.a.t.clearAnimation();
        this.a.s.setVisibility(8);
        this.a.G = false;
        this.a.H = false;
    }

    public final void onAnimationStart(Animator animator) {
        this.a.H = true;
    }
}
