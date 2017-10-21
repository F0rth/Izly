package defpackage;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

final class hi$2 extends Animation {
    final /* synthetic */ View a;
    final /* synthetic */ int b;

    hi$2(View view, int i) {
        this.a = view;
        this.b = i;
    }

    protected final void applyTransformation(float f, Transformation transformation) {
        this.a.getLayoutParams().height = f == 1.0f ? -2 : (int) (((float) this.b) * f);
        this.a.requestLayout();
    }

    public final boolean willChangeBounds() {
        return true;
    }
}
