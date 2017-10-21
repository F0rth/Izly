package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.support.annotation.NonNull;
import android.view.View;

public interface DraggableManager {
    boolean isDraggable(@NonNull View view, int i, float f, float f2);
}
