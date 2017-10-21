package com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.undo;

import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import java.util.HashMap;
import java.util.Map;

public class TimedUndoAdapter extends SimpleSwipeUndoAdapter {
    public static final long DEFAULT_TIMEOUT_MS = 3000;
    @NonNull
    private final Handler mHandler = new Handler();
    private final Map<Integer, TimeoutRunnable> mRunnables = new HashMap();
    private long mTimeoutMs = DEFAULT_TIMEOUT_MS;

    class TimeoutRunnable implements Runnable {
        private int mPosition;

        TimeoutRunnable(int i) {
            this.mPosition = i;
        }

        public int getPosition() {
            return this.mPosition;
        }

        public void run() {
            TimedUndoAdapter.this.dismiss(this.mPosition);
        }

        public void setPosition(int i) {
            this.mPosition = i;
        }
    }

    public <V extends BaseAdapter & UndoAdapter> TimedUndoAdapter(@NonNull V v, @NonNull Context context, @NonNull OnDismissCallback onDismissCallback) {
        super(v, context, onDismissCallback);
    }

    private void cancelCallback(int i) {
        Runnable runnable = (Runnable) this.mRunnables.get(Integer.valueOf(i));
        if (runnable != null) {
            this.mHandler.removeCallbacks(runnable);
            this.mRunnables.remove(Integer.valueOf(i));
        }
    }

    public void dismiss(int i) {
        super.dismiss(i);
        cancelCallback(i);
    }

    public void onDismiss(@NonNull View view, int i) {
        super.onDismiss(view, i);
        cancelCallback(i);
    }

    public void onDismiss(@NonNull ViewGroup viewGroup, @NonNull int[] iArr) {
        super.onDismiss(viewGroup, iArr);
        Map hashMap = new HashMap();
        for (int i : iArr) {
            for (Integer intValue : this.mRunnables.keySet()) {
                int intValue2 = intValue.intValue();
                TimeoutRunnable timeoutRunnable = (TimeoutRunnable) this.mRunnables.get(Integer.valueOf(intValue2));
                if (intValue2 > i) {
                    intValue2--;
                    timeoutRunnable.setPosition(intValue2);
                    hashMap.put(Integer.valueOf(intValue2), timeoutRunnable);
                } else if (intValue2 != i) {
                    hashMap.put(Integer.valueOf(intValue2), timeoutRunnable);
                }
            }
            this.mRunnables.clear();
            this.mRunnables.putAll(hashMap);
            hashMap.clear();
        }
    }

    public void onUndo(@NonNull View view, int i) {
        super.onUndo(view, i);
        cancelCallback(i);
    }

    public void onUndoShown(@NonNull View view, int i) {
        super.onUndoShown(view, i);
        Runnable timeoutRunnable = new TimeoutRunnable(i);
        this.mRunnables.put(Integer.valueOf(i), timeoutRunnable);
        this.mHandler.postDelayed(timeoutRunnable, this.mTimeoutMs);
    }

    public void setTimeoutMs(long j) {
        this.mTimeoutMs = j;
    }
}
