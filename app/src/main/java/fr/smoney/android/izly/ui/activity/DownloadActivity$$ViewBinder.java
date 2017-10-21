package fr.smoney.android.izly.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class DownloadActivity$$ViewBinder<T extends DownloadActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        t.mContentImage = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.content_image, "field 'mContentImage'"), R.id.content_image, "field 'mContentImage'");
        t.mDetailedTextView = (TextView) finder.castView((View) finder.findRequiredView(obj, R.id.download_WINGiT_detailed_text, "field 'mDetailedTextView'"), R.id.download_WINGiT_detailed_text, "field 'mDetailedTextView'");
    }

    public void unbind(T t) {
        t.mToolbar = null;
        t.mContentImage = null;
        t.mDetailedTextView = null;
    }
}
