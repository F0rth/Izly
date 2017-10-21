package defpackage;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import java.util.HashSet;
import java.util.Set;

public final class jq$a {
    public final Set<ActivityLifecycleCallbacks> a = new HashSet();
    public final Application b;

    jq$a(Application application) {
        this.b = application;
    }
}
