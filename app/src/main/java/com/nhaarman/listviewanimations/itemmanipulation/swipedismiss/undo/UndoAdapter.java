package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

public interface UndoAdapter {
    @NonNull
    View getUndoClickView(@NonNull View view);

    @NonNull
    View getUndoView(int i, @Nullable View view, @NonNull ViewGroup viewGroup);
}
