package com.ad4screen.sdk.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.os.Build.VERSION;
import android.os.Bundle;

import com.ad4screen.sdk.contract.A4SContract.Beacons;
import com.ad4screen.sdk.plugins.model.Beacon;

import java.util.ArrayList;
import java.util.List;

public class A4SBeaconResolver extends b {
    protected ContentResolver mResolver;

    public A4SBeaconResolver(Context context) {
        super(new d(context), context);
        this.mResolver = context.getContentResolver();
    }

    public void updateBeacons(List<Beacon> list) {
        if (VERSION.SDK_INT >= 11) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("beacons", (ArrayList) list);
            this.mResolver.call(Beacons.getContentUri(this.mContext), "updateBeacons", null, bundle);
            return;
        }
        super.updateBeacons(list);
    }
}
