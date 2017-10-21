package com.ad4screen.sdk.service.modules.inapp.c.a;

import android.content.Context;
import android.text.TextUtils;

import com.ad4screen.sdk.service.modules.inapp.a.e;
import com.ad4screen.sdk.service.modules.inapp.a.h;
import com.ad4screen.sdk.service.modules.inapp.a.j;
import com.ad4screen.sdk.service.modules.inapp.a.l;
import com.ad4screen.sdk.service.modules.inapp.c.m;
import com.ad4screen.sdk.service.modules.inapp.k;

import java.util.List;

public class g implements m {
    private String a;

    private boolean a(String str, List<l> list) {
        if (str != null) {
            for (l a : list) {
                if (str.equals(a.a())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(List<l> list) {
        return (list == null || list.isEmpty()) ? false : true;
    }

    public String a() {
        return "ViewNameExclusionCheck";
    }

    public void a(Context context, k kVar) {
        this.a = kVar.e();
    }

    public void a(j jVar, h hVar) {
    }

    public boolean a(e eVar, j jVar, h hVar) {
        List c = jVar.o().c();
        if (a(c)) {
            if (TextUtils.isEmpty(this.a)) {
                return false;
            }
            if (a(this.a, c)) {
                return false;
            }
        }
        return true;
    }
}
