package fr.smoney.android.izly.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import defpackage.hw;
import defpackage.ib;
import defpackage.ie;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetConfidentialitySettingsData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.SetConfidentialitySettingsData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class ChangeConfidentialitySettingsActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private int b = -1;
    private int c = -1;
    private GetConfidentialitySettingsData d;
    private Button e;
    private Button f;
    private Button g;
    private Button h;
    private Button i;
    private Button j;
    private a k;

    final class a extends BroadcastReceiver {
        final /* synthetic */ ChangeConfidentialitySettingsActivity a;

        private a(ChangeConfidentialitySettingsActivity changeConfidentialitySettingsActivity) {
            this.a = changeConfidentialitySettingsActivity;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
                this.a.l();
            }
        }
    }

    private void a(GetConfidentialitySettingsData getConfidentialitySettingsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (getConfidentialitySettingsData == null) {
            a(hw.a(this, this));
        } else {
            this.d = getConfidentialitySettingsData;
            k();
        }
    }

    private void a(SetConfidentialitySettingsData setConfidentialitySettingsData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (setConfidentialitySettingsData == null) {
            a(hw.a(this, this));
        } else {
            switch (this.b) {
                case 0:
                    this.d.a = this.c;
                    this.e.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
                case 1:
                    this.d.b = this.c;
                    this.f.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
                case 2:
                    this.d.c = this.c;
                    this.g.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
                case 3:
                    this.d.d = this.c;
                    this.h.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
                case 4:
                    this.d.e = this.c;
                    this.i.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
                case 5:
                    this.d.f = this.c;
                    this.j.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.c]);
                    break;
            }
            this.b = -1;
            this.c = -1;
        }
    }

    private void k() {
        this.e.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.a]);
        this.f.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.b]);
        this.g.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.c]);
        this.h.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.d]);
        this.i.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.e]);
        this.j.setText(getResources().getStringArray(R.array.change_confidentiality_settings_parameters)[this.d.f]);
    }

    private void l() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 171 && intent.getStringExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsSessionId").equals(str2)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 171);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.getConfidentialitySettingsSessionId", str2);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aI = null;
        j.f.aJ = null;
        super.a(keyAt, 171, true);
    }

    private void m() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        int i2 = this.b == 0 ? this.c : this.d.a;
        int i3 = this.b == 1 ? this.c : this.d.b;
        int i4 = this.b == 2 ? this.c : this.d.c;
        int i5 = this.b == 3 ? this.c : this.d.d;
        int i6 = this.b == 4 ? this.c : this.d.e;
        int i7 = this.b == 5 ? this.c : this.d.f;
        int size = j.b.size();
        for (int i8 = 0; i8 < size; i8++) {
            Intent intent = (Intent) j.b.valueAt(i8);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 172 && intent.getStringExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsSessionId").equals(str2) && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoto", -1) == i2 && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsName", -1) == i3 && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoneNumber", -1) == i4 && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsNickname", -1) == i5 && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsAddress", -1) == i6 && intent.getIntExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsEmail", -1) == i7) {
                keyAt = j.b.keyAt(i8);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 172);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoto", i2);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsName", i3);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsPhoneNumber", i4);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsNickname", i5);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsAddress", i6);
        intent2.putExtra("fr.smoney.android.izly.extras.setConfidentialitySettingsEmail", i7);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.aK = null;
        j.f.aL = null;
        super.a(keyAt, 172, true);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void a(defpackage.ie r5, android.os.Bundle r6) {
        /*
        r4 = this;
        r0 = 0;
        r1 = 1;
        r2 = fr.smoney.android.izly.ui.ChangeConfidentialitySettingsActivity.AnonymousClass1.a;
        r3 = r5.ordinal();
        r2 = r2[r3];
        switch(r2) {
            case 1: goto L_0x0011;
            case 2: goto L_0x0029;
            default: goto L_0x000d;
        };
    L_0x000d:
        super.a(r5, r6);
    L_0x0010:
        return;
    L_0x0011:
        r0 = r4.h();
        r1 = 171; // 0xab float:2.4E-43 double:8.45E-322;
        if (r0 != r1) goto L_0x001d;
    L_0x0019:
        r4.l();
        goto L_0x0010;
    L_0x001d:
        r1 = 172; // 0xac float:2.41E-43 double:8.5E-322;
        if (r0 != r1) goto L_0x0025;
    L_0x0021:
        r4.m();
        goto L_0x0010;
    L_0x0025:
        super.a(r5, r6);
        goto L_0x0010;
    L_0x0029:
        r2 = "Data.SelectItem";
        r2 = r6.getInt(r2);
        r4.c = r2;
        r2 = r4.b;
        r3 = r4.c;
        switch(r2) {
            case 0: goto L_0x003e;
            case 1: goto L_0x0046;
            case 2: goto L_0x004d;
            case 3: goto L_0x0054;
            case 4: goto L_0x005b;
            case 5: goto L_0x0062;
            default: goto L_0x0038;
        };
    L_0x0038:
        if (r0 == 0) goto L_0x0010;
    L_0x003a:
        r4.m();
        goto L_0x0010;
    L_0x003e:
        r2 = r4.d;
        r2 = r2.a;
        if (r2 == r3) goto L_0x0038;
    L_0x0044:
        r0 = r1;
        goto L_0x0038;
    L_0x0046:
        r2 = r4.d;
        r2 = r2.b;
        if (r2 != r3) goto L_0x0044;
    L_0x004c:
        goto L_0x0038;
    L_0x004d:
        r2 = r4.d;
        r2 = r2.c;
        if (r2 != r3) goto L_0x0044;
    L_0x0053:
        goto L_0x0038;
    L_0x0054:
        r2 = r4.d;
        r2 = r2.d;
        if (r2 != r3) goto L_0x0044;
    L_0x005a:
        goto L_0x0038;
    L_0x005b:
        r2 = r4.d;
        r2 = r2.e;
        if (r2 != r3) goto L_0x0044;
    L_0x0061:
        goto L_0x0038;
    L_0x0062:
        r2 = r4.d;
        r2 = r2.f;
        if (r2 != r3) goto L_0x0044;
    L_0x0068:
        goto L_0x0038;
        */
        throw new UnsupportedOperationException("Method not decompiled: fr.smoney.android.izly.ui.ChangeConfidentialitySettingsActivity.a(ie, android.os.Bundle):void");
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 171) {
                a((GetConfidentialitySettingsData) bundle.getParcelable("fr.smoney.android.izly.extras.getConfidentialitySettingsData"), serverError);
            } else if (i2 == 172) {
                a((SetConfidentialitySettingsData) bundle.getParcelable("fr.smoney.android.izly.extras.setConfidentialitySettingsData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 171) {
                    finish();
                    return;
                } else {
                    super.b(ieVar);
                    return;
                }
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 171) {
            a(i2.aI, i2.aJ);
        } else if (i == 172) {
            a(i2.aK, i2.aL);
        }
    }

    public final void d(ie ieVar) {
        int h = h();
        if (ieVar == ie.ProgressType && (h == 171 || h == 172)) {
            g();
            finish();
            return;
        }
        super.d(ieVar);
    }

    public void onClick(View view) {
        if (view == this.e) {
            this.b = 0;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_photo), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        } else if (view == this.f) {
            this.b = 1;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_firstname_lastname), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        } else if (view == this.g) {
            this.b = 2;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_mobile), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        } else if (view == this.h) {
            this.b = 3;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_nickname), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        } else if (view == this.i) {
            this.b = 4;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_address), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        } else if (view == this.j) {
            this.b = 5;
            a(ib.a(getString(R.string.change_confidentiality_settings_tv_email), getResources().getTextArray(R.array.change_confidentiality_settings_parameters), this, ie.ConfidentialitySettingType));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.change_confidentiality_settings);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.e = (Button) findViewById(R.id.sp_photo);
        this.e.setOnClickListener(this);
        this.f = (Button) findViewById(R.id.sp_firstname_lastname);
        this.f.setOnClickListener(this);
        this.g = (Button) findViewById(R.id.sp_mobile);
        this.g.setOnClickListener(this);
        this.h = (Button) findViewById(R.id.sp_nickname);
        this.h.setOnClickListener(this);
        this.i = (Button) findViewById(R.id.sp_address);
        this.i.setOnClickListener(this);
        this.j = (Button) findViewById(R.id.sp_email);
        this.j.setOnClickListener(this);
        if (bundle != null) {
            this.b = bundle.getInt("savedInstanceStateCurrentParameter");
            this.c = bundle.getInt("savedInstanceStateCurrentItemSelected");
            this.d = (GetConfidentialitySettingsData) bundle.getParcelable("savedInstanceStateGetConfidentialitySettings");
        }
        if (this.d == null) {
            l();
        } else {
            k();
        }
        jb.a(getApplicationContext(), R.string.screen_name_confidential_settings_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        finish();
        return true;
    }

    public void onPause() {
        unregisterReceiver(this.k);
        this.k = null;
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.k == null) {
            this.k = new a();
        }
        registerReceiver(this.k, intentFilter);
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("savedInstanceStateCurrentParameter", this.b);
        bundle.putInt("savedInstanceStateCurrentItemSelected", this.c);
        bundle.putParcelable("savedInstanceStateGetConfidentialitySettings", this.d);
        super.onSaveInstanceState(bundle);
    }
}
