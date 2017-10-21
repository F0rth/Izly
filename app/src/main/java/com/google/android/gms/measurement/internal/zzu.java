package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import com.google.android.gms.common.internal.zzx;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.internal.zzqa.zza;
import com.google.android.gms.internal.zzqa.zzb;
import com.google.android.gms.internal.zzqa.zzc;
import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import java.io.IOException;
import java.util.Map;

public class zzu extends zzz {
    private final Map<String, Map<String, String>> zzaXF = new ArrayMap();
    private final Map<String, Map<String, Boolean>> zzaXG = new ArrayMap();
    private final Map<String, zzb> zzaXH = new ArrayMap();

    zzu(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    private Map<String, String> zza(zzb com_google_android_gms_internal_zzqa_zzb) {
        Map<String, String> arrayMap = new ArrayMap();
        if (!(com_google_android_gms_internal_zzqa_zzb == null || com_google_android_gms_internal_zzqa_zzb.zzaZV == null)) {
            for (zzc com_google_android_gms_internal_zzqa_zzc : com_google_android_gms_internal_zzqa_zzb.zzaZV) {
                if (com_google_android_gms_internal_zzqa_zzc != null) {
                    arrayMap.put(com_google_android_gms_internal_zzqa_zzc.key, com_google_android_gms_internal_zzqa_zzc.value);
                }
            }
        }
        return arrayMap;
    }

    private Map<String, Boolean> zzb(zzb com_google_android_gms_internal_zzqa_zzb) {
        Map<String, Boolean> arrayMap = new ArrayMap();
        if (!(com_google_android_gms_internal_zzqa_zzb == null || com_google_android_gms_internal_zzqa_zzb.zzaZW == null)) {
            for (zza com_google_android_gms_internal_zzqa_zza : com_google_android_gms_internal_zzqa_zzb.zzaZW) {
                if (com_google_android_gms_internal_zzqa_zza != null) {
                    arrayMap.put(com_google_android_gms_internal_zzqa_zza.name, com_google_android_gms_internal_zzqa_zza.zzaZS);
                }
            }
        }
        return arrayMap;
    }

    @WorkerThread
    private zzb zzf(String str, byte[] bArr) {
        if (bArr == null) {
            return new zzb();
        }
        zzsm zzD = zzsm.zzD(bArr);
        zzb com_google_android_gms_internal_zzqa_zzb = new zzb();
        try {
            com_google_android_gms_internal_zzqa_zzb.zzA(zzD);
            zzAo().zzCK().zze("Parsed config. version, gmp_app_id", com_google_android_gms_internal_zzqa_zzb.zzaZT, com_google_android_gms_internal_zzqa_zzb.zzaVt);
            return com_google_android_gms_internal_zzqa_zzb;
        } catch (IOException e) {
            zzAo().zzCF().zze("Unable to merge remote config", str, e);
            return null;
        }
    }

    @WorkerThread
    private void zzfj(String str) {
        zzjv();
        zzjk();
        zzx.zzcM(str);
        if (!this.zzaXH.containsKey(str)) {
            byte[] zzfa = zzCj().zzfa(str);
            if (zzfa == null) {
                this.zzaXF.put(str, null);
                this.zzaXG.put(str, null);
                this.zzaXH.put(str, null);
                return;
            }
            zzb zzf = zzf(str, zzfa);
            this.zzaXF.put(str, zza(zzf));
            this.zzaXG.put(str, zzb(zzf));
            this.zzaXH.put(str, zzf);
        }
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

    @WorkerThread
    String zzO(String str, String str2) {
        zzjk();
        zzfj(str);
        Map map = (Map) this.zzaXF.get(str);
        return map != null ? (String) map.get(str2) : null;
    }

    @WorkerThread
    boolean zzP(String str, String str2) {
        zzjk();
        zzfj(str);
        Map map = (Map) this.zzaXG.get(str);
        if (map == null) {
            return false;
        }
        Boolean bool = (Boolean) map.get(str2);
        return bool == null ? false : bool.booleanValue();
    }

    @WorkerThread
    protected boolean zze(String str, byte[] bArr) {
        zzjv();
        zzjk();
        zzx.zzcM(str);
        zzb zzf = zzf(str, bArr);
        if (zzf == null) {
            return false;
        }
        this.zzaXG.put(str, zzb(zzf));
        this.zzaXH.put(str, zzf);
        this.zzaXF.put(str, zza(zzf));
        zzCe().zza(str, zzf.zzaZX);
        try {
            zzf.zzaZX = null;
            byte[] bArr2 = new byte[zzf.getSerializedSize()];
            zzf.writeTo(zzsn.zzE(bArr2));
            bArr = bArr2;
        } catch (IOException e) {
            zzAo().zzCF().zzj("Unable to serialize reduced-size config.  Storing full config instead.", e);
        }
        zzCj().zzd(str, bArr);
        return true;
    }

    @WorkerThread
    protected zzb zzfk(String str) {
        zzjv();
        zzjk();
        zzx.zzcM(str);
        zzfj(str);
        return (zzb) this.zzaXH.get(str);
    }

    protected void zziJ() {
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
}
