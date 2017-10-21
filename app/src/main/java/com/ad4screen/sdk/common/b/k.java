package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.util.Base64;

import com.ad4screen.sdk.common.g;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.push.a.a;
import com.google.android.gms.common.api.CommonStatusCodes;

import java.io.File;

@TargetApi(8)
public final class k {
    public static Notification a(Context context, Uri uri, int i, String str, a aVar, PendingIntent pendingIntent) {
        Notification notification = new Notification(i, str, g.e().b());
        notification.contentIntent = pendingIntent;
        notification.audioStreamType = -1;
        if (i.a(context, "android.permission.VIBRATE")) {
            notification.defaults = 2;
        }
        if (uri != null) {
            notification.sound = uri;
        } else if (aVar.s == null || !aVar.s.equalsIgnoreCase("none")) {
            notification.defaults |= 1;
        }
        notification.flags = 17;
        notification.number = 0;
        notification.tickerText = str;
        notification.ledARGB = -1;
        notification.ledOffMS = CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS;
        notification.ledOnMS = 1000;
        return notification;
    }

    public static File a(Context context) {
        return context.getExternalFilesDir(null);
    }

    public static String a(byte[] bArr, int i) {
        return Base64.encodeToString(bArr, i);
    }

    public static byte[] a(String str, int i) {
        return Base64.decode(str, i);
    }
}
