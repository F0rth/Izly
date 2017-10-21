package defpackage;

import android.content.Context;
import com.google.analytics.tracking.android.EasyTracker;
import com.google.analytics.tracking.android.MapBuilder;

public final class jb {
    public static void a(Context context, int i) {
        EasyTracker instance = EasyTracker.getInstance(context);
        instance.set("&cd", context.getString(i));
        instance.set("&av", "0.22");
        instance.send(MapBuilder.createAppView().build());
    }

    public static void a(Context context, int i, int i2, int i3) {
        EasyTracker.getInstance(context).send(MapBuilder.createEvent(context.getString(i), context.getString(i2), context.getString(i3), null).build());
    }
}
