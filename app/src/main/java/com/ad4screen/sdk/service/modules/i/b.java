package com.ad4screen.sdk.service.modules.i;

public final class b {

    public interface a {
        void a(String[] strArr);
    }

    public static final class b implements com.ad4screen.sdk.d.f.a<a> {
        private String[] a;

        public b(String[] strArr) {
            this.a = strArr;
        }

        public final void a(a aVar) {
            aVar.a(this.a);
        }
    }
}
