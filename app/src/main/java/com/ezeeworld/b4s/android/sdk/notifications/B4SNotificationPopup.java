package com.ezeeworld.b4s.android.sdk.notifications;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;

import com.ezeeworld.b4s.android.sdk.B4SLog;
import com.ezeeworld.b4s.android.sdk.EventBus;
import com.ezeeworld.b4s.android.sdk.server.InteractionsApi;

public class B4SNotificationPopup {
    private Context a;
    private String b;
    private boolean c = false;
    private AlertDialog d;

    private B4SNotificationPopup(Activity activity) {
        this.a = activity;
    }

    public static B4SNotificationPopup bind(Activity activity) {
        return new B4SNotificationPopup(activity);
    }

    @TargetApi(17)
    public void onEventMainThread(final PreparedNotification preparedNotification) {
        if (preparedNotification.e) {
            if (preparedNotification.c != null) {
                new a(preparedNotification.c, preparedNotification.d, preparedNotification.b, InteractionsApi.B4SSATUS_SENT).execute(new Context[]{this.a});
            }
            if (this.c) {
                B4SLog.w((Object) this, "Notification will not be shown, as there is already a B4S notification popup open on the screen!");
                return;
            }
            final Intent intent = preparedNotification.l;
            final boolean z = preparedNotification.m;
            final String str = preparedNotification.c;
            final String str2 = preparedNotification.b;
            this.b = InteractionsApi.B4SSATUS_REJECTED;
            this.d = new Builder(this.a).setTitle(preparedNotification.h).setMessage(preparedNotification.i).setNegativeButton(preparedNotification.k, null).setPositiveButton(preparedNotification.j, new OnClickListener(this) {
                final /* synthetic */ B4SNotificationPopup c;

                public void onClick(DialogInterface dialogInterface, int i) {
                    this.c.b = InteractionsApi.B4SSATUS_ACCEPTED;
                    try {
                        if (z) {
                            this.c.a.sendBroadcast(intent);
                        } else {
                            this.c.a.startActivity(intent);
                        }
                    } catch (Exception e) {
                        B4SLog.w(this.c, "Unsupported intent scheme to start an activity for: " + e.toString());
                    }
                    dialogInterface.dismiss();
                }
            }).setOnDismissListener(new OnDismissListener(this) {
                final /* synthetic */ B4SNotificationPopup d;

                public void onDismiss(DialogInterface dialogInterface) {
                    if (str != null) {
                        new a(str, preparedNotification.d, str2, this.d.b).execute(new Context[]{this.d.a});
                    }
                    this.d.c = false;
                    this.d.d = null;
                }
            }).create();
            if (!(this.a == null || ((this.a instanceof Activity) && ((Activity) this.a).isFinishing()))) {
                this.d.show();
            }
            this.c = true;
            return;
        }
        if (preparedNotification.m) {
            this.a.sendBroadcast(preparedNotification.l);
        } else {
            this.a.startActivity(preparedNotification.l);
        }
        if (preparedNotification.c != null) {
            new a(preparedNotification.c, preparedNotification.d, preparedNotification.b, InteractionsApi.B4SSATUS_OPENED).execute(new Context[]{this.a});
        }
    }

    public void onPause() {
        EventBus.get().unregister(this);
        if (this.d != null) {
            this.d.dismiss();
        }
    }

    public void onResume() {
        EventBus.get().register(this);
    }
}
