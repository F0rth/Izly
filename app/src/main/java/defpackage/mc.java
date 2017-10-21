package defpackage;

import android.content.Context;

public final class mc implements Runnable {
    private final Context a;
    private final ly b;

    public mc(Context context, ly lyVar) {
        this.a = context;
        this.b = lyVar;
    }

    public final void run() {
        try {
            kp.a(this.a, "Performing time based file roll over.");
            if (!this.b.rollFileOver()) {
                this.b.cancelTimeBasedFileRollOver();
            }
        } catch (Exception e) {
            kp.b(this.a, "Failed to roll over file");
        }
    }
}
