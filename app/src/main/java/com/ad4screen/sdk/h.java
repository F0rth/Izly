package com.ad4screen.sdk;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import com.ad4screen.sdk.b.c;

public interface h extends IInterface {

    public static abstract class a extends Binder implements h {

        static class a implements h {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public void a() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.InboxCallback");
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void a(c[] cVarArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.InboxCallback");
                    obtain.writeTypedArray(cVarArr, 0);
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
            attachInterface(this, "com.ad4screen.sdk.InboxCallback");
        }

        public static h a(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ad4screen.sdk.InboxCallback");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof h)) ? new a(iBinder) : (h) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.ad4screen.sdk.InboxCallback");
                    a((c[]) parcel.createTypedArray(c.CREATOR));
                    return true;
                case 2:
                    parcel.enforceInterface("com.ad4screen.sdk.InboxCallback");
                    a();
                    return true;
                case 1598968902:
                    parcel2.writeString("com.ad4screen.sdk.InboxCallback");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void a() throws RemoteException;

    void a(c[] cVarArr) throws RemoteException;
}
