package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

public class ListItemImageView extends ImageView {
    public ListItemImageView(Context context) {
        super(context);
    }

    public ListItemImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ListItemImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setPressed(boolean z) {
        if (!z || !(getParent() instanceof View) || !((View) getParent()).isPressed()) {
            super.setPressed(z);
        }
    }
}
