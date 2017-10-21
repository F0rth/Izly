package defpackage;

import android.content.Context;

public abstract class kd<T> implements kf<T> {
    private final kf<T> a;

    public kd(kf<T> kfVar) {
        this.a = kfVar;
    }

    protected abstract T a();

    public final T a(Context context, kg<T> kgVar) throws Exception {
        T a;
        synchronized (this) {
            a = a();
            if (a == null) {
                a = this.a != null ? this.a.a(context, kgVar) : kgVar.load(context);
                if (a == null) {
                    throw new NullPointerException();
                }
                a(a);
            }
        }
        return a;
    }

    protected abstract void a(T t);
}
