package defpackage;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public final class nq implements Runnable {
    private /* synthetic */ np a;

    public nq(np npVar) {
        this.a = npVar;
    }

    public final void run() {
        np npVar = this.a;
        ((ImageView) npVar.a.findViewById(19)).setVisibility(4);
        ((ImageView) npVar.a.findViewById(9)).setImageBitmap(null);
        RelativeLayout relativeLayout = (RelativeLayout) npVar.a.findViewById(5);
        relativeLayout.setBackgroundColor(0);
        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(0);
        alphaAnimation.setFillAfter(true);
        relativeLayout.startAnimation(alphaAnimation);
        ((RelativeLayout) npVar.a.findViewById(11)).setVisibility(0);
        ((TextView) npVar.a.findViewById(12)).setVisibility(0);
        N n = (N) this.a.a.findViewById(8);
        n.setBackgroundColor(0);
        this.a.c = n.a;
        if (this.a.c == null) {
            Toast.makeText(this.a.a, k.a().a("CameraUnavailable"), 1).show();
            return;
        }
        this.a.b = new nr(this.a.a, this.a.c, this.a.a, n.b);
        this.a.c.setPreviewCallback(this.a.b);
    }
}
