package com.ad4screen.sdk.service.modules.push;

public final class d {

    public interface b {
        void a();
    }

    public interface a {
        void a();

        void b();
    }

    public static final class c implements com.ad4screen.sdk.d.f.a<a> {
        public final void a(a aVar) {
            aVar.b();
        }
    }

    public static final class d implements com.ad4screen.sdk.d.f.a<a> {
        public final void a(a aVar) {
            aVar.a();
        }
    }
}
