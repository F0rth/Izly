package com.ad4screen.sdk.service.modules.h;

import android.content.Context;

import com.ad4screen.sdk.common.c.b;
import com.ad4screen.sdk.service.modules.h.a.a;

import org.json.JSONObject;

public class d extends b {
    public d(Context context) {
        super(context, "com.ad4screen.sdk.service.modules.member.LinkedMembers");
    }

    public a a() {
        return (a) b("com.ad4screen.LinkedMembers", new a());
    }

    public void a(a aVar) {
        a("com.ad4screen.LinkedMembers", (Object) aVar);
    }

    public void a(com.ad4screen.sdk.service.modules.h.a.b bVar) {
        a("com.ad4screen.ActiveMember", (Object) bVar);
    }

    public boolean a(int i, JSONObject jSONObject) {
        return false;
    }

    public int b() {
        return 4;
    }

    public com.ad4screen.sdk.service.modules.h.a.b e() {
        return (com.ad4screen.sdk.service.modules.h.a.b) b("com.ad4screen.ActiveMember", new com.ad4screen.sdk.service.modules.h.a.b());
    }

    public void f() {
        a("com.ad4screen.ActiveMember");
    }
}
