package com.ad4screen.sdk.d;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;

import org.json.JSONObject;

public class e extends b {
    public e(Context context) {
        super(context, "com.ad4screen.sdk.common.Environment");
    }

    public String a(d.b bVar) {
        return a(bVar.toString() + ".url", null);
    }

    public void a(d.b bVar, int i) {
        a(bVar.toString() + ".frequency", (Object) Integer.valueOf(i));
    }

    public void a(d.b bVar, long j) {
        a(bVar.toString() + ".lastSuccess", (Object) Long.valueOf(j));
    }

    public void a(d.b bVar, String str) {
        a(bVar.toString() + ".url", (Object) str);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public int b(d.b bVar, int i) {
        return a(bVar.toString() + ".frequency", i);
    }

    public long b(d.b bVar) {
        return a(bVar.toString() + ".lastSuccess", 0);
    }

    public void b(d.b bVar, long j) {
        a(bVar.toString() + ".date", (Object) Long.valueOf(j));
    }

    public int c(d.b bVar) {
        return b(bVar, 0);
    }

    public long c(d.b bVar, long j) {
        return a(bVar.toString() + ".date", j);
    }

    public void c(d.b bVar, int i) {
        a(bVar.toString() + ".callCount", (Object) Integer.valueOf(i));
    }

    public int d(d.b bVar) {
        return a(bVar.toString() + ".callCount", 0);
    }

    public long e(d.b bVar) {
        return c(bVar, 0);
    }
}
