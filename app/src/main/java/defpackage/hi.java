package defpackage;

import android.content.Intent;
import android.graphics.Point;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.dragdrop.GripView;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyApplication;
import fr.smoney.android.izly.data.model.CounterListData;
import fr.smoney.android.izly.data.model.GetBilendiBannerData;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.GetNewsFeedData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.NewsFeedItem;
import fr.smoney.android.izly.data.model.ServerError;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager;
import fr.smoney.android.izly.data.requestmanager.SmoneyRequestManager$a;
import fr.smoney.android.izly.data.service.SmoneyService;
import fr.smoney.android.izly.ui.GoodDealsActivity;
import fr.smoney.android.izly.ui.HomeActivity;
import fr.smoney.android.izly.ui.P2PGetDetailsActivity;
import fr.smoney.android.izly.ui.P2PPayRequestActivity;
import fr.smoney.android.izly.ui.PreAuthorizePaymentDetailsActivity;
import fr.smoney.android.izly.ui.PromotionalOfferDetails;
import fr.smoney.android.izly.ui.QRCodeActivity;
import fr.smoney.android.izly.ui.ReloadActivity;
import fr.smoney.android.izly.ui.TransactionListDetailsActivity;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.asn1.x509.DisplayText;

public final class hi extends aa implements OnRefreshListener, OnClickListener, SmoneyRequestManager$a {
    private AccelerateDecelerateInterpolator A;
    private cl B;
    private hi$a C;
    private hi$b D;
    private GetNearProListData E;
    private boolean F;
    private boolean G;
    private boolean H;
    private boolean I = false;
    private ge J;
    private WebView K;
    private it L;
    private OnClickListener M = new hi$11(this);
    public int e = -1;
    public int f = -1;
    private View g;
    private View h;
    private View i;
    private View j;
    private ListView k;
    private SwipeRefreshLayout l;
    private View m;
    private View n;
    private View o;
    private View p;
    private View q;
    private View r;
    private FrameLayout s;
    private FrameLayout t;
    private ListView u;
    private View v;
    private View w;
    private int x;
    private int y;
    private int z;

    private void a(View view, boolean z) {
        this.a.e = true;
        a(view);
        c();
    }

    private void a(CounterListData counterListData, ServerError serverError) {
        if (serverError != null) {
            a(serverError);
        } else if (counterListData == null) {
            a(hw.a(this.d, this));
        } else {
            a(this.j, true);
        }
    }

    private void a(GetBilendiBannerData getBilendiBannerData) {
        if (getBilendiBannerData == null || getBilendiBannerData.b != DisplayText.DISPLAY_TEXT_MAXIMUM_SIZE) {
            this.K.setVisibility(8);
            return;
        }
        this.K.loadData(getBilendiBannerData.a, "text/html; charset=utf-8", null);
        this.K.setWebViewClient(new hi$5(this));
    }

    private void a(GetNearProListData getNearProListData) {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getNearProListData.c);
        if (getNearProListData.b.size() > 0) {
            Iterator it = getNearProListData.b.iterator();
            while (it.hasNext()) {
                arrayList.addAll(((NearPro) it.next()).p);
            }
        }
        if (arrayList.size() > 0) {
            hi$b hi_b = this.D;
            hi_b.b = arrayList;
            hi_b.notifyDataSetChanged();
            ListView listView = this.u;
            WindowManager windowManager = this.d.getWindowManager();
            Point point = new Point();
            if (VERSION.SDK_INT >= 13) {
                windowManager.getDefaultDisplay().getSize(point);
            } else {
                Display defaultDisplay = windowManager.getDefaultDisplay();
                point.x = defaultDisplay.getWidth();
                point.y = defaultDisplay.getHeight();
            }
            float min = Math.min((((float) point.y) * GripView.DEFAULT_DOT_SIZE_RADIUS_DP) / 3.0f, (float) (this.z * this.D.getCount()));
            if (!this.H) {
                float measuredHeight = min / ((float) this.t.getMeasuredHeight());
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.t, "scaleY", 1.0f, measuredHeight);
                ofFloat.setInterpolator(this.A);
                Animation animationSet = new AnimationSet(true);
                Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                Animation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
                animationSet.setDuration(200);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(translateAnimation);
                listView.setLayoutAnimation(new LayoutAnimationController(animationSet, 0.5f));
                ViewHelper.setPivotY(this.t, 0.0f);
                ofFloat.setDuration(500);
                ofFloat.addListener(new hi$10(this, ofFloat, min, listView));
                ofFloat.start();
                return;
            }
            return;
        }
        Object obj = this.v;
        float f = (float) this.y;
        if (!this.H) {
            float measuredHeight2 = f / ((float) this.t.getMeasuredHeight());
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(this.t, "scaleY", 1.0f, measuredHeight2);
            ofFloat2.setInterpolator(this.A);
            ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(obj, "alpha", 0.0f, 1.0f);
            ViewHelper.setPivotY(this.t, 0.0f);
            animatorSet.setDuration(500);
            animatorSet.playSequentially(ofFloat2, ofFloat3);
            animatorSet.addListener(new hi$8(this, obj));
            ofFloat3.addListener(new hi$9(this, ofFloat2, f, obj));
            animatorSet.start();
        }
    }

    private void a(GetNearProListData getNearProListData, ServerError serverError) {
        this.w.setVisibility(4);
        if (serverError != null) {
            a(serverError);
        } else if (getNearProListData != null) {
            this.B.bo = 0;
            this.E = getNearProListData;
            if (this.G) {
                a(this.E);
            }
        }
    }

    private void a(GetNewsFeedData getNewsFeedData, ServerError serverError) {
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        this.l.setRefreshing(false);
        if (serverError != null) {
            a(serverError);
            this.q.setVisibility(0);
            this.n.setVisibility(0);
            this.m.setVisibility(0);
            this.o.setVisibility(8);
            a(this.p, true);
        } else if (getNewsFeedData == null) {
            a(hw.a(this.d, this));
            this.q.setVisibility(0);
            this.n.setVisibility(0);
            this.m.setVisibility(0);
            this.o.setVisibility(8);
            a(this.p, true);
        } else {
            this.q.setVisibility(8);
            if (this.F) {
                this.B.cs = false;
            }
            if (!(getNewsFeedData.a == null || this.B.b == null)) {
                this.B.b.B = getNewsFeedData.a;
            }
            a(getNewsFeedData.f);
        }
    }

    static /* synthetic */ void a(hi hiVar, Parcelable parcelable) {
        Intent a = is.a(hiVar.d, PromotionalOfferDetails.class);
        a.putExtra("fr.smoney.android.izly.promo", parcelable);
        hiVar.a(a, true);
    }

    static /* synthetic */ void a(hi hiVar, NewsFeedItem newsFeedItem) {
        Intent a = is.a(hiVar.d, TransactionListDetailsActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.relatedFeedItem", newsFeedItem);
        hiVar.a(a, true);
    }

    private void a(List<NewsFeedItem> list) {
        this.C.a.clear();
        if (list == null || list.size() <= 0) {
            this.q.setVisibility(0);
            this.n.setVisibility(0);
            this.m.setVisibility(0);
            this.o.setVisibility(8);
            a(this.p, true);
        } else {
            for (NewsFeedItem add : list) {
                this.C.a.add(add);
            }
            a(this.j, true);
            this.q.setVisibility(8);
        }
        this.C.notifyDataSetChanged();
    }

    private void a(boolean z, int i, long j, long j2, boolean z2) {
        int keyAt;
        this.F = false;
        this.l.setRefreshing(true);
        this.d.setSupportProgressBarIndeterminateVisibility(false);
        SmoneyRequestManager j3 = j();
        String str = this.B.b.a;
        String str2 = this.B.b.c;
        Parcelable parcelable = this.B.bv;
        int size = j3.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            Intent intent = (Intent) j3.b.valueAt(i2);
            if (intent.getIntExtra("com.foxykeep.datadroid.extras.workerType", -1) == 226 && intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedUserId").equals(str) && intent.getStringExtra("fr.smoney.android.izly.extras.GetNewsFeedSessionId").equals(str2) && intent.getIntExtra("fr.smoney.android.izly.extras.GetNewsFeedCurrentPage", 0) == 1 && intent.getIntExtra("fr.smoney.android.izly.extras.GetNewsFeedItemPerPage", 0) == 10 && intent.getLongExtra("fr.smoney.android.izly.extras.GetNewsFeedFromDate", -1) == -1 && intent.getLongExtra("fr.smoney.android.izly.extras.GetNewsFeedToDate", -1) == -1 && !intent.getBooleanExtra("fr.smoney.android.izly.extras.GetNewsFeedIsRefresh", false)) {
                keyAt = j3.b.keyAt(i2);
                break;
            }
        }
        keyAt = SmoneyRequestManager.a.nextInt(1000000);
        Intent intent2 = new Intent(j3.c, SmoneyService.class);
        intent2.putExtra("com.foxykeep.datadroid.extras.workerType", 226);
        intent2.putExtra("com.foxykeep.datadroid.extras.receiver", j3.e);
        intent2.putExtra("com.foxykeep.datadroid.extras.requestId", keyAt);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedUserId", str);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedSessionId", str2);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedCurrentPage", 1);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedItemPerPage", 10);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedFromDate", -1);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedToDate", -1);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedOldData", parcelable);
        intent2.putExtra("fr.smoney.android.izly.extras.GetNewsFeedIsRefresh", false);
        j3.c.startService(intent2);
        j3.b.append(keyAt, intent2);
        j3.f.bw = null;
        super.a(keyAt, 226, false);
    }

    static /* synthetic */ void b(hi hiVar, NewsFeedItem newsFeedItem) {
        Intent a = is.a(hiVar.d, P2PPayRequestActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.relatedFeedItem", newsFeedItem);
        hiVar.a(a, 10, true);
    }

    static /* synthetic */ void c(hi hiVar) {
        int a = it.a(hiVar.d);
        int i = (int) (((float) a) / 6.4f);
        LayoutParams layoutParams = hiVar.K.getLayoutParams();
        hiVar.K.setScrollbarFadingEnabled(true);
        if (layoutParams != null) {
            layoutParams.width = a;
            layoutParams.height = i;
            hiVar.K.setLayoutParams(layoutParams);
        }
        hiVar.K.setOnTouchListener(new hi$6(hiVar));
        hiVar.K.setVisibility(0);
    }

    static /* synthetic */ void c(hi hiVar, NewsFeedItem newsFeedItem) {
        Intent a = is.a(hiVar.d, P2PGetDetailsActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.relatedFeedItem", newsFeedItem);
        hiVar.a(a, true);
    }

    static /* synthetic */ void d(hi hiVar, NewsFeedItem newsFeedItem) {
        Intent a = is.a(hiVar.d, PreAuthorizePaymentDetailsActivity.class);
        a.putExtra("INTENT_EXTRA_P2P_PAY_REQUEST_RELATED_FEED_ITEM", newsFeedItem);
        hiVar.a(a, true);
    }

    public static hi n() {
        return new hi();
    }

    private void o() {
        if (this.B.b != null) {
            if ((this.B.b.s >= 18 ? 1 : null) != null) {
                Intent intent = new Intent(this.d, GoodDealsActivity.class);
                intent.putExtra(GoodDealsActivity.b, this.e);
                intent.putExtra(GoodDealsActivity.c, this.f);
                startActivity(intent);
                return;
            }
            a(hu.a(getString(R.string.dialog_warning_title), getString(R.string.cgu_good_deals_error_age), getString(17039370)));
            return;
        }
        a(hu.a(getString(R.string.dialog_error_data_error_title), getString(R.string.dialog_error_data_error_message), getString(17039370)));
    }

    private void p() {
        if (!this.H) {
            AnimatorSet animatorSet = new AnimatorSet();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.s, "alpha", 1.0f, 0.0f);
            ObjectAnimator.ofFloat(this.t, "scaleY", 1.0f, 0.0f).setInterpolator(this.A);
            ViewHelper.setPivotY(this.t, 0.0f);
            animatorSet.playTogether(r2, ofFloat);
            animatorSet.setDuration(500);
            animatorSet.addListener(new hi$7(this));
            animatorSet.start();
        }
    }

    private void q() {
        super.a(j().e(this.B.b.a, this.B.b.c), 261, true);
    }

    public final void a(ie ieVar, Bundle bundle) {
        switch (hi$4.a[ieVar.ordinal()]) {
            case 1:
                startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
                return;
            default:
                super.a(ieVar, bundle);
                return;
        }
    }

    public final boolean a() {
        if (this.G) {
            p();
        } else {
            a_(false);
        }
        return true;
    }

    public final void a_(int i, int i2, int i3, Bundle bundle) {
        this.l.setRefreshing(false);
        this.I = false;
        if (super.a(i, i2, i3, bundle)) {
            ServerError serverError = (ServerError) bundle.getParcelable("fr.smoney.android.izly.extras.serverError");
            if (i2 == 9923) {
                bundle.getInt("fr.smoney.android.izly.extras.NbNewPromoOffers");
            } else if (i2 == 226) {
                a((GetNewsFeedData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNewsFeed"), serverError);
            } else if (i2 == 213) {
                a((GetNearProListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetNearProList"), serverError);
            } else if (i2 == 261) {
                a((CounterListData) bundle.getParcelable("fr.smoney.android.izly.extras.GetMyCounterList"), serverError);
            } else if (i2 == 273) {
                a((GetBilendiBannerData) bundle.getParcelable("fr.smoney.android.izly.extra.BilendiBannerData"));
            }
        }
        if (this.B.bv != null) {
            a(this.B.bv.f);
        }
    }

    public final void b_(int i) {
        this.I = false;
        if (i == 9923) {
            return;
        }
        if (i == 226) {
            a(this.B.bv, this.B.bw);
        } else if (i == 213) {
            a(this.B.aW, this.B.aX);
        } else if (i == 261) {
            a(this.B.bB, this.B.bC);
        } else if (i == 273) {
            a(this.B.ch);
        }
    }

    public final void d(ie ieVar) {
        if (ieVar == ie.ProgressType && h() == 213) {
            g();
        } else {
            super.d(ieVar);
        }
    }

    public final void l() {
        this.p.setVisibility(0);
        if (this.r.getVisibility() == 0) {
            if (!this.b) {
                this.r.startAnimation(AnimationUtils.loadAnimation(this.d, 17432579));
            }
            this.r.setVisibility(8);
            this.k.setVisibility(0);
        }
        super.l();
        a(this.p, true);
        this.h.setEnabled(true);
        this.i.setEnabled(true);
        if (this.B.b == null) {
            super.a(j().a(SmoneyApplication.c.f()), 228, true);
        }
        this.d.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public final void m() {
        this.o.setVisibility(8);
        this.p.setVisibility(8);
        if (this.r.getVisibility() == 8) {
            this.r.setVisibility(0);
            if (this.b) {
                this.r.startAnimation(AnimationUtils.loadAnimation(this.d, 17432578));
            }
            this.k.setVisibility(8);
        }
        this.h.setEnabled(false);
        this.i.setEnabled(false);
        this.d.getSupportActionBar().setDisplayShowHomeEnabled(false);
        super.m();
    }

    public final void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.B = i();
        this.C = new hi$a(this, this.d);
        this.D = new hi$b(this, this.d);
        this.u.setAdapter(this.D);
        this.u.setOnItemClickListener(this.D);
        ListAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(this.C);
        alphaInAnimationAdapter.setAbsListView(this.k);
        this.k.setAdapter(alphaInAnimationAdapter);
        this.k.setOnItemClickListener(this.C);
        this.k.setOnScrollListener(new PauseOnScrollListener(SmoneyApplication.d.a, true, false));
        b(true);
        setHasOptionsMenu(true);
        if (bundle != null) {
            this.E = (GetNearProListData) bundle.getParcelable("getNearProListData");
        }
        if (this.B.bv != null) {
            a(this.B.bv.f);
        } else {
            try {
                a(this.p, true);
                a(false, 1, -1, -1, false);
            } catch (NullPointerException e) {
            }
        }
        if (this.E != null) {
            a(this.E);
        }
        this.K = (WebView) this.d.findViewById(R.id.bilendiAdserverView);
        this.L = new it();
        jb.a(this.d, R.string.screen_name_home_activity);
        if (this.e != -1) {
            o();
        }
    }

    public final void onActivityResult(int i, int i2, Intent intent) {
        if (i == 10 && i2 == 5) {
            a(this.B.bv.f);
        } else {
            super.onActivityResult(i, i2, intent);
        }
    }

    public final void onClick(View view) {
        if (view == this.i) {
            o();
        } else if (view == this.h) {
            Log.d("HomeFragment", "DisplayingReload");
            a(is.a(this.d, ReloadActivity.class), true);
        } else if (view == this.g) {
            a(is.a(this.d, QRCodeActivity.class), true);
        } else if (view == this.j) {
            if (this.B.bB == null) {
                try {
                    q();
                } catch (NullPointerException e) {
                }
            }
            View findViewById;
            if (this.j.findViewById(R.id.ll_counter_details).getVisibility() == 0) {
                findViewById = this.j.findViewById(R.id.ll_counter_details);
                Animation hi_3 = new hi$3(findViewById, findViewById.getMeasuredHeight());
                hi_3.setDuration(500);
                findViewById.startAnimation(hi_3);
                ((ImageView) this.j.findViewById(R.id.counter_arrow)).setImageResource(R.drawable.disclosure_down);
                return;
            }
            findViewById = this.j.findViewById(R.id.ll_counter_details);
            findViewById.measure(-1, -2);
            int measuredHeight = findViewById.getMeasuredHeight();
            findViewById.getLayoutParams().height = 1;
            findViewById.setVisibility(0);
            Animation hi_2 = new hi$2(findViewById, measuredHeight);
            hi_2.setDuration(500);
            findViewById.startAnimation(hi_2);
            ((ImageView) this.j.findViewById(R.id.counter_arrow)).setImageResource(R.drawable.disclosure_up);
        } else if (view == this.q) {
            hp hpVar = ((HomeActivity) this.d).b;
            hp$c hp_c = (hp$c) hpVar.h.get(2);
            hpVar.e.b(hp_c.d);
            hpVar.f.a = 2;
            hpVar.g = hp_c.d;
            hpVar.f.notifyDataSetChanged();
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.J = new ge(this.d);
    }

    public final void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.home_fragment, null);
        this.j = inflate.findViewById(R.id.account_balance);
        this.i = inflate.findViewById(R.id.bonPlansView);
        this.h = inflate.findViewById(R.id.reloadView);
        this.g = inflate.findViewById(R.id.payView);
        this.q = inflate.findViewById(R.id.list_empty_view);
        this.p = inflate.findViewById(R.id.account_balance);
        this.k = (ListView) inflate.findViewById(R.id.lv_home_news_feed);
        this.l = (SwipeRefreshLayout) inflate.findViewById(R.id.swl_home_news_feed);
        this.l.setOnRefreshListener(this);
        this.l.setColorSchemeResources(new int[]{R.color.izly_blue_light});
        this.i.setOnClickListener(this);
        this.h.setOnClickListener(this);
        this.g.setOnClickListener(this);
        this.j.setOnClickListener(this);
        this.p.setOnClickListener(this);
        this.s = (FrameLayout) inflate.findViewById(R.id.fl_home_sliding_bg);
        this.u = (ListView) inflate.findViewById(R.id.sldrawer_home_content);
        this.t = (FrameLayout) inflate.findViewById(R.id.ev_home_framelayout);
        this.v = inflate.findViewById(R.id.ev_home_img_promo);
        this.w = inflate.findViewById(R.id.pb_ev_home_promo);
        this.x = (int) getResources().getDimension(R.dimen.home_promo_panel_content_default_height);
        this.y = (int) getResources().getDimension(R.dimen.home_promo_panel_no_item_height);
        this.z = (int) getResources().getDimension(R.dimen.home_promo_panel_list_item_height);
        this.A = new AccelerateDecelerateInterpolator();
        this.n = inflate.findViewById(R.id.tv_list_empty_view);
        this.m = inflate.findViewById(R.id.iv_place_holder_no_news);
        this.o = inflate.findViewById(R.id.pb_list_empty_view);
        this.r = inflate.findViewById(R.id.no_network_panel);
        this.s.setOnTouchListener(new hi$1(this));
        return inflate;
    }

    public final boolean onOptionsItemSelected(MenuItem menuItem) {
        return false;
    }

    public final void onPause() {
        super.onPause();
    }

    public final void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    public final void onRefresh() {
        this.I = true;
        if (this.b) {
            a(false, 1, -1, -1, false);
        }
    }

    public final void onResume() {
        super.onResume();
        if (this.B.cs && !a(226)) {
            try {
                a(false, 1, -1, -1, false);
            } catch (NullPointerException e) {
            }
        }
        if (this.B.cy && !a(261)) {
            q();
        }
        if (this.d != null) {
            ((HomeActivity) this.d).b.f.notifyDataSetChanged();
        }
        if (this.B.b != null && this.B.b.t != null && this.B.b.u != null && !a(273)) {
            super.a(j().a(String.valueOf(this.L.a(this.d, 320)), String.valueOf(this.L.a(this.d, 50)), getString(R.string.profilage_bancaire_home), this.B.b.t, this.B.b.u, it.a(this.B.b.A, this.d)), 273, true);
        }
    }

    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("getNearProListData", this.E);
        super.onSaveInstanceState(bundle);
    }
}
