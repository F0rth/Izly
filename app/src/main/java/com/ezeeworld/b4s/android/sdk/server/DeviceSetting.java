package com.ezeeworld.b4s.android.sdk.server;

import java.util.List;

public class DeviceSetting {
    public String hw;
    public List<BeaconLevel> levels;

    public static class BeaconLevel {
        public int lvl;
        public int pl;
        public int tx;
    }
}
