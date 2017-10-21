package com.ad4screen.sdk.d;

import android.content.Context;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.common.c.b;

import org.json.JSONObject;

public class m extends b {
    public m(Context context) {
        super(context, "com.ad4screen.sdk.systems.UI");
    }

    public LayoutParams a() {
        return (LayoutParams) b("overlayPosition", new LayoutParams(-1, -2, 80));
    }

    public void a(LayoutParams layoutParams) {
        a("overlayPosition", (Object) layoutParams);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 1;
    }
}
