package com.crashlytics.android.core;

import java.util.HashMap;
import java.util.Map;

class RemoveRepeatsStrategy implements StackTraceTrimmingStrategy {
    private final int maxRepetitions;

    public RemoveRepeatsStrategy() {
        this(1);
    }

    public RemoveRepeatsStrategy(int i) {
        this.maxRepetitions = i;
    }

    private static boolean isRepeatingSequence(StackTraceElement[] stackTraceElementArr, int i, int i2) {
        int i3 = i2 - i;
        if (i2 + i3 > stackTraceElementArr.length) {
            return false;
        }
        for (int i4 = 0; i4 < i3; i4++) {
            if (!stackTraceElementArr[i + i4].equals(stackTraceElementArr[i2 + i4])) {
                return false;
            }
        }
        return true;
    }

    private static StackTraceElement[] trimRepeats(StackTraceElement[] stackTraceElementArr, int i) {
        Map hashMap = new HashMap();
        Object obj = new StackTraceElement[stackTraceElementArr.length];
        int i2 = 0;
        int i3 = 1;
        int i4 = 0;
        while (i4 < stackTraceElementArr.length) {
            int i5;
            Object obj2 = stackTraceElementArr[i4];
            Integer num = (Integer) hashMap.get(obj2);
            if (num == null || !isRepeatingSequence(stackTraceElementArr, num.intValue(), i4)) {
                obj[i2] = stackTraceElementArr[i4];
                i5 = i2 + 1;
                i3 = i4;
                i2 = 1;
            } else {
                int intValue = i4 - num.intValue();
                if (i3 < i) {
                    System.arraycopy(stackTraceElementArr, i4, obj, i2, intValue);
                    i5 = i2 + intValue;
                    i2 = i3 + 1;
                    i3 = i5;
                } else {
                    int i6 = i3;
                    i3 = i2;
                    i2 = i6;
                }
                i5 = i3;
                i3 = (intValue - 1) + i4;
            }
            hashMap.put(obj2, Integer.valueOf(i4));
            i4 = i3 + 1;
            i3 = i2;
            i2 = i5;
        }
        Object obj3 = new StackTraceElement[i2];
        System.arraycopy(obj, 0, obj3, 0, obj3.length);
        return obj3;
    }

    public StackTraceElement[] getTrimmedStackTrace(StackTraceElement[] stackTraceElementArr) {
        StackTraceElement[] trimRepeats = trimRepeats(stackTraceElementArr, this.maxRepetitions);
        return trimRepeats.length < stackTraceElementArr.length ? trimRepeats : stackTraceElementArr;
    }
}
