package fr.smoney.android.izly.adapter;

import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class CityEventAdapter$ViewHolder$$ViewBinder<T extends ViewHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mPartnerView = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.partner_view, null), R.id.partner_view, "field 'mPartnerView'");
        t.mPlaceText = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.place_text, null), R.id.place_text, "field 'mPlaceText'");
        t.mPoweredByWINGiTView = (RelativeLayout) finder.castView((View) finder.findOptionalView(obj, R.id.powered_by_wingit_layout, null), R.id.powered_by_wingit_layout, "field 'mPoweredByWINGiTView'");
        t.mBookTicketButton = (AppCompatButton) finder.castView((View) finder.findOptionalView(obj, R.id.book_ticket, null), R.id.book_ticket, "field 'mBookTicketButton'");
        t.mContentImage = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.content_image, "field 'mContentImage'"), R.id.content_image, "field 'mContentImage'");
        t.mContentView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.content_text, "field 'mContentView'"), R.id.content_text, "field 'mContentView'");
        t.mSubTextView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.sub_text, "field 'mSubTextView'"), R.id.sub_text, "field 'mSubTextView'");
    }

    public void unbind(T t) {
        t.mPartnerView = null;
        t.mPlaceText = null;
        t.mPoweredByWINGiTView = null;
        t.mBookTicketButton = null;
        t.mContentImage = null;
        t.mContentView = null;
        t.mSubTextView = null;
    }
}
