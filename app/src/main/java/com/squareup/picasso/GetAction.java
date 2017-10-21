package com.squareup.picasso;

import android.graphics.Bitmap;

class GetAction extends Action<Void> {
    GetAction(Picasso picasso, Request request, int i, int i2, Object obj, String str) {
        super(picasso, null, request, i, i2, 0, null, str, obj, false);
    }

    void complete(Bitmap bitmap, Picasso$LoadedFrom picasso$LoadedFrom) {
    }

    public void error() {
    }
}
