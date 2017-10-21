package fr.smoney.android.izly.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.thewingitapp.thirdparties.wingitlib.model.WGCategory;

import fr.smoney.android.izly.R;

import java.util.ArrayList;
import java.util.List;

public final class CategoryAdapter extends Adapter<ViewHolder> {
    public List<WGCategory> a;
    private List<WGCategory> b;

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        @Bind({2131755604})
        SwitchCompat mCategoryCheckBox;
        @Bind({2131755603})
        AppCompatTextView mCategoryTextView;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public CategoryAdapter(List<WGCategory> list, List<WGCategory> list2) {
        this.b = list;
        if (list2 == null || list2.size() == 0) {
            this.a = new ArrayList(this.b);
        } else {
            this.a = list2;
        }
    }

    public final int getItemCount() {
        return this.b.size();
    }

    public final long getItemId(int i) {
        return (long) i;
    }

    public final /* synthetic */ void onBindViewHolder(android.support.v7.widget.RecyclerView.ViewHolder viewHolder, int i) {
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final WGCategory wGCategory = (WGCategory) this.b.get(i);
        viewHolder2.mCategoryTextView.setText(wGCategory.getLocalizedName());
        if (this.a == null || !this.a.contains(wGCategory)) {
            viewHolder2.mCategoryCheckBox.setChecked(false);
        } else {
            viewHolder2.mCategoryCheckBox.setChecked(true);
        }
        viewHolder2.itemView.setOnClickListener(new OnClickListener(this) {
            final /* synthetic */ CategoryAdapter c;

            public final void onClick(View view) {
                if (viewHolder2.mCategoryCheckBox.isChecked()) {
                    this.c.a.remove(wGCategory);
                } else {
                    this.c.a.add(wGCategory);
                }
                this.c.notifyDataSetChanged();
            }
        });
    }

    public final /* synthetic */ android.support.v7.widget.RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_category, viewGroup, false));
    }
}
