package com.ad4screen.sdk;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.c.a.f;
import com.ad4screen.sdk.c.a.g;
import com.ad4screen.sdk.common.annotations.API;
import com.ad4screen.sdk.common.b.m;
import com.ad4screen.sdk.common.c.e;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.plugins.model.Beacon.PersonalParamsReplacer;

@API
public class A4SPopup extends Activity {
    public static final int FLAG_INAPP = 1;
    public static final int FLAG_PUSH = 2;
    public static final int FLAG_SHOW_APP_ICON = 4;
    private static e a = new e();
    private boolean b = false;
    private Integer c;
    private g d;
    private int e;

    final class a implements OnClickListener {
        final /* synthetic */ A4SPopup a;
        private final com.ad4screen.sdk.c.a.g.a b;

        private a(A4SPopup a4SPopup, com.ad4screen.sdk.c.a.g.a aVar) {
            this.a = a4SPopup;
            this.b = aVar;
        }

        private void a() {
            if (this.a.c != null) {
                ((NotificationManager) this.a.getSystemService("notification")).cancel(this.a.c.intValue());
            }
        }

        private void a(d dVar) {
            if (dVar instanceof f) {
                try {
                    this.a.startActivity(((f) dVar).a);
                    return;
                } catch (Throwable e) {
                    Log.error("A4SPopup|Could not open popup landing page", e);
                    return;
                }
            }
            Log.warn("A4SPopup|Unsupported target type for push : " + dVar.getClass());
        }

        public final void onClick(View view) {
            if (this.a.b()) {
                com.ad4screen.sdk.d.f.a().a(new com.ad4screen.sdk.service.modules.inapp.e.c(this.a.d.h, -1, "com_ad4screen_sdk_theme_popup", this.b.a(), null));
            }
            if (this.a.a()) {
                d d = this.b.d();
                if (d != null) {
                    a();
                    a(d);
                }
            }
            if (!this.a.isFinishing()) {
                this.a.finish();
            }
        }
    }

    public interface b {
        void a(A4SPopup a4SPopup);
    }

    public static final class c implements com.ad4screen.sdk.d.f.a<b> {
        private A4SPopup a;

        public c(A4SPopup a4SPopup) {
            this.a = a4SPopup;
        }

        public final void a(b bVar) {
            bVar.a(this.a);
        }
    }

    private Button a(LinearLayout linearLayout, int i) {
        int i2;
        switch (i) {
            case 0:
                i2 = R.id.com_ad4screen_sdk_popup_primary_button;
                break;
            case 1:
                i2 = R.id.com_ad4screen_sdk_popup_secondary_button;
                break;
            default:
                i2 = R.id.com_ad4screen_sdk_popup_other_button;
                break;
        }
        return (Button) linearLayout.findViewById(i2);
    }

    private void a(int i) {
        LinearLayout linearLayout = (LinearLayout) ((ViewGroup) getWindow().getDecorView()).getChildAt(0);
        if (linearLayout == null) {
            Log.error("A4SPopup|LinearLayout of DecorView is not found");
            return;
        }
        View childAt = linearLayout.getChildAt(0);
        if (childAt != null) {
            childAt.setVisibility(8);
        }
        linearLayout.addView((LinearLayout) getLayoutInflater().inflate(i, null), 0);
    }

    private void a(LinearLayout linearLayout) {
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2, 1.0f);
        com.ad4screen.sdk.c.a.g.a[] d = this.d.d();
        int i = d.length >= 3 ? 1 : 0;
        if (i != 0) {
            linearLayout.setOrientation(1);
            layoutParams.width = -1;
        }
        int i2 = 0;
        int i3 = 0;
        while (i3 < d.length) {
            com.ad4screen.sdk.c.a.g.a aVar = d[i3];
            Button a = i3 < 3 ? a(linearLayout, i3) : null;
            if (a == null) {
                a = new Button(this, null, c(i3));
                linearLayout.addView(a);
                a.setLayoutParams(layoutParams);
                Log.debug("A4SPopup|button[" + i3 + "] is created");
            } else {
                Log.debug("A4SPopup|button[" + i3 + "] is retrieved");
                if (i != 0) {
                    a.setLayoutParams(layoutParams);
                    a.setVisibility(0);
                }
                i2++;
            }
            a.setText(aVar.b());
            a.setOnClickListener(new a(aVar));
            i3++;
        }
        b(linearLayout, i2);
    }

    private void a(TextView textView, TextView textView2) {
        int f = f();
        ViewParent parent = textView2.getParent();
        if (parent instanceof View) {
            View view = (View) parent;
            textView.setPadding(view.getPaddingLeft(), view.getPaddingBottom(), view.getPaddingRight(), view.getPaddingTop());
            return;
        }
        textView.setPadding(f, f, f, f);
    }

    private void a(String str) {
        TextView textView;
        setTitle(str);
        Log.debug("A4SPopup|title : " + str);
        if (this.b) {
            textView = (TextView) findViewById(R.id.com_ad4screen_popup_title);
            if (textView != null) {
                textView.setText(str);
            } else {
                Log.warn("A4SPopup|TextView with com_ad4screen_popup_title is not found");
            }
        } else {
            textView = (TextView) findViewById(16908310);
        }
        if (textView != null) {
            textView.setSingleLine(false);
            textView.setMaxLines(2);
        }
    }

    private void a(String str, Throwable th) {
        if (th != null) {
            Log.error(str, th);
        } else {
            Log.error(str);
        }
        if (!isFinishing()) {
            finish();
        }
    }

    private boolean a() {
        return (this.e & 2) != 0;
    }

    private LinearLayout b(int i) {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.com_ad4screen_sdk_popup_rootview);
        if (linearLayout == null) {
            Log.warn("A4SPopup|LinearLayout with id com_ad4screen_sdk_popup_rootview is not found");
            return null;
        }
        LinearLayout linearLayout2 = (LinearLayout) getLayoutInflater().inflate(i, null);
        if (linearLayout2 == null) {
            Log.warn("A4SPopup|LinearLayout for buttons is not inflated");
            return null;
        }
        linearLayout.addView(linearLayout2);
        return linearLayout2;
    }

    private void b(LinearLayout linearLayout, int i) {
        while (i < 3) {
            Button a = a(linearLayout, i);
            if (a != null) {
                a.setVisibility(8);
                Log.verbose("A4SPopup|hideNonRetrievedButtons|button[" + i + "] is gone");
            } else {
                Log.verbose("A4SPopup|hideNonRetrievedButtons|button[" + i + "] is not existed");
            }
            i++;
        }
    }

    private void b(String str) {
        a(str, null);
    }

    private boolean b() {
        return (this.e & 1) != 0;
    }

    public static Intent build(Context context, int i, g gVar) {
        String jSONObject;
        Intent intent = new Intent(context, A4SPopup.class);
        intent.setFlags(1082130432);
        intent.putExtra("com.ad4screen.sdk.Popup.flags", i);
        try {
            jSONObject = a.a(gVar).toString();
        } catch (Throwable e) {
            Log.internal("A4SPopup|Error while serializing Format", e);
            jSONObject = null;
        }
        if (jSONObject != null) {
            intent.putExtra("com.ad4screen.sdk.Popup.format", jSONObject);
        } else {
            Log.warn("A4SPopup|Could not serialize PopupFormat as JSON");
        }
        return intent;
    }

    public static Intent build(Context context, int i, g gVar, int i2) {
        Intent build = build(context, i, gVar);
        build.putExtra("com.ad4screen.sdk.Popup.systemNotificationId", i2);
        return build;
    }

    private int c(int i) {
        switch (i) {
            case 0:
                return b() ? R.attr.com_ad4screen_sdk_popup_inapp_primary_button : R.attr.com_ad4screen_sdk_popup_notif_primary_button;
            case 1:
                return b() ? R.attr.com_ad4screen_sdk_popup_inapp_secondary_button : R.attr.com_ad4screen_sdk_popup_notif_secondary_button;
            default:
                return b() ? R.attr.com_ad4screen_sdk_popup_inapp_other_button : R.attr.com_ad4screen_sdk_popup_notif_other_button;
        }
    }

    private boolean c() {
        return (this.e & 4) != 0;
    }

    private boolean d() {
        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            b("A4SPopup|No extras parameters found");
            return false;
        }
        this.c = (Integer) i.a(Integer.class, extras, "com.ad4screen.sdk.Popup.systemNotificationId", null);
        this.e = extras.getInt("com.ad4screen.sdk.Popup.flags", 0);
        try {
            this.d = (g) a.a(extras.getString("com.ad4screen.sdk.Popup.format"), new d());
            if (this.d != null) {
                return true;
            }
            b("A4SPopup|Invalid parameter for extra : 'com.ad4screen.sdk.Popup.format'");
            return false;
        } catch (Throwable e) {
            a("A4SPopup|An error occurred while deserializing extra parameters", e);
            return false;
        }
    }

    private void e() {
        int f = f();
        TextView textView = (TextView) findViewById(R.id.com_ad4screen_sdk_popup_textview);
        if (textView == null) {
            Log.warn("A4SPopup|TextView with id com_ad4screen_sdk_popup_textview is not found");
            return;
        }
        TextView textView2 = this.b ? (TextView) findViewById(R.id.com_ad4screen_popup_title) : (TextView) findViewById(16908310);
        if (textView2 == null) {
            textView.setPadding(f, f, f, f);
        } else if (c()) {
            a(textView, textView2);
        } else if (this.b) {
            a(textView, textView2);
        } else {
            textView.setPadding(textView2.getPaddingLeft(), textView2.getPaddingBottom(), textView2.getPaddingRight(), textView2.getPaddingTop());
        }
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = f;
        if (VERSION.SDK_INT < 21) {
            layoutParams.bottomMargin = f;
        }
        textView.setLayoutParams(layoutParams);
        textView.setText(this.d.l != null ? d.a(this.d.b(), new PersonalParamsReplacer(this, this.d.l)) : d.a(this.d.b(), null));
    }

    private int f() {
        return VERSION.SDK_INT >= 21 ? i.a((Context) this, 24) : i.a((Context) this, 10);
    }

    private int g() {
        try {
            return getApplicationContext().getPackageManager().getActivityInfo(new ComponentName(getApplicationContext(), A4SPopup.class), 0).theme;
        } catch (Throwable e) {
            Log.internal("A4SPopup|Can't find A4SPopup theme, using default ones", e);
            return R.style.com_ad4screen_sdk_theme_popup;
        }
    }

    protected void onCreate(Bundle bundle) {
        boolean z = false;
        if (d()) {
            if (c()) {
                requestWindowFeature(3);
            }
            super.onCreate(bundle);
            if (this.d.o) {
                com.ad4screen.sdk.service.modules.inapp.g.a(this.d);
                com.ad4screen.sdk.d.f.a().a(new c(this));
                Log.warn("A4SPopup|Popup with id #" + this.d.h + " has not been displayed since you're in control group");
                finish();
                return;
            }
            ImageView imageView;
            m.e.a(this);
            TypedArray obtainStyledAttributes = obtainStyledAttributes(g(), R.styleable.PopupLayout);
            int resourceId = b() ? obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_inapp_title_layout, 0) : obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_notif_title_layout, 0);
            int resourceId2 = b() ? obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_inapp_layout, 0) : obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_notif_layout, 0);
            int resourceId3 = b() ? obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_inapp_buttons_layout, 0) : obtainStyledAttributes.getResourceId(R.styleable.PopupLayout_com_ad4screen_sdk_popup_notif_buttons_layout, 0);
            if (resourceId != 0) {
                z = true;
            }
            this.b = z;
            if (this.b) {
                a(resourceId);
                imageView = (ImageView) findViewById(R.id.com_ad4screen_popup_left_icon);
                if (imageView == null) {
                    Log.warn("A4SPopup|ImageView with com_ad4screen_popup_left_icon is not found and will be ignored");
                }
            } else {
                imageView = null;
            }
            setContentView(resourceId2);
            if (c()) {
                if (!this.b || imageView == null) {
                    getWindow().setFeatureDrawableResource(3, i.d(this));
                } else {
                    imageView.setImageResource(i.d(this));
                }
            } else if (this.b && imageView != null) {
                imageView.setVisibility(8);
            }
            if (this.d.a() != null) {
                a(this.d.a());
            } else if (this.b) {
                a(getString(getApplicationInfo().labelRes));
            }
            e();
            LinearLayout b = b(resourceId3);
            if (b == null) {
                b("A4SPopup|Buttons layout is not inflated");
                return;
            }
            a(b);
            com.ad4screen.sdk.d.f.a().a(new c(this));
            if (b() && bundle == null) {
                Log.debug("A4SPopup|Popup displayed with id #" + this.d.h);
                com.ad4screen.sdk.d.f.a().a(new com.ad4screen.sdk.service.modules.inapp.e.f(this.d.h, -1, "com_ad4screen_sdk_theme_popup", null, this.d.o));
            }
        }
    }

    protected void onPause() {
        super.onPause();
        if (!isFinishing()) {
            finish();
        }
        if (b()) {
            Log.debug("A4SPopup|Popup closing with id #" + this.d.h);
            com.ad4screen.sdk.d.f.a().a(new com.ad4screen.sdk.service.modules.inapp.e.d(this.d.h, -1, "com_ad4screen_sdk_theme_popup", false, false));
        }
    }
}
