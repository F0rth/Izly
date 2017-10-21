package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.support.annotation.NonNull;
import android.view.View;

public class TouchViewDraggableManager implements DraggableManager {
    private final int mTouchViewResId;

    public TouchViewDraggableManager(int i) {
        this.mTouchViewResId = i;
    }

    public boolean isDraggable(@NonNull View view, int i, float f, float f2) {
        View findViewById = view.findViewById(this.mTouchViewResId);
        if (findViewById == null) {
            return false;
        }
        boolean z = ((float) findViewById.getLeft()) <= f && ((float) findViewById.getRight()) >= f;
        boolean z2 = ((float) findViewById.getTop()) <= f2 && ((float) findViewById.getBottom()) >= f2;
        return z && z2;
    }
}
