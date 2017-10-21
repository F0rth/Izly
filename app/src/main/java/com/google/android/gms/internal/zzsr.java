package com.google.android.gms.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class zzsr implements Cloneable {
    private zzsp<?, ?> zzbuq;
    private Object zzbur;
    private List<zzsw> zzbus = new ArrayList();

    zzsr() {
    }

    private byte[] toByteArray() throws IOException {
        byte[] bArr = new byte[zzz()];
        writeTo(zzsn.zzE(bArr));
        return bArr;
    }

    public /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzJr();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzsr)) {
            return false;
        }
        zzsr com_google_android_gms_internal_zzsr = (zzsr) obj;
        if (this.zzbur != null && com_google_android_gms_internal_zzsr.zzbur != null) {
            return this.zzbuq == com_google_android_gms_internal_zzsr.zzbuq ? !this.zzbuq.zzbuk.isArray() ? this.zzbur.equals(com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof byte[] ? Arrays.equals((byte[]) this.zzbur, (byte[]) com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof int[] ? Arrays.equals((int[]) this.zzbur, (int[]) com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof long[] ? Arrays.equals((long[]) this.zzbur, (long[]) com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof float[] ? Arrays.equals((float[]) this.zzbur, (float[]) com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof double[] ? Arrays.equals((double[]) this.zzbur, (double[]) com_google_android_gms_internal_zzsr.zzbur) : this.zzbur instanceof boolean[] ? Arrays.equals((boolean[]) this.zzbur, (boolean[]) com_google_android_gms_internal_zzsr.zzbur) : Arrays.deepEquals((Object[]) this.zzbur, (Object[]) com_google_android_gms_internal_zzsr.zzbur) : false;
        } else {
            if (this.zzbus != null && com_google_android_gms_internal_zzsr.zzbus != null) {
                return this.zzbus.equals(com_google_android_gms_internal_zzsr.zzbus);
            }
            try {
                return Arrays.equals(toByteArray(), com_google_android_gms_internal_zzsr.toByteArray());
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public int hashCode() {
        try {
            return Arrays.hashCode(toByteArray()) + 527;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
        if (this.zzbur != null) {
            this.zzbuq.zza(this.zzbur, com_google_android_gms_internal_zzsn);
            return;
        }
        for (zzsw writeTo : this.zzbus) {
            writeTo.writeTo(com_google_android_gms_internal_zzsn);
        }
    }

    public final zzsr zzJr() {
        int i = 0;
        zzsr com_google_android_gms_internal_zzsr = new zzsr();
        try {
            com_google_android_gms_internal_zzsr.zzbuq = this.zzbuq;
            if (this.zzbus == null) {
                com_google_android_gms_internal_zzsr.zzbus = null;
            } else {
                com_google_android_gms_internal_zzsr.zzbus.addAll(this.zzbus);
            }
            if (this.zzbur != null) {
                if (this.zzbur instanceof zzsu) {
                    com_google_android_gms_internal_zzsr.zzbur = ((zzsu) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof byte[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((byte[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof byte[][]) {
                    byte[][] bArr = (byte[][]) this.zzbur;
                    Object obj = new byte[bArr.length][];
                    com_google_android_gms_internal_zzsr.zzbur = obj;
                    for (int i2 = 0; i2 < bArr.length; i2++) {
                        obj[i2] = (byte[]) bArr[i2].clone();
                    }
                } else if (this.zzbur instanceof boolean[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((boolean[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof int[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((int[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof long[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((long[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof float[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((float[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof double[]) {
                    com_google_android_gms_internal_zzsr.zzbur = ((double[]) this.zzbur).clone();
                    return com_google_android_gms_internal_zzsr;
                } else if (this.zzbur instanceof zzsu[]) {
                    zzsu[] com_google_android_gms_internal_zzsuArr = (zzsu[]) this.zzbur;
                    Object obj2 = new zzsu[com_google_android_gms_internal_zzsuArr.length];
                    com_google_android_gms_internal_zzsr.zzbur = obj2;
                    while (i < com_google_android_gms_internal_zzsuArr.length) {
                        obj2[i] = com_google_android_gms_internal_zzsuArr[i].clone();
                        i++;
                    }
                }
            }
            return com_google_android_gms_internal_zzsr;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    void zza(zzsw com_google_android_gms_internal_zzsw) {
        this.zzbus.add(com_google_android_gms_internal_zzsw);
    }

    <T> T zzb(zzsp<?, T> com_google_android_gms_internal_zzsp___T) {
        if (this.zzbur == null) {
            this.zzbuq = com_google_android_gms_internal_zzsp___T;
            this.zzbur = com_google_android_gms_internal_zzsp___T.zzJ(this.zzbus);
            this.zzbus = null;
        } else if (this.zzbuq != com_google_android_gms_internal_zzsp___T) {
            throw new IllegalStateException("Tried to getExtension with a differernt Extension.");
        }
        return this.zzbur;
    }

    int zzz() {
        if (this.zzbur != null) {
            return this.zzbuq.zzY(this.zzbur);
        }
        int i = 0;
        for (zzsw zzz : this.zzbus) {
            i += zzz.zzz();
        }
        return i;
    }
}
