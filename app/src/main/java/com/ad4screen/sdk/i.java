package com.ad4screen.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.ad4screen.sdk.Message.Button;
import com.ad4screen.sdk.Message.MessageContentType;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.b.b;
import com.ad4screen.sdk.b.c;
import com.ad4screen.sdk.b.c.a;
import com.ad4screen.sdk.service.modules.inapp.g;

import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONObject;

class i {
    protected static Inbox a(Context context, b bVar) {
        Message[] messageArr = new Message[bVar.a.length];
        for (int i = 0; i < bVar.a.length; i++) {
            messageArr[i] = a(bVar.a[i]);
        }
        return new Inbox(context, messageArr);
    }

    protected static Message a(c cVar) {
        Button[] buttonArr = new Button[cVar.q.length];
        for (int i = 0; i < cVar.q.length; i++) {
            buttonArr[i] = new Button(cVar.q[i].a, cVar.q[i].b);
        }
        return new Message(cVar.a, cVar.b, cVar.c, cVar.d, cVar.f, cVar.g, cVar.i, MessageContentType.valueOf(cVar.j.name()), cVar.k, cVar.l, cVar.m, false, cVar.p, buttonArr, cVar.n);
    }

    protected static void a(Context context, String str, HashMap<String, String> hashMap) {
        if (str != null) {
            Intent intent = new Intent(str);
            intent.addCategory(Constants.CATEGORY_INBOX_NOTIFICATIONS);
            g.a(intent, (HashMap) hashMap);
            com.ad4screen.sdk.common.i.a(context, intent);
        }
    }

    protected static void a(Handler handler, final Context context, final com.ad4screen.sdk.a.b bVar, final a aVar, final String str, String str2) {
        if (aVar == a.Event) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.isNull(Lead.KEY_VALUE)) {
                    A4S.get(context).trackEvent((long) jSONObject.getInt("id"), "", new String[0]);
                } else {
                    A4S.get(context).trackEvent((long) jSONObject.getInt("id"), jSONObject.getString(Lead.KEY_VALUE), new String[0]);
                }
            } catch (Throwable e) {
                Log.internal("Inbox|Invalid events arguments", e);
            }
        } else if (aVar == a.Push) {
            Bundle bundle = new Bundle();
            try {
                JSONObject jSONObject2 = new JSONObject(str);
                Iterator keys = jSONObject2.keys();
                while (keys.hasNext()) {
                    String str3 = (String) keys.next();
                    bundle.putString(str3, jSONObject2.getString(str3));
                }
                bundle.putString("a4stitle", str2);
                bundle.putString("a4scontent", str2);
            } catch (Throwable e2) {
                Log.internal("InboxUtil|Error while serializing Action to JSON", e2);
            }
            bVar.a(bundle);
        } else {
            handler.post(new Runnable() {
                public final void run() {
                    if (aVar == a.System) {
                        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
                        intent.setFlags(268435456);
                        try {
                            context.startActivity(intent);
                        } catch (Throwable e) {
                            Log.error("Url Scheme seems to be invalid", e);
                        }
                    } else if (aVar == a.Close) {
                        bVar.d();
                    }
                }
            });
        }
    }
}
