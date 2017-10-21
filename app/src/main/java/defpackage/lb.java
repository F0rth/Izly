package defpackage;

import android.os.SystemClock;
import android.util.Log;

public final class lb {
    private final String a;
    private final String b;
    private final boolean c;
    private long d;
    private long e;

    public lb(String str, String str2) {
        this.a = str;
        this.b = str2;
        this.c = !Log.isLoggable(str2, 2);
    }

    public final void a() {
        synchronized (this) {
            if (!this.c) {
                this.d = SystemClock.elapsedRealtime();
                this.e = 0;
            }
        }
    }

    public final void b() {
        synchronized (this) {
            if (!this.c) {
                if (this.e == 0) {
                    this.e = SystemClock.elapsedRealtime() - this.d;
                    Log.v(this.b, this.a + ": " + this.e + "ms");
                }
            }
        }
    }
}
