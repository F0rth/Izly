package com.nhaarman.listviewanimations.itemmanipulation;

import android.support.annotation.NonNull;
import android.view.MotionEvent;

public interface TouchEventHandler {
    boolean isInteracting();

    boolean onTouchEvent(@NonNull MotionEvent motionEvent);
}
