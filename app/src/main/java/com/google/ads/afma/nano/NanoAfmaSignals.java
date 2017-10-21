package com.google.ads.afma.nano;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;

public interface NanoAfmaSignals {

    public static final class AFMASignals extends zzsu {
        private static volatile AFMASignals[] zzaL;
        public Long actSignal;
        public Long acxSignal;
        public Long acySignal;
        public Long aczSignal;
        public AdAttestationSignal adAttestationSignal;
        public String afmaVersion;
        public Long attSignal;
        public Long atvSignal;
        public Long btlSignal;
        public Long btsSignal;
        public String cerSignal;
        public Boolean didOptOut;
        public String didSignal;
        public String didSignalAndroidAdId;
        public Integer didSignalType;
        public Long evtTime;
        public String intSignal;
        public Long jbkSignal;
        public Long netSignal;
        public Long ornSignal;
        public String osVersion;
        public TouchInfo[] previousTouches;
        public Long psnSignal;
        public RawAdAttestationSignal rawAdAttestationSignal;
        public Long reqType;
        public String stkSignal;
        public Long swzSignal;
        public Long tccSignal;
        public Long tcdSignal;
        public Long tcdnSignal;
        public Long tcmSignal;
        public Long tcpSignal;
        public Long tctSignal;
        public Long tcuSignal;
        public Long tcxSignal;
        public Long tcySignal;
        public Long uhSignal;
        public Long uptSignal;
        public Long usgSignal;
        public Long utzSignal;
        public Long uwSignal;
        public Long vcdSignal;
        public Long visSignal;
        public String vnmSignal;

        public interface DeviceIdType {
            public static final int DEVICE_IDENTIFIER_ADVERTISER_ID = 3;
            public static final int DEVICE_IDENTIFIER_ADVERTISER_ID_UNHASHED = 4;
            public static final int DEVICE_IDENTIFIER_ANDROID_AD_ID = 5;
            public static final int DEVICE_IDENTIFIER_APP_SPECIFIC_ID = 1;
            public static final int DEVICE_IDENTIFIER_GFIBER_ADVERTISING_ID = 6;
            public static final int DEVICE_IDENTIFIER_GLOBAL_ID = 2;
            public static final int DEVICE_IDENTIFIER_NO_ID = 0;
        }

        public static final class TouchInfo extends zzsu {
            private static volatile TouchInfo[] zzaM;
            public Long tcxSignal;
            public Long tcySignal;

            public TouchInfo() {
                clear();
            }

            public static TouchInfo[] emptyArray() {
                if (zzaM == null) {
                    synchronized (zzss.zzbut) {
                        if (zzaM == null) {
                            zzaM = new TouchInfo[0];
                        }
                    }
                }
                return zzaM;
            }

            public static TouchInfo parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
                return new TouchInfo().mergeFrom(com_google_android_gms_internal_zzsm);
            }

            public static TouchInfo parseFrom(byte[] bArr) throws zzst {
                return (TouchInfo) zzsu.mergeFrom(new TouchInfo(), bArr);
            }

            public final TouchInfo clear() {
                this.tcxSignal = null;
                this.tcySignal = null;
                this.zzbuu = -1;
                return this;
            }

            public final TouchInfo mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
                while (true) {
                    int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                    switch (zzIX) {
                        case 0:
                            break;
                        case 8:
                            this.tcxSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                            continue;
                        case 16:
                            this.tcySignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
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

            public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
                if (this.tcxSignal != null) {
                    com_google_android_gms_internal_zzsn.zzb(1, this.tcxSignal.longValue());
                }
                if (this.tcySignal != null) {
                    com_google_android_gms_internal_zzsn.zzb(2, this.tcySignal.longValue());
                }
                super.writeTo(com_google_android_gms_internal_zzsn);
            }

            protected final int zzz() {
                int zzz = super.zzz();
                if (this.tcxSignal != null) {
                    zzz += zzsn.zzd(1, this.tcxSignal.longValue());
                }
                return this.tcySignal != null ? zzz + zzsn.zzd(2, this.tcySignal.longValue()) : zzz;
            }
        }

        public AFMASignals() {
            clear();
        }

        public static AFMASignals[] emptyArray() {
            if (zzaL == null) {
                synchronized (zzss.zzbut) {
                    if (zzaL == null) {
                        zzaL = new AFMASignals[0];
                    }
                }
            }
            return zzaL;
        }

        public static AFMASignals parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return new AFMASignals().mergeFrom(com_google_android_gms_internal_zzsm);
        }

        public static AFMASignals parseFrom(byte[] bArr) throws zzst {
            return (AFMASignals) zzsu.mergeFrom(new AFMASignals(), bArr);
        }

        public final AFMASignals clear() {
            this.osVersion = null;
            this.afmaVersion = null;
            this.atvSignal = null;
            this.attSignal = null;
            this.btsSignal = null;
            this.btlSignal = null;
            this.acxSignal = null;
            this.acySignal = null;
            this.aczSignal = null;
            this.actSignal = null;
            this.netSignal = null;
            this.ornSignal = null;
            this.stkSignal = null;
            this.tcxSignal = null;
            this.tcySignal = null;
            this.tctSignal = null;
            this.uptSignal = null;
            this.visSignal = null;
            this.swzSignal = null;
            this.psnSignal = null;
            this.jbkSignal = null;
            this.usgSignal = null;
            this.intSignal = null;
            this.cerSignal = null;
            this.uwSignal = null;
            this.uhSignal = null;
            this.utzSignal = null;
            this.vnmSignal = null;
            this.vcdSignal = null;
            this.tcpSignal = null;
            this.tcdSignal = null;
            this.adAttestationSignal = null;
            this.tcuSignal = null;
            this.tcmSignal = null;
            this.tcdnSignal = null;
            this.tccSignal = null;
            this.previousTouches = TouchInfo.emptyArray();
            this.reqType = null;
            this.didSignal = null;
            this.didSignalType = null;
            this.didOptOut = null;
            this.didSignalAndroidAdId = null;
            this.evtTime = null;
            this.rawAdAttestationSignal = null;
            this.zzbuu = -1;
            return this;
        }

        public final AFMASignals mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.osVersion = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 18:
                        this.afmaVersion = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 24:
                        this.atvSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 32:
                        this.attSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 40:
                        this.btsSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 48:
                        this.btlSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 56:
                        this.acxSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 64:
                        this.acySignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 72:
                        this.aczSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 80:
                        this.actSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 88:
                        this.netSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 96:
                        this.ornSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 106:
                        this.stkSignal = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 112:
                        this.tcxSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 120:
                        this.tcySignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 128:
                        this.tctSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 136:
                        this.uptSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 144:
                        this.visSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 152:
                        this.swzSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 160:
                        this.psnSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 168:
                        this.reqType = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 176:
                        this.jbkSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 184:
                        this.usgSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 194:
                        this.didSignal = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 200:
                        this.evtTime = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 208:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                            case 4:
                            case 5:
                            case 6:
                                this.didSignalType = Integer.valueOf(zzIX);
                                break;
                            default:
                                continue;
                        }
                    case 218:
                        this.intSignal = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 224:
                        this.didOptOut = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 234:
                        this.cerSignal = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 242:
                        this.didSignalAndroidAdId = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 248:
                        this.uwSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 256:
                        this.uhSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 264:
                        this.utzSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 274:
                        this.vnmSignal = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 280:
                        this.vcdSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 288:
                        this.tcpSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 296:
                        this.tcdSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 306:
                        if (this.adAttestationSignal == null) {
                            this.adAttestationSignal = new AdAttestationSignal();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.adAttestationSignal);
                        continue;
                    case 312:
                        this.tcuSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 320:
                        this.tcmSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 328:
                        this.tcdnSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 336:
                        this.tccSignal = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 346:
                        int zzc = zzsx.zzc(com_google_android_gms_internal_zzsm, 346);
                        zzIX = this.previousTouches == null ? 0 : this.previousTouches.length;
                        Object obj = new TouchInfo[(zzc + zzIX)];
                        if (zzIX != 0) {
                            System.arraycopy(this.previousTouches, 0, obj, 0, zzIX);
                        }
                        while (zzIX < obj.length - 1) {
                            obj[zzIX] = new TouchInfo();
                            com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                            com_google_android_gms_internal_zzsm.zzIX();
                            zzIX++;
                        }
                        obj[zzIX] = new TouchInfo();
                        com_google_android_gms_internal_zzsm.zza(obj[zzIX]);
                        this.previousTouches = obj;
                        continue;
                    case 1610:
                        if (this.rawAdAttestationSignal == null) {
                            this.rawAdAttestationSignal = new RawAdAttestationSignal();
                        }
                        com_google_android_gms_internal_zzsm.zza(this.rawAdAttestationSignal);
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

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.osVersion != null) {
                com_google_android_gms_internal_zzsn.zzn(1, this.osVersion);
            }
            if (this.afmaVersion != null) {
                com_google_android_gms_internal_zzsn.zzn(2, this.afmaVersion);
            }
            if (this.atvSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(3, this.atvSignal.longValue());
            }
            if (this.attSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(4, this.attSignal.longValue());
            }
            if (this.btsSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(5, this.btsSignal.longValue());
            }
            if (this.btlSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(6, this.btlSignal.longValue());
            }
            if (this.acxSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(7, this.acxSignal.longValue());
            }
            if (this.acySignal != null) {
                com_google_android_gms_internal_zzsn.zzb(8, this.acySignal.longValue());
            }
            if (this.aczSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(9, this.aczSignal.longValue());
            }
            if (this.actSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(10, this.actSignal.longValue());
            }
            if (this.netSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(11, this.netSignal.longValue());
            }
            if (this.ornSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(12, this.ornSignal.longValue());
            }
            if (this.stkSignal != null) {
                com_google_android_gms_internal_zzsn.zzn(13, this.stkSignal);
            }
            if (this.tcxSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(14, this.tcxSignal.longValue());
            }
            if (this.tcySignal != null) {
                com_google_android_gms_internal_zzsn.zzb(15, this.tcySignal.longValue());
            }
            if (this.tctSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(16, this.tctSignal.longValue());
            }
            if (this.uptSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(17, this.uptSignal.longValue());
            }
            if (this.visSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(18, this.visSignal.longValue());
            }
            if (this.swzSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(19, this.swzSignal.longValue());
            }
            if (this.psnSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(20, this.psnSignal.longValue());
            }
            if (this.reqType != null) {
                com_google_android_gms_internal_zzsn.zzb(21, this.reqType.longValue());
            }
            if (this.jbkSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(22, this.jbkSignal.longValue());
            }
            if (this.usgSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(23, this.usgSignal.longValue());
            }
            if (this.didSignal != null) {
                com_google_android_gms_internal_zzsn.zzn(24, this.didSignal);
            }
            if (this.evtTime != null) {
                com_google_android_gms_internal_zzsn.zzb(25, this.evtTime.longValue());
            }
            if (this.didSignalType != null) {
                com_google_android_gms_internal_zzsn.zzA(26, this.didSignalType.intValue());
            }
            if (this.intSignal != null) {
                com_google_android_gms_internal_zzsn.zzn(27, this.intSignal);
            }
            if (this.didOptOut != null) {
                com_google_android_gms_internal_zzsn.zze(28, this.didOptOut.booleanValue());
            }
            if (this.cerSignal != null) {
                com_google_android_gms_internal_zzsn.zzn(29, this.cerSignal);
            }
            if (this.didSignalAndroidAdId != null) {
                com_google_android_gms_internal_zzsn.zzn(30, this.didSignalAndroidAdId);
            }
            if (this.uwSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(31, this.uwSignal.longValue());
            }
            if (this.uhSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(32, this.uhSignal.longValue());
            }
            if (this.utzSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(33, this.utzSignal.longValue());
            }
            if (this.vnmSignal != null) {
                com_google_android_gms_internal_zzsn.zzn(34, this.vnmSignal);
            }
            if (this.vcdSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(35, this.vcdSignal.longValue());
            }
            if (this.tcpSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(36, this.tcpSignal.longValue());
            }
            if (this.tcdSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(37, this.tcdSignal.longValue());
            }
            if (this.adAttestationSignal != null) {
                com_google_android_gms_internal_zzsn.zza(38, this.adAttestationSignal);
            }
            if (this.tcuSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(39, this.tcuSignal.longValue());
            }
            if (this.tcmSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(40, this.tcmSignal.longValue());
            }
            if (this.tcdnSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(41, this.tcdnSignal.longValue());
            }
            if (this.tccSignal != null) {
                com_google_android_gms_internal_zzsn.zzb(42, this.tccSignal.longValue());
            }
            if (this.previousTouches != null && this.previousTouches.length > 0) {
                for (zzsu com_google_android_gms_internal_zzsu : this.previousTouches) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        com_google_android_gms_internal_zzsn.zza(43, com_google_android_gms_internal_zzsu);
                    }
                }
            }
            if (this.rawAdAttestationSignal != null) {
                com_google_android_gms_internal_zzsn.zza(201, this.rawAdAttestationSignal);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.osVersion != null) {
                zzz += zzsn.zzo(1, this.osVersion);
            }
            if (this.afmaVersion != null) {
                zzz += zzsn.zzo(2, this.afmaVersion);
            }
            if (this.atvSignal != null) {
                zzz += zzsn.zzd(3, this.atvSignal.longValue());
            }
            if (this.attSignal != null) {
                zzz += zzsn.zzd(4, this.attSignal.longValue());
            }
            if (this.btsSignal != null) {
                zzz += zzsn.zzd(5, this.btsSignal.longValue());
            }
            if (this.btlSignal != null) {
                zzz += zzsn.zzd(6, this.btlSignal.longValue());
            }
            if (this.acxSignal != null) {
                zzz += zzsn.zzd(7, this.acxSignal.longValue());
            }
            if (this.acySignal != null) {
                zzz += zzsn.zzd(8, this.acySignal.longValue());
            }
            if (this.aczSignal != null) {
                zzz += zzsn.zzd(9, this.aczSignal.longValue());
            }
            if (this.actSignal != null) {
                zzz += zzsn.zzd(10, this.actSignal.longValue());
            }
            if (this.netSignal != null) {
                zzz += zzsn.zzd(11, this.netSignal.longValue());
            }
            if (this.ornSignal != null) {
                zzz += zzsn.zzd(12, this.ornSignal.longValue());
            }
            if (this.stkSignal != null) {
                zzz += zzsn.zzo(13, this.stkSignal);
            }
            if (this.tcxSignal != null) {
                zzz += zzsn.zzd(14, this.tcxSignal.longValue());
            }
            if (this.tcySignal != null) {
                zzz += zzsn.zzd(15, this.tcySignal.longValue());
            }
            if (this.tctSignal != null) {
                zzz += zzsn.zzd(16, this.tctSignal.longValue());
            }
            if (this.uptSignal != null) {
                zzz += zzsn.zzd(17, this.uptSignal.longValue());
            }
            if (this.visSignal != null) {
                zzz += zzsn.zzd(18, this.visSignal.longValue());
            }
            if (this.swzSignal != null) {
                zzz += zzsn.zzd(19, this.swzSignal.longValue());
            }
            if (this.psnSignal != null) {
                zzz += zzsn.zzd(20, this.psnSignal.longValue());
            }
            if (this.reqType != null) {
                zzz += zzsn.zzd(21, this.reqType.longValue());
            }
            if (this.jbkSignal != null) {
                zzz += zzsn.zzd(22, this.jbkSignal.longValue());
            }
            if (this.usgSignal != null) {
                zzz += zzsn.zzd(23, this.usgSignal.longValue());
            }
            if (this.didSignal != null) {
                zzz += zzsn.zzo(24, this.didSignal);
            }
            if (this.evtTime != null) {
                zzz += zzsn.zzd(25, this.evtTime.longValue());
            }
            if (this.didSignalType != null) {
                zzz += zzsn.zzC(26, this.didSignalType.intValue());
            }
            if (this.intSignal != null) {
                zzz += zzsn.zzo(27, this.intSignal);
            }
            if (this.didOptOut != null) {
                zzz += zzsn.zzf(28, this.didOptOut.booleanValue());
            }
            if (this.cerSignal != null) {
                zzz += zzsn.zzo(29, this.cerSignal);
            }
            if (this.didSignalAndroidAdId != null) {
                zzz += zzsn.zzo(30, this.didSignalAndroidAdId);
            }
            if (this.uwSignal != null) {
                zzz += zzsn.zzd(31, this.uwSignal.longValue());
            }
            if (this.uhSignal != null) {
                zzz += zzsn.zzd(32, this.uhSignal.longValue());
            }
            if (this.utzSignal != null) {
                zzz += zzsn.zzd(33, this.utzSignal.longValue());
            }
            if (this.vnmSignal != null) {
                zzz += zzsn.zzo(34, this.vnmSignal);
            }
            if (this.vcdSignal != null) {
                zzz += zzsn.zzd(35, this.vcdSignal.longValue());
            }
            if (this.tcpSignal != null) {
                zzz += zzsn.zzd(36, this.tcpSignal.longValue());
            }
            if (this.tcdSignal != null) {
                zzz += zzsn.zzd(37, this.tcdSignal.longValue());
            }
            if (this.adAttestationSignal != null) {
                zzz += zzsn.zzc(38, this.adAttestationSignal);
            }
            if (this.tcuSignal != null) {
                zzz += zzsn.zzd(39, this.tcuSignal.longValue());
            }
            if (this.tcmSignal != null) {
                zzz += zzsn.zzd(40, this.tcmSignal.longValue());
            }
            if (this.tcdnSignal != null) {
                zzz += zzsn.zzd(41, this.tcdnSignal.longValue());
            }
            if (this.tccSignal != null) {
                zzz += zzsn.zzd(42, this.tccSignal.longValue());
            }
            if (this.previousTouches != null && this.previousTouches.length > 0) {
                int i = zzz;
                for (zzsu com_google_android_gms_internal_zzsu : this.previousTouches) {
                    if (com_google_android_gms_internal_zzsu != null) {
                        i += zzsn.zzc(43, com_google_android_gms_internal_zzsu);
                    }
                }
                zzz = i;
            }
            return this.rawAdAttestationSignal != null ? zzz + zzsn.zzc(201, this.rawAdAttestationSignal) : zzz;
        }
    }

    public static final class AdAttestationSignal extends zzsu {
        private static volatile AdAttestationSignal[] zzaN;
        public Integer reasons;
        public Integer responseType;
        public Boolean suspicious;
        public Long timestampMs;

        public AdAttestationSignal() {
            clear();
        }

        public static AdAttestationSignal[] emptyArray() {
            if (zzaN == null) {
                synchronized (zzss.zzbut) {
                    if (zzaN == null) {
                        zzaN = new AdAttestationSignal[0];
                    }
                }
            }
            return zzaN;
        }

        public static AdAttestationSignal parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return new AdAttestationSignal().mergeFrom(com_google_android_gms_internal_zzsm);
        }

        public static AdAttestationSignal parseFrom(byte[] bArr) throws zzst {
            return (AdAttestationSignal) zzsu.mergeFrom(new AdAttestationSignal(), bArr);
        }

        public final AdAttestationSignal clear() {
            this.timestampMs = null;
            this.responseType = null;
            this.suspicious = null;
            this.reasons = null;
            this.zzbuu = -1;
            return this;
        }

        public final AdAttestationSignal mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.timestampMs = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 16:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
                            case 0:
                            case 1:
                            case 2:
                            case 3:
                                this.responseType = Integer.valueOf(zzIX);
                                break;
                            default:
                                continue;
                        }
                    case 24:
                        this.suspicious = Boolean.valueOf(com_google_android_gms_internal_zzsm.zzJc());
                        continue;
                    case 32:
                        zzIX = com_google_android_gms_internal_zzsm.zzJb();
                        switch (zzIX) {
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
                            case 27:
                            case 28:
                            case 29:
                            case 30:
                            case 31:
                                this.reasons = Integer.valueOf(zzIX);
                                break;
                            default:
                                continue;
                        }
                    default:
                        if (!zzsx.zzb(com_google_android_gms_internal_zzsm, zzIX)) {
                            break;
                        }
                        continue;
                }
                return this;
            }
        }

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.timestampMs != null) {
                com_google_android_gms_internal_zzsn.zzb(1, this.timestampMs.longValue());
            }
            if (this.responseType != null) {
                com_google_android_gms_internal_zzsn.zzA(2, this.responseType.intValue());
            }
            if (this.suspicious != null) {
                com_google_android_gms_internal_zzsn.zze(3, this.suspicious.booleanValue());
            }
            if (this.reasons != null) {
                com_google_android_gms_internal_zzsn.zzA(4, this.reasons.intValue());
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.timestampMs != null) {
                zzz += zzsn.zzd(1, this.timestampMs.longValue());
            }
            if (this.responseType != null) {
                zzz += zzsn.zzC(2, this.responseType.intValue());
            }
            if (this.suspicious != null) {
                zzz += zzsn.zzf(3, this.suspicious.booleanValue());
            }
            return this.reasons != null ? zzz + zzsn.zzC(4, this.reasons.intValue()) : zzz;
        }
    }

    public static final class AdSignalsContainer extends zzsu {
        private static volatile AdSignalsContainer[] zzaO;
        public byte[] encryptedDidSignal;
        public byte[] encryptedSpamSignals;

        public AdSignalsContainer() {
            clear();
        }

        public static AdSignalsContainer[] emptyArray() {
            if (zzaO == null) {
                synchronized (zzss.zzbut) {
                    if (zzaO == null) {
                        zzaO = new AdSignalsContainer[0];
                    }
                }
            }
            return zzaO;
        }

        public static AdSignalsContainer parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return new AdSignalsContainer().mergeFrom(com_google_android_gms_internal_zzsm);
        }

        public static AdSignalsContainer parseFrom(byte[] bArr) throws zzst {
            return (AdSignalsContainer) zzsu.mergeFrom(new AdSignalsContainer(), bArr);
        }

        public final AdSignalsContainer clear() {
            this.encryptedSpamSignals = null;
            this.encryptedDidSignal = null;
            this.zzbuu = -1;
            return this;
        }

        public final AdSignalsContainer mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.encryptedSpamSignals = com_google_android_gms_internal_zzsm.readBytes();
                        continue;
                    case 18:
                        this.encryptedDidSignal = com_google_android_gms_internal_zzsm.readBytes();
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

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.encryptedSpamSignals != null) {
                com_google_android_gms_internal_zzsn.zza(1, this.encryptedSpamSignals);
            }
            if (this.encryptedDidSignal != null) {
                com_google_android_gms_internal_zzsn.zza(2, this.encryptedDidSignal);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.encryptedSpamSignals != null) {
                zzz += zzsn.zzb(1, this.encryptedSpamSignals);
            }
            return this.encryptedDidSignal != null ? zzz + zzsn.zzb(2, this.encryptedDidSignal) : zzz;
        }
    }

    public interface DroidGuardResponseType {
        public static final int RESPONSE_TYPE_FALLBACK = 1;
        public static final int RESPONSE_TYPE_NORMAL = 0;
        public static final int RESPONSE_TYPE_OTHER = 3;
        public static final int RESPONSE_TYPE_PLAIN_TEXT = 2;
    }

    public interface DroidGuardSuspiciousReason {
        public static final int REASON_ANDROID_ID_MISMATCH = 26;
        public static final int REASON_BAD_SIGNAL_EVALUATION_ORDER = 24;
        public static final int REASON_BLACKLISTED_IMEI_HASH = 29;
        public static final int REASON_BLACKLISTED_IMSI_HASH = 30;
        public static final int REASON_BLACKLISTED_MAC_ADDRESS_HASH = 28;
        public static final int REASON_CONTENT_BINDING_FAILED = 7;
        public static final int REASON_CONTEXT_VERIFICATION_FAILED = 27;
        public static final int REASON_CORRUPTED_RESPONSE = 3;
        public static final int REASON_CTS_PROFILE_LOOKUP_FAILURE = 10;
        public static final int REASON_CTS_PROFILE_MISMATCH = 11;
        public static final int REASON_DEVICE_SIDE_ERROR = 8;
        public static final int REASON_DEVICE_SIDE_PARSE_ERROR = 14;
        public static final int REASON_DROIDGUASSO_VALIDATION_FAILED = 16;
        public static final int REASON_EMPTY_RESPONSE = 1;
        public static final int REASON_EMULATOR = 15;
        public static final int REASON_GMS_CORE_SIGNATURE_MISMATCH = 22;
        public static final int REASON_GOLDFISH = 12;
        public static final int REASON_INVALID_GMS_CORE_VERSION = 25;
        public static final int REASON_INVALID_TAG = 2;
        public static final int REASON_MISSING_ARM_CPU_CACHE = 9;
        public static final int REASON_MISSING_PROGRESS_REPORT = 6;
        public static final int REASON_MISSING_REPLAY_PROTECTION = 17;
        public static final int REASON_MISSING_RUNTIME_API = 20;
        public static final int REASON_REPLAY_PROTECTION = 4;
        public static final int REASON_ROOTED = 21;
        public static final int REASON_VIRTUALBOX = 13;
        public static final int REASON_VM_BINDING_FAILED = 18;
        public static final int REASON_VM_EXCEPTION = 5;
        public static final int REASON_WIDEVINE_LEVEL_MISMATCH = 23;
        public static final int REASON_WIDEVINE_VALIDATION_FAILED = 19;
        public static final int REASON_XPOSED_INSTALLED = 31;
    }

    public static final class RawAdAttestationSignal extends zzsu {
        private static volatile RawAdAttestationSignal[] zzaP;
        public byte[] encryptedAdAttestationStatemement;
        public String keyIdentifier;
        public Long timestampMs;

        public RawAdAttestationSignal() {
            clear();
        }

        public static RawAdAttestationSignal[] emptyArray() {
            if (zzaP == null) {
                synchronized (zzss.zzbut) {
                    if (zzaP == null) {
                        zzaP = new RawAdAttestationSignal[0];
                    }
                }
            }
            return zzaP;
        }

        public static RawAdAttestationSignal parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return new RawAdAttestationSignal().mergeFrom(com_google_android_gms_internal_zzsm);
        }

        public static RawAdAttestationSignal parseFrom(byte[] bArr) throws zzst {
            return (RawAdAttestationSignal) zzsu.mergeFrom(new RawAdAttestationSignal(), bArr);
        }

        public final RawAdAttestationSignal clear() {
            this.timestampMs = null;
            this.keyIdentifier = null;
            this.encryptedAdAttestationStatemement = null;
            this.zzbuu = -1;
            return this;
        }

        public final RawAdAttestationSignal mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 8:
                        this.timestampMs = Long.valueOf(com_google_android_gms_internal_zzsm.zzJa());
                        continue;
                    case 26:
                        this.keyIdentifier = com_google_android_gms_internal_zzsm.readString();
                        continue;
                    case 34:
                        this.encryptedAdAttestationStatemement = com_google_android_gms_internal_zzsm.readBytes();
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

        public final void writeTo(zzsn com_google_android_gms_internal_zzsn) throws IOException {
            if (this.timestampMs != null) {
                com_google_android_gms_internal_zzsn.zzb(1, this.timestampMs.longValue());
            }
            if (this.keyIdentifier != null) {
                com_google_android_gms_internal_zzsn.zzn(3, this.keyIdentifier);
            }
            if (this.encryptedAdAttestationStatemement != null) {
                com_google_android_gms_internal_zzsn.zza(4, this.encryptedAdAttestationStatemement);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        protected final int zzz() {
            int zzz = super.zzz();
            if (this.timestampMs != null) {
                zzz += zzsn.zzd(1, this.timestampMs.longValue());
            }
            if (this.keyIdentifier != null) {
                zzz += zzsn.zzo(3, this.keyIdentifier);
            }
            return this.encryptedAdAttestationStatemement != null ? zzz + zzsn.zzb(4, this.encryptedAdAttestationStatemement) : zzz;
        }
    }
}
