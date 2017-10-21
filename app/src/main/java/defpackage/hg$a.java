package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class hg$a {
    TextView a;
    ImageView b;
    ImageView c;
    final /* synthetic */ hg d;

    public hg$a(hg hgVar, View view) {
        this.d = hgVar;
        this.a = (TextView) view.findViewById(R.id.tv_contact_name);
        this.b = (ImageView) view.findViewById(R.id.maiv_contact_avatar);
        this.c = (ImageView) view.findViewById(R.id.img_contact_blocked);
    }
}
