package com.ad4screen.sdk.a;

import android.os.Bundle;

public final class c {

    public interface a {
        void a(Bundle bundle);
    }

    public interface b {
        void a(Bundle bundle);
    }

    public static final class c implements com.ad4screen.sdk.d.f.a<a> {
        private Bundle a;

        public c(Bundle bundle) {
            this.a = bundle;
        }

        public final void a(a aVar) {
            aVar.a(this.a);
        }
    }

    public static final class d implements com.ad4screen.sdk.d.f.a<b> {
        private Bundle a;

        public d(Bundle bundle) {
            this.a = bundle;
        }

        public final void a(b bVar) {
            bVar.a(this.a);
        }
    }
}
