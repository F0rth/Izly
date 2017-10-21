package defpackage;

import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;

public final class d implements AutoFocusCallback, Runnable {
    private final nr a;

    public d(nr nrVar) {
        this.a = nrVar;
    }

    public final void onAutoFocus(boolean z, Camera camera) {
    }

    public final void run() {
        if (!this.a.a('\u0004').booleanValue()) {
            this.a.e.autoFocus(this);
            e.a = Boolean.valueOf(false);
        }
    }
}
