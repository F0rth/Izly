package fr.smoney.android.izly.ui.widget;

import android.view.View;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class DetailTwoText$$ViewBinder<T extends DetailTwoText> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mTextLeft = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.left_text, "field 'mTextLeft'"), R.id.left_text, "field 'mTextLeft'");
        t.mTextRight = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.right_text, "field 'mTextRight'"), R.id.right_text, "field 'mTextRight'");
    }

    public void unbind(T t) {
        t.mTextLeft = null;
        t.mTextRight = null;
    }
}
