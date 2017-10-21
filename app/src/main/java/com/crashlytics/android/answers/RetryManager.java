package com.crashlytics.android.answers;

class RetryManager {
    private static final long NANOSECONDS_IN_MS = 1000000;
    long lastRetry;
    private lt retryState;

    public RetryManager(lt ltVar) {
        if (ltVar == null) {
            throw new NullPointerException("retryState must not be null");
        }
        this.retryState = ltVar;
    }

    public boolean canRetry(long j) {
        lt ltVar = this.retryState;
        return j - this.lastRetry >= ltVar.b.getDelayMillis(ltVar.a) * NANOSECONDS_IN_MS;
    }

    public void recordRetry(long j) {
        this.lastRetry = j;
        lt ltVar = this.retryState;
        this.retryState = new lt(ltVar.a + 1, ltVar.b, ltVar.c);
    }

    public void reset() {
        this.lastRetry = 0;
        lt ltVar = this.retryState;
        this.retryState = new lt(ltVar.b, ltVar.c);
    }
}
