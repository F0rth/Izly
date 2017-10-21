package defpackage;

import android.widget.AbsListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

final class hl$c extends PauseOnScrollListener {
    final /* synthetic */ hl a;

    public hl$c(hl hlVar, ImageLoader imageLoader, boolean z, boolean z2) {
        this.a = hlVar;
        super(imageLoader, true, false);
    }

    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        super.onScroll(absListView, i, i2, i3);
        boolean z = i + i2 >= i3 + -2;
        if (this.a.s.G == null) {
            return;
        }
        if (this.a.s.G.b) {
            if (this.a.k && i3 != 0 && !z) {
                this.a.k = false;
            }
        } else if (i3 != 0 && z && !this.a.a(61)) {
            this.a.a(false, false, true);
        }
    }
}
