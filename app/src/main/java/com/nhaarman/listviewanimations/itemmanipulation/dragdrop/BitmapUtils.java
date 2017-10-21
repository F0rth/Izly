package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.View;

class BitmapUtils {
    private BitmapUtils() {
    }

    @NonNull
    static Bitmap getBitmapFromView(@NonNull View view) {
        Bitmap createBitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Config.ARGB_8888);
        view.draw(new Canvas(createBitmap));
        return createBitmap;
    }
}
