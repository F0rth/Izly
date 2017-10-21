package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.app.Notification.Builder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;

import java.io.File;

@TargetApi(21)
public final class f {
    public static Builder a(Context context, int i, String str, int i2, boolean z, Builder builder) {
        builder.setPriority(i);
        if (str == null) {
            str = "promo";
        }
        builder.setCategory(str);
        if (z) {
            if (i2 == 0) {
                i2 = -7829368;
                String a = i.a(context, "com.ad4screen.notifications.accent_color", A4SService.class);
                if (a != null) {
                    try {
                        i2 = Integer.parseInt(a);
                    } catch (NumberFormatException e) {
                        Log.debug("Wrong color provided for com.ad4screen.notifications.accent_color value (must be an int constant like @android:color/gray)");
                    }
                }
            }
            builder.setColor(i2);
        }
        builder.setVisibility(1);
        return builder;
    }

    private static Bitmap a(Context context, int i) {
        Drawable drawable = context.getDrawable(i);
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        if (drawable instanceof VectorDrawable) {
            return a((VectorDrawable) drawable);
        }
        throw new IllegalArgumentException("unsupported drawable type");
    }

    public static Bitmap a(Context context, int i, int i2) {
        Bitmap a = a(context, i);
        float dimension = context.getResources().getDimension(17104901);
        float dimension2 = context.getResources().getDimension(17104902);
        float f = dimension > dimension2 ? dimension2 / 2.0f : dimension / 2.0f;
        float sqrt = (float) Math.sqrt((double) ((2.0f * f) * f));
        a = Bitmap.createScaledBitmap(a, (int) sqrt, (int) sqrt, true);
        Bitmap copy = Bitmap.createScaledBitmap(a, (int) dimension, (int) dimension2, true).copy(Config.ARGB_8888, true);
        copy.eraseColor(0);
        Canvas canvas = new Canvas(copy);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        if (i2 == 0) {
            i2 = -7829368;
            String a2 = i.a(context, "com.ad4screen.notifications.accent_color", A4SService.class);
            if (a2 != null) {
                try {
                    i2 = Integer.parseInt(a2);
                } catch (NumberFormatException e) {
                    Log.debug("Wrong color provided for com.ad4screen.notifications.accent_color value (must be an int constant like @android:color/gray)");
                }
            }
        }
        paint.setColor(i2);
        canvas.drawCircle(dimension / 2.0f, dimension2 / 2.0f, f, paint);
        canvas.drawBitmap(a, null, new Rect((int) ((dimension / 2.0f) - (sqrt / 3.0f)), (int) ((dimension2 / 2.0f) - (sqrt / 3.0f)), (int) ((dimension / 2.0f) + (sqrt / 3.0f)), (int) ((dimension2 / 2.0f) + (sqrt / 3.0f))), paint);
        return copy;
    }

    private static Bitmap a(VectorDrawable vectorDrawable) {
        Bitmap createBitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        vectorDrawable.setBounds(vectorDrawable.copyBounds());
        vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        vectorDrawable.draw(canvas);
        return createBitmap;
    }

    public static File a(Context context) {
        return context.getNoBackupFilesDir();
    }
}
