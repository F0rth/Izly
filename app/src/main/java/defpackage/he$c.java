package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class he$c {
    TextView a;
    ImageView b;
    ImageView c;
    final /* synthetic */ he d;

    public he$c(he heVar, View view) {
        this.d = heVar;
        this.a = (TextView) view.findViewById(R.id.tv_contact_name);
        this.b = (ImageView) view.findViewById(R.id.maiv_contact_avatar);
        this.c = (ImageView) view.findViewById(R.id.img_contact_blocked);
    }
}
