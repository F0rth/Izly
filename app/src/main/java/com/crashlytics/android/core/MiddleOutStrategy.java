package com.crashlytics.android.core;

class MiddleOutStrategy implements StackTraceTrimmingStrategy {
    private final int trimmedSize;

    public MiddleOutStrategy(int i) {
        this.trimmedSize = i;
    }

    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] stackTraceElementArr) {
        if (stackTraceElementArr.length <= this.trimmedSize) {
            return stackTraceElementArr;
        }
        int i = this.trimmedSize / 2;
        int i2 = this.trimmedSize - i;
        Object obj = new StackTraceElement[this.trimmedSize];
        System.arraycopy(stackTraceElementArr, 0, obj, 0, i2);
        System.arraycopy(stackTraceElementArr, stackTraceElementArr.length - i, obj, i2, i);
        return obj;
    }
}
