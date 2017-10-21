package com.ezeeworld.b4s.android.sdk.notifications;

import android.content.Context;
import android.os.AsyncTask;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory;
import com.ezeeworld.b4s.android.sdk.monitor.InteractionHistory.EventType;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;

class a extends AsyncTask<Context, Void, Boolean> {
    private final String a;
    private final String b;
    private final String c;
    private final String d;

    public a(String str, String str2, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = str3;
        this.d = str4;
    }

    protected Boolean a(Context... contextArr) {
        return Boolean.valueOf(a());
    }

    boolean a() {
        B4SLog.i((Object) this, "Update session notification status for beacon " + this.b + ": " + this.d);
        if (this.d.equals(InteractionsApi.B4SSATUS_OPENED) || this.d.equals(InteractionsApi.B4SSATUS_ACCEPTED)) {
            InteractionHistory.get().registerEvent(EventType.InteractionNotificationOpened, this.c, System.currentTimeMillis());
        }
        return InteractionsApi.get().registerSessionStatus(this.a, this.b, this.d);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return a((Context[]) objArr);
    }
}
