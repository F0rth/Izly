package defpackage;

import android.os.Handler;
import android.os.Message;
import java.lang.ref.WeakReference;

public final class gg$a extends Handler {
    private WeakReference<gg> a;

    public gg$a(gg ggVar) {
        this.a = new WeakReference(ggVar);
    }

    public final void handleMessage(Message message) {
        gg ggVar = (gg) this.a.get();
        if (ggVar != null && message.what == 1) {
            gg.a(ggVar, gg.a(ggVar).isProviderEnabled("gps"));
            gg.b(ggVar, gg.a(ggVar).isProviderEnabled("network"));
            if (gg.b(ggVar) || gg.c(ggVar)) {
                if (gg.b(ggVar) && gg.c(ggVar) && gg.a(ggVar).isProviderEnabled("network")) {
                    gg.a(ggVar, gg.a(ggVar).getLastKnownLocation("network"));
                }
                if (gg.b(ggVar) && !gg.c(ggVar) && gg.a(ggVar).isProviderEnabled("network")) {
                    gg.a(ggVar, gg.a(ggVar).getLastKnownLocation("network"));
                }
                if (!gg.b(ggVar) && gg.c(ggVar) && gg.a(ggVar).isProviderEnabled("gps")) {
                    gg.a(ggVar, gg.a(ggVar).getLastKnownLocation("gps"));
                }
                if (gg.d(ggVar) != null) {
                    gg.a(ggVar, gg.e(ggVar), gg.d(ggVar));
                }
            }
        }
        super.handleMessage(message);
    }
}
