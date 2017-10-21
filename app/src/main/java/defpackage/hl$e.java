package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class hl$e {
    TextView a;
    TextView b;
    TextView c;
    ImageView d;
    View e;
    TextView f;
    LinearLayout g;
    TextView h;
    TextView i;
    TextView j;
    TextView k;
    ImageView l;
    ImageView m;
    ImageView n;
    final /* synthetic */ hl o;

    public hl$e(hl hlVar, View view) {
        this.o = hlVar;
        this.a = (TextView) view.findViewById(R.id.tv_date);
        this.b = (TextView) view.findViewById(R.id.tv_time);
        this.c = (TextView) view.findViewById(R.id.tv_amount);
        this.d = (ImageView) view.findViewById(R.id.aiv_avatar);
        this.e = view.findViewById(R.id.v_unread);
        this.f = (TextView) view.findViewById(R.id.tv_name);
        this.g = (LinearLayout) view.findViewById(R.id.ll_status_get);
        this.h = (TextView) view.findViewById(R.id.tv_status_paid);
        this.i = (TextView) view.findViewById(R.id.tv_status_cancelled);
        this.j = (TextView) view.findViewById(R.id.tv_status_to_be_paid);
        this.k = (TextView) view.findViewById(R.id.tv_status_refused);
        this.l = (ImageView) view.findViewById(R.id.iv_status);
        this.m = (ImageView) view.findViewById(R.id.tv_message);
        this.n = (ImageView) view.findViewById(R.id.tv_attachment);
    }
}
