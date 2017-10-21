package defpackage;

import android.widget.ImageView;

final class or implements Runnable {
    private /* synthetic */ nr a;

    or(nr nrVar) {
        this.a = nrVar;
    }

    public final void run() {
        ImageView imageView = (this.a.d == ox.Left || this.a.d == ox.Right) ? (ImageView) this.a.b.findViewById(41) : (ImageView) this.a.b.findViewById(39);
        imageView.setImageBitmap(null);
    }
}
