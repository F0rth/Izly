package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

import com.devsmart.android.ui.HorizontalListView;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.widget.DetailTwoText;

public class P2PGetMultConfirmActivity$$ViewBinder<T extends P2PGetMultConfirmActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.titleInfo = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.title_info, "field 'titleInfo'"), R.id.title_info, "field 'titleInfo'");
        t.mTextViewAmount = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_amount, null), R.id.tv_amount, "field 'mTextViewAmount'");
        t.mTextViewDate = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_date, null), R.id.tv_date, "field 'mTextViewDate'");
        t.mLinearLayoutMessage = (View) finder.findOptionalView(obj, R.id.ll_message_me, null);
        t.mAvatarMessageAsyncImageView = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.img_message_me, null), R.id.img_message_me, "field 'mAvatarMessageAsyncImageView'");
        t.mRecipientAvater = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.aiv_recipient_photo, null), R.id.aiv_recipient_photo, "field 'mRecipientAvater'");
        t.mTextViewMessageDate = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_date_me, null), R.id.tv_message_date_me, "field 'mTextViewMessageDate'");
        t.mTextViewMessage = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_message_me, null), R.id.tv_message_me, "field 'mTextViewMessage'");
        t.mButtonAttachment = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.iv_attachment_inline, null), R.id.iv_attachment_inline, "field 'mButtonAttachment'");
        t.mTextViewRecipientName = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_name, null), R.id.tv_recipient_name, "field 'mTextViewRecipientName'");
        t.mTextViewRecipientPhoneNumber = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_id, null), R.id.tv_recipient_id, "field 'mTextViewRecipientPhoneNumber'");
        t.mButtonConfirm = (Button) finder.castView((View) finder.findOptionalView(obj, R.id.b_submit, null), R.id.b_submit, "field 'mButtonConfirm'");
        t.mTextViewRecipientInfo = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.tv_recipient_info, null), R.id.tv_recipient_info, "field 'mTextViewRecipientInfo'");
        t.mTextViewWithoutAmount = (View) finder.findOptionalView(obj, R.id.tv_without_amount, null);
        t.mRecipientItemsViews = (HorizontalListView) finder.castView((View) finder.findOptionalView(obj, R.id.riv_recipients, null), R.id.riv_recipients, "field 'mRecipientItemsViews'");
        t.mTextViewPerPeopleAmount = (DetailTwoText) finder.castView((View) finder.findOptionalView(obj, R.id.tv_per_people, null), R.id.tv_per_people, "field 'mTextViewPerPeopleAmount'");
        t.mViewRecipientInfo = (View) finder.findOptionalView(obj, R.id.ll_recipient_infos, null);
    }

    public void unbind(T t) {
        t.titleInfo = null;
        t.mTextViewAmount = null;
        t.mTextViewDate = null;
        t.mLinearLayoutMessage = null;
        t.mAvatarMessageAsyncImageView = null;
        t.mRecipientAvater = null;
        t.mTextViewMessageDate = null;
        t.mTextViewMessage = null;
        t.mButtonAttachment = null;
        t.mTextViewRecipientName = null;
        t.mTextViewRecipientPhoneNumber = null;
        t.mButtonConfirm = null;
        t.mTextViewRecipientInfo = null;
        t.mTextViewWithoutAmount = null;
        t.mRecipientItemsViews = null;
        t.mTextViewPerPeopleAmount = null;
        t.mViewRecipientInfo = null;
    }
}
