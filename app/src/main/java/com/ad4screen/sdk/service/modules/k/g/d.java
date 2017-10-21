package com.ad4screen.sdk.service.modules.k.g;

import android.net.Uri;

import com.ad4screen.sdk.Log;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class d {
    private Uri a = null;
    private Map<String, List<String>> b = new HashMap();

    public enum a {
        ACTION_CLOSE("bma4sclose"),
        ACTION_UPDATE_DEVICE_INFO("bma4sudi");

        private String c;

        private a(String str) {
            this.c = str;
        }

        public final String a() {
            return this.c;
        }
    }

    private d(Uri uri) {
        this.a = uri;
        this.b = a();
    }

    public static d a(Uri uri) {
        return new d(uri);
    }

    public static d a(String str) {
        Uri uri = null;
        if (str != null) {
            uri = Uri.parse(str);
        }
        return a(uri);
    }

    private Map<String, List<String>> a() {
        Map<String, List<String>> hashMap = new HashMap();
        try {
            hashMap = b(this.a);
        } catch (Throwable e) {
            Log.internal("Impossible to parse query actions", e);
        }
        return hashMap;
    }

    private Map<String, List<String>> b(Uri uri) throws UnsupportedEncodingException {
        Map<String, List<String>> linkedHashMap = new LinkedHashMap();
        if (uri != null) {
            String query = uri.getQuery();
            if (query != null) {
                for (String str : query.split("&")) {
                    Object substring;
                    int indexOf = str.indexOf("=");
                    if (indexOf > 0) {
                        substring = str.substring(0, indexOf);
                    } else {
                        query = str;
                    }
                    if (!linkedHashMap.containsKey(substring)) {
                        linkedHashMap.put(substring, new LinkedList());
                    }
                    Object substring2 = (indexOf <= 0 || str.length() <= indexOf + 1) ? null : str.substring(indexOf + 1);
                    ((List) linkedHashMap.get(substring)).add(substring2);
                }
            }
        }
        return linkedHashMap;
    }

    public final void a(b bVar) {
        if (a(a.ACTION_CLOSE)) {
            bVar.a(b(a.ACTION_CLOSE));
        }
        if (a(a.ACTION_UPDATE_DEVICE_INFO)) {
            bVar.b(b(a.ACTION_UPDATE_DEVICE_INFO));
        }
    }

    public final boolean a(a aVar) {
        return this.b.containsKey(aVar.a());
    }

    public final List<String> b(a aVar) {
        List<String> list = (List) this.b.get(aVar.a());
        return list != null ? list : new ArrayList();
    }
}
