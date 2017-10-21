package com.ad4screen.sdk.plugins.beacons;

import android.content.ServiceConnection;

public interface IBeaconServiceConnection<T> extends ServiceConnection {
    T getProvider();
}
