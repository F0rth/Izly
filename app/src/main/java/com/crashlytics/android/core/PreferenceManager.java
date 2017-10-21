package com.crashlytics.android.core;

import android.annotation.SuppressLint;
import defpackage.mo;

@SuppressLint({"CommitPrefEdits"})
class PreferenceManager {
    static final String PREF_ALWAYS_SEND_REPORTS_KEY = "always_send_reports_opt_in";
    private static final String PREF_MIGRATION_COMPLETE = "preferences_migration_complete";
    private static final boolean SHOULD_ALWAYS_SEND_REPORTS_DEFAULT = false;
    private final mn preferenceStore;

    public PreferenceManager(mn mnVar) {
        this.preferenceStore = mnVar;
    }

    public static PreferenceManager create(mn mnVar, CrashlyticsCore crashlyticsCore) {
        if (!mnVar.a().getBoolean(PREF_MIGRATION_COMPLETE, false)) {
            mo moVar = new mo(crashlyticsCore);
            boolean z = !mnVar.a().contains(PREF_ALWAYS_SEND_REPORTS_KEY) && moVar.a().contains(PREF_ALWAYS_SEND_REPORTS_KEY);
            if (z) {
                mnVar.a(mnVar.b().putBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, moVar.a().getBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, false)));
            }
            mnVar.a(mnVar.b().putBoolean(PREF_MIGRATION_COMPLETE, true));
        }
        return new PreferenceManager(mnVar);
    }

    void setShouldAlwaysSendReports(boolean z) {
        this.preferenceStore.a(this.preferenceStore.b().putBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, z));
    }

    boolean shouldAlwaysSendReports() {
        return this.preferenceStore.a().getBoolean(PREF_ALWAYS_SEND_REPORTS_KEY, false);
    }
}
