package defpackage;

import android.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;

public final class ge$a implements Runnable {
    final /* synthetic */ ge a;
    private ImageLoader b;

    public ge$a(ge geVar, ImageLoader imageLoader) {
        this.a = geVar;
        this.b = imageLoader;
    }

    public final void run() {
        Log.d("TOTO", "CLEAR CACHE");
        this.b.clearMemoryCache();
        this.b.clearDiskCache();
    }
}
