package com.google.android.gms.common.api.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.common.internal.zzx;

public final class zzq<L> {
    private volatile L mListener;
    private final zza zzaiw;

    public interface zzb<L> {
        void zzpr();

        void zzt(L l);
    }

    final class zza extends Handler {
        final /* synthetic */ zzq zzaix;

        public zza(zzq com_google_android_gms_common_api_internal_zzq, Looper looper) {
            this.zzaix = com_google_android_gms_common_api_internal_zzq;
            super(looper);
        }

        public final void handleMessage(Message message) {
            boolean z = true;
            if (message.what != 1) {
                z = false;
            }
            zzx.zzac(z);
            this.zzaix.zzb((zzb) message.obj);
        }
    }

    zzq(Looper looper, L l) {
        this.zzaiw = new zza(this, looper);
        this.mListener = zzx.zzb((Object) l, (Object) "Listener must not be null");
    }

    public final void clear() {
        this.mListener = null;
    }

    public final void zza(zzb<? super L> com_google_android_gms_common_api_internal_zzq_zzb__super_L) {
        zzx.zzb((Object) com_google_android_gms_common_api_internal_zzq_zzb__super_L, (Object) "Notifier must not be null");
        this.zzaiw.sendMessage(this.zzaiw.obtainMessage(1, com_google_android_gms_common_api_internal_zzq_zzb__super_L));
    }

    final void zzb(zzb<? super L> com_google_android_gms_common_api_internal_zzq_zzb__super_L) {
        Object obj = this.mListener;
        if (obj == null) {
            com_google_android_gms_common_api_internal_zzq_zzb__super_L.zzpr();
            return;
        }
        try {
            com_google_android_gms_common_api_internal_zzq_zzb__super_L.zzt(obj);
        } catch (RuntimeException e) {
            com_google_android_gms_common_api_internal_zzq_zzb__super_L.zzpr();
            throw e;
        }
    }
}
