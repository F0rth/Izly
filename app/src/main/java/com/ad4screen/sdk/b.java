package com.ad4screen.sdk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.widget.FrameLayout.LayoutParams;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.analytics.Cart;
import com.ad4screen.sdk.analytics.Lead;
import com.ad4screen.sdk.analytics.Purchase;
import com.ad4screen.sdk.plugins.model.Beacon;

import java.util.ArrayList;
import java.util.List;

final class b extends A4S {
    protected b() {
    }

    protected final void a() {
    }

    protected final void a(int i, Callback<Inbox> callback, boolean z) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    protected final void a(Context context, boolean z, boolean z2) {
    }

    protected final void a(String str) {
    }

    protected final void a(String str, Callback<Message> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    protected final void a(String str, String str2, String[] strArr) {
    }

    protected final void b() {
    }

    protected final void b(String str) {
    }

    protected final void c() {
    }

    protected final void c(String str) {
    }

    protected final void d(String str) {
    }

    public final void displayInApp(String str) {
    }

    public final void getA4SId(Callback<String> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    public final String getAndroidId() {
        return "";
    }

    protected final ArrayList<Beacon> getBeacons() {
        return null;
    }

    public final void getIDFV(Callback<String> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    public final void getInbox(Callback<Inbox> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    public final void getListOfSubscriptions(Callback<List<StaticList>> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    public final void getPushToken(Callback<String> callback) {
    }

    public final void getSubscriptionStatusForLists(List<StaticList> list, Callback<List<StaticList>> callback) {
        if (callback != null) {
            callback.onResult(null);
        }
    }

    protected final void handleGeofencingMessage(Bundle bundle) {
    }

    protected final void handlePushMessage(Bundle bundle) {
    }

    public final void isGCMEnabled(Callback<Boolean> callback) {
        if (callback != null) {
            callback.onResult(Boolean.valueOf(false));
        }
    }

    public final void isInAppDisplayLocked(Callback<Boolean> callback) {
        if (callback != null) {
            callback.onResult(Boolean.valueOf(false));
        }
    }

    public final void isPushEnabled(Callback<Boolean> callback) {
        if (callback != null) {
            callback.onResult(Boolean.valueOf(false));
        }
    }

    public final boolean isPushNotificationLocked() {
        return false;
    }

    public final void isRestrictedConnection(Callback<Boolean> callback) {
        if (callback != null) {
            callback.onResult(Boolean.valueOf(false));
        }
    }

    public final void putState(String str, String str2) {
    }

    public final void refreshPushToken() {
    }

    public final void resetOverlayPosition() {
    }

    public final void sendGCMToken(String str) {
    }

    public final void sendPushToken(String str) {
    }

    public final void setGCMEnabled(boolean z) {
    }

    public final void setInAppClickedCallback(Callback<InApp> callback, int... iArr) {
    }

    public final void setInAppClosedCallback(Callback<InApp> callback, int... iArr) {
    }

    public final void setInAppDisplayLocked(boolean z) {
    }

    public final void setInAppDisplayedCallback(Callback<InApp> callback, int... iArr) {
    }

    public final void setInAppReadyCallback(boolean z, Callback<InApp> callback, int... iArr) {
    }

    public final void setIntent(Intent intent) {
    }

    public final void setOverlayPosition(LayoutParams layoutParams) {
    }

    public final void setPushEnabled(boolean z) {
    }

    public final void setPushNotificationLocked(boolean z) {
    }

    public final void setRestrictedConnection(boolean z) {
    }

    public final void setView(String str) {
    }

    public final void startActivity(Activity activity) {
    }

    public final void stopActivity(Activity activity) {
    }

    public final void subscribeToLists(List<StaticList> list) {
    }

    public final void subscribeToLists(StaticList... staticListArr) {
    }

    public final void trackAddToCart(Cart cart) {
    }

    public final void trackEvent(long j, String str, String... strArr) {
    }

    public final void trackLead(Lead lead) {
    }

    public final void trackPurchase(Purchase purchase) {
    }

    protected final void triggerBeacons(Bundle bundle) {
    }

    public final void unsubscribeFromLists(List<StaticList> list) {
    }

    public final void unsubscribeFromLists(StaticList... staticListArr) {
    }

    public final void updateDeviceInfo(Bundle bundle) {
    }

    public final void updateGeolocation(Location location) {
    }

    public final void updateMessages(Inbox inbox) {
    }

    protected final void updatePushRegistration(Bundle bundle) {
    }
}
