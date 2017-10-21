package defpackage;

import android.content.Context;
import java.io.File;

public final class mm implements ml {
    private final Context a;
    private final String b;
    private final String c;

    public mm(jy jyVar) {
        if (jyVar.getContext() == null) {
            throw new IllegalStateException("Cannot get directory before context has been set. Call Fabric.with() first");
        }
        this.a = jyVar.getContext();
        this.b = jyVar.getPath();
        this.c = "Android/" + this.a.getPackageName();
    }

    public final File a() {
        File filesDir = this.a.getFilesDir();
        if (filesDir == null) {
            js.a().a("Fabric", "Null File");
        } else if (filesDir.exists() || filesDir.mkdirs()) {
            return filesDir;
        } else {
            js.a().d("Fabric", "Couldn't create file");
        }
        return null;
    }
}
