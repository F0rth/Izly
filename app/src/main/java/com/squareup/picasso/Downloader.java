package com.squareup.picasso;

import android.net.Uri;
import java.io.IOException;

public interface Downloader {

    public static class ResponseException extends IOException {
        final boolean localCacheOnly;
        final int responseCode;

        public ResponseException(String str, int i, int i2) {
            super(str);
            this.localCacheOnly = NetworkPolicy.isOfflineOnly(i);
            this.responseCode = i2;
        }
    }

    Response load(Uri uri, int i) throws IOException;

    void shutdown();
}
