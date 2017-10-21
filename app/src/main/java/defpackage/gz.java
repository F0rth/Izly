package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.smoney.android.izly.R;

public final class gz extends ArrayAdapter<ck> {
    private ck[] a;
    private Context b;
    private int c = R.layout.izly_spinner;

    public gz(Context context, int i, ck[] ckVarArr) {
        super(context, R.layout.izly_spinner, ckVarArr);
        this.b = context;
        this.a = ckVarArr;
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
        ((TextView) view.findViewById(2131755575)).setText(this.a[i].e);
        return view;
    }
}
