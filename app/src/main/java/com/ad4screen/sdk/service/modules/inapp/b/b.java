package com.ad4screen.sdk.service.modules.inapp.b;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.h;
import com.ad4screen.sdk.common.h.a;
import com.ad4screen.sdk.service.modules.inapp.a.a.d;
import com.ad4screen.sdk.service.modules.inapp.a.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.a.f;
import com.ad4screen.sdk.service.modules.inapp.a.a.g;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class b {
    private d a(JSONObject jSONObject) throws JSONException {
        int optInt;
        d dVar = new d();
        JSONObject jSONObject2 = jSONObject.getJSONObject("recurrence");
        String string = jSONObject2.getString("frequency");
        if (string != null) {
            try {
                dVar.a(g.valueOf(string.trim().toUpperCase(Locale.US)));
            } catch (IllegalArgumentException e) {
                throw new JSONException("Wrong enum from server : " + e.getMessage());
            }
        }
        if (jSONObject2.has("interval")) {
            optInt = jSONObject2.optInt("interval", -1);
            if (optInt > 0) {
                dVar.a(optInt);
            } else {
                throw new JSONException("Wrong interval, interval must be greater than 0");
            }
        }
        if (jSONObject2.has("byMonthDay")) {
            dVar.a(a(jSONObject2.getString("byMonthDay"), ","));
        }
        if (jSONObject2.has("byDay")) {
            string = jSONObject2.getString("byDay");
            if (string != null) {
                Pattern compile = Pattern.compile("^([-+]?[0-9]+)?([A-Z]{2})$");
                String[] split = string.split(",");
                HashMap hashMap = new HashMap();
                for (String trim : split) {
                    Matcher matcher = compile.matcher(trim.trim());
                    if (matcher.find() && matcher.groupCount() == 2) {
                        String group = matcher.group(1);
                        String group2 = matcher.group(2);
                        Object obj = null;
                        if (group != null) {
                            try {
                                obj = Integer.valueOf(Integer.parseInt(group));
                            } catch (Throwable e2) {
                                Log.internal("Impossible to parse day", e2);
                            }
                        }
                        hashMap.put(e.valueOf(group2.trim().toUpperCase(Locale.US)), obj);
                    }
                }
                dVar.a(hashMap);
            }
        }
        if (jSONObject2.has("byHour")) {
            dVar.b(a(jSONObject2.getString("byHour"), ","));
        }
        if (jSONObject2.has("byMinute")) {
            dVar.c(a(jSONObject2.getString("byMinute"), ","));
        }
        JSONObject jSONObject3 = jSONObject2.getJSONObject("duration");
        try {
            dVar.a(f.valueOf(jSONObject3.getString("type").trim().toUpperCase(Locale.US)));
            dVar.a(jSONObject3.getLong("duration"));
            return dVar;
        } catch (IllegalArgumentException e3) {
            throw new JSONException(e3.getMessage());
        }
    }

    private List<Integer> a(String str, String str2) {
        if (str == null) {
            return null;
        }
        String[] split = str.split(str2);
        List<Integer> arrayList = new ArrayList();
        for (String trim : split) {
            try {
                arrayList.add(Integer.valueOf(Integer.parseInt(trim.trim())));
            } catch (Throwable e) {
                Log.internal("Impossible to parse number", e);
            }
        }
        return arrayList;
    }

    public List<com.ad4screen.sdk.service.modules.inapp.a.a.b> a(JSONArray jSONArray) {
        List<com.ad4screen.sdk.service.modules.inapp.a.a.b> arrayList = new ArrayList();
        if (jSONArray != null) {
            for (int i = 0; i < jSONArray.length(); i++) {
                try {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    com.ad4screen.sdk.service.modules.inapp.a.a.b bVar = new com.ad4screen.sdk.service.modules.inapp.a.a.b();
                    String optString = jSONObject.optString("dateBeginning", null);
                    if (optString != null) {
                        bVar.a(h.a(optString, a.ISO8601));
                    }
                    optString = jSONObject.optString("dateEnding", null);
                    if (optString != null) {
                        bVar.b(h.a(optString, a.ISO8601));
                    }
                    bVar.a(jSONObject.optBoolean("isLocal", false));
                    if (jSONObject.has("recurrence")) {
                        bVar.a(a(jSONObject));
                    }
                    arrayList.add(bVar);
                } catch (JSONException e) {
                    Log.internal("Error during parsing of rule " + i + " : " + e.getMessage());
                }
            }
        }
        return arrayList;
    }
}
