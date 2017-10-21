package com.google.android.gms.common.images;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.widget.ImageView;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.internal.zzma;
import com.google.android.gms.internal.zzmb;
import com.google.android.gms.internal.zzmc;
import com.google.android.gms.internal.zzmd;
import java.lang.ref.WeakReference;

public abstract class zza {
    final zza zzajO;
    protected int zzajP = 0;
    protected int zzajQ = 0;
    protected boolean zzajR = false;
    protected OnImageLoadedListener zzajS;
    private boolean zzajT = true;
    private boolean zzajU = false;
    private boolean zzajV = true;
    protected int zzajW;

    static final class zza {
        public final Uri uri;

        public zza(Uri uri) {
            this.uri = uri;
        }

        public final boolean equals(Object obj) {
            return !(obj instanceof zza) ? false : this == obj ? true : zzw.equal(((zza) obj).uri, this.uri);
        }

        public final int hashCode() {
            return zzw.hashCode(this.uri);
        }
    }

    public static final class zzb extends zza {
        private WeakReference<ImageView> zzajX;

        public zzb(ImageView imageView, int i) {
            super(null, i);
            com.google.android.gms.common.internal.zzb.zzv(imageView);
            this.zzajX = new WeakReference(imageView);
        }

        public zzb(ImageView imageView, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzv(imageView);
            this.zzajX = new WeakReference(imageView);
        }

        private void zza(ImageView imageView, Drawable drawable, boolean z, boolean z2, boolean z3) {
            Object obj = (z2 || z3) ? null : 1;
            if (obj != null && (imageView instanceof zzmc)) {
                int zzqp = ((zzmc) imageView).zzqp();
                if (this.zzajQ != 0 && zzqp == this.zzajQ) {
                    return;
                }
            }
            boolean zzb = zzb(z, z2);
            Drawable newDrawable = (!this.zzajR || drawable == null) ? drawable : drawable.getConstantState().newDrawable();
            if (zzb) {
                newDrawable = zza(imageView.getDrawable(), newDrawable);
            }
            imageView.setImageDrawable(newDrawable);
            if (imageView instanceof zzmc) {
                zzmc com_google_android_gms_internal_zzmc = (zzmc) imageView;
                com_google_android_gms_internal_zzmc.zzm(z3 ? this.zzajO.uri : null);
                com_google_android_gms_internal_zzmc.zzbO(obj != null ? this.zzajQ : 0);
            }
            if (zzb) {
                ((zzma) newDrawable).startTransition(Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
            }
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzb)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            ImageView imageView = (ImageView) this.zzajX.get();
            ImageView imageView2 = (ImageView) ((zzb) obj).zzajX.get();
            return (imageView2 == null || imageView == null || !zzw.equal(imageView2, imageView)) ? false : true;
        }

        public final int hashCode() {
            return 0;
        }

        protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            ImageView imageView = (ImageView) this.zzajX.get();
            if (imageView != null) {
                zza(imageView, drawable, z, z2, z3);
            }
        }
    }

    public static final class zzc extends zza {
        private WeakReference<OnImageLoadedListener> zzajY;

        public zzc(OnImageLoadedListener onImageLoadedListener, Uri uri) {
            super(uri, 0);
            com.google.android.gms.common.internal.zzb.zzv(onImageLoadedListener);
            this.zzajY = new WeakReference(onImageLoadedListener);
        }

        public final boolean equals(Object obj) {
            if (!(obj instanceof zzc)) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            zzc com_google_android_gms_common_images_zza_zzc = (zzc) obj;
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzajY.get();
            OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) com_google_android_gms_common_images_zza_zzc.zzajY.get();
            return onImageLoadedListener2 != null && onImageLoadedListener != null && zzw.equal(onImageLoadedListener2, onImageLoadedListener) && zzw.equal(com_google_android_gms_common_images_zza_zzc.zzajO, this.zzajO);
        }

        public final int hashCode() {
            return zzw.hashCode(this.zzajO);
        }

        protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
            if (!z2) {
                OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzajY.get();
                if (onImageLoadedListener != null) {
                    onImageLoadedListener.onImageLoaded(this.zzajO.uri, drawable, z3);
                }
            }
        }
    }

    public zza(Uri uri, int i) {
        this.zzajO = new zza(uri);
        this.zzajQ = i;
    }

    private Drawable zza(Context context, zzmd com_google_android_gms_internal_zzmd, int i) {
        Resources resources = context.getResources();
        if (this.zzajW <= 0) {
            return resources.getDrawable(i);
        }
        com.google.android.gms.internal.zzmd.zza com_google_android_gms_internal_zzmd_zza = new com.google.android.gms.internal.zzmd.zza(i, this.zzajW);
        Drawable drawable = (Drawable) com_google_android_gms_internal_zzmd.get(com_google_android_gms_internal_zzmd_zza);
        if (drawable != null) {
            return drawable;
        }
        drawable = resources.getDrawable(i);
        if ((this.zzajW & 1) != 0) {
            drawable = zza(resources, drawable);
        }
        com_google_android_gms_internal_zzmd.put(com_google_android_gms_internal_zzmd_zza, drawable);
        return drawable;
    }

    protected Drawable zza(Resources resources, Drawable drawable) {
        return zzmb.zza(resources, drawable);
    }

    protected zzma zza(Drawable drawable, Drawable drawable2) {
        if (drawable == null) {
            drawable = null;
        } else if (drawable instanceof zzma) {
            drawable = ((zzma) drawable).zzqn();
        }
        return new zzma(drawable, drawable2);
    }

    void zza(Context context, Bitmap bitmap, boolean z) {
        com.google.android.gms.common.internal.zzb.zzv(bitmap);
        if ((this.zzajW & 1) != 0) {
            bitmap = zzmb.zzb(bitmap);
        }
        Drawable bitmapDrawable = new BitmapDrawable(context.getResources(), bitmap);
        if (this.zzajS != null) {
            this.zzajS.onImageLoaded(this.zzajO.uri, bitmapDrawable, true);
        }
        zza(bitmapDrawable, z, false, true);
    }

    void zza(Context context, zzmd com_google_android_gms_internal_zzmd) {
        if (this.zzajV) {
            Drawable drawable = null;
            if (this.zzajP != 0) {
                drawable = zza(context, com_google_android_gms_internal_zzmd, this.zzajP);
            }
            zza(drawable, false, true, false);
        }
    }

    void zza(Context context, zzmd com_google_android_gms_internal_zzmd, boolean z) {
        Drawable drawable = null;
        if (this.zzajQ != 0) {
            drawable = zza(context, com_google_android_gms_internal_zzmd, this.zzajQ);
        }
        if (this.zzajS != null) {
            this.zzajS.onImageLoaded(this.zzajO.uri, drawable, false);
        }
        zza(drawable, z, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected boolean zzb(boolean z, boolean z2) {
        return this.zzajT && !z2 && (!z || this.zzajU);
    }

    public void zzbM(int i) {
        this.zzajQ = i;
    }
}
