package fr.smoney.android.izly.ui.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;

public class ContactDetailsPartFragment$$ViewBinder<T extends ContactDetailsPartFragment> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        View view = (View) finder.findRequiredView(obj, R.id.b_p2p_get, "field 'mButtonP2pGet' and method 'onClick'");
        t.mButtonP2pGet = (Button) finder.castView(view, R.id.b_p2p_get, "field 'mButtonP2pGet'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ ContactDetailsPartFragment$$ViewBinder b;

            public final void doClick(View view) {
                t.onClick(view);
            }
        });
        view = (View) finder.findRequiredView(obj, R.id.b_p2p_pay, "field 'mButtonP2pPay' and method 'onClick'");
        t.mButtonP2pPay = (Button) finder.castView(view, R.id.b_p2p_pay, "field 'mButtonP2pPay'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ ContactDetailsPartFragment$$ViewBinder b;

            public final void doClick(View view) {
                t.onClick(view);
            }
        });
        t.mLastTransactionView = (LinearLayout) finder.castView((View) finder.findRequiredView(obj, R.id.ll_last_transactions, "field 'mLastTransactionView'"), R.id.ll_last_transactions, "field 'mLastTransactionView'");
        t.mImageViewAvatar = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.aiv_avatar, "field 'mImageViewAvatar'"), R.id.aiv_avatar, "field 'mImageViewAvatar'");
        t.mImageViewContactBlocked = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.img_contact_blocked, "field 'mImageViewContactBlocked'"), R.id.img_contact_blocked, "field 'mImageViewContactBlocked'");
        t.mLastTransactionViewLayout = (View) finder.findRequiredView(obj, R.id.rl_last_transactions_layout, "field 'mLastTransactionViewLayout'");
        ((View) finder.findRequiredView(obj, R.id.b_invite_sms, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ ContactDetailsPartFragment$$ViewBinder b;

            public final void doClick(View view) {
                t.onClick(view);
            }
        });
        ((View) finder.findRequiredView(obj, R.id.b_invite_email, "method 'onClick'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ ContactDetailsPartFragment$$ViewBinder b;

            public final void doClick(View view) {
                t.onClick(view);
            }
        });
    }

    public void unbind(T t) {
        t.mButtonP2pGet = null;
        t.mButtonP2pPay = null;
        t.mLastTransactionView = null;
        t.mImageViewAvatar = null;
        t.mImageViewContactBlocked = null;
        t.mLastTransactionViewLayout = null;
    }
}
