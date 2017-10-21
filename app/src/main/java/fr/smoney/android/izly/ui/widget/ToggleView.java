package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import fr.smoney.android.izly.R;

public class ToggleView extends RelativeLayout {
    private Context a;
    private CharSequence b;
    private TextView c;
    private ImageView d;
    private int e;
    private boolean f;

    public ToggleView(Context context) {
        super(context);
        this.a = context;
        a(0);
    }

    public ToggleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = context;
        a(0);
    }

    public ToggleView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = context;
        a(0);
    }

    public ToggleView(Context context, CharSequence charSequence, int i, int i2) {
        super(context);
        this.a = context;
        this.b = charSequence;
        this.e = i;
        a(i2);
    }

    private void a(int i) {
        LayoutInflater from = LayoutInflater.from(this.a);
        View inflate = i != 0 ? from.inflate(i, this, false) : from.inflate(R.layout.toggle_view, this, false);
        addView(inflate);
        this.c = (TextView) inflate.findViewById(R.id.toggle_name);
        this.d = (ImageView) inflate.findViewById(R.id.toggle_icon);
        if (this.b != null) {
            this.c.setText(this.b);
        }
        if (this.e != 0) {
            this.d.setImageResource(this.e);
        }
    }

    public void setChecked(boolean z) {
        setSelected(z);
        this.c.setSelected(z);
        this.f = z;
    }
}
