package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface zzc extends IInterface {

    public static abstract class zza extends Binder implements zzc {

        static class zza implements zzc {
            private IBinder zzoz;

            zza(IBinder iBinder) {
                this.zzoz = iBinder;
            }

            public IBinder asBinder() {
                return this.zzoz;
            }

            public Bundle getArguments() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    Bundle bundle = obtain2.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(obtain2) : null;
                    obtain2.recycle();
                    obtain.recycle();
                    return bundle;
                } catch (Throwable th) {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getId() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean getRetainInstance() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(7, obtain, obtain2, 0);
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

            public String getTag() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                    String readString = obtain2.readString();
                    return readString;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public int getTargetRequestCode() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                    int readInt = obtain2.readInt();
                    return readInt;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean getUserVisibleHint() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(11, obtain, obtain2, 0);
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

            public zzd getView() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbs = com.google.android.gms.dynamic.zzd.zza.zzbs(obtain2.readStrongBinder());
                    return zzbs;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public boolean isAdded() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(13, obtain, obtain2, 0);
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

            public boolean isDetached() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(14, obtain, obtain2, 0);
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

            public boolean isHidden() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(15, obtain, obtain2, 0);
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

            public boolean isInLayout() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(16, obtain, obtain2, 0);
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

            public boolean isRemoving() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public boolean isResumed() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(18, obtain, obtain2, 0);
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

            public boolean isVisible() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public void setHasOptionsMenu(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setMenuVisibility(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
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

            public void setRetainInstance(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setUserVisibleHint(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void startActivity(Intent intent) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        obtain.writeInt(1);
                        intent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.zzoz.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void startActivityForResult(Intent intent, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (intent != null) {
                        obtain.writeInt(1);
                        intent.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    this.zzoz.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzn(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    this.zzoz.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void zzo(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    obtain.writeStrongBinder(com_google_android_gms_dynamic_zzd != null ? com_google_android_gms_dynamic_zzd.asBinder() : null);
                    this.zzoz.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zztV() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbs = com.google.android.gms.dynamic.zzd.zza.zzbs(obtain2.readStrongBinder());
                    return zzbs;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc zztW() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                    zzc zzbr = zza.zzbr(obtain2.readStrongBinder());
                    return zzbr;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzd zztX() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    zzd zzbs = com.google.android.gms.dynamic.zzd.zza.zzbs(obtain2.readStrongBinder());
                    return zzbs;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public zzc zztY() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.google.android.gms.dynamic.IFragmentWrapper");
                    this.zzoz.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    zzc zzbr = zza.zzbr(obtain2.readStrongBinder());
                    return zzbr;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        public zza() {
            attachInterface(this, "com.google.android.gms.dynamic.IFragmentWrapper");
        }

        public static zzc zzbr(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.dynamic.IFragmentWrapper");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof zzc)) ? new zza(iBinder) : (zzc) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            Intent intent = null;
            boolean z = false;
            zzd zztV;
            IBinder asBinder;
            int id;
            zzc zztW;
            boolean retainInstance;
            int i3;
            switch (i) {
                case 2:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zztV = zztV();
                    parcel2.writeNoException();
                    if (zztV != null) {
                        asBinder = zztV.asBinder();
                    }
                    parcel2.writeStrongBinder(asBinder);
                    return true;
                case 3:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    Bundle arguments = getArguments();
                    parcel2.writeNoException();
                    if (arguments != null) {
                        parcel2.writeInt(1);
                        arguments.writeToParcel(parcel2, 1);
                        return true;
                    }
                    parcel2.writeInt(0);
                    return true;
                case 4:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    id = getId();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 5:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zztW = zztW();
                    parcel2.writeNoException();
                    if (zztW != null) {
                        asBinder = zztW.asBinder();
                    }
                    parcel2.writeStrongBinder(asBinder);
                    return true;
                case 6:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zztV = zztX();
                    parcel2.writeNoException();
                    if (zztV != null) {
                        asBinder = zztV.asBinder();
                    }
                    parcel2.writeStrongBinder(asBinder);
                    return true;
                case 7:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = getRetainInstance();
                    parcel2.writeNoException();
                    parcel2.writeInt(retainInstance ? 1 : 0);
                    return true;
                case 8:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    String tag = getTag();
                    parcel2.writeNoException();
                    parcel2.writeString(tag);
                    return true;
                case 9:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zztW = zztY();
                    parcel2.writeNoException();
                    if (zztW != null) {
                        asBinder = zztW.asBinder();
                    }
                    parcel2.writeStrongBinder(asBinder);
                    return true;
                case 10:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    id = getTargetRequestCode();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 11:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = getUserVisibleHint();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 12:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zztV = getView();
                    parcel2.writeNoException();
                    if (zztV != null) {
                        asBinder = zztV.asBinder();
                    }
                    parcel2.writeStrongBinder(asBinder);
                    return true;
                case 13:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isAdded();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 14:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isDetached();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 15:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isHidden();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 16:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isInLayout();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 17:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isRemoving();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 18:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isResumed();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 19:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    retainInstance = isVisible();
                    parcel2.writeNoException();
                    if (retainInstance) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 20:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzn(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setHasOptionsMenu(z);
                    parcel2.writeNoException();
                    return true;
                case 22:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setMenuVisibility(z);
                    parcel2.writeNoException();
                    return true;
                case 23:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setRetainInstance(z);
                    parcel2.writeNoException();
                    return true;
                case 24:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setUserVisibleHint(z);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        intent = (Intent) Intent.CREATOR.createFromParcel(parcel);
                    }
                    startActivity(intent);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    if (parcel.readInt() != 0) {
                        intent = (Intent) Intent.CREATOR.createFromParcel(parcel);
                    }
                    startActivityForResult(intent, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 27:
                    parcel.enforceInterface("com.google.android.gms.dynamic.IFragmentWrapper");
                    zzo(com.google.android.gms.dynamic.zzd.zza.zzbs(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.dynamic.IFragmentWrapper");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    Bundle getArguments() throws RemoteException;

    int getId() throws RemoteException;

    boolean getRetainInstance() throws RemoteException;

    String getTag() throws RemoteException;

    int getTargetRequestCode() throws RemoteException;

    boolean getUserVisibleHint() throws RemoteException;

    zzd getView() throws RemoteException;

    boolean isAdded() throws RemoteException;

    boolean isDetached() throws RemoteException;

    boolean isHidden() throws RemoteException;

    boolean isInLayout() throws RemoteException;

    boolean isRemoving() throws RemoteException;

    boolean isResumed() throws RemoteException;

    boolean isVisible() throws RemoteException;

    void setHasOptionsMenu(boolean z) throws RemoteException;

    void setMenuVisibility(boolean z) throws RemoteException;

    void setRetainInstance(boolean z) throws RemoteException;

    void setUserVisibleHint(boolean z) throws RemoteException;

    void startActivity(Intent intent) throws RemoteException;

    void startActivityForResult(Intent intent, int i) throws RemoteException;

    void zzn(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    void zzo(zzd com_google_android_gms_dynamic_zzd) throws RemoteException;

    zzd zztV() throws RemoteException;

    zzc zztW() throws RemoteException;

    zzd zztX() throws RemoteException;

    zzc zztY() throws RemoteException;
}
