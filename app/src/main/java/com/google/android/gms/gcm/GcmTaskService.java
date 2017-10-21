package com.google.android.gms.gcm;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.util.HashSet;
import java.util.Set;

public abstract class GcmTaskService extends Service {
    public static final String SERVICE_ACTION_EXECUTE_TASK = "com.google.android.gms.gcm.ACTION_TASK_READY";
    public static final String SERVICE_ACTION_INITIALIZE = "com.google.android.gms.gcm.SERVICE_ACTION_INITIALIZE";
    public static final String SERVICE_PERMISSION = "com.google.android.gms.permission.BIND_NETWORK_TASK_SERVICE";
    private final Set<String> zzaLI = new HashSet();
    private int zzaLJ;

    class zza extends Thread {
        private final Bundle mExtras;
        private final String mTag;
        private final zzc zzaLK;
        final /* synthetic */ GcmTaskService zzaLL;

        zza(GcmTaskService gcmTaskService, String str, IBinder iBinder, Bundle bundle) {
            this.zzaLL = gcmTaskService;
            this.mTag = str;
            this.zzaLK = com.google.android.gms.gcm.zzc.zza.zzbZ(iBinder);
            this.mExtras = bundle;
        }

        public void run() {
            Process.setThreadPriority(10);
            try {
                this.zzaLK.zzhe(this.zzaLL.onRunTask(new TaskParams(this.mTag, this.mExtras)));
            } catch (RemoteException e) {
                Log.e("GcmTaskService", "Error reporting result of operation to scheduler for " + this.mTag);
            } finally {
                this.zzaLL.zzdY(this.mTag);
            }
        }
    }

    private void zzdY(String str) {
        synchronized (this.zzaLI) {
            this.zzaLI.remove(str);
            if (this.zzaLI.size() == 0) {
                stopSelf(this.zzaLJ);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onInitializeTasks() {
    }

    public abstract int onRunTask(TaskParams taskParams);

    public int onStartCommand(Intent intent, int i, int i2) {
        intent.setExtrasClassLoader(PendingCallback.class.getClassLoader());
        if (SERVICE_ACTION_EXECUTE_TASK.equals(intent.getAction())) {
            String stringExtra = intent.getStringExtra("tag");
            Parcelable parcelableExtra = intent.getParcelableExtra("callback");
            Bundle bundle = (Bundle) intent.getParcelableExtra("extras");
            if (parcelableExtra == null || !(parcelableExtra instanceof PendingCallback)) {
                Log.e("GcmTaskService", getPackageName() + MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR + stringExtra + ": Could not process request, invalid callback.");
            } else {
                synchronized (this.zzaLI) {
                    this.zzaLI.add(stringExtra);
                    stopSelf(this.zzaLJ);
                    this.zzaLJ = i2;
                }
                new zza(this, stringExtra, ((PendingCallback) parcelableExtra).getIBinder(), bundle).start();
            }
        } else if (SERVICE_ACTION_INITIALIZE.equals(intent.getAction())) {
            onInitializeTasks();
            synchronized (this.zzaLI) {
                this.zzaLJ = i2;
                if (this.zzaLI.size() == 0) {
                    stopSelf(this.zzaLJ);
                }
            }
        }
        return 2;
    }
}
