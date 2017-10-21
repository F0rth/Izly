package com.ezeeworld.b4s.android.sdk.monitor;

import android.content.Context;

abstract class c {
    final Context h;
    final String i;
    final long j;
    long k = System.currentTimeMillis();
    long l = this.k;

    protected c(Context context, String str, long j) {
        this.h = context;
        this.i = str;
        this.j = j;
    }

    public boolean b() {
        return this.l + this.j < System.currentTimeMillis();
    }

    public void c() {
        this.l = System.currentTimeMillis();
    }

    protected int d() {
        return (int) ((this.l - this.k) / 1000);
    }
}
