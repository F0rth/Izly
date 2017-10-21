package defpackage;

import android.content.Context;
import android.content.Intent;

public final class ca {
    public static void a(Context context) {
        Intent intent = new Intent("fr.smoney.android.izly.notifications.NOTIFICATION_UPDATER");
        intent.putExtra("fr.smoney.android.izly.extra.callGetBadges", true);
        context.sendBroadcast(intent);
    }
}
