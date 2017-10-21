package com.ezeeworld.b4s.android.sdk;

public final class EventBus {
    private static final de.greenrobot.event.EventBus a = de.greenrobot.event.EventBus.builder().logNoSubscriberMessages(false).sendNoSubscriberEvent(true).build();

    public static de.greenrobot.event.EventBus get() {
        return a;
    }
}
