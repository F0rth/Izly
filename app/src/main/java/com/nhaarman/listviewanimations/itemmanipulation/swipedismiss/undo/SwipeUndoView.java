package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;

class SwipeUndoView extends FrameLayout {
    @Nullable
    private View mPrimaryView;
    @Nullable
    private View mUndoView;

    SwipeUndoView(Context context) {
        super(context);
    }

    @Nullable
    View getPrimaryView() {
        return this.mPrimaryView;
    }

    @Nullable
    View getUndoView() {
        return this.mUndoView;
    }

    void setPrimaryView(@NonNull View view) {
        if (this.mPrimaryView != null) {
            removeView(this.mPrimaryView);
        }
        this.mPrimaryView = view;
        addView(this.mPrimaryView);
    }

    void setUndoView(@NonNull View view) {
        if (this.mUndoView != null) {
            removeView(this.mUndoView);
        }
        this.mUndoView = view;
        this.mUndoView.setVisibility(8);
        addView(this.mUndoView);
    }
}
