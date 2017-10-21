package fr.smoney.android.izly.ui.activity;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import butterknife.internal.DebouncingOnClickListener;

import com.google.android.gms.maps.MapView;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.view.ExpandableTextView;

public class EventDetailActivity$$ViewBinder<T extends EventDetailActivity> implements ViewBinder<T> {
    public void bind(Finder finder, final T t, Object obj) {
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        t.mScrollView = (NestedScrollView) finder.castView((View) finder.findRequiredView(obj, R.id.scroll_view, "field 'mScrollView'"), R.id.scroll_view, "field 'mScrollView'");
        t.mContentImage = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.content_image, "field 'mContentImage'"), R.id.content_image, "field 'mContentImage'");
        t.mEventTitle = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.event_title, "field 'mEventTitle'"), R.id.event_title, "field 'mEventTitle'");
        t.mEventDescription = (ExpandableTextView) finder.castView((View) finder.findRequiredView(obj, R.id.event_description, "field 'mEventDescription'"), R.id.event_description, "field 'mEventDescription'");
        View view = (View) finder.findRequiredView(obj, R.id.event_description_expand_text, "field 'mEventDescriptionExpandText' and method 'onExpandTextClick'");
        t.mEventDescriptionExpandText = (AppCompatTextView) finder.castView(view, R.id.event_description_expand_text, "field 'mEventDescriptionExpandText'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ EventDetailActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onExpandTextClick();
            }
        });
        t.mVenueName = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.venue_name, "field 'mVenueName'"), R.id.venue_name, "field 'mVenueName'");
        t.mVenueAddress = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.venue_address, "field 'mVenueAddress'"), R.id.venue_address, "field 'mVenueAddress'");
        t.mEventDate = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.event_date, "field 'mEventDate'"), R.id.event_date, "field 'mEventDate'");
        view = (View) finder.findRequiredView(obj, R.id.book_ticket, "field 'mBookTicketButton' and method 'onBookTicketClick'");
        t.mBookTicketButton = (AppCompatButton) finder.castView(view, R.id.book_ticket, "field 'mBookTicketButton'");
        view.setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ EventDetailActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onBookTicketClick();
            }
        });
        t.mPartnerView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.partner_view, "field 'mPartnerView'"), R.id.partner_view, "field 'mPartnerView'");
        t.mTicketLayout = (View) finder.findRequiredView(obj, R.id.ticket_layout, "field 'mTicketLayout'");
        t.mTicketPriceView = (AppCompatTextView) finder.castView((View) finder.findRequiredView(obj, R.id.ticket_price, "field 'mTicketPriceView'"), R.id.ticket_price, "field 'mTicketPriceView'");
        t.mMapView = (MapView) finder.castView((View) finder.findRequiredView(obj, R.id.location_map, "field 'mMapView'"), R.id.location_map, "field 'mMapView'");
        ((View) finder.findRequiredView(obj, R.id.powered_by_wingit_layout, "method 'onPowereByWINGiTClick'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ EventDetailActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onPowereByWINGiTClick();
            }
        });
        ((View) finder.findRequiredView(obj, R.id.directions_button, "method 'onDirectionsClick'")).setOnClickListener(new DebouncingOnClickListener(this) {
            final /* synthetic */ EventDetailActivity$$ViewBinder b;

            public final void doClick(View view) {
                t.onDirectionsClick();
            }
        });
    }

    public void unbind(T t) {
        t.mToolbar = null;
        t.mScrollView = null;
        t.mContentImage = null;
        t.mEventTitle = null;
        t.mEventDescription = null;
        t.mEventDescriptionExpandText = null;
        t.mVenueName = null;
        t.mVenueAddress = null;
        t.mEventDate = null;
        t.mBookTicketButton = null;
        t.mPartnerView = null;
        t.mTicketLayout = null;
        t.mTicketPriceView = null;
        t.mMapView = null;
    }
}
