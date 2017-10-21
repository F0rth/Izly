package fr.smoney.android.izly.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.thewingitapp.thirdparties.wingitlib.model.WGCity;

import fr.smoney.android.izly.R;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class CityAdapter extends Adapter<ViewHolder> implements Filterable {
    private static List<WGCity> c = new ArrayList();
    private static List<WGCity> d = new ArrayList();
    public b a;
    Filter b = new Filter(this) {
        final /* synthetic */ CityAdapter a;

        {
            this.a = r1;
        }

        protected final FilterResults performFiltering(CharSequence charSequence) {
            String toLowerCase = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();
            int size = CityAdapter.c.size();
            Object arrayList = new ArrayList(size);
            Object obj = toLowerCase;
            int i = 0;
            while (i < size) {
                CharSequence trim = Normalizer.normalize(obj, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").trim();
                if (Normalizer.normalize(((WGCity) CityAdapter.c.get(i)).getName(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").trim().toLowerCase().contains(trim)) {
                    arrayList.add(CityAdapter.c.get(i));
                }
                i++;
                CharSequence charSequence2 = trim;
            }
            Collections.sort(arrayList, new a(obj));
            filterResults.values = arrayList;
            filterResults.count = arrayList.size();
            return filterResults;
        }

        protected final void publishResults(CharSequence charSequence, FilterResults filterResults) {
            CityAdapter.d = (ArrayList) filterResults.values;
            this.a.notifyDataSetChanged();
        }
    };

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Bind({2131755605})
        TextView mCityTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public static final class a implements Comparator<WGCity> {
        String a;

        public a(String str) {
            this.a = str.toLowerCase();
        }

        public final /* synthetic */ int compare(Object obj, Object obj2) {
            WGCity wGCity = (WGCity) obj2;
            String toLowerCase = Normalizer.normalize(((WGCity) obj).getName(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").trim().toLowerCase();
            String toLowerCase2 = Normalizer.normalize(wGCity.getName(), Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "").trim().toLowerCase();
            return (!toLowerCase.startsWith(this.a) || toLowerCase2.startsWith(this.a)) ? (!toLowerCase2.startsWith(this.a) || toLowerCase.startsWith(this.a)) ? toLowerCase.compareTo(toLowerCase2) : 1 : -1;
        }
    }

    public interface b {
        void a(WGCity wGCity);
    }

    public CityAdapter(List<WGCity> list) {
        c = list;
        d = list;
    }

    @NonNull
    public final Filter getFilter() {
        return this.b;
    }

    public final int getItemCount() {
        return d.size();
    }

    public final /* synthetic */ void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
        ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final WGCity wGCity = (WGCity) d.get(i);
        viewHolder2.mCityTextView.setText(wGCity.getLocalizedName());
        viewHolder2.itemView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CityAdapter b;

            public final void onClick(View view) {
                if (this.b.a != null) {
                    this.b.a.a(wGCity);
                }
            }
        });
    }

    public final /* synthetic */ android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_city, viewGroup, false));
    }
}
