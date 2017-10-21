package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.AddressValues.a;

public final class gt extends ArrayAdapter<a> {
    private LayoutInflater a;
    private Context b;

    public gt(Context context) {
        super(context, 17367048);
        this.b = context;
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        CheckedTextView checkedTextView;
        if (view == null) {
            View inflate = this.a.inflate(R.layout.country_spinner_item, null);
            CheckedTextView checkedTextView2 = (CheckedTextView) inflate;
            inflate.setTag(checkedTextView2);
            view = inflate;
            checkedTextView = checkedTextView2;
        } else {
            checkedTextView = (CheckedTextView) view.getTag();
        }
        checkedTextView.setText(this.b.getString(((a) getItem(i)).G));
        return view;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        View inflate = view == null ? this.a.inflate(17367048, null) : view;
        a aVar = (a) getItem(i);
        if (aVar != null) {
            ((TextView) inflate).setText(this.b.getString(aVar.G));
        }
        return inflate;
    }
}
