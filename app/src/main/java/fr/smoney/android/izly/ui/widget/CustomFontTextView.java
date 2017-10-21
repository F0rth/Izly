package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import fr.smoney.android.izly.R;

public class CustomFontTextView extends TextView {
    public CustomFontTextView(Context context) {
        this(context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CustomFontTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CustomFontTextView);
        String string = obtainStyledAttributes.getString(0);
        if (!isInEditMode()) {
            if (string != null) {
                Typeface createFromAsset = Typeface.createFromAsset(context.getAssets(), string);
                if (createFromAsset != null) {
                    setTypeface(createFromAsset);
                }
            } else {
                Log.w("View", "font path is null");
            }
        }
        obtainStyledAttributes.recycle();
    }
}
