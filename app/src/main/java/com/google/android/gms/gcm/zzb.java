package com.google.android.gms.gcm;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.TextUtils;
import android.util.Log;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

class zzb {
    static zzb zzaLC;
    private final Context mContext;
    private final Class<? extends GcmListenerService> zzaLD;

    class zza extends IllegalArgumentException {
    }

    private zzb(Context context, Class<? extends GcmListenerService> cls) {
        this.mContext = context.getApplicationContext();
        this.zzaLD = cls;
    }

    private Notification zzB(Bundle bundle) {
        CharSequence zzf = zzf(bundle, "gcm.n.title");
        CharSequence zzf2 = zzf(bundle, "gcm.n.body");
        int zzdW = zzdW(zze(bundle, "gcm.n.icon"));
        Object zze = zze(bundle, "gcm.n.color");
        Uri zzdX = zzdX(zze(bundle, "gcm.n.sound2"));
        PendingIntent zzC = zzC(bundle);
        PendingIntent pendingIntent = null;
        if (GcmListenerService.zzx(bundle)) {
            zzC = zza(bundle, zzC);
            pendingIntent = zzD(bundle);
        }
        Builder smallIcon = new Builder(this.mContext).setAutoCancel(true).setSmallIcon(zzdW);
        if (TextUtils.isEmpty(zzf)) {
            smallIcon.setContentTitle(this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager()));
        } else {
            smallIcon.setContentTitle(zzf);
        }
        if (!TextUtils.isEmpty(zzf2)) {
            smallIcon.setContentText(zzf2);
        }
        if (!TextUtils.isEmpty(zze)) {
            smallIcon.setColor(Color.parseColor(zze));
        }
        if (zzdX != null) {
            smallIcon.setSound(zzdX);
        }
        if (zzC != null) {
            smallIcon.setContentIntent(zzC);
        }
        if (pendingIntent != null) {
            smallIcon.setDeleteIntent(pendingIntent);
        }
        return smallIcon.build();
    }

    private PendingIntent zzC(Bundle bundle) {
        Intent intent;
        Object zze = zze(bundle, "gcm.n.click_action");
        Intent launchIntentForPackage;
        if (TextUtils.isEmpty(zze)) {
            launchIntentForPackage = this.mContext.getPackageManager().getLaunchIntentForPackage(this.mContext.getPackageName());
            if (launchIntentForPackage == null) {
                Log.w("GcmNotification", "No activity found to launch app");
                return null;
            }
            intent = launchIntentForPackage;
        } else {
            launchIntentForPackage = new Intent(zze);
            launchIntentForPackage.setPackage(this.mContext.getPackageName());
            launchIntentForPackage.setFlags(268435456);
            intent = launchIntentForPackage;
        }
        Bundle bundle2 = new Bundle(bundle);
        GcmListenerService.zzw(bundle2);
        intent.putExtras(bundle2);
        for (String str : bundle2.keySet()) {
            if (str.startsWith("gcm.n.") || str.startsWith("gcm.notification.")) {
                intent.removeExtra(str);
            }
        }
        return PendingIntent.getActivity(this.mContext, zzyj(), intent, 1073741824);
    }

    private PendingIntent zzD(Bundle bundle) {
        Intent intent = new Intent("com.google.android.gms.gcm.NOTIFICATION_DISMISS");
        zza(intent, bundle);
        return PendingIntent.getService(this.mContext, zzyj(), intent, 1073741824);
    }

    private PendingIntent zza(Bundle bundle, PendingIntent pendingIntent) {
        Intent intent = new Intent("com.google.android.gms.gcm.NOTIFICATION_OPEN");
        zza(intent, bundle);
        intent.putExtra("com.google.android.gms.gcm.PENDING_INTENT", pendingIntent);
        return PendingIntent.getService(this.mContext, zzyj(), intent, 1073741824);
    }

    private void zza(Intent intent, Bundle bundle) {
        intent.setClass(this.mContext, this.zzaLD);
        for (String str : bundle.keySet()) {
            if (str.startsWith("google.c.a.") || str.equals("from")) {
                intent.putExtra(str, bundle.getString(str));
            }
        }
    }

    private void zza(String str, Notification notification) {
        if (Log.isLoggable("GcmNotification", 3)) {
            Log.d("GcmNotification", "Showing notification");
        }
        NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService("notification");
        if (TextUtils.isEmpty(str)) {
            str = "GCM-Notification:" + SystemClock.uptimeMillis();
        }
        notificationManager.notify(str, 0, notification);
    }

    static boolean zzaI(Context context) {
        if (((KeyguardManager) context.getSystemService("keyguard")).inKeyguardRestrictedInputMode()) {
            return false;
        }
        int myPid = Process.myPid();
        List<RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses();
        if (runningAppProcesses != null) {
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (runningAppProcessInfo.pid == myPid) {
                    return runningAppProcessInfo.importance == 100;
                }
            }
        }
        return false;
    }

    static zzb zzc(Context context, Class<? extends GcmListenerService> cls) {
        synchronized (zzb.class) {
            try {
                if (zzaLC == null) {
                    zzaLC = new zzb(context, cls);
                }
                zzb com_google_android_gms_gcm_zzb = zzaLC;
                return com_google_android_gms_gcm_zzb;
            } finally {
                Object obj = zzb.class;
            }
        }
    }

    private String zzdV(String str) {
        return str.substring(6);
    }

    private int zzdW(String str) {
        int identifier;
        if (!TextUtils.isEmpty(str)) {
            Resources resources = this.mContext.getResources();
            identifier = resources.getIdentifier(str, "drawable", this.mContext.getPackageName());
            if (identifier != 0) {
                return identifier;
            }
            identifier = resources.getIdentifier(str, "mipmap", this.mContext.getPackageName());
            if (identifier != 0) {
                return identifier;
            }
            Log.w("GcmNotification", "Icon resource " + str + " not found. Notification will use app icon.");
        }
        identifier = this.mContext.getApplicationInfo().icon;
        return identifier == 0 ? 17301651 : identifier;
    }

    private Uri zzdX(String str) {
        return TextUtils.isEmpty(str) ? null : ("default".equals(str) || this.mContext.getResources().getIdentifier(str, "raw", this.mContext.getPackageName()) == 0) ? RingtoneManager.getDefaultUri(2) : Uri.parse("android.resource://" + this.mContext.getPackageName() + "/raw/" + str);
    }

    static String zze(Bundle bundle, String str) {
        String string = bundle.getString(str);
        return string == null ? bundle.getString(str.replace("gcm.n.", "gcm.notification.")) : string;
    }

    private String zzf(Bundle bundle, String str) {
        Object zze = zze(bundle, str);
        if (!TextUtils.isEmpty(zze)) {
            return zze;
        }
        String zze2 = zze(bundle, str + "_loc_key");
        if (TextUtils.isEmpty(zze2)) {
            return null;
        }
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(zze2, "string", this.mContext.getPackageName());
        if (identifier == 0) {
            Log.w("GcmNotification", zzdV(str + "_loc_key") + " resource not found: " + zze2 + " Default value will be used.");
            return null;
        }
        String zze3 = zze(bundle, str + "_loc_args");
        if (TextUtils.isEmpty(zze3)) {
            return resources.getString(identifier);
        }
        try {
            JSONArray jSONArray = new JSONArray(zze3);
            String[] strArr = new String[jSONArray.length()];
            for (int i = 0; i < strArr.length; i++) {
                strArr[i] = jSONArray.opt(i);
            }
            return resources.getString(identifier, strArr);
        } catch (JSONException e) {
            Log.w("GcmNotification", "Malformed " + zzdV(str + "_loc_args") + ": " + zze3 + "  Default value will be used.");
            return null;
        } catch (Throwable e2) {
            Log.w("GcmNotification", "Missing format argument for " + zze2 + ": " + zze3 + " Default value will be used.", e2);
            return null;
        }
    }

    static boolean zzy(Bundle bundle) {
        return "1".equals(zze(bundle, "gcm.n.e")) || zze(bundle, "gcm.n.icon") != null;
    }

    private int zzyj() {
        return (int) SystemClock.uptimeMillis();
    }

    static void zzz(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        Iterator it = bundle.keySet().iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if (str.startsWith("gcm.n.")) {
                bundle2.putString(str.substring(6), bundle.getString(str));
                it.remove();
            } else if (str.startsWith("gcm.notification.")) {
                bundle2.putString(str.substring(17), bundle.getString(str));
                it.remove();
            }
        }
        if (!bundle2.isEmpty()) {
            bundle.putBundle("notification", bundle2);
        }
    }

    boolean zzA(Bundle bundle) {
        try {
            zza(zze(bundle, "gcm.n.tag"), zzB(bundle));
            return true;
        } catch (zza e) {
            Log.e("GcmNotification", "Failed to show notification: " + e.getMessage());
            return false;
        }
    }
}
