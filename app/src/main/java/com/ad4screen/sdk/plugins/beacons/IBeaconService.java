package com.ad4screen.sdk.plugins.beacons;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.ad4screen.sdk.plugins.model.Beacon;

public interface IBeaconService extends IInterface {

    public static abstract class Stub extends Binder implements IBeaconService {
        private static final String DESCRIPTOR = "com.ad4screen.sdk.plugins.beacons.IBeaconService";
        static final int TRANSACTION_add = 3;
        static final int TRANSACTION_getVersion = 1;
        static final int TRANSACTION_remove = 2;

        static class Proxy implements IBeaconService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public void add(String str, Beacon[] beaconArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedArray(beaconArr, 0);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public long getVersion() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void remove(String str, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IBeaconService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IBeaconService)) ? new Proxy(iBinder) : (IBeaconService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    long version = getVersion();
                    parcel2.writeNoException();
                    parcel2.writeLong(version);
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    remove(parcel.readString(), parcel.createStringArray());
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    add(parcel.readString(), (Beacon[]) parcel.createTypedArray(Beacon.CREATOR));
                    return true;
                case 1598968902:
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void add(String str, Beacon[] beaconArr) throws RemoteException;

    long getVersion() throws RemoteException;

    void remove(String str, String[] strArr) throws RemoteException;
}
