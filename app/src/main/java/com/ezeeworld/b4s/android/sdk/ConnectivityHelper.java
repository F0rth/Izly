package com.ezeeworld.b4s.android.sdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectivityHelper {

    public enum ConnectivityType {
        Wifi,
        Cellular,
        NoConnection
    }

    public static ConnectivityType getCurrent(Context context) {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        return (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) ? ConnectivityType.NoConnection : (activeNetworkInfo.getType() == 1 || activeNetworkInfo.getType() == 9) ? ConnectivityType.Wifi : ConnectivityType.Cellular;
    }
}
