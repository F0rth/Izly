package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInCbAndPayResultActivity$$ViewBinder<T extends MoneyInCbAndPayResultActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.statusHeader = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.header, "field 'statusHeader'"), R.id.header, "field 'statusHeader'");
        t.imgMessageMe = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.img_message_me, "field 'imgMessageMe'"), R.id.img_message_me, "field 'imgMessageMe'");
        t.messageDateMe = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_message_date_me, "field 'messageDateMe'"), R.id.tv_message_date_me, "field 'messageDateMe'");
        t.messageMe = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_message_me, "field 'messageMe'"), R.id.tv_message_me, "field 'messageMe'");
        t.mTextViewCommission = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_commission, "field 'mTextViewCommission'"), R.id.tv_commission, "field 'mTextViewCommission'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_date, "field 'mDate'"), R.id.tv_date, "field 'mDate'");
        t.mButtonNewPay = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_confirm, "field 'mButtonNewPay'"), R.id.b_confirm, "field 'mButtonNewPay'");
        t.mAlias = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_alias, "field 'mAlias'"), R.id.tv_money_operation_alias, "field 'mAlias'");
        t.mHint = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_hint, "field 'mHint'"), R.id.tv_money_operation_hint, "field 'mHint'");
        t.recipientPhoto = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.aiv_recipient_photo, "field 'recipientPhoto'"), R.id.aiv_recipient_photo, "field 'recipientPhoto'");
        t.moneyInCommision = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_in_cb_commission, "field 'moneyInCommision'"), R.id.tv_money_in_cb_commission, "field 'moneyInCommision'");
        t.recipientName = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_name, "field 'recipientName'"), R.id.tv_recipient_name, "field 'recipientName'");
        t.recipientId = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_id, "field 'recipientId'"), R.id.tv_recipient_id, "field 'recipientId'");
        t.messageRightMe = (View) finder.findRequiredView(obj, R.id.ll_message_me, "field 'messageRightMe'");
        t.messageOther = (View) finder.findRequiredView(obj, R.id.ll_message_other, "field 'messageOther'");
        t.balance = (View) finder.findRequiredView(obj, R.id.account_balance_view, "field 'balance'");
    }

    public void unbind(T t) {
        t.statusHeader = null;
        t.imgMessageMe = null;
        t.messageDateMe = null;
        t.messageMe = null;
        t.mTextViewCommission = null;
        t.mTextViewAmount = null;
        t.mDate = null;
        t.mButtonNewPay = null;
        t.mAlias = null;
        t.mHint = null;
        t.recipientPhoto = null;
        t.moneyInCommision = null;
        t.recipientName = null;
        t.recipientId = null;
        t.messageRightMe = null;
        t.messageOther = null;
        t.balance = null;
    }
}
