package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.zzw;
import com.google.android.gms.common.internal.zzx;

public abstract class zzc {
    protected final DataHolder zzahi;
    protected int zzaje;
    private int zzajf;

    public zzc(DataHolder dataHolder, int i) {
        this.zzahi = (DataHolder) zzx.zzz(dataHolder);
        zzbF(i);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        zzc com_google_android_gms_common_data_zzc = (zzc) obj;
        return zzw.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.zzaje), Integer.valueOf(this.zzaje)) && zzw.equal(Integer.valueOf(com_google_android_gms_common_data_zzc.zzajf), Integer.valueOf(this.zzajf)) && com_google_android_gms_common_data_zzc.zzahi == this.zzahi;
    }

    protected boolean getBoolean(String str) {
        return this.zzahi.zze(str, this.zzaje, this.zzajf);
    }

    protected byte[] getByteArray(String str) {
        return this.zzahi.zzg(str, this.zzaje, this.zzajf);
    }

    protected float getFloat(String str) {
        return this.zzahi.zzf(str, this.zzaje, this.zzajf);
    }

    protected int getInteger(String str) {
        return this.zzahi.zzc(str, this.zzaje, this.zzajf);
    }

    protected long getLong(String str) {
        return this.zzahi.zzb(str, this.zzaje, this.zzajf);
    }

    protected String getString(String str) {
        return this.zzahi.zzd(str, this.zzaje, this.zzajf);
    }

    public int hashCode() {
        return zzw.hashCode(Integer.valueOf(this.zzaje), Integer.valueOf(this.zzajf), this.zzahi);
    }

    public boolean isDataValid() {
        return !this.zzahi.isClosed();
    }

    protected void zza(String str, CharArrayBuffer charArrayBuffer) {
        this.zzahi.zza(str, this.zzaje, this.zzajf, charArrayBuffer);
    }

    protected void zzbF(int i) {
        boolean z = i >= 0 && i < this.zzahi.getCount();
        zzx.zzab(z);
        this.zzaje = i;
        this.zzajf = this.zzahi.zzbH(this.zzaje);
    }

    protected Uri zzcA(String str) {
        return this.zzahi.zzh(str, this.zzaje, this.zzajf);
    }

    protected boolean zzcB(String str) {
        return this.zzahi.zzi(str, this.zzaje, this.zzajf);
    }

    public boolean zzcz(String str) {
        return this.zzahi.zzcz(str);
    }

    protected int zzqc() {
        return this.zzaje;
    }
}
