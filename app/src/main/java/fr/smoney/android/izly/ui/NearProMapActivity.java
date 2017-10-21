package fr.smoney.android.izly.ui;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.LatLngBounds.Builder;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;

import defpackage.is;
import defpackage.jl;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.SmoneyABSActivity;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.data.model.PromotionalOffer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class NearProMapActivity extends SmoneyABSActivity implements OnClickListener, OnInfoWindowClickListener, OnMarkerClickListener, LocationSource {
    private static int b = 50;
    private ViewPager c;
    private a d;
    private boolean e = false;
    private GoogleMap f;
    private GetNearProListData g;
    private HashMap<Marker, NearPro> h;
    private LatLngBounds i;
    private MenuItem j;
    private OnLocationChangedListener k;

    public final class a extends PagerAdapter {
        ArrayList<PromotionalOffer> a = new ArrayList();
        final /* synthetic */ NearProMapActivity b;
        private LayoutInflater c;

        final class a {
            TextView a;
            TextView b;
            TextView c;
            ImageView d;
            final /* synthetic */ a e;

            a(a aVar) {
                this.e = aVar;
            }
        }

        public a(NearProMapActivity nearProMapActivity) {
            this.b = nearProMapActivity;
            this.c = (LayoutInflater) nearProMapActivity.getSystemService("layout_inflater");
        }

        public final PromotionalOffer a(int i) {
            return (PromotionalOffer) this.a.get(i);
        }

        public final void destroyItem(View view, int i, Object obj) {
            ((ViewPager) view).removeView((View) obj);
        }

        public final int getCount() {
            return this.a.size();
        }

        public final Object instantiateItem(ViewGroup viewGroup, int i) {
            a aVar = new a(this);
            View inflate = this.c.inflate(R.layout.layout_promo_offer, null);
            aVar.a = (TextView) inflate.findViewById(R.id.pr_title);
            aVar.b = (TextView) inflate.findViewById(R.id.pr_activity);
            aVar.c = (TextView) inflate.findViewById(R.id.pr_description);
            aVar.d = (ImageView) inflate.findViewById(R.id.pr_aiv_avatar);
            PromotionalOffer a = a(i);
            if (a != null) {
                aVar.a.setText(a.c);
                aVar.c.setText(a.d);
                aVar.b.setText(a.i);
                if (a.m == fr.smoney.android.izly.data.model.PromotionalOffer.a.GLOBAL) {
                    aVar.d.setImageResource(R.drawable.list_aiv_avatar_smoney);
                } else {
                    aVar.d.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                }
                aVar.e.b.a.a.displayImage(jl.a(a.a), aVar.d);
            }
            inflate.setOnClickListener(this.b);
            ((ViewPager) viewGroup).addView(inflate, 0);
            return inflate;
        }

        public final boolean isViewFromObject(View view, Object obj) {
            return view == ((View) obj);
        }
    }

    static /* synthetic */ void b(NearProMapActivity nearProMapActivity) {
        CameraUpdate newLatLngBounds = CameraUpdateFactory.newLatLngBounds(nearProMapActivity.i, b);
        if (nearProMapActivity.f != null) {
            nearProMapActivity.f.moveCamera(newLatLngBounds);
        }
        nearProMapActivity.e = true;
    }

    private void b(boolean z) {
        int i = 1;
        if (z) {
            if (this.c.getVisibility() != 8) {
                i = 0;
            }
            if (i != 0) {
                this.c.setVisibility(0);
                AnimatorProxy.wrap(this.c).setPivotY((float) this.c.getHeight());
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.c, "scaleY", 0.0f, 1.0f);
                ofFloat.setDuration(400);
                ofFloat.start();
                return;
            }
            return;
        }
        if (this.c.getVisibility() != 0) {
            i = 0;
        }
        if (i != 0) {
            AnimatorProxy.wrap(this.c).setPivotY((float) this.c.getHeight());
            ofFloat = ObjectAnimator.ofFloat(this.c, "scaleY", 1.0f, 0.0f);
            ofFloat.setDuration(400);
            ofFloat.start();
            ofFloat.addListener(new AnimatorListenerAdapter(this) {
                final /* synthetic */ NearProMapActivity a;

                {
                    this.a = r1;
                }

                public final void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    this.a.c.setVisibility(8);
                }
            });
        }
    }

    private void k() {
        ArrayList b = this.g.b();
        if (b != null && b.size() > 0) {
            a aVar = this.d;
            aVar.a = b;
            aVar.notifyDataSetChanged();
            this.c.setAdapter(this.d);
            b(true);
        }
    }

    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.k = onLocationChangedListener;
    }

    public void deactivate() {
        this.k = null;
    }

    public void onClick(View view) {
        Parcelable a = this.d.a(this.c.getCurrentItem());
        Intent a2 = is.a(this, PromotionalOfferDetails.class);
        a2.putExtra("fr.smoney.android.izly.promo", a);
        startActivity(a2);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.near_pro_map);
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.near_pro_map_root_view);
        viewGroup.requestTransparentRegion(viewGroup);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeButtonEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        this.g = (GetNearProListData) getIntent().getParcelableExtra("fr.smoney.android.izly.extras.nearProMapData");
        this.c = (ViewPager) findViewById(R.id.near_pro_promotional_offer);
        this.d = new a(this);
        this.g.a();
        k();
        if (this.f == null) {
            this.f = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            if (this.f != null) {
                this.f.setOnMarkerClickListener(this);
                this.f.setOnInfoWindowClickListener(this);
                this.h = new HashMap();
                Iterator it = this.g.b.iterator();
                while (it.hasNext()) {
                    NearPro nearPro = (NearPro) it.next();
                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(new LatLng(nearPro.f, nearPro.g));
                    if (nearPro.a() && cn.a(nearPro.e).s > 0) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(cn.a(nearPro.e).s));
                    } else if (cn.a(nearPro.e).r > 0) {
                        markerOptions.icon(BitmapDescriptorFactory.fromResource(cn.a(nearPro.e).r));
                    } else {
                        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(240.0f));
                    }
                    markerOptions.title(nearPro.a);
                    Marker addMarker = this.f.addMarker(markerOptions);
                    this.h.put(addMarker, nearPro);
                    LatLng position = addMarker.getPosition();
                    if (this.i == null) {
                        Builder builder = new Builder();
                        builder.include(position);
                        this.i = builder.build();
                    } else {
                        this.i = this.i.including(position);
                    }
                }
                final View view = getSupportFragmentManager().findFragmentById(R.id.map).getView();
                if (view.getViewTreeObserver().isAlive()) {
                    view.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener(this) {
                        final /* synthetic */ NearProMapActivity b;

                        public final void onGlobalLayout() {
                            if (VERSION.SDK_INT < 16) {
                                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                            } else {
                                view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            }
                            NearProMapActivity.b(this.b);
                        }
                    });
                }
                this.f.setLocationSource(this);
                this.f.setMyLocationEnabled(true);
                this.f.getUiSettings().setAllGesturesEnabled(true);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        this.j = menu.add(R.string.menu_item_pro_list);
        this.j.setIcon(R.drawable.pict_list);
        this.j.setShowAsAction(2);
        return super.onCreateOptionsMenu(menu);
    }

    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        NearPro nearPro = (NearPro) this.h.get(marker);
        Intent a = is.a(this, ContactDetailsActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.contactId", nearPro);
        startActivity(a);
    }

    public boolean onMarkerClick(Marker marker) {
        this.g.a(((NearPro) this.h.get(marker)).d);
        if (this.g.b() == null || this.g.b().size() == 0) {
            this.g.a(-1);
        }
        if (this.g.b() == null || this.g.b().size() == 0) {
            b(false);
        } else {
            k();
        }
        return false;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem != this.j) {
            return super.onOptionsItemSelected(menuItem);
        }
        finish();
        return true;
    }

    public void onPause() {
        if (this.f != null) {
            this.f.setMyLocationEnabled(false);
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.f != null && !this.f.isMyLocationEnabled()) {
            this.f.setMyLocationEnabled(true);
        }
    }
}
