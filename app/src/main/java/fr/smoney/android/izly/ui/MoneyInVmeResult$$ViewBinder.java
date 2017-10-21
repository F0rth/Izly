package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInVmeResult$$ViewBinder<T extends MoneyInVmeResult> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mPhoto = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.aiv_recipient_photo, "field 'mPhoto'"), R.id.aiv_recipient_photo, "field 'mPhoto'");
        t.mTransactionType = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_transaction_type, "field 'mTransactionType'"), R.id.tv_transaction_type, "field 'mTransactionType'");
        t.mRecipientType = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_type, "field 'mRecipientType'"), R.id.tv_recipient_type, "field 'mRecipientType'");
        t.mRecipientName = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_name, "field 'mRecipientName'"), R.id.tv_recipient_name, "field 'mRecipientName'");
        t.mAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.amount, "field 'mAmount'"), R.id.amount, "field 'mAmount'");
        t.mDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.date, "field 'mDate'"), R.id.date, "field 'mDate'");
        t.mOldBalance = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.v_me_result_old_balance, "field 'mOldBalance'"), R.id.v_me_result_old_balance, "field 'mOldBalance'");
        t.mNewBalance = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.v_me_result_new_balance, "field 'mNewBalance'"), R.id.v_me_result_new_balance, "field 'mNewBalance'");
        t.mStatus = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_request_status, "field 'mStatus'"), R.id.tv_request_status, "field 'mStatus'");
        ((View) finder.findRequiredView(obj, R.id.b_close, "method 'close'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ MoneyInVmeResult$$ViewBinder b;

            public final void doClick(View view) {
                t.close();
            }
        });
    }

    public void unbind(T t) {
        t.mPhoto = null;
        t.mTransactionType = null;
        t.mRecipientType = null;
        t.mRecipientName = null;
        t.mAmount = null;
        t.mDate = null;
        t.mOldBalance = null;
        t.mNewBalance = null;
        t.mStatus = null;
    }
}
