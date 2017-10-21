package defpackage;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Bookmark;
import org.spongycastle.asn1.eac.EACTags;
import org.spongycastle.crypto.tls.CipherSuite;

final class he$d extends ArrayAdapter<Bookmark> {
    final /* synthetic */ he a;

    public he$d(he heVar, Context context) {
        this.a = heVar;
        super(context, -1);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        he$c he_c;
        if (view == null) {
            view = this.a.h.inflate(R.layout.contacts_item, null);
            he$c he_c2 = new he$c(this.a, view);
            view.setTag(he_c2);
            he_c = he_c2;
        } else {
            he_c = (he$c) view.getTag();
        }
        Bookmark bookmark = (Bookmark) getItem(i);
        he_c.a.setText(jf.a(bookmark.c, bookmark.b, bookmark.a));
        he_c.b.setVisibility(0);
        he_c.b.setImageResource(R.drawable.list_aiv_avatar_placeholder);
        he_c.d.c.a.displayImage(jl.a(bookmark.a), he_c.b);
        if (bookmark.d) {
            if (VERSION.SDK_INT < 11) {
                he_c.c.setAlpha(EACTags.SECURE_MESSAGING_TEMPLATE);
            } else {
                he_c.b.setAlpha(125.0f);
            }
            he_c.c.setVisibility(0);
        } else {
            if (VERSION.SDK_INT < 11) {
                he_c.c.setAlpha(CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            } else {
                he_c.b.setAlpha(255.0f);
            }
            he_c.c.setVisibility(8);
        }
        return view;
    }
}
