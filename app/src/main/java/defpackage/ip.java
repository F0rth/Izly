package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import fr.smoney.android.izly.R;
import java.lang.ref.WeakReference;
import java.util.List;

public final class ip extends iq {
    private HorizontalScrollView l;
    private Animation m;
    private ViewGroup n;
    private ViewGroup o;
    private List<io> p;
    private OnClickListener q = new ip$2(this);

    public ip(Context context) {
        super(context);
        this.m = AnimationUtils.loadAnimation(context, R.anim.gd_rack);
        this.m.setInterpolator(new ip$1(this));
        setContentView(LayoutInflater.from(this.b).inflate(R.layout.gd_quick_action_bar, null));
        View contentView = getContentView();
        this.n = (ViewGroup) contentView.findViewById(R.id.gdi_rack);
        this.o = (ViewGroup) contentView.findViewById(R.id.gdi_quick_action_items);
        this.l = (HorizontalScrollView) contentView.findViewById(R.id.gdi_scroll);
    }

    protected final void a() {
        super.a();
        this.o.removeAllViews();
    }

    protected final void a(Rect rect, View view) {
        view.setLayoutParams(new LayoutParams(-2, -2));
        view.measure(MeasureSpec.makeMeasureSpec(this.h, 1073741824), -2);
        int measuredHeight = view.getMeasuredHeight();
        int i = this.d;
        boolean z = rect.top > this.g - rect.bottom;
        this.e = z ? (rect.top - measuredHeight) + i : rect.bottom - i;
        this.f = z;
        this.a |= 2;
    }

    public final void a(View view) {
        super.a(view);
        this.l.scrollTo(0, 0);
        this.n.startAnimation(this.m);
    }

    protected final void a(List<io> list) {
        this.p = list;
        LayoutInflater from = LayoutInflater.from(this.b);
        for (io ioVar : list) {
            TextView textView = (TextView) from.inflate(R.layout.gd_quick_action_bar_item, this.o, false);
            textView.setText(ioVar.b);
            textView.setCompoundDrawablesWithIntrinsicBounds(null, ioVar.a, null, null);
            textView.setOnClickListener(this.q);
            this.o.addView(textView);
            ioVar.c = new WeakReference(textView);
        }
    }
}
