package com.google.android.gms.maps.internal;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.android.gms.maps.model.internal.IPolylineDelegate;
import com.google.android.gms.maps.model.internal.zzb;
import com.google.android.gms.maps.model.internal.zzc;
import com.google.android.gms.maps.model.internal.zzf;
import com.google.android.gms.maps.model.internal.zzg;
import com.google.android.gms.maps.model.internal.zzh;

public interface IGoogleMapDelegate extends IInterface {

    public static abstract class zza extends Binder implements IGoogleMapDelegate {

        static class zza implements IGoogleMapDelegate {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public zzb addCircle(CircleOptions circleOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (circleOptions != null) {
                        obtain.writeInt(1);
                        circleOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    zzb zzde = com.google.android.gms.maps.model.internal.zzb.zza.zzde(obtain2.readStrongBinder());
                    return zzde;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (groundOverlayOptions != null) {
                        obtain.writeInt(1);
                        groundOverlayOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    zzc zzdf = com.google.android.gms.maps.model.internal.zzc.zza.zzdf(obtain2.readStrongBinder());
                    return zzdf;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzf addMarker(MarkerOptions markerOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (markerOptions != null) {
                        obtain.writeInt(1);
                        markerOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    zzf zzdi = com.google.android.gms.maps.model.internal.zzf.zza.zzdi(obtain2.readStrongBinder());
                    return zzdi;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzg addPolygon(PolygonOptions polygonOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (polygonOptions != null) {
                        obtain.writeInt(1);
                        polygonOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    zzg zzdj = com.google.android.gms.maps.model.internal.zzg.zza.zzdj(obtain2.readStrongBinder());
                    return zzdj;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (polylineOptions != null) {
                        obtain.writeInt(1);
                        polylineOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    IPolylineDelegate zzdk = com.google.android.gms.maps.model.internal.IPolylineDelegate.zza.zzdk(obtain2.readStrongBinder());
                    return zzdk;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzh addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (tileOverlayOptions != null) {
                        obtain.writeInt(1);
                        tileOverlayOptions.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    zzh zzdl = com.google.android.gms.maps.model.internal.zzh.zza.zzdl(obtain2.readStrongBinder());
                    return zzdl;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCamera(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    this.zzoz.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCameraWithCallback(zzd com_google_android_gms_dynamic_zzd, zzb com_google_android_gms_maps_internal_zzb) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    if (com_google_android_gms_maps_internal_zzb != null) {
                        iBinder = com_google_android_gms_maps_internal_zzb.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.zzoz.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void animateCameraWithDurationAndCallback(zzd com_google_android_gms_dynamic_zzd, int i, zzb com_google_android_gms_maps_internal_zzb) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    obtain.writeInt(i);
                    if (com_google_android_gms_maps_internal_zzb != null) {
                        iBinder = com_google_android_gms_maps_internal_zzb.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.zzoz.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            public void clear() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public CameraPosition getCameraPosition() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    CameraPosition zzfv = obtain2.readInt() != 0 ? CameraPosition.CREATOR.zzfv(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return zzfv;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public com.google.android.gms.maps.model.internal.zzd getFocusedBuilding() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                    com.google.android.gms.maps.model.internal.zzd zzdg = com.google.android.gms.maps.model.internal.zzd.zza.zzdg(obtain2.readStrongBinder());
                    return zzdg;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void getMapAsync(zzo com_google_android_gms_maps_internal_zzo) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzo != null ? com_google_android_gms_maps_internal_zzo.asBinder() : null);
                    this.zzoz.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getMapType() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getMaxZoomLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public float getMinZoomLevel() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    float readFloat = obtain2.readFloat();
                    return readFloat;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public Location getMyLocation() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    Location location = obtain2.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return location;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IProjectionDelegate getProjection() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    IProjectionDelegate zzcX = com.google.android.gms.maps.internal.IProjectionDelegate.zza.zzcX(obtain2.readStrongBinder());
                    return zzcX;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public IUiSettingsDelegate getUiSettings() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                    IUiSettingsDelegate zzdc = com.google.android.gms.maps.internal.IUiSettingsDelegate.zza.zzdc(obtain2.readStrongBinder());
                    return zzdc;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isBuildingsEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isIndoorEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isMyLocationEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isTrafficEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void moveCamera(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    this.zzoz.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onCreate(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onDestroy() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onEnterAmbient(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onExitAmbient() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onLowMemory() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onPause() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onResume() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void onSaveInstanceState(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        bundle.readFromParcel(obtain2);
                    }
                    obtain2.recycle();
                    obtain.recycle();
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setBuildingsEnabled(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setContentDescription(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeString(str);
                    this.zzoz.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean setIndoorEnabled(boolean z) throws RemoteException {
                boolean z2 = true;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(z ? 1 : 0);
                    this.zzoz.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() == 0) {
                        z2 = false;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z2;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setInfoWindowAdapter(zzd com_google_android_gms_maps_internal_zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzd != null ? com_google_android_gms_maps_internal_zzd.asBinder() : null);
                    this.zzoz.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(iLocationSourceDelegate != null ? iLocationSourceDelegate.asBinder() : null);
                    this.zzoz.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMapType(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(i);
                    this.zzoz.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMyLocationEnabled(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnCameraChangeListener(zze com_google_android_gms_maps_internal_zze) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zze != null ? com_google_android_gms_maps_internal_zze.asBinder() : null);
                    this.zzoz.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnGroundOverlayClickListener(zzf com_google_android_gms_maps_internal_zzf) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzf != null ? com_google_android_gms_maps_internal_zzf.asBinder() : null);
                    this.zzoz.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnIndoorStateChangeListener(zzg com_google_android_gms_maps_internal_zzg) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzg != null ? com_google_android_gms_maps_internal_zzg.asBinder() : null);
                    this.zzoz.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowClickListener(zzh com_google_android_gms_maps_internal_zzh) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzh != null ? com_google_android_gms_maps_internal_zzh.asBinder() : null);
                    this.zzoz.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowCloseListener(zzi com_google_android_gms_maps_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzi != null ? com_google_android_gms_maps_internal_zzi.asBinder() : null);
                    this.zzoz.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnInfoWindowLongClickListener(zzj com_google_android_gms_maps_internal_zzj) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzj != null ? com_google_android_gms_maps_internal_zzj.asBinder() : null);
                    this.zzoz.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapClickListener(zzl com_google_android_gms_maps_internal_zzl) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzl != null ? com_google_android_gms_maps_internal_zzl.asBinder() : null);
                    this.zzoz.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapLoadedCallback(zzm com_google_android_gms_maps_internal_zzm) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzm != null ? com_google_android_gms_maps_internal_zzm.asBinder() : null);
                    this.zzoz.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMapLongClickListener(zzn com_google_android_gms_maps_internal_zzn) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzn != null ? com_google_android_gms_maps_internal_zzn.asBinder() : null);
                    this.zzoz.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMarkerClickListener(zzp com_google_android_gms_maps_internal_zzp) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzp != null ? com_google_android_gms_maps_internal_zzp.asBinder() : null);
                    this.zzoz.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMarkerDragListener(zzq com_google_android_gms_maps_internal_zzq) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzq != null ? com_google_android_gms_maps_internal_zzq.asBinder() : null);
                    this.zzoz.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMyLocationButtonClickListener(zzr com_google_android_gms_maps_internal_zzr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzr != null ? com_google_android_gms_maps_internal_zzr.asBinder() : null);
                    this.zzoz.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnMyLocationChangeListener(zzs com_google_android_gms_maps_internal_zzs) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzs != null ? com_google_android_gms_maps_internal_zzs.asBinder() : null);
                    this.zzoz.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPoiClickListener(zzt com_google_android_gms_maps_internal_zzt) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzt != null ? com_google_android_gms_maps_internal_zzt.asBinder() : null);
                    this.zzoz.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPolygonClickListener(zzu com_google_android_gms_maps_internal_zzu) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzu != null ? com_google_android_gms_maps_internal_zzu.asBinder() : null);
                    this.zzoz.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setOnPolylineClickListener(zzv com_google_android_gms_maps_internal_zzv) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzv != null ? com_google_android_gms_maps_internal_zzv.asBinder() : null);
                    this.zzoz.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setPadding(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    obtain.writeInt(i4);
                    this.zzoz.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setTrafficEnabled(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void snapshot(zzab com_google_android_gms_maps_internal_zzab, zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    obtain.writeStrongBinder(com_google_android_gms_maps_internal_zzab != null ? com_google_android_gms_maps_internal_zzab.asBinder() : null);
                    if (com_google_android_gms_dynamic_zzd != null) {
                        iBinder = com_google_android_gms_dynamic_zzd.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.zzoz.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void stopAnimation() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean useViewLifecycleWhenInFragment() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    this.zzoz.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    if (obtain2.readInt() != 0) {
                        z = true;
                    }
                    obtain2.recycle();
                    obtain.recycle();
                    return z;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static IGoogleMapDelegate zzcv(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IGoogleMapDelegate)) ? new zza(iBinder) : (IGoogleMapDelegate) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int i3 = 0;
            IBinder iBinder = null;
            float maxZoomLevel;
            boolean isTrafficEnabled;
            boolean z;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    CameraPosition cameraPosition = getCameraPosition();
                    parcel2.writeNoException();
                    if (cameraPosition != null) {
                        parcel2.writeInt(1);
                        cameraPosition.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 2:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    maxZoomLevel = getMaxZoomLevel();
                    parcel2.writeNoException();
                    parcel2.writeFloat(maxZoomLevel);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    maxZoomLevel = getMinZoomLevel();
                    parcel2.writeNoException();
                    parcel2.writeFloat(maxZoomLevel);
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    moveCamera(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCamera(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithCallback(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()), com.google.android.gms.maps.internal.zzb.zza.zzct(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    animateCameraWithDurationAndCallback(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()), parcel.readInt(), com.google.android.gms.maps.internal.zzb.zza.zzct(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    stopAnimation();
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IPolylineDelegate addPolyline = addPolyline(parcel.readInt() != 0 ? PolylineOptions.CREATOR.zzfD(parcel) : null);
                    parcel2.writeNoException();
                    if (addPolyline != null) {
                        iBinder = addPolyline.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    zzg addPolygon = addPolygon(parcel.readInt() != 0 ? PolygonOptions.CREATOR.zzfC(parcel) : null);
                    parcel2.writeNoException();
                    if (addPolygon != null) {
                        iBinder = addPolygon.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    zzf addMarker = addMarker(parcel.readInt() != 0 ? MarkerOptions.CREATOR.zzfA(parcel) : null);
                    parcel2.writeNoException();
                    if (addMarker != null) {
                        iBinder = addMarker.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    zzc addGroundOverlay = addGroundOverlay(parcel.readInt() != 0 ? GroundOverlayOptions.CREATOR.zzfx(parcel) : null);
                    parcel2.writeNoException();
                    if (addGroundOverlay != null) {
                        iBinder = addGroundOverlay.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    zzh addTileOverlay = addTileOverlay(parcel.readInt() != 0 ? TileOverlayOptions.CREATOR.zzfJ(parcel) : null);
                    parcel2.writeNoException();
                    if (addTileOverlay != null) {
                        iBinder = addTileOverlay.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    clear();
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    int mapType = getMapType();
                    parcel2.writeNoException();
                    parcel2.writeInt(mapType);
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setMapType(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = isTrafficEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isTrafficEnabled ? 1 : 0);
                    return true;
                case 18:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setTrafficEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = isIndoorEnabled();
                    parcel2.writeNoException();
                    if (isTrafficEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 20:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = setIndoorEnabled(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    if (isTrafficEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 21:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = isMyLocationEnabled();
                    parcel2.writeNoException();
                    if (isTrafficEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 22:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setMyLocationEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    Location myLocation = getMyLocation();
                    parcel2.writeNoException();
                    if (myLocation != null) {
                        parcel2.writeInt(1);
                        myLocation.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 24:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setLocationSource(com.google.android.gms.maps.internal.ILocationSourceDelegate.zza.zzcx(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IUiSettingsDelegate uiSettings = getUiSettings();
                    parcel2.writeNoException();
                    if (uiSettings != null) {
                        iBinder = uiSettings.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 26:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    IProjectionDelegate projection = getProjection();
                    parcel2.writeNoException();
                    if (projection != null) {
                        iBinder = projection.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 27:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnCameraChangeListener(com.google.android.gms.maps.internal.zze.zza.zzcA(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 28:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapClickListener(com.google.android.gms.maps.internal.zzl.zza.zzcH(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 29:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapLongClickListener(com.google.android.gms.maps.internal.zzn.zza.zzcJ(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 30:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMarkerClickListener(com.google.android.gms.maps.internal.zzp.zza.zzcL(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 31:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMarkerDragListener(com.google.android.gms.maps.internal.zzq.zza.zzcM(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 32:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnInfoWindowClickListener(com.google.android.gms.maps.internal.zzh.zza.zzcD(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 33:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setInfoWindowAdapter(com.google.android.gms.maps.internal.zzd.zza.zzcw(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 35:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    zzb addCircle = addCircle(parcel.readInt() != 0 ? CircleOptions.CREATOR.zzfw(parcel) : null);
                    parcel2.writeNoException();
                    if (addCircle != null) {
                        iBinder = addCircle.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 36:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMyLocationChangeListener(com.google.android.gms.maps.internal.zzs.zza.zzcO(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 37:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMyLocationButtonClickListener(com.google.android.gms.maps.internal.zzr.zza.zzcN(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 38:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    snapshot(com.google.android.gms.maps.internal.zzab.zza.zzcY(parcel.readStrongBinder()), com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 39:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setPadding(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 40:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = isBuildingsEnabled();
                    parcel2.writeNoException();
                    if (isTrafficEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 41:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setBuildingsEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnMapLoadedCallback(com.google.android.gms.maps.internal.zzm.zza.zzcI(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 44:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    com.google.android.gms.maps.model.internal.zzd focusedBuilding = getFocusedBuilding();
                    parcel2.writeNoException();
                    if (focusedBuilding != null) {
                        iBinder = focusedBuilding.asBinder();
                    }
                    parcel2.writeStrongBinder(iBinder);
                    return true;
                case 45:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnIndoorStateChangeListener(com.google.android.gms.maps.internal.zzg.zza.zzcC(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 53:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    getMapAsync(com.google.android.gms.maps.internal.zzo.zza.zzcK(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 54:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onCreate(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 55:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onResume();
                    parcel2.writeNoException();
                    return true;
                case 56:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onPause();
                    parcel2.writeNoException();
                    return true;
                case 57:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onDestroy();
                    parcel2.writeNoException();
                    return true;
                case 58:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onLowMemory();
                    parcel2.writeNoException();
                    return true;
                case 59:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    isTrafficEnabled = useViewLifecycleWhenInFragment();
                    parcel2.writeNoException();
                    if (isTrafficEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 60:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                    onSaveInstanceState(bundle);
                    parcel2.writeNoException();
                    if (bundle != null) {
                        parcel2.writeInt(1);
                        bundle.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 61:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setContentDescription(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 80:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnPoiClickListener(com.google.android.gms.maps.internal.zzt.zza.zzcP(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 81:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onEnterAmbient(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    onExitAmbient();
                    parcel2.writeNoException();
                    return true;
                case 83:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnGroundOverlayClickListener(com.google.android.gms.maps.internal.zzf.zza.zzcB(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 84:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnInfoWindowLongClickListener(com.google.android.gms.maps.internal.zzj.zza.zzcF(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 85:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnPolygonClickListener(com.google.android.gms.maps.internal.zzu.zza.zzcQ(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 86:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnInfoWindowCloseListener(com.google.android.gms.maps.internal.zzi.zza.zzcE(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 87:
                    parcel.enforceInterface("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    setOnPolylineClickListener(com.google.android.gms.maps.internal.zzv.zza.zzcR(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.maps.internal.IGoogleMapDelegate");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    zzb addCircle(CircleOptions circleOptions) throws RemoteException;

    zzc addGroundOverlay(GroundOverlayOptions groundOverlayOptions) throws RemoteException;

    zzf addMarker(MarkerOptions markerOptions) throws RemoteException;

    zzg addPolygon(PolygonOptions polygonOptions) throws RemoteException;

    IPolylineDelegate addPolyline(PolylineOptions polylineOptions) throws RemoteException;

    zzh addTileOverlay(TileOverlayOptions tileOverlayOptions) throws RemoteException;

    void animateCamera(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void animateCameraWithCallback(zzd com_google_android_gms_dynamic_zzd, zzb com_google_android_gms_maps_internal_zzb) throws RemoteException;

    void animateCameraWithDurationAndCallback(zzd com_google_android_gms_dynamic_zzd, int i, zzb com_google_android_gms_maps_internal_zzb) throws RemoteException;

    void clear() throws RemoteException;

    CameraPosition getCameraPosition() throws RemoteException;

    com.google.android.gms.maps.model.internal.zzd getFocusedBuilding() throws RemoteException;

    void getMapAsync(zzo com_google_android_gms_maps_internal_zzo) throws RemoteException;

    int getMapType() throws RemoteException;

    float getMaxZoomLevel() throws RemoteException;

    float getMinZoomLevel() throws RemoteException;

    Location getMyLocation() throws RemoteException;

    IProjectionDelegate getProjection() throws RemoteException;

    IUiSettingsDelegate getUiSettings() throws RemoteException;

    boolean isBuildingsEnabled() throws RemoteException;

    boolean isIndoorEnabled() throws RemoteException;

    boolean isMyLocationEnabled() throws RemoteException;

    boolean isTrafficEnabled() throws RemoteException;

    void moveCamera(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void onCreate(Bundle bundle) throws RemoteException;

    void onDestroy() throws RemoteException;

    void onEnterAmbient(Bundle bundle) throws RemoteException;

    void onExitAmbient() throws RemoteException;

    void onLowMemory() throws RemoteException;

    void onPause() throws RemoteException;

    void onResume() throws RemoteException;

    void onSaveInstanceState(Bundle bundle) throws RemoteException;

    void setBuildingsEnabled(boolean z) throws RemoteException;

    void setContentDescription(String str) throws RemoteException;

    boolean setIndoorEnabled(boolean z) throws RemoteException;

    void setInfoWindowAdapter(zzd com_google_android_gms_maps_internal_zzd) throws RemoteException;

    void setLocationSource(ILocationSourceDelegate iLocationSourceDelegate) throws RemoteException;

    void setMapType(int i) throws RemoteException;

    void setMyLocationEnabled(boolean z) throws RemoteException;

    void setOnCameraChangeListener(zze com_google_android_gms_maps_internal_zze) throws RemoteException;

    void setOnGroundOverlayClickListener(zzf com_google_android_gms_maps_internal_zzf) throws RemoteException;

    void setOnIndoorStateChangeListener(zzg com_google_android_gms_maps_internal_zzg) throws RemoteException;

    void setOnInfoWindowClickListener(zzh com_google_android_gms_maps_internal_zzh) throws RemoteException;

    void setOnInfoWindowCloseListener(zzi com_google_android_gms_maps_internal_zzi) throws RemoteException;

    void setOnInfoWindowLongClickListener(zzj com_google_android_gms_maps_internal_zzj) throws RemoteException;

    void setOnMapClickListener(zzl com_google_android_gms_maps_internal_zzl) throws RemoteException;

    void setOnMapLoadedCallback(zzm com_google_android_gms_maps_internal_zzm) throws RemoteException;

    void setOnMapLongClickListener(zzn com_google_android_gms_maps_internal_zzn) throws RemoteException;

    void setOnMarkerClickListener(zzp com_google_android_gms_maps_internal_zzp) throws RemoteException;

    void setOnMarkerDragListener(zzq com_google_android_gms_maps_internal_zzq) throws RemoteException;

    void setOnMyLocationButtonClickListener(zzr com_google_android_gms_maps_internal_zzr) throws RemoteException;

    void setOnMyLocationChangeListener(zzs com_google_android_gms_maps_internal_zzs) throws RemoteException;

    void setOnPoiClickListener(zzt com_google_android_gms_maps_internal_zzt) throws RemoteException;

    void setOnPolygonClickListener(zzu com_google_android_gms_maps_internal_zzu) throws RemoteException;

    void setOnPolylineClickListener(zzv com_google_android_gms_maps_internal_zzv) throws RemoteException;

    void setPadding(int i, int i2, int i3, int i4) throws RemoteException;

    void setTrafficEnabled(boolean z) throws RemoteException;

    void snapshot(zzab com_google_android_gms_maps_internal_zzab, zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void stopAnimation() throws RemoteException;

    boolean useViewLifecycleWhenInFragment() throws RemoteException;
}
