package defpackage;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class lj<V> extends FutureTask<V> implements ld<ln>, lk, ln {
    final Object b;

    public lj(Runnable runnable, V v) {
        super(runnable, v);
        this.b = lj.a(runnable);
    }

    public lj(Callable<V> callable) {
        super(callable);
        this.b = lj.a(callable);
    }

    private static <T extends ld<ln> & lk & ln> T a(Object obj) {
        return ll.isProperDelegate(obj) ? (ld) obj : new ll();
    }

    public <T extends ld<ln> & lk & ln> T a() {
        return (ld) this.b;
    }

    public /* synthetic */ void addDependency(Object obj) {
        ((ld) ((lk) a())).addDependency((ln) obj);
    }

    public boolean areDependenciesMet() {
        return ((ld) ((lk) a())).areDependenciesMet();
    }

    public int compareTo(Object obj) {
        return ((lk) a()).compareTo(obj);
    }

    public Collection<ln> getDependencies() {
        return ((ld) ((lk) a())).getDependencies();
    }

    public int getPriority$16699175() {
        return ((lk) a()).getPriority$16699175();
    }

    public boolean isFinished() {
        return ((ln) ((lk) a())).isFinished();
    }

    public void setError(Throwable th) {
        ((ln) ((lk) a())).setError(th);
    }

    public void setFinished(boolean z) {
        ((ln) ((lk) a())).setFinished(z);
    }
}
