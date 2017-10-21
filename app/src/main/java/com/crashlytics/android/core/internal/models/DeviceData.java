package com.crashlytics.android.core.internal.models;

public class DeviceData {
    public final long availableInternalStorage;
    public final long availablePhysicalMemory;
    public final int batteryCapacity;
    public final int batteryVelocity;
    public final int orientation;
    public final boolean proximity;
    public final long totalInternalStorage;
    public final long totalPhysicalMemory;

    public DeviceData(int i, long j, long j2, long j3, long j4, int i2, int i3, boolean z) {
        this.orientation = i;
        this.totalPhysicalMemory = j;
        this.totalInternalStorage = j2;
        this.availablePhysicalMemory = j3;
        this.availableInternalStorage = j4;
        this.batteryCapacity = i2;
        this.batteryVelocity = i3;
        this.proximity = z;
    }
}
