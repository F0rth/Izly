package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Counter;
import java.util.List;

public final class gq extends BaseAdapter {
    private Context a;
    private List<Counter> b;

    public gq(Context context, List<Counter> list) {
        this.a = context;
        this.b = list;
    }

    public final int getCount() {
        return this.b.size();
    }

    public final /* synthetic */ Object getItem(int i) {
        return (Counter) this.b.get(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.counter_detail_cell, viewGroup, false);
        Counter counter = (Counter) this.b.get(i);
        TextView textView = (TextView) inflate.findViewById(R.id.value);
        TextView textView2 = (TextView) inflate.findViewById(R.id.date);
        ((TextView) inflate.findViewById(R.id.label)).setText(counter.c);
        if (counter.b > 0) {
            textView.setText("+" + counter.b);
            textView.setTextColor(this.a.getResources().getColor(R.color.general_tv_green));
        } else {
            textView.setText(counter.b);
        }
        textView2.setText(jk.a(this.a, counter.a, true, true, false, false));
        return inflate;
    }
}
