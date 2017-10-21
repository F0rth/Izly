package defpackage;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PreviewCallback;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class nr implements AutoFocusCallback, PreviewCallback, no {
    volatile char a = '\u0000';
    final Activity b;
    public long c;
    public volatile ox d;
    public final Camera e;
    final ScheduledThreadPoolExecutor f;
    public i g;
    public e h;
    private final jp i;
    private final Boolean j;
    private ScheduledFuture k;
    private Animation l;
    private Q m;

    public nr(Activity activity, Camera camera, jp jpVar, Boolean bool) {
        this.e = camera;
        this.i = jpVar;
        this.j = bool;
        this.c = 0;
        this.f = new ScheduledThreadPoolExecutor(5);
        this.g = null;
        this.h = null;
        this.d = ox.Top;
        this.l = null;
        this.b = activity;
        this.m = (Q) this.b.findViewById(44);
        b(this.d);
        a();
    }

    private static void a(View view) {
        synchronized (view) {
            view.setVisibility(4);
        }
    }

    private static void b(View view) {
        synchronized (view) {
            view.setVisibility(8);
        }
    }

    private void b(ox oxVar) {
        if (oxVar == ox.Right || oxVar == ox.Left) {
            this.m.a = Boolean.valueOf(true);
        } else {
            this.m.a = Boolean.valueOf(false);
        }
        this.m.invalidate();
    }

    public final Boolean a(char c) {
        return Boolean.valueOf((this.a & c) == c);
    }

    public final void a() {
        if (!(this.k == null || this.k.isCancelled())) {
            this.k.cancel(true);
        }
        this.e.autoFocus(this);
        this.k = this.f.scheduleWithFixedDelay(new d(this), 5000, 5000, TimeUnit.MILLISECONDS);
    }

    public final void a(ox oxVar) {
        b(oxVar);
        View view = (LinearLayout) this.b.findViewById(13);
        View view2 = (LinearLayout) this.b.findViewById(14);
        View view3 = (LinearLayout) this.b.findViewById(16);
        View view4 = (LinearLayout) this.b.findViewById(15);
        if (this.d == ox.Right || this.d == ox.Left) {
            view = (LinearLayout) this.b.findViewById(33);
            view2 = (LinearLayout) this.b.findViewById(34);
            view3 = (LinearLayout) this.b.findViewById(36);
            view4 = (LinearLayout) this.b.findViewById(35);
        }
        this.d = oxVar;
        nr.b(view);
        nr.b(view3);
        nr.b(view2);
        nr.b(view4);
        view = (LinearLayout) this.b.findViewById(13);
        view2 = (LinearLayout) this.b.findViewById(14);
        view3 = (LinearLayout) this.b.findViewById(16);
        view4 = (LinearLayout) this.b.findViewById(15);
        if (this.d == ox.Right || this.d == ox.Left) {
            view = (LinearLayout) this.b.findViewById(33);
            view2 = (LinearLayout) this.b.findViewById(34);
            view3 = (LinearLayout) this.b.findViewById(36);
            view4 = (LinearLayout) this.b.findViewById(35);
        }
        nr.a(view);
        nr.a(view3);
        nr.a(view2);
        nr.a(view4);
    }

    public final void b() {
        if (this.l != null) {
            this.l.cancel();
        }
        if (this.k != null) {
            this.k.cancel(false);
        }
    }

    public final void b(char c) {
        synchronized (this) {
            this.a = (char) (this.a | c);
        }
    }

    public final void onAutoFocus(boolean z, Camera camera) {
    }

    public final void onPreviewFrame(byte[] bArr, Camera camera) {
        if (!a('\b').booleanValue() && ov.a() && !a('\u0002').booleanValue() && this.g == null) {
            b();
            b('\b');
            this.b.runOnUiThread(new or(this));
            float currentTimeMillis = ((float) (System.currentTimeMillis() - this.c)) / 1000.0f;
            Bitmap createBitmap = Bitmap.createBitmap(445, 276, Config.ARGB_8888);
            int[] iArr = new int[122820];
            ov.a(iArr);
            createBitmap.setPixels(iArr, 0, 445, 0, 0, 445, 276);
            if (this.j.booleanValue()) {
                Matrix matrix = new Matrix();
                matrix.postRotate(180.0f);
                createBitmap = Bitmap.createBitmap(createBitmap, 0, 0, 445, 276, matrix, true);
            }
            if (this.i != null) {
                this.i.a(createBitmap, currentTimeMillis);
            }
        } else if (bArr != null && camera != null && !a('\b').booleanValue() && !a('\u0002').booleanValue() && this.g == null) {
            synchronized (this) {
                this.h = new e(this, this.b, bArr);
            }
            this.f.execute(this.h);
        }
    }
}
