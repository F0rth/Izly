package com.google.tagmanager;

abstract class ServiceManager {
    ServiceManager() {
    }

    abstract void dispatch();

    abstract void onRadioPowered();

    abstract void setDispatchPeriod(int i);

    abstract void updateConnectivityStatus(boolean z);
}
