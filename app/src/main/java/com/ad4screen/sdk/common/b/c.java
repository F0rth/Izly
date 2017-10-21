package com.ad4screen.sdk.common.b;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.Notification.Style;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import com.ad4screen.sdk.A4S.Callback;
import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.R;
import com.ad4screen.sdk.common.i;
import com.ad4screen.sdk.service.modules.push.a.a;
import com.ad4screen.sdk.service.modules.push.e;
import com.ad4screen.sdk.service.modules.push.f;

@TargetApi(16)
public final class c {
    public static Builder a(Builder builder, int i, String str, PendingIntent pendingIntent) {
        builder.addAction(i, str, pendingIntent);
        return builder;
    }

    public static Notification a(Builder builder, Bitmap bitmap, String str, String str2) {
        if (builder == null) {
            return null;
        }
        Style bigPictureStyle = new BigPictureStyle(builder);
        if (bitmap != null) {
            bigPictureStyle.bigPicture(bitmap);
        }
        if (str2 != null) {
            bigPictureStyle.setSummaryText(str2);
        } else if (str != null) {
            bigPictureStyle.setSummaryText(str);
        }
        builder.setStyle(bigPictureStyle);
        return builder.build();
    }

    public static void a(Context context, String str, final String str2, int i, boolean z, int i2, Bitmap bitmap, final a aVar, final Builder builder, final Callback<Notification> callback) {
        if (aVar.g) {
            if (aVar.r == null || aVar.r.equals("BigTextStyle") || aVar.r.equals("InboxStyle")) {
                builder.setStyle(b(str2, aVar.o, builder));
                if (callback != null) {
                    callback.onResult(builder.build());
                }
            } else if (!aVar.r.equals("BigPictureStyle")) {
                String packageName = context.getPackageName();
                int i3 = 0;
                if (e.a(context) == f.a.ADM) {
                    i3 = context.getResources().getIdentifier(aVar.r + "_amazon", "layout", packageName);
                }
                int identifier = i3 == 0 ? context.getResources().getIdentifier(aVar.r, "layout", packageName) : i3;
                if (identifier == 0) {
                    Log.warn("Notification|Wrong big template provided : " + aVar.r + " using default");
                    builder.setStyle(b(str2, aVar.o, builder));
                    if (callback != null) {
                        callback.onResult(builder.build());
                        return;
                    }
                    return;
                }
                final RemoteViews remoteViews = new RemoteViews(packageName, identifier);
                remoteViews.setTextViewText(R.id.com_ad4screen_sdk_title, str);
                remoteViews.setTextViewText(R.id.com_ad4screen_sdk_body, aVar.o);
                remoteViews.setImageViewResource(R.id.com_ad4screen_sdk_logo, i);
                remoteViews.setViewVisibility(R.id.com_ad4screen_sdk_picture, 8);
                if (bitmap != null) {
                    remoteViews.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, bitmap);
                } else if (z && VERSION.SDK_INT >= 21) {
                    remoteViews.setImageViewBitmap(R.id.com_ad4screen_sdk_logo, f.a(context, i, i2));
                }
                final Notification build = builder.build();
                build.bigContentView = remoteViews;
                View inflate = ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(identifier, null);
                if (inflate == null || inflate.findViewById(R.id.com_ad4screen_sdk_picture) == null) {
                    if (callback != null) {
                        callback.onResult(build);
                    }
                } else if (aVar.p != null) {
                    Log.debug("Push|Downloading picture...");
                    i.a(aVar.p, new Callback<Bitmap>() {
                        public final void a(Bitmap bitmap) {
                            Log.debug("Push|Picture successfully downloaded");
                            Log.debug("Push|Displaying notification with picture...");
                            if (bitmap != null) {
                                remoteViews.setImageViewBitmap(R.id.com_ad4screen_sdk_picture, bitmap);
                                remoteViews.setViewVisibility(R.id.com_ad4screen_sdk_picture, 0);
                                if (callback != null) {
                                    callback.onResult(build);
                                }
                            }
                        }

                        public final void onError(int i, String str) {
                            Log.warn("Push|Can't download provided picture.");
                            Log.debug("Push|Displaying notification...");
                            if (callback != null) {
                                callback.onResult(build);
                            }
                        }

                        public final /* synthetic */ void onResult(Object obj) {
                            a((Bitmap) obj);
                        }
                    }, true);
                } else if (callback != null) {
                    callback.onResult(build);
                }
            } else if (aVar.p == null) {
                builder.setStyle(b(str2, aVar.o, builder));
                if (callback != null) {
                    callback.onResult(builder.build());
                }
            } else {
                Log.debug("Push|Downloading picture...");
                i.a(aVar.p, new Callback<Bitmap>() {
                    public final void a(Bitmap bitmap) {
                        Log.debug("Push|Picture successfully downloaded");
                        Log.debug("Push|Displaying notification with picture...");
                        c.a(builder, bitmap, aVar.n, aVar.o);
                        if (callback != null) {
                            callback.onResult(builder.build());
                        }
                    }

                    public final void onError(int i, String str) {
                        Log.warn("Push|Can't download provided picture.");
                        Log.debug("Push|Displaying notification...");
                        builder.setStyle(c.b(str2, aVar.o, builder));
                        if (callback != null) {
                            callback.onResult(builder.build());
                        }
                    }

                    public final /* synthetic */ void onResult(Object obj) {
                        a((Bitmap) obj);
                    }
                }, true);
            }
        } else if (callback != null) {
            callback.onResult(builder.build());
        }
    }

    private static BigTextStyle b(String str, String str2, Builder builder) {
        BigTextStyle bigTextStyle = new BigTextStyle(builder);
        if (str2 != null) {
            bigTextStyle.bigText(str2);
        }
        if (str != null) {
            bigTextStyle.setSummaryText(str);
        }
        return bigTextStyle;
    }
}
