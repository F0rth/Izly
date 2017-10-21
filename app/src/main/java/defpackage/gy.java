package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.IconTextView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Support;
import java.util.List;

public final class gy extends BaseAdapter {
    private Context a;
    private List<Support> b;

    public gy(Context context, List<Support> list) {
        this.a = context;
        this.b = list;
    }

    public final Support a(int i) {
        return (Support) this.b.get(i);
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
        View inflate = LayoutInflater.from(this.a).inflate(R.layout.support_cell, viewGroup, false);
        Support support = (Support) this.b.get(i);
        TextView textView = (TextView) inflate.findViewById(R.id.supportValue);
        IconTextView iconTextView = (IconTextView) inflate.findViewById(R.id.opposition_logo);
        ((TextView) inflate.findViewById(R.id.supportName)).setText(support.b.d);
        if (!support.c()) {
            switch (gy$1.a[support.b.ordinal()]) {
                case 1:
                    textView.setText(support.d);
                    break;
                case 2:
                    textView.setText(this.a.getString(R.string.expiration_label_format, new Object[]{support.a()}));
                    break;
                default:
                    break;
            }
        }
        textView.setText(this.a.getString(R.string.opposed_since, new Object[]{support.b()}));
        iconTextView.setVisibility(support.c() ? 0 : 8);
        return inflate;
    }
}
