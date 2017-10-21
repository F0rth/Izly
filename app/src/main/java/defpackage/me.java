package defpackage;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

public final class me implements mh {
    private final kb a;
    private mi b;
    private SSLSocketFactory c;
    private boolean d;

    public me() {
        this(new jr());
    }

    public me(kb kbVar) {
        this.a = kbVar;
    }

    private void a() {
        synchronized (this) {
            this.d = false;
            this.c = null;
        }
    }

    private SSLSocketFactory b() {
        SSLSocketFactory sSLSocketFactory;
        synchronized (this) {
            if (this.c == null && !this.d) {
                this.c = c();
            }
            sSLSocketFactory = this.c;
        }
        return sSLSocketFactory;
    }

    private SSLSocketFactory c() {
        SSLSocketFactory socketFactory;
        synchronized (this) {
            this.d = true;
            try {
                mi miVar = this.b;
                SSLContext instance = SSLContext.getInstance("TLS");
                instance.init(null, new TrustManager[]{new mj(new mk(miVar.getKeyStoreStream(), miVar.getKeyStorePassword()), miVar)}, null);
                socketFactory = instance.getSocketFactory();
                this.a.a("Fabric", "Custom SSL pinning enabled");
            } catch (Throwable e) {
                this.a.c("Fabric", "Exception while validating pinned certs", e);
                socketFactory = null;
            }
        }
        return socketFactory;
    }

    public final mg a(int i, String str) {
        return a(i, str, Collections.emptyMap());
    }

    public final mg a(int i, String str, Map<String, String> map) {
        mg a;
        boolean z = true;
        switch (me$1.a[i - 1]) {
            case 1:
                a = mg.a((CharSequence) str, (Map) map, true);
                break;
            case 2:
                a = mg.b((CharSequence) str, (Map) map, true);
                break;
            case 3:
                a = mg.a((CharSequence) str);
                break;
            case 4:
                a = mg.b((CharSequence) str);
                break;
            default:
                throw new IllegalArgumentException("Unsupported HTTP method!");
        }
        if (str == null || !str.toLowerCase(Locale.US).startsWith("https")) {
            z = false;
        }
        if (z && this.b != null) {
            SSLSocketFactory b = b();
            if (b != null) {
                ((HttpsURLConnection) a.a()).setSSLSocketFactory(b);
            }
        }
        return a;
    }

    public final void a(mi miVar) {
        if (this.b != miVar) {
            this.b = miVar;
            a();
        }
    }
}
