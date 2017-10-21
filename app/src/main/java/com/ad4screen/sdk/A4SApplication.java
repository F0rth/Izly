package com.ad4screen.sdk;

import android.app.Application;
import android.content.res.Configuration;

import com.ad4screen.sdk.common.annotations.API;

@API
public class A4SApplication extends Application {
    @API
    public void onApplicationConfigurationChanged(Configuration configuration) {
    }

    @API
    public void onApplicationCreate() {
    }

    @API
    public void onApplicationLowMemory() {
    }

    @API
    public void onApplicationTerminate() {
    }

    public final void onConfigurationChanged(Configuration configuration) {
        if (!A4S.isInA4SProcess(this)) {
            onApplicationConfigurationChanged(configuration);
        }
    }

    public final void onCreate() {
        if (!A4S.isInA4SProcess(this)) {
            onApplicationCreate();
        }
    }

    public final void onLowMemory() {
        if (!A4S.isInA4SProcess(this)) {
            onApplicationLowMemory();
        }
    }

    public final void onTerminate() {
        if (!A4S.isInA4SProcess(this)) {
            onApplicationTerminate();
        }
    }
}
