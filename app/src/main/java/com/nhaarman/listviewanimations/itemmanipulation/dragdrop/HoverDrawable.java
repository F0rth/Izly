package com.nhaarman.listviewanimations.itemmanipulation.dragdrop;

import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.view.MotionEvent;
import android.view.View;

class HoverDrawable extends BitmapDrawable {
    private float mDownY;
    private float mOriginalY;
    private float mScrollDistance;

    HoverDrawable(@NonNull View view, float f) {
        super(view.getResources(), BitmapUtils.getBitmapFromView(view));
        this.mOriginalY = (float) view.getTop();
        this.mDownY = f;
        setBounds(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
    }

    HoverDrawable(@NonNull View view, @NonNull MotionEvent motionEvent) {
        this(view, motionEvent.getY());
    }

    int getDeltaY() {
        return (int) (((float) getBounds().top) - this.mOriginalY);
    }

    int getTop() {
        return getBounds().top;
    }

    void handleMoveEvent(@NonNull MotionEvent motionEvent) {
        setTop((int) (((this.mOriginalY - this.mDownY) + motionEvent.getY()) + this.mScrollDistance));
    }

    boolean isMovingUpwards() {
        return this.mOriginalY > ((float) getBounds().top);
    }

    void onScroll(float f) {
        this.mScrollDistance += this.mOriginalY - f;
        this.mOriginalY = f;
    }

    void setTop(int i) {
        setBounds(getBounds().left, i, getBounds().left + getIntrinsicWidth(), getIntrinsicHeight() + i);
    }

    void shift(int i) {
        if (isMovingUpwards()) {
            i = -i;
        }
        this.mOriginalY += (float) i;
        this.mDownY += (float) i;
    }
}
