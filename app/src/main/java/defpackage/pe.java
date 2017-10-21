package defpackage;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.RelativeLayout.LayoutParams;

final class pe implements AnimationListener {
    private /* synthetic */ Boolean a;
    private /* synthetic */ pd b;

    pe(pd pdVar, Boolean bool) {
        this.b = pdVar;
        this.a = bool;
    }

    public final void onAnimationEnd(Animation animation) {
        this.b.a.clearAnimation();
        if (this.b.i.booleanValue()) {
            this.b.a.setLeft(0);
            this.b.a.requestFocus();
        } else {
            this.b.a(this.a);
        }
        this.b.i = Boolean.valueOf(!this.b.i.booleanValue());
        this.b.j = Boolean.valueOf(false);
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationStart(Animation animation) {
        if (this.b.i.booleanValue()) {
            LayoutParams layoutParams = (LayoutParams) this.b.a.getLayoutParams();
            layoutParams.addRule(7, 0);
            layoutParams.setMargins(0, 0, 0, 0);
            this.b.b.setVisibility(8);
            this.b.c.setVisibility(8);
        }
    }
}
