package com.ad4screen.sdk.service.modules.e;

public final class b {

    public static final class a implements com.ad4screen.sdk.d.f.a<c> {
        public final void a(c cVar) {
            cVar.a();
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<c> {
        private a a;

        public b(a aVar) {
            this.a = aVar;
        }

        public final void a(c cVar) {
            cVar.a(this.a);
        }
    }

    public interface c {
        void a();

        void a(a aVar);
    }
}
