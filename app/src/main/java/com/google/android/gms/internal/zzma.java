package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;

public final class zzma extends Drawable implements Callback {
    private int mFrom;
    private long zzRD;
    private boolean zzajT;
    private int zzaka;
    private int zzakb;
    private int zzakc;
    private int zzakd;
    private int zzake;
    private boolean zzakf;
    private zzb zzakg;
    private Drawable zzakh;
    private Drawable zzaki;
    private boolean zzakj;
    private boolean zzakk;
    private boolean zzakl;
    private int zzakm;

    static final class zza extends Drawable {
        private static final zza zzakn = new zza();
        private static final zza zzako = new zza();

        static final class zza extends ConstantState {
            private zza() {
            }

            public final int getChangingConfigurations() {
                return 0;
            }

            public final Drawable newDrawable() {
                return zza.zzakn;
            }
        }

        private zza() {
        }

        public final void draw(Canvas canvas) {
        }

        public final ConstantState getConstantState() {
            return zzako;
        }

        public final int getOpacity() {
            return -2;
        }

        public final void setAlpha(int i) {
        }

        public final void setColorFilter(ColorFilter colorFilter) {
        }
    }

    static final class zzb extends ConstantState {
        int zzakp;
        int zzakq;

        zzb(zzb com_google_android_gms_internal_zzma_zzb) {
            if (com_google_android_gms_internal_zzma_zzb != null) {
                this.zzakp = com_google_android_gms_internal_zzma_zzb.zzakp;
                this.zzakq = com_google_android_gms_internal_zzma_zzb.zzakq;
            }
        }

        public final int getChangingConfigurations() {
            return this.zzakp;
        }

        public final Drawable newDrawable() {
            return new zzma(this);
        }
    }

    public zzma(Drawable drawable, Drawable drawable2) {
        this(null);
        if (drawable == null) {
            drawable = zza.zzakn;
        }
        this.zzakh = drawable;
        drawable.setCallback(this);
        zzb com_google_android_gms_internal_zzma_zzb = this.zzakg;
        com_google_android_gms_internal_zzma_zzb.zzakq |= drawable.getChangingConfigurations();
        if (drawable2 == null) {
            drawable2 = zza.zzakn;
        }
        this.zzaki = drawable2;
        drawable2.setCallback(this);
        com_google_android_gms_internal_zzma_zzb = this.zzakg;
        com_google_android_gms_internal_zzma_zzb.zzakq |= drawable2.getChangingConfigurations();
    }

    zzma(zzb com_google_android_gms_internal_zzma_zzb) {
        this.zzaka = 0;
        this.zzakc = 255;
        this.zzake = 0;
        this.zzajT = true;
        this.zzakg = new zzb(com_google_android_gms_internal_zzma_zzb);
    }

    public final boolean canConstantState() {
        if (!this.zzakj) {
            boolean z = (this.zzakh.getConstantState() == null || this.zzaki.getConstantState() == null) ? false : true;
            this.zzakk = z;
            this.zzakj = true;
        }
        return this.zzakk;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void draw(android.graphics.Canvas r8) {
        /*
        r7 = this;
        r1 = 1;
        r6 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r0 = 0;
        r2 = r7.zzaka;
        switch(r2) {
            case 1: goto L_0x0028;
            case 2: goto L_0x0032;
            default: goto L_0x0009;
        };
    L_0x0009:
        r0 = r1;
    L_0x000a:
        r1 = r7.zzake;
        r2 = r7.zzajT;
        r3 = r7.zzakh;
        r4 = r7.zzaki;
        if (r0 == 0) goto L_0x0064;
    L_0x0014:
        if (r2 == 0) goto L_0x0018;
    L_0x0016:
        if (r1 != 0) goto L_0x001b;
    L_0x0018:
        r3.draw(r8);
    L_0x001b:
        r0 = r7.zzakc;
        if (r1 != r0) goto L_0x0027;
    L_0x001f:
        r0 = r7.zzakc;
        r4.setAlpha(r0);
        r4.draw(r8);
    L_0x0027:
        return;
    L_0x0028:
        r2 = android.os.SystemClock.uptimeMillis();
        r7.zzRD = r2;
        r1 = 2;
        r7.zzaka = r1;
        goto L_0x000a;
    L_0x0032:
        r2 = r7.zzRD;
        r4 = 0;
        r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
        if (r2 < 0) goto L_0x0009;
    L_0x003a:
        r2 = android.os.SystemClock.uptimeMillis();
        r4 = r7.zzRD;
        r2 = r2 - r4;
        r2 = (float) r2;
        r3 = r7.zzakd;
        r3 = (float) r3;
        r2 = r2 / r3;
        r3 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
        if (r3 < 0) goto L_0x0062;
    L_0x004a:
        if (r1 == 0) goto L_0x004e;
    L_0x004c:
        r7.zzaka = r0;
    L_0x004e:
        r0 = java.lang.Math.min(r2, r6);
        r2 = r7.mFrom;
        r2 = (float) r2;
        r3 = r7.zzakb;
        r4 = r7.mFrom;
        r3 = r3 - r4;
        r3 = (float) r3;
        r0 = r0 * r3;
        r0 = r0 + r2;
        r0 = (int) r0;
        r7.zzake = r0;
        r0 = r1;
        goto L_0x000a;
    L_0x0062:
        r1 = r0;
        goto L_0x004a;
    L_0x0064:
        if (r2 == 0) goto L_0x006c;
    L_0x0066:
        r0 = r7.zzakc;
        r0 = r0 - r1;
        r3.setAlpha(r0);
    L_0x006c:
        r3.draw(r8);
        if (r2 == 0) goto L_0x0076;
    L_0x0071:
        r0 = r7.zzakc;
        r3.setAlpha(r0);
    L_0x0076:
        if (r1 <= 0) goto L_0x0083;
    L_0x0078:
        r4.setAlpha(r1);
        r4.draw(r8);
        r0 = r7.zzakc;
        r4.setAlpha(r0);
    L_0x0083:
        r7.invalidateSelf();
        goto L_0x0027;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzma.draw(android.graphics.Canvas):void");
    }

    public final int getChangingConfigurations() {
        return (super.getChangingConfigurations() | this.zzakg.zzakp) | this.zzakg.zzakq;
    }

    public final ConstantState getConstantState() {
        if (!canConstantState()) {
            return null;
        }
        this.zzakg.zzakp = getChangingConfigurations();
        return this.zzakg;
    }

    public final int getIntrinsicHeight() {
        return Math.max(this.zzakh.getIntrinsicHeight(), this.zzaki.getIntrinsicHeight());
    }

    public final int getIntrinsicWidth() {
        return Math.max(this.zzakh.getIntrinsicWidth(), this.zzaki.getIntrinsicWidth());
    }

    public final int getOpacity() {
        if (!this.zzakl) {
            this.zzakm = Drawable.resolveOpacity(this.zzakh.getOpacity(), this.zzaki.getOpacity());
            this.zzakl = true;
        }
        return this.zzakm;
    }

    @TargetApi(11)
    public final void invalidateDrawable(Drawable drawable) {
        if (zzne.zzsd()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.invalidateDrawable(this);
            }
        }
    }

    public final Drawable mutate() {
        if (!this.zzakf && super.mutate() == this) {
            if (canConstantState()) {
                this.zzakh.mutate();
                this.zzaki.mutate();
                this.zzakf = true;
            } else {
                throw new IllegalStateException("One or more children of this LayerDrawable does not have constant state; this drawable cannot be mutated.");
            }
        }
        return this;
    }

    protected final void onBoundsChange(Rect rect) {
        this.zzakh.setBounds(rect);
        this.zzaki.setBounds(rect);
    }

    @TargetApi(11)
    public final void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        if (zzne.zzsd()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.scheduleDrawable(this, runnable, j);
            }
        }
    }

    public final void setAlpha(int i) {
        if (this.zzake == this.zzakc) {
            this.zzake = i;
        }
        this.zzakc = i;
        invalidateSelf();
    }

    public final void setColorFilter(ColorFilter colorFilter) {
        this.zzakh.setColorFilter(colorFilter);
        this.zzaki.setColorFilter(colorFilter);
    }

    public final void startTransition(int i) {
        this.mFrom = 0;
        this.zzakb = this.zzakc;
        this.zzake = 0;
        this.zzakd = i;
        this.zzaka = 1;
        invalidateSelf();
    }

    @TargetApi(11)
    public final void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        if (zzne.zzsd()) {
            Callback callback = getCallback();
            if (callback != null) {
                callback.unscheduleDrawable(this, runnable);
            }
        }
    }

    public final Drawable zzqn() {
        return this.zzaki;
    }
}
