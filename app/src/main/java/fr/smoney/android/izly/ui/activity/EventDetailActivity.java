package fr.smoney.android.izly.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.model.WGDownloadPage;
import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;

import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.activity.WebViewActivity.a;
import fr.smoney.android.izly.ui.view.ExpandableTextView;
import fr.smoney.android.izly.ui.view.ExpandableTextView.b;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.spongycastle.asn1.cmp.PKIFailureInfo;

public class EventDetailActivity extends AppCompatActivity {
    private static final String a = EventDetailActivity.class.getName();
    private static String c;
    private static String d;
    private final int b = 3;
    private WGEvent e;
    private GoogleMap f;
    @Bind({2131755260})
    AppCompatButton mBookTicketButton;
    @Bind({2131755242})
    ImageView mContentImage;
    @Bind({2131755249})
    TextView mEventDate;
    @Bind({2131755246})
    ExpandableTextView mEventDescription;
    @Bind({2131755247})
    AppCompatTextView mEventDescriptionExpandText;
    @Bind({2131755245})
    TextView mEventTitle;
    @Bind({2131755253})
    MapView mMapView;
    @Bind({2131755243})
    TextView mPartnerView;
    @Bind({2131755239})
    NestedScrollView mScrollView;
    @Bind({2131755258})
    View mTicketLayout;
    @Bind({2131755259})
    AppCompatTextView mTicketPriceView;
    @Bind({2131755238})
    Toolbar mToolbar;
    @Bind({2131755256})
    TextView mVenueAddress;
    @Bind({2131755255})
    TextView mVenueName;

    public static Intent a(Context context, WGEvent wGEvent) {
        Intent intent = new Intent(context, EventDetailActivity.class);
        intent.putExtra("ARG_EVENT", wGEvent);
        return intent;
    }

    static /* synthetic */ void b(EventDetailActivity eventDetailActivity) {
        final LatLng latLng = new LatLng(eventDetailActivity.e.getLat().doubleValue(), eventDetailActivity.e.getLon().doubleValue());
        if (eventDetailActivity.f != null) {
            eventDetailActivity.f.clear();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_map_purple_icon));
            eventDetailActivity.f.addMarker(markerOptions).showInfoWindow();
            eventDetailActivity.f.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
            if (eventDetailActivity.mMapView.getViewTreeObserver().isAlive()) {
                eventDetailActivity.mMapView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(eventDetailActivity) {
                    final /* synthetic */ EventDetailActivity b;

                    public final void onGlobalLayout() {
                        this.b.mMapView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        this.b.f.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15.0f));
                        Projection projection = this.b.f.getProjection();
                        CameraUpdate newLatLngZoom = CameraUpdateFactory.newLatLngZoom(projection.fromScreenLocation(projection.toScreenLocation(latLng)), 15.0f);
                        this.b.f.moveCamera(newLatLngZoom);
                        this.b.f.animateCamera(newLatLngZoom);
                    }
                });
            }
        }
    }

    @OnClick({2131755260})
    public void onBookTicketClick() {
        if (this.e != null && this.e.getBookingURL() != null) {
            WINGiTManager.trackTicket(this.e, null);
            startActivity(WebViewActivity.a(this, this.e.getBookingURL(), a.BOOK_TICKET));
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        c = System.getProperty("line.separator") + getString(R.string.wingit_event_details_label_more).toUpperCase();
        d = System.getProperty("line.separator") + getString(R.string.wingit_event_details_label_less).toUpperCase();
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("ARG_EVENT")) {
            this.e = (WGEvent) intent.getSerializableExtra("ARG_EVENT");
            if (this.e != null) {
                CharSequence concat;
                if (this.e.getHorizontalImage() != null) {
                    Picasso.with(this).load(this.e.getHorizontalImage()).error(this.e.getDefaultPlaceholder()).into(this.mContentImage);
                }
                if (this.e.getEventDescription() == null || this.e.getEventDescription().isEmpty()) {
                    this.mEventTitle.setVisibility(8);
                    if (this.e.getTitle() == null || this.e.getTitle().isEmpty()) {
                        this.mEventDescription.setVisibility(8);
                        this.mEventDescriptionExpandText.setVisibility(8);
                    } else if (TextUtils.isEmpty(this.e.getSocialUrl())) {
                        this.mEventDescription.setLinkifiedText(this.e.getTitle(), null);
                    } else {
                        this.mEventDescription.setLinkifiedText(this.e.getTitle(), this.e.getSocialUrl());
                    }
                } else {
                    if (TextUtils.isEmpty(this.e.getSocialUrl())) {
                        this.mEventDescription.setLinkifiedText(this.e.getEventDescription(), null);
                    } else {
                        this.mEventDescription.setLinkifiedText(this.e.getEventDescription(), this.e.getSocialUrl());
                    }
                    if (this.e.getTitle() == null || this.e.getTitle().isEmpty()) {
                        this.mEventTitle.setVisibility(8);
                    } else {
                        this.mEventTitle.setText(this.e.getTitle());
                    }
                }
                this.mEventDescriptionExpandText.setText(c);
                this.mEventDescription.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
                    final /* synthetic */ EventDetailActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onGlobalLayout() {
                        this.a.mEventDescriptionExpandText.setVisibility(this.a.mEventDescription.getLineCount() > 3 ? 0 : 8);
                    }
                });
                this.mEventDescriptionExpandText.setPaintFlags(8);
                this.mEventDescription.setOnExpandListener(new b(this) {
                    final /* synthetic */ EventDetailActivity a;

                    {
                        this.a = r1;
                    }

                    public final void a() {
                        this.a.mEventDescriptionExpandText.setText(EventDetailActivity.d);
                    }

                    public final void b() {
                        this.a.mEventDescriptionExpandText.setText(EventDetailActivity.c);
                        this.a.mScrollView.smoothScrollTo(0, 0);
                    }
                });
                this.mPartnerView.setVisibility(this.e.isPromoted().booleanValue() ? 0 : 8);
                if (this.e.getPlace().getName() == null || this.e.getPlace().getName().isEmpty()) {
                    this.mVenueName.setVisibility(8);
                } else {
                    this.mVenueName.setText(this.e.getPlace().getName());
                }
                if (this.e.getPlace().getAddress() == null || this.e.getPlace().getAddress().isEmpty()) {
                    this.mVenueAddress.setVisibility(8);
                } else {
                    this.mVenueAddress.setText(this.e.getPlace().getAddress());
                }
                TextView textView = this.mEventDate;
                WGEvent wGEvent = this.e;
                SimpleDateFormat simpleDateFormat = DateFormat.is24HourFormat(this) ? new SimpleDateFormat("HH:mm", Locale.getDefault()) : new SimpleDateFormat("hh:mma", Locale.getDefault());
                String str = DateUtils.formatDateTime(this, wGEvent.getEventDate().getTime(), 20) + ", " + simpleDateFormat.format(wGEvent.getEventDate());
                if (wGEvent.getEventEndDate() != null) {
                    concat = str.concat(" - " + simpleDateFormat.format(wGEvent.getEventEndDate()));
                } else {
                    Object obj = str;
                }
                textView.setText(concat);
                if (this.e.isTicketCompliant()) {
                    if (TextUtils.isEmpty(this.e.getPriceMin())) {
                        this.mTicketPriceView.setText(R.string.wingit_event_details_label_tickets_for_sale);
                    } else {
                        this.mBookTicketButton.setVisibility(0);
                        if (this.e.getPriceMin().contains("free") || this.e.getPriceMin().contains("gratuit")) {
                            this.mTicketPriceView.setText(this.e.getPriceMin());
                        } else {
                            this.mTicketPriceView.setText(getString(R.string.wingit_event_details_label_tickets_from, new Object[]{this.e.getPriceMin()}));
                        }
                    }
                    this.mTicketLayout.setVisibility(0);
                } else {
                    this.mTicketLayout.setVisibility(8);
                    this.mBookTicketButton.setVisibility(8);
                }
                this.mMapView.onCreate(null);
                this.mMapView.setClickable(false);
                this.mMapView.getMapAsync(new OnMapReadyCallback(this) {
                    final /* synthetic */ EventDetailActivity a;

                    {
                        this.a = r1;
                    }

                    public final void onMapReady(GoogleMap googleMap) {
                        if (googleMap != null) {
                            this.a.f = googleMap;
                            this.a.f.getUiSettings().setMapToolbarEnabled(false);
                            this.a.f.getUiSettings().setAllGesturesEnabled(false);
                            EventDetailActivity.b(this.a);
                        }
                    }
                });
            }
        }
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (this.e != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(R.string.wingit_event_details_label_navbar_title);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_detail, menu);
        return true;
    }

    @OnClick({2131755257})
    public void onDirectionsClick() {
        if (this.e != null && this.e.getLat() != null && this.e.getLon() != null) {
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("geo:" + this.e.getLat() + "," + this.e.getLon() + "?q=" + (!TextUtils.isEmpty(this.e.getPlace().getName()) ? Uri.encode(this.e.getPlace().getName()) : Uri.encode(this.e.getPlace().getAddress()))));
            intent.setPackage("com.google.android.apps.maps");
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }

    @OnClick({2131755247})
    public void onExpandTextClick() {
        ExpandableTextView expandableTextView = this.mEventDescription;
        int measuredHeight;
        ValueAnimator ofInt;
        if (expandableTextView.g) {
            if (expandableTextView.g && !expandableTextView.f && expandableTextView.d >= 0) {
                expandableTextView.f = true;
                if (expandableTextView.a != null) {
                    expandableTextView.a.b();
                }
                measuredHeight = expandableTextView.getMeasuredHeight();
                if (VERSION.SDK_INT >= 11) {
                    ofInt = ValueAnimator.ofInt(new int[]{measuredHeight, expandableTextView.h});
                    ofInt.addUpdateListener(new AnimatorUpdateListener(expandableTextView) {
                        final /* synthetic */ ExpandableTextView a;

                        {
                            this.a = r1;
                        }

                        @SuppressLint({"NewApi"})
                        public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                            LayoutParams layoutParams = this.a.getLayoutParams();
                            layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                            this.a.setLayoutParams(layoutParams);
                        }
                    });
                    ofInt.addListener(new AnimatorListenerAdapter(expandableTextView) {
                        final /* synthetic */ ExpandableTextView a;

                        {
                            this.a = r1;
                        }

                        public final void onAnimationEnd(Animator animator) {
                            this.a.setMaxLines(this.a.d);
                            LayoutParams layoutParams = this.a.getLayoutParams();
                            layoutParams.height = -2;
                            this.a.setLayoutParams(layoutParams);
                            this.a.g = false;
                            this.a.f = false;
                        }
                    });
                    ofInt.setInterpolator(expandableTextView.c);
                    ofInt.setDuration(expandableTextView.e).start();
                    return;
                }
                expandableTextView.setMaxLines(expandableTextView.d);
                LayoutParams layoutParams = expandableTextView.getLayoutParams();
                layoutParams.height = -2;
                expandableTextView.setLayoutParams(layoutParams);
                expandableTextView.g = false;
                expandableTextView.f = false;
            }
        } else if (!expandableTextView.g && !expandableTextView.f && expandableTextView.d >= 0) {
            expandableTextView.f = true;
            if (expandableTextView.a != null) {
                expandableTextView.a.a();
            }
            expandableTextView.measure(MeasureSpec.makeMeasureSpec(expandableTextView.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            expandableTextView.h = expandableTextView.getMeasuredHeight();
            expandableTextView.setMaxLines(Integer.MAX_VALUE);
            expandableTextView.measure(MeasureSpec.makeMeasureSpec(expandableTextView.getMeasuredWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            measuredHeight = expandableTextView.getMeasuredHeight();
            if (VERSION.SDK_INT >= 11) {
                ofInt = ValueAnimator.ofInt(new int[]{expandableTextView.h, measuredHeight});
                ofInt.addUpdateListener(new AnimatorUpdateListener(expandableTextView) {
                    final /* synthetic */ ExpandableTextView a;

                    {
                        this.a = r1;
                    }

                    @SuppressLint({"NewApi"})
                    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                        LayoutParams layoutParams = this.a.getLayoutParams();
                        layoutParams.height = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                        this.a.setLayoutParams(layoutParams);
                    }
                });
                ofInt.addListener(new AnimatorListenerAdapter(expandableTextView) {
                    final /* synthetic */ ExpandableTextView a;

                    {
                        this.a = r1;
                    }

                    public final void onAnimationEnd(Animator animator) {
                        LayoutParams layoutParams = this.a.getLayoutParams();
                        layoutParams.height = -2;
                        this.a.setLayoutParams(layoutParams);
                        this.a.g = true;
                        this.a.f = false;
                    }
                });
                ofInt.setInterpolator(expandableTextView.b);
                ofInt.setDuration(expandableTextView.e).start();
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.share_content_item /*2131755956*/:
                if (this.e != null) {
                    String string = getString(R.string.wingit_event_details_message_event_share);
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.SEND");
                    intent.putExtra("android.intent.extra.TEXT", string);
                    intent.setType("text/plain");
                    List arrayList = new ArrayList();
                    List<ResolveInfo> queryIntentActivities = getPackageManager().queryIntentActivities(intent, PKIFailureInfo.notAuthorized);
                    if (queryIntentActivities.isEmpty()) {
                        Toast.makeText(this, getString(R.string.wingit_event_details_message_event_share_error), 0).show();
                    } else {
                        for (ResolveInfo resolveInfo : queryIntentActivities) {
                            String str = resolveInfo.activityInfo.packageName;
                            if (!str.equals(getPackageName())) {
                                Object obj;
                                if (str.equalsIgnoreCase("com.twitter.android")) {
                                    obj = new Intent("android.intent.action.VIEW").setAction("android.intent.action.VIEW").setData(Uri.parse(String.format("http://www.twitter.com/intent/tweet?url=%s&text=%s", new Object[]{this.e.getPermalink(), string}))).setPackage(str);
                                } else {
                                    obj = new Intent("android.intent.action.VIEW").setAction("android.intent.action.SEND").putExtra("android.intent.extra.TEXT", String.format("%s\n%s", new Object[]{string, this.e.getPermalink()})).setType("text/plain").setPackage(str);
                                }
                                arrayList.add(obj);
                            }
                        }
                        Intent createChooser = Intent.createChooser((Intent) arrayList.remove(arrayList.size() - 1), getString(R.string.wingit_event_details_message_event_share_title));
                        createChooser.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[]) arrayList.toArray(new Parcelable[0]));
                        startActivity(createChooser);
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    @OnClick({2131755250})
    public void onPowereByWINGiTClick() {
        WGDownloadPage downloadPage = WINGiTDataHolder.getDownloadPage();
        if (downloadPage != null) {
            startActivity(DownloadActivity.a(this, downloadPage));
            return;
        }
        WINGiTManager.trackDownload(null);
        Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage("com.soin.wingit");
        if (launchIntentForPackage == null) {
            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://ad.apps.fm/cMXOCZ9zfaRXy9Pn2-0jPa5px440Px0vtrw1ww5B54x5gZUTumLI1FUnsgyArC7JU353vZsvaeGM5rVyPwk1vQ")));
        } else {
            startActivity(launchIntentForPackage);
        }
    }

    protected void onStart() {
        super.onStart();
        WINGiTManager.trackEvent(this.e, null);
    }
}
