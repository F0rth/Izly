package defpackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class ll implements ld<ln>, lk, ln {
    private final List<ln> dependencies = new ArrayList();
    private final AtomicBoolean hasRun = new AtomicBoolean(false);
    private final AtomicReference<Throwable> throwable = new AtomicReference(null);

    public static boolean isProperDelegate(Object obj) {
        try {
            return (((ld) obj) == null || ((ln) obj) == null || ((lk) obj) == null) ? false : true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public void addDependency(ln lnVar) {
        synchronized (this) {
            this.dependencies.add(lnVar);
        }
    }

    public boolean areDependenciesMet() {
        for (ln isFinished : getDependencies()) {
            if (!isFinished.isFinished()) {
                return false;
            }
        }
        return true;
    }

    public int compareTo(Object obj) {
        return lg.a(this, obj);
    }

    public Collection<ln> getDependencies() {
        Collection<ln> unmodifiableCollection;
        synchronized (this) {
            unmodifiableCollection = Collections.unmodifiableCollection(this.dependencies);
        }
        return unmodifiableCollection;
    }

    public Throwable getError() {
        return (Throwable) this.throwable.get();
    }

    public int getPriority$16699175() {
        return lg.b;
    }

    public boolean isFinished() {
        return this.hasRun.get();
    }

    public void setError(Throwable th) {
        this.throwable.set(th);
    }

    public void setFinished(boolean z) {
        synchronized (this) {
            this.hasRun.set(z);
        }
    }
}
