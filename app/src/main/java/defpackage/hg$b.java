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

final class hg$b extends ArrayAdapter<Bookmark> {
    final /* synthetic */ hg a;

    public hg$b(hg hgVar, Context context) {
        this.a = hgVar;
        super(context, -1);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hg$a hg_a;
        if (view == null) {
            view = this.a.h.inflate(R.layout.contacts_item, null);
            hg$a hg_a2 = new hg$a(this.a, view);
            view.setTag(hg_a2);
            hg_a = hg_a2;
        } else {
            hg_a = (hg$a) view.getTag();
        }
        Bookmark bookmark = (Bookmark) getItem(i);
        hg_a.a.setText(jf.a(bookmark.c, bookmark.b, bookmark.a));
        hg_a.b.setVisibility(0);
        hg_a.b.setImageResource(R.drawable.list_aiv_avatar_placeholder);
        hg_a.d.c.a.displayImage(jl.a(bookmark.a), hg_a.b);
        if (bookmark.d) {
            if (VERSION.SDK_INT < 11) {
                hg_a.c.setAlpha(EACTags.SECURE_MESSAGING_TEMPLATE);
            } else {
                hg_a.b.setAlpha(125.0f);
            }
            hg_a.c.setVisibility(0);
        } else {
            if (VERSION.SDK_INT < 11) {
                hg_a.c.setAlpha(CipherSuite.TLS_EMPTY_RENEGOTIATION_INFO_SCSV);
            } else {
                hg_a.b.setAlpha(255.0f);
            }
            hg_a.c.setVisibility(8);
        }
        return view;
    }
}
