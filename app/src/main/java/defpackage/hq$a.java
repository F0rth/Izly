package defpackage;

import android.widget.AbsListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;

final class hq$a extends PauseOnScrollListener {
    final /* synthetic */ hq a;

    public hq$a(hq hqVar, ImageLoader imageLoader, boolean z, boolean z2) {
        this.a = hqVar;
        super(imageLoader, true, false);
    }

    public final void onScroll(AbsListView absListView, int i, int i2, int i3) {
        super.onScroll(absListView, i, i2, i3);
        boolean z = i + i2 >= (i3 + -1) + -2;
        if (this.a.t != null && this.a.t.n != null && !this.a.h) {
            this.a.g = true;
            if (this.a.t.n.a) {
                if (this.a.f && !this.a.a(21) && i3 != 0 && !z) {
                    this.a.f = false;
                }
            } else if (i3 != 1 && z && !this.a.a(21) && !this.a.h) {
                this.a.a(false, false, true);
            }
        }
    }
}
