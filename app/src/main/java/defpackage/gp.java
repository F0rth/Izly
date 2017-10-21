package defpackage;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class gp {
    TextView a;
    ImageView b;
    TextView c;

    public gp(View view) {
        this.a = (TextView) view.findViewById(R.id.tv_contact_name);
        this.b = (ImageView) view.findViewById(R.id.maiv_contact_avatar);
        this.c = (TextView) view.findViewById(R.id.tv_contact_header);
    }
}
