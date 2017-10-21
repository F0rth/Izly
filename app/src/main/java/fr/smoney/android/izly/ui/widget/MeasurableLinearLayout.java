package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import java.lang.ref.WeakReference;

public class MeasurableLinearLayout extends LinearLayout {
    private WeakReference<a> a;

    public interface a {
    }

    public MeasurableLinearLayout(Context context) {
        this(context, null);
    }

    public MeasurableLinearLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        if (this.a != null && ((a) this.a.get()) != null) {
            getWidth();
            getHeight();
        }
    }

    public void setOnMeasuredListener(a aVar) {
        this.a = new WeakReference(aVar);
    }
}
