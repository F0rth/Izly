package com.crashlytics.android.answers;

import java.util.Random;

class RandomBackoff implements lp {
    final lp backoff;
    final double jitterPercent;
    final Random random;

    public RandomBackoff(lp lpVar, double d) {
        this(lpVar, d, new Random());
    }

    public RandomBackoff(lp lpVar, double d, Random random) {
        if (d < 0.0d || d > 1.0d) {
            throw new IllegalArgumentException("jitterPercent must be between 0.0 and 1.0");
        } else if (lpVar == null) {
            throw new NullPointerException("backoff must not be null");
        } else if (random == null) {
            throw new NullPointerException("random must not be null");
        } else {
            this.backoff = lpVar;
            this.jitterPercent = d;
            this.random = random;
        }
    }

    public long getDelayMillis(int i) {
        return (long) (randomJitter() * ((double) this.backoff.getDelayMillis(i)));
    }

    double randomJitter() {
        double d = 1.0d - this.jitterPercent;
        return d + (((this.jitterPercent + 1.0d) - d) * this.random.nextDouble());
    }
}
