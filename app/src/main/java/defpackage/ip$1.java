package defpackage;

import android.view.animation.Interpolator;

final class ip$1 implements Interpolator {
    final /* synthetic */ ip a;

    ip$1(ip ipVar) {
        this.a = ipVar;
    }

    public final float getInterpolation(float f) {
        float f2 = (1.55f * f) - 1.1f;
        return 1.2f - (f2 * f2);
    }
}
