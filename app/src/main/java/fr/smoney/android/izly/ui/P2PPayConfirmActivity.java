package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.hz;
import defpackage.ie;
import defpackage.is;
import defpackage.jb;
import defpackage.jf;
import defpackage.jh;
import defpackage.jj;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PPayCommerceInfos;
import fr.smoney.android.izly.data.model.P2PPayConfirmData;
import fr.smoney.android.izly.data.model.P2PPayData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.util.Currency;
import java.util.Locale;

public class P2PPayConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private String b;
    private P2PPayData c;
    private P2PPayCommerceInfos d;
    private ge e;
    @Nullable
    @Bind({2131755367})
    ImageView mAvatarImgMe;
    @Bind({2131755192})
    Button mButtonConfirm;
    @Nullable
    @Bind({2131755365})
    TextView mDateMessage;
    @Nullable
    @Bind({2131755659})
    View mLayoutCommission;
    @Nullable
    @Bind({2131755366})
    TextView mMessage;
    @Nullable
    @Bind({2131755386})
    TextView mRecipientId;
    @Nullable
    @Bind({2131755385})
    TextView mRecipientName;
    @Nullable
    @Bind({2131755382})
    ImageView mRecipientPhoto;
    @Nullable
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Nullable
    @Bind({2131755495})
    DetailTwoText mTextViewCommission;
    @Nullable
    @Bind({2131755579})
    TextView mTextViewCommissionInfo;
    @Nullable
    @Bind({2131755390})
    DetailTwoText mTextViewDate;
    @Nullable
    @Bind({2131755578})
    View mViewCommissionInfo;
    @Nullable
    @Bind({2131755363})
    View messageViewRightMe;

    private void a(P2PPayConfirmData p2PPayConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PPayConfirmData == null) {
            a_(12);
        } else {
            i().b.B = p2PPayConfirmData.b;
            Intent a = is.a(this, P2PPayResultActivity.class);
            a.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmData", p2PPayConfirmData);
            a.putExtra("startFromWebPayement", this.c.g);
            if (this.d != null) {
                a.putExtra("startFromWebPayementObject", this.d);
            }
            startActivity(a);
        }
    }

    private void k() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = this.c.a.c;
        String str2 = this.c.a.d;
        double d = this.c.a.j;
        String str3 = this.c.a.m;
        String str4 = i.b.c;
        String str5 = this.b;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 12 && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmRecipient").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.p2pPayConfirmAmount", -1.0d) == d && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmMessage").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmSessionId").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.p2pPayConfirmPassword").equals(str5)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 12);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmRecipient", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmMessage", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmSessionId", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.p2pPayConfirmPassword", str5);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.h = null;
        j.f.i = null;
        super.a(keyAt, 12, true);
    }

    private void l() {
        int keyAt;
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i().b.a;
        String str2 = this.c.a.e;
        double d = this.c.a.j;
        String str3 = this.c.a.m;
        Parcelable parcelable = this.d;
        String str4 = i.b.c;
        String str5 = this.b;
        int size = j.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 235 && intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceRecipient").equals(str2) && intent.getDoubleExtra("fr.smoney.android.izly.extras.makeEcommerceAmount", -1.0d) == d && intent.getParcelableExtra("fr.smoney.android.izly.extras.makeEcommercePayInfos") == parcelable && intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceMessage").equals(str3) && intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommerceSessionId").equals(str4) && intent.getStringExtra("fr.smoney.android.izly.extras.makeEcommercePassword").equals(str5)) {
                keyAt = j.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 235);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommerceUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommerceRecipient", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommerceAmount", d);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommercePayInfos", parcelable);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommerceMessage", str3);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommerceSessionId", str4);
        intent2.putExtra("fr.smoney.android.izly.extras.makeEcommercePassword", str5);
        j.c.startService(intent2);
        j.b.append(keyAt, intent2);
        j.f.h = null;
        j.f.i = null;
        super.a(keyAt, 235, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                if (h() == 12) {
                    k();
                    return;
                } else if (h() == 235) {
                    l();
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case InputPasswordType:
                this.b = bundle.getString("Data.Password");
                if (this.c.g) {
                    l();
                    return;
                } else {
                    k();
                    return;
                }
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 12) {
                a((P2PPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData"), serverError);
            } else if (i2 == 235) {
                a((P2PPayConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pPayConfirmData"), serverError);
            }
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 12) {
            a(i2.h, i2.i);
        } else if (i == 235) {
            a(i2.h, i2.i);
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case ErrorForNotMustBeRetryRequest:
                if (this.c.g) {
                    jj.a(this, this.d, false);
                    return;
                } else {
                    super.c(ieVar);
                    return;
                }
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar != ie.InputPasswordType) {
            super.d(ieVar);
        }
    }

    public void onClick(View view) {
        if (view == this.mButtonConfirm) {
            a(hz.a(this, this));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.e = new ge(this);
        this.mButtonConfirm.setOnClickListener(this);
        if (bundle != null) {
            this.b = bundle.getString("savedStateCurrentPassword");
        }
        Intent intent = getIntent();
        this.d = (P2PPayCommerceInfos) intent.getParcelableExtra("startFromWebPayementObject");
        if (intent != null) {
            if (intent.getBooleanExtra("fr.smoney.android.izly.extras.LaunchForExit", false)) {
                jj.a(this, this.d, true);
            } else {
                this.c = (P2PPayData) intent.getParcelableExtra("fr.smoney.android.izly.extras.p2pPayData");
                cl i = i();
                String symbol = Currency.getInstance(i.b.j).getSymbol();
                if (this.c.a.f) {
                    if (!(i == null || i.b == null)) {
                        this.e.a.displayImage(jl.a(this.c.a.d), this.mRecipientPhoto);
                    }
                    this.mRecipientName.setText(jf.a(this.c.a.g, this.c.a.h, this.c.a.d));
                    if (jf.a(this.c.a.g, this.c.a.h)) {
                        this.mRecipientId.setText(jf.a(this.c.a.d));
                    } else {
                        this.mRecipientId.setVisibility(8);
                    }
                } else {
                    this.mRecipientPhoto.setImageResource(R.drawable.icon_home_placeholder);
                    this.mRecipientName.setText((this.c.a.g != null ? this.c.a.g + " " : "") + (this.c.a.h != null ? this.c.a.h : ""));
                    this.mRecipientId.setText(jf.a(this.c.a.d));
                }
                this.mTextViewAmount.setRightText(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.j), symbol}));
                this.mTextViewDate.setRightText(jk.b(this, this.c.a.b));
                if (this.c.a.m == null || TextUtils.isEmpty(this.c.a.m)) {
                    this.messageViewRightMe.setVisibility(8);
                } else {
                    this.messageViewRightMe.setVisibility(0);
                    this.e.a.displayImage(jl.a(i.b.a), this.mAvatarImgMe);
                    this.mDateMessage.setText(jk.a(this, this.c.a.b, true));
                    this.mMessage.setText(this.c.a.m);
                    this.mMessage.invalidate();
                }
                if (this.c.a.k > 0.0d) {
                    this.mLayoutCommission.setVisibility(0);
                    this.mViewCommissionInfo.setVisibility(0);
                    this.mTextViewCommissionInfo.setText(jh.a(this, this.c.a.k, this.c.a.l, true));
                    this.mTextViewCommission.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(this.c.a.k), symbol}));
                }
            }
        }
        jb.a(getApplicationContext(), R.string.screen_name_send_money_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                if (this.c.g && !this.d.a()) {
                    jj.a(this, this.d, false);
                    break;
                }
                return super.onKeyUp(i, keyEvent);
        }
        return super.onKeyUp(i, keyEvent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                finish();
                break;
        }
        return true;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("savedStateCurrentPassword", this.b);
    }
}
