package defpackage;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

public abstract class lh<Params, Progress, Result> extends lc<Params, Progress, Result> implements ld<ln>, lk, ln {
    private final ll a = new ll();

    public final void a(ExecutorService executorService, Params... paramsArr) {
        super.a(new lh$a(executorService, this), (Object[]) paramsArr);
    }

    public final void a(ln lnVar) {
        if (this.d != lc$d.a) {
            throw new IllegalStateException("Must not add Dependency after task is running");
        }
        this.a.addDependency(lnVar);
    }

    public /* synthetic */ void addDependency(Object obj) {
        a((ln) obj);
    }

    public boolean areDependenciesMet() {
        return this.a.areDependenciesMet();
    }

    public int compareTo(Object obj) {
        return lg.a(this, obj);
    }

    public Collection<ln> getDependencies() {
        return this.a.getDependencies();
    }

    public int getPriority$16699175() {
        return this.a.getPriority$16699175();
    }

    public boolean isFinished() {
        return this.a.isFinished();
    }

    public void setError(Throwable th) {
        this.a.setError(th);
    }

    public void setFinished(boolean z) {
        this.a.setFinished(z);
    }
}
