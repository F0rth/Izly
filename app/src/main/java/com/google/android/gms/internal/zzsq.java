package com.google.android.gms.internal;

public final class zzsq implements Cloneable {
    private static final zzsr zzbum = new zzsr();
    private int mSize;
    private boolean zzbun;
    private int[] zzbuo;
    private zzsr[] zzbup;

    zzsq() {
        this(10);
    }

    zzsq(int i) {
        this.zzbun = false;
        int idealIntArraySize = idealIntArraySize(i);
        this.zzbuo = new int[idealIntArraySize];
        this.zzbup = new zzsr[idealIntArraySize];
        this.mSize = 0;
    }

    private void gc() {
        int i = this.mSize;
        int[] iArr = this.zzbuo;
        zzsr[] com_google_android_gms_internal_zzsrArr = this.zzbup;
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            zzsr com_google_android_gms_internal_zzsr = com_google_android_gms_internal_zzsrArr[i3];
            if (com_google_android_gms_internal_zzsr != zzbum) {
                if (i3 != i2) {
                    iArr[i2] = iArr[i3];
                    com_google_android_gms_internal_zzsrArr[i2] = com_google_android_gms_internal_zzsr;
                    com_google_android_gms_internal_zzsrArr[i3] = null;
                }
                i2++;
            }
        }
        this.zzbun = false;
        this.mSize = i2;
    }

    private int idealByteArraySize(int i) {
        for (int i2 = 4; i2 < 32; i2++) {
            if (i <= (1 << i2) - 12) {
                return (1 << i2) - 12;
            }
        }
        return i;
    }

    private int idealIntArraySize(int i) {
        return idealByteArraySize(i * 4) / 4;
    }

    private boolean zza(int[] iArr, int[] iArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (iArr[i2] != iArr2[i2]) {
                return false;
            }
        }
        return true;
    }

    private boolean zza(zzsr[] com_google_android_gms_internal_zzsrArr, zzsr[] com_google_android_gms_internal_zzsrArr2, int i) {
        for (int i2 = 0; i2 < i; i2++) {
            if (!com_google_android_gms_internal_zzsrArr[i2].equals(com_google_android_gms_internal_zzsrArr2[i2])) {
                return false;
            }
        }
        return true;
    }

    private int zzmH(int i) {
        int i2 = 0;
        int i3 = this.mSize - 1;
        while (i2 <= i3) {
            int i4 = (i2 + i3) >>> 1;
            int i5 = this.zzbuo[i4];
            if (i5 < i) {
                i2 = i4 + 1;
            } else if (i5 <= i) {
                return i4;
            } else {
                i3 = i4 - 1;
            }
        }
        return i2 ^ -1;
    }

    public final /* synthetic */ Object clone() throws CloneNotSupportedException {
        return zzJq();
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzsq)) {
                return false;
            }
            zzsq com_google_android_gms_internal_zzsq = (zzsq) obj;
            if (size() != com_google_android_gms_internal_zzsq.size() || !zza(this.zzbuo, com_google_android_gms_internal_zzsq.zzbuo, this.mSize)) {
                return false;
            }
            if (!zza(this.zzbup, com_google_android_gms_internal_zzsq.zzbup, this.mSize)) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        if (this.zzbun) {
            gc();
        }
        int i = 17;
        for (int i2 = 0; i2 < this.mSize; i2++) {
            i = (((i * 31) + this.zzbuo[i2]) * 31) + this.zzbup[i2].hashCode();
        }
        return i;
    }

    public final boolean isEmpty() {
        return size() == 0;
    }

    final int size() {
        if (this.zzbun) {
            gc();
        }
        return this.mSize;
    }

    public final zzsq zzJq() {
        int i = 0;
        int size = size();
        zzsq com_google_android_gms_internal_zzsq = new zzsq(size);
        System.arraycopy(this.zzbuo, 0, com_google_android_gms_internal_zzsq.zzbuo, 0, size);
        while (i < size) {
            if (this.zzbup[i] != null) {
                com_google_android_gms_internal_zzsq.zzbup[i] = this.zzbup[i].zzJr();
            }
            i++;
        }
        com_google_android_gms_internal_zzsq.mSize = size;
        return com_google_android_gms_internal_zzsq;
    }

    final void zza(int i, zzsr com_google_android_gms_internal_zzsr) {
        int zzmH = zzmH(i);
        if (zzmH >= 0) {
            this.zzbup[zzmH] = com_google_android_gms_internal_zzsr;
            return;
        }
        zzmH ^= -1;
        if (zzmH >= this.mSize || this.zzbup[zzmH] != zzbum) {
            if (this.zzbun && this.mSize >= this.zzbuo.length) {
                gc();
                zzmH = zzmH(i) ^ -1;
            }
            if (this.mSize >= this.zzbuo.length) {
                int idealIntArraySize = idealIntArraySize(this.mSize + 1);
                Object obj = new int[idealIntArraySize];
                Object obj2 = new zzsr[idealIntArraySize];
                System.arraycopy(this.zzbuo, 0, obj, 0, this.zzbuo.length);
                System.arraycopy(this.zzbup, 0, obj2, 0, this.zzbup.length);
                this.zzbuo = obj;
                this.zzbup = obj2;
            }
            if (this.mSize - zzmH != 0) {
                System.arraycopy(this.zzbuo, zzmH, this.zzbuo, zzmH + 1, this.mSize - zzmH);
                System.arraycopy(this.zzbup, zzmH, this.zzbup, zzmH + 1, this.mSize - zzmH);
            }
            this.zzbuo[zzmH] = i;
            this.zzbup[zzmH] = com_google_android_gms_internal_zzsr;
            this.mSize++;
            return;
        }
        this.zzbuo[zzmH] = i;
        this.zzbup[zzmH] = com_google_android_gms_internal_zzsr;
    }

    final zzsr zzmF(int i) {
        int zzmH = zzmH(i);
        return (zzmH < 0 || this.zzbup[zzmH] == zzbum) ? null : this.zzbup[zzmH];
    }

    final zzsr zzmG(int i) {
        if (this.zzbun) {
            gc();
        }
        return this.zzbup[i];
    }
}
