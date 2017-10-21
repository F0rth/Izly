package defpackage;

public final class lr implements lp {
    private final long a = 1000;
    private final int b = 8;

    public lr(long j, int i) {
    }

    public final long getDelayMillis(int i) {
        return (long) (((double) this.a) * Math.pow((double) this.b, (double) i));
    }
}
