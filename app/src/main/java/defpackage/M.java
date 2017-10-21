package defpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import android.widget.TextView;

public final class M extends TextView {
    private Context a;
    private Paint b = new Paint();
    private Paint c = new Paint();

    public M(Context context) {
        super(context);
        this.a = context;
    }

    private float a(float f) {
        return (float) (((double) (this.a.getResources().getDisplayMetrics().density * f)) + 0.5d);
    }

    protected final void onDraw(Canvas canvas) {
        this.b.setColor(ViewCompat.MEASURED_STATE_MASK);
        this.b.setTextAlign(Align.LEFT);
        this.b.setTextSize(a(20.0f));
        this.b.setTypeface(Typeface.DEFAULT_BOLD);
        this.b.setStyle(Style.STROKE);
        this.b.setStrokeWidth(a(2.0f));
        this.c.setColor(-1);
        this.c.setTextAlign(Align.LEFT);
        this.c.setTextSize(a(20.0f));
        this.c.setTypeface(Typeface.DEFAULT_BOLD);
        canvas.drawText(getText().toString(), a(2.0f), a(20.0f), this.b);
        canvas.drawText(getText().toString(), a(2.0f), a(20.0f), this.c);
    }
}
