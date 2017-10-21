package com.ad4screen.sdk.common.c.a;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.common.c.a.a.a;

import org.json.JSONException;
import org.json.JSONObject;

public class k extends a<LayoutParams> {
    private final String a = "android.widget.FrameLayout.LayoutParameters";
    private final String b = "width";
    private final String c = "height";
    private final String d = "gravity";
    private final String e = "bottom";
    private final String f = "left";
    private final String g = "right";
    private final String h = "top";
    private final String i = "direction";
    private final String j = "end";
    private final String k = "start";

    @SuppressLint({"NewApi"})
    public LayoutParams a(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str).getJSONObject("android.widget.FrameLayout.LayoutParameters");
        LayoutParams layoutParams = new LayoutParams(jSONObject.getInt("width"), jSONObject.getInt("height"), jSONObject.getInt("gravity"));
        layoutParams.setMargins(jSONObject.getInt("left"), jSONObject.getInt("top"), jSONObject.getInt("right"), jSONObject.getInt("bottom"));
        if (VERSION.SDK_INT >= 17) {
            layoutParams.setLayoutDirection(jSONObject.getInt("direction"));
            layoutParams.setMarginEnd(jSONObject.getInt("end"));
            layoutParams.setMarginStart(jSONObject.getInt("start"));
        }
        return layoutParams;
    }

    public String a() {
        return "android.widget.FrameLayout.LayoutParameters";
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
