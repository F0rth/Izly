package defpackage;

import android.app.Activity;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;

final class jq$a$1 implements ActivityLifecycleCallbacks {
    final /* synthetic */ jq$b a;
    final /* synthetic */ jq$a b;

    jq$a$1(jq$a jq_a, jq$b jq_b) {
        this.b = jq_a;
        this.a = jq_b;
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        this.a.onActivityCreated(activity, bundle);
    }

    public final void onActivityDestroyed(Activity activity) {
        this.a.onActivityDestroyed(activity);
    }

    public final void onActivityPaused(Activity activity) {
        this.a.onActivityPaused(activity);
    }

    public final void onActivityResumed(Activity activity) {
        this.a.onActivityResumed(activity);
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.a.onActivitySaveInstanceState(activity, bundle);
    }

    public final void onActivityStarted(Activity activity) {
        this.a.onActivityStarted(activity);
    }

    public final void onActivityStopped(Activity activity) {
        this.a.onActivityStopped(activity);
    }
}
