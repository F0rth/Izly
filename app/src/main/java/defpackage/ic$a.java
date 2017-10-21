package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

final class ic$a extends BaseAdapter {
    final /* synthetic */ ic a;
    private LayoutInflater b;

    public ic$a(ic icVar, Context context) {
        this.a = icVar;
        this.b = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final int getCount() {
        return cn.a();
    }

    public final /* synthetic */ Object getItem(int i) {
        return cn.b(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        ic$a$a ic_a_a;
        if (view == null) {
            ic$a$a ic_a_a2 = new ic$a$a(this);
            view = this.b.inflate(R.layout.listitem_nearpro_category, null);
            ic_a_a2.a = (ImageView) view.findViewById(R.id.nearpro_cat_picto);
            ic_a_a2.b = (TextView) view.findViewById(R.id.nearpro_cat_text);
            ic_a_a2.c = (ImageView) view.findViewById(R.id.nearpro_cat_picto_check);
            view.setTag(ic_a_a2);
            ic_a_a = ic_a_a2;
        } else {
            ic_a_a = (ic$a$a) view.getTag();
        }
        ic_a_a.b.setText(cn.b(i).o);
        int i2 = cn.b(i).q;
        ic_a_a.a.setVisibility(0);
        ic_a_a.a.setImageResource(i2);
        ic_a_a.c.setVisibility(i == this.a.c ? 0 : 4);
        return view;
    }
}
