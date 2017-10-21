package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class hj$a {
    TextView a;
    TextView b;
    TextView c;
    ImageView d;
    final /* synthetic */ hj e;
    private TextView f;

    public hj$a(hj hjVar, View view) {
        this.e = hjVar;
        this.a = (TextView) view.findViewById(R.id.tv_pro_item_name);
        this.f = (TextView) view.findViewById(R.id.tv_pro_item_type);
        this.b = (TextView) view.findViewById(R.id.tv_pro_item_actvity);
        this.c = (TextView) view.findViewById(R.id.tv_pro_item_distance);
        this.d = (ImageView) view.findViewById(R.id.iv_pro_item_avatar);
    }
}
