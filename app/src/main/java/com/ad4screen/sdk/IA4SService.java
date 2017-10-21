package com.ad4screen.sdk;

import android.location.Location;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ResultReceiver;

import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.b.a;
import com.ad4screen.sdk.b.c;
import com.ad4screen.sdk.service.modules.k.c.e;

import java.util.List;

public interface IA4SService extends IInterface {

    public static abstract class Stub extends Binder implements IA4SService {

        static class a implements IA4SService {
            private IBinder a;

            a(IBinder iBinder) {
                this.a = iBinder;
            }

            public IBinder asBinder() {
                return this.a;
            }

            public void clickButtonMessage(com.ad4screen.sdk.b.a aVar, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (aVar != null) {
                        obtain.writeInt(1);
                        aVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeString(str);
                    this.a.transact(15, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void clientStarted() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(34, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void closeCurrentInApp() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(22, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void closedPush(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(53, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void displayInApp(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(51, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void displayMessage(c cVar) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (cVar != null) {
                        obtain.writeInt(1);
                        cVar.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(14, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void doAction(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(38, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getA4SId(e eVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (eVar != null) {
                        iBinder = eVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(29, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getActiveMember(e eVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (eVar != null) {
                        iBinder = eVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(32, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getIDFV(e eVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (eVar != null) {
                        iBinder = eVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(30, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getListOfSubscriptions(j jVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (jVar != null) {
                        iBinder = jVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(28, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getMembers(f fVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (fVar != null) {
                        iBinder = fVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(31, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getMessagesDetails(String[] strArr, h hVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeStringArray(strArr);
                    if (hVar != null) {
                        iBinder = hVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(9, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getMessagesList(h hVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (hVar != null) {
                        iBinder = hVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(8, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getPushToken(g gVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (gVar != null) {
                        iBinder = gVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(33, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void getSubscriptionStatusForLists(List<e> list, j jVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeTypedList(list);
                    if (jVar != null) {
                        iBinder = jVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(27, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void handleGeofencingMessage(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(42, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void handlePushMessage(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(41, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void interstitialClosed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(36, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void interstitialDisplayed() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(35, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public boolean isInAppDisplayLocked() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(21, obtain, obtain2, 0);
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

            public boolean isPushEnabled() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(16, obtain, obtain2, 0);
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

            public boolean isRestrictedConnection() throws RemoteException {
                boolean z = false;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(18, obtain, obtain2, 0);
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

            public void logIn(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(11, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void logOut() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(12, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onInAppClicked(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(47, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onInAppClosed(String str, boolean z) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    this.a.transact(48, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onInAppDisplayed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(46, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void onInAppReady(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.a.transact(49, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void openedPush(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(52, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void putState(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.a.transact(54, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void refreshPushToken() throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    this.a.transact(39, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void removeMembers(String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeStringArray(strArr);
                    this.a.transact(13, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setClientCallback(ResultReceiver resultReceiver) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (resultReceiver != null) {
                        obtain.writeInt(1);
                        resultReceiver.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(37, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setDoNotTrack(boolean z, boolean z2, boolean z3, d dVar) throws RemoteException {
                IBinder iBinder = null;
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeInt(z ? 1 : 0);
                    obtain.writeInt(z2 ? 1 : 0);
                    if (!z3) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    if (dVar != null) {
                        iBinder = dVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(56, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setInAppDisplayLocked(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.a.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setInAppReadyCallback(boolean z, int[] iArr) throws RemoteException {
                int i = 1;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (!z) {
                        i = 0;
                    }
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.a.transact(50, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setPushEnabled(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.a.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setRestrictedConnection(boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.a.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            public void setSource(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(55, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void setView(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void startActivity(String str, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.a.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void stopActivity(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void subscribeToLists(List<e> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeTypedList(list);
                    this.a.transact(25, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void trackAddToCart(Cart cart, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (cart != null) {
                        obtain.writeInt(1);
                        cart.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void trackEvent(long j, String[] strArr, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeLong(j);
                    obtain.writeStringArray(strArr);
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void trackLead(Lead lead, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (lead != null) {
                        obtain.writeInt(1);
                        lead.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(7, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void trackPurchase(Purchase purchase, Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (purchase != null) {
                        obtain.writeInt(1);
                        purchase.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void trackReferrer(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    this.a.transact(44, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void triggerBeacons(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(43, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void unsubscribeFromLists(List<e> list) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeTypedList(list);
                    this.a.transact(26, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void updateGeolocation(Location location) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (location != null) {
                        obtain.writeInt(1);
                        location.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(24, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void updateMessages(c[] cVarArr, h hVar) throws RemoteException {
                IBinder iBinder = null;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeTypedArray(cVarArr, 0);
                    if (hVar != null) {
                        iBinder = hVar.asBinder();
                    }
                    obtain.writeStrongBinder(iBinder);
                    this.a.transact(10, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void updatePushRegistration(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    this.a.transact(40, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void updateUserPreferences(Bundle bundle, boolean z) throws RemoteException {
                int i = 0;
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    if (bundle != null) {
                        obtain.writeInt(1);
                        bundle.writeToParcel(obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    if (z) {
                        i = 1;
                    }
                    obtain.writeInt(i);
                    this.a.transact(23, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            public void uploadFacebookProfile(String str, String str2, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.ad4screen.sdk.IA4SService");
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeStringArray(strArr);
                    this.a.transact(45, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.ad4screen.sdk.IA4SService");
        }

        public static IA4SService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface("com.ad4screen.sdk.IA4SService");
            return (queryLocalInterface == null || !(queryLocalInterface instanceof IA4SService)) ? new a(iBinder) : (IA4SService) queryLocalInterface;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            boolean z = false;
            boolean isPushEnabled;
            int i3;
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    startActivity(parcel.readString(), parcel.readString(), parcel.readString());
                    return true;
                case 2:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    stopActivity(parcel.readString());
                    return true;
                case 3:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    setView(parcel.readString());
                    return true;
                case 4:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    trackEvent(parcel.readLong(), parcel.createStringArray(), parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 5:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    trackPurchase(parcel.readInt() != 0 ? (Purchase) Purchase.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 6:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    trackAddToCart(parcel.readInt() != 0 ? (Cart) Cart.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 7:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    trackLead(parcel.readInt() != 0 ? (Lead) Lead.CREATOR.createFromParcel(parcel) : null, parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 8:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getMessagesList(com.ad4screen.sdk.h.a.a(parcel.readStrongBinder()));
                    return true;
                case 9:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getMessagesDetails(parcel.createStringArray(), com.ad4screen.sdk.h.a.a(parcel.readStrongBinder()));
                    return true;
                case 10:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    updateMessages((c[]) parcel.createTypedArray(c.CREATOR), com.ad4screen.sdk.h.a.a(parcel.readStrongBinder()));
                    return true;
                case 11:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    logIn(parcel.readString());
                    return true;
                case 12:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    logOut();
                    return true;
                case 13:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    removeMembers(parcel.createStringArray());
                    return true;
                case 14:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    displayMessage(parcel.readInt() != 0 ? (c) c.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 15:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    clickButtonMessage(parcel.readInt() != 0 ? (com.ad4screen.sdk.b.a) com.ad4screen.sdk.b.a.CREATOR.createFromParcel(parcel) : null, parcel.readString());
                    return true;
                case 16:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    isPushEnabled = isPushEnabled();
                    parcel2.writeNoException();
                    parcel2.writeInt(isPushEnabled ? 1 : 0);
                    return true;
                case 17:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setPushEnabled(z);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    isPushEnabled = isRestrictedConnection();
                    parcel2.writeNoException();
                    if (isPushEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 19:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setRestrictedConnection(z);
                    parcel2.writeNoException();
                    return true;
                case 20:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setInAppDisplayLocked(z);
                    parcel2.writeNoException();
                    return true;
                case 21:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    isPushEnabled = isInAppDisplayLocked();
                    parcel2.writeNoException();
                    if (isPushEnabled) {
                        i3 = 1;
                    }
                    parcel2.writeInt(i3);
                    return true;
                case 22:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    closeCurrentInApp();
                    return true;
                case 23:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    Bundle bundle = parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null;
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    updateUserPreferences(bundle, z);
                    return true;
                case 24:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    updateGeolocation(parcel.readInt() != 0 ? (Location) Location.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 25:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    subscribeToLists(parcel.createTypedArrayList(e.CREATOR));
                    return true;
                case 26:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    unsubscribeFromLists(parcel.createTypedArrayList(e.CREATOR));
                    return true;
                case 27:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getSubscriptionStatusForLists(parcel.createTypedArrayList(e.CREATOR), com.ad4screen.sdk.j.a.a(parcel.readStrongBinder()));
                    return true;
                case 28:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getListOfSubscriptions(com.ad4screen.sdk.j.a.a(parcel.readStrongBinder()));
                    return true;
                case 29:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getA4SId(com.ad4screen.sdk.e.a.a(parcel.readStrongBinder()));
                    return true;
                case 30:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getIDFV(com.ad4screen.sdk.e.a.a(parcel.readStrongBinder()));
                    return true;
                case 31:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getMembers(com.ad4screen.sdk.f.a.a(parcel.readStrongBinder()));
                    return true;
                case 32:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getActiveMember(com.ad4screen.sdk.e.a.a(parcel.readStrongBinder()));
                    return true;
                case 33:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    getPushToken(com.ad4screen.sdk.g.a.a(parcel.readStrongBinder()));
                    return true;
                case 34:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    clientStarted();
                    return true;
                case 35:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    interstitialDisplayed();
                    return true;
                case 36:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    interstitialClosed();
                    return true;
                case 37:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    setClientCallback(parcel.readInt() != 0 ? (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 38:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    doAction(parcel.readString());
                    return true;
                case 39:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    refreshPushToken();
                    return true;
                case 40:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    updatePushRegistration(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 41:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    handlePushMessage(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 42:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    handleGeofencingMessage(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 43:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    triggerBeacons(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 44:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    trackReferrer(parcel.readString());
                    return true;
                case 45:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    uploadFacebookProfile(parcel.readString(), parcel.readString(), parcel.createStringArray());
                    return true;
                case 46:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    onInAppDisplayed(parcel.readString());
                    return true;
                case 47:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    onInAppClicked(parcel.readString(), parcel.readString());
                    return true;
                case 48:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    String readString = parcel.readString();
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    onInAppClosed(readString, z);
                    return true;
                case 49:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    onInAppReady(parcel.readString(), parcel.readInt());
                    return true;
                case 50:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setInAppReadyCallback(z, parcel.createIntArray());
                    return true;
                case 51:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    displayInApp(parcel.readString());
                    return true;
                case 52:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    openedPush(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 53:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    closedPush(parcel.readInt() != 0 ? (Bundle) Bundle.CREATOR.createFromParcel(parcel) : null);
                    return true;
                case 54:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    putState(parcel.readString(), parcel.readString());
                    return true;
                case 55:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    setSource(parcel.readString());
                    return true;
                case 56:
                    parcel.enforceInterface("com.ad4screen.sdk.IA4SService");
                    isPushEnabled = parcel.readInt() != 0;
                    boolean z2 = parcel.readInt() != 0;
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    setDoNotTrack(isPushEnabled, z2, z, com.ad4screen.sdk.d.a.a(parcel.readStrongBinder()));
                    return true;
                case 1598968902:
                    parcel2.writeString("com.ad4screen.sdk.IA4SService");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void clickButtonMessage(a aVar, String str) throws RemoteException;

    void clientStarted() throws RemoteException;

    void closeCurrentInApp() throws RemoteException;

    void closedPush(Bundle bundle) throws RemoteException;

    void displayInApp(String str) throws RemoteException;

    void displayMessage(c cVar) throws RemoteException;

    void doAction(String str) throws RemoteException;

    void getA4SId(e eVar) throws RemoteException;

    void getActiveMember(e eVar) throws RemoteException;

    void getIDFV(e eVar) throws RemoteException;

    void getListOfSubscriptions(j jVar) throws RemoteException;

    void getMembers(f fVar) throws RemoteException;

    void getMessagesDetails(String[] strArr, h hVar) throws RemoteException;

    void getMessagesList(h hVar) throws RemoteException;

    void getPushToken(g gVar) throws RemoteException;

    void getSubscriptionStatusForLists(List<e> list, j jVar) throws RemoteException;

    void handleGeofencingMessage(Bundle bundle) throws RemoteException;

    void handlePushMessage(Bundle bundle) throws RemoteException;

    void interstitialClosed() throws RemoteException;

    void interstitialDisplayed() throws RemoteException;

    boolean isInAppDisplayLocked() throws RemoteException;

    boolean isPushEnabled() throws RemoteException;

    boolean isRestrictedConnection() throws RemoteException;

    void logIn(String str) throws RemoteException;

    void logOut() throws RemoteException;

    void onInAppClicked(String str, String str2) throws RemoteException;

    void onInAppClosed(String str, boolean z) throws RemoteException;

    void onInAppDisplayed(String str) throws RemoteException;

    void onInAppReady(String str, int i) throws RemoteException;

    void openedPush(Bundle bundle) throws RemoteException;

    void putState(String str, String str2) throws RemoteException;

    void refreshPushToken() throws RemoteException;

    void removeMembers(String[] strArr) throws RemoteException;

    void setClientCallback(ResultReceiver resultReceiver) throws RemoteException;

    void setDoNotTrack(boolean z, boolean z2, boolean z3, d dVar) throws RemoteException;

    void setInAppDisplayLocked(boolean z) throws RemoteException;

    void setInAppReadyCallback(boolean z, int[] iArr) throws RemoteException;

    void setPushEnabled(boolean z) throws RemoteException;

    void setRestrictedConnection(boolean z) throws RemoteException;

    void setSource(String str) throws RemoteException;

    void setView(String str) throws RemoteException;

    void startActivity(String str, String str2, String str3) throws RemoteException;

    void stopActivity(String str) throws RemoteException;

    void subscribeToLists(List<e> list) throws RemoteException;

    void trackAddToCart(Cart cart, Bundle bundle) throws RemoteException;

    void trackEvent(long j, String[] strArr, Bundle bundle) throws RemoteException;

    void trackLead(Lead lead, Bundle bundle) throws RemoteException;

    void trackPurchase(Purchase purchase, Bundle bundle) throws RemoteException;

    void trackReferrer(String str) throws RemoteException;

    void triggerBeacons(Bundle bundle) throws RemoteException;

    void unsubscribeFromLists(List<e> list) throws RemoteException;

    void updateGeolocation(Location location) throws RemoteException;

    void updateMessages(c[] cVarArr, h hVar) throws RemoteException;

    void updatePushRegistration(Bundle bundle) throws RemoteException;

    void updateUserPreferences(Bundle bundle, boolean z) throws RemoteException;

    void uploadFacebookProfile(String str, String str2, String[] strArr) throws RemoteException;
}
