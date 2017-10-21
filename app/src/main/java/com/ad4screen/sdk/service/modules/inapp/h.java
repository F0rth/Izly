package com.ad4screen.sdk.service.modules.inapp;

import android.os.Handler;
import android.os.HandlerThread;

import com.ad4screen.sdk.Log;
import com.ad4screen.sdk.c.a.d;
import com.ad4screen.sdk.common.b.m.g;
import com.ad4screen.sdk.d.f;

import java.util.ArrayList;

public final class h {
    private HandlerThread a;
    private Handler b;
    private boolean c;
    private boolean d;
    private ArrayList<a> e = new ArrayList();
    private final Runnable f = new Runnable(this) {
        final /* synthetic */ h a;

        {
            this.a = r1;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void run() {
            /*
            r6 = this;
            r1 = r6.a;
            monitor-enter(r1);
            r0 = r6.a;	 Catch:{ all -> 0x0034 }
            r0 = r0.b;	 Catch:{ all -> 0x0034 }
            if (r0 != 0) goto L_0x000d;
        L_0x000b:
            monitor-exit(r1);	 Catch:{ all -> 0x0034 }
        L_0x000c:
            return;
        L_0x000d:
            r0 = r6.a;	 Catch:{ all -> 0x0034 }
            r0 = r0.d;	 Catch:{ all -> 0x0034 }
            if (r0 != 0) goto L_0x0037;
        L_0x0015:
            r0 = com.ad4screen.sdk.d.f.a();	 Catch:{ all -> 0x0034 }
            r2 = new com.ad4screen.sdk.service.modules.inapp.e$b;	 Catch:{ all -> 0x0034 }
            r2.<init>();	 Catch:{ all -> 0x0034 }
            r0.a(r2);	 Catch:{ all -> 0x0034 }
            r0 = r6.a;	 Catch:{ all -> 0x0034 }
            r0 = r0.b;	 Catch:{ all -> 0x0034 }
            r2 = r6.a;	 Catch:{ all -> 0x0034 }
            r2 = r2.f;	 Catch:{ all -> 0x0034 }
            r4 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;
            r0.postDelayed(r2, r4);	 Catch:{ all -> 0x0034 }
        L_0x0032:
            monitor-exit(r1);	 Catch:{ all -> 0x0034 }
            goto L_0x000c;
        L_0x0034:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0034 }
            throw r0;
        L_0x0037:
            r0 = r6.a;	 Catch:{ all -> 0x0034 }
            r2 = 0;
            r0.d = r2;	 Catch:{ all -> 0x0034 }
            r0 = r6.a;	 Catch:{ all -> 0x0034 }
            r0 = r0.b;	 Catch:{ all -> 0x0034 }
            r2 = r6.a;	 Catch:{ all -> 0x0034 }
            r2 = r2.f;	 Catch:{ all -> 0x0034 }
            r4 = 0;
            r0.postDelayed(r2, r4);	 Catch:{ all -> 0x0034 }
            goto L_0x0032;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.ad4screen.sdk.service.modules.inapp.h.1.run():void");
        }
    };

    public final class a implements Runnable {
        public String a;
        final /* synthetic */ h b;

        public a(h hVar, String str) {
            this.b = hVar;
            this.a = str;
        }

        public final void run() {
            f.a().a(new a(this.a));
        }
    }

    public final void a() {
        synchronized (this) {
            if (this.b != null) {
                for (int i = 0; i < this.e.size(); i++) {
                    this.b.removeCallbacks((Runnable) this.e.get(i));
                }
                this.e.clear();
                this.b.removeCallbacks(this.f);
                this.b = null;
            }
            if (this.a != null) {
                g.a(this.a);
                this.a = null;
            }
        }
    }

    public final void a(long j) {
        synchronized (this) {
            c();
            b(j);
        }
    }

    public final void a(d dVar) {
        synchronized (this) {
            if (this.b == null) {
                Log.warn("InApp|Cannot setup autoclose while worker is stopped");
            } else if (dVar instanceof com.ad4screen.sdk.c.a.a) {
                com.ad4screen.sdk.c.a.a aVar = (com.ad4screen.sdk.c.a.a) dVar;
                if (aVar.a != null) {
                    int intValue = aVar.a.intValue();
                    Runnable aVar2 = new a(this, aVar.h);
                    this.b.postDelayed(aVar2, (long) (intValue * 1000));
                    this.e.add(aVar2);
                    Log.debug("InApp|Autoclose scheduled in " + intValue + "s for inapp #" + aVar.h);
                }
            }
        }
    }

    public final void a(String str) {
        synchronized (this) {
            if (this.b != null) {
                for (int i = 0; i < this.e.size(); i++) {
                    if (((a) this.e.get(i)).a.equals(str)) {
                        this.b.removeCallbacks((Runnable) this.e.get(i));
                    }
                }
            }
        }
    }

    public final void b() {
        synchronized (this) {
            if (this.b == null) {
                this.a = new HandlerThread("InAppNotification.worker");
                this.a.start();
                this.b = new Handler(this.a.getLooper());
                this.c = false;
            }
        }
    }

    public final void b(long j) {
        synchronized (this) {
            if (this.b == null) {
                Log.warn("InApp|Cannot start rules checking while worker is stopped");
            } else if (this.c) {
                this.d = true;
            } else {
                this.c = true;
                this.b.postDelayed(this.f, j);
                Log.debug("InApp|Start checking rules in " + (j / 1000) + "s");
            }
        }
    }

    public final void c() {
        synchronized (this) {
            if (this.b != null) {
                if (this.c) {
                    Log.debug("InApp|Pause checking rules");
                    this.c = false;
                }
                this.b.removeCallbacks(this.f);
            }
        }
    }
}
