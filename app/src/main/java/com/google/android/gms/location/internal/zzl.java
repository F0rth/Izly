package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationStatusCodes;
import java.util.List;

public class zzl extends zzb {
    private final zzk zzaOM;

    static final class zza extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.common.api.internal.zza.zzb<Status> zzaON;

        public zza(com.google.android.gms.common.api.internal.zza.zzb<Status> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status) {
            this.zzaON = com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status;
        }

        public final void zza(int i, PendingIntent pendingIntent) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByPendingIntentResult");
        }

        public final void zza(int i, String[] strArr) {
            if (this.zzaON == null) {
                Log.wtf("LocationClientImpl", "onAddGeofenceResult called multiple times");
                return;
            }
            this.zzaON.zzs(LocationStatusCodes.zzhz(LocationStatusCodes.zzhy(i)));
            this.zzaON = null;
        }

        public final void zzb(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onRemoveGeofencesByRequestIdsResult");
        }
    }

    static final class zzb extends com.google.android.gms.location.internal.zzh.zza {
        private com.google.android.gms.common.api.internal.zza.zzb<Status> zzaON;

        public zzb(com.google.android.gms.common.api.internal.zza.zzb<Status> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status) {
            this.zzaON = com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status;
        }

        private void zzhC(int i) {
            if (this.zzaON == null) {
                Log.wtf("LocationClientImpl", "onRemoveGeofencesResult called multiple times");
                return;
            }
            this.zzaON.zzs(LocationStatusCodes.zzhz(LocationStatusCodes.zzhy(i)));
            this.zzaON = null;
        }

        public final void zza(int i, PendingIntent pendingIntent) {
            zzhC(i);
        }

        public final void zza(int i, String[] strArr) {
            Log.wtf("LocationClientImpl", "Unexpected call to onAddGeofencesResult");
        }

        public final void zzb(int i, String[] strArr) {
            zzhC(i);
        }
    }

    static final class zzc extends com.google.android.gms.location.internal.zzj.zza {
        private com.google.android.gms.common.api.internal.zza.zzb<LocationSettingsResult> zzaON;

        public zzc(com.google.android.gms.common.api.internal.zza.zzb<LocationSettingsResult> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult) {
            zzx.zzb(com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult != null, (Object) "listener can't be null.");
            this.zzaON = com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult;
        }

        public final void zza(LocationSettingsResult locationSettingsResult) throws RemoteException {
            this.zzaON.zzs(locationSettingsResult);
            this.zzaON = null;
        }
    }

    public zzl(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str) {
        this(context, looper, connectionCallbacks, onConnectionFailedListener, str, zzf.zzat(context));
    }

    public zzl(Context context, Looper looper, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, String str, zzf com_google_android_gms_common_internal_zzf) {
        super(context, looper, connectionCallbacks, onConnectionFailedListener, str, com_google_android_gms_common_internal_zzf);
        this.zzaOM = new zzk(context, this.zzaOt);
    }

    public void disconnect() {
        synchronized (this.zzaOM) {
            if (isConnected()) {
                try {
                    this.zzaOM.removeAllListeners();
                    this.zzaOM.zzyP();
                } catch (Throwable e) {
                    Log.e("LocationClientImpl", "Client disconnected before listeners could be cleaned up", e);
                }
            }
            super.disconnect();
        }
    }

    public Location getLastLocation() {
        return this.zzaOM.getLastLocation();
    }

    public void zza(long j, PendingIntent pendingIntent) throws RemoteException {
        zzqI();
        zzx.zzz(pendingIntent);
        zzx.zzb(j >= 0, (Object) "detectionIntervalMillis must be >= 0");
        ((zzi) zzqJ()).zza(j, true, pendingIntent);
    }

    public void zza(PendingIntent pendingIntent) throws RemoteException {
        zzqI();
        zzx.zzz(pendingIntent);
        ((zzi) zzqJ()).zza(pendingIntent);
    }

    public void zza(PendingIntent pendingIntent, com.google.android.gms.common.api.internal.zza.zzb<Status> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status) throws RemoteException {
        zzqI();
        zzx.zzb((Object) pendingIntent, (Object) "PendingIntent must be specified.");
        zzx.zzb((Object) com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status, (Object) "ResultHolder not provided.");
        ((zzi) zzqJ()).zza(pendingIntent, new zzb(com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status), getContext().getPackageName());
    }

    public void zza(PendingIntent pendingIntent, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOM.zza(pendingIntent, com_google_android_gms_location_internal_zzg);
    }

    public void zza(GeofencingRequest geofencingRequest, PendingIntent pendingIntent, com.google.android.gms.common.api.internal.zza.zzb<Status> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status) throws RemoteException {
        zzqI();
        zzx.zzb((Object) geofencingRequest, (Object) "geofencingRequest can't be null.");
        zzx.zzb((Object) pendingIntent, (Object) "PendingIntent must be specified.");
        zzx.zzb((Object) com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status, (Object) "ResultHolder not provided.");
        ((zzi) zzqJ()).zza(geofencingRequest, pendingIntent, new zza(com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status));
    }

    public void zza(LocationCallback locationCallback, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOM.zza(locationCallback, com_google_android_gms_location_internal_zzg);
    }

    public void zza(LocationListener locationListener, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOM.zza(locationListener, com_google_android_gms_location_internal_zzg);
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOM.zza(locationRequest, pendingIntent, com_google_android_gms_location_internal_zzg);
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        synchronized (this.zzaOM) {
            this.zzaOM.zza(locationRequest, locationListener, looper, com_google_android_gms_location_internal_zzg);
        }
    }

    public void zza(LocationSettingsRequest locationSettingsRequest, com.google.android.gms.common.api.internal.zza.zzb<LocationSettingsResult> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult, String str) throws RemoteException {
        boolean z = true;
        zzqI();
        zzx.zzb(locationSettingsRequest != null, (Object) "locationSettingsRequest can't be null nor empty.");
        if (com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult == null) {
            z = false;
        }
        zzx.zzb(z, (Object) "listener can't be null.");
        ((zzi) zzqJ()).zza(locationSettingsRequest, new zzc(com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_location_LocationSettingsResult), str);
    }

    public void zza(LocationRequestInternal locationRequestInternal, LocationCallback locationCallback, Looper looper, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        synchronized (this.zzaOM) {
            this.zzaOM.zza(locationRequestInternal, locationCallback, looper, com_google_android_gms_location_internal_zzg);
        }
    }

    public void zza(zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOM.zza(com_google_android_gms_location_internal_zzg);
    }

    public void zza(List<String> list, com.google.android.gms.common.api.internal.zza.zzb<Status> com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status) throws RemoteException {
        zzqI();
        boolean z = list != null && list.size() > 0;
        zzx.zzb(z, (Object) "geofenceRequestIds can't be null nor empty.");
        zzx.zzb((Object) com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status, (Object) "ResultHolder not provided.");
        ((zzi) zzqJ()).zza((String[]) list.toArray(new String[0]), new zzb(com_google_android_gms_common_api_internal_zza_zzb_com_google_android_gms_common_api_Status), getContext().getPackageName());
    }

    public void zzam(boolean z) throws RemoteException {
        this.zzaOM.zzam(z);
    }

    public void zzc(Location location) throws RemoteException {
        this.zzaOM.zzc(location);
    }

    public LocationAvailability zzyO() {
        return this.zzaOM.zzyO();
    }
}
