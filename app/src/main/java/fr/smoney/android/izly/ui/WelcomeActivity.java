package fr.smoney.android.izly.ui;

import android.annotation.SuppressLint;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.nineoldandroids.view.ViewHelper;
import com.viewpagerindicator.CirclePageIndicator;

import defpackage.hs;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;

public class WelcomeActivity extends AppCompatActivity implements OnClickListener {
    private ViewPager a;
    private PagerAdapter b;
    private Button c;
    private Button d;

    final class a extends FragmentStatePagerAdapter {
        final /* synthetic */ WelcomeActivity a;

        public a(WelcomeActivity welcomeActivity, FragmentManager fragmentManager) {
            this.a = welcomeActivity;
            super(fragmentManager);
        }

        public final int getCount() {
            return 3;
        }

        public final Fragment getItem(int i) {
            return hs.a(i);
        }
    }

    public void onBackPressed() {
        if (this.a.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            this.a.setCurrentItem(this.a.getCurrentItem() - 1);
        }
    }

    public void onClick(View view) {
        if (view == this.d) {
            setResult(-1);
        }
        finish();
    }

    @SuppressLint({"NewApi"})
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.welcome_activity);
        this.a = (ViewPager) findViewById(R.id.pager);
        this.b = new a(this, getSupportFragmentManager());
        this.a.setAdapter(this.b);
        CirclePageIndicator circlePageIndicator = (CirclePageIndicator) findViewById(R.id.indicator);
        circlePageIndicator.setViewPager(this.a);
        if (VERSION.SDK_INT < 11) {
            ViewHelper.setScaleY(circlePageIndicator, 1.5f);
            ViewHelper.setScaleX(circlePageIndicator, 1.5f);
        } else {
            circlePageIndicator.setScaleX(1.5f);
            circlePageIndicator.setScaleY(1.5f);
        }
        if (VERSION.SDK_INT >= 11) {
            getSupportActionBar().hide();
        }
        this.c = (Button) findViewById(R.id.tv_welcome_view_connect);
        this.d = (Button) findViewById(R.id.tv_welcome_view_subscribe);
        this.c.setOnClickListener(this);
        this.d.setOnClickListener(this);
        if (!SmoneyApplication.c.e()) {
            this.d.setVisibility(4);
        }
    }

    protected void onStart() {
        super.onStart();
        jb.a(getApplicationContext(), R.string.screen_name_welcome_activity);
    }
}
