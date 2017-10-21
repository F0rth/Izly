package com.ad4screen.sdk.service.modules.k.g;

import com.ad4screen.sdk.A4S;
import com.ad4screen.sdk.service.modules.inapp.DisplayView;
import com.ad4screen.sdk.service.modules.inapp.DisplayView.b;

import java.util.List;

public class c extends b {
    private b b;
    private DisplayView c;

    public c(A4S a4s, b bVar, DisplayView displayView) {
        super(a4s);
        this.b = bVar;
        this.c = displayView;
    }

    private void a() {
        if (this.b != null && this.c != null) {
            this.b.b(this.c);
        }
    }

    public void a(List<String> list) {
        if (list != null && !list.isEmpty()) {
            a();
        }
    }
}
