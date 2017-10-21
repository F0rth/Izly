package com.ezeeworld.b4s.android.sdk;

import com.ezeeworld.b4s.android.sdk.ibeacon.B4SBeacon;
import com.ezeeworld.b4s.android.sdk.server.DeviceSetting;
import com.ezeeworld.b4s.android.sdk.server.DeviceSetting.BeaconLevel;

import java.util.List;

public final class CalibrationConstants {
    public double a;
    public double n;

    private CalibrationConstants(double d, double d2) {
        this.a = d;
        this.n = d2;
    }

    public static CalibrationConstants get(List<DeviceSetting> list, B4SBeacon b4SBeacon) {
        if (list == null) {
            return offlineDefaults();
        }
        if (b4SBeacon.getHardwareId() == null || b4SBeacon.getEmittingLevel() == null) {
            return offlineDefaults();
        }
        for (DeviceSetting deviceSetting : list) {
            if (deviceSetting.hw.equals("D-" + b4SBeacon.getHardwareId())) {
                for (BeaconLevel beaconLevel : deviceSetting.levels) {
                    if (beaconLevel.lvl == b4SBeacon.getEmittingLevel().intValue()) {
                        return new CalibrationConstants((double) beaconLevel.tx, (double) beaconLevel.pl);
                    }
                }
                continue;
            }
        }
        return offlineDefaults();
    }

    public static CalibrationConstants offlineDefaults() {
        return Device.getDeviceModel().equalsIgnoreCase("LGE Nexus 4") ? new CalibrationConstants(-72.0d, 1.4d) : Device.getDeviceModel().equalsIgnoreCase("LGE Nexus 5") ? new CalibrationConstants(-60.5d, 2.1d) : Device.getDeviceModel().equalsIgnoreCase("Asus Nexus 7") ? new CalibrationConstants(-62.0d, 2.0d) : Device.getDeviceModel().equalsIgnoreCase("Motorola XT1032") ? new CalibrationConstants(-57.0d, 2.2d) : Device.getDeviceModel().equalsIgnoreCase("Motorola MotoG3") ? new CalibrationConstants(-63.0d, 2.5d) : Device.getDeviceModel().equalsIgnoreCase("Motorola XT1572") ? new CalibrationConstants(-60.0d, 1.7d) : Device.getDeviceModel().equalsIgnoreCase("Samsung GT-N7105") ? new CalibrationConstants(-76.0d, 2.2d) : (Device.getDeviceModel().equalsIgnoreCase("Samsung GT-I9300") || Device.getDeviceModel().equalsIgnoreCase("Samsung GT-I9305")) ? new CalibrationConstants(-55.0d, 2.3d) : Device.getDeviceModel().equalsIgnoreCase("Samsung SM-G900F") ? new CalibrationConstants(-72.0d, 1.7d) : Device.getDeviceModel().equalsIgnoreCase("Samsung SM-G850F") ? new CalibrationConstants(-95.0d, 2.8d) : (Device.getDeviceModel().equalsIgnoreCase("Samsung SM-A300F") || Device.getDeviceModel().equalsIgnoreCase("Samsung SM-A300FU")) ? new CalibrationConstants(-60.0d, 1.7d) : (Device.getDeviceModel().equalsIgnoreCase("WIKO HIGHWAY PURE") || Device.getDeviceModel().equalsIgnoreCase("WIKO HIGHWAY 4G")) ? new CalibrationConstants(-66.0d, 2.4d) : Device.getDeviceModel().equalsIgnoreCase("HUAWEI GRA-L09") ? new CalibrationConstants(-74.0d, 1.9d) : new CalibrationConstants(-65.0d, 2.3d);
    }
}
