package com.crashlytics.android.beta;

import android.content.Context;

interface UpdatesController {
    void initialize(Context context, Beta beta, kw kwVar, mu muVar, BuildProperties buildProperties, mn mnVar, kr krVar, mh mhVar);

    boolean isActivityLifecycleTriggered();
}
