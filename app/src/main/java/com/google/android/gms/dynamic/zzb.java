package com.google.android.gms.dynamic;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

@SuppressLint({"NewApi"})
public final class zzb extends zza {
    private Fragment zzavH;

    private zzb(Fragment fragment) {
        this.zzavH = fragment;
    }

    public static zzb zza(Fragment fragment) {
        return fragment != null ? new zzb(fragment) : null;
    }

    public final Bundle getArguments() {
        return this.zzavH.getArguments();
    }

    public final int getId() {
        return this.zzavH.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzavH.getRetainInstance();
    }

    public final String getTag() {
        return this.zzavH.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzavH.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzavH.getUserVisibleHint();
    }

    public final zzd getView() {
        return zze.zzC(this.zzavH.getView());
    }

    public final boolean isAdded() {
        return this.zzavH.isAdded();
    }

    public final boolean isDetached() {
        return this.zzavH.isDetached();
    }

    public final boolean isHidden() {
        return this.zzavH.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzavH.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzavH.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzavH.isResumed();
    }

    public final boolean isVisible() {
        return this.zzavH.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzavH.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzavH.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzavH.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzavH.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzavH.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzavH.startActivityForResult(intent, i);
    }

    public final void zzn(zzd com_google_android_gms_dynamic_zzd) {
        this.zzavH.registerForContextMenu((View) zze.zzp(com_google_android_gms_dynamic_zzd));
    }

    public final void zzo(zzd com_google_android_gms_dynamic_zzd) {
        this.zzavH.unregisterForContextMenu((View) zze.zzp(com_google_android_gms_dynamic_zzd));
    }

    public final zzd zztV() {
        return zze.zzC(this.zzavH.getActivity());
    }

    public final zzc zztW() {
        return zza(this.zzavH.getParentFragment());
    }

    public final zzd zztX() {
        return zze.zzC(this.zzavH.getResources());
    }

    public final zzc zztY() {
        return zza(this.zzavH.getTargetFragment());
    }
}
