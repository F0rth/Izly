package com.google.android.gms.internal;

import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.widget.ImageView;

public final class zzmc extends ImageView {
    private Uri zzakr;
    private int zzaks;
    private int zzakt;
    private zza zzaku;
    private int zzakv;
    private float zzakw;

    public interface zza {
        Path zzl(int i, int i2);
    }

    protected final void onDraw(Canvas canvas) {
        if (this.zzaku != null) {
            canvas.clipPath(this.zzaku.zzl(getWidth(), getHeight()));
        }
        super.onDraw(canvas);
        if (this.zzakt != 0) {
            canvas.drawColor(this.zzakt);
        }
    }

    protected final void onMeasure(int i, int i2) {
        int measuredHeight;
        int i3;
        super.onMeasure(i, i2);
        switch (this.zzakv) {
            case 1:
                measuredHeight = getMeasuredHeight();
                i3 = (int) (((float) measuredHeight) * this.zzakw);
                break;
            case 2:
                i3 = getMeasuredWidth();
                measuredHeight = (int) (((float) i3) / this.zzakw);
                break;
            default:
                return;
        }
        setMeasuredDimension(i3, measuredHeight);
    }

    public final void zzbO(int i) {
        this.zzaks = i;
    }

    public final void zzm(Uri uri) {
        this.zzakr = uri;
    }

    public final int zzqp() {
        return this.zzaks;
    }
}
