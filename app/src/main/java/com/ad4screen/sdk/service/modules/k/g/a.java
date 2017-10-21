package com.ad4screen.sdk.service.modules.k.g;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.A4S.Callback;

import java.util.List;

public class a extends b {
    private Callback<Void> b;

    public a(A4S a4s, Callback<Void> callback) {
        super(a4s);
        this.b = callback;
    }

    public void a(List<String> list) {
        if (list != null && !list.isEmpty() && this.b != null) {
            this.b.onResult(null);
        }
    }
}
