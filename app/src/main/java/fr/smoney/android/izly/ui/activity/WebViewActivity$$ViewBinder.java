package fr.smoney.android.izly.ui.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class WebViewActivity$$ViewBinder<T extends WebViewActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.mToolbar = (Toolbar) finder.castView((View) finder.findRequiredView(obj, R.id.toolbar, "field 'mToolbar'"), R.id.toolbar, "field 'mToolbar'");
        t.mWebView = (WebView) finder.castView((View) finder.findRequiredView(obj, R.id.web_view, "field 'mWebView'"), R.id.web_view, "field 'mWebView'");
        t.mLoader = (View) finder.findRequiredView(obj, R.id.loader, "field 'mLoader'");
    }

    public void unbind(T t) {
        t.mToolbar = null;
        t.mWebView = null;
        t.mLoader = null;
    }
}
