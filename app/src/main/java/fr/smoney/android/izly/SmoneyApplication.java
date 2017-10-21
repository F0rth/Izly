package fr.smoney.android.izly;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.multidex.MultiDex;

import com.ad4screen.sdk.A4SApplication;
import com.crashlytics.android.Crashlytics;
import com.ezeeworld.b4s.android.sdk.B4SSettings;
import com.ezeeworld.b4s.android.sdk.monitor.MonitoringManager;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorTimeout;
import com.thewingitapp.thirdparties.wingitlib.exception.WGException;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;

import defpackage.ac;
import defpackage.cl;
import defpackage.ge;
import defpackage.ge$a;
import defpackage.gg;
import defpackage.js;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import rx.Subscriber;

public class SmoneyApplication extends A4SApplication {
    public static cl a;
    public static SmoneyRequestManager b;
    public static ac c;
    public static ge d;
    public static gg e;
    private static final String h = SmoneyApplication.class.getSimpleName();
    public boolean f;
    public boolean g;

    public final void a() {
        b = null;
        a = null;
        if (d != null) {
            ge geVar = d;
            new ge$a(geVar, geVar.a).run();
            geVar.a = null;
            d = null;
        }
        if (c != null) {
            ac acVar = c;
            acVar.f = null;
            acVar.a = null;
            acVar.b = false;
            acVar.c = null;
            acVar.d = false;
            c = null;
        }
        if (e != null) {
            gg ggVar = e;
            ggVar.f = null;
            ggVar.d = null;
            e = null;
        }
        Context applicationContext = getApplicationContext();
        a = new cl();
        b = new SmoneyRequestManager(applicationContext, a);
        d = new ge(applicationContext);
        c = new ac(applicationContext);
        e = new gg(applicationContext);
        B4SSettings init = B4SSettings.init(this, "c42aea69-a4af-47ef-bd51-1f75e2da796f");
        init.setShouldLogScanning(false);
        init.setShouldLogMatching(false);
        init.setShouldLogApi(false);
        MonitoringManager.ensureMonitoringService(this);
        WINGiTManager.initialize("5C77D2BD-6D7B-4345-8873-F0F0BF4E1CD7", "j6QQu5iyobUm8npNKuI4jhkm6HYZtw6oiVFyrl0IDaWWJopLRuujAXJpDBCS0q+mxMoierFHNLFF6AOkPNGKWnulmwVXxC3I8tN0j5ouDe9aUNJH94i4k1zSCkOq04+QfuxXH3xh4V5BDhsef222QWolPKqmp7FVLIhiOfABHZ0=", getApplicationContext(), new Subscriber(this) {
            final /* synthetic */ SmoneyApplication a;

            {
                this.a = r1;
            }

            public final void onCompleted() {
                SmoneyApplication.a.cz = true;
            }

            public final void onError(Throwable th) {
                if (!(th instanceof WGException)) {
                    this.a.getString(2131231897);
                } else if (th instanceof WGErrorTimeout) {
                    this.a.getString(2131231898);
                } else {
                    th.getMessage();
                }
                SmoneyApplication.a.cz = false;
            }

            public final void onNext(Object obj) {
            }
        });
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }

    public void onApplicationCreate() {
        super.onApplicationCreate();
        js.a((Context) this, new Crashlytics());
        if (VERSION.SDK_INT <= 8) {
            System.setProperty("http.keepAlive", "false");
        }
        this.g = false;
        a();
    }
}
