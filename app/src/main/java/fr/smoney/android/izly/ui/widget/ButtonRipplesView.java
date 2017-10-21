package fr.smoney.android.izly.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RadialGradient;
import android.graphics.Region.Op;
import android.graphics.Shader.TileMode;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;

import com.nineoldandroids.animation.ObjectAnimator;

public class ButtonRipplesView extends Button {
    private float a;
    private float b;
    private float c;
    private Paint d;
    private Path e = new Path();
    private Path f = new Path();

    public ButtonRipplesView(Context context) {
        super(context);
        a();
    }

    public ButtonRipplesView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        a();
    }

    public ButtonRipplesView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        a();
    }

    private void a() {
        this.d = new Paint();
        this.d.setAlpha(100);
    }

    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        this.f.reset();
        this.f.addCircle(this.a, this.b, this.c, Direction.CW);
        canvas.clipPath(this.f);
        this.e.reset();
        this.e.addCircle(this.a, this.b, this.c / 3.0f, Direction.CW);
        canvas.clipPath(this.e, Op.DIFFERENCE);
        canvas.drawCircle(this.a, this.b, this.c, this.d);
    }

    public boolean onTouchEvent(@NonNull MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 1) {
            this.a = motionEvent.getX();
            this.b = motionEvent.getY();
            ObjectAnimator ofFloat = ObjectAnimator.ofFloat((Object) this, "radius", 0.0f, ((float) getWidth()) * 3.0f);
            ofFloat.setInterpolator(new AccelerateInterpolator());
            ofFloat.setDuration(400);
            ofFloat.start();
        }
        return super.onTouchEvent(motionEvent);
    }

    public void setRadius(float f) {
        this.c = f;
        if (this.c > 0.0f) {
            this.d.setShader(new RadialGradient(this.a, this.b, this.c * 3.0f, 0, -16777216, TileMode.MIRROR));
        }
        invalidate();
    }
}
