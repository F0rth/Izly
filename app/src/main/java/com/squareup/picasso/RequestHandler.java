package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory.Options;
import android.net.NetworkInfo;
import java.io.IOException;
import java.io.InputStream;

public abstract class RequestHandler {

    public static final class Result {
        private final Bitmap bitmap;
        private final int exifOrientation;
        private final Picasso$LoadedFrom loadedFrom;
        private final InputStream stream;

        public Result(Bitmap bitmap, Picasso$LoadedFrom picasso$LoadedFrom) {
            this((Bitmap) Utils.checkNotNull(bitmap, "bitmap == null"), null, picasso$LoadedFrom, 0);
        }

        Result(Bitmap bitmap, InputStream inputStream, Picasso$LoadedFrom picasso$LoadedFrom, int i) {
            int i2 = 0;
            int i3 = bitmap != null ? 1 : 0;
            if (inputStream != null) {
                i2 = 1;
            }
            if ((i2 ^ i3) == 0) {
                throw new AssertionError();
            }
            this.bitmap = bitmap;
            this.stream = inputStream;
            this.loadedFrom = (Picasso$LoadedFrom) Utils.checkNotNull(picasso$LoadedFrom, "loadedFrom == null");
            this.exifOrientation = i;
        }

        public Result(InputStream inputStream, Picasso$LoadedFrom picasso$LoadedFrom) {
            this(null, (InputStream) Utils.checkNotNull(inputStream, "stream == null"), picasso$LoadedFrom, 0);
        }

        public final Bitmap getBitmap() {
            return this.bitmap;
        }

        final int getExifOrientation() {
            return this.exifOrientation;
        }

        public final Picasso$LoadedFrom getLoadedFrom() {
            return this.loadedFrom;
        }

        public final InputStream getStream() {
            return this.stream;
        }
    }

    static void calculateInSampleSize(int i, int i2, int i3, int i4, Options options, Request request) {
        int i5 = 1;
        if (i4 > i2 || i3 > i) {
            if (i2 == 0) {
                i5 = (int) Math.floor((double) (((float) i3) / ((float) i)));
            } else if (i == 0) {
                i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
            } else {
                i5 = (int) Math.floor((double) (((float) i4) / ((float) i2)));
                int floor = (int) Math.floor((double) (((float) i3) / ((float) i)));
                i5 = request.centerInside ? Math.max(i5, floor) : Math.min(i5, floor);
            }
        }
        options.inSampleSize = i5;
        options.inJustDecodeBounds = false;
    }

    static void calculateInSampleSize(int i, int i2, Options options, Request request) {
        calculateInSampleSize(i, i2, options.outWidth, options.outHeight, options, request);
    }

    static Options createBitmapOptions(Request request) {
        boolean hasSize = request.hasSize();
        Object obj = request.config != null ? 1 : null;
        Options options = null;
        if (hasSize || obj != null) {
            options = new Options();
            options.inJustDecodeBounds = hasSize;
            if (obj != null) {
                options.inPreferredConfig = request.config;
            }
        }
        return options;
    }

    static boolean requiresInSampleSize(Options options) {
        return options != null && options.inJustDecodeBounds;
    }

    public abstract boolean canHandleRequest(Request request);

    int getRetryCount() {
        return 0;
    }

    public abstract Result load(Request request, int i) throws IOException;

    boolean shouldRetry(boolean z, NetworkInfo networkInfo) {
        return false;
    }

    boolean supportsReplay() {
        return false;
    }
}
