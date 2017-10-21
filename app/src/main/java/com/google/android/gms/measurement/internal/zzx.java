package com.google.android.gms.measurement.internal;

import android.os.Binder;
import android.os.Process;
import android.support.annotation.BinderThread;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import com.google.android.gms.common.zze;
import com.google.android.gms.measurement.internal.zzm.zza;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class zzx extends zza {
    private final zzw zzaTV;
    private final boolean zzaYw;

    public zzx(zzw com_google_android_gms_measurement_internal_zzw) {
        com.google.android.gms.common.internal.zzx.zzz(com_google_android_gms_measurement_internal_zzw);
        this.zzaTV = com_google_android_gms_measurement_internal_zzw;
        this.zzaYw = false;
    }

    public zzx(zzw com_google_android_gms_measurement_internal_zzw, boolean z) {
        com.google.android.gms.common.internal.zzx.zzz(com_google_android_gms_measurement_internal_zzw);
        this.zzaTV = com_google_android_gms_measurement_internal_zzw;
        this.zzaYw = z;
    }

    @BinderThread
    private void zzfm(String str) throws SecurityException {
        if (TextUtils.isEmpty(str)) {
            this.zzaTV.zzAo().zzCE().zzfg("Measurement Service called without app package");
            throw new SecurityException("Measurement Service called without app package");
        }
        try {
            zzfn(str);
        } catch (SecurityException e) {
            this.zzaTV.zzAo().zzCE().zzj("Measurement Service called with invalid calling package", str);
            throw e;
        }
    }

    private void zzfn(String str) throws SecurityException {
        int myUid = this.zzaYw ? Process.myUid() : Binder.getCallingUid();
        if (!zze.zzb(this.zzaTV.getContext(), myUid, str)) {
            if (!zze.zzf(this.zzaTV.getContext(), myUid) || this.zzaTV.zzCZ()) {
                throw new SecurityException(String.format("Unknown calling package name '%s'.", new Object[]{str}));
            }
        }
    }

    @BinderThread
    public List<UserAttributeParcel> zza(final AppMetadata appMetadata, boolean z) {
        Object e;
        com.google.android.gms.common.internal.zzx.zzz(appMetadata);
        zzfm(appMetadata.packageName);
        try {
            List<zzai> list = (List) this.zzaTV.zzCn().zzd(new Callable<List<zzai>>(this) {
                final /* synthetic */ zzx zzaYy;

                public /* synthetic */ Object call() throws Exception {
                    return zzDh();
                }

                public List<zzai> zzDh() throws Exception {
                    return this.zzaYy.zzaTV.zzCj().zzeX(appMetadata.zzaVt);
                }
            }).get();
            List<UserAttributeParcel> arrayList = new ArrayList(list.size());
            for (zzai com_google_android_gms_measurement_internal_zzai : list) {
                if (z || !zzaj.zzfv(com_google_android_gms_measurement_internal_zzai.mName)) {
                    arrayList.add(new UserAttributeParcel(com_google_android_gms_measurement_internal_zzai));
                }
            }
            return arrayList;
        } catch (InterruptedException e2) {
            e = e2;
            this.zzaTV.zzAo().zzCE().zzj("Failed to get user attributes", e);
            return null;
        } catch (ExecutionException e3) {
            e = e3;
            this.zzaTV.zzAo().zzCE().zzj("Failed to get user attributes", e);
            return null;
        }
    }

    @BinderThread
    public void zza(final AppMetadata appMetadata) {
        com.google.android.gms.common.internal.zzx.zzz(appMetadata);
        zzfm(appMetadata.packageName);
        this.zzaTV.zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzx zzaYy;

            public void run() {
                this.zzaYy.zzfl(appMetadata.zzaVx);
                this.zzaYy.zzaTV.zzd(appMetadata);
            }
        });
    }

    @BinderThread
    public void zza(final EventParcel eventParcel, final AppMetadata appMetadata) {
        com.google.android.gms.common.internal.zzx.zzz(eventParcel);
        com.google.android.gms.common.internal.zzx.zzz(appMetadata);
        zzfm(appMetadata.packageName);
        this.zzaTV.zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzx zzaYy;

            public void run() {
                this.zzaYy.zzfl(appMetadata.zzaVx);
                this.zzaYy.zzaTV.zzb(eventParcel, appMetadata);
            }
        });
    }

    @BinderThread
    public void zza(final EventParcel eventParcel, final String str, final String str2) {
        com.google.android.gms.common.internal.zzx.zzz(eventParcel);
        com.google.android.gms.common.internal.zzx.zzcM(str);
        zzfm(str);
        this.zzaTV.zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzx zzaYy;

            public void run() {
                this.zzaYy.zzfl(str2);
                this.zzaYy.zzaTV.zza(eventParcel, str);
            }
        });
    }

    @BinderThread
    public void zza(final UserAttributeParcel userAttributeParcel, final AppMetadata appMetadata) {
        com.google.android.gms.common.internal.zzx.zzz(userAttributeParcel);
        com.google.android.gms.common.internal.zzx.zzz(appMetadata);
        zzfm(appMetadata.packageName);
        if (userAttributeParcel.getValue() == null) {
            this.zzaTV.zzCn().zzg(new Runnable(this) {
                final /* synthetic */ zzx zzaYy;

                public void run() {
                    this.zzaYy.zzfl(appMetadata.zzaVx);
                    this.zzaYy.zzaTV.zzc(userAttributeParcel, appMetadata);
                }
            });
        } else {
            this.zzaTV.zzCn().zzg(new Runnable(this) {
                final /* synthetic */ zzx zzaYy;

                public void run() {
                    this.zzaYy.zzfl(appMetadata.zzaVx);
                    this.zzaYy.zzaTV.zzb(userAttributeParcel, appMetadata);
                }
            });
        }
    }

    @BinderThread
    public void zzb(final AppMetadata appMetadata) {
        com.google.android.gms.common.internal.zzx.zzz(appMetadata);
        zzfm(appMetadata.packageName);
        this.zzaTV.zzCn().zzg(new Runnable(this) {
            final /* synthetic */ zzx zzaYy;

            public void run() {
                this.zzaYy.zzfl(appMetadata.zzaVx);
                this.zzaYy.zzaTV.zzc(appMetadata);
            }
        });
    }

    @WorkerThread
    void zzfl(String str) {
        if (!TextUtils.isEmpty(str)) {
            String[] split = str.split(":", 2);
            if (split.length == 2) {
                try {
                    long longValue = Long.valueOf(split[0]).longValue();
                    if (longValue > 0) {
                        this.zzaTV.zzCo().zzaXi.zzf(split[1], longValue);
                    } else {
                        this.zzaTV.zzAo().zzCF().zzj("Combining sample with a non-positive weight", Long.valueOf(longValue));
                    }
                } catch (NumberFormatException e) {
                    this.zzaTV.zzAo().zzCF().zzj("Combining sample with a non-number weight", split[0]);
                }
            }
        }
    }
}
