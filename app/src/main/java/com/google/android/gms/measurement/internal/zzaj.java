package com.google.android.gms.measurement.internal;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.ad4screen.sdk.external.shortcutbadger.impl.NewHtcHomeBadger;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzqb.zza;
import com.google.android.gms.internal.zzqb.zzb;
import com.google.android.gms.internal.zzqb.zzc;
import com.google.android.gms.internal.zzqb.zzd;
import com.google.android.gms.internal.zzqb.zze;
import com.google.android.gms.internal.zzqb.zzf;
import com.google.android.gms.internal.zzqb.zzg;
import com.google.android.gms.internal.zzsn;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.BitSet;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class zzaj extends zzy {
    zzaj(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    public static boolean zzI(Bundle bundle) {
        return bundle.getLong("_c") == 1;
    }

    public static boolean zzQ(String str, String str2) {
        return (str == null && str2 == null) ? true : str == null ? false : str.equals(str2);
    }

    private Object zza(int i, Object obj, boolean z) {
        if (obj == null) {
            obj = null;
        } else if (!((obj instanceof Long) || (obj instanceof Float))) {
            if (obj instanceof Integer) {
                return Long.valueOf((long) ((Integer) obj).intValue());
            }
            if (obj instanceof Byte) {
                return Long.valueOf((long) ((Byte) obj).byteValue());
            }
            if (obj instanceof Short) {
                return Long.valueOf((long) ((Short) obj).shortValue());
            }
            if (obj instanceof Boolean) {
                return Long.valueOf(((Boolean) obj).booleanValue() ? 1 : 0);
            } else if (obj instanceof Double) {
                return Float.valueOf((float) ((Double) obj).doubleValue());
            } else {
                if (!(obj instanceof String) && !(obj instanceof Character) && !(obj instanceof CharSequence)) {
                    return null;
                }
                obj = String.valueOf(obj);
                if (obj.length() > i) {
                    return z ? obj.substring(0, i) : null;
                }
            }
        }
        return obj;
    }

    private void zza(String str, String str2, int i, Object obj) {
        if (obj == null) {
            zzAo().zzCH().zzj(str + " value can't be null. Ignoring " + str, str2);
        } else if (!(obj instanceof Long) && !(obj instanceof Float) && !(obj instanceof Integer) && !(obj instanceof Byte) && !(obj instanceof Short) && !(obj instanceof Boolean) && !(obj instanceof Double)) {
            if ((obj instanceof String) || (obj instanceof Character) || (obj instanceof CharSequence)) {
                String valueOf = String.valueOf(obj);
                if (valueOf.length() > i) {
                    zzAo().zzCH().zze("Ignoring " + str + ". Value is too long. name, value length", str2, Integer.valueOf(valueOf.length()));
                }
            }
        }
    }

    private static void zza(StringBuilder stringBuilder, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            stringBuilder.append("  ");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, zze com_google_android_gms_internal_zzqb_zze) {
        if (com_google_android_gms_internal_zzqb_zze != null) {
            zza(stringBuilder, i);
            stringBuilder.append("bundle {\n");
            zza(stringBuilder, i, "protocol_version", com_google_android_gms_internal_zzqb_zze.zzbal);
            zza(stringBuilder, i, "platform", com_google_android_gms_internal_zzqb_zze.zzbat);
            zza(stringBuilder, i, "gmp_version", com_google_android_gms_internal_zzqb_zze.zzbax);
            zza(stringBuilder, i, "uploading_gmp_version", com_google_android_gms_internal_zzqb_zze.zzbay);
            zza(stringBuilder, i, "gmp_app_id", com_google_android_gms_internal_zzqb_zze.zzaVt);
            zza(stringBuilder, i, "app_id", com_google_android_gms_internal_zzqb_zze.appId);
            zza(stringBuilder, i, "app_version", com_google_android_gms_internal_zzqb_zze.zzaMV);
            zza(stringBuilder, i, "dev_cert_hash", com_google_android_gms_internal_zzqb_zze.zzbaC);
            zza(stringBuilder, i, "app_store", com_google_android_gms_internal_zzqb_zze.zzaVu);
            zza(stringBuilder, i, "upload_timestamp_millis", com_google_android_gms_internal_zzqb_zze.zzbao);
            zza(stringBuilder, i, "start_timestamp_millis", com_google_android_gms_internal_zzqb_zze.zzbap);
            zza(stringBuilder, i, "end_timestamp_millis", com_google_android_gms_internal_zzqb_zze.zzbaq);
            zza(stringBuilder, i, "previous_bundle_start_timestamp_millis", com_google_android_gms_internal_zzqb_zze.zzbar);
            zza(stringBuilder, i, "previous_bundle_end_timestamp_millis", com_google_android_gms_internal_zzqb_zze.zzbas);
            zza(stringBuilder, i, "app_instance_id", com_google_android_gms_internal_zzqb_zze.zzbaB);
            zza(stringBuilder, i, "resettable_device_id", com_google_android_gms_internal_zzqb_zze.zzbaz);
            zza(stringBuilder, i, "limited_ad_tracking", com_google_android_gms_internal_zzqb_zze.zzbaA);
            zza(stringBuilder, i, "os_version", com_google_android_gms_internal_zzqb_zze.osVersion);
            zza(stringBuilder, i, "device_model", com_google_android_gms_internal_zzqb_zze.zzbau);
            zza(stringBuilder, i, "user_default_language", com_google_android_gms_internal_zzqb_zze.zzbav);
            zza(stringBuilder, i, "time_zone_offset_minutes", com_google_android_gms_internal_zzqb_zze.zzbaw);
            zza(stringBuilder, i, "bundle_sequential_index", com_google_android_gms_internal_zzqb_zze.zzbaD);
            zza(stringBuilder, i, "service_upload", com_google_android_gms_internal_zzqb_zze.zzbaE);
            zza(stringBuilder, i, "health_monitor", com_google_android_gms_internal_zzqb_zze.zzaVx);
            zza(stringBuilder, i, com_google_android_gms_internal_zzqb_zze.zzban);
            zza(stringBuilder, i, com_google_android_gms_internal_zzqb_zze.zzbaF);
            zza(stringBuilder, i, com_google_android_gms_internal_zzqb_zze.zzbam);
            zza(stringBuilder, i);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, zzf com_google_android_gms_internal_zzqb_zzf) {
        int i2 = 0;
        if (com_google_android_gms_internal_zzqb_zzf != null) {
            int i3;
            long j;
            int i4 = i + 1;
            zza(stringBuilder, i4);
            stringBuilder.append(str);
            stringBuilder.append(" {\n");
            if (com_google_android_gms_internal_zzqb_zzf.zzbaH != null) {
                zza(stringBuilder, i4 + 1);
                stringBuilder.append("results: ");
                long[] jArr = com_google_android_gms_internal_zzqb_zzf.zzbaH;
                int length = jArr.length;
                i3 = 0;
                int i5 = 0;
                while (i3 < length) {
                    j = jArr[i3];
                    if (i5 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(Long.valueOf(j));
                    i3++;
                    i5++;
                }
                stringBuilder.append('\n');
            }
            if (com_google_android_gms_internal_zzqb_zzf.zzbaG != null) {
                zza(stringBuilder, i4 + 1);
                stringBuilder.append("status: ");
                long[] jArr2 = com_google_android_gms_internal_zzqb_zzf.zzbaG;
                int length2 = jArr2.length;
                i3 = 0;
                while (i2 < length2) {
                    j = jArr2[i2];
                    if (i3 != 0) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(Long.valueOf(j));
                    i2++;
                    i3++;
                }
                stringBuilder.append('\n');
            }
            zza(stringBuilder, i4);
            stringBuilder.append("}\n");
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, String str, Object obj) {
        if (obj != null) {
            zza(stringBuilder, i + 1);
            stringBuilder.append(str);
            stringBuilder.append(": ");
            stringBuilder.append(obj);
            stringBuilder.append('\n');
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, zza[] com_google_android_gms_internal_zzqb_zzaArr) {
        if (com_google_android_gms_internal_zzqb_zzaArr != null) {
            int i2 = i + 1;
            for (zza com_google_android_gms_internal_zzqb_zza : com_google_android_gms_internal_zzqb_zzaArr) {
                if (com_google_android_gms_internal_zzqb_zza != null) {
                    zza(stringBuilder, i2);
                    stringBuilder.append("audience_membership {\n");
                    zza(stringBuilder, i2, "audience_id", com_google_android_gms_internal_zzqb_zza.zzaZr);
                    zza(stringBuilder, i2, "new_audience", com_google_android_gms_internal_zzqb_zza.zzbac);
                    zza(stringBuilder, i2, "current_data", com_google_android_gms_internal_zzqb_zza.zzbaa);
                    zza(stringBuilder, i2, "previous_data", com_google_android_gms_internal_zzqb_zza.zzbab);
                    zza(stringBuilder, i2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, zzb[] com_google_android_gms_internal_zzqb_zzbArr) {
        if (com_google_android_gms_internal_zzqb_zzbArr != null) {
            int i2 = i + 1;
            for (zzb com_google_android_gms_internal_zzqb_zzb : com_google_android_gms_internal_zzqb_zzbArr) {
                if (com_google_android_gms_internal_zzqb_zzb != null) {
                    zza(stringBuilder, i2);
                    stringBuilder.append("event {\n");
                    zza(stringBuilder, i2, "name", com_google_android_gms_internal_zzqb_zzb.name);
                    zza(stringBuilder, i2, "timestamp_millis", com_google_android_gms_internal_zzqb_zzb.zzbaf);
                    zza(stringBuilder, i2, "previous_timestamp_millis", com_google_android_gms_internal_zzqb_zzb.zzbag);
                    zza(stringBuilder, i2, NewHtcHomeBadger.COUNT, com_google_android_gms_internal_zzqb_zzb.count);
                    zza(stringBuilder, i2, com_google_android_gms_internal_zzqb_zzb.zzbae);
                    zza(stringBuilder, i2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, zzc[] com_google_android_gms_internal_zzqb_zzcArr) {
        if (com_google_android_gms_internal_zzqb_zzcArr != null) {
            int i2 = i + 1;
            for (zzc com_google_android_gms_internal_zzqb_zzc : com_google_android_gms_internal_zzqb_zzcArr) {
                if (com_google_android_gms_internal_zzqb_zzc != null) {
                    zza(stringBuilder, i2);
                    stringBuilder.append("event {\n");
                    zza(stringBuilder, i2, "name", com_google_android_gms_internal_zzqb_zzc.name);
                    zza(stringBuilder, i2, "string_value", com_google_android_gms_internal_zzqb_zzc.zzamJ);
                    zza(stringBuilder, i2, "int_value", com_google_android_gms_internal_zzqb_zzc.zzbai);
                    zza(stringBuilder, i2, "float_value", com_google_android_gms_internal_zzqb_zzc.zzaZo);
                    zza(stringBuilder, i2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    private static void zza(StringBuilder stringBuilder, int i, zzg[] com_google_android_gms_internal_zzqb_zzgArr) {
        if (com_google_android_gms_internal_zzqb_zzgArr != null) {
            int i2 = i + 1;
            for (zzg com_google_android_gms_internal_zzqb_zzg : com_google_android_gms_internal_zzqb_zzgArr) {
                if (com_google_android_gms_internal_zzqb_zzg != null) {
                    zza(stringBuilder, i2);
                    stringBuilder.append("user_property {\n");
                    zza(stringBuilder, i2, "set_timestamp_millis", com_google_android_gms_internal_zzqb_zzg.zzbaJ);
                    zza(stringBuilder, i2, "name", com_google_android_gms_internal_zzqb_zzg.name);
                    zza(stringBuilder, i2, "string_value", com_google_android_gms_internal_zzqb_zzg.zzamJ);
                    zza(stringBuilder, i2, "int_value", com_google_android_gms_internal_zzqb_zzg.zzbai);
                    zza(stringBuilder, i2, "float_value", com_google_android_gms_internal_zzqb_zzg.zzaZo);
                    zza(stringBuilder, i2);
                    stringBuilder.append("}\n");
                }
            }
        }
    }

    public static boolean zza(Context context, Class<? extends Service> cls) {
        try {
            ServiceInfo serviceInfo = context.getPackageManager().getServiceInfo(new ComponentName(context, cls), 4);
            if (serviceInfo != null && serviceInfo.enabled) {
                return true;
            }
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    public static boolean zza(Context context, Class<? extends BroadcastReceiver> cls, boolean z) {
        try {
            ActivityInfo receiverInfo = context.getPackageManager().getReceiverInfo(new ComponentName(context, cls), 2);
            if (receiverInfo != null && receiverInfo.enabled && (!z || receiverInfo.exported)) {
                return true;
            }
        } catch (NameNotFoundException e) {
        }
        return false;
    }

    public static boolean zza(long[] jArr, int i) {
        return i < jArr.length * 64 && (jArr[i / 64] & (1 << (i % 64))) != 0;
    }

    public static long[] zza(BitSet bitSet) {
        int length = (bitSet.length() + 63) / 64;
        long[] jArr = new long[length];
        int i = 0;
        while (i < length) {
            jArr[i] = 0;
            int i2 = 0;
            while (i2 < 64 && (i * 64) + i2 < bitSet.length()) {
                if (bitSet.get((i * 64) + i2)) {
                    jArr[i] = jArr[i] | (1 << i2);
                }
                i2++;
            }
            i++;
        }
        return jArr;
    }

    public static String zzb(zzd com_google_android_gms_internal_zzqb_zzd) {
        if (com_google_android_gms_internal_zzqb_zzd == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nbatch {\n");
        if (com_google_android_gms_internal_zzqb_zzd.zzbaj != null) {
            for (zze com_google_android_gms_internal_zzqb_zze : com_google_android_gms_internal_zzqb_zzd.zzbaj) {
                if (com_google_android_gms_internal_zzqb_zze != null) {
                    zza(stringBuilder, 1, com_google_android_gms_internal_zzqb_zze);
                }
            }
        }
        stringBuilder.append("}\n");
        return stringBuilder.toString();
    }

    static MessageDigest zzbv(String str) {
        int i = 0;
        while (i < 2) {
            try {
                MessageDigest instance = MessageDigest.getInstance(str);
                if (instance != null) {
                    return instance;
                }
                i++;
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return null;
    }

    static boolean zzfq(String str) {
        zzx.zzcM(str);
        return str.charAt(0) != '_';
    }

    private int zzfu(String str) {
        return "_ldl".equals(str) ? zzCp().zzBG() : zzCp().zzBF();
    }

    public static boolean zzfv(String str) {
        return !TextUtils.isEmpty(str) && str.startsWith("_");
    }

    static long zzq(byte[] bArr) {
        zzx.zzz(bArr);
        zzx.zzab(bArr.length > 0);
        long j = 0;
        int length = bArr.length - 1;
        long j2 = 0;
        while (length >= 0 && length >= bArr.length - 8) {
            j2 += (((long) bArr[length]) & 255) << j;
            j += 8;
            length--;
        }
        return j2;
    }

    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public /* bridge */ /* synthetic */ zzp zzAo() {
        return super.zzAo();
    }

    public /* bridge */ /* synthetic */ void zzCd() {
        super.zzCd();
    }

    public /* bridge */ /* synthetic */ zzc zzCe() {
        return super.zzCe();
    }

    public /* bridge */ /* synthetic */ zzab zzCf() {
        return super.zzCf();
    }

    public /* bridge */ /* synthetic */ zzn zzCg() {
        return super.zzCg();
    }

    public /* bridge */ /* synthetic */ zzg zzCh() {
        return super.zzCh();
    }

    public /* bridge */ /* synthetic */ zzac zzCi() {
        return super.zzCi();
    }

    public /* bridge */ /* synthetic */ zze zzCj() {
        return super.zzCj();
    }

    public /* bridge */ /* synthetic */ zzaj zzCk() {
        return super.zzCk();
    }

    public /* bridge */ /* synthetic */ zzu zzCl() {
        return super.zzCl();
    }

    public /* bridge */ /* synthetic */ zzad zzCm() {
        return super.zzCm();
    }

    public /* bridge */ /* synthetic */ zzv zzCn() {
        return super.zzCn();
    }

    public /* bridge */ /* synthetic */ zzt zzCo() {
        return super.zzCo();
    }

    public /* bridge */ /* synthetic */ zzd zzCp() {
        return super.zzCp();
    }

    public void zza(Bundle bundle, String str, Object obj) {
        if (obj instanceof Long) {
            bundle.putLong(str, ((Long) obj).longValue());
        } else if (obj instanceof Float) {
            bundle.putFloat(str, ((Float) obj).floatValue());
        } else if (obj instanceof String) {
            bundle.putString(str, String.valueOf(obj));
        } else if (str != null) {
            zzAo().zzCH().zze("Not putting event parameter. Invalid value type. name, type", str, obj.getClass().getSimpleName());
        }
    }

    public void zza(zzc com_google_android_gms_internal_zzqb_zzc, Object obj) {
        zzx.zzz(obj);
        com_google_android_gms_internal_zzqb_zzc.zzamJ = null;
        com_google_android_gms_internal_zzqb_zzc.zzbai = null;
        com_google_android_gms_internal_zzqb_zzc.zzaZo = null;
        if (obj instanceof String) {
            com_google_android_gms_internal_zzqb_zzc.zzamJ = (String) obj;
        } else if (obj instanceof Long) {
            com_google_android_gms_internal_zzqb_zzc.zzbai = (Long) obj;
        } else if (obj instanceof Float) {
            com_google_android_gms_internal_zzqb_zzc.zzaZo = (Float) obj;
        } else {
            zzAo().zzCE().zzj("Ignoring invalid (type) event param value", obj);
        }
    }

    public void zza(zzg com_google_android_gms_internal_zzqb_zzg, Object obj) {
        zzx.zzz(obj);
        com_google_android_gms_internal_zzqb_zzg.zzamJ = null;
        com_google_android_gms_internal_zzqb_zzg.zzbai = null;
        com_google_android_gms_internal_zzqb_zzg.zzaZo = null;
        if (obj instanceof String) {
            com_google_android_gms_internal_zzqb_zzg.zzamJ = (String) obj;
        } else if (obj instanceof Long) {
            com_google_android_gms_internal_zzqb_zzg.zzbai = (Long) obj;
        } else if (obj instanceof Float) {
            com_google_android_gms_internal_zzqb_zzg.zzaZo = (Float) obj;
        } else {
            zzAo().zzCE().zzj("Ignoring invalid (type) user attribute value", obj);
        }
    }

    public byte[] zza(zzd com_google_android_gms_internal_zzqb_zzd) {
        try {
            byte[] bArr = new byte[com_google_android_gms_internal_zzqb_zzd.getSerializedSize()];
            zzsn zzE = zzsn.zzE(bArr);
            com_google_android_gms_internal_zzqb_zzd.writeTo(zzE);
            zzE.zzJo();
            return bArr;
        } catch (IOException e) {
            zzAo().zzCE().zzj("Data loss. Failed to serialize batch", e);
            return null;
        }
    }

    @WorkerThread
    public boolean zzbk(String str) {
        zzjk();
        if (getContext().checkCallingOrSelfPermission(str) == 0) {
            return true;
        }
        zzAo().zzCJ().zzj("Permission not granted", str);
        return false;
    }

    void zzc(String str, int i, String str2) {
        if (str2 == null) {
            throw new IllegalArgumentException(str + " name is required and can't be null");
        } else if (str2.length() == 0) {
            throw new IllegalArgumentException(str + " name is required and can't be empty");
        } else {
            char charAt = str2.charAt(0);
            if (Character.isLetter(charAt) || charAt == '_') {
                int i2 = 1;
                while (i2 < str2.length()) {
                    char charAt2 = str2.charAt(i2);
                    if (charAt2 == '_' || Character.isLetterOrDigit(charAt2)) {
                        i2++;
                    } else {
                        throw new IllegalArgumentException(str + " name must consist of letters, digits or _ (underscores)");
                    }
                }
                if (str2.length() > i) {
                    throw new IllegalArgumentException(str + " name is too long. The maximum supported length is " + i);
                }
                return;
            }
            throw new IllegalArgumentException(str + " name must start with a letter or _");
        }
    }

    public boolean zzc(long j, long j2) {
        return j == 0 || j2 <= 0 || Math.abs(zzjl().currentTimeMillis() - j) > j2;
    }

    public void zzfr(String str) {
        zzc("event", zzCp().zzBB(), str);
    }

    public void zzfs(String str) {
        zzc("user attribute", zzCp().zzBC(), str);
    }

    public void zzft(String str) {
        zzc("event param", zzCp().zzBC(), str);
    }

    public byte[] zzg(byte[] bArr) throws IOException {
        try {
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            gZIPOutputStream.write(bArr);
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            zzAo().zzCE().zzj("Failed to gzip content", e);
            throw e;
        }
    }

    public /* bridge */ /* synthetic */ void zzjj() {
        super.zzjj();
    }

    public /* bridge */ /* synthetic */ void zzjk() {
        super.zzjk();
    }

    public /* bridge */ /* synthetic */ zzmq zzjl() {
        return super.zzjl();
    }

    public Object zzk(String str, Object obj) {
        return zza(zzfv(str) ? zzCp().zzBE() : zzCp().zzBD(), obj, false);
    }

    public void zzl(String str, Object obj) {
        if ("_ldl".equals(str)) {
            zza("user attribute referrer", str, zzfu(str), obj);
        } else {
            zza("user attribute", str, zzfu(str), obj);
        }
    }

    public Object zzm(String str, Object obj) {
        return "_ldl".equals(str) ? zza(zzfu(str), obj, true) : zza(zzfu(str), obj, false);
    }

    public byte[] zzp(byte[] bArr) throws IOException {
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArr2 = new byte[1024];
            while (true) {
                int read = gZIPInputStream.read(bArr2);
                if (read > 0) {
                    byteArrayOutputStream.write(bArr2, 0, read);
                } else {
                    gZIPInputStream.close();
                    byteArrayInputStream.close();
                    return byteArrayOutputStream.toByteArray();
                }
            }
        } catch (IOException e) {
            zzAo().zzCE().zzj("Failed to ungzip content", e);
            throw e;
        }
    }

    public long zzr(byte[] bArr) {
        zzx.zzz(bArr);
        MessageDigest zzbv = zzbv("MD5");
        if (zzbv != null) {
            return zzq(zzbv.digest(bArr));
        }
        zzAo().zzCE().zzfg("Failed to get MD5");
        return 0;
    }
}
