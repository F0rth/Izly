package com.ezeeworld.b4s.android.sdk.positioning;

import android.os.SystemClock;

import com.ezeeworld.b4s.android.sdk.B4SLog;

import java.util.ArrayList;

public class TimingLogger {
    ArrayList<Long> a;
    ArrayList<String> b;
    private String c;
    private String d;

    public TimingLogger(String str, String str2) {
        reset(str, str2);
    }

    public void addSplit(String str) {
        this.a.add(Long.valueOf(SystemClock.elapsedRealtime()));
        this.b.add(str);
    }

    public void dumpToLog() {
        B4SLog.ds(this.c, this.d + ": begin");
        long longValue = ((Long) this.a.get(0)).longValue();
        int i = 1;
        long j = longValue;
        while (i < this.a.size()) {
            long longValue2 = ((Long) this.a.get(i)).longValue();
            B4SLog.ds(this.c, this.d + ":      " + (longValue2 - ((Long) this.a.get(i - 1)).longValue()) + " ms, " + ((String) this.b.get(i)));
            i++;
            j = longValue2;
        }
        B4SLog.ds(this.c, this.d + ": end, " + (j - longValue) + " ms");
    }

    public void reset() {
        if (this.a == null) {
            this.a = new ArrayList();
            this.b = new ArrayList();
        } else {
            this.a.clear();
            this.b.clear();
        }
        addSplit(null);
    }

    public void reset(String str, String str2) {
        this.c = str;
        this.d = str2;
        reset();
    }
}
