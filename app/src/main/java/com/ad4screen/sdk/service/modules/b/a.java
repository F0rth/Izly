package com.ad4screen.sdk.service.modules.b;

public final class a {

    public interface c {
        void a();

        void a(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z);
    }

    public static final class a implements com.ad4screen.sdk.d.f.a<c> {
        public final void a(c cVar) {
            cVar.a();
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<c> {
        private com.ad4screen.sdk.service.modules.b.a.a a;
        private boolean b;

        public b(com.ad4screen.sdk.service.modules.b.a.a aVar, boolean z) {
            this.a = aVar;
            this.b = z;
        }

        public final void a(c cVar) {
            cVar.a(this.a, this.b);
        }
    }
}
