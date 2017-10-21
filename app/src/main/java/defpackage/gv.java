package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.GoodDealsData;
import java.util.ArrayList;

public final class gv extends BaseAdapter {
    private static ArrayList<GoodDealsData> b;
    private LayoutInflater a;

    public gv(Context context, ArrayList<GoodDealsData> arrayList) {
        b = arrayList;
        this.a = LayoutInflater.from(context);
    }

    public final int getCount() {
        return b.size();
    }

    public final Object getItem(int i) {
        return b.get(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        gv$a gv_a;
        if (view == null) {
            view = this.a.inflate(R.layout.list_view_good_deals, null);
            gv_a = new gv$a();
            gv_a.a = (ImageView) view.findViewById(R.id.good_deals_icon);
            gv_a.b = (TextView) view.findViewById(R.id.good_deals_label_title);
            gv_a.c = (TextView) view.findViewById(R.id.good_deals_label);
            view.setTag(gv_a);
        } else {
            gv_a = (gv$a) view.getTag();
        }
        Picasso.with(view.getContext()).load(((GoodDealsData) b.get(i)).c).into(gv_a.a);
        gv_a.b.setText(((GoodDealsData) b.get(i)).b);
        gv_a.c.setText(((GoodDealsData) b.get(i)).d);
        return view;
    }
}
