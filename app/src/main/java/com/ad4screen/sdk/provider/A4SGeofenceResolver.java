package com.ad4screen.sdk.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;

import com.ad4screen.sdk.contract.A4SContract.Geofences;
import com.ad4screen.sdk.plugins.model.Geofence;

import java.util.ArrayList;
import java.util.List;

public class A4SGeofenceResolver extends e {
    protected ContentResolver mResolver;

    public A4SGeofenceResolver(Context context) {
        super(new d(context), context);
        this.mResolver = context.getContentResolver();
    }

    public void updateGeofences(List<Geofence> list) {
        if (VERSION.SDK_INT >= 11) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("geofences", (ArrayList) list);
            this.mResolver.call(Geofences.getContentUri(this.mContext), "updateGeofences", null, bundle);
            return;
        }
        super.updateGeofences(list);
    }
}
