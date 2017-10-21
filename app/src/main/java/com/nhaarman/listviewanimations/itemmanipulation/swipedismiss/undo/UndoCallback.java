package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.support.annotation.NonNull;
import android.view.View;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;

public interface UndoCallback extends OnDismissCallback {
    @NonNull
    View getPrimaryView(@NonNull View view);

    @NonNull
    View getUndoView(@NonNull View view);

    void onDismiss(@NonNull View view, int i);

    void onUndo(@NonNull View view, int i);

    void onUndoShown(@NonNull View view, int i);
}
