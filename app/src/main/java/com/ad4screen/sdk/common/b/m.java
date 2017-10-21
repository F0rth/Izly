package com.ad4screen.sdk.common.b;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.Notification.Builder;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.WebView;
import android.widget.RemoteViews;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Constants;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;

@SuppressLint({"InlinedApi"})
public final class m {

    public static final class a {
        public static void a(AlarmManager alarmManager, int i, long j, PendingIntent pendingIntent) {
            if (VERSION.SDK_INT >= 19) {
                e.a(alarmManager, i, j, pendingIntent);
            } else {
                alarmManager.set(i, j, pendingIntent);
            }
        }
    }

    public static final class b {

        public interface a {
            void a();

            void b();
        }

        @SuppressLint({"NewApi"})
        public static boolean a(Context context, View view, String str, final a aVar) {
            return (VERSION.SDK_INT < 11 || !a.a(context, view, str, new AnimatorListener() {
                public final void onAnimationCancel(Animator animator) {
                }

                public final void onAnimationEnd(Animator animator) {
                    if (aVar != null) {
                        aVar.b();
                    }
                }

                public final void onAnimationRepeat(Animator animator) {
                }

                public final void onAnimationStart(Animator animator) {
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            })) ? h.a(context, view, str, new AnimationListener() {
                public final void onAnimationEnd(Animation animation) {
                    if (aVar != null) {
                        aVar.b();
                    }
                }

                public final void onAnimationRepeat(Animation animation) {
                }

                public final void onAnimationStart(Animation animation) {
                    if (aVar != null) {
                        aVar.a();
                    }
                }
            }) : true;
        }
    }

    public static final class c {
        public static String a(byte[] bArr, int i) {
            return VERSION.SDK_INT >= 8 ? k.a(bArr, i) : h.a(bArr);
        }

        public static byte[] a(String str, int i) {
            return VERSION.SDK_INT >= 8 ? k.a(str, i) : h.a(str, i);
        }
    }

    public static final class d {
        public static int a(Context context, String... strArr) {
            return VERSION.SDK_INT >= 23 ? g.a(context, strArr) : 0;
        }

        public static File a(Context context) {
            return VERSION.SDK_INT >= 21 ? b(context) : VERSION.SDK_INT >= 8 ? k.a(context) : h.a(context);
        }

        public static File b(Context context) {
            return VERSION.SDK_INT >= 21 ? f.a(context) : a(context);
        }
    }

    public static final class e {
        public static void a(Activity activity) {
            if (VERSION.SDK_INT < 11) {
                h.a(activity);
            }
        }
    }

    public static final class f {
        public static int a(Context context) {
            return context.getResources().getConfiguration().orientation;
        }
    }

    public static final class g {
        public static boolean a(HandlerThread handlerThread) {
            return VERSION.SDK_INT >= 5 ? i.a(handlerThread) : h.a(handlerThread);
        }
    }

    public static final class h {
        public static void a(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
            try {
                Class cls = Class.forName("android.support.v4.content.LocalBroadcastManager");
                Object invoke = cls.getMethod("getInstance", new Class[]{Context.class}).invoke(cls, new Object[]{context});
                Method method = cls.getMethod("registerReceiver", new Class[]{BroadcastReceiver.class, IntentFilter.class});
                if (invoke != null) {
                    method.invoke(invoke, new Object[]{broadcastReceiver, intentFilter});
                    Log.internal("LocalBroadcastManager|Receiver registered");
                    return;
                }
                Log.internal("LocalBroadcastManager|Can't register receiver. Instance is null");
            } catch (Throwable e) {
                Log.internal("LocalBroadcastManager|Can't register receiver. Class not found?", e);
            }
        }
    }

    public static final class i {
        public static void a() {
            if (VERSION.SDK_INT >= 14) {
                b.a();
            }
        }

        public static void a(int i) {
            if (VERSION.SDK_INT >= 14) {
                b.a(i);
            }
        }
    }

    public static final class j {
        public static void a(PendingIntent pendingIntent) {
            if (VERSION.SDK_INT == 18 || VERSION.SDK_INT == 19) {
                pendingIntent.cancel();
            }
        }

        public static void a(final Context context, final PendingIntent pendingIntent, final com.ad4screen.sdk.service.modules.push.a.a aVar, final com.ad4screen.sdk.service.modules.push.d.b bVar) {
            Log.debug("Push|Downloading large icon..");
            a(context, aVar.l, new Callback<Bitmap>() {
                Builder a = null;
                Notification b = null;
                RemoteViews c;
                int d = com.ad4screen.sdk.common.i.d(context);
                String e = com.ad4screen.sdk.common.i.e(context);
                NotificationManager f = ((NotificationManager) context.getSystemService("notification"));

                private void a(Context context, com.ad4screen.sdk.service.modules.push.a.a aVar) {
                    Log.debug("Push|Adding action buttons to this notification...");
                    try {
                        JSONArray jSONArray = new JSONArray(aVar.u);
                        for (int i = 0; i < jSONArray.length(); i++) {
                            com.ad4screen.sdk.service.modules.push.c a = com.ad4screen.sdk.service.modules.push.c.a(context, aVar, jSONArray.getJSONObject(i));
                            Intent a2 = com.ad4screen.sdk.service.modules.push.g.a(context);
                            Bundle bundle = new Bundle();
                            com.ad4screen.sdk.c.a.e eVar = (com.ad4screen.sdk.c.a.e) a.c();
                            bundle.putString("a4sid", eVar.h);
                            bundle.putString("a4ssysid", String.valueOf(aVar.x));
                            bundle.putString("openWithSafari", eVar.b == com.ad4screen.sdk.c.a.e.a.Webview ? "false" : "true");
                            bundle.putString("a4surl", eVar.a.c);
                            bundle.putString("a4st", eVar.a.d);
                            bundle.putString("a4scontent", a.a());
                            bundle.putBoolean("isDestructive", a.d());
                            bundle.putString("isAlarm", aVar.A ? "true" : "false");
                            HashMap b = a.b();
                            if (!b.isEmpty()) {
                                for (Entry entry : b.entrySet()) {
                                    bundle.putString((String) entry.getKey(), (String) entry.getValue());
                                }
                            }
                            a2.putExtra(Constants.EXTRA_GCM_PAYLOAD, bundle);
                            c.a(this.a, a.a(context), a.a(), PendingIntent.getActivity(context, aVar.x + (i + 1), a2, 134217728));
                        }
                    } catch (Throwable e) {
                        Log.error("Push|Can't parse notification buttons.", e);
                    } catch (Throwable e2) {
                        Log.error("Push|Can't find activity to launch.", e2);
                    } catch (Throwable e22) {
                        Log.error("Push|Can't find activity to launch.", e22);
                    }
                }

                public final void a(Bitmap bitmap) {
                    boolean z = false;
                    if (VERSION.SDK_INT >= 19 && e.a(context, aVar.k) >= 0) {
                        this.d = e.a(context, aVar.k);
                        z = true;
                    }
                    if (aVar.m != null && aVar.m.length() > 0) {
                        this.e = aVar.m;
                    }
                    this.c = j.b(context, this.d, aVar.k, aVar.q, this.e, aVar.n, bitmap, z, aVar.j);
                    Uri uri = null;
                    if (!(aVar.s == null || TextUtils.isEmpty(aVar.s) || aVar.s.equalsIgnoreCase("default") || aVar.s.equalsIgnoreCase("none"))) {
                        int identifier = context.getResources().getIdentifier(aVar.s, "raw", context.getPackageName());
                        if (identifier > 0) {
                            uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + identifier);
                            Log.internal("Push|Using " + aVar.s + " as notification sound");
                        } else {
                            Log.error("Push|Could not find " + aVar.s + " in raw folder, we will use default sound instead");
                        }
                    }
                    if (VERSION.SDK_INT >= 11) {
                        this.a = a.a(context, uri, this.e, this.d, bitmap, aVar, pendingIntent);
                        if (VERSION.SDK_INT >= 21) {
                            this.a = f.a(context, aVar.h, aVar.i, aVar.j, z, this.a);
                        }
                        if (this.c != null) {
                            this.a = a.a(this.a, this.c);
                        }
                    } else {
                        this.b = k.a(context, uri, this.d, aVar.n, aVar, pendingIntent);
                        if (this.c != null) {
                            this.b.contentView = this.c;
                        }
                    }
                    if (VERSION.SDK_INT >= 16) {
                        if (aVar.u != null) {
                            a(context, aVar);
                        }
                        c.a(context, this.e, com.ad4screen.sdk.common.i.e(context), this.d, z, aVar.j, bitmap, aVar, this.a, new Callback<Notification>(this) {
                            final /* synthetic */ AnonymousClass1 a;

                            {
                                this.a = r1;
                            }

                            public void a(Notification notification) {
                                Log.debug("Push|Notification is ready, displaying...");
                                j.b(this.a.f, aVar.x, notification, bVar);
                            }

                            public void onError(int i, String str) {
                            }

                            public /* synthetic */ void onResult(Object obj) {
                                a((Notification) obj);
                            }
                        });
                        return;
                    }
                    Log.debug("Push|Notification is ready, displaying...");
                    if (VERSION.SDK_INT >= 11) {
                        this.b = a.a(this.a);
                    }
                    j.b(this.f, aVar.x, this.b, bVar);
                }

                public final void onError(int i, String str) {
                }

                public final /* synthetic */ void onResult(Object obj) {
                    a((Bitmap) obj);
                }
            });
        }

        private static void a(Context context, String str, final Callback<Bitmap> callback) {
            if (VERSION.SDK_INT < 11) {
                Log.debug("Push|LargeIcon is not supported on API < 11.. No download needed");
                if (callback != null) {
                    callback.onResult(null);
                }
            } else if (str == null) {
                Log.debug("Push|No icon to download..");
                if (callback != null) {
                    callback.onResult(null);
                }
            } else {
                com.ad4screen.sdk.common.i.a(com.ad4screen.sdk.common.h.a(context, str, new com.ad4screen.sdk.common.e("iconsize", b(context))), new Callback<Bitmap>() {
                    public final void a(Bitmap bitmap) {
                        Log.debug("Push|Large Icon successfully downloaded");
                        if (callback != null) {
                            callback.onResult(bitmap);
                        }
                    }

                    public final void onError(int i, String str) {
                        Log.warn("Push|Can't download provided large icon");
                        if (callback != null) {
                            callback.onResult(null);
                        }
                    }

                    public final /* synthetic */ void onResult(Object obj) {
                        a((Bitmap) obj);
                    }
                }, true);
            }
        }

        public static boolean a(Context context) {
            return VERSION.SDK_INT >= 19 ? e.a(context) : VERSION.SDK_INT >= 18 ? d.a(context) : false;
        }

        private static RemoteViews b(Context context, int i, String str, String str2, String str3, String str4, Bitmap bitmap, boolean z, int i2) {
            RemoteViews remoteViews = null;
            if (str2 != null) {
                String packageName = context.getPackageName();
                int identifier = context.getResources().getIdentifier(str2, "layout", packageName);
                if (identifier == 0) {
                    Log.warn("Notification|Wrong template provided : " + str2 + " using default");
                } else {
                    remoteViews = new RemoteViews(packageName, identifier);
                    remoteViews.setTextViewText(R.id.com_ad4screen_sdk_title, str3);
                    remoteViews.setTextViewText(R.id.com_ad4screen_sdk_body, str4);
                    if (VERSION.SDK_INT >= 16) {
                        remoteViews.setImageViewResource(R.id.com_ad4screen_sdk_logo, i);
                        if (bitmap != null) {
                            remoteViews.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, bitmap);
                        } else if (z && VERSION.SDK_INT >= 21) {
                            remoteViews.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, f.a(context, i, i2));
                        }
                    } else {
                        remoteViews.setViewVisibility(R.id.com_ad4screen_sdk_logo, 8);
                    }
                }
            }
            return remoteViews;
        }

        private static String b(Context context) {
            switch (com.ad4screen.sdk.d.b.a(context).B()) {
                case hdpi:
                    return "36";
                case xhdpi:
                    return "48";
                case xxhdpi:
                    return "72";
                case xxxhdpi:
                    return "96";
                default:
                    return "24";
            }
        }

        private static void b(NotificationManager notificationManager, int i, Notification notification, com.ad4screen.sdk.service.modules.push.d.b bVar) {
            notificationManager.notify(i, notification);
            if (bVar != null) {
                bVar.a();
            }
        }
    }

    public static final class k {
        public static long a(PackageInfo packageInfo) {
            return VERSION.SDK_INT >= 9 ? l.a(packageInfo) : 0;
        }
    }

    public static final class l {
        public static void a(Context context) {
            if (VERSION.SDK_INT >= 19) {
                e.a();
            }
        }

        public static void a(WebView webView) {
            if ((webView.getBackground() instanceof ColorDrawable) && VERSION.SDK_INT >= 11) {
                a.a(webView);
            }
        }

        public static void a(WebView webView, boolean z) {
            if (VERSION.SDK_INT >= 7) {
                j.a(webView, z);
            }
        }

        public static void b(WebView webView, boolean z) {
            if (VERSION.SDK_INT >= 11) {
                a.a(webView, z);
            }
        }

        public static void c(WebView webView, boolean z) {
            if (VERSION.SDK_INT >= 7) {
                j.b(webView, z);
            } else {
                webView.setInitialScale(1);
            }
        }

        public static void d(WebView webView, boolean z) {
            if (VERSION.SDK_INT >= 18) {
                d.a(webView, z);
            } else {
                h.a(webView, z);
            }
        }
    }

    public static String a(Context context) {
        return VERSION.SDK_INT >= 7 ? j.a() : h.b(context);
    }

    public static void a(ViewGroup viewGroup, ArrayList<View> arrayList, int i) {
        for (int i2 = 0; i2 < viewGroup.getChildCount(); i2++) {
            View childAt = viewGroup.getChildAt(i2);
            if (childAt.getId() == i) {
                arrayList.add(childAt);
            }
            if (childAt instanceof ViewGroup) {
                a((ViewGroup) childAt, arrayList, i);
            }
        }
    }
}
