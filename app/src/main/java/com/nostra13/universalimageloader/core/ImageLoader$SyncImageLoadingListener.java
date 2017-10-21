package com.nostra13.universalimageloader.core;

import android.graphics.Bitmap;
import android.view.View;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

class ImageLoader$SyncImageLoadingListener extends SimpleImageLoadingListener {
    private Bitmap loadedImage;

    private ImageLoader$SyncImageLoadingListener() {
    }

    public Bitmap getLoadedBitmap() {
        return this.loadedImage;
    }

    public void onLoadingComplete(String str, View view, Bitmap bitmap) {
        this.loadedImage = bitmap;
    }
}
