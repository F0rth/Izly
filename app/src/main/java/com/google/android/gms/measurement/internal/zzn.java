package com.google.android.gms.measurement.internal;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzmq;
import com.google.android.gms.measurement.zza;
import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import javax.security.auth.x500.X500Principal;

public class zzn extends zzz {
    private static final X500Principal zzaWz = new X500Principal("CN=Android Debug,O=Android,C=US");
    private String zzSE;
    private String zzSF;
    private String zzaUa;
    private String zzaVd;
    private String zzaVi;
    private long zzaWA;

    zzn(zzw com_google_android_gms_measurement_internal_zzw) {
        super(com_google_android_gms_measurement_internal_zzw);
    }

    public /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    public /* bridge */ /* synthetic */ zzp zzAo() {
        return super.zzAo();
    }

    String zzBk() {
        zzjv();
        return this.zzaVd;
    }

    String zzBo() {
        zzjv();
        return this.zzaVi;
    }

    long zzBp() {
        return zzCp().zzBp();
    }

    long zzBq() {
        zzjv();
        return this.zzaWA;
    }

    boolean zzCD() {
        try {
            PackageInfo packageInfo = getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 64);
            if (!(packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length <= 0)) {
                return ((X509Certificate) CertificateFactory.getInstance("X.509").generateCertificate(new ByteArrayInputStream(packageInfo.signatures[0].toByteArray()))).getSubjectX500Principal().equals(zzaWz);
            }
        } catch (CertificateException e) {
            zzAo().zzCE().zzj("Error obtaining certificate", e);
        } catch (NameNotFoundException e2) {
            zzAo().zzCE().zzj("Package name not found", e2);
        }
        return true;
    }

    public /* bridge */ /* synthetic */ void zzCd() {
        super.zzCd();
    }

    public /* bridge */ /* synthetic */ zzc zzCe() {
        return super.zzCe();
    }

    public /* bridge */ /* synthetic */ zzab zzCf() {
        return super.zzCf();
    }

    public /* bridge */ /* synthetic */ zzn zzCg() {
        return super.zzCg();
    }

    public /* bridge */ /* synthetic */ zzg zzCh() {
        return super.zzCh();
    }

    public /* bridge */ /* synthetic */ zzac zzCi() {
        return super.zzCi();
    }

    public /* bridge */ /* synthetic */ zze zzCj() {
        return super.zzCj();
    }

    public /* bridge */ /* synthetic */ zzaj zzCk() {
        return super.zzCk();
    }

    public /* bridge */ /* synthetic */ zzu zzCl() {
        return super.zzCl();
    }

    public /* bridge */ /* synthetic */ zzad zzCm() {
        return super.zzCm();
    }

    public /* bridge */ /* synthetic */ zzv zzCn() {
        return super.zzCn();
    }

    public /* bridge */ /* synthetic */ zzt zzCo() {
        return super.zzCo();
    }

    public /* bridge */ /* synthetic */ zzd zzCp() {
        return super.zzCp();
    }

    protected void zzba(Status status) {
        if (status == null) {
            zzAo().zzCE().zzfg("GoogleService failed to initialize (no status)");
        } else {
            zzAo().zzCE().zze("GoogleService failed to initialize, status", Integer.valueOf(status.getStatusCode()), status.getStatusMessage());
        }
    }

    AppMetadata zzfe(String str) {
        return new AppMetadata(zzwK(), zzBk(), zzli(), zzBo(), zzBp(), zzBq(), str, zzCo().zzAr(), !zzCo().zzaXx);
    }

    protected void zziJ() {
        String str;
        String str2 = "Unknown";
        String str3 = "Unknown";
        PackageManager packageManager = getContext().getPackageManager();
        String packageName = getContext().getPackageName();
        String installerPackageName = packageManager.getInstallerPackageName(packageName);
        if (installerPackageName == null) {
            installerPackageName = "manual_install";
        } else if ("com.android.vending".equals(installerPackageName)) {
            installerPackageName = "";
        }
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getContext().getPackageName(), 0);
            if (packageInfo != null) {
                CharSequence applicationLabel = packageManager.getApplicationLabel(packageInfo.applicationInfo);
                if (!TextUtils.isEmpty(applicationLabel)) {
                    str3 = applicationLabel.toString();
                }
                str2 = str3;
                str3 = packageInfo.versionName;
            } else {
                str = str2;
                str2 = str3;
                str3 = str;
            }
        } catch (NameNotFoundException e) {
            zzAo().zzCE().zzj("Error retrieving package info: appName", str3);
            str = str2;
            str2 = str3;
            str3 = str;
        }
        this.zzaUa = packageName;
        this.zzaVi = installerPackageName;
        this.zzSF = str3;
        this.zzSE = str2;
        MessageDigest zzbv = zzaj.zzbv("MD5");
        if (zzbv == null) {
            zzAo().zzCE().zzfg("Could not get MD5 instance");
            this.zzaWA = -1;
        } else {
            this.zzaWA = 0;
            try {
                if (!zzCD()) {
                    PackageInfo packageInfo2 = packageManager.getPackageInfo(getContext().getPackageName(), 64);
                    if (packageInfo2.signatures != null && packageInfo2.signatures.length > 0) {
                        this.zzaWA = zzaj.zzq(zzbv.digest(packageInfo2.signatures[0].toByteArray()));
                    }
                }
            } catch (NameNotFoundException e2) {
                zzAo().zzCE().zzj("Package name not found", e2);
            }
        }
        Status zzb = zzCp().zzkr() ? zza.zzb(getContext(), "-", true) : zza.zzaR(getContext());
        boolean z = zzb != null && zzb.isSuccess();
        if (!z) {
            zzba(zzb);
        }
        if (z) {
            z = zza.zzAr();
            if (z) {
                zzAo().zzCK().zzfg("AppMeasurement enabled");
            } else {
                zzAo().zzCI().zzfg("AppMeasurement disabled with google_app_measurement_enable=0");
            }
        } else {
            z = false;
        }
        this.zzaVd = "";
        if (!zzCp().zzkr()) {
            try {
                str3 = zza.zzAp();
                if (TextUtils.isEmpty(str3)) {
                    str3 = "";
                }
                this.zzaVd = str3;
                if (z) {
                    zzAo().zzCK().zze("App package, google app id", this.zzaUa, this.zzaVd);
                }
            } catch (IllegalStateException e3) {
                zzAo().zzCE().zzj("getGoogleAppId or isMeasurementEnabled failed with exception", e3);
            }
        }
    }

    public /* bridge */ /* synthetic */ void zzjj() {
        super.zzjj();
    }

    public /* bridge */ /* synthetic */ void zzjk() {
        super.zzjk();
    }

    public /* bridge */ /* synthetic */ zzmq zzjl() {
        return super.zzjl();
    }

    String zzli() {
        zzjv();
        return this.zzSF;
    }

    String zzwK() {
        zzjv();
        return this.zzaUa;
    }
}
