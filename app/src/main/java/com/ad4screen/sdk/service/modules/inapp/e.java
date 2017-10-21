package com.ad4screen.sdk.service.modules.inapp;

import java.util.HashMap;

public final class e {

    public interface n {
        void a(String str, int i, String str2, boolean z);
    }

    public interface m {
        void a(String str, int i, String str2, HashMap<String, String> hashMap, boolean z);
    }

    public interface j {
        void a(String str, int i, String str2, String str3, HashMap<String, String> hashMap);
    }

    public interface k {
        void a(String str, int i, String str2, boolean z, boolean z2);
    }

    public interface l {
        void a(com.ad4screen.sdk.service.modules.inapp.a.f fVar, boolean z);

        void a(boolean z);
    }

    interface i {
        void a();
    }

    interface h {
        void a(String str);
    }

    static final class a implements com.ad4screen.sdk.d.f.a<h> {
        String a;

        public a(String str) {
            this.a = str;
        }

        public final void a(h hVar) {
            hVar.a(this.a);
        }
    }

    static final class b implements com.ad4screen.sdk.d.f.a<i> {
        b() {
        }

        public final void a(i iVar) {
            iVar.a();
        }
    }

    public static final class c implements com.ad4screen.sdk.d.f.a<j> {
        private String a;
        private String b;
        private int c;
        private String d;
        private HashMap<String, String> e;

        public c(String str, int i, String str2, String str3, HashMap<String, String> hashMap) {
            this.a = str;
            this.b = str3;
            this.d = str2;
            this.e = hashMap;
            this.c = i;
        }

        public final void a(j jVar) {
            jVar.a(this.a, this.c, this.d, this.b, this.e);
        }
    }

    public static final class d implements com.ad4screen.sdk.d.f.a<k> {
        private String a;
        private boolean b;
        private int c;
        private String d;
        private boolean e;

        public d(String str, int i, String str2, boolean z, boolean z2) {
            this.a = str;
            this.b = z;
            this.d = str2;
            this.c = i;
            this.e = z2;
        }

        public final void a(k kVar) {
            kVar.a(this.a, this.c, this.d, this.b, this.e);
        }
    }

    public static final class e implements com.ad4screen.sdk.d.f.a<l> {
        private com.ad4screen.sdk.service.modules.inapp.a.f a;
        private boolean b;
        private final boolean c = true;

        public e(com.ad4screen.sdk.service.modules.inapp.a.f fVar, boolean z) {
            this.a = fVar;
            this.b = z;
        }

        public e(boolean z) {
            this.b = z;
        }

        public final void a(l lVar) {
            if (this.c) {
                lVar.a(this.a, this.b);
            } else {
                lVar.a(this.b);
            }
        }
    }

    public static final class f implements com.ad4screen.sdk.d.f.a<m> {
        private String a;
        private int b;
        private String c;
        private HashMap<String, String> d;
        private boolean e;

        public f(String str, int i, String str2, HashMap<String, String> hashMap, boolean z) {
            this.a = str;
            this.c = str2;
            this.d = hashMap;
            this.b = i;
            this.e = z;
        }

        public final void a(m mVar) {
            mVar.a(this.a, this.b, this.c, this.d, this.e);
        }
    }

    public static final class g implements com.ad4screen.sdk.d.f.a<n> {
        private String a;
        private int b;
        private String c;
        private boolean d;

        public g(String str, int i, String str2, boolean z) {
            this.a = str;
            this.c = str2;
            this.b = i;
            this.d = z;
        }

        public final void a(n nVar) {
            nVar.a(this.a, this.b, this.c, this.d);
        }
    }
}
