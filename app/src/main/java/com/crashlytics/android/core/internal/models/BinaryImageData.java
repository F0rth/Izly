package com.crashlytics.android.core.internal.models;

public class BinaryImageData {
    public final long baseAddress;
    public final String id;
    public final String path;
    public final long size;

    public BinaryImageData(long j, long j2, String str, String str2) {
        this.baseAddress = j;
        this.size = j2;
        this.path = str;
        this.id = str2;
    }
}
