package com.nostra13.universalimageloader.cache.memory.impl;

import android.graphics.Bitmap;
import com.nostra13.universalimageloader.cache.memory.LimitedMemoryCache;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class LargestLimitedMemoryCache extends LimitedMemoryCache {
    private final Map<Bitmap, Integer> valueSizes = Collections.synchronizedMap(new HashMap());

    public LargestLimitedMemoryCache(int i) {
        super(i);
    }

    public void clear() {
        this.valueSizes.clear();
        super.clear();
    }

    protected Reference<Bitmap> createReference(Bitmap bitmap) {
        return new WeakReference(bitmap);
    }

    protected int getSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public boolean put(String str, Bitmap bitmap) {
        if (!super.put(str, bitmap)) {
            return false;
        }
        this.valueSizes.put(bitmap, Integer.valueOf(getSize(bitmap)));
        return true;
    }

    public Bitmap remove(String str) {
        Bitmap bitmap = super.get(str);
        if (bitmap != null) {
            this.valueSizes.remove(bitmap);
        }
        return super.remove(str);
    }

    protected Bitmap removeNext() {
        Bitmap bitmap;
        Integer num = null;
        Set<Entry> entrySet = this.valueSizes.entrySet();
        synchronized (this.valueSizes) {
            bitmap = null;
            for (Entry entry : entrySet) {
                if (bitmap == null) {
                    num = (Integer) entry.getValue();
                    bitmap = (Bitmap) entry.getKey();
                } else {
                    Bitmap bitmap2;
                    Integer num2 = (Integer) entry.getValue();
                    if (num2.intValue() > num.intValue()) {
                        bitmap2 = (Bitmap) entry.getKey();
                    } else {
                        bitmap2 = bitmap;
                        num2 = num;
                    }
                    num = num2;
                    bitmap = bitmap2;
                }
            }
        }
        this.valueSizes.remove(bitmap);
        return bitmap;
    }
}
