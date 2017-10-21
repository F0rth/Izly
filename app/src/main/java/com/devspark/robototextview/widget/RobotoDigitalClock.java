package com.devspark.robototextview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.DigitalClock;
import com.devspark.robototextview.R;
import com.devspark.robototextview.RobotoTypefaceManager;

public class RobotoDigitalClock extends DigitalClock {
    public RobotoDigitalClock(Context context) {
        super(context);
        onInitTypeface(context, null);
    }

    public RobotoDigitalClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        onInitTypeface(context, attributeSet);
    }

    private void onInitTypeface(Context context, AttributeSet attributeSet) {
        int i = 0;
        if (!isInEditMode()) {
            if (attributeSet != null) {
                TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.RobotoTextView);
                i = obtainStyledAttributes.getInt(R.styleable.RobotoTextView_typeface, 0);
                obtainStyledAttributes.recycle();
            }
            setTypeface(RobotoTypefaceManager.obtaintTypeface(context, i));
        }
    }
}
