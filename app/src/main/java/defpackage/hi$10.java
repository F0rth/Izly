package defpackage;

import android.widget.ListView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;

final class hi$10 extends AnimatorListenerAdapter {
    final /* synthetic */ ObjectAnimator a;
    final /* synthetic */ float b;
    final /* synthetic */ ListView c;
    final /* synthetic */ hi d;

    hi$10(hi hiVar, ObjectAnimator objectAnimator, float f, ListView listView) {
        this.d = hiVar;
        this.a = objectAnimator;
        this.b = f;
        this.c = listView;
    }

    public final void onAnimationEnd(Animator animator) {
        this.a.setDuration(0);
        this.a.reverse();
        this.d.t.getLayoutParams().height = (int) this.b;
        this.d.t.invalidate();
        this.c.setVisibility(0);
        this.c.startLayoutAnimation();
        this.d.t.clearAnimation();
        this.c.clearAnimation();
        this.d.H = false;
    }

    public final void onAnimationStart(Animator animator) {
        this.d.H = true;
        this.d.w.setVisibility(8);
    }
}
