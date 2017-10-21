package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.ht;
import defpackage.hv;
import defpackage.hz;
import defpackage.ia;
import defpackage.ie;
import defpackage.is;
import defpackage.iw;
import defpackage.jb;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMultConfirmData;
import fr.smoney.android.izly.data.model.P2PGetMultData;
import fr.smoney.android.izly.data.model.SendAttachmentData;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.io.File;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.spongycastle.crypto.tls.CipherSuite;

public class P2PGetSimpleConfirmActivity extends SmoneyABSActivity implements OnClickListener, SmoneyRequestManager$a {
    private P2PGetMultData b;
    private P2PGetMultConfirmData c;
    private String d;
    private String e;
    private String f;
    private String g;
    private ge h;
    @Nullable
    @Bind({2131755367})
    ImageView mAvatarMessageAsyncImageView;
    @Nullable
    @Bind({2131755382})
    ImageView mAvatarRecipientAsyncImageView;
    @Nullable
    @Bind({2131755368})
    ImageView mButtonAttachment;
    @Nullable
    @Bind({2131755192})
    Button mButtonConfirm;
    @Nullable
    @Bind({2131755363})
    View mLayoutMessage;
    @Nullable
    @Bind({2131755391})
    DetailTwoText mTextViewAmount;
    @Nullable
    @Bind({2131755390})
    DetailTwoText mTextViewDate;
    @Nullable
    @Bind({2131755366})
    TextView mTextViewMessage;
    @Nullable
    @Bind({2131755365})
    TextView mTextViewMessageDate;
    @Nullable
    @Bind({2131755384})
    TextView mTextViewRecipientInfo;
    @Nullable
    @Bind({2131755385})
    TextView mTextViewRecipientName;
    @Nullable
    @Bind({2131755386})
    TextView mTextViewRecipientPhoneNumber;
    @Nullable
    @Bind({2131755786})
    View mTextViewWithoutAmount;
    @Bind({2131755726})
    TextView titleInfo;

    private void a(P2PGetMultConfirmData p2PGetMultConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PGetMultConfirmData == null) {
            a_(67);
        } else {
            this.c = p2PGetMultConfirmData;
            if (k()) {
                a(String.valueOf(p2PGetMultConfirmData.c));
            } else {
                m();
            }
        }
    }

    private void a(SendAttachmentData sendAttachmentData, ServerError serverError) {
        a(ie.ProgressUploadAttachmentType);
        if (serverError != null || sendAttachmentData == null) {
            l();
        } else {
            m();
        }
    }

    private void a(String str) {
        cl i = i();
        a(ia.a(getString(R.string.progress_dialog_title), getString(R.string.p2P_get_mult_dialog_error_attachment_progress), this, ie.ProgressUploadAttachmentType));
        super.a(j().b(i.b.a, i.b.c, str, this.d, this.e), (int) CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA, false);
    }

    private boolean k() {
        return this.d != null;
    }

    private void l() {
        a(ht.a(getString(R.string.p2P_get_mult_dialog_error_attachment_title), getString(R.string.p2P_get_mult_dialog_error_attachment_message), getString(R.string.p2P_get_mult_dialog_error_attachment_button_retry), getString(17039360), this, ie.UploadAtachmentErrorType));
    }

    private void m() {
        Intent a = is.a(this, P2PGetSimpleResultActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmData", this.c);
        if (k()) {
            a.putExtra("fr.smoney.android.izly.extras.attachmentName", this.d);
            a.putExtra("fr.smoney.android.izly.extras.attachmentPath", this.e);
        }
        startActivity(a);
    }

    private void n() {
        String str;
        cl i = i();
        SmoneyRequestManager j = j();
        String str2 = i.b.a;
        String str3 = i.b.c;
        String str4 = "";
        if (this.b.b.size() > 0) {
            P2PGet p2PGet = (P2PGet) this.b.b.get(0);
            if (p2PGet.q == 0) {
                str4 = "" + p2PGet.c;
                if (p2PGet.h > 0.0d) {
                    str = (str4 + ":") + p2PGet.h;
                    super.a(j.a(str2, str3, str, this.b.f, this.g), 67, true);
                }
            }
        }
        str = str4;
        super.a(j.a(str2, str3, str, this.b.f, this.g), 67, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (ieVar) {
            case ConnexionErrorType:
                int h = h();
                if (h == 67) {
                    n();
                    return;
                } else if (h == CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA) {
                    a(String.valueOf(this.c.c));
                    return;
                } else {
                    super.a(ieVar, bundle);
                    return;
                }
            case UploadAtachmentErrorType:
                a(String.valueOf(this.c.c));
                return;
            case InputPasswordType:
                this.g = bundle.getString("Data.Password");
                n();
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 67) {
                a((P2PGetMultConfirmData) bundle.getParcelable("fr.smoney.android.izly.extras.p2pGetMultConfirmData"), serverError);
            } else if (i2 == CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA) {
                a((SendAttachmentData) bundle.getParcelable("fr.smoney.android.izly.extras.sendAttachementData"), serverError);
            }
        }
    }

    public final void b(ie ieVar) {
        switch (ieVar) {
            case UploadAtachmentErrorType:
                this.d = null;
                this.e = null;
                m();
                return;
            default:
                super.b(ieVar);
                return;
        }
    }

    public final void b_(int i) {
        cl i2 = i();
        if (i == 67) {
            a(i2.O, i2.P);
        } else if (i == CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA) {
            a(i2.ay, i2.az);
        }
    }

    public final void c(ie ieVar) {
        switch (ieVar) {
            case InvalidRecipientType:
                finish();
                return;
            default:
                super.c(ieVar);
                return;
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == CipherSuite.TLS_PSK_WITH_AES_256_CBC_SHA) {
            g();
            l();
        } else if (ieVar != ie.InputPasswordType) {
            super.d(ieVar);
        }
    }

    public void onClick(View view) {
        if (view == this.mButtonConfirm) {
            a(hz.a(this, this));
        } else if (view == this.mButtonAttachment) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(this.e)), "image/*");
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle bundle) {
        P2PGet p2PGet;
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        this.mButtonAttachment.setOnClickListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.h = new ge(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = (P2PGetMultData) extras.getParcelable("fr.smoney.android.izly.extras.p2pGetMultData");
            this.d = extras.getString("fr.smoney.android.izly.extras.attachmentName");
            this.e = extras.getString("fr.smoney.android.izly.extras.attachmentPath");
        }
        if (bundle != null) {
            this.g = bundle.getString("savedStateCurrentPassword");
        }
        this.f = Currency.getInstance(i().b.j).getSymbol();
        String str = "";
        cl i = i();
        this.mTextViewDate.setRightText(jk.b(this, new Date().getTime()));
        this.mTextViewRecipientInfo.setText(R.string.p2p_get_simple_confirm_recipient);
        int i2 = 0;
        boolean z = false;
        int i3 = 0;
        while (i2 < this.b.b.size()) {
            String str2;
            int i4;
            boolean z2;
            p2PGet = (P2PGet) this.b.b.get(i2);
            if (p2PGet.q != 0) {
                i3 = p2PGet.q;
                str2 = p2PGet.r;
                i4 = i3;
                z2 = true;
            } else {
                i4 = i3;
                z2 = z;
                str2 = str;
            }
            i2++;
            str = str2;
            z = z2;
            i3 = i4;
        }
        if (z) {
            if (i3 == 149) {
                a(hv.a(getString(R.string.p2p_get_mult_confirm_dialog_error_invalid_recipients_title), str, getString(17039370), this, ie.InvalidRecipientType));
            } else {
                a(hv.a(getString(R.string.p2p_get_mult_confirm_dialog_error_invalid_recipients_title), getString(R.string.p2p_get_mult_confirm_dialog_error_invalid_recipients_message), getString(17039370), this, ie.InvalidRecipientType));
            }
        }
        if (this.b.c > 0.0d) {
            DetailTwoText detailTwoText = this.mTextViewAmount;
            double d = this.b.c;
            detailTwoText.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.f}));
        } else {
            this.mTextViewAmount.setVisibility(8);
        }
        if (this.b.f != null && !this.b.f.equals("")) {
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mTextViewMessage.setText(this.b.f);
            this.h.a.displayImage(jl.a(i.b.a), this.mAvatarMessageAsyncImageView);
            this.mLayoutMessage.setVisibility(0);
        } else if (this.e == null || this.e.length() <= 0) {
            this.mLayoutMessage.setVisibility(8);
        } else {
            this.h.a.displayImage(jl.a(i.b.a), this.mAvatarMessageAsyncImageView);
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mLayoutMessage.setVisibility(0);
        }
        this.mButtonConfirm.setOnClickListener(this);
        if (k()) {
            this.mButtonAttachment.setVisibility(0);
            this.mButtonAttachment.setImageBitmap(iw.b(iw.a(new File(this.e), 60, 60)));
        }
        p2PGet = (P2PGet) this.b.b.get(0);
        if (p2PGet.d) {
            this.mTextViewRecipientName.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
            this.mTextViewRecipientPhoneNumber.setText(jf.a(p2PGet.c));
            this.h.a.displayImage(jl.a(p2PGet.c), this.mAvatarRecipientAsyncImageView);
            if (jf.a(p2PGet.f, p2PGet.g)) {
                this.mTextViewRecipientPhoneNumber.setVisibility(0);
            } else {
                this.mTextViewRecipientPhoneNumber.setVisibility(8);
            }
        } else {
            this.mTextViewRecipientName.setText((p2PGet.f != null ? p2PGet.f + " " : "") + (p2PGet.g != null ? p2PGet.g : ""));
            this.mTextViewRecipientPhoneNumber.setText(jf.a(p2PGet.c));
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_simple_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
        bundle.putString("savedStateCurrentPassword", this.g);
    }
}
