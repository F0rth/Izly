package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.nineoldandroids.view.animation.AnimatorProxy;

public class HandWrittenTextView extends TextView {
    public HandWrittenTextView(Context context) {
        super(context);
        a(context);
    }

    public HandWrittenTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context);
    }

    public HandWrittenTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context);
    }

    private void a(Context context) {
        if (!isInEditMode()) {
            setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/ardleyshandregular.ttf"));
        }
        AnimatorProxy.wrap(this).setRotation(-4.0f);
    }
}
