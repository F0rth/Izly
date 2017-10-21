package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss;

import android.support.annotation.NonNull;
import android.view.ViewGroup;

public interface OnDismissCallback {
    void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] iArr);
}
