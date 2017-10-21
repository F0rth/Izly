package defpackage;

import android.os.Build.VERSION;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

final class ns implements Runnable {
    final /* synthetic */ nr a;
    private /* synthetic */ Boolean b;

    ns(nr nrVar, Boolean bool) {
        this.a = nrVar;
        this.b = bool;
    }

    public final void run() {
        ImageView imageView = (this.a.d == ox.Left || this.a.d == ox.Right) ? (ImageView) this.a.b.findViewById(41) : (ImageView) this.a.b.findViewById(39);
        if (this.b.booleanValue()) {
            if (this.a.d == ox.Left) {
                imageView.setImageBitmap(os.a("card_example_left"));
            } else if (this.a.d == ox.Right) {
                imageView.setImageBitmap(os.a("card_example_right"));
            } else if (this.a.d == ox.Top) {
                imageView.setImageBitmap(os.a("card_example"));
            }
            if (VERSION.SDK_INT >= 16) {
                imageView.setImageAlpha(90);
            } else {
                imageView.setAlpha(90);
            }
        } else if (imageView.getAnimation() == null) {
            this.a.l = new AlphaAnimation(1.0f, 0.0f);
            this.a.l.setDuration(1000);
            this.a.l.setAnimationListener(new nt(this, imageView));
            imageView.startAnimation(this.a.l);
        }
    }
}
