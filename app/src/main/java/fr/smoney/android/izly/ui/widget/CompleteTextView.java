package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import fr.smoney.android.izly.R;

public class CompleteTextView extends TextView {
    private StringBuilder a;
    private String b;

    public CompleteTextView(Context context) {
        this(context, null);
    }

    public CompleteTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompleteTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new StringBuilder();
        this.b = ".";
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CompleteTextView);
            this.b = obtainStyledAttributes.getString(0);
            obtainStyledAttributes.recycle();
        }
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        int i5 = 0;
        super.onLayout(z, i, i2, i3, i4);
        if (z && this.b != null) {
            float[] fArr = new float[1];
            getPaint().getTextWidths(this.b, fArr);
            int round = Math.round(((float) getWidth()) / fArr[0]);
            this.a.setLength(0);
            while (i5 < round) {
                this.a.append(this.b);
                i5++;
            }
            setText(this.a.toString());
        }
    }
}
