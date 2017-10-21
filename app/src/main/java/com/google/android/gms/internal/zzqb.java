package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import java.io.IOException;

public interface zzqb {

    public static final class zza extends zzsu {
        private static volatile zza[] zzaZZ;
        public Integer zzaZr;
        public zzf zzbaa;
        public zzf zzbab;
        public Boolean zzbac;

        public zza() {
            zzDQ();
        }

        public static zza[] zzDP() {
            if (zzaZZ == null) {
                synchronized (zzss.zzbut) {
                    if (zzaZZ == null) {
                        zzaZZ = new zza[0];
                    }
                }
            }
            return zzaZZ;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zza)) {
                    return false;
                }
                zza com_google_android_gms_internal_zzqb_zza = (zza) obj;
                if (this.zzaZr == null) {
                    if (com_google_android_gms_internal_zzqb_zza.zzaZr != null) {
                        return false;
                    }
                } else if (!this.zzaZr.equals(com_google_android_gms_internal_zzqb_zza.zzaZr)) {
                    return false;
                }
                if (this.zzbaa == null) {
                    if (com_google_android_gms_internal_zzqb_zza.zzbaa != null) {
                        return false;
                    }
                } else if (!this.zzbaa.equals(com_google_android_gms_internal_zzqb_zza.zzbaa)) {
                    return false;
                }
                if (this.zzbab == null) {
                    if (com_google_android_gms_internal_zzqb_zza.zzbab != null) {
                        return false;
                    }
                } else if (!this.zzbab.equals(com_google_android_gms_internal_zzqb_zza.zzbab)) {
                    return false;
                }
                if (this.zzbac == null) {
                    if (com_google_android_gms_internal_zzqb_zza.zzbac != null) {
                        return false;
                    }
                } else if (!this.zzbac.equals(com_google_android_gms_internal_zzqb_zza.zzbac)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzaZr == null ? 0 : this.zzaZr.hashCode();
            int hashCode3 = this.zzbaa == null ? 0 : this.zzbaa.hashCode();
            int hashCode4 = this.zzbab == null ? 0 : this.zzbab.hashCode();
            if (this.zzbac != null) {
                i = this.zzbac.hashCode();
            }
            return ((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzC(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzaZr != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzaZr.intValue());
            }
            if (this.zzbaa != null) {
                com_google_android_gms_internal_zzsn.zza(2, this.zzbaa);
            }
            if (this.zzbab != null) {
                com_google_android_gms_internal_zzsn.zza(3, this.zzbab);
            }
            if (this.zzbac != null) {
                com_google_android_gms_internal_zzsn.zze(4, this.zzbac.booleanValue());
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zza zzC(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzaZr = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 18:
                        if (this.zzbaa == null) {
                            this.zzbaa = new zzf();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzbaa);
                        continue;
                    case 26:
                        if (this.zzbab == null) {
                            this.zzbab = new zzf();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzbab);
                        continue;
                    case 32:
                        this.zzbac = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
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

        public final zza zzDQ() {
            this.zzaZr = null;
            this.zzbaa = null;
            this.zzbab = null;
            this.zzbac = null;
            this.zzbuu = -1;
            return this;
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzaZr != null) {
                zzz += zzsn.zzC(1, this.zzaZr.intValue());
            }
            if (this.zzbaa != null) {
                zzz += zzsn.zzc(2, this.zzbaa);
            }
            if (this.zzbab != null) {
                zzz += zzsn.zzc(3, this.zzbab);
            }
            return this.zzbac != null ? zzz + zzsn.zzf(4, this.zzbac.booleanValue()) : zzz;
        }
    }

    public static final class zzb extends zzsu {
        private static volatile zzb[] zzbad;
        public Integer count;
        public String name;
        public zzc[] zzbae;
        public Long zzbaf;
        public Long zzbag;

        public zzb() {
            zzDS();
        }

        public static zzb[] zzDR() {
            if (zzbad == null) {
                synchronized (zzss.zzbut) {
                    if (zzbad == null) {
                        zzbad = new zzb[0];
                    }
                }
            }
            return zzbad;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzb)) {
                    return false;
                }
                zzb com_google_android_gms_internal_zzqb_zzb = (zzb) obj;
                if (!zzss.equals(this.zzbae, com_google_android_gms_internal_zzqb_zzb.zzbae)) {
                    return false;
                }
                if (this.name == null) {
                    if (com_google_android_gms_internal_zzqb_zzb.name != null) {
                        return false;
                    }
                } else if (!this.name.equals(com_google_android_gms_internal_zzqb_zzb.name)) {
                    return false;
                }
                if (this.zzbaf == null) {
                    if (com_google_android_gms_internal_zzqb_zzb.zzbaf != null) {
                        return false;
                    }
                } else if (!this.zzbaf.equals(com_google_android_gms_internal_zzqb_zzb.zzbaf)) {
                    return false;
                }
                if (this.zzbag == null) {
                    if (com_google_android_gms_internal_zzqb_zzb.zzbag != null) {
                        return false;
                    }
                } else if (!this.zzbag.equals(com_google_android_gms_internal_zzqb_zzb.zzbag)) {
                    return false;
                }
                if (this.count == null) {
                    if (com_google_android_gms_internal_zzqb_zzb.count != null) {
                        return false;
                    }
                } else if (!this.count.equals(com_google_android_gms_internal_zzqb_zzb.count)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = zzss.hashCode(this.zzbae);
            int hashCode3 = this.name == null ? 0 : this.name.hashCode();
            int hashCode4 = this.zzbaf == null ? 0 : this.zzbaf.hashCode();
            int hashCode5 = this.zzbag == null ? 0 : this.zzbag.hashCode();
            if (this.count != null) {
                i = this.count.hashCode();
            }
            return ((((((hashCode3 + ((((hashCode + 527) * 31) + hashCode2) * 31)) * 31) + hashCode4) * 31) + hashCode5) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzD(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzbae != null && this.zzbae.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbae) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(1, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.name != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.name);
            }
            if (this.zzbaf != null) {
                com_google_android_gms_internal_zzsn.zzb(3, this.zzbaf.longValue());
            }
            if (this.zzbag != null) {
                com_google_android_gms_internal_zzsn.zzb(4, this.zzbag.longValue());
            }
            if (this.count != null) {
                com_google_android_gms_internal_zzsn.zzA(5, this.count.intValue());
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzb zzD(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 10);
                        zzIX = this.zzbae == null ? 0 : this.zzbae.length;
                        Object obj = new zzc[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbae, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zzc();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zzc();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzbae = obj;
                        continue;
                    case 18:
                        this.name = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 24:
                        this.zzbaf = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 32:
                        this.zzbag = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 40:
                        this.count = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
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

        public final zzb zzDS() {
            this.zzbae = zzc.zzDT();
            this.name = null;
            this.zzbaf = null;
            this.zzbag = null;
            this.count = null;
            this.zzbuu = -1;
            return this;
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.zzbae != null && this.zzbae.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbae) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        zzz += zzsn.zzc(1, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.name != null) {
                zzz += zzsn.zzo(2, this.name);
            }
            if (this.zzbaf != null) {
                zzz += zzsn.zzd(3, this.zzbaf.longValue());
            }
            if (this.zzbag != null) {
                zzz += zzsn.zzd(4, this.zzbag.longValue());
            }
            return this.count != null ? zzz + zzsn.zzC(5, this.count.intValue()) : zzz;
        }
    }

    public static final class zzc extends zzsu {
        private static volatile zzc[] zzbah;
        public String name;
        public Float zzaZo;
        public String zzamJ;
        public Long zzbai;

        public zzc() {
            zzDU();
        }

        public static zzc[] zzDT() {
            if (zzbah == null) {
                synchronized (zzss.zzbut) {
                    if (zzbah == null) {
                        zzbah = new zzc[0];
                    }
                }
            }
            return zzbah;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzc)) {
                    return false;
                }
                zzc com_google_android_gms_internal_zzqb_zzc = (zzc) obj;
                if (this.name == null) {
                    if (com_google_android_gms_internal_zzqb_zzc.name != null) {
                        return false;
                    }
                } else if (!this.name.equals(com_google_android_gms_internal_zzqb_zzc.name)) {
                    return false;
                }
                if (this.zzamJ == null) {
                    if (com_google_android_gms_internal_zzqb_zzc.zzamJ != null) {
                        return false;
                    }
                } else if (!this.zzamJ.equals(com_google_android_gms_internal_zzqb_zzc.zzamJ)) {
                    return false;
                }
                if (this.zzbai == null) {
                    if (com_google_android_gms_internal_zzqb_zzc.zzbai != null) {
                        return false;
                    }
                } else if (!this.zzbai.equals(com_google_android_gms_internal_zzqb_zzc.zzbai)) {
                    return false;
                }
                if (this.zzaZo == null) {
                    if (com_google_android_gms_internal_zzqb_zzc.zzaZo != null) {
                        return false;
                    }
                } else if (!this.zzaZo.equals(com_google_android_gms_internal_zzqb_zzc.zzaZo)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.name == null ? 0 : this.name.hashCode();
            int hashCode3 = this.zzamJ == null ? 0 : this.zzamJ.hashCode();
            int hashCode4 = this.zzbai == null ? 0 : this.zzbai.hashCode();
            if (this.zzaZo != null) {
                i = this.zzaZo.hashCode();
            }
            return ((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzE(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.name != null) {
                com_google_android_gms_internal_zzsn.zzn(1, this.name);
            }
            if (this.zzamJ != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.zzamJ);
            }
            if (this.zzbai != null) {
                com_google_android_gms_internal_zzsn.zzb(3, this.zzbai.longValue());
            }
            if (this.zzaZo != null) {
                com_google_android_gms_internal_zzsn.zzb(4, this.zzaZo.floatValue());
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzc zzDU() {
            this.name = null;
            this.zzamJ = null;
            this.zzbai = null;
            this.zzaZo = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzc zzE(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.name = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 18:
                        this.zzamJ = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 24:
                        this.zzbai = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 37:
                        this.zzaZo = Float.valueOf(com_google_android_gms_internal_zzsm.readFloat());
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
            if (this.name != null) {
                zzz += zzsn.zzo(1, this.name);
            }
            if (this.zzamJ != null) {
                zzz += zzsn.zzo(2, this.zzamJ);
            }
            if (this.zzbai != null) {
                zzz += zzsn.zzd(3, this.zzbai.longValue());
            }
            return this.zzaZo != null ? zzz + zzsn.zzc(4, this.zzaZo.floatValue()) : zzz;
        }
    }

    public static final class zzd extends zzsu {
        public zze[] zzbaj;

        public zzd() {
            zzDV();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzd)) {
                    return false;
                }
                if (!zzss.equals(this.zzbaj, ((zzd) obj).zzbaj)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            return ((getClass().getName().hashCode() + 527) * 31) + zzss.hashCode(this.zzbaj);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzF(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzbaj != null && this.zzbaj.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbaj) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(1, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzd zzDV() {
            this.zzbaj = zze.zzDW();
            this.zzbuu = -1;
            return this;
        }

        public final zzd zzF(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 10);
                        zzIX = this.zzbaj == null ? 0 : this.zzbaj.length;
                        Object obj = new zze[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbaj, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zze();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zze();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzbaj = obj;
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
            if (this.zzbaj != null && this.zzbaj.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbaj) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        zzz += zzsn.zzc(1, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            return zzz;
        }
    }

    public static final class zze extends zzsu {
        private static volatile zze[] zzbak;
        public String appId;
        public String osVersion;
        public String zzaMV;
        public String zzaVt;
        public String zzaVu;
        public String zzaVx;
        public Boolean zzbaA;
        public String zzbaB;
        public Long zzbaC;
        public Integer zzbaD;
        public Boolean zzbaE;
        public zza[] zzbaF;
        public Integer zzbal;
        public zzb[] zzbam;
        public zzg[] zzban;
        public Long zzbao;
        public Long zzbap;
        public Long zzbaq;
        public Long zzbar;
        public Long zzbas;
        public String zzbat;
        public String zzbau;
        public String zzbav;
        public Integer zzbaw;
        public Long zzbax;
        public Long zzbay;
        public String zzbaz;

        public zze() {
            zzDX();
        }

        public static zze[] zzDW() {
            if (zzbak == null) {
                synchronized (zzss.zzbut) {
                    if (zzbak == null) {
                        zzbak = new zze[0];
                    }
                }
            }
            return zzbak;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zze)) {
                    return false;
                }
                zze com_google_android_gms_internal_zzqb_zze = (zze) obj;
                if (this.zzbal == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbal != null) {
                        return false;
                    }
                } else if (!this.zzbal.equals(com_google_android_gms_internal_zzqb_zze.zzbal)) {
                    return false;
                }
                if (!zzss.equals(this.zzbam, com_google_android_gms_internal_zzqb_zze.zzbam) || !zzss.equals(this.zzban, com_google_android_gms_internal_zzqb_zze.zzban)) {
                    return false;
                }
                if (this.zzbao == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbao != null) {
                        return false;
                    }
                } else if (!this.zzbao.equals(com_google_android_gms_internal_zzqb_zze.zzbao)) {
                    return false;
                }
                if (this.zzbap == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbap != null) {
                        return false;
                    }
                } else if (!this.zzbap.equals(com_google_android_gms_internal_zzqb_zze.zzbap)) {
                    return false;
                }
                if (this.zzbaq == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaq != null) {
                        return false;
                    }
                } else if (!this.zzbaq.equals(com_google_android_gms_internal_zzqb_zze.zzbaq)) {
                    return false;
                }
                if (this.zzbar == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbar != null) {
                        return false;
                    }
                } else if (!this.zzbar.equals(com_google_android_gms_internal_zzqb_zze.zzbar)) {
                    return false;
                }
                if (this.zzbas == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbas != null) {
                        return false;
                    }
                } else if (!this.zzbas.equals(com_google_android_gms_internal_zzqb_zze.zzbas)) {
                    return false;
                }
                if (this.zzbat == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbat != null) {
                        return false;
                    }
                } else if (!this.zzbat.equals(com_google_android_gms_internal_zzqb_zze.zzbat)) {
                    return false;
                }
                if (this.osVersion == null) {
                    if (com_google_android_gms_internal_zzqb_zze.osVersion != null) {
                        return false;
                    }
                } else if (!this.osVersion.equals(com_google_android_gms_internal_zzqb_zze.osVersion)) {
                    return false;
                }
                if (this.zzbau == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbau != null) {
                        return false;
                    }
                } else if (!this.zzbau.equals(com_google_android_gms_internal_zzqb_zze.zzbau)) {
                    return false;
                }
                if (this.zzbav == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbav != null) {
                        return false;
                    }
                } else if (!this.zzbav.equals(com_google_android_gms_internal_zzqb_zze.zzbav)) {
                    return false;
                }
                if (this.zzbaw == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaw != null) {
                        return false;
                    }
                } else if (!this.zzbaw.equals(com_google_android_gms_internal_zzqb_zze.zzbaw)) {
                    return false;
                }
                if (this.zzaVu == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzaVu != null) {
                        return false;
                    }
                } else if (!this.zzaVu.equals(com_google_android_gms_internal_zzqb_zze.zzaVu)) {
                    return false;
                }
                if (this.appId == null) {
                    if (com_google_android_gms_internal_zzqb_zze.appId != null) {
                        return false;
                    }
                } else if (!this.appId.equals(com_google_android_gms_internal_zzqb_zze.appId)) {
                    return false;
                }
                if (this.zzaMV == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzaMV != null) {
                        return false;
                    }
                } else if (!this.zzaMV.equals(com_google_android_gms_internal_zzqb_zze.zzaMV)) {
                    return false;
                }
                if (this.zzbax == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbax != null) {
                        return false;
                    }
                } else if (!this.zzbax.equals(com_google_android_gms_internal_zzqb_zze.zzbax)) {
                    return false;
                }
                if (this.zzbay == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbay != null) {
                        return false;
                    }
                } else if (!this.zzbay.equals(com_google_android_gms_internal_zzqb_zze.zzbay)) {
                    return false;
                }
                if (this.zzbaz == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaz != null) {
                        return false;
                    }
                } else if (!this.zzbaz.equals(com_google_android_gms_internal_zzqb_zze.zzbaz)) {
                    return false;
                }
                if (this.zzbaA == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaA != null) {
                        return false;
                    }
                } else if (!this.zzbaA.equals(com_google_android_gms_internal_zzqb_zze.zzbaA)) {
                    return false;
                }
                if (this.zzbaB == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaB != null) {
                        return false;
                    }
                } else if (!this.zzbaB.equals(com_google_android_gms_internal_zzqb_zze.zzbaB)) {
                    return false;
                }
                if (this.zzbaC == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaC != null) {
                        return false;
                    }
                } else if (!this.zzbaC.equals(com_google_android_gms_internal_zzqb_zze.zzbaC)) {
                    return false;
                }
                if (this.zzbaD == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaD != null) {
                        return false;
                    }
                } else if (!this.zzbaD.equals(com_google_android_gms_internal_zzqb_zze.zzbaD)) {
                    return false;
                }
                if (this.zzaVx == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzaVx != null) {
                        return false;
                    }
                } else if (!this.zzaVx.equals(com_google_android_gms_internal_zzqb_zze.zzaVx)) {
                    return false;
                }
                if (this.zzaVt == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzaVt != null) {
                        return false;
                    }
                } else if (!this.zzaVt.equals(com_google_android_gms_internal_zzqb_zze.zzaVt)) {
                    return false;
                }
                if (this.zzbaE == null) {
                    if (com_google_android_gms_internal_zzqb_zze.zzbaE != null) {
                        return false;
                    }
                } else if (!this.zzbaE.equals(com_google_android_gms_internal_zzqb_zze.zzbaE)) {
                    return false;
                }
                if (!zzss.equals(this.zzbaF, com_google_android_gms_internal_zzqb_zze.zzbaF)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzbal == null ? 0 : this.zzbal.hashCode();
            int hashCode3 = zzss.hashCode(this.zzbam);
            int hashCode4 = zzss.hashCode(this.zzban);
            int hashCode5 = this.zzbao == null ? 0 : this.zzbao.hashCode();
            int hashCode6 = this.zzbap == null ? 0 : this.zzbap.hashCode();
            int hashCode7 = this.zzbaq == null ? 0 : this.zzbaq.hashCode();
            int hashCode8 = this.zzbar == null ? 0 : this.zzbar.hashCode();
            int hashCode9 = this.zzbas == null ? 0 : this.zzbas.hashCode();
            int hashCode10 = this.zzbat == null ? 0 : this.zzbat.hashCode();
            int hashCode11 = this.osVersion == null ? 0 : this.osVersion.hashCode();
            int hashCode12 = this.zzbau == null ? 0 : this.zzbau.hashCode();
            int hashCode13 = this.zzbav == null ? 0 : this.zzbav.hashCode();
            int hashCode14 = this.zzbaw == null ? 0 : this.zzbaw.hashCode();
            int hashCode15 = this.zzaVu == null ? 0 : this.zzaVu.hashCode();
            int hashCode16 = this.appId == null ? 0 : this.appId.hashCode();
            int hashCode17 = this.zzaMV == null ? 0 : this.zzaMV.hashCode();
            int hashCode18 = this.zzbax == null ? 0 : this.zzbax.hashCode();
            int hashCode19 = this.zzbay == null ? 0 : this.zzbay.hashCode();
            int hashCode20 = this.zzbaz == null ? 0 : this.zzbaz.hashCode();
            int hashCode21 = this.zzbaA == null ? 0 : this.zzbaA.hashCode();
            int hashCode22 = this.zzbaB == null ? 0 : this.zzbaB.hashCode();
            int hashCode23 = this.zzbaC == null ? 0 : this.zzbaC.hashCode();
            int hashCode24 = this.zzbaD == null ? 0 : this.zzbaD.hashCode();
            int hashCode25 = this.zzaVx == null ? 0 : this.zzaVx.hashCode();
            int hashCode26 = this.zzaVt == null ? 0 : this.zzaVt.hashCode();
            if (this.zzbaE != null) {
                i = this.zzbaE.hashCode();
            }
            return ((((((((((((((((((((((((((((((((((((((((((((((((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + hashCode8) * 31) + hashCode9) * 31) + hashCode10) * 31) + hashCode11) * 31) + hashCode12) * 31) + hashCode13) * 31) + hashCode14) * 31) + hashCode15) * 31) + hashCode16) * 31) + hashCode17) * 31) + hashCode18) * 31) + hashCode19) * 31) + hashCode20) * 31) + hashCode21) * 31) + hashCode22) * 31) + hashCode23) * 31) + hashCode24) * 31) + hashCode25) * 31) + hashCode26) * 31) + i) * 31) + zzss.hashCode(this.zzbaF);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzG(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            int i = 0;
            if (this.zzbal != null) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzbal.intValue());
            }
            if (this.zzbam != null && this.zzbam.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbam) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(2, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.zzban != null && this.zzban.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu2 : this.zzban) {
                    if (com_google_android_gms_internal_zzsu2 != null) {
                        com_google_android_gms_internal_zzsn.zza(3, com_google_android_gms_internal_zzsu2);
                    }
                }
            }
            if (this.zzbao != null) {
                com_google_android_gms_internal_zzsn.zzb(4, this.zzbao.longValue());
            }
            if (this.zzbap != null) {
                com_google_android_gms_internal_zzsn.zzb(5, this.zzbap.longValue());
            }
            if (this.zzbaq != null) {
                com_google_android_gms_internal_zzsn.zzb(6, this.zzbaq.longValue());
            }
            if (this.zzbas != null) {
                com_google_android_gms_internal_zzsn.zzb(7, this.zzbas.longValue());
            }
            if (this.zzbat != null) {
                com_google_android_gms_internal_zzsn.zzn(8, this.zzbat);
            }
            if (this.osVersion != null) {
                com_google_android_gms_internal_zzsn.zzn(9, this.osVersion);
            }
            if (this.zzbau != null) {
                com_google_android_gms_internal_zzsn.zzn(10, this.zzbau);
            }
            if (this.zzbav != null) {
                com_google_android_gms_internal_zzsn.zzn(11, this.zzbav);
            }
            if (this.zzbaw != null) {
                com_google_android_gms_internal_zzsn.zzA(12, this.zzbaw.intValue());
            }
            if (this.zzaVu != null) {
                com_google_android_gms_internal_zzsn.zzn(13, this.zzaVu);
            }
            if (this.appId != null) {
                com_google_android_gms_internal_zzsn.zzn(14, this.appId);
            }
            if (this.zzaMV != null) {
                com_google_android_gms_internal_zzsn.zzn(16, this.zzaMV);
            }
            if (this.zzbax != null) {
                com_google_android_gms_internal_zzsn.zzb(17, this.zzbax.longValue());
            }
            if (this.zzbay != null) {
                com_google_android_gms_internal_zzsn.zzb(18, this.zzbay.longValue());
            }
            if (this.zzbaz != null) {
                com_google_android_gms_internal_zzsn.zzn(19, this.zzbaz);
            }
            if (this.zzbaA != null) {
                com_google_android_gms_internal_zzsn.zze(20, this.zzbaA.booleanValue());
            }
            if (this.zzbaB != null) {
                com_google_android_gms_internal_zzsn.zzn(21, this.zzbaB);
            }
            if (this.zzbaC != null) {
                com_google_android_gms_internal_zzsn.zzb(22, this.zzbaC.longValue());
            }
            if (this.zzbaD != null) {
                com_google_android_gms_internal_zzsn.zzA(23, this.zzbaD.intValue());
            }
            if (this.zzaVx != null) {
                com_google_android_gms_internal_zzsn.zzn(24, this.zzaVx);
            }
            if (this.zzaVt != null) {
                com_google_android_gms_internal_zzsn.zzn(25, this.zzaVt);
            }
            if (this.zzbar != null) {
                com_google_android_gms_internal_zzsn.zzb(26, this.zzbar.longValue());
            }
            if (this.zzbaE != null) {
                com_google_android_gms_internal_zzsn.zze(28, this.zzbaE.booleanValue());
            }
            if (this.zzbaF != null && this.zzbaF.length > 0) {
                while (i < this.zzbaF.length) {
                    zzsu com_google_android_gms_internal_zzsu3 = this.zzbaF[i];
                    if (com_google_android_gms_internal_zzsu3 != null) {
                        com_google_android_gms_internal_zzsn.zza(29, com_google_android_gms_internal_zzsu3);
                    }
                    i++;
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zze zzDX() {
            this.zzbal = null;
            this.zzbam = zzb.zzDR();
            this.zzban = zzg.zzDZ();
            this.zzbao = null;
            this.zzbap = null;
            this.zzbaq = null;
            this.zzbar = null;
            this.zzbas = null;
            this.zzbat = null;
            this.osVersion = null;
            this.zzbau = null;
            this.zzbav = null;
            this.zzbaw = null;
            this.zzaVu = null;
            this.appId = null;
            this.zzaMV = null;
            this.zzbax = null;
            this.zzbay = null;
            this.zzbaz = null;
            this.zzbaA = null;
            this.zzbaB = null;
            this.zzbaC = null;
            this.zzbaD = null;
            this.zzaVx = null;
            this.zzaVt = null;
            this.zzbaE = null;
            this.zzbaF = zza.zzDP();
            this.zzbuu = -1;
            return this;
        }

        public final zze zzG(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                int zzc;
                Object obj;
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzbal = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 18:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 18);
                        zzIX = this.zzbam == null ? 0 : this.zzbam.length;
                        obj = new zzb[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbam, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zzb();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zzb();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzbam = obj;
                        continue;
                    case 26:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 26);
                        zzIX = this.zzban == null ? 0 : this.zzban.length;
                        obj = new zzg[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzban, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zzg();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zzg();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzban = obj;
                        continue;
                    case 32:
                        this.zzbao = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 40:
                        this.zzbap = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 48:
                        this.zzbaq = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 56:
                        this.zzbas = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 66:
                        this.zzbat = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 74:
                        this.osVersion = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 82:
                        this.zzbau = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 90:
                        this.zzbav = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 96:
                        this.zzbaw = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 106:
                        this.zzaVu = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 114:
                        this.appId = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        this.zzaMV = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 136:
                        this.zzbax = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 144:
                        this.zzbay = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 154:
                        this.zzbaz = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 160:
                        this.zzbaA = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 170:
                        this.zzbaB = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 176:
                        this.zzbaC = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 184:
                        this.zzbaD = Integer.valueOf(com_google_android_gms_internal_zzsm.zzJb());
                        continue;
                    case 194:
                        this.zzaVx = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 202:
                        this.zzaVt = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 208:
                        this.zzbar = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 224:
                        this.zzbaE = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 234:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 234);
                        zzIX = this.zzbaF == null ? 0 : this.zzbaF.length;
                        obj = new zza[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbaF, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zza();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zza();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzbaF = obj;
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
            int i;
            int i2 = 0;
            int zzz = super.zzz();
            if (this.zzbal != null) {
                zzz += zzsn.zzC(1, this.zzbal.intValue());
            }
            if (this.zzbam != null && this.zzbam.length > 0) {
                i = zzz;
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbam) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        i += zzsn.zzc(2, com_google_android_gms_internal_zzsu);
                    }
                }
                zzz = i;
            }
            if (this.zzban != null && this.zzban.length > 0) {
                i = zzz;
                for (zzsu com_google_android_gms_internal_zzsu2 : this.zzban) {
                    if (com_google_android_gms_internal_zzsu2 != null) {
                        i += zzsn.zzc(3, com_google_android_gms_internal_zzsu2);
                    }
                }
                zzz = i;
            }
            if (this.zzbao != null) {
                zzz += zzsn.zzd(4, this.zzbao.longValue());
            }
            if (this.zzbap != null) {
                zzz += zzsn.zzd(5, this.zzbap.longValue());
            }
            if (this.zzbaq != null) {
                zzz += zzsn.zzd(6, this.zzbaq.longValue());
            }
            if (this.zzbas != null) {
                zzz += zzsn.zzd(7, this.zzbas.longValue());
            }
            if (this.zzbat != null) {
                zzz += zzsn.zzo(8, this.zzbat);
            }
            if (this.osVersion != null) {
                zzz += zzsn.zzo(9, this.osVersion);
            }
            if (this.zzbau != null) {
                zzz += zzsn.zzo(10, this.zzbau);
            }
            if (this.zzbav != null) {
                zzz += zzsn.zzo(11, this.zzbav);
            }
            if (this.zzbaw != null) {
                zzz += zzsn.zzC(12, this.zzbaw.intValue());
            }
            if (this.zzaVu != null) {
                zzz += zzsn.zzo(13, this.zzaVu);
            }
            if (this.appId != null) {
                zzz += zzsn.zzo(14, this.appId);
            }
            if (this.zzaMV != null) {
                zzz += zzsn.zzo(16, this.zzaMV);
            }
            if (this.zzbax != null) {
                zzz += zzsn.zzd(17, this.zzbax.longValue());
            }
            if (this.zzbay != null) {
                zzz += zzsn.zzd(18, this.zzbay.longValue());
            }
            if (this.zzbaz != null) {
                zzz += zzsn.zzo(19, this.zzbaz);
            }
            if (this.zzbaA != null) {
                zzz += zzsn.zzf(20, this.zzbaA.booleanValue());
            }
            if (this.zzbaB != null) {
                zzz += zzsn.zzo(21, this.zzbaB);
            }
            if (this.zzbaC != null) {
                zzz += zzsn.zzd(22, this.zzbaC.longValue());
            }
            if (this.zzbaD != null) {
                zzz += zzsn.zzC(23, this.zzbaD.intValue());
            }
            if (this.zzaVx != null) {
                zzz += zzsn.zzo(24, this.zzaVx);
            }
            if (this.zzaVt != null) {
                zzz += zzsn.zzo(25, this.zzaVt);
            }
            if (this.zzbar != null) {
                zzz += zzsn.zzd(26, this.zzbar.longValue());
            }
            if (this.zzbaE != null) {
                zzz += zzsn.zzf(28, this.zzbaE.booleanValue());
            }
            if (this.zzbaF != null && this.zzbaF.length > 0) {
                while (i2 < this.zzbaF.length) {
                    zzsu com_google_android_gms_internal_zzsu3 = this.zzbaF[i2];
                    if (com_google_android_gms_internal_zzsu3 != null) {
                        zzz += zzsn.zzc(29, com_google_android_gms_internal_zzsu3);
                    }
                    i2++;
                }
            }
            return zzz;
        }
    }

    public static final class zzf extends zzsu {
        public long[] zzbaG;
        public long[] zzbaH;

        public zzf() {
            zzDY();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzf)) {
                    return false;
                }
                zzf com_google_android_gms_internal_zzqb_zzf = (zzf) obj;
                if (!zzss.equals(this.zzbaG, com_google_android_gms_internal_zzqb_zzf.zzbaG)) {
                    return false;
                }
                if (!zzss.equals(this.zzbaH, com_google_android_gms_internal_zzqb_zzf.zzbaH)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            return ((((getClass().getName().hashCode() + 527) * 31) + zzss.hashCode(this.zzbaG)) * 31) + zzss.hashCode(this.zzbaH);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzH(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            int i = 0;
            if (this.zzbaG != null && this.zzbaG.length > 0) {
                for (long zza : this.zzbaG) {
                    com_google_android_gms_internal_zzsn.zza(1, zza);
                }
            }
            if (this.zzbaH != null && this.zzbaH.length > 0) {
                while (i < this.zzbaH.length) {
                    com_google_android_gms_internal_zzsn.zza(2, this.zzbaH[i]);
                    i++;
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzf zzDY() {
            this.zzbaG = zzsx.zzbux;
            this.zzbaH = zzsx.zzbux;
            this.zzbuu = -1;
            return this;
        }

        public final zzf zzH(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                int zzc;
                Object obj;
                int zzmq;
                Object obj2;
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 8);
                        zzIX = this.zzbaG == null ? 0 : this.zzbaG.length;
                        obj = new long[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbaG, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.zzIZ();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzIZ();
                        this.zzbaG = obj;
                        continue;
                    case 10:
                        zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                        zzc = com_google_android_gms_internal_zzsm.getPosition();
                        zzIX = 0;
                        while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                            com_google_android_gms_internal_zzsm.zzIZ();
                            zzIX++;
                        }
                        com_google_android_gms_internal_zzsm.zzms(zzc);
                        zzc = this.zzbaG == null ? 0 : this.zzbaG.length;
                        obj2 = new long[(zzIX + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzbaG, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzsm.zzIZ();
                            zzc++;
                        }
                        this.zzbaG = obj2;
                        com_google_android_gms_internal_zzsm.zzmr(zzmq);
                        continue;
                    case 16:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 16);
                        zzIX = this.zzbaH == null ? 0 : this.zzbaH.length;
                        obj = new long[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbaH, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.zzIZ();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzIZ();
                        this.zzbaH = obj;
                        continue;
                    case 18:
                        zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                        zzc = com_google_android_gms_internal_zzsm.getPosition();
                        zzIX = 0;
                        while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                            com_google_android_gms_internal_zzsm.zzIZ();
                            zzIX++;
                        }
                        com_google_android_gms_internal_zzsm.zzms(zzc);
                        zzc = this.zzbaH == null ? 0 : this.zzbaH.length;
                        obj2 = new long[(zzIX + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzbaH, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzsm.zzIZ();
                            zzc++;
                        }
                        this.zzbaH = obj2;
                        com_google_android_gms_internal_zzsm.zzmr(zzmq);
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
            int i;
            int i2;
            int i3 = 0;
            int zzz = super.zzz();
            if (this.zzbaG == null || this.zzbaG.length <= 0) {
                i = zzz;
            } else {
                i2 = 0;
                for (long zzar : this.zzbaG) {
                    i2 += zzsn.zzar(zzar);
                }
                i = (this.zzbaG.length * 1) + (i2 + zzz);
            }
            if (this.zzbaH == null || this.zzbaH.length <= 0) {
                return i;
            }
            for (long zzar2 : this.zzbaH) {
                i3 += zzsn.zzar(zzar2);
            }
            return (i + i3) + (this.zzbaH.length * 1);
        }
    }

    public static final class zzg extends zzsu {
        private static volatile zzg[] zzbaI;
        public String name;
        public Float zzaZo;
        public String zzamJ;
        public Long zzbaJ;
        public Long zzbai;

        public zzg() {
            zzEa();
        }

        public static zzg[] zzDZ() {
            if (zzbaI == null) {
                synchronized (zzss.zzbut) {
                    if (zzbaI == null) {
                        zzbaI = new zzg[0];
                    }
                }
            }
            return zzbaI;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzg)) {
                    return false;
                }
                zzg com_google_android_gms_internal_zzqb_zzg = (zzg) obj;
                if (this.zzbaJ == null) {
                    if (com_google_android_gms_internal_zzqb_zzg.zzbaJ != null) {
                        return false;
                    }
                } else if (!this.zzbaJ.equals(com_google_android_gms_internal_zzqb_zzg.zzbaJ)) {
                    return false;
                }
                if (this.name == null) {
                    if (com_google_android_gms_internal_zzqb_zzg.name != null) {
                        return false;
                    }
                } else if (!this.name.equals(com_google_android_gms_internal_zzqb_zzg.name)) {
                    return false;
                }
                if (this.zzamJ == null) {
                    if (com_google_android_gms_internal_zzqb_zzg.zzamJ != null) {
                        return false;
                    }
                } else if (!this.zzamJ.equals(com_google_android_gms_internal_zzqb_zzg.zzamJ)) {
                    return false;
                }
                if (this.zzbai == null) {
                    if (com_google_android_gms_internal_zzqb_zzg.zzbai != null) {
                        return false;
                    }
                } else if (!this.zzbai.equals(com_google_android_gms_internal_zzqb_zzg.zzbai)) {
                    return false;
                }
                if (this.zzaZo == null) {
                    if (com_google_android_gms_internal_zzqb_zzg.zzaZo != null) {
                        return false;
                    }
                } else if (!this.zzaZo.equals(com_google_android_gms_internal_zzqb_zzg.zzaZo)) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.zzbaJ == null ? 0 : this.zzbaJ.hashCode();
            int hashCode3 = this.name == null ? 0 : this.name.hashCode();
            int hashCode4 = this.zzamJ == null ? 0 : this.zzamJ.hashCode();
            int hashCode5 = this.zzbai == null ? 0 : this.zzbai.hashCode();
            if (this.zzaZo != null) {
                i = this.zzaZo.hashCode();
            }
            return ((((((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzI(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzbaJ != null) {
                com_google_android_gms_internal_zzsn.zzb(1, this.zzbaJ.longValue());
            }
            if (this.name != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.name);
            }
            if (this.zzamJ != null) {
                com_google_android_gms_internal_zzsn.zzn(3, this.zzamJ);
            }
            if (this.zzbai != null) {
                com_google_android_gms_internal_zzsn.zzb(4, this.zzbai.longValue());
            }
            if (this.zzaZo != null) {
                com_google_android_gms_internal_zzsn.zzb(5, this.zzaZo.floatValue());
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzg zzEa() {
            this.zzbaJ = null;
            this.name = null;
            this.zzamJ = null;
            this.zzbai = null;
            this.zzaZo = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzg zzI(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzbaJ = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 18:
                        this.name = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 26:
                        this.zzamJ = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 32:
                        this.zzbai = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 45:
                        this.zzaZo = Float.valueOf(com_google_android_gms_internal_zzsm.readFloat());
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
            if (this.zzbaJ != null) {
                zzz += zzsn.zzd(1, this.zzbaJ.longValue());
            }
            if (this.name != null) {
                zzz += zzsn.zzo(2, this.name);
            }
            if (this.zzamJ != null) {
                zzz += zzsn.zzo(3, this.zzamJ);
            }
            if (this.zzbai != null) {
                zzz += zzsn.zzd(4, this.zzbai.longValue());
            }
            return this.zzaZo != null ? zzz + zzsn.zzc(5, this.zzaZo.floatValue()) : zzz;
        }
    }
}
