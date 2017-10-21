package com.ezeeworld.b4s.android.sdk.positioning.a;

import android.util.Pair;

import java.util.Comparator;

class d implements Comparator<Pair<String, Double>> {
    final /* synthetic */ c a;

    d(c cVar) {
        this.a = cVar;
    }

    public int a(Pair<String, Double> pair, Pair<String, Double> pair2) {
        return Double.compare(((Double) pair.second).doubleValue(), ((Double) pair2.second).doubleValue());
    }

    public /* synthetic */ int compare(Object obj, Object obj2) {
        return a((Pair) obj, (Pair) obj2);
    }
}
