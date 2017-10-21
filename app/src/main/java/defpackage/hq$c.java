package defpackage;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.Time;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.Transaction;
import java.util.Currency;

final class hq$c extends ArrayAdapter<Transaction> {
    final /* synthetic */ hq a;

    public hq$c(hq hqVar, Context context) {
        this.a = hqVar;
        super(context, -1);
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hq$d hq_d;
        if (view == null) {
            if (this.a.e == null) {
                this.a.e = this.a.d.getLayoutInflater();
            }
            view = this.a.e.inflate(R.layout.list_item, null);
            hq$d hq_d2 = new hq$d(this.a, view);
            view.setTag(hq_d2);
            hq_d = hq_d2;
        } else {
            hq_d = (hq$d) view.getTag();
        }
        Transaction transaction = (Transaction) getItem(i);
        String symbol = Currency.getInstance(hq_d.l.t.b.j).getSymbol();
        hq_d.c.setOnClickListener(hq_d.l);
        if (transaction.d) {
            hq_d.d.setVisibility(8);
        } else {
            hq_d.d.setVisibility(0);
        }
        if (transaction != null) {
            int i2 = transaction.n;
            Time time = new Time();
            time.set(transaction.l);
            hq_d.a.setText(jk.a(hq_d.l.d, time));
            if (transaction.B) {
                hq_d.a.setVisibility(0);
            } else {
                hq_d.a.setVisibility(8);
            }
            int i3 = -1;
            String str = "";
            switch (transaction.o) {
                case 1:
                    str = "+ ";
                    i3 = hq_d.l.v;
                    break;
                case 2:
                    str = "- ";
                    i3 = hq_d.l.w;
                    break;
            }
            hq_d.f.setText(time.format("%H:%M"));
            hq_d.i.setTextColor(i3);
            switch (i2) {
                case 0:
                case 5:
                    hq_d.e.setText(R.string.list_tv_time_pay_in);
                    hq_d.g.setText(hq_d.l.getString(R.string.list_tv_name_pay_in, new Object[]{jf.a(transaction.q, transaction.r, transaction.p)}));
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                    hq_d.l.c.a.displayImage(jl.a(transaction.p), hq_d.c);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.g), symbol})));
                    break;
                case 1:
                case 4:
                    hq_d.e.setText(R.string.list_tv_time_pay_out);
                    hq_d.g.setText(hq_d.l.getString(R.string.list_tv_name_pay_out, new Object[]{jf.a(transaction.q, transaction.r, transaction.p)}));
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                    hq_d.l.c.a.displayImage(jl.a(transaction.p), hq_d.c);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 2:
                    hq_d.e.setText(R.string.list_tv_time_money_in);
                    hq_d.g.setText(hq_d.l.getString(R.string.list_tv_name_money_in, new Object[]{transaction.p}));
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_money_in);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.g), symbol})));
                    break;
                case 3:
                    hq_d.e.setText(R.string.list_tv_time_money_out);
                    hq_d.g.setText(hq_d.l.getString(R.string.list_tv_name_money_out, new Object[]{transaction.p}));
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_money_out);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 6:
                case 10:
                case 11:
                    hq_d.e.setText(R.string.list_tv_time_refund);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.g.setText(jf.a(transaction.p));
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.g), symbol})));
                    break;
                case 7:
                    hq_d.e.setText(R.string.list_tv_time_ebusiness);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_shopping_cart);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 8:
                    hq_d.e.setText(R.string.list_tv_time_distributor);
                    hq_d.g.setText(hq_d.l.getString(R.string.list_tv_name_distributor, new Object[]{jf.a(transaction.q, transaction.r, transaction.p)}));
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_distrib);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 9:
                    hq_d.g.setText(jf.a(transaction.q, transaction.r, transaction.p));
                    hq_d.e.setText(R.string.list_tv_time_commission);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s TTC", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 15:
                    hq_d.g.setText(jf.a(transaction.q, transaction.r, transaction.p));
                    hq_d.e.setText(R.string.list_tv_time_commission_refund);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s TTC", new Object[]{Double.valueOf(transaction.g), symbol})));
                    break;
                case 17:
                    hq_d.g.setText(jf.a(transaction.q, transaction.r, transaction.p));
                    hq_d.e.setText(transaction.b());
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s TTC", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 18:
                    hq_d.g.setText(jf.a(transaction.q, transaction.r, transaction.p));
                    hq_d.e.setText(R.string.transaction_list_details_pay_in_title);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_placeholder);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s", new Object[]{Double.valueOf(transaction.e), symbol})));
                    break;
                case 19:
                    hq_d.g.setText(jf.a(transaction.q, transaction.r, transaction.p));
                    hq_d.e.setText(R.string.transaction_list_details_pay_in_refund_title);
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.i.setText(str.concat(String.format("%1$.2f%2$s TTC", new Object[]{Double.valueOf(transaction.g), symbol})));
                    break;
                default:
                    hq_d.g.setText("");
                    hq_d.e.setText("");
                    hq_d.c.setImageResource(R.drawable.list_aiv_avatar_smoney);
                    hq_d.i.setText("");
                    break;
            }
            hq_d.b.setTag(Integer.valueOf(i));
            hq_d.j.setVisibility(8);
            i3 = transaction.A.size();
            if (!(transaction.m == null || TextUtils.isEmpty(transaction.m))) {
                i3++;
            }
            if (!(transaction.u == null || TextUtils.isEmpty(transaction.u))) {
                i3++;
            }
            hq_d.h.setVisibility(i3 > 0 ? 0 : 8);
            if (transaction.a()) {
                hq_d.k.setVisibility(0);
            } else {
                hq_d.k.setVisibility(8);
            }
        }
        return view;
    }
}
