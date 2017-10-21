package fr.smoney.android.izly.ui.adapter;

import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class IzlyListAdapterWithCheckboxHelper$PlaceHolder$$ViewBinder<T extends PlaceHolder> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.logo = (ImageView) finder.castView((View) finder.findOptionalView(obj, R.id.logo, null), R.id.logo, "field 'logo'");
        t.title = (TextView) finder.castView((View) finder.findRequiredView(obj, 2131755131, "field 'title'"), 2131755131, "field 'title'");
        t.desc = (TextView) finder.castView((View) finder.findOptionalView(obj, R.id.desc, null), R.id.desc, "field 'desc'");
        t.checkbox = (CheckBox) finder.castView((View) finder.findRequiredView(obj, R.id.opt_in_checkbox, "field 'checkbox'"), R.id.opt_in_checkbox, "field 'checkbox'");
    }

    public void unbind(T t) {
        t.logo = null;
        t.title = null;
        t.desc = null;
        t.checkbox = null;
    }
}
