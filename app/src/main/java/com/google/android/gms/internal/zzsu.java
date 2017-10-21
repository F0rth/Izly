package com.google.android.gms.internal;

import java.io.IOException;
import java.util.Arrays;

public abstract class zzsu {
    protected volatile int zzbuu = -1;

    public static final <T extends zzsu> T mergeFrom(T t, byte[] bArr) throws zzst {
        return mergeFrom(t, bArr, 0, bArr.length);
    }

    public static final <T extends zzsu> T mergeFrom(T t, byte[] bArr, int i, int i2) throws zzst {
        try {
            zzsm zza = zzsm.zza(bArr, i, i2);
            t.mergeFrom(zza);
            zza.zzmn(0);
            return t;
        } catch (zzst e) {
            throw e;
        } catch (IOException e2) {
            throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).");
        }
    }

    public static final boolean messageNanoEquals(zzsu com_google_android_gms_internal_zzsu, zzsu com_google_android_gms_internal_zzsu2) {
        if (com_google_android_gms_internal_zzsu == com_google_android_gms_internal_zzsu2) {
            return true;
        }
        if (com_google_android_gms_internal_zzsu == null || com_google_android_gms_internal_zzsu2 == null || com_google_android_gms_internal_zzsu.getClass() != com_google_android_gms_internal_zzsu2.getClass()) {
            return false;
        }
        int serializedSize = com_google_android_gms_internal_zzsu.getSerializedSize();
        if (com_google_android_gms_internal_zzsu2.getSerializedSize() != serializedSize) {
            return false;
        }
        byte[] bArr = new byte[serializedSize];
        byte[] bArr2 = new byte[serializedSize];
        toByteArray(com_google_android_gms_internal_zzsu, bArr, 0, serializedSize);
        toByteArray(com_google_android_gms_internal_zzsu2, bArr2, 0, serializedSize);
        return Arrays.equals(bArr, bArr2);
    }

    public static final void toByteArray(zzsu com_google_android_gms_internal_zzsu, byte[] bArr, int i, int i2) {
        try {
            zzsn zzb = zzsn.zzb(bArr, i, i2);
            com_google_android_gms_internal_zzsu.writeTo(zzb);
            zzb.zzJo();
        } catch (Throwable e) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", e);
        }
    }

    public static final byte[] toByteArray(zzsu com_google_android_gms_internal_zzsu) {
        byte[] bArr = new byte[com_google_android_gms_internal_zzsu.getSerializedSize()];
        toByteArray(com_google_android_gms_internal_zzsu, bArr, 0, bArr.length);
        return bArr;
    }

    public zzsu clone() throws CloneNotSupportedException {
        return (zzsu) super.clone();
    }

    public int getCachedSize() {
        if (this.zzbuu < 0) {
            getSerializedSize();
        }
        return this.zzbuu;
    }

    public int getSerializedSize() {
        int zzz = zzz();
        this.zzbuu = zzz;
        return zzz;
    }

    public abstract zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException;

    public String toString() {
        return zzsv.zzf(this);
    }

    public void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
    }

    public int zzz() {
        return 0;
    }
}
