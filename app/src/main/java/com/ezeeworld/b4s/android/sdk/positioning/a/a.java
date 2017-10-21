package com.ezeeworld.b4s.android.sdk.positioning.a;

final class a {
    private final double a;
    private final double b;
    private double c;
    private double d = 0.0d;

    a(double d, double d2, double d3) {
        this.c = d;
        this.a = d2;
        this.b = d3;
    }

    public final double a() {
        return this.c;
    }

    public final double a(double d) {
        double d2 = this.d + this.a;
        double d3 = (1.0d / (this.b + d2)) * d2;
        this.c += (d - this.c) * d3;
        this.d = d2 * (1.0d - d3);
        return this.c;
    }
}
