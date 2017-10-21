package com.crashlytics.android.core.internal.models;

public class ThreadData {
    public static final int IMPORTANCE_CRASHED_THREAD = 4;
    public final FrameData[] frames;
    public final int importance;
    public final String name;

    public static final class FrameData {
        public final long address;
        public final String file;
        public final int importance;
        public final long offset;
        public final String symbol;

        public FrameData(long j, int i) {
            this(j, "", i);
        }

        public FrameData(long j, String str, int i) {
            this(j, str, "", 0, i);
        }

        public FrameData(long j, String str, String str2, long j2, int i) {
            this.address = j;
            this.symbol = str;
            this.file = str2;
            this.offset = j2;
            this.importance = i;
        }
    }

    public ThreadData(int i, FrameData[] frameDataArr) {
        this(null, i, frameDataArr);
    }

    public ThreadData(String str, int i, FrameData[] frameDataArr) {
        this.name = str;
        this.importance = i;
        this.frames = frameDataArr;
    }
}
