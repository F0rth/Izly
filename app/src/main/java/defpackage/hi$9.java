package defpackage;

import android.view.View;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

final class hi$9 extends AnimatorListenerAdapter {
    final /* synthetic */ ObjectAnimator a;
    final /* synthetic */ float b;
    final /* synthetic */ View c;
    final /* synthetic */ hi d;

    hi$9(hi hiVar, ObjectAnimator objectAnimator, float f, View view) {
        this.d = hiVar;
        this.a = objectAnimator;
        this.b = f;
        this.c = view;
    }

    public final void onAnimationStart(Animator animator) {
        this.a.setDuration(0);
        this.a.reverse();
        this.d.t.getLayoutParams().height = (int) this.b;
        this.d.t.invalidate();
        this.c.setVisibility(0);
    }
}
