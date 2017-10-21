package com.ad4screen.sdk.common.b;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorInflater;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;
import android.widget.RemoteViews;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.common.i;
import com.google.android.gms.common.api.CommonStatusCodes;

@TargetApi(11)
public final class a {
    public static Builder a(Builder builder, RemoteViews remoteViews) {
        return builder.setContent(remoteViews);
    }

    public static Builder a(Context context, Uri uri, String str, int i, Bitmap bitmap, com.ad4screen.sdk.service.modules.push.a.a aVar, PendingIntent pendingIntent) {
        int i2 = 0;
        Builder builder = new Builder(context);
        if (bitmap != null) {
            builder.setLargeIcon(bitmap);
        }
        builder.setAutoCancel(true).setSmallIcon(i).setTicker(aVar.n).setContentTitle(str).setContentText(aVar.n).setContentIntent(pendingIntent).setLights(-1, 1000, CommonStatusCodes.AUTH_API_INVALID_CREDENTIALS).setNumber(0);
        if (i.a(context, "android.permission.VIBRATE")) {
            i2 = 2;
        }
        if (uri != null) {
            builder.setSound(uri);
        } else if (aVar.s == null || !aVar.s.equalsIgnoreCase("none")) {
            i2 |= 1;
        }
        builder.setDefaults(i2);
        return builder;
    }

    public static Notification a(Builder builder) {
        return builder.getNotification();
    }

    public static void a(WebView webView) {
        webView.setBackgroundColor(((ColorDrawable) webView.getBackground()).getColor());
    }

    public static void a(WebView webView, boolean z) {
        webView.getSettings().setDisplayZoomControls(z);
    }

    public static boolean a(Context context, View view, String str, AnimatorListener animatorListener) {
        try {
            int identifier = context.getResources().getIdentifier(str, "animator", context.getPackageName());
            if (identifier != 0) {
                Animator loadAnimator = AnimatorInflater.loadAnimator(context, identifier);
                loadAnimator.addListener(animatorListener);
                loadAnimator.setTarget(view);
                loadAnimator.start();
                return true;
            }
        } catch (Throwable e) {
            Log.warn("Animation|Could not use Property Animation : " + str, e);
        }
        return false;
    }
}
