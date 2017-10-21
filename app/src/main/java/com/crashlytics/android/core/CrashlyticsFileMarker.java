package com.crashlytics.android.core;

import defpackage.js;
import java.io.File;

class CrashlyticsFileMarker {
    private final ml fileStore;
    private final String markerName;

    public CrashlyticsFileMarker(String str, ml mlVar) {
        this.markerName = str;
        this.fileStore = mlVar;
    }

    private File getMarkerFile() {
        return new File(this.fileStore.a(), this.markerName);
    }

    public boolean create() {
        try {
            return getMarkerFile().createNewFile();
        } catch (Throwable e) {
            js.a().c(CrashlyticsCore.TAG, "Error creating marker: " + this.markerName, e);
            return false;
        }
    }

    public boolean isPresent() {
        return getMarkerFile().exists();
    }

    public boolean remove() {
        return getMarkerFile().delete();
    }
}
