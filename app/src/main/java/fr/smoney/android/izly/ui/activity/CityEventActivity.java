package fr.smoney.android.izly.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.AdapterDataObserver;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.thewingitapp.thirdparties.wingitlib.WINGiTDataHolder;
import com.thewingitapp.thirdparties.wingitlib.exception.WGErrorTimeout;
import com.thewingitapp.thirdparties.wingitlib.exception.WGException;
import com.thewingitapp.thirdparties.wingitlib.model.WGCity;
import com.thewingitapp.thirdparties.wingitlib.model.WGDownloadPage;
import com.thewingitapp.thirdparties.wingitlib.model.WGEvent;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineEvents;
import com.thewingitapp.thirdparties.wingitlib.model.WGTimelineParameters;
import com.thewingitapp.thirdparties.wingitlib.network.api.WINGiTManager;

import defpackage.iz;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.adapter.CityEventAdapter;
import fr.smoney.android.izly.ui.activity.WebViewActivity.a;
import rx.Subscriber;

public class CityEventActivity extends AppCompatActivity implements OnRefreshListener {
    private CityEventAdapter a;
    private OnScrollListener b;
    private LinearLayoutManager c;
    private WGTimelineEvents d;
    private WGCity e;
    private boolean f = false;
    private WGTimelineParameters g;
    private View h;
    private AdapterDataObserver i = new AdapterDataObserver(this) {
        final /* synthetic */ CityEventActivity a;

        {
            this.a = r1;
        }

        public final void onChanged() {
            super.onChanged();
            CityEventActivity.d(this.a);
        }

        public final void onItemRangeChanged(int i, int i2) {
            super.onItemRangeChanged(i, i2);
            CityEventActivity.d(this.a);
        }

        public final void onItemRangeInserted(int i, int i2) {
            super.onItemRangeInserted(i, i2);
            CityEventActivity.d(this.a);
        }

        public final void onItemRangeMoved(int i, int i2, int i3) {
            super.onItemRangeMoved(i, i2, i3);
            CityEventActivity.d(this.a);
        }

        public final void onItemRangeRemoved(int i, int i2) {
            super.onItemRangeRemoved(i, i2);
            CityEventActivity.d(this.a);
        }
    };
    @Bind({2131755943})
    TextView mEmptyTextView;
    @Bind({2131755262})
    View mEmptyView;
    @Bind({2131755265})
    View mLoader;
    @Bind({2131755264})
    RecyclerView mRecyclerView;
    @Bind({2131755944})
    AppCompatButton mRefineFiltersButton;
    @Bind({2131755263})
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Bind({2131755238})
    Toolbar mToolbar;

    public static Intent a(Context context, WGCity wGCity) {
        Intent intent = new Intent(context, CityEventActivity.class);
        intent.putExtra("ARG_CITY", wGCity);
        return intent;
    }

    private void a() {
        if (this.e != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle(this.e.getLocalizedName());
            if (this.h == null) {
                this.h = LayoutInflater.from(this).inflate(R.layout.city_event_toolbar, null);
            }
            this.mToolbar.removeView(this.h);
            this.mToolbar.addView(this.h);
            return;
        }
        this.mToolbar.removeView(this.h);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    static /* synthetic */ void a(CityEventActivity cityEventActivity) {
        if (cityEventActivity.d != null) {
            int findLastVisibleItemPosition = cityEventActivity.c.findLastVisibleItemPosition();
            int childCount = cityEventActivity.c.getChildCount();
            int itemCount = cityEventActivity.c.getItemCount();
            if ((itemCount - findLastVisibleItemPosition <= 5 || (itemCount - findLastVisibleItemPosition == 0 && itemCount > childCount)) && !cityEventActivity.f && cityEventActivity.d.getEvents().size() < cityEventActivity.d.getTotalNumber().intValue()) {
                cityEventActivity.a.a(true);
                cityEventActivity.b();
            }
        }
    }

    private void b() {
        if (!this.f) {
            this.f = true;
            invalidateOptionsMenu();
            if (!(this.mSwipeRefreshLayout.isRefreshing() || this.a.c)) {
                this.mLoader.setVisibility(0);
                if (this.mLoader.getBackground() instanceof AnimationDrawable) {
                    ((AnimationDrawable) this.mLoader.getBackground()).start();
                }
                this.mEmptyView.setVisibility(8);
                this.mSwipeRefreshLayout.setVisibility(8);
            }
            WINGiTManager.getEventsForCity(this.e, this.g, null, Integer.valueOf(this.d != null ? this.d.getEvents().size() : 0), new Subscriber<WGTimelineEvents>(this) {
                final /* synthetic */ CityEventActivity a;

                {
                    this.a = r1;
                }

                public final void onCompleted() {
                    this.a.f = false;
                    this.a.invalidateOptionsMenu();
                    this.a.a.a(false);
                }

                public final void onError(Throwable th) {
                    CharSequence string = th instanceof WGException ? th instanceof WGErrorTimeout ? this.a.getString(R.string.wingit_errors_message_network_not_connected) : th.getMessage() : this.a.getString(R.string.wingit_errors_message_network_error);
                    Toast.makeText(this.a, string, 0).show();
                    this.a.f = false;
                    this.a.invalidateOptionsMenu();
                    this.a.a.a(false);
                }

                public final /* synthetic */ void onNext(Object obj) {
                    WGTimelineEvents wGTimelineEvents = (WGTimelineEvents) obj;
                    Object events = wGTimelineEvents.getEvents();
                    if (this.a.d == null || this.a.mSwipeRefreshLayout.isRefreshing()) {
                        this.a.d = wGTimelineEvents;
                        this.a.a.a();
                        this.a.mRecyclerView.setAdapter(this.a.a);
                    } else {
                        this.a.d.getEvents().addAll(events);
                    }
                    this.a.a.a(events);
                }
            });
        }
    }

    static /* synthetic */ void d(CityEventActivity cityEventActivity) {
        if (cityEventActivity.mEmptyView != null && cityEventActivity.mRecyclerView.getAdapter() != null) {
            if (cityEventActivity.mRecyclerView.getAdapter().getItemCount() == 0) {
                if (cityEventActivity.g.categories.size() == 0) {
                    cityEventActivity.mRefineFiltersButton.setVisibility(8);
                    cityEventActivity.mEmptyTextView.setText(R.string.wingit_timeline_label_no_results_for_default_filters);
                } else {
                    cityEventActivity.mRefineFiltersButton.setVisibility(0);
                    cityEventActivity.mEmptyTextView.setText(R.string.wingit_timeline_label_no_results);
                }
                cityEventActivity.mLoader.setVisibility(8);
                cityEventActivity.mSwipeRefreshLayout.setVisibility(8);
                if (cityEventActivity.mEmptyView.getVisibility() != 0) {
                    cityEventActivity.mEmptyView.setVisibility(0);
                }
            } else {
                cityEventActivity.mLoader.setVisibility(8);
                cityEventActivity.mEmptyView.setVisibility(8);
                if (cityEventActivity.mSwipeRefreshLayout.getVisibility() != 0) {
                    cityEventActivity.mSwipeRefreshLayout.setVisibility(0);
                }
            }
            cityEventActivity.mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        switch (i) {
            case 2462:
                if (i2 == -1 && intent != null && intent.hasExtra("ARG_TIMELINE_PARAMETERS")) {
                    this.g = (WGTimelineParameters) intent.getSerializableExtra("ARG_TIMELINE_PARAMETERS");
                    this.a.a();
                    onRefresh();
                    return;
                }
                return;
            case 2463:
                if (i2 == -1 && intent != null && intent.hasExtra("INTENT_CITY")) {
                    this.e = (WGCity) intent.getSerializableExtra("INTENT_CITY");
                    if (iz.a != null) {
                        this.g = new WGTimelineParameters(iz.a);
                    } else {
                        this.g = new WGTimelineParameters();
                    }
                    a();
                    onRefresh();
                    return;
                }
                return;
            default:
                super.onActivityResult(i, i2, intent);
                return;
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_event_city);
        ButterKnife.bind(this);
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.mToolbar.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CityEventActivity a;

            {
                this.a = r1;
            }

            public final void onClick(View view) {
                this.a.startActivityForResult(CitySelectActivity.a(this.a, true), 2463);
            }
        });
        if (getIntent() != null && getIntent().hasExtra("ARG_CITY")) {
            this.e = (WGCity) getIntent().getSerializableExtra("ARG_CITY");
            a();
        }
        if (iz.a != null) {
            this.g = new WGTimelineParameters(iz.a);
        } else {
            this.g = new WGTimelineParameters();
        }
        this.a = new CityEventAdapter();
        this.c = new LinearLayoutManager(this);
        if (this.mRecyclerView.getAdapter() != null) {
            this.mRecyclerView.getAdapter().unregisterAdapterDataObserver(this.i);
        }
        this.a.registerAdapterDataObserver(this.i);
        this.b = new OnScrollListener(this) {
            final /* synthetic */ CityEventActivity a;

            {
                this.a = r1;
            }

            public final void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
            }

            public final void onScrolled(RecyclerView recyclerView, int i, int i2) {
                super.onScrolled(recyclerView, i, i2);
                CityEventActivity.a(this.a);
            }
        };
        this.mRecyclerView.addOnScrollListener(this.b);
        this.mSwipeRefreshLayout.setColorSchemeResources(new int[]{R.color.wingit_primary_color});
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
        this.mRecyclerView.setLayoutManager(this.c);
        this.mRecyclerView.setAdapter(this.a);
        this.a.a(new a(this) {
            final /* synthetic */ CityEventActivity a;

            {
                this.a = r1;
            }

            public final void a() {
                WGDownloadPage downloadPage = WINGiTDataHolder.getDownloadPage();
                if (downloadPage != null) {
                    this.a.startActivity(DownloadActivity.a(this.a, downloadPage));
                }
            }

            public final void a(WGEvent wGEvent) {
                this.a.startActivity(EventDetailActivity.a(this.a, wGEvent));
            }

            public final void b(WGEvent wGEvent) {
                if (wGEvent.getBookingURL() != null) {
                    WINGiTManager.trackTicket(wGEvent, null);
                    this.a.startActivity(WebViewActivity.a(this.a, wGEvent.getBookingURL(), a.BOOK_TICKET));
                }
            }
        });
        if (bundle == null) {
            b();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_event_city, menu);
        return true;
    }

    @OnClick({2131755944})
    public void onFiltersClick() {
        if (this.d != null) {
            startActivityForResult(FilterActivity.a(this, this.g, this.d.getDetectedCity()), 2462);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        super.onOptionsItemSelected(menuItem);
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            case R.id.refresh_menu_item /*2131755954*/:
                menuItem.setEnabled(false);
                onRefresh();
                menuItem.setEnabled(true);
                return true;
            case R.id.filter_menu_item /*2131755955*/:
                menuItem.setEnabled(false);
                onFiltersClick();
                menuItem.setEnabled(true);
                return true;
            default:
                return false;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean z = false;
        MenuItem findItem = menu.findItem(R.id.refresh_menu_item);
        MenuItem findItem2 = menu.findItem(R.id.filter_menu_item);
        findItem.setEnabled(!this.f);
        if (!this.f) {
            z = true;
        }
        findItem2.setEnabled(z);
        return true;
    }

    public void onRefresh() {
        this.d = null;
        b();
    }

    protected void onStart() {
        super.onStart();
        if (this.e != null) {
            WINGiTManager.trackDisplay(this.e.getName(), null);
        }
    }
}
