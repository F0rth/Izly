package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class P2PPayConfirmActivity$$ViewBinder<T extends P2PPayConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mLayoutCommission = (View) finder.findOptionalView(obj, R.id.ll_commission, null);
        t.mTextViewCommission = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_commission, null), R.id.tv_commission, "field 'mTextViewCommission'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_amount, null), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mTextViewDate = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_date, null), R.id.tv_date, "field 'mTextViewDate'");
        t.mButtonConfirm = (Button) finder.castView((View) finder.findRequiredView(obj, R.id.b_submit, "field 'mButtonConfirm'"), R.id.b_submit, "field 'mButtonConfirm'");
        t.mTextViewCommissionInfo = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_commission_info, null), R.id.tv_commission_info, "field 'mTextViewCommissionInfo'");
        t.mViewCommissionInfo = (View) finder.findOptionalView(obj, R.id.ll_commission_info, null);
        t.mRecipientPhoto = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.aiv_recipient_photo, null), R.id.aiv_recipient_photo, "field 'mRecipientPhoto'");
        t.mRecipientName = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_name, null), R.id.tv_recipient_name, "field 'mRecipientName'");
        t.mRecipientId = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_id, null), R.id.tv_recipient_id, "field 'mRecipientId'");
        t.messageViewRightMe = (View) finder.findOptionalView(obj, R.id.ll_message_me, null);
        t.mDateMessage = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_date_me, null), R.id.tv_message_date_me, "field 'mDateMessage'");
        t.mMessage = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_me, null), R.id.tv_message_me, "field 'mMessage'");
        t.mAvatarImgMe = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.img_message_me, null), R.id.img_message_me, "field 'mAvatarImgMe'");
    }

    public void unbind(T t) {
        t.mLayoutCommission = null;
        t.mTextViewCommission = null;
        t.mTextViewAmount = null;
        t.mTextViewDate = null;
        t.mButtonConfirm = null;
        t.mTextViewCommissionInfo = null;
        t.mViewCommissionInfo = null;
        t.mRecipientPhoto = null;
        t.mRecipientName = null;
        t.mRecipientId = null;
        t.messageViewRightMe = null;
        t.mDateMessage = null;
        t.mMessage = null;
        t.mAvatarImgMe = null;
    }
}
