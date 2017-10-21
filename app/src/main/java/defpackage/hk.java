package defpackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.animation.AnimatorProxy;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetNearProListData;
import fr.smoney.android.izly.data.model.NearPro;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.NearProActivity;
import fr.smoney.android.izly.ui.PromotionalOfferDetails;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class hk extends aa implements OnClickListener, OnInfoWindowClickListener, OnMarkerClickListener, LocationSource, gf {
    private static int e = 50;
    private ViewPager f;
    private hk$a g;
    private boolean h = false;
    private GoogleMap i;
    private GetNearProListData j;
    private HashMap<Marker, NearPro> k;
    private LatLngBounds l;
    private OnLocationChangedListener m;
    private View n;

    private void a(LatLng latLng) {
        if (this.l == null) {
            Builder builder = new Builder();
            builder.include(latLng);
            this.l = builder.build();
            return;
        }
        this.l = this.l.including(latLng);
    }

    private void c(boolean z) {
        int i = 1;
        if (z) {
            if (this.f.getVisibility() != 8) {
                i = 0;
            }
            if (i != 0) {
                this.f.setVisibility(0);
                AnimatorProxy.wrap(this.f).setPivotY((float) this.f.getHeight());
                ObjectAnimator ofFloat = ObjectAnimator.ofFloat(this.f, "scaleY", 0.0f, 1.0f);
                ofFloat.setDuration(400);
                ofFloat.start();
                return;
            }
            return;
        }
        if (this.f.getVisibility() != 0) {
            i = 0;
        }
        if (i != 0) {
            AnimatorProxy.wrap(this.f).setPivotY((float) this.f.getHeight());
            ofFloat = ObjectAnimator.ofFloat(this.f, "scaleY", 1.0f, 0.0f);
            ofFloat.setDuration(400);
            ofFloat.start();
            ofFloat.addListener(new hk$1(this));
        }
    }

    private void n() {
        ArrayList b = this.j.b();
        if (b != null && b.size() > 0) {
            hk$a hk_a = this.g;
            hk_a.a = b;
            hk_a.notifyDataSetChanged();
            this.f.setAdapter(this.g);
            c(true);
        }
    }

    @SuppressLint({"NewApi"})
    private void o() {
        if (this.i != null) {
            this.i.clear();
        }
        this.k = new HashMap();
        Iterator it = this.j.b.iterator();
        while (it.hasNext()) {
            Bitmap a;
            Bitmap a2;
            NearPro nearPro = (NearPro) it.next();
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(nearPro.f, nearPro.g));
            if (nearPro.a() && cn.a(nearPro.e).s > 0) {
                a = iw.a(getActivity(), R.drawable.plan_annotation_izly_promo);
                a2 = iw.a(getActivity(), cn.a(nearPro.e).s);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iw.a(a, 0.0f, 0.0f, a2, Math.abs((((float) a.getWidth()) / 2.4f) - ((float) (a2.getWidth() / 2))), (float) Math.abs((a.getHeight() / 3) - (a2.getHeight() / 2)))));
            } else if (cn.a(nearPro.e).r > 0) {
                a = iw.a(getActivity(), R.drawable.plan_annotation_izly);
                a2 = iw.a(getActivity(), cn.a(nearPro.e).r);
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iw.a(a, 0.0f, 0.0f, a2, Math.abs((((float) a.getWidth()) / 2.4f) - ((float) (a2.getWidth() / 2))), (float) Math.abs((a.getHeight() / 3) - (a2.getHeight() / 2)))));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(240.0f));
                a2 = null;
                a = null;
            }
            markerOptions.title(nearPro.a);
            Marker addMarker = this.i.addMarker(markerOptions);
            this.k.put(addMarker, nearPro);
            if (addMarker != null) {
                a(addMarker.getPosition());
            }
            if (a != null) {
                a.recycle();
            }
            if (a2 != null) {
                a2.recycle();
            }
        }
        View view = getChildFragmentManager().findFragmentById(R.id.map).getView();
        if (view.getViewTreeObserver().isAlive()) {
            view.getViewTreeObserver().addOnGlobalLayoutListener(new hk$2(this, view));
        }
    }

    private void p() {
        if (this.l != null) {
            this.i.moveCamera(CameraUpdateFactory.newLatLngBounds(this.l, e));
            this.h = true;
        }
    }

    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.m = onLocationChangedListener;
    }

    public final void b_() {
        this.j = i().aW;
        Location location = ((NearProActivity) getActivity()).f;
        if (location != null) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            this.i.clear();
            this.l = null;
            if (i().aW != null) {
                this.j = i().aW;
                o();
                a(latLng);
                p();
            }
        }
    }

    public void deactivate() {
        this.m = null;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ViewGroup viewGroup = (ViewGroup) this.n.findViewById(R.id.near_pro_map_root_view);
        viewGroup.requestTransparentRegion(viewGroup);
        this.j = (GetNearProListData) getActivity().getIntent().getParcelableExtra("fr.smoney.android.izly.extras.nearProMapData");
        if (this.j == null) {
            if (i().aW != null) {
                this.j = i().aW;
            } else {
                this.j = new GetNearProListData();
            }
        }
        this.f = (ViewPager) this.n.findViewById(R.id.near_pro_promotional_offer);
        this.g = new hk$a(this);
        this.j.a();
        n();
        if (this.i == null) {
            this.i = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
            if (this.i != null) {
                this.i.setOnMarkerClickListener(this);
                this.i.setOnInfoWindowClickListener(this);
                o();
                this.i.setLocationSource(this);
                this.i.setMyLocationEnabled(true);
                this.i.getUiSettings().setAllGesturesEnabled(true);
            }
        }
        ((NearProActivity) getActivity()).d = this;
    }

    public void onClick(View view) {
        Parcelable a = this.g.a(this.f.getCurrentItem());
        Intent a2 = is.a(getActivity(), PromotionalOfferDetails.class);
        a2.putExtra("fr.smoney.android.izly.promo", a);
        startActivity(a2);
    }

    public View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        this.n = layoutInflater.inflate(R.layout.near_pro_map, null);
        return this.n;
    }

    public void onInfoWindowClick(Marker marker) {
        marker.hideInfoWindow();
        NearPro nearPro = (NearPro) this.k.get(marker);
        Intent a = is.a(getActivity(), ContactDetailsActivity.class);
        a.putExtra("fr.smoney.android.izly.extras.contactId", nearPro);
        startActivity(a);
    }

    public boolean onMarkerClick(Marker marker) {
        this.j.a(((NearPro) this.k.get(marker)).d);
        if (this.j.b() == null || this.j.b().size() == 0) {
            this.j.a(-1);
        }
        if (this.j.b() == null || this.j.b().size() == 0) {
            c(false);
        } else {
            n();
        }
        return false;
    }

    public void onPause() {
        if (this.i != null) {
            this.i.setMyLocationEnabled(false);
        }
        super.onPause();
    }

    public void onResume() {
        super.onResume();
        if (this.i != null && !this.i.isMyLocationEnabled()) {
            this.i.setMyLocationEnabled(true);
        }
    }
}
