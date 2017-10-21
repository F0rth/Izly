package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
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

public class P2PGetMultResultActivity extends SmoneyABSActivity implements OnClickListener, OnItemClickListener {
    private P2PGetMultConfirmData b;
    private String c;
    private ge d;
    private String e;
    private String f;
    private cl g;
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
        final /* synthetic */ P2PGetMultResultActivity d;

        public a(P2PGetMultResultActivity p2PGetMultResultActivity, View view) {
            this.d = p2PGetMultResultActivity;
            this.a = (ImageView) view.findViewById(R.id.iv_recipient_item_avatar);
            this.b = (ImageView) view.findViewById(R.id.iv_recipient_status);
            this.c = (TextView) view.findViewById(R.id.tv_recipient_item_name);
        }
    }

    final class b extends BaseAdapter {
        public int a = -1;
        final /* synthetic */ P2PGetMultResultActivity b;
        private LayoutInflater c;

        public b(P2PGetMultResultActivity p2PGetMultResultActivity) {
            this.b = p2PGetMultResultActivity;
            this.c = p2PGetMultResultActivity.getLayoutInflater();
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
            aVar.d.d.a(R.drawable.icon_home_placeholder, aVar.a);
            if (p2PGet.d) {
                aVar.c.setText(jf.a(p2PGet.f, p2PGet.g, p2PGet.c));
                aVar.d.d.a(jl.a(p2PGet.c), aVar.a);
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
        return String.format(Locale.getDefault(), "%1$.2f%2$s", new Object[]{Double.valueOf(d), this.c});
    }

    public void onClick(View view) {
        if (view.getId() == R.id.b_attachment) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            intent.setDataAndType(Uri.fromFile(new File(this.f)), "image/*");
            startActivity(intent);
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.g = i();
        this.c = Currency.getInstance(this.g.b.j).getSymbol();
        setContentView((int) R.layout.p2p_confirm_result);
        ButterKnife.bind(this);
        this.mRecipientItemsViews.setVisibility(0);
        this.mViewRecipientInfo.setVisibility(8);
        this.mTextViewPerPeopleAmount.setVisibility(0);
        this.mButtonConfirm.setVisibility(8);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.d = new ge(this);
        this.mButtonAttachment.setOnClickListener(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            this.b = (P2PGetMultConfirmData) extras.getParcelable("fr.smoney.android.izly.extras.p2pGetMultConfirmData");
            this.e = extras.getString("fr.smoney.android.izly.extras.attachmentName");
            this.f = extras.getString("fr.smoney.android.izly.extras.attachmentPath");
        }
        this.mTextViewRecipientInfo.setText(R.string.p2p_get_mult_recipient);
        this.mTextViewPerPeopleAmount.setRightText(a(this.b.h));
        this.mRecipientItemsViews.setAdapter(new b(this));
        this.titleInfo.setText(getString(R.string.p2p_get_mult_details_result_tv_request_done));
        this.mTextViewDate.setRightText(jk.b(this, new Date().getTime()));
        if (this.b.e > 0.0d) {
            this.mTextViewAmount.setRightText(a(this.b.e));
        } else {
            this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
        }
        if (this.b.g == null || this.b.g.equals("")) {
            this.mLinearLayoutMessage.setVisibility(8);
        } else {
            this.mTextViewMessageDate.setText(jk.b(this, new Date().getTime()));
            this.mTextViewMessage.setText(this.b.g);
            this.d.a.displayImage(jl.a(this.g.b.a), this.mAvatarMessageAsyncImageView);
            this.mLinearLayoutMessage.setVisibility(0);
        }
        if (this.e != null) {
            this.mButtonAttachment.setVisibility(0);
        }
        jb.a(getApplicationContext(), R.string.screen_name_ask_money_collect_result_activity);
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        b bVar = (b) adapterView.getAdapter();
        if (bVar.a != i) {
            bVar.a = i;
            P2PGet p2PGet = (P2PGet) this.b.b.get(i);
            this.d.a.displayImage(jl.a(p2PGet.c), this.mRecipientAvater);
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
            this.mViewRecipientInfo.setVisibility(0);
            this.mTextViewPerPeopleAmount.setVisibility(8);
            if (p2PGet.h > 0.0d) {
                this.mTextViewAmount.setRightText(a(p2PGet.h));
            } else if (this.b.e > 0.0d) {
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
            if (this.b.e > 0.0d) {
                this.mTextViewAmount.setRightText(a(this.b.e));
            } else {
                this.mTextViewAmount.setRightText(getString(R.string.p2p_get_mult_tv_without_amount));
            }
        }
        bVar.notifyDataSetChanged();
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
