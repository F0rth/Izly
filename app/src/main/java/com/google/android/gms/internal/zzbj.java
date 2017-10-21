package com.google.android.gms.internal;

import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.fasterxml.jackson.core.util.MinimalPrettyPrinter;
import java.security.MessageDigest;

@zzhb
public class zzbj extends zzbg {
    private MessageDigest zztw;

    byte[] zza(String[] strArr) {
        byte[] bArr = new byte[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            bArr[i] = zzk(zzbi.zzx(strArr[i]));
        }
        return bArr;
    }

    byte zzk(int i) {
        return (byte) ((((i & 255) ^ ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >> 8)) ^ ((16711680 & i) >> 16)) ^ ((ViewCompat.MEASURED_STATE_MASK & i) >> 24));
    }

    public byte[] zzu(String str) {
        int i = 4;
        byte[] zza = zza(str.split(MinimalPrettyPrinter.DEFAULT_ROOT_VALUE_SEPARATOR));
        this.zztw = zzcL();
        synchronized (this.zzpV) {
            if (this.zztw == null) {
                return new byte[0];
            }
            this.zztw.reset();
            this.zztw.update(zza);
            Object digest = this.zztw.digest();
            if (digest.length <= 4) {
                i = digest.length;
            }
            Object obj = new byte[i];
            System.arraycopy(digest, 0, obj, 0, obj.length);
            return obj;
        }
    }
}
