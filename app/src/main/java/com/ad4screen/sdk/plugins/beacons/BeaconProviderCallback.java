package com.ad4screen.sdk.plugins.beacons;

import java.util.List;

public interface BeaconProviderCallback<T> {
    void onProvidersBinded(List<T> list);
}
