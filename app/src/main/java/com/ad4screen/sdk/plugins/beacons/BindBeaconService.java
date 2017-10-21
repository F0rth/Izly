package com.ad4screen.sdk.plugins.beacons;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.plugins.beacons.IBeaconService.Stub;

import java.util.List;

public class BindBeaconService {
    private BindBeaconProvider<IBeaconService> mBindProvider;
    private Context mContext;
    private IBeaconService mService;

    public BindBeaconService(Context context) {
        this.mContext = context;
    }

    private void bindToService(final Callback<IBeaconService> callback) {
        Log.internal("BindBeaconService|Finding service...");
        this.mBindProvider = new BindBeaconProvider(this.mContext, Constants.ACTION_QUERY, Constants.CATEGORY_BEACON_NOTIFICATIONS, IBeaconService.class, new BeaconProviderCallback<IBeaconService>() {
            private IBeaconService firstMatchingVersionService(List<IBeaconService> list) {
                for (IBeaconService iBeaconService : list) {
                    try {
                        if (2 == iBeaconService.getVersion()) {
                            return iBeaconService;
                        }
                    } catch (Throwable e) {
                        Log.internal("BindBeaconService|Remote exception while binding to service", e);
                    }
                }
                Log.internal("BindBeaconService|No service found with version 2");
                return null;
            }

            public void onProvidersBinded(List<IBeaconService> list) {
                BindBeaconService.this.mService = firstMatchingVersionService(list);
                if (BindBeaconService.this.mService == null) {
                    try {
                        BindBeaconService.this.mContext.bindService(new Intent(BindBeaconService.this.mContext, Class.forName("com.ad4screen.sdk.A4SBeaconService")), new ServiceConnection() {
                            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                                BindBeaconService.this.mService = Stub.asInterface(iBinder);
                                if (callback != null) {
                                    callback.onResult(BindBeaconService.this.mService);
                                }
                            }

                            public void onServiceDisconnected(ComponentName componentName) {
                                BindBeaconService.this.mService = null;
                            }
                        }, 1);
                    } catch (Throwable e) {
                        Log.error("BindBeaconService|Can't create com.ad4screen.sdk.A4SBeaconService", e);
                    }
                } else if (callback != null) {
                    callback.onResult(BindBeaconService.this.mService);
                }
            }
        });
        this.mBindProvider.connectToFirstRunningService();
    }

    public IBeaconService getService(Callback<IBeaconService> callback) {
        if (this.mService == null) {
            bindToService(callback);
        } else {
            try {
                Log.internal("BindBeaconService|Service running with v" + this.mService.getVersion());
                if (callback != null) {
                    callback.onResult(this.mService);
                }
            } catch (Throwable e) {
                Log.error("BindBeaconService|Error while getting service", e);
                bindToService(callback);
            }
        }
        return this.mService;
    }

    public void unbindService() {
        if (this.mBindProvider != null) {
            this.mBindProvider.closeConnections();
        }
    }
}
