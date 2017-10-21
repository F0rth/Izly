package com.ad4screen.sdk.service.modules.f;

public final class a {

    public static final class a implements com.ad4screen.sdk.d.f.a<c> {
        public final void a(c cVar) {
            cVar.a();
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<c> {
        com.ad4screen.sdk.b.c[] a;

        public b(com.ad4screen.sdk.b.c[] cVarArr) {
            this.a = cVarArr;
        }

        public final void a(c cVar) {
            cVar.a(this.a);
        }
    }

    public interface c {
        void a();

        void a(com.ad4screen.sdk.b.c[] cVarArr);
    }
}
