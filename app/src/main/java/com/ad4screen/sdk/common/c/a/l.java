package com.ad4screen.sdk.common.c.a;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.common.c.a.a.b;

import org.json.JSONException;
import org.json.JSONObject;

public class l extends b<LayoutParams> {
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
    public JSONObject a(LayoutParams layoutParams) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONObject jSONObject2 = new JSONObject();
        jSONObject2.put("width", layoutParams.width);
        jSONObject2.put("height", layoutParams.height);
        jSONObject2.put("gravity", layoutParams.gravity);
        jSONObject2.put("bottom", layoutParams.bottomMargin);
        jSONObject2.put("left", layoutParams.leftMargin);
        jSONObject2.put("right", layoutParams.rightMargin);
        jSONObject2.put("top", layoutParams.topMargin);
        if (VERSION.SDK_INT >= 17) {
            jSONObject2.put("direction", layoutParams.getLayoutDirection());
            jSONObject2.put("end", layoutParams.getMarginEnd());
            jSONObject2.put("start", layoutParams.getMarginStart());
        }
        jSONObject.put("type", "android.widget.FrameLayout.LayoutParameters");
        jSONObject.put("android.widget.FrameLayout.LayoutParameters", jSONObject2);
        return jSONObject;
    }
}
