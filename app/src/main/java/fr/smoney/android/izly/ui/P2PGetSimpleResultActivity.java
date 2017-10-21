package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import defpackage.iw;
import defpackage.jb;
import defpackage.jf;
import defpackage.jk;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.P2PGet;
import fr.smoney.android.izly.data.model.P2PGetMultConfirmData;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

import java.io.File;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

public class P2PGetSimpleResultActivity extends SmoneyABSActivity implements OnClickListener {
    private P2PGetMultConfirmData b;
    private String c;
    private String d;
    private cl e;
    private String f;
    private ge g;
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
    View mLinearLayoutMessage;
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

    public void onClick(View view) {
        if (view == this.mButtonAttachment) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(this.d)), "image/*");
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = i();
        this.f = Currency.getInstance(this.e.b.j).getSymbol();
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        this.g = new ge(this);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.mButtonAttachment.setOnClickListener(this);
        this.mButtonConfirm.setVisibility(8);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = (P2PGetMultConfirmData) extras.getParcelable("fr.smoney.android.izly.extras.p2pGetMultConfirmData");
            this.c = extras.getString("fr.smoney.android.izly.extras.attachmentName");
            this.d = extras.getString("fr.smoney.android.izly.extras.attachmentPath");
        }
        cl i = i();
        this.titleInfo.setText(getString(R.string.p2p_get_mult_details_result_tv_request_done));
        this.mTextViewRecipientInfo.setText(R.string.p2p_get_simple_confirm_recipient);
        this.mTextViewDate.setRightText(jk.b(this, new Date().getTime()));
        if (this.b.e > 0.0d) {
            DetailTwoText detailTwoText = this.mTextViewAmount;
            double d = this.b.e;
            detailTwoText.setRightText(String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.f}));
        } else {
            this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
        }
        if (this.b.g != null && !this.b.g.equals("")) {
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mTextViewMessage.setText(this.b.g);
            this.g.a.displayImage(jl.a(this.e.b.a), this.mAvatarMessageAsyncImageView);
            this.mLinearLayoutMessage.setVisibility(0);
        } else if (this.d == null || this.d.length() <= 0) {
            this.mLinearLayoutMessage.setVisibility(8);
        } else {
            this.g.a.displayImage(jl.a(i.b.a), this.mAvatarMessageAsyncImageView);
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mLinearLayoutMessage.setVisibility(0);
        }
        if (this.c != null) {
            this.mButtonAttachment.setVisibility(0);
            this.mButtonAttachment.setImageBitmap(iw.b(iw.a(new File(this.d), 60, 60)));
        }
        P2PGet p2PGet = (P2PGet) this.b.b.get(0);
        if (p2PGet.d) {
            this.mTextViewRecipientName.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
            this.mTextViewRecipientPhoneNumber.setText(jf.a(p2PGet.c));
            this.g.a.displayImage(jl.a(p2PGet.c), this.mAvatarRecipientAsyncImageView);
            if (jf.a(p2PGet.f, p2PGet.g)) {
                this.mTextViewRecipientPhoneNumber.setVisibility(0);
            } else {
                this.mTextViewRecipientPhoneNumber.setVisibility(8);
            }
        } else {
            this.mTextViewRecipientName.setText((p2PGet.f != null ? p2PGet.f + " " : "") + (p2PGet.g != null ? p2PGet.g : ""));
            this.mTextViewRecipientPhoneNumber.setText(jf.a(p2PGet.c));
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_simple_result_activity);
    }

    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        switch (i) {
            case 4:
                a();
                return true;
            default:
                return super.onKeyUp(i, keyEvent);
        }
    }
}
