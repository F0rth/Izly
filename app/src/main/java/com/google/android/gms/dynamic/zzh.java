package com.google.android.gms.dynamic;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.dynamic.zzc.zza;

public final class zzh extends zza {
    private Fragment zzalg;

    private zzh(Fragment fragment) {
        this.zzalg = fragment;
    }

    public static zzh zza(Fragment fragment) {
        return fragment != null ? new zzh(fragment) : null;
    }

    public final Bundle getArguments() {
        return this.zzalg.getArguments();
    }

    public final int getId() {
        return this.zzalg.getId();
    }

    public final boolean getRetainInstance() {
        return this.zzalg.getRetainInstance();
    }

    public final String getTag() {
        return this.zzalg.getTag();
    }

    public final int getTargetRequestCode() {
        return this.zzalg.getTargetRequestCode();
    }

    public final boolean getUserVisibleHint() {
        return this.zzalg.getUserVisibleHint();
    }

    public final zzd getView() {
        return zze.zzC(this.zzalg.getView());
    }

    public final boolean isAdded() {
        return this.zzalg.isAdded();
    }

    public final boolean isDetached() {
        return this.zzalg.isDetached();
    }

    public final boolean isHidden() {
        return this.zzalg.isHidden();
    }

    public final boolean isInLayout() {
        return this.zzalg.isInLayout();
    }

    public final boolean isRemoving() {
        return this.zzalg.isRemoving();
    }

    public final boolean isResumed() {
        return this.zzalg.isResumed();
    }

    public final boolean isVisible() {
        return this.zzalg.isVisible();
    }

    public final void setHasOptionsMenu(boolean z) {
        this.zzalg.setHasOptionsMenu(z);
    }

    public final void setMenuVisibility(boolean z) {
        this.zzalg.setMenuVisibility(z);
    }

    public final void setRetainInstance(boolean z) {
        this.zzalg.setRetainInstance(z);
    }

    public final void setUserVisibleHint(boolean z) {
        this.zzalg.setUserVisibleHint(z);
    }

    public final void startActivity(Intent intent) {
        this.zzalg.startActivity(intent);
    }

    public final void startActivityForResult(Intent intent, int i) {
        this.zzalg.startActivityForResult(intent, i);
    }

    public final void zzn(zzd com_google_android_gms_dynamic_zzd) {
        this.zzalg.registerForContextMenu((View) zze.zzp(com_google_android_gms_dynamic_zzd));
    }

    public final void zzo(zzd com_google_android_gms_dynamic_zzd) {
        this.zzalg.unregisterForContextMenu((View) zze.zzp(com_google_android_gms_dynamic_zzd));
    }

    public final zzd zztV() {
        return zze.zzC(this.zzalg.getActivity());
    }

    public final zzc zztW() {
        return zza(this.zzalg.getParentFragment());
    }

    public final zzd zztX() {
        return zze.zzC(this.zzalg.getResources());
    }

    public final zzc zztY() {
        return zza(this.zzalg.getTargetFragment());
    }
}
