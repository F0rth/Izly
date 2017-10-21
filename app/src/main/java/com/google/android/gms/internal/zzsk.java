package com.google.android.gms.internal;

import java.io.IOException;

public final class zzsk extends zzso<zzsk> {
    public String[] zzbtT;
    public int[] zzbtU;
    public byte[][] zzbtV;

    public zzsk() {
        zzIW();
    }

    public static zzsk zzB(byte[] bArr) throws zzst {
        return (zzsk) zzsu.mergeFrom(new zzsk(), bArr);
    }

    public final boolean equals(Object obj) {
        if (obj != this) {
            if (!(obj instanceof zzsk)) {
                return false;
            }
            zzsk com_google_android_gms_internal_zzsk = (zzsk) obj;
            if (!zzss.equals(this.zzbtT, com_google_android_gms_internal_zzsk.zzbtT) || !zzss.equals(this.zzbtU, com_google_android_gms_internal_zzsk.zzbtU) || !zzss.zza(this.zzbtV, com_google_android_gms_internal_zzsk.zzbtV)) {
                return false;
            }
            if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                return this.zzbuj.equals(com_google_android_gms_internal_zzsk.zzbuj);
            }
            if (!(com_google_android_gms_internal_zzsk.zzbuj == null || com_google_android_gms_internal_zzsk.zzbuj.isEmpty())) {
                return false;
            }
        }
        return true;
    }

    public final int hashCode() {
        int hashCode = getClass().getName().hashCode();
        int hashCode2 = zzss.hashCode(this.zzbtT);
        int hashCode3 = zzss.hashCode(this.zzbtU);
        int zza = zzss.zza(this.zzbtV);
        int hashCode4 = (this.zzbuj == null || this.zzbuj.isEmpty()) ? 0 : this.zzbuj.hashCode();
        return hashCode4 + ((((((((hashCode + 527) * 31) + hashCode2) * 31) + hashCode3) * 31) + zza) * 31);
    }

    public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
        return zzO(com_google_android_gms_internal_zzsm);
    }

    public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
        int i = 0;
        if (this.zzbtT != null && this.zzbtT.length > 0) {
            for (String str : this.zzbtT) {
                if (str != null) {
                    com_google_android_gms_internal_zzsn.zzn(1, str);
                }
            }
        }
        if (this.zzbtU != null && this.zzbtU.length > 0) {
            for (int zzA : this.zzbtU) {
                com_google_android_gms_internal_zzsn.zzA(2, zzA);
            }
        }
        if (this.zzbtV != null && this.zzbtV.length > 0) {
            while (i < this.zzbtV.length) {
                byte[] bArr = this.zzbtV[i];
                if (bArr != null) {
                    com_google_android_gms_internal_zzsn.zza(3, bArr);
                }
                i++;
            }
        }
        super.writeTo(com_google_android_gms_internal_zzsn);
    }

    public final zzsk zzIW() {
        this.zzbtT = zzsx.zzbuB;
        this.zzbtU = zzsx.zzbuw;
        this.zzbtV = zzsx.zzbuC;
        this.zzbuj = null;
        this.zzbuu = -1;
        return this;
    }

    public final zzsk zzO(zzsm com_google_android_gms_internal_zzsm) throws IOException {
        while (true) {
            int zzIX = com_google_android_gms_internal_zzsm.zzIX();
            int zzc;
            Object obj;
            switch (zzIX) {
                case 0:
                    break;
                case 10:
                    zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 10);
                    zzIX = this.zzbtT == null ? 0 : this.zzbtT.length;
                    obj = new String[(zzc + zzIX)];
                    if (zzIX != 0) {
                        System.arraycopy(this.zzbtT, 0, obj, 0, zzIX);
                    }
                    while (zzIX < obj.length - 1) {
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                        com_google_android_gms_internal_zzsm.zzIX();
                        zzIX++;
                    }
                    obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                    this.zzbtT = obj;
                    continue;
                case 16:
                    zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 16);
                    zzIX = this.zzbtU == null ? 0 : this.zzbtU.length;
                    obj = new int[(zzc + zzIX)];
                    if (zzIX != 0) {
                        System.arraycopy(this.zzbtU, 0, obj, 0, zzIX);
                    }
                    while (zzIX < obj.length - 1) {
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                        com_google_android_gms_internal_zzsm.zzIX();
                        zzIX++;
                    }
                    obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                    this.zzbtU = obj;
                    continue;
                case 18:
                    int zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                    zzc = com_google_android_gms_internal_zzsm.getPosition();
                    zzIX = 0;
                    while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                        com_google_android_gms_internal_zzsm.zzJb();
                        zzIX++;
                    }
                    com_google_android_gms_internal_zzsm.zzms(zzc);
                    zzc = this.zzbtU == null ? 0 : this.zzbtU.length;
                    Object obj2 = new int[(zzIX + zzc)];
                    if (zzc != 0) {
                        System.arraycopy(this.zzbtU, 0, obj2, 0, zzc);
                    }
                    while (zzc < obj2.length) {
                        obj2[zzc] = com_google_android_gms_internal_zzsm.zzJb();
                        zzc++;
                    }
                    this.zzbtU = obj2;
                    com_google_android_gms_internal_zzsm.zzmr(zzmq);
                    continue;
                case 26:
                    zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 26);
                    zzIX = this.zzbtV == null ? 0 : this.zzbtV.length;
                    obj = new byte[(zzc + zzIX)][];
                    if (zzIX != 0) {
                        System.arraycopy(this.zzbtV, 0, obj, 0, zzIX);
                    }
                    while (zzIX < obj.length - 1) {
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readBytes();
                        com_google_android_gms_internal_zzsm.zzIX();
                        zzIX++;
                    }
                    obj[zzIX] = com_google_android_gms_internal_zzsm.readBytes();
                    this.zzbtV = obj;
                    continue;
                default:
                    if (!zza(com_google_android_gms_internal_zzsm, zzIX)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }

    protected final int zzz() {
        int i;
        int i2;
        int i3;
        int i4 = 0;
        int zzz = super.zzz();
        if (this.zzbtT == null || this.zzbtT.length <= 0) {
            i = zzz;
        } else {
            i2 = 0;
            i3 = 0;
            for (String str : this.zzbtT) {
                if (str != null) {
                    i2++;
                    i3 += zzsn.zzgO(str);
                }
            }
            i = (i2 * 1) + (i3 + zzz);
        }
        if (this.zzbtU != null && this.zzbtU.length > 0) {
            i2 = 0;
            for (int zzz2 : this.zzbtU) {
                i2 += zzsn.zzmx(zzz2);
            }
            i = (i2 + i) + (this.zzbtU.length * 1);
        }
        if (this.zzbtV == null || this.zzbtV.length <= 0) {
            return i;
        }
        i2 = 0;
        for (byte[] bArr : this.zzbtV) {
            if (bArr != null) {
                i4++;
                i2 += zzsn.zzG(bArr);
            }
        }
        return (i2 + i) + (i4 * 1);
    }
}
