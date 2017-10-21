package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface Target {
    void onBitmapFailed(Drawable drawable);

    void onBitmapLoaded(Bitmap bitmap, Picasso$LoadedFrom picasso$LoadedFrom);

    void onPrepareLoad(Drawable drawable);
}
