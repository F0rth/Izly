package defpackage;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

final class hi$3 extends Animation {
    final /* synthetic */ View a;
    final /* synthetic */ int b;

    hi$3(View view, int i) {
        this.a = view;
        this.b = i;
    }

    protected final void applyTransformation(float f, Transformation transformation) {
        if (f == 1.0f) {
            this.a.setVisibility(8);
            return;
        }
        this.a.getLayoutParams().height = this.b - ((int) (((float) this.b) * f));
        this.a.requestLayout();
    }

    public final boolean willChangeBounds() {
        return true;
    }
}
