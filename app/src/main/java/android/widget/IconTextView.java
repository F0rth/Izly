package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView.BufferType;
import com.joanzapata.android.iconify.Iconify;

public class IconTextView extends TextView {
    public IconTextView(Context context) {
        super(context);
        init();
    }

    public IconTextView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    public IconTextView(Context context, AttributeSet attributeSet, int i) {
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
