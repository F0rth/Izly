package defpackage;

import android.os.Process;

public abstract class ko implements Runnable {
    public abstract void onRun();

    public final void run() {
        Process.setThreadPriority(10);
        onRun();
    }
}
