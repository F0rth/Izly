package defpackage;

import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

final class kl$b implements IInterface {
    private final IBinder a;

    public kl$b(IBinder iBinder) {
        this.a = iBinder;
    }

    public final String a() throws RemoteException {
        Parcel obtain = Parcel.obtain();
        Parcel obtain2 = Parcel.obtain();
        String readString;
        try {
            obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
            this.a.transact(1, obtain, obtain2, 0);
            obtain2.readException();
            readString = obtain2.readString();
            return readString;
        } catch (Exception e) {
            readString = js.a();
            readString.a("Fabric", "Could not get parcel from Google Play Service to capture AdvertisingId");
            return null;
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final IBinder asBinder() {
        return this.a;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean b() throws android.os.RemoteException {
        /*
        r7 = this;
        r1 = 1;
        r0 = 0;
        r2 = android.os.Parcel.obtain();
        r3 = android.os.Parcel.obtain();
        r4 = "com.google.android.gms.ads.identifier.internal.IAdvertisingIdService";
        r2.writeInterfaceToken(r4);	 Catch:{ Exception -> 0x002b }
        r4 = 1;
        r2.writeInt(r4);	 Catch:{ Exception -> 0x002b }
        r4 = r7.a;	 Catch:{ Exception -> 0x002b }
        r5 = 2;
        r6 = 0;
        r4.transact(r5, r2, r3, r6);	 Catch:{ Exception -> 0x002b }
        r3.readException();	 Catch:{ Exception -> 0x002b }
        r4 = r3.readInt();	 Catch:{ Exception -> 0x002b }
        if (r4 == 0) goto L_0x0024;
    L_0x0023:
        r0 = r1;
    L_0x0024:
        r3.recycle();
        r2.recycle();
    L_0x002a:
        return r0;
    L_0x002b:
        r1 = move-exception;
        r1 = js.a();	 Catch:{ all -> 0x003e }
        r4 = "Fabric";
        r5 = "Could not get parcel from Google Play Service to capture Advertising limitAdTracking";
        r1.a(r4, r5);	 Catch:{ all -> 0x003e }
        r3.recycle();
        r2.recycle();
        goto L_0x002a;
    L_0x003e:
        r0 = move-exception;
        r3.recycle();
        r2.recycle();
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: kl$b.b():boolean");
    }
}
