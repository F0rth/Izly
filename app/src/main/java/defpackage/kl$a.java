package defpackage;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

final class kl$a implements ServiceConnection {
    private boolean a;
    private final LinkedBlockingQueue<IBinder> b;

    private kl$a() {
        this.a = false;
        this.b = new LinkedBlockingQueue(1);
    }

    public final IBinder a() {
        if (this.a) {
            js.a().e("Fabric", "getBinder already called");
        }
        this.a = true;
        try {
            return (IBinder) this.b.poll(200, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            return null;
        }
    }

    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        try {
            this.b.put(iBinder);
        } catch (InterruptedException e) {
        }
    }

    public final void onServiceDisconnected(ComponentName componentName) {
        this.b.clear();
    }
}
