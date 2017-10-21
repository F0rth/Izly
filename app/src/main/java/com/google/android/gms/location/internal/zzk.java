package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.zzd;
import java.util.HashMap;
import java.util.Map;

public class zzk {
    private final Context mContext;
    private ContentProviderClient zzaOG = null;
    private boolean zzaOH = false;
    private Map<LocationCallback, zza> zzaOI = new HashMap();
    private final zzp<zzi> zzaOt;
    private Map<LocationListener, zzc> zzaxd = new HashMap();

    static class zza extends com.google.android.gms.location.zzc.zza {
        private Handler zzaOJ;

        zza(final LocationCallback locationCallback, Looper looper) {
            if (looper == null) {
                looper = Looper.myLooper();
                zzx.zza(looper != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.zzaOJ = new Handler(this, looper) {
                final /* synthetic */ zza zzaOK;

                public void handleMessage(Message message) {
                    switch (message.what) {
                        case 0:
                            locationCallback.onLocationResult((LocationResult) message.obj);
                            return;
                        case 1:
                            locationCallback.onLocationAvailability((LocationAvailability) message.obj);
                            return;
                        default:
                            return;
                    }
                }
            };
        }

        private void zzb(int i, Object obj) {
            if (this.zzaOJ == null) {
                Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.obj = obj;
            this.zzaOJ.sendMessage(obtain);
        }

        public void onLocationAvailability(LocationAvailability locationAvailability) {
            zzb(1, locationAvailability);
        }

        public void onLocationResult(LocationResult locationResult) {
            zzb(0, locationResult);
        }

        public void release() {
            this.zzaOJ = null;
        }
    }

    static class zzb extends Handler {
        private final LocationListener zzaOL;

        public zzb(LocationListener locationListener) {
            this.zzaOL = locationListener;
        }

        public zzb(LocationListener locationListener, Looper looper) {
            super(looper);
            this.zzaOL = locationListener;
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case 1:
                    this.zzaOL.onLocationChanged(new Location((Location) message.obj));
                    return;
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
                    return;
            }
        }
    }

    static class zzc extends com.google.android.gms.location.zzd.zza {
        private Handler zzaOJ;

        zzc(LocationListener locationListener, Looper looper) {
            if (looper == null) {
                zzx.zza(Looper.myLooper() != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.zzaOJ = looper == null ? new zzb(locationListener) : new zzb(locationListener, looper);
        }

        public void onLocationChanged(Location location) {
            if (this.zzaOJ == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.zzaOJ.sendMessage(obtain);
        }

        public void release() {
            this.zzaOJ = null;
        }
    }

    public zzk(Context context, zzp<zzi> com_google_android_gms_location_internal_zzp_com_google_android_gms_location_internal_zzi) {
        this.mContext = context;
        this.zzaOt = com_google_android_gms_location_internal_zzp_com_google_android_gms_location_internal_zzi;
    }

    private zza zza(LocationCallback locationCallback, Looper looper) {
        zza com_google_android_gms_location_internal_zzk_zza;
        synchronized (this.zzaOI) {
            com_google_android_gms_location_internal_zzk_zza = (zza) this.zzaOI.get(locationCallback);
            if (com_google_android_gms_location_internal_zzk_zza == null) {
                com_google_android_gms_location_internal_zzk_zza = new zza(locationCallback, looper);
            }
            this.zzaOI.put(locationCallback, com_google_android_gms_location_internal_zzk_zza);
        }
        return com_google_android_gms_location_internal_zzk_zza;
    }

    private zzc zza(LocationListener locationListener, Looper looper) {
        zzc com_google_android_gms_location_internal_zzk_zzc;
        synchronized (this.zzaxd) {
            com_google_android_gms_location_internal_zzk_zzc = (zzc) this.zzaxd.get(locationListener);
            if (com_google_android_gms_location_internal_zzk_zzc == null) {
                com_google_android_gms_location_internal_zzk_zzc = new zzc(locationListener, looper);
            }
            this.zzaxd.put(locationListener, com_google_android_gms_location_internal_zzk_zzc);
        }
        return com_google_android_gms_location_internal_zzk_zzc;
    }

    public Location getLastLocation() {
        this.zzaOt.zzqI();
        try {
            return ((zzi) this.zzaOt.zzqJ()).zzei(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.zzaxd) {
                for (zzd com_google_android_gms_location_zzd : this.zzaxd.values()) {
                    if (com_google_android_gms_location_zzd != null) {
                        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_zzd, null));
                    }
                }
                this.zzaxd.clear();
            }
            synchronized (this.zzaOI) {
                for (com.google.android.gms.location.zzc com_google_android_gms_location_zzc : this.zzaOI.values()) {
                    if (com_google_android_gms_location_zzc != null) {
                        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_zzc, null));
                    }
                }
                this.zzaOI.clear();
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void zza(PendingIntent pendingIntent, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zzb(pendingIntent, com_google_android_gms_location_internal_zzg));
    }

    public void zza(LocationCallback locationCallback, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        zzx.zzb((Object) locationCallback, (Object) "Invalid null callback");
        synchronized (this.zzaOI) {
            com.google.android.gms.location.zzc com_google_android_gms_location_zzc = (zza) this.zzaOI.remove(locationCallback);
            if (com_google_android_gms_location_zzc != null) {
                com_google_android_gms_location_zzc.release();
                ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_zzc, com_google_android_gms_location_internal_zzg));
            }
        }
    }

    public void zza(LocationListener locationListener, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        zzx.zzb((Object) locationListener, (Object) "Invalid null listener");
        synchronized (this.zzaxd) {
            zzd com_google_android_gms_location_zzd = (zzc) this.zzaxd.remove(locationListener);
            if (this.zzaOG != null && this.zzaxd.isEmpty()) {
                this.zzaOG.release();
                this.zzaOG = null;
            }
            if (com_google_android_gms_location_zzd != null) {
                com_google_android_gms_location_zzd.release();
                ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_zzd, com_google_android_gms_location_internal_zzg));
            }
        }
    }

    public void zza(LocationRequest locationRequest, PendingIntent pendingIntent, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb(locationRequest), pendingIntent, com_google_android_gms_location_internal_zzg));
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(LocationRequestInternal.zzb(locationRequest), zza(locationListener, looper), com_google_android_gms_location_internal_zzg));
    }

    public void zza(LocationRequestInternal locationRequestInternal, LocationCallback locationCallback, Looper looper, zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zza(LocationRequestUpdateData.zza(locationRequestInternal, zza(locationCallback, looper), com_google_android_gms_location_internal_zzg));
    }

    public void zza(zzg com_google_android_gms_location_internal_zzg) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zza(com_google_android_gms_location_internal_zzg);
    }

    public void zzam(boolean z) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zzam(z);
        this.zzaOH = z;
    }

    public void zzc(Location location) throws RemoteException {
        this.zzaOt.zzqI();
        ((zzi) this.zzaOt.zzqJ()).zzc(location);
    }

    public LocationAvailability zzyO() {
        this.zzaOt.zzqI();
        try {
            return ((zzi) this.zzaOt.zzqJ()).zzej(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void zzyP() {
        if (this.zzaOH) {
            try {
                zzam(false);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
