package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;

import java.util.HashMap;

@API
public class InApp {
    private String a;
    private String b;
    private int c;
    private HashMap<String, String> d;

    private InApp() {
    }

    protected InApp(String str, int i, String str2, HashMap<String, String> hashMap) {
        this.a = str;
        this.b = str2;
        this.c = i;
        this.d = hashMap;
    }

    public int getContainer() {
        return this.c;
    }

    public HashMap<String, String> getCustomParameters() {
        return this.d;
    }

    public String getDisplayTemplate() {
        return this.b;
    }

    public String getId() {
        return this.a;
    }
}
