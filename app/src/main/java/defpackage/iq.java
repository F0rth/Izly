package defpackage;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.WindowManager;
import android.widget.PopupWindow;
import fr.smoney.android.izly.R;
import java.util.ArrayList;
import java.util.List;

public abstract class iq extends PopupWindow {
    int a;
    Context b;
    boolean c;
    int d;
    int e;
    boolean f;
    int g;
    int h;
    public boolean i;
    public iq$a j;
    public ArrayList<io> k = new ArrayList();
    private final int[] l = new int[2];
    private final Rect m = new Rect();

    public iq(Context context) {
        super(context);
        this.b = context;
        this.c = true;
        this.d = this.b.getResources().getDimensionPixelSize(R.dimen.gd_arrow_offset);
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setWidth(-2);
        setHeight(-2);
        WindowManager windowManager = (WindowManager) this.b.getSystemService("window");
        this.h = windowManager.getDefaultDisplay().getWidth();
        this.g = windowManager.getDefaultDisplay().getHeight();
    }

    protected void a() {
    }

    protected abstract void a(Rect rect, View view);

    public void a(View view) {
        View contentView = getContentView();
        if (contentView == null) {
            throw new IllegalStateException("You need to set the content view using the setContentView method");
        }
        setBackgroundDrawable(new ColorDrawable(0));
        int[] iArr = this.l;
        view.getLocationOnScreen(iArr);
        this.m.set(iArr[0], iArr[1], iArr[0] + view.getWidth(), iArr[1] + view.getHeight());
        if (this.i) {
            if (!this.k.isEmpty()) {
                a();
            }
            a(this.k);
        }
        a(this.m, contentView);
        if ((this.a & 2) != 2) {
            throw new IllegalStateException("onMeasureAndLayout() did not set the widget specification by calling setWidgetSpecs()");
        }
        View contentView2 = getContentView();
        int i = this.f ? R.id.gdi_arrow_down : R.id.gdi_arrow_up;
        View findViewById = contentView2.findViewById(i);
        View findViewById2 = contentView2.findViewById(R.id.gdi_arrow_up);
        View findViewById3 = contentView2.findViewById(R.id.gdi_arrow_down);
        if (i == R.id.gdi_arrow_up) {
            findViewById2.setVisibility(0);
            findViewById3.setVisibility(4);
        } else {
            findViewById2.setVisibility(4);
            findViewById3.setVisibility(0);
        }
        ((MarginLayoutParams) findViewById.getLayoutParams()).leftMargin = this.m.centerX() - (findViewById.getMeasuredWidth() / 2);
        i = this.h;
        boolean z = this.f;
        int centerX = this.m.centerX();
        if (centerX <= i / 4) {
            setAnimationStyle(z ? R.style.GreenDroid_Animation_PopUp_Left : R.style.GreenDroid_Animation_PopDown_Left);
        } else if (centerX >= (i * 3) / 4) {
            setAnimationStyle(z ? R.style.GreenDroid_Animation_PopUp_Right : R.style.GreenDroid_Animation_PopDown_Right);
        } else {
            setAnimationStyle(z ? R.style.GreenDroid_Animation_PopUp_Center : R.style.GreenDroid_Animation_PopDown_Center);
        }
        showAtLocation(view, 0, 0, this.e);
    }

    protected abstract void a(List<io> list);
}
