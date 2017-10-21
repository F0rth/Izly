package com.ezeeworld.b4s.android.sdk.positioning.server;

import com.ezeeworld.b4s.android.sdk.server.DeviceSetting;

import java.util.List;

public final class IndoorMap {
    public List<IndoorArea> areas;
    public List<BeaconPosition> beacons;
    public Calibration calibration;
    public Size size;

    public class BeaconPosition {
        public float nX;
        public float nY;
        public String sInnerName;
        public String sName;
    }

    public class Calibration {
        public List<DeviceSetting> deviceSettings;
    }

    public class IndoorArea {
        public float nH;
        public float nW;
        public float nX;
        public float nY;
        public String sName;
    }

    public class Request {
        public String sDeviceModel;
        public String sMapId;
        public String sOS;
        public String sOSVersion;
    }

    public class Size {
        public float nH;
        public float nW;
    }
}
