package defpackage;

import android.content.Context;
import android.widget.ImageView;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.DisplayImageOptions$Builder;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public final class ge {
    public ImageLoader a = ImageLoader.getInstance();
    private DisplayImageOptions b;

    public ge(Context context) {
        ImageLoaderConfiguration build = new ImageLoaderConfiguration$Builder(context).memoryCache(new LruMemoryCache(2097152)).defaultDisplayImageOptions(new DisplayImageOptions$Builder().cacheInMemory(true).build()).build();
        this.b = new DisplayImageOptions$Builder().cacheInMemory(true).displayer(new im((int) context.getResources().getDimension(2131427529))).build();
        this.a.init(build);
    }

    public final void a(int i, ImageView imageView) {
        this.a.displayImage("drawable://" + 2130837984, imageView, this.b);
    }

    public final void a(String str, ImageView imageView) {
        this.a.displayImage(str, imageView, this.b);
    }
}
