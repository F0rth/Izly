package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.RequiresPermission;
import android.util.Log;
import com.google.android.gms.iid.InstanceID;
import com.google.android.gms.iid.zzc;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class GoogleCloudMessaging {
    public static final String ERROR_MAIN_THREAD = "MAIN_THREAD";
    public static final String ERROR_SERVICE_NOT_AVAILABLE = "SERVICE_NOT_AVAILABLE";
    public static final String INSTANCE_ID_SCOPE = "GCM";
    @Deprecated
    public static final String MESSAGE_TYPE_DELETED = "deleted_messages";
    @Deprecated
    public static final String MESSAGE_TYPE_MESSAGE = "gcm";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_ERROR = "send_error";
    @Deprecated
    public static final String MESSAGE_TYPE_SEND_EVENT = "send_event";
    public static int zzaLM = 5000000;
    public static int zzaLN = 6500000;
    public static int zzaLO = 7000000;
    static GoogleCloudMessaging zzaLP;
    private static final AtomicInteger zzaLS = new AtomicInteger(1);
    private Context context;
    private PendingIntent zzaLQ;
    private Map<String, Handler> zzaLR = Collections.synchronizedMap(new HashMap());
    private final BlockingQueue<Intent> zzaLT = new LinkedBlockingQueue();
    final Messenger zzaLU = new Messenger(new Handler(this, Looper.getMainLooper()) {
        final /* synthetic */ GoogleCloudMessaging zzaLV;

        public void handleMessage(Message message) {
            if (message == null || !(message.obj instanceof Intent)) {
                Log.w(GoogleCloudMessaging.INSTANCE_ID_SCOPE, "Dropping invalid message");
            }
            Intent intent = (Intent) message.obj;
            if ("com.google.android.c2dm.intent.REGISTRATION".equals(intent.getAction())) {
                this.zzaLV.zzaLT.add(intent);
            } else if (!this.zzaLV.zzr(intent)) {
                intent.setPackage(this.zzaLV.context.getPackageName());
                this.zzaLV.context.sendBroadcast(intent);
            }
        }
    });

    public static GoogleCloudMessaging getInstance(Context context) {
        synchronized (GoogleCloudMessaging.class) {
            Class applicationContext;
            try {
                GoogleCloudMessaging googleCloudMessaging;
                if (zzaLP == null) {
                    googleCloudMessaging = new GoogleCloudMessaging();
                    zzaLP = googleCloudMessaging;
                    applicationContext = context.getApplicationContext();
                    googleCloudMessaging.context = applicationContext;
                }
                googleCloudMessaging = zzaLP;
                return googleCloudMessaging;
            } finally {
                applicationContext = GoogleCloudMessaging.class;
            }
        }
    }

    static String zza(Intent intent, String str) throws IOException {
        if (intent == null) {
            throw new IOException("SERVICE_NOT_AVAILABLE");
        }
        String stringExtra = intent.getStringExtra(str);
        if (stringExtra != null) {
            return stringExtra;
        }
        stringExtra = intent.getStringExtra("error");
        if (stringExtra != null) {
            throw new IOException(stringExtra);
        }
        throw new IOException("SERVICE_NOT_AVAILABLE");
    }

    private void zza(String str, String str2, long j, int i, Bundle bundle) throws IOException {
        if (str == null) {
            throw new IllegalArgumentException("Missing 'to'");
        }
        Intent intent = new Intent("com.google.android.gcm.intent.SEND");
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        zzs(intent);
        intent.setPackage(zzaJ(this.context));
        intent.putExtra("google.to", str);
        intent.putExtra("google.message_id", str2);
        intent.putExtra("google.ttl", Long.toString(j));
        intent.putExtra("google.delay", Integer.toString(i));
        intent.putExtra("google.from", zzdZ(str));
        if (zzaJ(this.context).contains(".gsf")) {
            Bundle bundle2 = new Bundle();
            for (String str3 : bundle.keySet()) {
                Object obj = bundle.get(str3);
                if (obj instanceof String) {
                    bundle2.putString("gcm." + str3, (String) obj);
                }
            }
            bundle2.putString("google.to", str);
            bundle2.putString("google.message_id", str2);
            InstanceID.getInstance(this.context).zzc(INSTANCE_ID_SCOPE, "upstream", bundle2);
            return;
        }
        this.context.sendOrderedBroadcast(intent, "com.google.android.gtalkservice.permission.GTALK_SERVICE");
    }

    public static String zzaJ(Context context) {
        return zzc.zzaN(context);
    }

    public static int zzaK(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(zzaJ(context), 0).versionCode;
        } catch (NameNotFoundException e) {
            return -1;
        }
    }

    private String zzdZ(String str) {
        int indexOf = str.indexOf(64);
        if (indexOf > 0) {
            str = str.substring(0, indexOf);
        }
        return InstanceID.getInstance(this.context).zzyB().zzi("", str, INSTANCE_ID_SCOPE);
    }

    private boolean zzr(Intent intent) {
        Object stringExtra = intent.getStringExtra("In-Reply-To");
        if (stringExtra == null && intent.hasExtra("error")) {
            stringExtra = intent.getStringExtra("google.message_id");
        }
        if (stringExtra != null) {
            Handler handler = (Handler) this.zzaLR.remove(stringExtra);
            if (handler != null) {
                Message obtain = Message.obtain();
                obtain.obj = intent;
                return handler.sendMessage(obtain);
            }
        }
        return false;
    }

    private String zzyk() {
        return "google.rpc" + String.valueOf(zzaLS.getAndIncrement());
    }

    public void close() {
        zzaLP = null;
        zzb.zzaLC = null;
        zzyl();
    }

    public String getMessageType(Intent intent) {
        if (!"com.google.android.c2dm.intent.RECEIVE".equals(intent.getAction())) {
            return null;
        }
        String stringExtra = intent.getStringExtra("message_type");
        return stringExtra == null ? MESSAGE_TYPE_MESSAGE : stringExtra;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public String register(String... strArr) throws IOException {
        String zzc;
        synchronized (this) {
            zzc = zzc(strArr);
            Bundle bundle = new Bundle();
            if (zzaJ(this.context).contains(".gsf")) {
                bundle.putString("legacy.sender", zzc);
                zzc = InstanceID.getInstance(this.context).getToken(zzc, INSTANCE_ID_SCOPE, bundle);
            } else {
                bundle.putString("sender", zzc);
                zzc = zza(zzE(bundle), "registration_id");
            }
        }
        return zzc;
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, long j, Bundle bundle) throws IOException {
        zza(str, str2, j, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void send(String str, String str2, Bundle bundle) throws IOException {
        send(str, str2, -1, bundle);
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    @Deprecated
    public void unregister() throws IOException {
        synchronized (this) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                throw new IOException("MAIN_THREAD");
            }
            InstanceID.getInstance(this.context).deleteInstanceID();
        }
    }

    @Deprecated
    Intent zzE(Bundle bundle) throws IOException {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            throw new IOException("MAIN_THREAD");
        } else if (zzaK(this.context) < 0) {
            throw new IOException("Google Play Services missing");
        } else {
            if (bundle == null) {
                bundle = new Bundle();
            }
            Intent intent = new Intent("com.google.android.c2dm.intent.REGISTER");
            intent.setPackage(zzaJ(this.context));
            zzs(intent);
            intent.putExtra("google.message_id", zzyk());
            intent.putExtras(bundle);
            intent.putExtra("google.messenger", this.zzaLU);
            this.context.startService(intent);
            try {
                return (Intent) this.zzaLT.poll(30000, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                throw new IOException(e.getMessage());
            }
        }
    }

    String zzc(String... strArr) {
        if (strArr == null || strArr.length == 0) {
            throw new IllegalArgumentException("No senderIds");
        }
        StringBuilder stringBuilder = new StringBuilder(strArr[0]);
        for (int i = 1; i < strArr.length; i++) {
            stringBuilder.append(',').append(strArr[i]);
        }
        return stringBuilder.toString();
    }

    void zzs(Intent intent) {
        synchronized (this) {
            if (this.zzaLQ == null) {
                Intent intent2 = new Intent();
                intent2.setPackage("com.google.example.invalidpackage");
                this.zzaLQ = PendingIntent.getBroadcast(this.context, 0, intent2, 0);
            }
            intent.putExtra("app", this.zzaLQ);
        }
    }

    void zzyl() {
        synchronized (this) {
            if (this.zzaLQ != null) {
                this.zzaLQ.cancel();
                this.zzaLQ = null;
            }
        }
    }
}
