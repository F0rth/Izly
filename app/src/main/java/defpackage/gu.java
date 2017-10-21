package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.CrousData;
import java.util.List;

public final class gu extends ArrayAdapter<CrousData> {
    public gu(Context context, List<CrousData> list) {
        super(context, R.layout.izly_spinner, list);
    }

    private View a(int i, View view, ViewGroup viewGroup, int i2) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(i2, viewGroup, false);
        }
        ((TextView) view.findViewById(2131755575)).setText(((CrousData) getItem(i)).b);
        return view;
    }

    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return a(i, view, viewGroup, R.layout.spinner_item_crous);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        return a(i, view, viewGroup, R.layout.izly_spinner);
    }
}
