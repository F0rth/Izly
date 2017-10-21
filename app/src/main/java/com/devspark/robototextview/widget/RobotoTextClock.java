package com.devspark.robototextview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextClock;
import com.devspark.robototextview.R;
import com.devspark.robototextview.RobotoTypefaceManager;

public class RobotoTextClock extends TextClock {
    public RobotoTextClock(Context context) {
        super(context);
        onInitTypeface(context, null, 0);
    }

    public RobotoTextClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        onInitTypeface(context, attributeSet, 0);
    }

    public RobotoTextClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        onInitTypeface(context, attributeSet, i);
    }

    private void onInitTypeface(Context context, AttributeSet attributeSet, int i) {
        int i2 = 0;
        if (!isInEditMode()) {
            if (attributeSet != null) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RobotoTextView, i, 0);
                i2 = obtainStyledAttributes.getInt(R.styleable.RobotoTextView_typeface, 0);
                obtainStyledAttributes.recycle();
            }
            setTypeface(RobotoTypefaceManager.obtaintTypeface(context, i2));
        }
    }
}
