package defpackage;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.view.View;

public final class Q extends View {
    public Boolean a;
    private Rect[] b;

    public Q(Context context, Rect[] rectArr) {
        super(context);
        this.b = rectArr;
    }

    public final void draw(Canvas canvas) {
        super.draw(canvas);
        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(Mode.CLEAR));
        if (this.a.booleanValue()) {
            canvas.drawRect(this.b[1], paint);
        } else {
            canvas.drawRect(this.b[0], paint);
        }
    }
}
