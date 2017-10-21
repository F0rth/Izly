package com.squareup.picasso;

public enum NetworkPolicy {
    NO_CACHE(1),
    NO_STORE(2),
    OFFLINE(4);
    
    final int index;

    private NetworkPolicy(int i) {
        this.index = i;
    }

    public static boolean isOfflineOnly(int i) {
        return (OFFLINE.index & i) != 0;
    }

    public static boolean shouldReadFromDiskCache(int i) {
        return (NO_CACHE.index & i) == 0;
    }

    public static boolean shouldWriteToDiskCache(int i) {
        return (NO_STORE.index & i) == 0;
    }
}
