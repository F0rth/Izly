package defpackage;

import android.os.Build.VERSION;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.gms.maps.UiSettings;

final class hc$1 implements OnGlobalLayoutListener {
    final /* synthetic */ hc a;

    hc$1(hc hcVar) {
        this.a = hcVar;
    }

    public final void onGlobalLayout() {
        if (VERSION.SDK_INT < 16) {
            this.a.q.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        } else {
            this.a.q.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        }
        this.a.p = this.a.q.getMap();
        this.a.p.setOnMapClickListener(this.a);
        this.a.p.setOnMarkerClickListener(this.a);
        UiSettings uiSettings = this.a.p.getUiSettings();
        uiSettings.setAllGesturesEnabled(false);
        uiSettings.setZoomControlsEnabled(false);
        hc.c(this.a);
    }
}
