package com.google.android.gms.internal;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;

public final class zzmb {
    private static Bitmap zza(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap createBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return createBitmap;
    }

    public static Drawable zza(Resources resources, Drawable drawable) {
        return new BitmapDrawable(resources, zzb(zza(drawable)));
    }

    public static Bitmap zzb(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int i;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if (width >= height) {
            width = (height - width) / 2;
            int i2 = height;
            height = 0;
            i = i2;
        } else {
            height = (width - height) / 2;
            i = width;
            width = 0;
        }
        Bitmap createBitmap = Bitmap.createBitmap(i, i, Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        Paint paint = new Paint(1);
        paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        canvas.drawCircle((float) (i / 2), (float) (i / 2), (float) (i / 2), paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, (float) width, (float) height, paint);
        return createBitmap;
    }
}
