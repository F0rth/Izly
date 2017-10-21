package com.ezeeworld.b4s.android.sdk.monitor;

import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.ibeacon.IBeaconData;
import com.ezeeworld.b4s.android.sdk.notifications.MatchedInteration;

import java.util.ArrayList;
import java.util.List;

public enum MonitoringStatus {
    Starting,
    Waiting,
    NotYetInitialized,
    NoPlayServicesInstalled,
    BluetoothIncompatible,
    BluetoothPermissionsNotRequested,
    BluetoothCrashed,
    BluetoothDisabled,
    NoUserLocation,
    EnteredGeoFence,
    InteractionsDisabled,
    AppInfoNotLoaded,
    InteractionsNotLoaded,
    NoInteractions,
    NoBeaconsAround,
    NoBeaconsForApp,
    NoInteractionsMatched,
    UnexpectedError,
    MatchingBeacons;

    private static MonitoringStatus a;
    private static String b;
    private static List<MatchedInteration> c;

    public static class MonitoringBeaconsAround {
        private final List<IBeaconData> a;
        private final List<IBeaconData> b;
        private final boolean c;

        private MonitoringBeaconsAround(List<IBeaconData> list, List<IBeaconData> list2, boolean z) {
            this.a = list;
            this.b = list2;
            this.c = z;
        }

        public List<IBeaconData> getAppBeacons() {
            return this.a;
        }

        public List<IBeaconData> getOtherBeacons() {
            return this.b;
        }

        public boolean isDeviceCalibrated() {
            return this.c;
        }
    }

    public static class MonitoringStatusUpdated {
        private final MonitoringStatus a;
        private final String b;

        private MonitoringStatusUpdated(MonitoringStatus monitoringStatus, String str) {
            this.a = monitoringStatus;
            this.b = str;
        }

        public String getMessage() {
            return this.b;
        }

        public MonitoringStatus getStatus() {
            return this.a;
        }
    }

    static {
        a = Starting;
        c = new ArrayList();
    }

    public static void clearMatchedInteractions() {
        c.clear();
    }

    public static MonitoringStatus getCurrentStatus() {
        return a;
    }

    public static String getCurrentStatusMessage() {
        return b;
    }

    public static List<MatchedInteration> getMatchedInterations() {
        return c;
    }

    public static void matchedInteraction(MatchedInteration matchedInteration) {
        c.add(matchedInteration);
        EventBus.get().post(matchedInteration);
    }

    public static void update(MonitoringStatus monitoringStatus) {
        update(monitoringStatus, null);
    }

    public static void update(MonitoringStatus monitoringStatus, String str) {
        a = monitoringStatus;
        b = str;
        EventBus.get().post(new MonitoringStatusUpdated(str));
    }

    public static void visibleBeacons(List<IBeaconData> list, List<IBeaconData> list2, boolean z) {
        EventBus.get().post(new MonitoringBeaconsAround(list, list2, z));
    }
}
