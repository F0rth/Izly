package com.google.android.gms.location.places.internal;

import android.app.PendingIntent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.NearbyAlertRequest;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.PlaceRequest;
import com.google.android.gms.location.places.UserDataType;
import com.google.android.gms.location.places.personalized.PlaceAlias;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.List;

public interface zzg extends IInterface {

    public static abstract class zza extends Binder implements zzg {

        static class zza implements zzg {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            public void zza(AddPlaceRequest addPlaceRequest, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (addPlaceRequest != null) {
                        obtain.writeInt(1);
                        addPlaceRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (nearbyAlertRequest != null) {
                        obtain.writeInt(1);
                        nearbyAlertRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PlaceReport placeReport, PlacesParams placesParams) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeReport != null) {
                        obtain.writeInt(1);
                        placeReport.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeRequest != null) {
                        obtain.writeInt(1);
                        placeRequest.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(UserDataType userDataType, LatLngBounds latLngBounds, List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (userDataType != null) {
                        obtain.writeInt(1);
                        userDataType.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStringList(list);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PlaceAlias placeAlias, PlacesParams placesParams, com.google.android.gms.location.places.personalized.zza com_google_android_gms_location_places_personalized_zza) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeAlias != null) {
                        obtain.writeInt(1);
                        placeAlias.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_personalized_zza != null ? com_google_android_gms_location_places_personalized_zza.asBinder() : null);
                    this.zzoz.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(PlaceAlias placeAlias, String str, String str2, PlacesParams placesParams, com.google.android.gms.location.places.personalized.zza com_google_android_gms_location_places_personalized_zza) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeAlias != null) {
                        obtain.writeInt(1);
                        placeAlias.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_personalized_zza != null ? com_google_android_gms_location_places_personalized_zza.asBinder() : null);
                    this.zzoz.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LatLng latLng, PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placeFilter != null) {
                        obtain.writeInt(1);
                        placeFilter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LatLng latLng, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (latLng != null) {
                        obtain.writeInt(1);
                        latLng.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(LatLngBounds latLngBounds, int i, String str, PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    if (placeFilter != null) {
                        obtain.writeInt(1);
                        placeFilter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String str, int i, int i2, int i3, PlacesParams placesParams, zzh com_google_android_gms_location_places_internal_zzh) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeInt(i3);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzh != null ? com_google_android_gms_location_places_internal_zzh.asBinder() : null);
                    this.zzoz.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String str, int i, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String str, PlacesParams placesParams, zzh com_google_android_gms_location_places_internal_zzh) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzh != null ? com_google_android_gms_location_places_internal_zzh.asBinder() : null);
                    this.zzoz.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String str, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    if (latLngBounds != null) {
                        obtain.writeInt(1);
                        latLngBounds.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (autocompleteFilter != null) {
                        obtain.writeInt(1);
                        autocompleteFilter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zza(List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeStringList(list);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placeFilter != null) {
                        obtain.writeInt(1);
                        placeFilter.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (pendingIntent != null) {
                        obtain.writeInt(1);
                        pendingIntent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(String str, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeString(str);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzb(List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    obtain.writeStringList(list);
                    if (placesParams != null) {
                        obtain.writeInt(1);
                        placesParams.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeStrongBinder(com_google_android_gms_location_places_internal_zzi != null ? com_google_android_gms_location_places_internal_zzi.asBinder() : null);
                    this.zzoz.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public static zzg zzcn(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzg)) ? new zza(iBinder) : (zzg) queryLocalInterface;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            PlacesParams placesParams = null;
            int readInt;
            String readString;
            LatLng zzfz;
            List createStringArrayList;
            switch (i) {
                case 2:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    LatLngBounds zzfy = parcel.readInt() != 0 ? LatLngBounds.CREATOR.zzfy(parcel) : null;
                    readInt = parcel.readInt();
                    String readString2 = parcel.readString();
                    PlaceFilter zzfe = parcel.readInt() != 0 ? PlaceFilter.CREATOR.zzfe(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(zzfy, readInt, readString2, zzfe, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    readString = parcel.readString();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(readString, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzfz = parcel.readInt() != 0 ? LatLng.CREATOR.zzfz(parcel) : null;
                    PlaceFilter zzfe2 = parcel.readInt() != 0 ? PlaceFilter.CREATOR.zzfe(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(zzfz, zzfe2, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    PlaceFilter zzfe3 = parcel.readInt() != 0 ? PlaceFilter.CREATOR.zzfe(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zzb(zzfe3, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    readString = parcel.readString();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zzb(readString, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    createStringArrayList = parcel.createStringArrayList();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(createStringArrayList, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readInt() != 0 ? UserDataType.CREATOR.zzfj(parcel) : null, parcel.readInt() != 0 ? LatLngBounds.CREATOR.zzfy(parcel) : null, parcel.createStringArrayList(), parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readInt() != 0 ? (PlaceRequest) PlaceRequest.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readInt() != 0 ? NearbyAlertRequest.CREATOR.zzfd(parcel) : null, parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzb(parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, parcel.readInt() != 0 ? (PendingIntent) PendingIntent.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readString(), parcel.readInt() != 0 ? LatLngBounds.CREATOR.zzfy(parcel) : null, parcel.readInt() != 0 ? AutocompleteFilter.CREATOR.zzfb(parcel) : null, parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    AddPlaceRequest addPlaceRequest = parcel.readInt() != 0 ? (AddPlaceRequest) AddPlaceRequest.CREATOR.createFromParcel(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(addPlaceRequest, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    PlaceReport placeReport = parcel.readInt() != 0 ? (PlaceReport) PlaceReport.CREATOR.createFromParcel(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(placeReport, placesParams);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zza(parcel.readInt() != 0 ? PlaceAlias.CREATOR.zzfq(parcel) : null, parcel.readString(), parcel.readString(), parcel.readInt() != 0 ? PlacesParams.CREATOR.zzfo(parcel) : null, com.google.android.gms.location.places.personalized.zza.zza.zzcr(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 17:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    createStringArrayList = parcel.createStringArrayList();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zzb(createStringArrayList, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    readString = parcel.readString();
                    int readInt2 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(readString, readInt2, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 19:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    readString = parcel.readString();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(readString, placesParams, com.google.android.gms.location.places.internal.zzh.zza.zzco(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    String readString3 = parcel.readString();
                    readInt = parcel.readInt();
                    int readInt3 = parcel.readInt();
                    int readInt4 = parcel.readInt();
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(readString3, readInt, readInt3, readInt4, placesParams, com.google.android.gms.location.places.internal.zzh.zza.zzco(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    PlaceAlias zzfq = parcel.readInt() != 0 ? PlaceAlias.CREATOR.zzfq(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(zzfq, placesParams, com.google.android.gms.location.places.personalized.zza.zza.zzcr(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    zzfz = parcel.readInt() != 0 ? LatLng.CREATOR.zzfz(parcel) : null;
                    if (parcel.readInt() != 0) {
                        placesParams = PlacesParams.CREATOR.zzfo(parcel);
                    }
                    zza(zzfz, placesParams, com.google.android.gms.location.places.internal.zzi.zza.zzcp(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.location.places.internal.IGooglePlacesService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void zza(AddPlaceRequest addPlaceRequest, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(NearbyAlertRequest nearbyAlertRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(PlaceReport placeReport, PlacesParams placesParams) throws RemoteException;

    void zza(PlaceRequest placeRequest, PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(UserDataType userDataType, LatLngBounds latLngBounds, List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zza(PlaceAlias placeAlias, PlacesParams placesParams, com.google.android.gms.location.places.personalized.zza com_google_android_gms_location_places_personalized_zza) throws RemoteException;

    void zza(PlaceAlias placeAlias, String str, String str2, PlacesParams placesParams, com.google.android.gms.location.places.personalized.zza com_google_android_gms_location_places_personalized_zza) throws RemoteException;

    void zza(LatLng latLng, PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(LatLng latLng, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(LatLngBounds latLngBounds, int i, String str, PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(String str, int i, int i2, int i3, PlacesParams placesParams, zzh com_google_android_gms_location_places_internal_zzh) throws RemoteException;

    void zza(String str, int i, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(String str, PlacesParams placesParams, zzh com_google_android_gms_location_places_internal_zzh) throws RemoteException;

    void zza(String str, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zza(List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zzb(PlaceFilter placeFilter, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zzb(PlacesParams placesParams, PendingIntent pendingIntent) throws RemoteException;

    void zzb(String str, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;

    void zzb(List<String> list, PlacesParams placesParams, zzi com_google_android_gms_location_places_internal_zzi) throws RemoteException;
}
