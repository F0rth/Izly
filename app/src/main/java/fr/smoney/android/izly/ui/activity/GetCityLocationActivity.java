package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGDetectedCity;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;
import com.thewingitapp.thirdparties.wingitlib.util.WINGiTUtil;

import defpackage.gg$1;
import defpackage.gg$2;
import defpackage.gg$3;
import defpackage.gg$a;
import defpackage.gg$b;
import defpackage.iz;
import defpackage.jg;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;

import java.io.PrintStream;

import rx.Subscriber;

public class GetCityLocationActivity extends AppCompatActivity implements gg$b {
    private Location a;
    private boolean b = false;
    @Bind({2131755265})
    View mLoader;

    private boolean a() {
        return ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_COARSE_LOCATION") == 0 || ContextCompat.checkSelfPermission(this, "android.permission.ACCESS_FINE_LOCATION") == 0;
    }

    private void b() {
        WGCity b = jg.b(this);
        if (b != null) {
            startActivity(CityEventActivity.a((Context) this, b));
        } else {
            startActivity(CitySelectActivity.a(this, false));
        }
    }

    public final void a(Location location) {
        Log.d("onLocation", "Received :");
        if (location != null) {
            Log.d("onLocation", "Received :" + location.getLatitude() + " " + location.getLongitude());
            if (this.a == null || (this.a != location && (location.getProvider().equals("network") || location.getProvider().equals("gps")))) {
                this.a = location;
            }
            if (this.a != null) {
                iz.a = this.a;
                WINGiTManager.getNearestSupportedCity(this.a.getLatitude(), this.a.getLongitude(), new Subscriber<WGDetectedCity>(this) {
                    final /* synthetic */ GetCityLocationActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onCompleted() {
                    }

                    public final void onError(Throwable th) {
                        this.a.b();
                        this.a.finish();
                    }

                    public final /* synthetic */ void onNext(Object obj) {
                        WGCity wGCity = (WGDetectedCity) obj;
                        if (wGCity == null || !wGCity.isInsideCity().booleanValue() || wGCity.getDistance().doubleValue() > 150000.0d) {
                            this.a.b();
                            this.a.finish();
                            return;
                        }
                        jg.a(this.a, wGCity);
                        this.a.startActivity(CityEventActivity.a(this.a, wGCity));
                        this.a.finish();
                    }
                });
                return;
            }
            return;
        }
        b();
        finish();
    }

    public final void a(boolean z) {
        this.b = z;
        if (!this.b) {
            b();
            finish();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_get_city_location);
        ButterKnife.bind(this);
        getSupportActionBar().hide();
        if (this.mLoader.getBackground() instanceof AnimationDrawable) {
            ((AnimationDrawable) this.mLoader.getBackground()).start();
        }
    }

    protected void onPause() {
        super.onPause();
        if (a()) {
            SmoneyApplication.e.d.remove(this);
            SmoneyApplication.e.a();
        }
    }

    protected void onResume() {
        boolean z = true;
        super.onResume();
        if (a()) {
            gg ggVar = SmoneyApplication.e;
            ggVar.b = ggVar.f.isProviderEnabled("network");
            ggVar.a = ggVar.f.isProviderEnabled("gps");
            ggVar.c = new gg$a(ggVar);
            if (ggVar.b || ggVar.a) {
                if (ggVar.b && ggVar.a) {
                    new Handler().postDelayed(new gg$1(ggVar), 10000);
                    ggVar.f.requestLocationUpdates("network", WINGiTUtil.ONE_MINUTE, 10.0f, ggVar);
                }
                if (!ggVar.b && ggVar.a) {
                    new Handler().postDelayed(new gg$2(ggVar), 10000);
                    ggVar.f.requestLocationUpdates("gps", WINGiTUtil.ONE_MINUTE, 10.0f, ggVar);
                }
                if (ggVar.b && !ggVar.a) {
                    new Handler().postDelayed(new gg$3(ggVar), 10000);
                    ggVar.f.requestLocationUpdates("network", WINGiTUtil.ONE_MINUTE, 10.0f, ggVar);
                }
            } else {
                ggVar.e = null;
                ggVar.onLocationChanged(null);
            }
            if (ggVar.c != null) {
                if (ggVar.c.hasMessages(1)) {
                    ggVar.c.removeMessages(1);
                }
                ggVar.c.sendEmptyMessageDelayed(1, 10000);
            }
            gg ggVar2 = SmoneyApplication.e;
            ggVar2.d.add(this);
            boolean z2 = ggVar2.a || ggVar2.b;
            gg.a(this, z2);
            if (ggVar2.e != null) {
                System.out.println("addMyLocationListener");
                PrintStream printStream = System.out;
                if (("mLocation!=null?:" + ggVar2.e) == null) {
                    z = false;
                }
                printStream.println(z);
                gg.a(this, ggVar2.e);
                return;
            }
            return;
        }
        b();
        finish();
    }
}
