package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class P2PPayResultActivity$$ViewBinder<T extends P2PPayResultActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.titleInfo = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.title_info, "field 'titleInfo'"), R.id.title_info, "field 'titleInfo'");
        t.titleInfoResult = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.title_info_result, "field 'titleInfoResult'"), R.id.title_info_result, "field 'titleInfoResult'");
        t.mButtonNewPay = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mButtonNewPay'"), R.id.b_submit, "field 'mButtonNewPay'");
        t.mLayoutCommission = (View) finder.findOptionalView(obj, R.id.ll_commission, null);
        t.mTextViewCommission = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_commission, null), R.id.tv_commission, "field 'mTextViewCommission'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_amount, null), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mTextViewDate = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_date, null), R.id.tv_date, "field 'mTextViewDate'");
        t.mTextViewCommissionInfo = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_commission_info, null), R.id.tv_commission_info, "field 'mTextViewCommissionInfo'");
        t.mViewCommissionInfo = (View) finder.findOptionalView(obj, R.id.ll_commission_info, null);
        t.mMaskedAsyncImageViewRecipientPhoto = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.aiv_recipient_photo, null), R.id.aiv_recipient_photo, "field 'mMaskedAsyncImageViewRecipientPhoto'");
        t.mTextViewRecipientName = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_name, null), R.id.tv_recipient_name, "field 'mTextViewRecipientName'");
        t.mTextViewRecipientId = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_id, null), R.id.tv_recipient_id, "field 'mTextViewRecipientId'");
        t.messageViewRightMe = (View) finder.findOptionalView(obj, R.id.ll_message_me, null);
        t.mDateMessage = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_date_me, null), R.id.tv_message_date_me, "field 'mDateMessage'");
        t.mMessage = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_me, null), R.id.tv_message_me, "field 'mMessage'");
        t.mAvatarImgMe = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.img_message_me, null), R.id.img_message_me, "field 'mAvatarImgMe'");
    }

    public void unbind(T t) {
        t.titleInfo = null;
        t.titleInfoResult = null;
        t.mButtonNewPay = null;
        t.mLayoutCommission = null;
        t.mTextViewCommission = null;
        t.mTextViewAmount = null;
        t.mTextViewDate = null;
        t.mTextViewCommissionInfo = null;
        t.mViewCommissionInfo = null;
        t.mMaskedAsyncImageViewRecipientPhoto = null;
        t.mTextViewRecipientName = null;
        t.mTextViewRecipientId = null;
        t.messageViewRightMe = null;
        t.mDateMessage = null;
        t.mMessage = null;
        t.mAvatarImgMe = null;
    }
}
