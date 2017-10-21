package com.ad4screen.sdk.service.modules.j;

import android.content.Context;
import android.os.Bundle;

public class a extends b {
    public a(Context context, boolean z) {
        super(context, a(z));
    }

    private static Bundle a(boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt("doNotTrack", z ? 1 : 0);
        return bundle;
    }
}
