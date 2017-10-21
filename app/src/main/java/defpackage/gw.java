package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;

public final class gw extends ArrayAdapter<cg> {
    private cg[] a;
    private Context b;
    private int c = R.layout.izly_spinner;

    public gw(Context context, int i, cg[] cgVarArr) {
        super(context, R.layout.izly_spinner, cgVarArr);
        this.b = context;
        this.a = cgVarArr;
    }

    public final int getCount() {
        return this.a.length;
    }

    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        View view2 = getView(i, view, viewGroup);
        view2.findViewById(R.id.chevron).setVisibility(8);
        return view2;
    }

    public final /* bridge */ /* synthetic */ Object getItem(int i) {
        return this.a[i];
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(this.b).inflate(this.c, null);
        }
        cg cgVar = this.a[i];
        ImageView imageView = (ImageView) view.findViewById(R.id.img);
        ((TextView) view.findViewById(2131755575)).setText(cgVar.k);
        if (cgVar.j > 0) {
            imageView.setImageResource(cgVar.j);
            imageView.setVisibility(0);
        }
        return view;
    }
}
