package defpackage;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;

final class hk$1 extends AnimatorListenerAdapter {
    final /* synthetic */ hk a;

    hk$1(hk hkVar) {
        this.a = hkVar;
    }

    public final void onAnimationEnd(Animator animator) {
        super.onAnimationEnd(animator);
        this.a.f.setVisibility(8);
    }
}
