package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.devsmart.android.ui.HorizontalListView;

import defpackage.ht;
import defpackage.hv;
import defpackage.hz;
import defpackage.ia;
import defpackage.ie;
import defpackage.is;
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

import java.util.Currency;
import java.util.Date;
import java.util.Locale;

import org.spongycastle.crypto.tls.CipherSuite;

public class P2PGetMultConfirmActivity extends SmoneyABSActivity implements OnClickListener, OnItemClickListener, SmoneyRequestManager$a {
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
    @Bind({2131755368})
    ImageView mButtonAttachment;
    @Nullable
    @Bind({2131755192})
    Button mButtonConfirm;
    @Nullable
    @Bind({2131755363})
    View mLinearLayoutMessage;
    @Nullable
    @Bind({2131755382})
    ImageView mRecipientAvater;
    @Nullable
    @Bind({2131755728})
    HorizontalListView mRecipientItemsViews;
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
    @Bind({2131755729})
    DetailTwoText mTextViewPerPeopleAmount;
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
    @Nullable
    @Bind({2131755383})
    View mViewRecipientInfo;
    @Bind({2131755726})
    TextView titleInfo;

    final class a {
        ImageView a;
        ImageView b;
        TextView c;
        final /* synthetic */ P2PGetMultConfirmActivity d;

        public a(P2PGetMultConfirmActivity p2PGetMultConfirmActivity, View view) {
            this.d = p2PGetMultConfirmActivity;
            this.a = (ImageView) view.findViewById(R.id.iv_recipient_item_avatar);
            this.b = (ImageView) view.findViewById(R.id.iv_recipient_status);
            this.c = (TextView) view.findViewById(R.id.tv_recipient_item_name);
        }
    }

    final class b extends BaseAdapter {
        public int a = -1;
        final /* synthetic */ P2PGetMultConfirmActivity b;
        private LayoutInflater c;

        public b(P2PGetMultConfirmActivity p2PGetMultConfirmActivity) {
            this.b = p2PGetMultConfirmActivity;
            this.c = p2PGetMultConfirmActivity.getLayoutInflater();
        }

        public final int getCount() {
            return this.b.b.b.size();
        }

        public final Object getItem(int i) {
            return null;
        }

        public final long getItemId(int i) {
            return 0;
        }

        public final View getView(int i, View view, ViewGroup viewGroup) {
            a aVar;
            if (view == null) {
                view = this.c.inflate(R.layout.p2p_get_mult_confirm_recipient_item, null);
                a aVar2 = new a(this.b, view);
                view.setTag(aVar2);
                aVar = aVar2;
            } else {
                aVar = (a) view.getTag();
            }
            P2PGet p2PGet = (P2PGet) this.b.b.b.get(i);
            aVar.d.h.a(R.drawable.icon_home_placeholder, aVar.a);
            if (p2PGet.d) {
                aVar.c.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
                aVar.d.h.a(jl.a(p2PGet.c), aVar.a);
            } else {
                aVar.c.setText(jf.a(p2PGet.c));
            }
            if (p2PGet.q != 0) {
                aVar.b.setImageResource(R.drawable.p2p_get_mult_iv_reicpient_status_cancelled);
            } else {
                aVar.b.setImageDrawable(null);
            }
            if (this.a == i) {
                view.setSelected(true);
            } else {
                view.setSelected(false);
            }
            return view;
        }
    }

    private String a(double d) {
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.f});
    }

    private void a(P2PGetMultConfirmData p2PGetMultConfirmData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (p2PGetMultConfirmData == null) {
            a_(67);
        } else {
            this.c = p2PGetMultConfirmData;
            this.c.h = this.b.d;
            if (k()) {
                a(String.valueOf(p2PGetMultConfirmData.c));
            } else {
                l();
            }
        }
    }

    private void a(SendAttachmentData sendAttachmentData, ServerError serverError) {
        a(ie.ProgressUploadAttachmentType);
        if (serverError != null || sendAttachmentData == null) {
            m();
        } else {
            l();
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
        Intent a = is.a(this, P2PGetMultResultActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.p2pGetMultConfirmData", this.c);
        if (k()) {
            a.putExtra("fr.smoney.android.izly.extras.attachmentName", this.d);
            a.putExtra("fr.smoney.android.izly.extras.attachmentPath", this.e);
        }
        startActivity(a);
    }

    private void m() {
        a(ht.a(getString(R.string.p2P_get_mult_dialog_error_attachment_title), getString(R.string.p2P_get_mult_dialog_error_attachment_message), getString(R.string.p2P_get_mult_dialog_error_attachment_button_retry), getString(17039360), this, ie.UploadAtachmentErrorType));
    }

    private void n() {
        cl i = i();
        SmoneyRequestManager j = j();
        String str = i.b.a;
        String str2 = i.b.c;
        String str3 = "";
        int i2 = 0;
        while (i2 < this.b.b.size()) {
            String str4;
            P2PGet p2PGet = (P2PGet) this.b.b.get(i2);
            if (p2PGet.q == 0) {
                str3 = str3 + p2PGet.c;
                if (p2PGet.h > 0.0d) {
                    str4 = (str3 + ":") + p2PGet.h;
                } else {
                    str4 = str3;
                }
                str4 = str4 + ";";
            } else {
                str4 = str3;
            }
            i2++;
            str3 = str4;
        }
        super.a(j.a(str, str2, str3.substring(0, str3.length() - 1), this.b.f, this.g), 67, true);
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
                l();
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
            m();
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
            intent.setDataAndType(Uri.parse("file://" + this.e), "image/*");
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        this.mRecipientItemsViews.setVisibility(0);
        this.mViewRecipientInfo.setVisibility(8);
        this.mTextViewPerPeopleAmount.setVisibility(0);
        this.h = new ge(this);
        this.mButtonAttachment.setOnClickListener(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
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
        cl i = i();
        this.mTextViewRecipientInfo.setText(R.string.p2p_get_mult_recipient);
        this.mRecipientItemsViews.setAdapter(new b(this));
        this.mRecipientItemsViews.setOnItemClickListener(this);
        this.mTextViewDate.setRightText(jk.b(this, new Date().getTime()));
        boolean z = false;
        int i2 = 0;
        while (i2 < this.b.b.size()) {
            boolean z2 = ((P2PGet) this.b.b.get(i2)).q == 0 ? true : z;
            i2++;
            z = z2;
        }
        if (!z) {
            a(hv.a(getString(R.string.p2p_get_mult_confirm_dialog_error_invalid_recipients_title), getString(R.string.p2p_get_mult_confirm_dialog_error_invalid_recipients_message), getString(17039370), this, ie.InvalidRecipientType));
        }
        if (this.b.c > 0.0d) {
            this.mTextViewAmount.setRightText(a(this.b.c));
            this.mTextViewPerPeopleAmount.setRightText(a(this.b.d));
        } else {
            this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
            this.mTextViewPerPeopleAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
        }
        if (this.b.f == null || this.b.f.equals("")) {
            this.mLinearLayoutMessage.setVisibility(8);
        } else {
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mTextViewMessage.setText(this.b.f);
            this.h.a.displayImage(jl.a(i.b.a), this.mAvatarMessageAsyncImageView);
            this.mLinearLayoutMessage.setVisibility(0);
        }
        this.mButtonConfirm.setOnClickListener(this);
        if (k()) {
            this.mButtonAttachment.setVisibility(0);
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_collect_confirm_activity);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        b bVar = (b) adapterView.getAdapter();
        if (bVar.a != i) {
            bVar.a = i;
            P2PGet p2PGet = (P2PGet) this.b.b.get(i);
            this.h.a.displayImage(jl.a(p2PGet.c), this.mRecipientAvater);
            if (p2PGet.d) {
                this.mTextViewRecipientName.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
                this.mTextViewRecipientPhoneNumber.setText(jf.a(p2PGet.c));
                if (jf.a(p2PGet.f, p2PGet.g)) {
                    this.mTextViewRecipientPhoneNumber.setVisibility(0);
                } else {
                    this.mTextViewRecipientPhoneNumber.setVisibility(8);
                }
            } else {
                this.mTextViewRecipientName.setText(jf.a(p2PGet.c));
                this.mTextViewRecipientPhoneNumber.setVisibility(4);
            }
            if (p2PGet.h > 0.0d) {
                this.mTextViewAmount.setRightText(a(p2PGet.h));
            } else if (this.b.c > 0.0d) {
                this.mTextViewAmount.setRightText("");
            } else {
                this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
            }
            this.mViewRecipientInfo.setVisibility(0);
            this.mTextViewPerPeopleAmount.setVisibility(8);
        } else {
            bVar.a = -1;
            this.mViewRecipientInfo.setVisibility(8);
            this.mTextViewPerPeopleAmount.setVisibility(0);
            if (this.b.c > 0.0d) {
                this.mTextViewAmount.setRightText(a(this.b.c));
            } else {
                this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
            }
        }
        bVar.notifyDataSetChanged();
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
