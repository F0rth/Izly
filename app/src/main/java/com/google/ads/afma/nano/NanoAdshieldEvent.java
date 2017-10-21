package com.google.ads.afma.nano;

import com.google.android.gms.internal.zzsm;
import com.google.android.gms.internal.zzsn;
import com.google.android.gms.internal.zzss;
import com.google.android.gms.internal.zzst;
import com.google.android.gms.internal.zzsu;
import com.google.android.gms.internal.zzsx;
import java.io.IOException;

public interface NanoAdshieldEvent {

    public static final class AdShieldEvent extends zzsu {
        private static volatile AdShieldEvent[] zzaK;
        public String appId;

        public AdShieldEvent() {
            clear();
        }

        public static AdShieldEvent[] emptyArray() {
            if (zzaK == null) {
                synchronized (zzss.zzbut) {
                    if (zzaK == null) {
                        zzaK = new AdShieldEvent[0];
                    }
                }
            }
            return zzaK;
        }

        public static AdShieldEvent parseFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            return new AdShieldEvent().mergeFrom(com_google_android_gms_internal_zzsm);
        }

        public static AdShieldEvent parseFrom(byte[] bArr) throws zzst {
            return (AdShieldEvent) zzsu.mergeFrom(new AdShieldEvent(), bArr);
        }

        public final AdShieldEvent clear() {
            this.appId = "";
            this.zzbuu = -1;
            return this;
        }

        public final AdShieldEvent mergeFrom(zzsm com_google_android_gms_internal_zzsm) throws IOException {
            while (true) {
                int zzIX = com_google_android_gms_internal_zzsm.zzIX();
                switch (zzIX) {
                    case 0:
                        break;
                    case 10:
                        this.appId = com_google_android_gms_internal_zzsm.readString();
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
            if (!this.appId.equals("")) {
                com_google_android_gms_internal_zzsn.zzn(1, this.appId);
            }
            super.writeTo(com_google_android_gms_internal_zzsn);
        }

        protected final int zzz() {
            int zzz = super.zzz();
            return !this.appId.equals("") ? zzz + zzsn.zzo(1, this.appId) : zzz;
        }
    }
}
