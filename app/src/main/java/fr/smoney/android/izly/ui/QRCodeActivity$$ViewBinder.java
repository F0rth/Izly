package fr.smoney.android.izly.ui;

import android.view.View;
import android.widget.ImageView;

import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;
import fr.smoney.android.izly.R;

public class QRCodeActivity$$ViewBinder<T extends QRCodeActivity> implements ViewBinder<T> {
    public void bind(Finder finder, T t, Object obj) {
        t.qrImage = (ImageView) finder.castView((View) finder.findRequiredView(obj, R.id.qrImage, "field 'qrImage'"), R.id.qrImage, "field 'qrImage'");
    }

    public void unbind(T t) {
        t.qrImage = null;
    }
}
