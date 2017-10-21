package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.ui.fragment.ServicesFragment.a;
import java.util.ArrayList;

public final class gx extends BaseAdapter {
    private static ArrayList<a> c;
    private Integer[] a = new Integer[]{Integer.valueOf(R.drawable.banner_pot_commun_light), Integer.valueOf(R.drawable.banner_synintra_light), Integer.valueOf(R.drawable.banner_money_friends_light), Integer.valueOf(R.drawable.banner_wingit_light)};
    private LayoutInflater b;

    public gx(Context context, ArrayList<a> arrayList) {
        c = arrayList;
        this.b = LayoutInflater.from(context);
    }

    public final int getCount() {
        return c.size();
    }

    public final Object getItem(int i) {
        return c.get(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        gx$a gx_a;
        if (view == null) {
            view = this.b.inflate(R.layout.list_view_services, null);
            gx_a = new gx$a();
            gx_a.a = (ImageView) view.findViewById(R.id.services_icon);
            gx_a.b = (TextView) view.findViewById(R.id.services_label_title);
            gx_a.c = (TextView) view.findViewById(R.id.services_label);
            gx_a.d = (TextView) view.findViewById(R.id.services_label_bis);
            view.setTag(gx_a);
        } else {
            gx_a = (gx$a) view.getTag();
        }
        gx_a.a.setImageResource(this.a[i].intValue());
        gx_a.b.setText(((a) c.get(i)).a);
        gx_a.c.setText(((a) c.get(i)).b);
        gx_a.d.setText(((a) c.get(i)).c);
        return view;
    }
}
