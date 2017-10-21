package com.ad4screen.sdk.service.modules.c;

public final class a {

    public static final class a implements com.ad4screen.sdk.d.f.a<c> {
        public final void a(c cVar) {
            cVar.a();
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<c> {
        e a;

        public b(e eVar) {
            this.a = eVar;
        }

        public final void a(c cVar) {
            cVar.a(this.a);
        }
    }

    public interface c {
        void a();

        void a(e eVar);
    }
}
