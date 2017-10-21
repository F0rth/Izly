package defpackage;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class hq$d {
    TextView a;
    FrameLayout b;
    ImageView c;
    View d;
    TextView e;
    TextView f;
    TextView g;
    ImageView h;
    TextView i;
    ImageView j;
    ImageView k;
    final /* synthetic */ hq l;

    public hq$d(hq hqVar, View view) {
        this.l = hqVar;
        this.a = (TextView) view.findViewById(R.id.tv_date);
        this.b = (FrameLayout) view.findViewById(R.id.fl_avatar);
        this.c = (ImageView) view.findViewById(R.id.aiv_avatar);
        this.d = view.findViewById(R.id.v_unread);
        this.e = (TextView) view.findViewById(R.id.tv_operation_label);
        this.f = (TextView) view.findViewById(R.id.tv_time);
        this.g = (TextView) view.findViewById(R.id.tv_name);
        this.h = (ImageView) view.findViewById(R.id.iv_message);
        this.i = (TextView) view.findViewById(R.id.tv_amount);
        this.j = (ImageView) view.findViewById(R.id.iv_status);
        this.k = (ImageView) view.findViewById(R.id.tv_attachment);
    }
}
