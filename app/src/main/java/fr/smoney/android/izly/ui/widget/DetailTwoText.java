package fr.smoney.android.izly.ui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import fr.smoney.android.izly.R;

public class DetailTwoText extends LinearLayout {
    private String a;
    private String b;
    private Context c;
    @Bind({2131755507})
    TextView mTextLeft;
    @Bind({2131755508})
    TextView mTextRight;

    public DetailTwoText(Context context) {
        super(context);
        a(context, null);
    }

    public DetailTwoText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a(context, attributeSet);
    }

    @TargetApi(11)
    public DetailTwoText(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a(context, attributeSet);
    }

    private void a(Context context, AttributeSet attributeSet) {
        this.c = context;
        View inflate = LayoutInflater.from(context).inflate(R.layout.detail_two_text_view, this, false);
        addView(inflate);
        ButterKnife.bind(this, inflate);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R.styleable.two_text_component, 0, 0);
        this.a = obtainStyledAttributes.getString(0);
        this.b = obtainStyledAttributes.getString(1);
        this.mTextLeft.setText(this.a);
        this.mTextRight.setText(this.b);
        obtainStyledAttributes.recycle();
    }

    public void setLeftText(int i) {
        this.a = this.c.getString(i);
        this.mTextLeft.setText(i);
    }

    public void setLeftText(String str) {
        this.a = str;
        this.mTextLeft.setText(str);
    }

    public void setRightText(int i) {
        this.b = this.c.getString(i);
        this.mTextRight.setText(i);
    }

    public void setRightText(String str) {
        this.b = str;
        this.mTextRight.setText(str);
    }
}
