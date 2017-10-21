package com.crashlytics.android.answers;

import android.annotation.SuppressLint;
import android.content.Context;
import defpackage.mo;

class AnswersPreferenceManager {
    static final String PREFKEY_ANALYTICS_LAUNCHED = "analytics_launched";
    static final String PREF_STORE_NAME = "settings";
    private final mn prefStore;

    AnswersPreferenceManager(mn mnVar) {
        this.prefStore = mnVar;
    }

    public static AnswersPreferenceManager build(Context context) {
        return new AnswersPreferenceManager(new mo(context, PREF_STORE_NAME));
    }

    @SuppressLint({"CommitPrefEdits"})
    public boolean hasAnalyticsLaunched() {
        return this.prefStore.a().getBoolean(PREFKEY_ANALYTICS_LAUNCHED, false);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void setAnalyticsLaunched() {
        this.prefStore.a(this.prefStore.b().putBoolean(PREFKEY_ANALYTICS_LAUNCHED, true));
    }
}
