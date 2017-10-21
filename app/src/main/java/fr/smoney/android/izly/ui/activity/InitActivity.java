package fr.smoney.android.izly.ui.activity;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorTimeout;
import com.thewingitapp.thirdparties.wingitlib.exception.WGException;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGDetectedCity;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;

import defpackage.iz;
import fr.smoney.android.izly.R;
import rx.Subscriber;

public class InitActivity extends AppCompatActivity {
    public static String a = "j6QQu5iyobUm8npNKuI4jhkm6HYZtw6oiVFyrl0IDaWWJopLRuujAXJpDBCS0q+mxMoierFHNLFF6AOkPNGKWnulmwVXxC3I8tN0j5ouDe9aUNJH94i4k1zSCkOq04+QfuxXH3xh4V5BDhsef222QWolPKqmp7FVLIhiOfABHZ0=";
    @Bind({2131755280})
    AppCompatButton mCityButton;
    @Bind({2131755279})
    AppCompatButton mLocationNotSupportedButton;
    @Bind({2131755278})
    AppCompatButton mLocationSupportedButton;

    @OnClick({2131755280})
    public void onCityClick() {
        startActivity(CitySelectActivity.a(this, false));
    }

    protected void onCreate(@Nullable Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_init);
        ButterKnife.bind(this);
    }

    @OnClick({2131755279})
    public void onLocationNotSupportedClick() {
        Location location = new Location("");
        location.setLatitude(19.432608d);
        location.setLongitude(-99.133209d);
        iz.a = location;
        WINGiTManager.getNearestSupportedCity(location.getLatitude(), location.getLongitude(), new Subscriber<WGDetectedCity>(this) {
            final /* synthetic */ InitActivity a;

            {
                this.a = r1;
            }

            public final void onCompleted() {
            }

            public final void onError(Throwable th) {
                this.a.startActivity(CitySelectActivity.a(this.a, false));
            }

            public final /* synthetic */ void onNext(Object obj) {
                WGCity wGCity = (WGDetectedCity) obj;
                if (wGCity == null || !wGCity.isInsideCity().booleanValue() || wGCity.getDistance().doubleValue() > 150000.0d) {
                    this.a.startActivity(CitySelectActivity.a(this.a, false));
                } else {
                    this.a.startActivity(CityEventActivity.a(this.a, wGCity));
                }
            }
        });
    }

    @OnClick({2131755278})
    public void onLocationSupportedClick() {
        Location location = new Location("");
        location.setLatitude(48.85341d);
        location.setLongitude(2.3488d);
        iz.a = location;
        WINGiTManager.getNearestSupportedCity(location.getLatitude(), location.getLongitude(), new Subscriber<WGDetectedCity>(this) {
            final /* synthetic */ InitActivity a;

            {
                this.a = r1;
            }

            public final void onCompleted() {
            }

            public final void onError(Throwable th) {
                this.a.startActivity(CitySelectActivity.a(this.a, false));
            }

            public final /* synthetic */ void onNext(Object obj) {
                WGCity wGCity = (WGDetectedCity) obj;
                if (wGCity == null || !wGCity.isInsideCity().booleanValue() || wGCity.getDistance().doubleValue() > 150000.0d) {
                    this.a.startActivity(CitySelectActivity.a(this.a, false));
                } else {
                    this.a.startActivity(CityEventActivity.a(this.a, wGCity));
                }
            }
        });
    }

    protected void onStart() {
        super.onStart();
        this.mLocationSupportedButton.setVisibility(8);
        this.mLocationNotSupportedButton.setVisibility(8);
        this.mCityButton.setVisibility(8);
        WINGiTManager.initialize("5C77D2BD-6D7B-4345-8873-F0F0BF4E1CD7", a, getApplicationContext(), new Subscriber(this) {
            final /* synthetic */ InitActivity a;

            {
                this.a = r1;
            }

            public final void onCompleted() {
            }

            public final void onError(Throwable th) {
                CharSequence string = th instanceof WGException ? th instanceof WGErrorTimeout ? this.a.getString(R.string.wingit_errors_message_network_not_connected) : th.getMessage() : this.a.getString(R.string.wingit_errors_message_network_error);
                Toast.makeText(this.a, string, 0).show();
            }

            public final void onNext(Object obj) {
                this.a.mLocationSupportedButton.setVisibility(0);
                this.a.mLocationNotSupportedButton.setVisibility(0);
                this.a.mCityButton.setVisibility(0);
            }
        });
    }
}
