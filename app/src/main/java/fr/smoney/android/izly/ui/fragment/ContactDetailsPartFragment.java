package fr.smoney.android.izly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.nineoldandroids.view.animation.AnimatorProxy;

import defpackage.ha;
import defpackage.jf;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.ui.ContactDetailsActivity.c;
import fr.smoney.android.izly.ui.P2PGetSimpleActivity;
import fr.smoney.android.izly.ui.P2PPayActivity;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class ContactDetailsPartFragment extends ha implements c {
    protected GetContactDetailsData e;
    private View f;
    private ge g;
    private String h;
    @Bind({2131755405})
    Button mButtonP2pGet;
    @Bind({2131755404})
    Button mButtonP2pPay;
    @Bind({2131755392})
    ImageView mImageViewAvatar;
    @Bind({2131755393})
    ImageView mImageViewContactBlocked;
    @Bind({2131755407})
    LinearLayout mLastTransactionView;
    @Bind({2131755406})
    View mLastTransactionViewLayout;

    public static ContactDetailsPartFragment a(GetContactDetailsData getContactDetailsData) {
        ContactDetailsPartFragment contactDetailsPartFragment = new ContactDetailsPartFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("getContactDetailsData", getContactDetailsData);
        contactDetailsPartFragment.setArguments(bundle);
        return contactDetailsPartFragment;
    }

    private void n() {
        if (this.e.j) {
            AnimatorProxy.wrap(this.mImageViewAvatar).setAlpha(125.0f);
            this.mImageViewContactBlocked.setVisibility(0);
            if (this.mButtonP2pGet != null) {
                this.mButtonP2pGet.setEnabled(false);
            }
            this.mButtonP2pPay.setEnabled(false);
            return;
        }
        AnimatorProxy.wrap(this.mImageViewAvatar).setAlpha(255.0f);
        this.mImageViewContactBlocked.setVisibility(8);
        if (this.mButtonP2pGet != null) {
            this.mButtonP2pGet.setEnabled(true);
        }
        this.mButtonP2pPay.setEnabled(true);
    }

    public final void a(boolean z) {
        this.e.j = z;
        n();
    }

    public void onActivityCreated(Bundle bundle) {
        int i;
        int i2 = 1;
        super.onActivityCreated(bundle);
        if (this.e.g) {
            this.g.a.displayImage(jl.a(this.e.b), this.mImageViewAvatar);
        }
        ((TextView) this.f.findViewById(R.id.tv_name)).setText(jf.a(this.e.x, this.e.y, this.e.b));
        if (TextUtils.isEmpty(this.e.w)) {
            this.f.findViewById(R.id.tv_email).setVisibility(8);
        } else {
            this.h = this.e.w;
            ((DetailTwoText) this.f.findViewById(R.id.tv_email)).setRightText(this.e.w);
        }
        if (TextUtils.isEmpty(this.e.e)) {
            this.f.findViewById(R.id.tv_phone).setVisibility(8);
            i = 1;
        } else {
            this.h = this.e.e;
            ((TextView) this.f.findViewById(R.id.tv_phone)).setText(jf.a(this.e.e));
            i = 0;
        }
        if (TextUtils.isEmpty(this.e.d)) {
            this.f.findViewById(R.id.tv_pseudo).setVisibility(8);
            if (i == 0) {
                i2 = 0;
            }
            i = i2;
        } else {
            ((DetailTwoText) this.f.findViewById(R.id.tv_pseudo)).setRightText(this.e.d);
        }
        if (i != 0) {
            this.f.findViewById(R.id.ll_contact_part_infos).setBackgroundColor(0);
        }
        if (TextUtils.isEmpty(this.e.d)) {
            this.f.findViewById(R.id.tv_active_alias).setVisibility(8);
        } else {
            ((TextView) this.f.findViewById(R.id.tv_active_alias)).setText(this.e.d);
        }
        if (this.e.p) {
            this.f.findViewById(R.id.ll_buttons_invite).setVisibility(8);
        } else {
            this.f.findViewById(R.id.ll_buttons_invite).setVisibility(0);
        }
        a(this.e, this.mLastTransactionViewLayout, this.mLastTransactionView);
        n();
    }

    @OnClick({2131755401, 2131755402, 2131755404, 2131755405})
    public void onClick(View view) {
        Intent intent;
        if (view == this.mButtonP2pPay) {
            intent = new Intent(this.d, P2PPayActivity.class);
            intent.putExtra("defaultRecipientId", this.h);
            startActivity(intent);
        } else if (view == this.mButtonP2pGet) {
            intent = new Intent(this.d, P2PGetSimpleActivity.class);
            intent.putExtra("defaultRecipientId", this.h);
            startActivity(intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.e = (GetContactDetailsData) getArguments().getParcelable("getContactDetailsData");
        this.g = new ge(this.d);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.f = layoutInflater.inflate(R.layout.contact_details_part, viewGroup, false);
        ButterKnife.bind(this, this.f);
        return this.f;
    }
}
