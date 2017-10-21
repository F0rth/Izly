package com.ad4screen.sdk.common.c.a;

import android.content.Intent;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.c.a.a.a;

import java.net.URISyntaxException;

import org.json.JSONException;
import org.json.JSONObject;

public class i extends a<Intent> {
    private final String a = "android.content.Intent";
    private final String b = "uri";
    private final String c = "extras";

    public Intent a(String str) throws JSONException {
        Intent parseUri;
        Throwable e;
        JSONObject jSONObject = new JSONObject(str).getJSONObject("android.content.Intent");
        try {
            parseUri = Intent.parseUri(jSONObject.getString("uri"), 1);
            try {
                parseUri.putExtras(new c().a(jSONObject.getString("extras")));
            } catch (URISyntaxException e2) {
                e = e2;
                Log.internal("IntentDeserializer|URI is invalid", e);
                return parseUri;
            }
        } catch (URISyntaxException e3) {
            e = e3;
            parseUri = null;
            Log.internal("IntentDeserializer|URI is invalid", e);
            return parseUri;
        }
        return parseUri;
    }

    public String a() {
        return "android.content.Intent";
    }

    public /* synthetic */ Object b(String str) throws JSONException {
        return a(str);
    }
}
