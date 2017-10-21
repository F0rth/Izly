package defpackage;

import android.content.Context;
import java.io.File;
import java.util.Collection;

public abstract class jy<Result> implements Comparable<jy> {
    Context context;
    final lf dependsOnAnnotation = ((lf) getClass().getAnnotation(lf.class));
    js fabric;
    kw idManager;
    jv<Result> initializationCallback;
    jx<Result> initializationTask = new jx(this);

    public int compareTo(jy jyVar) {
        if (!containsAnnotatedDependency(jyVar)) {
            if (jyVar.containsAnnotatedDependency(this)) {
                return -1;
            }
            if (!hasAnnotatedDependency() || jyVar.hasAnnotatedDependency()) {
                return (hasAnnotatedDependency() || !jyVar.hasAnnotatedDependency()) ? 0 : -1;
            }
        }
        return 1;
    }

    boolean containsAnnotatedDependency(jy jyVar) {
        if (!hasAnnotatedDependency()) {
            return false;
        }
        for (Class isAssignableFrom : this.dependsOnAnnotation.a()) {
            if (isAssignableFrom.isAssignableFrom(jyVar.getClass())) {
                return true;
            }
        }
        return false;
    }

    public abstract Result doInBackground();

    public Context getContext() {
        return this.context;
    }

    protected Collection<ln> getDependencies() {
        return this.initializationTask.getDependencies();
    }

    public js getFabric() {
        return this.fabric;
    }

    protected kw getIdManager() {
        return this.idManager;
    }

    public abstract String getIdentifier();

    public String getPath() {
        return ".Fabric" + File.separator + getIdentifier();
    }

    public abstract String getVersion();

    boolean hasAnnotatedDependency() {
        return this.dependsOnAnnotation != null;
    }

    final void initialize() {
        this.initializationTask.a(this.fabric.c, new Void[]{null});
    }

    void injectParameters(Context context, js jsVar, jv<Result> jvVar, kw kwVar) {
        this.fabric = jsVar;
        this.context = new jt(context, getIdentifier(), getPath());
        this.initializationCallback = jvVar;
        this.idManager = kwVar;
    }

    protected void onCancelled(Result result) {
    }

    protected void onPostExecute(Result result) {
    }

    public boolean onPreExecute() {
        return true;
    }
}
