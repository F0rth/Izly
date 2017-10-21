package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

public class ListItemButton extends Button {
    public ListItemButton(Context context) {
        super(context);
    }

    public ListItemButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ListItemButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public void setPressed(boolean z) {
        if (!z || !(getParent() instanceof View) || !((View) getParent()).isPressed()) {
            super.setPressed(z);
        }
    }
}
