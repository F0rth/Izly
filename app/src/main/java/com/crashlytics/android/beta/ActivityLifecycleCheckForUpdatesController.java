package com.crashlytics.android.beta;

import android.annotation.TargetApi;
import android.app.Activity;
import java.util.concurrent.ExecutorService;

@TargetApi(14)
class ActivityLifecycleCheckForUpdatesController extends AbstractCheckForUpdatesController {
    private final b callbacks = new b() {
        public void onActivityStarted(Activity activity) {
            if (ActivityLifecycleCheckForUpdatesController.this.signalExternallyReady()) {
                ActivityLifecycleCheckForUpdatesController.this.executorService.submit(new Runnable() {
                    public void run() {
                        ActivityLifecycleCheckForUpdatesController.this.checkForUpdates();
                    }
                });
            }
        }
    };
    private final ExecutorService executorService;

    public ActivityLifecycleCheckForUpdatesController(jq jqVar, ExecutorService executorService) {
        this.executorService = executorService;
        jqVar.a(this.callbacks);
    }

    public boolean isActivityLifecycleTriggered() {
        return true;
    }
}
