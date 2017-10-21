package defpackage;

import android.util.Log;

public final class jr implements kb {
    private int a = 4;

    public jr(int i) {
    }

    public final void a(int i, String str, String str2) {
        a(i, str, str2, false);
    }

    public final void a(int i, String str, String str2, boolean z) {
        if (z || a(i)) {
            Log.println(i, str, str2);
        }
    }

    public final void a(String str, String str2) {
        a(str, str2, null);
    }

    public final void a(String str, String str2, Throwable th) {
        if (a(3)) {
            Log.d(str, str2, th);
        }
    }

    public final boolean a(int i) {
        return this.a <= i;
    }

    public final void b(String str, String str2) {
        if (a(2)) {
            Log.v(str, str2, null);
        }
    }

    public final void b(String str, String str2, Throwable th) {
        if (a(5)) {
            Log.w(str, str2, th);
        }
    }

    public final void c(String str, String str2) {
        if (a(4)) {
            Log.i(str, str2, null);
        }
    }

    public final void c(String str, String str2, Throwable th) {
        if (a(6)) {
            Log.e(str, str2, th);
        }
    }

    public final void d(String str, String str2) {
        b(str, str2, null);
    }

    public final void e(String str, String str2) {
        c(str, str2, null);
    }
}
