package com.ad4screen.sdk.service.modules.push;

import android.os.Bundle;

import com.ad4screen.sdk.c.a.d;

public interface a {
    void a();

    void b();

    void closedPush(Bundle bundle);

    String getPushToken();

    void handleMessage(Bundle bundle);

    boolean isEnabled();

    void openedPush(Bundle bundle);

    void setEnabled(boolean z);

    void setFormat(d dVar);

    void updateRegistration(Bundle bundle);
}
