package com.ad4screen.sdk.service.modules.k;

public final class f {

    public interface d {
        void a();
    }

    public interface c {
        void a(long j, String[] strArr);
    }

    public static final class a implements com.ad4screen.sdk.d.f.a<g> {
        com.ad4screen.sdk.service.modules.k.d.c a;

        public a(com.ad4screen.sdk.service.modules.k.d.c cVar) {
            this.a = cVar;
        }

        public final void a(g gVar) {
            gVar.a(this.a);
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<g> {
        String a;

        public b(String str) {
            this.a = str;
        }

        public final void a(g gVar) {
            gVar.a(this.a);
        }
    }

    public static final class e implements com.ad4screen.sdk.d.f.a<c> {
        private long a;
        private String[] b;

        public e(long j, String[] strArr) {
            this.a = j;
            this.b = strArr;
        }

        public final void a(c cVar) {
            cVar.a(this.a, this.b);
        }
    }

    public static final class f implements com.ad4screen.sdk.d.f.a<d> {
        public final void a(d dVar) {
            dVar.a();
        }
    }

    public interface g {
        void a(com.ad4screen.sdk.service.modules.k.d.c cVar);

        void a(String str);
    }
}
