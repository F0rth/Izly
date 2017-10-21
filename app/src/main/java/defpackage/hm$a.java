package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class hm$a {
    ImageView a;
    ImageView b;
    TextView c;
    final /* synthetic */ hm d;

    public hm$a(hm hmVar, View view) {
        this.d = hmVar;
        this.a = (ImageView) view.findViewById(R.id.iv_recipient_item_avatar);
        this.b = (ImageView) view.findViewById(R.id.iv_recipient_status);
        this.c = (TextView) view.findViewById(R.id.tv_recipient_item_name);
    }
}
