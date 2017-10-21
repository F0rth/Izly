package com.crashlytics.android.core.internal.models;

public class SessionEventData {
    public final BinaryImageData[] binaryImages;
    public final CustomAttributeData[] customAttributes;
    public final DeviceData deviceData;
    public final SignalData signal;
    public final ThreadData[] threads;
    public final long timestamp;

    public SessionEventData(long j, SignalData signalData, ThreadData[] threadDataArr, BinaryImageData[] binaryImageDataArr, CustomAttributeData[] customAttributeDataArr, DeviceData deviceData) {
        this.timestamp = j;
        this.signal = signalData;
        this.threads = threadDataArr;
        this.binaryImages = binaryImageDataArr;
        this.customAttributes = customAttributeDataArr;
        this.deviceData = deviceData;
    }
}
