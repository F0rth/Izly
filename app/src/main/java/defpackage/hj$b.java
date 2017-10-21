package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.NearPro;

final class hj$b extends ArrayAdapter<NearPro> {
    final /* synthetic */ hj a;
    private LayoutInflater b;

    public hj$b(hj hjVar, Context context) {
        this.a = hjVar;
        super(context, -1);
        this.b = hjVar.getActivity().getLayoutInflater();
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hj$a hj_a;
        if (view == null) {
            view = this.b.inflate(R.layout.near_pro_view_item, null);
            hj$a hj_a2 = new hj$a(this.a, view);
            view.setTag(hj_a2);
            hj_a = hj_a2;
        } else {
            hj_a = (hj$a) view.getTag();
        }
        NearPro nearPro = (NearPro) getItem(i);
        StringBuilder stringBuilder = new StringBuilder();
        if (nearPro.a != null) {
            stringBuilder.append(nearPro.a);
        } else {
            stringBuilder.append(hj_a.e.getString(R.string.near_pro_display_name_unkonwn));
        }
        hj_a.a.setText(stringBuilder.toString());
        cn a = cn.a(nearPro.e);
        if (a != null) {
            hj_a.b.setText(a.o);
        } else {
            hj_a.b.setText(hj_a.e.getString(R.string.near_pro_activity_unknown));
        }
        hj_a.c.setText(nearPro.a(hj_a.e.getActivity()));
        hj_a.d.setImageResource(R.drawable.list_aiv_avatar_pro);
        hj_a.e.c.a.displayImage(jl.a(nearPro.b), hj_a.d);
        if (hj_a.e.m == 0) {
            if (nearPro.c) {
                hj_a.c.setVisibility(8);
            } else {
                hj_a.c.setVisibility(0);
            }
        }
        return view;
    }
}
