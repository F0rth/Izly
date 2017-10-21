package com.google.analytics.tracking.android;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

class HitBuilder {
    HitBuilder() {
    }

    static String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError("URL encoding failed for: " + str);
        }
    }

    static Map<String, String> generateHitParams(Map<String, String> map) {
        Map<String, String> hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            if (((String) entry.getKey()).startsWith("&") && entry.getValue() != null) {
                CharSequence substring = ((String) entry.getKey()).substring(1);
                if (!TextUtils.isEmpty(substring)) {
                    hashMap.put(substring, entry.getValue());
                }
            }
        }
        return hashMap;
    }

    static String postProcessHit(Hit hit, long j) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hit.getHitParams());
        if (hit.getHitTime() > 0) {
            long hitTime = j - hit.getHitTime();
            if (hitTime >= 0) {
                stringBuilder.append("&qt=").append(hitTime);
            }
        }
        stringBuilder.append("&z=").append(hit.getHitId());
        return stringBuilder.toString();
    }
}
