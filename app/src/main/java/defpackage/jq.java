package defpackage;

import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Build.VERSION;

public final class jq {
    public jq$a a;
    private final Application b;

    public jq(Context context) {
        this.b = (Application) context.getApplicationContext();
        if (VERSION.SDK_INT >= 14) {
            this.a = new jq$a(this.b);
        }
    }

    public final boolean a(jq$b jq_b) {
        if (this.a != null) {
            boolean z;
            jq$a jq_a = this.a;
            if (jq_a.b != null) {
                ActivityLifecycleCallbacks jq_a_1 = new jq$a$1(jq_a, jq_b);
                jq_a.b.registerActivityLifecycleCallbacks(jq_a_1);
                jq_a.a.add(jq_a_1);
                z = true;
            } else {
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }
}
