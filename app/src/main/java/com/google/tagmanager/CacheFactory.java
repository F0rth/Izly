package com.google.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.common.util.VisibleForTesting;

class CacheFactory<K, V> {
    @VisibleForTesting
    final CacheSizeManager<K, V> mDefaultSizeManager = new CacheSizeManager<K, V>() {
        public int sizeOf(K k, V v) {
            return 1;
        }
    };

    public interface CacheSizeManager<K, V> {
        int sizeOf(K k, V v);
    }

    public Cache<K, V> createCache(int i) {
        return createCache(i, this.mDefaultSizeManager);
    }

    public Cache<K, V> createCache(int i, CacheSizeManager<K, V> cacheSizeManager) {
        if (i > 0) {
            return getSdkVersion() < 12 ? new SimpleCache(i, cacheSizeManager) : new LRUCache(i, cacheSizeManager);
        } else {
            throw new IllegalArgumentException("maxSize <= 0");
        }
    }

    @VisibleForTesting
    int getSdkVersion() {
        return VERSION.SDK_INT;
    }
}
