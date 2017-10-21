package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.CounterFamily;
import java.util.List;

public final class gr extends BaseAdapter {
    private Context a;
    private List<CounterFamily> b;

    public gr(Context context, List<CounterFamily> list) {
        this.a = context;
        this.b = list;
    }

    public final CounterFamily a(int i) {
        return (CounterFamily) this.b.get(i);
    }

    public final int getCount() {
        return this.b.size();
    }

    public final /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.counter_family_cell, viewGroup, false);
        CounterFamily counterFamily = (CounterFamily) this.b.get(i);
        TextView textView = (TextView) inflate.findViewById(R.id.tokenLeft);
        ((TextView) inflate.findViewById(R.id.family)).setText(counterFamily.b);
        textView.setText(counterFamily.c);
        return inflate;
    }
}
