package defpackage;

import java.util.concurrent.CountDownLatch;

final class js$2 implements jv {
    final CountDownLatch a = new CountDownLatch(this.b);
    final /* synthetic */ int b;
    final /* synthetic */ js c;

    js$2(js jsVar, int i) {
        this.c = jsVar;
        this.b = i;
    }

    public final void a() {
        this.a.countDown();
        if (this.a.getCount() == 0) {
            js.a(this.c).set(true);
            js.b(this.c).a();
        }
    }

    public final void a(Exception exception) {
        js.b(this.c).a(exception);
    }
}
