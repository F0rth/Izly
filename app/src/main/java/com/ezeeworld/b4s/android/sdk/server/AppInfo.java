package com.ezeeworld.b4s.android.sdk.server;

import java.util.List;

public class AppInfo {
    public boolean adaptativeBLEScanningRate = true;
    public String appId;
    public String appName;
    public int beaconsCacheValidity;
    public String beaconsUdid;
    public int boundingBoxSideLength = 100;
    public String clientId;
    public List<DeviceSetting> deviceSettings;
    public int geofencingRadius;
    public boolean isCountingActive = false;
    public boolean isDeviceCalibrated;
    public int locationTrackingHistorySize = 15;
    public int locationTrackingMaxRecordsToKeep = 100000;
    public int locationTrackingMaxUploadSize = 20;
    public int locationTrackingUploadInterval = 120;
    public boolean logging;
    public int loggingLevel;
    public int loggingMedium;
    public String md5;
    public int minDistanceBetweenUserCoords = 50;
    public int minTimeBetweenUserCoords = 60;
    public int nMaxSessionStorageDuration;
    public int nMaxUserPropertiesCount = -1;
    public boolean pluggedOnlyUpload = false;
    public int radius;
    public int scanSampleRate;
    public ScanSettings scanSettings;
    public boolean sdkEnabled = true;
    public boolean wifiOnlyUpload;

    static class AppInfoWrapper {
        public AppInfo app;
        public boolean wasRefreshed;

        AppInfoWrapper() {
        }
    }

    static class Request {
        public boolean bBluetooth;
        public String md5;
        public int nAdvertisingTrackingEnabled;
        public String sAdvertisingIdentifier;
        public String sAppId;
        public String sDeviceModel;
        public String sDeviceName;
        public String sDeviceOs;
        public String sDeviceUdid;
        public String sOsVersion;

        Request() {
        }
    }
}
