package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class MoneyInCbAndPayConfirmActivity$$ViewBinder<T extends MoneyInCbAndPayConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.imgMessageMe = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.img_message_me, "field 'imgMessageMe'"), R.id.img_message_me, "field 'imgMessageMe'");
        t.messageDateMe = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_message_date_me, "field 'messageDateMe'"), R.id.tv_message_date_me, "field 'messageDateMe'");
        t.messageMe = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_message_me, "field 'messageMe'"), R.id.tv_message_me, "field 'messageMe'");
        t.mTextViewCommission = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_commission, "field 'mTextViewCommission'"), R.id.tv_commission, "field 'mTextViewCommission'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_amount, "field 'mTextViewAmount'"), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mDate = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_date, "field 'mDate'"), R.id.tv_date, "field 'mDate'");
        t.mButtonConfirm = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_confirm, "field 'mButtonConfirm'"), R.id.b_confirm, "field 'mButtonConfirm'");
        t.recipientPhoto = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.aiv_recipient_photo, "field 'recipientPhoto'"), R.id.aiv_recipient_photo, "field 'recipientPhoto'");
        t.recipientName = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_name, "field 'recipientName'"), R.id.tv_recipient_name, "field 'recipientName'");
        t.recipientId = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_id, "field 'recipientId'"), R.id.tv_recipient_id, "field 'recipientId'");
        t.recipientInfo = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_recipient_info, "field 'recipientInfo'"), R.id.tv_recipient_info, "field 'recipientInfo'");
        t.messageRightMe = (View) finder.findRequiredView(obj, R.id.ll_message_me, "field 'messageRightMe'");
        t.mAlias = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_alias, "field 'mAlias'"), R.id.tv_money_operation_alias, "field 'mAlias'");
        t.mHint = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_operation_hint, "field 'mHint'"), R.id.tv_money_operation_hint, "field 'mHint'");
        t.moneyInCommision = (DetailTwoText) finder.castView((View) finder.findRequiredView(obj, R.id.tv_money_in_cb_commission, "field 'moneyInCommision'"), R.id.tv_money_in_cb_commission, "field 'moneyInCommision'");
        t.messageOther = (View) finder.findRequiredView(obj, R.id.ll_message_other, "field 'messageOther'");
    }

    public void unbind(T t) {
        t.imgMessageMe = null;
        t.messageDateMe = null;
        t.messageMe = null;
        t.mTextViewCommission = null;
        t.mTextViewAmount = null;
        t.mDate = null;
        t.mButtonConfirm = null;
        t.recipientPhoto = null;
        t.recipientName = null;
        t.recipientId = null;
        t.recipientInfo = null;
        t.messageRightMe = null;
        t.mAlias = null;
        t.mHint = null;
        t.moneyInCommision = null;
        t.messageOther = null;
    }
}
