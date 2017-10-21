package com.google.android.gms.iid;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v4.content.ContextCompat;
import android.util.Base64;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class zzd {
    Context context;
    SharedPreferences zzaNt;

    public zzd(Context context) {
        this(context, "com.google.android.gms.appid");
    }

    public zzd(Context context, String str) {
        this.context = context;
        this.zzaNt = context.getSharedPreferences(str, 4);
        zzeb(str + "-no-backup");
    }

    private void zzeb(String str) {
        File file = new File(new ContextCompat().getNoBackupFilesDir(this.context), str);
        if (!file.exists()) {
            try {
                if (file.createNewFile() && !isEmpty()) {
                    Log.i("InstanceID/Store", "App restored, clearing state");
                    InstanceIDListenerService.zza(this.context, this);
                }
            } catch (IOException e) {
                if (Log.isLoggable("InstanceID/Store", 3)) {
                    Log.d("InstanceID/Store", "Error creating file in no backup dir: " + e.getMessage());
                }
            }
        }
    }

    private String zzh(String str, String str2, String str3) {
        return str + "|T|" + str2 + "|" + str3;
    }

    String get(String str) {
        String string;
        synchronized (this) {
            string = this.zzaNt.getString(str, null);
        }
        return string;
    }

    String get(String str, String str2) {
        String string;
        synchronized (this) {
            string = this.zzaNt.getString(str + "|S|" + str2, null);
        }
        return string;
    }

    boolean isEmpty() {
        return this.zzaNt.getAll().isEmpty();
    }

    void zza(Editor editor, String str, String str2, String str3) {
        synchronized (this) {
            editor.putString(str + "|S|" + str2, str3);
        }
    }

    public void zza(String str, String str2, String str3, String str4, String str5) {
        synchronized (this) {
            String zzh = zzh(str, str2, str3);
            Editor edit = this.zzaNt.edit();
            edit.putString(zzh, str4);
            edit.putString("appVersion", str5);
            edit.putString("lastToken", Long.toString(System.currentTimeMillis() / 1000));
            edit.commit();
        }
    }

    KeyPair zzd(String str, long j) {
        KeyPair zzyy;
        synchronized (this) {
            zzyy = zza.zzyy();
            Editor edit = this.zzaNt.edit();
            zza(edit, str, "|P|", InstanceID.zzn(zzyy.getPublic().getEncoded()));
            zza(edit, str, "|K|", InstanceID.zzn(zzyy.getPrivate().getEncoded()));
            zza(edit, str, "cre", Long.toString(j));
            edit.commit();
        }
        return zzyy;
    }

    public void zzec(String str) {
        synchronized (this) {
            Editor edit = this.zzaNt.edit();
            for (String str2 : this.zzaNt.getAll().keySet()) {
                if (str2.startsWith(str)) {
                    edit.remove(str2);
                }
            }
            edit.commit();
        }
    }

    public KeyPair zzed(String str) {
        return zzeg(str);
    }

    void zzee(String str) {
        zzec(str + "|");
    }

    public void zzef(String str) {
        zzec(str + "|T|");
    }

    KeyPair zzeg(String str) {
        Object e;
        String str2 = get(str, "|P|");
        String str3 = get(str, "|K|");
        if (str3 == null) {
            return null;
        }
        try {
            byte[] decode = Base64.decode(str2, 8);
            byte[] decode2 = Base64.decode(str3, 8);
            KeyFactory instance = KeyFactory.getInstance("RSA");
            return new KeyPair(instance.generatePublic(new X509EncodedKeySpec(decode)), instance.generatePrivate(new PKCS8EncodedKeySpec(decode2)));
        } catch (InvalidKeySpecException e2) {
            e = e2;
            Log.w("InstanceID/Store", "Invalid key stored " + e);
            InstanceIDListenerService.zza(this.context, this);
            return null;
        } catch (NoSuchAlgorithmException e3) {
            e = e3;
            Log.w("InstanceID/Store", "Invalid key stored " + e);
            InstanceIDListenerService.zza(this.context, this);
            return null;
        }
    }

    public String zzi(String str, String str2, String str3) {
        String string;
        synchronized (this) {
            string = this.zzaNt.getString(zzh(str, str2, str3), null);
        }
        return string;
    }

    public void zzj(String str, String str2, String str3) {
        synchronized (this) {
            String zzh = zzh(str, str2, str3);
            Editor edit = this.zzaNt.edit();
            edit.remove(zzh);
            edit.commit();
        }
    }

    public void zzyG() {
        synchronized (this) {
            this.zzaNt.edit().clear().commit();
        }
    }
}
