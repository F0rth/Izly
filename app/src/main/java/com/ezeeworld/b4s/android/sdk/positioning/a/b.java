package com.ezeeworld.b4s.android.sdk.positioning.a;

import com.ezeeworld.b4s.android.sdk.positioning.Position;

import java.util.Locale;

final class b {
    public double a;
    public double b;
    public double c;

    b(double d, double d2, double d3) {
        this.a = d;
        this.b = d2;
        this.c = d3;
    }

    public final double a(Position position) {
        return Math.sqrt(Math.pow(this.a - position.x, 2.0d) + Math.pow(this.b - position.y, 2.0d));
    }

    public final void a(double d) {
        this.c *= d;
    }

    public final void b(double d) {
        this.c /= d;
    }

    public final String toString() {
        return String.format(Locale.US, "%1$.1f, %2$.1f @ %3$.1f", new Object[]{Double.valueOf(this.a), Double.valueOf(this.b), Double.valueOf(this.c)});
    }
}
