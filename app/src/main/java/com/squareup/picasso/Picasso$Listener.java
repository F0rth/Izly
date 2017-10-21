package com.squareup.picasso;

import android.net.Uri;

public interface Picasso$Listener {
    void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception);
}
