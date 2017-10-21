package com.google.android.gms.internal;

import java.io.IOException;

public interface zzpz {

    public static final class zza extends zzsu {
        private static volatile zza[] zzaZq;
        public Integer zzaZr;
        public zze[] zzaZs;
        public zzb[] zzaZt;

        public zza() {
            zzDB();
        }

        public static zza[] zzDA() {
            if (zzaZq == null) {
                synchronized (zzss.zzbut) {
                    if (zzaZq == null) {
                        zzaZq = new zza[0];
                    }
                }
            }
            return zzaZq;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zza)) {
                    return false;
                }
                zza com_google_android_gms_internal_zzpz_zza = (zza) obj;
                if (this.zzaZr == null) {
                    if (com_google_android_gms_internal_zzpz_zza.zzaZr != null) {
                        return false;
                    }
                } else if (!this.zzaZr.equals(com_google_android_gms_internal_zzpz_zza.zzaZr)) {
                    return false;
                }
                if (!zzss.equals(this.zzaZs, com_google_android_gms_internal_zzpz_zza.zzaZs)) {
                    return false;
                }
                if (!zzss.equals(this.zzaZt, com_google_android_gms_internal_zzpz_zza.zzaZt)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            return (((((this.zzaZr == null ? 0 : this.zzaZr.hashCode()) + ((getClass().getName().hashCode() + 527) * 31)) * 31) + zzss.hashCode(this.zzaZs)) * 31) + zzss.hashCode(this.zzaZt);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzt(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            int i = 0;
            if (this.zzaZr != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZr.intValue());
            }
            if (this.zzaZs != null && this.zzaZs.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzaZs) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(2, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.zzaZt != null && this.zzaZt.length > 0) {
                while (i < this.zzaZt.length) {
                    zzsu com_google_android_gms_internal_zzsu2 = this.zzaZt[i];
                    if (com_google_android_gms_internal_zzsu2 != null) {
                        com_google_android_gms_internal_zzsn.zza(3, com_google_android_gms_internal_zzsu2);
                    }
                    i++;
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zza zzDB() {
            this.zzaZr = null;
            this.zzaZs = zze.zzDH();
            this.zzaZt = zzb.zzDC();
            this.zzbuu = -1;
            return this;
        }

        public final zza zzt(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                int zzc;
                Object obj;
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzaZr = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 18:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 18);
                        zzIX = this.zzaZs == null ? 0 : this.zzaZs.length;
                        obj = new zze[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzaZs, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zze();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zze();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzaZs = obj;
                        continue;
                    case 26:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 26);
                        zzIX = this.zzaZt == null ? 0 : this.zzaZt.length;
                        obj = new zzb[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzaZt, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zzb();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zzb();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzaZt = obj;
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int i = 0;
            int zzz = super.zzz();
            if (this.zzaZr != null) {
                zzz += zzsn.zzC(1, this.zzaZr.intValue());
            }
            if (this.zzaZs != null && this.zzaZs.length > 0) {
                int i2 = zzz;
                for (zzsu com_google_android_gms_internal_zzsu : this.zzaZs) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        i2 += zzsn.zzc(2, com_google_android_gms_internal_zzsu);
                    }
                }
                zzz = i2;
            }
            if (this.zzaZt != null && this.zzaZt.length > 0) {
                while (i < this.zzaZt.length) {
                    zzsu com_google_android_gms_internal_zzsu2 = this.zzaZt[i];
                    if (com_google_android_gms_internal_zzsu2 != null) {
                        zzz += zzsn.zzc(3, com_google_android_gms_internal_zzsu2);
                    }
                    i++;
                }
            }
            return zzz;
        }
    }

    public static final class zzb extends zzsu {
        private static volatile zzb[] zzaZu;
        public Integer zzaZv;
        public String zzaZw;
        public zzc[] zzaZx;
        public Boolean zzaZy;
        public zzd zzaZz;

        public zzb() {
            zzDD();
        }

        public static zzb[] zzDC() {
            if (zzaZu == null) {
                synchronized (zzss.zzbut) {
                    if (zzaZu == null) {
                        zzaZu = new zzb[0];
                    }
                }
            }
            return zzaZu;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzb)) {
                    return false;
                }
                zzb com_google_android_gms_internal_zzpz_zzb = (zzb) obj;
                if (this.zzaZv == null) {
                    if (com_google_android_gms_internal_zzpz_zzb.zzaZv != null) {
                        return false;
                    }
                } else if (!this.zzaZv.equals(com_google_android_gms_internal_zzpz_zzb.zzaZv)) {
                    return false;
                }
                if (this.zzaZw == null) {
                    if (com_google_android_gms_internal_zzpz_zzb.zzaZw != null) {
                        return false;
                    }
                } else if (!this.zzaZw.equals(com_google_android_gms_internal_zzpz_zzb.zzaZw)) {
                    return false;
                }
                if (!zzss.equals(this.zzaZx, com_google_android_gms_internal_zzpz_zzb.zzaZx)) {
                    return false;
                }
                if (this.zzaZy == null) {
                    if (com_google_android_gms_internal_zzpz_zzb.zzaZy != null) {
                        return false;
                    }
                } else if (!this.zzaZy.equals(com_google_android_gms_internal_zzpz_zzb.zzaZy)) {
                    return false;
                }
                if (this.zzaZz == null) {
                    if (com_google_android_gms_internal_zzpz_zzb.zzaZz != null) {
                        return false;
                    }
                } else if (!this.zzaZz.equals(com_google_android_gms_internal_zzpz_zzb.zzaZz)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzaZv == null ? 0 : this.zzaZv.hashCode();
            int hashCode3 = this.zzaZw == null ? 0 : this.zzaZw.hashCode();
            int hashCode4 = zzss.hashCode(this.zzaZx);
            int hashCode5 = this.zzaZy == null ? 0 : this.zzaZy.hashCode();
            if (this.zzaZz != null) {
                i = this.zzaZz.hashCode();
            }
            return ((((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzu(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZv != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZv.intValue());
            }
            if (this.zzaZw != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.zzaZw);
            }
            if (this.zzaZx != null && this.zzaZx.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzaZx) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(3, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.zzaZy != null) {
                com_google_android_gms_internal_zzsn.zze(4, this.zzaZy.booleanValue());
            }
            if (this.zzaZz != null) {
                com_google_android_gms_internal_zzsn.zza(5, this.zzaZz);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzb zzDD() {
            this.zzaZv = null;
            this.zzaZw = null;
            this.zzaZx = zzc.zzDE();
            this.zzaZy = null;
            this.zzaZz = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzb zzu(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzaZv = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 18:
                        this.zzaZw = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 26:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 26);
                        zzIX = this.zzaZx == null ? 0 : this.zzaZx.length;
                        Object obj = new zzc[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzaZx, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zzc();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zzc();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzaZx = obj;
                        continue;
                    case 32:
                        this.zzaZy = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 42:
                        if (this.zzaZz == null) {
                            this.zzaZz = new zzd();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzaZz);
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzaZv != null) {
                zzz += zzsn.zzC(1, this.zzaZv.intValue());
            }
            if (this.zzaZw != null) {
                zzz += zzsn.zzo(2, this.zzaZw);
            }
            if (this.zzaZx != null && this.zzaZx.length > 0) {
                int i = zzz;
                for (zzsu com_google_android_gms_internal_zzsu : this.zzaZx) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        i += zzsn.zzc(3, com_google_android_gms_internal_zzsu);
                    }
                }
                zzz = i;
            }
            if (this.zzaZy != null) {
                zzz += zzsn.zzf(4, this.zzaZy.booleanValue());
            }
            return this.zzaZz != null ? zzz + zzsn.zzc(5, this.zzaZz) : zzz;
        }
    }

    public static final class zzc extends zzsu {
        private static volatile zzc[] zzaZA;
        public zzf zzaZB;
        public zzd zzaZC;
        public Boolean zzaZD;
        public String zzaZE;

        public zzc() {
            zzDF();
        }

        public static zzc[] zzDE() {
            if (zzaZA == null) {
                synchronized (zzss.zzbut) {
                    if (zzaZA == null) {
                        zzaZA = new zzc[0];
                    }
                }
            }
            return zzaZA;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzc)) {
                    return false;
                }
                zzc com_google_android_gms_internal_zzpz_zzc = (zzc) obj;
                if (this.zzaZB == null) {
                    if (com_google_android_gms_internal_zzpz_zzc.zzaZB != null) {
                        return false;
                    }
                } else if (!this.zzaZB.equals(com_google_android_gms_internal_zzpz_zzc.zzaZB)) {
                    return false;
                }
                if (this.zzaZC == null) {
                    if (com_google_android_gms_internal_zzpz_zzc.zzaZC != null) {
                        return false;
                    }
                } else if (!this.zzaZC.equals(com_google_android_gms_internal_zzpz_zzc.zzaZC)) {
                    return false;
                }
                if (this.zzaZD == null) {
                    if (com_google_android_gms_internal_zzpz_zzc.zzaZD != null) {
                        return false;
                    }
                } else if (!this.zzaZD.equals(com_google_android_gms_internal_zzpz_zzc.zzaZD)) {
                    return false;
                }
                if (this.zzaZE == null) {
                    if (com_google_android_gms_internal_zzpz_zzc.zzaZE != null) {
                        return false;
                    }
                } else if (!this.zzaZE.equals(com_google_android_gms_internal_zzpz_zzc.zzaZE)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzaZB == null ? 0 : this.zzaZB.hashCode();
            int hashCode3 = this.zzaZC == null ? 0 : this.zzaZC.hashCode();
            int hashCode4 = this.zzaZD == null ? 0 : this.zzaZD.hashCode();
            if (this.zzaZE != null) {
                i = this.zzaZE.hashCode();
            }
            return ((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzv(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZB != null) {
                com_google_android_gms_internal_zzsn.zza(1, this.zzaZB);
            }
            if (this.zzaZC != null) {
                com_google_android_gms_internal_zzsn.zza(2, this.zzaZC);
            }
            if (this.zzaZD != null) {
                com_google_android_gms_internal_zzsn.zze(3, this.zzaZD.booleanValue());
            }
            if (this.zzaZE != null) {
                com_google_android_gms_internal_zzsn.zzn(4, this.zzaZE);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzc zzDF() {
            this.zzaZB = null;
            this.zzaZC = null;
            this.zzaZD = null;
            this.zzaZE = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzc zzv(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        if (this.zzaZB == null) {
                            this.zzaZB = new zzf();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzaZB);
                        continue;
                    case 18:
                        if (this.zzaZC == null) {
                            this.zzaZC = new zzd();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzaZC);
                        continue;
                    case 24:
                        this.zzaZD = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 34:
                        this.zzaZE = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzaZB != null) {
                zzz += zzsn.zzc(1, this.zzaZB);
            }
            if (this.zzaZC != null) {
                zzz += zzsn.zzc(2, this.zzaZC);
            }
            if (this.zzaZD != null) {
                zzz += zzsn.zzf(3, this.zzaZD.booleanValue());
            }
            return this.zzaZE != null ? zzz + zzsn.zzo(4, this.zzaZE) : zzz;
        }
    }

    public static final class zzd extends zzsu {
        public Integer zzaZF;
        public Boolean zzaZG;
        public String zzaZH;
        public String zzaZI;
        public String zzaZJ;

        public zzd() {
            zzDG();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzd)) {
                    return false;
                }
                zzd com_google_android_gms_internal_zzpz_zzd = (zzd) obj;
                if (this.zzaZF == null) {
                    if (com_google_android_gms_internal_zzpz_zzd.zzaZF != null) {
                        return false;
                    }
                } else if (!this.zzaZF.equals(com_google_android_gms_internal_zzpz_zzd.zzaZF)) {
                    return false;
                }
                if (this.zzaZG == null) {
                    if (com_google_android_gms_internal_zzpz_zzd.zzaZG != null) {
                        return false;
                    }
                } else if (!this.zzaZG.equals(com_google_android_gms_internal_zzpz_zzd.zzaZG)) {
                    return false;
                }
                if (this.zzaZH == null) {
                    if (com_google_android_gms_internal_zzpz_zzd.zzaZH != null) {
                        return false;
                    }
                } else if (!this.zzaZH.equals(com_google_android_gms_internal_zzpz_zzd.zzaZH)) {
                    return false;
                }
                if (this.zzaZI == null) {
                    if (com_google_android_gms_internal_zzpz_zzd.zzaZI != null) {
                        return false;
                    }
                } else if (!this.zzaZI.equals(com_google_android_gms_internal_zzpz_zzd.zzaZI)) {
                    return false;
                }
                if (this.zzaZJ == null) {
                    if (com_google_android_gms_internal_zzpz_zzd.zzaZJ != null) {
                        return false;
                    }
                } else if (!this.zzaZJ.equals(com_google_android_gms_internal_zzpz_zzd.zzaZJ)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int intValue = this.zzaZF == null ? 0 : this.zzaZF.intValue();
            int hashCode2 = this.zzaZG == null ? 0 : this.zzaZG.hashCode();
            int hashCode3 = this.zzaZH == null ? 0 : this.zzaZH.hashCode();
            int hashCode4 = this.zzaZI == null ? 0 : this.zzaZI.hashCode();
            if (this.zzaZJ != null) {
                i = this.zzaZJ.hashCode();
            }
            return ((((((((intValue + ((hashCode + 527) * 31)) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzw(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZF != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZF.intValue());
            }
            if (this.zzaZG != null) {
                com_google_android_gms_internal_zzsn.zze(2, this.zzaZG.booleanValue());
            }
            if (this.zzaZH != null) {
                com_google_android_gms_internal_zzsn.zzn(3, this.zzaZH);
            }
            if (this.zzaZI != null) {
                com_google_android_gms_internal_zzsn.zzn(4, this.zzaZI);
            }
            if (this.zzaZJ != null) {
                com_google_android_gms_internal_zzsn.zzn(5, this.zzaZJ);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzd zzDG() {
            this.zzaZF = null;
            this.zzaZG = null;
            this.zzaZH = null;
            this.zzaZI = null;
            this.zzaZJ = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzd zzw(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                                this.zzaZF = Integer.valueOf(zzIX);
                                break;
                            default:
                                continue;
                        }
                    case 16:
                        this.zzaZG = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 26:
                        this.zzaZH = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 34:
                        this.zzaZI = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 42:
                        this.zzaZJ = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzaZF != null) {
                zzz += zzsn.zzC(1, this.zzaZF.intValue());
            }
            if (this.zzaZG != null) {
                zzz += zzsn.zzf(2, this.zzaZG.booleanValue());
            }
            if (this.zzaZH != null) {
                zzz += zzsn.zzo(3, this.zzaZH);
            }
            if (this.zzaZI != null) {
                zzz += zzsn.zzo(4, this.zzaZI);
            }
            return this.zzaZJ != null ? zzz + zzsn.zzo(5, this.zzaZJ) : zzz;
        }
    }

    public static final class zze extends zzsu {
        private static volatile zze[] zzaZK;
        public String zzaZL;
        public zzc zzaZM;
        public Integer zzaZv;

        public zze() {
            zzDI();
        }

        public static zze[] zzDH() {
            if (zzaZK == null) {
                synchronized (zzss.zzbut) {
                    if (zzaZK == null) {
                        zzaZK = new zze[0];
                    }
                }
            }
            return zzaZK;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zze)) {
                    return false;
                }
                zze com_google_android_gms_internal_zzpz_zze = (zze) obj;
                if (this.zzaZv == null) {
                    if (com_google_android_gms_internal_zzpz_zze.zzaZv != null) {
                        return false;
                    }
                } else if (!this.zzaZv.equals(com_google_android_gms_internal_zzpz_zze.zzaZv)) {
                    return false;
                }
                if (this.zzaZL == null) {
                    if (com_google_android_gms_internal_zzpz_zze.zzaZL != null) {
                        return false;
                    }
                } else if (!this.zzaZL.equals(com_google_android_gms_internal_zzpz_zze.zzaZL)) {
                    return false;
                }
                if (this.zzaZM == null) {
                    if (com_google_android_gms_internal_zzpz_zze.zzaZM != null) {
                        return false;
                    }
                } else if (!this.zzaZM.equals(com_google_android_gms_internal_zzpz_zze.zzaZM)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzaZv == null ? 0 : this.zzaZv.hashCode();
            int hashCode3 = this.zzaZL == null ? 0 : this.zzaZL.hashCode();
            if (this.zzaZM != null) {
                i = this.zzaZM.hashCode();
            }
            return ((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzx(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZv != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZv.intValue());
            }
            if (this.zzaZL != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.zzaZL);
            }
            if (this.zzaZM != null) {
                com_google_android_gms_internal_zzsn.zza(3, this.zzaZM);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zze zzDI() {
            this.zzaZv = null;
            this.zzaZL = null;
            this.zzaZM = null;
            this.zzbuu = -1;
            return this;
        }

        public final zze zzx(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzaZv = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 18:
                        this.zzaZL = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 26:
                        if (this.zzaZM == null) {
                            this.zzaZM = new zzc();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzaZM);
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzaZv != null) {
                zzz += zzsn.zzC(1, this.zzaZv.intValue());
            }
            if (this.zzaZL != null) {
                zzz += zzsn.zzo(2, this.zzaZL);
            }
            return this.zzaZM != null ? zzz + zzsn.zzc(3, this.zzaZM) : zzz;
        }
    }

    public static final class zzf extends zzsu {
        public Integer zzaZN;
        public String zzaZO;
        public Boolean zzaZP;
        public String[] zzaZQ;

        public zzf() {
            zzDJ();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzf)) {
                    return false;
                }
                zzf com_google_android_gms_internal_zzpz_zzf = (zzf) obj;
                if (this.zzaZN == null) {
                    if (com_google_android_gms_internal_zzpz_zzf.zzaZN != null) {
                        return false;
                    }
                } else if (!this.zzaZN.equals(com_google_android_gms_internal_zzpz_zzf.zzaZN)) {
                    return false;
                }
                if (this.zzaZO == null) {
                    if (com_google_android_gms_internal_zzpz_zzf.zzaZO != null) {
                        return false;
                    }
                } else if (!this.zzaZO.equals(com_google_android_gms_internal_zzpz_zzf.zzaZO)) {
                    return false;
                }
                if (this.zzaZP == null) {
                    if (com_google_android_gms_internal_zzpz_zzf.zzaZP != null) {
                        return false;
                    }
                } else if (!this.zzaZP.equals(com_google_android_gms_internal_zzpz_zzf.zzaZP)) {
                    return false;
                }
                if (!zzss.equals(this.zzaZQ, com_google_android_gms_internal_zzpz_zzf.zzaZQ)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int intValue = this.zzaZN == null ? 0 : this.zzaZN.intValue();
            int hashCode2 = this.zzaZO == null ? 0 : this.zzaZO.hashCode();
            if (this.zzaZP != null) {
                i = this.zzaZP.hashCode();
            }
            return ((((((intValue + ((hashCode + 527) * 31)) * 31) + hashCode2) * 31) + i) * 31) + zzss.hashCode(this.zzaZQ);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzy(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZN != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZN.intValue());
            }
            if (this.zzaZO != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.zzaZO);
            }
            if (this.zzaZP != null) {
                com_google_android_gms_internal_zzsn.zze(3, this.zzaZP.booleanValue());
            }
            if (this.zzaZQ != null && this.zzaZQ.length > 0) {
                for (String str : this.zzaZQ) {
                    if (str != null) {
                        com_google_android_gms_internal_zzsn.zzn(4, str);
                    }
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzf zzDJ() {
            this.zzaZN = null;
            this.zzaZO = null;
            this.zzaZP = null;
            this.zzaZQ = zzsx.zzbuB;
            this.zzbuu = -1;
            return this;
        }

        public final zzf zzy(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.zzaZN = Integer.valueOf(zzIX);
                                break;
                            default:
                                continue;
                        }
                    case 18:
                        this.zzaZO = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 24:
                        this.zzaZP = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 34:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 34);
                        zzIX = this.zzaZQ == null ? 0 : this.zzaZQ.length;
                        Object obj = new String[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzaZQ, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                        this.zzaZQ = obj;
                        continue;
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        protected final int zzz() {
            int i = 0;
            int zzz = super.zzz();
            if (this.zzaZN != null) {
                zzz += zzsn.zzC(1, this.zzaZN.intValue());
            }
            if (this.zzaZO != null) {
                zzz += zzsn.zzo(2, this.zzaZO);
            }
            if (this.zzaZP != null) {
                zzz += zzsn.zzf(3, this.zzaZP.booleanValue());
            }
            if (this.zzaZQ == null || this.zzaZQ.length <= 0) {
                return zzz;
            }
            int i2 = 0;
            for (String str : this.zzaZQ) {
                if (str != null) {
                    i++;
                    i2 += zzsn.zzgO(str);
                }
            }
            return (zzz + i2) + (i * 1);
        }
    }
}
