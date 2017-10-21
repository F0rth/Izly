package com.ad4screen.sdk.plugins.beacons;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.IBinder;
import android.os.IInterface;

import com.ad4screen.sdk.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindBeaconProvider<T extends IInterface> {
    private static final String TAG = "BindBeaconService";
    private final BeaconProviderCallback<T> mCallback;
    private final Context mContext;
    private final Intent mIntent;
    private int mMax;
    private final Class<T> mParameterType;
    private List<T> mProviders = new ArrayList();
    private List<IBeaconServiceConnection<T>> mProvidersConnections = new ArrayList();

    final class BeaconServiceConnection implements IBeaconServiceConnection<T> {
        private T mProvider;
        private List<IBeaconServiceConnection<T>> mProvidersConnection;

        private BeaconServiceConnection(List<IBeaconServiceConnection<T>> list) {
            this.mProvidersConnection = list;
        }

        public final T getProvider() {
            return this.mProvider;
        }

        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                Log.debug("BindBeaconService|onServiceConnected " + componentName);
                this.mProvider = (IInterface) Class.forName(BindBeaconProvider.this.mParameterType.getName() + "$Stub").getMethod("asInterface", new Class[]{IBinder.class}).invoke(null, new Object[]{iBinder});
            } catch (Throwable e) {
                Log.error("BindBeaconService|Ignore provider. Invalid type " + BindBeaconProvider.this.mParameterType.getName() + " in " + getClass(), e);
            }
            BindBeaconProvider.this.mMax = BindBeaconProvider.this.mMax - 1;
            if (BindBeaconProvider.this.mMax == 0) {
                BindBeaconProvider.this.onFinishToBind();
            }
        }

        public final void onServiceDisconnected(ComponentName componentName) {
            Log.debug("BindBeaconService|onServiceDisconnected");
            this.mProvidersConnection.remove(this);
            BindBeaconProvider.this.onProviderDisconnected(this.mProvider);
            this.mProvider = null;
        }
    }

    public BindBeaconProvider(Context context, String str, String str2, Class<T> cls, BeaconProviderCallback<T> beaconProviderCallback) {
        this.mContext = context;
        this.mIntent = new Intent(str);
        if (str2 != null) {
            this.mIntent.addCategory(str2);
        }
        this.mParameterType = cls;
        this.mCallback = beaconProviderCallback;
    }

    private List<ResolveInfo> getServicesForIntent(Intent intent) {
        return this.mContext.getPackageManager().queryIntentServices(intent, 0);
    }

    private boolean isServiceRunning(String str, String str2) {
        for (RunningServiceInfo runningServiceInfo : ((ActivityManager) this.mContext.getSystemService("activity")).getRunningServices(ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED)) {
            if (str.equals(runningServiceInfo.service.getClassName()) && str2.equals(runningServiceInfo.service.getPackageName())) {
                Log.info("BindBeaconService|" + str + " running");
                return true;
            }
        }
        Log.info("BindBeaconService|" + str + " not running");
        return false;
    }

    private void onFinishToBind() {
        Log.info("BindBeaconService|onFinishToBind with " + this.mProvidersConnections.size() + " items");
        for (IBeaconServiceConnection provider : this.mProvidersConnections) {
            IInterface iInterface = (IInterface) provider.getProvider();
            if (iInterface != null) {
                this.mProviders.add(iInterface);
            }
        }
        if (this.mCallback != null) {
            this.mCallback.onProvidersBinded(this.mProviders);
        }
    }

    private void onProviderDisconnected(T t) {
        this.mProviders.remove(t);
    }

    public void closeConnections() {
        Log.info("BindBeaconService|Clear " + this.mProvidersConnections.size() + " binded interface");
        for (IBeaconServiceConnection unbindService : this.mProvidersConnections) {
            this.mContext.unbindService(unbindService);
        }
        this.mProvidersConnections.clear();
        this.mProviders.clear();
        this.mMax = 0;
    }

    public void connectToFirstRunningService() {
        List synchronizedList = Collections.synchronizedList(new ArrayList());
        for (ResolveInfo resolveInfo : getServicesForIntent(this.mIntent)) {
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            if (serviceInfo != null && isServiceRunning(serviceInfo.name, serviceInfo.packageName)) {
                Log.info("BindBeaconService|Service found " + resolveInfo);
                Intent intent = new Intent(this.mIntent);
                intent.setClassName(serviceInfo.packageName, serviceInfo.name);
                ServiceConnection beaconServiceConnection = new BeaconServiceConnection(synchronizedList);
                try {
                    Log.info("BindBeaconService|Binding...");
                    if (this.mContext.bindService(intent, beaconServiceConnection, 1)) {
                        synchronizedList.add(beaconServiceConnection);
                        Log.info("BindBeaconService|Bind OK !");
                    } else {
                        Log.error("BindBeaconService|Impossible to bind to service " + resolveInfo);
                    }
                } catch (Throwable e) {
                    Log.warn("BindBeaconService|Impossible to bind to '" + resolveInfo.serviceInfo.name + "' in app '" + resolveInfo.serviceInfo.applicationInfo.packageName + "'", e);
                }
            }
        }
        this.mMax = synchronizedList.size();
        if (this.mMax == 0 && this.mCallback != null) {
            this.mCallback.onProvidersBinded(this.mProviders);
        }
        this.mProvidersConnections = synchronizedList;
    }

    public List<T> getProviders() {
        return this.mProviders;
    }
}
