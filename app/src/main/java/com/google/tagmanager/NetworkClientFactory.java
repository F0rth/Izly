package com.google.tagmanager;

import android.os.Build.VERSION;
import com.google.android.gms.common.util.VisibleForTesting;

class NetworkClientFactory {
    NetworkClientFactory() {
    }

    public NetworkClient createNetworkClient() {
        return getSdkVersion() < 8 ? new HttpNetworkClient() : new HttpUrlConnectionNetworkClient();
    }

    @VisibleForTesting
    int getSdkVersion() {
        return VERSION.SDK_INT;
    }
}
