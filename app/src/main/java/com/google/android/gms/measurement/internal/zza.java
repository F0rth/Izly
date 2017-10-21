package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.zzx;

class zza {
    private String zzSF;
    private final zzw zzaTV;
    private final String zzaUa;
    private String zzaVc;
    private String zzaVd;
    private String zzaVe;
    private long zzaVf;
    private long zzaVg;
    private long zzaVh;
    private String zzaVi;
    private long zzaVj;
    private long zzaVk;
    private boolean zzaVl;
    private long zzaVm;
    private long zzaVn;
    private long zzaVo;
    private long zzaVp;
    private boolean zzaVq;
    private long zzaVr;
    private long zzaVs;

    zza(zzw com_google_android_gms_measurement_internal_zzw, String str) {
        zzx.zzz(com_google_android_gms_measurement_internal_zzw);
        zzx.zzcM(str);
        this.zzaTV = com_google_android_gms_measurement_internal_zzw;
        this.zzaUa = str;
        this.zzaTV.zzjk();
    }

    public void setAppVersion(String str) {
        this.zzaTV.zzjk();
        this.zzaVq |= zzaj.zzQ(this.zzSF, str);
        this.zzSF = str;
    }

    public void setMeasurementEnabled(boolean z) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVl != z ? 1 : 0) | this.zzaVq;
        this.zzaVl = z;
    }

    public boolean zzAr() {
        this.zzaTV.zzjk();
        return this.zzaVl;
    }

    public void zzBi() {
        this.zzaTV.zzjk();
        this.zzaVq = false;
    }

    public String zzBj() {
        this.zzaTV.zzjk();
        return this.zzaVc;
    }

    public String zzBk() {
        this.zzaTV.zzjk();
        return this.zzaVd;
    }

    public String zzBl() {
        this.zzaTV.zzjk();
        return this.zzaVe;
    }

    public long zzBm() {
        this.zzaTV.zzjk();
        return this.zzaVg;
    }

    public long zzBn() {
        this.zzaTV.zzjk();
        return this.zzaVh;
    }

    public String zzBo() {
        this.zzaTV.zzjk();
        return this.zzaVi;
    }

    public long zzBp() {
        this.zzaTV.zzjk();
        return this.zzaVj;
    }

    public long zzBq() {
        this.zzaTV.zzjk();
        return this.zzaVk;
    }

    public long zzBr() {
        this.zzaTV.zzjk();
        return this.zzaVf;
    }

    public long zzBs() {
        this.zzaTV.zzjk();
        return this.zzaVr;
    }

    public long zzBt() {
        this.zzaTV.zzjk();
        return this.zzaVs;
    }

    public void zzBu() {
        this.zzaTV.zzjk();
        long j = this.zzaVf + 1;
        if (j > 2147483647L) {
            this.zzaTV.zzAo().zzCF().zzfg("Bundle index overflow");
            j = 0;
        }
        this.zzaVq = true;
        this.zzaVf = j;
    }

    public long zzBv() {
        this.zzaTV.zzjk();
        return this.zzaVm;
    }

    public long zzBw() {
        this.zzaTV.zzjk();
        return this.zzaVn;
    }

    public long zzBx() {
        this.zzaTV.zzjk();
        return this.zzaVo;
    }

    public long zzBy() {
        this.zzaTV.zzjk();
        return this.zzaVp;
    }

    public void zzO(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVg != j ? 1 : 0) | this.zzaVq;
        this.zzaVg = j;
    }

    public void zzP(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVh != j ? 1 : 0) | this.zzaVq;
        this.zzaVh = j;
    }

    public void zzQ(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVj != j ? 1 : 0) | this.zzaVq;
        this.zzaVj = j;
    }

    public void zzR(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVk != j ? 1 : 0) | this.zzaVq;
        this.zzaVk = j;
    }

    public void zzS(long j) {
        int i = 0;
        zzx.zzac(j >= 0);
        this.zzaTV.zzjk();
        boolean z = this.zzaVq;
        if (this.zzaVf != j) {
            i = 1;
        }
        this.zzaVq = z | i;
        this.zzaVf = j;
    }

    public void zzT(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVr != j ? 1 : 0) | this.zzaVq;
        this.zzaVr = j;
    }

    public void zzU(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVs != j ? 1 : 0) | this.zzaVq;
        this.zzaVs = j;
    }

    public void zzV(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVm != j ? 1 : 0) | this.zzaVq;
        this.zzaVm = j;
    }

    public void zzW(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVn != j ? 1 : 0) | this.zzaVq;
        this.zzaVn = j;
    }

    public void zzX(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVo != j ? 1 : 0) | this.zzaVq;
        this.zzaVo = j;
    }

    public void zzY(long j) {
        this.zzaTV.zzjk();
        this.zzaVq = (this.zzaVp != j ? 1 : 0) | this.zzaVq;
        this.zzaVp = j;
    }

    public void zzeM(String str) {
        this.zzaTV.zzjk();
        this.zzaVq |= zzaj.zzQ(this.zzaVc, str);
        this.zzaVc = str;
    }

    public void zzeN(String str) {
        this.zzaTV.zzjk();
        if (TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzaVq |= zzaj.zzQ(this.zzaVd, str);
        this.zzaVd = str;
    }

    public void zzeO(String str) {
        this.zzaTV.zzjk();
        this.zzaVq |= zzaj.zzQ(this.zzaVe, str);
        this.zzaVe = str;
    }

    public void zzeP(String str) {
        this.zzaTV.zzjk();
        this.zzaVq |= zzaj.zzQ(this.zzaVi, str);
        this.zzaVi = str;
    }

    public String zzli() {
        this.zzaTV.zzjk();
        return this.zzSF;
    }

    public String zzwK() {
        this.zzaTV.zzjk();
        return this.zzaUa;
    }
}
