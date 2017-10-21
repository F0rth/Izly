package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView.BufferType;
import com.joanzapata.android.iconify.Iconify;

public class IconButton extends Button {
    public IconButton(Context context) {
        super(context);
        init();
    }

    public IconButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public IconButton(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init();
    }

    private void init() {
        if (isInEditMode()) {
            setText(getText());
            return;
        }
        Iconify.addIcons(this);
    }

    public void setText(CharSequence charSequence, BufferType bufferType) {
        super.setText(Iconify.compute(charSequence), bufferType);
    }
}
