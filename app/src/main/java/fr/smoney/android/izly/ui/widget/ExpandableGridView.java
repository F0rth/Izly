package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.GridView;

import org.spongycastle.asn1.cmp.PKIFailureInfo;

public class ExpandableGridView extends GridView {
    boolean a;

    public ExpandableGridView(Context context) {
        this(context, null, 0);
    }

    public ExpandableGridView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ExpandableGridView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = false;
    }

    public void onMeasure(int i, int i2) {
        if (this.a) {
            super.onMeasure(i, MeasureSpec.makeMeasureSpec(536870911, PKIFailureInfo.systemUnavail));
            getLayoutParams().height = getMeasuredHeight();
            return;
        }
        super.onMeasure(i, i2);
    }

    public void setExpanded(boolean z) {
        this.a = z;
    }
}
