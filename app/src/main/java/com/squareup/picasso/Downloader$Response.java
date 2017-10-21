package com.squareup.picasso;

import android.graphics.Bitmap;
import java.io.InputStream;

public class Downloader$Response {
    final Bitmap bitmap;
    final boolean cached;
    final long contentLength;
    final InputStream stream;

    @Deprecated
    public Downloader$Response(Bitmap bitmap, boolean z) {
        if (bitmap == null) {
            throw new IllegalArgumentException("Bitmap may not be null.");
        }
        this.stream = null;
        this.bitmap = bitmap;
        this.cached = z;
        this.contentLength = -1;
    }

    @Deprecated
    public Downloader$Response(Bitmap bitmap, boolean z, long j) {
        this(bitmap, z);
    }

    @Deprecated
    public Downloader$Response(InputStream inputStream, boolean z) {
        this(inputStream, z, -1);
    }

    public Downloader$Response(InputStream inputStream, boolean z, long j) {
        if (inputStream == null) {
            throw new IllegalArgumentException("Stream may not be null.");
        }
        this.stream = inputStream;
        this.bitmap = null;
        this.cached = z;
        this.contentLength = j;
    }

    @Deprecated
    public Bitmap getBitmap() {
        return this.bitmap;
    }

    public long getContentLength() {
        return this.contentLength;
    }

    public InputStream getInputStream() {
        return this.stream;
    }
}
