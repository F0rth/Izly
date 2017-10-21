package com.ad4screen.sdk;

import com.ad4screen.sdk.common.annotations.API;

import java.util.Date;

@API
public class StaticList {
    public static final int SUBSCRIBED = 2;
    public static final int UNSUBSCRIBED = 4;
    private String a;
    private String b;
    private Date c;
    private int d;

    public StaticList(String str) {
        this.a = str;
    }

    public StaticList(String str, Date date) {
        this.a = str;
        this.c = date;
    }

    protected StaticList(String str, Date date, String str2, int i) {
        this.a = str;
        this.c = date;
        this.d = i;
        this.b = str2;
    }

    public Date getExpireAt() {
        return this.c;
    }

    public String getListId() {
        return this.a;
    }

    public String getName() {
        return this.b;
    }

    public int getStatus() {
        return this.d;
    }

    public boolean isSubscribed() {
        return getStatus() == 2;
    }
}
