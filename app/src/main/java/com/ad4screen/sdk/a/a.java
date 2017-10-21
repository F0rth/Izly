package com.ad4screen.sdk.a;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;

import com.ad4screen.sdk.A4SService;
import com.ad4screen.sdk.Log;

import java.util.LinkedList;
import java.util.Queue;

public abstract class a<I> {
    private final Context a;
    private final Handler b;
    private final Queue<a<I>> c = new LinkedList();
    private I d;
    private boolean e;
    private boolean f;
    private boolean g;
    private final ServiceConnection h = new ServiceConnection(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.debug("Connected to A4SService");
            this.a.d = this.a.b(iBinder);
            this.a.e = false;
            this.a.b(new a<I>(this, "onServiceConnected") {
                final /* synthetic */ AnonymousClass1 a;

                public void a(I i) throws RemoteException {
                    this.a.a.a((Object) i);
                }
            });
            this.a.e();
            this.a.c();
        }

        public void onServiceDisconnected(ComponentName componentName) {
            Log.debug("Disconnected from A4SService");
            this.a.d = null;
        }
    };
    private final Runnable i = new Runnable(this) {
        final /* synthetic */ a a;

        {
            this.a = r1;
        }

        public void run() {
            Log.debug("Unbinding from A4SService");
            if (this.a.d != null) {
                this.a.a.unbindService(this.a.h);
            }
            this.a.d = null;
            this.a.f = false;
        }
    };

    public static abstract class a<I> {
        private final String a;

        public a(String str) {
            this.a = str;
        }

        private final void b(I i) {
            Log.verbose("Sending '" + this.a + "' command");
            try {
                a(i);
            } catch (Throwable e) {
                Log.error("Error while sending '" + this.a + "' command", e);
            }
        }

        public abstract void a(I i) throws RemoteException;
    }

    public a(Context context) {
        this.a = context.getApplicationContext();
        this.b = new Handler(Looper.getMainLooper());
    }

    private void a(Runnable runnable) {
        if (Thread.currentThread() == this.b.getLooper().getThread()) {
            runnable.run();
        } else {
            this.b.post(runnable);
        }
    }

    private void b() {
        d();
        if (this.d == null && !this.e) {
            Log.debug("Binding to A4SService");
            if (this.a.bindService(new Intent(this.a, A4SService.class), this.h, 1)) {
                this.e = true;
            } else {
                Log.error("Could not bind to A4SService, please check your AndroidManifest.xml");
            }
        }
    }

    private void b(a<I> aVar) {
        aVar.b(this.d);
    }

    private void c() {
        if (!this.f) {
            this.b.postDelayed(this.i, 10000);
            this.f = true;
        }
    }

    private void d() {
        if (!this.g) {
            this.b.removeCallbacks(this.i);
            this.f = false;
        }
    }

    private void e() {
        for (a a : this.c) {
            a.b(this.d);
        }
        this.c.clear();
    }

    public void a() {
        this.g = true;
        this.c.clear();
        if (!this.f) {
            this.b.post(this.i);
            this.f = true;
        }
        this.a.stopService(new Intent(this.a, A4SService.class));
    }

    public void a(final a<I> aVar) {
        if (!this.g) {
            a(new Runnable(this) {
                final /* synthetic */ a b;

                public void run() {
                    this.b.b();
                    if (this.b.d == null) {
                        this.b.c.offer(aVar);
                        return;
                    }
                    aVar.b(this.b.d);
                    this.b.c();
                }
            });
        }
    }

    public abstract void a(I i) throws RemoteException;

    public abstract I b(IBinder iBinder);
}
