package defpackage;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

final class nt implements AnimationListener {
    final /* synthetic */ ImageView a;
    private /* synthetic */ ns b;

    nt(ns nsVar, ImageView imageView) {
        this.b = nsVar;
        this.a = imageView;
    }

    public final void onAnimationEnd(Animation animation) {
        this.b.a.b.runOnUiThread(new oq(this));
    }

    public final void onAnimationRepeat(Animation animation) {
    }

    public final void onAnimationStart(Animation animation) {
    }
}
