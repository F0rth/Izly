package defpackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.MoneyInCbCb;

public final class go extends ArrayAdapter<MoneyInCbCb> {
    private LayoutInflater a;

    public go(Context context) {
        super(context, R.layout.izly_spinner);
        this.a = (LayoutInflater) context.getSystemService("layout_inflater");
    }

    public final View getDropDownView(int i, View view, ViewGroup viewGroup) {
        gn gnVar;
        if (view == null) {
            view = this.a.inflate(R.layout.money_in_cb_spinner_card_dropdown_item, null);
            gn gnVar2 = new gn(view);
            view.setTag(gnVar2);
            gnVar = gnVar2;
        } else {
            gnVar = (gn) view.getTag();
        }
        gnVar.a.setText(((MoneyInCbCb) getItem(i)).b);
        return view;
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = this.a.inflate(R.layout.izly_spinner, null);
        }
        MoneyInCbCb moneyInCbCb = (MoneyInCbCb) getItem(i);
        if (moneyInCbCb != null) {
            ((TextView) view.findViewById(2131755575)).setText(moneyInCbCb.b);
        }
        return view;
    }
}
