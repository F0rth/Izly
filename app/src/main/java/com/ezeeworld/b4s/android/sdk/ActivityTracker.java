package com.ezeeworld.b4s.android.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Build.VERSION;
import android.os.Bundle;

import com.ezeeworld.b4s.android.sdk.notifications.B4SNotificationPopup;

import java.util.Timer;
import java.util.TimerTask;

public class ActivityTracker {
    private static ActivityTracker a;
    private B4SNotificationPopup b;
    private boolean c = false;
    private Timer d;

    public static class ApplicationToBackground {
        private ApplicationToBackground() {
        }
    }

    public static class ApplicationToForeground {
        private final Activity a;

        private ApplicationToForeground(Activity activity) {
            this.a = activity;
        }

        public Activity getCauseActivity() {
            return this.a;
        }
    }

    @TargetApi(14)
    private ActivityTracker(Application application) {
        if (VERSION.SDK_INT >= 14) {
            application.registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks(this) {
                final /* synthetic */ ActivityTracker a;

                {
                    this.a = r1;
                }

                public void onActivityCreated(Activity activity, Bundle bundle) {
                }

                public void onActivityDestroyed(Activity activity) {
                }

                public void onActivityPaused(Activity activity) {
                    this.a.a();
                }

                public void onActivityResumed(Activity activity) {
                    this.a.a(activity);
                }

                public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
                }

                public void onActivityStarted(Activity activity) {
                }

                public void onActivityStopped(Activity activity) {
                }
            });
        }
    }

    private void a() {
        if (this.b != null) {
            this.b.onPause();
            this.b = null;
        }
        this.d = new Timer();
        this.d.schedule(new TimerTask(this) {
            final /* synthetic */ ActivityTracker a;

            {
                this.a = r1;
            }

            public void run() {
                B4SLog.d(this.a, "Application to background");
                this.a.c = false;
                EventBus.get().post(new ApplicationToBackground());
                B4SUserProperty.get().uploadIfNeeded(null);
            }
        }, 5000);
    }

    private void a(Activity activity) {
        this.b = B4SNotificationPopup.bind(activity);
        this.b.onResume();
        if (!this.c) {
            B4SLog.d((Object) this, "Application launched");
            EventBus.get().post(new ApplicationToForeground(activity));
        }
        this.c = true;
        if (this.d != null) {
            this.d.cancel();
        }
        this.d = null;
    }

    static void a(Application application) {
        if (a == null) {
            a = new ActivityTracker(application);
        }
    }

    public static boolean isAppInForeground() {
        return a.c;
    }
}
