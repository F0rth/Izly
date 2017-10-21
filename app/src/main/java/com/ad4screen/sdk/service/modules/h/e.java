package com.ad4screen.sdk.service.modules.h;

import com.ad4screen.sdk.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class e {
    public String[] a;

    private String[] a(JSONArray jSONArray) throws JSONException {
        String[] strArr = new String[jSONArray.length()];
        for (int i = 0; i < strArr.length; i++) {
            strArr[i] = jSONArray.getString(i);
        }
        return strArr;
    }

    public void a(JSONObject jSONObject) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject("listMembersResponse");
            if (jSONObject2.getInt("returnCode") != 0) {
                Log.internal("MemberManager|Members List WebService returned an error : " + jSONObject.getString("returnCode") + " : " + jSONObject.getString("returnLabel"));
                this.a = null;
                return;
            }
            this.a = a(jSONObject2.getJSONArray("members"));
        } catch (Throwable e) {
            Log.internal("MemberManager|Members JSON Parsing error!", e);
            this.a = null;
        }
    }
}
