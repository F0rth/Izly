package fr.smoney.android.izly.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import defpackage.jb;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.LoginData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.model.ServiceData;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;

public class CGUSmoneyActivity extends SmoneyABSActivity implements OnCheckedChangeListener, SmoneyRequestManager$a {
    private boolean b;
    private String c;
    private String d;
    private cl e;
    private LoginData f;
    private a g;
    @Bind({2131755235})
    public Button mConfirmBtn;
    @Bind({2131755232})
    public TextView mSmoneyCgu;
    @Bind({2131755233})
    public CheckBox mSmoneyCguCheckbox;
    @Bind({2131755228})
    public CheckBox mSmoneyOffersCheckbox;
    @Bind({2131755226})
    public View mSmoneyOffersView;
    @Bind({2131755231})
    public CheckBox mSmoneyPartnersOffersCheckbox;
    @Bind({2131755229})
    public View mSmoneyPartnersOffersView;
    @Bind({2131755225})
    public TextView mSubtitle;

    final class a extends BroadcastReceiver {
        final /* synthetic */ CGUSmoneyActivity a;

        private a(CGUSmoneyActivity cGUSmoneyActivity) {
            this.a = cGUSmoneyActivity;
        }

        public final void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("fr.smoney.android.izly.sessionState", -1) == 1) {
                this.a.k();
            }
        }
    }

    private void b(ServerError serverError) {
        if (serverError != null) {
            a(serverError);
            return;
        }
        if (this.f != null) {
            for (ServiceData serviceData : this.f.J) {
                if (serviceData.a == 1) {
                    break;
                }
            }
            ServiceData serviceData2 = null;
            if (serviceData2 == null) {
                this.f.J.add(new ServiceData(1, false));
            } else {
                serviceData2.b = false;
            }
        }
        if (this.c != null) {
            this.f.C = this.mSmoneyOffersCheckbox.isChecked();
        }
        if (this.d != null) {
            this.f.D = this.mSmoneyPartnersOffersCheckbox.isChecked();
        }
        finish();
        startActivity(new Intent(this, GoodDealsActivity.class));
    }

    private void k() throws NullPointerException {
        int keyAt;
        if (this.mSmoneyOffersView.getVisibility() == 0) {
            this.c = this.mSmoneyOffersCheckbox.isChecked() ? "1" : "0";
        }
        if (this.mSmoneyPartnersOffersView.getVisibility() == 0) {
            this.d = this.mSmoneyPartnersOffersCheckbox.isChecked() ? "1" : "0";
        }
        SmoneyRequestManager j = j();
        String str = this.e.b.a;
        String str2 = this.e.b.c;
        String str3 = this.c;
        String str4 = this.d;
        int size = j.b.size();
        for (int i = 0; i < size; i++) {
            Intent intent = (Intent) j.b.valueAt(i);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 267 && intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUSessionId").equals(str2) && intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUServiceType").equals("1") && intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptin").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptinPartners").equals(str4)) {
                keyAt = j.b.keyAt(i);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 267);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptServicesCGUUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptServicesCGUSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptServicesCGUServiceType", "1");
        intent2.putExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptin", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.acceptServicesCGUOptinPartners", str4);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.bZ = null;
        super.a(keyAt, 267, false);
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            switch (i2) {
                case 267:
                    b(serverError);
                    return;
                default:
                    return;
            }
        }
    }

    public final void b_(int i) {
        switch (i) {
            case 267:
                b(this.e.bZ);
                return;
            default:
                return;
        }
    }

    public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
        switch (compoundButton.getId()) {
            case R.id.cgu_smoney_checkbox /*2131755233*/:
                this.mConfirmBtn.setEnabled(z);
                return;
            default:
                return;
        }
    }

    @OnClick({2131755235})
    public void onConfirmClick(View view) {
        if (this.mSmoneyCguCheckbox.isChecked()) {
            k();
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_cgu_smoney);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.b = getIntent().getBooleanExtra("isSmoneyCguExpired", false);
        this.mSmoneyCgu.setMovementMethod(LinkMovementMethod.getInstance());
        this.e = i();
        if (this.e != null) {
            this.f = this.e.b;
        }
        if (this.b) {
            this.mSubtitle.setText(getString(R.string.cgu_good_deals_subtitle_expired));
        }
        this.mSmoneyCguCheckbox.setOnCheckedChangeListener(this);
        this.mSmoneyOffersCheckbox.setOnCheckedChangeListener(this);
        this.mSmoneyPartnersOffersCheckbox.setOnCheckedChangeListener(this);
        if (this.f != null) {
            if (this.f.C) {
                this.mSmoneyOffersView.setVisibility(8);
            }
            if (this.f.D) {
                this.mSmoneyPartnersOffersView.setVisibility(8);
            }
        }
        jb.a(getApplicationContext(), R.string.screen_name_good_deals_cgu_activity);
    }

    protected void onNewIntent(Intent intent) {
        if (intent.getScheme().equalsIgnoreCase("terms")) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://mon-espace.izly.fr/Content/pdf/CGU_Particuliers-S-MONEY_2015.pdf")));
        } else {
            super.onNewIntent(intent);
        }
    }

    public void onPause() {
        unregisterReceiver(this.g);
        this.g = null;
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("fr.smoney.android.izly.notifications.NOTIFICATION_SESSION_STATE_CHANGE_INTENT_URI");
        if (this.g == null) {
            this.g = new a();
        }
        registerReceiver(this.g, intentFilter);
    }
}
