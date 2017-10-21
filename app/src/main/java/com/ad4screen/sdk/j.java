package com.ad4screen.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.ad4screen.sdk.service.modules.k.c.e;

import java.util.List;

public interface j extends IInterface {

    public static abstract class a extends Binder implements j {

        static class a implements j {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public void a(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.ListsStatusCallback");
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void a(List<e> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.ListsStatusCallback");
                    obtain.writeTypedList(list);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public IBinder asBinder() {
                return this.a;
            }
        }

        public a() {
            attachInterface(this, "com.ad4screen.sdk.ListsStatusCallback");
        }

        public static j a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ad4screen.sdk.ListsStatusCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof j)) ? new a(iBinder) : (j) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.ad4screen.sdk.ListsStatusCallback");
                    a(parcel.createTypedArrayList(e.CREATOR));
                    return true;
                case 2:
                    parcel.enforceInterface("com.ad4screen.sdk.ListsStatusCallback");
                    a(parcel.readInt(), parcel.readString());
                    return true;
                case 1598968902:
                    parcel2.writeString("com.ad4screen.sdk.ListsStatusCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void a(int i, String str) throws RemoteException;

    void a(List<e> list) throws RemoteException;
}
