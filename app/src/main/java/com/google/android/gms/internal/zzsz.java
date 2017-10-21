package com.google.android.gms.internal;

import android.support.v4.media.TransportMediator;
import java.io.IOException;
import java.util.Arrays;

public interface zzsz {

    public static final class zza extends zzso<zza> {
        public String[] zzbuI;
        public String[] zzbuJ;
        public int[] zzbuK;
        public long[] zzbuL;

        public zza() {
            zzJC();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zza)) {
                    return false;
                }
                zza com_google_android_gms_internal_zzsz_zza = (zza) obj;
                if (!zzss.equals(this.zzbuI, com_google_android_gms_internal_zzsz_zza.zzbuI) || !zzss.equals(this.zzbuJ, com_google_android_gms_internal_zzsz_zza.zzbuJ) || !zzss.equals(this.zzbuK, com_google_android_gms_internal_zzsz_zza.zzbuK) || !zzss.equals(this.zzbuL, com_google_android_gms_internal_zzsz_zza.zzbuL)) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(com_google_android_gms_internal_zzsz_zza.zzbuj);
                }
                if (!(com_google_android_gms_internal_zzsz_zza.zzbuj == null || com_google_android_gms_internal_zzsz_zza.zzbuj.isEmpty())) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = zzss.hashCode(this.zzbuI);
            int hashCode3 = zzss.hashCode(this.zzbuJ);
            int hashCode4 = zzss.hashCode(this.zzbuK);
            int hashCode5 = zzss.hashCode(this.zzbuL);
            int hashCode6 = (this.zzbuj == null || this.zzbuj.isEmpty()) ? 0 : this.zzbuj.hashCode();
            return hashCode6 + ((((((((((hashCode + 527) * 31) + hashCode2) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31);
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzS(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            int i = 0;
            if (this.zzbuI != null && this.zzbuI.length > 0) {
                for (String str : this.zzbuI) {
                    if (str != null) {
                        com_google_android_gms_internal_zzsn.zzn(1, str);
                    }
                }
            }
            if (this.zzbuJ != null && this.zzbuJ.length > 0) {
                for (String str2 : this.zzbuJ) {
                    if (str2 != null) {
                        com_google_android_gms_internal_zzsn.zzn(2, str2);
                    }
                }
            }
            if (this.zzbuK != null && this.zzbuK.length > 0) {
                for (int zzA : this.zzbuK) {
                    com_google_android_gms_internal_zzsn.zzA(3, zzA);
                }
            }
            if (this.zzbuL != null && this.zzbuL.length > 0) {
                while (i < this.zzbuL.length) {
                    com_google_android_gms_internal_zzsn.zzb(4, this.zzbuL[i]);
                    i++;
                }
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zza zzJC() {
            this.zzbuI = zzsx.zzbuB;
            this.zzbuJ = zzsx.zzbuB;
            this.zzbuK = zzsx.zzbuw;
            this.zzbuL = zzsx.zzbux;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public final zza zzS(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                int zzc;
                Object obj;
                int zzmq;
                Object obj2;
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 10);
                        zzIX = this.zzbuI == null ? 0 : this.zzbuI.length;
                        obj = new String[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuI, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                        this.zzbuI = obj;
                        continue;
                    case 18:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 18);
                        zzIX = this.zzbuJ == null ? 0 : this.zzbuJ.length;
                        obj = new String[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuJ, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readString();
                        this.zzbuJ = obj;
                        continue;
                    case 24:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 24);
                        zzIX = this.zzbuK == null ? 0 : this.zzbuK.length;
                        obj = new int[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuK, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                        this.zzbuK = obj;
                        continue;
                    case 26:
                        zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                        zzc = com_google_android_gms_internal_zzsm.getPosition();
                        zzIX = 0;
                        while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                            com_google_android_gms_internal_zzsm.zzJb();
                            zzIX++;
                        }
                        com_google_android_gms_internal_zzsm.zzms(zzc);
                        zzc = this.zzbuK == null ? 0 : this.zzbuK.length;
                        obj2 = new int[(zzIX + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzbuK, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzsm.zzJb();
                            zzc++;
                        }
                        this.zzbuK = obj2;
                        com_google_android_gms_internal_zzsm.zzmr(zzmq);
                        continue;
                    case 32:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 32);
                        zzIX = this.zzbuL == null ? 0 : this.zzbuL.length;
                        obj = new long[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuL, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.zzJa();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzJa();
                        this.zzbuL = obj;
                        continue;
                    case 34:
                        zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                        zzc = com_google_android_gms_internal_zzsm.getPosition();
                        zzIX = 0;
                        while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                            com_google_android_gms_internal_zzsm.zzJa();
                            zzIX++;
                        }
                        com_google_android_gms_internal_zzsm.zzms(zzc);
                        zzc = this.zzbuL == null ? 0 : this.zzbuL.length;
                        obj2 = new long[(zzIX + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzbuL, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzsm.zzJa();
                            zzc++;
                        }
                        this.zzbuL = obj2;
                        com_google_android_gms_internal_zzsm.zzmr(zzmq);
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
            int i3 = 0;
            int zzz = super.zzz();
            if (this.zzbuI != null && this.zzbuI.length > 0) {
                i = 0;
                i2 = 0;
                for (String str : this.zzbuI) {
                    if (str != null) {
                        i++;
                        i2 += zzsn.zzgO(str);
                    }
                }
                zzz = (i * 1) + (i2 + zzz);
            }
            if (this.zzbuJ != null && this.zzbuJ.length > 0) {
                i = 0;
                i2 = 0;
                for (String str2 : this.zzbuJ) {
                    if (str2 != null) {
                        i++;
                        i2 += zzsn.zzgO(str2);
                    }
                }
                zzz = (i2 + zzz) + (i * 1);
            }
            if (this.zzbuK != null && this.zzbuK.length > 0) {
                i = 0;
                for (int zzmx : this.zzbuK) {
                    i += zzsn.zzmx(zzmx);
                }
                zzz = (i + zzz) + (this.zzbuK.length * 1);
            }
            if (this.zzbuL == null || this.zzbuL.length <= 0) {
                return zzz;
            }
            for (long zzas : this.zzbuL) {
                i3 += zzsn.zzas(zzas);
            }
            return (zzz + i3) + (this.zzbuL.length * 1);
        }
    }

    public static final class zzb extends zzso<zzb> {
        public String version;
        public int zzbuM;
        public String zzbuN;

        public zzb() {
            zzJD();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzb)) {
                    return false;
                }
                zzb com_google_android_gms_internal_zzsz_zzb = (zzb) obj;
                if (this.zzbuM != com_google_android_gms_internal_zzsz_zzb.zzbuM) {
                    return false;
                }
                if (this.zzbuN == null) {
                    if (com_google_android_gms_internal_zzsz_zzb.zzbuN != null) {
                        return false;
                    }
                } else if (!this.zzbuN.equals(com_google_android_gms_internal_zzsz_zzb.zzbuN)) {
                    return false;
                }
                if (this.version == null) {
                    if (com_google_android_gms_internal_zzsz_zzb.version != null) {
                        return false;
                    }
                } else if (!this.version.equals(com_google_android_gms_internal_zzsz_zzb.version)) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(com_google_android_gms_internal_zzsz_zzb.zzbuj);
                }
                if (!(com_google_android_gms_internal_zzsz_zzb.zzbuj == null || com_google_android_gms_internal_zzsz_zzb.zzbuj.isEmpty())) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int i2 = this.zzbuM;
            int hashCode2 = this.zzbuN == null ? 0 : this.zzbuN.hashCode();
            int hashCode3 = this.version == null ? 0 : this.version.hashCode();
            if (!(this.zzbuj == null || this.zzbuj.isEmpty())) {
                i = this.zzbuj.hashCode();
            }
            return ((((hashCode2 + ((((hashCode + 527) * 31) + i2) * 31)) * 31) + hashCode3) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzT(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.zzbuM != 0) {
                com_google_android_gms_internal_zzsn.zzA(1, this.zzbuM);
            }
            if (!this.zzbuN.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(2, this.zzbuN);
            }
            if (!this.version.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(3, this.version);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzb zzJD() {
            this.zzbuM = 0;
            this.zzbuN = "";
            this.version = "";
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzb zzT(zzsm com_google_android_gms_internal_zzsm) throws IOException {
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
                            case 7:
                            case 8:
                            case 9:
                            case 10:
                            case 11:
                            case 12:
                            case 13:
                            case 14:
                            case 15:
                            case 16:
                            case 17:
                            case 18:
                            case 19:
                            case 20:
                            case 21:
                            case 22:
                            case 23:
                            case 24:
                            case 25:
                            case 26:
                                this.zzbuM = zzIX;
                                break;
                            default:
                                continue;
                        }
                    case 18:
                        this.zzbuN = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 26:
                        this.version = com_google_android_gms_internal_zzsm.readString();
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
            int zzz = super.zzz();
            if (this.zzbuM != 0) {
                zzz += zzsn.zzC(1, this.zzbuM);
            }
            if (!this.zzbuN.equals("")) {
                zzz += zzsn.zzo(2, this.zzbuN);
            }
            return !this.version.equals("") ? zzz + zzsn.zzo(3, this.version) : zzz;
        }
    }

    public static final class zzc extends zzso<zzc> {
        public byte[] zzbuO;
        public byte[][] zzbuP;
        public boolean zzbuQ;

        public zzc() {
            zzJE();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzc)) {
                    return false;
                }
                zzc com_google_android_gms_internal_zzsz_zzc = (zzc) obj;
                if (!Arrays.equals(this.zzbuO, com_google_android_gms_internal_zzsz_zzc.zzbuO) || !zzss.zza(this.zzbuP, com_google_android_gms_internal_zzsz_zzc.zzbuP) || this.zzbuQ != com_google_android_gms_internal_zzsz_zzc.zzbuQ) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(com_google_android_gms_internal_zzsz_zzc.zzbuj);
                }
                if (!(com_google_android_gms_internal_zzsz_zzc.zzbuj == null || com_google_android_gms_internal_zzsz_zzc.zzbuj.isEmpty())) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = Arrays.hashCode(this.zzbuO);
            int zza = zzss.zza(this.zzbuP);
            int i = this.zzbuQ ? 1231 : 1237;
            int hashCode3 = (this.zzbuj == null || this.zzbuj.isEmpty()) ? 0 : this.zzbuj.hashCode();
            return ((i + ((((((hashCode + 527) * 31) + hashCode2) * 31) + zza) * 31)) * 31) + hashCode3;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzU(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (!Arrays.equals(this.zzbuO, zzsx.zzbuD)) {
                com_google_android_gms_internal_zzsn.zza(1, this.zzbuO);
            }
            if (this.zzbuP != null && this.zzbuP.length > 0) {
                for (byte[] bArr : this.zzbuP) {
                    if (bArr != null) {
                        com_google_android_gms_internal_zzsn.zza(2, bArr);
                    }
                }
            }
            if (this.zzbuQ) {
                com_google_android_gms_internal_zzsn.zze(3, this.zzbuQ);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzc zzJE() {
            this.zzbuO = zzsx.zzbuD;
            this.zzbuP = zzsx.zzbuC;
            this.zzbuQ = false;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzc zzU(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.zzbuO = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 18:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 18);
                        zzIX = this.zzbuP == null ? 0 : this.zzbuP.length;
                        Object obj = new byte[(zzc + zzIX)][];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuP, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.readBytes();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.readBytes();
                        this.zzbuP = obj;
                        continue;
                    case 24:
                        this.zzbuQ = com_google_android_gms_internal_zzsm.zzJc();
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
            int i = 0;
            int zzz = super.zzz();
            if (!Arrays.equals(this.zzbuO, zzsx.zzbuD)) {
                zzz += zzsn.zzb(1, this.zzbuO);
            }
            if (this.zzbuP != null && this.zzbuP.length > 0) {
                int i2 = 0;
                for (byte[] bArr : this.zzbuP) {
                    if (bArr != null) {
                        i++;
                        i2 += zzsn.zzG(bArr);
                    }
                }
                zzz = (zzz + i2) + (i * 1);
            }
            return this.zzbuQ ? zzz + zzsn.zzf(3, this.zzbuQ) : zzz;
        }
    }

    public static final class zzd extends zzso<zzd> {
        public String tag;
        public long zzbuR;
        public long zzbuS;
        public long zzbuT;
        public int zzbuU;
        public boolean zzbuV;
        public zze[] zzbuW;
        public zzb zzbuX;
        public byte[] zzbuY;
        public byte[] zzbuZ;
        public byte[] zzbva;
        public zza zzbvb;
        public String zzbvc;
        public long zzbvd;
        public zzc zzbve;
        public byte[] zzbvf;
        public int zzbvg;
        public int[] zzbvh;
        public long zzbvi;
        public int zzob;

        public zzd() {
            zzJF();
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zzd)) {
                    return false;
                }
                zzd com_google_android_gms_internal_zzsz_zzd = (zzd) obj;
                if (this.zzbuR != com_google_android_gms_internal_zzsz_zzd.zzbuR || this.zzbuS != com_google_android_gms_internal_zzsz_zzd.zzbuS || this.zzbuT != com_google_android_gms_internal_zzsz_zzd.zzbuT) {
                    return false;
                }
                if (this.tag == null) {
                    if (com_google_android_gms_internal_zzsz_zzd.tag != null) {
                        return false;
                    }
                } else if (!this.tag.equals(com_google_android_gms_internal_zzsz_zzd.tag)) {
                    return false;
                }
                if (this.zzbuU != com_google_android_gms_internal_zzsz_zzd.zzbuU || this.zzob != com_google_android_gms_internal_zzsz_zzd.zzob || this.zzbuV != com_google_android_gms_internal_zzsz_zzd.zzbuV || !zzss.equals(this.zzbuW, com_google_android_gms_internal_zzsz_zzd.zzbuW)) {
                    return false;
                }
                if (this.zzbuX == null) {
                    if (com_google_android_gms_internal_zzsz_zzd.zzbuX != null) {
                        return false;
                    }
                } else if (!this.zzbuX.equals(com_google_android_gms_internal_zzsz_zzd.zzbuX)) {
                    return false;
                }
                if (!Arrays.equals(this.zzbuY, com_google_android_gms_internal_zzsz_zzd.zzbuY) || !Arrays.equals(this.zzbuZ, com_google_android_gms_internal_zzsz_zzd.zzbuZ) || !Arrays.equals(this.zzbva, com_google_android_gms_internal_zzsz_zzd.zzbva)) {
                    return false;
                }
                if (this.zzbvb == null) {
                    if (com_google_android_gms_internal_zzsz_zzd.zzbvb != null) {
                        return false;
                    }
                } else if (!this.zzbvb.equals(com_google_android_gms_internal_zzsz_zzd.zzbvb)) {
                    return false;
                }
                if (this.zzbvc == null) {
                    if (com_google_android_gms_internal_zzsz_zzd.zzbvc != null) {
                        return false;
                    }
                } else if (!this.zzbvc.equals(com_google_android_gms_internal_zzsz_zzd.zzbvc)) {
                    return false;
                }
                if (this.zzbvd != com_google_android_gms_internal_zzsz_zzd.zzbvd) {
                    return false;
                }
                if (this.zzbve == null) {
                    if (com_google_android_gms_internal_zzsz_zzd.zzbve != null) {
                        return false;
                    }
                } else if (!this.zzbve.equals(com_google_android_gms_internal_zzsz_zzd.zzbve)) {
                    return false;
                }
                if (!Arrays.equals(this.zzbvf, com_google_android_gms_internal_zzsz_zzd.zzbvf) || this.zzbvg != com_google_android_gms_internal_zzsz_zzd.zzbvg || !zzss.equals(this.zzbvh, com_google_android_gms_internal_zzsz_zzd.zzbvh) || this.zzbvi != com_google_android_gms_internal_zzsz_zzd.zzbvi) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(com_google_android_gms_internal_zzsz_zzd.zzbuj);
                }
                if (!(com_google_android_gms_internal_zzsz_zzd.zzbuj == null || com_google_android_gms_internal_zzsz_zzd.zzbuj.isEmpty())) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int i2 = (int) (this.zzbuR ^ (this.zzbuR >>> 32));
            int i3 = (int) (this.zzbuS ^ (this.zzbuS >>> 32));
            int i4 = (int) (this.zzbuT ^ (this.zzbuT >>> 32));
            int hashCode2 = this.tag == null ? 0 : this.tag.hashCode();
            int i5 = this.zzbuU;
            int i6 = this.zzob;
            int i7 = this.zzbuV ? 1231 : 1237;
            int hashCode3 = zzss.hashCode(this.zzbuW);
            int hashCode4 = this.zzbuX == null ? 0 : this.zzbuX.hashCode();
            int hashCode5 = Arrays.hashCode(this.zzbuY);
            int hashCode6 = Arrays.hashCode(this.zzbuZ);
            int hashCode7 = Arrays.hashCode(this.zzbva);
            int hashCode8 = this.zzbvb == null ? 0 : this.zzbvb.hashCode();
            int hashCode9 = this.zzbvc == null ? 0 : this.zzbvc.hashCode();
            int i8 = (int) (this.zzbvd ^ (this.zzbvd >>> 32));
            int hashCode10 = this.zzbve == null ? 0 : this.zzbve.hashCode();
            int hashCode11 = Arrays.hashCode(this.zzbvf);
            int i9 = this.zzbvg;
            int hashCode12 = zzss.hashCode(this.zzbvh);
            int i10 = (int) (this.zzbvi ^ (this.zzbvi >>> 32));
            if (!(this.zzbuj == null || this.zzbuj.isEmpty())) {
                i = this.zzbuj.hashCode();
            }
            return ((((((((((((((((((((((((((((((((((hashCode2 + ((((((((hashCode + 527) * 31) + i2) * 31) + i3) * 31) + i4) * 31)) * 31) + i5) * 31) + i6) * 31) + i7) * 31) + hashCode3) * 31) + hashCode4) * 31) + hashCode5) * 31) + hashCode6) * 31) + hashCode7) * 31) + hashCode8) * 31) + hashCode9) * 31) + i8) * 31) + hashCode10) * 31) + hashCode11) * 31) + i9) * 31) + hashCode12) * 31) + i10) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzV(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            int i = 0;
            if (this.zzbuR != 0) {
                com_google_android_gms_internal_zzsn.zzb(1, this.zzbuR);
            }
            if (!this.tag.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(2, this.tag);
            }
            if (this.zzbuW != null && this.zzbuW.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbuW) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(3, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (!Arrays.equals(this.zzbuY, zzsx.zzbuD)) {
                com_google_android_gms_internal_zzsn.zza(6, this.zzbuY);
            }
            if (this.zzbvb != null) {
                com_google_android_gms_internal_zzsn.zza(7, this.zzbvb);
            }
            if (!Arrays.equals(this.zzbuZ, zzsx.zzbuD)) {
                com_google_android_gms_internal_zzsn.zza(8, this.zzbuZ);
            }
            if (this.zzbuX != null) {
                com_google_android_gms_internal_zzsn.zza(9, this.zzbuX);
            }
            if (this.zzbuV) {
                com_google_android_gms_internal_zzsn.zze(10, this.zzbuV);
            }
            if (this.zzbuU != 0) {
                com_google_android_gms_internal_zzsn.zzA(11, this.zzbuU);
            }
            if (this.zzob != 0) {
                com_google_android_gms_internal_zzsn.zzA(12, this.zzob);
            }
            if (!Arrays.equals(this.zzbva, zzsx.zzbuD)) {
                com_google_android_gms_internal_zzsn.zza(13, this.zzbva);
            }
            if (!this.zzbvc.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(14, this.zzbvc);
            }
            if (this.zzbvd != 180000) {
                com_google_android_gms_internal_zzsn.zzc(15, this.zzbvd);
            }
            if (this.zzbve != null) {
                com_google_android_gms_internal_zzsn.zza(16, this.zzbve);
            }
            if (this.zzbuS != 0) {
                com_google_android_gms_internal_zzsn.zzb(17, this.zzbuS);
            }
            if (!Arrays.equals(this.zzbvf, zzsx.zzbuD)) {
                com_google_android_gms_internal_zzsn.zza(18, this.zzbvf);
            }
            if (this.zzbvg != 0) {
                com_google_android_gms_internal_zzsn.zzA(19, this.zzbvg);
            }
            if (this.zzbvh != null && this.zzbvh.length > 0) {
                while (i < this.zzbvh.length) {
                    com_google_android_gms_internal_zzsn.zzA(20, this.zzbvh[i]);
                    i++;
                }
            }
            if (this.zzbuT != 0) {
                com_google_android_gms_internal_zzsn.zzb(21, this.zzbuT);
            }
            if (this.zzbvi != 0) {
                com_google_android_gms_internal_zzsn.zzb(22, this.zzbvi);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zzd zzJF() {
            this.zzbuR = 0;
            this.zzbuS = 0;
            this.zzbuT = 0;
            this.tag = "";
            this.zzbuU = 0;
            this.zzob = 0;
            this.zzbuV = false;
            this.zzbuW = zze.zzJG();
            this.zzbuX = null;
            this.zzbuY = zzsx.zzbuD;
            this.zzbuZ = zzsx.zzbuD;
            this.zzbva = zzsx.zzbuD;
            this.zzbvb = null;
            this.zzbvc = "";
            this.zzbvd = 180000;
            this.zzbve = null;
            this.zzbvf = zzsx.zzbuD;
            this.zzbvg = 0;
            this.zzbvh = zzsx.zzbuw;
            this.zzbvi = 0;
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public final zzd zzV(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                int zzc;
                Object obj;
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.zzbuR = com_google_android_gms_internal_zzsm.zzJa();
                        continue;
                    case 18:
                        this.tag = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 26:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 26);
                        zzIX = this.zzbuW == null ? 0 : this.zzbuW.length;
                        obj = new zze[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbuW, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new zze();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new zze();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.zzbuW = obj;
                        continue;
                    case 50:
                        this.zzbuY = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 58:
                        if (this.zzbvb == null) {
                            this.zzbvb = new zza();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzbvb);
                        continue;
                    case 66:
                        this.zzbuZ = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 74:
                        if (this.zzbuX == null) {
                            this.zzbuX = new zzb();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzbuX);
                        continue;
                    case 80:
                        this.zzbuV = com_google_android_gms_internal_zzsm.zzJc();
                        continue;
                    case 88:
                        this.zzbuU = com_google_android_gms_internal_zzsm.zzJb();
                        continue;
                    case 96:
                        this.zzob = com_google_android_gms_internal_zzsm.zzJb();
                        continue;
                    case 106:
                        this.zzbva = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 114:
                        this.zzbvc = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 120:
                        this.zzbvd = com_google_android_gms_internal_zzsm.zzJe();
                        continue;
                    case TransportMediator.KEYCODE_MEDIA_RECORD /*130*/:
                        if (this.zzbve == null) {
                            this.zzbve = new zzc();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.zzbve);
                        continue;
                    case 136:
                        this.zzbuS = com_google_android_gms_internal_zzsm.zzJa();
                        continue;
                    case 146:
                        this.zzbvf = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 152:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
                            case 0:
                            case 1:
                            case 2:
                                this.zzbvg = zzIX;
                                break;
                            default:
                                continue;
                        }
                    case 160:
                        zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 160);
                        zzIX = this.zzbvh == null ? 0 : this.zzbvh.length;
                        obj = new int[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.zzbvh, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = com_google_android_gms_internal_zzsm.zzJb();
                        this.zzbvh = obj;
                        continue;
                    case 162:
                        int zzmq = com_google_android_gms_internal_zzsm.zzmq(com_google_android_gms_internal_zzsm.zzJf());
                        zzc = com_google_android_gms_internal_zzsm.getPosition();
                        zzIX = 0;
                        while (com_google_android_gms_internal_zzsm.zzJk() > 0) {
                            com_google_android_gms_internal_zzsm.zzJb();
                            zzIX++;
                        }
                        com_google_android_gms_internal_zzsm.zzms(zzc);
                        zzc = this.zzbvh == null ? 0 : this.zzbvh.length;
                        Object obj2 = new int[(zzIX + zzc)];
                        if (zzc != 0) {
                            System.arraycopy(this.zzbvh, 0, obj2, 0, zzc);
                        }
                        while (zzc < obj2.length) {
                            obj2[zzc] = com_google_android_gms_internal_zzsm.zzJb();
                            zzc++;
                        }
                        this.zzbvh = obj2;
                        com_google_android_gms_internal_zzsm.zzmr(zzmq);
                        continue;
                    case 168:
                        this.zzbuT = com_google_android_gms_internal_zzsm.zzJa();
                        continue;
                    case 176:
                        this.zzbvi = com_google_android_gms_internal_zzsm.zzJa();
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
            int i2 = 0;
            int zzz = super.zzz();
            if (this.zzbuR != 0) {
                zzz += zzsn.zzd(1, this.zzbuR);
            }
            if (!this.tag.equals("")) {
                zzz += zzsn.zzo(2, this.tag);
            }
            if (this.zzbuW != null && this.zzbuW.length > 0) {
                i = zzz;
                for (zzsu com_google_android_gms_internal_zzsu : this.zzbuW) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        i += zzsn.zzc(3, com_google_android_gms_internal_zzsu);
                    }
                }
                zzz = i;
            }
            if (!Arrays.equals(this.zzbuY, zzsx.zzbuD)) {
                zzz += zzsn.zzb(6, this.zzbuY);
            }
            if (this.zzbvb != null) {
                zzz += zzsn.zzc(7, this.zzbvb);
            }
            if (!Arrays.equals(this.zzbuZ, zzsx.zzbuD)) {
                zzz += zzsn.zzb(8, this.zzbuZ);
            }
            if (this.zzbuX != null) {
                zzz += zzsn.zzc(9, this.zzbuX);
            }
            if (this.zzbuV) {
                zzz += zzsn.zzf(10, this.zzbuV);
            }
            if (this.zzbuU != 0) {
                zzz += zzsn.zzC(11, this.zzbuU);
            }
            if (this.zzob != 0) {
                zzz += zzsn.zzC(12, this.zzob);
            }
            if (!Arrays.equals(this.zzbva, zzsx.zzbuD)) {
                zzz += zzsn.zzb(13, this.zzbva);
            }
            if (!this.zzbvc.equals("")) {
                zzz += zzsn.zzo(14, this.zzbvc);
            }
            if (this.zzbvd != 180000) {
                zzz += zzsn.zze(15, this.zzbvd);
            }
            if (this.zzbve != null) {
                zzz += zzsn.zzc(16, this.zzbve);
            }
            if (this.zzbuS != 0) {
                zzz += zzsn.zzd(17, this.zzbuS);
            }
            if (!Arrays.equals(this.zzbvf, zzsx.zzbuD)) {
                zzz += zzsn.zzb(18, this.zzbvf);
            }
            if (this.zzbvg != 0) {
                zzz += zzsn.zzC(19, this.zzbvg);
            }
            if (this.zzbvh != null && this.zzbvh.length > 0) {
                for (int zzmx : this.zzbvh) {
                    i2 += zzsn.zzmx(zzmx);
                }
                zzz = (zzz + i2) + (this.zzbvh.length * 2);
            }
            if (this.zzbuT != 0) {
                zzz += zzsn.zzd(21, this.zzbuT);
            }
            return this.zzbvi != 0 ? zzz + zzsn.zzd(22, this.zzbvi) : zzz;
        }
    }

    public static final class zze extends zzso<zze> {
        private static volatile zze[] zzbvj;
        public String key;
        public String value;

        public zze() {
            zzJH();
        }

        public static zze[] zzJG() {
            if (zzbvj == null) {
                synchronized (zzss.zzbut) {
                    if (zzbvj == null) {
                        zzbvj = new zze[0];
                    }
                }
            }
            return zzbvj;
        }

        public final boolean equals(Object obj) {
            if (obj != this) {
                if (!(obj instanceof zze)) {
                    return false;
                }
                zze com_google_android_gms_internal_zzsz_zze = (zze) obj;
                if (this.key == null) {
                    if (com_google_android_gms_internal_zzsz_zze.key != null) {
                        return false;
                    }
                } else if (!this.key.equals(com_google_android_gms_internal_zzsz_zze.key)) {
                    return false;
                }
                if (this.value == null) {
                    if (com_google_android_gms_internal_zzsz_zze.value != null) {
                        return false;
                    }
                } else if (!this.value.equals(com_google_android_gms_internal_zzsz_zze.value)) {
                    return false;
                }
                if (this.zzbuj != null && !this.zzbuj.isEmpty()) {
                    return this.zzbuj.equals(com_google_android_gms_internal_zzsz_zze.zzbuj);
                }
                if (!(com_google_android_gms_internal_zzsz_zze.zzbuj == null || com_google_android_gms_internal_zzsz_zze.zzbuj.isEmpty())) {
                    return false;
                }
            }
            return true;
        }

        public final int hashCode() {
            int i = 0;
            int hashCode = getClass().getName().hashCode();
            int hashCode2 = this.key == null ? 0 : this.key.hashCode();
            int hashCode3 = this.value == null ? 0 : this.value.hashCode();
            if (!(this.zzbuj == null || this.zzbuj.isEmpty())) {
                i = this.zzbuj.hashCode();
            }
            return ((((hashCode2 + ((hashCode + 527) * 31)) * 31) + hashCode3) * 31) + i;
        }

        public final /* synthetic */ zzsu mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return zzW(com_google_android_gms_internal_zzsm);
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (!this.key.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(1, this.key);
            }
            if (!this.value.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(2, this.value);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        public final zze zzJH() {
            this.key = "";
            this.value = "";
            this.zzbuj = null;
            this.zzbuu = -1;
            return this;
        }

        public final zze zzW(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.key = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 18:
                        this.value = com_google_android_gms_internal_zzsm.readString();
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
            int zzz = super.zzz();
            if (!this.key.equals("")) {
                zzz += zzsn.zzo(1, this.key);
            }
            return !this.value.equals("") ? zzz + zzsn.zzo(2, this.value) : zzz;
        }
    }
}
