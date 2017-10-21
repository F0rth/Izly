package defpackage;

import android.os.Build.VERSION;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

final class f implements Runnable {
    private /* synthetic */ Boolean a;
    private /* synthetic */ Q b;

    f(Boolean bool, Q q) {
        this.a = bool;
        this.b = q;
    }

    public final void run() {
        Animation alphaAnimation;
        if (this.a.booleanValue()) {
            if (VERSION.SDK_INT >= 11) {
                this.b.setAlpha(0.8235294f);
            } else {
                alphaAnimation = new AlphaAnimation(0.8235294f, 0.8235294f);
                alphaAnimation.setDuration(0);
                alphaAnimation.setFillAfter(true);
                this.b.setAnimation(alphaAnimation);
            }
        } else if (VERSION.SDK_INT >= 11) {
            this.b.setAlpha(0.49019608f);
        } else {
            alphaAnimation = new AlphaAnimation(0.49019608f, 0.49019608f);
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            this.b.setAnimation(alphaAnimation);
        }
        this.b.invalidate();
    }
}
