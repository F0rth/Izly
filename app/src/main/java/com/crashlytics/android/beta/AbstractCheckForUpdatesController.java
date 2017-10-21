package com.crashlytics.android.beta;

import android.annotation.SuppressLint;
import android.content.Context;
import defpackage.js;
import java.util.concurrent.atomic.AtomicBoolean;

abstract class AbstractCheckForUpdatesController implements UpdatesController {
    static final long LAST_UPDATE_CHECK_DEFAULT = 0;
    static final String LAST_UPDATE_CHECK_KEY = "last_update_check";
    private static final long MILLIS_PER_SECOND = 1000;
    private Beta beta;
    private mu betaSettings;
    private BuildProperties buildProps;
    private Context context;
    private kr currentTimeProvider;
    private final AtomicBoolean externallyReady;
    private mh httpRequestFactory;
    private kw idManager;
    private final AtomicBoolean initialized;
    private long lastCheckTimeMillis;
    private mn preferenceStore;

    public AbstractCheckForUpdatesController() {
        this(false);
    }

    public AbstractCheckForUpdatesController(boolean z) {
        this.initialized = new AtomicBoolean();
        this.lastCheckTimeMillis = LAST_UPDATE_CHECK_DEFAULT;
        this.externallyReady = new AtomicBoolean(z);
    }

    private void performUpdateCheck() {
        js.a().a(Beta.TAG, "Performing update check");
        new CheckForUpdatesRequest(this.beta, this.beta.getOverridenSpiEndpoint(), this.betaSettings.a, this.httpRequestFactory, new CheckForUpdatesResponseTransform()).invoke(new kn().a(this.context), (String) this.idManager.c().get(a.c), this.buildProps);
    }

    @SuppressLint({"CommitPrefEdits"})
    protected void checkForUpdates() {
        synchronized (this.preferenceStore) {
            if (this.preferenceStore.a().contains(LAST_UPDATE_CHECK_KEY)) {
                this.preferenceStore.a(this.preferenceStore.b().remove(LAST_UPDATE_CHECK_KEY));
            }
        }
        long a = this.currentTimeProvider.a();
        long j = ((long) this.betaSettings.b) * MILLIS_PER_SECOND;
        js.a().a(Beta.TAG, "Check for updates delay: " + j);
        js.a().a(Beta.TAG, "Check for updates last check time: " + getLastCheckTimeMillis());
        j += getLastCheckTimeMillis();
        js.a().a(Beta.TAG, "Check for updates current time: " + a + ", next check time: " + j);
        if (a >= j) {
            try {
                performUpdateCheck();
            } finally {
                setLastCheckTimeMillis(a);
            }
        } else {
            js.a().a(Beta.TAG, "Check for updates next check time was not passed");
        }
    }

    long getLastCheckTimeMillis() {
        return this.lastCheckTimeMillis;
    }

    public void initialize(Context context, Beta beta, kw kwVar, mu muVar, BuildProperties buildProperties, mn mnVar, kr krVar, mh mhVar) {
        this.context = context;
        this.beta = beta;
        this.idManager = kwVar;
        this.betaSettings = muVar;
        this.buildProps = buildProperties;
        this.preferenceStore = mnVar;
        this.currentTimeProvider = krVar;
        this.httpRequestFactory = mhVar;
        if (signalInitialized()) {
            checkForUpdates();
        }
    }

    void setLastCheckTimeMillis(long j) {
        this.lastCheckTimeMillis = j;
    }

    protected boolean signalExternallyReady() {
        this.externallyReady.set(true);
        return this.initialized.get();
    }

    boolean signalInitialized() {
        this.initialized.set(true);
        return this.externallyReady.get();
    }
}
