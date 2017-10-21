package defpackage;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.data.model.LoginData;

public final class hp$b extends BaseAdapter implements OnItemClickListener {
    public int a = 1;
    final /* synthetic */ hp b;
    private LayoutInflater c;

    public hp$b(hp hpVar, Activity activity) {
        this.b = hpVar;
        this.c = (LayoutInflater) activity.getSystemService("layout_inflater");
    }

    private hp$c a(int i) {
        return (hp$c) this.b.h.get(i);
    }

    public final int getCount() {
        return this.b.h.size();
    }

    public final /* synthetic */ Object getItem(int i) {
        return a(i);
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final int getItemViewType(int i) {
        switch (a(i).d) {
            case 0:
                return 3;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
                return 2;
            default:
                return 1;
        }
    }

    public final View getView(int i, View view, ViewGroup viewGroup) {
        hp$b$a hp_b_a;
        int itemViewType = getItemViewType(i);
        if (view == null) {
            hp$b$a hp_b_a2 = new hp$b$a(this);
            switch (itemViewType) {
                case 0:
                    view = this.c.inflate(R.layout.sliding_menu_item_header, null);
                    hp_b_a2.c = (TextView) view.findViewById(R.id.menu_item_title);
                    break;
                case 1:
                case 2:
                    view = this.c.inflate(R.layout.sliding_menu_item_normal, null);
                    hp_b_a2.c = (TextView) view.findViewById(R.id.menu_item_title);
                    hp_b_a2.b = (ImageView) view.findViewById(R.id.menu_item_aiv);
                    hp_b_a2.d = view.findViewById(R.id.menu_item_fl_badge);
                    hp_b_a2.e = (TextView) view.findViewById(R.id.menu_item_tv_badge_number);
                    break;
                case 3:
                    view = this.c.inflate(R.layout.sliding_menu_item_account, null);
                    hp_b_a2.c = (TextView) view.findViewById(R.id.menu_item_title);
                    hp_b_a2.a = (ImageView) view.findViewById(R.id.menu_item_aiv);
                    break;
            }
            view.setTag(hp_b_a2);
            hp_b_a = hp_b_a2;
        } else {
            hp_b_a = (hp$b$a) view.getTag();
        }
        hp$c a = a(i);
        if (itemViewType == 3) {
            if (this.b.m.b != null) {
                TextView textView = hp_b_a.c;
                LoginData loginData = this.b.m.b;
                StringBuilder stringBuilder = new StringBuilder(loginData.p);
                stringBuilder.append(" ");
                stringBuilder.append(loginData.q);
                textView.setText(stringBuilder.toString());
                hp_b_a.a.setVisibility(0);
                hp_b_a.a.setImageResource(R.drawable.img_profil);
                this.b.c.a(jl.a(this.b.m.b.a), hp_b_a.a);
            }
            if (this.a == -1 || i != this.a) {
                view.setSelected(false);
                view.setBackgroundColor(this.b.getResources().getColor(R.color.izly_blue_account));
            } else {
                view.setSelected(true);
                view.setBackgroundColor(this.b.getResources().getColor(R.color.izly_blue_selected));
            }
        } else {
            hp_b_a.c.setText(a.c);
            if (itemViewType != 0) {
                if (a.a > 0) {
                    hp_b_a.b.setVisibility(0);
                    hp_b_a.b.setImageResource(a.a);
                } else {
                    hp_b_a.b.setVisibility(8);
                }
                if (this.a == -1 || i != this.a) {
                    view.setSelected(false);
                    view.setBackgroundColor(this.b.getResources().getColor(R.color.izly_blue));
                    if (a.a > 0) {
                        hp_b_a.b.setImageResource(a.a);
                    }
                } else {
                    view.setSelected(true);
                    view.setBackgroundColor(this.b.getResources().getColor(R.color.izly_blue_selected));
                    if (a.a > 0) {
                        hp_b_a.b.setImageResource(a.b);
                    }
                }
            }
            if (itemViewType == 1) {
                hp_b_a.d.setVisibility(4);
            }
        }
        return view;
    }

    public final int getViewTypeCount() {
        return 5;
    }

    public final void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = this.b.k.getHeaderViewsCount();
        if (i >= headerViewsCount) {
            headerViewsCount = i - headerViewsCount;
            hp$c a = a(headerViewsCount);
            int itemViewType = getItemViewType(headerViewsCount);
            if (!(itemViewType == 0 || this.b.e == null || this.b.g == a.d)) {
                this.b.e.b(a.d);
            }
            if (itemViewType == 1) {
                this.a = headerViewsCount;
                this.b.g = a.d;
                notifyDataSetChanged();
            }
        } else if (view == this.b.l) {
            hp.h(this.b);
        }
    }
}
