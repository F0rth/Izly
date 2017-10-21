package com.ad4screen.sdk;

import android.content.Context;

import com.ad4screen.sdk.common.annotations.API;

@API
public interface A4SIdsProvider {
    String getPartnerId(Context context);

    String getPrivateKey(Context context);
}
