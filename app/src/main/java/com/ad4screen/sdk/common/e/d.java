package com.ad4screen.sdk.common.e;

public final class d {

    public interface c {
        void a(c cVar, String str);

        void b(c cVar, String str);
    }

    public static final class a implements com.ad4screen.sdk.d.f.a<c> {
        c a;
        String b;

        public a(c cVar, String str) {
            this.a = cVar;
            this.b = str;
        }

        public final void a(c cVar) {
            cVar.b(this.a, this.b);
        }
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<c> {
        c a;
        String b;

        public b(c cVar, String str) {
            this.a = cVar;
            this.b = str;
        }

        public final void a(c cVar) {
            cVar.a(this.a, this.b);
        }
    }
}
