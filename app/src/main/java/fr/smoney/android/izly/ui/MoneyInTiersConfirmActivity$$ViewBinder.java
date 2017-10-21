package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInTiersConfirmActivity$$ViewBinder<T extends MoneyInTiersConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mPhoto = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.aiv_recipient_photo, "field 'mPhoto'"), R.id.aiv_recipient_photo, "field 'mPhoto'");
        t.mTransactionType = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_transaction_type, "field 'mTransactionType'"), R.id.tv_transaction_type, "field 'mTransactionType'");
        t.mRecipientName = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_name, "field 'mRecipientName'"), R.id.tv_recipient_name, "field 'mRecipientName'");
        t.mRecipientType = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_type, "field 'mRecipientType'"), R.id.tv_recipient_type, "field 'mRecipientType'");
        t.mMessage = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.message, "field 'mMessage'"), R.id.message, "field 'mMessage'");
        t.mAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.amount, "field 'mAmount'"), R.id.amount, "field 'mAmount'");
        t.mDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.date, "field 'mDate'"), R.id.date, "field 'mDate'");
        t.mRecipient = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.recipient, "field 'mRecipient'"), R.id.recipient, "field 'mRecipient'");
        ((View) finder.findRequiredView(obj, R.id.b_confirm, "method 'confirm'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ MoneyInTiersConfirmActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.confirm();
            }
        });
    }

    public void unbind(T t) {
        t.mPhoto = null;
        t.mTransactionType = null;
        t.mRecipientName = null;
        t.mRecipientType = null;
        t.mMessage = null;
        t.mAmount = null;
        t.mDate = null;
        t.mRecipient = null;
    }
}
