package defpackage;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.astuetz.viewpagertabs.ViewPagerTabs;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GetContactDetailsData;
import fr.smoney.android.izly.data.model.ProInfos;
import fr.smoney.android.izly.data.model.PromotionalOffer;
import fr.smoney.android.izly.ui.ContactDetailsActivity;
import fr.smoney.android.izly.ui.ContactDetailsActivity.c;
import fr.smoney.android.izly.ui.P2PGetMultActivity;
import fr.smoney.android.izly.ui.P2PPayActivity;
import fr.smoney.android.izly.ui.PreAuthorizePaymentActivity;
import fr.smoney.android.izly.ui.PromotionalOfferDetails;
import fr.smoney.android.izly.ui.widget.DetailTwoText;
import java.util.ArrayList;
import java.util.Iterator;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.tls.CipherSuite;

public final class hc extends ha implements OnClickListener, OnItemClickListener, OnMapClickListener, OnMarkerClickListener, c {
    private static final String e = ContactDetailsActivity.class.getSimpleName();
    private Button f;
    private Button g;
    private LinearLayout h;
    private ImageView i;
    private ImageView j;
    private TextView k;
    private DetailTwoText l;
    private DetailTwoText m;
    private DetailTwoText n;
    private DetailTwoText o;
    private GoogleMap p;
    private MapView q;
    private View r;
    private View s;
    private View t;
    private GetContactDetailsData u;
    private View v;
    private OnClickListener w = new hc$2(this);

    public static hc a(GetContactDetailsData getContactDetailsData) {
        hc hcVar = new hc();
        Bundle bundle = new Bundle();
        bundle.putParcelable("getContactDetailsData", getContactDetailsData);
        hcVar.setArguments(bundle);
        return hcVar;
    }

    static /* synthetic */ void c(hc hcVar) {
        ProInfos proInfos = hcVar.u.J;
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(proInfos.e, proInfos.f));
        Object obj = (proInfos.o == null || proInfos.o.size() <= 0) ? null : 1;
        Bitmap a;
        Bitmap a2;
        if (obj != null && cn.a(proInfos.i).s > 0) {
            a = iw.a(hcVar.getActivity(), R.drawable.plan_annotation_izly_promo);
            a2 = iw.a(hcVar.getActivity(), cn.a(proInfos.i).s);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iw.a(a, 0.0f, 0.0f, a2, Math.abs((((float) a.getWidth()) / 2.4f) - ((float) (a2.getWidth() / 2))), (float) Math.abs((a.getHeight() / 3) - (a2.getHeight() / 2)))));
        } else if (cn.a(proInfos.i).r > 0) {
            a = iw.a(hcVar.getActivity(), R.drawable.plan_annotation_izly);
            a2 = iw.a(hcVar.getActivity(), cn.a(proInfos.i).r);
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(iw.a(a, 0.0f, 0.0f, a2, Math.abs((((float) a.getWidth()) / 2.4f) - ((float) (a2.getWidth() / 2))), (float) Math.abs((a.getHeight() / 3) - (a2.getHeight() / 2)))));
        } else {
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(240.0f));
        }
        hcVar.p.addMarker(markerOptions);
        hcVar.p.moveCamera(CameraUpdateFactory.newLatLngZoom(markerOptions.getPosition(), 17.0f));
    }

    @SuppressLint({"NewApi"})
    private void n() {
        if (this.u.j) {
            if (VERSION.SDK_INT < 11) {
                this.i.setAlpha(EACTags.SECURE_MESSAGING_TEMPLATE);
            } else {
                this.i.setAlpha(0.5f);
            }
            this.j.setVisibility(0);
            if (this.f != null) {
                this.f.setEnabled(false);
            }
            this.g.setEnabled(false);
            return;
        }
        if (VERSION.SDK_INT < 11) {
            this.i.setAlpha(CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
        } else {
            this.i.setAlpha(1.0f);
        }
        this.j.setVisibility(8);
        if (this.f != null) {
            this.f.setEnabled(true);
        }
        this.g.setEnabled(true);
    }

    private void o() {
        AppCompatActivity appCompatActivity = this.d;
        String str = this.u.q;
        String str2 = this.u.s;
        String str3 = this.u.a;
        jc.a(appCompatActivity, str, str2);
    }

    public final void a(boolean z) {
        this.u.j = z;
        n();
    }

    public final void onActivityCreated(Bundle bundle) {
        StringBuilder stringBuilder;
        super.onActivityCreated(bundle);
        this.k = (TextView) this.t.findViewById(R.id.pro_premium_promo_tv);
        this.l = (DetailTwoText) this.s.findViewById(R.id.dtt_phone);
        this.m = (DetailTwoText) this.s.findViewById(R.id.dtt_email);
        this.n = (DetailTwoText) this.s.findViewById(R.id.dtt_address);
        this.o = (DetailTwoText) this.s.findViewById(R.id.dtt_schedules);
        this.g = (Button) this.t.findViewById(R.id.b_p2p_pay);
        this.g.setOnClickListener(this);
        this.i = (ImageView) this.t.findViewById(R.id.aiv_avatar);
        this.j = (ImageView) this.t.findViewById(R.id.img_contact_blocked);
        this.h = (LinearLayout) this.r.findViewById(R.id.ll_last_transactions);
        if (this.u.J.e == -1.0d || this.u.J.f == -1.0d) {
            this.q.setVisibility(8);
            this.v.setVisibility(8);
        } else if (this.p == null && this.q.getViewTreeObserver().isAlive()) {
            this.q.getViewTreeObserver().addOnGlobalLayoutListener(new hc$1(this));
        }
        if (this.u.g) {
            this.c.a.displayImage(jl.a(this.u.b), this.i);
        }
        ((TextView) this.t.findViewById(R.id.tv_name)).setText(jf.a(this.u.x, this.u.y, this.u.b));
        if (TextUtils.isEmpty(this.u.e)) {
            this.l.setVisibility(8);
        } else {
            this.l.setRightText(jf.a(this.u.e));
        }
        if (TextUtils.isEmpty(this.u.w)) {
            this.m.setVisibility(8);
        } else {
            this.m.setRightText(this.u.w);
        }
        if (this.u.J.h) {
            this.g.setText(R.string.contact_details_bt_donate);
        }
        if (TextUtils.isEmpty(this.u.q)) {
            this.n.setVisibility(8);
        } else {
            StringBuilder stringBuilder2 = new StringBuilder(this.u.q);
            stringBuilder2.append("\n");
            stringBuilder2.append(this.u.r);
            stringBuilder2.append(" ");
            stringBuilder2.append(this.u.s);
            this.n.setRightText(stringBuilder2.toString());
        }
        if (this.u.J.q == null || this.u.J.q.size() <= 0) {
            this.o.setVisibility(8);
        } else {
            stringBuilder = new StringBuilder();
            Iterator it = this.u.J.q.iterator();
            while (it.hasNext()) {
                stringBuilder.append((String) it.next());
                stringBuilder.append('\n');
            }
            this.o.setRightText(stringBuilder.toString());
        }
        CharSequence charSequence = getResources().getText(cn.a(this.u.J.i).o).toString();
        if (TextUtils.isEmpty(charSequence)) {
            this.t.findViewById(R.id.tv_activity).setVisibility(8);
        } else {
            ((TextView) this.t.findViewById(R.id.tv_activity)).setText(charSequence);
        }
        if (this.u.J.d != -1.0d) {
            if (this.u.J.d >= 1000.0d) {
                stringBuilder2 = new StringBuilder(ProInfos.r.format(this.u.J.d / 1000.0d));
                stringBuilder2.append(" ");
                stringBuilder2.append(getString(R.string.contact_details_distance_unit));
                stringBuilder = stringBuilder2;
            } else {
                stringBuilder2 = new StringBuilder(String.valueOf(ProInfos.s.format((double) Math.round(this.u.J.d))));
                stringBuilder2.append(" ");
                stringBuilder2.append(getString(R.string.contact_details_distance_unit_meter));
                stringBuilder = stringBuilder2;
            }
            ((TextView) this.t.findViewById(R.id.tv_distance)).setText(stringBuilder.toString());
        } else {
            this.t.findViewById(R.id.tv_distance).setVisibility(8);
        }
        if (this.u.J.o == null || this.u.J.o.size() <= 0) {
            this.k.setVisibility(8);
        } else {
            this.k.setText(getString(R.string.contact_details_premium_promo_offer_label, new Object[]{((PromotionalOffer) this.u.J.o.get(0)).d}));
            this.k.setOnClickListener(this);
        }
        a(this.u, this.h, this.h);
        n();
    }

    public final void onClick(View view) {
        Intent a;
        if (view == this.g) {
            if (this.u.K != null) {
                a = is.a(this.d, PreAuthorizePaymentActivity.class);
                a.putExtra("INTENT_EXTRA_PRE_AUTHORIZATION", this.u.K);
                startActivity(a);
                return;
            }
            a = new Intent(this.d, P2PPayActivity.class);
            a.putExtra("defaultRecipientId", this.u.b);
            if (this.u.I.size() > 0) {
                a.putExtra("nearProData", this.u);
            }
            startActivity(a);
        } else if (view == this.f) {
            a = new Intent(this.d, P2PGetMultActivity.class);
            a.putExtra("defaultRecipientId", this.u.b);
            startActivity(a);
        } else if (view == this.k) {
            Intent intent = new Intent(this.d, PromotionalOfferDetails.class);
            intent.putExtra("fr.smoney.android.izly.promo", (Parcelable) this.u.J.o.get(0));
            startActivity(intent);
        }
    }

    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.u = (GetContactDetailsData) getArguments().getParcelable("getContactDetailsData");
    }

    public final View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        super.onCreateView(layoutInflater, viewGroup, bundle);
        this.t = layoutInflater.inflate(R.layout.contact_details_pro_premium, viewGroup, false);
        MapsInitializer.initialize(getActivity());
        AppCompatActivity appCompatActivity = this.d;
        View view = this.t;
        ViewPagerTabs viewPagerTabs = (ViewPagerTabs) this.t.findViewById(R.id.tabs);
        ArrayList arrayList = new ArrayList();
        LayoutInflater layoutInflater2 = appCompatActivity.getLayoutInflater();
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.pager);
        this.s = layoutInflater2.inflate(R.layout.contact_details_pro_premium_tab_info, viewPager, false);
        this.r = layoutInflater2.inflate(R.layout.contact_details_pro_premium_tab_historic, viewPager, false);
        arrayList.add(new hc$a(this, getResources().getString(R.string.contact_details_premium_info_header), this.s));
        arrayList.add(new hc$a(this, getResources().getString(R.string.contact_details_premium_historic_header), this.r));
        viewPager.setAdapter(new hc$b(this, arrayList));
        viewPagerTabs.setViewPager(viewPager);
        this.v = this.t.findViewById(R.id.relativeLayoutMapView);
        this.q = (MapView) this.t.findViewById(R.id.mapView);
        this.q.onCreate(bundle);
        return this.t;
    }

    public final void onDestroy() {
        this.q.onDestroy();
        super.onDestroy();
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
    }

    public final void onMapClick(LatLng latLng) {
        o();
    }

    public final boolean onMarkerClick(Marker marker) {
        o();
        return true;
    }

    public final void onPause() {
        this.q.onPause();
        super.onPause();
    }

    public final void onResume() {
        super.onResume();
        this.q.onResume();
    }
}
