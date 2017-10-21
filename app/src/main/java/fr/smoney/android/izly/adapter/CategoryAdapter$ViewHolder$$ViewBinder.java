package fr.smoney.android.izly.adapter;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.SwitchCompat;
import android.view.View;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;
import fr.smoney.android.izly.adapter.CategoryAdapter.ViewHolder;

public class CategoryAdapter$ViewHolder$$ViewBinder<T extends ViewHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mCategoryCheckBox = (SwitchCompat) finder.castView((View) finder.findRequiredView(obj, R.id.category_swicth, "field 'mCategoryCheckBox'"), R.id.category_swicth, "field 'mCategoryCheckBox'");
        t.mCategoryTextView = (AppCompatTextView) finder.castView((View) finder.findRequiredView(obj, R.id.category_name, "field 'mCategoryTextView'"), R.id.category_name, "field 'mCategoryTextView'");
    }

    public void unbind(T t) {
        t.mCategoryCheckBox = null;
        t.mCategoryTextView = null;
    }
}
