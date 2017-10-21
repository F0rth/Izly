package com.ad4screen.sdk.service.modules.f;

import com.ad4screen.sdk.A4SService.a;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.d.f;
import com.ad4screen.sdk.h;
import com.ad4screen.sdk.service.modules.f.a.c;

public class b {
    private final a a;
    private h b;
    private final c c = new c(this) {
        final /* synthetic */ b a;

        {
            this.a = r1;
        }

        public void a() {
            try {
                this.a.b.a();
            } catch (Throwable e) {
                Log.internal("Inbox|Error in callback ! Is InboxCallback defined correctly?", e);
            }
        }

        public void a(com.ad4screen.sdk.b.c[] cVarArr) {
            try {
                this.a.b.a(cVarArr);
            } catch (Throwable e) {
                Log.internal("Inbox|Error in callback ! Is InboxCallback defined correctly?", e);
            }
        }
    };

    public b(a aVar) {
        this.a = aVar;
        f.a().a(com.ad4screen.sdk.service.modules.f.a.b.class, this.c);
        f.a().a(a.a.class, this.c);
    }

    public void a(h hVar) {
        this.b = hVar;
        new d(this.a.a()).run();
    }

    public void a(com.ad4screen.sdk.b.c[] cVarArr, h hVar) {
        new e(cVarArr, this.a.a(), hVar).run();
    }

    public void a(String[] strArr, h hVar) {
        this.b = hVar;
        new d(strArr, this.a.a()).run();
    }
}
