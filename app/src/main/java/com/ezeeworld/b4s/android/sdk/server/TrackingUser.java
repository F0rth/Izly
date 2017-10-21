package com.ezeeworld.b4s.android.sdk.server;

import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TrackingUser {
    @JsonProperty("sAddress")
    public String address;
    @JsonProperty("sAdvertisingIdentifier")
    public String advertisingIdentifier;
    @JsonProperty("nAdvertisingTrackingEnabled")
    public int advertisingTrackingEnabled;
    @JsonProperty("nAge")
    public Integer age;
    @JsonProperty("sBundleID")
    public String appPackage;
    @JsonProperty("sAppVersion")
    public String appVersion;
    @JsonProperty("sAppId")
    public String applicationId;
    @JsonProperty("bBluetooth")
    public boolean bluetoothEnabled;
    @JsonProperty("sCity")
    public String city;
    @JsonProperty("sClientId")
    public String clientId;
    @JsonProperty("sCountry")
    public String country;
    @JsonProperty("sCustomerRef")
    public String custumerReference;
    @JsonProperty("sDeviceUdid")
    public String deviceId;
    @JsonProperty("sDeviceModel")
    public String deviceModel;
    @JsonProperty("sDeviceOs")
    public String deviceOs;
    @JsonProperty("sOsVersion")
    public String deviceOsVersion;
    @JsonProperty("sEmail")
    public String email;
    @JsonProperty("sFirstname")
    public String firstName;
    @JsonProperty("nGender")
    public int gender;
    @JsonProperty("aApps")
    public List<InstalledApplication> installedApplications;
    @JsonProperty("sLang")
    public String language;
    @JsonProperty("sName")
    public String lastName;
    @JsonProperty("nLatitude")
    public double latitude;
    @JsonProperty("bLocation")
    public boolean locationEnabled;
    @JsonProperty("nLongitude")
    public double longitude;
    @JsonProperty("sPhone")
    public String phone;
    @JsonProperty("bPlayServicesInstalled")
    public boolean playServicesInstalled;
    @JsonProperty("sSdkVersion")
    public String sdkVersion;
    @JsonProperty("sUserId")
    public String userId;
    @JsonProperty("sZipCode")
    public String zipCode;

    public static class InstalledApplication {
        @JsonProperty("sAppID")
        public String appId;
        @JsonProperty("sAppVersion")
        public String appVersion;
        @JsonProperty("sBundleID")
        public String packageName;
        @JsonProperty("sSdkVersion")
        public String sdkVersion;

        public boolean equals(Object obj) {
            if (obj != null && (obj instanceof InstalledApplication)) {
                InstalledApplication installedApplication = (InstalledApplication) obj;
                if (TextUtils.equals(this.packageName, installedApplication.packageName) && TextUtils.equals(this.appId, installedApplication.appId) && TextUtils.equals(this.appVersion, installedApplication.appVersion) && TextUtils.equals(this.sdkVersion, installedApplication.sdkVersion)) {
                    return true;
                }
            }
            return false;
        }
    }
}
