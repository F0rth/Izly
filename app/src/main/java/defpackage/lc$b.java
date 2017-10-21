package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

final class lc$b extends Handler {
    public lc$b() {
        super(Looper.getMainLooper());
    }

    public final void handleMessage(Message message) {
        lc$a lc_a = (lc$a) message.obj;
        switch (message.what) {
            case 1:
                lc.c(lc_a.a, lc_a.b[0]);
                return;
            case 2:
                lc.b();
                return;
            default:
                return;
        }
    }
}
